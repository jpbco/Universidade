package com.uevora.sdv.utente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class UtenteApplication {

    private static final Logger log = LoggerFactory.getLogger(UtenteApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(UtenteApplication.class, args);

        Scanner scan = new Scanner(System.in);
        System.out.println("Bem vindo ao Sistema de acompanhamento de vacinação 2.0!");
        System.out.println("\tMódulo Utente");

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
                    System.out.println("Lista de centros:");
                    listarCentros();
                    break;
                case 2:
                    System.out.println("Por favor indique:");
                    System.out.println("o centro em que deseja ser vacinado/a");
                    String centro = scan.nextLine();
                    if (!centroExiste(centro)) {
                        System.out.println("O centro " + centro + "não existe.");
                        break;
                    }
                    System.out.println("o seu nome");
                    String nome = scan.nextLine();
                    System.out.println("o seu número de cartão de cidadão");
                    String cc = scan.nextLine();
                    System.out.println("a sua idade");
                    int idade = 0;
                    try {
                        idade = Integer.parseInt(scan.nextLine());
                    } catch (Exception e) {
                        System.out.println("Idade num formato inválido!\nPor favor introduza um número.");
                        break;
                    }
                    System.out.println("o seu email");
                    String email = scan.nextLine();
                    System.out.println("a data em que prefere ser vacinado");
                    System.out.println("o formato da data deve ser yyyy-mm-dd");
                    System.out.println("ex: 2021-07-18");
                    LocalDate data = null;
                    try {
                        data = LocalDate.parse(scan.nextLine());

                    } catch (Exception e) {
                        System.out.println("Data num formato inválido!");
                        break;
                    }
                    if (data.compareTo(LocalDate.now()) < 0) {
                        System.out.println("Data inválida!\nPor favor introduza um dia no futuro");
                        break;
                    }

                    agendar(nome, cc, idade, email, data, centro);
                    break;
                case 3:
                    System.out.println("Indique o seu número de cartão de cidadão");
                    cc = scan.nextLine();
                    System.out.println(getMensagens(cc));
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

    private static String getMensagens(String cc) {
        String url = "http://localhost:8080/api/utente/mensagens/" + cc;
        RestTemplate restTemplate = new RestTemplate();

        String html = restTemplate.getForObject(url, String.class);
        return html;
    }

    private static void agendar(String nome, String cc, int idade, String email, LocalDate data, String centro) {
        String url = "http://localhost:8080/api/utente";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> map = new HashMap<>();
        map.put("name", nome);
        map.put("idade", idade);
        map.put("cc", cc);
        map.put("email", email);
        map.put("data", data);
        map.put("centroName", centro);


        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        } catch (HttpServerErrorException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarCentros() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<Centro>> centroResponse =
                    restTemplate.exchange("http://localhost:8080/api/centro",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Centro>>() {
                            });
            List<Centro> centros = centroResponse.getBody();
            for (Centro centro : centros) {
                System.out.println("\t" + centro.getNome());
            }
        } catch (Exception e) {

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

    public static String Menu() {
        StringBuilder sbr = new StringBuilder("\n\t\t\tMenu\n");
        sbr.append("---------------------------------------------------------\n");
        sbr.append("1: Consultar centros de vacinação\n");
        sbr.append("2: Realizar agendamento num dos centros.\n");
        sbr.append("3: Consultar mensagens.\n");
        sbr.append("0: Sair.\n");
        sbr.append("---------------------------------------------------------\n");
        return sbr.toString();

    }
}
