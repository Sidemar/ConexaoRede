package TCP;

import java.io.*; 
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        String sentence; 
        String modifiedSentence; 
 
        //Cria stream de entrada
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        
        //Cria socket cliente, conecta ao servidor
        //Socket clientSocket = new Socket("hostname", 6789);
        Socket clientSocket = new Socket("127.0.0.1", 8080); 
        
        //Cria stream de saída ligada ao socket
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
        
        //Cria stream de entrada ligada ao socket
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 
        sentence = inFromUser.readLine(); 
 
        //Envia linha para o servidor
        outToServer.writeBytes(sentence + '\n'); 
 
        //Lê linha do servidor
        modifiedSentence = inFromServer.readLine(); 
 
        System.out.println("FROM SERVER: " + modifiedSentence); 

    }
}
