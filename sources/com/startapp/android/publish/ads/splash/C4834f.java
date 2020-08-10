package com.startapp.android.publish.ads.splash;

import android.content.Context;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.common.p042c.C2362f;
import com.startapp.common.p092a.C5152e;
import java.io.Serializable;

/* renamed from: com.startapp.android.publish.ads.splash.f */
/* compiled from: StartAppSDK */
public class C4834f implements Serializable {

    /* renamed from: a */
    private static transient C4834f f2710a = new C4834f();

    /* renamed from: b */
    private static Object f2711b = new Object();
    private static final long serialVersionUID = 1;
    @C2362f(mo20785a = true)
    private SplashConfig SplashConfig = new SplashConfig();
    private String splashMetadataUpdateVersion = AdsConstants.f3024h;

    private C4834f() {
    }

    /* renamed from: a */
    public SplashConfig mo61612a() {
        return this.SplashConfig;
    }

    /* renamed from: b */
    public static C4834f m2519b() {
        return f2710a;
    }

    /* renamed from: a */
    public static void m2518a(Context context, C4834f fVar) {
        synchronized (f2711b) {
            fVar.splashMetadataUpdateVersion = AdsConstants.f3024h;
            f2710a = fVar;
            C5152e.m3790a(context, "StartappSplashMetadata", (Serializable) fVar);
        }
    }

    /* renamed from: a */
    public static void m2517a(Context context) {
        C4834f fVar = (C4834f) C5152e.m3786a(context, "StartappSplashMetadata", C4834f.class);
        C4834f fVar2 = new C4834f();
        if (fVar != null) {
            boolean a = C4946i.m2927a(fVar, fVar2);
            if (!fVar.m2520c() && a) {
                String str = "";
                C5017f.m3256a(context, C5015d.METADATA_NULL, "SplashMetaData", str, str);
            }
            f2710a = fVar;
            return;
        }
        f2710a = fVar2;
    }

    /* renamed from: c */
    private boolean m2520c() {
        return !AdsConstants.f3024h.equals(this.splashMetadataUpdateVersion);
    }
}
