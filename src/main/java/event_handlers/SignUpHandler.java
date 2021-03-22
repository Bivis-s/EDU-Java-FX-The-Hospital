package event_handlers;

import controllers.BaseController;
import controllers.SignUpController;
import db_connection.HospitalDBConnector;
import db_objects.Account;
import errors.IncorrectAccountDataError;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;

import static constants.FxmlValues.SIGN_UP_PATIENT_PATH;

@Getter
@Setter
public class SignUpHandler extends BaseHandler {
    private SignUpController controller;
    private TextField phoneField;
    private TextField passwordField;
    private boolean isPatient;

    public SignUpHandler(Parent parent, HospitalDBConnector dbConnector, SignUpController controller,
                         TextField phoneField, TextField passwordField, boolean isPatient) {
        super(parent, dbConnector);
        this.controller = controller;
        this.phoneField = phoneField;
        this.passwordField = passwordField;
        this.isPatient = isPatient;
    }

    @Override
    public void handle(ActionEvent event) {
        Account account = new Account();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        try {
            account.setPhone(phone);
            account.setPassword(password);
            getDbConnector().addAccount(account);
            controller.setCurrentAccount(getDbConnector().getAccount(phone, password));
            if (isPatient) {
                controller.changePage(getParent(), SIGN_UP_PATIENT_PATH);
            } else {
                // TODO create doctor method
            }
        } catch (IncorrectAccountDataError | SQLException e) {
            controller.showAlert(Alert.AlertType.WARNING, "Can't create an account", e.getMessage());
        }

//            try {
//                showAlert(Alert.AlertType.CONFIRMATION, "Successful", "Account has been created");
//                setCurrentUser(signUpPhoneField.getText(), signUpPasswordField.getText());
//                changePage(signUpButton, APP_FXML_PATH);
//            } catch (AccountAlreadyExistsError e) {
//                showAlert(Alert.AlertType.ERROR, "Can't create an account",
//                        "Account with such phone already exists");
//                e.printStackTrace();
//            }
    }
}
