package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import fr.acoupat.ag44.dataStructures.SkiGraph;
import fr.acoupat.ag44.graphics.AppConstants;

public class MyScene extends Scene
{
	private SkiGraph sg; 
	private ShortestPathMenu spm;
	private ReachablePointsMenu rpm; 
	private Text title;
	
	
	public MyScene(HBox hb, int width , int height)
	{
		super(hb, width, height);
		title = new Text("SKI RESORT");
		title.setId("title");
		sg = new SkiGraph();
		spm = new ShortestPathMenu(sg);
		rpm = new ReachablePointsMenu(sg);
		
		//left Side
		VBox vbLeft=new VBox();
		vbLeft.setAlignment(Pos.BOTTOM_CENTER);
		vbLeft.setSpacing(10);
		vbLeft.setPrefWidth(AppConstants.WindowWidth-AppConstants.MenuSize);
			GridPane titlePane = new GridPane();
			titlePane.setAlignment(Pos.CENTER);
			titlePane.getChildren().add(title);
			vbLeft.getChildren().add(titlePane);
			hb.getChildren().add(vbLeft);
			
			ImageView img = new ImageView(new Image("skiResort.png"));
			img.setPreserveRatio(true);
			img.setFitWidth((AppConstants.WindowWidth-AppConstants.MenuSize));

			vbLeft.getChildren().add(img);
		
		
		
		
		
		//Right Side
		Accordion menu = new Accordion(new TitledPane("Shortest path", spm),new TitledPane("Reachable points", rpm));
		menu.setPrefWidth(AppConstants.MenuSize);
		menu.setExpandedPane(menu.getPanes().get(0));
		//menu.setColla
		hb.getChildren().add(menu);

		
		
		
		
	}
}
