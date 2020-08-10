package com.startapp.android.publish.adsCommon;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.startapp.android.publish.ads.splash.SplashConfig;
import com.startapp.android.publish.ads.splash.SplashHideListener;
import com.startapp.android.publish.adsCommon.C4925Ad.AdState;
import com.startapp.android.publish.adsCommon.C4925Ad.AdType;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.activities.AppWallActivity;
import com.startapp.android.publish.adsCommon.activities.OverlayActivity;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.adsCommon.p077a.C4958f;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.cache.C5071a;
import com.startapp.android.publish.cache.C5080c;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.C5160b;
import com.startapp.common.Constants;
import com.startapp.common.p092a.C5155g;
import com.startapp.common.p092a.C5159i;
import java.io.Serializable;

/* compiled from: StartAppSDK */
public class StartAppAd extends C4925Ad {
    private static final String TAG = "StartAppAd";
    private static final long serialVersionUID = 1;
    private static boolean testMode = false;

    /* renamed from: ad */
    C5021g f3034ad = null;
    private C5080c adKey = null;
    private AdMode adMode = AdMode.AUTOMATIC;
    private AdPreferences adPreferences = null;
    AdDisplayListener callback = null;
    private BroadcastReceiver callbackBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.startapp.android.ShowFailedDisplayBroadcastListener")) {
                String str = "showFailedReason";
                if (intent.getExtras().containsKey(str)) {
                    StartAppAd.this.setNotDisplayedReason((NotDisplayedReason) intent.getExtras().getSerializable(str));
                }
                if (StartAppAd.this.callback != null) {
                    StartAppAd.this.callback.adNotDisplayed(StartAppAd.this);
                }
                m2857a(context);
            } else if (intent.getAction().equals("com.startapp.android.ShowDisplayBroadcastListener")) {
                if (StartAppAd.this.callback != null) {
                    StartAppAd.this.callback.adDisplayed(StartAppAd.this);
                }
            } else if (intent.getAction().equals("com.startapp.android.OnClickCallback")) {
                if (StartAppAd.this.callback != null) {
                    StartAppAd.this.callback.adClicked(StartAppAd.this);
                }
            } else if (!intent.getAction().equals("com.startapp.android.OnVideoCompleted")) {
                if (StartAppAd.this.callback != null) {
                    StartAppAd.this.callback.adHidden(StartAppAd.this);
                }
                m2857a(context);
            } else if (StartAppAd.this.videoListener != null) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        StartAppAd.this.videoListener.onVideoCompleted();
                    }
                });
            }
            StartAppAd.this.f3034ad = null;
        }

        /* renamed from: a */
        private void m2857a(Context context) {
            C5160b.m3831a(context).mo62878a((BroadcastReceiver) this);
        }
    };
    VideoListener videoListener = null;

    /* renamed from: com.startapp.android.publish.adsCommon.StartAppAd$3 */
    /* compiled from: StartAppSDK */
    static /* synthetic */ class C49333 {

        /* renamed from: a */
        static final /* synthetic */ int[] f3039a = new int[AdMode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.startapp.android.publish.adsCommon.StartAppAd$AdMode[] r0 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3039a = r0
                int[] r0 = f3039a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.startapp.android.publish.adsCommon.StartAppAd$AdMode r1 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.FULLPAGE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f3039a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.startapp.android.publish.adsCommon.StartAppAd$AdMode r1 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.OFFERWALL     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f3039a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.startapp.android.publish.adsCommon.StartAppAd$AdMode r1 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.OVERLAY     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f3039a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.startapp.android.publish.adsCommon.StartAppAd$AdMode r1 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.REWARDED_VIDEO     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.adsCommon.StartAppAd.C49333.<clinit>():void");
        }
    }

    /* compiled from: StartAppSDK */
    public enum AdMode {
        AUTOMATIC,
        FULLPAGE,
        OFFERWALL,
        REWARDED_VIDEO,
        VIDEO,
        OVERLAY
    }

    /* access modifiers changed from: protected */
    public void loadAds(AdPreferences adPreferences2, AdEventListener adEventListener) {
    }

    public void onPause() {
    }

    public StartAppAd(Context context) {
        super(context, null);
    }

    public static void init(Context context, String str, String str2) {
        StartAppSDK.init(context, str, str2);
    }

    @Deprecated
    public boolean load(AdPreferences adPreferences2, AdEventListener adEventListener) {
        boolean z = false;
        if (!canShowAd()) {
            if (adEventListener != null) {
                setErrorMessage("serving ads disabled");
                adEventListener.onFailedToReceiveAd(this);
            }
            return false;
        }
        this.adKey = C5071a.m3485a().mo62453a(this.context, this, this.adMode, adPreferences2, adEventListener);
        if (this.adKey != null) {
            z = true;
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00a3, code lost:
        if (r11.equals(new com.startapp.android.publish.common.model.AdPreferences()) == false) goto L_0x00a6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x016a  */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean show(java.lang.String r10, com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener r11) {
        /*
            r9 = this;
            r0 = 0
            r9.setNotDisplayedReason(r0)
            com.startapp.android.publish.adsCommon.adListeners.a r1 = new com.startapp.android.publish.adsCommon.adListeners.a
            r1.<init>(r11)
            r9.callback = r1
            boolean r11 = r9.canShowAd()
            r1 = 0
            java.lang.Integer r2 = java.lang.Integer.valueOf(r1)
            if (r11 != 0) goto L_0x0021
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r10 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.SERVING_ADS_DISABLED
            r9.setNotDisplayedReason(r10)
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener r10 = r9.callback
            r10.adNotDisplayed(r9)
            return r1
        L_0x0021:
            com.startapp.android.publish.cache.c r11 = r9.adKey
            if (r11 != 0) goto L_0x0028
            r9.loadAd()
        L_0x0028:
            boolean r11 = r9.isAppOnForeground()
            if (r11 == 0) goto L_0x0144
            boolean r11 = r9.isNetworkAvailable()
            if (r11 == 0) goto L_0x013e
            boolean r11 = r9.isReady()
            r3 = 1
            if (r11 == 0) goto L_0x0121
            com.startapp.android.publish.common.model.AdPreferences$Placement r11 = r9.getPlacement()
            com.startapp.android.publish.adsCommon.a.f r4 = r9.shouldDisplayAd(r10, r11)
            boolean r5 = r4.mo62064a()
            if (r5 == 0) goto L_0x0102
            com.startapp.android.publish.cache.a r5 = com.startapp.android.publish.cache.C5071a.m3485a()
            com.startapp.android.publish.cache.c r6 = r9.adKey
            com.startapp.android.publish.adsCommon.g r5 = r5.mo62452a(r6)
            r9.f3034ad = r5
            com.startapp.android.publish.adsCommon.g r5 = r9.f3034ad
            if (r5 == 0) goto L_0x011e
            com.startapp.android.publish.common.model.AdPreferences$Placement r5 = r9.placement
            com.startapp.android.publish.common.model.AdPreferences$Placement r6 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_SPLASH
            r7 = 3
            if (r5 != r6) goto L_0x0078
            com.startapp.android.publish.adsCommon.m r5 = com.startapp.android.publish.adsCommon.C5059m.m3378a()
            boolean r5 = r5.mo62408n()
            if (r5 == 0) goto L_0x0078
            java.lang.String r11 = "StartAppAd"
            java.lang.String r2 = "App in background, can't display splash"
            com.startapp.common.p092a.C5155g.m3807a(r11, r7, r2)
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r11 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.APP_IN_BACKGROUND
            r9.setNotDisplayedReason(r11)
            goto L_0x011e
        L_0x0078:
            com.startapp.android.publish.adsCommon.g r5 = r9.f3034ad
            boolean r5 = r5.mo61221a(r10)
            if (r5 == 0) goto L_0x00eb
            com.startapp.android.publish.adsCommon.a.b r6 = com.startapp.android.publish.adsCommon.p077a.C4954b.m2946a()
            com.startapp.android.publish.adsCommon.a.a r8 = new com.startapp.android.publish.adsCommon.a.a
            r8.<init>(r11, r10)
            r6.mo62055a(r8)
            com.startapp.android.publish.adsCommon.StartAppAd$AdMode r11 = r9.adMode
            if (r11 == 0) goto L_0x00a6
            com.startapp.android.publish.common.model.AdPreferences$Placement r11 = r9.placement
            com.startapp.android.publish.common.model.AdPreferences$Placement r6 = com.startapp.android.publish.common.model.AdPreferences.Placement.INAPP_SPLASH
            if (r11 == r6) goto L_0x00a6
            com.startapp.android.publish.common.model.AdPreferences r11 = r9.adPreferences
            if (r11 == 0) goto L_0x00a7
            com.startapp.android.publish.common.model.AdPreferences r6 = new com.startapp.android.publish.common.model.AdPreferences
            r6.<init>()
            boolean r11 = r11.equals(r6)
            if (r11 == 0) goto L_0x00a6
            goto L_0x00a7
        L_0x00a6:
            r3 = 0
        L_0x00a7:
            if (r3 == 0) goto L_0x00fa
            com.startapp.android.publish.cache.a r11 = com.startapp.android.publish.cache.C5071a.m3485a()
            com.startapp.android.publish.adsCommon.StartAppAd$AdMode r3 = r9.adMode
            java.lang.String r3 = r11.mo62458a(r3)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r8 = "reset autoLoad after show "
            r6.append(r8)
            r6.append(r3)
            java.lang.String r6 = r6.toString()
            java.lang.String r8 = "preCache"
            com.startapp.common.p092a.C5155g.m3807a(r8, r7, r6)
            android.content.Context r6 = r9.context
            com.startapp.android.publish.adsCommon.C5051k.m3344b(r6, r3, r2)
            com.startapp.android.publish.adsCommon.StartAppAd$AdMode r3 = r9.adMode
            com.startapp.android.publish.adsCommon.StartAppAd$AdMode r6 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.AUTOMATIC
            if (r3 != r6) goto L_0x00fa
            android.content.Context r3 = r9.context
            com.startapp.android.publish.adsCommon.StartAppAd$AdMode r6 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.FULLPAGE
            java.lang.String r6 = r11.mo62458a(r6)
            com.startapp.android.publish.adsCommon.C5051k.m3344b(r3, r6, r2)
            android.content.Context r3 = r9.context
            com.startapp.android.publish.adsCommon.StartAppAd$AdMode r6 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.OFFERWALL
            java.lang.String r11 = r11.mo62458a(r6)
            com.startapp.android.publish.adsCommon.C5051k.m3344b(r3, r11, r2)
            goto L_0x00fa
        L_0x00eb:
            com.startapp.android.publish.adsCommon.g r11 = r9.f3034ad
            boolean r2 = r11 instanceof com.startapp.android.publish.adsCommon.C4925Ad
            if (r2 == 0) goto L_0x00fa
            com.startapp.android.publish.adsCommon.Ad r11 = (com.startapp.android.publish.adsCommon.C4925Ad) r11
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r11 = r11.getNotDisplayedReason()
            r9.setNotDisplayedReason(r11)
        L_0x00fa:
            com.startapp.android.publish.adsCommon.StartAppAd$AdMode r11 = r9.adMode
            com.startapp.android.publish.common.model.AdPreferences r2 = r9.adPreferences
            r9.loadAd(r11, r2, r0)
            goto L_0x011f
        L_0x0102:
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r11 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.AD_RULES
            r9.setNotDisplayedReason(r11)
            java.lang.Boolean r11 = com.startapp.common.Constants.m3707a()
            boolean r11 = r11.booleanValue()
            if (r11 == 0) goto L_0x011e
            com.startapp.common.a.i r11 = com.startapp.common.p092a.C5159i.m3829a()
            android.content.Context r2 = r9.context
            java.lang.String r3 = r4.mo62065b()
            r11.mo62876a(r2, r3)
        L_0x011e:
            r5 = 0
        L_0x011f:
            r1 = r5
            goto L_0x014a
        L_0x0121:
            com.startapp.android.publish.adsCommon.StartAppAd$AdMode r11 = r9.adMode
            com.startapp.android.publish.adsCommon.StartAppAd$AdMode r2 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.REWARDED_VIDEO
            if (r11 == r2) goto L_0x0134
            com.startapp.android.publish.adsCommon.StartAppAd$AdMode r11 = r9.adMode
            com.startapp.android.publish.adsCommon.StartAppAd$AdMode r2 = com.startapp.android.publish.adsCommon.StartAppAd.AdMode.VIDEO
            if (r11 == r2) goto L_0x0134
            boolean r11 = r9.showPreparedVideoFallbackAd(r10)
            if (r11 == 0) goto L_0x0134
            goto L_0x0135
        L_0x0134:
            r3 = 0
        L_0x0135:
            if (r3 != 0) goto L_0x013c
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r11 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.AD_NOT_READY
            r9.setNotDisplayedReason(r11)
        L_0x013c:
            r4 = r0
            goto L_0x014b
        L_0x013e:
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r11 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.NETWORK_PROBLEM
            r9.setNotDisplayedReason(r11)
            goto L_0x0149
        L_0x0144:
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r11 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.APP_IN_BACKGROUND
            r9.setNotDisplayedReason(r11)
        L_0x0149:
            r4 = r0
        L_0x014a:
            r3 = 0
        L_0x014b:
            if (r1 != 0) goto L_0x014f
            if (r3 == 0) goto L_0x0168
        L_0x014f:
            java.lang.String r11 = "com.startapp.android.HideDisplayBroadcastListener"
            r9.registerBroadcastReceiver(r11)
            java.lang.String r11 = "com.startapp.android.ShowDisplayBroadcastListener"
            r9.registerBroadcastReceiver(r11)
            java.lang.String r11 = "com.startapp.android.ShowFailedDisplayBroadcastListener"
            r9.registerBroadcastReceiver(r11)
            java.lang.String r11 = "com.startapp.android.OnClickCallback"
            r9.registerBroadcastReceiver(r11)
            java.lang.String r11 = "com.startapp.android.OnVideoCompleted"
            r9.registerBroadcastReceiver(r11)
        L_0x0168:
            if (r1 != 0) goto L_0x01eb
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r11 = r9.getNotDisplayedReason()
            if (r11 != 0) goto L_0x0175
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r11 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.INTERNAL_ERROR
            r9.setNotDisplayedReason(r11)
        L_0x0175:
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r11 = r9.getNotDisplayedReason()
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r2 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.NETWORK_PROBLEM
            if (r11 == r2) goto L_0x01e0
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r11 = r9.getNotDisplayedReason()
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r2 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.AD_RULES
            if (r11 == r2) goto L_0x01c7
            if (r3 == 0) goto L_0x01a6
            android.content.Context r11 = r9.context
            com.startapp.android.publish.adsCommon.g r2 = r9.f3034ad
            if (r2 == 0) goto L_0x018e
            goto L_0x0198
        L_0x018e:
            com.startapp.android.publish.cache.a r2 = com.startapp.android.publish.cache.C5071a.m3485a()
            com.startapp.android.publish.cache.c r4 = r9.adKey
            com.startapp.android.publish.adsCommon.g r2 = r2.mo62465b(r4)
        L_0x0198:
            java.lang.String[] r2 = com.startapp.android.publish.adsCommon.C4988c.m3119a(r2)
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r4 = com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason.AD_NOT_READY_VIDEO_FALLBACK
            java.lang.String r4 = r4.toString()
            com.startapp.android.publish.adsCommon.C4988c.m3112a(r11, r2, r10, r4)
            goto L_0x01e0
        L_0x01a6:
            android.content.Context r11 = r9.context
            com.startapp.android.publish.adsCommon.g r2 = r9.f3034ad
            if (r2 == 0) goto L_0x01ad
            goto L_0x01b7
        L_0x01ad:
            com.startapp.android.publish.cache.a r2 = com.startapp.android.publish.cache.C5071a.m3485a()
            com.startapp.android.publish.cache.c r4 = r9.adKey
            com.startapp.android.publish.adsCommon.g r2 = r2.mo62465b(r4)
        L_0x01b7:
            java.lang.String[] r2 = com.startapp.android.publish.adsCommon.C4988c.m3119a(r2)
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener$NotDisplayedReason r4 = r9.getNotDisplayedReason()
            java.lang.String r4 = r4.toString()
            com.startapp.android.publish.adsCommon.C4988c.m3112a(r11, r2, r10, r4)
            goto L_0x01e0
        L_0x01c7:
            if (r4 == 0) goto L_0x01e0
            android.content.Context r11 = r9.context
            com.startapp.android.publish.cache.a r2 = com.startapp.android.publish.cache.C5071a.m3485a()
            com.startapp.android.publish.cache.c r5 = r9.adKey
            com.startapp.android.publish.adsCommon.g r2 = r2.mo62465b(r5)
            java.lang.String[] r2 = com.startapp.android.publish.adsCommon.C4988c.m3119a(r2)
            java.lang.String r4 = r4.mo62066c()
            com.startapp.android.publish.adsCommon.C4988c.m3112a(r11, r2, r10, r4)
        L_0x01e0:
            r9.f3034ad = r0
            if (r3 != 0) goto L_0x01eb
            com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener r10 = r9.callback
            if (r10 == 0) goto L_0x01eb
            r10.adNotDisplayed(r9)
        L_0x01eb:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.adsCommon.StartAppAd.show(java.lang.String, com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener):boolean");
    }

    private boolean showPreparedVideoFallbackAd(String str) {
        if (canShowAd() && C4983b.m3032a().mo62154H().mo62429h()) {
            AdPreferences adPreferences2 = this.adPreferences;
            if (adPreferences2 == null) {
                adPreferences2 = new AdPreferences();
            }
            adPreferences2.setType(AdType.NON_VIDEO);
            Placement placement = getPlacement();
            C5021g b = C5071a.m3485a().mo62465b(new C5080c(placement, adPreferences2));
            if (b != null && b.isReady() && shouldDisplayAd(str, placement).mo62064a()) {
                b.setVideoCancelCallBack(true);
                if (Constants.m3707a().booleanValue()) {
                    C5159i.m3829a().mo62876a(this.context, "display Video fallback");
                }
                return b.mo61221a(str);
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public C4958f shouldDisplayAd(String str, Placement placement) {
        return C4983b.m3032a().mo62152F().mo62061a(placement, str);
    }

    /* access modifiers changed from: protected */
    public Placement getPlacement() {
        Placement placement = super.getPlacement();
        return (placement != null || this.adKey == null || C5071a.m3485a().mo62465b(this.adKey) == null) ? placement : ((C4925Ad) C5071a.m3485a().mo62465b(this.adKey)).getPlacement();
    }

    /* access modifiers changed from: protected */
    public String getAdHtml() {
        C5021g b = C5071a.m3485a().mo62465b(this.adKey);
        if (b == null || !(b instanceof C5003e)) {
            return null;
        }
        return ((C5003e) b).mo62243f();
    }

    private void registerBroadcastReceiver(String str) {
        C5160b.m3831a(this.context).mo62879a(this.callbackBroadcastReceiver, new IntentFilter(str));
    }

    @Deprecated
    public boolean show() {
        return show(null, null);
    }

    private void setAdMode(AdMode adMode2) {
        this.adMode = adMode2;
    }

    private void setAdPrefs(AdPreferences adPreferences2) {
        this.adPreferences = adPreferences2;
    }

    public void loadAd() {
        loadAd(AdMode.AUTOMATIC, new AdPreferences(), null);
    }

    public void loadAd(AdPreferences adPreferences2) {
        loadAd(AdMode.AUTOMATIC, adPreferences2, null);
    }

    public void loadAd(AdEventListener adEventListener) {
        loadAd(AdMode.AUTOMATIC, new AdPreferences(), adEventListener);
    }

    public void loadAd(AdPreferences adPreferences2, AdEventListener adEventListener) {
        loadAd(AdMode.AUTOMATIC, adPreferences2, adEventListener);
    }

    public void loadAd(AdMode adMode2) {
        loadAd(adMode2, new AdPreferences(), null);
    }

    public void loadAd(AdMode adMode2, AdPreferences adPreferences2) {
        loadAd(adMode2, adPreferences2, null);
    }

    public void loadAd(AdMode adMode2, AdEventListener adEventListener) {
        loadAd(adMode2, new AdPreferences(), adEventListener);
    }

    public void loadAd(AdMode adMode2, AdPreferences adPreferences2, AdEventListener adEventListener) {
        setAdMode(adMode2);
        setAdPrefs(adPreferences2);
        try {
            load(adPreferences2, adEventListener);
        } catch (Exception e) {
            C5017f.m3256a(this.context, C5015d.EXCEPTION, "StartAppAd.loadAd - unexpected Error occurd", e.getMessage(), "");
            if (adEventListener != null) {
                adEventListener.onFailedToReceiveAd(this);
            }
        }
    }

    public boolean showAd() {
        return showAd(null, null);
    }

    public boolean showAd(String str) {
        return showAd(str, null);
    }

    public boolean showAd(AdDisplayListener adDisplayListener) {
        return showAd(null, adDisplayListener);
    }

    public boolean showAd(String str, AdDisplayListener adDisplayListener) {
        try {
            return show(str, adDisplayListener);
        } catch (Exception e) {
            C5017f.m3256a(this.context, C5015d.EXCEPTION, "StartAppAd.showAd - unexpected Error occurd", e.getMessage(), "");
            setNotDisplayedReason(NotDisplayedReason.INTERNAL_ERROR);
            if (adDisplayListener != null) {
                adDisplayListener.adNotDisplayed(null);
            }
            return false;
        }
    }

    public void setVideoListener(VideoListener videoListener2) {
        this.videoListener = videoListener2;
    }

    public void onResume() {
        if (!isReady()) {
            loadAd();
        }
    }

    public void onBackPressed() {
        if (!showAd("exit_ad")) {
            C5155g.m3807a(TAG, 3, "Could not display StartAppAd onBackPressed");
        }
        C5059m.m3378a().mo62407m();
    }

    public void onSaveInstanceState(Bundle bundle) {
        int i = C49333.f3039a[this.adMode.ordinal()];
        int i2 = 4;
        if (i == 1) {
            i2 = 1;
        } else if (i == 2) {
            i2 = 2;
        } else if (i == 3) {
            i2 = 3;
        } else if (i != 4) {
            i2 = 0;
        }
        AdPreferences adPreferences2 = this.adPreferences;
        if (adPreferences2 != null) {
            bundle.putSerializable("AdPrefs", adPreferences2);
        }
        bundle.putInt("AdMode", i2);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        int i = bundle.getInt("AdMode");
        this.adMode = AdMode.AUTOMATIC;
        if (i == 1) {
            this.adMode = AdMode.FULLPAGE;
        } else if (i == 2) {
            this.adMode = AdMode.OFFERWALL;
        } else if (i == 3) {
            this.adMode = AdMode.OVERLAY;
        } else if (i == 4) {
            this.adMode = AdMode.REWARDED_VIDEO;
        } else if (i == 5) {
            this.adMode = AdMode.VIDEO;
        }
        Serializable serializable = bundle.getSerializable("AdPrefs");
        if (serializable != null) {
            this.adPreferences = (AdPreferences) serializable;
        }
    }

    public void close() {
        if (this.callbackBroadcastReceiver != null) {
            C5160b.m3831a(this.context).mo62878a(this.callbackBroadcastReceiver);
        }
        C5160b.m3831a(this.context).mo62880a(new Intent("com.startapp.android.CloseAdActivity"));
    }

    public boolean isReady() {
        C5021g b = C5071a.m3485a().mo62465b(this.adKey);
        if (b != null) {
            return b.isReady();
        }
        return false;
    }

    public boolean isNetworkAvailable() {
        return C4946i.m2924a(this.context);
    }

    private boolean isAppOnForeground() {
        try {
            return !C4983b.m3032a().mo62160N() || C4946i.m2936e(this.context);
        } catch (Exception unused) {
            return true;
        }
    }

    public static void disableSplash() {
        C5059m.m3378a().mo62404j();
    }

    public C5080c loadSplash(AdPreferences adPreferences2, AdEventListener adEventListener) {
        this.adKey = C5071a.m3485a().mo62456a(this.context, this, adPreferences2, adEventListener);
        return this.adKey;
    }

    public static void enableAutoInterstitial() {
        C5008f.m3205a().mo62261b();
    }

    public static void disableAutoInterstitial() {
        C5008f.m3205a().mo62262c();
    }

    public static void setAutoInterstitialPreferences(AutoInterstitialPreferences autoInterstitialPreferences) {
        C5008f.m3205a().mo62260a(autoInterstitialPreferences);
    }

    public static void showSplash(Activity activity, Bundle bundle) {
        showSplash(activity, bundle, new SplashConfig());
    }

    public static void showSplash(Activity activity, Bundle bundle, SplashConfig splashConfig) {
        showSplash(activity, bundle, splashConfig, new AdPreferences());
    }

    public static void showSplash(Activity activity, Bundle bundle, AdPreferences adPreferences2) {
        showSplash(activity, bundle, new SplashConfig(), adPreferences2);
    }

    public static void showSplash(Activity activity, Bundle bundle, SplashConfig splashConfig, AdPreferences adPreferences2) {
        showSplash(activity, bundle, splashConfig, adPreferences2, null);
    }

    public static void showSplash(Activity activity, Bundle bundle, SplashConfig splashConfig, AdPreferences adPreferences2, SplashHideListener splashHideListener) {
        showSplash(activity, bundle, splashConfig, adPreferences2, splashHideListener, true);
    }

    static void showSplash(final Activity activity, Bundle bundle, SplashConfig splashConfig, AdPreferences adPreferences2, final SplashHideListener splashHideListener, boolean z) {
        if (bundle == null && MetaData.getInstance().canShowAd()) {
            try {
                C5059m a = C5059m.m3378a();
                if (!a.mo62405k() && z) {
                    a.mo62391d(true);
                }
                a.mo62387c(z);
                if (!z) {
                    if (adPreferences2 == null) {
                        adPreferences2 = new AdPreferences();
                    }
                    adPreferences2.setAs(Boolean.valueOf(true));
                }
                splashConfig.setDefaults(activity);
                C4946i.m2911a(activity, true);
                Intent intent = new Intent(activity, C4946i.m2903a((Context) activity, OverlayActivity.class, AppWallActivity.class));
                intent.putExtra("SplashConfig", splashConfig);
                intent.putExtra("AdPreference", adPreferences2);
                intent.putExtra("testMode", testMode);
                intent.putExtra("fullscreen", C4988c.m3116a(activity));
                intent.putExtra("placement", Placement.INAPP_SPLASH.getIndex());
                intent.addFlags(1140883456);
                activity.startActivity(intent);
                C5160b.m3831a((Context) activity).mo62879a(new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        C4946i.m2911a(activity, false);
                        SplashHideListener splashHideListener = splashHideListener;
                        if (splashHideListener != null) {
                            splashHideListener.splashHidden();
                        }
                        C5160b.m3831a((Context) activity).mo62878a((BroadcastReceiver) this);
                    }
                }, new IntentFilter("com.startapp.android.splashHidden"));
            } catch (Exception e) {
                if (splashHideListener != null) {
                    splashHideListener.splashHidden();
                    C5017f.m3256a(activity, C5015d.EXCEPTION, "StartAppAd.showSplash - unexpected Error occurd", e.getMessage(), "");
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public String getLauncherName() {
        C5021g b = C5071a.m3485a().mo62465b(this.adKey);
        if (b != null) {
            return b.mo61222a_();
        }
        return C4946i.m2932c(getContext());
    }

    public AdState getState() {
        C5021g b = C5071a.m3485a().mo62465b(this.adKey);
        if (b != null) {
            return b.getState();
        }
        return AdState.UN_INITIALIZED;
    }

    public boolean isBelowMinCPM() {
        C5021g b = C5071a.m3485a().mo62465b(this.adKey);
        if (b != null) {
            return b.isBelowMinCPM();
        }
        return false;
    }

    public static boolean showAd(Context context) {
        try {
            return new StartAppAd(context).showAd();
        } catch (Exception e) {
            C5017f.m3256a(context, C5015d.EXCEPTION, "StartAppAd.showAd(one line integration) - unexpected Error occurd", e.getMessage(), "");
            return false;
        }
    }

    public static void onBackPressed(Context context) {
        new StartAppAd(context).onBackPressed();
    }
}
