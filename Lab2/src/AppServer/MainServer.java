package AppServer;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MainServer {

	static ServerSocket serverSocket;
	private static boolean exit = false;
	
	public MainServer(ServerSocket serverSocket) {
		
	}
	
	static String serverIp, hostName;
	static int port, nodePort = 4444;
	
	public static void main(String[] args) {
		ArrayList<Integer> identifierList = new ArrayList<Integer>(List.of(1,2,3,4,5,6,7,8,9,10));
		
		Socket socket;
		
		NodeTable nodeTable = new NodeTable(serverIp, nodePort, identifierList);
		
		try {
			
			
			final DatagramSocket getIpSocket = new DatagramSocket();
			getIpSocket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			serverIp = getIpSocket.getLocalAddress().getHostAddress();
		 
			getIpSocket.close();
			
			serverSocket = new ServerSocket(4444);
			serverSocket.setReuseAddress(true);
			port = serverSocket.getLocalPort();
			
			nodeTable.addNode(serverIp, port, identifierList);
			
			System.out.println("Servidor iniciado.");
			System.out.println("Endereço de Servidor: "+ serverIp + ":" + port);
			
			while (!exit) {
				
				nodePort++;
				
				System.out.println("Em espera por ligações...");
				socket = serverSocket.accept();
				
				ServerOperations node = new ServerOperations(serverSocket, socket, nodeTable, serverIp, nodePort);
				new Thread(node).start();
				
			}

		} catch (IOException error) {
			error.printStackTrace();
		}
	}

	
	public void quit(Socket clientSocket) {
		try {
			serverSocket.close();
			System.out.println("O cliente " + clientSocket + " enviou a instrução Q - Ligação terminada\n");
			exit = true;
		} catch (IOException error) {
		
			error.printStackTrace();
		}
	}

}
