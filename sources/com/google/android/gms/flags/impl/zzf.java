package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.internal.flags.zze;

public final class zzf extends zza<Long> {
    public static Long zza(SharedPreferences sharedPreferences, String str, Long l) {
        try {
            return (Long) zze.zza(new zzg(sharedPreferences, str, l));
        } catch (Exception e) {
            String str2 = "Flag value not available, returning default: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w("FlagDataUtils", valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return l;
        }
    }
}
