package constants;

public class DBValues {
    public static final String ACCOUNTS_TABLE_NAME = "accounts";
    public static final String ACCOUNTS_ID_COLUMN_NAME = "id";
    public static final String ACCOUNTS_NAME_COLUMN_NAME = "name";
    public static final String ACCOUNTS_PHONE_COLUMN_NAME = "phone";
    public static final String ACCOUNTS_PASSWORD_COLUMN_NAME = "password";
    public static final String ACCOUNTS_TYPE_COLUMN_NAME = "type";

    public static final String ACCOUNT_TYPE_PATIENT = "patient";
    public static final String ACCOUNT_TYPE_DOCTOR = "doctor";

    public static final String TICKETS_TABLE_NAME = "tickets";
    public static final String TICKETS_ID_COLUMN_NAME = "id";
    public static final String TICKETS_DATE_COLUMN_NAME = "date";
    public static final String TICKETS_TIME_COLUMN_NAME = "time";
    public static final String TICKETS_PATIENT_ID_COLUMN_NAME = "patient_id";
    public static final String TICKETS_DOCTOR_ID_COLUMN_NAME = "doctor_id";
}
