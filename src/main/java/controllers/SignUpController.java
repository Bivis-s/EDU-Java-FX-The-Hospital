package controllers;

import event_handlers.SignUpHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static constants.FxmlValues.LOGIN_FXML_PATH;

public class SignUpController extends BaseController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
        goToLoginPageButton.setOnAction(event -> changePage(goToLoginPageButton, LOGIN_FXML_PATH));

        signUpButton.setOnAction(new SignUpHandler(signUpButton, getDbConnector(), this,
                signUpPhoneField, signUpPasswordField, patientRadiobutton.isSelected()));
    }
}
