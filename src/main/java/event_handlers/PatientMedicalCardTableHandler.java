package event_handlers;

import controllers.app_controllers.MyRecordsController;
import controllers.app_controllers.PatientMedicalCardController;
import controllers.app_objects.PatientMedicalCardTableRow;
import db_connection.HospitalDBConnector;
import db_objects.MedicalRecord;
import db_objects.PatientAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Log4j2
public class PatientMedicalCardTableHandler extends BaseHandler {
    private final PatientMedicalCardController controller;
    private final TableView<PatientMedicalCardTableRow> medcardTable;
    private final TableColumn<PatientMedicalCardTableRow, String> diseaseName;
    private final TableColumn<PatientMedicalCardTableRow, String> diseaseDegree;
    private final TableColumn<PatientMedicalCardTableRow, String> diseaseNote;

    public PatientMedicalCardTableHandler(Parent parent, HospitalDBConnector dbConnector,
                                          PatientMedicalCardController controller,
                                          TableView<PatientMedicalCardTableRow> medcardTable,
                                          TableColumn<PatientMedicalCardTableRow, String> diseaseName,
                                          TableColumn<PatientMedicalCardTableRow, String> diseaseDegree,
                                          TableColumn<PatientMedicalCardTableRow, String> diseaseNote) {
        super(parent, dbConnector);
        this.controller = controller;
        this.medcardTable = medcardTable;
        this.diseaseName = diseaseName;
        this.diseaseDegree = diseaseDegree;
        this.diseaseNote = diseaseNote;
    }

    private ObservableList<PatientMedicalCardTableRow> getPatientMedicalCardTableRowList() throws SQLException {
        ObservableList<PatientMedicalCardTableRow> rows = FXCollections.observableArrayList();
        int cardId = getDbConnector().getMedicalCardId(controller.getCurrentPatient().getId());
        log.info("card id: " + cardId);
        List<MedicalRecord> medicalRecords = getDbConnector().getMedicalRecords(cardId);
        log.info("medicalRecords: " + Arrays.toString(medicalRecords.toArray()));
        for (MedicalRecord record : medicalRecords) {
            PatientMedicalCardTableRow row = new PatientMedicalCardTableRow();
            row.setDiseaseName(record.getDisease().getName());
            row.setDiseaseDegree(record.getDisease().getId());
            row.setNote(record.getNote());
            rows.add(row);
        }
        return rows;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            diseaseName.setCellValueFactory(new PropertyValueFactory<>("diseaseName"));
            diseaseDegree.setCellValueFactory(new PropertyValueFactory<>("diseaseDegree"));
            diseaseNote.setCellValueFactory(new PropertyValueFactory<>("note"));
            medcardTable.setItems(getPatientMedicalCardTableRowList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
