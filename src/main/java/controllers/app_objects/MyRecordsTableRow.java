package controllers.app_objects;

import javafx.scene.control.Button;
import lombok.Data;

@Data
public class MyRecordsTableRow {
    private String date;
    private String time;
    private String doctorsName;
    private Button cancelRecordButton;
    private int doctorId;
}
