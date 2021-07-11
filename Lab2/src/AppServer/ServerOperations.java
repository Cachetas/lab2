package AppServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class ServerOperations extends MainServer implements Runnable{

    private ObjectOutputStream out;
    private ObjectInputStream in;
    protected Object nodeOrClient, clientInput;
    private Socket socket;

    protected String command, response, key, hostName, hostAddress, ip, localIp;
    protected String register = "R", query = "C", delete = "D", quit = "Q", queryAll = "L";
    String[] words;

    //private long hashValue;
    protected int nodePort, port, localPort = 4444, identifier, wordsCounter;
    protected boolean nodeExistenceCheck, search, exit = false;


    private ArrayList<Integer> identifierList;
    private ServerHashTable hashTable = new ServerHashTable(key, null);

    private NodeTable nodeTable = new NodeTable(ip, port, identifierList);

    //NodeTable payload;

    public ServerOperations(ServerSocket serverSocket, Socket socket, NodeTable nodeTable, String serverIp, int nodePort)  {
        super(serverSocket);
        this.socket = socket;
        this.nodeTable = nodeTable;
        this.localIp = serverIp;
        this.nodePort = nodePort;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());


        }  catch (IOException error) {
            error.printStackTrace();
        }
    }


    @Override
    public void run() {

        hostAddress =  socket.getInetAddress().getHostAddress();

        try {

            nodeOrClient =  in.readObject();

            //New NODE
            if (((String) nodeOrClient).equals("node")) {


                nodeExistenceCheck = nodeTable.verifyNodeTable(hostAddress, nodePort);

                if (nodeExistenceCheck == false) {


                    nodeTable.addNode(hostAddress, nodePort, identifierList);
                    out.writeObject(nodePort);
                    out.writeObject(hostAddress);

                    System.out.println("Nó resgistado com endereço: " + hostAddress + ":" + nodePort);
                    
                    ArrayList<NodeTable> newNodeTable = nodeTable.redistributeIdentifiers();
                    nodeTable.setNodeTable(newNodeTable);
                } else {
                    System.out.println("Este nó já existe.");
                }


                //CLIENT Request
            } else if (((String) nodeOrClient).equals("client")) {
            	

                System.out.println("Cliente ligado.\n");
                


                while (!exit) {
                	
                	String command = "";
                	String key = "";
                	String value = "";
                	
                    clientInput = in.readObject();


                   
                    //REGEX SPLITER
	                    words = ((String) clientInput).split("\\s");
	
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
	

	                    //Quit - Q
	                    if(command.equals(quit)) {
	                    	
	                   	 	System.out.println("Ligação de cliente terminada.");
	                        socket.close();
	                        exit = true;
	                    } else { 
	                    	
	                    identifier = hashTable.keyToIdentifier(key);
	                   
	 
	                    ip = nodeTable.searchIp(identifier);
	                    port = nodeTable.searchPort(identifier);
	                    
	                    //Register - R
	                    if(ip.equals(localIp) && port == localPort && command.equals(register)) {
	                    	
	                        search = hashTable.searchKey(key);
	                        if (search == false) {
	                            response = hashTable.addToHashTable(key, value);
	                            out.writeObject(response);
	                            System.out.println("\n[Server] " + response + " \n");
	
	                        } else {
	
	                            out.writeObject("Chave existente.");
	                            System.out.println("\n[Server] Registo repetido. Não efectuado. \n");
	                        }
	
	
	                        //Query - C
	                    } else if (ip.equals(localIp) && port == localPort && command.equals(query)) {
	                    	
	                        value = hashTable.queryValue(key);
	                        out.writeObject(value);
	                        System.out.println("\n[Server] " + value + " \n");
	
	
	                        //Delete - D
	                    } else if(ip.equals(localIp) && port == localPort && command.equals(delete)) {

	                        response = hashTable.removeKey(key);
	                        out.writeObject(response);
	                        System.out.println("\n[Server] " + response + " \n");
	
	                        //QueryAll - L
	                    } else if(command.equals(queryAll)) {
	
	                    	response = "Não implementado.";
                        	out.writeObject(response);
                        	System.out.println(response);
	                        
	                    } else {

	                        out.writeObject("toNode");
	                        out.writeObject(ip);
	                        out.writeObject(port);
	                        System.out.println("Cliente redirecionado para: " + ip + ":" + port + " - Nó ID - " + identifier);
	                        
	                    	/*ServerToNode connectNode = new ServerToNode(ip, port, clientInput);
	                    	new Thread(connectNode).start();
	                    	connectNode.join();
	                    	response = connectNode.getResponse();
	                    	*/
	                    }
	                }
	            }
            }
		
        } catch (ClassNotFoundException | IOException error) {

            error.printStackTrace();
        }
    }

}