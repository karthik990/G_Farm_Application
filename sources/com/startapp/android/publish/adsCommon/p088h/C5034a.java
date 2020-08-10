package com.startapp.android.publish.adsCommon.p088h;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.startapp.android.publish.adsCommon.p082f.C5012b;
import com.startapp.common.C5183d;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;

/* renamed from: com.startapp.android.publish.adsCommon.h.a */
/* compiled from: StartAppSDK */
public abstract class C5034a implements C5183d {

    /* renamed from: a */
    protected final Context f3273a;

    /* renamed from: b */
    protected final Runnable f3274b;

    /* renamed from: c */
    protected C5012b f3275c;

    /* renamed from: d */
    private Handler f3276d = new Handler(Looper.getMainLooper());

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public abstract void mo62340b();

    public C5034a(Context context, Runnable runnable, C5012b bVar) {
        this.f3273a = context;
        this.f3274b = runnable;
        this.f3275c = bVar;
    }

    /* renamed from: a */
    public void mo62337a() {
        C5188g.m3935a(C5192a.DEFAULT, (Runnable) new Runnable() {
            public void run() {
                C5034a.this.mo62340b();
            }
        });
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public void mo62339a(Runnable runnable, long j) {
        this.f3276d.postDelayed(runnable, j);
    }

    /* renamed from: a */
    public void mo62338a(Object obj) {
        Handler handler = this.f3276d;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        this.f3274b.run();
    }
}
