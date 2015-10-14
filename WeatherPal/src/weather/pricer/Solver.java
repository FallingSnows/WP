package weather.pricer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Solver
{
	public static void QiXiangJuToResult()
	{
		double [][] read = new double[12][25];
		
		for(int i = 0; i < read.length; ++i)
		{
			for(int j = 0; j < read[i].length; ++j)
			{
				read[i][j] = 0.00001;
			}
		}
		
		try
		{
			File csv = new File("C:/Database/ResultOfQiXiangJu.txt");  //the input path?
			
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
					read[i][j] = Double.parseDouble(st.nextToken());
					j++;
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
				
		for(int i = 0; i < 12; ++i)
		{
			double sum = 0;
			int position = 0;
			
			for(int j = 0; j < read[i].length-1; ++j)
			{
				sum += read[i][j];
				if(read[i][j] != read[i][j + 1])
				{
					position = j;
				}
				
			}
			
			
			for(int j = 0; j < read[i].length-1; ++j)
			{
				
				if(read[i][j] != read[i][j + 1])
				{
					System.out.println("data is; " + read[i][j] + "; the proba is ; " +  (j + 1) / (double)(position + 1));
				}
			}
			
			System.out.println("the average is; " + sum / (double)(position + 1));
		}
		
		return ;
	}

	public static double[] Risk(String strin)
	{
		
		double [] read = new double [61];
		
		try
		{
			File csv = new File("C:/Database/YUN_Rule/" + strin + ".csv");  //the input path?
			
			BufferedReader br = new BufferedReader( new FileReader (csv));
			
			//read until the last line
			int i = 0;
			while(br.ready())
			{
				String line = br.readLine();
				java.util.StringTokenizer st = new StringTokenizer(line, ";||,");
				
				st.nextToken();
				while(st.hasMoreTokens())
				{
					read[i] = Double.parseDouble(st.nextToken());
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

 
	
}
