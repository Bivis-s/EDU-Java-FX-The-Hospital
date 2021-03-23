package db_objects.users;

import db_objects.Account;
import db_objects.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;
import utils.Utils;

@EqualsAndHashCode(callSuper = true)
@Data
@Log4j2
public class Patient extends Entity {
    private String name;
    private String dateOfBirth;
    private String address;
    private Account account;

    public void setName(String name) {
        if (Utils.isEntityStringEmpty(name, "Name field is empty")) {
            this.name = name;
        }
    }

    public void setDateOfBirth(String dateOfBirth) {
        if (Utils.isEntityStringEmpty(dateOfBirth, "Date of birth field is empty")) {
            this.dateOfBirth = dateOfBirth;
        }
    }

    public void setAddress(String address) {
        if (Utils.isEntityStringEmpty(address, "Address field is empty")) {
            this.address = address;
        }
    }

    public String getName() {
        log.info("Get patient name " + name);
        return name;
    }

    public String getDateOfBirth() {
        log.info("Get patient birthdate " + dateOfBirth);
        return dateOfBirth;
    }

    public String getAddress() {
        log.info("Get patient address " + address);
        return address;
    }

    public Account getAccount() {
        log.info("Get patient account " + account);
        return account;
    }
}
