package controllers.app_controllers;

import controllers.BaseController;
import controllers.app_objects.RecordTableTimeButtons;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PickTheDateController extends BaseController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button chooseDateRecordButton;

    @FXML
    private DatePicker dateSelector;

    @FXML
    private TableView<RecordTableTimeButtons> dateTable;

    @FXML
    private TableColumn<RecordTableTimeButtons, Button> dateTableFirstColumn;

    @FXML
    private TableColumn<RecordTableTimeButtons, Button> dateTableSecondColumn;

    @FXML
    private TableColumn<RecordTableTimeButtons, Button> dateTableThirdColumn;

    @FXML
    void initialize() {
        dateSelector.setOnAction(event -> {
            ObservableList<RecordTableTimeButtons> recordTableTimeRows;
            try {
                recordTableTimeRows = getRecordTableTimeRows(dateSelector.getValue().toString());
                dateTableFirstColumn.setCellValueFactory(new PropertyValueFactory<>("firstRecordButtons"));
                dateTableSecondColumn.setCellValueFactory(new PropertyValueFactory<>("secondRecordButtons"));
                dateTableThirdColumn.setCellValueFactory(new PropertyValueFactory<>("thirdRecordButtons"));
                dateTable.setItems(recordTableTimeRows);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}

