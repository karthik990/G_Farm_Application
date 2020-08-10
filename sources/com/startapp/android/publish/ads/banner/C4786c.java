package com.startapp.android.publish.ads.banner;

import android.content.Context;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.common.p042c.C2362f;
import com.startapp.common.p092a.C5152e;
import java.io.Serializable;

/* renamed from: com.startapp.android.publish.ads.banner.c */
/* compiled from: StartAppSDK */
public class C4786c implements Serializable {

    /* renamed from: a */
    private static transient Object f2555a = new Object();

    /* renamed from: b */
    private static transient C4786c f2556b = new C4786c();
    private static final long serialVersionUID = 1;
    @C2362f(mo20785a = true)
    private BannerOptions BannerOptions = new BannerOptions();
    private String bannerMetadataUpdateVersion = AdsConstants.f3024h;

    /* renamed from: a */
    public static C4786c m2356a() {
        return f2556b;
    }

    /* renamed from: b */
    public BannerOptions mo61369b() {
        return this.BannerOptions;
    }

    /* renamed from: c */
    public BannerOptions mo61370c() {
        return new BannerOptions(this.BannerOptions);
    }

    /* renamed from: a */
    public static void m2358a(Context context, C4786c cVar) {
        synchronized (f2555a) {
            cVar.bannerMetadataUpdateVersion = AdsConstants.f3024h;
            f2556b = cVar;
            C5152e.m3790a(context, "StartappBannerMetadata", (Serializable) cVar);
        }
    }

    /* renamed from: a */
    public static void m2357a(Context context) {
        C4786c cVar = (C4786c) C5152e.m3786a(context, "StartappBannerMetadata", C4786c.class);
        C4786c cVar2 = new C4786c();
        if (cVar != null) {
            boolean a = C4946i.m2927a(cVar, cVar2);
            if (!cVar.m2359d() && a) {
                String str = "";
                C5017f.m3256a(context, C5015d.METADATA_NULL, "BannerMetaData", str, str);
            }
            f2556b = cVar;
            return;
        }
        f2556b = cVar2;
    }

    /* renamed from: d */
    private boolean m2359d() {
        return !AdsConstants.f3024h.equals(this.bannerMetadataUpdateVersion);
    }
}
