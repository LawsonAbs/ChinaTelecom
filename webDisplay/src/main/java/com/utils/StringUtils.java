package com.utils;

public class StringUtils {
    public static String getYearMonth(String date) {
        if (date.contains("-")) {
            String yearMonth[] = date.split("-");
            return yearMonth[0]+yearMonth[1];
        }
        return null;
    }
}
