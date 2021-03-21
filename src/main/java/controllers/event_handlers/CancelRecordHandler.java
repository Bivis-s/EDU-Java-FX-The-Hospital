package controllers.event_handlers;

import controllers.BaseController;
import db_connection.HospitalDBConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import static constants.FxmlValues.MY_RECORDS_PATH;

public class CancelRecordHandler implements EventHandler<ActionEvent> {
    private final Parent parent;
    private final String date;
    private final String time;
    private final int patientId;
    private final int doctorId;

    public CancelRecordHandler(Parent parent, int patientId, int doctorId, String date, String time) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.parent = parent;
    }

    @Override
    public void handle(ActionEvent event) {
        HospitalDBConnector hospitalDBConnector = HospitalDBConnector.getHospitalDBConnector();
        hospitalDBConnector.removeRecord(patientId, doctorId, date, time);
        BaseController.showAlert(Alert.AlertType.CONFIRMATION, "Record is canceled", "Record is canceled");
        BaseController.closePage(parent);
        BaseController.openPage(MY_RECORDS_PATH);
    }
}
