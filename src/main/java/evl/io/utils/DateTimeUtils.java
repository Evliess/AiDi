package evl.io.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtils {
    public static String convertToFormattedString(Long epochMilli, String pattern) {
        if (epochMilli == null) return "N/A";
        Instant instant = Instant.ofEpochMilli(epochMilli);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    public static String convertToFormattedStringDateTime(Long epochMilli) {
        return convertToFormattedString(epochMilli, "yyyy/MM/dd HH:mm:ss");
    }

    public static String convertToFormattedStringDate(Long epochMilli) {
        return convertToFormattedString(epochMilli, "yyyy/MM/dd");
    }

    public static long dateToMillisPlus1Day(String dateString) {
        return dateToMillis(dateString, "yyyy/MM/dd", ZoneId.systemDefault().toString(), 1);
    }

    public static long dateToMillis(String dateString) {
        return dateToMillis(dateString, "yyyy/MM/dd", ZoneId.systemDefault().toString(), 0);
    }

    public static long dateToMillis(String dateString, String pattern) {
        return dateToMillis(dateString, pattern, ZoneId.systemDefault().toString(), 0);
    }

    public static long dateToMillis(String dateString, String pattern, String zoneId, long days) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate date = LocalDate.parse(dateString, formatter).plusDays(days);
            return date.atStartOfDay(ZoneId.of(zoneId))
                    .toInstant()
                    .toEpochMilli();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("日期格式解析错误: " + dateString, e);
        }
    }

    public static String nowPlusDays(int days) {
        LocalDate now = LocalDate.now();
        LocalDate oneMonthLater = now.plusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return oneMonthLater.format(formatter);
    }

    public static String nowPlus1Month() {
        return nowPlusDays(31);
    }

    public static String nowPlus1Season() {
        return nowPlusDays(92);
    }

    public static String nowPlus1Year() {
        return nowPlusDays(366);
    }
}
