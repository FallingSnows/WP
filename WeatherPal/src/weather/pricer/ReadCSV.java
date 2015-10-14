package weather.pricer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;


/*
 * usage: define the configuration file for Qixiangju
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
 * city name, the begin date and the end date
 * writer: Vincent
 * */
class Data
{
	double [][][]histo_data = new double[30][366][3];
	double [][][]histo_data_ = new double[30][365][3];
	
	
	//check the data the give the standard statue, maybe do not need
	boolean [][][] Check_Statue(double Ruin, double High_tempreture, double low_tempreture)
	{
		boolean [][][]statue = new boolean[20][366][3];
		for(int i = 0; i < 20; ++i)
			for(int j = 0; j < 365; ++j)
			{
				statue[i][j][0] = (histo_data[i][j][0] >= Ruin);
				statue[i][j][1] = (histo_data[i][j][1] >= High_tempreture);
				statue[i][j][2] = (histo_data[i][j][2] <= low_tempreture);
			}
		return statue;
	};
};


class Data_
{
	double	[][][] histo_data_;
};


//this class define how to read the file
public class ReadCSV
{
	/*
	void CheckAndLoadData()
	{
		return ;
	};
	*/

	/*usage: define the standard history data
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
			File csv = new File("C:/Database/All/" + str + ".csv");  //the input path?
			
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
			
			
			/* no change any more for history data
			 * 
			 *  
			 * 
			for (int IndexOfYears = 0; IndexOfYears < NumOfYears; ++IndexOfYears)
			{
				for (int IndexOfDays = 0; IndexOfDays < NumOfDays; ++IndexOfDays)
				{
						if(((int)(history.histo_data[IndexOfYears][IndexOfDays][2])) % 10 >= 5)
							history.histo_data[IndexOfYears][IndexOfDays][2] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][2])) / 10 + 1);
						else if (((int)(history.histo_data[IndexOfYears][IndexOfDays][2])) % 10 <= -5)
							history.histo_data[IndexOfYears][IndexOfDays][2] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][2])) / 10 - 1);
						else
							history.histo_data[IndexOfYears][IndexOfDays][2] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][2])) / 10);
						
						if(((int)(history.histo_data[IndexOfYears][IndexOfDays][1])) % 10 >= 5)
							history.histo_data[IndexOfYears][IndexOfDays][1] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][1])) / 10 + 1);
						else if (((int)(history.histo_data[IndexOfYears][IndexOfDays][1])) % 10 <= -5)
							history.histo_data[IndexOfYears][IndexOfDays][1] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][1])) / 10 - 1);
						else
							history.histo_data[IndexOfYears][IndexOfDays][1] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][1])) / 10);
						
						if(((int)(history.histo_data[IndexOfYears][IndexOfDays][0])) % 10 >= 5)
							history.histo_data[IndexOfYears][IndexOfDays][0] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][0])) / 10 + 1);
						else if (((int)(history.histo_data[IndexOfYears][IndexOfDays][0])) % 10 <= -5)
							history.histo_data[IndexOfYears][IndexOfDays][0] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][0])) / 10 - 1);
						else
							history.histo_data[IndexOfYears][IndexOfDays][0] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][0])) / 10);
				}
			}
			
			
			*/
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
			
			/*
			for(int ii = 0; ii < 18; ++ii)
			{
				for(int jj = 360; jj < 365; ++jj)
				{
					System.out.println(history.histo_data_[ii][jj][0] + " " +  history.histo_data_[ii][jj][1] + " "+ history.histo_data_[ii][jj][2]);
				}
				
				System.out.println();
			}
			*/
			//boolean [][][]Statue_his;
			//Statue_his = history.Check_Statue(100, 350,-99999);

			
/*			
			for (int ii = 15; ii < 16; ++ii)
				{
					System.out.print(history.year[ii] + "\n" + "\t" + "Ã‚Â½Ã‚ÂµÃƒâ€œÃƒÂª" +"\t" + "Ã‚Â¸ÃƒÅ¸ÃƒÅ½Ãƒâ€š");
					for(int j = 0; j < 300; ++j)
					{
						System.out.println();
						System.out.print(history.month_days[j] + "\t");
						System.out.print(Statue_his[ii][j][0] + "\t");
						System.out.print(Statue_his[ii][j][1] + "\t");
						//System.out.print(Statue_his[ii][j][2] + " ");
						
					
					}
				}
				
*/		
			//System.out.print(Statue_his[1][1][0]);
			/*
			for (int ii = 15; ii < 16; ++ii)
				{
					System.out.print(history.year[ii] + "\n" + "\t" + "Ã‚Â½Ã‚ÂµÃƒâ€œÃƒÂª" +"\t" + "Ã‚Â¸ÃƒÅ¸ÃƒÅ½Ãƒâ€š");
					for(int j = 0; j < 300; ++j)
					{
						System.out.println();
						System.out.print(history.month_days[j] + "\t");
						System.out.print(Statue_his[ii][j][0] + "\t");
						System.out.print(Statue_his[ii][j][1] + "\t");
						//System.out.print(Statue_his[ii][j][2] + " ");
						
					
					}
				}
			*/	
			
