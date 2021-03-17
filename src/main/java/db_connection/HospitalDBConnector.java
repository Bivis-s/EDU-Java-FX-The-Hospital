package db_connection;

import errors.AccountAlreadyExistsError;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            ResultSet resultSet = preparedStatement.executeQuery();
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
            try (PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from " + ACCOUNTS_TABLE_NAME + " where phone = ? and password = ?;")) {
                preparedStatement.setString(1, phone);
                preparedStatement.setString(2, password);
                return isThere(preparedStatement, "count(*)");
            }
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public Account getAccount(String phone, String password) {
        if (isThereSuchAccount(phone, password)) {
            try {
                String query = "select * from " + ACCOUNTS_TABLE_NAME +
                        " where " + ACCOUNTS_PHONE_COLUMN_NAME + " = ? and " + ACCOUNTS_PASSWORD_COLUMN_NAME + " = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, phone);
                preparedStatement.setString(2, password);
                return getAccountList(preparedStatement).get(0);
            } catch (SQLException e) {
                throw new Error(e.getMessage());
            }
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
            throw new AccountAlreadyExistsError("There is exists such account");
        }
    }

    public List<Account> getAccountList(PreparedStatement preparedStatement) {
        try {
            List<Account> accountList = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt(ACCOUNTS_ID_COLUMN_NAME));
                account.setName(resultSet.getString(ACCOUNTS_NAME_COLUMN_NAME));
                account.setPhone(resultSet.getString(ACCOUNTS_PHONE_COLUMN_NAME));
                account.setPassword(resultSet.getString(ACCOUNTS_PASSWORD_COLUMN_NAME));
                account.setType(resultSet.getString(ACCOUNTS_TYPE_COLUMN_NAME));
                accountList.add(account);
            }
            return accountList;
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public List<Account> getAccountListByType(String accountType) {
        try {
            String query = "select * from " + ACCOUNTS_TABLE_NAME +
                    " where " + ACCOUNTS_TYPE_COLUMN_NAME + " = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountType);
            return getAccountList(preparedStatement);
        } catch (SQLException e) {
            throw new Error(e.getMessage());
        }
    }

    public List<Account> getDoctorsAccountList() {
        return getAccountListByType(ACCOUNT_TYPE_DOCTOR);
    }

    public List<Account> getPatientsAccountList() {
        return getAccountListByType(ACCOUNT_TYPE_PATIENT);
    }
}
