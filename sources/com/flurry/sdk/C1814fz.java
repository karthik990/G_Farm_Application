package com.flurry.sdk;

import android.os.SystemClock;
import com.flurry.sdk.C1812fy.C1813a;
import com.flurry.sdk.C1833gj.C1834a;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.flurry.sdk.fz */
public final class C1814fz implements C1812fy {

    /* renamed from: a */
    Map<C1928jn, C1930jp> f1142a;

    /* renamed from: b */
    long f1143b = Long.MIN_VALUE;

    /* renamed from: c */
    long f1144c = Long.MIN_VALUE;

    /* renamed from: d */
    long f1145d = Long.MIN_VALUE;

    /* renamed from: e */
    int f1146e = C1587bd.BACKGROUND.f643d;

    /* renamed from: f */
    private AtomicBoolean f1147f;

    /* renamed from: g */
    private C1811fx f1148g;

    /* renamed from: h */
    private boolean f1149h = false;

    /* renamed from: i */
    private Timer f1150i = null;

    /* renamed from: j */
    private TimerTask f1151j = null;

    /* renamed from: k */
    private C1818b f1152k = C1818b.INACTIVE;

    /* renamed from: com.flurry.sdk.fz$2 */
    static /* synthetic */ class C18162 {

        /* renamed from: a */
        static final /* synthetic */ int[] f1155a = new int[C1818b.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.flurry.sdk.fz$b[] r0 = com.flurry.sdk.C1814fz.C1818b.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1155a = r0
                int[] r0 = f1155a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.flurry.sdk.fz$b r1 = com.flurry.sdk.C1814fz.C1818b.FOREGROUND_RUNNING     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f1155a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.flurry.sdk.fz$b r1 = com.flurry.sdk.C1814fz.C1818b.FOREGROUND_ENDING     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f1155a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.flurry.sdk.fz$b r1 = com.flurry.sdk.C1814fz.C1818b.BACKGROUND_RUNNING     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f1155a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.flurry.sdk.fz$b r1 = com.flurry.sdk.C1814fz.C1818b.BACKGROUND_ENDING     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = f1155a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.flurry.sdk.fz$b r1 = com.flurry.sdk.C1814fz.C1818b.INACTIVE     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1814fz.C18162.<clinit>():void");
        }
    }

    /* renamed from: com.flurry.sdk.fz$a */
    public class C1817a extends TimerTask {
        protected C1817a() {
        }

        public final void run() {
            C1814fz.this.mo16493b();
            C1814fz fzVar = C1814fz.this;
            if (fzVar.f1145d <= 0) {
                fzVar.f1145d = SystemClock.elapsedRealtime();
            }
            if (C1814fz.m1029a(fzVar.f1143b)) {
                fzVar.mo16494b((C1930jp) C1919jf.m1169a(fzVar.f1143b, fzVar.f1144c, fzVar.f1145d, fzVar.f1146e));
            } else {
                C1685cy.m754a(6, "SessionRule", "Session id is invalid. Not appending this session id frame.");
            }
            C1813a aVar = C1813a.REASON_SESSION_FINALIZE;
            fzVar.mo16494b((C1930jp) C1900in.m1148a(aVar.ordinal(), aVar.f1141j));
            fzVar.mo16492a(false);
            fzVar.mo16495c();
        }
    }

    /* renamed from: com.flurry.sdk.fz$b */
    enum C1818b {
        INACTIVE,
        FOREGROUND_RUNNING,
        FOREGROUND_ENDING,
        BACKGROUND_RUNNING,
        BACKGROUND_ENDING
    }

    /* renamed from: a */
    static boolean m1029a(long j) {
        return j > 0;
    }

    public C1814fz(C1811fx fxVar) {
        this.f1148g = fxVar;
        if (this.f1142a == null) {
            this.f1142a = new HashMap();
        }
        this.f1142a.clear();
        this.f1142a.put(C1928jn.SESSION_INFO, null);
        this.f1142a.put(C1928jn.APP_STATE, null);
        this.f1142a.put(C1928jn.APP_INFO, null);
        this.f1142a.put(C1928jn.REPORTED_ID, null);
        this.f1142a.put(C1928jn.DEVICE_PROPERTIES, null);
        this.f1142a.put(C1928jn.SESSION_ID, null);
        this.f1142a = this.f1142a;
        this.f1147f = new AtomicBoolean(false);
    }

    /* renamed from: a */
    public final void mo16490a() {
        m1036e();
    }

