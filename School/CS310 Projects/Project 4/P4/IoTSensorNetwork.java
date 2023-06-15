//TODO: Complete java docs and code in missing spots.
//apply code style without changing the class/methods/variables naming
import java.util.*;
/**
 * The IoTSensorNetwork class consists of the main methods for creating an IoTSensorNetwork and applying some optimization and forwarding protocols on it. 
 * @author Logan France
 *
 */
public class IoTSensorNetwork {
	/**
	 * The network graph that holds the nodes and edges.
	 */
    private IoTGraph networkGraph;
    /**
     * An ArrayList that keeps track of what nodes have already been visited.
     */
    private ArrayList<IoTSensorNode> visited = new ArrayList<IoTSensorNode>(); //should contain the IoTSensorNodes already visited by the Forwarding protocol or else the protocol will run infinitely
    /**
     * Default constructor creates an IoTSensorNetwork consisting of 500 nodes located in an area of 10x10.
     */
    public IoTSensorNetwork(){
	//Default constructor creates an IoTSensorNetwork consisting of 500 nodes located in an area of 10x10
        IoTSensorNetwork sampleNetwork=new IoTSensorNetwork(500,10);
    }
    
    /**
     * Constructor that creates a node with the given number of sensors in a square with a size of the given side.
     * @param num_of_sensors The number of nodes in the network graph.
     * @param side The size of the square.
     */
    public IoTSensorNetwork(int num_of_sensors,double side){
        ArrayList<AdjacencyListHead> nodesList=new ArrayList<AdjacencyListHead>();
        for (int i = 0; i < num_of_sensors; i++)
		{
			double x_coordinate = side*Math.random();  
			double y_coordinate = side*Math.random();  
			int id=i;
            String name="node "+i; 
			IoTSensorNode node=new IoTSensorNode(id,name,x_coordinate,y_coordinate);
            nodesList.add(new AdjacencyListHead(node));
                        
		}
        networkGraph=new IoTGraph(nodesList);
            
        //Iterate over all the sensor node pairs in the graph and connect the nodes with a distance <=1 (wireless range of sensors) with links
        IoTSensorNode node1;
        IoTSensorNode node2;
        for(int i = 0;i<networkGraph.getNumNodes()-1;i++) {
        	node1 = networkGraph.nodeFromIndex(i);
        	for(int j = i+1;j<networkGraph.getNumNodes();j++) {
        		node2 = networkGraph.nodeFromIndex(j);
        		double xDistance=Math.abs(node1.getX_coordinate()-node2.getX_coordinate());
        		double yDistance=Math.abs(node1.getY_coordinate()-node2.getY_coordinate());
        		if(xDistance<=1&&yDistance<=1) {
        			//weight of the euclidean distance
        			networkGraph.addLink(node1, node2, euclideanDistance(node1,node2));
        		}
        	}
        }
    }
    
