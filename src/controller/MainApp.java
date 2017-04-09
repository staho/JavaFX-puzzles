package controller;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.CutImage;
import model.Tile;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by staho on 08.04.2017.
 */
public class MainApp extends Application{
    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Puzzles");
        primaryStage.getIcons().add(new Image("https://is1-ssl.mzstatic.com/image/thumb/Purple111/v4/bc/61/2e/bc612e80-d78e-6659-e4a5-4c7387a28b1e/source/256x256bb.jpg"));

        //URL resource = MainApp.class.getResource("../view/RootLayout.fxml");
        //System.out.println(resource.toString());

        initRootLayout();
        showPuzzleLayout();

    }

    public void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/RootLayout.fxml"));

            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void showPuzzleLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/PuzzleLayout.fxml"));

            AnchorPane anchorPane = loader.load();

            rootLayout.setCenter(anchorPane);
        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public MainApp(){

    }


    public static void main(String[] args){
        launch(args);
    }
}
