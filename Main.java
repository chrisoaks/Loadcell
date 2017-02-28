package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	public static Loop loop;
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,1600,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Hello World");
			primaryStage.setScene(scene);
			primaryStage.show();
	        
			Listener load_listener = new Listener("COM3", "load");
	        Listener ard_listener = new Listener("COM4", "ard");

	        /* Begin Camera Related Code */
	        
	        //CLCamera myCameras[] = new CLCamera[2];
	        //System.out.println("Camera " + (1) + " UUID " + CLCamera.cameraUUID(1));
	        //System.out.println("Camera " + (2) + " UUID " + CLCamera.cameraUUID(2));
	        /* End Camera Related Code */
	        
	        
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              System.out.println("Stage is closing");
		              System.out.println(Controller.dataQ1);
		              load_listener.close();
					  ard_listener.close();

		          }
		      });
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
