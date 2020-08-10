package com.startapp.android.publish.cache;

import android.os.Handler;
import android.os.Looper;
import com.startapp.common.p092a.C5155g;

/* renamed from: com.startapp.android.publish.cache.e */
/* compiled from: StartAppSDK */
public abstract class C5082e {

    /* renamed from: a */
    protected C5085g f3397a;

    /* renamed from: b */
    private Handler f3398b = null;

    /* renamed from: c */
    private Long f3399c = null;

    /* renamed from: d */
    private boolean f3400d = false;

    /* access modifiers changed from: protected */
    /* renamed from: c */
    public abstract boolean mo62481c();

    /* access modifiers changed from: protected */
    /* renamed from: d */
    public abstract long mo62482d();

    /* access modifiers changed from: protected */
    /* renamed from: e */
    public String mo62483e() {
        return "CacheScheduledTask";
    }

    public C5082e(C5085g gVar) {
        this.f3397a = gVar;
    }

    /* renamed from: f */
    public void mo62490f() {
        if (!this.f3400d) {
            if (this.f3399c == null) {
                this.f3399c = Long.valueOf(System.currentTimeMillis());
            }
            if (mo62481c()) {
                if (this.f3398b == null) {
                    Looper myLooper = Looper.myLooper();
                    if (myLooper == null) {
                        myLooper = Looper.getMainLooper();
                    }
                    this.f3398b = new Handler(myLooper);
                }
                long d = mo62482d();
                if (d >= 0) {
                    this.f3400d = true;
                    String e = mo62483e();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Started for ");
                    sb.append(this.f3397a.mo62504c());
                    sb.append(" - scheduled to: ");
                    sb.append(d);
                    C5155g.m3807a(e, 4, sb.toString());
                    this.f3398b.postDelayed(new Runnable() {
                        public void run() {
                            C5082e.this.mo62480b();
                        }
                    }, d);
                    return;
                }
                C5155g.m3807a(mo62483e(), 3, "Can't start, scheduled time < 0");
                return;
            }
            C5155g.m3807a(mo62483e(), 3, "Not allowed");
        }
    }

    /* renamed from: g */
    public void mo62491g() {
        m3538j();
        m3539k();
    }

    /* renamed from: h */
    public void mo62492h() {
        m3538j();
        this.f3400d = false;
    }

    /* renamed from: a */
    public void mo62479a() {
        String e = mo62483e();
        StringBuilder sb = new StringBuilder();
        sb.append("Resetting for ");
        sb.append(this.f3397a.mo62504c());
        C5155g.m3807a(e, 4, sb.toString());
        mo62491g();
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo62480b() {
        String e = mo62483e();
        StringBuilder sb = new StringBuilder();
        sb.append("Time reached, reloading ");
        sb.append(this.f3397a.mo62504c());
        C5155g.m3807a(e, 3, sb.toString());
        m3539k();
        this.f3397a.mo62512k();
    }

    /* access modifiers changed from: protected */
    /* renamed from: i */
    public final Long mo62493i() {
        return this.f3399c;
    }

    /* renamed from: j */
    private void m3538j() {
        Handler handler = this.f3398b;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    /* renamed from: k */
    private void m3539k() {
        this.f3399c = null;
        this.f3400d = false;
    }
}
