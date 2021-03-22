package controllers.app_objects;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientMedicalCardTableRow {
    private String diseaseName;
    private int diseaseDegree;
    private String note;
}
