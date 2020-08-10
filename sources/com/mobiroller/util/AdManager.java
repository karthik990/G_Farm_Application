package com.mobiroller.util;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;
import com.mobiroller.MobiRollerApplication;
import com.mobiroller.helpers.UtilManager;
import com.mobiroller.models.events.StartMedia;
import com.mobiroller.models.events.StopMedia;
import java.util.Date;
import org.greenrobot.eventbus.EventBus;

public class AdManager {
    public static boolean isAdOpen = false;
    private static AdManager singleton = new AdManager();
    /* access modifiers changed from: private */
    public InterstitialAdCallBack callBack;
    private InterstitialAd interstitialAd;

    public interface InterstitialAdCallBack {
        void onAdClosed();
    }

    private AdManager() {
    }

    public static synchronized AdManager getInstance() {
        AdManager adManager;
        synchronized (AdManager.class) {
            adManager = singleton;
        }
        return adManager;
    }

    private void createAdmobInterstitialAd(String str) {
        if (this.interstitialAd == null) {
            this.interstitialAd = new InterstitialAd(MobiRollerApplication.context);
            this.interstitialAd.setAdUnitId(str);
            this.interstitialAd.setAdListener(new AdListener() {
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);
                }

                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                }

                public void onAdOpened() {
                    super.onAdOpened();
                    AdManager.isAdOpen = true;
                    EventBus.getDefault().post(new StopMedia());
                }

                public void onAdLoaded() {
                    super.onAdLoaded();
                }

                public void onAdClicked() {
                    super.onAdClicked();
                }

                public void onAdImpression() {
                    super.onAdImpression();
                }

                public void onAdClosed() {
                    super.onAdClosed();
                    if (AdManager.this.callBack != null) {
                        AdManager.this.callBack.onAdClosed();
                    }
                    AdManager.isAdOpen = false;
                    MobiRollerApplication.isInterstitialShown = true;
                    if (UtilManager.sharedPrefHelper().getInterstitialMultipleDisplayEnabled()) {
                        UtilManager.sharedPrefHelper().setInterstitialClickCount(0);
                        UtilManager.sharedPrefHelper().setInterstitialTimer(new Date());
                    }
                    EventBus.getDefault().post(new StartMedia());
                }
            });
        }
        if (!this.interstitialAd.isLoading() && !this.interstitialAd.isLoaded()) {
            this.interstitialAd.loadAd(new Builder().build());
        }
    }

    public boolean isInterstitialAdReady() {
        InterstitialAd interstitialAd2 = this.interstitialAd;
        return interstitialAd2 != null && interstitialAd2.isLoaded() && MobiRollerApplication.isAppForeground;
    }

    public void createInterstitialAd() {
        InterstitialAd interstitialAd2 = this.interstitialAd;
        if (interstitialAd2 == null) {
            getInstance().createAdmobInterstitialAd(UtilManager.sharedPrefHelper().getAdUnitID());
        } else {
            interstitialAd2.loadAd(new Builder().build());
        }
    }

    public void showInterstitialAd(InterstitialAdCallBack interstitialAdCallBack) {
        this.callBack = interstitialAdCallBack;
        this.interstitialAd.show();
    }
}
