package controllers.app_controllers;

import controllers.BaseController;
import controllers.app_objects.MyRecordsTableRow;
import event_handlers.PatientRecordsTableHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MyRecordsController extends BaseController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableView<MyRecordsTableRow> dateTable;
    @FXML
    private TableColumn<MyRecordsTableRow, String> myRecordsDateTableRow;
    @FXML
    private TableColumn<MyRecordsTableRow, String> myRecordsTimeTableRow;
    @FXML
    private TableColumn<MyRecordsTableRow, String> myRecordsDoctorNameTableRow;
    @FXML
    private TableColumn<MyRecordsTableRow, String> myRecordsDoctorSpecializationTableRow;
    @FXML
    private TableColumn<MyRecordsTableRow, Button> myRecordsCancelTableRow;

    @FXML
    void initialize() {
        new PatientRecordsTableHandler(dateTable, getDbConnector(), this,
                dateTable, myRecordsDateTableRow, myRecordsTimeTableRow, myRecordsDoctorNameTableRow,
                myRecordsDoctorSpecializationTableRow, myRecordsCancelTableRow).handle(new ActionEvent());
    }
}
