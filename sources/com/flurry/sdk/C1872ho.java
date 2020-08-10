package com.flurry.sdk;

import android.os.Bundle;
import com.flurry.sdk.C1550ao.C1551a;
import java.util.ArrayList;

/* renamed from: com.flurry.sdk.ho */
final class C1872ho implements C1949o<C1550ao> {
    C1872ho() {
    }

    /* renamed from: a */
    public final /* synthetic */ void mo16242a(Object obj) {
        C1550ao aoVar = (C1550ao) obj;
        if (C1551a.APP_ORIENTATION_CHANGE.equals(aoVar.f526a)) {
            Bundle bundle = aoVar.f527b;
            if (bundle != null) {
                String str = "orientation_name";
                if (bundle.containsKey(str)) {
                    int i = bundle.getInt(str);
                    C1888ib.m1133a(i);
                    StringBuilder sb = new StringBuilder();
                    sb.append(aoVar.f526a.name());
                    sb.append(" orientation: ");
                    sb.append(i);
                    C1685cy.m754a(5, "LifecycleObserver", sb.toString());
                }
            }
        }
        if (C1551a.CREATED.equals(aoVar.f526a)) {
            m1115a(aoVar);
        }
    }

    /* renamed from: a */
    private static void m1115a(C1550ao aoVar) {
        Bundle bundle = aoVar.f527b;
        if (bundle != null) {
            Bundle bundle2 = bundle.getBundle("launch_options");
            if (bundle2 != null) {
                StringBuilder sb = new StringBuilder("Launch Options Bundle is present ");
                sb.append(bundle2.toString());
                String str = "LifecycleObserver";
                C1685cy.m754a(3, str, sb.toString());
                for (String str2 : bundle2.keySet()) {
                    if (str2 != null) {
                        Object obj = bundle2.get(str2);
                        String obj2 = obj != null ? obj.toString() : "null";
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(obj2);
                        String str3 = "LaunchOptionsFrame";
                        if (str2 == null || str2.isEmpty()) {
                            C1685cy.m754a(2, str3, "Launch option key is empty, do not send the frame.");
                        } else if (arrayList.size() == 0) {
                            C1685cy.m754a(2, str3, "Launch option values is empty, do not send the frame.");
                        } else {
                            C1771fb.m926a().mo16467a(new C1904ir(new C1905is(str2, arrayList)));
                        }
                        StringBuilder sb2 = new StringBuilder("Launch options Key: ");
                        sb2.append(str2);
                        sb2.append(". Its value: ");
                        sb2.append(obj2);
                        C1685cy.m754a(3, str, sb2.toString());
                    }
                }
            }
        }
    }
}
