package com.startapp.android.publish.adsCommon;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.browser.customtabs.CustomTabsIntent;
import com.startapp.android.publish.adsCommon.C4925Ad.AdType;
import com.startapp.android.publish.adsCommon.Utils.C4946i;
import com.startapp.android.publish.adsCommon.activities.OverlayActivity;
import com.startapp.android.publish.adsCommon.p080d.C5002b;
import com.startapp.android.publish.adsCommon.p082f.C5015d;
import com.startapp.android.publish.adsCommon.p082f.C5017f;
import com.startapp.android.publish.adsCommon.p091k.C5052a;
import com.startapp.android.publish.common.metaData.MetaData;
import com.startapp.android.publish.common.model.AdDetails;
import com.startapp.android.publish.common.model.AdPreferences;
import com.startapp.android.publish.common.model.AdPreferences.Placement;
import com.startapp.common.C5160b;
import com.startapp.common.C5186e;
import com.startapp.common.C5188g;
import com.startapp.common.C5188g.C5192a;
import com.startapp.common.Constants;
import com.startapp.common.p092a.C5138a;
import com.startapp.common.p092a.C5139b;
import com.startapp.common.p092a.C5146c;
import com.startapp.common.p092a.C5155g;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.startapp.android.publish.adsCommon.c */
/* compiled from: StartAppSDK */
public class C4988c {

    /* renamed from: a */
    private static ProgressDialog f3160a;

    /* renamed from: com.startapp.android.publish.adsCommon.c$a */
    /* compiled from: StartAppSDK */
    private static class C4992a extends WebViewClient {

        /* renamed from: a */
        protected String f3168a = "";

        /* renamed from: b */
        protected String f3169b;

        /* renamed from: c */
        protected boolean f3170c = false;

        /* renamed from: d */
        protected boolean f3171d = false;

        /* renamed from: e */
        protected long f3172e;

        /* renamed from: f */
        protected boolean f3173f = true;

        /* renamed from: g */
        protected Boolean f3174g = null;

        /* renamed from: h */
        protected String f3175h;

        /* renamed from: i */
        protected ProgressDialog f3176i;

        /* renamed from: j */
        protected Runnable f3177j;

        /* renamed from: k */
        protected boolean f3178k = false;

        /* renamed from: l */
        protected boolean f3179l = false;

        /* renamed from: m */
        private long f3180m;

        /* renamed from: n */
        private LinkedHashMap<String, Float> f3181n = new LinkedHashMap<>();

        /* renamed from: o */
        private long f3182o;

        /* renamed from: p */
        private Timer f3183p;

        public C4992a(long j, long j2, boolean z, Boolean bool, ProgressDialog progressDialog, String str, String str2, String str3, Runnable runnable) {
            this.f3172e = j;
            this.f3180m = j2;
            this.f3173f = z;
            this.f3174g = bool;
            this.f3176i = progressDialog;
            this.f3168a = str;
            this.f3175h = str2;
            this.f3169b = str3;
            this.f3177j = runnable;
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            StringBuilder sb = new StringBuilder();
            sb.append("MyWebViewClientSmartRedirect::onPageStarted - [");
            sb.append(str);
            sb.append("]");
            C5155g.m3805a(2, sb.toString());
            super.onPageStarted(webView, str, bitmap);
            if (!this.f3171d) {
                this.f3182o = System.currentTimeMillis();
                this.f3181n.put(str, Float.valueOf(-1.0f));
                m3138a(webView.getContext());
                this.f3171d = true;
            }
            this.f3179l = false;
            m3141b();
        }

