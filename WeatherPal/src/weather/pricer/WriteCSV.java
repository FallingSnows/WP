package weather.pricer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class WriteCSV
{
	public static void WriteQiXiangJuResult(double[][][] matrix)
	{
		
        try
        {
        	
        	FileOutputStream out = new FileOutputStream("C:/Database/ResultOfQiXiangJu.txt");
        	for(int i = 0; i < matrix.length; ++i)
        	{
        		for(int j = 0; j < matrix[i].length; ++j)
        		{
        			for(int k = 0; k < matrix[i][j].length; ++k)
        			{
        				byte[] buff=new byte[]{};
        				String aa = Double.toString(matrix[i][j][k]) + ";";
        	            buff=aa.getBytes();
        	            out.write(buff,0,buff.length);
        			}
        			byte[] buff=new byte[]{};
        			String aa = "\r\n";
    	            buff=aa.getBytes();
    	            out.write(buff,0,buff.length);
        			
        		}
        	}		
        	
            
            out.close();
           
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


		
		
		return ;
	}
}