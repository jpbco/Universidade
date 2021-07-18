package sd;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

public class VeiculosServer {
    private int serverPort;	
    
    private List<Registo> repositorio;
    
    public VeiculosServer(int p) {
	serverPort= p;		
	repositorio= new LinkedList<Registo>(); // INICIALIZE com LinkedList
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
	
	
	VeiculosServer serv= new VeiculosServer(p);
        serv.servico();   // activa o servico
    }
    
    





    public void servico() {

	try {

	    // inicializa o socket para receber ligacoes
	    ServerSocket s = new ServerSocket(serverPort);
            System.out.println("Server is now listening on port: "+ serverPort);
	    while (true) {
		// espera uma ligacao
		// ... accept()
		Socket socket = s.accept();
		try {
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		    Object objPedido= input.readObject();
		    // le os dados do pedido, como um OBJECTO "objPedido"		
                    Registo r = null;
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		    
		    // apreciar o objecto com o pedido recebido:
		    if (objPedido==null)
			System.out.println("PEDIDO NULL: esqueceu-se de alguma coisa");
		    
		    if (objPedido instanceof PedidoDeConsulta) {
			PedidoDeConsulta pc= (PedidoDeConsulta) objPedido;
			System.out.println("Recebi registo");
			// procurar o registo associado a matricula pretendida
                        for (Registo registo : repositorio) {
                            if(registo.getMatricula().equals(pc.getMatricula())){
                                r = registo;
                            }
                        }
			// pesquisar no servidor (List, mais tarde num ficheiro)

			// enviar objecto Cliente via socket		    
			// se encontra devolve o registo, se não, devolve um novo objecto ou string a representar que nao encontrou	
                        if(r != null){
                            output.writeObject(r);
                        }else{
                            output.writeObject("O seu veículo não se encontra na base de dados.");
                        }
                        


			
		    }
		    else if (objPedido instanceof PedidoDeRegisto) {
			PedidoDeRegisto pr= (PedidoDeRegisto) objPedido; // ...
			System.out.println("Recebi pedido");

                        boolean flag = false;
			// ver se ja existia registo desta matricula
                       for (Registo registo : repositorio) {
                            if(registo.getMatricula().equals(pr.getRegisto().getMatricula())){
                                flag = true;
                            }
                        }
			
			// adicionar ao rep local (se nao e' uma repeticao)
                        if(flag){
                            output.writeObject("O veículo já se encontra na base de dados.");
                        }else{
                            
                            repositorio.add(pr.getRegisto());
                            output.writeObject("O veículo foi registado com sucesso.");
                        }
			
			// responder ao cliente


		    }
		    else
			System.out.println("PROBLEMA");
		    
                }
                catch (Exception exNoAtendimento) {
                    exNoAtendimento.printStackTrace();
                }
                finally { 
                    // fechar o socket de dados dedicado a este cliente:
                    try {
                        socket.close();
                        //AQUI: fechar o socket de dados
                    }
                    catch (Exception e002) {
                    }
                }
                
		
	    
		
	    }  // ... ciclo de atendimento
	
	}
	catch (Exception problemaBindAccept) {
	    problemaBindAccept.printStackTrace();
	}

    }


}
