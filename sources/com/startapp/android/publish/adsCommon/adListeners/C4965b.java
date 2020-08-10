package com.startapp.android.publish.adsCommon.adListeners;

import android.os.Handler;
import android.os.Looper;
import com.startapp.android.publish.adsCommon.C4925Ad;

/* renamed from: com.startapp.android.publish.adsCommon.adListeners.b */
/* compiled from: StartAppSDK */
public class C4965b implements AdEventListener {

    /* renamed from: a */
    AdEventListener f3096a;

    public C4965b(AdEventListener adEventListener) {
        this.f3096a = adEventListener;
    }

    public void onReceiveAd(final C4925Ad ad) {
        if (this.f3096a != null) {
            Handler a = mo62082a();
            if (a != null) {
                a.post(new Runnable() {
                    public void run() {
                        C4965b.this.f3096a.onReceiveAd(ad);
                    }
                });
            } else {
                this.f3096a.onReceiveAd(ad);
            }
        }
    }

    public void onFailedToReceiveAd(final C4925Ad ad) {
        if (this.f3096a != null) {
            Handler a = mo62082a();
            if (a != null) {
                a.post(new Runnable() {
                    public void run() {
                        C4965b.this.f3096a.onFailedToReceiveAd(ad);
                    }
                });
            } else {
                this.f3096a.onFailedToReceiveAd(ad);
            }
        }
    }

    /* renamed from: a */
    public Handler mo62082a() {
        return new Handler(Looper.getMainLooper());
    }
}
