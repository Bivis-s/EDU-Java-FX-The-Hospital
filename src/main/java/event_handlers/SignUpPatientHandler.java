package event_handlers;

import controllers.SignUpPatientController;
import db_connection.HospitalDBConnector;
import db_objects.users.Patient;
import errors.IncorrectAccountDataError;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.sql.SQLException;

import static constants.FxmlValues.LOGIN_FXML_PATH;

public class SignUpPatientHandler extends BaseHandler {
    private final SignUpPatientController controller;
    private final TextField nameField;
    private final TextField addressField;
    private final DatePicker birthDatePicker;

    public SignUpPatientHandler(Parent parent, HospitalDBConnector dbConnector, SignUpPatientController controller,
                                TextField nameField, TextField addressField, DatePicker birthDatePicker) {
        super(parent, dbConnector);
        this.controller = controller;
        this.nameField = nameField;
        this.addressField = addressField;
        this.birthDatePicker = birthDatePicker;
    }

    @Override
    public void handle(ActionEvent event) {
        Patient patient = new Patient();
        try {
            patient.setName(nameField.getText());
            patient.setAddress(addressField.getText());
            patient.setDateOfBirth(Date.valueOf(birthDatePicker.getValue()).toString());
            patient.setAccount(controller.getCurrentAccount());
            getDbConnector().addPatient(patient);
            controller.setCurrentPatient(getDbConnector().getPatient(controller.getCurrentAccount().getId()));
            controller.showAlert(Alert.AlertType.CONFIRMATION, "Successful", "Account has been created");
            controller.changePage(getParent(), LOGIN_FXML_PATH);
        } catch (IncorrectAccountDataError | SQLException e) {
            controller.showAlert(Alert.AlertType.WARNING, "Can't create an account", e.getMessage());
        }
    }
}
