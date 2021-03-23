package db_objects.users;

import db_objects.Account;
import db_objects.Entity;
import lombok.Getter;
import lombok.Setter;
import utils.Utils;

@Getter
@Setter
public class Doctor extends Entity {
    private String name;
    private String type;
    private Account account;

    public void setName(String name) {
        if (Utils.isEntityStringEmpty(name, "Name field is empty")) {
            this.name = name;
        }
    }

    public void setType(String type) {
        if (Utils.isEntityStringEmpty(type, "Type field is empty")) {
            this.type = type;
        }
    }
}
