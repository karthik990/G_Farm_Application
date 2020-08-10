package com.google.android.gms.internal.gtm;

import android.os.Build.VERSION;

public final class zzbx {
    public static int version() {
        try {
            return Integer.parseInt(VERSION.SDK);
        } catch (NumberFormatException unused) {
            zzch.zzf("Invalid version number", VERSION.SDK);
            return 0;
        }
    }
}
