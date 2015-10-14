package weather.pricer;


public class WeatherPal
{
	public static void main(String[] args)
	{
		
		SimpleRiskSimulator.Simulator();		
		
		
		/*
		 * calculate the risk
		 * 
		 */
		
		/*
		String[] strin= {"ANYANG","BINZHOU","DEZHOU","DONGYING","HEBI","HEZE","JIAOZUO","JINAN","JINING","JIYUAN",
				"KAIFENG","LIAOCHENG","LINYI","LUOHE","LUOYANG","NANYANG","PINGDINGSHAN","PUYANG","QINGDAO","RIZHAO",
				"SHANGQIU","TAIAN","WEIFANG","WEIHAI","XINXIANG","XINYANG","XUCHANG","YANTAI","ZAOZHUANG","ZHENGZHOU",
				"ZHOUKOU","ZHUMADIAN","ZIBO"};
		
		*/
		/*
		String[] strin = {"LIAOCHENG"};
		
		double [][] result = new double [strin.length][18];
		
		for(int p = 0; p < strin.length; ++p)
		{
			Data history =	ReadCSV.Read(strin[p]);
			double [] rule = Solver.Risk(strin[p]);
		
			System.out.println(p);
			
			double [][] flag = new double [18][61]; 
			
			for(int i = 0; i < 18; ++i)
			{
				for(int j = 0; j < 61; ++j)
				{
					flag[i][j] = 0;
				}
			}
			
			
			
			
			for(int day = 7; day < 61; ++day)
			{
				double rule_ = rule[day] - 1;
			
				for(int year = 0; year < 18; ++year)
				{
					flag[year][day] = 0;
					for(int index = 213 + day; index < 304 ; ++ index)
					{
						if(history.histo_data_[year][index][1] >= rule_ && history.histo_data_[year][index + 1][1] >= rule_)
						{
							flag[year][day] = 1;
						}
					}
				}
			}
			
			
			
			for(int year = 0; year < 18; ++year)
			{
				for(int day = 0; day < flag[year].length; ++day)
				{
					result[p][year] += flag[year][day];
				}
			}
			
		}
		
		
		for(int p = 0; p < strin.length; ++p)
		{
			for(int year = 0; year < 18; ++year)
			{
				System.out.print(result[p][year] + "\t");
			}
			System.out.println();
		}
		
		
		
		*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
		
//		Config conf = ReadCSV.Read();
	
//		conf.city_name = "MAOMING";
		
		
		
		
		
		
		/*calculate the contract
		 * 
		 * 
		 * 
		 * 
		 */
		/*
		String[] strin= {"BEIJING", "TIANJIN", "SHANGHAI", "CHONGQING", "SHIJIAZHUANG", "TANGSHAN", "JINAN", "QINGDAO", "YANTAI", "SHENYANG", 
				 "HARBIN", "NANJING", "CHANGZHOU",  "HANGZHOU", "NINGBO", "WENZHOU", "XIAN", "XIANYANG", 
				"TAIYUAN", "DATONG", "FUZHOU", "XIAMEN", "GUANGZHOU", "SHENZHEN", "ZHENGZHOU", 
				"KAIFENG", "LUOYANG", "CHENGDU", "MIANYANG", "CHANGSHA", "ZHUZHOU", "WUHAN", "XIANGTAN","HUANGSHI", 
				"ZHUHAI", "HEFEI", "WUHU", "NANCHANG","HAIKOU", "SANYA", "KUNMING", "QUANZHOU",
				"QUJING", "GUIYANG", "ZUNYI", "LANZHOU", "XINING", "YINCHUAN", "BAOTOU", "LASA", "NANNING", 
				"LIUZHOU" };
		*/
		/*
		String[] strin = {  "BEIJING", "CHENGDU", "CHONGQING", "GUANGZHOU", "HAIKOU", "HANGZHOU", "HARBIN", 
							"NANJING", "SHANGHAI", "SHENYANG", "SHENZHEN", "XIAMEN"};
							
		String[] strin = {  "BANGKOK", "PARIS", "LONDON", "BERLIN", "LOSANGELES", "MALE", "NEWYORK", 
							"SINGAPORE", "TOKYO"};
		*/
		
		//String[] strin = {"BEIJING", "GUANGZHOU", "HANGZHOU", "JINAN", "SHENZHEN", "SHIJIAZHUANG", "WUHAN", "ZHENGZHOU", "SHANGHAI"};
		String[] strin = {};
		//String[] strin = {"China/WEIFANG"};
		
		//"NANNING", "CHONGQING", "NANCHANG", "SHANGHAI", "WUXI", "SUZHOU", "NINGBO", 
			
			/*{ "BEIJING", "SHANGHAI", "CHONGQING", "HARBIN", "CHANGCHUN", "SHENYANG", "HUHHOT","SHENZHEN",
				 		  "BAOTOU", "SHIJIAZHUANG", "TANGSHAN", "TAIYUAN", "XIAN", "JINAN", "QINGDAO", "WULUMUQI", "LANZHOU",
				 		  "ZHENGZHOU", "LUOYANG", "NANJING", "WUXI", "SUZHOU", "WUHAN", "HANGZHOU", "NINGBO",  "HEFEI",
				 		  "FUZHOU", "XIAMEN", "NANCHANG", "CHANGSHA", "CHENGDU", "GUANGZHOU", "SHENZHEN", "DONGGUAN", "KUNMING", "NANNING"};  //, "MACAU"
		*/
		// "YANTAI"
		
		
		//   "HUHEHOT","XIANGFAN", "YICHANG", "JIUJIANG",  "GANZHOU",  "SUZHOU", "DALIAN","WUXI","ZIGONG",, "WULUMUQI"
		//"YAAN","KURLE","WENCHANG","HEZHOU","WEIFANG","ZHOUKOU","BEIJING","GUANGZHOU","SHISHOU","HARBIN"
		//double[] strike = {33.0, 34.0, 36.0, 36.0, 34.0, 32.0, 35.0, 34.0,29.0};
		
		for(int i = 0; i < strin.length; ++i)
		{
			
			double[][][] history =	ReadCSV.Standard_Read(strin[i]);
			
			Contract_ contract1 = new Contract_(1, 1, 0, 282, 341, 0, 17, 100);

			System.out.println(strin[i]);
			
			double[] res = contract1.ExoticCalculate(history); 	
			
			/*
			Data history = ReadCSV.Read(strin[i]);
			
			Contract_ contract1 = new Contract_(2, 2, 0, 304, 544, 0, 16, 100);

			System.out.println(strin[i]);
			
			double[] res = contract1.EmpiricalProba(history); 	
			*/
			
			//System.out.println(strin[i] + "     " + (res[0] + res[1] + res[2] + res[3]));
			
			
			for(int j = 0; j < res.length; ++j)
			{
				System.out.println(res[j]);
			}
			
			System.out.println();
			//System.out.println("##################" + strin[i] + "##################"); 
			
			
			/*
			double result = 0;
			
			for(int j = 0; j < res.length; ++j)
			{
				result += res[j];
			}
			
			System.out.println(result + "              " + result/270.0);
			*/
			/*
			if(max > mean)
			{
				System.out.println(strin[i] + "  "+ max + " + max");
				max = mean;
			}
			 */		
			
			
		}
		
		//System.out.println(temp + "temp");
		
		/*
		double[][][] res = new double [4][3][];
		
		for (int IndexOfType = 1; IndexOfType < 5; ++IndexOfType)
		{
			for(int IndexOfForm = 1; IndexOfForm < 4; ++ IndexOfForm)
			{	
				Contract_ contract1 = new Contract_(IndexOfType, IndexOfForm, 0, 236, 242, 0, 17, 10.0);
				res[IndexOfType-1][IndexOfForm-1] = contract1.EmpiricalProba(history);
			}
		}
		WriteCSV.WriteQiXiangJuResult(res);
		
		
		
		Solver.QiXiangJuToResult();
		
		*/
		 
		/*
		Data history =	ReadCSV.Read("TOKYO");

		
		Contract_ contract1 = new Contract_(5, 1, 0, 243, 252, 0, 17, 10.0);
		double[] res = contract1.EmpiricalProba(history);
		
		Contract_ contract2 = new Contract_(5, 2, 0, 243, 252, 0, 17, 10.0);
		double[] res1 = contract2.EmpiricalProba(history);
		 */	
		
		
		
		/*
		ReadCSV.Read_();
		
		double [][] read = ReadCSV.Read_();
		int [][] test = new int[read.length][31];
		int [][] test1 = new int[read.length][36];
		int [][] test2 = new int[read.length][41];
		
		for(int i = 0; i < 30; ++i)
		{
			for(int j = 0; j < 30; ++j)
			{
				test[i][j] = (int)read[i][j];
			}
			test[i][30] = -99;
			
			for(int j = 0; j < 35; ++j)
			{
				test1[i][j] = (int)read[i][j];
			}
			test1[i][35] = -99;
				
			
			for(int j = 0; j < 40; ++j)
			{
				test2[i][j] = (int)read[i][j];
			}
			test2[i][40] = -99;
			
			Algorithme.quick_sort(test[i], 0, 30);
			
			for(int j = 0; j < 30; ++j)
			{
				if(test[i][j] != test[i][j+1])
				{
					System.out.println("the data is: " +  test[i][j] + " and the proba is:" + (j + 1) / (double)(30));
				}
			}	
			
			System.out.println("##############" +  (i + 1));
			
			Algorithme.quick_sort(test1[i], 0, 35);
			for(int j = 0; j < 35; ++j)
			{
				if(test1[i][j] != test1[i][j+1])
				{
					System.out.println("the data is: " +  test1[i][j] + " and the proba is:" + (j + 1) / (double)(35));
				}
			}	
			
			System.out.println("##############" +  (i + 1));
			
			Algorithme.quick_sort(test2[i], 0, 40);
			
			for(int j = 0; j < 40; ++j)
			{
				if(test2[i][j] != test2[i][j+1])
				{
					System.out.println("the data is: " +  test2[i][j] + " and the proba is:" + (j + 1) / (double)(40));
				}
			}	
			
			System.out.println("##############" + (i + 1));
		
		
			System.out.println("****************");
		
		}
		
		*/
		
		
		
		/*
		String[] strin = {"HARBIN"};
		for(int i = 0; i < strin.length; ++i)
		{
			Data history =	ReadCSV.Read(strin[i]);
		
			Contract_ contract1 = new Contract_(3, 1, 0, 60, 121, 0, 17, 10.0);
			
			double [][] res = contract1.EmpiricalProba(history);
		}
	
		
		//check OK!!!
//		System.out.print(history.histo_data[23][365][2]);
		
			
		/*
		String[] strin = {"ANHUI-ANQING","ANHUI-CHUZHOU","ANHUI-FENGBU","ANHUI-FUYANG","ANHUI-HAOZHOU"
				,"ANHUI-HEFEI","ANHUI-HUANGSHAN","ANHUI-LIUAN","ANHUI-SUZHOU","HENAN-ANYANG"
				,"HENAN-KAIFENG","HENAN-NANYANG","HENAN-SANMENXIA","HENAN-SHANGQIU","HENAN-XINXIANG"
				,"HENAN-XINYANG","HENAN-XUCHANG","HENAN-ZHENGZHOU","HENAN-ZHUMADIAN","HUNAN-BINZHOU"
				,"HUNAN-CHANGDE","HUNAN-HENGYANG","HUNAN-JISHOU","HUNAN-SHAOYANG","HUNAN-YONGZHOU"
				,"HUNAN-YUEYANG","HUNAN-ZHUZHOU","JIANGSU-CHANGZHOU","JIANGSU-NANJING","JIANGSU-NANTONG"
				,"JIANGSU-XUZHOU"
				,"SHANDONG-JINAN","SHANDONG-RIZHAO","SHANDONG-WEIFANG"
				,"SHANDONG-WEIHAI"};
		
		for(int i = 0; i < strin.length; ++i)
		{
			ReadCSV.ReadForYunnongchang(strin[i]);
		}
		*/
		
		return ;
	}
}
