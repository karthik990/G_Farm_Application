package p043io.netty.handler.ssl;

import java.io.File;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

@Deprecated
/* renamed from: io.netty.handler.ssl.JdkSslClientContext */
public final class JdkSslClientContext extends JdkSslContext {
    @Deprecated
    public JdkSslClientContext() throws SSLException {
        this(null, null);
    }

    @Deprecated
    public JdkSslClientContext(File file) throws SSLException {
        this(file, null);
    }

    @Deprecated
    public JdkSslClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
        this(null, trustManagerFactory);
    }

    @Deprecated
    public JdkSslClientContext(File file, TrustManagerFactory trustManagerFactory) throws SSLException {
        this(file, trustManagerFactory, null, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, (JdkApplicationProtocolNegotiator) JdkDefaultApplicationProtocolNegotiator.INSTANCE, 0, 0);
    }

    @Deprecated
    public JdkSslClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        this(file, trustManagerFactory, iterable, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, toNegotiator(toApplicationProtocolConfig(iterable2), false), j, j2);
    }

    @Deprecated
    public JdkSslClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, trustManagerFactory, iterable, cipherSuiteFilter, toNegotiator(applicationProtocolConfig, false), j, j2);
    }

    @Deprecated
    public JdkSslClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        this(null, file, trustManagerFactory, iterable, cipherSuiteFilter, jdkApplicationProtocolNegotiator, j, j2);
    }

    JdkSslClientContext(Provider provider, File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        Iterable<String> iterable2 = iterable;
        CipherSuiteFilter cipherSuiteFilter2 = cipherSuiteFilter;
        JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator2 = jdkApplicationProtocolNegotiator;
        super(newSSLContext(provider, toX509CertificatesInternal(file), trustManagerFactory, null, null, null, null, j, j2), true, iterable2, cipherSuiteFilter2, jdkApplicationProtocolNegotiator2, ClientAuth.NONE, null, false);
    }

    @Deprecated
    public JdkSslClientContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, trustManagerFactory, file2, file3, str, keyManagerFactory, iterable, cipherSuiteFilter, toNegotiator(applicationProtocolConfig, false), j, j2);
    }

    @Deprecated
    public JdkSslClientContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        Iterable<String> iterable2 = iterable;
        CipherSuiteFilter cipherSuiteFilter2 = cipherSuiteFilter;
        JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator2 = jdkApplicationProtocolNegotiator;
        super(newSSLContext(null, toX509CertificatesInternal(file), trustManagerFactory, toX509CertificatesInternal(file2), toPrivateKeyInternal(file3, str), str, keyManagerFactory, j, j2), true, iterable2, cipherSuiteFilter2, jdkApplicationProtocolNegotiator2, ClientAuth.NONE, null, false);
    }

    JdkSslClientContext(Provider provider, X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, String[] strArr, long j, long j2) throws SSLException {
        super(newSSLContext(provider, x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, j, j2), true, iterable, cipherSuiteFilter, toNegotiator(applicationProtocolConfig, false), ClientAuth.NONE, strArr, false);
    }

    private static SSLContext newSSLContext(Provider provider, X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, long j, long j2) throws SSLException {
        SSLContext sSLContext;
        KeyManager[] keyManagerArr;
        TrustManager[] trustManagerArr;
        if (x509CertificateArr != null) {
            try {
                trustManagerFactory = buildTrustManagerFactory(x509CertificateArr, trustManagerFactory);
            } catch (Exception e) {
                if (e instanceof SSLException) {
                    throw ((SSLException) e);
                }
                throw new SSLException("failed to initialize the client-side SSL context", e);
            }
        }
        if (x509CertificateArr2 != null) {
            keyManagerFactory = buildKeyManagerFactory(x509CertificateArr2, privateKey, str, keyManagerFactory);
        }
        String str2 = "TLS";
        if (provider == null) {
            sSLContext = SSLContext.getInstance(str2);
        } else {
            sSLContext = SSLContext.getInstance(str2, provider);
        }
        if (keyManagerFactory == null) {
            keyManagerArr = null;
        } else {
            keyManagerArr = keyManagerFactory.getKeyManagers();
        }
        if (trustManagerFactory == null) {
            trustManagerArr = null;
        } else {
            trustManagerArr = trustManagerFactory.getTrustManagers();
        }
        sSLContext.init(keyManagerArr, trustManagerArr, null);
        SSLSessionContext clientSessionContext = sSLContext.getClientSessionContext();
        if (j > 0) {
            clientSessionContext.setSessionCacheSize((int) Math.min(j, 2147483647L));
        }
        if (j2 > 0) {
            clientSessionContext.setSessionTimeout((int) Math.min(j2, 2147483647L));
        }
        return sSLContext;
    }
}
