package UDP;

import java.io.*; 
import java.net.*; 

public class UDPClient {
    public static void main(String[] args) throws Exception {
        //Cria stream de entrada
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); 
        
        //Cria socket cliente
        DatagramSocket clientSocket = new DatagramSocket(); 
 
        //Traduz nome do host para endereço IP usando DNS
        //InetAddress IPAddress = InetAddress.getByName("hostname");
        InetAddress IPAddress = InetAddress.getByName("127.0.0.1");
 
        byte[] sendData = new byte[1024]; 
        byte[] receiveData = new byte[1024]; 

        String sentence = inFromUser.readLine(); 
        sendData = sentence.getBytes();
        
        //Cria datagrama com dados a enviar, tamanho, endereço IP e porta
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);

        //Envia datagrama para servidor
        clientSocket.send(sendPacket);
        
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
        
        //Lê datagrama do servidor 
        clientSocket.receive(receivePacket); 
 
        String modifiedSentence = new String(receivePacket.getData()); 
 
        System.out.println("FROM SERVER:" + modifiedSentence); 
        clientSocket.close(); 
    }
}
