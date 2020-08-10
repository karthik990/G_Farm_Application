package com.flurry.sdk;

import com.flurry.sdk.C1756ex.C1758a;
import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;

/* renamed from: com.flurry.sdk.ez */
public final class C1764ez extends C1766f implements C1931jq {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public PriorityQueue<String> f1034a;

    /* renamed from: a */
    public final void mo16243a() {
    }

    public C1764ez() {
        super("FrameLogTestHandler", C1756ex.m904a(C1758a.CORE));
        this.f1034a = null;
        this.f1034a = new PriorityQueue<>(4, new C1779fh());
    }

    /* renamed from: a */
    public final void mo16457a(final List<String> list) {
        String str = "FrameLogTestHandler";
        if (list.size() == 0) {
            C1685cy.m762b(str, "File List is null or empty");
            return;
        }
        StringBuilder sb = new StringBuilder("Number of files being added:");
        sb.append(list.toString());
        C1685cy.m762b(str, sb.toString());
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() throws Exception {
                C1764ez.this.f1034a.addAll(list);
                C1764ez.this.m920b();
            }
        });
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public void m920b() {
        StringBuilder sb = new StringBuilder(" Starting processNextFile ");
        sb.append(this.f1034a.size());
        String str = "FrameLogTestHandler";
        C1685cy.m762b(str, sb.toString());
        if (this.f1034a.peek() == null) {
            C1685cy.m762b(str, "No file present to process.");
            return;
        }
        String str2 = (String) this.f1034a.poll();
        if (C1776ff.m944b(str2)) {
            File file = new File(str2);
            File a = C1732dy.m859a();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a.toString());
            sb2.append(File.separator);
            sb2.append("fCompletedInApp");
            boolean a2 = C1933js.m1206a(file, new File(sb2.toString(), String.format(Locale.US, "completedInApp-%d", new Object[]{Long.valueOf(System.currentTimeMillis())})));
            if (a2) {
                a2 = file.delete();
            }
            m919a(str2, a2);
        }
    }

    /* renamed from: a */
    private synchronized void m919a(String str, boolean z) {
        String str2 = "FrameLogTestHandler";
        StringBuilder sb = new StringBuilder("File move to test folder for file: ");
        sb.append(str);
        sb.append(" fileMoved:");
        sb.append(z);
        C1685cy.m762b(str2, sb.toString());
        boolean a = C1776ff.m942a(str);
        StringBuilder sb2 = new StringBuilder("Deleting file ");
        sb2.append(str);
        sb2.append(" deleted ");
        sb2.append(a);
        C1685cy.m754a(2, "FrameLogTestHandler", sb2.toString());
        m920b();
    }
}
