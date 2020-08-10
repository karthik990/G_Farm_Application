package com.google.firebase.auth.internal;

import android.os.Handler;
import android.os.HandlerThread;
import androidx.work.PeriodicWorkRequest;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.logging.Logger;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.firebase_auth.zzj;
import com.google.firebase.FirebaseApp;

public final class zzv {
    /* access modifiers changed from: private */
    public static Logger zzjt = new Logger("TokenRefresher", "FirebaseAuth:");
    private Handler handler;
    private final FirebaseApp zzik;
    volatile long zztv;
    volatile long zztw;
    private long zztx;
    private HandlerThread zzty = new HandlerThread("TokenRefresher", 10);
    private Runnable zztz;

    public zzv(FirebaseApp firebaseApp) {
        zzjt.mo26595v("Initializing TokenRefresher", new Object[0]);
        this.zzik = (FirebaseApp) Preconditions.checkNotNull(firebaseApp);
        this.zzty.start();
        this.handler = new zzj(this.zzty.getLooper());
        this.zztz = new zzy(this, this.zzik.getName());
        this.zztx = PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS;
    }

    public final void zzfh() {
        Logger logger = zzjt;
        long j = this.zztv - this.zztx;
        StringBuilder sb = new StringBuilder(43);
        sb.append("Scheduling refresh for ");
        sb.append(j);
        logger.mo26595v(sb.toString(), new Object[0]);
        cancel();
        this.zztw = Math.max((this.zztv - DefaultClock.getInstance().currentTimeMillis()) - this.zztx, 0) / 1000;
        this.handler.postDelayed(this.zztz, this.zztw * 1000);
    }

    /* access modifiers changed from: 0000 */
    public final void zzfi() {
        int i = (int) this.zztw;
        long j = (i == 30 || i == 60 || i == 120 || i == 240 || i == 480) ? 2 * this.zztw : i != 960 ? 30 : 960;
        this.zztw = j;
        this.zztv = DefaultClock.getInstance().currentTimeMillis() + (this.zztw * 1000);
        Logger logger = zzjt;
        long j2 = this.zztv;
        StringBuilder sb = new StringBuilder(43);
        sb.append("Scheduling refresh for ");
        sb.append(j2);
        logger.mo26595v(sb.toString(), new Object[0]);
        this.handler.postDelayed(this.zztz, this.zztw * 1000);
    }

    public final void cancel() {
        this.handler.removeCallbacks(this.zztz);
    }
}
