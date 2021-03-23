package controllers;

import controllers.app_objects.RecordTableTimeButtons;
import db_objects.Account;
import event_handlers.RecordTimeHandler;
import db_connection.HospitalDBConnector;
import db_objects.users.Doctor;
import db_objects.users.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import utils.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Log4j2
public abstract class BaseController {
    protected static Account currentAccount;
    protected static Patient currentPatient;
    protected static Doctor currentDoctor;
    public static int chosenDoctorId;

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Account currentAccount) {
        BaseController.currentAccount = currentAccount;
    }

    public Patient getCurrentPatient() {
        return currentPatient;
    }

    public void setCurrentPatient(Patient currentPatient) {
        BaseController.currentPatient = currentPatient;
    }

    public Doctor getCurrentDoctor() {
        return currentDoctor;
    }

    public void setCurrentDoctor(Doctor currentDoctor) {
        BaseController.currentDoctor = currentDoctor;
    }

    @Getter
    private final HospitalDBConnector dbConnector = HospitalDBConnector.getHospitalDBConnector();

    public void openPage(String newPageFxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.class.getResource(newPageFxmlPath));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void closePage(Parent parent) {
        parent.getScene().getWindow().hide();
    }

    public void changePage(Parent oldParent, String newPageFxmlPath) {
        closePage(oldParent);
        openPage(newPageFxmlPath);
    }

    public void showAlert(Alert.AlertType type, String header, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Button createRecordTimeButton(int patientId, int doctorId, String time, String date) throws SQLException {
        boolean isBusy = dbConnector.isDoctorsTimeBusy(doctorId, time, date);
        Button button = new Button(time);
        if (isBusy) {
            button.setText("BUSY");
            button.setDefaultButton(true);
        }
        button.setOnAction(new RecordTimeHandler(button, getDbConnector(), this, patientId, doctorId, date, time));
        return button;
    }

    protected ObservableList<RecordTableTimeButtons> getRecordTableTimeRows(String date) throws SQLException {
        ObservableList<RecordTableTimeButtons> recordTableTimeButtonList = FXCollections.observableArrayList();
        List<String> times1 = Utils.getTimeInRangeWithStep(9, 13, 30);
        List<String> times2 = Utils.getTimeInRangeWithStep(13, 17, 30);
        List<String> times3 = Utils.getTimeInRangeWithStep(17, 21, 30);
        for (int i = 0; i < times1.size(); i++) {
            recordTableTimeButtonList.add(
                    new RecordTableTimeButtons(
                            createRecordTimeButton(currentPatient.getId(), chosenDoctorId, times1.get(i), date),
                            createRecordTimeButton(currentPatient.getId(), chosenDoctorId, times2.get(i), date),
                            createRecordTimeButton(currentPatient.getId(), chosenDoctorId, times3.get(i), date)));
        }
        return recordTableTimeButtonList;
    }
}
