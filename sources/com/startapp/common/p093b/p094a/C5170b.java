package com.startapp.common.p093b.p094a;

import android.content.Context;
import java.util.Map;

/* renamed from: com.startapp.common.b.a.b */
/* compiled from: StartAppSDK */
public interface C5170b {

    /* renamed from: com.startapp.common.b.a.b$a */
    /* compiled from: StartAppSDK */
    public enum C5171a {
        SUCCESS,
        FAILED,
        RESCHEDULE
    }

    /* renamed from: com.startapp.common.b.a.b$b */
    /* compiled from: StartAppSDK */
    public interface C5172b {
        /* renamed from: a */
        void mo62538a(C5171a aVar);
    }

    /* renamed from: a */
    void mo44557a(Context context, int i, Map<String, String> map, C5172b bVar);
}
