package com.google.android.gms.tagmanager;

import androidx.work.PeriodicWorkRequest;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.gms.common.util.Clock;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

final class zzdg implements zzej {
    private final long zzabf = PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS;
    private final int zzabg = 5;
    private double zzabh = ((double) Math.min(1, 5));
    private long zzabi;
    private final Object zzabj = new Object();
    private final long zzaia = DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
    private final Clock zzsd;
    private final String zzup;

    public zzdg(int i, int i2, long j, long j2, String str, Clock clock) {
        this.zzup = str;
        this.zzsd = clock;
    }

    public final boolean zzfm() {
        synchronized (this.zzabj) {
            long currentTimeMillis = this.zzsd.currentTimeMillis();
            if (currentTimeMillis - this.zzabi < this.zzaia) {
                String str = this.zzup;
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 34);
                sb.append("Excessive ");
                sb.append(str);
                sb.append(" detected; call ignored.");
                zzdi.zzac(sb.toString());
                return false;
            }
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
            String str2 = this.zzup;
            StringBuilder sb2 = new StringBuilder(String.valueOf(str2).length() + 34);
            sb2.append("Excessive ");
            sb2.append(str2);
            sb2.append(" detected; call ignored.");
            zzdi.zzac(sb2.toString());
            return false;
        }
    }
}
