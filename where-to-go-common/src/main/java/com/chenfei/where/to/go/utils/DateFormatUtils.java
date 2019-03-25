package com.chenfei.where.to.go.utils;


import java.util.Date;

public class DateFormatUtils extends org.apache.commons.lang3.time.DateFormatUtils {

    public static String formatFullTimestamp(Date date) {
        return format(date, "yyyyMMddHHmmss");
    }

    public static String formatFullTimestamp() {
        return formatFullTimestamp(new Date());
    }

    public static String formatDateTimestamp(Date date) {
        return format(date, "yyyyMMdd");
    }

    public static String formatDateTimestamp() {
        return formatDateTimestamp(new Date());
    }

    public static String formatTimeTimestamp(Date date) {
        return format(date, "HHmmss");
    }

    public static String formatTimeTimestamp() {
        return formatTimeTimestamp(new Date());
    }

    public static String formatShowDateTime(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatShowDateTime() {
        return formatShowDateTime(new Date());
    }

    public static String formatShowDate(Date date) {
        return ISO_8601_EXTENDED_DATE_FORMAT.format(date);
    }

    public static String formatShowDate() {
        return formatShowDate(new Date());
    }

    public static String formatShowTime(Date date) {
        return ISO_8601_EXTENDED_TIME_FORMAT.format(date);
    }

    public static String formatShowTime() {
        return formatShowTime(new Date());
    }

    public static String format(String pattern) {
        return format(new Date(), pattern);
    }

}
