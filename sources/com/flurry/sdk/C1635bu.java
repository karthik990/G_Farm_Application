package com.flurry.sdk;

import android.content.Context;
import java.io.File;
import java.util.List;
import java.util.Map;

/* renamed from: com.flurry.sdk.bu */
public final class C1635bu {

    /* renamed from: a */
    boolean f733a;

    /* renamed from: b */
    private final C1636bv f734b;

    /* renamed from: c */
    private final File f735c;

    /* renamed from: d */
    private String f736d;

    public C1635bu() {
        this(C1576b.m502a());
    }

    public C1635bu(Context context) {
        this.f734b = new C1636bv();
        this.f735c = context.getFileStreamPath(".flurryinstallreceiver.");
        StringBuilder sb = new StringBuilder("Referrer file name if it exists:  ");
        sb.append(this.f735c);
        C1685cy.m754a(3, "InstallLogger", sb.toString());
    }

    /* renamed from: b */
    private void m603b(String str) {
        if (str != null) {
            this.f736d = str;
        }
    }

    /* renamed from: a */
    public final synchronized void mo16319a(String str) {
        this.f733a = true;
        m603b(str);
        C1732dy.m860a(this.f735c, this.f736d);
    }

    /* renamed from: a */
    public final synchronized Map<String, List<String>> mo16318a() {
        if (!this.f733a) {
            this.f733a = true;
            StringBuilder sb = new StringBuilder("Loading referrer info from file: ");
            sb.append(this.f735c.getAbsolutePath());
            C1685cy.m754a(4, "InstallLogger", sb.toString());
            String c = C1732dy.m865c(this.f735c);
            C1685cy.m756a("InstallLogger", "Referrer file contents: ".concat(String.valueOf(c)));
            m603b(c);
        }
        return C1636bv.m606a(this.f736d);
    }
}
