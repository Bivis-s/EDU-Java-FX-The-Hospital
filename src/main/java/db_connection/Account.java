package db_connection;

import errors.IncorrectAccountDataError;
import lombok.Data;

@Data
public class Account {
    private int id;
    private String name;
    private String phone;
    private String password;
    private String type;

    private boolean isStringEmpty(String field, String errorMessage) {
        if (field != null && !field.equals("")) {
            return true;
        } else {
            throw new IncorrectAccountDataError(errorMessage);
        }
    }

    public void setName(String name) {
        if (isStringEmpty(name, "Empty name field")) {
            this.name = name;
        }
    }

    public void setPhone(String phone) {
        if (isStringEmpty(phone, "Empty phone field")) {
            this.phone = phone;
        }
    }

    public void setPassword(String password) {
        if (isStringEmpty(password, "Empty password field")) {
            this.password = password;
        }
    }

    public void setType(String type) {
        this.type = type;
    }
}
