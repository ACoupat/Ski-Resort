package application;
	
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import fr.acoupat.ag44.graphics.AppConstants;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage)
	{
		
		primaryStage.setTitle("SKI RESORT");
		primaryStage.getIcons().add(new Image("SkiResortIcon.png"));
		primaryStage.setResizable(false);
		HBox hb = new HBox();
		MyScene scene = new MyScene(hb,AppConstants.WindowWidth,AppConstants.WindowHeight);

		primaryStage.setScene(scene);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{

		launch(args);
		setUserAgentStylesheet(STYLESHEET_MODENA);
	}
}
