package com.truenet.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.fasterxml.jackson.core.JsonPointer;
import com.truenet.android.p096a.C5203i;
import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import p000a.p001a.C0041c;
import p000a.p001a.C0046d;
import p000a.p001a.C0073h;
import p000a.p001a.p002a.C0007g;
import p000a.p001a.p003b.p004a.C0022a;
import p000a.p001a.p003b.p005b.C0032h;
import p000a.p001a.p003b.p005b.C0033i;
import p000a.p001a.p003b.p005b.C0035k;
import p000a.p001a.p003b.p005b.C0036l;
import p000a.p001a.p003b.p005b.C0037m.C0038a;
import p000a.p001a.p003b.p005b.C0039n;
import p000a.p001a.p007d.C0051e;
import p000a.p001a.p008e.C0055b;
import p000a.p001a.p008e.C0059c;

/* renamed from: com.truenet.android.b */
/* compiled from: StartAppSDK */
public final class C3030b {

    /* renamed from: a */
    static final /* synthetic */ C0051e[] f1913a;

    /* renamed from: b */
    public static final C3031a f1914b = new C3031a(null);
    /* access modifiers changed from: private */

    /* renamed from: n */
    public static final String f1915n = f1914b.getClass().getSimpleName();
    /* access modifiers changed from: private */

    /* renamed from: o */
    public static final C0055b f1916o = new C0055b("^\\w+(://){1}.+$");

    /* renamed from: c */
    private Bitmap f1917c;

    /* renamed from: d */
    private long f1918d;

    /* renamed from: e */
    private String f1919e = this.f1925k;

    /* renamed from: f */
    private String f1920f;

    /* renamed from: g */
    private final List<C3032b> f1921g = new ArrayList();

    /* renamed from: h */
    private final C0041c f1922h = C0046d.m59a(C3037f.f1942a);

    /* renamed from: i */
    private final C0041c f1923i = C0046d.m59a(new C3038g(this));
    /* access modifiers changed from: private */

    /* renamed from: j */
    public final Context f1924j;

    /* renamed from: k */
    private final String f1925k;

    /* renamed from: l */
    private final int f1926l;

    /* renamed from: m */
    private final long f1927m;

    /* renamed from: com.truenet.android.b$a */
    /* compiled from: StartAppSDK */
    public static final class C3031a {
        private C3031a() {
        }

        public /* synthetic */ C3031a(C0029e eVar) {
            this();
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public final boolean m1849a(String str) {
            if (C0059c.m63a(str, "http://play.google.com", false, 2, null) || C0059c.m63a(str, "https://play.google.com", false, 2, null) || C0059c.m63a(str, "http://itunes.apple.com", false, 2, null) || C0059c.m63a(str, "https://itunes.apple.com", false, 2, null)) {
                return true;
            }
            if (!C0059c.m63a(str, "http://", false, 2, null) && !C0059c.m63a(str, "https://", false, 2, null) && C3030b.f1916o.mo69a(str)) {
                return true;
            }
            return false;
        }
    }

    /* renamed from: com.truenet.android.b$b */
    /* compiled from: StartAppSDK */
    public static final class C3032b {

        /* renamed from: a */
        private final String f1928a;

        /* renamed from: b */
        private final long f1929b;

        /* renamed from: c */
        private final int f1930c;

        /* renamed from: d */
        private final List<String> f1931d;

        /* renamed from: e */
        private final String f1932e;

