package controllers.app_controllers;

import controllers.BaseController;
import controllers.app_objects.DiseasesTableRow;
import db_objects.Disease;
import event_handlers.SelectDiseaseTableHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class SelectDiseaseController extends BaseController {
    public static Disease selectedDisease;

    public Disease getSelectedDisease() {
        return selectedDisease;
    }

    public void setSelectedDisease(Disease disease) {
        selectedDisease = disease;
    }

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
    private TableColumn<DiseasesTableRow, String> selectDiseaseColumn;

    @FXML
    void initialize() {
        new SelectDiseaseTableHandler(diseasesTable, getDbConnector(), this, diseasesTable, diseaseName,
                diseaseDegree, selectDiseaseColumn).handle(new ActionEvent());
    }
}
