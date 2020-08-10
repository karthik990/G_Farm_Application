package com.google.api.client.util;

import java.nio.charset.StandardCharsets;

public class StringUtils {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static byte[] getBytesUtf8(String str) {
        if (str == null) {
            return null;
        }
        return str.getBytes(StandardCharsets.UTF_8);
    }

    public static String newStringUtf8(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return new String(bArr, StandardCharsets.UTF_8);
    }

    private StringUtils() {
    }
}
