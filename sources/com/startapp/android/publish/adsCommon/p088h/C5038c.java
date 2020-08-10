package com.startapp.android.publish.adsCommon.p088h;

import android.content.Context;
import com.startapp.android.publish.adsCommon.p082f.C5012b;
import com.startapp.android.publish.adsCommon.p090j.C5048b;
import com.startapp.android.publish.common.metaData.MetaData;
import java.util.concurrent.TimeUnit;

/* renamed from: com.startapp.android.publish.adsCommon.h.c */
/* compiled from: StartAppSDK */
public class C5038c extends C5034a {
    public C5038c(Context context, Runnable runnable, C5012b bVar) {
        super(context, runnable, bVar);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public void mo62340b() {
        try {
            long millis = TimeUnit.SECONDS.toMillis((long) MetaData.getInstance().getSensorsConfig().mo62650a());
            final C5048b bVar = new C5048b(this.f3273a, this);
            mo62339a(new Runnable() {
                public void run() {
                    bVar.mo62358b();
                    C5038c.this.mo62338a(bVar.mo62359c());
                }
            }, millis);
            bVar.mo62357a();
        } catch (Exception unused) {
            mo62338a(null);
        }
    }

    /* renamed from: a */
    public void mo62338a(Object obj) {
        if (obj != null) {
            this.f3275c.mo62272a(obj.toString());
        }
        super.mo62338a(obj);
    }
}
