package com.google.android.gms.internal.ads;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import java.lang.ref.WeakReference;

@zzadh
final class zzaot extends zzaou implements OnScrollChangedListener {
    private final WeakReference<OnScrollChangedListener> zzcwm;

    public zzaot(View view, OnScrollChangedListener onScrollChangedListener) {
        super(view);
        this.zzcwm = new WeakReference<>(onScrollChangedListener);
    }

    public final void onScrollChanged() {
        OnScrollChangedListener onScrollChangedListener = (OnScrollChangedListener) this.zzcwm.get();
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged();
        } else {
            detach();
        }
    }

    /* access modifiers changed from: protected */
    public final void zza(ViewTreeObserver viewTreeObserver) {
        viewTreeObserver.addOnScrollChangedListener(this);
    }

    /* access modifiers changed from: protected */
    public final void zzb(ViewTreeObserver viewTreeObserver) {
        viewTreeObserver.removeOnScrollChangedListener(this);
    }
}
