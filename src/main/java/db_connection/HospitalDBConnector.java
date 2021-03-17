package db_connection;

import java.io.File;
import java.sql.*;

import static constants.DBValues.*;

public class HospitalDBConnector extends BaseDBConnector {
    private static HospitalDBConnector hospitalDBConnector;
    private final Connection connection;

    private HospitalDBConnector() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" +
                    new File("src/main/resources/db.sqlite").getAbsolutePath());
        } catch (SQLException e) {
            throw new Error("Failed to connect db\n" + e.getMessage());
        }
    }

    public static HospitalDBConnector getHospitalDBConnector() {
        if (hospitalDBConnector == null) {
            hospitalDBConnector = new HospitalDBConnector();
        }
        return hospitalDBConnector;
    }

    private boolean isThere(PreparedStatement preparedStatement, String countColumnLabel) {
        try {
            ResultSet resultSet =
                    preparedStatement.executeQuery();
            resultSet.next();
            int accountCount = resultSet.getInt(countColumnLabel);
            if (accountCount == 1) {
                return true;
            } else if (accountCount == 0) {
                return false;
            } else {
                throw new Error("There is more than one account");
            }
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public boolean isThereSuchAccount(String phone, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from " + ACCOUNTS_TABLE_NAME + " where phone = ? and password = ?;");
            preparedStatement.setString(1, phone);
            preparedStatement.setString(2, password);
            return isThere(preparedStatement, "count(*)");
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public Account getAccountInfo(String phone, String password) {
        if (isThereSuchAccount(phone, password)) {
            Account account = new Account();
            account.setPhone(phone);
            return account;
        } else {
            throw new Error("There is no such account");
        }
    }

    public boolean isThereAccountWithSuchPhone(String phone) {
    try {
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from " + ACCOUNTS_TABLE_NAME + " where phone = ?;");
        preparedStatement.setString(1, phone);
        return isThere(preparedStatement, "count(*)");
    } catch (SQLException e) {
        throw new Error(e.getMessage());
    }
    }

    public void signUpUser(Account account) {
        if (!isThereAccountWithSuchPhone(account.getPhone())) {
            String update = "insert into " + ACCOUNTS_TABLE_NAME + " (" +
                    ACCOUNTS_NAME_COLUMN_NAME + "," +
                    ACCOUNTS_PHONE_COLUMN_NAME + "," +
                    ACCOUNTS_PASSWORD_COLUMN_NAME + "," +
                    ACCOUNTS_TYPE_COLUMN_NAME + ") " +
                    "values(?,?,?,?);";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(update);
                preparedStatement.setString(1, account.getName());
                preparedStatement.setString(2, account.getPhone());
                preparedStatement.setString(3, account.getPassword());
                preparedStatement.setString(4, account.getType());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new Error(e.getMessage());
            }
        } else {
            throw new Error("There is exists such account");
        }
    }
}
