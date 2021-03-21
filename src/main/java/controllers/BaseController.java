package controllers;

import controllers.app_objects.DoctorsTableRow;
import controllers.app_objects.MyRecordsTableRow;
import controllers.app_objects.RecordTableTimeButtons;
import controllers.event_handlers.CancelRecordHandler;
import controllers.event_handlers.RecordButtonHandler;
import controllers.event_handlers.RecordTimeHandler;
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
import utils.Utils;

import java.io.IOException;
import java.util.List;

@Log4j2
public abstract class BaseController {
    protected static Account currentUserAccount = new Account();
    protected HospitalDBConnector hospitalDBConnector;
    public static int chosenDoctorAccountId;

    {
        hospitalDBConnector = HospitalDBConnector.getHospitalDBConnector();
    }

    protected void setCurrentUser(String phone, String password) {
        currentUserAccount = hospitalDBConnector.getAccount(phone, password);
    }

    public static void openPage(String newPageFxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BaseController.class.getResource(newPageFxmlPath));
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

    public static void closePage(Parent parent) {
        parent.getScene().getWindow().hide();
    }

    public static void changePage(Parent oldParent, String newPageFxmlPath) {
        closePage(oldParent);
        openPage(newPageFxmlPath);
    }

    public static void showAlert(Alert.AlertType type, String header, String message) {
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

    public Button createRecordTimeButton(String time, int doctorId, String date) {
        boolean isBusy = hospitalDBConnector.isDoctorsTimeBusy(doctorId, time, date);
        Button button = new Button(time);
        if (isBusy) {
            button.setText("BUSY");
            button.setDefaultButton(true);
        }
        button.setOnAction(new RecordTimeHandler(button, currentUserAccount.getId(), doctorId, date, time));
        return button;
    }

    protected ObservableList<RecordTableTimeButtons> getRecordTableTimeRows(String date) {
        ObservableList<RecordTableTimeButtons> recordTableTimeButtonList = FXCollections.observableArrayList();
        List<String> times1 = Utils.getTimeInRangeWithStep(9, 13, 30);
        List<String> times2 = Utils.getTimeInRangeWithStep(13, 17, 30);
        List<String> times3 = Utils.getTimeInRangeWithStep(17, 21, 30);
        for (int i = 0; i < times1.size(); i++) {
            recordTableTimeButtonList.add(
                    new RecordTableTimeButtons(
                            createRecordTimeButton(times1.get(i), chosenDoctorAccountId, date),
                            createRecordTimeButton(times2.get(i), chosenDoctorAccountId, date),
                            createRecordTimeButton(times3.get(i), chosenDoctorAccountId, date)));
        }
        return recordTableTimeButtonList;
    }

    public static Button createCancelRecordButton(int doctorId, String date, String time) {
        Button button = new Button(time);
        button.setText("Cancel");
        button.setOnAction(new CancelRecordHandler(button, currentUserAccount.getId(), doctorId, date, time));
        return button;
    }
}
