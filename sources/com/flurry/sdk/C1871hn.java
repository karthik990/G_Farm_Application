package com.flurry.sdk;

import android.util.Base64;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: com.flurry.sdk.hn */
final class C1871hn implements C1949o<C1543ak> {
    C1871hn() {
    }

    /* renamed from: a */
    public final /* synthetic */ void mo16242a(Object obj) {
        C1543ak akVar = (C1543ak) obj;
        boolean z = akVar.f508b;
        Map a = akVar.mo16260a();
        if (a == null || a.size() == 0) {
            C1685cy.m754a(2, "ReportedIDFrame", "Reported ids is empty, do not send the frame.");
        } else {
            C1771fb.m926a().mo16467a(new C1917jd(new C1918je(a, z)));
        }
        Map a2 = akVar.mo16260a();
        HashMap hashMap = new HashMap();
        for (Entry entry : a2.entrySet()) {
            if (((C1544al) entry.getKey()).equals(C1544al.AndroidInstallationId)) {
                hashMap.put(((C1544al) entry.getKey()).name(), C1734dz.m868a(Base64.decode((String) entry.getValue(), 2)).toUpperCase(Locale.getDefault()));
            } else {
                hashMap.put(((C1544al) entry.getKey()).name(), entry.getValue());
            }
        }
        C1588be.m517a();
        C1588be.m519a("Reported Ids", hashMap);
        StringBuilder sb = new StringBuilder("IdProvider");
        sb.append(akVar.mo16260a());
        C1685cy.m754a(4, "IdObserver", sb.toString());
    }
}
