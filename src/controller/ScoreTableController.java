package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Time;

/**
 * Created by staho on 10.04.2017.
 */
public class ScoreTableController {
    private MainApp main;


    @FXML
    private TableView<Time> timeTable;

    @FXML
    private TableColumn<Time, String> timeColumn;

    @FXML
    private void initialize(){
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeString());

    }


    public void setMain(MainApp main) {
        this.main = main;

        timeTable.setItems(main.getTimeObservableList());
    }


}
