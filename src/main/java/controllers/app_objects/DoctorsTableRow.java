package controllers.app_objects;

import javafx.scene.control.Button;
import lombok.Data;

@Data
public class DoctorsTableRow {
    private String doctorsName;
    private String doctorsPhone;
    private String doctorsType;
    private Button recordButton;
}
