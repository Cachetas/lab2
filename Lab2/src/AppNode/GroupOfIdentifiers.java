package AppNode;

import java.util.ArrayList;


public class GroupOfIdentifiers {
	
	
	private int identifier;
	
	private ArrayList<GroupOfIdentifiers> listGroupOfIdentifiers = new ArrayList<>();


	protected GroupOfIdentifiers() {

	}


	protected ArrayList<GroupOfIdentifiers> getList() {
		return listGroupOfIdentifiers;
	}

	
	protected int getIdentifier() {
		return identifier;
	}


	protected void setIdentifier( int identifier ) {
		
	this.identifier = identifier;
	
	}
	
}
