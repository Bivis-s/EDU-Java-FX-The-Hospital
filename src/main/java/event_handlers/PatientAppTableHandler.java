package event_handlers;

import controllers.app_controllers.PatientAppController;
import controllers.app_objects.PatientAppTableRow;
import db_connection.HospitalDBConnector;
import db_objects.users.Doctor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class PatientAppTableHandler extends BaseHandler {
    private final PatientAppController controller;
    private final TableView<PatientAppTableRow> doctorTable;
    private final TableColumn<PatientAppTableRow, String> doctorsNameColumn;
    private final TableColumn<PatientAppTableRow, String> doctorsPhoneColumn;
    private final TableColumn<PatientAppTableRow, String> doctorsTypeColumn;
    private final TableColumn<PatientAppTableRow, Button> recordToDoctorColumn;

    public PatientAppTableHandler(Parent parent, HospitalDBConnector dbConnector, PatientAppController controller,
                                  TableView<PatientAppTableRow> doctorTable,
                                  TableColumn<PatientAppTableRow, String> doctorsNameColumn,
                                  TableColumn<PatientAppTableRow, String> doctorsPhoneColumn,
                                  TableColumn<PatientAppTableRow, String> doctorsTypeColumn,
                                  TableColumn<PatientAppTableRow, Button> recordToDoctorColumn) {
        super(parent, dbConnector);
        this.controller = controller;
        this.doctorTable = doctorTable;
        this.doctorsNameColumn = doctorsNameColumn;
        this.doctorsPhoneColumn = doctorsPhoneColumn;
        this.doctorsTypeColumn = doctorsTypeColumn;
        this.recordToDoctorColumn = recordToDoctorColumn;
    }

    private ObservableList<PatientAppTableRow> createPatientAppTableRows() throws SQLException {
        ObservableList<PatientAppTableRow> patientAppTableRows = FXCollections.observableArrayList();
        List<Doctor> doctorList = getDbConnector().getDoctorList();
        for (Doctor doctor : doctorList) {
            PatientAppTableRow patientAppTableRow = new PatientAppTableRow();
            patientAppTableRow.setDoctorsName(doctor.getName());
            patientAppTableRow.setDoctorsPhone(doctor.getAccount().getPhone());
            patientAppTableRow.setDoctorsType(doctor.getType());
            Button recordButton = new Button("Record");
            recordButton.setDefaultButton(true);
            recordButton.setOnAction(new RecordButtonHandler(recordButton, getDbConnector(), controller,
                    controller.getCurrentPatient().getId(), doctor.getId()));
            patientAppTableRow.setRecordButton(recordButton);
            patientAppTableRows.add(patientAppTableRow);
        }
        return patientAppTableRows;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            ObservableList<PatientAppTableRow> patientAppTableRows = createPatientAppTableRows();
            doctorsNameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorsName"));
            doctorsPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("doctorsPhone"));
            doctorsTypeColumn.setCellValueFactory(new PropertyValueFactory<>("doctorsType"));
            recordToDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("recordButton"));
            doctorTable.setItems(patientAppTableRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
