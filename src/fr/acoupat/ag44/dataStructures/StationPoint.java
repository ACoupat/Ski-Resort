/*
 * Author : Antoine Coupat  Date : 25/11/2015
 * File Name : StationPoint.java
 * Project : Ski Resort (UTBM : AG44)   
 * 
 * Description : Class representing a Point of the station.
 * 				 It contains the references to the routes 
 * 				 coming out of the point.
 */

package fr.acoupat.ag44.dataStructures;
import java.util.ArrayList;


public class StationPoint 
{
	private String placeName;
	private int altitude;
	private float label;
	private ArrayList<Route> routeList;
	private StationPoint pred;
	private int index;
	private boolean marked;
	private Route lastRoute;
	private int order;
	
	/*
	 * Default Constructor
	 */
	public StationPoint()
	{
		super();
		placeName="";
		altitude=0;
		label = Integer.MAX_VALUE;
		routeList=null;
		pred=null;
		marked=false;
		lastRoute = null;
		
	}
	/*
	 * Constructor, initializes the different attributes of the StationPoint
	 */
	public StationPoint(String name, int alt, int num, int order)
	{
		//super(num,order);
		placeName = name;
		altitude = alt;
		index = num;
		this.order = order;
		routeList = new ArrayList<Route>();
		lastRoute = null;
		
	}
	
	public String toString(boolean detail)
	{
		if (detail)
		{
			if (lastRoute==null)
			{
				return ""+index+" - "+placeName;
			}
			else
			{
				return ""+index+" - "+placeName+" by "+lastRoute.toString(true);
			}
		}
		else
		{
			return ""+index+" - "+placeName;
		}
		
		//return "n°"+index+" label ="+label;
	}
	/*
	 * Resets the predecessor, the mark and the
	 * lastRoute of the Point.
	 */
	public void resetPoint()
	{
		pred=null;
		marked=false;
		lastRoute = null;
	}
	
	public float getLabel()
	{
		return label;
	}

	public void setLabel(float f)
	{
		label = f;
	}
	
	public void addRoute(Route r)
	{
		routeList.add(r);
	}
	
	public ArrayList<Route> getRouteList()
	{
		return routeList;
	}
	
	/*
	 * Returns the adjacent Route with the lower cost.
	 */
	public Route getMinRoute()
	{
		int minCost =Integer.MAX_VALUE;
		Route minRoute=null;
		for(Route r : routeList)
		{
			if(r.getCost()<minCost)
			{
				minRoute =r;
			}
		}
		return minRoute;
	}
	/*
	 * Returns true if the Point still has adjacent
	 * authorized Routes (reachable points algorithm)	
	 */
	public boolean hasAuthorizedNeighbours()
	{
		int nb=0;
		for(Route r : routeList)
		{
			if(r.isAuthorized())
				nb++;
		}
		
		return nb!=0;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public StationPoint getPred() {
		return pred;
	}

	public void setPred(StationPoint pred) {
		this.pred = pred;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	/*
	 * Returns the first adjacent Point which is
	 * unmarked and marks it;
	 */
	public StationPoint getNeighbour() 
	{
		for(Route r : routeList)
		{
			if(r.isAuthorized())
			{
				r.setAuthorized(false);
				if(!(r.getEndPoint().isMarked()))
				{
					r.getEndPoint().setMarked(true);
					return r.getEndPoint();
				}
				
				
			}
		}
		return null;
		
	}

	public Route getLastRoute() {
		return lastRoute;
	}

	public void setLastRoute(Route lastRoute) {
		this.lastRoute = lastRoute;
	}
}
