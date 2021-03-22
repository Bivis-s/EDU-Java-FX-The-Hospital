package db_objects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MedicalRecord extends Entity {
    private String note;
    private Disease disease;
}
