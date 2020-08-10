package p043io.netty.handler.ssl;

import io.netty.internal.tcnative.SSLContext;
import io.netty.internal.tcnative.SniHostNameMatcher;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslServerContext */
public final class ReferenceCountedOpenSslServerContext extends ReferenceCountedOpenSslContext {

    /* renamed from: ID */
    private static final byte[] f3737ID = {110, 101, 116, 116, 121};
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslServerContext.class);
    private final OpenSslKeyMaterialManager keyMaterialManager;
    private final OpenSslServerSessionContext sessionContext;

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslServerContext$ExtendedTrustManagerVerifyCallback */
    private static final class ExtendedTrustManagerVerifyCallback extends AbstractCertificateVerifier {
        private final X509ExtendedTrustManager manager;

        ExtendedTrustManagerVerifyCallback(OpenSslEngineMap openSslEngineMap, X509ExtendedTrustManager x509ExtendedTrustManager) {
            super(openSslEngineMap);
            this.manager = x509ExtendedTrustManager;
        }

        /* access modifiers changed from: 0000 */
        public void verify(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, X509Certificate[] x509CertificateArr, String str) throws Exception {
            this.manager.checkClientTrusted(x509CertificateArr, str, referenceCountedOpenSslEngine);
        }
    }

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslServerContext$OpenSslSniHostnameMatcher */
    private static final class OpenSslSniHostnameMatcher implements SniHostNameMatcher {
        private final OpenSslEngineMap engineMap;

        OpenSslSniHostnameMatcher(OpenSslEngineMap openSslEngineMap) {
            this.engineMap = openSslEngineMap;
        }

        public boolean match(long j, String str) {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.engineMap.get(j);
            if (referenceCountedOpenSslEngine != null) {
                return referenceCountedOpenSslEngine.checkSniHostnameMatch(str);
            }
            ReferenceCountedOpenSslServerContext.logger.warn("No ReferenceCountedOpenSslEngine found for SSL pointer: {}", (Object) Long.valueOf(j));
            return false;
        }
    }

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslServerContext$ServerContext */
    static final class ServerContext {
        OpenSslKeyMaterialManager keyMaterialManager;
        OpenSslServerSessionContext sessionContext;

        ServerContext() {
        }
    }

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslServerContext$TrustManagerVerifyCallback */
    private static final class TrustManagerVerifyCallback extends AbstractCertificateVerifier {
        private final X509TrustManager manager;

        TrustManagerVerifyCallback(OpenSslEngineMap openSslEngineMap, X509TrustManager x509TrustManager) {
            super(openSslEngineMap);
            this.manager = x509TrustManager;
        }

        /* access modifiers changed from: 0000 */
        public void verify(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, X509Certificate[] x509CertificateArr, String str) throws Exception {
            this.manager.checkClientTrusted(x509CertificateArr, str);
        }
    }

    ReferenceCountedOpenSslServerContext(X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2, ClientAuth clientAuth, String[] strArr, boolean z, boolean z2) throws SSLException {
        this(x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory, iterable, cipherSuiteFilter, toNegotiator(applicationProtocolConfig), j, j2, clientAuth, strArr, z, z2);
    }

    private ReferenceCountedOpenSslServerContext(X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long j, long j2, ClientAuth clientAuth, String[] strArr, boolean z, boolean z2) throws SSLException {
        super(iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, j, j2, 1, (Certificate[]) x509CertificateArr2, clientAuth, strArr, z, z2, true);
        try {
            ServerContext newSessionContext = newSessionContext(this, this.ctx, this.engineMap, x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory);
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

    static ServerContext newSessionContext(ReferenceCountedOpenSslContext referenceCountedOpenSslContext, long j, OpenSslEngineMap openSslEngineMap, X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory) throws SSLException {
        long j2;
        ServerContext serverContext = new ServerContext();
        synchronized (ReferenceCountedOpenSslContext.class) {
            try {
                SSLContext.setVerify(j, 0, 10);
                if (OpenSsl.useKeyManagerFactory()) {
                    if (keyManagerFactory == null) {
                        keyManagerFactory = buildKeyManagerFactory(x509CertificateArr2, privateKey, str, keyManagerFactory);
                    }
                    X509KeyManager chooseX509KeyManager = chooseX509KeyManager(keyManagerFactory.getKeyManagers());
                    serverContext.keyMaterialManager = useExtendedKeyManager(chooseX509KeyManager) ? new OpenSslExtendedKeyMaterialManager((X509ExtendedKeyManager) chooseX509KeyManager, str) : new OpenSslKeyMaterialManager(chooseX509KeyManager, str);
                } else if (keyManagerFactory == null) {
                    ObjectUtil.checkNotNull(x509CertificateArr2, "keyCertChain");
                    setKeyMaterial(j, x509CertificateArr2, privateKey, str);
                } else {
                    throw new IllegalArgumentException("KeyManagerFactory not supported");
                }
                if (x509CertificateArr != null) {
                    trustManagerFactory = buildTrustManagerFactory(x509CertificateArr, trustManagerFactory);
                } else if (trustManagerFactory == null) {
                    trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    trustManagerFactory.init(null);
                }
                X509TrustManager chooseTrustManager = chooseTrustManager(trustManagerFactory.getTrustManagers());
                if (useExtendedTrustManager(chooseTrustManager)) {
                    SSLContext.setCertVerifyCallback(j, new ExtendedTrustManagerVerifyCallback(openSslEngineMap, (X509ExtendedTrustManager) chooseTrustManager));
                } else {
                    SSLContext.setCertVerifyCallback(j, new TrustManagerVerifyCallback(openSslEngineMap, chooseTrustManager));
                }
                X509Certificate[] acceptedIssuers = chooseTrustManager.getAcceptedIssuers();
                if (acceptedIssuers != null && acceptedIssuers.length > 0) {
                    j2 = 0;
                    j2 = toBIO(acceptedIssuers);
                    if (SSLContext.setCACertificateBio(j, j2)) {
                        freeBio(j2);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("unable to setup accepted issuers for trustmanager ");
                        sb.append(chooseTrustManager);
                        throw new SSLException(sb.toString());
                    }
                }
                if (PlatformDependent.javaVersion() >= 8) {
                    SSLContext.setSniHostnameMatcher(j, new OpenSslSniHostnameMatcher(openSslEngineMap));
                }
            } catch (Exception e) {
                throw new SSLException("failed to set certificate and key", e);
            } catch (SSLException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new SSLException("unable to setup trustmanager", e3);
            } catch (Throwable th) {
                throw th;
            }
        }
        serverContext.sessionContext = new OpenSslServerSessionContext(referenceCountedOpenSslContext);
        serverContext.sessionContext.setSessionIdContext(f3737ID);
        return serverContext;
    }
}
