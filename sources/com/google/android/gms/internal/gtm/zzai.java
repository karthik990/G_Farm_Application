package com.google.android.gms.internal.gtm;

final class zzai implements Runnable {
    private final /* synthetic */ zzae zzvw;
    private final /* synthetic */ zzcd zzwa;

    zzai(zzae zzae, zzcd zzcd) {
        this.zzvw = zzae;
        this.zzwa = zzcd;
    }

    public final void run() {
        this.zzvw.zzvu.zza(this.zzwa);
    }
}
