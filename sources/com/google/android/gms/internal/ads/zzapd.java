package com.google.android.gms.internal.ads;

final class zzapd implements Runnable {
    private final /* synthetic */ zzaov zzcxf;

    zzapd(zzaov zzaov) {
        this.zzcxf = zzaov;
    }

    public final void run() {
        if (this.zzcxf.zzcxd != null) {
            this.zzcxf.zzcxd.zzsw();
        }
    }
}
