package controllers.app_objects;

import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DiseasesTableRow {
    private String name;
    private int degree;
    private Button deleteButton;
}
