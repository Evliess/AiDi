package evl.io.utils;

import evl.io.constant.ServiceConstants;

public class ValidationUtils {
    public static boolean isNotValidPhone(String phone) {
        return !isValidPhone(phone);
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && !phone.isEmpty() && phone.matches(ServiceConstants.PHONE_PATTERN);
    }

    public static boolean isNotBlank(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isBlank(String str) {
        return !isNotBlank(str);
    }

    public static boolean isNotValidDate(String date) {
        return (date == null || date.isEmpty() || !date.matches(ServiceConstants.DATE_PATTERN_FULL));
    }
}
