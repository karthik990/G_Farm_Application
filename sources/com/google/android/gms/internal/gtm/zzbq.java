package com.google.android.gms.internal.gtm;

import android.content.pm.ApplicationInfo;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.ProcessUtils;
import java.util.Set;

public final class zzbq {
    private final zzap zzrb;
    private volatile Boolean zzyo;
    private String zzyp;
    private Set<Integer> zzyq;

    protected zzbq(zzap zzap) {
        Preconditions.checkNotNull(zzap);
        this.zzrb = zzap;
    }

    public final boolean zzem() {
        if (this.zzyo == null) {
            synchronized (this) {
                if (this.zzyo == null) {
                    ApplicationInfo applicationInfo = this.zzrb.getContext().getApplicationInfo();
                    String myProcessName = ProcessUtils.getMyProcessName();
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        this.zzyo = Boolean.valueOf(str != null && str.equals(myProcessName));
                    }
                    if ((this.zzyo == null || !this.zzyo.booleanValue()) && "com.google.android.gms.analytics".equals(myProcessName)) {
                        this.zzyo = Boolean.TRUE;
                    }
                    if (this.zzyo == null) {
                        this.zzyo = Boolean.TRUE;
                        this.zzrb.zzco().zzu("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzyo.booleanValue();
    }

    public static boolean zzen() {
        return ((Boolean) zzby.zzza.get()).booleanValue();
    }

    public static int zzeo() {
        return ((Integer) zzby.zzzx.get()).intValue();
    }

    public static long zzep() {
        return ((Long) zzby.zzzi.get()).longValue();
    }

    public static long zzeq() {
        return ((Long) zzby.zzzl.get()).longValue();
    }

    public static int zzer() {
        return ((Integer) zzby.zzzn.get()).intValue();
    }

    public static int zzes() {
        return ((Integer) zzby.zzzo.get()).intValue();
    }

    public static String zzet() {
        return (String) zzby.zzzq.get();
    }

    public static String zzeu() {
        return (String) zzby.zzzp.get();
    }

    public static String zzev() {
        return (String) zzby.zzzr.get();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0014, code lost:
        if (r1.equals(r0) != false) goto L_0x0039;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.util.Set<java.lang.Integer> zzew() {
        /*
            r6 = this;
            com.google.android.gms.internal.gtm.zzbz<java.lang.String> r0 = com.google.android.gms.internal.gtm.zzby.zzaaa
            java.lang.Object r0 = r0.get()
            java.lang.String r0 = (java.lang.String) r0
            java.util.Set<java.lang.Integer> r1 = r6.zzyq
            if (r1 == 0) goto L_0x0016
            java.lang.String r1 = r6.zzyp
            if (r1 == 0) goto L_0x0016
            boolean r1 = r1.equals(r0)
            if (r1 != 0) goto L_0x0039
        L_0x0016:
            java.lang.String r1 = ","
            java.lang.String[] r1 = android.text.TextUtils.split(r0, r1)
            java.util.HashSet r2 = new java.util.HashSet
            r2.<init>()
            int r3 = r1.length
            r4 = 0
        L_0x0023:
            if (r4 >= r3) goto L_0x0035
            r5 = r1[r4]
            int r5 = java.lang.Integer.parseInt(r5)     // Catch:{ NumberFormatException -> 0x0032 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ NumberFormatException -> 0x0032 }
            r2.add(r5)     // Catch:{ NumberFormatException -> 0x0032 }
        L_0x0032:
            int r4 = r4 + 1
            goto L_0x0023
        L_0x0035:
            r6.zzyp = r0
            r6.zzyq = r2
        L_0x0039:
            java.util.Set<java.lang.Integer> r0 = r6.zzyq
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.gtm.zzbq.zzew():java.util.Set");
    }

    public static long zzex() {
        return ((Long) zzby.zzaaf.get()).longValue();
    }
}
