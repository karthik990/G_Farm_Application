package com.flurry.sdk;

import android.text.TextUtils;

/* renamed from: com.flurry.sdk.bi */
public final class C1598bi {

    /* renamed from: c */
    private static C1598bi f663c;

    /* renamed from: a */
    public String f664a;

    /* renamed from: b */
    public String f665b;

    private C1598bi() {
    }

    /* renamed from: a */
    public static C1598bi m531a() {
        if (f663c == null) {
            f663c = new C1598bi();
        }
        return f663c;
    }

    /* renamed from: b */
    public final String mo16288b() {
        if (TextUtils.isEmpty(this.f664a)) {
            mo16289c();
        }
        StringBuilder sb = new StringBuilder("Getting streaming apikey: ");
        sb.append(this.f664a);
        C1685cy.m754a(3, "APIKeyProvider", sb.toString());
        return this.f664a;
    }

    /* renamed from: c */
    public final void mo16289c() {
        if (TextUtils.isEmpty(this.f664a)) {
            this.f664a = this.f665b;
            if (!m532d()) {
                StringBuilder sb = new StringBuilder();
                sb.append(this.f664a);
                sb.append("0");
                this.f664a = sb.toString();
            }
            StringBuilder sb2 = new StringBuilder("Generating a streaming apikey: ");
            sb2.append(this.f664a);
            C1685cy.m754a(3, "APIKeyProvider", sb2.toString());
        }
    }

    /* renamed from: d */
    private static boolean m532d() {
        return C1687d.m770a() == 0;
    }
}
