import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

import org.w3c.dom.NodeList;

public class Chord {
	
	private static TreeMap<Integer, Node> nodeList = new TreeMap<Integer, Node>();
	private static ArrayList<String> visitedNodeList = new ArrayList<>();
	private static int nodeSize;
	private static int chordRingSize;
	private static String routPath;
	private static Node sucessor;
	private static boolean check;
	
	public static void InitChord(int n)
	{	
		if(n<1 || n>31)
		{
			return;
		}
				
		nodeSize = n;
		chordRingSize = (int) (Math.pow(2, (double) nodeSize));
		
		Node indexNode = new Node(0);
		Hashtable<Integer, Integer> fingerTable = new Hashtable<Integer, Integer>();
		for(int i=1; i<=n; i++)
		{
			fingerTable.put(i, 0);
		}
		indexNode.setFingerTable(fingerTable);
		//Node indexNode1 = new Node(7);
		//Node indexNode2 = new Node(3);
		nodeList.put(0, indexNode);
		System.out.println("PHYO THANT KO");
		System.out.println("5363901");
		//nodeList.put(7, indexNode1);
		//nodeList.put(3, indexNode2);
		
		//closestSucessor(0);
		
	}
	
	public static void addPeer(int ID)
	{
		try
		{
			if(chordRingSize == 0)
			{
				return;
			}
			Hashtable<Integer, Integer> fingerTable = new Hashtable<Integer, Integer>();
			Node indexNode = new Node(ID);
			
			fingerTable = buildFingerTable(ID);
			indexNode.setFingerTable(fingerTable);
			nodeList.put(ID, indexNode);
			//System.out.println(routPath);
			System.out.println("PEER " + ID + " ADDED");
			
			Node precessor = getPrecessor(indexNode);
			for(int key : nodeList.keySet())
			{
				Node node = nodeList.get(key);

				if(key != ID)
				{		
					updateFingerTableAfterAdd(node, ID, precessor.getId());
					
				}
			}
		}
		catch(Exception e)
		{
			
		}
		
	}
	
	public static void removePeer(int ID)
	{
		try
		{
			if(chordRingSize == 0)
			{
				return;
			}
			Node precessor = getPrecessor(nodeList.get(ID));
			Node successor = getSuccessor(nodeList.get(ID));
			int firstkey = nodeList.firstKey();
			int lastkey = nodeList.lastKey();
			Map<Integer, ArrayList<String>> dataItems = new HashMap<Integer, ArrayList<String>>();
			ArrayList<String> array = new ArrayList<>();
			Node removeNode = nodeList.get(ID);
			dataItems = removeNode.getDataItems();
			
			visitedNodeList = new ArrayList<>();
			check = false;
			findKey(ID);
			check = true;
			for(int i=0; i<visitedNodeList.size(); i++)
			{
				System.out.print(visitedNodeList.get(i));
				
			}
			System.out.println("");
			
			nodeList.remove(ID);	
			System.out.println("PEER " + ID + " REMOVED");
			for(int key : nodeList.keySet())
			{
				Node node = nodeList.get(key);
				
				if(ID == firstkey)
				{
					updateFingerTableAfterRemoveFirstNode(node, ID, successor.getId(), precessor.getId(), dataItems);
				}
				else if(ID == lastkey)
				{
					updateFingerTableAfterRemoveLastNode(node, ID, successor.getId(), precessor.getId(), dataItems);
				}
				else
				{
					updateFingerTableAfterRemove(node, ID, successor.getId(), precessor.getId(), dataItems);	
				}
			}
			
			for(int dataItemKey : dataItems.keySet())
			{
				array = dataItems.get(dataItemKey);
				successor.getDataItems().put(dataItemKey, array);
				
			}
		}
		catch(Exception e)
		{
			
		}
			
	}
	
	
	public static Hashtable<Integer, Integer> buildFingerTable(int ID)
	{
		Hashtable<Integer, Integer> fingerTable = new Hashtable<Integer, Integer>();
		
		try
		{
			int key = 0;
			int nodeID = 0;
			check = false;
			visitedNodeList = new ArrayList<>();
			
			for(int i=1; i<=nodeSize; i++)
			{
				key = ID + fingerHashCode(i);
				if(key > chordRingSize - 1)
				{
					key = (key + 1) - (chordRingSize - 1);
				}
				nodeID = findKey(key);
				check = true;
				fingerTable.put(i, nodeID);
			}
			for(int i=0; i<visitedNodeList.size(); i++)
			{
				System.out.print(visitedNodeList.get(i));		
			}
			System.out.println("");
			return fingerTable;
		}
		catch(Exception e)
		{
			return fingerTable;
		}		
	}
	
