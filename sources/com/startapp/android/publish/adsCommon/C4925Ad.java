package com.startapp.android.publish.adsCommon;

import android.content.Context;
import com.startapp.android.publish.adsCommon.Utils.C4944g;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.adsCommon.adListeners.C4965b;
import com.startapp.android.publish.adsCommon.adinformation.C4978c;
import com.startapp.android.publish.cache.C5081d;
import com.startapp.android.publish.common.metaData.C5116d;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import java.io.Serializable;

/* renamed from: com.startapp.android.publish.adsCommon.Ad */
/* compiled from: StartAppSDK */
public abstract class C4925Ad implements Serializable {
    private static boolean init = false;
    private static final long serialVersionUID = 1;
    protected C4952a activityExtra;
    protected Long adCacheTtl = null;
    private C4978c adInfoOverride;
    protected boolean belowMinCPM = false;
    protected transient Context context;
    protected String errorMessage = null;
    protected Serializable extraData = null;
    private Long lastLoadTime = null;
    private NotDisplayedReason notDisplayedReason;
    protected Placement placement;
    private AdState state = AdState.UN_INITIALIZED;
    private AdType type;
    private boolean videoCancelCallBack;

    /* renamed from: com.startapp.android.publish.adsCommon.Ad$AdState */
    /* compiled from: StartAppSDK */
    public enum AdState {
        UN_INITIALIZED,
        PROCESSING,
        READY
    }

    /* renamed from: com.startapp.android.publish.adsCommon.Ad$AdType */
    /* compiled from: StartAppSDK */
    public enum AdType {
        INTERSTITIAL,
        RICH_TEXT,
        VIDEO,
        REWARDED_VIDEO,
        NON_VIDEO,
        VIDEO_NO_VAST
    }

    /* access modifiers changed from: protected */
    public abstract void loadAds(AdPreferences adPreferences, AdEventListener adEventListener);

    @Deprecated
    public boolean show() {
        return false;
    }

    public C4925Ad(Context context2, Placement placement2) {
        this.context = context2;
        this.placement = placement2;
        if (C4946i.m2935e()) {
            this.adInfoOverride = C4978c.m3006a();
        }
    }

    public Serializable getExtraData() {
        return this.extraData;
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context2) {
        this.context = context2;
    }

    public void setActivityExtra(C4952a aVar) {
        this.activityExtra = aVar;
    }

    public void setExtraData(Serializable serializable) {
        this.extraData = serializable;
    }

    public AdState getState() {
        return this.state;
    }

    public boolean isBelowMinCPM() {
        return this.belowMinCPM;
    }

    public void setState(AdState adState) {
        this.state = adState;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String str) {
        this.errorMessage = str;
    }

    public C4978c getAdInfoOverride() {
        return this.adInfoOverride;
    }

    public void setAdInfoOverride(C4978c cVar) {
        this.adInfoOverride = cVar;
    }

    /* access modifiers changed from: protected */
    public Placement getPlacement() {
        return this.placement;
    }

    /* access modifiers changed from: protected */
    public void setPlacement(Placement placement2) {
        this.placement = placement2;
    }

    @Deprecated
    public boolean load() {
        return load(new AdPreferences(), null);
    }

    @Deprecated
    public boolean load(AdEventListener adEventListener) {
        return load(new AdPreferences(), adEventListener);
    }

    @Deprecated
    public boolean load(AdPreferences adPreferences) {
        return load(adPreferences, null);
    }

    @Deprecated
    public boolean load(AdPreferences adPreferences, AdEventListener adEventListener) {
        return load(adPreferences, adEventListener, true);
    }

