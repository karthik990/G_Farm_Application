package com.startapp.android.publish.ads.video.p073c.p076b;

import android.text.TextUtils;
import com.startapp.android.publish.ads.video.p073c.p074a.C4881c;
import com.startapp.android.publish.ads.video.p073c.p074a.C4886e;
import com.startapp.android.publish.ads.video.p073c.p074a.p075a.C4876b;
import com.startapp.common.p092a.C5155g;
import java.util.List;

/* renamed from: com.startapp.android.publish.ads.video.c.b.a */
/* compiled from: StartAppSDK */
public class C4887a {
    /* renamed from: a */
    public static C4876b m2721a(C4886e eVar, C4881c cVar) {
        String str = "VASTModelPostValidator";
        C5155g.m3807a(str, 3, "validate");
        C4876b bVar = null;
        if (!m2722a(eVar)) {
            C5155g.m3807a(str, 3, "Validator returns: not valid (invalid model)");
            return null;
        }
        if (cVar != null) {
            C4876b a = cVar.mo61744a(eVar.mo61754b());
            if (a != null) {
                String a2 = a.mo61717a();
                if (!TextUtils.isEmpty(a2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("mediaPicker selected mediaFile with URL ");
                    sb.append(a2);
                    C5155g.m3807a(str, 3, sb.toString());
                    bVar = a;
                }
            }
        } else {
            C5155g.m3807a(str, 5, "mediaPicker: We don't have a compatible media file to play.");
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Validator returns: ");
        sb2.append(bVar != null ? "valid" : "not valid (no media file)");
        C5155g.m3807a(str, 3, sb2.toString());
        return bVar;
    }

    /* renamed from: a */
    public static boolean m2722a(C4886e eVar) {
        String str = "VASTModelPostValidator";
        C5155g.m3807a(str, 3, "validateModel");
        List d = eVar.mo61756d();
        boolean z = (d == null || d.size() == 0) ? false : true;
        List b = eVar.mo61754b();
        if (b != null && b.size() != 0) {
            return z;
        }
        C5155g.m3807a(str, 3, "Validator error: mediaFile list invalid");
        return false;
    }
}
