package com.startapp.android.publish.ads.nativead;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.common.model.GetAdRequest;
import com.startapp.android.publish.p065a.C4732a;

/* renamed from: com.startapp.android.publish.ads.nativead.a */
/* compiled from: StartAppSDK */
public class C4820a extends C4732a {

    /* renamed from: g */
    private NativeAdPreferences f2682g;

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61144a(C4925Ad ad) {
    }

    public C4820a(Context context, C4925Ad ad, AdPreferences adPreferences, AdEventListener adEventListener, NativeAdPreferences nativeAdPreferences) {
        super(context, ad, adPreferences, adEventListener, Placement.INAPP_NATIVE);
        this.f2682g = nativeAdPreferences;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public GetAdRequest mo61353a() {
        GetAdRequest a = super.mo61353a();
        if (a == null) {
            return null;
        }
        a.setAdsNumber(this.f2682g.getAdsNumber());
        if (this.f2682g.getImageSize() != null) {
            a.setWidth(this.f2682g.getImageSize().getWidth());
            a.setHeight(this.f2682g.getImageSize().getHeight());
        } else {
            int primaryImageSize = this.f2682g.getPrimaryImageSize();
            if (primaryImageSize == -1) {
                primaryImageSize = 2;
            }
            a.setPrimaryImg(Integer.toString(primaryImageSize));
            int secondaryImageSize = this.f2682g.getSecondaryImageSize();
            if (secondaryImageSize == -1) {
                secondaryImageSize = 2;
            }
            a.setMoreImg(Integer.toString(secondaryImageSize));
        }
        if (this.f2682g.isContentAd()) {
            a.setContentAd(this.f2682g.isContentAd());
        }
        return a;
    }
}
