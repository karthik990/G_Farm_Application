package com.startapp.android.publish.adsCommon.adListeners;

import com.startapp.android.publish.adsCommon.C4925Ad;

/* compiled from: StartAppSDK */
public interface AdEventListener {
    void onFailedToReceiveAd(C4925Ad ad);

    void onReceiveAd(C4925Ad ad);
}
