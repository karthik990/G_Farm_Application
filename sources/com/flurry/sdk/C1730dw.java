package com.flurry.sdk;

import java.util.concurrent.ThreadFactory;

/* renamed from: com.flurry.sdk.dw */
public final class C1730dw implements ThreadFactory {

    /* renamed from: a */
    private final ThreadGroup f987a;

    /* renamed from: b */
    private final int f988b = 1;

    public C1730dw(String str) {
        this.f987a = new ThreadGroup(str);
    }

    public final Thread newThread(Runnable runnable) {
        Thread thread = new Thread(this.f987a, runnable);
        StringBuilder sb = new StringBuilder();
        sb.append(this.f987a.getName());
        sb.append(":");
        sb.append(thread.getId());
        thread.setName(sb.toString());
        thread.setPriority(this.f988b);
        return thread;
    }
}
