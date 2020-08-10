package com.flurry.sdk;

import com.flurry.sdk.C1739ec;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

/* renamed from: com.flurry.sdk.cx */
public class C1679cx<T extends C1739ec> {

    /* renamed from: a */
    final HashMap<T, Future<?>> f855a = new HashMap<>();

    /* renamed from: b */
    private final C1676cu<Object, T> f856b = new C1676cu<>();

    /* renamed from: c */
    private final HashMap<T, Object> f857c = new HashMap<>();

    /* renamed from: d */
    private final ThreadPoolExecutor f858d;

    public C1679cx(String str, TimeUnit timeUnit, BlockingQueue<Runnable> blockingQueue) {
        this.f858d = new ThreadPoolExecutor(timeUnit, blockingQueue) {
            /* access modifiers changed from: protected */
            public final void beforeExecute(Thread thread, Runnable runnable) {
                super.beforeExecute(thread, runnable);
                final C1739ec a = C1679cx.m744a(runnable);
                if (a != null) {
                    new C1738eb() {
                        /* renamed from: a */
                        public final void mo16236a() {
                        }
                    }.run();
                }
            }

            /* access modifiers changed from: protected */
            public final void afterExecute(Runnable runnable, Throwable th) {
                super.afterExecute(runnable, th);
                final C1739ec a = C1679cx.m744a(runnable);
                if (a != null) {
                    synchronized (C1679cx.this.f855a) {
                        C1679cx.this.f855a.remove(a);
                    }
                    C1679cx.this.mo16388a(a);
                    new C1738eb() {
                        /* renamed from: a */
                        public final void mo16236a() {
                        }
                    }.run();
                }
            }

            /* access modifiers changed from: protected */
            public final <V> RunnableFuture<V> newTaskFor(Runnable runnable, V v) {
                C1678cw cwVar = new C1678cw(runnable, v);
                synchronized (C1679cx.this.f855a) {
                    C1679cx.this.f855a.put((C1739ec) runnable, cwVar);
                }
                return cwVar;
            }

            /* access modifiers changed from: protected */
            public final <V> RunnableFuture<V> newTaskFor(Callable<V> callable) {
                throw new UnsupportedOperationException("Callable not supported");
            }
        };
        this.f858d.setRejectedExecutionHandler(new DiscardPolicy() {
            public final void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                super.rejectedExecution(runnable, threadPoolExecutor);
                final C1739ec a = C1679cx.m744a(runnable);
                if (a != null) {
                    synchronized (C1679cx.this.f855a) {
                        C1679cx.this.f855a.remove(a);
                    }
                    C1679cx.this.mo16388a(a);
                    new C1738eb() {
                        /* renamed from: a */
                        public final void mo16236a() {
                        }
                    }.run();
                }
            }
        });
        this.f858d.setThreadFactory(new C1730dw(str));
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final synchronized void mo16388a(T t) {
        m745b(this.f857c.get(t), t);
    }

    /* renamed from: b */
    private synchronized void m745b(Object obj, T t) {
        C1676cu<Object, T> cuVar = this.f856b;
        if (obj != null) {
            List a = cuVar.mo16384a(obj, false);
            if (a != null) {
                a.remove(t);
                if (a.size() == 0) {
                    cuVar.f851a.remove(obj);
                }
            }
        }
        this.f857c.remove(t);
    }

    /* renamed from: a */
    public final synchronized void mo16389a(Object obj, T t) {
        if (obj != null) {
            m746c(obj, t);
            this.f858d.submit(t);
        }
    }

    /* renamed from: c */
    private synchronized void m746c(Object obj, T t) {
        this.f856b.mo16385a(obj, t);
        this.f857c.put(t, obj);
    }

    /* renamed from: a */
    static /* synthetic */ C1739ec m744a(Runnable runnable) {
        if (runnable instanceof C1678cw) {
            return (C1739ec) ((C1678cw) runnable).mo16387a();
        }
        if (runnable instanceof C1739ec) {
            return (C1739ec) runnable;
        }
        StringBuilder sb = new StringBuilder("Unknown runnable class: ");
        sb.append(runnable.getClass().getName());
        C1685cy.m754a(6, "TrackedThreadPoolExecutor", sb.toString());
        return null;
    }
}
