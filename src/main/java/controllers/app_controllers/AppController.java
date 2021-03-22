package controllers.app_controllers;

import controllers.BaseController;
import controllers.app_objects.DoctorsTableRow;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static constants.FxmlValues.MY_RECORDS_PATH;
import static constants.FxmlValues.LOGIN_FXML_PATH;

public class AppController extends BaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text myAccountPhone;

    @FXML
    private Text myAccountType;

    @FXML
    private Text myAccountName;

    @FXML
    private Button myRecords;

    @FXML
    private Button singoutButton;

    @FXML
    private Button settingButton;

    @FXML
    private TableView<DoctorsTableRow> doctorTable;

    @FXML
    private TableColumn<DoctorsTableRow, String> doctorsNameColumn;

    @FXML
    private TableColumn<DoctorsTableRow, String> doctorsPhoneColumn;

    @FXML
    private TableColumn<DoctorsTableRow, Button> recordToDoctorColumn;

    @FXML
    private TextArea appLog;



    @FXML
    void initialize() {
        myAccountName.setText(currentPatient.getName());
        myAccountPhone.setText(currentPatient.getAccount().getPhone());
        myAccountType.setText(currentPatient.getAddress());

        appLog.setEditable(false);

        singoutButton.setOnAction(event -> changePage(singoutButton, LOGIN_FXML_PATH));

        myRecords.setOnAction(event -> openPage(MY_RECORDS_PATH));

        try { // TODO подогнать
            ObservableList<DoctorsTableRow> doctorsTableRows = getDoctorsTableRows();
            doctorsNameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorsName"));
            doctorsPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("doctorsPhone"));
            recordToDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("recordButton"));
            doctorTable.setItems(doctorsTableRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
