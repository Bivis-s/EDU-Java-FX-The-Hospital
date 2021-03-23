package event_handlers;

import controllers.app_controllers.DoctorMedicalCardController;
import controllers.app_objects.DoctorMedicalCardTableRow;
import db_connection.HospitalDBConnector;
import db_objects.MedicalRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.sql.SQLException;
import java.util.List;

@Getter
@Setter
@Log4j2
public class DoctorMedicalCardTableHandler extends BaseHandler {
    private final DoctorMedicalCardController controller;
    private final TableView<DoctorMedicalCardTableRow> medcardTable;
    private final TableColumn<DoctorMedicalCardTableRow, String> diseaseName;
    private final TableColumn<DoctorMedicalCardTableRow, String> diseaseDegree;
    private final TableColumn<DoctorMedicalCardTableRow, String> recordNote;
    private final TableColumn<DoctorMedicalCardTableRow, String> deleteRecordColumn;

    public DoctorMedicalCardTableHandler(Parent parent, HospitalDBConnector dbConnector,
                                         DoctorMedicalCardController controller,
                                         TableView<DoctorMedicalCardTableRow> medcardTable,
                                         TableColumn<DoctorMedicalCardTableRow, String> diseaseName,
                                         TableColumn<DoctorMedicalCardTableRow, String> diseaseDegree,
                                         TableColumn<DoctorMedicalCardTableRow, String> recordNote,
                                         TableColumn<DoctorMedicalCardTableRow, String> deleteRecordColumn) {
        super(parent, dbConnector);
        this.controller = controller;
        this.medcardTable = medcardTable;
        this.diseaseName = diseaseName;
        this.diseaseDegree = diseaseDegree;
        this.recordNote = recordNote;
        this.deleteRecordColumn = deleteRecordColumn;
    }

    private Button createDeleteRecordButton(int recordId) {
        Button button = new Button();
        button.setText("Delete");
        button.setOnAction(event -> {
            try {
                getDbConnector().deleteMedicalCardRecordById(recordId);
                controller.showAlert(Alert.AlertType.CONFIRMATION, "All good", "The record was deleted");
                handle(new ActionEvent());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return button;
    }

    private ObservableList<DoctorMedicalCardTableRow> getDoctorMedicalCardTableRowList() throws SQLException {
        ObservableList<DoctorMedicalCardTableRow> rows = FXCollections.observableArrayList();
        int cardId = controller.getChosenCardId();
        log.info("card id: " + cardId);
        List<MedicalRecord> medicalRecords = getDbConnector().getMedicalRecords(cardId);
        for (MedicalRecord record : medicalRecords) {
            DoctorMedicalCardTableRow row = new DoctorMedicalCardTableRow();
            row.setDiseaseName(record.getDisease().getName());
            row.setDiseaseDegree(record.getDisease().getId());
            row.setRecordNote(record.getNote());
            row.setDeleteRecord(createDeleteRecordButton(record.getId()));
            rows.add(row);
        }
        return rows;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            diseaseName.setCellValueFactory(new PropertyValueFactory<>("diseaseName"));
            diseaseDegree.setCellValueFactory(new PropertyValueFactory<>("diseaseDegree"));
            recordNote.setCellValueFactory(new PropertyValueFactory<>("recordNote"));
            deleteRecordColumn.setCellValueFactory(new PropertyValueFactory<>("deleteRecord"));
            medcardTable.setItems(getDoctorMedicalCardTableRowList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
