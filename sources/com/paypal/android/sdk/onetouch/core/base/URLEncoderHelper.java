package com.paypal.android.sdk.onetouch.core.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLEncoderHelper {
    public static String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("unable_to_encode:");
            sb.append(str);
            return sb.toString();
        }
    }
}