    /**
     * Returns the Euclidean distance between two nodes node1 and node2.
     * @param node1 The first node.
     * @param node2 The second node.
     * @return The Euclidean distance between two nodes node1 and node2.
     */
    double euclideanDistance(IoTSensorNode node1,IoTSensorNode node2){
	//Implement this method
        //returns the Euclidean distance between two nodes node1 and node2
    	double ed = 0;
    	//Calculate the difference in the x and y coords of the two nodes
    	double xCord = node2.getX_coordinate()-node1.getX_coordinate();
    	double yCord = node2.getY_coordinate()-node1.getY_coordinate();
    	
    	//Square them
    	xCord = Math.pow(xCord, 2);
    	yCord = Math.pow(yCord, 2);
    	
    	//add them together
    	double xAndY = xCord+yCord;
    	
    	//return the square root
    	ed = Math.sqrt(xAndY);
        return ed;
    }
    //current solution could lead to nulls
    //NetworkOptimizer needs to work backwards
    //Need to account for removing a link messing with the size?
    //Removing a link messes with size, need to account for this by breaking when xNeighbors is down a link
    //Have a variable that doesn't change to represent the xNeighbors size, if it changes, end the loop?
    /**
     * Optimizes the network graph by removing unnecessary links.
     */
    public void networkOptimizer()
	{	
    	//O(n*l*l) where n is the number of IoT sensor nodes and l is the number of links
        //Implement this method
		//for each node x in the graph
                        //for each node y adjacent to x
                                //for each node z adjacent to y
                // If |xz|<|xy| and |yz|<|xy|, delete link |xy| from the graph
    	//The neighbors of X
    	LinkedList<Adjacent> xNeighbors;
    	//The neighbors of Y
    	LinkedList<Adjacent> yNeighbors;
    	
    	//The current x node
    	IoTSensorNode xNode;
    	//The current y node
    	IoTSensorNode yNode;
    	//The current z node
    	IoTSensorNode zNode;
    	
    	for(int x = networkGraph.getNumNodes()-1;x>=0;x--) {
    		//Get the X, then put its neighbors into a list
    		xNode = networkGraph.getNodesList().get(x).getIoTSensorNode();
    		//System.out.println("X Node: "+xNode.getName());
    		xNeighbors = networkGraph.getAdjacents(xNode);
    		//xNeighbors = networkGraph.getNodesList().get(x);
    		for(int y = xNeighbors.size()-1;y>=0;y--) {
    			if(xNeighbors.size()==0) {
    				break;
    			}
    			yNode = xNeighbors.get(y).getNeighbor();
    			//System.out.println("\tY Node: "+yNode.getName());
    			if(yNode.equals(xNode))
    				continue;
    			yNeighbors = networkGraph.getAdjacents(yNode);
    			//Get the Y, then put its neighbors into a list

    			for(int z = yNeighbors.size()-1;z>=0;z--) {
    				//Do the checks
    				if(yNeighbors.size()==0) {
    					break;
    				}
    				zNode = yNeighbors.get(z).getNeighbor();
    				//System.out.println("\t\tZ Node: "+zNode.getName());
    				if(zNode.equals(yNode)||zNode.equals(xNode))
    					continue;
    				//If the link between XZ is less than the link between XY and the link between YZ is less than the link between XY, delete XY from the graph
    				//This can be done by comparing the weights of the Adjacents, just need to get them.
    				//Could compare zNode to all the adjacents in X to build another List
    				//Or could search the xNeighbors for the current zNode, then return the weight of that Adjacent
    				/*
    				 * If |XZ| < |XY|
    				 * XZ is every edge where X connects to Z
    				 * XY is every edge where X connects to Y, represented by xNeighbors
    				 * In order to get XZ, check the spots where X has an adjacent to a node in Z?
    				 * 
    				 * if(something.get(z).getWeight()<xNeighbors.get(y).getWeight())
    				 * or
    				 * if(XZ < XY)
    				 * */
    				
    				/*
    				 * If |YZ| < |XY|
    				 * YZ is every edge where Y connects to Z, represented by yNeighbors
    				 * XY is every edge where X connects to Y, represented by xNeighbors
    				 * if(yNeighbors.get(z).getWeight()<xNeighbors.get(y).getWeight())
    				 * or
    				 * if(YZ < XY)
    				 * */
    				
    				double xZ = findAdjacentWeight(zNode, xNeighbors);
    				double xY = xNeighbors.get(y).getWeight();
    				double yZ = yNeighbors.get(z).getWeight();
    				if(xZ < xY && yZ < xY) {
    					//remove |XY| from the graph
    					networkGraph.removeLink(xNode, yNode);
    					break;
    				}
    			}
    		}
    	}
    	
    	
	}
    //Helper method for networkOptimizer
    //Might need to make ans start as a number higher than 1
    //If XZ does not exist in xNeighbors, then it can't be less than XY
    //Which means the optimization equation can never be true.
    /**
     * Returns the weight of the adjacent that contains the given node as a neighbor.
     * @param node The given node.
     * @param adjList The LinkedList of Adjacents being checked.
     * @return The weight of the Adjacent, or 2 if none could be found.
     */
    private double findAdjacentWeight(IoTSensorNode node, LinkedList<Adjacent> adjList) {
    	double ans = 2;
    	for(Adjacent adj : adjList) {
    		if(adj.getNeighbor().equals(node)) {
    			ans = adj.getWeight();
    			break;
    		}
    	}
    	return ans;
    }
    
    /**
     * Returns the networkGraph.
     * @return The networkGraph.
     */
    public IoTGraph getIoTGraph(){
        return networkGraph;
    }
    
    /**
     * Checks if a node is in the visited ArrayList.
     * @param node The node being checked.
     * @param visited The ArrayList being checked.
     * @return True if the node is in visited, False if not.
     */
    protected boolean isVisited(IoTSensorNode node, ArrayList<IoTSensorNode> visited)
	{
    	if(visited.contains(node)){
    		return true;
        }
        else
        {
            return false;
        }
	}
    
    /**
     * Returns an ArrayList of IoTSensorNodes that contains the path from a source node to a destination node.
     * @param sourceNode The source node.
     * @param destNode The destination node.
     * @return An ArrayList of IoTSensorNodes that contains the path from a source node to a destination node.
     */
    public ArrayList<IoTSensorNode> Three10ForwardingProtocol(IoTSensorNode sourceNode,IoTSensorNode destNode){
    	//Implement this method
        ////O(n*l) where n is the number of IoT sensor nodes and l is the number of links
        ArrayList<IoTSensorNode> path=new ArrayList<IoTSensorNode>();
        //Check if sourceNode and destNode exist in the graph and that they are not null. If this is not the case, raise an IllegalArgumentException
        //Check if sourceNode equals destNode. If yes, clear the visited ArrayList and return a 1-element path consisting of the sourceNode.
        //Else:
        //Check if current node on the path doesn't have adjacent neighbors. if yes, clear the visited ArrayList and return an empty path
        //Else if:
        // The current node has only one adjacent node which belongs to the visited ArrayList
        // This indicates that there is no way to move forward from that node since its only neighbor is already visited
        // In this case return an empty path
        // Else:
        //Find the neighbor of the current node with the smallest angle (maximum cosine) with the vector connecting the current node with the destination node
        //mark the current node visited and add the neighbor to the path
        
        //Check if sourceNode and destNode exist in the graph and that they are not null. If this is not the case, raise an IllegalArgumentException
        
        //If getNodeIndex() does not throw an error, then they exist
        networkGraph.getNodeIndex(sourceNode);
        networkGraph.getNodeIndex(destNode);
        
        if(sourceNode.equals(destNode)) {
        	visited.clear();
        	path.add(sourceNode);
        	return path;
        }
        path.add(destNode);
        return forwardingProtocolRecur(destNode, sourceNode, path);
    }
    
