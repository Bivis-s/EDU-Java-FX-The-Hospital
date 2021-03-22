package event_handlers;

import controllers.LoginController;
import db_connection.HospitalDBConnector;
import db_objects.Account;
import errors.NoSuchAccountError;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;

import static constants.FxmlValues.DOCTOR_APP_FXML_PATH;
import static constants.FxmlValues.PATIENT_APP_FXML_PATH;

@Getter
@Setter
public class LoginHandler extends BaseHandler {
    private LoginController loginController;
    private TextField phoneField;
    private TextField passwordField;

    public LoginHandler(Parent parent, HospitalDBConnector dbConnector, LoginController loginController,
                        TextField phoneField, TextField passwordField) {
        super(parent, dbConnector);
        this.loginController = loginController;
        this.phoneField = phoneField;
        this.passwordField = passwordField;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            Account account = getDbConnector().getAccount(phoneField.getText().trim(), passwordField.getText().trim());
            if (getDbConnector().isPatientsAccount(account)) {
                loginController.setCurrentPatient(getDbConnector().getPatient(account.getId()));
                loginController.changePage(getParent(), PATIENT_APP_FXML_PATH);
            } else if (getDbConnector().isDoctorsAccount(account)) {
                loginController.setCurrentDoctor(getDbConnector().getDoctor(account.getId()));
                loginController.changePage(getParent(), DOCTOR_APP_FXML_PATH);
            }
        } catch (NoSuchAccountError e) {
            e.printStackTrace();
            loginController.showAlert(Alert.AlertType.ERROR, e.getMessage(), e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
