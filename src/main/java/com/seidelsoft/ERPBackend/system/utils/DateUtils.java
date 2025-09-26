package com.seidelsoft.ERPBackend.system.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final String DDMMYYYHHmmss = "dd/MM/yyyy HH:mm:ss";

    public static String getDateFormatted() {
        return format(LocalDateTime.now(), DDMMYYYHHmmss);
    }

    public static String format(LocalDateTime date, String pattern) {
        if (date == null || pattern == null) return "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

}
