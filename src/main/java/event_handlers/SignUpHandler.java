package event_handlers;

import controllers.SignUpController;
import db_connection.HospitalDBConnector;
import db_objects.Account;
import errors.IncorrectAccountDataError;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;

import static constants.FxmlValues.SIGN_UP_DOCTOR_PATH;
import static constants.FxmlValues.SIGN_UP_PATIENT_PATH;

@Getter
@Setter
public class SignUpHandler extends BaseHandler {
    private SignUpController controller;
    private TextField phoneField;
    private TextField passwordField;
    private RadioButton patientRadioButton;

    public SignUpHandler(Parent parent, HospitalDBConnector dbConnector, SignUpController controller,
                         TextField phoneField, TextField passwordField, RadioButton patientRadioButton) {
        super(parent, dbConnector);
        this.controller = controller;
        this.phoneField = phoneField;
        this.passwordField = passwordField;
        this.patientRadioButton = patientRadioButton;
    }

    @Override
    public void handle(ActionEvent event) {
        Account account = new Account();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        try {
            account.setPhone(phone);
            account.setPassword(password);
            if (getDbConnector().isThereAccountWithSuchPhone(account.getPhone())) {
                throw new IncorrectAccountDataError("An account with same number is already exists");
            }
            getDbConnector().addAccount(account);
            controller.setCurrentAccount(getDbConnector().getAccount(phone, password));
            if (patientRadioButton.isSelected()) {
                controller.changePage(getParent(), SIGN_UP_PATIENT_PATH);
            } else {
                controller.changePage(getParent(), SIGN_UP_DOCTOR_PATH);
            }
        } catch (IncorrectAccountDataError | SQLException e) {
            controller.showAlert(Alert.AlertType.WARNING, "Can't create an account", e.getMessage());
        }
    }
}
