package controllers.app_objects;

import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorAppTableRow {
    private String patientName;
    private String patientPhone;
    private String date;
    private String time;
    private Button openCard;
}
