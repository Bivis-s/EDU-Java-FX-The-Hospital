package event_handlers;

import controllers.SignUpDoctorController;
import db_connection.HospitalDBConnector;
import db_objects.users.Doctor;
import errors.IncorrectDataError;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

import static constants.FxmlValues.LOGIN_FXML_PATH;

public class SignUpDoctortHandler extends BaseHandler {
    private final SignUpDoctorController controller;
    private final TextField nameField;
    private final TextField typeField;

    public SignUpDoctortHandler(Parent parent, HospitalDBConnector dbConnector, SignUpDoctorController controller,
            TextField nameField, TextField typeField) {
        super(parent, dbConnector);
        this.controller = controller;
        this.nameField = nameField;
        this.typeField = typeField;
    }

    @Override
    public void handle(ActionEvent event) {
        Doctor doctor = new Doctor();
        try {
            doctor.setName(nameField.getText());
            doctor.setType(typeField.getText());
            doctor.setAccount(controller.getCurrentAccount());
            getDbConnector().addDoctor(doctor);
            controller.setCurrentDoctor(getDbConnector().getDoctor(controller.getCurrentAccount().getId()));
            controller.showAlert(Alert.AlertType.CONFIRMATION, "Successful", "Account has been created");
            controller.changePage(getParent(), LOGIN_FXML_PATH);
        } catch (IncorrectDataError | SQLException e) {
            e.printStackTrace();
            controller.showAlert(Alert.AlertType.WARNING, "Can't create an account", e.getMessage());
        }
    }
}
