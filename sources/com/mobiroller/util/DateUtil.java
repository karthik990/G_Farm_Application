package com.mobiroller.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
    public static boolean dateControlString(String str, String str2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        boolean z = false;
        if (!(str == null || str2 == null)) {
            try {
                if (simpleDateFormat.parse(str).compareTo(simpleDateFormat.parse(str2)) == 0) {
                    z = true;
                }
                return z;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
