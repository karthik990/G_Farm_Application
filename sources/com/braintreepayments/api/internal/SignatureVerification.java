package com.braintreepayments.api.internal;

public class SignatureVerification {
    static boolean sEnableSignatureVerification = true;

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0073 A[SYNTHETIC, Splitter:B:38:0x0073] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x007a A[SYNTHETIC, Splitter:B:46:0x007a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isSignatureValid(android.content.Context r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, int r12) {
        /*
            boolean r0 = sEnableSignatureVerification
            r1 = 1
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            android.content.pm.PackageManager r8 = r8.getPackageManager()
            r0 = 64
            r2 = 0
            android.content.pm.PackageInfo r8 = r8.getPackageInfo(r9, r0)     // Catch:{ NameNotFoundException -> 0x007f }
            android.content.pm.Signature[] r8 = r8.signatures     // Catch:{ NameNotFoundException -> 0x007f }
            r9 = 0
            int r0 = r8.length
            if (r0 == 0) goto L_0x0019
            r0 = 1
            goto L_0x001a
        L_0x0019:
            r0 = 0
        L_0x001a:
            int r3 = r8.length
            r4 = r9
            r9 = 0
        L_0x001d:
            if (r9 >= r3) goto L_0x007e
            r5 = r8[r9]
            java.io.ByteArrayInputStream r6 = new java.io.ByteArrayInputStream     // Catch:{ CertificateException -> 0x0077, all -> 0x006f }
            byte[] r5 = r5.toByteArray()     // Catch:{ CertificateException -> 0x0077, all -> 0x006f }
            r6.<init>(r5)     // Catch:{ CertificateException -> 0x0077, all -> 0x006f }
            java.lang.String r4 = "X509"
            java.security.cert.CertificateFactory r4 = java.security.cert.CertificateFactory.getInstance(r4)     // Catch:{ CertificateException -> 0x0078, all -> 0x006d }
            java.security.cert.Certificate r4 = r4.generateCertificate(r6)     // Catch:{ CertificateException -> 0x0078, all -> 0x006d }
            java.security.cert.X509Certificate r4 = (java.security.cert.X509Certificate) r4     // Catch:{ CertificateException -> 0x0078, all -> 0x006d }
            javax.security.auth.x500.X500Principal r5 = r4.getSubjectX500Principal()     // Catch:{ CertificateException -> 0x0078, all -> 0x006d }
            java.lang.String r5 = r5.getName()     // Catch:{ CertificateException -> 0x0078, all -> 0x006d }
            javax.security.auth.x500.X500Principal r7 = r4.getIssuerX500Principal()     // Catch:{ CertificateException -> 0x0078, all -> 0x006d }
            java.lang.String r7 = r7.getName()     // Catch:{ CertificateException -> 0x0078, all -> 0x006d }
            java.security.PublicKey r4 = r4.getPublicKey()     // Catch:{ CertificateException -> 0x0078, all -> 0x006d }
            int r4 = r4.hashCode()     // Catch:{ CertificateException -> 0x0078, all -> 0x006d }
            boolean r5 = r10.equals(r5)     // Catch:{ CertificateException -> 0x0078, all -> 0x006d }
            if (r5 == 0) goto L_0x005e
            boolean r5 = r11.equals(r7)     // Catch:{ CertificateException -> 0x0078, all -> 0x006d }
            if (r5 == 0) goto L_0x005e
            if (r12 != r4) goto L_0x005e
            r4 = 1
            goto L_0x005f
        L_0x005e:
            r4 = 0
        L_0x005f:
            r0 = r0 & r4
            if (r0 != 0) goto L_0x0066
            r6.close()     // Catch:{ IOException -> 0x0065 }
        L_0x0065:
            return r2
        L_0x0066:
            r6.close()     // Catch:{ IOException -> 0x0069 }
        L_0x0069:
            int r9 = r9 + 1
            r4 = r6
            goto L_0x001d
        L_0x006d:
            r8 = move-exception
            goto L_0x0071
        L_0x006f:
            r8 = move-exception
            r6 = r4
        L_0x0071:
            if (r6 == 0) goto L_0x0076
            r6.close()     // Catch:{ IOException -> 0x0076 }
        L_0x0076:
            throw r8
        L_0x0077:
            r6 = r4
        L_0x0078:
            if (r6 == 0) goto L_0x007d
            r6.close()     // Catch:{ IOException -> 0x007d }
        L_0x007d:
            return r2
        L_0x007e:
            return r0
        L_0x007f:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.braintreepayments.api.internal.SignatureVerification.isSignatureValid(android.content.Context, java.lang.String, java.lang.String, java.lang.String, int):boolean");
    }
}
