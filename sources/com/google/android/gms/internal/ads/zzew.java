package com.google.android.gms.internal.ads;

import android.database.ContentObserver;
import android.os.Handler;

final class zzew extends ContentObserver {
    private final /* synthetic */ zzet zzafk;

    public zzew(zzet zzet, Handler handler) {
        this.zzafk = zzet;
        super(handler);
    }

    public final void onChange(boolean z) {
        super.onChange(z);
        this.zzafk.zzgb();
    }
}
