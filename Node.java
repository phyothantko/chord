// PHYO THANT KO, 5363901
// javac <filename>.java
// Using jdk 7

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Node implements Comparable<Node>{
	
	private Integer id;
	private Hashtable<Integer, Integer> fingerTable = new Hashtable<Integer, Integer>();
	private Map<Integer, ArrayList<String>> dataItems = new HashMap<Integer, ArrayList<String>>();
	
	public Node(int ID, Hashtable<Integer, Integer> FingerTable, Map<Integer, ArrayList<String>> DataItems)
	{
		this.id = ID;
		this.fingerTable = FingerTable;
		this.dataItems = DataItems;
	}
	
	public Node(int ID)
	{
		this.id = ID;
	}
	
	public Node()
	{ }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Hashtable<Integer, Integer> getFingerTable() {
		return fingerTable;
	}

	public void setFingerTable(Hashtable<Integer, Integer> fingerTable) {
		this.fingerTable = fingerTable;
	}

	public Map<Integer, ArrayList<String>> getDataItems() {
		return dataItems;
	}

	public void setDataItems(Map<Integer, ArrayList<String>> dataItems) {
		this.dataItems = dataItems;
	}
	
	public int compareTo(Node node) {
        
        return this.id.compareTo(node.getId());
    }
	

}
