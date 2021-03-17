package controllers;

import db_connection.Account;
import db_connection.HospitalDBConnector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseController {
    protected Account account;
    protected HospitalDBConnector hospitalDBConnector;

    {
        hospitalDBConnector = HospitalDBConnector.getHospitalDBConnector();
    }

    protected void changePage(Parent oldParent, String newPageFxmlPath) {
        oldParent.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(newPageFxmlPath));
        try {
            loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    protected void showAlert(Alert.AlertType type, String header, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
