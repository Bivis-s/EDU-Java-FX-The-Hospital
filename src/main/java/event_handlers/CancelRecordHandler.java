package event_handlers;

import controllers.BaseController;
import db_connection.HospitalDBConnector;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.sql.SQLException;

import static constants.FxmlValues.MY_RECORDS_FXML_PATH;

public class CancelRecordHandler extends BaseHandler {
    private final BaseController baseController;
    private final int appointmentId;

    public CancelRecordHandler(Parent parent, HospitalDBConnector dbConnector, BaseController baseController,
                               int appointmentId) {
        super(parent, dbConnector);
        this.baseController = baseController;
        this.appointmentId = appointmentId;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            getDbConnector().deleteAppointment(appointmentId);
            baseController.showAlert(Alert.AlertType.CONFIRMATION, "Record is canceled", "Record is canceled");
            baseController.closePage(getParent());
            baseController.openPage(MY_RECORDS_FXML_PATH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
