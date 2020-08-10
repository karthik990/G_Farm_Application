package lib.android.paypal.com.magnessdk;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

/* renamed from: lib.android.paypal.com.magnessdk.b */
final class C5987b implements UncaughtExceptionHandler {

    /* renamed from: a */
    private static C5987b f4236a;

    /* renamed from: b */
    private UncaughtExceptionHandler f4237b = Thread.getDefaultUncaughtExceptionHandler();

    private C5987b() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /* renamed from: a */
    public static synchronized C5987b m4029a() {
        C5987b bVar;
        synchronized (C5987b.class) {
            if (f4236a == null) {
                f4236a = new C5987b();
            }
            bVar = f4236a;
        }
        return bVar;
    }

    /* renamed from: a */
    public static void m4030a(Throwable th) {
        th.printStackTrace(new PrintWriter(new StringWriter()));
    }

    public void uncaughtException(Thread thread, Throwable th) {
        th.printStackTrace(new PrintWriter(new StringWriter()));
        m4030a(th);
        UncaughtExceptionHandler uncaughtExceptionHandler = this.f4237b;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }
}