        /* JADX WARNING: Removed duplicated region for block: B:29:0x00e9 A[Catch:{ Exception -> 0x013c }] */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x00f6 A[Catch:{ Exception -> 0x013c }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean shouldOverrideUrlLoading(android.webkit.WebView r9, java.lang.String r10) {
            /*
                r8 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "MyWebViewClientSmartRedirect::shouldOverrideUrlLoading - ["
                r0.append(r1)
                r0.append(r10)
                java.lang.String r1 = "]"
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                r1 = 2
                com.startapp.common.p092a.C5155g.m3805a(r1, r0)
                r0 = 1
                long r1 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x013c }
                long r3 = r8.f3182o     // Catch:{ Exception -> 0x013c }
                long r3 = r1 - r3
                float r3 = (float) r3     // Catch:{ Exception -> 0x013c }
                r4 = 1148846080(0x447a0000, float:1000.0)
                float r3 = r3 / r4
                java.lang.Float r3 = java.lang.Float.valueOf(r3)     // Catch:{ Exception -> 0x013c }
                r8.f3182o = r1     // Catch:{ Exception -> 0x013c }
                java.util.LinkedHashMap<java.lang.String, java.lang.Float> r1 = r8.f3181n     // Catch:{ Exception -> 0x013c }
                java.lang.String r2 = r8.f3168a     // Catch:{ Exception -> 0x013c }
                r1.put(r2, r3)     // Catch:{ Exception -> 0x013c }
                java.util.LinkedHashMap<java.lang.String, java.lang.Float> r1 = r8.f3181n     // Catch:{ Exception -> 0x013c }
                r2 = -1082130432(0xffffffffbf800000, float:-1.0)
                java.lang.Float r2 = java.lang.Float.valueOf(r2)     // Catch:{ Exception -> 0x013c }
                r1.put(r10, r2)     // Catch:{ Exception -> 0x013c }
                r8.f3168a = r10     // Catch:{ Exception -> 0x013c }
                java.lang.String r1 = r10.toLowerCase()     // Catch:{ Exception -> 0x013c }
                boolean r2 = com.startapp.android.publish.adsCommon.C4988c.m3126b(r1)     // Catch:{ Exception -> 0x013c }
                r3 = 0
                if (r2 != 0) goto L_0x0053
                boolean r2 = com.startapp.android.publish.adsCommon.C4988c.m3129c(r1)     // Catch:{ Exception -> 0x013c }
                if (r2 != 0) goto L_0x0053
                return r3
            L_0x0053:
                boolean r2 = r8.f3178k     // Catch:{ Exception -> 0x013c }
                if (r2 != 0) goto L_0x013c
                r8.f3170c = r0     // Catch:{ Exception -> 0x013c }
                android.content.Context r2 = r9.getContext()     // Catch:{ Exception -> 0x013c }
                com.startapp.android.publish.adsCommon.C4988c.m3099a(r2)     // Catch:{ Exception -> 0x013c }
                r8.m3141b()     // Catch:{ Exception -> 0x013c }
                android.content.Context r2 = r9.getContext()     // Catch:{ Exception -> 0x013c }
                boolean r1 = com.startapp.android.publish.adsCommon.C4988c.m3129c(r1)     // Catch:{ Exception -> 0x013c }
                if (r1 == 0) goto L_0x0071
                java.lang.String r10 = r9.getUrl()     // Catch:{ Exception -> 0x013c }
            L_0x0071:
                com.startapp.android.publish.adsCommon.C4988c.m3127c(r2, r10)     // Catch:{ Exception -> 0x013c }
                java.lang.String r10 = r8.f3175h     // Catch:{ Exception -> 0x013c }
                if (r10 == 0) goto L_0x00c0
                java.lang.String r10 = r8.f3175h     // Catch:{ Exception -> 0x013c }
                java.lang.String r1 = ""
                boolean r10 = r10.equals(r1)     // Catch:{ Exception -> 0x013c }
                if (r10 != 0) goto L_0x00c0
                java.lang.String r10 = r8.f3168a     // Catch:{ Exception -> 0x013c }
                java.lang.String r10 = r10.toLowerCase()     // Catch:{ Exception -> 0x013c }
                java.lang.String r1 = r8.f3175h     // Catch:{ Exception -> 0x013c }
                java.lang.String r1 = r1.toLowerCase()     // Catch:{ Exception -> 0x013c }
                boolean r10 = r10.contains(r1)     // Catch:{ Exception -> 0x013c }
                if (r10 != 0) goto L_0x00c0
                android.content.Context r9 = r9.getContext()     // Catch:{ Exception -> 0x013c }
                com.startapp.android.publish.adsCommon.f.d r10 = com.startapp.android.publish.adsCommon.p082f.C5015d.WRONG_PACKAGE_REACHED     // Catch:{ Exception -> 0x013c }
                java.lang.String r1 = "Wrong package name reached"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x013c }
                r2.<init>()     // Catch:{ Exception -> 0x013c }
                java.lang.String r3 = "Expected: "
                r2.append(r3)     // Catch:{ Exception -> 0x013c }
                java.lang.String r3 = r8.f3175h     // Catch:{ Exception -> 0x013c }
                r2.append(r3)     // Catch:{ Exception -> 0x013c }
                java.lang.String r3 = " Link: "
                r2.append(r3)     // Catch:{ Exception -> 0x013c }
                java.lang.String r3 = r8.f3168a     // Catch:{ Exception -> 0x013c }
                r2.append(r3)     // Catch:{ Exception -> 0x013c }
                java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x013c }
                java.lang.String r3 = r8.f3169b     // Catch:{ Exception -> 0x013c }
                com.startapp.android.publish.adsCommon.p082f.C5017f.m3256a(r9, r10, r1, r2, r3)     // Catch:{ Exception -> 0x013c }
                goto L_0x0133
            L_0x00c0:
                com.startapp.android.publish.common.metaData.MetaData r10 = com.startapp.android.publish.common.metaData.MetaData.getInstance()     // Catch:{ Exception -> 0x013c }
                com.startapp.android.publish.adsCommon.f.a r10 = r10.getAnalyticsConfig()     // Catch:{ Exception -> 0x013c }
                boolean r10 = r10.mo62270g()     // Catch:{ Exception -> 0x013c }
                java.lang.String r1 = "firstSucceededSmartRedirect"
                if (r10 == 0) goto L_0x00e4
                android.content.Context r10 = r9.getContext()     // Catch:{ Exception -> 0x013c }
                java.lang.Boolean r2 = java.lang.Boolean.valueOf(r0)     // Catch:{ Exception -> 0x013c }
                java.lang.Boolean r10 = com.startapp.android.publish.adsCommon.C5051k.m3335a(r10, r1, r2)     // Catch:{ Exception -> 0x013c }
                boolean r10 = r10.booleanValue()     // Catch:{ Exception -> 0x013c }
                if (r10 == 0) goto L_0x00e4
                r10 = 1
                goto L_0x00e5
            L_0x00e4:
                r10 = 0
            L_0x00e5:
                java.lang.Boolean r2 = r8.f3174g     // Catch:{ Exception -> 0x013c }
                if (r2 != 0) goto L_0x00f6
                com.startapp.android.publish.common.metaData.MetaData r2 = com.startapp.android.publish.common.metaData.MetaData.getInstance()     // Catch:{ Exception -> 0x013c }
                com.startapp.android.publish.adsCommon.f.a r2 = r2.getAnalyticsConfig()     // Catch:{ Exception -> 0x013c }
                float r2 = r2.mo62269f()     // Catch:{ Exception -> 0x013c }
                goto L_0x0102
            L_0x00f6:
                java.lang.Boolean r2 = r8.f3174g     // Catch:{ Exception -> 0x013c }
                boolean r2 = r2.booleanValue()     // Catch:{ Exception -> 0x013c }
                if (r2 == 0) goto L_0x0101
                r2 = 1120403456(0x42c80000, float:100.0)
                goto L_0x0102
            L_0x0101:
                r2 = 0
            L_0x0102:
                if (r10 != 0) goto L_0x0111
                double r4 = java.lang.Math.random()     // Catch:{ Exception -> 0x013c }
                r6 = 4636737291354636288(0x4059000000000000, double:100.0)
                double r4 = r4 * r6
                double r6 = (double) r2     // Catch:{ Exception -> 0x013c }
                int r10 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
                if (r10 >= 0) goto L_0x0133
            L_0x0111:
                com.startapp.android.publish.adsCommon.f.e r10 = new com.startapp.android.publish.adsCommon.f.e     // Catch:{ Exception -> 0x013c }
                com.startapp.android.publish.adsCommon.f.d r2 = com.startapp.android.publish.adsCommon.p082f.C5015d.SUCCESS_SMART_REDIRECT_HOP_INFO     // Catch:{ Exception -> 0x013c }
                r10.<init>(r2)     // Catch:{ Exception -> 0x013c }
                org.json.JSONArray r2 = r8.mo62201a()     // Catch:{ Exception -> 0x013c }
                r10.mo62285a(r2)     // Catch:{ Exception -> 0x013c }
                android.content.Context r2 = r9.getContext()     // Catch:{ Exception -> 0x013c }
                java.lang.String r4 = r8.f3169b     // Catch:{ Exception -> 0x013c }
                com.startapp.android.publish.adsCommon.p082f.C5017f.m3258a(r2, r10, r4)     // Catch:{ Exception -> 0x013c }
                android.content.Context r9 = r9.getContext()     // Catch:{ Exception -> 0x013c }
                java.lang.Boolean r10 = java.lang.Boolean.valueOf(r3)     // Catch:{ Exception -> 0x013c }
                com.startapp.android.publish.adsCommon.C5051k.m3342b(r9, r1, r10)     // Catch:{ Exception -> 0x013c }
            L_0x0133:
                java.lang.Runnable r9 = r8.f3177j     // Catch:{ Exception -> 0x013c }
                if (r9 == 0) goto L_0x013c
                java.lang.Runnable r9 = r8.f3177j     // Catch:{ Exception -> 0x013c }
                r9.run()     // Catch:{ Exception -> 0x013c }
            L_0x013c:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.adsCommon.C4988c.C4992a.shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String):boolean");
        }

        public void onPageFinished(WebView webView, String str) {
            StringBuilder sb = new StringBuilder();
            sb.append("MyWebViewClientSmartRedirect::onPageFinished - [");
            sb.append(str);
            sb.append("]");
            C5155g.m3805a(2, sb.toString());
            if (!this.f3170c && !this.f3178k && this.f3168a.equals(str) && str != null && !C4988c.m3126b(str) && (str.startsWith("http://") || str.startsWith("https://"))) {
                this.f3179l = true;
                try {
                    m3140a(str);
                } catch (Exception unused) {
                }
                m3142b(webView.getContext());
            }
            super.onPageFinished(webView, str);
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            StringBuilder sb = new StringBuilder();
            sb.append("MyWebViewClientSmartRedirect::onReceivedError - [");
            sb.append(str);
            sb.append("], [");
            sb.append(str2);
            sb.append("]");
            C5155g.m3805a(2, sb.toString());
            m3141b();
            if (str2 != null && !C4988c.m3126b(str2) && C4988c.m3132d(str2)) {
                C5017f.m3256a(webView.getContext(), C5015d.FAILED_SMART_REDIRECT, Integer.toString(i), str2, this.f3169b);
            }
            super.onReceivedError(webView, i, str, str2);
        }

        /* renamed from: a */
        private void m3138a(final Context context) {
            C5188g.m3934a((Runnable) new Runnable() {
                /* JADX WARNING: Failed to process nested try/catch */
                /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0030 */
                /* JADX WARNING: Removed duplicated region for block: B:17:0x0070 A[Catch:{ Exception -> 0x0078 }] */
                /* JADX WARNING: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r5 = this;
                        com.startapp.android.publish.adsCommon.c$a r0 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this
                        boolean r0 = r0.f3170c
                        if (r0 != 0) goto L_0x008a
                        com.startapp.android.publish.adsCommon.f.e r0 = new com.startapp.android.publish.adsCommon.f.e     // Catch:{ Exception -> 0x0030 }
                        com.startapp.android.publish.adsCommon.f.d r1 = com.startapp.android.publish.adsCommon.p082f.C5015d.FAILED_SMART_REDIRECT_HOP_INFO     // Catch:{ Exception -> 0x0030 }
                        r0.<init>(r1)     // Catch:{ Exception -> 0x0030 }
                        com.startapp.android.publish.adsCommon.c$a r1 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this     // Catch:{ Exception -> 0x0030 }
                        org.json.JSONArray r1 = r1.mo62201a()     // Catch:{ Exception -> 0x0030 }
                        r0.mo62285a(r1)     // Catch:{ Exception -> 0x0030 }
                        com.startapp.android.publish.adsCommon.c$a r1 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this     // Catch:{ Exception -> 0x0030 }
                        boolean r1 = r1.f3179l     // Catch:{ Exception -> 0x0030 }
                        if (r1 == 0) goto L_0x0022
                        java.lang.String r1 = "Page Finished"
                        r0.mo62286d(r1)     // Catch:{ Exception -> 0x0030 }
                        goto L_0x0027
                    L_0x0022:
                        java.lang.String r1 = "Timeout"
                        r0.mo62286d(r1)     // Catch:{ Exception -> 0x0030 }
                    L_0x0027:
                        android.content.Context r1 = r4     // Catch:{ Exception -> 0x0030 }
                        com.startapp.android.publish.adsCommon.c$a r2 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this     // Catch:{ Exception -> 0x0030 }
                        java.lang.String r2 = r2.f3169b     // Catch:{ Exception -> 0x0030 }
                        com.startapp.android.publish.adsCommon.p082f.C5017f.m3258a(r1, r0, r2)     // Catch:{ Exception -> 0x0030 }
                    L_0x0030:
                        com.startapp.android.publish.adsCommon.c$a r0 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this     // Catch:{ Exception -> 0x0078 }
                        r1 = 1
                        r0.f3178k = r1     // Catch:{ Exception -> 0x0078 }
                        android.content.Context r0 = r4     // Catch:{ Exception -> 0x0078 }
                        com.startapp.android.publish.adsCommon.C4988c.m3099a(r0)     // Catch:{ Exception -> 0x0078 }
                        com.startapp.android.publish.adsCommon.c$a r0 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this     // Catch:{ Exception -> 0x0078 }
                        r0.m3141b()     // Catch:{ Exception -> 0x0078 }
                        com.startapp.android.publish.adsCommon.c$a r0 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this     // Catch:{ Exception -> 0x0078 }
                        boolean r0 = r0.f3173f     // Catch:{ Exception -> 0x0078 }
                        if (r0 == 0) goto L_0x005d
                        com.startapp.android.publish.common.metaData.MetaData r0 = com.startapp.android.publish.common.metaData.MetaData.getInstance()     // Catch:{ Exception -> 0x0078 }
                        boolean r0 = r0.isInAppBrowser()     // Catch:{ Exception -> 0x0078 }
                        if (r0 == 0) goto L_0x005d
                        android.content.Context r0 = r4     // Catch:{ Exception -> 0x0078 }
                        com.startapp.android.publish.adsCommon.c$a r1 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this     // Catch:{ Exception -> 0x0078 }
                        java.lang.String r1 = r1.f3168a     // Catch:{ Exception -> 0x0078 }
                        com.startapp.android.publish.adsCommon.c$a r2 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this     // Catch:{ Exception -> 0x0078 }
                        java.lang.String r2 = r2.f3169b     // Catch:{ Exception -> 0x0078 }
                        com.startapp.android.publish.adsCommon.C4988c.m3125b(r0, r1, r2)     // Catch:{ Exception -> 0x0078 }
                        goto L_0x006a
                    L_0x005d:
                        android.content.Context r0 = r4     // Catch:{ Exception -> 0x0078 }
                        com.startapp.android.publish.adsCommon.c$a r1 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this     // Catch:{ Exception -> 0x0078 }
                        java.lang.String r1 = r1.f3168a     // Catch:{ Exception -> 0x0078 }
                        com.startapp.android.publish.adsCommon.c$a r2 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this     // Catch:{ Exception -> 0x0078 }
                        java.lang.String r2 = r2.f3169b     // Catch:{ Exception -> 0x0078 }
                        com.startapp.android.publish.adsCommon.C4988c.m3103a(r0, r1, r2)     // Catch:{ Exception -> 0x0078 }
                    L_0x006a:
                        com.startapp.android.publish.adsCommon.c$a r0 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this     // Catch:{ Exception -> 0x0078 }
                        java.lang.Runnable r0 = r0.f3177j     // Catch:{ Exception -> 0x0078 }
                        if (r0 == 0) goto L_0x008a
                        com.startapp.android.publish.adsCommon.c$a r0 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this     // Catch:{ Exception -> 0x0078 }
                        java.lang.Runnable r0 = r0.f3177j     // Catch:{ Exception -> 0x0078 }
                        r0.run()     // Catch:{ Exception -> 0x0078 }
                        goto L_0x008a
                    L_0x0078:
                        r0 = move-exception
                        android.content.Context r1 = r4
                        com.startapp.android.publish.adsCommon.f.d r2 = com.startapp.android.publish.adsCommon.p082f.C5015d.EXCEPTION
                        java.lang.String r0 = r0.getMessage()
                        com.startapp.android.publish.adsCommon.c$a r3 = com.startapp.android.publish.adsCommon.C4988c.C4992a.this
                        java.lang.String r3 = r3.f3169b
                        java.lang.String r4 = "AdsCommonUtils.startTimeout - error after time elapsed"
                        com.startapp.android.publish.adsCommon.p082f.C5017f.m3256a(r1, r2, r4, r0, r3)
                    L_0x008a:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.adsCommon.C4988c.C4992a.C49931.run():void");
                }
            }, this.f3172e);
        }

        /* renamed from: b */
        private void m3142b(final Context context) {
            m3141b();
            try {
                this.f3183p = new Timer();
                this.f3183p.schedule(new TimerTask() {
                    public void run() {
                        if (!C4992a.this.f3178k && !C4992a.this.f3170c) {
                            try {
                                C4992a.this.f3170c = true;
                                C4988c.m3099a(context);
                                if (!C4992a.this.f3173f || !MetaData.getInstance().isInAppBrowser()) {
                                    C4988c.m3103a(context, C4992a.this.f3168a, C4992a.this.f3169b);
                                } else {
                                    C4988c.m3125b(context, C4992a.this.f3168a, C4992a.this.f3169b);
                                }
                                if (C4992a.this.f3177j != null) {
                                    C4992a.this.f3177j.run();
                                }
                            } catch (Exception e) {
                                C5017f.m3256a(context, C5015d.EXCEPTION, "AdsCommonUtils.startLoadedTimer - error after time elapsed", e.getMessage(), C4992a.this.f3169b);
                            }
                        }
                    }
                }, this.f3180m);
            } catch (Exception e) {
                this.f3183p = null;
                C5155g.m3808a("AdsCommonUtils", 6, "startLoadedTimer", e);
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: b */
        public void m3141b() {
            Timer timer = this.f3183p;
            if (timer != null) {
                timer.cancel();
                this.f3183p = null;
            }
        }

        /* renamed from: a */
        private void m3140a(String str) {
            if (((Float) this.f3181n.get(str)).floatValue() < 0.0f) {
                this.f3181n.put(str, Float.valueOf(((float) (System.currentTimeMillis() - this.f3182o)) / 1000.0f));
            }
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public JSONArray mo62201a() {
            JSONArray jSONArray = new JSONArray();
            for (String str : this.f3181n.keySet()) {
                JSONObject jSONObject = new JSONObject();
                try {
                    m3140a(str);
                    jSONObject.put("time", ((Float) this.f3181n.get(str)).toString());
                    jSONObject.put("url", str);
                    jSONArray.put(jSONObject);
                } catch (JSONException unused) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("error puting url into json [");
                    sb.append(str);
                    sb.append("]");
                    C5155g.m3805a(6, sb.toString());
                }
            }
            return jSONArray;
        }
    }

    /* renamed from: a */
    public static AdType m3094a(AdPreferences adPreferences, String str) {
        Object a = C4946i.m2904a(adPreferences.getClass(), str, (Object) adPreferences);
        if (a == null || !(a instanceof AdType)) {
            return null;
        }
        return (AdType) a;
    }

    /* renamed from: a */
    public static void m3113a(AdPreferences adPreferences, String str, AdType adType) {
        C4946i.m2920a(adPreferences.getClass(), str, (Object) adPreferences, (Object) adType);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.String, code=java.lang.Object, for r4v0, types: [java.lang.Object, java.lang.String] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String m3096a(android.content.Context r3, java.lang.Object r4) {
        /*
            android.content.res.Resources r0 = r3.getResources()     // Catch:{ NotFoundException -> 0x000f }
            android.content.pm.ApplicationInfo r1 = r3.getApplicationInfo()     // Catch:{ NotFoundException -> 0x000f }
            int r1 = r1.labelRes     // Catch:{ NotFoundException -> 0x000f }
            java.lang.String r3 = r0.getString(r1)     // Catch:{ NotFoundException -> 0x000f }
            return r3
        L_0x000f:
            android.content.pm.PackageManager r0 = r3.getPackageManager()
            r1 = 0
            android.content.pm.ApplicationInfo r3 = r3.getApplicationInfo()     // Catch:{ NameNotFoundException -> 0x0020 }
            java.lang.String r3 = r3.packageName     // Catch:{ NameNotFoundException -> 0x0020 }
            r2 = 0
            android.content.pm.ApplicationInfo r1 = r0.getApplicationInfo(r3, r2)     // Catch:{ NameNotFoundException -> 0x0020 }
            goto L_0x0021
        L_0x0020:
        L_0x0021:
            if (r1 == 0) goto L_0x0027
            java.lang.CharSequence r4 = r0.getApplicationLabel(r1)
        L_0x0027:
            java.lang.String r4 = (java.lang.String) r4
            java.lang.String r4 = (java.lang.String) r4
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.startapp.android.publish.adsCommon.C4988c.m3096a(android.content.Context, java.lang.String):java.lang.String");
    }

    /* renamed from: a */
    public static boolean m3116a(Activity activity) {
        boolean z = activity.getTheme().obtainStyledAttributes(new int[]{16843277}).getBoolean(0, false);
        if ((activity.getWindow().getAttributes().flags & 1024) != 0) {
            return true;
        }
        return z;
    }

    /* renamed from: a */
    public static int m3093a(String str) {
        String[] split = str.split("&");
        return Integer.parseInt(split[split.length - 1].split("=")[1]);
    }

    /* renamed from: a */
    public static void m3112a(Context context, String[] strArr, String str, String str2) {
        m3111a(context, strArr, str, 0, str2);
    }

    /* renamed from: a */
    public static void m3111a(Context context, String[] strArr, String str, int i, String str2) {
        C5002b nonImpressionReason = new C5002b(str).setOffset(i).setNonImpressionReason(str2);
        String str3 = "";
        if (strArr == null || strArr.length == 0) {
            C5017f.m3256a(context, C5015d.NON_IMPRESSION_NO_DPARAM, str2, nonImpressionReason.getProfileId(), str3);
            return;
        }
        for (String str4 : strArr) {
            if (str4 != null && !str4.equalsIgnoreCase(str3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Sending Impression: [");
                sb.append(str4);
                sb.append("]");
                C5155g.m3805a(3, sb.toString());
                m3102a(context, str4, nonImpressionReason, false);
            }
        }
    }

    /* renamed from: a */
    public static void m3101a(Context context, String str, C5002b bVar) {
        if (str != null && !str.equalsIgnoreCase("")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Sending Impression: [");
            sb.append(str);
            sb.append("]");
            C5155g.m3805a(3, sb.toString());
            if (bVar != null) {
                bVar.setLocation(context);
            }
            m3102a(context, str, bVar, true);
        }
    }

    /* renamed from: a */
    public static void m3110a(Context context, String[] strArr, C5002b bVar) {
        if (strArr != null) {
            for (String a : strArr) {
                m3101a(context, a, bVar);
            }
        }
    }

    /* renamed from: a */
    public static List<String> m3098a(List<String> list, String str, String str2) {
        String str3;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < list.size()) {
            int i2 = i + 5;
            List subList = list.subList(i, Math.min(i2, list.size()));
            StringBuilder sb = new StringBuilder();
            sb.append(AdsConstants.f3022f);
            sb.append("?");
            sb.append(TextUtils.join("&", subList));
            sb.append("&isShown=");
            sb.append(str);
            if (str2 != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("&appPresence=");
                sb2.append(str2);
                str3 = sb2.toString();
            } else {
                str3 = "";
            }
            sb.append(str3);
            arrayList.add(sb.toString());
            i = i2;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("newUrlList size = ");
        sb3.append(arrayList.size());
        C5155g.m3805a(3, sb3.toString());
        return arrayList;
    }

    /* renamed from: a */
    public static final void m3105a(Context context, String str, String str2, C5002b bVar, boolean z, boolean z2) {
        if (!TextUtils.isEmpty(str2)) {
            m3124b(context, str2, bVar);
        }
        C5059m.m3378a().mo62379b();
        String str3 = null;
        String str4 = "]";
        String str5 = "Cannot start activity to handle url: [";
        if (!z2) {
            try {
                str3 = m3097a(str, str2);
            } catch (Exception e) {
                C5015d dVar = C5015d.FAILED_EXTRACTING_DPARAMS;
                StringBuilder sb = new StringBuilder();
                sb.append("Util.clickWithoutSmartRedirect(): Couldn't extract dparams with clickUrl ");
                sb.append(str);
                sb.append(" and tacking click url ");
                sb.append(str2);
                C5017f.m3256a(context, dVar, sb.toString(), e.getMessage(), null);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str5);
                sb2.append(str);
                sb2.append(str4);
                C5155g.m3805a(6, sb2.toString());
            }
        }
        String str6 = "InAppBrowser";
        try {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(m3136f(str2) ? C5138a.m3711a(str3) : "");
            String sb4 = sb3.toString();
            if (!MetaData.getInstance().isInAppBrowser() || !z) {
                String str7 = "externalBrowser";
                if (!TextUtils.isEmpty(str2) || !m3135f(context)) {
                    m3127c(context, sb4);
                    return;
                }
                m3122b(context);
                m3127c(context, m3137g(sb4));
                C5155g.m3805a(6, "forceExternal - click without - External");
                StringBuilder sb5 = new StringBuilder();
                sb5.append("forceExternal - click without - trackingClickUrl : ");
                sb5.append(str2);
                C5155g.m3805a(6, sb5.toString());
                return;
            }
            m3125b(context, sb4, str3);
        } catch (Exception e2) {
            C5015d dVar2 = C5015d.EXCEPTION;
            StringBuilder sb6 = new StringBuilder();
            sb6.append("Util.clickWithoutSmartRedirect - Couldn't start activity for ");
            sb6.append(str6);
            C5017f.m3256a(context, dVar2, sb6.toString(), e2.getMessage(), str3);
            StringBuilder sb7 = new StringBuilder();
            sb7.append(str5);
            sb7.append(str);
            sb7.append(str4);
            C5155g.m3805a(6, sb7.toString());
        }
    }

    /* renamed from: f */
    private static boolean m3136f(String str) {
        return C4983b.m3032a().mo62150D() || TextUtils.isEmpty(str);
    }

    /* renamed from: g */
    private static String m3137g(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("&cki=1");
        return sb.toString();
    }

    /* renamed from: b */
    private static void m3122b(Context context) {
        C5051k.m3345b(context, "shared_prefs_CookieFeatureTS", Long.valueOf(System.currentTimeMillis()));
        StringBuilder sb = new StringBuilder();
        sb.append("forceExternal - write to sp - TS : ");
        sb.append(SimpleDateFormat.getDateInstance().format(new Date()));
        C5155g.m3805a(6, sb.toString());
    }

    /* renamed from: a */
    public static final void m3107a(Context context, String str, String str2, String str3, C5002b bVar, long j, long j2, boolean z, Boolean bool, boolean z2) {
        m3108a(context, str, str2, str3, bVar, j, j2, z, bool, z2, null);
    }

    /* renamed from: a */
    public static final void m3108a(Context context, String str, String str2, String str3, C5002b bVar, long j, long j2, boolean z, Boolean bool, boolean z2, Runnable runnable) {
        Context context2 = context;
        String str4 = str;
        String str5 = str2;
        if (C4983b.m3032a().mo62149C()) {
            C5059m.m3378a().mo62379b();
            String str6 = null;
            if (!z2) {
                try {
                    str6 = m3097a(str, str2);
                } catch (Exception e) {
                    Exception exc = e;
                    C5015d dVar = C5015d.FAILED_EXTRACTING_DPARAMS;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Util.clickWithSmartRedirect(): Couldn't extract dparams with clickUrl ");
                    sb.append(str);
                    sb.append(" and tacking click url ");
                    sb.append(str2);
                    C5017f.m3256a(context, dVar, sb.toString(), exc.getMessage(), null);
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Cannot start activity to handle url: [");
                    sb2.append(str);
                    sb2.append("]");
                    C5155g.m3805a(6, sb2.toString());
                }
            }
            String str7 = "";
            if (str5 != null && !str2.equals(str7)) {
                m3124b(context, str2, bVar);
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            if (m3136f(str2)) {
                str7 = C5138a.m3711a(str6);
            }
            sb3.append(str7);
            m3106a(context, sb3.toString(), str3, str6, j, j2, z, bool, runnable);
            return;
        }
        C5002b bVar2 = bVar;
        m3105a(context, str, str2, bVar, z, z2);
    }

    /* renamed from: b */
    public static void m3124b(Context context, String str, C5002b bVar) {
        m3102a(context, str, bVar, true);
    }

    /* renamed from: a */
    public static void m3102a(final Context context, final String str, final C5002b bVar, final boolean z) {
        if (!str.equals("")) {
            C5188g.m3935a(C5192a.HIGH, (Runnable) new Runnable() {
                public void run() {
                    try {
                        if (z) {
                            Context context = context;
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append(C5138a.m3711a(C4988c.m3133e(str)));
                            sb.append(bVar.getQueryString());
                            C5052a.m3351a(context, sb.toString(), null);
                            return;
                        }
                        Context context2 = context;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(str);
                        sb2.append(bVar.getQueryString());
                        C5052a.m3351a(context2, sb2.toString(), null);
                    } catch (C5186e e) {
                        C5017f.m3256a(context, C5015d.EXCEPTION, "Util.sendTrackingMessage - Error sending tracking message", e.getMessage(), C4988c.m3133e(str));
                        C5155g.m3806a(6, "Error sending tracking message", (Throwable) e);
                    }
                }
            });
        }
    }

    /* renamed from: b */
    public static void m3123b(final Context context, final String str) {
        C5188g.m3935a(C5192a.HIGH, (Runnable) new Runnable() {
            public void run() {
                try {
                    C5052a.m3351a(context, str, null);
                } catch (C5186e e) {
                    C5017f.m3256a(context, C5015d.EXCEPTION, "Util.sendTrackingMessage - Error sending tracking message", e.getMessage(), "");
                    C5155g.m3806a(6, "Error sending tracking message", (Throwable) e);
                }
            }
        });
    }

    /* renamed from: a */
    private static final void m3106a(Context context, String str, String str2, String str3, long j, long j2, boolean z, Boolean bool, Runnable runnable) {
        Context context2 = context;
        String str4 = str;
        String str5 = str2;
        String str6 = str3;
        C5160b.m3831a(context).mo62880a(new Intent("com.startapp.android.OnClickCallback"));
        if (m3126b(str)) {
            if (str5 != null && !str5.equals("") && !str.toLowerCase().contains(str2.toLowerCase())) {
                C5015d dVar = C5015d.WRONG_PACKAGE_REACHED;
                StringBuilder sb = new StringBuilder();
                sb.append("Expected: ");
                sb.append(str5);
                sb.append(" Link: ");
                sb.append(str4);
                C5017f.m3256a(context2, dVar, "Wrong package name reached", sb.toString(), str6);
            }
            m3103a(context2, str4, str6);
            if (runnable != null) {
                runnable.run();
            }
            return;
        }
        if (context2 instanceof Activity) {
            C4946i.m2911a((Activity) context2, true);
        }
        try {
            final WebView webView = new WebView(context2);
            if (f3160a == null) {
                if (VERSION.SDK_INT >= 22) {
                    f3160a = new ProgressDialog(context2, 16974545);
                } else {
                    f3160a = new ProgressDialog(context2);
                }
                f3160a.setTitle(null);
                f3160a.setMessage("Loading....");
                f3160a.setIndeterminate(false);
                f3160a.setCancelable(false);
                f3160a.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialogInterface) {
                        webView.stopLoading();
                    }
                });
                if ((context2 instanceof Activity) && !((Activity) context2).isFinishing()) {
                    f3160a.show();
                } else if (!(context2 instanceof Activity) && m3128c(context) && f3160a.getWindow() != null) {
                    if (VERSION.SDK_INT >= 26) {
                        f3160a.getWindow().setType(2038);
                    } else {
                        f3160a.getWindow().setType(2003);
                    }
                    f3160a.show();
                }
            }
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());
            C4992a aVar = r2;
            WebView webView2 = webView;
            C4992a aVar2 = new C4992a(j, j2, z, bool, f3160a, str, str2, str3, runnable);
            webView2.setWebViewClient(aVar);
            webView2.loadUrl(str4);
        } catch (Exception e) {
            Context context3 = context;
            C5017f.m3256a(context3, C5015d.EXCEPTION, "Util.smartRedirect - Webview failed", e.getMessage(), str6);
            m3103a(context3, str4, str6);
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    /* renamed from: c */
    private static boolean m3128c(Context context) {
        if (VERSION.SDK_INT >= 23) {
            return Settings.canDrawOverlays(context);
        }
        return C5146c.m3760a(context, "android.permission.SYSTEM_ALERT_WINDOW");
    }

    /* renamed from: b */
    public static boolean m3126b(String str) {
        return str.startsWith("market") || str.startsWith("http://play.google.com") || str.startsWith("https://play.google.com");
    }

    /* renamed from: c */
    public static boolean m3129c(String str) {
        return str.startsWith("intent://");
    }

    /* renamed from: d */
    public static boolean m3132d(String str) {
        return str != null && (str.startsWith("http://") || str.startsWith("https://"));
    }

    /* renamed from: a */
    public static final void m3099a(Context context) {
        if (context != null && (context instanceof Activity)) {
            C4946i.m2911a((Activity) context, false);
        }
        m3130d(context);
    }

    /* renamed from: d */
    private static void m3130d(Context context) {
        ProgressDialog progressDialog = f3160a;
        if (progressDialog != null) {
            synchronized (progressDialog) {
                if (f3160a != null && f3160a.isShowing()) {
                    try {
                        f3160a.cancel();
                    } catch (Exception e) {
                        C5155g.m3806a(6, "Error while cancelling progress", (Throwable) e);
                        C5017f.m3256a(context, C5015d.EXCEPTION, "AdsCommonUtils.cancelProgress - progress.cancel() failed", e.getMessage(), "");
                    }
                    f3160a = null;
                }
            }
        }
    }

    /* renamed from: c */
    public static void m3127c(Context context, String str) {
        m3103a(context, str, (String) null);
    }

    /* renamed from: a */
    public static void m3103a(Context context, String str, String str2) {
        m3109a(context, str, str2, m3132d(str));
    }

    /* renamed from: a */
    public static void m3109a(Context context, String str, String str2, boolean z) {
        if (context != null) {
            int i = 76021760;
            if (C4983b.m3032a().mo62153G() || !(context instanceof Activity)) {
                i = 344457216;
            }
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
            intent.addFlags(i);
            boolean a = m3117a(context, intent);
            if (!a) {
                try {
                    if (VERSION.SDK_INT >= 18 && MetaData.getInstance().getChromeCustomeTabsExternal() && m3134e(context)) {
                        m3131d(context, str);
                    }
                } catch (Exception unused) {
                    m3104a(context, str, str2, i);
                }
            }
            if (z && !a) {
                m3100a(context, intent, i);
            }
            context.startActivity(intent);
        }
    }

    /* renamed from: a */
    private static void m3100a(Context context, Intent intent, int i) {
        String[] strArr = {"com.android.chrome", "com.android.browser", "com.opera.mini.native", "org.mozilla.firefox", "com.opera.browser"};
        try {
            List queryIntentActivities = context.getPackageManager().queryIntentActivities(intent, i);
            if (queryIntentActivities != null && queryIntentActivities.size() > 1) {
                for (String str : strArr) {
                    if (C5146c.m3761a(context, str, 0)) {
                        intent.setPackage(str);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            C5017f.m3256a(context, C5015d.EXCEPTION, "AdsCommonUtils.chooseDefaultBrowser", e.getMessage(), "");
        }
    }

    /* renamed from: a */
    private static void m3104a(Context context, String str, String str2, int i) {
        try {
            Intent parseUri = Intent.parseUri(str, i);
            m3117a(context, parseUri);
            if (!(context instanceof Activity)) {
                parseUri.addFlags(268435456);
            }
            context.startActivity(parseUri);
        } catch (Exception e) {
            C5017f.m3256a(context, C5015d.EXCEPTION, "Util.openUrlExternally - Couldn't start activity", e.getMessage(), str2);
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot find activity to handle url: [");
            sb.append(str);
            sb.append("]");
            C5155g.m3805a(6, sb.toString());
        }
    }

    /* renamed from: b */
    public static void m3125b(Context context, String str, String str2) {
        String str3 = "Util.OpenAsInAppBrowser - Couldn't start activity";
        if (TextUtils.isEmpty(str)) {
            C5017f.m3256a(context, C5015d.EXCEPTION, str3, "Parameter clickUrl is null", str2);
            C5155g.m3805a(6, "Cannot start activity, because url is null");
        } else if (m3126b(str) || !C4946i.m2923a(256)) {
            m3103a(context, str, str2);
        } else {
            try {
                if (VERSION.SDK_INT >= 18 && MetaData.getInstance().getChromeCustomeTabsInternal() && m3134e(context)) {
                    m3131d(context, str);
                    return;
                }
            } catch (Exception e) {
                C5017f.m3256a(context, C5015d.EXCEPTION, "Util.OpenAsInAppBrowser - Couldn't openUrlChromeTabs", e.getMessage(), str2);
            }
            Intent intent = new Intent(context, OverlayActivity.class);
            if (VERSION.SDK_INT >= 21) {
                intent.addFlags(524288);
            }
            if (VERSION.SDK_INT >= 11) {
                intent.addFlags(32768);
            }
            if (!(context instanceof Activity)) {
                intent.addFlags(268435456);
            }
            intent.setData(Uri.parse(str));
            intent.putExtra("placement", Placement.INAPP_BROWSER.getIndex());
            intent.putExtra("activityShouldLockOrientation", false);
            try {
                context.startActivity(intent);
            } catch (Exception e2) {
                C5017f.m3256a(context, C5015d.EXCEPTION, str3, e2.getMessage(), str2);
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot find activity to handle url: [");
                sb.append(str);
                sb.append("]");
                C5155g.m3805a(6, sb.toString());
            }
        }
    }

    /* renamed from: d */
    private static void m3131d(Context context, String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        Bundle bundle = new Bundle();
        bundle.putBinder(CustomTabsIntent.EXTRA_SESSION, null);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /* renamed from: e */
    private static boolean m3134e(Context context) {
        return C5051k.m3335a(context, "chromeTabs", Boolean.valueOf(false)).booleanValue();
    }

    /* renamed from: a */
    public static void m3114a(String str, String str2, String str3, Context context, C5002b bVar) {
        m3102a(context, str3, bVar, true);
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
        if (str2 != null) {
            try {
                JSONObject jSONObject = new JSONObject(str2);
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    String valueOf = String.valueOf(keys.next());
                    launchIntentForPackage.putExtra(valueOf, String.valueOf(jSONObject.get(valueOf)));
                }
            } catch (JSONException e) {
                C5155g.m3806a(6, "Couldn't parse intent details json!", (Throwable) e);
            }
        }
        try {
            context.startActivity(launchIntentForPackage);
        } catch (Exception e2) {
            String str4 = "Util.handleCPEClick - Couldn't start activity";
            C5017f.m3256a(context, C5015d.EXCEPTION, str4, e2.getMessage(), m3097a(str3, (String) null));
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot find activity to handle url: [");
            sb.append(str3);
            sb.append("]");
            C5155g.m3805a(6, sb.toString());
        }
    }

    /* renamed from: e */
    public static String m3133e(String str) {
        return m3097a(str, (String) null);
    }

    /* renamed from: a */
    public static String m3097a(String str, String str2) {
        String str3 = "";
        if (str2 != null) {
            try {
                if (!str2.equals(str3)) {
                    str = str2;
                }
            } catch (Exception unused) {
                return str3;
            }
        }
        String[] split = str.split("[?&]d=");
        return split.length >= 2 ? split[1].split("[?&]")[0] : str3;
    }

    /* renamed from: a */
    public static boolean m3117a(Context context, Intent intent) {
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 0)) {
            if (resolveInfo.activityInfo.packageName.equalsIgnoreCase(Constants.f3532a)) {
                intent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    public static String m3095a() {
        StringBuilder sb = new StringBuilder();
        sb.append("&position=");
        sb.append(m3121b());
        return sb.toString();
    }

    /* renamed from: b */
    public static String m3121b() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        int i = 0;
        while (true) {
            String str = "interstitial";
            if (i >= 8) {
                return str;
            }
            if (stackTrace[i].getMethodName().compareTo("doHome") == 0) {
                return "home";
            }
            if (stackTrace[i].getMethodName().compareTo("onBackPressed") != 0) {
                i++;
            } else if (!C5059m.m3378a().mo62398f() && !C5059m.m3406p()) {
                return str;
            } else {
                C5059m.m3378a().mo62407m();
                return "back";
            }
        }
    }

    /* renamed from: a */
    public static String[] m3119a(C5021g gVar) {
        if (gVar instanceof C5003e) {
            return ((C5003e) gVar).mo62249l();
        }
        return gVar instanceof C5033h ? m3120a(((C5033h) gVar).mo62336d()) : new String[0];
    }

    /* renamed from: a */
    public static String[] m3120a(List<AdDetails> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (AdDetails trackingUrl : list) {
                arrayList.add(trackingUrl.getTrackingUrl());
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    /* renamed from: a */
    public static boolean m3118a(Context context, Placement placement) {
        StringBuilder sb = new StringBuilder();
        sb.append("forceExternal - check -placement is : ");
        sb.append(placement);
        C5155g.m3807a("AdsCommonUtils", 6, sb.toString());
        if (placement.equals(Placement.INAPP_SPLASH) || !C4983b.m3032a().mo62159M()) {
            return false;
        }
        return m3135f(context);
    }

    /* renamed from: f */
    private static boolean m3135f(Context context) {
        if (!C5139b.m3719a().mo62840a(context).mo62853b()) {
            if (m3115a(C5051k.m3338a(context, "shared_prefs_CookieFeatureTS", Long.valueOf(0)).longValue(), System.currentTimeMillis())) {
                C5155g.m3807a("AdsCommonUtils", 6, "forceExternal - check - true ");
                return true;
            }
        }
        return false;
    }

    /* renamed from: a */
    private static boolean m3115a(long j, long j2) {
        return j == 0 || j + (((long) C4983b.m3032a().mo62158L()) * 86400000) <= j2;
    }
}