			//System.out.print(Statue_his[1][1][0]);
					
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
	
	

	public static Data ReadQiXiangJu(String str)
	{
		Data history = new Data();
		
		try
		{
			File csv = new File("C:/Database/" + str + " CH.csv");  //the input path?
			
			BufferedReader br = new BufferedReader( new FileReader (csv));
			
			double [][] read = new double[10000][6];
			
				
			//read until the last line
			int i = 0;
			String line = br.readLine();
			while(br.ready())
			{
				line = br.readLine();
				java.util.StringTokenizer st = new StringTokenizer(line, ",");
				
				st.nextToken();
				int j = 0;
				while(st.hasMoreTokens())
				{
					read[i][j] = Double.parseDouble(st.nextToken());
					j++;
					//System.out.print(Double.parseDouble(st.nextToken()) + "\t");
				}
				i++;
//					System.out.println();
			}
				
			br.close();
			//read finish
				
			int first_year = (int)read[0][0];
			//arrange the history data
				
			int NumOfYears = 25;
			int NumOfDays = 365;
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
				for (int IndexOfDays = 0; IndexOfDays < NumOfDays; ++IndexOfDays)
				{
					if(((int)(history.histo_data[IndexOfYears][IndexOfDays][2])) % 10 >= 5)
					{
						history.histo_data[IndexOfYears][IndexOfDays][2] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][2])) / 10 + 1);
					}
					else if (((int)(history.histo_data[IndexOfYears][IndexOfDays][2])) % 10 <= -5)
					{
						history.histo_data[IndexOfYears][IndexOfDays][2] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][2])) / 10 - 1);
					}
					else
					{	
						history.histo_data[IndexOfYears][IndexOfDays][2] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][2])) / 10);
					}
							
					if(((int)(history.histo_data[IndexOfYears][IndexOfDays][1])) % 10 >= 5)
					{
						history.histo_data[IndexOfYears][IndexOfDays][1] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][1])) / 10 + 1);
					}
					else if (((int)(history.histo_data[IndexOfYears][IndexOfDays][1])) % 10 <= -5)
					{
						history.histo_data[IndexOfYears][IndexOfDays][1] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][1])) / 10 - 1);
					}
					else
					{
						history.histo_data[IndexOfYears][IndexOfDays][1] = (double)(((int)(history.histo_data[IndexOfYears][IndexOfDays][1])) / 10);
					}
				}
			}
				
				
			for (int IndexOfYears = 0; IndexOfYears < NumOfYears; ++IndexOfYears)
			{
				for (int IndexOfDays = 0; IndexOfDays < 365; ++IndexOfDays)
				{
					if(IndexOfDays > 59)
					{
						history.histo_data_[IndexOfYears][IndexOfDays][1] = history.histo_data[IndexOfYears][IndexOfDays][0];
						history.histo_data_[IndexOfYears][IndexOfDays][2] = history.histo_data[IndexOfYears][IndexOfDays][1];
						history.histo_data_[IndexOfYears][IndexOfDays][0] = history.histo_data[IndexOfYears][IndexOfDays][2];
					}
					else
					{
						history.histo_data_[IndexOfYears][IndexOfDays][1] = history.histo_data[IndexOfYears][IndexOfDays + 1][0];
						history.histo_data_[IndexOfYears][IndexOfDays][2] = history.histo_data[IndexOfYears][IndexOfDays + 1][1];
						history.histo_data_[IndexOfYears][IndexOfDays][0] = history.histo_data[IndexOfYears][IndexOfDays + 1][2];
					}
					
					if (history.histo_data_[IndexOfYears][IndexOfDays][0] == 32700)
					{
						history.histo_data_[IndexOfYears][IndexOfDays][0] = 0;
					}
				}
			}
			//boolean [][][]Statue_his;
			//Statue_his = history.Check_Statue(100, 350,-99999);
			
			
	/*				
				for (int ii = 15; ii < 16; ++ii)
					{
						System.out.print(history.year[ii] + "\n" + "\t" + "Ã‚Â½Ã‚ÂµÃƒâ€œÃƒÂª" +"\t" + "Ã‚Â¸ÃƒÅ¸ÃƒÅ½Ãƒâ€š");
						for(int j = 0; j < 300; ++j)
						{
							System.out.println();
							System.out.print(history.month_days[j] + "\t");
							System.out.print(Statue_his[ii][j][0] + "\t");
							System.out.print(Statue_his[ii][j][1] + "\t");
							//System.out.print(Statue_his[ii][j][2] + " ");
							
						
						}
					}
					
	*/		
				//System.out.print(Statue_his[1][1][0]);
				/*
				for (int ii = 15; ii < 16; ++ii)
					{
						System.out.print(history.year[ii] + "\n" + "\t" + "Ã‚Â½Ã‚ÂµÃƒâ€œÃƒÂª" +"\t" + "Ã‚Â¸ÃƒÅ¸ÃƒÅ½Ãƒâ€š");
						for(int j = 0; j < 300; ++j)
						{
							System.out.println();
							System.out.print(history.month_days[j] + "\t");
							System.out.print(Statue_his[ii][j][0] + "\t");
							System.out.print(Statue_his[ii][j][1] + "\t");
							//System.out.print(Statue_his[ii][j][2] + " ");
							
						
						}
					}
				*/	
				
				//System.out.print(Statue_his[1][1][0]);
						
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
	/*ReadForYunnongchangÃ‚Â£Ã‚ÂºÃƒâ€�ÃƒÅ¡Ãƒâ€�Ãƒâ€ Ãƒâ€¦Ã‚Â©Ã‚Â³Ã‚Â¡Ãƒï¿½ÃƒÂ®Ãƒâ€žÃ‚Â¿Ãƒâ€“Ãƒï¿½Ã‚Â¶Ãƒï¿½ÃƒË†Ã‚Â¡Ãƒâ‚¬ÃƒÂºÃƒÅ Ã‚Â·ÃƒÅ ÃƒÂ½Ã‚Â¾Ãƒï¿½Ã‚Â£Ã‚Â¬Ã‚Â²Ã‚Â¢Ã‚Â½Ã‚Â«ÃƒÅ ÃƒÂ½Ã‚Â¾Ãƒï¿½Ã‚Â¸ÃƒÂ±ÃƒÅ Ã‚Â½Ã‚Â»Ã‚Â¯Ã‚Â¡Ã‚Â¾ÃƒÅ½ÃƒÅ¾Ãƒï¿½Ã‚Â£Ãƒâ€˜ÃƒÂ©Ã‚Â²Ã‚Â¿Ã‚Â·Ãƒâ€“Ã‚Â¡Ã‚Â¿
	 * ÃƒÅ ÃƒÂ¤ÃƒË†ÃƒÂ«Ã‚Â£Ã‚ÂºÃƒâ€�Ãƒï¿½ÃƒÅ½ÃƒÅ¾Ã‚Â£Ã‚Â¬Ã‚Â¿Ãƒâ€°Ãƒâ€™Ãƒâ€�Ã‚Â¿Ã‚Â¼Ãƒâ€šÃƒâ€¡Ãƒï¿½ÃƒÅ¾Ã‚Â¸Ãƒâ€žÃƒÅ Ãƒâ€¡Ã‚ÂµÃƒâ€žÃƒâ€™Ã‚Â»Ã‚Â´ÃƒÅ½Ã‚Â¶Ãƒï¿½ÃƒË†Ã‚Â¡Ãƒâ€™Ã‚Â»Ã‚Â¸ÃƒÂ¶ÃƒÅ½Ãƒâ€žÃ‚Â¼ÃƒÂ¾Ã‚Â¼Ãƒï¿½Ãƒâ€“Ãƒï¿½Ã‚ÂµÃƒâ€žÃƒâ‚¬ÃƒÂºÃƒÅ Ã‚Â·ÃƒÅ ÃƒÂ½Ã‚Â¾Ãƒï¿½
	 * ÃƒÅ ÃƒÂ¤Ã‚Â³ÃƒÂ¶Ã‚Â£Ã‚Âº20*366 Ã‚ÂµÃƒâ€žÃƒâ‚¬ÃƒÂºÃƒÅ Ã‚Â·ÃƒÅ ÃƒÂ½Ã‚Â¾Ãƒï¿½Ã‚Â¾ÃƒËœÃƒâ€¢ÃƒÂ³Ã‚Â£Ã‚Â¬Ãƒâ€ ÃƒÂ¤Ãƒâ€“Ãƒï¿½Ãƒâ‚¬ÃƒÂºÃƒÅ Ã‚Â·ÃƒÅ ÃƒÂ½Ã‚Â¾Ãƒï¿½Ã‚Â¶Ãƒâ€�Ãƒâ€œÃ‚Â¦ÃƒÅ½Ã‚Âª{Ã‚Â¸Ãƒâ€°Ã‚ÂºÃ‚ÂµÃ‚Â£Ã‚Â¬Ã‚Â±Ã‚Â©Ãƒâ€œÃƒÂªÃ‚Â£Ã‚Â¬Ã‚Â¸ÃƒÅ¸ÃƒÅ½Ãƒâ€šÃ‚Â£Ã‚Â¬Ãƒâ€¹Ã‚ÂªÃ‚Â¶Ã‚Â³Ã‚Â£Ã‚Â¬Ã‚Â½Ã‚ÂµÃƒâ€œÃƒÂªÃ‚Â£Ã‚Â¨3ÃƒÅ’ÃƒÂ¬Ã‚Â£Ã‚Â©Ã‚Â£Ã‚Â¬Ã‚Â½Ã‚ÂµÃƒâ€œÃƒÂªÃ‚Â£Ã‚Â¨5ÃƒÅ’ÃƒÂ¬Ã‚Â£Ã‚Â©Ã‚Â£Ã‚Â¬Ã‚Â½Ã‚ÂµÃƒâ€œÃƒÂªÃ‚Â£Ã‚Â¨7ÃƒÅ’ÃƒÂ¬Ã‚Â£Ã‚Â©}
	 * Ã‚Â±Ã‚Â¸Ãƒâ€”Ã‚Â¢Ã‚Â£Ã‚ÂºÃ‚Â¡Ã‚Â¾
	 * - Ã‚Â¸Ãƒâ€°Ã‚ÂºÃ‚ÂµÃ‚Â£Ã‚Â¨droughtÃ‚Â£Ã‚Â©Ã‚Â£Ã‚ÂºÃƒÆ’Ã‚Â¿Ãƒâ€žÃƒÂªÃƒâ€�ÃƒÅ¡ÃƒÅ Ã‚Â±Ã‚Â¼ÃƒÂ¤Ã‚Â·Ã‚Â¶ÃƒÅ½Ã‚Â§Ãƒâ€žÃƒÅ¡Ã‚Â£Ã‚Â¨Ãƒâ€žÃ‚Â³Ã‚ÂºÃƒâ€¦Ã‚ÂµÃ‚Â½10Ãƒâ€�Ãƒâ€š31Ã‚ÂºÃƒâ€¦Ã‚Â£Ã‚Â©Ãƒï¿½Ã‚Â¬Ãƒï¿½ÃƒÂ¸Ãƒâ€”ÃƒÂ®Ã‚Â³Ã‚Â¤XÃƒÅ’ÃƒÂ¬Ã‚Â½Ã‚ÂµÃƒâ€œÃƒÂªÃƒï¿½Ã‚Â¡Ãƒâ€œÃƒÂª10Ã‚ÂºÃƒï¿½ÃƒÆ’Ãƒâ€”
	 * - Ã‚Â±Ã‚Â©Ãƒâ€œÃƒÂªÃ‚Â¡Ã‚Â¢Ã‚ÂºÃƒÂ©Ãƒâ€¹Ã‚Â®Ã‚Â£Ã‚Â¨floodÃ‚Â£Ã‚Â©Ã‚Â£Ã‚ÂºÃƒÆ’Ã‚Â¿Ãƒâ€žÃƒÂªÃ‚Â£Ã‚Â¨Ãƒâ€žÃ‚Â³Ã‚ÂºÃƒâ€¦Ã‚ÂµÃ‚Â½10Ãƒâ€�Ãƒâ€š31Ã‚ÂºÃƒâ€¦Ã‚Â£Ã‚Â©Ãƒï¿½Ã‚Â½ÃƒÅ’ÃƒÂ¬Ãƒï¿½Ã‚Â¬Ãƒï¿½ÃƒÂ¸Ã‚Â½Ã‚ÂµÃƒâ€œÃƒÂªÃƒâ€”ÃƒÅ“Ãƒï¿½Ã‚Â¿Ãƒâ€”ÃƒÂ®Ã‚Â¸ÃƒÅ¸XÃ‚ÂºÃƒï¿½ÃƒÆ’Ãƒâ€”
	 * - Ã‚Â¸ÃƒÅ¸ÃƒÅ½Ãƒâ€šÃ‚Â£Ã‚Â¨heatÃ‚Â£Ã‚Â©Ã‚Â£Ã‚ÂºÃƒÆ’Ã‚Â¿Ãƒâ€žÃƒÂªÃ‚Â£Ã‚Â¨Ãƒâ€žÃ‚Â³Ã‚ÂºÃƒâ€¦Ã‚ÂµÃ‚Â½10Ãƒâ€�Ãƒâ€š31Ã‚ÂºÃƒâ€¦Ã‚Â£Ã‚Â©Ãƒâ€”ÃƒÂ®Ã‚Â¸ÃƒÅ¸Ã‚ÂµÃƒâ€žÃƒï¿½Ã‚Â¬Ãƒï¿½ÃƒÂ¸Ãƒï¿½Ã‚Â½ÃƒÅ’ÃƒÂ¬ÃƒÅ½Ãƒâ€šÃ‚Â¶ÃƒË†Ã‚Â³Ã‚Â¬Ã‚Â¹ÃƒÂ½XÃ‚Â¶ÃƒË†
	 * - Ã‚ÂµÃƒï¿½ÃƒÅ½Ãƒâ€šÃ‚Â£Ã‚Â¨frostÃ‚Â£Ã‚Â©Ã‚Â£Ã‚ÂºÃƒÆ’Ã‚Â¿Ãƒâ€žÃƒÂªÃ‚Â£Ã‚Â¨Ãƒâ€žÃ‚Â³Ã‚ÂºÃƒâ€¦Ã‚ÂµÃ‚Â½10Ãƒâ€�Ãƒâ€š31Ã‚ÂºÃƒâ€¦Ã‚Â£Ã‚Â©Ãƒâ€ ÃƒÅ¡Ã‚Â¼ÃƒÂ¤Ãƒâ€”ÃƒÂ®Ã‚ÂµÃƒï¿½ÃƒÅ½Ãƒâ€šÃ‚Â¶ÃƒË†XÃ‚Â¶ÃƒË†
	 * - Ã‚Â½Ã‚ÂµÃƒâ€œÃƒÂªÃ‚Â£Ã‚Â¨3ÃƒÅ’ÃƒÂ¬Ã‚Â£Ã‚Â©Ã‚Â£Ã‚ÂºÃ‚Â¸Ãƒâ€¦Ãƒâ€žÃƒÂ®Ãƒâ€žÃ‚Â³Ã‚ÂºÃƒâ€¦Ãƒï¿½ÃƒÂ¹Ã‚ÂºÃƒÂ³Ãƒâ€¹ÃƒÂ£3ÃƒÅ’ÃƒÂ¬Ãƒâ€žÃƒÅ¡Ã‚Â£Ã‚Â¬Ãƒâ€™Ã‚Â»Ã‚Â¹Ã‚Â²Ã‚Â¼Ã‚Â¸ÃƒÅ’ÃƒÂ¬Ãƒï¿½Ãƒâ€šÃƒâ€œÃƒÂªÃ‚Â£Ã‚Â¨Ã‚Â½Ã‚ÂµÃƒâ€¹Ã‚Â®Ã‚Â´ÃƒÂ¯Ã‚ÂµÃ‚Â½10Ã‚ÂºÃƒï¿½ÃƒÆ’Ãƒâ€”Ã‚Â£Ã‚Â©
	 * - Ã‚Â½Ã‚ÂµÃƒâ€œÃƒÂªÃ‚Â£Ã‚Â¨5ÃƒÅ’ÃƒÂ¬Ã‚Â£Ã‚Â©Ã‚Â£Ã‚ÂºÃ‚Â¸Ãƒâ€¦Ãƒâ€žÃƒÂ®Ãƒâ€žÃ‚Â³Ã‚ÂºÃƒâ€¦Ãƒï¿½ÃƒÂ¹Ã‚ÂºÃƒÂ³Ãƒâ€¹ÃƒÂ£5ÃƒÅ’ÃƒÂ¬Ãƒâ€žÃƒÅ¡Ã‚Â£Ã‚Â¬Ãƒâ€™Ã‚Â»Ã‚Â¹Ã‚Â²Ã‚Â¼Ã‚Â¸ÃƒÅ’ÃƒÂ¬Ãƒï¿½Ãƒâ€šÃƒâ€œÃƒÂªÃ‚Â£Ã‚Â¨Ã‚Â½Ã‚ÂµÃƒâ€¹Ã‚Â®Ã‚Â´ÃƒÂ¯Ã‚ÂµÃ‚Â½10Ã‚ÂºÃƒï¿½ÃƒÆ’Ãƒâ€”Ã‚Â£Ã‚Â©
	 * - Ã‚Â½Ã‚ÂµÃƒâ€œÃƒÂªÃ‚Â£Ã‚Â¨7ÃƒÅ’ÃƒÂ¬Ã‚Â£Ã‚Â©Ã‚Â£Ã‚ÂºÃ‚Â¸Ãƒâ€¦Ãƒâ€žÃƒÂ®Ãƒâ€žÃ‚Â³Ã‚ÂºÃƒâ€¦Ãƒï¿½ÃƒÂ¹Ã‚ÂºÃƒÂ³Ãƒâ€¹ÃƒÂ£7ÃƒÅ’ÃƒÂ¬Ãƒâ€žÃƒÅ¡Ã‚Â£Ã‚Â¬Ãƒâ€™Ã‚Â»Ã‚Â¹Ã‚Â²Ã‚Â¼Ã‚Â¸ÃƒÅ’ÃƒÂ¬Ãƒï¿½Ãƒâ€šÃƒâ€œÃƒÂªÃ‚Â£Ã‚Â¨Ã‚Â½Ã‚ÂµÃƒâ€¹Ã‚Â®Ã‚Â´ÃƒÂ¯Ã‚ÂµÃ‚Â½10Ã‚ÂºÃƒï¿½ÃƒÆ’Ãƒâ€”Ã‚Â£Ã‚Â©
	 * Ã‚Â¡Ã‚Â¿
	 * Ãƒâ€ ÃƒÂ¤Ãƒâ€“Ãƒï¿½Ãƒâ€¡Ã‚Â°Ãƒâ€¹Ãƒâ€žÃƒï¿½ÃƒÂ®Ãƒâ€œÃƒï¿½Ãƒï¿½Ã‚Â§ÃƒÅ ÃƒÂ½Ã‚Â¾Ãƒï¿½ÃƒÅ½Ã‚Âª ÃƒÆ’Ã‚Â¿Ãƒâ€žÃƒÂªÃ‚ÂµÃƒÅ¡60ÃƒÅ’ÃƒÂ¬Ã‚Â£Ã‚Â¨3Ãƒâ€�Ãƒâ€š1Ã‚ÂºÃƒâ€¦Ã‚Â£Ã‚Â©Ã‚ÂµÃ‚Â½305ÃƒÅ’ÃƒÂ¬Ã‚Â£Ã‚Â¨10Ãƒâ€�Ãƒâ€š31Ã‚ÂºÃƒâ€¦Ã‚Â£Ã‚Â©Ã‚Â£Ã‚Â¬Ã‚ÂºÃƒÂ³ÃƒË†ÃƒÂ½Ãƒï¿½ÃƒÂ®ÃƒÅ½Ã‚ÂªÃƒÆ’Ã‚Â¿Ãƒâ€žÃƒÂªÃ‚ÂµÃƒÅ¡1ÃƒÅ’ÃƒÂ¬Ã‚Â£Ã‚Â¨1Ãƒâ€�Ãƒâ€š1Ã‚ÂºÃƒâ€¦Ã‚Â£Ã‚Â©Ã‚ÂµÃ‚Â½357ÃƒÅ’ÃƒÂ¬Ã‚Â£Ã‚Â¨10Ãƒâ€�Ãƒâ€š22Ã‚ÂºÃƒâ€¦Ã‚Â£Ã‚Â©
	 * AuthorÃ‚Â£Ã‚ÂºHaifeng LI
	 * 
	 * 
	 * #AttentionÃ‚Â£Ã‚Âºthis code is only used for yunnongchang, not general#*/
	public static Data_ ReadForYunnongchang(String strin)
	{
		Data_ history = new Data_();
		
		history.histo_data_ = new double[20][366][7];
		
		try
		{
			File csv = new File("C:/Database/Vincent/" + strin +".csv.out");  //the input path?
			
			BufferedReader br = new BufferedReader( new FileReader (csv));
			
			
			double [][] read = new double[43111][2];
			
			
			
			//read until the last line
			int i = 0;
			
			while(br.ready())
			{
				if((i+1) % 21 == 1)
				{
					br.readLine();
					i++;
				} 
				else
				{
					String line = br.readLine();
					java.util.StringTokenizer st = new StringTokenizer(line, " ");
				
					int j = 0;
					while(st.hasMoreTokens())
					{
						read[i][j] = Double.parseDouble(st.nextToken());
						j++;
						//System.out.print(Double.parseDouble(st.nextToken()) + "\t");

						
					}
					i++;
				}
//				System.out.println();
			}
			
			br.close();
			//read finish
			
			
			/*
			int linecounter = 1;
			
			//stock "drought"
			for(int daycounter = 60; daycounter < 305; ++daycounter)
			{
				for(int yearcounter = 0; yearcounter < 20; ++yearcounter)
				{
					history.histo_data_[yearcounter][daycounter][0] = read[linecounter][1];
					linecounter++;
				}
				linecounter++;
			}
			
			//stock "flood"
			for(int daycounter = 60; daycounter < 305; ++daycounter)
			{
				for(int yearcounter = 0; yearcounter < 20; ++yearcounter)
				{
					history.histo_data_[yearcounter][daycounter][1] = read[linecounter][1];
					linecounter++;
				}
				linecounter++;
			}
			
			
			//stock "heat"
			for(int daycounter = 60; daycounter < 305; ++daycounter)
			{
				for(int yearcounter = 0; yearcounter < 20; ++yearcounter)
				{
					history.histo_data_[yearcounter][daycounter][2] = read[linecounter][1];
					linecounter++;
				}
				linecounter++;
			}

			//stock "frost"
			for(int daycounter = 60; daycounter < 305; ++daycounter)
			{
				for(int yearcounter = 0; yearcounter < 20; ++yearcounter)
				{
					history.histo_data_[yearcounter][daycounter][3] = read[linecounter][1];
					linecounter++;
				}
				linecounter++;
			}
			
			//stock "rain 3 days duration"
			for(int daycounter = 0; daycounter < 357; ++daycounter)
			{
				for(int yearcounter = 0; yearcounter < 20; ++yearcounter)
				{
					history.histo_data_[yearcounter][daycounter][4] = read[linecounter][1];
					linecounter++;
				}
				linecounter++;
			}
			
			//stock "rain 5 days duration"
			for(int daycounter = 0; daycounter < 357; ++daycounter)
			{
				for(int yearcounter = 0; yearcounter < 20; ++yearcounter)
				{
					history.histo_data_[yearcounter][daycounter][5] = read[linecounter][1];
					linecounter++;
				}
				linecounter++;
			}
			
			//stock "rain 7 days duration"
			for(int daycounter = 0; daycounter < 357; ++daycounter)
			{
				for(int yearcounter = 0; yearcounter < 20; ++yearcounter)
				{
					history.histo_data_[yearcounter][daycounter][6] = read[linecounter][1];
					linecounter++;
				}
				linecounter++;
			}
			
			//System.out.print(history.histo_data_[0][60][0]);
			
			*/
			
			int linecounter = 1;
			double [][] vecteur_drought = new double[245][20];
			//stock "drought"
			for(int daycounter = 60; daycounter < 305; ++daycounter)
			{
				for(int yearcounter = 0; yearcounter < 20; ++yearcounter)
				{
					vecteur_drought[daycounter-60][yearcounter] = read[linecounter][1];
					linecounter++;
				}
				Algorithme.quick_sort(vecteur_drought[daycounter-60], 0, 19);
				linecounter++;
			}
			
			
			
			
			double [][] vecteur_flood = new double[245][20];
			//stock "flood"
			for(int daycounter = 60; daycounter < 305; ++daycounter)
			{
				for(int yearcounter = 0; yearcounter < 20; ++yearcounter)
				{
					vecteur_flood[daycounter-60][yearcounter] = read[linecounter][1];
					linecounter++;
				}
				Algorithme.quick_sort(vecteur_flood[daycounter-60], 0, 19);
				linecounter++;
			}
			
			
			double [][] vecteur_heat = new double[245][20];
			//stock "heat"
			for(int daycounter = 60; daycounter < 305; ++daycounter)
			{
				for(int yearcounter = 0; yearcounter < 20; ++yearcounter)
				{
					vecteur_heat[daycounter-60][yearcounter] = read[linecounter][1];
					linecounter++;
				}
				Algorithme.quick_sort(vecteur_heat[daycounter-60], 0, 19);
				linecounter++;
			}

			try
			{
				FileWriter fw = new FileWriter("D:\\" + strin + "_drought.csv");
				String header = "Drought:\r\n";
				fw.write(header);

				int compter = 42064;
				for (int daycounter = 60; daycounter < 305; ++daycounter) 
				{
					StringBuffer str = new StringBuffer();
					if(vecteur_drought[daycounter-60][17] != vecteur_drought[daycounter-60][16])
					{
						str.append(compter + "," + (int)vecteur_drought[daycounter-60][17] + ",15%\r\n");
					}
					else if(vecteur_drought[daycounter-60][16] != vecteur_drought[daycounter-60][15])
					{
						str.append(compter + "," + (int)vecteur_drought[daycounter-60][16] + ",20%\r\n");
					}
					else if(vecteur_drought[daycounter-60][17] != vecteur_drought[daycounter-60][18])
					{
						str.append(compter + "," + (int)vecteur_drought[daycounter-60][18] + ",10%\r\n");
					}
					else if(vecteur_drought[daycounter-60][18] != vecteur_drought[daycounter-60][19])
					{
						str.append(compter + "," + (int)vecteur_drought[daycounter-60][19] + ",5%\r\n");
					}
					else
					{
						str.append(compter + ", " + ((int)vecteur_drought[daycounter-60][19] +1) + ",<5%\r\n");
					}
					fw.write(str.toString());
					fw.flush();
					compter++;
				}
				fw.close();
	
				FileWriter fw1 = new FileWriter("D:\\" + strin + "_flood.csv");
				String header1 = "Flood:\r\n";
				fw1.write(header1);

				compter = 42064;
				for (int daycounter = 60; daycounter < 305; ++daycounter) 
				{
					StringBuffer str = new StringBuffer();
					
					if((double)((int)(vecteur_flood[daycounter-60][17]/10) + 1 ) <= vecteur_flood[daycounter-60][18]/10)
					{
						str.append(compter + "," + ((int)(vecteur_flood[daycounter-60][17]/10) + 1) + ", 10% - 15%\r\n");
					}
					else if((double)((int)(vecteur_flood[daycounter-60][17]/10)) >= vecteur_flood[daycounter-60][16]/10)
					{
						str.append(compter + "," + (int)(vecteur_flood[daycounter-60][17]/10) + ", 15% - 20%\r\n");
					}
					else if((double)((int)(vecteur_flood[daycounter-60][17]/10) + 1 ) <= vecteur_flood[daycounter-60][19]/10)
					{
						str.append(compter + "," + (int)(vecteur_flood[daycounter-60][19]/10) + ", 5% - 10%\r\n");
					}
					else
					{
						str.append(compter + "," + ((int)(vecteur_flood[daycounter-60][19]/10) + 1) + ", < 5%\r\n");
					}
			
					fw1.write(str.toString());
					fw1.flush();
					compter++;
				}
				
				fw1.close();
				
				FileWriter fw2 = new FileWriter("D:/" + strin + "_heat.csv");
				String header2 = "Heat:\r\n";
				fw2.write(header2);
				compter = 42064;
				for (int daycounter = 60; daycounter < 305; ++daycounter) 
				{
					StringBuffer str = new StringBuffer();
					if((double)((int)(vecteur_heat[daycounter-60][17]/10) + 1 ) <= vecteur_heat[daycounter-60][18]/10)
					{
						str.append(compter + "," + ((int)(vecteur_heat[daycounter-60][17]/10) + 1) + ", 10% - 15%\r\n");
					}
					else if((double)((int)(vecteur_heat[daycounter-60][17]/10)) >= vecteur_heat[daycounter-60][16]/10)
					{
						str.append(compter + "," + (int)(vecteur_heat[daycounter-60][17]/10) + ", 15% - 20%\r\n");
					}
					else if((double)((int)(vecteur_heat[daycounter-60][17]/10) + 1 ) <= vecteur_heat[daycounter-60][19]/10)
					{
						str.append(compter + "," + (int)(vecteur_heat[daycounter-60][19]/10) + ", 5% - 10%\r\n");
					}
					else
					{
						str.append(compter + "," + ((int)(vecteur_heat[daycounter-60][19]/10) + 1) + ", < 5%\r\n");
					}
					fw2.write(str.toString());
					fw2.flush();
					compter++;
				}
				fw2.close();
				
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			//System.out.print(history.histo_data_[0][60][0]);
		
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
}



