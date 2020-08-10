package p043io.netty.handler.ssl;

import io.netty.internal.tcnative.CertificateRequestedCallback;
import io.netty.internal.tcnative.CertificateRequestedCallback.KeyMaterial;
import io.netty.internal.tcnative.SSLContext;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslClientContext */
public final class ReferenceCountedOpenSslClientContext extends ReferenceCountedOpenSslContext {
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslClientContext.class);
    private final OpenSslSessionContext sessionContext;

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslClientContext$ExtendedTrustManagerVerifyCallback */
    private static final class ExtendedTrustManagerVerifyCallback extends AbstractCertificateVerifier {
        private final X509ExtendedTrustManager manager;

        ExtendedTrustManagerVerifyCallback(OpenSslEngineMap openSslEngineMap, X509ExtendedTrustManager x509ExtendedTrustManager) {
            super(openSslEngineMap);
            this.manager = x509ExtendedTrustManager;
        }

        /* access modifiers changed from: 0000 */
        public void verify(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, X509Certificate[] x509CertificateArr, String str) throws Exception {
            this.manager.checkServerTrusted(x509CertificateArr, str, referenceCountedOpenSslEngine);
        }
    }

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslClientContext$OpenSslCertificateRequestedCallback */
    private static final class OpenSslCertificateRequestedCallback implements CertificateRequestedCallback {
        private final OpenSslEngineMap engineMap;
        private final OpenSslKeyMaterialManager keyManagerHolder;

        private static String clientKeyType(byte b) {
            if (b == 1) {
                return "RSA";
            }
            if (b == 3) {
                return "DH_RSA";
            }
            switch (b) {
                case 64:
                    return "EC";
                case 65:
                    return "EC_RSA";
                case 66:
                    return "EC_EC";
                default:
                    return null;
            }
        }

        OpenSslCertificateRequestedCallback(OpenSslEngineMap openSslEngineMap, OpenSslKeyMaterialManager openSslKeyMaterialManager) {
            this.engineMap = openSslEngineMap;
            this.keyManagerHolder = openSslKeyMaterialManager;
        }

        public KeyMaterial requested(long j, byte[] bArr, byte[][] bArr2) {
            X500Principal[] x500PrincipalArr;
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.engineMap.get(j);
            try {
                Set supportedClientKeyTypes = supportedClientKeyTypes(bArr);
                String[] strArr = (String[]) supportedClientKeyTypes.toArray(new String[supportedClientKeyTypes.size()]);
                if (bArr2 == null) {
                    x500PrincipalArr = null;
                } else {
                    x500PrincipalArr = new X500Principal[bArr2.length];
                    for (int i = 0; i < bArr2.length; i++) {
                        x500PrincipalArr[i] = new X500Principal(bArr2[i]);
                    }
                }
                return this.keyManagerHolder.keyMaterial(referenceCountedOpenSslEngine, strArr, x500PrincipalArr);
            } catch (Throwable th) {
                ReferenceCountedOpenSslClientContext.logger.debug("request of key failed", th);
                SSLHandshakeException sSLHandshakeException = new SSLHandshakeException("General OpenSslEngine problem");
                sSLHandshakeException.initCause(th);
                referenceCountedOpenSslEngine.handshakeException = sSLHandshakeException;
                return null;
            }
        }

        private static Set<String> supportedClientKeyTypes(byte[] bArr) {
            HashSet hashSet = new HashSet(bArr.length);
            for (byte clientKeyType : bArr) {
                String clientKeyType2 = clientKeyType(clientKeyType);
                if (clientKeyType2 != null) {
                    hashSet.add(clientKeyType2);
                }
            }
            return hashSet;
        }
    }

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslClientContext$OpenSslClientSessionContext */
    static final class OpenSslClientSessionContext extends OpenSslSessionContext {
        public int getSessionCacheSize() {
            return 0;
        }

        public int getSessionTimeout() {
            return 0;
        }

        public boolean isSessionCacheEnabled() {
            return false;
        }

        public void setSessionCacheEnabled(boolean z) {
        }

        OpenSslClientSessionContext(ReferenceCountedOpenSslContext referenceCountedOpenSslContext) {
            super(referenceCountedOpenSslContext);
        }

        public void setSessionTimeout(int i) {
            if (i < 0) {
                throw new IllegalArgumentException();
            }
        }

        public void setSessionCacheSize(int i) {
            if (i < 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslClientContext$TrustManagerVerifyCallback */
    private static final class TrustManagerVerifyCallback extends AbstractCertificateVerifier {
        private final X509TrustManager manager;

        TrustManagerVerifyCallback(OpenSslEngineMap openSslEngineMap, X509TrustManager x509TrustManager) {
            super(openSslEngineMap);
            this.manager = x509TrustManager;
        }

        /* access modifiers changed from: 0000 */
        public void verify(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, X509Certificate[] x509CertificateArr, String str) throws Exception {
            this.manager.checkServerTrusted(x509CertificateArr, str);
        }
    }

    /* access modifiers changed from: 0000 */
    public OpenSslKeyMaterialManager keyMaterialManager() {
        return null;
    }

    ReferenceCountedOpenSslClientContext(X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, String[] strArr, long j, long j2, boolean z) throws SSLException {
        super(iterable, cipherSuiteFilter, applicationProtocolConfig, j, j2, 0, (Certificate[]) x509CertificateArr2, ClientAuth.NONE, strArr, false, z, true);
        try {
            this.sessionContext = newSessionContext(this, this.ctx, this.engineMap, x509CertificateArr, trustManagerFactory, x509CertificateArr2, privateKey, str, keyManagerFactory);
        } catch (Throwable th) {
            release();
            throw th;
        }
    }

    public OpenSslSessionContext sessionContext() {
        return this.sessionContext;
    }

    static OpenSslSessionContext newSessionContext(ReferenceCountedOpenSslContext referenceCountedOpenSslContext, long j, OpenSslEngineMap openSslEngineMap, X509Certificate[] x509CertificateArr, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArr2, PrivateKey privateKey, String str, KeyManagerFactory keyManagerFactory) throws SSLException {
        if ((privateKey != null || x509CertificateArr2 == null) && (privateKey == null || x509CertificateArr2 != null)) {
            synchronized (ReferenceCountedOpenSslContext.class) {
                try {
                    if (OpenSsl.useKeyManagerFactory()) {
                        if (keyManagerFactory == null && x509CertificateArr2 != null) {
                            keyManagerFactory = buildKeyManagerFactory(x509CertificateArr2, privateKey, str, keyManagerFactory);
                        }
                        if (keyManagerFactory != null) {
                            X509KeyManager chooseX509KeyManager = chooseX509KeyManager(keyManagerFactory.getKeyManagers());
                            SSLContext.setCertRequestedCallback(j, new OpenSslCertificateRequestedCallback(openSslEngineMap, useExtendedKeyManager(chooseX509KeyManager) ? new OpenSslExtendedKeyMaterialManager((X509ExtendedKeyManager) chooseX509KeyManager, str) : new OpenSslKeyMaterialManager(chooseX509KeyManager, str)));
                        }
                    } else if (keyManagerFactory != null) {
                        throw new IllegalArgumentException("KeyManagerFactory not supported");
                    } else if (x509CertificateArr2 != null) {
                        setKeyMaterial(j, x509CertificateArr2, privateKey, str);
                    }
                    SSLContext.setVerify(j, 0, 10);
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
                } catch (Exception e) {
                    throw new SSLException("failed to set certificate and key", e);
                } catch (Exception e2) {
                    throw new SSLException("unable to setup trustmanager", e2);
                } catch (Throwable th) {
                    throw th;
                }
            }
            return new OpenSslClientSessionContext(referenceCountedOpenSslContext);
        }
        throw new IllegalArgumentException("Either both keyCertChain and key needs to be null or none of them");
    }
}
