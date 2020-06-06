package com.tungmr.hintfoodanddrinks.utils;

import java.util.Calendar;

public class TimeUtils {

    public static final String MORNING = "Morning";
    public static final String NOON = "Noon";
    public static final String EVENING = "Evening";

    public static String getTime() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 8) {
            return MORNING;
        } else if (timeOfDay >= 8 && timeOfDay < 16) {
            return NOON;
        } else if (timeOfDay >= 16 && timeOfDay < 24) {
            return EVENING;
        }
        return null;
    }
}
