package com.google.android.gms.internal.ads;

import android.view.View;
import android.view.View.OnAttachStateChangeListener;

final class zzara implements OnAttachStateChangeListener {
    private final /* synthetic */ zzait zzdcg;
    private final /* synthetic */ zzaqx zzdci;

    zzara(zzaqx zzaqx, zzait zzait) {
        this.zzdci = zzaqx;
        this.zzdcg = zzait;
    }

    public final void onViewAttachedToWindow(View view) {
        this.zzdci.zza(view, this.zzdcg, 10);
    }

    public final void onViewDetachedFromWindow(View view) {
    }
}
