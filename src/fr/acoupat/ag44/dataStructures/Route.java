/*
 * Author : Antoine Coupat  Date : 25/11/2015
 * File Name : Route.java
 * Project : Ski Resort (UTBM : AG44)   
 * 
 * Description : Class representing a Route between two Points
 * 				 of the station. It contains the references to he starting and
 * 	             the ending vertex of the route.
 */

package fr.acoupat.ag44.dataStructures;



public class Route 
{
	private boolean directed;
	private int index;
	private String routeName;
	private String type;
	private float cost;
	private StationPoint startPoint;
	private StationPoint endPoint;
	private boolean authorized;
	
	/* 
	 * Default Constructor
	 */
	public Route()
	{
		super();
		routeName = "";
		type="";		
		cost =0;
	}
	/*
	 * Constructor, the new Route is created setting the name of the route, its type
	 * its starting and ending points, and a boolean to know if the route is derected 
	 * or not
	 */
	public Route(String name, String t, int nb, StationPoint start, StationPoint end, boolean directed)
	{

		routeName = name;
		type = t;
		startPoint = start;
		endPoint = end;
		index =nb;
		this.directed =directed;
		computeCost();

	}
	
	public String getRouteName()
	{
		return routeName;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String toString(boolean detail)
	{
		return "route n°"+index+" ("+type+")";
	}
	/*
	 * Computes the cost of the Route according to
	 * its type.
	 */
	private void computeCost()
	{
		float height = Math.abs(startPoint.getAltitude()-endPoint.getAltitude());
		switch(type)
		{
			/*case "V": cost = Math.abs(5*((startPoint.getAltitude()-endPoint.getAltitude())/100)); 	break;
			case "B": cost = Math.abs(4*((startPoint.getAltitude()-endPoint.getAltitude())/100));	break;
			case "R": cost = Math.abs(3*((startPoint.getAltitude()-endPoint.getAltitude())/100));	break;
			case "N": cost = Math.abs(2*((startPoint.getAltitude()-endPoint.getAltitude())/100));	break;
			case "KL": cost = Math.abs((startPoint.getAltitude()-endPoint.getAltitude())/600);	break;
			case "SURF": cost = Math.abs(10*((startPoint.getAltitude()-endPoint.getAltitude())/100));	break;
			case "TPH": cost = Math.abs(4 + 2*((startPoint.getAltitude()-endPoint.getAltitude())/100));	break;
			case "TC": cost = Math.abs(2 + 3*((startPoint.getAltitude()-endPoint.getAltitude())/100));	break;
			case "TSD": cost = Math.abs(1 + 3*((startPoint.getAltitude()-endPoint.getAltitude())/100));	break;
			case "TS": cost = Math.abs(1 + 4*((startPoint.getAltitude()-endPoint.getAltitude())/100)); 	break;	
			case "TK": cost = Math.abs(1 + 4*((startPoint.getAltitude()-endPoint.getAltitude())/100));	break;*/
			case "V": cost = 5*(height/100); 	break;
			case "B": cost = 4*(height/100);	break;
			case "R": cost =  3*(height/100);	break;
			case "N": cost =  2*(height/100);	break;
			case "KL": cost =  height/600;	break;
			case "SURF": cost =  10*(height/100);	break;
			case "TPH": cost =  4 + 2*(height/100);	break;
			case "TC": cost =  2 + 3*(height/100);	break;
			case "TSD": cost =  1 + 3*(height/100);	break;
			case "TS": cost =  1 + 4*(height/100); 	break;	
			case "TK": cost =  1 + 4*(height/100);	break;
				
			case "BUS" :
				
				if(routeName.equals("navette1600-1800")||routeName.equals("navette1800-1600"))
				{
					cost = 30;
				}

				else if(routeName.equals("navette2000-1600")||routeName.equals("navette1600-2000"))
				{
					cost = 40;
				}
					
			break;
			
			default: cost=0; break;
			
		}
	}
	
	
	
	public float getCost()
	{
		return cost;
	}

	public StationPoint getEndPoint()
	{
		return endPoint;
	}

	public void setEndPoint(StationPoint endPoint) 
	{
		this.endPoint = endPoint;
	}

	public StationPoint getStartPoint() 
	{
		return startPoint;
	}

	public void setStartPoint(StationPoint startPoint) 
	{
		this.startPoint = startPoint;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
