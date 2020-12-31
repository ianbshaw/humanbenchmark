package main.backend;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import static main.Loader.loadFxmlFile;

/*handler for clicker game*/
public class MyGame {

    /*reference variables for game*/
    private boolean gameStarted = false;
    private int timeRemaining = 15;
    private int score = 0;
    private final IntegerProperty scoreProperty = new SimpleIntegerProperty();
    private final IntegerProperty timeProperty = new SimpleIntegerProperty();
    private AnimationTimer timer;

    /*fxml variables*/
    @FXML
    private Pane pane;
    @FXML
    private Label label;
    @FXML
    private Button button;

    /*init method add event handler to pane*/
    public void initialize() {
        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            score++;
            start();
        });
    }

    /*method to handle game start*/
    private void start() {
        if (!gameStarted) {
            timer = new AnimationTimer() {
                private long lastUpdate = 0;
                @Override
                public void handle(long now) {
                    if (now - lastUpdate >= (10_000)) {
                        scoreProperty.setValue(score);
                        label.setText("Time Remaining: " + timeRemaining + "  Score: " + score);
                    }
                    if (now - lastUpdate >= (1_000_000_000)) {
                        timeRemaining--;
                        lastUpdate = now;
                        timeProperty.setValue(timeRemaining);
                        if (timeRemaining <= 0) {
                            timer.stop();
                            label.setText("Time Remaining: " + timeRemaining + "  Score: " + score);
                            button.setVisible(true);
                        }
                    }
                }
            };
            gameStarted = true;
            timer.start();
        }
    }

    /*method to handle restart button*/
    public void restart() {
        timer.stop();
        timeRemaining = 5;
        score = 0;
        button.setVisible(false);
        timer.start();
    }

    /*method to return to homepage*/
    public void homeClicked() {
        Pane newRoot = loadFxmlFile(Home.getHomePage());
        Home.getScene().setRoot(newRoot);
    }
}
