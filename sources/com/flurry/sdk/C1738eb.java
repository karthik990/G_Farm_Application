package com.flurry.sdk;

import java.io.PrintStream;
import java.io.PrintWriter;

/* renamed from: com.flurry.sdk.eb */
public abstract class C1738eb implements Runnable {

    /* renamed from: a */
    private PrintStream f995a;

    /* renamed from: b */
    private PrintWriter f996b;

    /* renamed from: a */
    public abstract void mo16236a() throws Exception;

    public final void run() {
        try {
            mo16236a();
        } catch (Throwable th) {
            PrintStream printStream = this.f995a;
            if (printStream != null) {
                th.printStackTrace(printStream);
            } else {
                PrintWriter printWriter = this.f996b;
                if (printWriter != null) {
                    th.printStackTrace(printWriter);
                } else {
                    th.printStackTrace();
                }
            }
            C1685cy.m755a(6, "SafeRunnable", "", th);
            C1588be.m517a();
            C1588be.m518a("SafeRunnableException", "Exception caught by SafeRunnable", th);
        }
    }
}
