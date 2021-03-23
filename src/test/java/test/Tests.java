package test;

import db_connection.HospitalDBConnector;
import db_objects.Account;
import db_objects.users.Patient;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.sql.SQLException;

@Log4j2
public class Tests {
    @Test
    public void getLastAccountIdTest() throws SQLException {
        HospitalDBConnector connector = HospitalDBConnector.getHospitalDBConnector();
        log.info(connector.getLastAccountId());
    }

    @Test
    public void getAccount() throws SQLException {
        HospitalDBConnector connector = HospitalDBConnector.getHospitalDBConnector();
        Account account = connector.getAccount("w", "w");
        log.info(account);
        log.info(connector.isPatientsAccount(account));
        Patient patient = connector.getPatientByAccountId(account.getId());
        log.info(patient);
    }
}
