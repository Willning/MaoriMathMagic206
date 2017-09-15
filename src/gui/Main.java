package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	//TODO, rejig the constants to a constants class 
	
	private final int WINDOW_HEIGHT=600; //constants to be used by all Panes for size
	private final int WINDOW_WIDTH=800;
	
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("MaoriMathsMagic");
				
		primaryStage.setScene(new Scene(new StartPane(primaryStage), WINDOW_WIDTH, WINDOW_HEIGHT));
		primaryStage.sizeToScene();
		//every-time the scene shifts, fit the stage to the pane, stops unneccesary size changes
		
		primaryStage.setResizable(false);
		//primary stage will not be able to be resized due to it messing up the layout
		primaryStage.show();
	}
}




