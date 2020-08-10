package com.google.android.gms.internal.ads;

import android.net.TrafficStats;
import android.os.Process;
import android.os.SystemClock;
import java.util.concurrent.BlockingQueue;

public final class zzn extends Thread {
    private final zzm zzaa;
    private final zzb zzj;
    private final zzaa zzk;
    private volatile boolean zzl = false;
    private final BlockingQueue<zzr<?>> zzz;

    public zzn(BlockingQueue<zzr<?>> blockingQueue, zzm zzm, zzb zzb, zzaa zzaa2) {
        this.zzz = blockingQueue;
        this.zzaa = zzm;
        this.zzj = zzb;
        this.zzk = zzaa2;
    }

    private final void processRequest() throws InterruptedException {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        zzr zzr = (zzr) this.zzz.take();
        try {
            zzr.zzb("network-queue-take");
            zzr.isCanceled();
            TrafficStats.setThreadStatsTag(zzr.zze());
            zzp zzc = this.zzaa.zzc(zzr);
            zzr.zzb("network-http-complete");
            if (!zzc.zzac || !zzr.zzl()) {
                zzx zza = zzr.zza(zzc);
                zzr.zzb("network-parse-complete");
                if (zzr.zzh() && zza.zzbg != null) {
                    this.zzj.zza(zzr.getUrl(), zza.zzbg);
                    zzr.zzb("network-cache-written");
                }
                zzr.zzk();
                this.zzk.zzb(zzr, zza);
                zzr.zza(zza);
                return;
            }
            zzr.zzc("not-modified");
            zzr.zzm();
        } catch (zzae e) {
            e.zza(SystemClock.elapsedRealtime() - elapsedRealtime);
            this.zzk.zza(zzr, e);
            zzr.zzm();
        } catch (Exception e2) {
            zzaf.zza(e2, "Unhandled exception %s", e2.toString());
            zzae zzae = new zzae((Throwable) e2);
            zzae.zza(SystemClock.elapsedRealtime() - elapsedRealtime);
            this.zzk.zza(zzr, zzae);
            zzr.zzm();
        }
    }

    public final void quit() {
        this.zzl = true;
        interrupt();
    }

    public final void run() {
        Process.setThreadPriority(10);
        while (true) {
            try {
                processRequest();
            } catch (InterruptedException unused) {
                if (this.zzl) {
                    return;
                }
            }
        }
    }
}
