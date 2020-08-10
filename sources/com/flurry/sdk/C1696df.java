package com.flurry.sdk;

import android.net.TrafficStats;
import android.net.Uri;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;

/* renamed from: com.flurry.sdk.df */
public class C1696df extends C1739ec {

    /* renamed from: a */
    private final C1676cu<String, String> f892a = new C1676cu<>();

    /* renamed from: b */
    private final C1676cu<String, String> f893b = new C1676cu<>();

    /* renamed from: c */
    private int f894c = 10000;

    /* renamed from: d */
    private int f895d = 15000;

    /* renamed from: e */
    final Object f896e = new Object();

    /* renamed from: f */
    public String f897f;

    /* renamed from: g */
    public C1699a f898g;

    /* renamed from: h */
    C1700b f899h;

    /* renamed from: i */
    HttpURLConnection f900i;

    /* renamed from: j */
    boolean f901j;

    /* renamed from: k */
    boolean f902k;

    /* renamed from: l */
    long f903l = -1;

    /* renamed from: m */
    public int f904m = -1;

    /* renamed from: n */
    public boolean f905n = false;

    /* renamed from: o */
    boolean f906o;

    /* renamed from: q */
    private int f907q;

    /* renamed from: r */
    private int f908r;

    /* renamed from: s */
    private boolean f909s = true;

    /* renamed from: t */
    private boolean f910t;

    /* renamed from: u */
    private long f911u = -1;

    /* renamed from: v */
    private Exception f912v;

    /* renamed from: w */
    private boolean f913w;

    /* renamed from: x */
    private int f914x = 25000;

    /* renamed from: y */
    private C1694de f915y = new C1694de(this);

