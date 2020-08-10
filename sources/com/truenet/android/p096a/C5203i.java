package com.truenet.android.p096a;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.util.concurrent.SynchronousQueue;
import p000a.p001a.p003b.p005b.C0032h;

/* renamed from: com.truenet.android.a.i */
/* compiled from: StartAppSDK */
public final class C5203i {

    /* renamed from: a */
    public static final C5204a f3648a = new C5204a(null);
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static final String f3649b = f3648a.getClass().getSimpleName();
    /* access modifiers changed from: private */

    /* renamed from: c */
    public static String f3650c;

    /* renamed from: com.truenet.android.a.i$a */
    /* compiled from: StartAppSDK */
    public static final class C5204a {

        /* renamed from: com.truenet.android.a.i$a$a */
        /* compiled from: StartAppSDK */
        static final class C5205a implements Runnable {

            /* renamed from: a */
            final /* synthetic */ Context f3651a;

            /* renamed from: b */
            final /* synthetic */ SynchronousQueue f3652b;

            C5205a(Context context, SynchronousQueue synchronousQueue) {
                this.f3651a = context;
                this.f3652b = synchronousQueue;
            }

            public final void run() {
                try {
                    WebSettings settings = new WebView(this.f3651a).getSettings();
                    C0032h.m41a((Object) settings, "WebView(context).settings");
                    this.f3652b.offer(settings.getUserAgentString());
                } catch (Exception e) {
                    Log.e(C5203i.f3649b, e.getMessage());
                    this.f3652b.offer("undefined");
                }
            }
        }

        private C5204a() {
        }

        public /* synthetic */ C5204a(C0029e eVar) {
            this();
        }

        /* renamed from: a */
        public final String mo62918a(Context context) {
            C0032h.m44b(context, "context");
            if (C5203i.f3650c != null) {
                String a = C5203i.f3650c;
                if (a == null) {
                    C0032h.m40a();
                }
                return a;
            }
            SynchronousQueue synchronousQueue = new SynchronousQueue();
            new Handler(Looper.getMainLooper()).post(new C5205a(context, synchronousQueue));
            C5203i.f3650c = (String) synchronousQueue.take();
            String a2 = C5203i.f3650c;
            if (a2 == null) {
                C0032h.m40a();
            }
            return a2;
        }
    }
}
