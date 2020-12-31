package main.backend;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static main.Loader.loadFxmlFile;

/*handler for verbal memory game*/
public class VerbalMemory {

    /*reference game variables*/
    private int score = 0;
    private int lives = 3;
    private final IntegerProperty livesProperty = new SimpleIntegerProperty(lives);
    private final IntegerProperty scoreProperty = new SimpleIntegerProperty(score);
    private final StringProperty targetProperty = new SimpleStringProperty();

    /*fxml variables*/
    @FXML
    private Label livesLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Text target;
    @FXML
    private Button seen;
    @FXML
    private Button nButton;

    /*word lists*/
    private final List<String> wordList = new ArrayList<>();
    private final List<String> seenList = new ArrayList<>();

    /*init method that adds words to word bank and binds variables to labels*/
    public void initialize () {
        target.textProperty().bind(targetProperty);
        livesLabel.textProperty().bind(livesProperty.asString());
        scoreLabel.textProperty().bind(scoreProperty.asString());
        wordList.add("steward");
        wordList.add("sting");
        wordList.add("exact");
        wordList.add("gallery");
        wordList.add("lover");
        wordList.add("lighter");
        wordList.add("philosophy");
        wordList.add("sodium");
        wordList.add("reckless");
        wordList.add("outlook");
        wordList.add("relative");
        wordList.add("entertain");
        wordList.add("bullet");
        wordList.add("tent");
        wordList.add("glance");
        wordList.add("core");
        wordList.add("fruit");
        wordList.add("origin");
    }

    /*method to handle game start*/
    public void start () {
        seen.setVisible(true);
        nButton.setVisible(true);
        if (lives == 0) {
            score = 0;
            lives = 3;
            livesProperty.setValue(lives);
            scoreProperty.setValue(score);
        }
        String s = wordList.get(ThreadLocalRandom.current().nextInt(0, wordList.size()));
        targetProperty.setValue(s);
    }

    /*method to handle the new button*/
    public void newWord () {
        if (lives == 0) return;
        if (seenList.contains(targetProperty.getValue())) {
            lives--;
            livesProperty.setValue(lives);
        }
        else {
            seenList.add(targetProperty.getValue());
            score++;
            scoreProperty.setValue(score);
        }
        if (lives == 0) {
            targetProperty.setValue("");
            seenList.clear();
            return;
        }
        start();
    }

    /*method to handle the seen button*/
    public void seenWord () {
        if (lives == 0) return;
        if (seenList.contains(targetProperty.getValue())) {
            score++;
            scoreProperty.setValue(score);
        }
        else {
            lives--;
            livesProperty.setValue(lives);
            seenList.add(targetProperty.getValue());
        }
        if (lives == 0) {
            targetProperty.setValue("");
            seenList.clear();
            return;
        }
        start();
    }

    /*method to return to homepage*/
    public void homeClicked() {
        Pane newRoot = loadFxmlFile(Home.getHomePage());
        Home.getScene().setRoot(newRoot);
    }
}
