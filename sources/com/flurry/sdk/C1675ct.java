package com.flurry.sdk;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/* renamed from: com.flurry.sdk.ct */
public final class C1675ct extends C1679cx<C1696df> {

    /* renamed from: b */
    private static C1675ct f850b;

    protected C1675ct() {
        super("HttpRequestManager", TimeUnit.MILLISECONDS, new PriorityBlockingQueue(11, new C1677cv()));
    }

    /* renamed from: a */
    public static synchronized C1675ct m738a() {
        C1675ct ctVar;
        synchronized (C1675ct.class) {
            if (f850b == null) {
                f850b = new C1675ct();
            }
            ctVar = f850b;
        }
        return ctVar;
    }
}
