package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	private final int WINDOW_HEIGHT=600; //constants to be used by all Panes for size
	private final int WINDOW_WIDTH=800;
	
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("MaoriMathsMagic");
				
		primaryStage.setScene(new Scene(new StartScreen(primaryStage), WINDOW_WIDTH, WINDOW_HEIGHT));
		primaryStage.sizeToScene();
		
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}




