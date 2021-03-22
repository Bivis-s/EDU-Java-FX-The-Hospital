package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import event_handlers.SignUpPatientHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import static constants.FxmlValues.LOGIN_FXML_PATH;

public class SignUpPatientController extends BaseController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button goToLoginPageButton;
    @FXML
    private Button signUpPatientButton;
    @FXML
    private TextField signUpPatientNameField;
    @FXML
    private TextField signUpPatientAddressField;
    @FXML
    private DatePicker signUpPatientBirthDate;

    @FXML
    void initialize() {
        goToLoginPageButton.setOnAction(event -> changePage(goToLoginPageButton, LOGIN_FXML_PATH));

        signUpPatientButton.setOnAction(new SignUpPatientHandler(signUpPatientButton, getDbConnector(), this,
                signUpPatientNameField, signUpPatientAddressField, signUpPatientBirthDate));
    }
}
