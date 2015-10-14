package weather.pricer;

public class SimpleRiskSimulator
{
	public static void Simulator()
	{
		//calculate the AFU huminity risk
				//String[] name_list = {"鍖椾含", "骞垮窞", "鏉窞", "娴庡崡", "娣卞湷", "鐭冲搴�", "姝︽眽",	"閮戝窞", "涓婃捣"};
				double[] proba = {0.166666667,0.166666667,0.166666667,0.111111111,0.111111111,0.166666667,0.111111111,0.222222222,0.166666667}; 
				//double[] proba = {0.166666667,0.166666667,0.166666667,0.166666667,0.166666667,0.166666667,0.166666667,0.166666667,0.166666667}; 
				
				double[] result1 = {2480,	240,	440,	100,	250,	220,	220,	210,	840};
				double[] result2 = {-9424,	-912,	-1672,	-380,	-950,	-836,	-836,	-798,	-3192};
				
				
				int[][] name = new int[5000000][2];
				
				for (int i = 0; i < name.length; ++i)
				{
					double[] simulator = new double[9];
					name[i][0] = 0;
					name[i][1] = 0;
					
					for (int counter = 0; counter < simulator.length; ++counter)
					{
						if(Math.random() < proba[counter])
						{
							simulator[counter] = 1;
						}
						//System.out.println(simulator[counter]);
						//System.out.println(proba[counter]);
						
					}

					for (int counter = 0; counter < simulator.length; ++counter)
					{
						name[i][0]  = name[i][0] + (int)simulator[counter];
						
						double result = simulator[counter]  == 0 ? result1[counter]: result2[counter];
						name[i][1] = name[i][1] + (int)result;
					}
					
				}
				
				double[] probability = new double[10];
				double[] mean = new double[10];
				
				for(int counter = 0; counter < name.length; ++counter)
				{
					probability[name[counter][0]]++;
					mean[name[counter][0]] += name[counter][1];
				}
			
				
				for(int i = 0 ; i < mean.length; ++i)
				{
					System.out.println(probability[i]/name.length);
				}
				

		
		return;
	}
}