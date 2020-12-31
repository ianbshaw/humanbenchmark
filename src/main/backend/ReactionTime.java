package main.backend;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static main.Loader.loadFxmlFile;

/*handler for reaction time game*/
public class ReactionTime {
    /*fxml references*/
    @FXML
    private Pane root;
    @FXML
    private Label reactionTime;
    @FXML
    private Label welcome;

    /*game variables*/
    private boolean gameStarted = false;
    private long startTime;
    private final LongProperty reactionTimeValue;

    /*constructor*/
    public ReactionTime() {
        reactionTimeValue = new SimpleLongProperty(0);
    }

    /*init method that binds a label and adds event handler to pane*/
    public void initialize() {
        reactionTime.textProperty().bind(reactionTimeValue.asString());
        root.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (!gameStarted) {
                root.setStyle("-fx-background-color: red");
                welcome.setVisible(false);
                reactionTime.setVisible(false);
                startTimer();
            }
            else finishTimer();
        });
    }

    /*method to start the timer upon game start*/
    private void startTimer() {
        reactionTimeValue.setValue(0);
        gameStarted = true;
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                root.setStyle("-fx-background-color: green");
                startTime = System.nanoTime();
            }
        };
        long rand = ThreadLocalRandom.current().nextLong(1000, 5000);
        t.schedule(task, rand);
    }

    /*method to calculate end time*/
    private void finishTimer() {
        long finishTime = System.nanoTime();
        long reactionTimeNano = finishTime - startTime;
        long milliValue = TimeUnit.NANOSECONDS.toMillis(reactionTimeNano);
        reactionTimeValue.setValue(milliValue);
        reactionTime.setVisible(true);
        gameStarted = false;
    }

    /*method to return to homepage*/
    public void homeClicked() {
        Pane newRoot = loadFxmlFile(Home.getHomePage());
        Home.getScene().setRoot(newRoot);
    }
}
