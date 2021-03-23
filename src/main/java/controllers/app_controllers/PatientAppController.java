package controllers.app_controllers;

import controllers.BaseController;
import controllers.app_objects.PatientAppTableRow;
import event_handlers.PatientAppTableHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static constants.FxmlValues.*;

public class PatientAppController extends BaseController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Text myAccountPhone;
    @FXML
    private Text myAccountBirthDate;
    @FXML
    private Button myRecords;
    @FXML
    private Button myMedicalCard;
    @FXML
    private Text myAccountName;
    @FXML
    private Button signoutButton;
    @FXML
    private TableView<PatientAppTableRow> doctorTable;
    @FXML
    private TableColumn<PatientAppTableRow, String> doctorsNameColumn;
    @FXML
    private TableColumn<PatientAppTableRow, String> doctorsPhoneColumn;
    @FXML
    private TableColumn<PatientAppTableRow, String> doctorsTypeColumn;
    @FXML
    private TableColumn<PatientAppTableRow, Button> appointDoctorColumn;

    @FXML
    void initialize() {
        myAccountName.setText(currentPatient.getName());
        myAccountPhone.setText(currentPatient.getAccount().getPhone());
        myAccountBirthDate.setText(currentPatient.getDateOfBirth());

        signoutButton.setOnAction(event -> changePage(signoutButton, LOGIN_FXML_PATH));

        myRecords.setOnAction(event -> openPage(MY_RECORDS_FXML_PATH));

        myMedicalCard.setOnAction(event -> openPage(PATIENT_MEDCARD_FXML_PATH));

        new PatientAppTableHandler(doctorTable, getDbConnector(), this, doctorTable, doctorsNameColumn,
                doctorsPhoneColumn, doctorsTypeColumn, appointDoctorColumn).handle(new ActionEvent());
    }
}
