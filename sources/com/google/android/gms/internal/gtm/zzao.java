package com.google.android.gms.internal.gtm;

import com.google.android.gms.common.GoogleApiAvailabilityLight;

public final class zzao {
    public static final String VERSION = String.valueOf(GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE / 1000).replaceAll("(\\d+)(\\d)(\\d\\d)", "$1.$2.$3");
    public static final String zzwe;

    static {
        String valueOf = String.valueOf(VERSION);
        String str = "ma";
        zzwe = valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
    }
}
