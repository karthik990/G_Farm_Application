package com.startapp.common;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import com.startapp.common.C5188g.C5192a;
import com.startapp.common.p092a.C5150d;

/* renamed from: com.startapp.common.a */
/* compiled from: StartAppSDK */
public class C5134a {

    /* renamed from: a */
    String f3533a;

    /* renamed from: b */
    C5137a f3534b;

    /* renamed from: c */
    int f3535c;

    /* renamed from: com.startapp.common.a$a */
    /* compiled from: StartAppSDK */
    public interface C5137a {
        /* renamed from: a */
        void mo61329a(Bitmap bitmap, int i);
    }

    public C5134a(String str, C5137a aVar, int i) {
        this.f3533a = str;
        this.f3534b = aVar;
        this.f3535c = i;
    }

    /* renamed from: a */
    public void mo62837a() {
        C5188g.m3935a(C5192a.HIGH, (Runnable) new Runnable() {
            public void run() {
                final Bitmap b = C5150d.m3785b(C5134a.this.f3533a);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        if (C5134a.this.f3534b != null) {
                            C5134a.this.f3534b.mo61329a(b, C5134a.this.f3535c);
                        }
                    }
                });
            }
        });
    }
}
