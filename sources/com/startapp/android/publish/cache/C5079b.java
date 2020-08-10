package com.startapp.android.publish.cache;

import com.startapp.android.publish.adsCommon.C5059m;
import com.startapp.common.p092a.C5155g;
import java.util.concurrent.TimeUnit;

/* renamed from: com.startapp.android.publish.cache.b */
/* compiled from: StartAppSDK */
public class C5079b extends C5082e {

    /* renamed from: b */
    private final FailuresHandler f3393b = C5081d.m3532a().mo62488b().getFailuresHandler();

    /* renamed from: c */
    private int f3394c = 0;

    /* renamed from: d */
    private boolean f3395d = false;

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public String mo62483e() {
        return "CacheErrorReloadTimer";
    }

    public C5079b(C5085g gVar) {
        super(gVar);
    }

    /* renamed from: a */
    public void mo62479a() {
        super.mo62479a();
        this.f3394c = 0;
        this.f3395d = false;
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo62480b() {
        m3524j();
        super.mo62480b();
    }

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public boolean mo62481c() {
        if (!C5059m.m3378a().mo62406l() || !m3525k()) {
            return false;
        }
        if (this.f3395d) {
            return this.f3393b.isInfiniteLastRetry();
        }
        return true;
    }

    /* renamed from: j */
    private void m3524j() {
        String str = "CacheErrorReloadTimer";
        if (this.f3394c == this.f3393b.getIntervals().size() - 1) {
            this.f3395d = true;
            StringBuilder sb = new StringBuilder();
            sb.append("Reached end index: ");
            sb.append(this.f3394c);
            C5155g.m3807a(str, 4, sb.toString());
            return;
        }
        this.f3394c++;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Advanced to index: ");
        sb2.append(this.f3394c);
        C5155g.m3807a(str, 4, sb2.toString());
    }

    /* renamed from: k */
    private boolean m3525k() {
        FailuresHandler failuresHandler = this.f3393b;
        return (failuresHandler == null || failuresHandler.getIntervals() == null) ? false : true;
    }

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public long mo62482d() {
        if (this.f3394c >= this.f3393b.getIntervals().size()) {
            return -1;
        }
        Long i = mo62493i();
        if (i == null) {
            return -1;
        }
        long millis = TimeUnit.SECONDS.toMillis((long) ((Integer) this.f3393b.getIntervals().get(this.f3394c)).intValue()) - (System.currentTimeMillis() - i.longValue());
        if (millis < 0) {
            millis = 0;
        }
        return millis;
    }
}
