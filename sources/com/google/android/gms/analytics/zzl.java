package com.google.android.gms.analytics;

final class zzl implements Runnable {
    private final /* synthetic */ zzg zzsw;
    private final /* synthetic */ zzk zzsx;

    zzl(zzk zzk, zzg zzg) {
        this.zzsx = zzk;
        this.zzsw = zzg;
    }

    public final void run() {
        this.zzsw.zzap().zza(this.zzsw);
        for (zzn zza : this.zzsx.zzsr) {
            zza.zza(this.zzsw);
        }
        zzk.zzb(this.zzsw);
    }
}
