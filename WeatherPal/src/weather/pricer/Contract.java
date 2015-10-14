package weather.pricer;


class Contract_
{
	private int contract_type;
	private int contract_form;
	
	private int today;
	private int begin_day_index;
	private int end_day_index;
	private int begin_year_index;
	private int end_year_index;
	private double strike;
	
	public Contract_(int contract_type, int contract_form, int today, int begin_day_index, int end_day_index,
			int begin_year_index, int end_year_index, double strike) {
		this.contract_type = contract_type;
		this.contract_form = contract_form;
		this.today = today;
		this.begin_day_index = begin_day_index;
		this.end_day_index = end_day_index;
		this.begin_year_index = begin_year_index;
		this.end_year_index = end_year_index;
		this.strike = strike;
	}



	public int getContract_type() {
		return contract_type;
	}



	public void setContract_type(int contract_type) {
		this.contract_type = contract_type;
	}



	public int getContract_form() {
		return contract_form;
	}



	public void setContract_form(int contract_form) {
		this.contract_form = contract_form;
	}



	public int getToday() {
		return today;
	}



	public void setToday(int today) {
		this.today = today;
	}



	public int getBegin_day_index() {
		return begin_day_index;
	}



	public void setBegin_day_index(int begin_day_index) {
		this.begin_day_index = begin_day_index;
	}



	public int getEnd_day_index() {
		return end_day_index;
	}



	public void setEnd_day_index(int end_day_index) {
		this.end_day_index = end_day_index;
	}



	public int getBegin_year_index() {
		return begin_year_index;
	}



	public void setBegin_year_index(int begin_year_index) {
		this.begin_year_index = begin_year_index;
	}



	public int getEnd_year_index() {
		return end_year_index;
	}



	public void setEnd_year_index(int end_year_index) {
		this.end_year_index = end_year_index;
	}



	public double getStrike() {
		return strike;
	}



	public void setStrike(double strike) {
		this.strike = strike;
	}

