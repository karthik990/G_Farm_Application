package com.startapp.android.publish.adsCommon.p088h;

import android.content.Context;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.startapp.android.publish.adsCommon.C5051k;
import com.startapp.android.publish.adsCommon.p079c.C4996b;
import com.startapp.android.publish.adsCommon.p082f.C5012b;
import com.startapp.android.publish.common.metaData.MetaData;
import java.util.concurrent.TimeUnit;

/* renamed from: com.startapp.android.publish.adsCommon.h.b */
/* compiled from: StartAppSDK */
public class C5036b extends C5034a {
    public C5036b(Context context, Runnable runnable, C5012b bVar) {
        super(context, runnable, bVar);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo62340b() {
        try {
            long millis = TimeUnit.SECONDS.toMillis((long) MetaData.getInstance().getBluetoothConfig().mo62641a());
            final C4996b bVar = new C4996b(this.f3273a, this);
            mo62339a(new Runnable() {
                public void run() {
                    bVar.mo62211a();
                    C5036b.this.mo62338a(bVar.mo62213b());
                }
            }, millis);
            bVar.mo62212a(m3305c());
        } catch (Exception unused) {
            mo62338a(null);
        }
    }

    /* renamed from: c */
    private boolean m3305c() {
        long currentTimeMillis = System.currentTimeMillis();
        String str = "lastBtDiscoveringTime";
        boolean z = currentTimeMillis - C5051k.m3338a(this.f3273a, str, Long.valueOf(0)).longValue() >= ((long) MetaData.getInstance().getBluetoothConfig().mo62643c()) * DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS;
        if (z) {
            C5051k.m3345b(this.f3273a, str, Long.valueOf(currentTimeMillis));
        }
        return z;
    }

    /* renamed from: a */
    public void mo62338a(Object obj) {
        if (obj != null) {
            this.f3275c.mo62275b(obj.toString());
        }
        super.mo62338a(obj);
    }
}
