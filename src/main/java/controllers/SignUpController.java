package controllers;

import db_connection.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static constants.DBValues.ACCOUNT_TYPE_DOCTOR;
import static constants.DBValues.ACCOUNT_TYPE_PATIENT;
import static constants.FxmlValues.START_FXML_PATH;

public class SignUpController extends BaseController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField signUpNameField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpPhoneField;

    @FXML
    private TextField signUpPasswordField;

    @FXML
    private Button goToLoginPageButton;

    @FXML
    private RadioButton patientRadiobutton;

    @FXML
    private RadioButton doctorRadiobutton;

    @FXML
    void initialize() {
        goToLoginPageButton.setOnAction(event -> changePage(goToLoginPageButton, START_FXML_PATH));

        signUpButton.setOnAction(event -> {
            Account account = new Account();
            account.setName(signUpNameField.getText());
            account.setPhone(signUpPhoneField.getText());
            account.setPassword(signUpPasswordField.getText());
            if (patientRadiobutton.isSelected()) {
                account.setType(ACCOUNT_TYPE_PATIENT);
            } else if (doctorRadiobutton.isSelected()) {
                account.setType(ACCOUNT_TYPE_DOCTOR);
            }
            try {
                hospitalDBConnector.signUpUser(account);
                showAlert(Alert.AlertType.CONFIRMATION, "Successful", "Account has been created");
            } catch (Error e) {
                showAlert(Alert.AlertType.ERROR, "Can't create an account",
                        "Account with such phone already exists");
                e.printStackTrace();
            }
        });
    }
}
