package p043io.netty.handler.ssl;

import java.io.File;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.ssl.SslContextBuilder */
public final class SslContextBuilder {
    private ApplicationProtocolConfig apn;
    private CipherSuiteFilter cipherFilter = IdentityCipherSuiteFilter.INSTANCE;
    private Iterable<String> ciphers;
    private ClientAuth clientAuth = ClientAuth.NONE;
    private boolean enableOcsp;
    private final boolean forServer;
    private PrivateKey key;
    private X509Certificate[] keyCertChain;
    private KeyManagerFactory keyManagerFactory;
    private String keyPassword;
    private String[] protocols;
    private SslProvider provider;
    private long sessionCacheSize;
    private long sessionTimeout;
    private Provider sslContextProvider;
    private boolean startTls;
    private X509Certificate[] trustCertCollection;
    private TrustManagerFactory trustManagerFactory;

    public static SslContextBuilder forClient() {
        return new SslContextBuilder(false);
    }

    public static SslContextBuilder forServer(File file, File file2) {
        return new SslContextBuilder(true).keyManager(file, file2);
    }

    public static SslContextBuilder forServer(InputStream inputStream, InputStream inputStream2) {
        return new SslContextBuilder(true).keyManager(inputStream, inputStream2);
    }

    public static SslContextBuilder forServer(PrivateKey privateKey, X509Certificate... x509CertificateArr) {
        return new SslContextBuilder(true).keyManager(privateKey, x509CertificateArr);
    }

    public static SslContextBuilder forServer(File file, File file2, String str) {
        return new SslContextBuilder(true).keyManager(file, file2, str);
    }

    public static SslContextBuilder forServer(InputStream inputStream, InputStream inputStream2, String str) {
        return new SslContextBuilder(true).keyManager(inputStream, inputStream2, str);
    }

    public static SslContextBuilder forServer(PrivateKey privateKey, String str, X509Certificate... x509CertificateArr) {
        return new SslContextBuilder(true).keyManager(privateKey, str, x509CertificateArr);
    }

    public static SslContextBuilder forServer(KeyManagerFactory keyManagerFactory2) {
        return new SslContextBuilder(true).keyManager(keyManagerFactory2);
    }

    private SslContextBuilder(boolean z) {
        this.forServer = z;
    }

    public SslContextBuilder sslProvider(SslProvider sslProvider) {
        this.provider = sslProvider;
        return this;
    }

    public SslContextBuilder sslContextProvider(Provider provider2) {
        this.sslContextProvider = provider2;
        return this;
    }

    public SslContextBuilder trustManager(File file) {
        try {
            return trustManager(SslContext.toX509Certificates(file));
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("File does not contain valid certificates: ");
            sb.append(file);
            throw new IllegalArgumentException(sb.toString(), e);
        }
    }

    public SslContextBuilder trustManager(InputStream inputStream) {
        try {
            return trustManager(SslContext.toX509Certificates(inputStream));
        } catch (Exception e) {
            throw new IllegalArgumentException("Input stream does not contain valid certificates.", e);
        }
    }

    public SslContextBuilder trustManager(X509Certificate... x509CertificateArr) {
        this.trustCertCollection = x509CertificateArr != null ? (X509Certificate[]) x509CertificateArr.clone() : null;
        this.trustManagerFactory = null;
        return this;
    }

    public SslContextBuilder trustManager(TrustManagerFactory trustManagerFactory2) {
        this.trustCertCollection = null;
        this.trustManagerFactory = trustManagerFactory2;
        return this;
    }

    public SslContextBuilder keyManager(File file, File file2) {
        return keyManager(file, file2, (String) null);
    }

    public SslContextBuilder keyManager(InputStream inputStream, InputStream inputStream2) {
        return keyManager(inputStream, inputStream2, (String) null);
    }

    public SslContextBuilder keyManager(PrivateKey privateKey, X509Certificate... x509CertificateArr) {
        return keyManager(privateKey, (String) null, x509CertificateArr);
    }

    public SslContextBuilder keyManager(File file, File file2, String str) {
        try {
            try {
                return keyManager(SslContext.toPrivateKey(file2, str), str, SslContext.toX509Certificates(file));
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder();
                sb.append("File does not contain valid private key: ");
                sb.append(file2);
                throw new IllegalArgumentException(sb.toString(), e);
            }
        } catch (Exception e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("File does not contain valid certificates: ");
            sb2.append(file);
            throw new IllegalArgumentException(sb2.toString(), e2);
        }
    }

