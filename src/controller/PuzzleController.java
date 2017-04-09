package controller;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import model.CutImage;
import model.Tile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by staho on 08.04.2017.
 */
public class PuzzleController {

    private List<Tile> tilesList;
    private Tile first = null;
    private Tile second = null;

    @FXML
    private AnchorPane panel;

    @FXML
    private void handleStartBtn(){
        Collections.shuffle(tilesList);
        for(Tile tile : tilesList){
            int num = tile.getNum();
            tile.setFill(new ImagePattern(SwingFXUtils.toFXImage(tilesList.get(num).getPart(), null)));
        }

    }

    public void setTilesList(List<Tile> tilesList) {
        this.tilesList = tilesList;
    }

    @FXML
    private void initialize(){
        this.tilesList = CutImage.getTileList(new File("out\\production\\JavaFX-puzzles\\assets\\photo.png"));


        for(Tile tile : tilesList){
            tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(first == null) first = (Tile) event.getSource();
                    else if(second == null) {
                        second = (Tile) event.getSource();
                        swap();
                        if(isGameWon()){
                            setWonAlert();
                        }
                    }
                }
            });
        }



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
        alert.setContentText("Jesteś zwycięzcą!!!");
        alert.showAndWait();
    }

}
