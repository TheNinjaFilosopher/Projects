//TODO: Complete java docs and code in missing spots.
//apply code style without changing the class/methods/variables naming
import java.util.*;
/**
 * The class that represents the IoTGraph data structure.
 * @author Logan France
 *
 */
public class IoTGraph {
	/**
	 * An ArrayList of AdjacencyListHeads that contains the nodes of the graph.
	 */
    private ArrayList<AdjacencyListHead> nodesList;
    
    /**
     * The constructor for the IoTGraph that accepts a nodesList of nodes.
     * @param nodesList
     */
    public IoTGraph(ArrayList<AdjacencyListHead> nodesList){
        this.nodesList=nodesList;
        //This method is complete
    }
    
    /**
     * Returns the nodesList.
     * @return The nodesList.
     */
    public ArrayList<AdjacencyListHead> getNodesList(){
        return nodesList;
        //This method is complete
    }
    
    /**
     * Returns the number of nodes in the Graph.
     * @return the number of nodes in the Graph.
     */
    public int getNumNodes(){
        //Implement this method
        //Should return the number of nodes in the Graph
        return nodesList.size();
    }
    
    /**
     * Returns the number of edges in the graph.
     * @return The number of edges in the graph.
     */
    public int getNumLinks(){
        //Implement this method
       //returns the number of edges in the graph. Remember this is an undirected graph
    	int size = 0;
    	for(AdjacencyListHead alh : nodesList) {
    		size+=alh.getAdjacencyList().size();
    	}
        return size/2;
    }
    
    /**
     * Adds a new node to the graph. The node is represented by the IoTSensorNode class having id, name, x_coordinate, and y_coordinate instance variables.
     * @param id The id of the node.
     * @param name The name of the node.
     * @param x_coordinate The x coordinate of the node.
     * @param y_coordinate The y coordinate of the node.
     */
    public void addIoTSensorNode(int id,String name,double x_coordinate,double y_coordinate)
    {
        //Implement this method
        //Adds a new node to the graph. The node is represented by the IoTSensorNode class having id, name, x_coordinate, and y_coordinatte instance variables
        //You should check if the nodes already exists in the graph. If this is the case throw an IllegalArgumentException
    	IoTSensorNode node = new IoTSensorNode(id, name, x_coordinate, y_coordinate);
    	
    	if(containsNode(node)) {
    		throw new IllegalArgumentException();
    	}
    	nodesList.add(new AdjacencyListHead(node));
	}
    
    /**
     * Adds a link in the graph between two sensor nodes node1 and node2.
     * @param node1 The first node.
     * @param node2 The second node.
     * @param weight The weight of the edge.
     */
    public void addLink(IoTSensorNode node1, IoTSensorNode node2, double weight)
    {
        //Implement this method
        //adds a link in the graph between two sensor nodes node1 and node2.
        //You should check if the nodes exist in the graph and that they are not null or else you should raise an IllegalArgumentException
    	if(node1==null||node2==null||!containsNode(node1)||!containsNode(node2)||node1==node2) {
    		throw new IllegalArgumentException();
    	}
    	int node1Pos = getNodeIndex(node1);
    	int node2Pos = getNodeIndex(node2);
    	
    	Adjacent adj1 = new Adjacent(node2, weight);
    	Adjacent adj2 = new Adjacent(node1, weight);
    	
    	if(containsAdj(adj1, node1Pos)||containsAdj(adj2, node2Pos)) {
    		return;
    	}
    	nodesList.get(node1Pos).getAdjacencyList().add(adj1);
    	nodesList.get(node2Pos).getAdjacencyList().add(adj2);
    }
    
    /**
     * Deletes a particular sensor node from the IoTGraph.
     * @param node The node being deleted.
     */
    public void deleteIoTSensorNode(IoTSensorNode node){
        //Implement this method
        //deletes a particular sensor node from the IoTGraph. Remember to delete all edges containing it from the different adjacency lists
        //You should check if node exists in the graph and that it is not null or else you should raise an IllegalArgumentException
    	if(node==null||!containsNode(node)) {
    		throw new IllegalArgumentException();
    	}
    	
    	//Get the position of the node for deletion
    	int nodePos = getNodeIndex(node);
    	
    	//Method using removeLink
    	
    	LinkedList<Adjacent> adjList = getAdjacents(node);
    	
    	for(int i = adjList.size()-1;i>=0;i--) {
    		removeLink(node, adjList.get(i).getNeighbor());
    	}
    	
    	//delete the node at nodePos
    	nodesList.remove(nodePos);
    }
    
