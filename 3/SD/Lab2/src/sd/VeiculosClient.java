package sd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class VeiculosClient {

    private String address = null;
    private int serverPort = 0;

    public VeiculosClient(String add, int p) {
        address = add;
        serverPort = p;
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Argumentos insuficientes:  java EchoClient ADDRESS PORT");
            System.exit(1);
        } else if (args.length == 3) {
            try {
                String addr = args[0];
                int port = Integer.parseInt(args[1]);

                VeiculosClient client = new VeiculosClient(addr, port);

                String matricula = args[2];
                System.out.println("Consulta: "+ matricula);
                client.consulta(matricula);
            } catch (Exception e) {
                // TODO: handle exception
            }
        } else if (args.length == 4) {
            try {
                String addr = args[0];
                int port = Integer.parseInt(args[1]);

                VeiculosClient client = new VeiculosClient(addr, port);
                String nome = args[2];
                String matricula = args[3];
                System.out.println("Registo:" + nome + " " + matricula);
                client.registo(nome,matricula);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }

    public void consulta(String matricula) {
        try {
            Socket s = new Socket(address, serverPort);
            OutputStream socketOut = s.getOutputStream();
            String pedido = "consulta " + matricula +"\n";
            socketOut.write(pedido.getBytes());
            System.out.println("Pedido consulta "+ pedido);
            InputStream socketIn = s.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(socketIn));
            String recebida = br.readLine();
            System.out.println("RESPOSTA DO SERVIDOR -> " + recebida);
            s.close();

        } catch (Exception e) {
            //TODO: handle exception
        }

    }

    public void registo(String nome, String matricula){
        try {
            Socket s = new Socket(address, serverPort);
            OutputStream socketOut = s.getOutputStream();
            String pedido = "registo " + nome + " " + matricula+"\n";
            System.out.println("PEDIDO ENVIADO -> " + pedido);
            socketOut.write(pedido.getBytes());
            

            InputStream socketIn = s.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(socketIn));
            String recebida = br.readLine();
            System.out.println("RESPOSTA DO SERVIDOR -> " + recebida);
            s.close();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

}
