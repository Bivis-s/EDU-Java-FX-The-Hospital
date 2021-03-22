package event_handlers;

import controllers.BaseController;
import db_connection.HospitalDBConnector;
import errors.RecordError;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import lombok.extern.log4j.Log4j2;

import java.sql.SQLException;

@Log4j2
public class RecordTimeHandler extends BaseHandler {
    private final BaseController baseController;
    private final int patientId;
    private final int doctorId;
    private final String date;
    private final String time;

    public RecordTimeHandler(Parent parent, HospitalDBConnector dbConnector, BaseController baseController,
                             int patientId, int doctorId, String date, String time) {
        super(parent, dbConnector);
        this.baseController = baseController;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            log.info("Record Patient " + patientId + " to Doctor " + doctorId + " at time " + time + " at date " + date);
            if (getDbConnector().isDoctorsTimeBusy(doctorId, time, date)) {
                baseController.showAlert(Alert.AlertType.WARNING, "Cannot make record", "Doctor's time is busy");
                throw new RecordError("Cannot make record");
            }
            getDbConnector().addAppointment(date, time, patientId, doctorId);
            baseController.showAlert(Alert.AlertType.CONFIRMATION, "Record made", "All good");
            baseController.closePage(getParent());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