	// find key to be modified
	public static int findKey(int key)
	{
		try
		{
			ArrayList<Integer> minarr = new ArrayList<>();
			ArrayList<Integer> maxarr = new ArrayList<>();
			int k = 0;
			int successorNode = 0;
			Node firstNode = nodeList.get(nodeList.firstKey());
			Node lastNode = nodeList.get(nodeList.lastKey());
			
			if(check == false)
			{
				visitedNodeList.add(Integer.toString(firstNode.getId()));
			}
			
			for(int fingerID : firstNode.getFingerTable().keySet())
			{
				int successorMin = firstNode.getFingerTable().get(fingerID);
				if(successorMin < key)
				{
					if(successorMin != firstNode.getId())
					{
						minarr.add(successorMin);
					}			
				}		
			}
			if(minarr.size() == 0)
			{
				for(int fingerID : firstNode.getFingerTable().keySet())
				{
					int successorMax = firstNode.getFingerTable().get(fingerID);
					if(successorMax == key)
					{
						if(check == false)
						{
							visitedNodeList.add(">");
							visitedNodeList.add(Integer.toString(successorMax));
						}
						return successorMax;
					}
					else if(successorMax > key)
					{
						maxarr.add(successorMax);
					}
				}
				
			}
			else if(minarr.size() == 1)
			{
				successorNode = minarr.get(0);
			}
			else if(minarr.size() >= 2)
			{
				successorNode = getClosest(key, minarr);
			}
			
			if(maxarr.size() == 1)
			{
				if(check == false)
				{
					visitedNodeList.add(">");
					visitedNodeList.add(Integer.toString(minarr.get(0)));
				}
				return minarr.get(0);
				
			}
			else if(maxarr.size() >= 2)
			{
				successorNode = getClosest(key, maxarr);
			}
			
			while(k < nodeList.size())
			{
				ArrayList<Integer> minarray = new ArrayList<>();
				ArrayList<Integer> maxarray = new ArrayList<>();
				Node routedNode = nodeList.get(successorNode);
				
				if(check == false)
				{
					visitedNodeList.add(">");
					visitedNodeList.add(Integer.toString(routedNode.getId()));
				}
				
				 if(key <= routedNode.getId())
				 {
					
					return routedNode.getId();
				 }
				
				 if(lastNode == routedNode)
				 {
					 if(key > lastNode.getId())
					 {
						 if(check == false && routedNode != firstNode)
							{
							 	visitedNodeList.add(">");
								visitedNodeList.add(Integer.toString(firstNode.getId()));
							}
							return firstNode.getId(); 					 
					 }										
				 }
							 		
				for(int fingerID : routedNode.getFingerTable().keySet())
				{
					int successormi = routedNode.getFingerTable().get(fingerID);			
					
					
					///
					
					if(successormi < key)
					{
						if(successormi != routedNode.getId())
						{
							if(successormi > routedNode.getId())
							{
								minarray.add(successormi);
							}
										
						}			
					}				
				}
				if(minarray.size() == 0)
				{
					for(int fingerID : routedNode.getFingerTable().keySet())
					{
						int successorma = routedNode.getFingerTable().get(fingerID);
						if(successorma == key)
						{
							return successorma;
						}
						else if(successorma > key)
						{
							maxarray.add(successorma);
						}
					}
					
				}
				else if(minarray.size() == 1)
				{
					successorNode = minarray.get(0);
				}
				else if(minarray.size() >= 2)
				{
					successorNode = getClosest(key, minarray);
				}
				
				if(maxarray.size() == 1)
				{
					return maxarray.get(0);
					
				}
				else if(maxarray.size() >= 2)
				{
					successorNode = getClosest(key, maxarray);
				}	
				k++;
			}
			return 0;
		}
		catch(Exception e)
		{
			return 0;
		}	
	
	}
	
	
	public static void updateFingerTableAfterRemove(Node node, int removeID, int successorID, int precessorID, Map<Integer, ArrayList<String>> dataItems)
	{
		try
		{
			int key= 0;
			
			for(int i=1; i<=nodeSize; i++)
			{
				key = node.getId() + fingerHashCode(i);
				if(key > chordRingSize - 1)
				{
					key = (key + 1) - (chordRingSize - 1);
				}
				if(key <= removeID && key > precessorID)
				{
					node.getFingerTable().put(i, successorID);
					//System.out.println("Changed " + node.getId() + " node at " + i + " place: " + successorID);
				}		
			}	
		}
		catch(Exception e)
		{
			
		}			
	}
	
	
	public static void updateFingerTableAfterRemoveFirstNode(Node node, int removeID, int successorID, int precessorID, Map<Integer, ArrayList<String>> dataItems)
	{
		try
		{
			int key= 0;
			
			for(int i=1; i<=nodeSize; i++)
			{
				key = node.getId() + fingerHashCode(i);
				if(key > chordRingSize - 1)
				{
					key = (key + 1) - (chordRingSize - 1);
				}
				if(key > precessorID || key <= successorID)
				{
					node.getFingerTable().put(i, successorID);
					//System.out.println("Changed " + node.getId() + " node at " + i + " place: " + successorID);
				}		
			}	
		}
		catch(Exception e)
		{
			
		}	
		
	}
	
