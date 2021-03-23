package controllers.app_controllers;

import controllers.BaseController;
import controllers.app_objects.DiseasesTableRow;
import event_handlers.DiseasesTableHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

import static constants.FxmlValues.ADD_DISEASE_FXML_PATH;

public class DiseasesController extends BaseController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TableView<DiseasesTableRow> diseasesTable;
    @FXML
    private TableColumn<DiseasesTableRow, String> diseaseName;
    @FXML
    private TableColumn<DiseasesTableRow, String> diseaseDegree;
    @FXML
    private TableColumn<DiseasesTableRow, Button> deleteDiseaseColumn;
    @FXML
    private Button addDisease;

    @FXML
    void initialize() {
        addDisease.setOnAction(event -> changePage(addDisease, ADD_DISEASE_FXML_PATH));

        new DiseasesTableHandler(diseasesTable, getDbConnector(), this, diseasesTable, diseaseName,
                diseaseDegree, deleteDiseaseColumn).handle(new ActionEvent());
    }
}
