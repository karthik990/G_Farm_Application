package com.google.android.gms.ads.internal;

import android.view.View;
import android.view.View.OnClickListener;

final class zzo implements OnClickListener {
    private final /* synthetic */ zzl zzwp;
    private final /* synthetic */ zzx zzwq;

    zzo(zzl zzl, zzx zzx) {
        this.zzwp = zzl;
        this.zzwq = zzx;
    }

    public final void onClick(View view) {
        this.zzwq.recordClick();
        if (this.zzwp.zzwn != null) {
            this.zzwp.zzwn.zzpi();
        }
    }
}
