package db_connection;

import lombok.Data;

@Data
public class Account {
    private int id;
    private String name;
    private String phone;
    private String password;
    private String type;
}
