//This class is complete. Don't modify it
import java.util.*;
public class AdjacencyListHead {
    private IoTSensorNode node;
    private LinkedList<Adjacent> adjacencyList;
    
    public AdjacencyListHead(IoTSensorNode node){
        this.node=node;
        this.adjacencyList=new LinkedList<Adjacent>();
    }
    public AdjacencyListHead(IoTSensorNode node,LinkedList<Adjacent> adjacencyList){
        this.node=node;
        this.adjacencyList=adjacencyList;
    }
    public void setIoTSensorNode(IoTSensorNode node){
        this.node=node;
    }
    public void setAdjacencyList(LinkedList<Adjacent> adjacencyList){
        this.adjacencyList=adjacencyList;
    }
    public IoTSensorNode getIoTSensorNode(){
        return node;
    }
    public LinkedList<Adjacent> getAdjacencyList(){
        return adjacencyList;
    }
    
    
}
