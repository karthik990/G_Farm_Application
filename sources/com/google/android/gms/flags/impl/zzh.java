package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.internal.flags.zze;

public final class zzh extends zza<String> {
    public static String zza(SharedPreferences sharedPreferences, String str, String str2) {
        try {
            return (String) zze.zza(new zzi(sharedPreferences, str, str2));
        } catch (Exception e) {
            String str3 = "Flag value not available, returning default: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w("FlagDataUtils", valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return str2;
        }
    }
}
