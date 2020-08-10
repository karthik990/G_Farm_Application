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
/* renamed from: io.netty.handler.ssl.JdkSslServerContext */
public final class JdkSslServerContext extends JdkSslContext {
    @Deprecated
    public JdkSslServerContext(File file, File file2) throws SSLException {
        this(file, file2, null);
    }

    @Deprecated
    public JdkSslServerContext(File file, File file2, String str) throws SSLException {
        this(file, file2, str, null, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, (JdkApplicationProtocolNegotiator) JdkDefaultApplicationProtocolNegotiator.INSTANCE, 0, 0);
    }

    @Deprecated
    public JdkSslServerContext(File file, File file2, String str, Iterable<String> iterable, Iterable<String> iterable2, long j, long j2) throws SSLException {
        this(file, file2, str, iterable, (CipherSuiteFilter) IdentityCipherSuiteFilter.INSTANCE, toNegotiator(toApplicationProtocolConfig(iterable2), true), j, j2);
    }

    @Deprecated
    public JdkSslServerContext(File file, File file2, String str, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, file2, str, iterable, cipherSuiteFilter, toNegotiator(applicationProtocolConfig, true), j, j2);
    }

    @Deprecated
    public JdkSslServerContext(File file, File file2, String str, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        this(null, file, file2, str, iterable, cipherSuiteFilter, jdkApplicationProtocolNegotiator, j, j2);
    }

    JdkSslServerContext(Provider provider, File file, File file2, String str, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        Iterable<String> iterable2 = iterable;
        CipherSuiteFilter cipherSuiteFilter2 = cipherSuiteFilter;
        JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator2 = jdkApplicationProtocolNegotiator;
        super(newSSLContext(provider, null, null, toX509CertificatesInternal(file), toPrivateKeyInternal(file2, str), str, null, j, j2), false, iterable2, cipherSuiteFilter2, jdkApplicationProtocolNegotiator2, ClientAuth.NONE, null, false);
    }

    @Deprecated
    public JdkSslServerContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2) throws SSLException {
        this(file, trustManagerFactory, file2, file3, str, keyManagerFactory, iterable, cipherSuiteFilter, toNegotiator(applicationProtocolConfig, true), j, j2);
    }

    @Deprecated
    public JdkSslServerContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long j, long j2) throws SSLException {
        Iterable<String> iterable2 = iterable;
        CipherSuiteFilter cipherSuiteFilter2 = cipherSuiteFilter;
        JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator2 = jdkApplicationProtocolNegotiator;
        super(newSSLContext(null, toX509CertificatesInternal(file), trustManagerFactory, toX509CertificatesInternal(file2), toPrivateKeyInternal(file3, str), str, keyManagerFactory, j, j2), false, iterable2, cipherSuiteFilter2, jdkApplicationProtocolNegotiator2, ClientAuth.NONE, null, false);
    }

    JdkSslServerContext(Provider provider, X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2, ClientAuth clientAuth, String[] strArr, boolean z) throws SSLException {
        super(newSSLContext(provider, x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, j, j2), false, iterable, cipherSuiteFilter, toNegotiator(applicationProtocolConfig, true), clientAuth, strArr, z);
    }

    private static SSLContext newSSLContext(Provider provider, X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, long j, long j2) throws SSLException {
        SSLContext sSLContext;
        TrustManager[] trustManagerArr;
        if (privateKey == null && keyManagerFactory == null) {
            throw new NullPointerException("key, keyManagerFactory");
        }
        if (x509CertificateArr != null) {
            try {
                trustManagerFactory = buildTrustManagerFactory(x509CertificateArr, trustManagerFactory);
            } catch (Exception e) {
                if (e instanceof SSLException) {
                    throw ((SSLException) e);
                }
                throw new SSLException("failed to initialize the server-side SSL context", e);
            }
        }
        if (privateKey != null) {
            keyManagerFactory = buildKeyManagerFactory(x509CertificateArr2, privateKey, str, keyManagerFactory);
        }
        String str2 = "TLS";
        if (provider == null) {
            sSLContext = SSLContext.getInstance(str2);
        } else {
            sSLContext = SSLContext.getInstance(str2, provider);
        }
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        if (trustManagerFactory == null) {
            trustManagerArr = null;
        } else {
            trustManagerArr = trustManagerFactory.getTrustManagers();
        }
        sSLContext.init(keyManagers, trustManagerArr, null);
        SSLSessionContext serverSessionContext = sSLContext.getServerSessionContext();
        if (j > 0) {
            serverSessionContext.setSessionCacheSize((int) Math.min(j, 2147483647L));
        }
        if (j2 > 0) {
            serverSessionContext.setSessionTimeout((int) Math.min(j2, 2147483647L));
        }
        return sSLContext;
    }
}