        /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<java.lang.String>, for r9v0, types: [java.util.List] */
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static /* bridge */ /* synthetic */ com.truenet.android.C3030b.C3032b m1850a(com.truenet.android.C3030b.C3032b r4, java.lang.String r5, long r6, int r8, java.util.List<java.lang.String> r9, java.lang.String r10, int r11, java.lang.Object r12) {
            /*
                r12 = r11 & 1
                if (r12 == 0) goto L_0x0006
                java.lang.String r5 = r4.f1928a
            L_0x0006:
                r12 = r11 & 2
                if (r12 == 0) goto L_0x000c
                long r6 = r4.f1929b
            L_0x000c:
                r0 = r6
                r6 = r11 & 4
                if (r6 == 0) goto L_0x0013
                int r8 = r4.f1930c
            L_0x0013:
                r12 = r8
                r6 = r11 & 8
                if (r6 == 0) goto L_0x001a
                java.util.List<java.lang.String> r9 = r4.f1931d
            L_0x001a:
                r2 = r9
                r6 = r11 & 16
                if (r6 == 0) goto L_0x0021
                java.lang.String r10 = r4.f1932e
            L_0x0021:
                r3 = r10
                r6 = r4
                r7 = r5
                r8 = r0
                r10 = r12
                r11 = r2
                r12 = r3
                com.truenet.android.b$b r4 = r6.mo44595a(r7, r8, r10, r11, r12)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.truenet.android.C3030b.C3032b.m1850a(com.truenet.android.b$b, java.lang.String, long, int, java.util.List, java.lang.String, int, java.lang.Object):com.truenet.android.b$b");
        }

        /* renamed from: a */
        public final C3032b mo44595a(String str, long j, int i, List<String> list, String str2) {
            C0032h.m44b(str, "url");
            C3032b bVar = new C3032b(str, j, i, list, str2);
            return bVar;
        }

        public boolean equals(Object obj) {
            if (this != obj) {
                if (obj instanceof C3032b) {
                    C3032b bVar = (C3032b) obj;
                    if (C0032h.m43a((Object) this.f1928a, (Object) bVar.f1928a)) {
                        if (this.f1929b == bVar.f1929b) {
                            if (!(this.f1930c == bVar.f1930c) || !C0032h.m43a((Object) this.f1931d, (Object) bVar.f1931d) || !C0032h.m43a((Object) this.f1932e, (Object) bVar.f1932e)) {
                                return false;
                            }
                        }
                    }
                }
                return false;
            }
            return true;
        }

        public int hashCode() {
            String str = this.f1928a;
            int i = 0;
            int hashCode = (str != null ? str.hashCode() : 0) * 31;
            long j = this.f1929b;
            int i2 = (((hashCode + ((int) (j ^ (j >>> 32)))) * 31) + this.f1930c) * 31;
            List<String> list = this.f1931d;
            int hashCode2 = (i2 + (list != null ? list.hashCode() : 0)) * 31;
            String str2 = this.f1932e;
            if (str2 != null) {
                i = str2.hashCode();
            }
            return hashCode2 + i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ConnectionInfo(url=");
            sb.append(this.f1928a);
            sb.append(", loadTime=");
            sb.append(this.f1929b);
            sb.append(", httpCode=");
            sb.append(this.f1930c);
            sb.append(", cookie=");
            sb.append(this.f1931d);
            sb.append(", redirectUrl=");
            sb.append(this.f1932e);
            sb.append(")");
            return sb.toString();
        }

        public C3032b(String str, long j, int i, List<String> list, String str2) {
            C0032h.m44b(str, "url");
            this.f1928a = str;
            this.f1929b = j;
            this.f1930c = i;
            this.f1931d = list;
            this.f1932e = str2;
        }

        /* renamed from: a */
        public final String mo44596a() {
            return this.f1928a;
        }

        /* renamed from: b */
        public final long mo44597b() {
            return this.f1929b;
        }

        /* renamed from: c */
        public final int mo44598c() {
            return this.f1930c;
        }

        /* renamed from: d */
        public final List<String> mo44599d() {
            return this.f1931d;
        }

        /* renamed from: e */
        public final String mo44600e() {
            return this.f1932e;
        }
    }

    /* renamed from: com.truenet.android.b$c */
    /* compiled from: StartAppSDK */
    public final class C3033c extends WebViewClient {

        /* renamed from: b */
        private ScheduledExecutorService f1934b = Executors.newScheduledThreadPool(1);

        /* renamed from: c */
        private ScheduledFuture<?> f1935c;

        /* renamed from: com.truenet.android.b$c$a */
        /* compiled from: StartAppSDK */
        static final class C3034a implements Runnable {

            /* renamed from: a */
            final /* synthetic */ C3033c f1936a;

            C3034a(C3033c cVar) {
                this.f1936a = cVar;
            }

            public final void run() {
                C3030b.this.m1839j().offer("");
            }
        }

        public C3033c() {
        }

        /* renamed from: a */
        private final void m1858a(WebView webView, String str) {
            m1857a();
            if (str != null) {
                if (webView != null) {
                    webView.stopLoading();
                }
                C3030b.this.m1839j().offer(str);
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            m1858a(webView, String.valueOf(webResourceRequest != null ? webResourceRequest.getUrl() : null));
            return true;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            m1858a(webView, str);
            return true;
        }

        public void onPageFinished(WebView webView, String str) {
            m1857a();
            this.f1935c = this.f1934b.schedule(new C3034a(this), 1, TimeUnit.SECONDS);
            super.onPageFinished(webView, str);
        }

        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            m1857a();
            if (webView != null) {
                webView.stopLoading();
            }
            C3030b.this.m1839j().offer("");
            super.onReceivedError(webView, webResourceRequest, webResourceError);
        }

        /* renamed from: a */
        private final void m1857a() {
            ScheduledFuture<?> scheduledFuture = this.f1935c;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
            this.f1935c = null;
        }
    }

    /* renamed from: com.truenet.android.b$d */
    /* compiled from: StartAppSDK */
    static final class C3035d extends C0033i implements C0022a<String> {
        final /* synthetic */ C0038a $line;
        final /* synthetic */ C0038a $reader;

        C3035d(C0038a aVar, C0038a aVar2) {
            this.$line = aVar;
            this.$reader = aVar2;
            super(0);
        }

        /* renamed from: b */
        public final String mo45a() {
            this.$line.element = ((BufferedReader) this.$reader.element).readLine();
            return (String) this.$line.element;
        }
    }

    /* renamed from: com.truenet.android.b$e */
    /* compiled from: StartAppSDK */
    static final class C3036e implements Runnable {

        /* renamed from: a */
        final /* synthetic */ HttpURLConnection f1937a;

        /* renamed from: b */
        final /* synthetic */ C3030b f1938b;

        /* renamed from: c */
        final /* synthetic */ List f1939c;

        /* renamed from: d */
        final /* synthetic */ URL f1940d;

        /* renamed from: e */
        final /* synthetic */ String f1941e;

        C3036e(HttpURLConnection httpURLConnection, C3030b bVar, List list, URL url, String str) {
            this.f1937a = httpURLConnection;
            this.f1938b = bVar;
            this.f1939c = list;
            this.f1940d = url;
            this.f1941e = str;
        }

        public final void run() {
            WebView b = this.f1938b.m1840k();
            if (b != null) {
                b.loadDataWithBaseURL(this.f1941e, this.f1938b.mo44593f(), this.f1937a.getContentType(), this.f1937a.getContentEncoding(), null);
            }
        }
    }

    /* renamed from: com.truenet.android.b$f */
    /* compiled from: StartAppSDK */
    static final class C3037f extends C0033i implements C0022a<SynchronousQueue<String>> {

        /* renamed from: a */
        public static final C3037f f1942a = new C3037f();

        C3037f() {
            super(0);
        }

        /* renamed from: b */
        public final SynchronousQueue<String> mo45a() {
            return new SynchronousQueue<>();
        }
    }

    /* renamed from: com.truenet.android.b$g */
    /* compiled from: StartAppSDK */
    static final class C3038g extends C0033i implements C0022a<WebView> {
        final /* synthetic */ C3030b this$0;

        C3038g(C3030b bVar) {
            this.this$0 = bVar;
            super(0);
        }

        /* renamed from: b */
        public final WebView mo45a() {
            try {
                WebView webView = new WebView(this.this$0.f1924j);
                if (VERSION.SDK_INT >= 11) {
                    webView.setLayerType(1, null);
                }
                WebSettings settings = webView.getSettings();
                C0032h.m41a((Object) settings, "settings");
                settings.setJavaScriptEnabled(true);
                webView.setWebChromeClient(new WebChromeClient());
                webView.setWebViewClient(new C3033c());
                return webView;
            } catch (Exception e) {
                Log.e(C3030b.f1915n, e.getMessage());
                return null;
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public final SynchronousQueue<String> m1839j() {
        C0041c cVar = this.f1922h;
        C0051e eVar = f1913a[0];
        return (SynchronousQueue) cVar.mo1a();
    }

    /* access modifiers changed from: private */
    /* renamed from: k */
    public final WebView m1840k() {
        C0041c cVar = this.f1923i;
        C0051e eVar = f1913a[1];
        return (WebView) cVar.mo1a();
    }

    public C3030b(Context context, String str, int i, long j) {
        C0032h.m44b(context, "context");
        C0032h.m44b(str, "url");
        this.f1924j = context;
        this.f1925k = str;
        this.f1926l = i;
        this.f1927m = j;
    }

    /* renamed from: a */
    public final Bitmap mo44588a() {
        return this.f1917c;
    }

    /* renamed from: b */
    public final int mo44589b() {
        return this.f1921g.size();
    }

    /* renamed from: c */
    public final long mo44590c() {
        return this.f1918d;
    }

    /* renamed from: d */
    public final List<C3032b> mo44591d() {
        return C0007g.m10a((Iterable<? extends T>) this.f1921g);
    }

    /* renamed from: e */
    public final String mo44592e() {
        return this.f1919e;
    }

    /* renamed from: f */
    public final String mo44593f() {
        return this.f1920f;
    }

    static {
        Class<C3030b> cls = C3030b.class;
        f1913a = new C0051e[]{C0039n.m51a((C0035k) new C0036l(C0039n.m49a((Class) cls), "queue", "getQueue()Ljava/util/concurrent/SynchronousQueue;")), C0039n.m51a((C0035k) new C0036l(C0039n.m49a((Class) cls), "webView", "getWebView()Landroid/webkit/WebView;"))};
    }

    /* renamed from: a */
    static /* bridge */ /* synthetic */ C3032b m1830a(C3030b bVar, String str, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            list = null;
        }
        return bVar.m1831a(str, list);
    }

    /* renamed from: a */
    private final C3032b m1831a(String str, List<String> list) {
        String str2;
        String str3 = str;
        String str4 = "nextUrl";
        this.f1920f = null;
        if (f1914b.m1849a(str3)) {
            C3032b bVar = new C3032b(str, 0, 200, null, null);
            return bVar;
        }
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(str3);
            URLConnection openConnection = url.openConnection();
            if (openConnection != null) {
                HttpURLConnection httpURLConnection2 = (HttpURLConnection) openConnection;
                boolean z = false;
                httpURLConnection2.setInstanceFollowRedirects(false);
                httpURLConnection2.setConnectTimeout(((int) this.f1927m) * 1000);
                httpURLConnection2.setReadTimeout(((int) this.f1927m) * 1000);
                httpURLConnection2.addRequestProperty("User-Agent", C5203i.f3648a.mo62918a(this.f1924j));
                if (list != null) {
                    String str5 = "Cookie";
                    CharSequence charSequence = ";";
                    Iterable<String> iterable = list;
                    Collection arrayList = new ArrayList(C0007g.m8a(iterable, 10));
                    for (String parse : iterable) {
                        List parse2 = HttpCookie.parse(parse);
                        C0032h.m41a((Object) parse2, "HttpCookie.parse(it)");
                        arrayList.add((HttpCookie) C0007g.m13c(parse2));
                    }
                    httpURLConnection2.setRequestProperty(str5, TextUtils.join(charSequence, (List) arrayList));
                }
                long currentTimeMillis = System.currentTimeMillis();
                httpURLConnection2.connect();
                long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
                String headerField = httpURLConnection2.getHeaderField("Location");
                if (headerField != null) {
                    C0055b bVar2 = f1916o;
                    C0032h.m41a((Object) headerField, str4);
                    if (!bVar2.mo69a(headerField)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(url.getProtocol());
                        sb.append("://");
                        sb.append(url.getHost());
                        C0032h.m41a((Object) headerField, str4);
                        if (!C0059c.m63a(headerField, "/", false, 2, null)) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(JsonPointer.SEPARATOR);
                            sb2.append(headerField);
                            headerField = sb2.toString();
                        }
                        sb.append(headerField);
                        headerField = sb.toString();
                    }
                    str2 = headerField;
                } else {
                    str2 = null;
                }
                String str6 = str;
                C3032b bVar3 = new C3032b(str6, currentTimeMillis2, httpURLConnection2.getResponseCode(), (List) httpURLConnection2.getHeaderFields().get("Set-Cookie"), str2);
                int responseCode = httpURLConnection2.getResponseCode();
                if (200 <= responseCode) {
                    if (299 >= responseCode) {
                        InputStream inputStream = httpURLConnection2.getInputStream();
                        C0032h.m41a((Object) inputStream, "inputStream");
                        this.f1920f = m1832a(inputStream);
                        long currentTimeMillis3 = System.currentTimeMillis() - currentTimeMillis;
                        Handler handler = new Handler(Looper.getMainLooper());
                        C3036e eVar = new C3036e(httpURLConnection2, this, list, url, str);
                        handler.post(eVar);
                        String str7 = (String) m1839j().take();
                        C0032h.m41a((Object) str7, "jsRedirectUrl");
                        if (str7.length() == 0) {
                            z = true;
                        }
                        if (z) {
                            return C3032b.m1850a(bVar3, null, currentTimeMillis3, 0, null, null, 29, null);
                        }
                        return C3032b.m1850a(bVar3, null, currentTimeMillis3, 0, null, str7, 13, null);
                    }
                }
                if (300 <= responseCode) {
                    if (399 >= responseCode) {
                        return bVar3;
                    }
                }
                return C3032b.m1850a(bVar3, null, 0, 0, null, null, 15, null);
            }
            throw new C0073h("null cannot be cast to non-null type java.net.HttpURLConnection");
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0076 A[Catch:{ all -> 0x007f }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x007b A[Catch:{ all -> 0x007f }] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.String m1832a(java.io.InputStream r8) {
        /*
            r7 = this;
            java.lang.String r0 = "stream closed with error!"
            r1 = 0
            r2 = r1
            java.io.BufferedInputStream r2 = (java.io.BufferedInputStream) r2
            a.a.b.b.m$a r3 = new a.a.b.b.m$a
            r3.<init>()
            r4 = r1
            java.io.BufferedReader r4 = (java.io.BufferedReader) r4
            r3.element = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x006e }
            r4.<init>()     // Catch:{ all -> 0x006e }
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch:{ all -> 0x006e }
            r5.<init>(r8)     // Catch:{ all -> 0x006e }
            java.io.BufferedReader r8 = new java.io.BufferedReader     // Catch:{ all -> 0x006c }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ all -> 0x006c }
            r6 = r5
            java.io.InputStream r6 = (java.io.InputStream) r6     // Catch:{ all -> 0x006c }
            r2.<init>(r6)     // Catch:{ all -> 0x006c }
            java.io.Reader r2 = (java.io.Reader) r2     // Catch:{ all -> 0x006c }
            r8.<init>(r2)     // Catch:{ all -> 0x006c }
            r3.element = r8     // Catch:{ all -> 0x006c }
            a.a.b.b.m$a r8 = new a.a.b.b.m$a     // Catch:{ all -> 0x006c }
            r8.<init>()     // Catch:{ all -> 0x006c }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x006c }
            r8.element = r1     // Catch:{ all -> 0x006c }
        L_0x0034:
            com.truenet.android.b$d r1 = new com.truenet.android.b$d     // Catch:{ all -> 0x006c }
            r1.<init>(r8, r3)     // Catch:{ all -> 0x006c }
            a.a.b.a.a r1 = (p000a.p001a.p003b.p004a.C0022a) r1     // Catch:{ all -> 0x006c }
            java.lang.Object r1 = r1.mo45a()     // Catch:{ all -> 0x006c }
            if (r1 == 0) goto L_0x0049
            T r1 = r8.element     // Catch:{ all -> 0x006c }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x006c }
            r4.append(r1)     // Catch:{ all -> 0x006c }
            goto L_0x0034
        L_0x0049:
            java.lang.String r8 = r4.toString()     // Catch:{ all -> 0x006c }
            java.lang.String r1 = "result.toString()"
            p000a.p001a.p003b.p005b.C0032h.m41a(r8, r1)     // Catch:{ all -> 0x006c }
            T r1 = r3.element     // Catch:{ all -> 0x005f }
            java.io.BufferedReader r1 = (java.io.BufferedReader) r1     // Catch:{ all -> 0x005f }
            if (r1 == 0) goto L_0x005b
            r1.close()     // Catch:{ all -> 0x005f }
        L_0x005b:
            r5.close()     // Catch:{ all -> 0x005f }
            goto L_0x006b
        L_0x005f:
            r1 = move-exception
            java.lang.Class r2 = r7.getClass()
            java.lang.String r2 = r2.getCanonicalName()
            android.util.Log.e(r2, r0, r1)
        L_0x006b:
            return r8
        L_0x006c:
            r8 = move-exception
            goto L_0x0070
        L_0x006e:
            r8 = move-exception
            r5 = r2
        L_0x0070:
            T r1 = r3.element     // Catch:{ all -> 0x007f }
            java.io.BufferedReader r1 = (java.io.BufferedReader) r1     // Catch:{ all -> 0x007f }
            if (r1 == 0) goto L_0x0079
            r1.close()     // Catch:{ all -> 0x007f }
        L_0x0079:
            if (r5 == 0) goto L_0x008b
            r5.close()     // Catch:{ all -> 0x007f }
            goto L_0x008b
        L_0x007f:
            r1 = move-exception
            java.lang.Class r2 = r7.getClass()
            java.lang.String r2 = r2.getCanonicalName()
            android.util.Log.e(r2, r0, r1)
        L_0x008b:
            goto L_0x008d
        L_0x008c:
            throw r8
        L_0x008d:
            goto L_0x008c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.truenet.android.C3030b.m1832a(java.io.InputStream):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x003e  */
    /* renamed from: g */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void mo44594g() {
        /*
            r7 = this;
            long r0 = java.lang.System.currentTimeMillis()
            java.lang.String r2 = r7.f1925k
            r3 = 0
            r4 = 2
            com.truenet.android.b$b r2 = m1830a(r7, r2, r3, r4, r3)
            if (r2 == 0) goto L_0x0087
            java.util.List<com.truenet.android.b$b> r4 = r7.f1921g
            r4.add(r2)
        L_0x0013:
            if (r2 == 0) goto L_0x001a
            java.lang.String r4 = r2.mo44600e()
            goto L_0x001b
        L_0x001a:
            r4 = r3
        L_0x001b:
            if (r4 == 0) goto L_0x003c
            boolean r4 = r7.m1834a(r0)
            if (r4 == 0) goto L_0x003c
            java.lang.String r4 = r2.mo44600e()
            if (r4 != 0) goto L_0x002c
            p000a.p001a.p003b.p005b.C0032h.m40a()
        L_0x002c:
            java.util.List r2 = r2.mo44599d()
            com.truenet.android.b$b r2 = r7.m1831a(r4, r2)
            if (r2 == 0) goto L_0x0013
            java.util.List<com.truenet.android.b$b> r4 = r7.f1921g
            r4.add(r2)
            goto L_0x0013
        L_0x003c:
            if (r2 == 0) goto L_0x005b
            r4 = 299(0x12b, float:4.19E-43)
            r5 = 200(0xc8, float:2.8E-43)
            int r2 = r2.mo44598c()
            if (r5 <= r2) goto L_0x0049
            goto L_0x005b
        L_0x0049:
            if (r4 < r2) goto L_0x005b
            java.lang.String r2 = r7.f1920f
            if (r2 == 0) goto L_0x005b
            android.webkit.WebView r2 = r7.m1840k()
            if (r2 == 0) goto L_0x0059
            android.graphics.Bitmap r3 = com.truenet.android.p096a.C5206j.m3956a(r2)
        L_0x0059:
            r7.f1917c = r3
        L_0x005b:
            java.util.List<com.truenet.android.b$b> r2 = r7.f1921g
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            r3 = 0
            java.util.Iterator r2 = r2.iterator()
        L_0x0065:
            boolean r5 = r2.hasNext()
            if (r5 == 0) goto L_0x0077
            java.lang.Object r5 = r2.next()
            com.truenet.android.b$b r5 = (com.truenet.android.C3030b.C3032b) r5
            long r5 = r5.mo44597b()
            long r3 = r3 + r5
            goto L_0x0065
        L_0x0077:
            r7.f1918d = r3
            java.util.List<com.truenet.android.b$b> r2 = r7.f1921g
            java.lang.Object r2 = p000a.p001a.p002a.C0007g.m15e(r2)
            com.truenet.android.b$b r2 = (com.truenet.android.C3030b.C3032b) r2
            java.lang.String r2 = r2.mo44596a()
            r7.f1919e = r2
        L_0x0087:
            java.util.List<com.truenet.android.b$b> r2 = r7.f1921g
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x0096
            long r2 = java.lang.System.currentTimeMillis()
            long r2 = r2 - r0
            r7.f1918d = r2
        L_0x0096:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.truenet.android.C3030b.mo44594g():void");
    }

    /* renamed from: a */
    private final boolean m1834a(long j) {
        int size = this.f1921g.size();
        int i = this.f1926l;
        return (size < i || i == -1) && System.currentTimeMillis() - j < this.f1927m * ((long) 1000);
    }
}
