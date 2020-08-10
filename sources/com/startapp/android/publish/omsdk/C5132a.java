package com.startapp.android.publish.omsdk;

import android.content.Context;
import android.webkit.WebView;
import com.p021b.p022a.p023a.p024a.C0954a;
import com.p021b.p022a.p023a.p024a.p026b.C0963b;
import com.p021b.p022a.p023a.p024a.p026b.C0964c;
import com.p021b.p022a.p023a.p024a.p026b.C0965d;
import com.p021b.p022a.p023a.p024a.p026b.C0967f;
import com.p021b.p022a.p023a.p024a.p026b.C0968g;
import com.p021b.p022a.p023a.p024a.p026b.C0969h;
import com.startapp.android.publish.GeneratedConstants;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.startapp.android.publish.omsdk.a */
/* compiled from: StartAppSDK */
public class C5132a {
    /* renamed from: a */
    private static String m3702a() {
        return "";
    }

    /* renamed from: a */
    public static C0963b m3700a(WebView webView) {
        if (!m3704a(webView.getContext())) {
            return null;
        }
        return m3701a(C0965d.m127a(m3705b(), webView, m3702a()), false);
    }

    /* renamed from: a */
    public static C0963b m3699a(Context context, AdVerification adVerification) {
        if (!m3704a(context)) {
            return null;
        }
        if (adVerification == null) {
            String str = "";
            C5017f.m3256a(context, C5015d.EXCEPTION, "OMSDK: Verification details can't be null!", str, str);
            return null;
        }
        String a = C5133b.m3706a();
        List<VerificationDetails> adVerification2 = adVerification.getAdVerification();
        ArrayList arrayList = new ArrayList(adVerification2.size());
        for (VerificationDetails verificationDetails : adVerification2) {
            URL a2 = m3703a(context, verificationDetails.getVerificationScriptUrl());
            if (a2 != null) {
                arrayList.add(C0969h.m138a(verificationDetails.getVendorKey(), a2, verificationDetails.getVerificationParameters()));
            }
        }
        return m3701a(C0965d.m128a(m3705b(), a, arrayList, m3702a()), true);
    }

    /* renamed from: a */
    private static C0963b m3701a(C0965d dVar, boolean z) {
        return C0963b.m118a(C0964c.m123a(C0967f.NATIVE, z ? C0967f.NATIVE : C0967f.NONE, false), dVar);
    }

    /* renamed from: b */
    private static C0968g m3705b() {
        return C0968g.m135a("StartApp", GeneratedConstants.INAPP_VERSION);
    }

    /* renamed from: a */
    private static URL m3703a(Context context, String str) {
        try {
            return new URL(str);
        } catch (MalformedURLException e) {
            C5015d dVar = C5015d.EXCEPTION;
            StringBuilder sb = new StringBuilder();
            sb.append("OMSDK: can't create URL - ");
            sb.append(str);
            C5017f.m3256a(context, dVar, sb.toString(), e.getMessage(), "");
            return null;
        }
    }

    /* renamed from: a */
    private static boolean m3704a(Context context) {
        String str = "OMSDK: Failed to activate sdk.";
        String str2 = "";
        try {
            if (C0954a.m92b() || C0954a.m91a(C0954a.m90a(), context)) {
                return true;
            }
            C5017f.m3256a(context, C5015d.EXCEPTION, str, str2, str2);
            return false;
        } catch (Exception e) {
            C5017f.m3256a(context, C5015d.EXCEPTION, str, e.getMessage(), str2);
            return false;
        }
    }
}
