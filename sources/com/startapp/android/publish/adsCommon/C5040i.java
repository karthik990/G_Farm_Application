package com.startapp.android.publish.adsCommon;

import android.content.Context;
import android.os.Handler;
import com.startapp.android.publish.adsCommon.adListeners.AdDisplayListener.NotDisplayedReason;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.common.p092a.C5155g;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.startapp.android.publish.adsCommon.i */
/* compiled from: StartAppSDK */
public class C5040i {

    /* renamed from: a */
    private static final boolean f3282a = MetaData.getInstance().isSupportIABViewability();

    /* renamed from: b */
    private Handler f3283b = new Handler();

    /* renamed from: c */
    private long f3284c;

    /* renamed from: d */
    private Context f3285d;

    /* renamed from: e */
    private long f3286e = -1;

    /* renamed from: f */
    private long f3287f;

    /* renamed from: g */
    private boolean f3288g;

    /* renamed from: h */
    private boolean f3289h;

    /* renamed from: i */
    private String[] f3290i;

    /* renamed from: j */
    private C5002b f3291j;

    /* renamed from: k */
    private AtomicBoolean f3292k = new AtomicBoolean(false);

    public C5040i(Context context, String[] strArr, C5002b bVar, long j) {
        this.f3285d = context.getApplicationContext();
        this.f3290i = strArr;
        this.f3291j = bVar;
        this.f3284c = j;
    }

    /* renamed from: a */
    public void mo62344a() {
        String str = "ScheduledImpression";
        C5155g.m3807a(str, 4, "schedule");
        if (mo62348c()) {
            C5155g.m3807a(str, 3, "Already sent impression. Must call cancel(true/false) to reschedule");
        } else if (f3282a) {
            m3310a(this.f3284c);
        } else {
            C5155g.m3807a(str, 3, "Delay feature disabled, sending impression now!");
            mo62347b(true);
        }
    }

    /* renamed from: b */
    public void mo62346b() {
        if (this.f3288g && this.f3289h) {
            C5155g.m3807a("ScheduledImpression", 4, "pause");
            this.f3283b.removeCallbacksAndMessages(null);
            this.f3286e = System.currentTimeMillis();
            this.f3284c -= this.f3286e - this.f3287f;
            this.f3289h = false;
        }
    }

    /* renamed from: a */
    public void mo62345a(boolean z) {
        if (this.f3288g) {
            StringBuilder sb = new StringBuilder();
            sb.append("cancel(");
            sb.append(z);
            sb.append(")");
            C5155g.m3807a("ScheduledImpression", 4, sb.toString());
            mo62347b(z);
            m3311d();
        }
    }

    /* renamed from: c */
    public boolean mo62348c() {
        return this.f3292k.get();
    }

    /* renamed from: a */
    private void m3310a(long j) {
        String str = "ScheduledImpression";
        if (!this.f3289h) {
            this.f3289h = true;
            if (!this.f3288g) {
                this.f3288g = true;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Scheduling timer to: ");
            sb.append(j);
            sb.append(" millis, Num urls = ");
            sb.append(this.f3290i.length);
            C5155g.m3807a(str, 3, sb.toString());
            this.f3287f = System.currentTimeMillis();
            this.f3283b.postDelayed(new Runnable() {
                public void run() {
                    C5155g.m3807a("ScheduledImpression", 4, "Timer elapsed");
                    C5040i.this.mo62347b(true);
                }
            }, j);
            return;
        }
        C5155g.m3807a(str, 3, "Already running");
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo62347b(boolean z) {
        String str = "ScheduledImpression";
        if (!this.f3292k.compareAndSet(false, true)) {
            C5155g.m3807a(str, 4, "Already sent");
        } else if (z) {
            C5155g.m3807a(str, 3, "Sending impression");
            C4988c.m3110a(this.f3285d, this.f3290i, this.f3291j);
        } else {
            C5155g.m3807a(str, 3, "Sending non-impression");
            C4988c.m3112a(this.f3285d, this.f3290i, this.f3291j.getAdTag(), NotDisplayedReason.AD_CLOSED_TOO_QUICKLY.toString());
        }
    }

    /* renamed from: d */
    private void m3311d() {
        C5155g.m3807a("ScheduledImpression", 4, "reset");
        this.f3288g = false;
        this.f3283b.removeCallbacksAndMessages(null);
        this.f3289h = false;
        this.f3286e = -1;
        this.f3287f = 0;
    }
}
