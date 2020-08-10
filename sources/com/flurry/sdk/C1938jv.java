package com.flurry.sdk;

import android.os.FileObserver;

/* renamed from: com.flurry.sdk.jv */
public final class C1938jv extends FileObserver {

    /* renamed from: a */
    private C1934jt f1395a;

    /* renamed from: b */
    private String f1396b;

    public C1938jv(String str, C1934jt jtVar) {
        super(str);
        this.f1396b = str;
        this.f1395a = jtVar;
    }

    public final void onEvent(int i, String str) {
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            if ((i & 8) != 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(this.f1396b);
                sb2.append("/");
                sb2.append(str);
                sb2.append(" is written and closed\n");
                sb.append(sb2.toString());
                StringBuilder sb3 = new StringBuilder("Observer triggered ");
                sb3.append(sb.toString());
                C1685cy.m754a(3, "VNodeObserver", sb3.toString());
                this.f1395a.mo16522a(str);
            }
        }
    }
}
