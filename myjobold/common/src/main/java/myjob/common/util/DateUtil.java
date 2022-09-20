package myjob.common.util;

import java.util.Date;

public class DateUtil {
    public static String second(Date data) {
        return String.valueOf(data.getSeconds());
    }

    public static String minute(Date data) {
        return String.valueOf(data.getMinutes());
    }

    public static String hour(Date data) {
        return String.valueOf(data.getHours());
    }

    public static String dayOfMonth(Date data) {
        return String.valueOf(data.getDay());
    }

    public static String month(Date data) {
        return String.valueOf(data.getMonth());
    }

    public static String year(Date data) {
        return String.valueOf(data.getYear());
    }
}
