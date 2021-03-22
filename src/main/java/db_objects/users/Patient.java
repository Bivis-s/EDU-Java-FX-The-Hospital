package db_objects.users;

import db_objects.Account;
import db_objects.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.Utils;

@EqualsAndHashCode(callSuper = true)
@Data
public class Patient extends Entity {
    private String name;
    private String dateOfBirth;
    private String address;
    private Account account;

    public void setName(String name) {
        if (Utils.isAccountStringEmpty(name, "Name field is empty")) {
            this.name = name;
        }
    }

    public void setDateOfBirth(String dateOfBirth) {
        if (Utils.isAccountStringEmpty(dateOfBirth, "Date of birth field is empty")) {
            this.dateOfBirth = dateOfBirth;
        }
    }

    public void setAddress(String address) {
        if (Utils.isAccountStringEmpty(address, "Address field is empty")) {
            this.address = address;
        }
    }
}
