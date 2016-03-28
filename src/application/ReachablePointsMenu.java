package application;

import java.util.ArrayList;
import java.util.Stack;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import fr.acoupat.ag44.dataStructures.Route;
import fr.acoupat.ag44.dataStructures.SkiGraph;
import fr.acoupat.ag44.dataStructures.StationPoint;

public class ReachablePointsMenu extends GridPane
{
	private Spinner<Integer> position;
	private ListView<CheckBox> listTracks;
	private ComboBox<String> comboLevel;
	private ObservableList<CheckBox> listCheck1;
	private Button computeRP = new Button("Compute");
	private ListView<String> listResults ;
	private ObservableList<String> listCheck2;
	private SkiGraph sg;
	private TitledPane titledPaneResults;
	public boolean setTph =false;

	public ReachablePointsMenu(SkiGraph sg)
	{
		this.sg = sg;
		position = new Spinner<Integer>(1,sg.getNbPoints(),1);
		position.setEditable(true);
		listTracks = new ListView<CheckBox>();
		comboLevel = new ComboBox<String>();
		listCheck1 = FXCollections.observableArrayList();
		listResults = new ListView<String>();
		listCheck2 = FXCollections.observableArrayList();
		HBox hbCompute = new HBox();
		titledPaneResults = new TitledPane("Results :",listResults);
		
		setAlignment(Pos.TOP_CENTER);
		setPadding(new Insets(20,20,20,20));
		setVgap(10);
		setHgap(10);
		
		int nbRoutes = sg.getNbRoutes();
		ArrayList<Route> routeList = sg.getRouteList();
		
		//Setting the list of CheckBoxes according to the list of routes of the graph
		for(int i=0; i<nbRoutes ;++i)
		{
			CheckBox newCheckBox = new CheckBox(((i+1)+" - "+routeList.get(i).getRouteName())+" ("+routeList.get(i).getType()+") ("+routeList.get(i).getStartPoint().getIndex()+"-"+routeList.get(i).getEndPoint().getIndex()+")");
			newCheckBox.setId(""+(i+1));
			newCheckBox.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					comboLevel.setValue("Custom");
					
				}
				
				
			});
			listCheck1.add(newCheckBox);
		}
		listTracks.setItems(listCheck1);
		
		computeRP.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				computeReachablePoints();
				
			}
		});
		add(new Text("Your Position"),0,0);
		add(position,1,0);
		add(new Text("Filters"),0,1);
		add(new TitledPane("Tracks",listTracks),0,2,2,1);
		add(new Text("Your Level :"),0,3);
		comboLevel.setItems(FXCollections.observableArrayList("-","V","B","R","N","No Special"));
		comboLevel.setValue("-");
		comboLevel.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,String oldValue, String newValue) {

				updateListTracks(newValue);
				
				
			}
		});
		
		add(comboLevel,1,3);
		hbCompute.getChildren().add(computeRP);
		hbCompute.setAlignment(Pos.BASELINE_RIGHT);
		add(hbCompute,1,4);
		add(titledPaneResults,0,5,2,1);

		
	}
	
	/*
	 * 
	 */
	private void computeReachablePoints()
	{
		StationPoint start = sg.getPointList().get(position.getValue()-1);


		//Resetting all the routes to unauthorized status
		for(Route r : sg.getRouteList())
		{
			r.setAuthorized(false);
		}
		//Setting the authorized Routes according to the CheckBoxes selected
		for(CheckBox cb : listCheck1)
		{
			if(cb.isSelected())
			{
				sg.getRouteList().get(Integer.parseInt(cb.getId())-1).setAuthorized(true);

			}
		}
		
		for(StationPoint sp : sg.getPointList())
		{
			sp.setMarked(false);
		}	
		listCheck2.clear();

		dfs(start);

		
	}
	

	/*
	 * Processes a dfs on the graph according to the starting point
	 * and the authorized routes.
	 */
	private void dfs(StationPoint start)
	{
		Stack<StationPoint> stack = new Stack<StationPoint>();
		StationPoint currentPoint =null;
		start.setMarked(true);
		stack.push(start);

		while(stack.size()!=0)
		{
			currentPoint = stack.pop();
			
			for(Route r : currentPoint.getRouteList())
			{
				if( r.isAuthorized() && r.getEndPoint().isMarked()==false)
				{
					r.getEndPoint().setMarked(true);
					stack.push(r.getEndPoint());

					listCheck2.add(""+stack.peek().toString(false));
				}
			}
			
		}
		titledPaneResults.setText("Results : "+listCheck2.size()+" points reachable");
		listResults.setItems(listCheck2);
		
		
	}


	/*
	 * Updates the list of CheckBoxes according to 
	 * the lvl of the user
	 */
	private void updateListTracks(String lvl)
	{
		int idCb =0;
		String cbType="";
		for(CheckBox cb : listCheck1)
		{
			idCb = Integer.parseInt(cb.getId());
			cbType = sg.getRouteList().get(idCb-1).getType();
			switch(lvl)
			{
				case "V":
				if(cbType.equals("N") || cbType.equals("R") || cbType.equals("B"))
				{
					cb.setSelected(false);
				}
				else
				{
					cb.setSelected(true);
				}
				
					break;
				case "B":
				if(cbType.equals("N") || cbType.equals("R"))
				{
					cb.setSelected(false);
				}
				else
				{
					cb.setSelected(true);
				}
					
					break;
				case "R":
					if(cbType.equals("N"))
					{
						cb.setSelected(false);
					}
					else
					{
						cb.setSelected(true);
					}
					
					break;
				case "N":
					cb.setSelected(true);					
					break;
				case "-":
					cb.setSelected(false);	
					break;
					
				case "No Special":
					if(!(cbType.equals("N") || cbType.equals("R") || cbType.equals("B")))
					{
						cb.setSelected(false);	
					}
					break;
			}
			
		}
		listTracks.setItems(listCheck1);
	}


}
