package sd;

import java.net.*;
import java.io.*;

public class VeiculosClient {

    private String address = "localhost";
    private int sPort = 0;

    public VeiculosClient(String add, int p) {
        address = add;
        sPort = p;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Argumentos insuficientes:  java VeiculosClient ADDRESS PORT");
            System.exit(1);
        }

        try {
            String addr = args[0];
            int p = Integer.parseInt(args[1]);

            VeiculosClient cl = new VeiculosClient(addr, p);

            cl.menu();   // interage com o utilizador
        } catch (Exception e) {
            System.out.println("Problema no formato dos argumentos");
            e.printStackTrace();

        }
    }

    public void menu() {
        int lidos;
        byte[] b = new byte[256];
        try {
            while (true) {  // ciclo: pede ao utilizador...
                System.out.print("\n\n --- MENU --- \n"
                        + "registar - enviar novo registo para o servidor\n"
                        + "consultar - obter dados sobre o proprietario de um veiculo\n\n> ");

                // ler a opcao
                lidos = System.in.read(b, 0, 256);
                String op = new String(b, 0, lidos - 1);

                if (op.equals("registar")) {
                    // ler dados
                    String s = null;

                    System.out.println("matricula: ");
                    lidos = System.in.read(b, 0, 256);
                    String matricula = new String(b, 0, lidos - 1);

                    System.out.println("nome do proprietario: ");
                    lidos = System.in.read(b, 0, 256);
                    String nome = new String(b, 0, lidos - 1);

                    System.out.println("ano de matricula: ");
                    lidos = System.in.read(b, 0, 256);
                    s = new String(b, 0, lidos - 1);
                    int ano = Integer.parseInt(s);

                    PedidoDeRegisto pr = new PedidoDeRegisto(new Registo(matricula, nome, ano));
                     System.out.println("VOU ENVIAR PEDIDO");
                    enviaPedido(pr);
                    System.out.println("pedido de consulta enviado "+ pr.toString());
                } else if (op.equals("consultar")) {
                    System.out.println("matricula: ");

                    lidos = System.in.read(b, 0, 256);
                    String matricula = new String(b, 0, lidos - 1);
                    
                    PedidoDeConsulta pc = new PedidoDeConsulta(matricula);
                    System.out.println("VOU ENVIAR PEDIDO");
                    enviaPedido(pc);
                    System.out.println("pedido de consulta enviado " + pc.toString());
                } else {
                    System.out.println("opcao invalida");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enviaPedido(Pedido p) {
        try {
            System.out.println("A");
            Socket s = new Socket(address, sPort);
            
            // que Streams usar????
            System.out.println("B");
            ObjectOutputStream output = new ObjectOutputStream(s.getOutputStream());
            System.out.println("C");
         
            // escrever a mensagem?
            
            output.writeObject(p);
            System.out.println("D");
            // ler a resposta 
            ObjectInputStream input = new ObjectInputStream(s.getInputStream());
            System.out.println("E");
            Object recebido = input.readObject();
            System.out.println("F");
            System.out.println(recebido);
            // fechar o socket
            
            s.close();
            // ver a resposta - modo simples
            System.out.println("RESPOSTA: " + recebido);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
