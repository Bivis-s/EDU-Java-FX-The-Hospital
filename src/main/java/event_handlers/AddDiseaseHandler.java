package event_handlers;

import controllers.app_controllers.AddDiseaseController;
import db_connection.HospitalDBConnector;
import db_objects.Disease;
import errors.IncorrectDataError;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.SQLException;

import static constants.FxmlValues.DISEASES_FXML_PATH;

public class AddDiseaseHandler extends BaseHandler {
    private final AddDiseaseController controller;
    private final TextField diseaseNameField;
    private final TextField diseaseDegreeField;

    public AddDiseaseHandler(Parent parent, HospitalDBConnector dbConnector, AddDiseaseController controller,
                             TextField diseaseNameField, TextField diseaseDegreeField) {
        super(parent, dbConnector);
        this.controller = controller;
        this.diseaseNameField = diseaseNameField;
        this.diseaseDegreeField = diseaseDegreeField;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            Disease disease = new Disease();
            disease.setName(diseaseNameField.getText());
            disease.setDegree(Integer.parseInt(diseaseDegreeField.getText()));
            getDbConnector().addDiseases(disease.getName(), disease.getDegree());
            controller.showAlert(Alert.AlertType.CONFIRMATION, "All good", "Disease has been added");
            controller.changePage(getParent(), DISEASES_FXML_PATH);
        } catch (IncorrectDataError e) {
            e.printStackTrace();
            controller.showAlert(Alert.AlertType.ERROR, "Error",
                    e.getMessage());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            controller.showAlert(Alert.AlertType.ERROR, "Error",
                    "Please, enter into degree field only integer numbers");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
