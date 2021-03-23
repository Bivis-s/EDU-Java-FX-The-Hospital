package db_objects;

import lombok.Getter;
import lombok.Setter;
import utils.Utils;

@Getter
@Setter
public class Disease extends Entity {
    private String name;
    private int degree;

    public void setName(String name) {
        if (Utils.isEntityStringEmpty(name, "Name field is empty")) {
            this.name = name;
        }
    }
}
