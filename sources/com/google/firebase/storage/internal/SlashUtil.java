package com.google.firebase.storage.internal;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import java.io.UnsupportedEncodingException;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class SlashUtil {
    public static String preserveSlashEncode(String str) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return slashize(Uri.encode(str));
    }

    public static String slashize(String str) {
        Preconditions.checkNotNull(str);
        return str.replace("%2F", "/");
    }

    public static String unSlashize(String str) {
        Preconditions.checkNotNull(str);
        return str.replace("/", "%2F");
    }

    public static String normalizeSlashes(String str) {
        String[] split;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String str2 = "/";
        if (!str.startsWith(str2) && !str.endsWith(str2) && !str.contains("//")) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (String str3 : str.split(str2, -1)) {
            if (!TextUtils.isEmpty(str3)) {
                if (sb.length() > 0) {
                    sb.append(str2);
                    sb.append(str3);
                } else {
                    sb.append(str3);
                }
            }
        }
        return sb.toString();
    }
}
