package com.startapp.android.publish.common.metaData;

import android.content.Context;
import com.startapp.android.publish.adsCommon.Utils.C4936b;
import com.startapp.android.publish.adsCommon.p082f.C5013c;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.common.C5183d;
import com.startapp.common.p092a.C5155g;
import com.startapp.common.p093b.p094a.C5170b;
import com.startapp.common.p093b.p094a.C5170b.C5171a;
import com.startapp.common.p093b.p094a.C5170b.C5172b;
import java.util.Map;

/* renamed from: com.startapp.android.publish.common.metaData.e */
/* compiled from: StartAppSDK */
public class C5117e implements C5170b {
    /* renamed from: a */
    public void mo44557a(final Context context, int i, Map<String, String> map, final C5172b bVar) {
        C5155g.m3807a("PeriodicInfoEvent", 3, "PeriodicInfoEvent execute");
        try {
            MetaData.init(context);
            MetaData.getInstance().setReady(true);
            if (MetaData.getInstance().isPeriodicInfoEventEnabled()) {
                new C5013c(context, true, new C5183d() {
                    /* renamed from: a */
                    public void mo62338a(Object obj) {
                        if (bVar != null) {
                            C4936b.m2871d(context);
                            bVar.mo62538a(C5171a.SUCCESS);
                        }
                    }
                }).mo62279a();
            } else if (bVar != null) {
                C4936b.m2871d(context);
                bVar.mo62538a(C5171a.SUCCESS);
            }
        } catch (Exception e) {
            C5017f.m3256a(context, C5015d.EXCEPTION, "PeriodicInfoEvent.execute", e.getMessage(), "");
        }
    }
}
