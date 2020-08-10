package com.startapp.android.publish.ads.video;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.startapp.android.publish.ads.video.C4869c.C4872a;
import com.startapp.android.publish.adsCommon.C4983b;
import java.net.URL;

/* renamed from: com.startapp.android.publish.ads.video.g */
/* compiled from: StartAppSDK */
public class C4920g {

    /* renamed from: a */
    protected Context f3000a;

    /* renamed from: b */
    protected URL f3001b;

    /* renamed from: c */
    protected String f3002c;

    /* renamed from: d */
    protected C4922a f3003d;

    /* renamed from: e */
    protected C4872a f3004e;

    /* renamed from: com.startapp.android.publish.ads.video.g$a */
    /* compiled from: StartAppSDK */
    public interface C4922a {
        /* renamed from: a */
        void mo61662a(String str);
    }

    public C4920g(Context context, URL url, String str, C4922a aVar, C4872a aVar2) {
        this.f3000a = context;
        this.f3001b = url;
        this.f3002c = str;
        this.f3003d = aVar;
        this.f3004e = aVar2;
    }

    /* renamed from: a */
    public void mo61815a() {
        final String str;
        try {
            str = C4983b.m3032a().mo62154H().mo62430i() ? C4869c.m2627a().mo61691a(this.f3000a, this.f3001b, this.f3002c, this.f3004e) : C4923h.m2845a(this.f3000a, this.f3001b, this.f3002c);
        } catch (Exception unused) {
            str = null;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                if (C4920g.this.f3003d != null) {
                    C4920g.this.f3003d.mo61662a(str);
                }
            }
        });
    }
}
