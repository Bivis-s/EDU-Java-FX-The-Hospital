package controllers;

import event_handlers.LoginHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static constants.FxmlValues.SIGN_UP_FXML_PATH;

public class LoginController extends BaseController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button singupButton;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField phoneField;

    @FXML
    void initialize() {
        singupButton.setOnAction(event -> changePage(singupButton, SIGN_UP_FXML_PATH));
        loginButton.setOnAction(new LoginHandler(loginButton, getDbConnector(), this,
                phoneField, passwordField));
    }
}
