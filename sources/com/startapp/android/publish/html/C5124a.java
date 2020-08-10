package com.startapp.android.publish.html;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.AdsConstants.AdApiType;
import com.startapp.android.publish.adsCommon.C4925Ad;
import com.startapp.android.publish.adsCommon.C4983b;
import com.startapp.android.publish.adsCommon.C4998d;
import com.startapp.android.publish.adsCommon.C5003e;
import com.startapp.android.publish.adsCommon.C5053l;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.Utils.C4946i.C4951a;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.startapp.android.publish.adsCommon.p078b.C4984a;
import com.startapp.android.publish.adsCommon.p078b.C4985b;
import com.startapp.android.publish.adsCommon.p078b.C4987c;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.adsCommon.p083g.p087d.C5031a;
import com.startapp.android.publish.adsCommon.p091k.C5052a;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.android.publish.common.model.GetAdRequest;
import com.startapp.common.C5160b;
import com.startapp.common.C5186e;
import com.startapp.common.p092a.C5155g;
import com.startapp.common.p092a.C5156h.C5157a;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.startapp.android.publish.html.a */
/* compiled from: StartAppSDK */
public abstract class C5124a extends C4998d {

    /* renamed from: g */
    protected Set<String> f3484g = new HashSet();

    /* renamed from: h */
    protected GetAdRequest f3485h;

    /* renamed from: i */
    private Set<String> f3486i = new HashSet();

    /* renamed from: j */
    private List<C4984a> f3487j = new ArrayList();

    /* renamed from: k */
    private int f3488k = 0;

    /* renamed from: l */
    private boolean f3489l;

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo61659a(GetAdRequest getAdRequest) {
        return getAdRequest != null;
    }

    public C5124a(Context context, C4925Ad ad, AdPreferences adPreferences, AdEventListener adEventListener, Placement placement, boolean z) {
        super(context, ad, adPreferences, adEventListener, placement);
        this.f3489l = z;
    }

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public Object mo61147e() {
        this.f3485h = mo61353a();
        if (mo61659a(this.f3485h)) {
            if (this.f3486i.size() == 0) {
                this.f3486i.add(this.f3196a.getPackageName());
            }
            this.f3485h.setPackageExclude(this.f3486i);
            this.f3485h.setCampaignExclude(this.f3484g);
            if (this.f3488k > 0) {
                this.f3485h.setEngInclude(false);
                if (MetaData.getInstance().getSimpleTokenConfig().mo62664b(this.f3196a)) {
                    C5053l.m3365b(this.f3196a);
                }
            }
            try {
                return C5052a.m3348a(this.f3196a, AdsConstants.m2855a(AdApiType.HTML, mo62218f()), this.f3485h, null);
            } catch (C5186e e) {
                if (!MetaData.getInstance().getInvalidNetworkCodesInfoEvents().contains(Integer.valueOf(e.mo62911a()))) {
                    C5017f.m3256a(this.f3196a, C5015d.EXCEPTION, "BaseHtmlService.sendCommand - network failure", e.getMessage(), "");
                }
                C5155g.m3808a("BaseHtmlService", 6, "Unable to handle GetHtmlAdService command!!!!", e);
                this.f3201f = e.getMessage();
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo61146a(Object obj) {
        C5155g.m3807a("BaseHtmlService", 4, "Handle Html Response");
        try {
            this.f3487j = new ArrayList();
            String a = ((C5157a) obj).mo62865a();
            if (TextUtils.isEmpty(a)) {
                if (this.f3201f == null) {
                    if (this.f3485h == null || !this.f3485h.isAdTypeVideo()) {
                        this.f3201f = "Empty Ad";
                    } else {
                        this.f3201f = "Video isn't available";
                    }
                }
                return false;
            }
            List a2 = C4987c.m3091a(a, this.f3488k);
            if (C4983b.m3032a().mo62151E() ? C4987c.m3088a(this.f3196a, a2, this.f3488k, this.f3486i, this.f3487j).booleanValue() : false) {
                return mo62806g();
            }
            ((C5003e) this.f3197b).mo62233a(a2);
            return mo62805a(a);
        } catch (Exception unused) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo61145a(Boolean bool) {
        super.mo61145a(bool);
        StringBuilder sb = new StringBuilder();
        sb.append("Html onPostExecute, result=[");
        sb.append(bool);
        sb.append("]");
        C5155g.m3807a("BaseHtmlService", 4, sb.toString());
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo61661b(Boolean bool) {
        super.mo61661b(bool);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62804a(boolean z) {
        Intent intent = new Intent("com.startapp.android.OnReceiveResponseBroadcastListener");
        intent.putExtra("adHashcode", this.f3197b.hashCode());
        intent.putExtra("adResult", z);
        C5160b.m3831a(this.f3196a).mo62880a(intent);
        if (!z || this.f3197b == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Html onPostExecute failed error=[");
            sb.append(this.f3201f);
            sb.append("]");
            C5155g.m3807a("BaseHtmlService", 6, sb.toString());
        } else if (this.f3489l) {
            C4946i.m2914a(this.f3196a, ((C5003e) this.f3197b).mo62243f(), (C4951a) new C4951a() {
                /* renamed from: a */
                public void mo62044a() {
                    C5124a.this.f3199d.onReceiveAd(C5124a.this.f3197b);
                }

                /* renamed from: a */
                public void mo62045a(String str) {
                    C5124a.this.f3197b.setErrorMessage(str);
                    C5124a.this.f3199d.onFailedToReceiveAd(C5124a.this.f3197b);
                }
            });
        } else if (z) {
            this.f3199d.onReceiveAd(this.f3197b);
        } else {
            this.f3199d.onFailedToReceiveAd(this.f3197b);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public boolean mo62805a(String str) {
        C5003e eVar = (C5003e) this.f3197b;
        if (C5031a.m3293b(str)) {
            str = C5031a.m3290a(str);
        }
        eVar.mo61769b(str);
        return true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public boolean mo62806g() {
        C5155g.m3807a("BaseHtmlService", 3, "At least one package is present. sending another request to AdPlatform");
        this.f3488k++;
        new C4985b(this.f3196a, this.f3487j).mo62195a();
        return mo62217d().booleanValue();
    }
}
