package db_objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientAppointment extends Entity {
    private String date;
    private String time;
    private int doctorId;
    private String doctorName;
    private String doctorType;
}