    /* access modifiers changed from: protected */
    public boolean load(final AdPreferences adPreferences, AdEventListener adEventListener, boolean z) {
        boolean z2;
        final C4965b bVar = new C4965b(adEventListener);
        final C49261 r10 = new AdEventListener() {
            public void onReceiveAd(C4925Ad ad) {
                C4925Ad.this.setLastLoadTime(Long.valueOf(System.currentTimeMillis()));
                bVar.onReceiveAd(ad);
            }

            public void onFailedToReceiveAd(C4925Ad ad) {
                bVar.onFailedToReceiveAd(ad);
            }
        };
        if (!init) {
            C5053l.m3367c(this.context);
            init = true;
        }
        C4946i.m2913a(this.context, adPreferences);
        String str = "";
        if (adPreferences.getProductId() == null || str.equals(adPreferences.getProductId())) {
            str = "app ID was not set.";
            z2 = true;
        } else {
            z2 = false;
        }
        if (this.state != AdState.UN_INITIALIZED) {
            str = "load() was already called.";
            z2 = true;
        }
        if (!C4946i.m2924a(this.context)) {
            str = "network not available.";
            z2 = true;
        }
        if (!canShowAd()) {
            str = "serving ads disabled";
            z2 = true;
        }
        if (z2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Ad wasn't loaded: ");
            sb.append(str);
            setErrorMessage(sb.toString());
            r10.onFailedToReceiveAd(this);
            return false;
        }
        setState(AdState.PROCESSING);
        C49272 r7 = new C5116d() {
            /* renamed from: a */
            public void mo61600a() {
                MetaData.getInstance().removeMetaDataListener(this);
                C4925Ad.this.loadAds(adPreferences, r10);
            }

            /* renamed from: b */
            public void mo61601b() {
                MetaData.getInstance().removeMetaDataListener(this);
                C4925Ad.this.loadAds(adPreferences, r10);
            }
        };
        if (adPreferences.getType() != null) {
            setType(adPreferences.getType());
        }
        MetaData.getInstance().loadFromServer(this.context, adPreferences, C4944g.m2886d().mo62036c(), z, r7);
        return true;
    }

    public boolean isReady() {
        return this.state == AdState.READY && !hasAdCacheTtlPassed();
    }

    public NotDisplayedReason getNotDisplayedReason() {
        return this.notDisplayedReason;
    }

    /* access modifiers changed from: protected */
    public void setNotDisplayedReason(NotDisplayedReason notDisplayedReason2) {
        this.notDisplayedReason = notDisplayedReason2;
    }

    /* access modifiers changed from: protected */
    public Long getAdCacheTtl() {
        long fallbackAdCacheTtl = getFallbackAdCacheTtl();
        Long l = this.adCacheTtl;
        if (l != null) {
            fallbackAdCacheTtl = Math.min(l.longValue(), fallbackAdCacheTtl);
        }
        return Long.valueOf(fallbackAdCacheTtl);
    }

    /* access modifiers changed from: protected */
    public long getFallbackAdCacheTtl() {
        return C5081d.m3532a().mo62488b().getAdCacheTtl();
    }

    /* access modifiers changed from: protected */
    public Long getLastLoadTime() {
        return this.lastLoadTime;
    }

    /* access modifiers changed from: private */
    public void setLastLoadTime(Long l) {
        this.lastLoadTime = l;
    }

    /* access modifiers changed from: protected */
    public boolean hasAdCacheTtlPassed() {
        boolean z = false;
        if (this.lastLoadTime == null) {
            return false;
        }
        if (System.currentTimeMillis() - this.lastLoadTime.longValue() > getAdCacheTtl().longValue()) {
            z = true;
        }
        return z;
    }

    private void setType(AdType adType) {
        this.type = adType;
    }

    public AdType getType() {
        return this.type;
    }

    /* access modifiers changed from: protected */
    public boolean getVideoCancelCallBack() {
        return this.videoCancelCallBack;
    }

    /* access modifiers changed from: protected */
    public void setVideoCancelCallBack(boolean z) {
        this.videoCancelCallBack = z;
    }

    /* access modifiers changed from: protected */
    public boolean canShowAd() {
        return MetaData.getInstance().canShowAd();
    }
}
