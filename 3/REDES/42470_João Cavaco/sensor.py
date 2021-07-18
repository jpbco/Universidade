import urllib3
import json
import socket
import select
import time


def get_locations(city, parameter='default'):
    """[Função que lista os locais onde existem sensores de determinados tipos]

    Arguments:
        city {[str]} -- [Cidade]
        parameters {[str]} -- [Diferentes poluentes que podem ser detetados pelo sensor]
    Return:
        Lista de sensores
    """
    if parameter != 'default':
        http = urllib3.PoolManager()
        r = http.request(
            'GET',
            'https://api.openaq.org/v1/locations',
            fields={'city': city, 'parameter': parameter}
        )
    else:
        http = urllib3.PoolManager()
        r = http.request(
            'GET',
            'https://api.openaq.org/v1/locations',
            fields={'city': city}
        )

    data = json.loads(r.data.decode('utf-8'))

    sensores = []

    for item in data['results']:
        sensores.append(
            Sensor(item['id'], item['country'], item['city'], item['location'], item['parameters']))
    return sensores


def sensor_spawn(city):
    """[Função que obtém todos os sensores de uma cidade]

    Arguments:
        city {[str]} -- [Cidade]
    Return:
        Lista de sensores 
    """
    sensores = []
    for count, item in enumerate(get_locations(city.title())):
        sensores.append(item)
        print(str(count + 1) + ":" + item.describe_sensor() + "\n")
    return sensores


class Sensor:

    def __init__(self, s_id, country, city, location, parameters):
        self.s_id = s_id
        self.country = country
        self.city = city
        self.location = location
        self.parameters = parameters

    def describe_sensor(self):
        """[Função que descreve um sensor]
        """

        return(
            f"Sensor ID: {self.s_id}, country: {self.country}, city: {self.city}, location : {self.location}, "
            f"Sensor parameters: {self.parameters}\n")

    def get_latest(self, parameter):
        """[Função que descreve um sensor]

        Arguments:
            parameter {[str]} -- [Tipo de poluente]
        Return:
            String com a informação em relação á leitura mais recente do poluente.
        """
        if parameter in self.parameters:
            http = urllib3.PoolManager()
            r = http.request(
                'GET',
                'https://api.openaq.org/v1/latest',
                fields={'location': f'{self.location}',
                        'parameter': parameter}
            )
            data = json.loads(r.data.decode('utf-8'))

            results = data['results']

            for item in results:
                # return 0 porque só vai existir um item no array em todas a situaçoes devido ao facto do pedido do
                # cliente ser a leitura acerca de um unico poluente
                return f"sensor {self.s_id} {self.location} {item['measurements'][0]['parameter']} {item['measurements'][0]['value']}"

        else:
            return (f'O sensor {self.s_id} não suporta a deteção de {parameter}')


SOCKET_LIST = []    # lista de sockets abertos
RECV_BUFFER = 4096  # valor recomendado na doc. do python
PORT = 8001
# intervalo de tempo entre cada envio da leitura do sensor ao broker

if __name__ == "__main__":
    sensores = []
    while not sensores:
        cidade = input('Cidade?\n')
        sensores = sensor_spawn(cidade)
        if not sensores:
            print("Não existem sensores nessa cidade\n")
        else:
            pass

    sensor = input('Qual?\n')
    parametro = input('Poluente?\n')
    DELAY_TIME = int(
        input("Intervalo de tempo para a leitura do parametro.\n"))
    a = sensores[int(sensor)-1]

    clientsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    clientsocket.connect(('localhost', PORT))
    print("Este sensor->" + a.describe_sensor()+"\n")
    while True:

        valor = a.get_latest(parametro)
        print(f"Enviei a string:  {valor}\n")

        sent = clientsocket.send(valor.encode())
        if sent == 0:
            raise RuntimeError("socket connection broken")
        result = clientsocket.recv(4096)
        result = result.decode()
        if(result == 'None'):
            result = 'Não recebi nada\n'
        print("Recebi do broker: " + result + "\n")
        time.sleep(DELAY_TIME)