	public static void updateFingerTableAfterRemoveLastNode(Node node, int removeID, int successorID, int precessorID, Map<Integer, ArrayList<String>> dataItems)
	{
		try
		{
			int key= 0;
			
			for(int i=1; i<=nodeSize; i++)
			{
				key = node.getId() + fingerHashCode(i);
				if(key > chordRingSize - 1)
				{
					key = (key + 1) - (chordRingSize - 1);
				}
				if(key > precessorID)
				{
					node.getFingerTable().put(i, successorID);
					//System.out.println("Changed " + node.getId() + " node at " + i + " place: " + successorID);
				}	
			}	
		}
		catch(Exception e)
		{
			
		}	
		
	}
	
	public static void updateFingerTableAfterAdd(Node node, int newNodeID, int beforeNewNodeID)
	{
		try
		{
			int key= 0;
			
			for(int i=1; i<=nodeSize; i++)
			{
				key = node.getId() + fingerHashCode(i);
				if(key > chordRingSize - 1)
				{
					key = (key + 1) - (chordRingSize - 1);
				}
				if(key <= newNodeID && key > beforeNewNodeID)
				{
					node.getFingerTable().put(i, newNodeID);
					//System.out.println("Changed " + node.getId() + " node at " + i + " place: " + newNodeID);
				}			
			}	
		}
		catch(Exception e)
		{
			
		}	
		
	}
	
	public static void Insert(String text)
	{
		try
		{
			if(chordRingSize == 0)
			{
				return;
			}
			Map<Integer, ArrayList<String>> dataItems = new HashMap<Integer, ArrayList<String>>();
			ArrayList<String> arr = new ArrayList<String>();
			visitedNodeList = new ArrayList<>();
			
			int hashKey = Hash(text);		
			check = false;
			int successor = findKey(hashKey);
			check = true;
			for(int i=0; i<visitedNodeList.size(); i++)
			{
				System.out.print(visitedNodeList.get(i));		
			}
			System.out.println("");
			Node node = nodeList.get(successor);
			if(node.getDataItems().containsKey(hashKey))
			{
				arr = node.getDataItems().get(hashKey);
				arr.add(text);
			}
			else
			{
				arr.add(text);
			}
			dataItems.put(hashKey, arr);
			node.setDataItems(dataItems);
			System.out.println("INSERTED " + text + " (key=" + hashKey + ") AT " + successor);
		}
		catch(Exception e)
		{
			
		}	
	}
	
