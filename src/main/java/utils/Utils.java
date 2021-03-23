package utils;


import errors.IncorrectAccountDataError;
import lombok.extern.log4j.Log4j2;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class Utils {
    public static List<String> getTimeInRangeWithStep(int startHour, int endHour, int step) {
        List<String> dates = new ArrayList<>();
        for (int i = startHour; i < endHour; i++) {
            for (int j = 0; j < 60; j += step) {
                dates.add(i + ":" + String.format("%02d",j));
            }
        }
        return dates;
    }

    public static boolean isAccountStringEmpty(String field, String errorMessage) {
        if (field != null && !field.equals("")) {
            return true;
        } else {
            throw new IncorrectAccountDataError(errorMessage);
        }
    }

    public static void logAllResultSetColumns(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            log.info(metaData.getColumnName(i));
        }
    }
}
