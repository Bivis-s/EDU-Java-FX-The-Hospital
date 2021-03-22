package event_handlers;

import controllers.BaseController;
import db_connection.HospitalDBConnector;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import lombok.extern.log4j.Log4j2;

import static constants.FxmlValues.PICK_THE_DATE_FXML_PATH;

@Log4j2
public class RecordButtonHandler extends BaseHandler {
    private final BaseController baseController;
    private final int patientId;
    private final int doctorId;

    public RecordButtonHandler(Parent parent, HospitalDBConnector dbConnector, BaseController baseController, int patientId, int doctorId) {
        super(parent, dbConnector);
        this.baseController = baseController;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    @Override
    public void handle(ActionEvent event) {
        log.info("Record Patient " + patientId + " to Doctor " + doctorId);
        BaseController.chosenDoctorId = doctorId;
        baseController.openPage(PICK_THE_DATE_FXML_PATH);
    }
}
