package application;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import fr.acoupat.ag44.dataStructures.Route;
import fr.acoupat.ag44.dataStructures.SkiGraph;
import fr.acoupat.ag44.dataStructures.StationPoint;

public class ShortestPathMenu extends GridPane
{
	private Spinner<Integer> startSpin;
	private Spinner<Integer> endSpin;
	private Button compute;
	//private TextArea results;
	private SkiGraph sg;
	private ListView<String> resultList;
	private ObservableList<String> resultListContent;
	public double somme=0;

	public ShortestPathMenu(SkiGraph sg)
	{		
		startSpin = new Spinner<Integer>(1,sg.getNbPoints(),1);
		startSpin.setEditable(true);
		endSpin = new Spinner<Integer>(1,sg.getNbPoints(),1);
		endSpin.setEditable(true);
		compute = new Button("Compute");
	
		resultList = new ListView<String>();
		resultListContent = FXCollections.observableArrayList();
		this.sg=sg;

		setAlignment(Pos.TOP_CENTER);
		setPadding(new Insets(20,20,20,20));
		setVgap(10);
		setHgap(10);

		add(new Text("Start :"),0,0);
		add(new Text("End :"),0,1);
		add(startSpin,1,0);
		add(endSpin,1,1);
		HBox hbCompute = new HBox();
		compute.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				getShortestPath();

			}
		});
		hbCompute.getChildren().add(compute);
		hbCompute.setAlignment(Pos.BASELINE_RIGHT);
		add(hbCompute,1,3);
		TitledPane paneResults = new TitledPane("Results",resultList);
		add(paneResults,0,5,2,1);		
	}

	/*
	 * Computes and prints the Shortest path and resets
	 * all the Points at the end of the computation.
	 */
	private void getShortestPath()
	{
		ArrayList<StationPoint> sp=null;
		ArrayList<Integer> spR = new ArrayList<Integer>();
		sp = computeShortestPath(spR);
		printSP(sp,spR);
		for(StationPoint p : sg.getPointList())
		{
			p.resetPoint();
		}
	}
	/*
	 * Returns a List containing all the points of 
	 * the shortest path between the two points specified
	 * in the two spinners.
	 */
	private ArrayList<StationPoint> computeShortestPath(ArrayList<Integer> spR)
	{
		ArrayList<StationPoint> sp = new ArrayList<StationPoint>();
		
		StationPoint start = sg.getPointList().get(startSpin.getValue()-1);
		StationPoint end = sg.getPointList().get(endSpin.getValue()-1);
		StationPoint currentPoint = null;

		sg.resetVertices();
		start.setLabel(0);
		
		for (int i=1;i<sg.getOrder();++i)
		{
			for(Route r : sg.getRouteList())
			{
				if(r.getEndPoint().getLabel() > r.getStartPoint().getLabel() + r.getCost())
				{
					r.getEndPoint().setLabel(r.getStartPoint().getLabel() + r.getCost());
					r.getEndPoint().setPred(r.getStartPoint());
					r.getEndPoint().setLastRoute(r);
				}
			}
		}
		currentPoint = end;
		while (currentPoint.getIndex()!=start.getIndex() && currentPoint.getPred()!=null)
		{
			sp.add(currentPoint);
			currentPoint=currentPoint.getPred();			
			
		}
		sp.add(start);
		
		return sp;


	}

	/*
     * Prints the shortest path a list in the 
	 * corresponding listView
	 */
	private void printSP(ArrayList<StationPoint> sp, ArrayList<Integer> spR)
	{		
		resultList.getItems().clear();
		int size = sp.size();
		
		if(size==1)
		{
			resultListContent.add("No shortest path found");
		}
		else
		{
			for(int i=size-1; i>=0; --i)
			{
				resultListContent.add(sp.get(i).toString(true));
				//System.out.println(sp.get(i).getLabel());
			}
		}
		System.out.println("Somme :"+sp.get(0).getLabel());
		
		resultList.setItems(resultListContent);
	}

}
