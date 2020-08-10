package com.startapp.android.publish.ads.video;

import android.content.Context;
import android.util.Pair;
import com.startapp.android.publish.ads.video.C4923h.C4924a;
import com.startapp.android.publish.adsCommon.C4925Ad.AdType;
import com.startapp.android.publish.adsCommon.Utils.C4940d;
import com.startapp.android.publish.adsCommon.Utils.C4941e;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.common.model.GetAdRequest;

/* renamed from: com.startapp.android.publish.ads.video.a */
/* compiled from: StartAppSDK */
public class C4849a extends GetAdRequest {

    /* renamed from: a */
    private VideoRequestType f2738a;

    /* renamed from: b */
    private VideoRequestMode f2739b = VideoRequestMode.INTERSTITIAL;

    public void fillAdPreferences(Context context, AdPreferences adPreferences, Placement placement, Pair<String, String> pair) {
        super.fillAdPreferences(context, adPreferences, placement, pair);
        m2551a(context);
        m2550a();
    }

    /* renamed from: a */
    private void m2550a() {
        if (getType() == AdType.REWARDED_VIDEO) {
            this.f2739b = VideoRequestMode.REWARDED;
        }
        if (getType() == AdType.VIDEO) {
            this.f2739b = VideoRequestMode.INTERSTITIAL;
        }
    }

    /* renamed from: a */
    private void m2551a(Context context) {
        if (getType() != null) {
            if (getType() == AdType.NON_VIDEO) {
                this.f2738a = VideoRequestType.DISABLED;
            } else if (getType() == AdType.VIDEO_NO_VAST) {
                this.f2738a = VideoRequestType.FORCED_NONVAST;
            } else if (isAdTypeVideo()) {
                this.f2738a = VideoRequestType.FORCED;
            }
        } else if (C4923h.m2843a(context) != C4924a.ELIGIBLE) {
            this.f2738a = VideoRequestType.DISABLED;
        } else if (!C4946i.m2923a(2)) {
            this.f2738a = VideoRequestType.FORCED;
        } else {
            this.f2738a = VideoRequestType.ENABLED;
        }
    }

    public C4941e getNameValueMap() {
        C4941e nameValueMap = super.getNameValueMap();
        if (nameValueMap == null) {
            nameValueMap = new C4940d();
        }
        nameValueMap.mo62030a("video", (Object) this.f2738a, false);
        nameValueMap.mo62030a("videoMode", (Object) this.f2739b, false);
        return nameValueMap;
    }
}
