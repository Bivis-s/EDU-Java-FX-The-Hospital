package controllers.app_objects;

import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoctorMedicalCardTableRow {
    private String diseaseName;
    private int diseaseDegree;
    private String recordNote;
    private Button deleteRecord;
}
