package controllers;

import event_handlers.SignUpDoctortHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static constants.FxmlValues.LOGIN_FXML_PATH;

public class SignUpDoctorController extends BaseController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button goToLoginPageButton;
    @FXML
    private Button signUpDoctorButton;
    @FXML
    private TextField signupDoctorNameField;
    @FXML
    private TextField signUpDoctorTypeField;

    @FXML
    void initialize() {
        goToLoginPageButton.setOnAction(event -> changePage(goToLoginPageButton, LOGIN_FXML_PATH));

        signUpDoctorButton.setOnAction(new SignUpDoctortHandler(signUpDoctorButton, getDbConnector(), this,
                signupDoctorNameField, signUpDoctorTypeField));
    }
}
