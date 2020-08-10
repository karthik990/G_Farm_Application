package com.startapp.android.publish.adsCommon.adListeners;

import android.os.Handler;
import android.os.Looper;
import com.startapp.android.publish.adsCommon.C4925Ad;

/* renamed from: com.startapp.android.publish.adsCommon.adListeners.a */
/* compiled from: StartAppSDK */
public class C4960a implements AdDisplayListener {

    /* renamed from: a */
    AdDisplayListener f3087a;

    public C4960a(AdDisplayListener adDisplayListener) {
        this.f3087a = adDisplayListener;
    }

    public void adHidden(final C4925Ad ad) {
        if (this.f3087a != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    C4960a.this.f3087a.adHidden(ad);
                }
            });
        }
    }

    public void adDisplayed(final C4925Ad ad) {
        if (this.f3087a != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    C4960a.this.f3087a.adDisplayed(ad);
                }
            });
        }
    }

    public void adClicked(final C4925Ad ad) {
        if (this.f3087a != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    C4960a.this.f3087a.adClicked(ad);
                }
            });
        }
    }

    public void adNotDisplayed(final C4925Ad ad) {
        if (this.f3087a != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    C4960a.this.f3087a.adNotDisplayed(ad);
                }
            });
        }
    }
}
