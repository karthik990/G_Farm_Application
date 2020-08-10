package com.startapp.common;

import android.os.Build.VERSION;
import com.startapp.common.p092a.C5155g;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.startapp.common.g */
/* compiled from: StartAppSDK */
public final class C5188g {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static final String f3628a = C5188g.class.getSimpleName();

    /* renamed from: b */
    private static final int f3629b = (VERSION.SDK_INT < 22 ? 10 : 20);

    /* renamed from: c */
    private static final int f3630c = (VERSION.SDK_INT < 22 ? 4 : 8);

    /* renamed from: d */
    private static final ThreadFactory f3631d = new ThreadFactory() {

        /* renamed from: a */
        private final AtomicInteger f3637a = new AtomicInteger(1);

        public Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder();
            sb.append("highPriorityThreadFactory #");
            sb.append(this.f3637a.getAndIncrement());
            return new Thread(runnable, sb.toString());
        }
    };

    /* renamed from: e */
    private static final ThreadFactory f3632e = new ThreadFactory() {

        /* renamed from: a */
        private final AtomicInteger f3638a = new AtomicInteger(1);

        public Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder();
            sb.append("defaultPriorityThreadFactory #");
            sb.append(this.f3638a.getAndIncrement());
            return new Thread(runnable, sb.toString());
        }
    };

    /* renamed from: f */
    private static final RejectedExecutionHandler f3633f = new RejectedExecutionHandler() {
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            String a = C5188g.f3628a;
            StringBuilder sb = new StringBuilder();
            sb.append("ThreadPoolExecutor rejected execution! ");
            sb.append(threadPoolExecutor);
            C5155g.m3807a(a, 6, sb.toString());
        }
    };

    /* renamed from: g */
    private static final Executor f3634g;

    /* renamed from: h */
    private static final Executor f3635h;

    /* renamed from: i */
    private static final ScheduledExecutorService f3636i = new ScheduledThreadPoolExecutor(1);

    /* renamed from: com.startapp.common.g$a */
    /* compiled from: StartAppSDK */
    public enum C5192a {
        DEFAULT,
        HIGH
    }

    static {
        int i = f3629b;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(i, i, 20, TimeUnit.SECONDS, new LinkedBlockingQueue(), f3631d, f3633f);
        f3634g = threadPoolExecutor;
        int i2 = f3630c;
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(i2, i2, 20, TimeUnit.SECONDS, new LinkedBlockingQueue(), f3632e, f3633f);
        f3635h = threadPoolExecutor2;
    }

    /* renamed from: a */
    public static ScheduledFuture<?> m3934a(Runnable runnable, long j) {
        return f3636i.schedule(runnable, j, TimeUnit.MILLISECONDS);
    }

    /* renamed from: a */
    public static void m3935a(C5192a aVar, Runnable runnable) {
        Executor executor;
        try {
            if (aVar.equals(C5192a.HIGH)) {
                executor = f3634g;
            } else {
                executor = f3635h;
            }
            executor.execute(runnable);
        } catch (Exception unused) {
            String str = f3628a;
            StringBuilder sb = new StringBuilder();
            sb.append("executeWithPriority failed to execute! ");
            sb.append(null);
            C5155g.m3807a(str, 6, sb.toString());
        }
    }
}
