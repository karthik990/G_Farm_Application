package com.flurry.sdk;

import com.flurry.sdk.C1756ex.C1758a;
import com.flurry.sdk.C1824gc.C1825a;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.protocol.HTTP;

/* renamed from: com.flurry.sdk.ga */
public final class C1820ga extends C1766f implements C1824gc {

    /* renamed from: a */
    protected BufferedOutputStream f1163a;

    /* renamed from: b */
    private C1823gb f1164b;

    /* renamed from: d */
    private int f1165d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public ReentrantLock f1166e;

    public C1820ga() {
        super("BufferedFrameAppender", C1756ex.m904a(C1758a.CORE));
        this.f1164b = null;
        this.f1163a = null;
        this.f1165d = 0;
        this.f1166e = new ReentrantLock();
        this.f1164b = new C1823gb();
    }

    /* renamed from: a */
    public final void mo16498a(final C1930jp jpVar, final C1825a aVar) {
        StringBuilder sb = new StringBuilder("Appending Frame:");
        sb.append(jpVar.mo16501a());
        C1685cy.m754a(2, "BufferedFrameAppender", sb.toString());
        runAsync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                C1820ga.this.f1166e.lock();
                C1820ga.m1045a(C1820ga.this, jpVar);
                C1825a aVar = aVar;
                if (aVar != null) {
                    aVar.mo16473a();
                }
                C1820ga.this.f1166e.unlock();
            }
        });
    }

    /* renamed from: a */
    public final void mo16497a(final C1930jp jpVar) {
        StringBuilder sb = new StringBuilder("Appending Frame:");
        sb.append(jpVar.mo16501a());
        C1685cy.m754a(2, "BufferedFrameAppender", sb.toString());
        runSync(new C1738eb() {
            /* renamed from: a */
            public final void mo16236a() {
                C1820ga.this.f1166e.lock();
                C1820ga.m1045a(C1820ga.this, jpVar);
                C1820ga.this.f1166e.unlock();
            }
        });
    }

    /* renamed from: a */
    private boolean m1046a(byte[] bArr) {
        boolean z = false;
        if (bArr == null) {
            return false;
        }
        try {
            this.f1163a.write(bArr);
            this.f1163a.flush();
            z = true;
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder("Error appending frame:");
            sb.append(e.getMessage());
            C1685cy.m754a(2, "BufferedFrameAppender", sb.toString());
        }
        return z;
    }

    /* renamed from: a */
    public final void mo16243a() {
        C1685cy.m754a(2, "BufferedFrameAppender", HTTP.CONN_CLOSE);
        this.f1165d = 0;
        C1734dz.m871a((Closeable) this.f1163a);
        this.f1163a = null;
    }

    /* renamed from: a */
    public final boolean mo16499a(String str, String str2) {
        boolean z;
        String str3 = "BufferedFrameAppender";
        C1685cy.m754a(2, str3, "Open");
        boolean z2 = true;
        try {
            File file = new File(str, str2);
            if (file.exists()) {
                z = false;
            } else if (C1732dy.m861a(file)) {
                z = true;
            } else {
                StringBuilder sb = new StringBuilder("Frame file: Error creating directory for :");
                sb.append(file.getAbsolutePath());
                throw new IOException(sb.toString());
            }
            try {
                this.f1163a = new BufferedOutputStream(new FileOutputStream(file, true));
                try {
                    this.f1165d = 0;
                } catch (IOException e) {
                    e = e;
                }
            } catch (IOException e2) {
                z2 = z;
                e = e2;
                StringBuilder sb2 = new StringBuilder("Error in opening file:");
                sb2.append(str2);
                sb2.append(" Message:");
                sb2.append(e.getMessage());
                C1685cy.m754a(6, str3, sb2.toString());
                return z2;
            }
        } catch (IOException e3) {
            e = e3;
            z2 = false;
            StringBuilder sb22 = new StringBuilder("Error in opening file:");
            sb22.append(str2);
            sb22.append(" Message:");
            sb22.append(e.getMessage());
            C1685cy.m754a(6, str3, sb22.toString());
            return z2;
        }
        return z2;
    }

    /* renamed from: b */
    public final boolean mo16500b() {
        return this.f1163a != null;
    }

    /* renamed from: a */
    static /* synthetic */ void m1045a(C1820ga gaVar, C1930jp jpVar) {
        gaVar.f1165d++;
        boolean a = gaVar.m1046a(C1823gb.m1054a(jpVar));
        StringBuilder sb = new StringBuilder("Appending Frame ");
        sb.append(jpVar.mo16501a());
        sb.append(" frameSaved:");
        sb.append(a);
        sb.append(" frameCount:");
        sb.append(gaVar.f1165d);
        C1685cy.m754a(2, "BufferedFrameAppender", sb.toString());
    }
}
