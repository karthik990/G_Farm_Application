package com.google.android.gms.internal.ads;

import android.util.Log;
import com.google.ads.AdRequest;

@zzadh
public class zzane {
    /* renamed from: e */
    public static void m1272e(String str) {
        if (isLoggable(6)) {
            Log.e(AdRequest.LOGTAG, str);
        }
    }

    public static boolean isLoggable(int i) {
        return i >= 5 || Log.isLoggable(AdRequest.LOGTAG, i);
    }

    public static void zza(String str, Throwable th) {
        if (isLoggable(3)) {
            Log.d(AdRequest.LOGTAG, str, th);
        }
    }

    public static void zzb(String str, Throwable th) {
        if (isLoggable(6)) {
            Log.e(AdRequest.LOGTAG, str, th);
        }
    }

    public static void zzc(String str, Throwable th) {
        if (isLoggable(5)) {
            Log.w(AdRequest.LOGTAG, str, th);
        }
    }

    public static void zzck(String str) {
        if (isLoggable(3)) {
            Log.d(AdRequest.LOGTAG, str);
        }
    }

    public static void zzd(String str, Throwable th) {
        if (isLoggable(5)) {
            String zzdl = zzdl(str);
            if (th != null) {
                zzc(zzdl, th);
                return;
            }
            zzdk(zzdl);
        }
    }

    public static void zzdj(String str) {
        if (isLoggable(4)) {
            Log.i(AdRequest.LOGTAG, str);
        }
    }

    public static void zzdk(String str) {
        if (isLoggable(5)) {
            Log.w(AdRequest.LOGTAG, str);
        }
    }

    private static String zzdl(String str) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length < 4) {
            return str;
        }
        int lineNumber = stackTrace[3].getLineNumber();
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 13);
        sb.append(str);
        sb.append(" @");
        sb.append(lineNumber);
        return sb.toString();
    }

    public static void zzdm(String str) {
        zzd(str, null);
    }
}
