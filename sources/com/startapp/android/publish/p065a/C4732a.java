package com.startapp.android.publish.p065a;

import android.content.Context;
import android.content.Intent;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.AdsConstants.AdApiType;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.C4998d;
import com.startapp.android.publish.adsCommon.C5033h;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.adsCommon.p078b.C4987c;
import com.startapp.android.publish.adsCommon.p091k.C5052a;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.common.model.GetAdRequest;
import com.startapp.android.publish.common.model.GetAdResponse;
import com.startapp.common.C5160b;
import com.startapp.common.C5186e;
import com.startapp.common.p092a.C5155g;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.startapp.android.publish.a.a */
/* compiled from: StartAppSDK */
public abstract class C4732a extends C4998d {

    /* renamed from: g */
    private int f2463g = 0;

    /* renamed from: h */
    private Set<String> f2464h = new HashSet();

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo61144a(C4925Ad ad);

    public C4732a(Context context, C4925Ad ad, AdPreferences adPreferences, AdEventListener adEventListener, Placement placement) {
        super(context, ad, adPreferences, adEventListener, placement);
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public Object mo61147e() {
        GetAdRequest a = mo61353a();
        if (a == null) {
            return null;
        }
        if (this.f2464h.size() == 0) {
            this.f2464h.add(this.f3196a.getPackageName());
        }
        boolean z = false;
        if (this.f2463g > 0) {
            a.setEngInclude(false);
        }
        a.setPackageExclude(this.f2464h);
        if (this.f2463g == 0) {
            z = true;
        }
        a.setEngInclude(z);
        try {
            return (GetAdResponse) C5052a.m3347a(this.f3196a, AdsConstants.m2855a(AdApiType.JSON, mo62218f()), a, null, GetAdResponse.class);
        } catch (C5186e e) {
            C5155g.m3808a("AppPresence", 6, "Unable to handle GetAdsSetService command!!!!", e);
            this.f3201f = e.getMessage();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo61146a(Object obj) {
        GetAdResponse getAdResponse = (GetAdResponse) obj;
        String str = "Empty Response";
        boolean z = false;
        String str2 = "AppPresence";
        if (obj == null) {
            this.f3201f = str;
            C5155g.m3807a(str2, 6, "Error Empty Response");
            return false;
        } else if (!getAdResponse.isValidResponse()) {
            this.f3201f = getAdResponse.getErrorMessage();
            StringBuilder sb = new StringBuilder();
            sb.append("Error msg = [");
            sb.append(this.f3201f);
            sb.append("]");
            C5155g.m3807a(str2, 6, sb.toString());
            return false;
        } else {
            C5033h hVar = (C5033h) this.f3197b;
            List a = C4987c.m3089a(this.f3196a, getAdResponse.getAdsDetails(), this.f2463g, this.f2464h);
            hVar.mo62335a(a);
            hVar.setAdInfoOverride(getAdResponse.getAdInfoOverride());
            if (getAdResponse.getAdsDetails() != null && getAdResponse.getAdsDetails().size() > 0) {
                z = true;
            }
            if (!z) {
                this.f3201f = str;
            } else if (a.size() == 0 && this.f2463g == 0) {
                C5155g.m3807a(str2, 3, "Packages exists - another request");
                return m2175b();
            }
            return z;
        }
    }

    /* renamed from: b */
    private boolean m2175b() {
        this.f2463g++;
        return mo62217d().booleanValue();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61145a(Boolean bool) {
        super.mo61145a(bool);
        Intent intent = new Intent("com.startapp.android.OnReceiveResponseBroadcastListener");
        intent.putExtra("adHashcode", this.f3197b.hashCode());
        intent.putExtra("adResult", bool);
        C5160b.m3831a(this.f3196a).mo62880a(intent);
        if (bool.booleanValue()) {
            mo61144a((C4925Ad) (C5033h) this.f3197b);
            this.f3199d.onReceiveAd(this.f3197b);
        }
    }
}
