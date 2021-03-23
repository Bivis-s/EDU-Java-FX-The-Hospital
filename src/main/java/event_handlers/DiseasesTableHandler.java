package event_handlers;

import controllers.app_controllers.DiseasesController;
import controllers.app_objects.DiseasesTableRow;
import controllers.app_objects.DoctorAppTableRow;
import db_connection.HospitalDBConnector;
import db_objects.Disease;
import db_objects.DoctorAppointment;
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

public class DiseasesTableHandler extends BaseHandler {
    private final DiseasesController controller;
    private final TableView<DiseasesTableRow> diseasesTable;
    private final TableColumn<DiseasesTableRow, String> diseaseName;
    private final TableColumn<DiseasesTableRow, String> diseaseDegree;
    private final TableColumn<DiseasesTableRow, Button> deleteDiseaseColumn;

    public DiseasesTableHandler(Parent parent, HospitalDBConnector dbConnector, DiseasesController controller,
                                TableView<DiseasesTableRow> diseasesTable,
                                TableColumn<DiseasesTableRow, String> diseaseName,
                                TableColumn<DiseasesTableRow, String> diseaseDegree,
                                TableColumn<DiseasesTableRow, Button> deleteDiseaseColumn) {
        super(parent, dbConnector);
        this.controller = controller;
        this.diseasesTable = diseasesTable;
        this.diseaseName = diseaseName;
        this.diseaseDegree = diseaseDegree;
        this.deleteDiseaseColumn = deleteDiseaseColumn;
    }

    private Button createDeleteDiseaseColumn(int diseaseId) {
        Button button = new Button();
        button.setText("Delete");
        button.setOnAction(event -> {
            try {
                getDbConnector().deleteDiseaseById(diseaseId);
                controller.showAlert(Alert.AlertType.CONFIRMATION, "All good", "Disease has been deleted");
                handle(new ActionEvent());
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
            row.setDeleteButton(createDeleteDiseaseColumn(disease.getId()));
            rows.add(row);
        }
        return rows;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            diseaseName.setCellValueFactory(new PropertyValueFactory<>("name"));
            diseaseDegree.setCellValueFactory(new PropertyValueFactory<>("degree"));
            deleteDiseaseColumn.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
            diseasesTable.setItems(createDiseasesTableRowList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
