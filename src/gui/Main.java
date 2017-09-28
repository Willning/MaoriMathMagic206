package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

//@@TODO: rejig the constants to a constants class 
//@@TODO: make a media player to play the recording
/**
 * Entry point.
 */
public class Main extends Application{
	
    public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage stage) {
		stage.setTitle("Tāitai");

		SceneManager.create(stage);
		SceneManager.get().changeScene(SceneManager.SceneType.START);
		
		// Every time the scene shifts, fit the stage to the pane.
		// Stops unneccesary size changes.
		stage.sizeToScene();
		
		stage.setResizable(false);
		stage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
        });
		
		// Primary stage will not be able to be resized, due to it messing up the layout
		stage.show();
	}
}




