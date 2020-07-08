package com.example.foodtruck;

public class FormattingHelper {

    public static String timeFormatter(int time){
        if (time < 10){
            return "0" + time;
        }
        return Integer.toString(time);
    }
}
