package event_handlers;

import controllers.app_controllers.DoctorAppController;
import controllers.app_controllers.DoctorMedicalCardController;
import controllers.app_objects.DoctorAppTableRow;
import db_connection.HospitalDBConnector;
import db_objects.DoctorAppointment;
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

import static constants.FxmlValues.DOCTOR_MEDCARD_FXML_PATH;

public class DoctorAppTableHandler extends BaseHandler {
    private final DoctorAppController controller;
    private final TableView<DoctorAppTableRow> doctorTable;
    private final TableColumn<DoctorAppTableRow, String> patientNameColumn;
    private final TableColumn<DoctorAppTableRow, String> patientPhoneColumn;
    private final TableColumn<DoctorAppTableRow, String> appointmentDateColumn;
    private final TableColumn<DoctorAppTableRow, String> appointmentTimeColumn;
    private final TableColumn<DoctorAppTableRow, String> patientCardColumn;

    public DoctorAppTableHandler(Parent parent, HospitalDBConnector dbConnector, DoctorAppController controller,
                                 TableView<DoctorAppTableRow> doctorTable,
                                 TableColumn<DoctorAppTableRow, String> patientNameColumn,
                                 TableColumn<DoctorAppTableRow, String> patientPhoneColumn,
                                 TableColumn<DoctorAppTableRow, String> appointmentDateColumn,
                                 TableColumn<DoctorAppTableRow, String> appointmentTimeColumn,
                                 TableColumn<DoctorAppTableRow, String> patientCardColumn) {
        super(parent, dbConnector);
        this.controller = controller;
        this.doctorTable = doctorTable;
        this.patientNameColumn = patientNameColumn;
        this.patientPhoneColumn = patientPhoneColumn;
        this.appointmentDateColumn = appointmentDateColumn;
        this.appointmentTimeColumn = appointmentTimeColumn;
        this.patientCardColumn = patientCardColumn;
    }

    private Button createOpenCardButton(int cardId) {
        Button button = new Button();
        button.setText("Card");
        button.setOnAction(event -> {
            DoctorMedicalCardController.chosenCardId = cardId;
            controller.openPage(DOCTOR_MEDCARD_FXML_PATH);
        });
        return button;
    }

    private ObservableList<DoctorAppTableRow> createDoctorAppTableRow() throws SQLException {
        ObservableList<DoctorAppTableRow> rows = FXCollections.observableArrayList();
        List<DoctorAppointment> appointments = getDbConnector().getDoctorAppointments(controller.getCurrentDoctor().getId());
        for (DoctorAppointment appointment : appointments) {
            DoctorAppTableRow row = new DoctorAppTableRow();
            row.setPatientName(appointment.getPatientName());
            row.setPatientPhone(getDbConnector().getPatient(appointment.getPatientId()).getAccount().getPhone());
            row.setDate(appointment.getDate());
            row.setTime(appointment.getTime());
            row.setOpenCard(createOpenCardButton(appointment.getCardId()));
            rows.add(row);
        }
        return rows;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
            patientPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("patientPhone"));
            appointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            appointmentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            patientCardColumn.setCellValueFactory(new PropertyValueFactory<>("openCard"));
            doctorTable.setItems(createDoctorAppTableRow());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
