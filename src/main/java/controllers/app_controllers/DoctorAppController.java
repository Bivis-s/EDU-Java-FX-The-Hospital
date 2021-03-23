package controllers.app_controllers;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.BaseController;
import controllers.app_objects.DoctorAppTableRow;
import event_handlers.DoctorAppTableHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import static constants.FxmlValues.LOGIN_FXML_PATH;

public class DoctorAppController extends BaseController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button signoutButton;
    @FXML
    private Button settingButton;
    @FXML
    private TableView<DoctorAppTableRow> doctorTable;
    @FXML
    private TableColumn<DoctorAppTableRow, String> patientNameColumn;
    @FXML
    private TableColumn<DoctorAppTableRow, String> patientPhoneColumn;
    @FXML
    private TableColumn<DoctorAppTableRow, String> appointmentDateColumn;
    @FXML
    private TableColumn<DoctorAppTableRow, String> appointmentTimeColumn;
    @FXML
    private TableColumn<DoctorAppTableRow, String> patientCardColumn;
    @FXML
    private Text nameText;
    @FXML
    private Text phoneText;
    @FXML
    private Text typeText;
    @FXML
    private Button diseasesButton;
    @FXML
    private TextArea appLog;

    @FXML
    void initialize() {
        nameText.setText(getCurrentDoctor().getName());
        phoneText.setText(getCurrentDoctor().getAccount().getPhone());
        typeText.setText(getCurrentDoctor().getType());

        signoutButton.setOnAction(event -> changePage(signoutButton, LOGIN_FXML_PATH));

        new DoctorAppTableHandler(doctorTable, getDbConnector(), this, doctorTable,
                patientNameColumn, patientPhoneColumn, appointmentDateColumn, appointmentTimeColumn,
                patientCardColumn).handle(new ActionEvent());
    }
}
