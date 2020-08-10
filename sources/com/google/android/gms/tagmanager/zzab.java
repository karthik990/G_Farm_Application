package com.google.android.gms.tagmanager;

final class zzab implements zzac {
    private final /* synthetic */ zzy zzafg;
    private Long zzafh;
    private final /* synthetic */ boolean zzafi;

    zzab(zzy zzy, boolean z) {
        this.zzafg = zzy;
        this.zzafi = z;
    }

    public final boolean zzb(Container container) {
        if (!this.zzafi) {
            return !container.isDefault();
        }
        long lastRefreshTime = container.getLastRefreshTime();
        if (this.zzafh == null) {
            this.zzafh = Long.valueOf(this.zzafg.zzaex.zzhl());
        }
        return lastRefreshTime + this.zzafh.longValue() >= this.zzafg.zzsd.currentTimeMillis();
    }
}
