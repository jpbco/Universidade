# Imports necessarios
import requests
from fake_useragent import UserAgent
from stem import Signal
from stem.control import Controller
from pprint import pprint
import json
import os

# Mostrar o nosso ip verdadeiro
real_ip = requests.get("http://httpbin.org/ip").text
print(f"This is your ip address: {real_ip}")

# Encaminhar o nosso pedido atraves de proxies
session = requests.session()
session.proxies = {
    "http": "socks5://127.0.0.1:9050",
    "https": "socks5://127.0.0.1:9050",
}
# Randomizar o user-agent do nosso pedido
session.headers = {"User-Agent": UserAgent().random}

# Obter uma nova identidade Tor
# Ver documentacao oficial para uma explicacao em detalhe
with Controller.from_port(port=9051) as controller:
    controller.authenticate()
    controller.signal(Signal.NEWNYM)

# Mostrar o ip do servidor proxy
fake_ip = session.get("http://httpbin.org/ip").text

if real_ip == fake_ip:
    # impedir que pedidos sejam efetuados com o nosso ip verdadeiro
    print("Real IP detected")
    print("Please check if Tor is running and if a proxy is being used")
    exit(1)
else:
    # realizar o pedido encaminhando o trafego para um proxy
    print(f"This is the ip address used to make the request: {fake_ip}")
    api_url = "https://api.openaq.org/v2/countries/PT"
    response_text = json.loads(session.get(api_url).text)
    pprint(response_text)
