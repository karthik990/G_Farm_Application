package com.flurry.sdk;

import android.location.Location;
import android.location.LocationManager;
import com.google.firebase.analytics.FirebaseAnalytics.Param;

/* renamed from: com.flurry.sdk.at */
public final class C1558at extends C1942m<C1557as> {

    /* renamed from: a */
    public boolean f552a = true;

    /* renamed from: b */
    protected C1949o<C1955r> f553b = new C1949o<C1955r>() {
        /* renamed from: a */
        public final /* synthetic */ void mo16242a(Object obj) {
            if (((C1955r) obj).f1445b == C1950p.FOREGROUND) {
                C1558at.this.refresh();
            }
        }
    };
    /* access modifiers changed from: private */

    /* renamed from: d */
    public boolean f554d = false;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public Location f555e;

    /* renamed from: f */
    private C1951q f556f;

    public C1558at(C1951q qVar) {
        super("LocationProvider");
        this.f556f = qVar;
        this.f556f.subscribe(this.f553b);
    }

    public final void refresh() {
        Location a = m463a();
        if (a != null) {
            this.f555e = a;
        }
        notifyObservers(new C1557as(this.f552a, this.f554d, this.f555e));
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public Location m463a() {
        if (!this.f552a) {
            return null;
        }
        if (C1774fd.m929a() || C1774fd.m931b()) {
            String str = C1774fd.m929a() ? "passive" : "network";
            this.f554d = true;
            LocationManager locationManager = (LocationManager) C1576b.m502a().getSystemService(Param.LOCATION);
            if (locationManager != null) {
                return locationManager.getLastKnownLocation(str);
            }
            return null;
        }
        this.f554d = false;
        return null;
    }

    public final void subscribe(final C1949o<C1557as> oVar) {
        super.subscribe(oVar);
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                Location a = C1558at.this.m463a();
                if (a != null) {
                    C1558at.this.f555e = a;
                }
                oVar.mo16242a(new C1557as(C1558at.this.f552a, C1558at.this.f554d, C1558at.this.f555e));
            }
        });
    }

    public final void destroy() {
        super.destroy();
        this.f556f.unsubscribe(this.f553b);
    }
}
