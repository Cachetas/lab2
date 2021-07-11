package AppNode;

import java.util.ArrayList;

public class NodeTable {
	

	private String ip;
	protected int port, actualNodes;
	private boolean search;
	private NodeTable node;

	private ArrayList<NodeTable> nodeTable = new ArrayList<>();
	private GroupOfIdentifiers groupOfIdentifiers = new GroupOfIdentifiers();
	
	public NodeTable(String ip, int port, GroupOfIdentifiers groupOfIdentifiers) {
		this.ip = ip;
		this.port = port;
		this.groupOfIdentifiers = groupOfIdentifiers;
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
	

	protected ArrayList<NodeTable> getNodeTable() {
		return nodeTable;
	}
	
	public void setNodeTable(ArrayList<NodeTable> array) {
		this.nodeTable = array;
	}
	

	protected void addNode(String ip, int port, GroupOfIdentifiers groupOfIdentifiers) {
		
		node = new NodeTable(ip, port, groupOfIdentifiers);
		nodeTable.add(node);
	}


	protected void redistributeIdentifiers() {
		
		int totalIdentifiers = 0;
		
		while (totalIdentifiers < 10) {
			
			for (NodeTable nodeIdentifiers : nodeTable) {
				if (totalIdentifiers <= 10) {
				
					groupOfIdentifiers = nodeIdentifiers.getGroupOfIdentifiers();
					ArrayList<GroupOfIdentifiers> list = groupOfIdentifiers.getList();
					for (GroupOfIdentifiers identifierInList : list) {
						
						totalIdentifiers++;
						identifierInList.setIdentifier(totalIdentifiers);
					}
				}
			}
		}
	}

	protected String searchIp(int identifier) {
		search = false;
		for (NodeTable listNodes : nodeTable) {
			
				//groupOfIdentifiers = listNodes.getGroupOfIdentifiers();
				ArrayList<GroupOfIdentifiers> list = groupOfIdentifiers.getList();
				for (GroupOfIdentifiers identifierInList : list) {
					if (identifier == identifierInList.getIdentifier()) {
						search = true;
					}
					
					if (search == true) {
						ip = listNodes.getIp();
					}
				}
		}
		
		if (search == false) {
			ip = null;
		}
		return ip;
	}


	protected String getIp() {		
		return ip;
	}


	protected int getPort() {
		return port;
	}


	protected GroupOfIdentifiers getGroupOfIdentifiers() {
		return groupOfIdentifiers;
	}
	
}
