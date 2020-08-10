package com.startapp.android.publish.ads.p068c.p070b;

import android.content.Context;
import android.content.Intent;
import com.mobiroller.constants.Constants;
import com.startapp.android.publish.ads.list3d.C4811f;
import com.startapp.android.publish.ads.list3d.List3DActivity;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.C4925Ad.AdState;
import com.startapp.android.publish.adsCommon.C4988c;
import com.startapp.android.publish.adsCommon.C5021g;
import com.startapp.android.publish.adsCommon.C5033h;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import java.util.UUID;

/* renamed from: com.startapp.android.publish.ads.c.b.b */
/* compiled from: StartAppSDK */
public class C4791b extends C5033h implements C5021g {

    /* renamed from: a */
    private static String f2558a = null;
    private static final long serialVersionUID = 1;
    private final String uuid = UUID.randomUUID().toString();

    public C4791b(Context context) {
        super(context, Placement.INAPP_OFFER_WALL);
        if (f2558a == null) {
            f2558a = C4946i.m2932c(context);
        }
    }

    /* access modifiers changed from: protected */
    public void loadAds(AdPreferences adPreferences, AdEventListener adEventListener) {
        new C4790a(this.context, this, adPreferences, adEventListener).mo62216c();
    }

    /* renamed from: a */
    public boolean mo61221a(String str) {
        C4811f.m2473a().mo61493a(this.uuid).mo61489b(C4988c.m3095a());
        boolean a = this.activityExtra != null ? this.activityExtra.mo62047a() : false;
        if (hasAdCacheTtlPassed()) {
            setNotDisplayedReason(NotDisplayedReason.AD_EXPIRED);
            return false;
        }
        Intent intent = new Intent(this.context, List3DActivity.class);
        intent.putExtra("adInfoOverride", getAdInfoOverride());
        intent.putExtra("fullscreen", a);
        intent.putExtra("adTag", str);
        intent.putExtra("lastLoadTime", getLastLoadTime());
        intent.putExtra("adCacheTtl", getAdCacheTtl());
        intent.putExtra(Constants.KEY_RSS_POSITION, C4988c.m3121b());
        intent.putExtra("listModelUuid", this.uuid);
        intent.addFlags(343932928);
        this.context.startActivity(intent);
        if (!AdsConstants.OVERRIDE_NETWORK.booleanValue()) {
            setState(AdState.UN_INITIALIZED);
        }
        return true;
    }

    /* renamed from: a_ */
    public String mo61222a_() {
        return f2558a;
    }

    /* renamed from: a */
    public String mo61342a() {
        return this.uuid;
    }

    public Long getLastLoadTime() {
        return super.getLastLoadTime();
    }

    public Long getAdCacheTtl() {
        return super.getAdCacheTtl();
    }

    public boolean hasAdCacheTtlPassed() {
        return super.hasAdCacheTtlPassed();
    }

    public boolean getVideoCancelCallBack() {
        return super.getVideoCancelCallBack();
    }

    public void setVideoCancelCallBack(boolean z) {
        super.setVideoCancelCallBack(z);
    }
}