    /**
     * Deletes a link between two sensor nodes in the IoTGraph. 
     * @param node1 The first node.
     * @param node2 The second node.
     */
    public void removeLink(IoTSensorNode node1, IoTSensorNode node2)
	{   
            //Implement this method
            //deletes a link between two sensor nodes in the IoTGraph. 
            //You should check if the nodes exist in the graph and that they are not null or else you should raise an IllegalArgumentException
    	if(node1==null || node2==null || !containsNode(node1) || !containsNode(node2)) {
    		throw new IllegalArgumentException();
    	}
    	
    	LinkedList<Adjacent> al = getAdjacents(node1);
    	for(int i = 0;i<al.size();i++) {
    		if(al.get(i).getNeighbor()==node2) {
    			al.remove(i);
    			break;
    		}
    	}
    	
    	al = getAdjacents(node2);
    	for(int i = 0;i<al.size();i++) {
    		if(al.get(i).getNeighbor()==node1) {
    			al.remove(i);
    			break;
    		}
    	}
    }
    
    /**
     * Returns a LinkedList containing the Adjacent Objects representing the neighbors of a particular node and the weights of the link.
     * @param node The node whose Adjacents are being returned.
     * @return A LinkedList containing the Adjacent Objects representing the neighbors of a particular node and the weights of the link.
     */
    public LinkedList<Adjacent> getAdjacents(IoTSensorNode node){
          //Implement this method
          //returns a LinkedList containing the Adjacent Objects representing the neighbors of a particular node and the weights of the link
    	if(node==null||!containsNode(node))
    		throw new IllegalArgumentException();
    	int nodePos = getNodeIndex(node);
    	return nodesList.get(nodePos).getAdjacencyList();      
    }
    
    /**
     * Returns the index in the nodesList ArrayList of a particular node.
     * @param node The node whose index is being returned.
     * @return The index in the nodesList ArrayList of a particular node.
     */
    int getNodeIndex(IoTSensorNode node){ 
       //Implement this method
       //returns the index in the nodesList ArrayList of a particular node.
       //You should check if node exists in the graph and that it is not null or else you should raise an IllegalArgumentException
    	if(node==null||!containsNode(node))
    		throw new IllegalArgumentException();
    	int index = 0;
    	for(int i = 0;i<nodesList.size();i++) {
    		if(nodesList.get(i).getIoTSensorNode().equals(node)) {
    			index = i;
    		}
    	}
    	return index;
    }
    
    /**
     * Returns the number of adjacent nodes of a particular sensor node.
     * @param node The node who's degree is being checked.
     * @return The number of adjacent nodes of a particular sensor node.
     */
    public int degree(IoTSensorNode node){ 
       //Implement this method
       //returns the number of adjacent nodes of a particular sensor node
       ////You should check if node exists in the graph and that it is not null or else you should raise an IllegalArgumentException
    	if(node==null||!containsNode(node)) {
    		throw new IllegalArgumentException();
    	}
    	return getAdjacents(node).size(); 
    }
    
    /**
     * Returns the maximum number of adjacent nodes connected to a particular node.
     * @return The maximum number of adjacent nodes connected to a particular node.
     */
    public int getGraphMaxDegree()
	{
	    //Implement this method
        //returns the maximum number of adjacent nodes connected to a particular node
    	int max = 0;
    	for(AdjacencyListHead alh : nodesList) {
    		max = Math.max(degree(alh.getIoTSensorNode()), max);
    	}
        return max;
	}
    
    /**
     * Returns an IoTSensorNode object from the index of the node in nodesList ArrayList.
     * @param index The index of the node.
     * @return An IoTSensorNode object from the index of the node in nodesList ArrayList.
     */
    public IoTSensorNode nodeFromIndex(int index){
        //Implement this method
        //returns an IoTSensorNode object from the index of the node in nodesList ArrayList
    	if(index<0||index>=nodesList.size()) {
    		throw new IllegalArgumentException();
    	}
        return nodesList.get(index).getIoTSensorNode();
    }
    
    /**
     * Returns a String representation of the IoT graph in the adjacency list format.
     * @return A String representation of the IoT graph in the adjacency list format.
     */
    public String printGraph(){
        //Implement this method
        //returns a String representation of the IoT graph in the adjacency list format: e.g.
        /*
            A: {(B,3), (D,2), (E,0.5)}
            B: {(A,3),(E,2)}
            C:{}
            D:{(A,2)}
            E:{(A,0.5),(B,2)}
        */
    	String ans = "";
    	for(int i = 0;i<nodesList.size();i++) {
    		ans+=nodesList.get(i).getIoTSensorNode().getName()+": {";
    		for(int j = 0;j<nodesList.get(i).getAdjacencyList().size();j++) {
    			
    			String neighborName = nodesList.get(i).getAdjacencyList().get(j).getNeighbor().getName();
    			double weight = nodesList.get(i).getAdjacencyList().get(j).getWeight();
    			
    			ans+="("+neighborName+","+weight+")";
    			if(j!=nodesList.get(i).getAdjacencyList().size()-1) {
    				ans+=",";
    			}
    		}
    		ans+="}\n";
    	}
        return ans;
    }
    
