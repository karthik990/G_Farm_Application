package com.startapp.android.publish.adsCommon.p083g.p086c;

import java.util.Arrays;
import java.util.List;
import p043io.reactivex.annotations.SchedulerSupport;

/* renamed from: com.startapp.android.publish.adsCommon.g.c.a */
/* compiled from: StartAppSDK */
public final class C5030a {

    /* renamed from: c */
    private static final List<String> f3269c = Arrays.asList(new String[]{"portrait", "landscape", SchedulerSupport.NONE});

    /* renamed from: a */
    public boolean f3270a;

    /* renamed from: b */
    public int f3271b;

    public C5030a() {
        this(true, 2);
    }

    public C5030a(boolean z, int i) {
        this.f3270a = z;
        this.f3271b = i;
    }

    /* renamed from: a */
    public static int m3288a(String str) {
        int indexOf = f3269c.indexOf(str);
        if (indexOf != -1) {
            return indexOf;
        }
        return 2;
    }
}
