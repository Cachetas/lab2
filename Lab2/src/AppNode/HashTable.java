package AppNode;

//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;


public class HashTable {

	private String key, value;
	String response, ip;
	private int indexKey, port; //identifier;
	private boolean search;

	private HashTable hash;
	
	private ArrayList<HashTable> hashTable = new ArrayList<>();
	NodeTable nodeTable = new NodeTable(ip, port, null);
	ArrayList<NodeTable> addressesTable;
	//private GroupOfIdentifiers groupOfIdentifiers;
	//private ObjectOutputStream out;

	
	Socket hashTableUpdateSocket;
	
	protected HashTable(String key, String value) {
		this.key = key;
		this.value = value;
		
	}
	
	/*protected void hashTableUpdate() {
		
		
		
		//////////////////////////////////
		//								//
		//								//
		// FAZER ISTO COM FOR ITERATOR	//
		//								//
		//								//
		/////////////////////////////////
		
		addressesTable = nodeTable.getNodeTable();
		
		for (NodeTable nodeIn : addressesTable) {
			
			groupOfIdentifiers = nodeIn.getGroupOfIdentifiers();
			ArrayList<GroupOfIdentifiers> list = groupOfIdentifiers.getList();
			
			for(GroupOfIdentifiers identifierInList : list) {
				
				for (HashTable hashInList : hashTable) {
					
					identifier = keyToIdentifier(hashInList.getKey());
					
					if (identifier == identifierInList.getIdentifier()) {
						
						ip = nodeIn.getIp();
						port = nodeIn.getPort();
						
						try {
							hashTableUpdateSocket = new Socket(ip, port);
						
							out = new ObjectOutputStream(hashTableUpdateSocket.getOutputStream());

							out.writeObject("R" + hashInList);

							out.close();
							
							hashTableUpdateSocket.close();

						} catch (IOException error) {
							
							error.printStackTrace();
						}	
					}	
				}	
			}	
		}
	}
	*/
	
	protected boolean searchKey(String key) {
		
		search = false;
		
		if (!hashTable.isEmpty()) {
			for (HashTable keyInList : hashTable) {
				
				if (key.equals(keyInList.getKey())) {
					
					search = true;
						
					}
				}
		}

		return search;
	}

	protected String addToHashTable(String key, String value) {
		
		hash = new HashTable(key, value);
		hashTable.add(hash);
		
		return "Item registado com sucesso.";

	}
	
	
	protected String queryValue(String key) {
		
		search = false;
		value = "Chave inexistente";
		
		if (!hashTable.isEmpty()) {
			for (HashTable keyInList : hashTable) {
				
				if (key.equals(keyInList.getKey())) {
					
					value = keyInList.getValue();
					search = true;
				}
			}
		}
			return value;
	}
	
	protected String removeKey(String keyToRemove) {
		
		HashTable keyValue;
		int hashTableIndex = 0;

		response = "Chave inexistente.";
		
		if (!hashTable.isEmpty()) {
			for (Iterator<HashTable> listKeys = hashTable.iterator(); listKeys.hasNext();) {
				
			keyValue = listKeys.next();
			
			
			if (keyToRemove.equals(keyValue.getKey()) ) {
				indexKey = hashTableIndex;
				response = "Item removido com sucesso.";
				
			}
			hashTableIndex++;
			
			}
				hashTable.remove(indexKey);
					
		}
		return response;
	}
	
	protected int keyToIdentifier(String key) {
		
		long keyToId = 0;
		keyToId = key.hashCode();
		
		while (keyToId >= 11) {
			keyToId = keyToId / 10;
		}
			return (int) keyToId;
	}

		
		
		/*
				long keyToId = 0;
				for(int i = 0; i<key.length(); i++) {
		
				      char symbol = key.charAt(i);
				      keyToId += (long) symbol;
				    }
				
				while (keyToId >= 11) {
					
					keyToId = keyToId / 10;
					
				}
					return (int) keyToId;
			}
		*/
	

		
		
	
	protected String getKey() {
		return key;
	}
	
	protected String getValue() {
		return value;
	}
	
}
