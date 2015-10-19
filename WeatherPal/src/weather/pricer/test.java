import java.util.*;


public class test {

    public static void main(String[] args) {
  
    	List<tf_noeud> some_typhoon = ReadCSV.Typhon_Read(201407);
    	
    	/*
    	for(int i = 0; i < some_typhoon.size(); ++i){
    		for(int j = 0; j < 2; ++j){
    			System.out.println(some_typhoon.get(i).location[j]);
    		}
    	}
    	
    	for(int i = 0; i+1 < some_typhoon.size(); ++i){
    		System.out.println("Distance between points "+i+" and " + (i+1) + " for this typhoon: " + Algorithm.distance(some_typhoon.get(i).location,some_typhoon.get(i+1).location));
    	}
    	*/
    	double[] shenzhen ={22.65,114.00}; //p //深圳气象站经纬度坐标
    	double[] minDistanceToSeg = new double[some_typhoon.size()];
    	
    	for(int i = 0; i+1 < some_typhoon.size(); ++i){
    		minDistanceToSeg[i]	= Algorithm.point_to_line_segment(shenzhen,some_typhoon.get(i).location,some_typhoon.get(i+1).location);
    		System.out.println("Distance between Shenzhen and line segment between points "+i+" and " + (i+1) + "( "+Algorithm.distance(some_typhoon.get(i).location,some_typhoon.get(i+1).location)+ "km) for typhoon "+some_typhoon.get(0).id +" is " + minDistanceToSeg[i]+ "km at "+ some_typhoon.get(i).time);
    	}
    	//point to line segment
    	
    	double minDTS = minDistanceToSeg[0];
    	String minDTStime = null;
    	for(int k = 0; k+1 < minDistanceToSeg.length; k++){
    		if(minDistanceToSeg[k] < minDTS){
    			minDTS = minDistanceToSeg[k];
    			minDTStime = some_typhoon.get(k).time;
    		}
    	}
    	System.out.println("Minimum distance between Shenzhen and the typhoon " +some_typhoon.get(0).id +" is "+ minDTS+" km at "+ minDTStime);
    	//min distance to typhoon from p
    	
    }
}
    	
    	
    	
  