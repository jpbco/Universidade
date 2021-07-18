package sd;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.util.HashMap;

public class VeiculosServer {

    private int serverPort;
    private HashMap<String, String> table;

    public VeiculosServer(int p) {
        serverPort = p;
        table = new HashMap<String, String>();
    }

    public static void main(String[] args) {
        System.err.println("SERVER...");
        if (args.length < 1) {
            System.err.println("Missing parameter: port number");
            System.exit(1);
        }
        int p = 0;
        try {
            p = Integer.parseInt(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }

        VeiculosServer serv = new VeiculosServer(p);
        serv.servico(); // activa o servico
    }

    public void servico() {
        try {
            ServerSocket server = new ServerSocket(serverPort);
            System.out.println("Server is now listening on port: "+ serverPort);
            while (true) {
                
                Socket s = server.accept();

                InputStream socketIn = s.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(socketIn));

                String recebida = br.readLine();
                System.out.println("Recebi " + recebida);
                String[] sa = recebida.trim().split(" ");
                String msg = recebida;

                if (sa[0].equals("consulta")) {
                    if (table.containsKey(sa[1])){
                        msg = table.get(sa[1]);
                    }else{
                        msg = "Not found!";
                    }
                } else if (sa[0].equals("registo")) {
                    table.put(sa[1], sa[2]);
                    System.out.println("Added " + sa[1] + " " + sa[2]);
                    msg = "Added " + sa[1] + " " + sa[2];
                } else {
                    System.out.println("Debug " + sa.toString());
                }

                OutputStream socketOut = s.getOutputStream();
                msg = msg +"\n";
                socketOut.write(msg.getBytes());

                //s.close();
               // server.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