    public SslContextBuilder keyManager(InputStream inputStream, InputStream inputStream2, String str) {
        try {
            try {
                return keyManager(SslContext.toPrivateKey(inputStream2, str), str, SslContext.toX509Certificates(inputStream));
            } catch (Exception e) {
                throw new IllegalArgumentException("Input stream does not contain valid private key.", e);
            }
        } catch (Exception e2) {
            throw new IllegalArgumentException("Input stream not contain valid certificates.", e2);
        }
    }

    public SslContextBuilder keyManager(PrivateKey privateKey, String str, X509Certificate... x509CertificateArr) {
        if (this.forServer) {
            ObjectUtil.checkNotNull(x509CertificateArr, "keyCertChain required for servers");
            if (x509CertificateArr.length != 0) {
                ObjectUtil.checkNotNull(privateKey, "key required for servers");
            } else {
                throw new IllegalArgumentException("keyCertChain must be non-empty");
            }
        }
        if (x509CertificateArr == null || x509CertificateArr.length == 0) {
            this.keyCertChain = null;
        } else {
            int length = x509CertificateArr.length;
            int i = 0;
            while (i < length) {
                if (x509CertificateArr[i] != null) {
                    i++;
                } else {
                    throw new IllegalArgumentException("keyCertChain contains null entry");
                }
            }
            this.keyCertChain = (X509Certificate[]) x509CertificateArr.clone();
        }
        this.key = privateKey;
        this.keyPassword = str;
        this.keyManagerFactory = null;
        return this;
    }

    public SslContextBuilder keyManager(KeyManagerFactory keyManagerFactory2) {
        if (this.forServer) {
            ObjectUtil.checkNotNull(keyManagerFactory2, "keyManagerFactory required for servers");
        }
        this.keyCertChain = null;
        this.key = null;
        this.keyPassword = null;
        this.keyManagerFactory = keyManagerFactory2;
        return this;
    }

    public SslContextBuilder ciphers(Iterable<String> iterable) {
        return ciphers(iterable, IdentityCipherSuiteFilter.INSTANCE);
    }

    public SslContextBuilder ciphers(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter) {
        ObjectUtil.checkNotNull(cipherSuiteFilter, "cipherFilter");
        this.ciphers = iterable;
        this.cipherFilter = cipherSuiteFilter;
        return this;
    }

    public SslContextBuilder applicationProtocolConfig(ApplicationProtocolConfig applicationProtocolConfig) {
        this.apn = applicationProtocolConfig;
        return this;
    }

    public SslContextBuilder sessionCacheSize(long j) {
        this.sessionCacheSize = j;
        return this;
    }

    public SslContextBuilder sessionTimeout(long j) {
        this.sessionTimeout = j;
        return this;
    }

    public SslContextBuilder clientAuth(ClientAuth clientAuth2) {
        this.clientAuth = (ClientAuth) ObjectUtil.checkNotNull(clientAuth2, "clientAuth");
        return this;
    }

    public SslContextBuilder protocols(String... strArr) {
        this.protocols = strArr == null ? null : (String[]) strArr.clone();
        return this;
    }

    public SslContextBuilder startTls(boolean z) {
        this.startTls = z;
        return this;
    }

    public SslContextBuilder enableOcsp(boolean z) {
        this.enableOcsp = z;
        return this;
    }

    public SslContext build() throws SSLException {
        if (this.forServer) {
            return SslContext.newServerContextInternal(this.provider, this.sslContextProvider, this.trustCertCollection, this.trustManagerFactory, this.keyCertChain, this.key, this.keyPassword, this.keyManagerFactory, this.ciphers, this.cipherFilter, this.apn, this.sessionCacheSize, this.sessionTimeout, this.clientAuth, this.protocols, this.startTls, this.enableOcsp);
        }
        return SslContext.newClientContextInternal(this.provider, this.sslContextProvider, this.trustCertCollection, this.trustManagerFactory, this.keyCertChain, this.key, this.keyPassword, this.keyManagerFactory, this.ciphers, this.cipherFilter, this.apn, this.protocols, this.sessionCacheSize, this.sessionTimeout, this.enableOcsp);
    }
}
