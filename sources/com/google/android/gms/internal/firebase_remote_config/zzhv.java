package com.google.android.gms.internal.firebase_remote_config;

public class zzhv {
    private static final zzgv zzof = zzgv.zzgj();
    private zzfw zzun;
    private volatile zzio zzuo;
    private volatile zzfw zzup;

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzhv)) {
            return false;
        }
        zzhv zzhv = (zzhv) obj;
        zzio zzio = this.zzuo;
        zzio zzio2 = zzhv.zzuo;
        if (zzio == null && zzio2 == null) {
            return zzen().equals(zzhv.zzen());
        }
        if (zzio != null && zzio2 != null) {
            return zzio.equals(zzio2);
        }
        if (zzio != null) {
            return zzio.equals(zzhv.zzh(zzio.zzgz()));
        }
        return zzh(zzio2.zzgz()).equals(zzio2);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(6:7|8|9|10|11|12) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0012 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.google.android.gms.internal.firebase_remote_config.zzio zzh(com.google.android.gms.internal.firebase_remote_config.zzio r2) {
        /*
            r1 = this;
            com.google.android.gms.internal.firebase_remote_config.zzio r0 = r1.zzuo
            if (r0 != 0) goto L_0x001d
            monitor-enter(r1)
            com.google.android.gms.internal.firebase_remote_config.zzio r0 = r1.zzuo     // Catch:{ all -> 0x001a }
            if (r0 == 0) goto L_0x000b
            monitor-exit(r1)     // Catch:{ all -> 0x001a }
            goto L_0x001d
        L_0x000b:
            r1.zzuo = r2     // Catch:{ zzho -> 0x0012 }
            com.google.android.gms.internal.firebase_remote_config.zzfw r0 = com.google.android.gms.internal.firebase_remote_config.zzfw.zzop     // Catch:{ zzho -> 0x0012 }
            r1.zzup = r0     // Catch:{ zzho -> 0x0012 }
            goto L_0x0018
        L_0x0012:
            r1.zzuo = r2     // Catch:{ all -> 0x001a }
            com.google.android.gms.internal.firebase_remote_config.zzfw r2 = com.google.android.gms.internal.firebase_remote_config.zzfw.zzop     // Catch:{ all -> 0x001a }
            r1.zzup = r2     // Catch:{ all -> 0x001a }
        L_0x0018:
            monitor-exit(r1)     // Catch:{ all -> 0x001a }
            goto L_0x001d
        L_0x001a:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001a }
            throw r2
        L_0x001d:
            com.google.android.gms.internal.firebase_remote_config.zzio r2 = r1.zzuo
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_remote_config.zzhv.zzh(com.google.android.gms.internal.firebase_remote_config.zzio):com.google.android.gms.internal.firebase_remote_config.zzio");
    }

    public final zzio zzi(zzio zzio) {
        zzio zzio2 = this.zzuo;
        this.zzun = null;
        this.zzup = null;
        this.zzuo = zzio;
        return zzio2;
    }

    public final int zzgo() {
        if (this.zzup != null) {
            return this.zzup.size();
        }
        if (this.zzuo != null) {
            return this.zzuo.zzgo();
        }
        return 0;
    }

    public final zzfw zzen() {
        if (this.zzup != null) {
            return this.zzup;
        }
        synchronized (this) {
            if (this.zzup != null) {
                zzfw zzfw = this.zzup;
                return zzfw;
            }
            if (this.zzuo == null) {
                this.zzup = zzfw.zzop;
            } else {
                this.zzup = this.zzuo.zzen();
            }
            zzfw zzfw2 = this.zzup;
            return zzfw2;
        }
    }
}
