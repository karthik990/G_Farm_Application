package com.startapp.android.publish.adsCommon.p082f;

import android.content.Context;
import com.startapp.android.publish.adsCommon.p088h.C5034a;
import com.startapp.android.publish.adsCommon.p088h.C5036b;
import com.startapp.android.publish.adsCommon.p088h.C5038c;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.common.C5183d;
import com.startapp.common.p092a.C5155g;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.startapp.android.publish.adsCommon.f.c */
/* compiled from: StartAppSDK */
public class C5013c {

    /* renamed from: f */
    static AtomicBoolean f3222f = new AtomicBoolean(false);

    /* renamed from: a */
    Context f3223a;

    /* renamed from: b */
    C5183d f3224b;

    /* renamed from: c */
    ArrayList<C5034a> f3225c;

    /* renamed from: d */
    int f3226d;

    /* renamed from: e */
    C5012b f3227e;

    /* renamed from: g */
    private Runnable f3228g;

    public C5013c(Context context, boolean z) {
        this(context, z, null);
    }

    public C5013c(Context context, boolean z, C5183d dVar) {
        this.f3228g = new Runnable() {
            public void run() {
                synchronized (this) {
                    C5013c cVar = C5013c.this;
                    int i = cVar.f3226d - 1;
                    cVar.f3226d = i;
                    if (i == 0) {
                        C5155g.m3807a("DataEventTask", 3, "sending DataEvent");
                        C5017f.m3258a(C5013c.this.f3223a, C5013c.this.f3227e, "");
                        C5013c.f3222f.set(false);
                        C5013c.this.mo62280b();
                    }
                }
            }
        };
        this.f3223a = context;
        this.f3224b = dVar;
        this.f3225c = new ArrayList<>();
        this.f3227e = new C5012b(C5015d.PERIODIC);
        this.f3227e.mo62273a(z);
        if (MetaData.getInstance().getSensorsConfig().mo62651b()) {
            this.f3225c.add(new C5038c(context, this.f3228g, this.f3227e));
        }
        if (MetaData.getInstance().getBluetoothConfig().mo62642b()) {
            this.f3225c.add(new C5036b(context, this.f3228g, this.f3227e));
        }
        this.f3226d = this.f3225c.size();
    }

    /* renamed from: a */
    public void mo62279a() {
        if (this.f3226d > 0) {
            if (f3222f.compareAndSet(false, true)) {
                for (int i = 0; i < this.f3226d; i++) {
                    ((C5034a) this.f3225c.get(i)).mo62337a();
                }
                return;
            }
        }
        mo62280b();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public void mo62280b() {
        C5183d dVar = this.f3224b;
        if (dVar != null) {
            dVar.mo62338a(null);
        }
    }

    /* renamed from: c */
    public C5012b mo62281c() {
        return this.f3227e;
    }
}
