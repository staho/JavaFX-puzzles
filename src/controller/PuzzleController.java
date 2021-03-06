package controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.CutImage;
import model.Tile;

import java.io.File;
import java.sql.Time;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

/**
 * Created by staho on 08.04.2017.
 */
public class PuzzleController {

    private List<Tile> tilesList;
    private ObservableList<model.Time> timeList;
    private Tile first = null;
    private Tile second = null;
    private Timeline timeline;
    private long time = 0;
    private int movesCount = 0;
    private boolean isGameStarted = false;
    private model.Time timeOfGame;
    private MainApp main;
    private File file;

    public void setMain(MainApp main) {
        this.main = main;
    }

    private void saveTimes(File file){
        main.saveTimesToFile(file);
    }
    private void readTimes(File file){
        main.loadTimesFromFile(file);
    }
    public void setFile(File file){
        this.file = file;

        if(file != null) {
            readTimes(file);
        }

    }

    private Rectangle firstChosen;
    private Rectangle secondChosen;

    public void setTimeList(ObservableList<model.Time> timeList){
        this.timeList = timeList;
    }

    @FXML
    private AnchorPane panel;
    @FXML
    private Label label;

    @FXML
    private void handleStartBtn(){
        time = 0;
        isGameStarted = true;
        movesCount = 0;
        first = null;
        second = null;
        firstChosen.setFill(Color.TRANSPARENT);
        secondChosen.setFill(Color.TRANSPARENT);
        timeOfGame = new model.Time();


        Collections.shuffle(tilesList);
        for(Tile tile : tilesList){
            int num = tile.getNum();
            tile.setFill(new ImagePattern(SwingFXUtils.toFXImage(tilesList.get(num).getPart(), null)));
        }

        timeline = new Timeline(new KeyFrame(
                Duration.millis(100),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        updateTime();
                    }
                }
        ));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();




    }

    @FXML
    private void initialize(){

        this.tilesList = CutImage.getTileList(new File("out/production/JavaFX-puzzles/assets/photo1.png"));


        for(Tile tile : tilesList){
            tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(first == null){
                        first = (Tile) event.getSource();
                        firstChosen.setLayoutX(first.getLayoutX() - 3);
                        firstChosen.setLayoutY(first.getLayoutY() - 3);
                        firstChosen.setFill(Color.AQUA);
                    }
                    else if(second == null) {
                        second = (Tile) event.getSource();
                        //double firstToSecondAbs = Math.abs(tilesList.indexOf(first) - tilesList.indexOf(second));
                        if ((first.getLayoutX() == second.getLayoutX() && Math.abs(first.getLayoutY() - second.getLayoutY()) < 150) || (Math.abs(first.getLayoutX() - second.getLayoutX()) < 150 && first.getLayoutY() == second.getLayoutY())){
                            movesCount++;
                            secondChosen.setLayoutX(first.getLayoutX() - 3);
                            secondChosen.setLayoutY(first.getLayoutY() - 3);
                            secondChosen.setFill(Color.AQUA);
                            PathTransition ptr = getPathTransition(first, second);
                            PathTransition ptr2 = getPathTransition(second, first);
                            ParallelTransition pt = new ParallelTransition(ptr, ptr2);
                            firstChosen.setFill(Color.TRANSPARENT);
                            secondChosen.setFill(Color.TRANSPARENT);
                            pt.play();
                            pt.setOnFinished(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    swap();
                                    if (isGameWon()) {
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (isGameStarted) {
                                                    timeline.stop();

                                                    timeList.add(timeOfGame);

                                                    saveTimes(file);

                                                    setWonAlert();
                                                    isGameStarted = false;
                                                }
                                            }
                                         });
                                    }
                                }
                            });
                        } else {
                            second = null;
                        }
                    }
                }
            });
        }


        firstChosen = new Rectangle(106, 106, Color.TRANSPARENT);
        secondChosen = new Rectangle(106, 106, Color.TRANSPARENT);
        panel.getChildren().add(firstChosen);
        panel.getChildren().add(secondChosen);
        panel.getChildren().addAll(tilesList);
    }
    private void swap(){
        int indexOfFirst = tilesList.indexOf(first);
        int indexOfSecond = tilesList.indexOf(second);

        final double xf = first.getLayoutX();
        final double yf = first.getLayoutY();
        final double xs = second.getLayoutX();
        final double ys = second.getLayoutY();

        first.setTranslateX(0);
        first.setTranslateY(0);
        second.setTranslateX(0);
        second.setTranslateY(0);

        first.setLayoutX(xs);
        first.setLayoutY(ys);
        second.setLayoutX(xf);
        second.setLayoutY(yf);

        Collections.swap(tilesList, indexOfFirst, indexOfSecond);
        first = null;
        second = null;
    }
    private boolean isGameWon(){
        for(int i = 0; i < tilesList.size(); i++){
            if(tilesList.get(i).getNum() != i)
                return false;
        }
        return true;
    }
    private void setWonAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wygrana");
        alert.setHeaderText("Udało Ci się ułożyć puzle!");
        alert.setContentText("Jesteś zwycięzcą!!!\nIlość ruchów to: " + movesCount);
        alert.showAndWait();
    }
    private void updateTime(){
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        long millis = time - TimeUnit.SECONDS.toMillis(seconds);
        String timeString = String.format("%02d:%02d:%02d:%d", hours, minutes, seconds, millis);
        label.setText(timeString);
        time += 100;
        timeOfGame.setMillis(millis);
        timeOfGame.setSeconds(seconds);
        timeOfGame.setMinutes(minutes);
        timeOfGame.setHours(hours);
    }
    private PathTransition getPathTransition(Tile first, Tile second){
        PathTransition pathTransition = new PathTransition();

        Path path = new Path();
        path.getElements().clear();
        path.getElements().add(new MoveToAbs(first));
        path.getElements().add(new LineToAbs(first, second.getLayoutX(), second.getLayoutY()));

        pathTransition.setPath(path);
        pathTransition.setNode(first);
        return pathTransition;
    }

    public static class MoveToAbs extends MoveTo{
        public MoveToAbs(Node node){
            super(node.getLayoutBounds().getWidth()/2,node.getLayoutBounds().getHeight()/2);
        }
    }
    public static class LineToAbs extends LineTo{
        public LineToAbs(Node node, double x, double y){
            super(x - node.getLayoutX() + node.getLayoutBounds().getWidth() / 2, y - node.getLayoutY() + node.getLayoutBounds().getHeight() / 2);
        }
    }
}
