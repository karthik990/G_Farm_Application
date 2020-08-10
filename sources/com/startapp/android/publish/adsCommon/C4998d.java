package com.startapp.android.publish.adsCommon;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Pair;
import com.startapp.android.publish.adsCommon.C4925Ad.AdState;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.adsCommon.adListeners.C4965b;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.common.model.GetAdRequest;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.adsCommon.d */
/* compiled from: StartAppSDK */
public abstract class C4998d {

    /* renamed from: a */
    protected final Context f3196a;
    /* access modifiers changed from: protected */

    /* renamed from: b */
    public final C4925Ad f3197b;

    /* renamed from: c */
    protected final AdPreferences f3198c;
    /* access modifiers changed from: protected */

    /* renamed from: d */
    public final AdEventListener f3199d;

    /* renamed from: e */
    protected Placement f3200e;

    /* renamed from: f */
    protected String f3201f = null;

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract boolean mo61146a(Object obj);

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public abstract Object mo61147e();

    public C4998d(Context context, C4925Ad ad, AdPreferences adPreferences, AdEventListener adEventListener, Placement placement) {
        this.f3196a = context;
        this.f3197b = ad;
        this.f3198c = adPreferences;
        this.f3199d = new C4965b(adEventListener);
        this.f3200e = placement;
    }

    /* renamed from: c */
    public void mo62216c() {
        C5188g.m3935a(C5192a.HIGH, (Runnable) new Runnable() {
            public void run() {
                Process.setThreadPriority(10);
                final Boolean d = C4998d.this.mo62217d();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        C4998d.this.mo61145a(d);
                    }
                });
            }
        });
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public Boolean mo62217d() {
        return Boolean.valueOf(mo61146a(mo61147e()));
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61145a(Boolean bool) {
        mo61661b(bool);
        if (!bool.booleanValue()) {
            this.f3197b.setErrorMessage(this.f3201f);
            this.f3199d.onFailedToReceiveAd(this.f3197b);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo61661b(Boolean bool) {
        this.f3197b.setState(bool.booleanValue() ? AdState.READY : AdState.UN_INITIALIZED);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public GetAdRequest mo61353a() {
        return mo62215b(new GetAdRequest());
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public GetAdRequest mo62215b(GetAdRequest getAdRequest) {
        Pair d = C5053l.m3370d(this.f3196a);
        try {
            getAdRequest.fillAdPreferences(this.f3196a, this.f3198c, this.f3200e, d);
            if (!C4983b.m3032a().mo62150D()) {
                C5155g.m3805a(6, "forceExternal - check - request - metadata false disabletwoclicks");
                if (C4988c.m3118a(this.f3196a, this.f3200e)) {
                    getAdRequest.setDisableTwoClicks(true);
                }
            }
            try {
                getAdRequest.fillApplicationDetails(this.f3196a, this.f3198c, false);
            } catch (Exception e) {
                C5017f.m3256a(this.f3196a, C5015d.EXCEPTION, "BaseService.GetAdRequest - fillApplicationDetails failed", e.getMessage(), "");
            }
            return getAdRequest;
        } catch (Exception unused) {
            C5053l.m3362a(d);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public Placement mo62218f() {
        return this.f3200e;
    }
}
