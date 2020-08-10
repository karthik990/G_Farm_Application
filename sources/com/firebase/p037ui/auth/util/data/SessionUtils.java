package com.firebase.p037ui.auth.util.data;

import java.util.Random;

/* renamed from: com.firebase.ui.auth.util.data.SessionUtils */
public class SessionUtils {
    private static final String VALID_CHARS = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateRandomAlphaNumericString(int i) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(VALID_CHARS.charAt(random.nextInt(i)));
        }
        return sb.toString();
    }
}
