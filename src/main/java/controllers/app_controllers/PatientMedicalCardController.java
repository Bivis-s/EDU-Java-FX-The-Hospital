package controllers.app_controllers;

import controllers.BaseController;
import controllers.app_objects.PatientMedicalCardTableRow;
import event_handlers.PatientMedicalCardTableHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientMedicalCardController extends BaseController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableView<PatientMedicalCardTableRow> medcardTable;
    @FXML
    private TableColumn<PatientMedicalCardTableRow, String> diseaseName;
    @FXML
    private TableColumn<PatientMedicalCardTableRow, String> diseaseDegree;
    @FXML
    private TableColumn<PatientMedicalCardTableRow, String> diseaseNote;
    @FXML
    private Text nameField;
    @FXML
    private Text birthdayField;
    @FXML
    private Text addressField;
    @FXML
    private Text phoneField;

    @FXML
    void initialize() {
        nameField.setText(getCurrentPatient().getName());
        birthdayField.setText(getCurrentPatient().getDateOfBirth());
        addressField.setText(getCurrentPatient().getAddress());
        phoneField.setText(getCurrentPatient().getAccount().getPhone());

        new PatientMedicalCardTableHandler(medcardTable, getDbConnector(), this,
                medcardTable, diseaseName, diseaseDegree, diseaseNote).handle(new ActionEvent());
    }
}
