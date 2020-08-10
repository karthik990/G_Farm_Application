package com.flurry.sdk;

import java.util.TimerTask;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/* renamed from: com.flurry.sdk.h */
public abstract class C1854h {

    /* renamed from: c */
    static Runnable f1242c = new Runnable() {
        public final void run() {
        }
    };
    protected final boolean concurrent;
    protected final String description;
    protected final boolean syncFlush;
    protected final C1854h target;

    /* renamed from: com.flurry.sdk.h$a */
    public class C1856a extends FutureTask<Void> {

        /* renamed from: a */
        private TimerTask f1243a;

        /* renamed from: b */
        public final C1854h f1244b;

        /* renamed from: d */
        private final int f1246d = 0;

        /* renamed from: e */
        private final int f1247e = 1;

        /* renamed from: f */
        private final int f1248f = 2;

        /* renamed from: g */
        private int f1249g;

        C1856a(C1854h hVar, Runnable runnable) {
            super(runnable, null);
            this.f1244b = hVar;
            if (runnable == C1854h.f1242c) {
                this.f1249g = 0;
            } else {
                this.f1249g = 1;
            }
        }

        /* renamed from: a */
        public final synchronized boolean mo16506a() {
            return this.f1249g == 0;
        }

        public synchronized boolean cancel(boolean z) {
            super.cancel(z);
            if (this.f1243a != null) {
                this.f1243a.cancel();
            }
            return true;
        }

        /* renamed from: a */
        public final synchronized void mo16505a(TimerTask timerTask) {
            if (!isDone()) {
                this.f1243a = timerTask;
            } else {
                this.f1243a.cancel();
            }
        }

        public synchronized void run() {
            if (this.f1249g == 1) {
                this.f1249g = 2;
                if (!this.f1244b.wrapRunnable(this)) {
                    this.f1244b.wrapNextRunnable(this);
                }
                this.f1249g = 1;
                return;
            }
            super.run();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void cleanupTask(Runnable runnable);

    /* access modifiers changed from: protected */
    public void flush(Runnable runnable) {
    }

    /* access modifiers changed from: protected */
    public abstract Future<Void> runAfter(Runnable runnable, long j);

    /* access modifiers changed from: protected */
    public abstract Future<Void> runAsync(Runnable runnable);

    /* access modifiers changed from: protected */
    public abstract void runSync(Runnable runnable) throws CancellationException;

    /* access modifiers changed from: protected */
    public boolean wrapRunnable(Runnable runnable) {
        return false;
    }

    C1854h(String str, C1854h hVar, boolean z) {
        this(str, hVar, z, hVar == null ? false : hVar.syncFlush);
    }

    private C1854h(String str, C1854h hVar, boolean z, boolean z2) {
        this.description = str;
        this.target = hVar;
        this.concurrent = z;
        this.syncFlush = z2;
    }

    /* access modifiers changed from: protected */
    public final boolean wrapNextRunnable(Runnable runnable) {
        for (C1854h hVar = this.target; hVar != null; hVar = hVar.target) {
            if (hVar.wrapRunnable(runnable)) {
                return true;
            }
        }
        runnable.run();
        return true;
    }
}
