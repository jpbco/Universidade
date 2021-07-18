import socket
import time

PORT = 8000


def main(task, usertype):
    """[Função que transforma o input do utilizador numa string para ser enviada ao broker]

    Arguments:
        task {[str]} -- [Tipo de pedido]
        usertype {[str]} -- [Tipo de utilizador]
    """
    if task == "1":
        tipo = input("Que tipo de sensor procura?\n")
        return f"Listar {tipo} "
    elif task == "2":
        localizacao = input("Qual localização?\n")
        return f"Leitura {localizacao}\n"
    elif task == "3":
        if usertype == 'admin':
            return "admin Listar"
        else:
            print("You must be an administrator to use this function\n")
            return "error"
    elif task == "4":
        if usertype == 'admin':
            sensorid = input("Sensor ID:?\n")
            return f"admin Leitura {sensorid} "
        else:
            print("You must be an administrator to use this function\n")
            return "error"
    else:
        return "error"


def login():
    """[Função utilizada para distiguir entre um utilizador e um administrador]


    Returns:
        tipo de utilizador
    """

    permission = input("Login\n1: Cliente\n2:Administador\n")
    if permission == "1":
        return 'cliente'
    elif permission == "2":
        password = input("Please input the administrator credentials\n")
        if password == 'admin20':
            print("Welcome admin.\n")
            return 'admin'
        else:
            print("Wrong password.\n Logging in as a normal Client\n")
            return 'cliente'
    else:
        return 'cliente'


if __name__ == "__main__":
    clientsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    clientsocket.connect(('localhost', PORT))
    print("Bem vindo,\nEsta aplicação permite-lhe obter várias informações em relação á qualidade do ar de uma cidade.\n")
    usertype = login()
    while True:

        ola = input(
            'Que informação deseja obter?\n \t 1: Listar locais ondem existem sensores de um determinado tipo\n \t 2: Obter ultima leitura de uma '
            'localização\n \t 3: Listar todos os sensores connectados ao servidor (admin only)\n \t 4: Obter ultima leitura de um sensor em especifico (admin only)\n')
        valor = main(ola, usertype)

        sent = clientsocket.send(valor.encode())
        if sent == 0:
            raise RuntimeError("socket connection broken")
        result = clientsocket.recv(4096)
        result = result.decode()
        if(result == None):
            result = 'Não recebi nada\n'
        print(result)
