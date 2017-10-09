package gui;

import java.io.File;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

/**
 * This class allows us to change and manage scenes.
 */
public class ScreenManager {
    public enum ScreenType {
        MAIN_MENU,
        LIST_SELECT,
        HARD_TEST,
        EASY_TEST,
        SCORE,
        HIGHSCORE, 
        PRACTICE_MODE,
    }
    
    private static ScreenManager instance = null;
    
    private Stage _stage;

    public ScreenManager(Stage stage) {
        _stage = stage;

    }

    public static void create(Stage stage) {
        instance = new ScreenManager(stage);
    }

    public static ScreenManager get() {
        return instance;
    }

    public void changeScreen(ScreenType screenType) {
        Parent root;
        
        switch (screenType) {
            case MAIN_MENU:
				root = new MainMenuScreen();
                break;

            case LIST_SELECT:
				root = new ListSelectScreen(); 
                break;

            case HARD_TEST:
				root = new TestScreen(ListMode.HARD);
                break;

            case EASY_TEST:
				root = new TestScreen(ListMode.EASY);
                break;
                
            case PRACTICE_MODE:
            	root = new TestScreen(ListMode.PRACTICE);
            	break;

            case HIGHSCORE:
                root = new HighScoreScreen();
                break;

            case SCORE:

            default:
                root = new MainMenuScreen();
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add(new File("./styles.css").toURI().toString());

        _stage.setScene(scene);
    }

    public void changeScreen(Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(new File("./styles.css").toURI().toString());

        _stage.setScene(scene);
    }
}