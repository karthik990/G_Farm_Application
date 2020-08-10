package com.flurry.sdk;

import android.os.Handler;
import android.os.Looper;
import com.flurry.sdk.C1854h.C1856a;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;

/* renamed from: com.flurry.sdk.f */
public class C1766f extends C1884i {

    /* renamed from: a */
    private static final ThreadLocal<C1766f> f1037a = new ThreadLocal<>();

    /* renamed from: b */
    private Thread f1038b;

    /* renamed from: com.flurry.sdk.f$a */
    public class C1767a extends C1854h {

        /* renamed from: b */
        private Deque<Runnable> f1040b = new LinkedList();

        /* renamed from: d */
        private int f1041d = 1;

        public final void cleanupTask(Runnable runnable) {
        }

        public C1767a(String str, C1766f fVar) {
            super(str, fVar, true);
        }

        public final void runSync(Runnable runnable) throws CancellationException {
            boolean z;
            synchronized (this) {
                z = this.f1041d == 0;
            }
            if (z) {
                this.target.runSync(runnable);
                return;
            }
            C1856a aVar = new C1856a(this.target, f1242c);
            synchronized (this) {
                this.f1040b.add(aVar);
            }
            while (!aVar.isDone()) {
                try {
                    aVar.get();
                } catch (CancellationException e) {
                    throw e;
                } catch (Exception unused) {
                }
            }
            if (!wrapRunnable(runnable)) {
                wrapNextRunnable(runnable);
            }
            aVar.f1244b.cleanupTask(aVar);
        }

        public final synchronized Future<Void> runAsync(Runnable runnable) {
            if (this.f1041d == 0) {
                return this.target.runAsync(runnable);
            }
            C17681 r0 = new C1856a(this.target, runnable) {
                /* access modifiers changed from: protected */
                public final void done() {
                    this.f1244b.cleanupTask(this);
                }
            };
            this.f1040b.add(r0);
            return r0;
        }

        public final synchronized Future<Void> runAfter(Runnable runnable, long j) {
            return this.target.runAfter(new C1856a(this, runnable) {
                /* access modifiers changed from: protected */
                public final void done() {
                    this.f1244b.cleanupTask(this);
                }
            }, j);
        }
    }

    public static C1766f currentActor() {
        return (C1766f) f1037a.get();
    }

    public C1766f(String str, C1854h hVar) {
        super(str, hVar, false);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void runSync(java.lang.Runnable r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            java.lang.Thread r0 = r2.f1038b     // Catch:{ all -> 0x0021 }
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0021 }
            if (r0 != r1) goto L_0x001c
            boolean r0 = r3 instanceof com.flurry.sdk.C1854h.C1856a     // Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x0017
            com.flurry.sdk.h r0 = r2.target     // Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x001a
            com.flurry.sdk.h r0 = r2.target     // Catch:{ all -> 0x0021 }
            r0.runSync(r3)     // Catch:{ all -> 0x0021 }
            goto L_0x001a
        L_0x0017:
            r3.run()     // Catch:{ all -> 0x0021 }
        L_0x001a:
            monitor-exit(r2)     // Catch:{ all -> 0x0021 }
            return
        L_0x001c:
            monitor-exit(r2)     // Catch:{ all -> 0x0021 }
            super.runSync(r3)
            return
        L_0x0021:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0021 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1766f.runSync(java.lang.Runnable):void");
    }

    public Future<Void> runAsync(Runnable runnable) {
        return super.runAsync(runnable);
    }

    /* access modifiers changed from: protected */
    public Future<Void> runAfter(Runnable runnable, long j) {
        return super.runAfter(runnable, j);
    }

    /* access modifiers changed from: protected */
    public boolean wrapRunnable(Runnable runnable) {
        C1766f fVar;
        Thread thread;
        synchronized (this) {
            fVar = (C1766f) f1037a.get();
            f1037a.set(this);
            thread = this.f1038b;
            this.f1038b = Thread.currentThread();
        }
        try {
            wrapNextRunnable(runnable);
            synchronized (this) {
                this.f1038b = thread;
                f1037a.set(fVar);
            }
            return true;
        } catch (Throwable th) {
            synchronized (this) {
                this.f1038b = thread;
                f1037a.set(fVar);
                throw th;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void flush(Runnable runnable) {
        if (Thread.currentThread() == this.f1038b) {
            runnable.run();
        }
    }

    /* access modifiers changed from: protected */
    public C1767a createDeferredQueue(String str) {
        return new C1767a(str, this);
    }

    /* access modifiers changed from: protected */
    public void postOnMainThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }
}
