package com.startapp.android.publish.adsCommon.adinformation;

import android.content.Context;
import com.startapp.android.publish.adsCommon.AdsConstants;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.common.p042c.C2362f;
import com.startapp.common.p092a.C5152e;
import java.io.Serializable;

/* renamed from: com.startapp.android.publish.adsCommon.adinformation.a */
/* compiled from: StartAppSDK */
public class C4968a implements Serializable {

    /* renamed from: a */
    private static C4968a f3103a = new C4968a();

    /* renamed from: b */
    private static Object f3104b = new Object();
    private static final long serialVersionUID = 1;
    @C2362f(mo20785a = true)
    private AdInformationConfig AdInformation = AdInformationConfig.m2966a();
    private String adInformationMetadataUpdateVersion = AdsConstants.f3024h;

    public C4968a() {
        mo62109a().mo62095g();
    }

    /* renamed from: a */
    public AdInformationConfig mo62109a() {
        return this.AdInformation;
    }

    /* renamed from: a */
    public static void m2983a(Context context) {
        C4968a aVar = (C4968a) C5152e.m3786a(context, "StartappAdInfoMetadata", C4968a.class);
        C4968a aVar2 = new C4968a();
        if (aVar != null) {
            boolean a = C4946i.m2927a(aVar, aVar2);
            if (!aVar.m2987f() && a) {
                String str = "";
                C5017f.m3256a(context, C5015d.METADATA_NULL, "AdInformationMetaData", str, str);
            }
            aVar.m2986e();
            f3103a = aVar;
        } else {
            f3103a = aVar2;
        }
        m2985b().mo62109a().mo62095g();
    }

    /* renamed from: e */
    private void m2986e() {
        this.AdInformation.mo62099k();
    }

    /* renamed from: b */
    public static C4968a m2985b() {
        return f3103a;
    }

    /* renamed from: a */
    public static void m2984a(Context context, C4968a aVar) {
        synchronized (f3104b) {
            aVar.adInformationMetadataUpdateVersion = AdsConstants.f3024h;
            f3103a = aVar;
            AdInformationConfig.m2967a(m2985b().AdInformation);
            m2985b().mo62109a().mo62095g();
            C5152e.m3790a(context, "StartappAdInfoMetadata", (Serializable) aVar);
        }
    }

    /* renamed from: c */
    public String mo62110c() {
        return this.AdInformation.mo62090b();
    }

    /* renamed from: d */
    public String mo62111d() {
        return this.AdInformation.mo62091c();
    }

    /* renamed from: f */
    private boolean m2987f() {
        return !AdsConstants.f3024h.equals(this.adInformationMetadataUpdateVersion);
    }
}
