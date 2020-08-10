package p043io.netty.handler.ssl;

import java.io.File;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;

/* renamed from: io.netty.handler.ssl.OpenSslServerContext */
public final class OpenSslServerContext extends OpenSslContext {
    private final OpenSslKeyMaterialManager keyMaterialManager;
    private final OpenSslServerSessionContext sessionContext;

    @Deprecated
    public OpenSslServerContext(File file, File file2) throws SSLException {
        this(file, file2, null);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str) throws SSLException {
        this(file, file2, str, null, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, ApplicationProtocolConfig.DISABLED, 0, 0);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, Iterable<String> iterable, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, file2, str, iterable, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        this(file, file2, str, iterable, toApplicationProtocolConfig(iterable2), j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, file2, str, trustManagerFactory, iterable, toNegotiator(applicationProtocolConfig), j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        this((File) null, trustManagerFactory, file, file2, str, (KeyManagerFactory) null, iterable, (CipherSuiteFilter) null, openSslApplicationProtocolNegotiator, j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this((File) null, (TrustManagerFactory) null, file, file2, str, (KeyManagerFactory) null, iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, trustManagerFactory, file2, file3, str, keyManagerFactory, iterable, cipherSuiteFilter, toNegotiator(applicationProtocolConfig), j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this((File) null, trustManagerFactory, file, file2, str, (KeyManagerFactory) null, iterable, cipherSuiteFilter, toNegotiator(applicationProtocolConfig), j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String str, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        this((File) null, trustManagerFactory, file, file2, str, (KeyManagerFactory) null, iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, j, j2);
    }

    @Deprecated
    public OpenSslServerContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        this(toX509CertificatesInternal(file), trustManagerFactory, toX509CertificatesInternal(file2), toPrivateKeyInternal(file3, str), str, keyManagerFactory, iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, j, j2, ClientAuth.NONE, (String[]) null, false, false);
    }

    OpenSslServerContext(X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2, ClientAuth clientAuth, String[] strArr, boolean z, boolean z2) throws SSLException {
        this(x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, toNegotiator(applicationProtocolConfig), j, j2, clientAuth, strArr, z, z2);
    }

    private OpenSslServerContext(X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long j, long j2, ClientAuth clientAuth, String[] strArr, boolean z, boolean z2) throws SSLException {
        super(iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, j, j2, 1, (Certificate[]) x509CertificateArr2, clientAuth, strArr, z, z2);
        try {
            ServerContext newSessionContext = ReferenceCountedOpenSslServerContext.newSessionContext(this, this.ctx, this.engineMap, x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory);
            this.sessionContext = newSessionContext.sessionContext;
            this.keyMaterialManager = newSessionContext.keyMaterialManager;
        } catch (Throwable th) {
            release();
            throw th;
        }
    }

    public OpenSslServerSessionContext sessionContext() {
        return this.sessionContext;
    }

    /* access modifiers changed from: 0000 */
    public OpenSslKeyMaterialManager keyMaterialManager() {
        return this.keyMaterialManager;
    }
}
