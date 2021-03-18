package controllers.event_handlers;

import controllers.BaseController;
import db_connection.HospitalDBConnector;
import errors.RecordError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class RecordTimeHandler implements EventHandler<ActionEvent> {
    private final int patientId;
    private final int doctorId;
    private final String date;
    private final String time;
    private final Parent parent;

    public RecordTimeHandler(Parent parent, int patientId, int doctorId, String date, String time) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.parent = parent;
    }

    @Override
    public void handle(ActionEvent event) {
        log.info("Record Patient " + patientId + " to Doctor " + doctorId + " at time " + time + " at date " + date);
        HospitalDBConnector hospitalDBConnector = HospitalDBConnector.getHospitalDBConnector();
        if (hospitalDBConnector.isDoctorsTimeBusy(doctorId, time, date)) {
            BaseController.showAlert(Alert.AlertType.WARNING, "Cannot make record", "Doctor's time is busy");
            throw new RecordError("Cannot make record");
        }
        hospitalDBConnector.createRecord(patientId, doctorId, date, time);
        BaseController.showAlert(Alert.AlertType.CONFIRMATION, "Record made", "All good");
        BaseController.closePage(parent);
    }
}
