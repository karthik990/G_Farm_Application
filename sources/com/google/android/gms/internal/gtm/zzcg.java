package com.google.android.gms.internal.gtm;

import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.gms.common.util.Clock;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class zzcg {
    private final long zzabf;
    private final int zzabg;
    private double zzabh;
    private long zzabi;
    private final Object zzabj;
    private final Clock zzsd;
    private final String zzup;

    private zzcg(int i, long j, String str, Clock clock) {
        this.zzabj = new Object();
        this.zzabg = 60;
        this.zzabh = (double) this.zzabg;
        this.zzabf = AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS;
        this.zzup = str;
        this.zzsd = clock;
    }

    public zzcg(String str, Clock clock) {
        this(60, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS, str, clock);
    }

    public final boolean zzfm() {
        synchronized (this.zzabj) {
            long currentTimeMillis = this.zzsd.currentTimeMillis();
            if (this.zzabh < ((double) this.zzabg)) {
                double d = (double) (currentTimeMillis - this.zzabi);
                double d2 = (double) this.zzabf;
                Double.isNaN(d);
                Double.isNaN(d2);
                double d3 = d / d2;
                if (d3 > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    this.zzabh = Math.min((double) this.zzabg, this.zzabh + d3);
                }
            }
            this.zzabi = currentTimeMillis;
            if (this.zzabh >= 1.0d) {
                this.zzabh -= 1.0d;
                return true;
            }
            String str = this.zzup;
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34);
            sb.append("Excessive ");
            sb.append(str);
            sb.append(" detected; call ignored.");
            zzch.zzac(sb.toString());
            return false;
        }
    }
}
