package utils;


import java.util.ArrayList;
import java.util.List;

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
}