	public double[] ExoticCalculate(double[][][] data)
	{
		double result[] = new double[end_year_index - begin_year_index + 1];
		if(contract_type == 1)
		{
			if(contract_form == 1)//continual exotic
			{
				double[] max_successive_exotic_days = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					max_successive_exotic_days[yearcounter] = 0;
					int a = 0;
					int b = 0;
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						if(data[yearcounter + daycounter/365][daycounter%365][19] <= 40)
						{
							a++;
						}
						else
						{
							b =b > a? b : a;
							a = 0;
						}
						b = b > a? b : a;
					}
					max_successive_exotic_days[yearcounter] = b;
				}			
				Algorithme.quick_sort(max_successive_exotic_days, 0, max_successive_exotic_days.length-1);
				result = max_successive_exotic_days;
				System.out.println("Felicitation: Continual exotic is done");
			}
			else
			{
				System.out.println("Error: This contract form is not designed!");
			}
		}
		else
		{
			System.out.println("Error: This contract type is not designed!");
		}
		
		return result;
	}
	


	public double[] EmpiricalProba(Data data)
	{
		double result[] = new double[end_year_index - begin_year_index + 1];
		
		if(contract_type == 1)//drought
		{
			if(contract_form == 1)//continual no ruin
			{
				double[] max_successive_no_rain_days = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					max_successive_no_rain_days[yearcounter] = 0;
					int a = 0;
					int b = 0;
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						if(data.histo_data_[yearcounter + daycounter/365][daycounter%365][0] <= 1)
						{
							a++;
						}
						else
						{
							b =b > a? b : a;
							a = 0;
						}
						b = b > a? b : a;
					}
					max_successive_no_rain_days[yearcounter] = b;
				}			
				Algorithme.quick_sort(max_successive_no_rain_days, 0, max_successive_no_rain_days.length-1);
				result = max_successive_no_rain_days;
				System.out.println("continual no ruin");
			}
			else if(contract_form == 2)//accumulated no rain
			{
				double[] accumulated_no_rain_days = new double[end_year_index - begin_year_index + 1];
				
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						if(data.histo_data_[yearcounter + daycounter/365][daycounter%365][0] <= 1)
						{
							accumulated_no_rain_days[yearcounter]++;
						}
					}
				}
				
				Algorithme.quick_sort(accumulated_no_rain_days, 0, accumulated_no_rain_days.length-1);
				result = accumulated_no_rain_days;
				
				System.out.println("accumulated no ruin");
			}
			else if(contract_form == 3)//ruin volume per year
			{
				double[] accumulative_year_rain_volume = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						accumulative_year_rain_volume[yearcounter] += data.histo_data_[yearcounter + daycounter/365][daycounter%365][0];
					}
				}
				
				Algorithme.quick_sort(accumulative_year_rain_volume, 0, accumulative_year_rain_volume.length-1);
				result = accumulative_year_rain_volume;
				System.out.println("ruin volume per year");
			}
			else if(contract_form == 4)//no use
			{
				double[] status = new double[7];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						// donot use this code, thx
						
						if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] >= 32.0 && data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] <= 34.0)
						{
							status[0]++;
						}
						
						else
						{}
					}
				}
				
				
				for(int i = 0; i < status.length; ++i )
				{
					status[i] /= (double)(end_year_index - begin_year_index + 1) * (end_day_index - begin_day_index + 1);
				}
				
				result = status;
				System.out.println("Tidu high tempreture is done");
			}
			else
			{
				System.out.print("This form is not designed, please check the configuration!");
			}
		}
		else if(contract_type == 2)//flood
		{
			if(contract_form == 1)
			{
				double[] max_successive_flood_days = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					max_successive_flood_days[yearcounter] = 0;
					int a = 0;
					int b = 0;
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						if(data.histo_data_[yearcounter+daycounter/365][daycounter%365][0] >= strike)
						{
							a++;
						}
						else
						{
							b =b > a? b : a;
							a = 0;
						}
						b = b > a? b : a;
					}
					max_successive_flood_days[yearcounter] = b;
				}		
				Algorithme.quick_sort(max_successive_flood_days, 0, max_successive_flood_days.length-1);
				result = max_successive_flood_days;
				System.out.println("successive_flood");
			}
			else if(contract_form == 2)
			{
				double[] accumulated_flood_days = new double[end_year_index - begin_year_index + 1];
				
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						if(data.histo_data_[yearcounter + daycounter/365][daycounter%365][0] >= strike)
						{
							accumulated_flood_days[yearcounter]++;
						}
					}
				}
				
				Algorithme.quick_sort(accumulated_flood_days, 0, accumulated_flood_days.length-1);
				System.out.println("accumulated flood");
				result = accumulated_flood_days;
			}
			else if(contract_form == 3)
			{
				double[] accumulative_year_rain_volume = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						accumulative_year_rain_volume[yearcounter] += data.histo_data_[yearcounter + daycounter/365][daycounter%365][0];
					}
				}
				
				Algorithme.quick_sort(accumulative_year_rain_volume, 0, accumulative_year_rain_volume.length-1);
				result = accumulative_year_rain_volume;
				System.out.println("accumulative year ruin volume");
			}
			else if(contract_form == 4)//gradient
			{
				double[] status = new double[3];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
					
						if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][0] >= 100 && data.histo_data_[yearcounter + daycounter/365][daycounter%365][0] <= 249 )
						{
							status[0]++;
						}
						else if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][0] >= 250 && data.histo_data_[yearcounter + daycounter/365][daycounter%365][0] <= 499)
						{
							status[1]++;
						}
						else if(data.histo_data_[yearcounter + daycounter/365][daycounter%365][0] >= 500)
						{
							status[2]++;
						}
						else
						{}
					}
				}
				
				
				for(int i = 0; i < status.length; ++i )
				{
					status[i] /= (double)(end_year_index - begin_year_index + 1) * (end_day_index - begin_day_index + 1);
				}
				
				result = status;
				System.out.println("Gradient HT is done");
			}
			else if(contract_form == 5)//continual two dys
			{
				double[] status = new double[end_year_index - begin_year_index + 1];
				
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index - 1; ++ daycounter)
					{
					
						if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][0] >= strike && data.histo_data_[yearcounter + (daycounter+1)/365][(daycounter+1)%365][0] >= strike )
						{
							status[yearcounter]++;
						}
					}
				}
				
				result = status;
				System.out.println("continual two dys rain is done");
			}
			else if(contract_form == 6)//continual three days
			{
				double[] status = new double[end_year_index - begin_year_index + 1];
				
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index - 1; ++ daycounter)
					{
					
						if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][0] >= strike
						 && data.histo_data_[yearcounter + (daycounter+1)/365][(daycounter+1)%365][0] >= strike 
						 && data.histo_data_[yearcounter + (daycounter+2)/365][(daycounter+2)%365][0] >= strike )
						{
							status[yearcounter]++;
						}
					}
				}
				
				result = status;
				System.out.println("continual two dys rain is done");
			}
			else
			{
				System.out.print("This form is not designed, please check the configuration!");
			}
		}
		else if(contract_type == 3)//heat
		{
			if(contract_form == 1) //continual
			{
				double[] max_continuous_hot_days = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					max_continuous_hot_days[yearcounter] = 0;
					int a = 0;
					int b = 0;
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						if(data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] >= strike)
						{
							a++;
						}
						else
						{
							b = b > a? b : a;
							a = 0;
						}
					}
					b = b > a? b : a;
					max_continuous_hot_days[yearcounter] = b;
				}			
				
				Algorithme.quick_sort(max_continuous_hot_days, 0, max_continuous_hot_days.length-1);
				result = max_continuous_hot_days;
				System.out.println("max_continuous_hot_days");
			}
			else if(contract_form == 2)//commulate
			{
				double[] commulate_year_hot_days = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						if(data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] >= strike)
						{
							commulate_year_hot_days[yearcounter]++;
						}
					}
				}
				
				Algorithme.quick_sort(commulate_year_hot_days, 0, commulate_year_hot_days.length-1);
				result = commulate_year_hot_days;
				System.out.println("commulate year hot days");
			}
			else if(contract_form == 3)//average
			{
				double[] average_high_tempreture = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						average_high_tempreture[yearcounter] += data.histo_data_[yearcounter + daycounter/365][daycounter%365][1];
					}
					
					average_high_tempreture[yearcounter] /= (double)(end_day_index - begin_day_index + 1);
				}
				
				Algorithme.quick_sort(average_high_tempreture, 0, average_high_tempreture.length-1);
				result = average_high_tempreture;
				System.out.println("average_high_tempreture");
			}
			else if(contract_form == 4)//gradient HT
			{
				double[] status = new double[4];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
					
						if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] >= 320 && data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] <= 349)
						{
							status[0]++;
						}
						else if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] >= 350 && data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] <= 379)
						{
							status[1]++;
						}
						else if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] >= 380 && data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] <= 399)
						{
							status[2]++;
						}
						else if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] > 400)
						{
							status[3]++;
						}
						else
						{}
					}
				}
				
				
				for(int i = 0; i < status.length; ++i )
				{
					status[i] /= (double)(end_year_index - begin_year_index + 1) * (end_day_index - begin_day_index + 1);
				}
				
				result = status;
				System.out.println("Tidu high tempreture is done");
			}
			else if(contract_form == 5)//continual two days
			{
				double[] status = new double[end_year_index - begin_year_index + 1];
				
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index - 1; ++ daycounter)
					{
					
						if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] >= strike && data.histo_data_[yearcounter + (daycounter+1)/365][(daycounter+1)%365][1] >= strike )
						{
							status[yearcounter]++;
						}
					}
				}
				
				result = status;
				System.out.println("continual two days HT is done");
			}
			else if(contract_form == 6)//continual three days
			{
				double[] status = new double[end_year_index - begin_year_index + 1];
				
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index - 2; ++daycounter)
					{
					
						if(data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] >= strike 
						&& data.histo_data_[yearcounter + (daycounter+1)/365][(daycounter+1)%365][1] >= strike 
						&& data.histo_data_[yearcounter + (daycounter+2)/365][(daycounter+2)%365][1] >= strike )
						{
							status[yearcounter]++;
						}
					}
				}
				
				result = status;
				System.out.println("continual three days HT rain is done");
			}
			else
			{
				System.out.print("This form is not designed");
			}
		}
		else if(contract_type == 4)//frost
		{
			if(contract_form == 1)
			{
				double[] max_continuous_cold_days = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					max_continuous_cold_days[yearcounter] = 0;
					int a = 0;
					int b = 0;
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						if(data.histo_data_[yearcounter + daycounter/365][daycounter%365][2] <= strike)
						{
							a++;
						}
						else
						{
							b = b > a? b : a;
							a = 0;
						}
					}
					b = b > a? b : a;
					max_continuous_cold_days[yearcounter] = b;
				}			
				
				Algorithme.quick_sort(max_continuous_cold_days, 0, max_continuous_cold_days.length-1);
				result = max_continuous_cold_days;
				System.out.println("continuous cold days");
			}
			else if(contract_form == 2)
			{
				double[] commulate_year_cold_days = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						if(data.histo_data_[yearcounter + daycounter/365][daycounter%365][2] <= strike)
						{
							commulate_year_cold_days[yearcounter]++;
						}
					}
				}
				
				Algorithme.quick_sort(commulate_year_cold_days, 0, commulate_year_cold_days.length-1);
				result = commulate_year_cold_days;
				System.out.println("commulate year cold days");
				
			}
			else if(contract_form == 3)
			{
				double[] average_low_tempreture = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						average_low_tempreture[yearcounter] += data.histo_data_[yearcounter + daycounter/365][daycounter%365][2];
					}
					
					average_low_tempreture[yearcounter] /= (double)(end_day_index - begin_day_index + 1);
				}
				
				Algorithme.quick_sort(average_low_tempreture, 0, average_low_tempreture.length-1);
				result = average_low_tempreture;
				System.out.println("average low tempreture");
			}
			else if(contract_form == 4)//gradient LT
			{
				double[] status = new double[4];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
					
						if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][2] <= 210 && data.histo_data_[yearcounter + daycounter/365][daycounter%365][2] >= 170)
						{
							status[0]++;
						}
						else if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][2] < 170 && data.histo_data_[yearcounter + daycounter/365][daycounter%365][2] >= 120)
						{
							status[1]++;
						}
						else if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][2] < 120 && data.histo_data_[yearcounter + daycounter/365][daycounter%365][2] >= 50)
						{
							status[2]++;
						}
						else if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][2] < 50)
						{
							status[3]++;
						}
						else
						{}
					}
				}
				
				
				for(int i = 0; i < status.length; ++i )
				{
					status[i] /= (double)(end_year_index - begin_year_index + 1) * (end_day_index - begin_day_index + 1);
				}
				
				result = status;
				//System.out.println("Gradient LT is done");
			}
			else
			{
				System.out.print("This form is not designed, please check the configuration!");
			}
		}
		if(contract_type == 5)//exotic  , prepare to rewrite
		{
			if(contract_form == 1)//continual
			{
				double[] max_successive_no_rain_days = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					max_successive_no_rain_days[yearcounter] = 0;
					int a = 0;
					int b = 0;
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						if(data.histo_data_[yearcounter + daycounter/365][daycounter%365][0] >= 10 && data.histo_data_[yearcounter + daycounter/365][daycounter%365][2] <= -40)
						{
							a++;
						}
						else
						{
							b =b > a? b : a;
							a = 0;
						}
						b = b > a? b : a;
					}
					max_successive_no_rain_days[yearcounter] = b;
				}			
				Algorithme.quick_sort(max_successive_no_rain_days, 0, max_successive_no_rain_days.length-1);
				result = max_successive_no_rain_days;
				System.out.println("continual no ruin");
			}
			else if(contract_form == 2)//accumulated no rain
			{
				double[] accumulated_no_rain_days = new double[end_year_index - begin_year_index + 1];
				
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						if(data.histo_data_[yearcounter + daycounter/365][daycounter%365][0] >= 10 && data.histo_data_[yearcounter + daycounter/365][daycounter%365][2] <= -40)
						{
							accumulated_no_rain_days[yearcounter]++;
						}
					}
				}
				
				Algorithme.quick_sort(accumulated_no_rain_days, 0, accumulated_no_rain_days.length-1);
				result = accumulated_no_rain_days;
				
				System.out.println("accumulated no ruin");
			}
			else if(contract_form == 3)//ruin volume per year
			{
				double[] accumulative_year_rain_volume = new double[end_year_index - begin_year_index + 1];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						accumulative_year_rain_volume[yearcounter] += data.histo_data_[yearcounter + daycounter/365][daycounter%365][0];
					}
				}
				
				Algorithme.quick_sort(accumulative_year_rain_volume, 0, accumulative_year_rain_volume.length-1);
				result = accumulative_year_rain_volume;
				System.out.println("ruin volume per year");
			}
			else if(contract_form == 4)//no use
			{
				double[] status = new double[7];
				for(int yearcounter = begin_year_index; yearcounter <= end_year_index; ++ yearcounter)
				{
					for(int daycounter = begin_day_index; daycounter <= end_day_index; ++ daycounter)
					{
						// donot use this code, thx
						
						if( data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] >= 32.0 && data.histo_data_[yearcounter + daycounter/365][daycounter%365][1] <= 34.0)
						{
							status[0]++;
						}
						
						else
						{}
					}
				}
				
				
				for(int i = 0; i < status.length; ++i )
				{
					status[i] /= (double)(end_year_index - begin_year_index + 1) * (end_day_index - begin_day_index + 1);
				}
				
				result = status;
				System.out.println("Tidu high tempreture is done");
			}
			else
			{
				System.out.print("This form is not designed, please check the configuration!");
			}
		}
		else
		{
			System.out.print("This type is not designed, please check the configuration!");
		}
		/*
		for(int index = 0; index < result.length; ++index)
		{
			System.out.print(+ result[index] + "\n");
		}
		*/
		/*
		double sum = 0;
		for(int index = 0; index < result.length; ++index)
		{
			sum += result[index];
		}
		
		strike = sum / (double)result.length;
		
		System.out.print("average: " + strike + "\n");
		/*
		double Res[][] = new double [2][20];
		
		for(int i = 0; i < 20; ++i)
		{
			Res[0][i] = (0.05 + 0.1 * i) * strike;
			Res[1][i] = 1 - Algorithme.find_index_interval(result, Res[0][i]) / (double) 23;
		}
		*/
		
		return result;
	}
}
