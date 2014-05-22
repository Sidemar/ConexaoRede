package Trabalho;

import java.io.*;
import java.net.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class ServidorHttp {
    public static void main(String[] args) throws Exception{
        //Cria socket de aceitação na porta 8080
        ServerSocket welcomeSocket = new ServerSocket(8080);

        while (true) {
            //Espera, no socket de aceitação por contato do cliente
            Socket connectionSocket = welcomeSocket.accept();
            
            //Cria stream de entrada, ligado ao socket
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));				

            //Cria stream de saída, ligado ao socket 
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            
            String cabecalho = "";
            while (inFromClient.ready()){
                //Lê linha do socket 
                cabecalho += inFromClient.readLine()+"\n";
            }
            
            StringTokenizer st = null;
            String pagina = "";
            try {
                st = new StringTokenizer(cabecalho);
                st.nextToken();
                pagina = st.nextToken();
            } catch (Exception e) {
                System.out.println("Erro na requisicao");
                pagina = "/";
            }
            
            //System.out.println("PALAVRA="+pagina);
            
            //Imprime mensagem do cliente
            System.out.println(cabecalho);
        
            String conteudo = "";
            //lê arquivo
            
            int cod = 0;
            BufferedReader br = null;
            if(pagina.equals("/") || pagina.equals(""))
            {
                try {
                    br = new BufferedReader(new FileReader("D:/Documentos/NetBeansProjects/ConexaoRede/src/Trabalho/index.html"));
                    while(br.ready()){  
                        conteudo += br.readLine();  
                    }  
                    br.close();
                    cod = 200;
                }
                catch (IOException ex) {
                    System.out.println("Nao encontrou o arquivo");
                }
                
            } else {
                try {
                    br = new BufferedReader(new FileReader("D:/Documentos/NetBeansProjects/ConexaoRede/src/Trabalho"+pagina));
                    while(br.ready()){  
                    conteudo += br.readLine();  
                }  
                br.close();
                cod = 200;
                } catch (Exception e) {
                        
                    cod = 404;
                    try {
                        br = new BufferedReader(new FileReader("D:/Documentos/NetBeansProjects/ConexaoRede/src/Trabalho/erro.html"));
                        while(br.ready()){  
                            conteudo += br.readLine();  
                        }  
                        br.close();
                    } catch (Exception ex) {
                        System.out.println("Nao encontrou o arquivo");
                    }
                }   
            }
            
            
            String repostaServidor = "HTTP/1.1 ";
            switch (cod) {
                case 200:
                    repostaServidor += "200 OK";
                    break;
                case 404:
                    repostaServidor += "404 Not Found";
                    break;
            }
            
            Date date = new Date();
            Format formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
            String s = formatter.format(date);
            
            repostaServidor += repostaServidor + "\r\n";
            repostaServidor += repostaServidor + "Connection: close\r\n";
            repostaServidor += repostaServidor + "Date: "+ s + " GMT" + "\r\n";
            repostaServidor += repostaServidor + "Server: Apache/1.3.0 (Unix)\r\n";	
            repostaServidor += repostaServidor + "Last-Modified: " + s + " GMT" + "\r\n";
            //repostaServidor += repostaServidor + "Content-Length: 6821\r\n";
            repostaServidor += repostaServidor + "Content-Type: text/html\r\n";
            repostaServidor += repostaServidor + "\r\n";

            try {
                //Escreve linha para o socket
                outToClient.writeBytes(repostaServidor);
                outToClient.writeBytes(conteudo);
                outToClient.close();
                connectionSocket.close();
            } catch (IOException ex) {
                System.out.println("Nao conseguiu enviar dados");
            }
        }
    }
}
