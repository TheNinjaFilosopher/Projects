//This class is complete. Don't modify it
public class Adjacent {
  private IoTSensorNode neighbor;
    private double weight; 
    public Adjacent(IoTSensorNode neighbor,double weight){
        this.neighbor=neighbor;
        this.weight=weight;
    }
    public void setNeighbor(IoTSensorNode neighbor){
       this.neighbor=neighbor; 
    }
    public void setWeight(double weight){
        this.weight=weight;
    }
     public IoTSensorNode getNeighbor(){
       return neighbor; 
    }
    public double getWeight(){
        return weight;
    }  
} 
