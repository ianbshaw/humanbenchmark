package main.backend;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.*;

import static main.Loader.loadFxmlFile;

/*handler for visual memory game*/
public class VisualMemory {

    /*reference variables for game*/
    private int gridSize;
    private int numTargets;
    private int numClicked;
    private int numLives;
    private int level;
    private int rectangleSize;
    private int upgradeCounter;
    private int upgradeBound;
    private ArrayList<Rectangle> rectangles;
    private IntegerProperty livesText;
    private IntegerProperty levelText;
    private ColumnConstraints columnConstraints;
    private RowConstraints rowConstraints;

    /*fxml variables*/
    @FXML
    private GridPane grid;
    @FXML
    private Label livesLabel;
    @FXML
    private Label levelLabel;

    /*init method to setup game and bind variables to labels*/
    public void initialize() {
        upgradeCounter = 0;
        upgradeBound = 1;
        rectangleSize = 100;
        gridSize = 3;
        level = 1;
        numTargets = 3;
        numLives = 3;
        numClicked = 0;
        rectangles = new ArrayList<>();
        columnConstraints = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        levelText = new SimpleIntegerProperty();
        livesText = new SimpleIntegerProperty();
        livesText.setValue(numLives);
        levelText.setValue(level);
        livesLabel.textProperty().bind(livesText.asString());
        levelLabel.textProperty().bind(levelText.asString());
    }

    /*method to handle game start*/
    public void start() {
        for (int i = 0; i < gridSize; i++){
            increaseGridSize();
        }
        createRectangles();
        addRectanglesToGrid(grid, rectangles);
        Collections.shuffle(rectangles);
        setBoard();
    }

    /*method to create tiles based on gridsize and add to list*/
    private void createRectangles() {
        rectangles.clear();
        for (int i = 0; i < gridSize * gridSize; i++) {
            Rectangle r = new Rectangle(rectangleSize, rectangleSize, Color.BLUE);
            r.setStroke(Color.BLACK);
            rectangles.add(r);
        }
    }

    /*method to add event handler to target tiles that handle game logic*/
    private void setBoard() {
        Timer timer = new Timer();
        for (int i = 0; i < rectangles.size(); i++) {
            if (i < numTargets) {
                rectangles.get(i).setFill(Color.DARKBLUE);
                int finalI = i;
                rectangles.get(i).setOnMouseClicked(new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangles.get(finalI).removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                        rectangles.get(finalI).setFill(Color.DARKBLUE);
                        for (int i = 0; i < numTargets; i++) {
                            if (rectangles.get(i).getFill() == Color.DARKBLUE) numClicked++;
                        }
                        if (numClicked == numTargets) {
                            level++;
                            levelText.setValue(level);
                            for (int j = 0; j < numTargets; j++) rectangles.get(j).setFill(Color.BLUE);
                            numTargets++;
                            numClicked = 0;
                            if (upgradeCounter == upgradeBound) {
                                upgradeCounter = 0;
                                upgradeBound++;
                                gridSize++;
                                increaseGridSize();
                                createRectangles();
                                addRectanglesToGrid(grid, rectangles);
                                rectangleSize -= 10;
                                resizeRectangle();
                            } else upgradeCounter++;

                            Collections.shuffle(rectangles);
                            TimerTask t = new TimerTask() {
                                @Override
                                public void run() {
                                    setBoard();
                                }
                            };
                            timer.schedule(t, 1000L);
                        }
                        numClicked = 0;
                        rectangles.get(finalI).removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                    }
                });
            }
            else {
                rectangles.get(i).setFill(Color.BLUE);
                int finalI1 = i;
                rectangles.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        rectangles.get(finalI1).removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                        numClicked = 0;
                        numLives--;
                        livesText.setValue(numLives);
                        Collections.shuffle(rectangles);
                        setBoard();
                        rectangles.get(finalI1).removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
                    }
                });
            }
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < numTargets; i++)
                    rectangles.get(i).setFill(Color.BLUE);
            }
        };
        timer.schedule(task, 2000L);
    }

    /*method that increases the size of the gridpane*/
    private void increaseGridSize() {
        columnConstraints.setPercentWidth(100.0 / gridSize);
        rowConstraints.setPercentHeight(100.0 / gridSize);
        grid.getColumnConstraints().add(columnConstraints);
        grid.getRowConstraints().add(rowConstraints);
    }

    /*method to resize tiles*/
    private void resizeRectangle() {
        Rectangle r;
        for (Rectangle rectangle : rectangles) {
            r = rectangle;
            r.setHeight(rectangleSize);
            r.setWidth(rectangleSize);
        }
    }

    /*method to set tiles onto grid pane*/
    private void addRectanglesToGrid(GridPane grid, List<Rectangle> rectangles) {
        if (!grid.getChildren().isEmpty())
            grid.getChildren().clear();
        for (int i = 0; i < gridSize * gridSize; i++)
            grid.add(rectangles.get(i), i % gridSize, i / gridSize);
    }

    /*method to return to homepage*/
    public void homeClicked() {
        Pane newRoot = loadFxmlFile(Home.getHomePage());
        Home.getScene().setRoot(newRoot);
    }
}
