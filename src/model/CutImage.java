package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.paint.ImagePattern;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by staho on 08.04.2017.
 */
public class CutImage {

    static public List<Tile> getTileList(File file){

        List<Tile> temp = new ArrayList<>(9);
        int tileCounter = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0 ; j < 3; j++){
                BufferedImage image = getImageFromFile(file);
                BufferedImage part = image.getSubimage(j*100, i*100, 100, 100);
                Tile tempTile = new Tile(100, 100, part, tileCounter++);
                tempTile.setFill(new ImagePattern(SwingFXUtils.toFXImage(tempTile.getPart(),null)));
                //tempTile.setFill(Color.AZURE);
                tempTile.setLayoutX(14 + j*110);
                tempTile.setLayoutY(14 + i*110);
                temp.add(tempTile);
            }
        }
        return temp;
    }

    static private BufferedImage getImageFromFile(File file){
        try {
            BufferedImage image = ImageIO.read(file);

            if(image.getHeight() != 300 && image.getWidth() != 300){
                Image tmp = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                BufferedImage img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);

                Graphics2D g2d = img.createGraphics();
                g2d.drawImage(tmp, 0, 0, null);
                g2d.dispose();
                return img;
            }
            return image;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //14 + i * 110 + j *
}
