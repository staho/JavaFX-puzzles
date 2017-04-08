package controller;

import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
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

        panel.getChildren().addAll(tilesList);
        for(Tile tile : tilesList){
            tile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(first == null) first = (Tile) event.getSource();
                    else if(second == null) {
                        second = (Tile) event.getSource();
                        swap();
                    }
                }
            });
        }




    }
    private void swap(){
        int indexOfFirst = tilesList.indexOf(first);
        int indexOfSecond = tilesList.indexOf(second);

        Collections.swap(tilesList, indexOfFirst, indexOfSecond);
        first = null;
        second = null;
    }
}
