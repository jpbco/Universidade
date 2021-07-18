package pt.uevora.ServicoDeVacinas;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Cliente {

    public static String Menu() {
        StringBuilder sbr = new StringBuilder("\n\t\t\tMenu\n");
        sbr.append("---------------------------------------------------------\n");
        sbr.append("1: Consultar centros de vacinação\n");
        sbr.append("2: Consultar a fila de esperar de um centro de vacinação.\n");
        sbr.append("3: Inscrição para vacinação.\n");
        sbr.append("4: Registar vacinação.\n");
        sbr.append("5: Reportar efeitos secundários.\n");
        sbr.append("6: Informações por vacina:\n");
        sbr.append("\t* Vacinados;\n\t* Casos com efeitos secundários.\n");
        sbr.append("0: Sair.\n");
        sbr.append("---------------------------------------------------------\n");
        return sbr.toString();

    }

    public static void main(String args[]) {

        if (args.length != 2) // requer 2 argumentos
        {
            System.out.println(args.length);
            System.out.println("Usage:java ... registryHost registryPort");
            System.exit(1);
        }
        String regHost = args[0];
        String regPort = args[1];

        try {
            // objeto que fica associado ao proxy para objeto remoto
            Servico obj = (Servico) java.rmi.Naming.lookup("rmi://" + regHost + ":" + regPort + "/servico");

            Scanner scan = new Scanner(System.in);
            System.out.println("Bem vindo ao Sistema de acompanhamento de vacinação 1.0!");

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
                        String a1 = obj.consultarCentrosVacinacao();
                        System.out.println(a1);
                        break;
                    case 2:
                        System.out.println("Indique o centro que pretende consultar:\n");
                        String centro = scan.nextLine();
                        String a2 = obj.consultarFilaEsperaCentro(centro);
                        System.out.println(a2);
                        break;
                    case 3:
                        System.out.println("Indique o centro em que pretende ser vacinado:\n");
                        String centro1 = scan.nextLine();
                        System.out.println("Indique o seu nome:\n");
                        String nome = scan.nextLine();
                        System.out.println("Indique o seu cc:\n");
                        String cc1 = scan.nextLine();
                        System.out.println("Indique a sua idade:\n");
                        while (!scan.hasNextInt()) {
                            System.out.println("Por favor insira um número!");
                            scan.next();
                        }

                        int idade = scan.nextInt();
                        scan.nextLine();
                        System.out.println("Indique o seu género:\n");
                        String genero = scan.nextLine();
                        System.out.println(obj.increverVacinar(centro1, nome, cc1, idade, genero));
                        break;
                    case 4:
                        System.out.println("Insira o código único que lhe foi atribuído");
                        String cc = scan.nextLine();
                        System.out.println("Insira o tipo de vacina que lhe foi administrada.");
                        System.out.println(obj.getVacinas());
                        String vacina = scan.nextLine();
                        String a4 = obj.registarVacina(cc, vacina);
                        System.out.println(a4);
                        break;
                    case 5:
                        System.out.println("Insira o código único que lhe foi atribuído");
                        String cc2 = scan.nextLine();
                        System.out.println(obj.reportarEfeitos(cc2));

                        break;
                    case 6:
                        String a6 = obj.listarVacinados();
                        System.out.println(a6);
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
                        System.exit(0);
                    }
                }

            }

        } catch (MalformedURLException | NotBoundException | RemoteException ex) {
            ex.printStackTrace();
        }
    }
}
