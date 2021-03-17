package controllers;

import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controllers.app_objects.DoctorsTableRow;
import db_connection.Account;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import static constants.DBValues.ACCOUNTS_NAME_COLUMN_NAME;
import static constants.DBValues.ACCOUNTS_PHONE_COLUMN_NAME;
import static constants.FxmlValues.APP_FXML_PATH;
import static constants.FxmlValues.START_FXML_PATH;

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
    private TableView<DoctorsTableRow> doctorTable;

    @FXML
    private TableColumn<DoctorsTableRow, String> doctorsNameColumn;

    @FXML
    private TableColumn<DoctorsTableRow, String> doctorsPhoneColumn;

    @FXML
    private TableColumn<DoctorsTableRow, Button> recordToDoctorColumn;

    @FXML
    void initialize() {
        singoutButton.setOnAction(event -> changePage(singoutButton, START_FXML_PATH));

        ObservableList<DoctorsTableRow> doctorsTableRows = super.getDoctorsTableRows();
        doctorsNameColumn.setCellValueFactory(new PropertyValueFactory<>("doctorsName"));
        doctorsPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("doctorsPhone"));
        recordToDoctorColumn.setCellValueFactory(new PropertyValueFactory<>("recordButton"));
        doctorTable.setItems(doctorsTableRows);
    }
}
