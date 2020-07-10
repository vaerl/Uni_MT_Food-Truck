package com.example.foodtruck;

import java.time.LocalDateTime;

public class FormattingHelper {

    public static String localDateTimeFormatter(LocalDateTime time){
        int hour = time.getHour();
        int minute = time.getMinute();
        return appendZeroForSingleDigitInt(hour) + ":" + appendZeroForSingleDigitInt(minute);
    }

    public static String appendZeroForSingleDigitInt(int time){
        if (time < 10){
            return "0" + time;
        }
        return Integer.toString(time);
    }

    public static String getHours(String string) {
        return string.split(":")[0];
    }

    public static String getMinutes(String string) {
        return string.split(":")[1];
    }
}
