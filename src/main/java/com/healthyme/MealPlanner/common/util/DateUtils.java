package com.healthyme.MealPlanner.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private DateUtils() {
    }

    public static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    public static LocalDate toLocalDate(Instant instant, ZoneId zoneId) {
        return instant == null ? null : instant.atZone(zoneId).toLocalDate();
    }

    public static Instant toInstant(LocalDate localDate, ZoneId zoneId) {
        return localDate == null ? null : localDate.atStartOfDay(zoneId).toInstant();
    }

    public static String format(LocalDate date) {
        return date == null ? null : date.format(ISO_DATE);
    }

    public static LocalDate parse(String dateStr) {
        return dateStr == null ? null : LocalDate.parse(dateStr, ISO_DATE);
    }
}
