package evliess.io.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtils {
    public static String convertToFormattedString(long epochMilli) {
        Instant instant = Instant.ofEpochMilli(epochMilli);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public static long dateToMillis(String dateString) {
        return dateToMillis(dateString, "yyyy/MM/dd", ZoneId.systemDefault().toString());
    }

    public static long dateToMillis(String dateString, String pattern) {
        return dateToMillis(dateString, pattern, ZoneId.systemDefault().toString());
    }

    public static long dateToMillis(String dateString, String pattern, String zoneId) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate date = LocalDate.parse(dateString, formatter);
            return date.atStartOfDay(ZoneId.of(zoneId))
                    .toInstant()
                    .toEpochMilli();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("日期格式解析错误: " + dateString, e);
        }
    }
}
