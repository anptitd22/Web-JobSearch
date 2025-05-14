package com.project.webIT.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
    }

    public static LocalDateTime parse(String dateString) {
        return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
    }

    public static String nowAsString() {
        return format(LocalDateTime.now());
    }

    public static boolean isPast(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }
}

