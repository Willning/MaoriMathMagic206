package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application{
	//TODO, rejig the constants to a constants class 
	//TODO, make a media player to play the recording
	
	
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("MaoriMathsMagic");
				
		primaryStage.setScene(new Scene(new StartPane(primaryStage), FrameConstants.WINDOW_WIDTH, FrameConstants.WINDOW_HEIGHT));
		primaryStage.sizeToScene();
		//every-time the scene shifts, fit the stage to the pane, stops unneccesary size changes
		
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		//primary stage will not be able to be resized due to it messing up the layout
		primaryStage.show();
	}
}




