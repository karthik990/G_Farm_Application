package com.flurry.sdk;

import java.util.Timer;
import java.util.TimerTask;

/* renamed from: com.flurry.sdk.de */
public final class C1694de {

    /* renamed from: a */
    C1696df f888a;

    /* renamed from: b */
    private Timer f889b;

    /* renamed from: c */
    private C1695a f890c;

    /* renamed from: com.flurry.sdk.de$a */
    class C1695a extends TimerTask {
        private C1695a() {
        }

        /* synthetic */ C1695a(C1694de deVar, byte b) {
            this();
        }

        public final void run() {
            C1685cy.m754a(3, "HttpRequestTimeoutTimer", "HttpRequest timed out. Cancelling.");
            C1696df dfVar = C1694de.this.f888a;
            long currentTimeMillis = System.currentTimeMillis() - dfVar.f903l;
            StringBuilder sb = new StringBuilder("Timeout (");
            sb.append(currentTimeMillis);
            sb.append("MS) for url: ");
            sb.append(dfVar.f897f);
            C1685cy.m754a(3, "HttpStreamRequest", sb.toString());
            dfVar.f904m = 629;
            dfVar.f906o = true;
            dfVar.mo16404b();
            StringBuilder sb2 = new StringBuilder("Cancelling http request: ");
            sb2.append(dfVar.f897f);
            C1685cy.m754a(3, "HttpStreamRequest", sb2.toString());
            synchronized (dfVar.f896e) {
                dfVar.f902k = true;
            }
            if (!dfVar.f901j) {
                dfVar.f901j = true;
                if (dfVar.f900i != null) {
                    new Thread() {
                        public final void run() {
                            try {
                                if (C1696df.this.f900i != null) {
                                    C1696df.this.f900i.disconnect();
                                }
                            } catch (Throwable unused) {
                            }
                        }
                    }.start();
                }
            }
        }
    }

    public C1694de(C1696df dfVar) {
        this.f888a = dfVar;
    }

    /* renamed from: a */
    public final synchronized void mo16400a() {
        if (this.f889b != null) {
            this.f889b.cancel();
            this.f889b = null;
            C1685cy.m754a(3, "HttpRequestTimeoutTimer", "HttpRequestTimeoutTimer stopped.");
        }
        this.f890c = null;
    }

    /* renamed from: a */
    public final synchronized void mo16401a(long j) {
        if (this.f889b != null) {
            mo16400a();
        }
        this.f889b = new Timer("HttpRequestTimeoutTimer");
        this.f890c = new C1695a(this, 0);
        this.f889b.schedule(this.f890c, j);
        StringBuilder sb = new StringBuilder("HttpRequestTimeoutTimer started: ");
        sb.append(j);
        sb.append("MS");
        C1685cy.m754a(3, "HttpRequestTimeoutTimer", sb.toString());
    }
}
