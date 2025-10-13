package evl.io.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import evl.io.constant.ServiceConstants;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public static JSONArray sortWithNullHandling(JSONArray array, String fieldName, boolean ascending) {
        Comparator<JSONObject> comparator = Comparator.comparing(
                obj -> {
                    String value = obj.getString(fieldName);
                    return value != null ? value : "";
                },
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
        );

        if (!ascending) {
            comparator = comparator.reversed();
        }

        List<JSONObject> sortedList = array.stream()
                .map(obj -> (JSONObject) obj)
                .sorted(comparator)
                .collect(Collectors.toList());

        return new JSONArray(sortedList);
    }
}
