package main.backend;

import javafx.animation.AnimationTimer;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.concurrent.TimeUnit;

import static main.Loader.loadFxmlFile;

/*handler for typing game*/
public class Typing {

    /*reference variables, including the excerpt that the user will copy*/
    private final String checkString = "He's one of the Knights Radiant, Dalinar thought, watching him go. I'll probably need to stop sending him on errands." +
            " Storms. It was really happening." +
            " Shallan had walked to the windows. Dalinar stepped up beside her. This was the eastern face of the tower, the flat edge that looked directly toward the Origin." +
            "\" Kaladin will only have time to save a few,\" Shallan said. \"If that many. There are four of us, Brightlord. Only four against a storm full of destruction.\"" +
            "\" It is what it is.\"" +
            "\" So many will die.\"" +
            "\" And we will save the ones we can,\" Dalinar said. He turned to her. \"Life before death, Radiant. It is the task to which we are now sworn.\"" +
            " She pursed her lips, still looking eastward, but nodded. \"Life before death, Radiant.\"";
    private String checkUser;
    private long startTime;
    private boolean gameStarted = false;
    private final LongProperty timeValue = new SimpleLongProperty();
    AnimationTimer timer;

    /*fxml variables*/
    @FXML
    private TextFlow textFlow;
    @FXML
    private TextArea typeTest;
    @FXML
    private Label finalScore = new Label();

    /*init method to setup game and add event handler to textbox*/
    @FXML
    public void initialize() {
        Text t = new Text(checkString);
        t.setFont(Font.font("Arial-Black", FontWeight.BOLD, 15));
        t.setFill(Color.WHITE);
        textFlow.getChildren().add(t);
        typeTest.addEventHandler(KeyEvent.KEY_TYPED, (key) -> start());
        finalScore.textProperty().bind(timeValue.asString());
        finalScore.setVisible(false);
        typeTest.requestFocus();
    }

    /*method to handle game start*/
    private void start() {
        if (!gameStarted) {
            startTime = System.nanoTime();
            timer = new AnimationTimer() {
                private long lastUpdate = 0;
                @Override
                public void handle(long now) {
                    if (now - lastUpdate >= (10_000)) {
                        checkUser = typeTest.getText();
                        checkText(checkUser);
                        lastUpdate = now;
                    }
                    if (typeTest.getText().equals(checkString)) {
                        long finishTime = System.nanoTime();
                        long reactionTimeNano = finishTime - startTime;
                        long milliValue = TimeUnit.NANOSECONDS.toMillis(reactionTimeNano);
                        timeValue.setValue(milliValue);
                        finalScore.setVisible(true);
                        timer.stop();
                    }
                }
            };
            gameStarted = true;
            timer.start();
        }
        if (typeTest.getText().equals(checkString)) {
            long finishTime = System.nanoTime();
            long reactionTimeNano = finishTime - startTime;
            final long seconds = TimeUnit.NANOSECONDS.toSeconds(reactionTimeNano);
            timeValue.setValue(seconds);
            finalScore.setVisible(true);
            timer.stop();
        }
    }

    /*method that checks the user text to the excerpt and shows errors*/
    private void checkText(String u) {
        textFlow.getChildren().clear();
        if (u.length() > checkString.length()) return;
        for (int i = 0; i < u.length(); i++) {
            char c1 = checkString.charAt(i);
            char c2 = u.charAt(i);
            Text t = new Text(String.valueOf(c1));
            if (c1 == c2)
                t.setFill(Color.LIGHTGREEN);
            else
                t.setFill(Color.RED);
            t.setFont(Font.font("Arial-Black", FontWeight.BOLD, 15));
            textFlow.getChildren().add(t);
        }
        Text r = new Text(checkString.substring(u.length()));
        r.setFont(Font.font("Arial-Black", FontWeight.BOLD, 15));
        r.setFill(Color.WHITE);
        textFlow.getChildren().add(r);
    }

    /*method to return to homepage*/
    public void homeClicked() {
        Pane newRoot = loadFxmlFile(Home.getHomePage());
        Home.getScene().setRoot(newRoot);
    }
}
