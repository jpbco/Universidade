import socket
import select
import traceback  # para informação de excepções


SOCKET_LIST = []    # lista de sockets abertos
RECV_BUFFER = 4096  # valor recomendado na doc. do python
PORT = 8000         # porta para os clientes
PORT2 = 8001        # porta para os sensores
LISTA = []          # lista de sensores


def leitura(local):
    """[Função que obtém o valor da última leitura feita numa localização]

    Arguments:
        local {[str]} -- [Localização do sensor]
    Return:
        Leitura em forma de string
    """
    for item in LISTA:
        if item[2] == local:
            return f"Leitura no local {local} é do poluente : {item[3]} e teve o valor de {item[4]} µg/m³"

    return f"Não existem leituras no local {local}\n"


def adminleitura(sensorid):
    """[Função utilizada por administradores que obtém o valor da última leitura feita por um Sensor]

    Arguments:
        sensorid {[str]} -- [ID do sensor]
    Return:
        Leitura em forma de string
    """
    for item in LISTA:
        if item[1] == sensorid:
            return f"Leitura do sensor {sensorid} é do poluente : {item[3]} e teve o valor de {item[4]} µg/m³"

    return f"Não existem leituras para o sensor {sensorid}\n"


def listar(tipo):
    """[Função que lista todos os Sensores no sistema que sejam de um determinado tipo]

    Arguments:
        tipo {[str]} -- [Tipo de poluente]
    Return:
        Lista de sensores
    """
    locais = []
    for item in LISTA:
        if item[3] == tipo:
                locais.append(item[2])

    if locais:
        return locais
    else:
        return f"Não existem sensores do tipo {tipo} no Sistema\n"


def atualizar(sensor):
    """[Função que atualiza a ultima leitura de um Sensor no sistma]

    Arguments:
        sensor {[str]} -- [ID do sensor]

    """
    if not LISTA:
        LISTA.append(sensor)
        print(f"-> {sensor} nao estava na lista mas agora ja esta\n")
    else:
        res = sensor[1] in (item for sublist in LISTA for item in sublist)
        if not res:
            LISTA.append(sensor)
        else:
            for item in LISTA:
                if item[1] == sensor[1]:
                    print(
                        f"TROQUEI O VALOR {item[4]} no sensor {item[1]} por {sensor[4]}\n")
                    item[4] = sensor[4]


def interpreta(data):
    """[Função que interpreta a informação recebida nas sockets]

    Arguments:
       data {[str]} -- [String recebida pelo broker proveniente dos clientes e servidos]
    """
    d = data.split()  # transforma a string recebi numa lista para facilitar a sua utilização
    if(len(d) < 2):
        return "Opção inválida!\n"
    elif(d[0] == "Leitura"):
      return leitura(d[1])
    elif(d[0]) == "Listar":
        return listar(d[1])
    elif(d[0]) == "admin":
        if d[1] == 'Listar':
            return LISTA
        elif d[1] == 'Leitura':
            return adminleitura(d[2])
    elif(d[0] == "sensor"):
        atualizar(d)
        return f"Sent from Broker-> {d}\n"


def work(data, sock):
    """[Função que recebe e envia strings através das sockets]
    """
    addr = sock.getpeername()
    data = data.decode()
    print(f"\n Recebi do endereço {addr} a string: {data}\n")

    valor = str(interpreta(data))

    if valor != 'None':
        print(f"Enviei a string: {valor}\n")
    else:
        print(f"Não enviei nada\n")

    print(f"SERVER SIDE LISTA SENSORES-> {LISTA}")
    valor = valor.encode()
    sent = sock.send(valor)

    if sent == 0:
        raise RuntimeError("socket connection broken")


if __name__ == "__main__":
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind(("0.0.0.0", PORT))  # aceita ligações de qualquer lado
    server_socket.listen(100)
    server_socket.setblocking(0)  # o socket deixa de ser blocking

    server_socket2 = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket2.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket2.bind(("0.0.0.0", PORT2))  # aceita ligações de qualquer lado
    server_socket2.listen(100)
    server_socket2.setblocking(0)  # o socket deixa de ser blocking

    # Adicionamos o socket à lista de sockets a monitorizar
    SOCKET_LIST.append(server_socket)
    SOCKET_LIST.append(server_socket2)

    print("Server started on port %d" % (PORT,))

    timecount = 0
    while True:  # ciclo infinito

        # apagamos os sockets que "morreram" entretanto
        for sock in SOCKET_LIST:
            if sock.fileno() < 0:
                SOCKET_LIST.remove(sock)

        # agora, esperamos que haja dados em algum dos sockets que temos
        rsocks, _, _ = select.select(SOCKET_LIST, [], [], 5)

        if len(rsocks) == 0:  # timeout
            timecount += 5
            print("Timeout on select() -> %d secs" % (timecount))
            if timecount % 60 == 0:  # passou um minuto
                timecount = 0
            continue

        for sock in rsocks:  # percorrer os sockets com nova informação

            if sock == server_socket:  # há uma nova ligação
                newsock, addr = server_socket.accept()
                newsock.setblocking(0)
                SOCKET_LIST.append(newsock)

                print(f"New client - {addr}")
            elif sock == server_socket2:
                newsock1, addr = server_socket2.accept()
                newsock1.setblocking(0)
                SOCKET_LIST.append(newsock1)

                print(f"New Sensor - {addr}")
            else:  # há dados num socket ligado a um cliente
                try:
                    data = sock.recv(RECV_BUFFER)
                    if data:
                        work(data, sock)

                    else:  # não há dados, o cliente fechou o socket
                        print("Client disconnected 1")
                        sock.close()
                        SOCKET_LIST.remove(sock)

                except Exception as e:  # excepção ao ler o socket, o cliente fechou ou morreu
                    print("Client disconnected")
                    print("Exception -> %s" % (e,))
                    print(traceback.format_exc())

                    sock.close()
                    SOCKET_LIST.remove(sock)
