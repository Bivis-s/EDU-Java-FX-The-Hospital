package controllers.app_controllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.BaseController;
import event_handlers.AddDiseaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddDiseaseController extends BaseController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button addDisease;
    @FXML
    private TextField diseaseNameField;
    @FXML
    private TextField diseaseDegreeField;

    @FXML
    public void initialize() {
        addDisease.setOnAction(new AddDiseaseHandler(addDisease, getDbConnector(), this,
                    diseaseNameField, diseaseDegreeField));
    }
}
