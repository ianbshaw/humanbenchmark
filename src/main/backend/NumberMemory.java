package main.backend;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import static main.Loader.loadFxmlFile;

/*handler for number memory game*/
public class NumberMemory {

    /*reference variables for game*/
    private int guess;
    private int interval;
    private int level;
    private IntegerProperty levelText;
    private IntegerProperty guessText;

    /*fxml variables*/
    @FXML
    private TextField answer;
    @FXML
    private Label guessLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label answerLabel;
    @FXML
    private Button submitButton;
    @FXML
    private Button startButton;
    @FXML
    private Pane root;

    /*initializer that sets bindings for labels*/
    public void initialize() {
        levelText = new SimpleIntegerProperty();
        guessText = new SimpleIntegerProperty();
        interval = 1;
        level = 1;
        guessLabel.textProperty().bind(guessText.asString());
        levelLabel.textProperty().bind(levelText.asString());
        guessLabel.setVisible(false);
        answerLabel.setVisible(false);
        answer.setVisible(false);
        submitButton.setVisible(false);
    }

    /*method to handle game start*/
    public void start() {
        startButton.setVisible(false);
        levelText.setValue(level);
        guess = ThreadLocalRandom.current().nextInt(interval, (10 * interval) - 1);
        guessText.setValue(guess);
        guessLabel.setVisible(true);
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                guessLabel.setVisible(false);
                answerLabel.setVisible(true);
                answer.setVisible(true);
                submitButton.setVisible(true);
            }
        };
        t.schedule(task, 3000L);
    }

    /*method to handle answer submission from user*/
    public void submit() {
        if (Integer.parseInt(answer.getText()) == guess) {
            level++;
            interval *= 10;
            answer.setText("");
            answerLabel.setVisible(false);
            answer.setVisible(false);
            submitButton.setVisible(false);
            start();
        }
        else {
            root.setStyle("-fx-background-color: red");
            interval = 1;
            answer.setText("");
            answerLabel.setVisible(false);
            answer.setVisible(false);
            submitButton.setVisible(false);
            Timer t = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    root.setStyle("-fx-background-color: #0282e5");
                    startButton.setVisible(true);
                    level = 1;
                }
            };
            t.schedule(task, 1500L);
        }
    }

    /*method to return to homepage*/
    public void homeClicked() {
        Pane newRoot = loadFxmlFile(Home.getHomePage());
        Home.getScene().setRoot(newRoot);
    }
}
