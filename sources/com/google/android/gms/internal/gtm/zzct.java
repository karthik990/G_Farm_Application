package com.google.android.gms.internal.gtm;

final class zzct implements zzbw {
    private final /* synthetic */ Runnable zzacj;
    private final /* synthetic */ zzcq zzack;

    zzct(zzcq zzcq, Runnable runnable) {
        this.zzack = zzcq;
        this.zzacj = runnable;
    }

    public final void zza(Throwable th) {
        this.zzack.handler.post(this.zzacj);
    }
}
