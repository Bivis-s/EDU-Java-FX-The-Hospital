package event_handlers;

import controllers.app_controllers.MyRecordsController;
import controllers.app_objects.MyRecordsTableRow;
import db_connection.HospitalDBConnector;
import db_objects.PatientAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class PatientRecordsTableHandler extends BaseHandler {
    private final MyRecordsController controller;
    private final TableView<MyRecordsTableRow> dateTable;
    private final TableColumn<MyRecordsTableRow, String> dateTableRow;
    private final TableColumn<MyRecordsTableRow, String> timeTableRow;
    private final TableColumn<MyRecordsTableRow, String> doctorNameTableRow;
    private final TableColumn<MyRecordsTableRow, String> doctorSpecializationTableRow;
    private final TableColumn<MyRecordsTableRow, Button> cancelTableRow;

    public PatientRecordsTableHandler(Parent parent, HospitalDBConnector dbConnector, MyRecordsController controller,
                                      TableView<MyRecordsTableRow> dateTable,
                                      TableColumn<MyRecordsTableRow, String> dateTableRow,
                                      TableColumn<MyRecordsTableRow, String> timeTableRow,
                                      TableColumn<MyRecordsTableRow, String> doctorNameTableRow,
                                      TableColumn<MyRecordsTableRow, String> doctorSpecializationTableRow,
                                      TableColumn<MyRecordsTableRow, Button> cancelTableRow) {
        super(parent, dbConnector);
        this.controller = controller;
        this.dateTable = dateTable;
        this.dateTableRow = dateTableRow;
        this.timeTableRow = timeTableRow;
        this.doctorNameTableRow = doctorNameTableRow;
        this.doctorSpecializationTableRow = doctorSpecializationTableRow;
        this.cancelTableRow = cancelTableRow;
    }

    private Button createCancelAppointmentButton(PatientAppointment appointment) {
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(event -> {
            try {
                getDbConnector().deleteAppointment(appointment.getId());
                controller.showAlert(Alert.AlertType.CONFIRMATION, "All good", "Appointment has been deleted");
                handle(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return cancelButton;
    }

    private ObservableList<MyRecordsTableRow> getPatientAppointmentRowList() throws SQLException {
        ObservableList<MyRecordsTableRow> doctorsTableRows = FXCollections.observableArrayList();
        List<PatientAppointment> patientAppointments =
                getDbConnector().getPatientAppointments(controller.getCurrentPatient().getId());
        for (PatientAppointment appointment : patientAppointments) {
            MyRecordsTableRow row = new MyRecordsTableRow();
            row.setDate(appointment.getDate());
            row.setTime(appointment.getTime());
            row.setDoctorsName(appointment.getDoctorName());
            row.setDoctorsType(appointment.getDoctorType());
            row.setCancelAppointmentButton(createCancelAppointmentButton(appointment));
            doctorsTableRows.add(row);
        }
        return doctorsTableRows;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            dateTableRow.setCellValueFactory(new PropertyValueFactory<>("date"));
            timeTableRow.setCellValueFactory(new PropertyValueFactory<>("time"));
            doctorNameTableRow.setCellValueFactory(new PropertyValueFactory<>("doctorsName"));
            doctorSpecializationTableRow.setCellValueFactory(new PropertyValueFactory<>("doctorsType"));
            cancelTableRow.setCellValueFactory(new PropertyValueFactory<>("cancelAppointmentButton"));
            dateTable.setItems(getPatientAppointmentRowList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
