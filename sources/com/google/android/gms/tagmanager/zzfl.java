package com.google.android.gms.tagmanager;

import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

final class zzfl implements zzej {
    private final long zzabf;
    private final int zzabg;
    private double zzabh;
    private final Object zzabj;
    private long zzakm;
    private final Clock zzsd;

    private zzfl(int i, long j) {
        this.zzabj = new Object();
        this.zzabg = 60;
        this.zzabh = (double) this.zzabg;
        this.zzabf = AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS;
        this.zzsd = DefaultClock.getInstance();
    }

    public zzfl() {
        this(60, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
    }

    public final boolean zzfm() {
        synchronized (this.zzabj) {
            long currentTimeMillis = this.zzsd.currentTimeMillis();
            if (this.zzabh < ((double) this.zzabg)) {
                double d = (double) (currentTimeMillis - this.zzakm);
                double d2 = (double) this.zzabf;
                Double.isNaN(d);
                Double.isNaN(d2);
                double d3 = d / d2;
                if (d3 > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    this.zzabh = Math.min((double) this.zzabg, this.zzabh + d3);
                }
            }
            this.zzakm = currentTimeMillis;
            if (this.zzabh >= 1.0d) {
                this.zzabh -= 1.0d;
                return true;
            }
            zzdi.zzac("No more tokens available.");
            return false;
        }
    }
}
