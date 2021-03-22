package db_objects;

import db_objects.users.Patient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalCard extends Entity {
    private Patient patient;
}
