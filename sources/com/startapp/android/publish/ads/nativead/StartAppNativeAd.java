package com.startapp.android.publish.ads.nativead;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.adsCommon.adListeners.C4965b;
import com.startapp.android.publish.adsCommon.adinformation.C4968a;
import com.startapp.android.publish.adsCommon.p077a.C4953a;
import com.startapp.android.publish.adsCommon.p077a.C4954b;
import com.startapp.android.publish.adsCommon.p077a.C4958f;
import com.startapp.android.publish.adsCommon.p077a.C4959g;
import com.startapp.android.publish.common.model.AdDetails;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.Constants;
import com.startapp.common.p092a.C5155g;
import com.startapp.common.p092a.C5159i;
import java.util.ArrayList;
import java.util.List;

/* compiled from: StartAppSDK */
public class StartAppNativeAd extends C4925Ad implements C4817a {
    private static final String TAG = "StartAppNativeAd";
    private static final long serialVersionUID = 1;
    private C4818a adEventDelegate;
    boolean isLoading = false;
    private List<NativeAdDetails> listNativeAds = new ArrayList();
    private C4821b nativeAd;
    private NativeAdPreferences preferences;
    private int totalObjectsLoaded = 0;

    /* renamed from: com.startapp.android.publish.ads.nativead.StartAppNativeAd$a */
    /* compiled from: StartAppSDK */
    private class C4818a implements AdEventListener {

        /* renamed from: b */
        private AdEventListener f2678b = null;

        public C4818a(AdEventListener adEventListener) {
            this.f2678b = new C4965b(adEventListener);
        }

        public void onReceiveAd(C4925Ad ad) {
            C5155g.m3807a(StartAppNativeAd.TAG, 3, "NativeAd Received");
            StartAppNativeAd.this.initNativeAdList();
        }

        public void onFailedToReceiveAd(C4925Ad ad) {
            C5155g.m3807a(StartAppNativeAd.TAG, 3, "NativeAd Failed to load");
            StartAppNativeAd.this.setErrorMessage(ad.getErrorMessage());
            AdEventListener adEventListener = this.f2678b;
            if (adEventListener != null) {
                adEventListener.onFailedToReceiveAd(StartAppNativeAd.this);
                this.f2678b = null;
            }
            StartAppNativeAd startAppNativeAd = StartAppNativeAd.this;
            startAppNativeAd.isLoading = false;
            startAppNativeAd.initNativeAdList();
        }

        /* renamed from: a */
        public AdEventListener mo61544a() {
            return this.f2678b;
        }
    }

    /* renamed from: com.startapp.android.publish.ads.nativead.StartAppNativeAd$b */
    /* compiled from: StartAppSDK */
    public enum C4819b {
        LAUNCH_APP,
        OPEN_MARKET
    }

    /* access modifiers changed from: protected */
    public void loadAds(AdPreferences adPreferences, AdEventListener adEventListener) {
    }

    public StartAppNativeAd(Context context) {
        super(context, Placement.INAPP_NATIVE);
    }

    private NativeAdPreferences getPreferences() {
        return this.preferences;
    }

    private void setPreferences(NativeAdPreferences nativeAdPreferences) {
        this.preferences = nativeAdPreferences;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n===== StartAppNativeAd =====\n");
        for (int i = 0; i < getNumberOfAds(); i++) {
            stringBuffer.append(this.listNativeAds.get(i));
        }
        stringBuffer.append("===== End StartAppNativeAd =====");
        return stringBuffer.toString();
    }

    /* access modifiers changed from: 0000 */
    public void initNativeAdList() {
        this.totalObjectsLoaded = 0;
        if (this.listNativeAds == null) {
            this.listNativeAds = new ArrayList();
        }
        this.listNativeAds.clear();
        C4821b bVar = this.nativeAd;
        if (bVar != null && bVar.mo62336d() != null) {
            for (int i = 0; i < this.nativeAd.mo62336d().size(); i++) {
                this.listNativeAds.add(new NativeAdDetails((AdDetails) this.nativeAd.mo62336d().get(i), getPreferences(), i, this));
            }
        }
    }

