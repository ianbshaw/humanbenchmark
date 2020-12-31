package main.backend;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static main.Loader.loadFxmlFile;

/*handler for aim trainer game*/
public class AimTrainer {
    private final DoubleProperty x;
    private final DoubleProperty y;
    private final IntegerProperty r;
    private final LongProperty timeValue;
    private int targetNum;
    private long timeStart;

    /*fxml variables*/
    @FXML
    private Label remaining;
    @FXML
    private Label label;
    @FXML
    private Circle target;
    @FXML
    private Button button;

    /*constructor*/
    public AimTrainer() {
       x = new SimpleDoubleProperty(125);
       y = new SimpleDoubleProperty(70);
       r = new SimpleIntegerProperty(30);
       timeValue = new SimpleLongProperty(0);
    }

    /*initializer that binds variables and adds an event handler*/
    public void initialize() {
        label.textProperty().bind(timeValue.asString());
        remaining.textProperty().bind(r.asString());
        target.centerXProperty().bind(x);
        target.centerYProperty().bind(y);
        target.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> moveTarget());
    }

    /*method to handle start of game*/
    public void start () {
        targetNum = 30;
        r.setValue(targetNum);
        timeValue.setValue(0);
        target.setVisible(true);
        label.setVisible(false);
        button.setVisible(false);
        timeStart = System.nanoTime();
    }

    /*method to move the target to a random location*/
    private void moveTarget () {
        targetNum--;
        r.setValue(targetNum);
        if (r.doubleValue() == 0) {
            long timeEnd = System.nanoTime();
            timeValue.setValue(TimeUnit.NANOSECONDS.toSeconds(timeEnd - timeStart));
            target.setVisible(false);
            label.setVisible(true);
            button.setVisible(true);
        }
        x.setValue(ThreadLocalRandom.current().nextDouble(0, 440));
        y.setValue(ThreadLocalRandom.current().nextDouble(0, 190));
    }

    /*method to return to homepage*/
    public void homeClicked() {
        Pane newRoot = loadFxmlFile(Home.getHomePage());
        Home.getScene().setRoot(newRoot);
    }

}
