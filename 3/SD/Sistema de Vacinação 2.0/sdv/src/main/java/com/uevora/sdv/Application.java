package com.uevora.sdv;

import com.uevora.sdv.Entity.Agendamento;
import com.uevora.sdv.Entity.Centro;
import com.uevora.sdv.Entity.StockNacional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        Scanner scan = new Scanner(System.in);
        System.out.println("Bem vindo ao Sistema de acompanhamento de vacinação 2.0!");
        System.out.println("\tMódulo DGS");

        while (true) {
            System.out.println(Menu());
            while (!scan.hasNextInt()) {
                System.out.println("Por favor insira um número correto!");
                scan.next();
            }
            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {
                case 0 -> {
                    System.out.println("Adeus!");
                    scan.close();
                    System.exit(0);
                }
                case 1 -> {
                    System.out.println("Lista de centros:");
                    List<Centro> centros =listarCentros();
                    System.out.println("\nCentros:");
                    for (Centro centro : centros) {
                        System.out.println("Centro:");
                        System.out.println("\tNome: " + centro.getNome());
                        System.out.println("\tCapacidade máxima diária: " + centro.getCapacidade_diaria());
                        System.out.println("\tAgendamentos: "+ centro.getAgendamentos().size());
                    }
                }
                case 2 -> {
                    LocalDate data = getDataInput(scan);
                    System.out.println("número de vacinas");
                    int vacinas;
                    try {
                        vacinas = Integer.parseInt(scan.nextLine());
                    } catch (Exception e) {
                        System.out.println("Formato inválido!Por favor introduza um número.");
                        break;
                    }
                    addStockNacional(data.toString(), vacinas);
                }
                case 3 -> {
                    LocalDate data = getDataInput(scan);
                    listAgendamentos(data.toString());

                }
                case 4 -> {
                    LocalDate data = getDataInput(scan);
                    if(data == null)
                        break;
                    List<Centro> centros = listarCentros();
                    System.out.println("Stock nacional: " +getStockNacional(data).getVacinas()+" vacinas.");
                    for (Centro c: centros) {
                        listAgendamentos(data.toString(),c.getNome());
                    }
                    System.out.println("Indique o nome do centro a que deseja distribuir vacinas.");
                    String centro = scan.nextLine();
                    System.out.println("Indique o número de vacinas que pretende distribuir a esse centro");
                    int vacinas = Integer.parseInt(scan.nextLine());
                    distribuirVacinas(data,centro,vacinas);
                }
                case 5 -> {
                    listarVacinados();
                }
                default -> System.out.println("Escolha inválida");
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

    private static StockNacional getStockNacional(LocalDate data){
        RestTemplate restTemplate = new RestTemplate();
        StockNacional stock = null;
        try {
            ResponseEntity<StockNacional> centroResponse =
                    restTemplate.exchange("http://localhost:8080/api/stockNacional/"+data.toString(),
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
            stock = centroResponse.getBody();

        } catch (Exception e) {
        }
        return stock;
    }

    private static void distribuirVacinas(LocalDate data, String centro, int vacinas) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<Agendamento>> agendamentoResponse =
                    restTemplate.exchange("http://localhost:8080/api/stockDiario/centro/"
                                    +centro
                                    +"/data/"
                                    +data.toString()
                                    +"/"+vacinas,
                            HttpMethod.POST, null, new ParameterizedTypeReference<>() {
                            });
        } catch (Exception e) {
        }

    }

    private static void listarVacinados() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<Agendamento>> agendamentoResponse =
                    restTemplate.exchange("http://localhost:8080/api/vacinados",
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
            List<Agendamento> agendamentos = agendamentoResponse.getBody();
            List<LocalDate> datas = new LinkedList<LocalDate>();
            Map<LocalDate, Integer> map = new HashMap<>();
            for (Agendamento agendamento : agendamentos) {
                datas.add(agendamento.getData());
            }
            for (LocalDate data : datas) {
                map.put(data, Collections.frequency(datas, data));
            }
            System.out.println("Data:número de vacinados");
            System.out.println(map);
        } catch (Exception e) {
        }

    }

    private static List<Centro> listarCentros() {
        RestTemplate restTemplate = new RestTemplate();
        List<Centro> centros = null;
        try {
            ResponseEntity<List<Centro>> centroResponse =
                    restTemplate.exchange("http://localhost:8080/api/centro",
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
             centros = centroResponse.getBody();

        } catch (Exception e) {
        }
        return centros;
    }

    private static void addStockNacional(String data, int nVacinas) {
        String url = "http://localhost:8080/api/stockNacional";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("vacinas", nVacinas);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        try {
            restTemplate.postForEntity(url, entity, String.class);
        } catch (HttpServerErrorException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listAgendamentos(String data) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<Agendamento>> agendamentoResponse =
                    restTemplate.exchange("http://localhost:8080/api/agendamentos/data/" + data,
                            HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                            });
            List<Agendamento> agendamentos = agendamentoResponse.getBody();
            System.out.println("\nAgendamentos para: " + data);
            for (Agendamento agendamento : agendamentos) {
                System.out.println("Agendamento:");
                System.out.println("\tCartão de cidadão: " + agendamento.getCc());
                System.out.println("\tIdade: " + agendamento.getIdade());
                System.out.println("\tCentro: " + agendamento.getCentroName());
            }
        } catch (Exception e) {
        }
    }
    private static void listAgendamentos(String data, String centro){
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<Agendamento>> agendamentoResponse =
                    restTemplate.exchange("http://localhost:8080/api/agendamentos/centro/" +
                                    centro
                                    +"/data/"+
                                    data ,
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
            else
                idade = nAgendamentos;
            System.out.println("centro: " + centro
                    +", agendamentos: " + nAgendamentos
                    +", idade média: " + idade
            );

        } catch (Exception e) {
        }
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

    public static String Menu() {
        StringBuilder sbr = new StringBuilder("\n\t\t\tMenu\n");
        sbr.append("---------------------------------------------------------\n");
        sbr.append("1: Consultar centros de vacinação\n");
        sbr.append("2: Inserir stock de vacinas nacional para uma data.\n");
        sbr.append("3: Verificar agendamentos para uma data.\n");
        sbr.append("4: Distribuir vacinas pelos vários centros numa data.\n");
        sbr.append("5: Listar vacinados por dia.\n");
        sbr.append("0: Sair.\n");
        sbr.append("---------------------------------------------------------\n");
        return sbr.toString();

    }

}
