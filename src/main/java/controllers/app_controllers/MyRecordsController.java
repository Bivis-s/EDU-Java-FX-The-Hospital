package controllers.app_controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import controllers.BaseController;
import controllers.app_objects.MyRecordsTableRow;
import db_connection.HospitalDBConnector;
import db_objects.PatientAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private TableColumn<MyRecordsTableRow, String> myRecordsDoctorTableRow;

    @FXML
    private TableColumn<MyRecordsTableRow, Button> myRecordsCancelTableRow;

    @FXML
    void initialize() { //TODO подогнать и вынести в хэндлер
        try {
            ObservableList<MyRecordsTableRow> doctorsTableRows = FXCollections.observableArrayList(); // TODO метод создания
                    List<PatientAppointment> patientAppointments =
                            getDbConnector().getPatientAppointments(BaseController.currentPatient.getId());
            myRecordsDateTableRow.setCellValueFactory(new PropertyValueFactory<>("date"));
            myRecordsTimeTableRow.setCellValueFactory(new PropertyValueFactory<>("time"));
            myRecordsDoctorTableRow.setCellValueFactory(new PropertyValueFactory<>("doctorsName"));
            myRecordsCancelTableRow.setCellValueFactory(new PropertyValueFactory<>("cancelRecordButton"));
            dateTable.setItems(doctorsTableRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
