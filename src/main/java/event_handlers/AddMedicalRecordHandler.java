package event_handlers;

import controllers.app_controllers.AddMedicalRecordController;
import controllers.app_controllers.DoctorMedicalCardController;
import controllers.app_controllers.SelectDiseaseController;
import db_connection.HospitalDBConnector;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.sql.SQLException;

import static constants.FxmlValues.DOCTOR_MEDCARD_FXML_PATH;

public class AddMedicalRecordHandler extends BaseHandler {
    private final AddMedicalRecordController controller;
    private final TextArea noteArea;

    public AddMedicalRecordHandler(Parent parent, HospitalDBConnector dbConnector,
                                   AddMedicalRecordController controller, TextArea noteArea) {
        super(parent, dbConnector);
        this.controller = controller;
        this.noteArea = noteArea;
    }

    private void addMedicalRecordToDb(int cardId, String note, int diseaseId) throws SQLException {
        getDbConnector().addMedicalRecord(note, diseaseId);
        int lastRecord = getDbConnector().getLastMedicalRecordId();
        getDbConnector().addMedicalRecordToCard(cardId, lastRecord);
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            addMedicalRecordToDb(DoctorMedicalCardController.chosenCardId,
                    noteArea.getText(), SelectDiseaseController.selectedDisease.getId());
            controller.showAlert(Alert.AlertType.CONFIRMATION, "Successful", "Medical record has been added");
            AddMedicalRecordController.noteText = null;
            SelectDiseaseController.selectedDisease = null;
            controller.changePage(getParent(), DOCTOR_MEDCARD_FXML_PATH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
