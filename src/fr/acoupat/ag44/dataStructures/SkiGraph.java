/*
 * Author : Antoine Coupat  Date : 25/11/2015
 * File Name : SkiGraph.java
 * Project : Ski Resort (UTBM : AG44)   
 * 
 * Description : Class representing a Graph composed by
 * 				 several StationPoints and Routes.
 */
package fr.acoupat.ag44.dataStructures;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SkiGraph
{
	private ArrayList<StationPoint> pointList;
	private ArrayList<Route> routeList;

	private int order;
	private FileInputStream fis;
	private InputStreamReader isr; 
	private BufferedReader br;

	
	/*
	 * Constructor, it creates the graph corresponding 
	 * to the text file dataSki.txt by adding the right 
	 * number of StationPoints and Routes with their
	 * attributes to the graph.
	 */
	public SkiGraph()
	{
		File file = new File("src/dataSki.txt");
		try 
		{

			fis  = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br  =new BufferedReader(isr);
			
			String line;
			line=br.readLine();
			
			//Initialisation of the list of points
			order = Integer.parseInt(line);
			pointList = new ArrayList<StationPoint>(order);
			//System.out.println("**** list of points *****");
			for(int i=0;i<order;++i)
			{
				line=br.readLine();
				String[] split = line.split("	");
				
				pointList.add(new StationPoint(split[1],Integer.parseInt(split[2]),Integer.parseInt(split[0]),order));//adding the right number of vertices to the list
			//	System.out.println(pointList.get(i));
			}
			
			line=br.readLine();
			
			//Initialisation of the liste of Routes
			int nbRoute = Integer.parseInt(line);
			routeList = new ArrayList<Route>(nbRoute);
			//System.out.println("**** list of routes *****");
			for(int i=0;i<nbRoute;++i)
			{
				line=br.readLine();
				String[] split = line.split("	");
				String name = split[1];
				int nb = Integer.parseInt(split[0]);
				String type = split[2];
				StationPoint start = pointList.get(Integer.parseInt(split[3])-1);
				
				StationPoint end = pointList.get(Integer.parseInt(split[4])-1);
				Route newRoute = new Route(name,type,nb,start,end,true);
				routeList.add(newRoute);//adding the right number of vertices to the list
				start.addRoute(newRoute);
				//System.out.println(routeList.get(i));
			}



		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				if (fis != null)
					fis.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

	}
	
	public int getNbPoints()
	{
		return pointList.size();
	}
	
	public int getNbRoutes()
	{
		return routeList.size();
	}
	
	public ArrayList<Route> getRouteList()
	{
		return routeList;
	}

	public ArrayList<StationPoint> getPointList()
	{
		return pointList;
	}
	/*
	 * Resets the labels and predecessors of all the points 
	 * of the Graph.
	 */
	public void resetVertices()
	{
		for(StationPoint pt : getPointList())
		{
			pt.setLabel(Integer.MAX_VALUE);
			pt.setPred(null);
		}
	}
	
	public int getOrder()
	{
		return order;
	}


}
