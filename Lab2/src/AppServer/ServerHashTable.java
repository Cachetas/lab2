package AppServer;

import java.util.ArrayList;
import java.util.Iterator;


public class ServerHashTable {

	private String key, value;
	String response;
	private int indexKey;
	private boolean search;

	private ServerHashTable hash;
	
	private ArrayList<ServerHashTable> hashTable = new ArrayList<>();
	
	protected ServerHashTable(String key, String value) {
		this.key = key;
		this.value = value;
		
	}
	
	protected boolean searchKey(String key) {
		
		search = false;
		
		if (!hashTable.isEmpty()) {
			for (ServerHashTable keyInList : hashTable) {
				
				if (key.equals(keyInList.getKey())) {
					
					search = true;
						
				}
			}

		}
		return search;
	}

	protected String addToHashTable(String key, String value) {
		
		hash = new ServerHashTable(key, value);
		hashTable.add(hash);
		
		return "Item registado com sucesso.";

	}
	
	
protected String queryValue(String key) {
		
		search = false;
		value = "Chave inexistente";
		
		if (!hashTable.isEmpty()) {
			for (ServerHashTable keyInList : hashTable) {
				
				if (key.equals(keyInList.getKey())) {
					
					value = keyInList.getValue();
					search = true;
				}
			}
		}
			return value;
	}
	
	protected String removeKey(String keyToRemove) {
		
		ServerHashTable keyValue;
		int hashTableIndex = 0;
		
		response = "Chave inexistente.";
		
		if (!hashTable.isEmpty()) {
			for (Iterator<ServerHashTable> listKeys = hashTable.iterator(); listKeys.hasNext();) {
				
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
		//long finalNumber = 1;
		/*
		
		for(int i = 0; i<key.length(); i++) {

		      char symbol = key.charAt(i);
		      int symbol2 = (int)symbol;
		      
		      String numberOfChar = String.valueOf(symbol2);
		      
		     
		      finalNumber = (long) numberOfChar.charAt(0);
		      for (int j = 1; j<numberOfChar.length(); j++) {
		    	  
		    	  //int temp = (int) numberOfChar.charAt(j);
		    	  
		    	  if ((int) numberOfChar.charAt(j) != 0) {
	
		    		  finalNumber = finalNumber * (long) numberOfChar.charAt(j);
		    		  System.out.println("FINAL NUMBER" + finalNumber);
		    	  }
		    	  
		      }
		      
		    }*/

		keyToId = key.hashCode();
		
		//keyToId = String.valueOf(keyToId).hashCode();
		
		//keyToId = keyToId * finalNumber;
	      
		
		while (keyToId >= 11) {
			
			keyToId = keyToId / 10;

		}
			return (int) keyToId;
	}

	
	
	
	protected String getKey() {
		return key;
	}
	
	
	
	protected String getValue() {
		return value;
	}
	

	

}
