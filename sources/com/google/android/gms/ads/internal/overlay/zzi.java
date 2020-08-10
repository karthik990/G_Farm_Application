package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzaqw;

@zzadh
public final class zzi {
    public final int index;
    public final ViewGroup parent;
    public final LayoutParams zzbyi;
    public final Context zzrt;

    public zzi(zzaqw zzaqw) throws zzg {
        this.zzbyi = zzaqw.getLayoutParams();
        ViewParent parent2 = zzaqw.getParent();
        this.zzrt = zzaqw.zzua();
        if (parent2 == null || !(parent2 instanceof ViewGroup)) {
            throw new zzg("Could not get the parent of the WebView for an overlay.");
        }
        this.parent = (ViewGroup) parent2;
        this.index = this.parent.indexOfChild(zzaqw.getView());
        this.parent.removeView(zzaqw.getView());
        zzaqw.zzai(true);
    }
}
