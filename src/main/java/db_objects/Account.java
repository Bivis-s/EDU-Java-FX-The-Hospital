package db_objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.Utils;

@EqualsAndHashCode(callSuper = true)
@Data
public class Account extends Entity {
    private int id;
    private String phone;
    private String password;

    public void setPhone(String phone) {
        if (Utils.isAccountStringEmpty(phone, "Empty phone field")) {
            this.phone = phone;
        }
    }

    public void setPassword(String password) {
        if (Utils.isAccountStringEmpty(password, "Empty password field")) {
            this.password = password;
        }
    }
}