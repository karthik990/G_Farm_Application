package com.google.android.gms.internal.ads;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;

@zzadh
public final class zzpz extends Image {
    private final Drawable mDrawable;
    private final Uri mUri;
    private final double zzbhv;
    private final zzpw zzbkm;

    public zzpz(zzpw zzpw) {
        Drawable drawable;
        String str = "";
        this.zzbkm = zzpw;
        Uri uri = null;
        try {
            IObjectWrapper zzjy = this.zzbkm.zzjy();
            if (zzjy != null) {
                drawable = (Drawable) ObjectWrapper.unwrap(zzjy);
                this.mDrawable = drawable;
                uri = this.zzbkm.getUri();
                this.mUri = uri;
                double d = 1.0d;
                d = this.zzbkm.getScale();
                this.zzbhv = d;
            }
        } catch (RemoteException e) {
            zzane.zzb(str, e);
        }
        drawable = null;
        this.mDrawable = drawable;
        try {
            uri = this.zzbkm.getUri();
        } catch (RemoteException e2) {
            zzane.zzb(str, e2);
        }
        this.mUri = uri;
        double d2 = 1.0d;
        try {
            d2 = this.zzbkm.getScale();
        } catch (RemoteException e3) {
            zzane.zzb(str, e3);
        }
        this.zzbhv = d2;
    }

    public final Drawable getDrawable() {
        return this.mDrawable;
    }

    public final double getScale() {
        return this.zzbhv;
    }

    public final Uri getUri() {
        return this.mUri;
    }
}
