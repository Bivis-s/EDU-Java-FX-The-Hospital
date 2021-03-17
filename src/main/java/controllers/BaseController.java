package controllers;

import controllers.app_objects.DoctorsTableRow;
import controllers.event_handlers.RecordButtonHandler;
import db_connection.Account;
import db_connection.HospitalDBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;

@Log4j2
public abstract class BaseController {
    protected static Account currentUserAccount = new Account();
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

    protected ObservableList<Account> getDoctorsAccounts() {
        ObservableList<Account> accountObservableList = FXCollections.observableArrayList();
        accountObservableList.addAll(hospitalDBConnector.getDoctorsAccountList());
        return accountObservableList;
    }

    protected ObservableList<DoctorsTableRow> getDoctorsTableRows() {
        ObservableList<DoctorsTableRow> doctorsTableRows = FXCollections.observableArrayList();
        List<Account> accountList = hospitalDBConnector.getDoctorsAccountList();
        for (Account doctorAccount : accountList) {
            DoctorsTableRow doctorsTableRow = new DoctorsTableRow();
            doctorsTableRow.setDoctorsName(doctorAccount.getName());
            doctorsTableRow.setDoctorsPhone(doctorAccount.getPhone());
            Button recordButton = new Button("Record");
            recordButton.setDefaultButton(true);
            recordButton.setId("record_to_doctor_with_id_" + doctorAccount.getId());
            recordButton.setOnAction(new RecordButtonHandler(currentUserAccount.getId(), doctorAccount.getId()));
            doctorsTableRow.setRecordButton(recordButton);
            doctorsTableRows.add(doctorsTableRow);
        }
        return doctorsTableRows;
    }
}
