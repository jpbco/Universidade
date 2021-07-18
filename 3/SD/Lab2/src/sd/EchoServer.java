package sd;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;

public class EchoServer {

	private int serverPort;

	public EchoServer(int p) {
		serverPort = p;
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

		EchoServer serv = new EchoServer(p);
		serv.servico2(); // activa o servico
	}

	// activa o servidor no porto indicado em "serverPort"
	public void servico() {
		try {
			ServerSocket server = new ServerSocket(serverPort);
			Socket s = server.accept();

			InputStream socketIn = s.getInputStream();
			// byte[] bytes = socketIn.readAllBytes();
			BufferedReader br = new BufferedReader(new InputStreamReader(socketIn));
			// byte[] b= new byte[256];
			// int lidos= socketIn.read(b,0,256);

			// String recebida = new String(b,0,lidos);
			String recebida = br.readLine();
			System.out.println("Recebi " + recebida);

			String msg = "Mensagem " + recebida + "adulterada pelo servidor!\n";

			// escrever a mensagem?
			OutputStream socketOut = s.getOutputStream();
			socketOut.write(msg.getBytes());

			s.close();
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void servico2() {
		try {
			ServerSocket s = new ServerSocket(serverPort);
			while (true) {
				Socket data = s.accept();

				try {
					BufferedReader breader = new BufferedReader(new InputStreamReader(data.getInputStream()));
					String msg = breader.readLine();
					DataOutputStream sout = new DataOutputStream(data.getOutputStream());
					sout.writeChars(msg);
					System.out.println(msg);

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
