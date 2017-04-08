package model;

import javafx.scene.shape.*;

import java.awt.image.BufferedImage;


/**
 * Created by staho on 08.04.2017.
 */
public class Tile extends javafx.scene.shape.Rectangle {
    private BufferedImage part;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public BufferedImage getPart() {
        return part;
    }

    public void setPart(BufferedImage part) {
        this.part = part;
    }



    public Tile(double width, double height, BufferedImage part, int num){
        super(width, height);
        this.part = part;
        this.num = num;
    }

    public Tile(BufferedImage part, int num){
        this.part = part;
        this.num = num;
    }

}
