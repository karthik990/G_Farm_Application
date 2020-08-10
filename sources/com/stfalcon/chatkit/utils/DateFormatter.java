package com.stfalcon.chatkit.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateFormatter {

    public interface Formatter {
        String format(Date date);
    }

    public enum Template {
        STRING_DAY_MONTH_YEAR("d MMMM yyyy"),
        STRING_DAY_MONTH("d MMMM"),
        TIME("HH:mm");
        
        private String template;

        private Template(String str) {
            this.template = str;
        }

        public String get() {
            return this.template;
        }
    }

    private DateFormatter() {
        throw new AssertionError();
    }

    public static String format(Date date, Template template) {
        return format(date, template.get());
    }

    public static String format(Date date, String str) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(str, Locale.getDefault()).format(date);
    }

    public static boolean isSameDay(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("Dates must not be null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        return isSameDay(instance, instance2);
    }

    public static boolean isSameDay(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("Dates must not be null");
        } else if (calendar.get(0) == calendar2.get(0) && calendar.get(1) == calendar2.get(1) && calendar.get(6) == calendar2.get(6)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSameYear(Date date, Date date2) {
        if (date == null || date2 == null) {
            throw new IllegalArgumentException("Dates must not be null");
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(date2);
        return isSameYear(instance, instance2);
    }

    public static boolean isSameYear(Calendar calendar, Calendar calendar2) {
        if (calendar == null || calendar2 == null) {
            throw new IllegalArgumentException("Dates must not be null");
        } else if (calendar.get(0) == calendar2.get(0) && calendar.get(1) == calendar2.get(1)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isToday(Calendar calendar) {
        return isSameDay(calendar, Calendar.getInstance());
    }

    public static boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }

    public static boolean isYesterday(Calendar calendar) {
        Calendar instance = Calendar.getInstance();
        instance.add(5, -1);
        return isSameDay(calendar, instance);
    }

    public static boolean isYesterday(Date date) {
        Calendar instance = Calendar.getInstance();
        instance.add(5, -1);
        return isSameDay(date, instance.getTime());
    }

    public static boolean isCurrentYear(Date date) {
        return isSameYear(date, Calendar.getInstance().getTime());
    }

    public static boolean isCurrentYear(Calendar calendar) {
        return isSameYear(calendar, Calendar.getInstance());
    }
}
