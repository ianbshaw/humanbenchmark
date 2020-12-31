package main.backend;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import static main.Loader.loadFxmlFile;

/*Home class that handles the home page to application*/
public class Home {

    /*strings containing directory paths for all games*/
    private static final String directory = "resources/";
    private static final String homePage = directory + "home.fxml";
    private static final String reactionTimePage = directory + "reactionTime.fxml";
    private static final String aimTrainerPage = directory + "aimTrainer.fxml";
    private static final String chimpTestPage = directory + "chimpTest.fxml";
    private static final String visualMemoryPage = directory + "visualMemory.fxml";
    private static final String numberMemoryPage = directory + "numberMemory.fxml";
    private static final String typingPage = directory + "typing.fxml";
    private static final String verbalMemoryPage = directory + "verbalMemory.fxml";
    private static final String myGamePage = directory + "myGame.fxml";

    /*static scene to load fxml pages*/
    private static Scene scene;

    /*getters and setters*/
    public void setScene(Scene scene) {
        Home.scene = scene;
    }

    public static Scene getScene() {
        return scene;
    }

    public static String getHomePage() {
        return homePage;
    }

    /*methods to load fxml pages for each game*/
    public void reactionTimeClicked() {
        Pane newRoot = loadFxmlFile(reactionTimePage);
        scene.setRoot(newRoot);
    }

    public void aimTrainerClicked() {
        Pane newRoot = loadFxmlFile(aimTrainerPage);
        scene.setRoot(newRoot);
    }

    public void chimpTestClicked() {
        Pane newRoot = loadFxmlFile(chimpTestPage);
        scene.setRoot(newRoot);
    }

    public void visualMemoryClicked() {
        Pane newRoot = loadFxmlFile(visualMemoryPage);
        scene.setRoot(newRoot);
    }

    public void numberMemoryClicked() {
        Pane newRoot = loadFxmlFile(numberMemoryPage);
        scene.setRoot(newRoot);
    }

    public void typingClicked() {
        Pane newRoot = loadFxmlFile(typingPage);
        scene.setRoot(newRoot);
    }

    public void verbalMemoryClicked() {
        Pane newRoot = loadFxmlFile(verbalMemoryPage);
        scene.setRoot(newRoot);
    }

    public void myGameClicked() {
        Pane newRoot = loadFxmlFile(myGamePage);
        scene.setRoot(newRoot);
    }
}