    /* renamed from: a */
    public final void mo16491a(C1930jp jpVar) {
        if (jpVar.mo16501a().equals(C1928jn.FLUSH_FRAME)) {
            C1901io ioVar = (C1901io) jpVar.mo16519f();
            if (!C1813a.REASON_SESSION_FINALIZE.f1141j.equals(ioVar.f1296b)) {
                if (!C1813a.REASON_STICKY_SET_COMPLETE.f1141j.equals(ioVar.f1296b)) {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    m1026a(this.f1144c, elapsedRealtime, "Flush In Middle");
                    mo16494b((C1930jp) C1919jf.m1169a(this.f1143b, this.f1144c, elapsedRealtime, this.f1146e));
                }
                C1930jp jpVar2 = (C1930jp) this.f1142a.get(C1928jn.SESSION_ID);
                if (jpVar2 != null) {
                    m1033c(jpVar2);
                }
            }
            return;
        }
        String str = "SessionRule";
        if (jpVar.mo16501a().equals(C1928jn.REPORTING)) {
            C1852gy gyVar = (C1852gy) jpVar.mo16519f();
            int i = C18162.f1155a[this.f1152k.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            if (i != 5) {
                                C1685cy.m754a(6, str, "Unreachable Code");
                            } else if (m1031b(gyVar)) {
                                this.f1149h = gyVar.f1241f;
                                m1027a(C1818b.FOREGROUND_RUNNING);
                                m1028a(gyVar);
                            } else if (m1034c(gyVar)) {
                                m1027a(C1818b.BACKGROUND_RUNNING);
                                m1028a(gyVar);
                            }
                        } else if (m1031b(gyVar)) {
                            m1036e();
                            m1027a(C1818b.FOREGROUND_RUNNING);
                            m1028a(gyVar);
                        } else if (m1034c(gyVar)) {
                            mo16493b();
                            this.f1145d = Long.MIN_VALUE;
                            m1027a(C1818b.BACKGROUND_RUNNING);
                        }
                    } else if (m1031b(gyVar)) {
                        m1036e();
                        m1027a(C1818b.FOREGROUND_RUNNING);
                        m1028a(gyVar);
                    } else {
                        if (gyVar.f1236a.equals(C1587bd.BACKGROUND) && gyVar.f1240e.equals(C1586bc.SESSION_END)) {
                            m1030b(gyVar.f1239d);
                            m1027a(C1818b.BACKGROUND_ENDING);
                        }
                    }
                } else if (m1031b(gyVar)) {
                    mo16493b();
                    this.f1145d = Long.MIN_VALUE;
                    m1027a(C1818b.FOREGROUND_RUNNING);
                }
            } else if (gyVar.f1236a.equals(C1587bd.FOREGROUND)) {
                if (this.f1149h && !gyVar.f1241f) {
                    this.f1149h = false;
                }
                if ((gyVar.f1236a.equals(C1587bd.FOREGROUND) && gyVar.f1240e.equals(C1586bc.SESSION_END)) && (this.f1149h || !gyVar.f1241f)) {
                    m1030b(gyVar.f1239d);
                    m1027a(C1818b.FOREGROUND_ENDING);
                }
            }
        }
        if (jpVar.mo16501a().equals(C1928jn.ANALYTICS_ERROR) && ((C1836gk) jpVar.mo16519f()).f1193g == C1834a.UNRECOVERABLE_CRASH.f1181d) {
            mo16493b();
            this.f1145d = SystemClock.elapsedRealtime();
            if (m1029a(this.f1143b)) {
                m1026a(this.f1144c, this.f1145d, "Process Crash");
                mo16494b((C1930jp) C1919jf.m1169a(this.f1143b, this.f1144c, this.f1145d, this.f1146e));
            } else {
                C1685cy.m754a(6, str, "Session id is invalid. Not appending this session id frame.");
            }
        }
        if (jpVar.mo16501a().equals(C1928jn.CCPA_DELETION)) {
            C1813a aVar = C1813a.REASON_DATA_DELETION;
            m1033c((C1930jp) C1900in.m1148a(aVar.ordinal(), aVar.f1141j));
        }
        C1928jn a = jpVar.mo16501a();
        if (this.f1142a.containsKey(a)) {
            StringBuilder sb = new StringBuilder("Adding Sticky Frame:");
            sb.append(jpVar.mo16518e());
            C1685cy.m754a(3, str, sb.toString());
            this.f1142a.put(a, jpVar);
        } else {
            C1930jp jpVar3 = jpVar;
        }
        if (this.f1147f.get() || !m1035d()) {
            if (this.f1147f.get() && jpVar.mo16501a().equals(C1928jn.NOTIFICATION)) {
                C1588be.m517a();
                C1588be.m519a("Flush Token Refreshed", Collections.emptyMap());
                C1813a aVar2 = C1813a.REASON_PUSH_TOKEN_REFRESH;
                m1033c((C1930jp) C1900in.m1148a(aVar2.ordinal(), aVar2.f1141j));
            }
            return;
        }
        this.f1147f.set(true);
        C1813a aVar3 = C1813a.REASON_STICKY_SET_COMPLETE;
        m1033c((C1930jp) C1900in.m1148a(aVar3.ordinal(), aVar3.f1141j));
        String str2 = "last_streaming_http_error_code";
        int b = C1775fe.m938b(str2, Integer.MIN_VALUE);
        String str3 = "last_streaming_http_error_message";
        String str4 = "";
        String b2 = C1775fe.m940b(str3, str4);
        String str5 = "last_streaming_http_report_identifier";
        String b3 = C1775fe.m940b(str5, str4);
        if (b != Integer.MIN_VALUE) {
            C1734dz.m870a(b, b2, b3, true, false);
            C1775fe.m934a(str2);
            C1775fe.m934a(str3);
            C1775fe.m934a(str5);
        }
        String str6 = "last_legacy_http_error_code";
        int b4 = C1775fe.m938b(str6, Integer.MIN_VALUE);
        String str7 = "last_legacy_http_error_message";
        String b5 = C1775fe.m940b(str7, str4);
        String str8 = "last_legacy_http_report_identifier";
        String b6 = C1775fe.m940b(str8, str4);
        if (b4 != Integer.MIN_VALUE) {
            C1734dz.m870a(b4, b5, b6, false, false);
            C1775fe.m934a(str6);
            C1775fe.m934a(str7);
            C1775fe.m934a(str8);
        }
        C1775fe.m936a("last_streaming_session_id", this.f1143b);
        HashMap hashMap = new HashMap();
        hashMap.put("streaming.session.id", String.valueOf(this.f1143b));
        C1588be.m517a();
        C1588be.m519a("Session Ids", hashMap);
    }

    /* renamed from: a */
    private void m1027a(C1818b bVar) {
        String str = "SessionRule";
        if (this.f1152k.equals(bVar)) {
            C1685cy.m754a(3, str, "Invalid state transition.");
            return;
        }
        StringBuilder sb = new StringBuilder("Previous session state: ");
        sb.append(this.f1152k.name());
        C1685cy.m754a(3, str, sb.toString());
        this.f1152k = bVar;
        StringBuilder sb2 = new StringBuilder("Current session state: ");
        sb2.append(this.f1152k.name());
        C1685cy.m754a(3, str, sb2.toString());
    }

    /* renamed from: d */
    private boolean m1035d() {
        boolean z = true;
        for (Entry value : this.f1142a.entrySet()) {
            if (value.getValue() == null) {
                z = false;
            }
        }
        return z;
    }

    /* renamed from: e */
    private void m1036e() {
        String str = "SessionRule";
        if (this.f1143b <= 0) {
            StringBuilder sb = new StringBuilder("Finalize session ");
            sb.append(this.f1143b);
            C1685cy.m754a(6, str, sb.toString());
            return;
        }
        mo16493b();
        this.f1145d = SystemClock.elapsedRealtime();
        if (m1029a(this.f1143b)) {
            mo16494b((C1930jp) C1919jf.m1169a(this.f1143b, this.f1144c, this.f1145d, this.f1146e));
        } else {
            C1685cy.m754a(6, str, "Session id is invalid. Not appending this session id frame.");
        }
        C1813a aVar = C1813a.REASON_SESSION_FINALIZE;
        mo16494b((C1930jp) C1900in.m1148a(aVar.ordinal(), aVar.f1141j));
        mo16492a(false);
        mo16495c();
    }

    /* renamed from: a */
    private void m1028a(C1852gy gyVar) {
        String str = "SessionRule";
        if (!gyVar.f1240e.equals(C1586bc.SESSION_START)) {
            C1685cy.m754a(3, str, "Only generate session id during session start");
            return;
        }
        if (this.f1143b == Long.MIN_VALUE && this.f1142a.get(C1928jn.SESSION_ID) == null) {
            StringBuilder sb = new StringBuilder("Generating Session Id:");
            sb.append(gyVar.f1237b);
            C1685cy.m754a(3, str, sb.toString());
            this.f1143b = gyVar.f1237b;
            this.f1144c = SystemClock.elapsedRealtime();
            this.f1146e = gyVar.f1236a.f643d == 1 ? 2 : 0;
            if (m1029a(this.f1143b)) {
                m1026a(this.f1144c, this.f1145d, "Generate Session Id");
                m1033c((C1930jp) C1919jf.m1169a(this.f1143b, this.f1144c, this.f1145d, this.f1146e));
            } else {
                C1685cy.m754a(6, str, "Session id is invalid. Not appending this session id frame.");
            }
            mo16492a(true);
        }
    }

    /* renamed from: c */
    private void m1033c(C1930jp jpVar) {
        if (this.f1148g != null) {
            StringBuilder sb = new StringBuilder("Appending Frame:");
            sb.append(jpVar.mo16518e());
            C1685cy.m754a(3, "SessionRule", sb.toString());
            this.f1148g.mo16483a(jpVar);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final void mo16494b(C1930jp jpVar) {
        if (this.f1148g != null) {
            StringBuilder sb = new StringBuilder("Forwarding Frame:");
            sb.append(jpVar.mo16518e());
            C1685cy.m754a(3, "SessionRule", sb.toString());
            this.f1148g.mo16485b(jpVar);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final synchronized void mo16493b() {
        if (this.f1150i != null) {
            this.f1150i.cancel();
            this.f1150i = null;
        }
        if (this.f1151j != null) {
            this.f1151j.cancel();
            this.f1151j = null;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public final void mo16495c() {
        C1685cy.m754a(3, "SessionRule", "Reset session rule");
        this.f1142a.put(C1928jn.SESSION_ID, null);
        this.f1147f.set(false);
        this.f1143b = Long.MIN_VALUE;
        this.f1144c = Long.MIN_VALUE;
        this.f1145d = Long.MIN_VALUE;
        this.f1152k = C1818b.INACTIVE;
        this.f1149h = false;
    }

    /* renamed from: b */
    private static boolean m1031b(C1852gy gyVar) {
        return gyVar.f1236a.equals(C1587bd.FOREGROUND) && gyVar.f1240e.equals(C1586bc.SESSION_START);
    }

    /* renamed from: c */
    private static boolean m1034c(C1852gy gyVar) {
        return gyVar.f1236a.equals(C1587bd.BACKGROUND) && gyVar.f1240e.equals(C1586bc.SESSION_START);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final void mo16492a(final boolean z) {
        C1811fx fxVar = this.f1148g;
        if (fxVar != null) {
            fxVar.mo16484a((Runnable) new C1738eb() {
                /* renamed from: a */
                public final void mo16236a() throws Exception {
                    if (z) {
                        C1948n.m1229a().f1425k.mo16283a(C1814fz.this.f1143b, C1814fz.this.f1144c);
                    }
                    C1578bb bbVar = C1948n.m1229a().f1425k;
                    bbVar.f618d.set(z);
                }
            });
        }
    }

    /* renamed from: a */
    private static void m1026a(long j, long j2, String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("fl.session.elapsed.start.time", String.valueOf(j));
        if (j2 != Long.MIN_VALUE) {
            hashMap.put("fl.session.elapsed.end.time", String.valueOf(j2));
            hashMap.put("fl.session.duration", String.valueOf(j2 - j));
        }
        hashMap.put("fl.session.message", str);
        C1588be.m517a();
        C1588be.m519a("Session Duration", hashMap);
    }

    /* renamed from: b */
    private void m1030b(long j) {
        mo16493b();
        this.f1145d = SystemClock.elapsedRealtime();
        if (m1029a(this.f1143b)) {
            m1026a(this.f1144c, this.f1145d, "Start Session Finalize Timer");
            m1033c((C1930jp) C1919jf.m1169a(this.f1143b, this.f1144c, this.f1145d, this.f1146e));
        } else {
            C1685cy.m754a(6, "SessionRule", "Session id is invalid. Not appending this session id frame.");
        }
        m1032c(j);
    }

    /* renamed from: c */
    private synchronized void m1032c(long j) {
        if (this.f1150i != null) {
            mo16493b();
        }
        this.f1150i = new Timer("FlurrySessionTimer");
        this.f1151j = new C1817a();
        this.f1150i.schedule(this.f1151j, j);
    }
}
