package com.google.android.exoplayer2.drm;

import android.text.TextUtils;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.drm.ExoMediaDrm.KeyRequest;
import com.google.android.exoplayer2.drm.ExoMediaDrm.ProvisionRequest;
import com.google.android.exoplayer2.upstream.HttpDataSource.Factory;
import com.google.android.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class HttpMediaDrmCallback implements MediaDrmCallback {
    private static final int MAX_MANUAL_REDIRECTS = 5;
    private final Factory dataSourceFactory;
    private final String defaultLicenseUrl;
    private final boolean forceDefaultLicenseUrl;
    private final Map<String, String> keyRequestProperties;

    public HttpMediaDrmCallback(String str, Factory factory) {
        this(str, false, factory);
    }

    public HttpMediaDrmCallback(String str, boolean z, Factory factory) {
        this.dataSourceFactory = factory;
        this.defaultLicenseUrl = str;
        this.forceDefaultLicenseUrl = z;
        this.keyRequestProperties = new HashMap();
    }

    public void setKeyRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.put(str, str2);
        }
    }

    public void clearKeyRequestProperty(String str) {
        Assertions.checkNotNull(str);
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.remove(str);
        }
    }

    public void clearAllKeyRequestProperties() {
        synchronized (this.keyRequestProperties) {
            this.keyRequestProperties.clear();
        }
    }

    public byte[] executeProvisionRequest(UUID uuid, ProvisionRequest provisionRequest) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(provisionRequest.getDefaultUrl());
        sb.append("&signedRequest=");
        sb.append(Util.fromUtf8Bytes(provisionRequest.getData()));
        return executePost(this.dataSourceFactory, sb.toString(), null, null);
    }

    public byte[] executeKeyRequest(UUID uuid, KeyRequest keyRequest) throws Exception {
        String licenseServerUrl = keyRequest.getLicenseServerUrl();
        if (this.forceDefaultLicenseUrl || TextUtils.isEmpty(licenseServerUrl)) {
            licenseServerUrl = this.defaultLicenseUrl;
        }
        HashMap hashMap = new HashMap();
        String str = C1996C.PLAYREADY_UUID.equals(uuid) ? "text/xml" : C1996C.CLEARKEY_UUID.equals(uuid) ? "application/json" : "application/octet-stream";
        hashMap.put("Content-Type", str);
        if (C1996C.PLAYREADY_UUID.equals(uuid)) {
            hashMap.put("SOAPAction", "http://schemas.microsoft.com/DRM/2007/03/protocols/AcquireLicense");
        }
        synchronized (this.keyRequestProperties) {
            hashMap.putAll(this.keyRequestProperties);
        }
        return executePost(this.dataSourceFactory, licenseServerUrl, keyRequest.getData(), hashMap);
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x006e A[Catch:{ InvalidResponseCodeException -> 0x0053, all -> 0x0051 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0076 A[LOOP:1: B:7:0x002d->B:30:0x0076, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x007f A[EDGE_INSN: B:31:0x007f->B:32:? ?: BREAK  , SYNTHETIC, Splitter:B:31:0x007f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] executePost(com.google.android.exoplayer2.upstream.HttpDataSource.Factory r17, java.lang.String r18, byte[] r19, java.util.Map<java.lang.String, java.lang.String> r20) throws java.io.IOException {
        /*
            com.google.android.exoplayer2.upstream.HttpDataSource r1 = r17.createDataSource()
            if (r20 == 0) goto L_0x002a
            java.util.Set r0 = r20.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x000e:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x002a
            java.lang.Object r2 = r0.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getKey()
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Object r2 = r2.getValue()
            java.lang.String r2 = (java.lang.String) r2
            r1.setRequestProperty(r3, r2)
            goto L_0x000e
        L_0x002a:
            r0 = r18
            r3 = 0
        L_0x002d:
            com.google.android.exoplayer2.upstream.DataSpec r15 = new com.google.android.exoplayer2.upstream.DataSpec
            android.net.Uri r5 = android.net.Uri.parse(r0)
            r6 = 2
            r8 = 0
            r10 = 0
            r12 = -1
            r14 = 0
            r0 = 1
            r4 = r15
            r7 = r19
            r2 = r15
            r15 = r0
            r4.<init>(r5, r6, r7, r8, r10, r12, r14, r15)
            com.google.android.exoplayer2.upstream.DataSourceInputStream r4 = new com.google.android.exoplayer2.upstream.DataSourceInputStream
            r4.<init>(r1, r2)
            byte[] r0 = com.google.android.exoplayer2.util.Util.toByteArray(r4)     // Catch:{ InvalidResponseCodeException -> 0x0053 }
            com.google.android.exoplayer2.util.Util.closeQuietly(r4)
            return r0
        L_0x0051:
            r0 = move-exception
            goto L_0x0080
        L_0x0053:
            r0 = move-exception
            r2 = r0
            int r0 = r2.responseCode     // Catch:{ all -> 0x0051 }
            r5 = 307(0x133, float:4.3E-43)
            if (r0 == r5) goto L_0x0064
            int r0 = r2.responseCode     // Catch:{ all -> 0x0051 }
            r5 = 308(0x134, float:4.32E-43)
            if (r0 != r5) goto L_0x0062
            goto L_0x0064
        L_0x0062:
            r0 = r3
            goto L_0x006b
        L_0x0064:
            int r0 = r3 + 1
            r5 = 5
            if (r3 >= r5) goto L_0x006b
            r3 = 1
            goto L_0x006c
        L_0x006b:
            r3 = 0
        L_0x006c:
            if (r3 == 0) goto L_0x0073
            java.lang.String r3 = getRedirectUrl(r2)     // Catch:{ all -> 0x0051 }
            goto L_0x0074
        L_0x0073:
            r3 = 0
        L_0x0074:
            if (r3 == 0) goto L_0x007f
            com.google.android.exoplayer2.util.Util.closeQuietly(r4)
            r16 = r3
            r3 = r0
            r0 = r16
            goto L_0x002d
        L_0x007f:
            throw r2     // Catch:{ all -> 0x0051 }
        L_0x0080:
            com.google.android.exoplayer2.util.Util.closeQuietly(r4)
            goto L_0x0085
        L_0x0084:
            throw r0
        L_0x0085:
            goto L_0x0084
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.HttpMediaDrmCallback.executePost(com.google.android.exoplayer2.upstream.HttpDataSource$Factory, java.lang.String, byte[], java.util.Map):byte[]");
    }

    private static String getRedirectUrl(InvalidResponseCodeException invalidResponseCodeException) {
        Map<String, List<String>> map = invalidResponseCodeException.headerFields;
        if (map != null) {
            List list = (List) map.get("Location");
            if (list != null && !list.isEmpty()) {
                return (String) list.get(0);
            }
        }
        return null;
    }
}