	public static void Delete(String text)
	{
		try
		{
			if(chordRingSize == 0)
			{
				return;
			}
			Map<Integer, ArrayList<String>> dataItems = new HashMap<Integer, ArrayList<String>>();
			ArrayList<String> arr = new ArrayList<String>();
			visitedNodeList = new ArrayList<>();
			
			int hashKey = Hash(text);		
			check = false;
			int successor = findKey(hashKey);
			check = true;
			for(int i=0; i<visitedNodeList.size(); i++)
			{
				System.out.print(visitedNodeList.get(i));		
			}
			System.out.println("");
			Node node = nodeList.get(successor);
			if(node.getDataItems().containsKey(hashKey))
			{
				arr = node.getDataItems().get(hashKey);
				arr.clear();
			}
			else
			{
				System.out.println("THERE IS NO DATA ITEM: " + text + " (key=" + hashKey + ") AT " + successor);
			}
			dataItems.put(hashKey, arr);
			node.setDataItems(dataItems);
			System.out.println("REMOVED " + text + " (key=" + hashKey + ") FROM " + successor);
		}
		catch(Exception e)
		{
			
		}	
	}
	
	public static void Print(int key)
	{
		try
		{
			if(chordRingSize == 0)
			{
				return;
			}
			visitedNodeList = new ArrayList<>();
			check = false;			
			int successor = findKey(key);
			check = true;
			for(int i=0; i<visitedNodeList.size(); i++)
			{
				System.out.print(visitedNodeList.get(i));		
			}
			System.out.println("");
			Node node = nodeList.get(successor);
			System.out.println("DATA AT INDEX NODE " + successor +":");
			for(int dataItemID : node.getDataItems().keySet())
			{
				ArrayList<String> arr = node.getDataItems().get(dataItemID);
				for(String dataItem : arr)
				System.out.println(dataItem);
			}
			
			System.out.println("FINGER TABLE OF NODE " + successor + ":");
			ArrayList<Integer> array = new ArrayList<>(node.getFingerTable().keySet());
			Collections.reverse(array);
			for(int ID : array)
			{
				int succesorNode = node.getFingerTable().get(ID);
				System.out.print(succesorNode + " ");
			}
			System.out.println("");
		}
		catch(Exception e)
		{
			
		}		
	}
	
	public static int Hash(String text)
	{
		int key = 0;
		try
		{
			
			for(char character : text.toCharArray())
			{
				key = (((key << 5) + key) ^ character) & 0xff;
			}
			
			key = key % chordRingSize;
			
			return key;
		}
		catch(Exception e)
		{
			return key;
		}		
	}
	
	
	public static int fingerHashCode(int i)
	{
		try
		{
			int power = i - 1;
			i = (int) (Math.pow(2, (double) power));
			return i;
		}
		catch(Exception e)
		{
			return i;
		}	
		
	}
	
	public static int getClosest(int key, ArrayList<Integer> array)
	{
		int actualValue = 0;
		try
		{
			int minDiff = Integer.MAX_VALUE;
			
			for(int i=0; i<array.size(); i++)
			{
				int diff = Math.abs(key - array.get(i));
				if(diff < minDiff)
				{
					minDiff = diff;
					actualValue = array.get(i);
				}
			}
			return actualValue;
		}
		catch(Exception e)
		{
			return actualValue;
		}	
		
	}
	
	public static Node getPrecessor(Node currentNode)
	{
		Node precessor = new Node();
		try
		{
			if(currentNode.getId() == nodeList.firstKey())
			{
				return nodeList.get(nodeList.lastKey());
			}
			//Collections.sort(nodeList);
			for(int key : nodeList.keySet())
			{
				if(key < currentNode.getId())
				{
					precessor = nodeList.get(key);
				}			
			}
			return precessor;
		}
		catch(Exception e)
		{
			return precessor;
		}	
		
	}
	
	public static Node getSuccessor(Node currentNode)
	{
		Node successor = new Node();
		try
		{
			if(currentNode.getId() == nodeList.lastKey())
			{
				return nodeList.get(nodeList.firstKey());
			}
			for(int key : nodeList.keySet())
			{
				if(key > currentNode.getId())
				{
					successor = nodeList.get(key);
					break;
				}		
			}
			return successor;
		}
		catch(Exception e)
		{
			return successor;
		}	
		
	}
}
