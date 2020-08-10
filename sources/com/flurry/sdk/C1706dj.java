package com.flurry.sdk;

import com.flurry.android.FlurryAgent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.EventListener.Factory;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpStatus;

/* renamed from: com.flurry.sdk.dj */
public final class C1706dj {

    /* renamed from: com.flurry.sdk.dj$a */
    public static class C1707a extends EventListener {

        /* renamed from: a */
        private static final AtomicLong f948a = new AtomicLong(1);

        /* renamed from: b */
        private long f949b = f948a.getAndIncrement();

        /* renamed from: c */
        private String f950c;

        /* renamed from: d */
        private Map<String, String> f951d;

        /* renamed from: e */
        private long f952e;

        /* renamed from: f */
        private long f953f;

        /* renamed from: g */
        private long f954g;

        /* renamed from: h */
        private long f955h;

        /* renamed from: i */
        private boolean f956i;

        /* renamed from: com.flurry.sdk.dj$a$a */
        public static class C1708a implements Factory {

            /* renamed from: a */
            private String f957a;

            public C1708a(String str) {
                this.f957a = str;
            }

            public void setId(String str) {
                this.f957a = str;
            }

            public C1707a create(Call call) {
                return new C1707a(this.f957a);
            }
        }

        public void requestBodyStart(Call call) {
        }

        public void requestHeadersStart(Call call) {
        }

        public void responseBodyStart(Call call) {
        }

        public void responseHeadersStart(Call call) {
        }

        public C1707a(String str) {
            this.f950c = str;
            this.f952e = System.nanoTime();
            this.f956i = false;
            this.f951d = new HashMap();
        }

        public void setId(String str) {
            this.f950c = str;
        }

        public void callStart(Call call) {
            this.f951d.clear();
            this.f951d.put("fl.id", this.f950c);
            this.f952e = System.nanoTime();
            Request request = call.request();
            if (request != null) {
                this.f951d.put("fl.request.url", request.url().toString());
            }
        }

        public void callEnd(Call call) {
            if (!m826b()) {
                m825a();
            }
        }

        public void callFailed(Call call, IOException iOException) {
            String str = "fl.response.code";
            if (!this.f951d.containsKey(str) || m826b()) {
                if ("timeout".equals(iOException.getMessage())) {
                    this.f951d.put(str, Integer.toString(HttpStatus.SC_REQUEST_TIMEOUT));
                }
            }
            m825a();
        }

        /* renamed from: a */
        private void m825a() {
            double nanoTime = (double) (System.nanoTime() - this.f952e);
            Double.isNaN(nanoTime);
            String str = "fl.total.time";
            this.f951d.put(str, Long.toString((long) (nanoTime / 1000000.0d)));
            StringBuilder sb = new StringBuilder("Logging parameters: ");
            sb.append(this.f951d);
            C1685cy.m766c("HttpLogging", sb.toString());
            FlurryAgent.logEvent("Flurry.HTTPRequestTime", this.f951d);
        }

        public void dnsStart(Call call, String str) {
            this.f953f = System.nanoTime();
        }

        public void dnsEnd(Call call, String str, List<InetAddress> list) {
            double nanoTime = (double) (System.nanoTime() - this.f953f);
            Double.isNaN(nanoTime);
            String str2 = "fl.dns.time";
            this.f951d.put(str2, Long.toString((long) (nanoTime / 1000000.0d)));
        }

        public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
            this.f954g = System.nanoTime();
        }

        public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, Protocol protocol) {
            double nanoTime = (double) (System.nanoTime() - this.f954g);
            Double.isNaN(nanoTime);
            String str = "fl.connect.time";
            this.f951d.put(str, Long.toString((long) (nanoTime / 1000000.0d)));
        }

        public void requestHeadersEnd(Call call, Request request) {
            if (!this.f956i) {
                this.f956i = true;
                this.f951d.put("fl.request.url", request.url().toString());
            }
            this.f955h = System.nanoTime();
        }

        public void requestBodyEnd(Call call, long j) {
            this.f955h = System.nanoTime();
        }

        public void responseHeadersEnd(Call call, Response response) {
            int code = response.code();
            String httpUrl = response.request().url().toString();
            this.f951d.put("fl.response.code", Integer.toString(code));
            this.f951d.put("fl.response.url", httpUrl);
            double nanoTime = (double) (System.nanoTime() - this.f955h);
            Double.isNaN(nanoTime);
            String str = "fl.response.time";
            this.f951d.put(str, Long.toString((long) (nanoTime / 1000000.0d)));
        }

        public void responseBodyEnd(Call call, long j) {
            if (m826b()) {
                double nanoTime = (double) (System.nanoTime() - this.f952e);
                Double.isNaN(nanoTime);
                String str = "fl.redirect.time";
                this.f951d.put(str, Long.toString((long) (nanoTime / 1000000.0d)));
            }
            double nanoTime2 = (double) (System.nanoTime() - this.f955h);
            Double.isNaN(nanoTime2);
            String str2 = "fl.transfer.time";
            this.f951d.put(str2, Long.toString((long) (nanoTime2 / 1000000.0d)));
        }

        /* renamed from: b */
        private boolean m826b() {
            try {
                int parseInt = Integer.parseInt((String) this.f951d.get("fl.response.code"));
                if (parseInt >= 300 && parseInt < 400) {
                    return true;
                }
            } catch (NumberFormatException unused) {
            }
            return false;
        }
    }

    /* renamed from: com.flurry.sdk.dj$b */
    public static class C1709b implements Interceptor {

        /* renamed from: a */
        private String f958a;

        public C1709b(String str) {
            this.f958a = str;
        }

        public void setId(String str) {
            this.f958a = str;
        }

        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long nanoTime = System.nanoTime();
            String httpUrl = request.url().toString();
            String concat = "Sending request for ".concat(String.valueOf(httpUrl));
            String str = "HttpLogging";
            C1685cy.m754a(3, str, concat);
            Response proceed = chain.proceed(request);
            double nanoTime2 = (double) (System.nanoTime() - nanoTime);
            Double.isNaN(nanoTime2);
            long j = (long) (nanoTime2 / 1000000.0d);
            int code = proceed.code();
            String httpUrl2 = proceed.request().url().toString();
            StringBuilder sb = new StringBuilder("Received response ");
            sb.append(code);
            sb.append(" for ");
            sb.append(httpUrl2);
            sb.append(" in ");
            sb.append(j);
            sb.append(" ms");
            C1685cy.m754a(3, str, sb.toString());
            C1706dj.m824a(this.f958a, httpUrl, code, httpUrl2, j);
            return proceed;
        }
    }

    /* renamed from: a */
    public static void m824a(String str, String str2, int i, String str3, long j) {
        HashMap hashMap = new HashMap();
        hashMap.put("fl.id", str);
        hashMap.put("fl.request.url", str2);
        hashMap.put("fl.response.code", Integer.toString(i));
        hashMap.put("fl.response.url", str3);
        hashMap.put("fl.total.time", Long.toString(j));
        C1685cy.m766c("HttpLogging", "Logging parameters: ".concat(String.valueOf(hashMap)));
        FlurryAgent.logEvent("Flurry.HTTPRequestTime", (Map<String, String>) hashMap);
    }
}