    /**
     * Checks to see if the nodesList contains the given node.
     * @param node The given node.
     * @return True if the given node is in nodesList, False if not.
     */
    private boolean containsNode(IoTSensorNode node) {
    	if(node==null) {
    		throw new IllegalArgumentException();
    	}
    	IoTSensorNode current;
    	for(int i = 0;i<nodesList.size();i++) {
    		current = nodesList.get(i).getIoTSensorNode();
    		
    		if(current.getId()==node.getId()) 
    			if(current.getName().equals(node.getName())) 
    				if(current.getX_coordinate()==node.getX_coordinate()) 
    					if(current.getY_coordinate()==node.getY_coordinate()) 
    						return true;
    	}
    	return false;
    }
    
    /**
     * Checks if the given Adjacent is contained within the Adjacent list of the node at nodePos.
     * @param adj The given Adjacent.
     * @param nodePos The position of the node whose list is being checked.
     * @return True if the given Adjacent is in the Adjacent List, False if not.
     */
    private boolean containsAdj(Adjacent adj, int nodePos) {
    	if(adj==null)
    		return false;
    	LinkedList<Adjacent> adjList = getAdjacents(nodeFromIndex(nodePos));
    	
    	for(int i = 0;i<adjList.size();i++) {
    		if(adjList.get(i).getNeighbor()==adj.getNeighbor()) {
    			if(adjList.get(i).getWeight()==adj.getWeight()) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    public static void main(String[] args) {
    	/*
    	 * Methods to Test:
    	 * getNumNodes() - Works as intended.
    	 * getNumLinks() - Works as intended.
    	 * addIoTSensorNode() - Works as intended.
    	 * addLink() - Works as intended.
    	 * deleteIoTSensorNode() - Works as intended.
    	 * removeLink() - Works as intended.
    	 * getAdjacents() - Works as intended.
    	 * getNodeIndex() - Works as intended.
    	 * degree() - Works as intended.
    	 * getGraphMaxDegree() - Works as intended.
    	 * nodeFromIndex() - Works as intended.
    	 * printGraph() - Works as intended.
    	 * */
    	
    	IoTGraph iot = new IoTGraph(new ArrayList<AdjacencyListHead>());
    	//Add the nodes
    	iot.addIoTSensorNode(0, "A", 0, 0);
    	//Checking if it adds duplicates (it shouldn't)
    	//iot.addIoTSensorNode(0, "A", 0, 0);
    	iot.addIoTSensorNode(1, "B", 1, 1);
    	iot.addIoTSensorNode(2, "C", 2, 2);
    	iot.addIoTSensorNode(3, "D", 3, 3);
    	iot.addIoTSensorNode(4, "E", 4, 4);
    	
    	//Print the Graph
    	System.out.println(iot.printGraph());
    	System.out.println("---------------------------------");
    	
    	//Add the Links
    	
    	//A and B, Weight: 3
    	IoTSensorNode node1 = iot.nodeFromIndex(0);
    	IoTSensorNode node2 = iot.nodeFromIndex(1);
    	iot.addLink(node1, node2, 3);
    	//Checking if it adds duplicates (it shouldn't)
    	iot.addLink(node1, node2, 3);
    	
    	//A and D, Weight: 2
    	node2 = iot.nodeFromIndex(3);
    	iot.addLink(node1, node2, 2);
    	
    	//A and E, Weight: 0.5
    	node2 = iot.nodeFromIndex(4);
    	iot.addLink(node1, node2, 0.5);
    	
    	//B and E, Weight: 2
    	node1 = iot.nodeFromIndex(1);
    	iot.addLink(node1, node2, 2);
    	
    	//Print the Graph
    	System.out.println(iot.printGraph());
    	
    	boolean check = iot.containsNode(node1);
    	System.out.println(check);
    	
    	check = iot.containsNode(node2);
    	System.out.println(check);
    	
    	
    	/*//Delete A
    	System.out.println("Delete A");
    	node1 = iot.nodeFromIndex(0);
    	iot.deleteIoTSensorNode(node1);
    	
    	//Print the Graph
    	System.out.println(iot.printGraph());
    	
    	node1 = iot.nodeFromIndex(0);
    	node2 = iot.nodeFromIndex(3);
    	
    	System.out.println("remove link between B and E");
    	//Test removing the link between B and E
    	iot.removeLink(node1, node2);
    	
    	//Print the Graph
    	System.out.println(iot.printGraph());*/
    	
    	//Test for proper degree
    	for(int i = 0;i<iot.getNumNodes();i++) {
        	node1 = iot.nodeFromIndex(i);
        	int deg = iot.degree(node1);
        	System.out.println(deg);
    	}
    	//Test getGraphMaxDegree
    	System.out.println(iot.getGraphMaxDegree());
    	
    	//Test getNumLinks()
    	System.out.println(iot.getNumLinks());
    }
}
