package controllers.app_controllers;

import controllers.BaseController;
import db_objects.Disease;
import event_handlers.AddMedicalRecordHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static constants.FxmlValues.SELECT_DISEASE_FXML_PATH;

public class AddMedicalRecordController extends BaseController {
    public static String noteText;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button addRecord;
    @FXML
    private Button selectDisease;
    @FXML
    private TextArea noteArea;
    @FXML
    private TextField selectedDiseaseField;

    @FXML
    void initialize() {
        selectDisease.setOnAction(event -> {
            String noteText = noteArea.getText();
            if(!noteText.equals("")) {
                AddMedicalRecordController.noteText = noteText;
            }
            changePage(selectDisease, SELECT_DISEASE_FXML_PATH);
        });

        if (noteText != null && !noteText.equals("")) {
            noteArea.setText(noteText);
        }

        Disease selectedDisease = SelectDiseaseController.selectedDisease;
        if (selectedDisease != null) {
            selectedDiseaseField.setText(selectedDisease.getName() + " " + selectedDisease.getDegree());
        }

        addRecord.setOnAction(new AddMedicalRecordHandler(addRecord, getDbConnector(), this, noteArea));
    }
}
