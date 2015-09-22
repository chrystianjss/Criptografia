package cliente;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import util.Criptografia;

public class ClienteMain {

	public static void main(String[] args) {
		String sentence;
		String modifiedSentence = null;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		while(true){
			System.out.println("Digite algo para enviar ao servidor TCP...");

			Socket clientSocket;
			try {
				//Abre conexão com destino: local e porta: 6789
				clientSocket = new Socket("localhost", 6789);
				
				//Lê entrada do cliente.
				sentence = inFromUser.readLine();
				
				//Realiza a criptografia dos dados
				Criptografia cripto = new Criptografia();
				byte[] cifra = null;
				cifra = cripto.encrypt(sentence);

				//Cria canal de comunicação com o servidor
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				
				//Envia a mensagem ao servidor.
				outToServer.write(cifra);
				
				//Lê resposta do servidor.
				byte[] cifraServer = new byte[16]; //tamanho que possibilita utilizar os métodos de Criptografia
				clientSocket.getInputStream().read(cifraServer);
						
				//Trecho que decriptografa a mensagem do servidor.
				modifiedSentence = cripto.decrypt(cifraServer);
				System.out.println("Recebido do servidor TCP: " + modifiedSentence);
				clientSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
