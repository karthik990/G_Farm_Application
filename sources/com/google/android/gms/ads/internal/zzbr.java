package com.google.android.gms.ads.internal;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

final class zzbr implements OnTouchListener {
    private final /* synthetic */ zzbp zzaba;

    zzbr(zzbp zzbp) {
        this.zzaba = zzbp;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.zzaba.zzaay != null) {
            this.zzaba.zzaay.zza(motionEvent);
        }
        return false;
    }
}
