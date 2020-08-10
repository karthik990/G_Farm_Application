package com.flurry.sdk;

import com.flurry.android.FlurryEventRecordStatus;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.flurry.sdk.gj */
public final class C1833gj extends C1927jm {

    /* renamed from: a */
    private static final AtomicInteger f1176a = new AtomicInteger(0);

    /* renamed from: com.flurry.sdk.gj$a */
    public enum C1834a {
        RECOVERABLE_ERROR(1),
        CAUGHT_EXCEPTION(2),
        UNRECOVERABLE_CRASH(3);
        

        /* renamed from: d */
        public int f1181d;

        private C1834a(int i) {
            this.f1181d = i;
        }
    }

    /* renamed from: com.flurry.sdk.gj$b */
    public enum C1835b {
        NO_LOG(0),
        ANDROID_LOG_ATTACHED(2),
        NATIVE_CRASH_ATTACHED(3);
        

        /* renamed from: d */
        public int f1186d;

        private C1835b(int i) {
            this.f1186d = i;
        }
    }

    private C1833gj(C1929jo joVar) {
        super(joVar);
    }

    /* renamed from: a */
    public final C1928jn mo16501a() {
        return C1928jn.ANALYTICS_ERROR;
    }

    /* renamed from: a */
    public static FlurryEventRecordStatus m1068a(C1517aa aaVar) {
        int i;
        int i2;
        C1517aa aaVar2 = aaVar;
        if (aaVar2 == null) {
            C1685cy.m768d("StreamingErrorFrame", "Error is null, do not send the frame.");
            return FlurryEventRecordStatus.kFlurryEventFailed;
        }
        boolean equals = C1963y.UNCAUGHT_EXCEPTION_ID.f1467c.equals(aaVar2.f434a);
        List list = equals ? aaVar2.f441h : null;
        int incrementAndGet = f1176a.incrementAndGet();
        String str = aaVar2.f434a;
        long j = aaVar2.f435b;
        String str2 = aaVar2.f436c;
        String str3 = aaVar2.f437d;
        String a = m1070a(aaVar2.f438e);
        String str4 = aaVar2.f434a;
        if (aaVar2.f438e != null) {
            if (C1963y.UNCAUGHT_EXCEPTION_ID.f1467c.equals(str4)) {
                i = C1834a.UNRECOVERABLE_CRASH.f1181d;
            } else {
                i = C1834a.CAUGHT_EXCEPTION.f1181d;
            }
        } else if (C1963y.NATIVE_CRASH.f1467c.equals(str4)) {
            i = C1834a.UNRECOVERABLE_CRASH.f1181d;
        } else {
            i = C1834a.RECOVERABLE_ERROR.f1181d;
        }
        int i3 = i;
        if (aaVar2.f438e == null) {
            i2 = C1835b.NO_LOG.f1186d;
        } else {
            i2 = C1835b.ANDROID_LOG_ATTACHED.f1186d;
        }
        C1836gk gkVar = new C1836gk(incrementAndGet, str, j, str2, str3, a, i3, i2, aaVar2.f439f, aaVar2.f440g, C1961w.m1240b(), list, "", "");
        C1833gj gjVar = new C1833gj(gkVar);
        if (equals) {
            C1771fb.m926a().f1045b.f1059a.mo16481b(gjVar);
        } else {
            C1771fb.m926a().mo16467a(gjVar);
        }
        return FlurryEventRecordStatus.kFlurryEventRecorded;
    }

    /* renamed from: a */
    public static C1833gj m1069a(C1836gk gkVar) {
        return new C1833gj(gkVar);
    }

    /* renamed from: b */
    public static AtomicInteger m1071b() {
        return f1176a;
    }

    /* renamed from: a */
    private static String m1070a(Throwable th) {
        if (th == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement append : th.getStackTrace()) {
            sb.append(append);
            sb.append(C1772fc.f1048a);
        }
        if (th.getCause() != null) {
            sb.append(C1772fc.f1048a);
            sb.append("Caused by: ");
            for (StackTraceElement append2 : th.getCause().getStackTrace()) {
                sb.append(append2);
                sb.append(C1772fc.f1048a);
            }
        }
        return sb.toString();
    }
}
