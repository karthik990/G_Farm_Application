package com.flurry.android;

import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import com.flurry.sdk.C1576b;
import com.flurry.sdk.C1685cy;
import com.flurry.sdk.C1703di;
import com.flurry.sdk.C1706dj;
import com.flurry.sdk.C1706dj.C1707a;
import com.flurry.sdk.C1706dj.C1707a.C1708a;
import com.flurry.sdk.C1706dj.C1709b;
import com.flurry.sdk.C1713dl;
import com.flurry.sdk.C1714dm;
import com.flurry.sdk.C1734dz;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import okhttp3.Request;
import okhttp3.Response;

public class FlurryPerformance {
    public static int ALL = (COLD_START | SCREEN_TIME);
    public static int COLD_START = 1;
    public static int NONE = 0;
    public static int SCREEN_TIME = 2;

    public static class HttpEventListener extends C1707a {

        public static class Factory extends C1708a {
            public Factory(String str) {
                super(str);
            }

            public void setId(String str) {
                super.setId(str);
            }
        }

        public HttpEventListener(String str) {
            super(str);
        }

        public void setId(String str) {
            super.setId(str);
        }
    }

    public static class HttpInterceptor extends C1709b {
        public HttpInterceptor(String str) {
            super(str);
        }

        public void setId(String str) {
            super.setId(str);
        }
    }

    public static class HttpLogger {

        /* renamed from: a */
        private long f306a = System.nanoTime();

        public void logEvent(String str, Request request, Response response) {
            double nanoTime = (double) (System.nanoTime() - this.f306a);
            Double.isNaN(nanoTime);
            String str2 = str;
            C1706dj.m824a(str2, request.url().toString(), response.code(), response.request().url().toString(), (long) (nanoTime / 1000000.0d));
        }

        public void logEvent(String str, String str2, int i, String str3) {
            double nanoTime = (double) (System.nanoTime() - this.f306a);
            Double.isNaN(nanoTime);
            C1706dj.m824a(str, str2, i, str3, (long) (nanoTime / 1000000.0d));
        }
    }

    public static class ResourceLogger {

        /* renamed from: a */
        private C1714dm f307a = new C1714dm();

        public void logEvent(String str) {
            C1714dm dmVar = this.f307a;
            double nanoTime = (double) (System.nanoTime() - dmVar.f963a);
            Double.isNaN(nanoTime);
            long j = (long) (nanoTime / 1000000.0d);
            HashMap hashMap = new HashMap();
            hashMap.put("fl.id", str);
            hashMap.put("fl.resource.time", Long.toString(j));
            Runtime runtime = Runtime.getRuntime();
            long freeMemory = (runtime.totalMemory() - runtime.freeMemory()) - dmVar.f964b;
            if (freeMemory < 0) {
                freeMemory = 0;
            }
            hashMap.put("fl.resource.runtime.memory", Long.toString(freeMemory));
            Context a = C1576b.m502a();
            if (a != null) {
                MemoryInfo a2 = C1713dl.m834a(a);
                long j2 = (a2.totalMem - a2.availMem) - dmVar.f965c;
                if (j2 < 0) {
                    j2 = 0;
                }
                hashMap.put("fl.resource.system.memory", Long.toString(j2));
            }
            C1685cy.m766c("ResourceLogging", "Logging parameters: ".concat(String.valueOf(hashMap)));
            FlurryAgent.logEvent("Flurry.ResourceLog", (Map<String, String>) hashMap);
        }
    }

    public static void reportFullyDrawn() {
        if (!C1734dz.m872a(16)) {
            C1685cy.m762b("FlurryPerformance", String.format(Locale.getDefault(), "Device SDK Version older than %d", new Object[]{Integer.valueOf(16)}));
            return;
        }
        C1703di a = C1703di.m811a();
        if (a.f937a && !a.f939c) {
            a.f939c = true;
            a.mo16409a(C1576b.m502a(), "onReportFullyDrawn", "fl.fully.drawn.time", "fl.fully.drawn.runtime.memory", "fl.fully.drawn.system.memory");
            if (a.f938b) {
                a.mo16410b();
            }
        }
    }
}