    /* renamed from: com.flurry.sdk.df$2 */
    static /* synthetic */ class C16982 {

        /* renamed from: a */
        static final /* synthetic */ int[] f917a = new int[C1699a.values().length];

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
                com.flurry.sdk.df$a[] r0 = com.flurry.sdk.C1696df.C1699a.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f917a = r0
                int[] r0 = f917a     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.flurry.sdk.df$a r1 = com.flurry.sdk.C1696df.C1699a.kPost     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f917a     // Catch:{ NoSuchFieldError -> 0x001f }
                com.flurry.sdk.df$a r1 = com.flurry.sdk.C1696df.C1699a.kPut     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f917a     // Catch:{ NoSuchFieldError -> 0x002a }
                com.flurry.sdk.df$a r1 = com.flurry.sdk.C1696df.C1699a.kDelete     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f917a     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.flurry.sdk.df$a r1 = com.flurry.sdk.C1696df.C1699a.kHead     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = f917a     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.flurry.sdk.df$a r1 = com.flurry.sdk.C1696df.C1699a.kGet     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1696df.C16982.<clinit>():void");
        }
    }

    /* renamed from: com.flurry.sdk.df$a */
    public enum C1699a {
        kUnknown,
        kGet,
        kPost,
        kPut,
        kDelete,
        kHead;

        public final String toString() {
            int i = C16982.f917a[ordinal()];
            if (i == 1) {
                return "POST";
            }
            if (i == 2) {
                return "PUT";
            }
            if (i == 3) {
                return "DELETE";
            }
            if (i == 4) {
                return "HEAD";
            }
            if (i != 5) {
                return null;
            }
            return "GET";
        }
    }

    /* renamed from: com.flurry.sdk.df$b */
    public interface C1700b {
        /* renamed from: a */
        void mo16397a();

        /* renamed from: a */
        void mo16398a(C1696df dfVar, InputStream inputStream) throws Exception;

        /* renamed from: a */
        void mo16399a(OutputStream outputStream) throws Exception;
    }

    /* renamed from: a */
    public final void mo16403a(String str, String str2) {
        this.f892a.mo16385a(str, str2);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final void mo16404b() {
        if (this.f899h != null && !mo16405c()) {
            this.f899h.mo16397a();
        }
    }

    /* renamed from: c */
    public final boolean mo16405c() {
        boolean z;
        synchronized (this.f896e) {
            z = this.f902k;
        }
        return z;
    }

    /* renamed from: a */
    public void mo16236a() {
        String str = " for url: ";
        String str2 = "HTTP status: ";
        String str3 = "HttpStreamRequest";
        try {
            if (this.f897f != null) {
                if (!C1652c.m655a()) {
                    StringBuilder sb = new StringBuilder("Network not available, aborting http request: ");
                    sb.append(this.f897f);
                    C1685cy.m754a(3, str3, sb.toString());
                } else {
                    if (this.f898g == null || C1699a.kUnknown.equals(this.f898g)) {
                        this.f898g = C1699a.kGet;
                    }
                    m800d();
                    StringBuilder sb2 = new StringBuilder(str2);
                    sb2.append(this.f904m);
                    sb2.append(str);
                    sb2.append(this.f897f);
                    C1685cy.m754a(4, str3, sb2.toString());
                }
            }
        } catch (Exception e) {
            StringBuilder sb3 = new StringBuilder(str2);
            sb3.append(this.f904m);
            sb3.append(str);
            sb3.append(this.f897f);
            C1685cy.m754a(4, str3, sb3.toString());
            StringBuilder sb4 = new StringBuilder("Exception during http request: ");
            sb4.append(this.f897f);
            C1685cy.m755a(3, str3, sb4.toString(), e);
            if (this.f900i != null) {
                this.f908r = this.f900i.getReadTimeout();
                this.f907q = this.f900i.getConnectTimeout();
            }
            this.f912v = e;
        } catch (Throwable th) {
            this.f915y.mo16400a();
            mo16404b();
            throw th;
        }
        this.f915y.mo16400a();
        mo16404b();
    }

    /* renamed from: d */
    private void m800d() throws Exception {
        InputStream inputStream;
        InputStream inputStream2;
        BufferedOutputStream bufferedOutputStream;
        Throwable th;
        OutputStream outputStream;
        if (!this.f902k) {
            String str = this.f897f;
            if (!TextUtils.isEmpty(str) && Uri.parse(str).getScheme() == null) {
                str = "http://".concat(String.valueOf(str));
            }
            this.f897f = str;
            this.f900i = (HttpURLConnection) new URL(this.f897f).openConnection();
            this.f900i.setConnectTimeout(this.f894c);
            this.f900i.setReadTimeout(this.f895d);
            this.f900i.setRequestMethod(this.f898g.toString());
            this.f900i.setInstanceFollowRedirects(this.f909s);
            this.f900i.setDoOutput(C1699a.kPost.equals(this.f898g));
            this.f900i.setDoInput(true);
            TrafficStats.setThreadStatsTag(1234);
            for (Entry entry : this.f892a.mo16383a()) {
                this.f900i.addRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }
            if (!C1699a.kGet.equals(this.f898g) && !C1699a.kPost.equals(this.f898g)) {
                this.f900i.setRequestProperty("Accept-Encoding", "");
            }
            if (this.f902k) {
                m801e();
                return;
            }
            if (this.f905n && (this.f900i instanceof HttpsURLConnection)) {
                this.f900i.connect();
                C1701dg.m810a((HttpsURLConnection) this.f900i);
            }
            BufferedInputStream bufferedInputStream = null;
            if (C1699a.kPost.equals(this.f898g)) {
                try {
                    outputStream = this.f900i.getOutputStream();
                    try {
                        bufferedOutputStream = new BufferedOutputStream(outputStream);
                    } catch (Throwable th2) {
                        Throwable th3 = th2;
                        bufferedOutputStream = null;
                        th = th3;
                        C1734dz.m871a((Closeable) bufferedOutputStream);
                        C1734dz.m871a((Closeable) outputStream);
                        throw th;
                    }
                    try {
                        if (this.f899h != null && !mo16405c()) {
                            this.f899h.mo16399a(bufferedOutputStream);
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        C1734dz.m871a((Closeable) bufferedOutputStream);
                        C1734dz.m871a((Closeable) outputStream);
                        throw th;
                    }
                } catch (Throwable th5) {
                    bufferedOutputStream = null;
                    th = th5;
                    outputStream = null;
                    C1734dz.m871a((Closeable) bufferedOutputStream);
                    C1734dz.m871a((Closeable) outputStream);
                    throw th;
                }
                try {
                    C1734dz.m871a((Closeable) bufferedOutputStream);
                    C1734dz.m871a((Closeable) outputStream);
                } catch (Exception e) {
                    String str2 = "HttpStreamRequest";
                    StringBuilder sb = new StringBuilder("Exception is:");
                    sb.append(e.getLocalizedMessage());
                    C1685cy.m754a(6, str2, sb.toString());
                    m801e();
                    return;
                } catch (Throwable th6) {
                    m801e();
                    throw th6;
                }
            }
            if (this.f910t) {
                this.f903l = System.currentTimeMillis();
            }
            if (this.f913w) {
                this.f915y.mo16401a((long) this.f914x);
            }
            this.f904m = this.f900i.getResponseCode();
            if (this.f910t && this.f903l != -1) {
                this.f911u = System.currentTimeMillis() - this.f903l;
            }
            this.f915y.mo16400a();
            for (Entry entry2 : this.f900i.getHeaderFields().entrySet()) {
                for (String a : (List) entry2.getValue()) {
                    this.f893b.mo16385a(entry2.getKey(), a);
                }
            }
            if (!C1699a.kGet.equals(this.f898g) && !C1699a.kPost.equals(this.f898g)) {
                m801e();
            } else if (this.f902k) {
                m801e();
            } else {
                try {
                    if (this.f904m == 200) {
                        inputStream2 = this.f900i.getInputStream();
                    } else {
                        inputStream2 = this.f900i.getErrorStream();
                    }
                    try {
                        BufferedInputStream bufferedInputStream2 = new BufferedInputStream(inputStream2);
                        try {
                            if (this.f899h != null && !mo16405c()) {
                                this.f899h.mo16398a(this, bufferedInputStream2);
                            }
                            C1734dz.m871a((Closeable) bufferedInputStream2);
                            C1734dz.m871a((Closeable) inputStream2);
                            m801e();
                        } catch (Throwable th7) {
                            BufferedInputStream bufferedInputStream3 = bufferedInputStream2;
                            inputStream = inputStream2;
                            th = th7;
                            bufferedInputStream = bufferedInputStream3;
                            C1734dz.m871a((Closeable) bufferedInputStream);
                            C1734dz.m871a((Closeable) inputStream);
                            throw th;
                        }
                    } catch (Throwable th8) {
                        Throwable th9 = th8;
                        inputStream = inputStream2;
                        th = th9;
                        C1734dz.m871a((Closeable) bufferedInputStream);
                        C1734dz.m871a((Closeable) inputStream);
                        throw th;
                    }
                } catch (Throwable th10) {
                    th = th10;
                    inputStream = null;
                    C1734dz.m871a((Closeable) bufferedInputStream);
                    C1734dz.m871a((Closeable) inputStream);
                    throw th;
                }
            }
        }
    }

    /* renamed from: e */
    private void m801e() {
        if (!this.f901j) {
            this.f901j = true;
            HttpURLConnection httpURLConnection = this.f900i;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }
}
