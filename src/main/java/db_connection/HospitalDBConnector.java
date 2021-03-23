package db_connection;

import db_objects.*;
import db_objects.users.Doctor;
import db_objects.users.Patient;
import errors.NoSuchAccountError;
import lombok.extern.log4j.Log4j2;
import utils.Utils;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class HospitalDBConnector {
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

    public void addAccount(Account account) throws SQLException {
        String query = "insert into accounts(phone, password) values (?, ?);";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, account.getPhone());
        ps.setString(2, account.getPassword());
        ps.executeUpdate();
    }

    public Account getAccount(String phone, String password) throws SQLException {
        log.info("Get account with phone: '" + phone + "' and password '" + password + "'");
        String query = "select id, phone, password from accounts where phone = ? and password = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, phone);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        Account account = new Account();
        if (rs.next()) {
            account.setId(rs.getInt("id"));
            account.setPhone(rs.getString("phone"));
            account.setPassword(rs.getString("password"));
            return account;
        } else {
            throw new NoSuchAccountError("There is no such account");
        }
    }

    /**
     * @param preparedStatement prepared statement, containing select count(*)
     * @return true, if there is more than 1 entry
     * @throws SQLException exception
     */
    private boolean isThere(PreparedStatement preparedStatement) throws SQLException {
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        int accountCount = rs.getInt("count(*)");
        return accountCount >= 1;
    }

    public boolean isThereAccountWithSuchPhone(String phone) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select count(*) from accounts where phone = ?;");
        ps.setString(1, phone);
        return isThere(ps);
    }

    public int getLastAccountId() throws SQLException {
        String query = "select max(id) as last_account_id from accounts;";
        ResultSet rs = connection.prepareStatement(query).executeQuery();
        rs.next();
        return rs.getInt("last_account_id");
    }

    public void addDoctor(Doctor doctor) throws SQLException {
        String query = "insert into doctors(name, type, account_id) values (?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, doctor.getName());
        ps.setString(2, doctor.getType());
        ps.setInt(3, doctor.getAccount().getId());
        ps.executeUpdate();
    }

    public void addPatient(Patient patient) throws SQLException {
        String query = "insert into patients (name, date_of_birth, address, account_id) " +
                "values (?, ?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, patient.getName());
        ps.setString(2, patient.getDateOfBirth());
        ps.setString(3, patient.getAddress());
        ps.setInt(4, patient.getAccount().getId());
        ps.executeUpdate();
    }

    public Patient getPatient(int accountId) throws SQLException {
        String query = "select patients.id, name, date_of_birth, address, account_id " +
                "from patients " +
                "         inner join accounts on accounts.id = patients.account_id " +
                "where account_id = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, accountId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setName(rs.getString("name"));
        patient.setDateOfBirth(rs.getString("date_of_birth"));
        patient.setAddress(rs.getString("address"));
        patient.setAccount(getAccountById(rs.getInt("account_id")));
        return patient;
    }

    public Doctor getDoctor(int accountId) throws SQLException {
        String query = "select doctors.id, name, type, account_id " +
                "from doctors " +
                "         inner join accounts on accounts.id = doctors.account_id " +
                "where account_id = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, accountId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Doctor doctor = new Doctor();
        doctor.setId(rs.getInt("id"));
        doctor.setName(rs.getString("name"));
        doctor.setType(rs.getString("type"));
        doctor.setAccount(getAccountById(rs.getInt("account_id")));
        return doctor;
    }

    public void addMedicalCard(int patientID) throws SQLException {
        String query = "insert into medical_cards (patient_id) values (?);";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, patientID);
        ps.executeUpdate();
    }

    public int getMedicalCardId(int patientId) throws SQLException {
        String query = "select id " +
                "from medical_cards " +
                "where patient_id = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, patientId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("id");
    }

    public int getPatientIdByMedicalCardId(int medicalCardId) throws SQLException {
        String query = "select patient_id " +
                "from medical_cards " +
                "where id = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, medicalCardId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("patient_id");
    }

    public Disease getDisease(int diseaseId) throws SQLException {
        String query = "select id, name, degree " +
                "from diseases " +
                "where id = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, diseaseId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Disease disease = new Disease();
        disease.setId(rs.getInt("id"));
        disease.setName(rs.getString("name"));
        disease.setDegree(rs.getInt("degree"));
        return disease;
    }

    public List<MedicalRecord> getMedicalRecords(int cardId) throws SQLException {
        String query = "select id, note, disease_id " +
                "from medical_records " +
                "         inner join card_records on medical_records.id = card_records.medical_record_id " +
                "where card_records.medical_card_id = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, cardId);
        ResultSet rs = ps.executeQuery();
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        while (rs.next()) {
            MedicalRecord record = new MedicalRecord();
            record.setId(rs.getInt("id"));
            record.setNote(rs.getString("note"));
            record.setDisease(getDisease(rs.getInt("disease_id")));
            medicalRecords.add(record);
        }
        return medicalRecords;
    }

    public void addDiseases(String name, int degree) throws SQLException {
        String query = "insert into diseases(name, degree) values (?, ?);";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, name);
        ps.setInt(2, degree);
        ps.executeUpdate();
    }

    public List<Disease> getDiseasesList() throws SQLException {
        String query = "select id, name, degree from  diseases;";
        ResultSet rs = connection.prepareStatement(query).executeQuery();
        List<Disease> diseaseList = new ArrayList<>();
        while (rs.next()) {
            Disease disease = new Disease();
            disease.setId(rs.getInt("id"));
            disease.setName(rs.getString("name"));
            disease.setDegree(rs.getInt("degree"));
            diseaseList.add(disease);
        }
        return diseaseList;
    }

    public Account getAccountById(int accountId) throws SQLException {
        String query = "select id, phone, password from accounts where id = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, accountId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setPhone(rs.getString("phone"));
        account.setPassword(rs.getString("password"));
        return account;
    }

    public List<Doctor> getDoctorList() throws SQLException {
        String query = "select id, name, type, account_id from doctors;";
        ResultSet rs = connection.prepareStatement(query).executeQuery();
        List<Doctor> doctorList = new ArrayList<>();
        while (rs.next()) {
            Doctor doctor = new Doctor();
            doctor.setId(rs.getInt("id"));
            doctor.setName(rs.getString("name"));
            doctor.setType(rs.getString("type"));
            doctor.setAccount(getAccountById(rs.getInt("account_id")));
            doctorList.add(doctor);
        }
        return doctorList;
    }

    public void addAppointment(String date, String time, int patientId, int doctorId) throws SQLException {
        String query = "insert into appointments (date, time, patient_id, doctor_id) " +
                "values (?, ?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, date);
        ps.setString(2, time);
        ps.setInt(3, patientId);
        ps.setInt(4, doctorId);
        ps.executeUpdate();
    }

    public List<PatientAppointment> getPatientAppointments(int patientId) throws SQLException {
        String query = "select appointments.id, " +
                "appointments.date, " +
                "appointments.time, " +
                "appointments.doctor_id, " +
                "doctors.name, " +
                "doctors.type " +
                "from appointments " +
                "         inner join doctors on doctors.id = appointments.doctor_id " +
                "where patient_id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, patientId);
        ResultSet rs = ps.executeQuery();
        List<PatientAppointment> patientAppointments = new ArrayList<>();
        while (rs.next()) {
            PatientAppointment appointment = new PatientAppointment();
            appointment.setId(rs.getInt("id"));
            appointment.setDate(rs.getString("date"));
            appointment.setTime(rs.getString("time"));
            appointment.setDoctorId(rs.getInt("doctor_id"));
            appointment.setDoctorName(rs.getString("name"));
            appointment.setDoctorType(rs.getString("type"));
            patientAppointments.add(appointment);
        }
        return patientAppointments;
    }

    public PatientAppointment getPatientAppointment(int patientId, int doctorId, String date, String time)
            throws SQLException {
        String query = "select appointments.id, " +
                "       appointments.date, " +
                "       appointments.time, " +
                "       appointments.doctor_id, " +
                "       doctors.name, " +
                "       doctors.type " +
                "from appointments " +
                "         inner join doctors on doctors.id = appointments.doctor_id " +
                "where patient_id = ? " +
                "  and doctor_id = ? " +
                "  and appointments.date = ? " +
                "  and appointments.time = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, patientId);
        ps.setInt(2, doctorId);
        ps.setString(3, date);
        ps.setString(4, time);
        ResultSet rs = ps.executeQuery();
        rs.next();
        PatientAppointment appointment = new PatientAppointment();
        appointment.setId(rs.getInt("id"));
        appointment.setDate(rs.getString("date"));
        appointment.setTime(rs.getString("time"));
        appointment.setDoctorId(rs.getInt("doctor_id"));
        appointment.setDoctorName(rs.getString("name"));
        appointment.setDoctorType(rs.getString("type"));
        return appointment;
    }

    public List<DoctorAppointment> getDoctorAppointments(int doctorId) throws SQLException {
        String query = "select appointments.id as appointment_id, " +
                "appointments.date, " +
                "appointments.time, " +
                "appointments.patient_id, " +
                "patients.name, " +
                "medical_cards.id as medical_card_id " +
                "from appointments " +
                "         inner join patients on appointments.patient_id = patients.id " +
                "         inner join medical_cards on appointments.patient_id = medical_cards.patient_id " +
                "where doctor_id = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, doctorId);
        ResultSet rs = ps.executeQuery();

        Utils.logAllResultSetColumns(rs);

        List<DoctorAppointment> doctorAppointments = new ArrayList<>();
        while (rs.next()) {
            DoctorAppointment appointment = new DoctorAppointment();
            appointment.setId(rs.getInt("appointment_id"));
            appointment.setDate(rs.getString("date"));
            appointment.setTime(rs.getString("time"));
            appointment.setPatientId(rs.getInt("patient_id"));
            appointment.setPatientName(rs.getString("name"));
            appointment.setCardId(rs.getInt("medical_card_id"));
            doctorAppointments.add(appointment);
        }
        return doctorAppointments;
    }

    public void deleteAppointment(int appointmentId) throws SQLException {
        String query = "delete from appointments where id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, appointmentId);
        ps.executeUpdate();
    }

    public boolean isDoctorsTimeBusy(int doctorId, String time, String date) throws SQLException {
        String query = "select count(*) " +
                "from appointments " +
                "where doctor_id = ? " +
                "  and date = ? " +
                "  and time = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, doctorId);
        ps.setString(2, date);
        ps.setString(3, time);
        return isThere(ps);
    }

    public boolean isPatientsAccount(Account account) throws SQLException {
        String query = "select count(*) " +
                "from patients " +
                "         inner join accounts on accounts.id = patients.account_id " +
                "where accounts.id = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, account.getId());
        return isThere(ps);
    }

    public boolean isDoctorsAccount(Account account) throws SQLException {
        String query = "select count(*) " +
                "from doctors " +
                "         inner join accounts on doctors.account_id = accounts.id " +
                "where accounts.id = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, account.getId());
        return isThere(ps);
    }

    public void deleteMedicalCardRecordById(int recordId) throws SQLException {
        String query = "delete from card_records " +
                "where medical_record_id = ?;";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, recordId);
        ps.executeUpdate();
    }
}
