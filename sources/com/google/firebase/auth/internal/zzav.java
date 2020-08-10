package com.google.firebase.auth.internal;

import com.google.android.gms.common.api.internal.BackgroundDetector.BackgroundStateChangeListener;

final class zzav implements BackgroundStateChangeListener {
    private final /* synthetic */ zzas zzvb;

    zzav(zzas zzas) {
        this.zzvb = zzas;
    }

    public final void onBackgroundStateChanged(boolean z) {
        if (z) {
            this.zzvb.zzux = true;
            this.zzvb.cancel();
            return;
        }
        this.zzvb.zzux = false;
        if (this.zzvb.zzfq()) {
            this.zzvb.zzuw.zzfh();
        }
    }
}
