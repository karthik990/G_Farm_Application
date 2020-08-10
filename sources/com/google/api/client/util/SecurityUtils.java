package com.google.api.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public final class SecurityUtils {
    public static KeyStore getDefaultKeyStore() throws KeyStoreException {
        return KeyStore.getInstance(KeyStore.getDefaultType());
    }

    public static KeyStore getJavaKeyStore() throws KeyStoreException {
        return KeyStore.getInstance("JKS");
    }

    public static KeyStore getPkcs12KeyStore() throws KeyStoreException {
        return KeyStore.getInstance("PKCS12");
    }

    public static void loadKeyStore(KeyStore keyStore, InputStream inputStream, String str) throws IOException, GeneralSecurityException {
        try {
            keyStore.load(inputStream, str.toCharArray());
        } finally {
            inputStream.close();
        }
    }

    public static PrivateKey getPrivateKey(KeyStore keyStore, String str, String str2) throws GeneralSecurityException {
        return (PrivateKey) keyStore.getKey(str, str2.toCharArray());
    }

    public static PrivateKey loadPrivateKeyFromKeyStore(KeyStore keyStore, InputStream inputStream, String str, String str2, String str3) throws IOException, GeneralSecurityException {
        loadKeyStore(keyStore, inputStream, str);
        return getPrivateKey(keyStore, str2, str3);
    }

    public static KeyFactory getRsaKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance("RSA");
    }

    public static Signature getSha1WithRsaSignatureAlgorithm() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA1withRSA");
    }

    public static Signature getSha256WithRsaSignatureAlgorithm() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA256withRSA");
    }

    public static byte[] sign(Signature signature, PrivateKey privateKey, byte[] bArr) throws InvalidKeyException, SignatureException {
        signature.initSign(privateKey);
        signature.update(bArr);
        return signature.sign();
    }

    public static boolean verify(Signature signature, PublicKey publicKey, byte[] bArr, byte[] bArr2) throws InvalidKeyException, SignatureException {
        signature.initVerify(publicKey);
        signature.update(bArr2);
        try {
            return signature.verify(bArr);
        } catch (SignatureException unused) {
            return false;
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.security.cert.X509Certificate verify(java.security.Signature r7, javax.net.ssl.X509TrustManager r8, java.util.List<java.lang.String> r9, byte[] r10, byte[] r11) throws java.security.InvalidKeyException, java.security.SignatureException {
        /*
            r0 = 0
            java.security.cert.CertificateFactory r1 = getX509CertificateFactory()     // Catch:{ CertificateException -> 0x004c }
            int r2 = r9.size()
            java.security.cert.X509Certificate[] r2 = new java.security.cert.X509Certificate[r2]
            java.util.Iterator r9 = r9.iterator()
            r3 = 0
            r4 = 0
        L_0x0011:
            boolean r5 = r9.hasNext()
            if (r5 == 0) goto L_0x0038
            java.lang.Object r5 = r9.next()
            java.lang.String r5 = (java.lang.String) r5
            byte[] r5 = com.google.api.client.util.Base64.decodeBase64(r5)
            java.io.ByteArrayInputStream r6 = new java.io.ByteArrayInputStream
            r6.<init>(r5)
            java.security.cert.Certificate r5 = r1.generateCertificate(r6)     // Catch:{ CertificateException -> 0x0037 }
            boolean r6 = r5 instanceof java.security.cert.X509Certificate     // Catch:{ CertificateException -> 0x0037 }
            if (r6 != 0) goto L_0x002f
            return r0
        L_0x002f:
            int r6 = r4 + 1
            java.security.cert.X509Certificate r5 = (java.security.cert.X509Certificate) r5     // Catch:{ CertificateException -> 0x0037 }
            r2[r4] = r5     // Catch:{ CertificateException -> 0x0037 }
            r4 = r6
            goto L_0x0011
        L_0x0037:
            return r0
        L_0x0038:
            java.lang.String r9 = "RSA"
            r8.checkServerTrusted(r2, r9)     // Catch:{  }
            r8 = r2[r3]
            java.security.PublicKey r8 = r8.getPublicKey()
            boolean r7 = verify(r7, r8, r10, r11)
            if (r7 == 0) goto L_0x004c
            r7 = r2[r3]
            return r7
        L_0x004c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.util.SecurityUtils.verify(java.security.Signature, javax.net.ssl.X509TrustManager, java.util.List, byte[], byte[]):java.security.cert.X509Certificate");
    }

    public static CertificateFactory getX509CertificateFactory() throws CertificateException {
        return CertificateFactory.getInstance("X.509");
    }

    public static void loadKeyStoreFromCertificates(KeyStore keyStore, CertificateFactory certificateFactory, InputStream inputStream) throws GeneralSecurityException {
        int i = 0;
        for (Certificate certificateEntry : certificateFactory.generateCertificates(inputStream)) {
            keyStore.setCertificateEntry(String.valueOf(i), certificateEntry);
            i++;
        }
    }

    private SecurityUtils() {
    }
}
