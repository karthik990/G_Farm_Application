package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import androidx.work.PeriodicWorkRequest;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;

public final class zza {
    private static Object zzadq = new Object();
    private static zza zzadr;
    private volatile boolean closed;
    private volatile long zzadj;
    private volatile long zzadk;
    private volatile long zzadl;
    private volatile long zzadm;
    private final Thread zzadn;
    private final Object zzado;
    private zzd zzadp;
    /* access modifiers changed from: private */
    public final Context zzrm;
    private final Clock zzsd;
    private volatile Info zzvp;

    public static zza zzf(Context context) {
        if (zzadr == null) {
            synchronized (zzadq) {
                if (zzadr == null) {
                    zza zza = new zza(context);
                    zzadr = zza;
                    zza.zzadn.start();
                }
            }
        }
        return zzadr;
    }

    private zza(Context context) {
        this(context, null, DefaultClock.getInstance());
    }

    private zza(Context context, zzd zzd, Clock clock) {
        this.zzadj = PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS;
        this.zzadk = 30000;
        this.closed = false;
        this.zzado = new Object();
        this.zzadp = new zzb(this);
        this.zzsd = clock;
        if (context != null) {
            this.zzrm = context.getApplicationContext();
        } else {
            this.zzrm = context;
        }
        this.zzadl = this.zzsd.currentTimeMillis();
        this.zzadn = new Thread(new zzc(this));
    }

    public final String zzgq() {
        if (this.zzvp == null) {
            zzgr();
        } else {
            zzgs();
        }
        zzgt();
        if (this.zzvp == null) {
            return null;
        }
        return this.zzvp.getId();
    }

    public final boolean isLimitAdTrackingEnabled() {
        if (this.zzvp == null) {
            zzgr();
        } else {
            zzgs();
        }
        zzgt();
        return this.zzvp == null || this.zzvp.isLimitAdTrackingEnabled();
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0010 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzgr() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.closed     // Catch:{ InterruptedException -> 0x0010 }
            if (r0 != 0) goto L_0x0010
            r2.zzgs()     // Catch:{ InterruptedException -> 0x0010 }
            r0 = 500(0x1f4, double:2.47E-321)
            r2.wait(r0)     // Catch:{ InterruptedException -> 0x0010 }
            goto L_0x0010
        L_0x000e:
            r0 = move-exception
            goto L_0x0012
        L_0x0010:
            monitor-exit(r2)     // Catch:{ all -> 0x000e }
            return
        L_0x0012:
            monitor-exit(r2)     // Catch:{ all -> 0x000e }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zza.zzgr():void");
    }

    private final void zzgs() {
        if (this.zzsd.currentTimeMillis() - this.zzadl > this.zzadk) {
            synchronized (this.zzado) {
                this.zzado.notify();
            }
            this.zzadl = this.zzsd.currentTimeMillis();
        }
    }

    private final void zzgt() {
        if (this.zzsd.currentTimeMillis() - this.zzadm > 3600000) {
            this.zzvp = null;
        }
    }

    /* access modifiers changed from: private */
    public final void zzgu() {
        Process.setThreadPriority(10);
        while (!this.closed) {
            Info zzgv = this.zzadp.zzgv();
            if (zzgv != null) {
                this.zzvp = zzgv;
                this.zzadm = this.zzsd.currentTimeMillis();
                zzdi.zzaw("Obtained fresh AdvertisingId info from GmsCore.");
            }
            synchronized (this) {
                notifyAll();
            }
            try {
                synchronized (this.zzado) {
                    this.zzado.wait(this.zzadj);
                }
            } catch (InterruptedException unused) {
                zzdi.zzaw("sleep interrupted in AdvertiserDataPoller thread; continuing");
            }
        }
    }

    public final void close() {
        this.closed = true;
        this.zzadn.interrupt();
    }
}
