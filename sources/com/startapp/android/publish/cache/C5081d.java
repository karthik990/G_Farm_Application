package com.startapp.android.publish.cache;

import android.content.Context;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.common.p042c.C2362f;
import com.startapp.common.p092a.C5152e;
import java.io.Serializable;

/* renamed from: com.startapp.android.publish.cache.d */
/* compiled from: StartAppSDK */
public class C5081d implements Serializable {

    /* renamed from: a */
    private static transient C5081d f3396a = new C5081d();
    private static final long serialVersionUID = 1;
    @C2362f(mo20785a = true)
    private ACMConfig ACM = new ACMConfig();
    private String cacheMetaDataUpdateVersion = AdsConstants.f3024h;
    private float sendCacheSizeProb = 20.0f;

    private C5081d() {
    }

    /* renamed from: a */
    public static C5081d m3532a() {
        return f3396a;
    }

    /* renamed from: b */
    public ACMConfig mo62488b() {
        return this.ACM;
    }

    /* renamed from: a */
    public static void m3534a(Context context, C5081d dVar) {
        dVar.cacheMetaDataUpdateVersion = AdsConstants.f3024h;
        f3396a = dVar;
        C5152e.m3790a(context, "StartappCacheMetadata", (Serializable) dVar);
    }

    /* renamed from: a */
    public static void m3533a(Context context) {
        C5081d dVar = (C5081d) C5152e.m3786a(context, "StartappCacheMetadata", C5081d.class);
        C5081d dVar2 = new C5081d();
        if (dVar != null) {
            boolean a = C4946i.m2927a(dVar, dVar2);
            if (!dVar.m3535d() && a) {
                String str = "";
                C5017f.m3256a(context, C5015d.METADATA_NULL, "CacheMetaData", str, str);
            }
            f3396a = dVar;
            return;
        }
        f3396a = dVar2;
    }

    /* renamed from: d */
    private boolean m3535d() {
        return !AdsConstants.f3024h.equals(this.cacheMetaDataUpdateVersion);
    }

    /* renamed from: c */
    public float mo62489c() {
        return this.sendCacheSizeProb;
    }
}
