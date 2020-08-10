package p043io.netty.handler.ssl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.buffer.ByteBufInputStream;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import p043io.netty.util.internal.EmptyArrays;

/* renamed from: io.netty.handler.ssl.SslContext */
public abstract class SslContext {
    static final CertificateFactory X509_CERT_FACTORY;
    private final boolean startTls;

    /* renamed from: io.netty.handler.ssl.SslContext$1 */
    static /* synthetic */ class C57331 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$SslProvider = new int[SslProvider.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                io.netty.handler.ssl.SslProvider[] r0 = p043io.netty.handler.ssl.SslProvider.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$netty$handler$ssl$SslProvider = r0
                int[] r0 = $SwitchMap$io$netty$handler$ssl$SslProvider     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.ssl.SslProvider r1 = p043io.netty.handler.ssl.SslProvider.JDK     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$netty$handler$ssl$SslProvider     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.ssl.SslProvider r1 = p043io.netty.handler.ssl.SslProvider.OPENSSL     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$io$netty$handler$ssl$SslProvider     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.handler.ssl.SslProvider r1 = p043io.netty.handler.ssl.SslProvider.OPENSSL_REFCNT     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.SslContext.C57331.<clinit>():void");
        }
    }

    public abstract ApplicationProtocolNegotiator applicationProtocolNegotiator();

    public abstract List<String> cipherSuites();

    public abstract boolean isClient();

    public abstract SSLEngine newEngine(ByteBufAllocator byteBufAllocator);

    public abstract SSLEngine newEngine(ByteBufAllocator byteBufAllocator, String str, int i);

    public abstract long sessionCacheSize();

    public abstract SSLSessionContext sessionContext();

    public abstract long sessionTimeout();

    static {
        try {
            X509_CERT_FACTORY = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            throw new IllegalStateException("unable to instance X.509 CertificateFactory", e);
        }
    }

    public static SslProvider defaultServerProvider() {
        return defaultProvider();
    }

    public static SslProvider defaultClientProvider() {
        return defaultProvider();
    }

    private static SslProvider defaultProvider() {
        if (OpenSsl.isAvailable()) {
            return SslProvider.OPENSSL;
        }
        return SslProvider.JDK;
    }

    @Deprecated
    public static SslContext newServerContext(File file, File file2) throws SSLException {
        return newServerContext(file, file2, (String) null);
    }

    @Deprecated
    public static SslContext newServerContext(File file, File file2, String str) throws SSLException {
        return newServerContext(null, file, file2, str);
    }

    @Deprecated
    public static SslContext newServerContext(File file, File file2, String str, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        return newServerContext((SslProvider) null, file, file2, str, iterable, iterable2, j, j2);
    }

    @Deprecated
    public static SslContext newServerContext(File file, File file2, String str, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        return newServerContext((SslProvider) null, file, file2, str, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2) throws SSLException {
        return newServerContext(sslProvider, file, file2, null);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2, String str) throws SSLException {
        return newServerContext(sslProvider, file, file2, str, null, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, (ApplicationProtocolConfig) null, 0, 0);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2, String str, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        return newServerContext(sslProvider, file, file2, str, iterable, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, toApplicationProtocolConfig(iterable2), j, j2);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2, String str, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        return newServerContext(sslProvider, null, trustManagerFactory, file, file2, str, null, iterable, IdentityCipherSuiteFilter.INSTANCE, toApplicationProtocolConfig(iterable2), j, j2);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2, String str, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        return newServerContext(sslProvider, null, null, file, file2, str, null, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        try {
            return newServerContextInternal(sslProvider, null, toX509Certificates(file), trustManagerFactory, toX509Certificates(file2), toPrivateKey(file3, str), str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2, ClientAuth.NONE, null, false, false);
        } catch (Exception e) {
            if (e instanceof SSLException) {
                throw ((SSLException) e);
            }
            throw new SSLException("failed to initialize the server-side SSL context", e);
        }
    }

    static SslContext newServerContextInternal(SslProvider sslProvider, Provider provider, X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2, ClientAuth clientAuth, String[] strArr, boolean z, boolean z2) throws SSLException {
        Provider provider2 = provider;
        SslProvider defaultServerProvider = sslProvider == null ? defaultServerProvider() : sslProvider;
        int i = C57331.$SwitchMap$io$netty$handler$ssl$SslProvider[defaultServerProvider.ordinal()];
        if (i != 1) {
            if (i == 2) {
                verifyNullSslContextProvider(defaultServerProvider, provider2);
                OpenSslServerContext openSslServerContext = new OpenSslServerContext(x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2, clientAuth, strArr, z, z2);
                return openSslServerContext;
            } else if (i == 3) {
                verifyNullSslContextProvider(defaultServerProvider, provider2);
                ReferenceCountedOpenSslServerContext referenceCountedOpenSslServerContext = new ReferenceCountedOpenSslServerContext(x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2, clientAuth, strArr, z, z2);
                return referenceCountedOpenSslServerContext;
            } else {
                throw new Error(defaultServerProvider.toString());
            }
        } else if (!z2) {
            JdkSslServerContext jdkSslServerContext = new JdkSslServerContext(provider, x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2, clientAuth, strArr, z);
            return jdkSslServerContext;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("OCSP is not supported with this SslProvider: ");
            sb.append(defaultServerProvider);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private static void verifyNullSslContextProvider(SslProvider sslProvider, Provider provider) {
        if (provider != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Java Security Provider unsupported for SslProvider: ");
            sb.append(sslProvider);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @Deprecated
    public static SslContext newClientContext() throws SSLException {
        return newClientContext(null, null, null);
    }

    @Deprecated
    public static SslContext newClientContext(File file) throws SSLException {
        return newClientContext((SslProvider) null, file);
    }

    @Deprecated
    public static SslContext newClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(null, null, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(File file, TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(null, file, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        return newClientContext((SslProvider) null, file, trustManagerFactory, iterable, iterable2, j, j2);
    }

    @Deprecated
    public static SslContext newClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        return newClientContext(null, file, trustManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider) throws SSLException {
        return newClientContext(sslProvider, null, null);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file) throws SSLException {
        return newClientContext(sslProvider, file, null);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(sslProvider, null, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory) throws SSLException {
        return newClientContext(sslProvider, file, trustManagerFactory, null, IdentityCipherSuiteFilter.INSTANCE, null, 0, 0);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        return newClientContext(sslProvider, file, trustManagerFactory, null, null, null, null, iterable, IdentityCipherSuiteFilter.INSTANCE, toApplicationProtocolConfig(iterable2), j, j2);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        return newClientContext(sslProvider, file, trustManagerFactory, null, null, null, null, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        try {
            return newClientContextInternal(sslProvider, null, toX509Certificates(file), trustManagerFactory, toX509Certificates(file2), toPrivateKey(file3, str), str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, null, j, j2, false);
        } catch (Exception e) {
            if (e instanceof SSLException) {
                throw ((SSLException) e);
            }
            throw new SSLException("failed to initialize the client-side SSL context", e);
        }
    }

    static SslContext newClientContextInternal(SslProvider sslProvider, Provider provider, X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, String[] strArr, long j, long j2, boolean z) throws SSLException {
        Provider provider2 = provider;
        SslProvider defaultClientProvider = sslProvider == null ? defaultClientProvider() : sslProvider;
        int i = C57331.$SwitchMap$io$netty$handler$ssl$SslProvider[defaultClientProvider.ordinal()];
        if (i != 1) {
            if (i == 2) {
                verifyNullSslContextProvider(defaultClientProvider, provider2);
                OpenSslClientContext openSslClientContext = new OpenSslClientContext(x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, strArr, j, j2, z);
                return openSslClientContext;
            } else if (i == 3) {
                verifyNullSslContextProvider(defaultClientProvider, provider2);
                ReferenceCountedOpenSslClientContext referenceCountedOpenSslClientContext = new ReferenceCountedOpenSslClientContext(x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, strArr, j, j2, z);
                return referenceCountedOpenSslClientContext;
            } else {
                throw new Error(defaultClientProvider.toString());
            }
        } else if (!z) {
            JdkSslClientContext jdkSslClientContext = new JdkSslClientContext(provider, x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, strArr, j, j2);
            return jdkSslClientContext;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("OCSP is not supported with this SslProvider: ");
            sb.append(defaultClientProvider);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static ApplicationProtocolConfig toApplicationProtocolConfig(Iterable<String> iterable) {
        if (iterable == null) {
            return ApplicationProtocolConfig.DISABLED;
        }
        return new ApplicationProtocolConfig(Protocol.NPN_AND_ALPN, SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL, SelectedListenerFailureBehavior.ACCEPT, iterable);
    }

    protected SslContext() {
        this(false);
    }

    protected SslContext(boolean z) {
        this.startTls = z;
    }

    public final boolean isServer() {
        return !isClient();
    }

    @Deprecated
    public final List<String> nextProtocols() {
        return applicationProtocolNegotiator().protocols();
    }

    public final SslHandler newHandler(ByteBufAllocator byteBufAllocator) {
        return new SslHandler(newEngine(byteBufAllocator), this.startTls);
    }

    public final SslHandler newHandler(ByteBufAllocator byteBufAllocator, String str, int i) {
        return new SslHandler(newEngine(byteBufAllocator, str, i), this.startTls);
    }

    protected static PKCS8EncodedKeySpec generateKeySpec(char[] cArr, byte[] bArr) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (cArr == null) {
            return new PKCS8EncodedKeySpec(bArr);
        }
        EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(bArr);
        SecretKey generateSecret = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName()).generateSecret(new PBEKeySpec(cArr));
        Cipher instance = Cipher.getInstance(encryptedPrivateKeyInfo.getAlgName());
        instance.init(2, generateSecret, encryptedPrivateKeyInfo.getAlgParameters());
        return encryptedPrivateKeyInfo.getKeySpec(instance);
    }

    static KeyStore buildKeyStore(X509Certificate[] x509CertificateArr, PrivateKey privateKey, char[] cArr) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore instance = KeyStore.getInstance("JKS");
        instance.load(null, null);
        instance.setKeyEntry("key", privateKey, cArr, x509CertificateArr);
        return instance;
    }

    static PrivateKey toPrivateKey(File file, String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
        if (file == null) {
            return null;
        }
        return getPrivateKeyFromByteBuffer(PemReader.readPrivateKey(file), str);
    }

    static PrivateKey toPrivateKey(InputStream inputStream, String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
        if (inputStream == null) {
            return null;
        }
        return getPrivateKeyFromByteBuffer(PemReader.readPrivateKey(inputStream), str);
    }

    private static PrivateKey getPrivateKeyFromByteBuffer(ByteBuf byteBuf, String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
        char[] cArr;
        byte[] bArr = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bArr).release();
        if (str == null) {
            cArr = null;
        } else {
            cArr = str.toCharArray();
        }
        PKCS8EncodedKeySpec generateKeySpec = generateKeySpec(cArr, bArr);
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(generateKeySpec);
        } catch (InvalidKeySpecException unused) {
            try {
                return KeyFactory.getInstance("DSA").generatePrivate(generateKeySpec);
            } catch (InvalidKeySpecException unused2) {
                try {
                    return KeyFactory.getInstance("EC").generatePrivate(generateKeySpec);
                } catch (InvalidKeySpecException e) {
                    throw new InvalidKeySpecException("Neither RSA, DSA nor EC worked", e);
                }
            }
        }
    }

    @Deprecated
    protected static TrustManagerFactory buildTrustManagerFactory(File file, TrustManagerFactory trustManagerFactory) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
        return buildTrustManagerFactory(toX509Certificates(file), trustManagerFactory);
    }

    static X509Certificate[] toX509Certificates(File file) throws CertificateException {
        if (file == null) {
            return null;
        }
        return getCertificatesFromBuffers(PemReader.readCertificates(file));
    }

    static X509Certificate[] toX509Certificates(InputStream inputStream) throws CertificateException {
        if (inputStream == null) {
            return null;
        }
        return getCertificatesFromBuffers(PemReader.readCertificates(inputStream));
    }

    private static X509Certificate[] getCertificatesFromBuffers(ByteBuf[] byteBufArr) throws CertificateException {
        ByteBufInputStream byteBufInputStream;
        CertificateFactory instance = CertificateFactory.getInstance("X.509");
        X509Certificate[] x509CertificateArr = new X509Certificate[byteBufArr.length];
        int i = 0;
        while (i < byteBufArr.length) {
            try {
                byteBufInputStream = new ByteBufInputStream(byteBufArr[i], true);
                x509CertificateArr[i] = (X509Certificate) instance.generateCertificate(byteBufInputStream);
                byteBufInputStream.close();
                i++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            } catch (Throwable th) {
                while (i < byteBufArr.length) {
                    byteBufArr[i].release();
                    i++;
                }
                throw th;
            }
        }
        while (i < byteBufArr.length) {
            byteBufArr[i].release();
            i++;
        }
        return x509CertificateArr;
    }

    static TrustManagerFactory buildTrustManagerFactory(X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
        KeyStore instance = KeyStore.getInstance("JKS");
        instance.load(null, null);
        int i = 1;
        for (X509Certificate certificateEntry : x509CertificateArr) {
            instance.setCertificateEntry(Integer.toString(i), certificateEntry);
            i++;
        }
        if (trustManagerFactory == null) {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        }
        trustManagerFactory.init(instance);
        return trustManagerFactory;
    }

    static PrivateKey toPrivateKeyInternal(File file, String str) throws SSLException {
        try {
            return toPrivateKey(file, str);
        } catch (Exception e) {
            throw new SSLException(e);
        }
    }

    static X509Certificate[] toX509CertificatesInternal(File file) throws SSLException {
        try {
            return toX509Certificates(file);
        } catch (CertificateException e) {
            throw new SSLException(e);
        }
    }

    static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] x509CertificateArr, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        String property = Security.getProperty("ssl.KeyManagerFactory.algorithm");
        if (property == null) {
            property = "SunX509";
        }
        return buildKeyManagerFactory(x509CertificateArr, property, privateKey, str, keyManagerFactory);
    }

    static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] x509CertificateArr, String str, PrivateKey privateKey, String str2, KeyManagerFactory keyManagerFactory) throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException, UnrecoverableKeyException {
        char[] charArray = str2 == null ? EmptyArrays.EMPTY_CHARS : str2.toCharArray();
        KeyStore buildKeyStore = buildKeyStore(x509CertificateArr, privateKey, charArray);
        if (keyManagerFactory == null) {
            keyManagerFactory = KeyManagerFactory.getInstance(str);
        }
        keyManagerFactory.init(buildKeyStore, charArray);
        return keyManagerFactory;
    }
}
