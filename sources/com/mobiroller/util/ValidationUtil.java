package com.mobiroller.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    public static boolean isValidEmail(String str) {
        if (str == null) {
            return false;
        }
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(str).matches();
    }

    public static boolean isValidPassword(String str) {
        return str != null && str.length() >= 6;
    }
}
