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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
        //this.tilesList = CutImage.getTileList(new File("out\\production\\JavaFX-puzzles\\assets\\photo.png"));

        this.tilesList = new ArrayList<>(9);
        try {
            BufferedImage image = ImageIO.read(new File("out\\production\\JavaFX-puzzles\\assets\\photo.png"));
            int tileCounter = 0;
            for(int i = 0; i < 3; i++){
                for(int j = 0 ; j < 3; j++){
                    BufferedImage part = image.getSubimage(j*100, i*100, 100, 100);
                    Tile tempTile = new Tile(100, 100, part, tileCounter++);
                    tempTile.setFill(new ImagePattern(SwingFXUtils.toFXImage(tempTile.getPart(),null)));
                    //tempTile.setFill(Color.AZURE);
                    tempTile.setLayoutX(14 + j*110);
                    tempTile.setLayoutY(14 + i*110);
                    tilesList.add(tempTile);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
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
