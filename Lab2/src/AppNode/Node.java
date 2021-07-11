package AppNode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.ArrayList;
import java.util.Scanner;


public class Node {

	private static String ip;
	private static String key;
	
	static String command;

	private static int port;
	
	static GroupOfIdentifiers newGroupOfIdentifiers;
	
	public static void main(String[] args) {
		
		
		String register = "R", query = "C", delete = "D", queryAll = "L", serverIp;
		String [] words;
		int wordsCounter, identifier;
		boolean exit = false, search;
		
		ObjectOutputStream out;
		ObjectInputStream in;
		Socket nodeSocket;
		ServerSocket serverNodeSocket;
		
		Object response;
		
		HashTable hashTable = new HashTable(key, null);
		NodeTable nodeTable = new NodeTable(ip, port, newGroupOfIdentifiers);
		
		Scanner sc = new Scanner(System.in);
    	
    	System.out.print("Introduza o IP do Servidor: ");
    	serverIp = sc.nextLine();
		

		try {
			
			nodeSocket = new Socket(serverIp, 4444);
	        
	        out = new ObjectOutputStream(nodeSocket.getOutputStream());
	        in = new ObjectInputStream(nodeSocket.getInputStream());
	        
	        
	        
	        out.writeObject("node");
            
            port = (int) (response = in.readObject());
            ip = (String) in.readObject();
            
            
            System.out.println("Endereço deste Nó: " + ip + ":" + port);
            
            out.close();
            in.close();
            nodeSocket.close();
            
            
	        
            while (!exit) {
            	
            	
            	serverNodeSocket = new ServerSocket(port);
                serverNodeSocket.setReuseAddress(true);
            	
            	System.out.println("Em espera de pedidos...");
            	
            	nodeSocket = serverNodeSocket.accept();
            	System.out.println("Ligação estabelecida com cliente.\n");
            	out = new ObjectOutputStream(nodeSocket.getOutputStream());
    	        in = new ObjectInputStream(nodeSocket.getInputStream());
            	
            	response = in.readObject();
            	
            	
            	    /*
            	      	if(response instanceof NodeTable) {	
            	    	
            	    	newNodeTable = ((NodeTable) response).getNodeTable();
            	    	
            	    	nodeTable.setNodeTable(newNodeTable); 
            	    	
            	    	System.out.println("NodeTable Atualizado");
            	    	
            	    	
            	    } else if (response == "hashTableUpdate") {
            	    	
            	    	response = in.readObject();
            	    	
            	    	
            	    	
            	    } else {
            	    
            	    	//response = in.readObject();
            	    */
            	    	 
            	    	
            	    	
	            	     //if (response == "clientRequest") {
	            	    	//response = in.readObject();
	            	    	            	    	
	            	    	
            	
            				String command = "";
            				String key = "";
            				String value = "";
            				
            				//REGEX SPLITER
            				words = ((String) response).split("\\s");
	                        
	                        wordsCounter = 1;
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
	                        
	                        
	                        identifier = hashTable.keyToIdentifier(key);
	                        
	                        ip = nodeTable.searchIp(identifier);
	
	                        search = hashTable.searchKey(key);
	                         
	                        //Register - R
	                        if(command.equals(register) ) {
	                        	
	                        	
	                        	search = hashTable.searchKey(key);
	                        	if (search == false) {
	                        	response = hashTable.addToHashTable(key, value);
	                        	out.writeObject(response);
	                        	System.out.println(response);
	                        	
	                        	} else {
	                        		
	                        		System.out.println("Registo repetido. Não efectuado.");
	                        		out.writeObject("Chave existente.");
	                        	}
	                        	
	                        	
	                        //Query - C
	                        } else if(command.equals(query)) {
	                        	
	                        	value = hashTable.queryValue(key);
	                        	
	                    		out.writeObject(value);
	                    		
	                    		System.out.println(value);
	                        
	                        	
	                        //Delete - D
	                        } else if(command.equals(delete)) {
	                        	
	                        	response = hashTable.removeKey(key);
	                        	out.writeObject(response);
	                        	
	                        	System.out.println(response);
	                        	
	                        //QueryAll - L
	                        } else if(command.equals(queryAll)) {
	                        	
	                        	response = "Não implementado.";
	                        	
	                        	out.writeObject(response);
	                        	System.out.println(response);
	                        	
	                        }  
	            	     //} 
            	    //}
            	    
            	    out.close();
            	    in.close();
            	    nodeSocket.close();
            	    serverNodeSocket.close();
            	}
        
		} catch (IOException  error) {
            error.printStackTrace();
            
        } catch (ClassNotFoundException error) {
        	error.printStackTrace();
        }        
		sc.close();
	}
}