    /**
     * Recursive helper method for Three10ForwardingProtocol.
     * In order to have the path in the proper order, the currNode variable is passed in the destNode variable of Three10ForwardingProtocol and the sourceNode variable of Three10ForwardingProtocol is treated
     * As the destination node.
     * @param currNode The current node being checked.
     * @param destNode The destination node.
     * @param path The path of the nodes.
     * @return The path of the nodes.
     */
    private ArrayList<IoTSensorNode> forwardingProtocolRecur(IoTSensorNode currNode,IoTSensorNode destNode, ArrayList<IoTSensorNode> path){
    	//The original method already checks if sourceNode equals destNode, no need to do that here
    	//If the current node has no neighbors, then just end with an empty path as a base case
    	//If the current node has 1 neighbor that's already visited, then just end with an empty path as a base case
    	//Find the neighbor of the current node with the smallest angle (maximum cosine) with the vector connecting the current node with the destination node
        //mark the current node visited and add the neighbor to the path
    	
    	
    	if(currNode.equals(destNode)) {
    		visited.clear();
    		return path;
    	}
    	LinkedList<Adjacent> adjList = networkGraph.getAdjacents(currNode);

    	if(adjList.size()==0) {
    		visited.clear();
    		return new ArrayList<IoTSensorNode>();
    	}
    	else if(adjList.size() == 1 && visited.contains(adjList.get(0).getNeighbor())) {
    		visited.clear();
    		return new ArrayList<IoTSensorNode>();
    	}

        double angle = -2;
        double cos = 0;
        IoTSensorNode neighbor;
        int maxPos = 0;
        
    	for(int i = 0;i<adjList.size();i++) {
    		neighbor = adjList.get(i).getNeighbor();
    		if(visited.contains(neighbor)) {
    			continue;
    		}
    		cos = cos(currNode, destNode, neighbor);
    		if(angle<cos) {
    			maxPos = i;
    			angle = cos;
    		}	
    	}
    	//if angle == -2, then all the nodes were already visited
    	//This means that it is a dead end for the path.
    	if(angle==-2) {
    		visited.clear();
    		return new ArrayList<IoTSensorNode>();
    	}
    	
    	visited.add(currNode);
    	path.add(adjList.get(maxPos).getNeighbor());
    	return forwardingProtocolRecur(adjList.get(maxPos).getNeighbor(),destNode, path);
    }
    
    /**
     * Returns the trigonometric cosine of the angle between nextHop and the vector connecting node to destination
     * @param node The current node.
     * @param destination The destination node.
     * @param nextHop The next potential node to be jumped to.
     * @return The trigonometric cosine of the angle between nextHop and the vector connecting node to destination
     */
	private double cos(IoTSensorNode node, IoTSensorNode destination, IoTSensorNode nextHop)
	{       //method code complete. Don't modify
		//returns the trigonometric cosine of the angle between nextHop and the vector connecting node to destination
        double hyp = euclideanDistance(destination, nextHop);
		double side1 = euclideanDistance(destination, node);
		double side2 = euclideanDistance(node, nextHop);
		return (side1*side1 + side2*side2 -hyp*hyp)/(2*side1*side2);
                
	}
	
	public static void main(String[] args) {
		IoTSensorNetwork iot = new IoTSensorNetwork(500,10);
		IoTSensorNode node1 = iot.getIoTGraph().nodeFromIndex(0);
		IoTSensorNode node2 = iot.getIoTGraph().nodeFromIndex(iot.getIoTGraph().getNumNodes()-1);
		
		//System.out.println(iot.getIoTGraph().printGraph());
		
		ArrayList<IoTSensorNode> path = iot.Three10ForwardingProtocol(node1, node2);
		for(IoTSensorNode iotNode : path) {
			System.out.println(iotNode.getName());
		}
		System.out.println("------------------------------------------------------");
		
		iot.networkOptimizer();
		
		//System.out.println(iot.getIoTGraph().printGraph());
		
		System.out.println("------------------------------------------------------");
		
		path = iot.Three10ForwardingProtocol(node1, node2);
		for(IoTSensorNode iotNode : path) {
			System.out.println(iotNode.getName());
		}
	}
}
