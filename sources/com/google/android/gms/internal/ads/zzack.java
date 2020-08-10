package com.google.android.gms.internal.ads;

import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import java.lang.ref.WeakReference;

final class zzack implements OnGlobalLayoutListener {
    private final /* synthetic */ zzace zzcbi;
    private final /* synthetic */ WeakReference zzcbj;

    zzack(zzace zzace, WeakReference weakReference) {
        this.zzcbi = zzace;
        this.zzcbj = weakReference;
    }

    public final void onGlobalLayout() {
        this.zzcbi.zza(this.zzcbj, false);
    }
}
