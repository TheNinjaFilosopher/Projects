//TODO: Complete java docs and code in missing spots.
//apply code style without changing the class/methods/variables naming
import java.util.*;
public class Project4TestCode {

    public static void main(String[] args) {
       //Test the IoTSensorNetwork creation process and get the average and maximum degrees of the IoT sensor networks before and after network optimization
		for (int i = 200; i<=750; i+=10)
		{     
			
                        IoTSensorNetwork testNetwork = new IoTSensorNetwork(i, 10);
			System.out.println("For network with n="+i+": ");
			double avg = ((double)testNetwork.getIoTGraph().getNumLinks())/(testNetwork.getIoTGraph().getNumNodes());
			System.out.println("The avg. graph degree is "+avg);
			System.out.println("The max. graph degree is "+testNetwork.getIoTGraph().getGraphMaxDegree());
			//Execute the network optimization process
			testNetwork.networkOptimizer();
			System.out.println("After applying network optimization: ");
			avg = ((double)testNetwork.getIoTGraph().getNumLinks())/(testNetwork.getIoTGraph().getNumNodes());
			System.out.println("The avg. graph degree is "+avg);
			System.out.println("The max. degree is "+testNetwork.getIoTGraph().getGraphMaxDegree());
			
		}
		
		//Test the forwarding process before and after network optimization
		IoTSensorNetwork testNetwork = new IoTSensorNetwork(900, 10);
		
		for (int i=1; i<=20; i++)
		{
			//select the indices of a source and destination sensors at random
			int sensor1Index = (int)(900*Math.random());
			int sensor2Index = (int)(900*Math.random());
			IoTSensorNode sensor1 =testNetwork.getIoTGraph().nodeFromIndex(sensor1Index);
			IoTSensorNode sensor2 =testNetwork.getIoTGraph().nodeFromIndex(sensor2Index);
			
			ArrayList<IoTSensorNode> path = testNetwork.Three10ForwardingProtocol(sensor1, sensor2);
			
			System.out.println("Forwarding path from sensor"+sensor1Index+" to sensor "+sensor2Index+" is :");
			for (int j=0; j<path.size(); j++)
			{
				System.out.print(" "+path.get(j).getName());
			}
			if (!sensor2.equals(path.get(0)))
			{
				
				System.out.println("No path found between the source and destination");
			}
			System.out.println();
			System.out.println("Length of the path between source "+sensor1Index+" and destination"+sensor2Index+" is: "+(path.size()));
		}
		
		
		//Test the forwarding process after network optimization
		testNetwork.networkOptimizer();
		
		System.out.println("After applying network optimization:");
		for (int i=1; i<=10; i++)
		{
			/* Randomly pick two vertices as the source and destination */
			int sensor1Index = (int)(900*Math.random());
			int sensor2Index = (int)(900*Math.random());
			IoTSensorNode sensor1 =testNetwork.getIoTGraph().nodeFromIndex(sensor1Index);
			IoTSensorNode sensor2 =testNetwork.getIoTGraph().nodeFromIndex(sensor2Index);
			
			ArrayList<IoTSensorNode> path = testNetwork.Three10ForwardingProtocol(sensor1, sensor2);
			System.out.println();
			System.out.println("Forwarding path from sensor"+sensor1Index+" to sensor "+sensor2Index+" is :");
			for (int j=0; j<path.size(); j++)
			{
				System.out.print(" "+path.get(j).getName());
			}
			if (!sensor2.equals(path.get(0)))
			{
				System.out.println();
				System.out.println("No path found between the source and destination");
			}
			System.out.println();
			System.out.println("Length of the path between source "+sensor1Index+" and destination"+sensor2Index+" is: "+(path.size()));
		}
    
    
    }
}

