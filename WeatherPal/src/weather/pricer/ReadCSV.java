package weather.pricer;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;



/*
 * usage: define the configuration file for Xingnongwang
 * 			city name, the begin date and the end date
 * writer: Vincent
 * */
class Config
{
	String city_name;
	int BeginDayIndex;
	int EndDayIndex;
}



/*
 * usage: define the history data
 * 		  histo_data: repeat the 2/29 for every year
 * 		  histo_data_: delete the 2/29 for every year
 * 		  city name, the begin date and the end date
 * writer: Vincent
 * */
class Data
{
	double [][][]histo_data = new double[30][366][3];
	double [][][]histo_data_ = new double[30][365][3];
	
};

/*
 * usage: hold the typhon data noeud 
 * 		  time, location, speed
 * 
 * Writer: Vincent
 */

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
	//getter and setter
	
	public String getTime() {
		return time;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTime(String time) {
		this.time = time;
	}
	public double[] getLocation() {
		return location;
	}
	public void setLocation(double[] location) {
		this.location = location;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
}


class Data_
{
	double	[][][] histo_data_;
};

/*
 * usage: this class define how to read the file
 * 
 * 
 * writer: Vincent
 */

public class ReadCSV
{
	/*
	 * usage: define how to read the typhon history data
	 *
	 *Writer: Vincent
	 */
	
	public static void Typhon_Read()
	{
		List<tf_noeud> tf_noeud_list = new ArrayList<tf_noeud>();
		
		try
		{
			File csv = new File("C:/Database/Typhon/195904.csv");  //the input path
			
			BufferedReader br = new BufferedReader( new FileReader (csv));
			
			//read until the last line
			while(br.ready())
			{
				String line = br.readLine();
				
				StringTokenizer st = new StringTokenizer(line, "; || , || \t");
				
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
		
		for(int q = 0; q < tf_noeud_list.size(); ++q)
		{
			System.out.println(tf_noeud_list.get(q).id + " " + tf_noeud_list.get(q).time + " " + tf_noeud_list.get(q).speed);
		}
		return ;
	}
	
	
	
	
	/*usage: define how to read the standard history data
	 * 
	 * writer: Vincent
	 * 
	 * */
	public static double [][][] Standard_Read(String str)
	{
		double [][][] history = new double[18][365][20];
		int[][] read_pool = new int[7000][20];
		
		try
		{
			File csv = new File("C:/Database/All/" + str + ".csv");  //the input path
			
			BufferedReader br = new BufferedReader( new FileReader (csv));
			
			//read until the last line
			int i = 0;
			while(br.ready())
			{
				if( (i-424) % 1461 != 0)
				{
					
					String line = br.readLine();
					java.util.StringTokenizer st = new StringTokenizer(line, ";||,");
					int j = 0;
					while(st.hasMoreTokens())
					{
						read_pool[i][j] = Integer.parseInt(st.nextToken());
						j++;
					}
					
				}
				else
				{
					br.readLine();
				}
				
			i++;
			}
			
			br.close();
			//read finish
			
			
			for(int j = 0; j < 6570; ++j)
			{
				for(int k = 0; k <20; ++k)
				{
					history[j/365][j%365][k] = read_pool[j][k];
				}
			}
			
			for(int IndexofYear = 0; IndexofYear < 18; ++IndexofYear)
			{
				for(int IndexofDay = 0; IndexofDay < 365; ++IndexofDay)
				{
					if(IndexofDay > 0 && history[IndexofYear][IndexofDay][3] == history[IndexofYear][IndexofDay-1][3])
					{
						System.out.println("Duplicated data in " + history[IndexofYear][IndexofDay][1]+history[IndexofYear][IndexofDay][2]+history[IndexofYear][IndexofDay][3]+", is approximately on the line "+ ((history[IndexofYear][IndexofDay][1]-1995)*365+(history[IndexofYear][IndexofDay][2]-1)*12+(history[IndexofYear][IndexofDay][3]-1)) + ", with an error(leap year) of 5 lines");
					}
					
					if(history[IndexofYear][IndexofDay][4] == 32700)
					{
						history[IndexofYear][IndexofDay][4] = 0;
					}
				}
			}
			
			
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
		
		return history;
	}

	//public static 
	
	public static Data Read(String str)
	{
		Data history = new Data();
		
		try
		{
			File csv = new File("C:/Database/" + str + "_CH.csv");  //the input path?
			
			BufferedReader br = new BufferedReader( new FileReader (csv));
			
			double [][] read = new double[10000][6];
			
			
			//read until the last line
			int i = 0;
			while(br.ready())
			{
				String line = br.readLine();
				java.util.StringTokenizer st = new StringTokenizer(line, ";||,");
				
				int j = 0;
				while(st.hasMoreTokens())
				{
					read[i][j] = Double.parseDouble(st.nextToken());
					j++;
				}
				i++;
			}
			
			br.close();
			//read finish
			
			int first_year = (int)read[0][0];
			//arrange the history data
			
			int NumOfYears = 25;
			int NumOfDays = 366;
			int NumOfSpecialYear = 0;
			
			for (int IndexOfYears = 0; IndexOfYears < NumOfYears; ++IndexOfYears)
			{
				if((first_year + IndexOfYears) % 4 != 0)
				{
					for (int IndexOfDays = 0; IndexOfDays < NumOfDays; ++IndexOfDays)
					{
						if(IndexOfDays < 59)
						{
						
							history.histo_data[IndexOfYears][IndexOfDays][0] = 
								read[IndexOfYears * 365 + IndexOfDays + NumOfSpecialYear][3];
							history.histo_data[IndexOfYears][IndexOfDays][1] = 
								read[IndexOfYears * 365 + IndexOfDays + NumOfSpecialYear][4];
							history.histo_data[IndexOfYears][IndexOfDays][2] = 
								read[IndexOfYears * 365 + IndexOfDays + NumOfSpecialYear][5];
						}
						else
						{
							history.histo_data[IndexOfYears][IndexOfDays][0] = 
									read[IndexOfYears * 365 + IndexOfDays - 1 + NumOfSpecialYear][3];
							history.histo_data[IndexOfYears][IndexOfDays][1] = 
									read[IndexOfYears * 365 + IndexOfDays - 1 + NumOfSpecialYear][4];
							history.histo_data[IndexOfYears][IndexOfDays][2] = 
									read[IndexOfYears * 365 + IndexOfDays - 1 + NumOfSpecialYear][5];
						}
						
					}
					
				}
				else
				{
					
					for (int IndexOfDays = 0; IndexOfDays < NumOfDays; ++IndexOfDays)
					{
						history.histo_data[IndexOfYears][IndexOfDays][0] = 
								read[IndexOfYears * 365 + IndexOfDays + NumOfSpecialYear][3];
						history.histo_data[IndexOfYears][IndexOfDays][1] = 
								read[IndexOfYears * 365 + IndexOfDays + NumOfSpecialYear][4];
						history.histo_data[IndexOfYears][IndexOfDays][2] = 
								read[IndexOfYears * 365 + IndexOfDays + NumOfSpecialYear][5];
					}
					NumOfSpecialYear ++;
				}
			}
			
			
			
			for (int IndexOfYears = 0; IndexOfYears < NumOfYears; ++IndexOfYears)
			{
				for (int IndexOfDays = 0; IndexOfDays < 365; ++IndexOfDays)
				{
					if(IndexOfDays < 59)
					{
						history.histo_data_[IndexOfYears][IndexOfDays][0] = history.histo_data[IndexOfYears][IndexOfDays][0];
						history.histo_data_[IndexOfYears][IndexOfDays][1] = history.histo_data[IndexOfYears][IndexOfDays][1];
						history.histo_data_[IndexOfYears][IndexOfDays][2] = history.histo_data[IndexOfYears][IndexOfDays][2];
					}
					else
					{
						history.histo_data_[IndexOfYears][IndexOfDays][0] = history.histo_data[IndexOfYears][IndexOfDays + 1][0];
						history.histo_data_[IndexOfYears][IndexOfDays][1] = history.histo_data[IndexOfYears][IndexOfDays + 1][1];
						history.histo_data_[IndexOfYears][IndexOfDays][2] = history.histo_data[IndexOfYears][IndexOfDays + 1][2];
					}
				}
			}
			
			for (int IndexOfYears = 0; IndexOfYears < NumOfYears; ++IndexOfYears)
			{
				for (int IndexOfDays = 0; IndexOfDays < 365; ++IndexOfDays)
				{
					if(history.histo_data_[IndexOfYears][IndexOfDays][0] == 32700)
					{
						history.histo_data_[IndexOfYears][IndexOfDays][0] = 0;
					}
				}
			}
					
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
		
		return history;
	}

	/*
	 * usage: test the read function
	 * 
	 * 
	 * writer: Vincent
	 */
	
	public static double [][] Read_()
	{
		double [][] read = new double[36][43];
		
		try
		{
			File csv = new File("C:/Database/xyz.csv"); 
			
			BufferedReader br = new BufferedReader( new FileReader (csv));
			
			
			//read until the last line
			int i = 0;
			while(br.ready())
			{
				String line = br.readLine();
				java.util.StringTokenizer st = new StringTokenizer(line, ";");
				
				
				int j = 0;
				while(st.hasMoreTokens())
				{
					read[i][42-j] = Double.parseDouble(st.nextToken());
					j++;
					//System.out.print(Double.parseDouble(st.nextToken()) + "\t");
				}
				i++;
			}
			
			br.close();
		
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
		
		return read;
	}
	
	/*
	 * usage: read the config file
	 * 
	 * Write: Vincent
	 */
	public static Config Read()
	{
		Config conf = new Config();
		
		try
		{
			File txt = new File("C:/Database/config.txt");
			
			BufferedReader br = new BufferedReader( new FileReader (txt));
			
			if(br.ready())
			{
				String line = br.readLine();
				java.util.StringTokenizer st = new StringTokenizer(line, " ");
				
				if(st.hasMoreTokens())
				{
					conf.city_name = st.nextToken();
					conf.BeginDayIndex = Integer.parseInt(st.nextToken());
					conf.EndDayIndex = Integer.parseInt(st.nextToken());
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
		
		return conf;
	}
	
}



