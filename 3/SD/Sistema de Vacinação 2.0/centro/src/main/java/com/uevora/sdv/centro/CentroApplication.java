package com.uevora.sdv.centro;

import org.apache.coyote.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class CentroApplication {

    public static void main(String[] args) {
        SpringApplication.run(CentroApplication.class, args);

        Scanner scan = new Scanner(System.in);
        System.out.println("Bem vindo ao Sistema de acompanhamento de vacinação 2.0!");
        System.out.println("\tMódulo Centro");
        System.out.println("\tLogin\nPor favor indique o nome do seu centro");
        String centroName = scan.nextLine();
        if(!centroName.equals("DGS")){
            while(centroExiste(centroName)){
                System.out.println("\tErro! Centro não encontrado\nPor favor indique o nome de um centro existente ou peça o registo por parte da DGS");
                centroName = scan.nextLine();
            }
        }

        while (true) {
            System.out.println(Menu());
            while (!scan.hasNextInt()) {
                System.out.println("Por favor insira um número correto!");
                scan.next();
            }
            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Adeus!");
                    scan.close();
                    System.exit(0);
                case 1:
                    System.out.println("registar centro:");
                    System.out.println("Nome do centro: ");
                    String name = scan.nextLine();

                    System.out.println("Capacidade de vacinação máxima diária: ");
                    int capacidade = 0;
                    try {
                        capacidade = Integer.parseInt(scan.nextLine());
                    } catch (Exception e) {
                        System.out.println("Capacidade num formato inválido!\nPor favor introduza um número.");
                        break;
                    }

                    registarCentro(name, capacidade);
                    break;
                case 2:
                    System.out.println("realizar vacinaçao");
                    String cc = scan.nextLine();
                    vacinar(cc);
                    break;
                case 3:
                    System.out.println("consultar vacinas");
                    LocalDate data = getDataInput(scan);
                    getVacinas(data,centroName);
                    break;
                case 4:
                    System.out.println("consultar agendamento");
                    data = getDataInput(scan);
                    getAgendamentos(data,centroName);
                    break;
                default:
                    System.out.println("Escolha inválida");
                    break;
            }
            while (true) {
                System.out.println("Retornar ao menu? [y/n]");
                String reset = scan.nextLine();
                if (reset.startsWith("y")) {
                    break;
                } else if (reset.startsWith("n")) {
                    System.out.println("Adeus!");
                    System.exit(0);
                }
            }
        }
    }

    private static void getVacinas(LocalDate data, String centro) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<StockDiario> agendamentoResponse =
                    restTemplate.exchange("http://localhost:8080/api/stockDiario/centro/" +
                                    centro
                                    +"/data/"+
                                    data.toString() ,
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
            StockDiario vacinas = agendamentoResponse.getBody();
            System.out.println("dia: " + data.toString()
                    +", stock de vacinas: " + vacinas.getVacinas()
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    private static boolean centroExiste(String name) {
        boolean resposta = false;
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Centro> centroResponse =
                    restTemplate.exchange("http://localhost:8080/api/centro/" + name,
                            HttpMethod.GET, null, new ParameterizedTypeReference<Centro>() {
                            });
            resposta = centroResponse.getStatusCodeValue() == 200;
        } catch (Exception e) {
        }

        return resposta;
    }

    private static void getAgendamentos(LocalDate data, String centro){
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<Agendamento>> agendamentoResponse =
                    restTemplate.exchange("http://localhost:8080/api/agendamentos/centro/" +
                                    centro
                                    +"/data/"+
                                    data.toString() ,
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
            List<Agendamento> agendamentos = agendamentoResponse.getBody();
            int nAgendamentos = agendamentos.size();
            int idade = 0;
            for (Agendamento a: agendamentos) {
                idade+=a.getIdade();
            }
            if(nAgendamentos!=0)
                idade /= nAgendamentos;
            System.out.println("dia: " + data.toString()
                    +", agendamentos: " + nAgendamentos
                    +", idade média: " + idade
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    private static void vacinar(String cc) {
        ;
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/api/vacinar/" + cc,
                            HttpMethod.PUT, null, new ParameterizedTypeReference<>() {
                            });
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Vacinação bem sucedida!.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

    private static void registarCentro(String name, int capacidade) {
        String url = "http://localhost:8080/api/centro";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> map = new HashMap<>();
        map.put("nome", name);
        map.put("capacidade_diaria", capacidade);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Registação bem sucedida!.");
            }
        } catch (HttpServerErrorException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String Menu() {
        StringBuilder sbr = new StringBuilder("\n\t\t\tMenu\n");
        sbr.append("---------------------------------------------------------\n");
        sbr.append("1: Registar novo centro.\n");
        sbr.append("2: Realizar a vacinação de um utente.\n");
        sbr.append("3: Consultar stock de vacinas para uma data.\n");
        sbr.append("4: Consultar número de agendamentos para uma data.\n");

        sbr.append("0: Sair.\n");
        sbr.append("---------------------------------------------------------\n");
        return sbr.toString();

    }

    private static LocalDate getDataInput(Scanner scan) {
        System.out.println("Por favor indique:");
        System.out.println("a data utilizando o formato yyyy-mm-dd\"");
        System.out.println("ex: 2021-07-18");
        String data = scan.nextLine();
        LocalDate data1 = null;
        try {
            data1 = LocalDate.parse(data);
            if (data1.compareTo(LocalDate.now()) < 0) {
                System.out.println("Data inválida!\nPor favor introduza um dia no futuro");

            }
        } catch (Exception e) {
            System.out.println("Data num formato inválido!");

        }

        return data1;
    }
}
