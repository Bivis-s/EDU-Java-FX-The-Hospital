package event_handlers;

import controllers.app_controllers.SelectDiseaseController;
import controllers.app_objects.DiseasesTableRow;
import db_connection.HospitalDBConnector;
import db_objects.Disease;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

import static constants.FxmlValues.ADD_MEDICAL_RECORD_FXML_PATH;

public class SelectDiseaseTableHandler extends BaseHandler {
    private final SelectDiseaseController controller;
    private final TableView<DiseasesTableRow> diseasesTable;
    private final TableColumn<DiseasesTableRow, String> diseaseName;
    private final TableColumn<DiseasesTableRow, String> diseaseDegree;
    private final TableColumn<DiseasesTableRow, String> selectDiseaseColumn;

    public SelectDiseaseTableHandler(Parent parent, HospitalDBConnector dbConnector, SelectDiseaseController controller,
                                     TableView<DiseasesTableRow> diseasesTable,
                                     TableColumn<DiseasesTableRow, String> diseaseName,
                                     TableColumn<DiseasesTableRow, String> diseaseDegree,
                                     TableColumn<DiseasesTableRow, String> selectDiseaseColumn) {
        super(parent, dbConnector);
        this.controller = controller;
        this.diseasesTable = diseasesTable;
        this.diseaseName = diseaseName;
        this.diseaseDegree = diseaseDegree;
        this.selectDiseaseColumn = selectDiseaseColumn;
    }

    private Button createSelectDiseaseButton(int diseaseId) {
        Button button = new Button();
        button.setText("Select");
        button.setOnAction(event -> {
            try {
                controller.setSelectedDisease(getDbConnector().getDisease(diseaseId));
                controller.showAlert(Alert.AlertType.CONFIRMATION, "All good", "Disease has been selected");
                controller.changePage(getParent(), ADD_MEDICAL_RECORD_FXML_PATH);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return button;
    }

    private ObservableList<DiseasesTableRow> createDiseasesTableRowList() throws SQLException {
        ObservableList<DiseasesTableRow> rows = FXCollections.observableArrayList();
        List<Disease> diseases = getDbConnector().getDiseasesList();
        for (Disease disease : diseases) {
            DiseasesTableRow row = new DiseasesTableRow();
            row.setName(disease.getName());
            row.setDegree(disease.getDegree());
            row.setButton(createSelectDiseaseButton(disease.getId()));
            rows.add(row);
        }
        return rows;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            diseaseName.setCellValueFactory(new PropertyValueFactory<>("name"));
            diseaseDegree.setCellValueFactory(new PropertyValueFactory<>("degree"));
            selectDiseaseColumn.setCellValueFactory(new PropertyValueFactory<>("button"));
            diseasesTable.setItems(createDiseasesTableRowList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
