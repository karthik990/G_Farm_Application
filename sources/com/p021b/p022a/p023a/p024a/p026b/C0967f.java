package com.p021b.p022a.p023a.p024a.p026b;

import p043io.reactivex.annotations.SchedulerSupport;

/* renamed from: com.b.a.a.a.b.f */
public enum C0967f {
    NATIVE("native"),
    JAVASCRIPT("javascript"),
    NONE(SchedulerSupport.NONE);
    
    private final String owner;

    private C0967f(String str) {
        this.owner = str;
    }

    public String toString() {
        return this.owner;
    }
}
