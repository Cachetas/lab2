package AppClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
    public static void main(String[] args) {
    	
    	String register = "R", query = "C", delete = "D", quit = "Q", queryAll = "L", instruction, serverIp, nodeIp;
    	String[] words;
    	int wordsCounter, port;
    	boolean exit = false;
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.print("Introduza o IP do Servidor: ");
    	serverIp = sc.nextLine();
        try {
            Socket clientSocket = new Socket(serverIp, 4444);
            //clientSocket.setReuseAddress(true);
            
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            Object response;
            
            out.writeObject("client");
            
           
            
            while (!exit) {
         
	        	System.out.print("\nComandos: R -> Registar (chave-valor); C -> Consultar (chave); D -> Apagar (chave-valor); Q -> Sair;");
	            System.out.print("\nIntroduza os parametros [(comando) (chave) (valor)]: ");
	            
	            instruction = sc.nextLine();
	            
	            //Regex Spliter
	            words = instruction.split("\\s+");
	            wordsCounter = 1;
	            
	            
	            String command = ""; 
	            String key = "";
	            String value = "";
	            
	            		
	            
	            for(String wordIn: words) {

                    if (wordsCounter == 1) {
                        command = wordIn;
                        wordsCounter++;

                    } else if (wordsCounter == 2){
                        key = wordIn;
                        wordsCounter++;

                    } else if (wordsCounter == 3) {
                        value = wordIn;
                        wordsCounter++;

                    } else {
                    	value = value + " " + wordIn;
                    }
	            	
	            }
	            
	            
	           
	            if (!key.isBlank() || !value.isBlank() || command.equals(quit)) {
            		
            	
	            if (command.equals(register) || command.equals(query) || command.equals(delete) || command.equals(queryAll)) {
	            	
	            	 out.writeObject(instruction);

	            	 response = in.readObject();
	            	
	            	 if (response.equals("toNode")) {
	            		 
	            		 nodeIp = (String) in.readObject();
	            		
	            		 port = (int) in.readObject();
	            		 
	            		 out.writeObject("Q");
	            		 
	            		 in.close();
	            		 out.close();
	            		 clientSocket.close();
	            		 
	            		 clientSocket = new Socket(nodeIp, port);
	            		 out = new ObjectOutputStream(clientSocket.getOutputStream());
	                     in = new ObjectInputStream(clientSocket.getInputStream());
	                     
	                     out.writeObject(instruction);
	                     response = in.readObject();
	                     System.out.println(response);
	                     in.close();
	            		 out.close();
	            		 clientSocket.close();
	            		 
	            		 
	            		 
	            		 clientSocket = new Socket(serverIp, 4444);
	            		 out = new ObjectOutputStream(clientSocket.getOutputStream());
	                     in = new ObjectInputStream(clientSocket.getInputStream());
	                     
	                     out.writeObject("client");
	                     
	            	
	            	 
	            	 } else {
	            		 
	            		 System.out.println(response);
	            		 
	            	 }
	            	 	
	            } else if (command.equals(quit)) {
	            	
	            	
	            	out.writeObject(instruction);
	            	clientSocket.close();
	            	
	            	System.out.println("Ligação terminada.");
	            	exit = true;
	            	
	            }		            	
	            } else {
	            
	            	System.out.println("Ocorreu um erro.");
	            }

            }
            
            //out.writeObject(payload);
            
            //System.out.println("[sent] " + payload.getData());
            //System.out.println("[received] " + ((Payload)in.readObject()).getData());
            
        } catch (IOException error) {
            error.printStackTrace();
            
        } catch (ClassNotFoundException error) {
            error.printStackTrace();
            
        } 
        sc.close();
    }
}
