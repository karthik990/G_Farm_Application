package com.google.android.gms.internal.firebase_auth;

public class zzih {
    private static final zzhf zzvq = zzhf.zzhq();
    private zzgf zzabv;
    private volatile zzjc zzabw;
    private volatile zzgf zzabx;

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzih)) {
            return false;
        }
        zzih zzih = (zzih) obj;
        zzjc zzjc = this.zzabw;
        zzjc zzjc2 = zzih.zzabw;
        if (zzjc == null && zzjc2 == null) {
            return zzft().equals(zzih.zzft());
        }
        if (zzjc != null && zzjc2 != null) {
            return zzjc.equals(zzjc2);
        }
        if (zzjc != null) {
            return zzjc.equals(zzih.zzi(zzjc.zzii()));
        }
        return zzi(zzjc2.zzii()).equals(zzjc2);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:7|8|9|10|11|12) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0012 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.android.gms.internal.firebase_auth.zzjc zzi(com.google.android.gms.internal.firebase_auth.zzjc r2) {
        /*
            r1 = this;
            com.google.android.gms.internal.firebase_auth.zzjc r0 = r1.zzabw
            if (r0 != 0) goto L_0x001d
            monitor-enter(r1)
            com.google.android.gms.internal.firebase_auth.zzjc r0 = r1.zzabw     // Catch:{ all -> 0x001a }
            if (r0 == 0) goto L_0x000b
            monitor-exit(r1)     // Catch:{ all -> 0x001a }
            goto L_0x001d
        L_0x000b:
            r1.zzabw = r2     // Catch:{ zzic -> 0x0012 }
            com.google.android.gms.internal.firebase_auth.zzgf r0 = com.google.android.gms.internal.firebase_auth.zzgf.zzvv     // Catch:{ zzic -> 0x0012 }
            r1.zzabx = r0     // Catch:{ zzic -> 0x0012 }
            goto L_0x0018
        L_0x0012:
            r1.zzabw = r2     // Catch:{ all -> 0x001a }
            com.google.android.gms.internal.firebase_auth.zzgf r2 = com.google.android.gms.internal.firebase_auth.zzgf.zzvv     // Catch:{ all -> 0x001a }
            r1.zzabx = r2     // Catch:{ all -> 0x001a }
        L_0x0018:
            monitor-exit(r1)     // Catch:{ all -> 0x001a }
            goto L_0x001d
        L_0x001a:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001a }
            throw r2
        L_0x001d:
            com.google.android.gms.internal.firebase_auth.zzjc r2 = r1.zzabw
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzih.zzi(com.google.android.gms.internal.firebase_auth.zzjc):com.google.android.gms.internal.firebase_auth.zzjc");
    }

    public final zzjc zzj(zzjc zzjc) {
        zzjc zzjc2 = this.zzabw;
        this.zzabv = null;
        this.zzabx = null;
        this.zzabw = zzjc;
        return zzjc2;
    }

    public final int zzik() {
        if (this.zzabx != null) {
            return this.zzabx.size();
        }
        if (this.zzabw != null) {
            return this.zzabw.zzik();
        }
        return 0;
    }

    public final zzgf zzft() {
        if (this.zzabx != null) {
            return this.zzabx;
        }
        synchronized (this) {
            if (this.zzabx != null) {
                zzgf zzgf = this.zzabx;
                return zzgf;
            }
            if (this.zzabw == null) {
                this.zzabx = zzgf.zzvv;
            } else {
                this.zzabx = this.zzabw.zzft();
            }
            zzgf zzgf2 = this.zzabx;
            return zzgf2;
        }
    }
}
