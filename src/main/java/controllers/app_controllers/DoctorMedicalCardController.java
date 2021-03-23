package controllers.app_controllers;

import controllers.BaseController;
import controllers.app_objects.DoctorMedicalCardTableRow;
import db_objects.users.Patient;
import event_handlers.DoctorMedicalCardTableHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DoctorMedicalCardController extends BaseController {
    public static int chosenCardId;

    public int getChosenCardId() {
        return chosenCardId;
    }

    public void setChosenCardId(int cardId) {
        chosenCardId = cardId;
    }

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableView<DoctorMedicalCardTableRow> medcardTable;
    @FXML
    private TableColumn<DoctorMedicalCardTableRow, String> diseaseName;
    @FXML
    private TableColumn<DoctorMedicalCardTableRow, String> diseaseDegree;
    @FXML
    private TableColumn<DoctorMedicalCardTableRow, String> recordNote;
    @FXML
    private TableColumn<DoctorMedicalCardTableRow, String> deleteRecordColumn;
    @FXML
    private Text nameField;
    @FXML
    private Text birthdayField;
    @FXML
    private Text addressField;
    @FXML
    private Text phoneField;
    @FXML
    private Button addRecordButton;

    @FXML
    void initialize() {
        try {
            Patient patient = getDbConnector()
                    .getPatientById(getDbConnector()
                            .getAccountById(getDbConnector()
                                    .getPatientIdByMedicalCardId(getChosenCardId()))
                            .getId());
            nameField.setText(patient.getName());
            birthdayField.setText(patient.getDateOfBirth());
            addressField.setText(patient.getAddress());
            phoneField.setText(patient.getAccount().getPhone());
//            TODO ADD ADDING RECORDS
//            addRecordButton.setOnAction(event ->
//                openPage()
//            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        new DoctorMedicalCardTableHandler(medcardTable, getDbConnector(), this,
                medcardTable, diseaseName, diseaseDegree, recordNote, deleteRecordColumn).handle(new ActionEvent());
    }
}
