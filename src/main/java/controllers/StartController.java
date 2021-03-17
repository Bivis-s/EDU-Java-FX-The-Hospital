package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;

import static constants.FxmlValues.APP_FXML_PATH;
import static constants.FxmlValues.SIGN_UP_FXML_PATH;

@Log4j2
public class StartController extends BaseController {
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

        loginButton.setOnAction(event -> {
            String phone = phoneField.getText().trim();
            String password = passwordField.getText().trim();

            if (hospitalDBConnector.isThereSuchAccount(phone, password)) {
                account = hospitalDBConnector.getAccountInfo(phone, password);
                changePage(loginButton, APP_FXML_PATH);
            } else {
                showAlert(Alert.AlertType.ERROR, "Wrong data", "Wrong data");
            }
        });
    }
}
