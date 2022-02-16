/**
* @author Venkatesh Yaganti (1223441505)
* @version 1.8
* @since 1.5
*/
import java.lang.Math.*;
import java.util.*;
import java.io.*;
import java.text.*;
public class TSP
{
	/** Declaring First class named Assignment1
  	* Creating Arraylist of Strings named Token
  	*/
	static int k=0;
	static int str[];
	static double[][] dim_mat;
	static int intdimension;
	static String[] X;
	static String[] Y;
	/**Decalring a function symmetric_data for reading the input file. In this method, input file is converted into distance matrix.
	* And calls another method tsp() for minimum cost for distancce matrix.
	*/
	public static void symmetric_data(String file_name) throws Exception
	{
		String text="";
		File f = new File("/Users/user/Documents/Java Programs/"+file_name);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String s;
		String[] dimension = new String[2];
		String[] mat = new String[3];
		ArrayList<String> xlist= new ArrayList<String>();
		ArrayList<String> ylist= new ArrayList<String>();
		while((s=br.readLine())!=null)
		{
			text = text + s;
			String array_of_strings[] = s.split(" ",1);
			for(String a: array_of_strings)
			{
				if(a.contains("DIMENSION"))
				{
					dimension = a.split(" ");
				}
				mat = a.split(" ");
				if(isNumeric(mat[0]))
				{
					xlist.add(mat[1]);
					ylist.add(mat[2]);
				}
			}
		}
		intdimension = Integer.parseInt(dimension[dimension.length-1]);
		X = new String[intdimension];
		Y = new String[intdimension];
		dim_mat = new double[intdimension][intdimension];
		double xp,yp;
		for(int i=0;i<xlist.size();i++)
		{
			X[i] = xlist.get(i);
			Y[i] = ylist.get(i);
		}
		for(int a=0;a<X.length;a++)
		{
			for(int b=0;b<Y.length;b++)
			{
				xp = Math.pow(Double.parseDouble(X[a])-Double.parseDouble(X[b]),2);
				yp = Math.pow(Double.parseDouble(Y[a])-Double.parseDouble(Y[b]),2);
				dim_mat[a][b] = Math.sqrt(xp+yp);
				DecimalFormat df = new DecimalFormat("#.#####");      
				dim_mat[a][b] = Double.valueOf(df.format(dim_mat[a][b]));
			}
		}
		boolean[] v = new boolean[Integer.valueOf(dimension[1])];
		v[0] = true;
		double ans = Double.MAX_VALUE;
		System.out.println(tsp(dim_mat,v,0,Integer.valueOf(dimension[1]),1,0,ans));
	}
	/**
	*Decalring a function asymetric_data for reading the input file. In this method, input file is converted into distance matrix.
	* And calls another method tspasy() for minimum cost for distancce matrix.
	*/
	static int[][] dis;
	public static void asymetric_data(String fname) throws Exception
	{
		String text="";
		File f = new File("/Users/user/Documents/Java Programs/"+fname);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String s;
		String[] dimension = new String[2];
		String[] mat;
		ArrayList<Integer> indis  = new ArrayList<Integer>();
		while((s=br.readLine())!=null)
			{
				text = text + s;
				String array_of_strings[] = s.split(" ",1);
				for(String a: array_of_strings)
				{
					if(a.startsWith("DIMENSION"))
					{
						dimension = a.split(" ");		
						dis = new int[Integer.valueOf(dimension[1])][Integer.valueOf(dimension[1])];
					}
					mat = a.split(" ");
					for(int i=0;i<mat.length;i++)
					{
						if(isNumeric(mat[i]))
						{
							indis.add(Integer.valueOf(mat[i]));
						}
						else if(mat[i] == " ")
						{	continue;	}
					}
				}
			}
		int insize = 1;
		if(insize<indis.size())
		{
			for(int i=0;i<Integer.valueOf(dimension[1]);i++)
			{
				for(int j=0;j<Integer.valueOf(dimension[1]);j++)
				{
					if(i==j)
						dis[i][j] = 0;
					else
						dis[i][j] = indis.get(insize);
						insize++;
				}
			}
		}
		boolean[] v = new boolean[Integer.valueOf(dimension[1])];
		v[0] = true;
		int ans = Integer.MAX_VALUE;
		System.out.println(tspasy(dis,v,0,Integer.valueOf(dimension[1]),1,0,ans));
	}
	/**Decalring a function tspasy() for finding the minimumcost.
	*/
	static int tspasy(int[][] graph, boolean[] v,int currPos, int n,int count, int cost, int ans)
	{
		if (count == n && graph[currPos][0] > 0)
		{
			ans = Math.min(ans, cost + graph[currPos][0]);
			return ans;
		}
		for (int i = 0; i < n; i++)
		{
			if (v[i] == false && graph[currPos][i] > 0)
			{
				v[i] = true;
				ans = tspasy(graph, v, i, n, count + 1, cost + graph[currPos][i], ans);
				v[i] = false;
			}
		}
		return ans;
	}
	/**Decalring a function tsp() for finding the minimumcost.
	*/
	static double tsp(double[][] graph, boolean[] v,int currPos, int n,double count, double cost1, double ans)
	{
		if (count == n && graph[currPos][0] > 0)
		{
			ans = Math.min(ans, cost1 + graph[currPos][0]);
			return ans;
		}
		for (int i = 0; i < n; i++)
		{
			if (v[i] == false && graph[currPos][i] > 0)
			{
				v[i] = true;
				ans = tsp(graph, v, i, n, count + 1, cost1 + graph[currPos][i], ans);
				v[i] = false;
			}
		}
		return ans;
	}
	/**Decalring a function isNumeric() for finding whether passed argument is a number or not.
	*/	
	public static boolean isNumeric(String str) 
	{
	        if (str == null || str.length() == 0) 
		{
        	    return false;
	        }	
        	try 
		{
	            Double.parseDouble(str);
        	    return true;
	        } 
		catch (NumberFormatException e) 
		{
	            return false;
        	}
	}
	/** Declaring Main class and reading the input file
  	* and passes as a parameter to functions
	*/
	public static void main(String[] args) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the type of Data (symetric or asymetric, all lowercase)");
		String type_of_data = sc.nextLine();
		if(type_of_data.equals("symetric"))
		{
			System.out.println("Please enter the name of file");
			String data1 = sc.nextLine();
			symmetric_data(data1);
		}
		else if(type_of_data.equals("asymetric"))
		{
			System.out.println("Please enter the name of file");
			String data2 = sc.nextLine();
			asymetric_data(data2);
		}
	}
}