    public void onNativeAdDetailsLoaded(int i) {
        this.totalObjectsLoaded++;
        if (this.nativeAd.mo62336d() != null && this.totalObjectsLoaded == this.nativeAd.mo62336d().size()) {
            onNativeAdLoaded();
        }
    }

    private void onNativeAdLoaded() {
        String str = TAG;
        C5155g.m3807a(str, 3, "Ad Loaded successfully");
        this.isLoading = false;
        setErrorMessage(null);
        if (this.adEventDelegate != null) {
            C5155g.m3807a(str, 3, "Calling original RecienedAd callback");
            AdEventListener a = this.adEventDelegate.mo61544a();
            if (a != null) {
                a.onReceiveAd(this);
            }
        }
    }

    public int getNumberOfAds() {
        List<NativeAdDetails> list = this.listNativeAds;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public boolean isBelowMinCPM() {
        return this.nativeAd.isBelowMinCPM();
    }

    public boolean loadAd() {
        return loadAd(new NativeAdPreferences(), null);
    }

    public boolean loadAd(AdEventListener adEventListener) {
        return loadAd(new NativeAdPreferences(), adEventListener);
    }

    public boolean loadAd(NativeAdPreferences nativeAdPreferences) {
        return loadAd(nativeAdPreferences, null);
    }

    public boolean loadAd(NativeAdPreferences nativeAdPreferences, AdEventListener adEventListener) {
        C5155g.m3807a(TAG, 3, "Start loading StartAppNativeAd");
        this.adEventDelegate = new C4818a(adEventListener);
        setPreferences(nativeAdPreferences);
        if (this.isLoading) {
            setErrorMessage("Ad is currently being loaded");
            return false;
        }
        this.isLoading = true;
        this.nativeAd = new C4821b(this.context, getPreferences());
        return this.nativeAd.load(nativeAdPreferences, this.adEventDelegate);
    }

    public ArrayList<NativeAdDetails> getNativeAds() {
        return getNativeAds(null);
    }

    public ArrayList<NativeAdDetails> getNativeAds(String str) {
        ArrayList<NativeAdDetails> arrayList = new ArrayList<>();
        C4958f a = C4959g.m2962a().mo62067b().mo62061a(Placement.INAPP_NATIVE, str);
        if (a.mo62064a()) {
            List<NativeAdDetails> list = this.listNativeAds;
            if (list != null) {
                for (NativeAdDetails nativeAdDetails : list) {
                    nativeAdDetails.mo61497a(str);
                    arrayList.add(nativeAdDetails);
                }
                C4954b.m2946a().mo62055a(new C4953a(Placement.INAPP_NATIVE, str));
            }
        } else {
            C4988c.m3112a(this.context, C4988c.m3120a(getAdDetailsList()), str, a.mo62066c());
            if (Constants.m3707a().booleanValue()) {
                C5159i.m3829a().mo62876a(this.context, a.mo62065b());
            }
        }
        return arrayList;
    }

    private List<AdDetails> getAdDetailsList() {
        ArrayList arrayList = new ArrayList();
        List<NativeAdDetails> list = this.listNativeAds;
        if (list != null) {
            for (NativeAdDetails b : list) {
                arrayList.add(b.mo61498b());
            }
        }
        return arrayList;
    }

    public static String getPrivacyURL() {
        if (C4968a.m2985b().mo62110c() == null) {
            return "";
        }
        String c = C4968a.m2985b().mo62110c();
        if (!c.contains("http://")) {
            String str = "https://";
            if (!c.contains(str)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(C4968a.m2985b().mo62110c());
                return sb.toString();
            }
        }
        return C4968a.m2985b().mo62110c();
    }

    public static String getPrivacyImageUrl() {
        return C4968a.m2985b().mo62111d();
    }
}
