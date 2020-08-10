package p043io.netty.handler.ssl.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.ssl.util.SelfSignedCertificate */
public final class SelfSignedCertificate {
    private static final Date DEFAULT_NOT_AFTER = new Date(SystemPropertyUtil.getLong("io.netty.selfSignedCertificate.defaultNotAfter", 253402300799000L));
    private static final Date DEFAULT_NOT_BEFORE = new Date(SystemPropertyUtil.getLong("io.netty.selfSignedCertificate.defaultNotBefore", System.currentTimeMillis() - 31536000000L));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(SelfSignedCertificate.class);
    private final X509Certificate cert;
    private final File certificate;
    private final PrivateKey key;
    private final File privateKey;

    public SelfSignedCertificate() throws CertificateException {
        this(DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
    }

    public SelfSignedCertificate(Date date, Date date2) throws CertificateException {
        this("example.com", date, date2);
    }

    public SelfSignedCertificate(String str) throws CertificateException {
        this(str, DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
    }

    public SelfSignedCertificate(String str, Date date, Date date2) throws CertificateException {
        this(str, ThreadLocalInsecureRandom.current(), 1024, date, date2);
    }

    public SelfSignedCertificate(String str, SecureRandom secureRandom, int i) throws CertificateException {
        this(str, secureRandom, i, DEFAULT_NOT_BEFORE, DEFAULT_NOT_AFTER);
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0083 A[SYNTHETIC, Splitter:B:29:0x0083] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SelfSignedCertificate(java.lang.String r6, java.security.SecureRandom r7, int r8, java.util.Date r9, java.util.Date r10) throws java.security.cert.CertificateException {
        /*
            r5 = this;
            java.lang.String r0 = "Failed to close a file: "
            r5.<init>()
            java.lang.String r1 = "RSA"
            java.security.KeyPairGenerator r1 = java.security.KeyPairGenerator.getInstance(r1)     // Catch:{ NoSuchAlgorithmException -> 0x00af }
            r1.initialize(r8, r7)     // Catch:{ NoSuchAlgorithmException -> 0x00af }
            java.security.KeyPair r8 = r1.generateKeyPair()     // Catch:{ NoSuchAlgorithmException -> 0x00af }
            java.lang.String[] r6 = p043io.netty.handler.ssl.util.OpenJdkSelfSignedCertGenerator.generate(r6, r8, r7, r9, r10)     // Catch:{ all -> 0x0017 }
            goto L_0x0023
        L_0x0017:
            r1 = move-exception
            io.netty.util.internal.logging.InternalLogger r2 = logger
            java.lang.String r3 = "Failed to generate a self-signed X.509 certificate using sun.security.x509:"
            r2.debug(r3, r1)
            java.lang.String[] r6 = p043io.netty.handler.ssl.util.BouncyCastleSelfSignedCertGenerator.generate(r6, r8, r7, r9, r10)     // Catch:{ all -> 0x009f }
        L_0x0023:
            java.io.File r7 = new java.io.File
            r9 = 0
            r9 = r6[r9]
            r7.<init>(r9)
            r5.certificate = r7
            java.io.File r7 = new java.io.File
            r9 = 1
            r6 = r6[r9]
            r7.<init>(r6)
            r5.privateKey = r7
            java.security.PrivateKey r6 = r8.getPrivate()
            r5.key = r6
            r6 = 0
            java.io.FileInputStream r7 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0076, all -> 0x0071 }
            java.io.File r8 = r5.certificate     // Catch:{ Exception -> 0x0076, all -> 0x0071 }
            r7.<init>(r8)     // Catch:{ Exception -> 0x0076, all -> 0x0071 }
            java.lang.String r6 = "X509"
            java.security.cert.CertificateFactory r6 = java.security.cert.CertificateFactory.getInstance(r6)     // Catch:{ Exception -> 0x006f }
            java.security.cert.Certificate r6 = r6.generateCertificate(r7)     // Catch:{ Exception -> 0x006f }
            java.security.cert.X509Certificate r6 = (java.security.cert.X509Certificate) r6     // Catch:{ Exception -> 0x006f }
            r5.cert = r6     // Catch:{ Exception -> 0x006f }
            r7.close()     // Catch:{ IOException -> 0x0057 }
            goto L_0x006e
        L_0x0057:
            r6 = move-exception
            io.netty.util.internal.logging.InternalLogger r7 = logger
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r0)
            java.io.File r9 = r5.certificate
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r7.warn(r8, r6)
        L_0x006e:
            return
        L_0x006f:
            r6 = move-exception
            goto L_0x007a
        L_0x0071:
            r7 = move-exception
            r4 = r7
            r7 = r6
            r6 = r4
            goto L_0x0081
        L_0x0076:
            r7 = move-exception
            r4 = r7
            r7 = r6
            r6 = r4
        L_0x007a:
            java.security.cert.CertificateEncodingException r8 = new java.security.cert.CertificateEncodingException     // Catch:{ all -> 0x0080 }
            r8.<init>(r6)     // Catch:{ all -> 0x0080 }
            throw r8     // Catch:{ all -> 0x0080 }
        L_0x0080:
            r6 = move-exception
        L_0x0081:
            if (r7 == 0) goto L_0x009e
            r7.close()     // Catch:{ IOException -> 0x0087 }
            goto L_0x009e
        L_0x0087:
            r7 = move-exception
            io.netty.util.internal.logging.InternalLogger r8 = logger
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r0)
            java.io.File r10 = r5.certificate
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.warn(r9, r7)
        L_0x009e:
            throw r6
        L_0x009f:
            r6 = move-exception
            io.netty.util.internal.logging.InternalLogger r7 = logger
            java.lang.String r8 = "Failed to generate a self-signed X.509 certificate using Bouncy Castle:"
            r7.debug(r8, r6)
            java.security.cert.CertificateException r6 = new java.security.cert.CertificateException
            java.lang.String r7 = "No provider succeeded to generate a self-signed certificate. See debug log for the root cause."
            r6.<init>(r7)
            throw r6
        L_0x00af:
            r6 = move-exception
            java.lang.Error r7 = new java.lang.Error
            r7.<init>(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.util.SelfSignedCertificate.<init>(java.lang.String, java.security.SecureRandom, int, java.util.Date, java.util.Date):void");
    }

    public File certificate() {
        return this.certificate;
    }

    public File privateKey() {
        return this.privateKey;
    }

    public X509Certificate cert() {
        return this.cert;
    }

    public PrivateKey key() {
        return this.key;
    }

    public void delete() {
        safeDelete(this.certificate);
        safeDelete(this.privateKey);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x00d5, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00d9, code lost:
        throw r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00e7, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00eb, code lost:
        throw r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.String[] newSelfSignedCertificate(java.lang.String r6, java.security.PrivateKey r7, java.security.cert.X509Certificate r8) throws java.io.IOException, java.security.cert.CertificateEncodingException {
        /*
            byte[] r7 = r7.getEncoded()
            io.netty.buffer.ByteBuf r7 = p043io.netty.buffer.Unpooled.wrappedBuffer(r7)
            r0 = 1
            io.netty.buffer.ByteBuf r1 = p043io.netty.handler.codec.base64.Base64.encode(r7, r0)     // Catch:{ all -> 0x00ec }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e7 }
            r2.<init>()     // Catch:{ all -> 0x00e7 }
            java.lang.String r3 = "-----BEGIN PRIVATE KEY-----\n"
            r2.append(r3)     // Catch:{ all -> 0x00e7 }
            java.nio.charset.Charset r3 = p043io.netty.util.CharsetUtil.US_ASCII     // Catch:{ all -> 0x00e7 }
            java.lang.String r3 = r1.toString(r3)     // Catch:{ all -> 0x00e7 }
            r2.append(r3)     // Catch:{ all -> 0x00e7 }
            java.lang.String r3 = "\n-----END PRIVATE KEY-----\n"
            r2.append(r3)     // Catch:{ all -> 0x00e7 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00e7 }
            r1.release()     // Catch:{ all -> 0x00ec }
            r7.release()
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r1 = "keyutil_"
            r7.append(r1)
            r7.append(r6)
            r3 = 95
            r7.append(r3)
            java.lang.String r7 = r7.toString()
            java.lang.String r4 = ".key"
            java.io.File r7 = java.io.File.createTempFile(r7, r4)
            r7.deleteOnExit()
            java.io.FileOutputStream r4 = new java.io.FileOutputStream
            r4.<init>(r7)
            java.nio.charset.Charset r5 = p043io.netty.util.CharsetUtil.US_ASCII     // Catch:{ all -> 0x00df }
            byte[] r2 = r2.getBytes(r5)     // Catch:{ all -> 0x00df }
            r4.write(r2)     // Catch:{ all -> 0x00df }
            r4.close()     // Catch:{ all -> 0x00df }
            byte[] r8 = r8.getEncoded()
            io.netty.buffer.ByteBuf r8 = p043io.netty.buffer.Unpooled.wrappedBuffer(r8)
            io.netty.buffer.ByteBuf r2 = p043io.netty.handler.codec.base64.Base64.encode(r8, r0)     // Catch:{ all -> 0x00da }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d5 }
            r4.<init>()     // Catch:{ all -> 0x00d5 }
            java.lang.String r5 = "-----BEGIN CERTIFICATE-----\n"
            r4.append(r5)     // Catch:{ all -> 0x00d5 }
            java.nio.charset.Charset r5 = p043io.netty.util.CharsetUtil.US_ASCII     // Catch:{ all -> 0x00d5 }
            java.lang.String r5 = r2.toString(r5)     // Catch:{ all -> 0x00d5 }
            r4.append(r5)     // Catch:{ all -> 0x00d5 }
            java.lang.String r5 = "\n-----END CERTIFICATE-----\n"
            r4.append(r5)     // Catch:{ all -> 0x00d5 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00d5 }
            r2.release()     // Catch:{ all -> 0x00da }
            r8.release()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r1)
            r8.append(r6)
            r8.append(r3)
            java.lang.String r6 = r8.toString()
            java.lang.String r8 = ".crt"
            java.io.File r6 = java.io.File.createTempFile(r6, r8)
            r6.deleteOnExit()
            java.io.FileOutputStream r8 = new java.io.FileOutputStream
            r8.<init>(r6)
            java.nio.charset.Charset r1 = p043io.netty.util.CharsetUtil.US_ASCII     // Catch:{ all -> 0x00ca }
            byte[] r1 = r4.getBytes(r1)     // Catch:{ all -> 0x00ca }
            r8.write(r1)     // Catch:{ all -> 0x00ca }
            r8.close()     // Catch:{ all -> 0x00ca }
            r8 = 2
            java.lang.String[] r8 = new java.lang.String[r8]
            r1 = 0
            java.lang.String r6 = r6.getPath()
            r8[r1] = r6
            java.lang.String r6 = r7.getPath()
            r8[r0] = r6
            return r8
        L_0x00ca:
            r0 = move-exception
            safeClose(r6, r8)
            safeDelete(r6)
            safeDelete(r7)
            throw r0
        L_0x00d5:
            r6 = move-exception
            r2.release()     // Catch:{ all -> 0x00da }
            throw r6     // Catch:{ all -> 0x00da }
        L_0x00da:
            r6 = move-exception
            r8.release()
            throw r6
        L_0x00df:
            r6 = move-exception
            safeClose(r7, r4)
            safeDelete(r7)
            throw r6
        L_0x00e7:
            r6 = move-exception
            r1.release()     // Catch:{ all -> 0x00ec }
            throw r6     // Catch:{ all -> 0x00ec }
        L_0x00ec:
            r6 = move-exception
            r7.release()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.util.SelfSignedCertificate.newSelfSignedCertificate(java.lang.String, java.security.PrivateKey, java.security.cert.X509Certificate):java.lang.String[]");
    }

    private static void safeDelete(File file) {
        if (!file.delete()) {
            InternalLogger internalLogger = logger;
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to delete a file: ");
            sb.append(file);
            internalLogger.warn(sb.toString());
        }
    }

    private static void safeClose(File file, OutputStream outputStream) {
        try {
            outputStream.close();
        } catch (IOException e) {
            InternalLogger internalLogger = logger;
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to close a file: ");
            sb.append(file);
            internalLogger.warn(sb.toString(), (Throwable) e);
        }
    }
}
