package com.google.android.gms.internal.ads;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class zzaf {
    public static boolean DEBUG = Log.isLoggable("Volley", 2);
    private static String TAG = "Volley";

    static class zza {
        public static final boolean zzbk = zzaf.DEBUG;
        private final List<zzag> zzbl = new ArrayList();
        private boolean zzbm = false;

        zza() {
        }

        /* access modifiers changed from: protected */
        public final void finalize() throws Throwable {
            if (!this.zzbm) {
                zzc("Request on the loose");
                zzaf.m1417e("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
            }
        }

        public final synchronized void zza(String str, long j) {
            if (!this.zzbm) {
                List<zzag> list = this.zzbl;
                zzag zzag = new zzag(str, j, SystemClock.elapsedRealtime());
                list.add(zzag);
            } else {
                throw new IllegalStateException("Marker added to finished log");
            }
        }

        public final synchronized void zzc(String str) {
            long j;
            this.zzbm = true;
            if (this.zzbl.size() == 0) {
                j = 0;
            } else {
                j = ((zzag) this.zzbl.get(this.zzbl.size() - 1)).time - ((zzag) this.zzbl.get(0)).time;
            }
            if (j > 0) {
                long j2 = ((zzag) this.zzbl.get(0)).time;
                zzaf.m1416d("(%-4d ms) %s", Long.valueOf(j), str);
                for (zzag zzag : this.zzbl) {
                    long j3 = zzag.time;
                    zzaf.m1416d("(+%-4d) [%2d] %s", Long.valueOf(j3 - j2), Long.valueOf(zzag.zzbn), zzag.name);
                    j2 = j3;
                }
            }
        }
    }

    /* renamed from: d */
    public static void m1416d(String str, Object... objArr) {
        Log.d(TAG, zza(str, objArr));
    }

    /* renamed from: e */
    public static void m1417e(String str, Object... objArr) {
        Log.e(TAG, zza(str, objArr));
    }

    /* renamed from: v */
    public static void m1418v(String str, Object... objArr) {
        if (DEBUG) {
            Log.v(TAG, zza(str, objArr));
        }
    }

    private static String zza(String str, Object... objArr) {
        String str2;
        if (objArr != null) {
            str = String.format(Locale.US, str, objArr);
        }
        StackTraceElement[] stackTrace = new Throwable().fillInStackTrace().getStackTrace();
        int i = 2;
        while (true) {
            if (i >= stackTrace.length) {
                str2 = "<unknown>";
                break;
            } else if (!stackTrace[i].getClass().equals(zzaf.class)) {
                String className = stackTrace[i].getClassName();
                String substring = className.substring(className.lastIndexOf(46) + 1);
                String substring2 = substring.substring(substring.lastIndexOf(36) + 1);
                String methodName = stackTrace[i].getMethodName();
                StringBuilder sb = new StringBuilder(String.valueOf(substring2).length() + 1 + String.valueOf(methodName).length());
                sb.append(substring2);
                sb.append(".");
                sb.append(methodName);
                str2 = sb.toString();
                break;
            } else {
                i++;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", new Object[]{Long.valueOf(Thread.currentThread().getId()), str2, str});
    }

    public static void zza(Throwable th, String str, Object... objArr) {
        Log.e(TAG, zza(str, objArr), th);
    }
}
