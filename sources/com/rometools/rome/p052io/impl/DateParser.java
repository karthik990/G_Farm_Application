package com.rometools.rome.p052io.impl;

import androidx.exifinterface.media.ExifInterface;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.slf4j.Marker;

/* renamed from: com.rometools.rome.io.impl.DateParser */
public class DateParser {
    private static String[] ADDITIONAL_MASKS = PropertiesLoader.getPropertiesLoader().getTokenizedProperty("datetime.extra.masks", "|");
    private static final String[] RFC822_MASKS = {"EEE, dd MMM yy HH:mm:ss z", "EEE, dd MMM yy HH:mm z", "dd MMM yy HH:mm:ss z", "dd MMM yy HH:mm z"};
    private static final String[] W3CDATETIME_MASKS;
    private static final String[] masks;

    static {
        String str = "yyyy-MM-dd'T'HH:mm:ss.SSSz";
        String str2 = "yyyy-MM-dd't'HH:mm:ss.SSSz";
        String str3 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String str4 = "yyyy-MM-dd't'HH:mm:ss.SSS'z'";
        String str5 = "yyyy-MM-dd'T'HH:mm:ssz";
        String str6 = "yyyy-MM-dd't'HH:mm:ssz";
        W3CDATETIME_MASKS = new String[]{str, str2, str3, str4, str5, str6, "yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd't'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd't'HH:mm:ss'z'", "yyyy-MM-dd'T'HH:mmz", "yyyy-MM'T'HH:mmz", "yyyy'T'HH:mmz", "yyyy-MM-dd't'HH:mmz", "yyyy-MM-dd'T'HH:mm'Z'", "yyyy-MM-dd't'HH:mm'z'", "yyyy-MM-dd", "yyyy-MM", "yyyy"};
        masks = new String[]{str, str2, str3, str4, str5, str6, "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd't'HH:mm:ss'z'", "yyyy-MM-dd'T'HH:mmz", "yyyy-MM-dd't'HH:mmz", "yyyy-MM-dd'T'HH:mm'Z'", "yyyy-MM-dd't'HH:mm'z'", "yyyy-MM-dd", "yyyy-MM", "yyyy"};
    }

    private DateParser() {
    }

    private static Date parseUsingMask(String[] strArr, String str, Locale locale) {
        if (str != null) {
            str = str.trim();
        }
        Date date = null;
        int i = 0;
        while (date == null && i < strArr.length) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strArr[i], locale);
            simpleDateFormat.setLenient(true);
            try {
                ParsePosition parsePosition = new ParsePosition(0);
                date = simpleDateFormat.parse(str, parsePosition);
                if (parsePosition.getIndex() != str.length()) {
                    date = null;
                }
            } catch (Exception unused) {
            }
            i++;
        }
        return date;
    }

    public static Date parseRFC822(String str, Locale locale) {
        return parseUsingMask(RFC822_MASKS, convertUnsupportedTimeZones(str), locale);
    }

    private static String convertUnsupportedTimeZones(String str) {
        for (String str2 : Arrays.asList(new String[]{"UT", "Z"})) {
            if (str.endsWith(str2)) {
                return replaceLastOccurrence(str, str2, "UTC");
            }
        }
        return str;
    }

    private static String replaceLastOccurrence(String str, String str2, String str3) {
        int lastIndexOf = str.lastIndexOf(str2);
        if (lastIndexOf == -1) {
            return str;
        }
        return new StringBuilder(str).replace(lastIndexOf, str2.length() + lastIndexOf, str3).toString();
    }

    public static Date parseW3CDateTime(String str, Locale locale) {
        int indexOf = str.indexOf(ExifInterface.GPS_DIRECTION_TRUE);
        if (indexOf > -1) {
            if (str.endsWith("Z")) {
                StringBuilder sb = new StringBuilder();
                sb.append(str.substring(0, str.length() - 1));
                sb.append("+00:00");
                str = sb.toString();
            }
            int indexOf2 = str.indexOf(Marker.ANY_NON_NULL_MARKER, indexOf);
            if (indexOf2 == -1) {
                indexOf2 = str.indexOf("-", indexOf);
            }
            if (indexOf2 > -1) {
                String substring = str.substring(0, indexOf2);
                int indexOf3 = substring.indexOf(",");
                if (indexOf3 > -1) {
                    substring = substring.substring(0, indexOf3);
                }
                String substring2 = str.substring(indexOf2);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(substring);
                sb2.append("GMT");
                sb2.append(substring2);
                str = sb2.toString();
            }
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("T00:00GMT");
            str = sb3.toString();
        }
        return parseUsingMask(W3CDATETIME_MASKS, str, locale);
    }

    public static Date parseDate(String str, Locale locale) {
        Date parseW3CDateTime = parseW3CDateTime(str, locale);
        if (parseW3CDateTime != null) {
            return parseW3CDateTime;
        }
        Date parseRFC822 = parseRFC822(str, locale);
        if (parseRFC822 != null) {
            return parseRFC822;
        }
        String[] strArr = ADDITIONAL_MASKS;
        return strArr.length > 0 ? parseUsingMask(strArr, str, locale) : parseRFC822;
    }

    public static String formatRFC822(Date date, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", locale);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(date);
    }

    public static String formatW3CDateTime(Date date, Locale locale) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", locale);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(date);
    }
}
