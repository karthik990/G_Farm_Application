package com.startapp.android.publish.common.metaData;

import android.content.Context;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.AdsConstants.ServiceApiType;
import com.startapp.android.publish.adsCommon.C5051k;
import com.startapp.android.publish.adsCommon.C5053l;
import com.startapp.android.publish.adsCommon.Utils.C4936b;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.adsCommon.p091k.C5052a;
import com.startapp.android.publish.common.metaData.MetaDataRequest.C5109a;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.common.p092a.C5155g;
import com.startapp.common.p093b.p094a.C5170b;
import com.startapp.common.p093b.p094a.C5170b.C5171a;
import com.startapp.common.p093b.p094a.C5170b.C5172b;
import java.util.Map;

/* renamed from: com.startapp.android.publish.common.metaData.f */
/* compiled from: StartAppSDK */
public class C5119f implements C5170b {
    /* renamed from: a */
    public void mo44557a(Context context, int i, Map<String, String> map, C5172b bVar) {
        try {
            MetaData.init(context);
            if (MetaData.getInstance().isPeriodicMetaDataEnabled()) {
                m3638a(context, bVar);
            }
        } catch (Exception e) {
            C5017f.m3256a(context, C5015d.EXCEPTION, "PeriodicMetaData.execute", e.getMessage(), "");
        }
    }

    /* renamed from: a */
    private static void m3638a(Context context, C5172b bVar) {
        final AdPreferences adPreferences = new AdPreferences(C5051k.m3339a(context, "shared_prefs_devId", (String) null), C5051k.m3339a(context, "shared_prefs_appId", ""));
        final Context context2 = context;
        final C5172b bVar2 = bVar;
        C51201 r2 = new C5113c(context, adPreferences, C5109a.PERIODIC) {

            /* renamed from: d */
            private MetaData f3479d = null;

            /* access modifiers changed from: protected */
            /* renamed from: c */
            public Boolean mo62647c() {
                C5155g.m3805a(3, "Loading MetaData");
                try {
                    C5053l.m3365b(context2);
                    MetaDataRequest metaDataRequest = new MetaDataRequest(context2, C5109a.PERIODIC);
                    metaDataRequest.fillApplicationDetails(context2, adPreferences, false);
                    metaDataRequest.fillLocationDetails(adPreferences, context2);
                    this.f3479d = (MetaData) C4946i.m2905a(C5052a.m3348a(context2, AdsConstants.m2856a(ServiceApiType.METADATA), metaDataRequest, null).mo62865a(), MetaData.class);
                    return Boolean.TRUE;
                } catch (Exception e) {
                    C5155g.m3806a(6, "Unable to handle GetMetaData command!!!!", (Throwable) e);
                    return Boolean.FALSE;
                }
            }

            /* access modifiers changed from: protected */
            /* renamed from: a */
            public void mo62645a(Boolean bool) {
                try {
                    if (!(!bool.booleanValue() || this.f3479d == null || context2 == null)) {
                        MetaData.update(context2, this.f3479d);
                    }
                    C4936b.m2870c(context2);
                    if (bVar2 != null) {
                        bVar2.mo62538a(C5171a.SUCCESS);
                    }
                } catch (Exception e) {
                    C5017f.m3256a(context2, C5015d.EXCEPTION, "PeriodicMetaData.onPostExecute", e.getMessage(), "");
                }
            }
        };
        r2.mo62644a();
    }
}
