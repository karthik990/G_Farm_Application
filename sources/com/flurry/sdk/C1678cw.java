package com.flurry.sdk;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* renamed from: com.flurry.sdk.cw */
public final class C1678cw<V> extends FutureTask<V> {

    /* renamed from: a */
    private final WeakReference<Callable<V>> f853a = new WeakReference<>(null);

    /* renamed from: b */
    private final WeakReference<Runnable> f854b;

    public C1678cw(Runnable runnable, V v) {
        super(runnable, v);
        this.f854b = new WeakReference<>(runnable);
    }

    /* renamed from: a */
    public final Runnable mo16387a() {
        return (Runnable) this.f854b.get();
    }
}
