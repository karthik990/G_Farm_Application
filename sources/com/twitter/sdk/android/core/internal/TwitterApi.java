package com.twitter.sdk.android.core.internal;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import com.fasterxml.jackson.core.JsonPointer;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class TwitterApi {
    public static final String BASE_HOST = "api.twitter.com";
    public static final String BASE_HOST_URL = "https://api.twitter.com";
    private final String baseHostUrl;

    public TwitterApi() {
        this(BASE_HOST_URL);
    }

    public TwitterApi(String str) {
        this.baseHostUrl = str;
    }

    public String getBaseHostUrl() {
        return this.baseHostUrl;
    }

    public Builder buildUponBaseHostUrl(String... strArr) {
        Builder buildUpon = Uri.parse(getBaseHostUrl()).buildUpon();
        if (strArr != null) {
            for (String appendPath : strArr) {
                buildUpon.appendPath(appendPath);
            }
        }
        return buildUpon;
    }

    public static String buildUserAgent(String str, String str2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(JsonPointer.SEPARATOR);
        sb.append(str2);
        sb.append(' ');
        sb.append(Build.MODEL);
        sb.append(JsonPointer.SEPARATOR);
        sb.append(VERSION.RELEASE);
        sb.append(" (");
        sb.append(Build.MANUFACTURER);
        sb.append(';');
        sb.append(Build.MODEL);
        sb.append(';');
        sb.append(Build.BRAND);
        sb.append(';');
        sb.append(Build.PRODUCT);
        sb.append(')');
        return normalizeString(sb.toString());
    }

    static String normalizeString(String str) {
        return stripNonAscii(Normalizer.normalize(str, Form.NFD));
    }

    static String stripNonAscii(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt > 31 && charAt < 127) {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }
}
