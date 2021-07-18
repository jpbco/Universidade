package sd;

import java.io.*;
import java.net.*;


public class EchoServerConcorrente {

    private int serverPort;	
    
    public EchoServerConcorrente(int p) {
	serverPort= p;		
    }
    
    
    public static void main(String[] args){
	System.err.println("SERVER...");
	if (args.length<1) {
	    System.err.println("Missing parameter: port number");	
	    System.exit(1);
	}
	int p=0;
	try {
	    p= Integer.parseInt( args[0] );
	}
	catch (Exception e) {
	    e.printStackTrace();
	    System.exit(2);
	}
	
	
	EchoServerConcorrente serv= new EchoServerConcorrente(p);
	serv.servico();   // activa o servico
    }

    
    // activa o servidor no porto indicado em "serverPort"
    public void servico() {
	
	// um servidor TCP: ver outro exemplo no livro
	
        ServerSocket server= null;
	ServiceManager manager = null;
        try {
	    // inicializar o socket e associa-lo ao porto local
            server= new ServerSocket(serverPort);
            manager = new ServiceManager(server);
            manager.start();
	    // ciclo de atendimento dos pedidos
	    while (true) {
                
		Socket data= server.accept();
                System.err.println("\t chegou um pedido");


                // o atendimento será delegado para um novo objecto...
                
		// EXERCICIO:
		// a) criar instancia da classe que atende pedidos
		//    e invocar o método para realizar o atendimento  (ainda iterativo)
                AtendedorDePedidos atendedor = new AtendedorDePedidos(data);
                //atendedor.atendePedido();
                




		// EXERCICIO:   (não faca esta parte antes de executar a anterior)
		// b) a classe de atendimento vai estender a classe Thread
		//    precisa adicionar-lhe um metodo run(), que invoque o metodo de atendimento
		//    neste local, em vez de invocar diretamente o atendimento, vai ativar a thread, com start()
				   // ... esta thread (principal) segue em frente
                atendedor.start();



                // ... e volta ao ciclo de atendimento
                
            }

	}catch(SocketException e){
            if(! server.isClosed()){
             System.err.println("PROBLEMA no funcionamento do servidor: "+e);
            }
        }
	catch(Exception e) {
	    e.printStackTrace();
	    System.err.println("PROBLEMA no funcionamento do servidor: "+e);
	}
    }


}


// esta classe não pode ser pública se está num ficheiro que ja tem uma classe pública!
class AtendedorDePedidos extends Thread {

    Socket data;

    public AtendedorDePedidos(Socket data) {
        this.data= data;
    }

    public void atendePedido() {
        try {
            DataOutputStream sout = new DataOutputStream(data.getOutputStream());
            BufferedReader breader = new BufferedReader(new InputStreamReader(data.getInputStream()));

            String msgEcho = breader.readLine();
            System.err.println("\t\t pedido: " + msgEcho);


            // espera para simular demora no atendimento do pedido
            Thread.sleep(4000);


            msgEcho = "Ola! " + msgEcho + " Adeus";
            System.err.println("\t\t a resposta vai ser: " + msgEcho);

            sout.writeUTF(msgEcho);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("PROBLEMA no atendimento do pedido: " + e);
        } 
        finally {  // haja ou nao excepcao
            // muito importante: fechar o socket de dados
            if (data != null) {
                try {
                    data.close();        //  MUITO IMPORTANTE... fechar sempre
                } catch (Exception ec) {
                    System.err.println(ec);
                }
            }
        }

    }
    
    public void run(){
        atendePedido();
    }


}
class ServiceManager extends Thread {
    ServerSocket serviceSocket;
    ServerSocket adminSocket;
    
    public ServiceManager(ServerSocket s) {
        // exercício
        serviceSocket= s;
        
    }
    public void run() {

        // exercício
        adminService();
     }

    public void adminService() {
        try {
            // ...
            // aceitar ligações TCP num porto (anterior +1) via adminSocket
            // se... parar o serviço: fechar o socket serviceSocket
            adminSocket = new ServerSocket(serviceSocket.getLocalPort()+1);
            Socket data = adminSocket.accept();
            System.err.println("\t chegou um pedido de ADMINISTRAÇÂO DO SERVIÇO");
            data.close();
            adminSocket.close();
            serviceSocket.close();

            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
