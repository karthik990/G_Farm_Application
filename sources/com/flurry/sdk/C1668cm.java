package com.flurry.sdk;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.util.HashMap;
import javax.net.ssl.HttpsURLConnection;

/* renamed from: com.flurry.sdk.cm */
public final class C1668cm extends C1671cp {

    /* renamed from: i */
    private static String f835i;

    /* renamed from: j */
    private HttpsURLConnection f836j;

    /* renamed from: k */
    private String f837k;

    /* renamed from: l */
    private boolean f838l;

    C1668cm(String str) {
        this.f840a = str;
        StringBuilder sb = new StringBuilder("Flurry-Config/1.0 (Android ");
        sb.append(VERSION.RELEASE);
        sb.append("/");
        sb.append(Build.ID);
        sb.append(")");
        f835i = sb.toString();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x011d  */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.io.InputStream mo16373a() throws java.io.IOException {
        /*
            r6 = this;
            java.net.URL r0 = new java.net.URL
            java.lang.String r1 = r6.f840a
            r0.<init>(r1)
            java.net.URLConnection r0 = r0.openConnection()
            javax.net.ssl.HttpsURLConnection r0 = (javax.net.ssl.HttpsURLConnection) r0
            r6.f836j = r0
            javax.net.ssl.HttpsURLConnection r0 = r6.f836j
            r1 = 10000(0x2710, float:1.4013E-41)
            r0.setReadTimeout(r1)
            javax.net.ssl.HttpsURLConnection r0 = r6.f836j
            r1 = 15000(0x3a98, float:2.102E-41)
            r0.setConnectTimeout(r1)
            javax.net.ssl.HttpsURLConnection r0 = r6.f836j
            java.lang.String r1 = "POST"
            r0.setRequestMethod(r1)
            javax.net.ssl.HttpsURLConnection r0 = r6.f836j
            java.lang.String r1 = f835i
            java.lang.String r2 = "User-Agent"
            r0.setRequestProperty(r2, r1)
            javax.net.ssl.HttpsURLConnection r0 = r6.f836j
            java.lang.String r1 = "Content-Type"
            java.lang.String r2 = "application/json"
            r0.setRequestProperty(r1, r2)
            javax.net.ssl.HttpsURLConnection r0 = r6.f836j
            r1 = 1
            r0.setDoInput(r1)
            javax.net.ssl.HttpsURLConnection r0 = r6.f836j
            r0.setDoOutput(r1)
            r0 = 1234(0x4d2, float:1.729E-42)
            android.net.TrafficStats.setThreadStatsTag(r0)
            javax.net.ssl.HttpsURLConnection r0 = r6.f836j
            r0.connect()
            javax.net.ssl.HttpsURLConnection r0 = r6.f836j
            com.flurry.sdk.C1701dg.m810a(r0)
            java.util.UUID r0 = java.util.UUID.randomUUID()
            java.lang.String r0 = r0.toString()
            java.util.Locale r1 = java.util.Locale.ENGLISH
            java.lang.String r0 = r0.toUpperCase(r1)
            r6.f842c = r0
            r0 = 0
            javax.net.ssl.HttpsURLConnection r1 = r6.f836j     // Catch:{ all -> 0x0113 }
            java.io.OutputStream r1 = r1.getOutputStream()     // Catch:{ all -> 0x0113 }
            java.io.BufferedWriter r2 = new java.io.BufferedWriter     // Catch:{ all -> 0x0111 }
            java.io.OutputStreamWriter r3 = new java.io.OutputStreamWriter     // Catch:{ all -> 0x0111 }
            java.lang.String r4 = "UTF-8"
            r3.<init>(r1, r4)     // Catch:{ all -> 0x0111 }
            r2.<init>(r3)     // Catch:{ all -> 0x0111 }
            java.lang.String r0 = r6.f842c     // Catch:{ all -> 0x010c }
            java.lang.String r0 = com.flurry.sdk.C1670co.m715a(r0)     // Catch:{ all -> 0x010c }
            r2.write(r0)     // Catch:{ all -> 0x010c }
            r2.close()
            if (r1 == 0) goto L_0x0084
            r1.close()
        L_0x0084:
            javax.net.ssl.HttpsURLConnection r0 = r6.f836j
            int r0 = r0.getResponseCode()
            r1 = 400(0x190, float:5.6E-43)
            if (r0 >= r1) goto L_0x00fc
            javax.net.ssl.HttpsURLConnection r1 = r6.f836j
            java.lang.String r2 = "Content-Signature"
            java.lang.String r1 = r1.getHeaderField(r2)
            r6.f837k = r1
            javax.net.ssl.HttpsURLConnection r1 = r6.f836j
            java.lang.String r2 = "ETag"
            java.lang.String r1 = r1.getHeaderField(r2)
            r6.f846g = r1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Content-Signature: "
            r1.<init>(r2)
            java.lang.String r2 = r6.f837k
            r1.append(r2)
            java.lang.String r2 = ", ETag: "
            r1.append(r2)
            java.lang.String r2 = r6.f846g
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "HttpTransport"
            com.flurry.sdk.C1685cy.m756a(r2, r1)
            r1 = 304(0x130, float:4.26E-43)
            if (r0 != r1) goto L_0x00f5
            java.lang.String r0 = r6.f842c
            boolean r0 = r6.mo16374a(r0)
            if (r0 == 0) goto L_0x00d7
            com.flurry.sdk.cc r0 = com.flurry.sdk.C1655cc.f796b
            r6.f841b = r0
            java.lang.String r0 = "Empty 304 payload; No Change."
            com.flurry.sdk.C1685cy.m756a(r2, r0)
            goto L_0x00f5
        L_0x00d7:
            com.flurry.sdk.cc r0 = new com.flurry.sdk.cc
            com.flurry.sdk.cc$a r1 = com.flurry.sdk.C1655cc.C1656a.AUTHENTICATE
            java.lang.String r3 = "GUID Signature Error."
            r0.<init>(r1, r3)
            r6.f841b = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Authentication error: "
            r0.<init>(r1)
            com.flurry.sdk.cc r1 = r6.f841b
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.flurry.sdk.C1685cy.m762b(r2, r0)
        L_0x00f5:
            javax.net.ssl.HttpsURLConnection r0 = r6.f836j
            java.io.InputStream r0 = r0.getInputStream()
            return r0
        L_0x00fc:
            java.io.IOException r1 = new java.io.IOException
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r2 = "Server response code is "
            java.lang.String r0 = r2.concat(r0)
            r1.<init>(r0)
            throw r1
        L_0x010c:
            r0 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x0116
        L_0x0111:
            r2 = move-exception
            goto L_0x0116
        L_0x0113:
            r1 = move-exception
            r2 = r1
            r1 = r0
        L_0x0116:
            if (r0 == 0) goto L_0x011b
            r0.close()
        L_0x011b:
            if (r1 == 0) goto L_0x0120
            r1.close()
        L_0x0120:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.C1668cm.mo16373a():java.io.InputStream");
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public final void mo16375b() {
        HttpsURLConnection httpsURLConnection = this.f836j;
        if (httpsURLConnection != null) {
            httpsURLConnection.disconnect();
        }
    }

    /* renamed from: c */
    public final boolean mo16376c() {
        return "https://cfg.flurry.com/sdk/v1/config".equals(this.f840a);
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final boolean mo16374a(String str) {
        boolean z;
        if (!m708b(this.f837k)) {
            return false;
        }
        if (this.f838l) {
            z = C1673cr.m736c(this.f844e, str, this.f845f);
        } else {
            z = C1673cr.m734b(this.f844e, str, this.f845f);
        }
        if (z) {
            return true;
        }
        C1685cy.m762b("HttpTransport", "Incorrect signature for response.");
        return false;
    }

    /* renamed from: b */
    private boolean m708b(String str) {
        String[] split;
        String str2 = "HttpTransport";
        if (TextUtils.isEmpty(str)) {
            C1685cy.m762b(str2, "Content-Signature is empty.");
            return false;
        }
        HashMap hashMap = new HashMap();
        for (String str3 : str.split(";")) {
            int indexOf = str3.indexOf("=");
            if (indexOf > 0) {
                hashMap.put(str3.substring(0, indexOf), str3.substring(indexOf + 1));
            }
        }
        this.f843d = (String) hashMap.get("keyid");
        if (TextUtils.isEmpty(this.f843d)) {
            C1685cy.m762b(str2, "Error to get keyid from Signature.");
            return false;
        }
        this.f844e = (String) C1674cs.f849a.get(this.f843d);
        StringBuilder sb = new StringBuilder("Signature keyid: ");
        sb.append(this.f843d);
        sb.append(", key: ");
        sb.append(this.f844e);
        C1685cy.m756a(str2, sb.toString());
        if (this.f844e == null) {
            C1685cy.m762b(str2, "Unknown keyid from Signature.");
            return false;
        }
        String str4 = "sha256ecdsa";
        this.f838l = hashMap.containsKey(str4);
        if (!this.f838l) {
            str4 = "sha256rsa";
        }
        this.f845f = (String) hashMap.get(str4);
        if (TextUtils.isEmpty(this.f845f)) {
            C1685cy.m762b(str2, "Error to get rsa from Signature.");
            return false;
        }
        StringBuilder sb2 = new StringBuilder("Signature rsa: ");
        sb2.append(this.f845f);
        C1685cy.m756a(str2, sb2.toString());
        return true;
    }
}
