import requests
from xml.etree import ElementTree

api_root_url = "http://dev.virtualearth.net/REST/v1/Locations/PT/"
bing_maps_key = "?o=xml&key=AvMkksfWKfV8cIY_V8p97ySFDqOzZdwjnhO7k8raL1CNU5ZpcrF9dQ_38NHXoEwL"
cidade = input("Cidade?\n");

response = requests.get(api_root_url + cidade + bing_maps_key)
print(response.text)


