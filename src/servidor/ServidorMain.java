package servidor;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import util.Criptografia;

public class ServidorMain {

	public static void main(String[] args) {
		String capitalizedSentence;
		ServerSocket welcomeSocket;
		
		try {
			welcomeSocket = new ServerSocket(6789);
			String clientSentence = null;
			
			while (true) {
				System.out.println("Servidor TCP ouvindo...");
				
				//Aceitando conexões de clientes.
				Socket connectionSocket = welcomeSocket.accept();
				
				//Abrindo canal de comunicação para escrita no socket.
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				
				//Lendo dados recebidos.
				byte[] cifraClient = new byte[16]; //Tamanho que possibilita utilizar os métodos de Criptografia
				connectionSocket.getInputStream().read(cifraClient);
				
				//Decriptação da mensagem do cliente.
				Criptografia cripto = new Criptografia();
				clientSentence = cripto.decrypt(cifraClient);
				System.out.println("Recebido: " + clientSentence);
				
				//A resposta será a mesma mensagem, porém captalizada.
				capitalizedSentence = clientSentence.toUpperCase();
				
				//Trecho que criptografa a mensagem modificada e envia para o cliente.
				byte[] cifra = null;
				cifra = cripto.encrypt(capitalizedSentence);
				outToClient.write(cifra);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
