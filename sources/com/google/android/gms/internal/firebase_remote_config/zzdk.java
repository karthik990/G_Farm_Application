package com.google.android.gms.internal.firebase_remote_config;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import org.apache.http.message.TokenParser;
import p043io.netty.util.internal.StringUtil;

public abstract class zzdk {
    public static zzdk zza(char c) {
        return new zzdm(StringUtil.COMMA);
    }

    public abstract boolean zzb(char c);

    protected zzdk() {
    }

    public int zza(CharSequence charSequence, int i) {
        int length = charSequence.length();
        zzds.zza(i, length, Param.INDEX);
        while (i < length) {
            if (zzb(charSequence.charAt(i))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /* access modifiers changed from: private */
    public static String zzc(char c) {
        char[] cArr = {TokenParser.ESCAPE, 'u', 0, 0, 0, 0};
        for (int i = 0; i < 4; i++) {
            cArr[5 - i] = "0123456789ABCDEF".charAt(c & 15);
            c = (char) (c >> 4);
        }
        return String.copyValueOf(cArr);
    }
}
