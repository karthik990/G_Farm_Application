package com.flurry.sdk;

import com.flurry.sdk.C1854h.C1856a;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/* renamed from: com.flurry.sdk.e */
public final class C1735e extends C1819g {

    /* renamed from: b */
    private static Timer f991b = new Timer("ExecutorQueue Global Timer", true);

    /* renamed from: a */
    Executor f992a;

    public C1735e(Executor executor, String str) {
        super(str);
        this.f992a = executor;
    }

    public final Future<Void> runAfter(Runnable runnable, long j) {
        final C1856a aVar;
        if (runnable instanceof C1856a) {
            aVar = (C1856a) runnable;
        } else {
            aVar = new C1856a(this, runnable);
        }
        C17361 r0 = new TimerTask() {
            public final void run() {
                aVar.f1244b.runAsync(aVar);
            }
        };
        aVar.mo16505a(r0);
        f991b.schedule(r0, j);
        return aVar;
    }

    /* access modifiers changed from: protected */
    public final synchronized boolean onActive(C1856a aVar) {
        try {
            if (aVar.mo16506a()) {
                aVar.run();
            } else {
                this.f992a.execute(aVar);
            }
        } catch (Exception unused) {
            return false;
        }
        return true;
    }
}
