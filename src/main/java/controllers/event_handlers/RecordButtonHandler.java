package controllers.event_handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import lombok.extern.log4j.Log4j2;

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
    }
}
