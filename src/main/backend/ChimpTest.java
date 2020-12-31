package main.backend;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static main.Loader.loadFxmlFile;

/*handler for chimptest game*/
public class ChimpTest {

    /*reference variables for game objects*/
    private int gridSize;
    private int numBoxes;
    private int sequence;
    private int livesCounter;
    private int bestScore;
    private IntegerProperty scoreProperty;
    private IntegerProperty numLives;
    private ColumnConstraints columnConstraints;
    private RowConstraints rowConstraints;
    private ArrayList<StackPane> targetList;

    /*fxml object references*/
    @FXML
    private Label score;
    @FXML
    private Label lives;
    @FXML
    private GridPane grid;

    /*setup for game binding variables to objects*/
    public void initialize() {
        bestScore = 0;
        livesCounter = 3;
        numBoxes = 4;
        gridSize = 5;
        sequence = 1;
        numLives = new SimpleIntegerProperty();
        scoreProperty = new SimpleIntegerProperty();
        scoreProperty.setValue(bestScore);
        numLives.setValue(livesCounter);
        columnConstraints = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        targetList = new ArrayList<>();
        lives.textProperty().bind(numLives.asString());
        score.textProperty().bind(scoreProperty.asString());
    }

    /*method that runs on game start*/
    public void start() {
        scoreProperty.setValue(0);
        numLives.setValue(livesCounter);

        /*create grid*/
        for (int i = 0; i < gridSize; i++)
            increaseGridSize();

        for (int i = 0; i < numBoxes; i++)
            addRectangle(i + 1);

        setLocation(targetList);
    }

    /*method that checks if chosen tile is correct or not*/
    private void targetClick(int check, StackPane s) {
        if (check == sequence) {
            if (sequence == 1) {
                for (StackPane stackPane:
                     targetList) {
                    stackPane.getChildren().get(1).setVisible(false);
                }
            }
            sequence++;
            s.setVisible(false);
        } else {
            livesCounter--;
            numLives.setValue(livesCounter);
            if (livesCounter == 0) {
                grid.getChildren().clear();
                numBoxes = 4;
                sequence = 1;
                livesCounter = 3;
                targetList.clear();
                return;
            }
            setLocation(targetList);
            sequence = 1;
            return;
        }

        if (sequence > numBoxes) {
            bestScore = numBoxes;
            scoreProperty.setValue(bestScore);
            addRectangle(targetList.size() + 1);
            gridSize++;
            increaseGridSize();
            setLocation(targetList);
            sequence = 1;
            numBoxes++;
        }
    }

    /*method that creates the tiles*/
    private void addRectangle(int number) {
        StackPane s = new StackPane();
        Rectangle r = new Rectangle(50,50, Color.BLUE);
        r.setStroke(Color.BLACK);
        Label l = new Label(Integer.toString(number));
        l.setTextFill(Color.WHITE);
        l.setFont(new Font("Arial-Black", 23));
        s.getChildren().addAll(r, l);
        targetList.add(s);

        s.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> targetClick((number), s));
    }

    /*method to increase gridsize based on number of tiles in row/column*/
    private void increaseGridSize() {
        columnConstraints.setPercentWidth(100.0 / gridSize);
        rowConstraints.setPercentHeight(100.0 / gridSize);
        grid.getColumnConstraints().add(columnConstraints);
        grid.getRowConstraints().add(rowConstraints);
    }

    /*method to set the location of each tile within the gridpane*/
    private void setLocation(ArrayList<StackPane> targetList) {
        grid.getChildren().clear();

        for (int i = 0; i < targetList.size(); i++) {
            int x = ThreadLocalRandom.current().nextInt(0, gridSize);
            int y = ThreadLocalRandom.current().nextInt(0, gridSize);

            if (targetList.contains(getNodeByRowColumnIndex(x, y, grid)))
                i--;
            else {
                StackPane s = targetList.get(i);
                s.setVisible(true);
                s.getChildren().get(1).setVisible(true);
                try {
                    grid.add(s, y, x);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*helper function that gets the location of a target tile within the gridpane*/
    private Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    /*method to return to homepage*/
    public void homeClicked() {
        Pane newRoot = loadFxmlFile(Home.getHomePage());
        Home.getScene().setRoot(newRoot);
    }
}
