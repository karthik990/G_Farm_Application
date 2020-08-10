package com.google.android.gms.internal.ads;

import android.view.View;
import android.widget.FrameLayout;
import androidx.collection.SimpleArrayMap;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import java.util.Arrays;
import java.util.List;

@zzadh
public final class zzos extends zzqt implements zzpb {
    private final Object mLock = new Object();
    private final zzoj zzbie;
    private zzlo zzbif;
    private View zzbig;
    /* access modifiers changed from: private */
    public zzoz zzbij;
    private final String zzbio;
    private final SimpleArrayMap<String, zzon> zzbip;
    private final SimpleArrayMap<String, String> zzbiq;

    public zzos(String str, SimpleArrayMap<String, zzon> simpleArrayMap, SimpleArrayMap<String, String> simpleArrayMap2, zzoj zzoj, zzlo zzlo, View view) {
        this.zzbio = str;
        this.zzbip = simpleArrayMap;
        this.zzbiq = simpleArrayMap2;
        this.zzbie = zzoj;
        this.zzbif = zzlo;
        this.zzbig = view;
    }

    public final void destroy() {
        zzakk.zzcrm.post(new zzou(this));
        this.zzbif = null;
        this.zzbig = null;
    }

    public final List<String> getAvailableAssetNames() {
        String[] strArr = new String[(this.zzbip.size() + this.zzbiq.size())];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.zzbip.size()) {
            strArr[i3] = (String) this.zzbip.keyAt(i2);
            i2++;
            i3++;
        }
        while (i < this.zzbiq.size()) {
            strArr[i3] = (String) this.zzbiq.keyAt(i);
            i++;
            i3++;
        }
        return Arrays.asList(strArr);
    }

    public final String getCustomTemplateId() {
        return this.zzbio;
    }

    public final zzlo getVideoController() {
        return this.zzbif;
    }

    public final void performClick(String str) {
        synchronized (this.mLock) {
            if (this.zzbij == null) {
                zzane.m1272e("#001 Attempt to perform click before app native ad initialized.");
            } else {
                this.zzbij.zza(null, str, null, null, null);
            }
        }
    }

    public final void recordImpression() {
        synchronized (this.mLock) {
            if (this.zzbij == null) {
                zzane.m1272e("#002 Attempt to record impression before native ad initialized.");
            } else {
                this.zzbij.zza((View) null, null);
            }
        }
    }

    public final String zzao(String str) {
        return (String) this.zzbiq.get(str);
    }

    public final zzpw zzap(String str) {
        return (zzpw) this.zzbip.get(str);
    }

    public final void zzb(zzoz zzoz) {
        synchronized (this.mLock) {
            this.zzbij = zzoz;
        }
    }

    public final boolean zzh(IObjectWrapper iObjectWrapper) {
        if (this.zzbij == null) {
            zzane.m1272e("Attempt to call renderVideoInMediaView before ad initialized.");
            return false;
        } else if (this.zzbig == null) {
            return false;
        } else {
            FrameLayout frameLayout = (FrameLayout) ObjectWrapper.unwrap(iObjectWrapper);
            this.zzbij.zza((View) frameLayout, (zzox) new zzot(this));
            return true;
        }
    }

    public final IObjectWrapper zzka() {
        return ObjectWrapper.wrap(this.zzbij);
    }

    public final String zzkb() {
        return ExifInterface.GPS_MEASUREMENT_3D;
    }

    public final zzoj zzkc() {
        return this.zzbie;
    }

    public final View zzkd() {
        return this.zzbig;
    }

    public final IObjectWrapper zzkh() {
        return ObjectWrapper.wrap(this.zzbij.getContext().getApplicationContext());
    }
}