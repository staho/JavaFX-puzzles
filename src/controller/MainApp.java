package controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.CutImage;
import model.Tile;
import model.Time;
import model.TimeTableWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
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
    private File file;

    public ObservableList<Time> getTimeObservableList() {
        return timeObservableList;
    }

    private ObservableList<Time> timeObservableList = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Puzzles");
        primaryStage.getIcons().add(new Image("https://is1-ssl.mzstatic.com/image/thumb/Purple111/v4/bc/61/2e/bc612e80-d78e-6659-e4a5-4c7387a28b1e/source/256x256bb.jpg"));
        this.file = new File("out/production/JavaFX-puzzles/times.xml");
        //URL resource = MainApp.class.getResource("../view/RootLayout.fxml");
        //System.out.println(resource.toString());

        initRootLayout();
        showPuzzleLayout();
        showTimeTableLayout();

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

            PuzzleController controller = loader.getController();
            controller.setTimeList(timeObservableList);
            controller.setMain(this);
            controller.setFile(file);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void showTimeTableLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/ScoreTableLayout.fxml"));
            //timeObservableList.add(new Time());

            AnchorPane anchorPane = loader.load();

            rootLayout.setRight(anchorPane);
            ScoreTableController controller = loader.getController();
            controller.setMain(this);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveTimesToFile(File file){
        try{
            JAXBContext context = JAXBContext.newInstance(TimeTableWrapper.class);

            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            TimeTableWrapper wrapper = new TimeTableWrapper();
            wrapper.setTimeList(timeObservableList);

            //File file = new File("../scores.txt");
            m.marshal(wrapper,file);


        }catch(JAXBException e){
            e.printStackTrace();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();

        }
    }
    public void loadTimesFromFile(File file){
        try {
            JAXBContext context = JAXBContext
                    .newInstance(TimeTableWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            TimeTableWrapper wrapper = (TimeTableWrapper) um.unmarshal(file);

            timeObservableList.clear();
            timeObservableList.addAll(wrapper.getTimeList());


        }catch(JAXBException e){
            e.printStackTrace();
        }
        catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }


    public MainApp(){

    }


    public static void main(String[] args){
        launch(args);
    }
}
