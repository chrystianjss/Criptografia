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
				//Abre conex�o com destino: local e porta: 6789
				clientSocket = new Socket("localhost", 6789);
				
				//L� entrada do cliente.
				sentence = inFromUser.readLine();
				
				//Realiza a criptografia dos dados
				Criptografia cripto = new Criptografia();
				byte[] cifra = null;
				cifra = cripto.encrypt(sentence);

				//Cria canal de comunica��o com o servidor
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				
				//Envia a mensagem ao servidor.
				outToServer.write(cifra);
				
				//L� resposta do servidor.
				byte[] cifraServer = new byte[16]; //tamanho que possibilita utilizar os m�todos de Criptografia
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
