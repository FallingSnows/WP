package weather.pricer;

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
	
}
