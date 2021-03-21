package controllers.event_handlers;

import controllers.BaseController;
import errors.SelfRecordError;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import lombok.extern.log4j.Log4j2;

import static constants.FxmlValues.PICK_THE_DATE_PATH;

@Log4j2
public class RecordButtonHandler implements EventHandler<ActionEvent> {
    private final int patientId;
    private final int doctorId;

    public RecordButtonHandler(int patientId, int doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    @Override
    public void handle(ActionEvent event) {
        log.info("Record Patient " + patientId + " to Doctor " + doctorId);
        BaseController.chosenDoctorAccountId = doctorId;
        if (patientId == doctorId) {
            BaseController.showAlert(Alert.AlertType.WARNING, "You cannot record yourself", "You cannot record yourself");
            throw new SelfRecordError("You cannot record yourself");
        }
        BaseController.openPage(PICK_THE_DATE_PATH);
    }
}
