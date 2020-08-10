package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.gtm.zza;
import com.google.android.gms.internal.gtm.zzb;

final class zzei extends zzbq {

    /* renamed from: ID */
    private static final String f1569ID = zza.RANDOM.toString();
    private static final String zzaix = zzb.MIN.toString();
    private static final String zzaiy = zzb.MAX.toString();

    public zzei() {
        super(f1569ID, new String[0]);
    }

    public final boolean zzgw() {
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003e, code lost:
        if (r0 <= r2) goto L_0x0048;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.gtm.zzl zzb(java.util.Map<java.lang.String, com.google.android.gms.internal.gtm.zzl> r7) {
        /*
            r6 = this;
            java.lang.String r0 = zzaix
            java.lang.Object r0 = r7.get(r0)
            com.google.android.gms.internal.gtm.zzl r0 = (com.google.android.gms.internal.gtm.zzl) r0
            java.lang.String r1 = zzaiy
            java.lang.Object r7 = r7.get(r1)
            com.google.android.gms.internal.gtm.zzl r7 = (com.google.android.gms.internal.gtm.zzl) r7
            if (r0 == 0) goto L_0x0041
            com.google.android.gms.internal.gtm.zzl r1 = com.google.android.gms.tagmanager.zzgj.zzkc()
            if (r0 == r1) goto L_0x0041
            if (r7 == 0) goto L_0x0041
            com.google.android.gms.internal.gtm.zzl r1 = com.google.android.gms.tagmanager.zzgj.zzkc()
            if (r7 == r1) goto L_0x0041
            com.google.android.gms.tagmanager.zzgi r0 = com.google.android.gms.tagmanager.zzgj.zzd(r0)
            com.google.android.gms.tagmanager.zzgi r7 = com.google.android.gms.tagmanager.zzgj.zzd(r7)
            com.google.android.gms.tagmanager.zzgi r1 = com.google.android.gms.tagmanager.zzgj.zzka()
            if (r0 == r1) goto L_0x0041
            com.google.android.gms.tagmanager.zzgi r1 = com.google.android.gms.tagmanager.zzgj.zzka()
            if (r7 == r1) goto L_0x0041
            double r0 = r0.doubleValue()
            double r2 = r7.doubleValue()
            int r7 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r7 > 0) goto L_0x0041
            goto L_0x0048
        L_0x0041:
            r0 = 0
            r2 = 4746794007244308480(0x41dfffffffc00000, double:2.147483647E9)
        L_0x0048:
            double r4 = java.lang.Math.random()
            double r2 = r2 - r0
            double r4 = r4 * r2
            double r4 = r4 + r0
            long r0 = java.lang.Math.round(r4)
            java.lang.Long r7 = java.lang.Long.valueOf(r0)
            com.google.android.gms.internal.gtm.zzl r7 = com.google.android.gms.tagmanager.zzgj.zzi(r7)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzei.zzb(java.util.Map):com.google.android.gms.internal.gtm.zzl");
    }
}
