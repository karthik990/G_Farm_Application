package com.google.android.gms.tagmanager;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.gtm.zzk;

final class zzae implements zzdh<zzk> {
    private final /* synthetic */ zzy zzafg;

    private zzae(zzy zzy) {
        this.zzafg = zzy;
    }

    public final void zzhj() {
    }

    public final void zzs(int i) {
        if (i == zzcz.zzahw) {
            this.zzafg.zzaex.zzhn();
        }
        synchronized (this.zzafg) {
            if (!this.zzafg.isReady()) {
                if (this.zzafg.zzafa != null) {
                    this.zzafg.setResult(this.zzafg.zzafa);
                } else {
                    this.zzafg.setResult(this.zzafg.createFailedResult(Status.RESULT_TIMEOUT));
                }
            }
        }
        this.zzafg.zzk(this.zzafg.zzaex.zzhm());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0076, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void zze(java.lang.Object r6) {
        /*
            r5 = this;
            com.google.android.gms.internal.gtm.zzk r6 = (com.google.android.gms.internal.gtm.zzk) r6
            com.google.android.gms.tagmanager.zzy r0 = r5.zzafg
            com.google.android.gms.tagmanager.zzai r0 = r0.zzaex
            r0.zzho()
            com.google.android.gms.tagmanager.zzy r0 = r5.zzafg
            monitor-enter(r0)
            com.google.android.gms.internal.gtm.zzi r1 = r6.zzqk     // Catch:{ all -> 0x0077 }
            if (r1 != 0) goto L_0x003c
            com.google.android.gms.tagmanager.zzy r1 = r5.zzafg     // Catch:{ all -> 0x0077 }
            com.google.android.gms.internal.gtm.zzk r1 = r1.zzafc     // Catch:{ all -> 0x0077 }
            com.google.android.gms.internal.gtm.zzi r1 = r1.zzqk     // Catch:{ all -> 0x0077 }
            if (r1 != 0) goto L_0x0032
            java.lang.String r6 = "Current resource is null; network resource is also null"
            com.google.android.gms.tagmanager.zzdi.zzav(r6)     // Catch:{ all -> 0x0077 }
            com.google.android.gms.tagmanager.zzy r6 = r5.zzafg     // Catch:{ all -> 0x0077 }
            com.google.android.gms.tagmanager.zzai r6 = r6.zzaex     // Catch:{ all -> 0x0077 }
            long r1 = r6.zzhm()     // Catch:{ all -> 0x0077 }
            com.google.android.gms.tagmanager.zzy r6 = r5.zzafg     // Catch:{ all -> 0x0077 }
            r6.zzk(r1)     // Catch:{ all -> 0x0077 }
            monitor-exit(r0)     // Catch:{ all -> 0x0077 }
            return
        L_0x0032:
            com.google.android.gms.tagmanager.zzy r1 = r5.zzafg     // Catch:{ all -> 0x0077 }
            com.google.android.gms.internal.gtm.zzk r1 = r1.zzafc     // Catch:{ all -> 0x0077 }
            com.google.android.gms.internal.gtm.zzi r1 = r1.zzqk     // Catch:{ all -> 0x0077 }
            r6.zzqk = r1     // Catch:{ all -> 0x0077 }
        L_0x003c:
            com.google.android.gms.tagmanager.zzy r1 = r5.zzafg     // Catch:{ all -> 0x0077 }
            com.google.android.gms.tagmanager.zzy r2 = r5.zzafg     // Catch:{ all -> 0x0077 }
            com.google.android.gms.common.util.Clock r2 = r2.zzsd     // Catch:{ all -> 0x0077 }
            long r2 = r2.currentTimeMillis()     // Catch:{ all -> 0x0077 }
            r4 = 0
            r1.zza(r6, r2, r4)     // Catch:{ all -> 0x0077 }
            com.google.android.gms.tagmanager.zzy r1 = r5.zzafg     // Catch:{ all -> 0x0077 }
            long r1 = r1.zzaeh     // Catch:{ all -> 0x0077 }
            r3 = 58
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0077 }
            r4.<init>(r3)     // Catch:{ all -> 0x0077 }
            java.lang.String r3 = "setting refresh time to current time: "
            r4.append(r3)     // Catch:{ all -> 0x0077 }
            r4.append(r1)     // Catch:{ all -> 0x0077 }
            java.lang.String r1 = r4.toString()     // Catch:{ all -> 0x0077 }
            com.google.android.gms.tagmanager.zzdi.zzab(r1)     // Catch:{ all -> 0x0077 }
            com.google.android.gms.tagmanager.zzy r1 = r5.zzafg     // Catch:{ all -> 0x0077 }
            boolean r1 = r1.zzhi()     // Catch:{ all -> 0x0077 }
            if (r1 != 0) goto L_0x0075
            com.google.android.gms.tagmanager.zzy r1 = r5.zzafg     // Catch:{ all -> 0x0077 }
            r1.zza(r6)     // Catch:{ all -> 0x0077 }
        L_0x0075:
            monitor-exit(r0)     // Catch:{ all -> 0x0077 }
            return
        L_0x0077:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0077 }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzae.zze(java.lang.Object):void");
    }

    /* synthetic */ zzae(zzy zzy, zzz zzz) {
        this(zzy);
    }
}
