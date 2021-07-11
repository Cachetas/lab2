package AppServer;

import java.util.ArrayList;



public class NodeTable  {
	
		
	private String ip;
	protected int port, actualNodes;
	private boolean search;
	private NodeTable node;

	
	private ArrayList<NodeTable> nodeTable = new ArrayList<>();
	private ServerListIds listIds = new ServerListIds(); 
	ArrayList<Integer> identifierList;
	
	
	protected ArrayList<Integer> getListIds() {
		
		
		return listIds.getListIds();
	}


	protected void setListIds(ArrayList<Integer> listIds) {
		this.listIds.setListIds(listIds); 
	}


	public NodeTable(String ip, int port, ArrayList<Integer> identifierList) {
		this.ip = ip;
		this.port = port;
		this.listIds.setListIds(identifierList);
			
	}

		
	protected ArrayList<NodeTable> getNodeTable() {
		return nodeTable;
	}
	
	
	protected void setNodeTable(ArrayList<NodeTable> newNodeTable) {
		this.nodeTable = newNodeTable;
	}

	protected boolean verifyNodeTable(String ip, int port){
		
		search = false;
		for (NodeTable nodeInList : nodeTable) {
			
			if (ip.equals(nodeInList.getIp()) && port == nodeInList.getPort()) {

				search = true;
			}
		}
		return search;
	}
	

	protected void addNode(String ip, int port, ArrayList<Integer> listIds) {
		
		node = new NodeTable(ip, port, listIds);
		nodeTable.add(node);
	}

	protected ArrayList<NodeTable> redistributeIdentifiers() {
		
		int totalIdentifiers = 0;
		
		int nodeTableIndex = 0;
		
		System.out.println("\nLista de endereços dos Nós: ");
		
		ArrayList<NodeTable> newNodeTable = new ArrayList<>();

			for (NodeTable nodeIdentifiers : nodeTable) {
				int idPerNode = 10 / nodeTable.size();
				identifierList = new ArrayList<Integer>();
				
				while (idPerNode > 0 ) {
					
					idPerNode--;
					if (totalIdentifiers < 10) {

						totalIdentifiers++;

						identifierList.add(totalIdentifiers);
						
						if (nodeTableIndex == nodeTable.size() - 1) {
					
							while (totalIdentifiers < 10) {
								
								totalIdentifiers++;
								identifierList.add(totalIdentifiers);
								

							}
						}	
					}
				}
				
				ip = nodeIdentifiers.getIp();
				port = nodeIdentifiers.getPort();
				
				node = new NodeTable(ip, port, identifierList); 
				
				newNodeTable.add(node);
				
				System.out.println(ip + ":" + port + " -> IDs: " + identifierList);
				
				nodeTableIndex++;

			}
			
			return nodeTable = newNodeTable;
		}
	

	
	protected String searchIp(int identifier) {
		search = false;
		for (NodeTable listNodes : nodeTable) {
			
			listIds.setListIds(listNodes.getListIds());
				ArrayList<Integer> list = listIds.getListIds();
				for (Integer identifierInList : list) {
					
					
					if (identifier == identifierInList) {
						search = true;
					
						ip = listNodes.getIp();	
					}
				
				}
		}
		
		
		return ip;
	}

	
	protected int searchPort(int identifier) {
		search = false;
		for (NodeTable listNodes : nodeTable) {
			
			listIds.setListIds(listNodes.getListIds());
				ArrayList<Integer> list = listIds.getListIds();
				for (Integer identifierInList : list) {
					if (identifier == identifierInList) {
						search = true;
					
						port = listNodes.getPort();
					}
				}
		}
		
		return port;	
	}
	

	protected String getIp() {
		
		return ip;
	}


	protected int getPort() {
		return port;
	}
	
}
