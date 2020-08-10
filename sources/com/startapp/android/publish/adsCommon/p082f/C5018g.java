package com.startapp.android.publish.adsCommon.p082f;

import android.content.Context;
import com.startapp.android.publish.adsCommon.C5053l;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p091k.C5052a;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.common.C5186e;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.adsCommon.f.g */
/* compiled from: StartAppSDK */
public class C5018g {

    /* renamed from: a */
    private final Context f3254a;

    /* renamed from: b */
    private final AdPreferences f3255b;

    /* renamed from: c */
    private final C5016e f3256c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public final C5020a f3257d;

    /* renamed from: com.startapp.android.publish.adsCommon.f.g$a */
    /* compiled from: StartAppSDK */
    public interface C5020a {
        /* renamed from: a */
        void mo62257a(boolean z);
    }

    public C5018g(Context context, AdPreferences adPreferences, C5016e eVar, C5020a aVar) {
        this.f3254a = context;
        this.f3255b = adPreferences;
        this.f3256c = eVar;
        this.f3257d = aVar;
    }

    /* renamed from: a */
    public void mo62303a() {
        C5188g.m3935a(C5192a.DEFAULT, (Runnable) new Runnable() {
            public void run() {
                boolean b = C5018g.this.mo62304b();
                if (C5018g.this.f3257d != null) {
                    C5018g.this.f3257d.mo62257a(b);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public boolean mo62304b() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sending InfoEvent ");
        sb.append(this.f3256c);
        C5155g.m3805a(3, sb.toString());
        try {
            C4946i.m2913a(this.f3254a, this.f3255b);
            try {
                C5053l.m3365b(this.f3254a);
                this.f3256c.fillLocationDetails(this.f3255b, this.f3254a);
                this.f3256c.fillApplicationDetails(this.f3254a, this.f3255b);
            } catch (Exception unused) {
            }
            try {
                C5155g.m3805a(3, "Networking InfoEvent");
                String a = MetaData.getInstance().getAnalyticsConfig().mo62264a();
                if (C5015d.PERIODIC.equals(this.f3256c.mo62287e())) {
                    a = MetaData.getInstance().getAnalyticsConfig().mo62265b();
                }
                C5052a.m3350a(this.f3254a, a, this.f3256c, null, MetaData.getInstance().getAnalyticsConfig().mo62267d(), MetaData.getInstance().getAnalyticsConfig().mo62268e());
                return true;
            } catch (C5186e e) {
                C5155g.m3806a(6, "Unable to send InfoEvent command!!!!", (Throwable) e);
                return false;
            }
        } catch (Exception e2) {
            C5155g.m3806a(6, "Unable to fill AdPreferences ", (Throwable) e2);
            return false;
        }
    }
}
