package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.Window;
import com.google.android.gms.ads.internal.zzbv;

@zzadh
public final class zzamt {
    private final View mView;
    private Activity zzcuj;
    private boolean zzcuk;
    private boolean zzcul;
    private boolean zzcum;
    private OnGlobalLayoutListener zzcun;
    private OnScrollChangedListener zzcuo;

    public zzamt(Activity activity, View view, OnGlobalLayoutListener onGlobalLayoutListener, OnScrollChangedListener onScrollChangedListener) {
        this.zzcuj = activity;
        this.mView = view;
        this.zzcun = onGlobalLayoutListener;
        this.zzcuo = onScrollChangedListener;
    }

    private static ViewTreeObserver zzj(Activity activity) {
        if (activity == null) {
            return null;
        }
        Window window = activity.getWindow();
        if (window == null) {
            return null;
        }
        View decorView = window.getDecorView();
        if (decorView == null) {
            return null;
        }
        return decorView.getViewTreeObserver();
    }

    private final void zzse() {
        if (!this.zzcuk) {
            OnGlobalLayoutListener onGlobalLayoutListener = this.zzcun;
            if (onGlobalLayoutListener != null) {
                Activity activity = this.zzcuj;
                if (activity != null) {
                    ViewTreeObserver zzj = zzj(activity);
                    if (zzj != null) {
                        zzj.addOnGlobalLayoutListener(onGlobalLayoutListener);
                    }
                }
                zzbv.zzfg();
                zzaor.zza(this.mView, this.zzcun);
            }
            OnScrollChangedListener onScrollChangedListener = this.zzcuo;
            if (onScrollChangedListener != null) {
                Activity activity2 = this.zzcuj;
                if (activity2 != null) {
                    ViewTreeObserver zzj2 = zzj(activity2);
                    if (zzj2 != null) {
                        zzj2.addOnScrollChangedListener(onScrollChangedListener);
                    }
                }
                zzbv.zzfg();
                zzaor.zza(this.mView, this.zzcuo);
            }
            this.zzcuk = true;
        }
    }

    private final void zzsf() {
        Activity activity = this.zzcuj;
        if (activity != null && this.zzcuk) {
            OnGlobalLayoutListener onGlobalLayoutListener = this.zzcun;
            if (onGlobalLayoutListener != null) {
                ViewTreeObserver zzj = zzj(activity);
                if (zzj != null) {
                    zzbv.zzem().zza(zzj, onGlobalLayoutListener);
                }
            }
            OnScrollChangedListener onScrollChangedListener = this.zzcuo;
            if (onScrollChangedListener != null) {
                ViewTreeObserver zzj2 = zzj(this.zzcuj);
                if (zzj2 != null) {
                    zzj2.removeOnScrollChangedListener(onScrollChangedListener);
                }
            }
            this.zzcuk = false;
        }
    }

    public final void onAttachedToWindow() {
        this.zzcul = true;
        if (this.zzcum) {
            zzse();
        }
    }

    public final void onDetachedFromWindow() {
        this.zzcul = false;
        zzsf();
    }

    public final void zzi(Activity activity) {
        this.zzcuj = activity;
    }

    public final void zzsc() {
        this.zzcum = true;
        if (this.zzcul) {
            zzse();
        }
    }

    public final void zzsd() {
        this.zzcum = false;
        zzsf();
    }
}
