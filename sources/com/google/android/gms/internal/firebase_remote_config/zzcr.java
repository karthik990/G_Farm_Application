package com.google.android.gms.internal.firebase_remote_config;

import com.google.api.client.util.escape.PercentEscaper;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public final class zzcr {
    private static final zzcs zzgk = new zzct(PercentEscaper.SAFECHARS_URLENCODER, true);
    private static final zzcs zzgl = new zzct(PercentEscaper.SAFEPATHCHARS_URLENCODER, false);
    private static final zzcs zzgm = new zzct(PercentEscaper.SAFE_PLUS_RESERVED_CHARS_URLENCODER, false);
    private static final zzcs zzgn = new zzct(PercentEscaper.SAFEUSERINFOCHARS_URLENCODER, false);
    private static final zzcs zzgo = new zzct(PercentEscaper.SAFEQUERYSTRINGCHARS_URLENCODER, false);

    public static String zzag(String str) {
        return zzgk.zzam(str);
    }

    public static String zzah(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String zzai(String str) {
        return zzgl.zzam(str);
    }

    public static String zzaj(String str) {
        return zzgm.zzam(str);
    }

    public static String zzak(String str) {
        return zzgn.zzam(str);
    }

    public static String zzal(String str) {
        return zzgo.zzam(str);
    }
}
