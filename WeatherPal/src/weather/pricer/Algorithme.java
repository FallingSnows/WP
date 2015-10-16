package weather.pricer;

import java.util.List;

public class Algorithme 
{
	//how to use a function as a parameter
	public double Bisection(double function, double Strike)
	{
		double result = 0;
		
		
		
		
		return result;
	}
	
	/*
	 * usage: quicksort the double array
	 * 
	 * Write: Vincent
	 */
	public static void quick_sort(double s[], int l, int r)  
	{  
	    if (l < r)  
	    {  
	        //Swap(s[l], s[(l + r) / 2]); //���м��������͵�һ�������� �μ�ע1  
	        int i = l, j = r;
	        double x = s[l];  
	        while (i < j)  
	        {  
	            while(i < j && s[j] <= x) // ���������ҵ�һ��С��x����  
	                j--;    
	            if(i < j)   
	                s[i++] = s[j];  
	              
	            while(i < j && s[i] > x) // ���������ҵ�һ�����ڵ���x����  
	                i++;    
	            if(i < j)   
	                s[j--] = s[i];  
	        }  
	        s[i] = x;  
	        quick_sort(s, l, i - 1); // �ݹ����   
	        quick_sort(s, i + 1, r);  
	    }  
	}


	/*
	 * usage: quicksort the int array
	 * 
	 * Write: Vincent
	 */
	public static void quick_sort(int s[], int l, int r)  
	{  
	    if (l < r)  
	    {  
	        //Swap(s[l], s[(l + r) / 2]); //���м��������͵�һ�������� �μ�ע1  
	        int i = l, j = r;
	        int x = s[l];  
	        while (i < j)  
	        {  
	            while(i < j && s[j] <= x) // ���������ҵ�һ��С��x����  
	                j--;    
	            if(i < j)   
	                s[i++] = s[j];  
	              
	            while(i < j && s[i] > x) // ���������ҵ�һ�����ڵ���x����  
	                i++;    
	            if(i < j)   
	                s[j--] = s[i];  
	        }  
	        s[i] = x;  
	        quick_sort(s, l, i - 1); // �ݹ����   
	        quick_sort(s, i + 1, r);  
	    }  
	}
	
	
	public static int find_index_interval(double s[], double strike)
	{
		int result = 0;
		if(strike > s[s.length - 1])
		{
			result = s.length -1;
		}
		else if(strike < s[0])
		{
			result = 0;
		}
		else
		{
			for(int index = 0; index != s.length; ++ index)
			{
				if((strike >= s[index]) && (strike < s[index + 1]))
				{
					result = index;
				}
				
			}
		}

		
		return result;
	}
	

public class Algorithm
{
	public static double distance(double a[], double b[])  
	{ 
		double a_lat = a[0]*Math.PI/180; //a_lat is the latitude of a
		double a_lon = a[1]*Math.PI/180; //a_lon is the longitude of a
		double b_lat = b[0]*Math.PI/180; //b_lat is the latitude of b
		double b_lon = b[1]*Math.PI/180; //b_lon is the longitude of b
		
		double lat_difference = a_lat - b_lat;
		double lon_difference = a_lon - b_lon;
		
		double d = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(lat_difference/2),2) + Math.cos(a_lat)*Math.cos(b_lat)*Math.pow(Math.sin(lon_difference/2),2))) * 6371; 
		//The Haversine formula remains particularly well-conditioned for numerical computation even at small distances 
		
		return Math.round(d * 100.0)/100.0;//2 decimal places
	}
	
	public static double point_to_line_segment(double p[], double a[], double b[])
	{
		/* 	Idea:
		    1) 求出c点在直线ab上的投影点d
 			2) 如果d在线段ab上 返回Distance(d, c);
 			3) 如果d不在线段ab上 找出两端点中距离d最近的端点e, 返回Distance(e, c);
		 */
		double p_lat = p[0]*Math.PI/180;
		double p_lon = p[1]*Math.PI/180;
		double a_lat = a[0]*Math.PI/180;
		double a_lon = a[1]*Math.PI/180;
		double b_lat = b[0]*Math.PI/180;
		double b_lon = b[1]*Math.PI/180;
	
		double lat_difference = a_lat - b_lat;
		double lon_difference = a_lon - b_lon;
		
		double vector_ab_square = lat_difference * lat_difference + lon_difference * lon_difference; // vector AB^2
		double r = ((p_lat - a_lat) * (-lat_difference) + (p_lon - a_lon) * (-lon_difference)) / vector_ab_square; //  AP*AB/AB^2
		// if r>=1, PAB; if 1>r>0, APB; if r<=0, ABP.
		System.out.println(r);
		
		// Now we are going to use Heron's formula
		double ap = distance(a,p);
		double bp = distance(b,p);
		double ab = distance(a,b);
		
		double P = (ap+bp+ab)/2; 
		double area = Math.sqrt(P*(P-ap)*(P-bp)*(P-ab));
		double height = 2*area / ab;
		
		 if (r <= 0) {
	    	return distance(p,a); //AP
	    } 
		else if (r >= 1) {
			return distance(p,b); //BP
	    }		   
	    else {
	    	return Math.round(height * 100.0)/100.0;//CP
	    } 
	}
	
	public static double searchSpeed(double speed, List<Typhoon> a)
	{
	     for(int i = 0; i+1 < a.size(); i++) {
	            if((a.get(i).getSpeed()<=speed)&&(a.get(i+1).getSpeed()>=speed)){
	            	return 10+i;
	            }
	            else if((a.get(i).getSpeed()>=speed)&&(a.get(i+1).getSpeed()<=speed)){
	            	return 20+i;
	            }
	        }
		return -1;
	}

	public static String find_newSpeed_location(double speed, List<Typhoon> a)
	{
			double d = searchSpeed(speed,a)%10;
			
            if( (searchSpeed(speed,a)-d)/10 == 1){
            	double x2 = a.get((int) (d+1)).getLat();
            	double x1 = a.get((int) d).getLat();
            	double y2 = a.get((int) (d+1)).getLon();
            	double y1 = a.get((int) d).getLon();
            	double s2 = a.get((int) (d+1)).getSpeed();
            	double s1 = a.get((int) d).getSpeed();
            	//System.out.println(d +","+ x2 +","+ x1 +","+ y2 +","+ y1 +","+ s2 +","+ s1);
            	
            	double new_lat = x1+(s2-speed)/(s2-s1)*(x2-x1);
            	double new_lon = y2-(s2-speed)/(s2-s1)*(y2-y1);
            	return new_lat +","+ new_lon +","+ 1;
            }
            else if((searchSpeed(speed,a)-d)/10 == 2){
            	double x2 = a.get((int) (d+1)).getLat();
            	double x1 = a.get((int) d).getLat();
            	double y2 = a.get((int) (d+1)).getLon();
            	double y1 = a.get((int) d).getLon();
            	double s2 = a.get((int) (d+1)).getSpeed();
            	double s1 = a.get((int) d).getSpeed();
            	//System.out.println(d +","+ x2 +","+ x1 +","+ y2 +","+ y1 +","+ s2 +","+ s1);
            	
            	double new_lat = x2-(s2-speed)/(s2-s1)*(x2-x1);
            	double new_lon = y2-(s2-speed)/(s2-s1)*(y2-y1);
            	return new_lat +","+ new_lon +","+ 2;
            }
            else if((searchSpeed(speed,a)-d)/10 == -1){	
            	return "Cannot be found";
            	}
            
		return "Cannot be found";
	}

	public static void insert(double speed, List<Typhoon> a){
		String d = find_newSpeed_location(speed,a);
		String dSplit_lat = d.split(",")[0];
		String dSplit_lon = d.split(",")[1];
		double new_lat = Double.parseDouble(dSplit_lat);
		double new_lon = Double.parseDouble(dSplit_lon);
		//double new_lon = Double.parseDouble(find_newSpeed_location(speed,a).split(",", 1)[1]);
		
		
		Typhoon ty_new = new Typhoon();
		ty_new.setParameters(196523,"1965-11-14T02:00:00",new_lat,new_lon,speed);

	}
	

	
	
}

	class Typhoon
	{
		private int typhoon_id;
		private String time;
		private double latitude;
		private double longitude;
		private double speed;
		

	    public String getParameters() {
	        return typhoon_id + time + latitude + longitude + speed;
	    }
	    public double getSpeed(){
	    	return speed;
	    }
	    public double getLat(){
	    	return latitude;
	    }
	    public double getLon(){
	    	return longitude;
	    }

	    public void setParameters(int typhoon_id, String time, double latitude, double longitude, double speed) {
	        this.typhoon_id = typhoon_id;
	    	this.time = time;
	    	this.latitude = latitude;
	    	this.longitude = longitude;
	    	this.speed = speed;
	    }
	    
	    @Override
	    public String toString(){
	        return "typhoon_id:"+typhoon_id+", time:" +time+", latitude:" +latitude+", longitude:" +longitude+", speed:" +speed +"\n";
	    }
	}
}