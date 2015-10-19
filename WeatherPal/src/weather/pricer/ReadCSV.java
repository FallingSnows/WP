import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import java.util.ArrayList;

class tf_noeud
{
	int id;
	String time;
	double[] location = new double[2];
	double speed;
	
	
	public tf_noeud(int id, String time, double[] location, double speed) {
		super();
		this.id = id;
		this.time = time;
		this.location = location;
		this.speed = speed;
	}
}
	
	
	/*
 	* writer: Vincent
 	* */
public class ReadCSV
{
	/*
	 * usage: define how to read the typhoon history data
	 *
	 *Writer: Vincent
	 */
	
	public static List<tf_noeud> Typhon_Read(int typhoon_num)
	{
		List<tf_noeud> tf_noeud_list = new ArrayList<tf_noeud>();
		List<tf_noeud> some_specific_typhoon_id = new ArrayList<tf_noeud>();
		
		try
		{
			File csv = new File("/Users/Walter/Documents/workspace/typhoon_test/src/typhoon.csv");  //the input path
			
			BufferedReader br = new BufferedReader( new FileReader (csv));
			
			//read until the last line
			while(br.ready())
			{
				String line = br.readLine();
				
				if(line.trim().isEmpty()){
					continue;
				}
				
				StringTokenizer st = new StringTokenizer(line, ",");
				
				while(st.hasMoreTokens())
				{
					int tf_id = Integer.parseInt(st.nextToken());
					
					String tf_time = st.nextToken();
					
					double[] tf_location = new double[2];
					tf_location[0] = Double.parseDouble(st.nextToken());
					tf_location[1] = Double.parseDouble(st.nextToken());
					double tf_speed = Double.parseDouble(st.nextToken());
					
					tf_noeud noeud = new tf_noeud(tf_id, tf_time, tf_location, tf_speed);
					
					tf_noeud_list.add(noeud);
					
					if(tf_id == typhoon_num){
						some_specific_typhoon_id.add(noeud);
					}
				}
				
			}
			
			br.close();
			//read finish
		} 
		catch (FileNotFoundException e)
		{
			//catch the exception when we create the file object
			e.printStackTrace();
		} 
		
		catch (IOException e)
		{
			//BufferedReader close the object and catch the exception
			e.printStackTrace();
		}
		/*
		for(int q = 0; q < tf_noeud_list.size(); ++q)
		{
			if( typhoon_num == tf_noeud_list.get(q).id)
			System.out.println(tf_noeud_list.get(q).id + " " + tf_noeud_list.get(q).time + " " + tf_noeud_list.get(q).speed);
			
		}
		
		*/
			return some_specific_typhoon_id;
	}


	public static List<tf_noeud> reverse(List<tf_noeud> list) {
		for(int i = 0, j = list.size() - 1; i < j; i++) {
			list.add(i, list.remove(j));
		}
		return list;
	}
}