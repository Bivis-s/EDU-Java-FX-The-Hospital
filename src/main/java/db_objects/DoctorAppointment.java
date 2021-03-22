package db_objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorAppointment extends Entity {
    private String date;
    private String time;
    private int patientId;
    private String patientName;
    private int cardId;
}
