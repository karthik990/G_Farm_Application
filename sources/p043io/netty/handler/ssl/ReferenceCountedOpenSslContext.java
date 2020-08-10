package p043io.netty.handler.ssl;

import io.netty.internal.tcnative.CertificateVerifier;
import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertPathValidatorException.BasicReason;
import java.security.cert.CertPathValidatorException.Reason;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateRevokedException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import p043io.netty.util.AbstractReferenceCounted;
import p043io.netty.util.ReferenceCounted;
import p043io.netty.util.ResourceLeakDetector;
import p043io.netty.util.ResourceLeakTracker;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;

/* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslContext */
public abstract class ReferenceCountedOpenSslContext extends SslContext implements ReferenceCounted {
    private static final int DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE = ((Integer) AccessController.doPrivileged(new PrivilegedAction<Integer>() {
        public Integer run() {
            return Integer.valueOf(Math.max(1, SystemPropertyUtil.getInt("io.netty.handler.ssl.openssl.bioNonApplicationBufferSize", 2048)));
        }
    })).intValue();
    private static final List<String> DEFAULT_CIPHERS;
    private static final Integer DH_KEY_LENGTH;
    private static final boolean JDK_REJECT_CLIENT_INITIATED_RENEGOTIATION = ((Boolean) AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
        public Boolean run() {
            return Boolean.valueOf(SystemPropertyUtil.getBoolean("jdk.tls.rejectClientInitiatedRenegotiation", false));
        }
    })).booleanValue();
    static final OpenSslApplicationProtocolNegotiator NONE_PROTOCOL_NEGOTIATOR = new OpenSslApplicationProtocolNegotiator() {
        public Protocol protocol() {
            return Protocol.NONE;
        }

        public List<String> protocols() {
            return Collections.emptyList();
        }

        public SelectorFailureBehavior selectorFailureBehavior() {
            return SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
        }

        public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
            return SelectedListenerFailureBehavior.ACCEPT;
        }
    };
    protected static final int VERIFY_DEPTH = 10;
    private static final ResourceLeakDetector<ReferenceCountedOpenSslContext> leakDetector;
    /* access modifiers changed from: private */
    public static final InternalLogger logger;
    private final OpenSslApplicationProtocolNegotiator apn;
    private volatile int aprPoolDestroyed;
    private volatile int bioNonApplicationBufferSize;
    final ClientAuth clientAuth;
    protected volatile long ctx;
    final boolean enableOcsp;
    final OpenSslEngineMap engineMap;
    final Certificate[] keyCertChain;
    /* access modifiers changed from: private */
    public final ResourceLeakTracker<ReferenceCountedOpenSslContext> leak;
    private final int mode;
    final String[] protocols;
    private final AbstractReferenceCounted refCnt;
    private volatile boolean rejectRemoteInitiatedRenegotiation;
    private final long sessionCacheSize;
    private final long sessionTimeout;
    private final List<String> unmodifiableCiphers;

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslContext$6 */
    static /* synthetic */ class C57296 {

        /* renamed from: $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol */
        static final /* synthetic */ int[] f3731xc16482e4 = new int[Protocol.values().length];

        /* renamed from: $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectedListenerFailureBehavior */
        static final /* synthetic */ int[] f3732xcbdfafc1 = new int[SelectedListenerFailureBehavior.values().length];

        /* renamed from: $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$SelectorFailureBehavior */
        static final /* synthetic */ int[] f3733xb32e3251 = new int[SelectorFailureBehavior.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(19:0|(2:1|2)|3|(2:5|6)|7|9|10|11|12|13|15|16|17|18|19|20|21|22|24) */
        /* JADX WARNING: Can't wrap try/catch for region: R(20:0|(2:1|2)|3|5|6|7|9|10|11|12|13|15|16|17|18|19|20|21|22|24) */
        /* JADX WARNING: Can't wrap try/catch for region: R(21:0|1|2|3|5|6|7|9|10|11|12|13|15|16|17|18|19|20|21|22|24) */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0032 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x004f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0059 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0064 */
        static {
            /*
                io.netty.handler.ssl.ApplicationProtocolConfig$SelectedListenerFailureBehavior[] r0 = p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3732xcbdfafc1 = r0
                r0 = 1
                int[] r1 = f3732xcbdfafc1     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.ssl.ApplicationProtocolConfig$SelectedListenerFailureBehavior r2 = p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f3732xcbdfafc1     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.ssl.ApplicationProtocolConfig$SelectedListenerFailureBehavior r3 = p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                io.netty.handler.ssl.ApplicationProtocolConfig$SelectorFailureBehavior[] r2 = p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                f3733xb32e3251 = r2
                int[] r2 = f3733xb32e3251     // Catch:{ NoSuchFieldError -> 0x0032 }
                io.netty.handler.ssl.ApplicationProtocolConfig$SelectorFailureBehavior r3 = p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior.NO_ADVERTISE     // Catch:{ NoSuchFieldError -> 0x0032 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0032 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0032 }
            L_0x0032:
                int[] r2 = f3733xb32e3251     // Catch:{ NoSuchFieldError -> 0x003c }
                io.netty.handler.ssl.ApplicationProtocolConfig$SelectorFailureBehavior r3 = p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL     // Catch:{ NoSuchFieldError -> 0x003c }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x003c }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x003c }
            L_0x003c:
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol[] r2 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                f3731xc16482e4 = r2
                int[] r2 = f3731xc16482e4     // Catch:{ NoSuchFieldError -> 0x004f }
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol r3 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.NPN     // Catch:{ NoSuchFieldError -> 0x004f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x004f }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x004f }
            L_0x004f:
                int[] r0 = f3731xc16482e4     // Catch:{ NoSuchFieldError -> 0x0059 }
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol r2 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.ALPN     // Catch:{ NoSuchFieldError -> 0x0059 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0059 }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0059 }
            L_0x0059:
                int[] r0 = f3731xc16482e4     // Catch:{ NoSuchFieldError -> 0x0064 }
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol r1 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.NPN_AND_ALPN     // Catch:{ NoSuchFieldError -> 0x0064 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0064 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0064 }
            L_0x0064:
                int[] r0 = f3731xc16482e4     // Catch:{ NoSuchFieldError -> 0x006f }
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol r1 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.NONE     // Catch:{ NoSuchFieldError -> 0x006f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006f }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006f }
            L_0x006f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.ReferenceCountedOpenSslContext.C57296.<clinit>():void");
        }
    }

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslContext$AbstractCertificateVerifier */
    static abstract class AbstractCertificateVerifier extends CertificateVerifier {
        private final OpenSslEngineMap engineMap;

        /* access modifiers changed from: 0000 */
        public abstract void verify(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, X509Certificate[] x509CertificateArr, String str) throws Exception;

        AbstractCertificateVerifier(OpenSslEngineMap openSslEngineMap) {
            this.engineMap = openSslEngineMap;
        }

        public final int verify(long j, byte[][] bArr, String str) {
            X509Certificate[] certificates = ReferenceCountedOpenSslContext.certificates(bArr);
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.engineMap.get(j);
            try {
                verify(referenceCountedOpenSslEngine, certificates, str);
                return CertificateVerifier.X509_V_OK;
            } catch (Throwable th) {
                ReferenceCountedOpenSslContext.logger.debug("verification of certificate failed", (Throwable) th);
                SSLHandshakeException sSLHandshakeException = new SSLHandshakeException("General OpenSslEngine problem");
                sSLHandshakeException.initCause(th);
                referenceCountedOpenSslEngine.handshakeException = sSLHandshakeException;
                if (th instanceof OpenSslCertificateException) {
                    return th.errorCode();
                }
                if (th instanceof CertificateExpiredException) {
                    return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
                }
                if (th instanceof CertificateNotYetValidException) {
                    return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
                }
                if (PlatformDependent.javaVersion() >= 7) {
                    if (th instanceof CertificateRevokedException) {
                        return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
                    }
                    for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
                        if (cause instanceof CertPathValidatorException) {
                            Reason reason = ((CertPathValidatorException) cause).getReason();
                            if (reason == BasicReason.EXPIRED) {
                                return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
                            }
                            if (reason == BasicReason.NOT_YET_VALID) {
                                return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
                            }
                            if (reason == BasicReason.REVOKED) {
                                return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
                            }
                        }
                    }
                }
                return CertificateVerifier.X509_V_ERR_UNSPECIFIED;
            }
        }
    }

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslContext$DefaultOpenSslEngineMap */
    private static final class DefaultOpenSslEngineMap implements OpenSslEngineMap {
        private final Map<Long, ReferenceCountedOpenSslEngine> engines;

        private DefaultOpenSslEngineMap() {
            this.engines = PlatformDependent.newConcurrentHashMap();
        }

        public ReferenceCountedOpenSslEngine remove(long j) {
            return (ReferenceCountedOpenSslEngine) this.engines.remove(Long.valueOf(j));
        }

        public void add(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) {
            this.engines.put(Long.valueOf(referenceCountedOpenSslEngine.sslPointer()), referenceCountedOpenSslEngine);
        }

        public ReferenceCountedOpenSslEngine get(long j) {
            return (ReferenceCountedOpenSslEngine) this.engines.get(Long.valueOf(j));
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract OpenSslKeyMaterialManager keyMaterialManager();

    public abstract OpenSslSessionContext sessionContext();

    /* JADX WARNING: Can't wrap try/catch for region: R(2:9|10) */
    /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
        r2 = logger;
        r3 = new java.lang.StringBuilder();
        r3.append("ReferenceCountedOpenSslContext supports -Djdk.tls.ephemeralDHKeySize={int}, but got: ");
        r3.append(r1);
        r2.debug(r3.toString());
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x00a6 */
    static {
        /*
            java.lang.Class<io.netty.handler.ssl.ReferenceCountedOpenSslContext> r0 = p043io.netty.handler.ssl.ReferenceCountedOpenSslContext.class
            io.netty.util.internal.logging.InternalLogger r1 = p043io.netty.util.internal.logging.InternalLoggerFactory.getInstance(r0)
            logger = r1
            io.netty.handler.ssl.ReferenceCountedOpenSslContext$1 r1 = new io.netty.handler.ssl.ReferenceCountedOpenSslContext$1
            r1.<init>()
            java.lang.Object r1 = java.security.AccessController.doPrivileged(r1)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            JDK_REJECT_CLIENT_INITIATED_RENEGOTIATION = r1
            io.netty.handler.ssl.ReferenceCountedOpenSslContext$2 r1 = new io.netty.handler.ssl.ReferenceCountedOpenSslContext$2
            r1.<init>()
            java.lang.Object r1 = java.security.AccessController.doPrivileged(r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE = r1
            io.netty.util.ResourceLeakDetectorFactory r1 = p043io.netty.util.ResourceLeakDetectorFactory.instance()
            io.netty.util.ResourceLeakDetector r0 = r1.newResourceLeakDetector(r0)
            leakDetector = r0
            io.netty.handler.ssl.ReferenceCountedOpenSslContext$4 r0 = new io.netty.handler.ssl.ReferenceCountedOpenSslContext$4
            r0.<init>()
            NONE_PROTOCOL_NEGOTIATOR = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 8
            java.lang.String[] r1 = new java.lang.String[r1]
            r2 = 0
            java.lang.String r3 = "ECDHE-ECDSA-AES256-GCM-SHA384"
            r1[r2] = r3
            r2 = 1
            java.lang.String r3 = "ECDHE-ECDSA-AES128-GCM-SHA256"
            r1[r2] = r3
            r2 = 2
            java.lang.String r3 = "ECDHE-RSA-AES128-GCM-SHA256"
            r1[r2] = r3
            r2 = 3
            java.lang.String r3 = "ECDHE-RSA-AES128-SHA"
            r1[r2] = r3
            r2 = 4
            java.lang.String r3 = "ECDHE-RSA-AES256-SHA"
            r1[r2] = r3
            r2 = 5
            java.lang.String r3 = "AES128-GCM-SHA256"
            r1[r2] = r3
            r2 = 6
            java.lang.String r3 = "AES128-SHA"
            r1[r2] = r3
            r2 = 7
            java.lang.String r3 = "AES256-SHA"
            r1[r2] = r3
            java.util.Collections.addAll(r0, r1)
            java.util.List r1 = java.util.Collections.unmodifiableList(r0)
            DEFAULT_CIPHERS = r1
            io.netty.util.internal.logging.InternalLogger r1 = logger
            boolean r1 = r1.isDebugEnabled()
            if (r1 == 0) goto L_0x0093
            io.netty.util.internal.logging.InternalLogger r1 = logger
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Default cipher suite (OpenSSL): "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.debug(r0)
        L_0x0093:
            r0 = 0
            io.netty.handler.ssl.ReferenceCountedOpenSslContext$5 r1 = new io.netty.handler.ssl.ReferenceCountedOpenSslContext$5     // Catch:{ all -> 0x00bc }
            r1.<init>()     // Catch:{ all -> 0x00bc }
            java.lang.Object r1 = java.security.AccessController.doPrivileged(r1)     // Catch:{ all -> 0x00bc }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x00bc }
            if (r1 == 0) goto L_0x00bc
            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)     // Catch:{ NumberFormatException -> 0x00a6 }
            goto L_0x00bc
        L_0x00a6:
            io.netty.util.internal.logging.InternalLogger r2 = logger     // Catch:{ all -> 0x00bc }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bc }
            r3.<init>()     // Catch:{ all -> 0x00bc }
            java.lang.String r4 = "ReferenceCountedOpenSslContext supports -Djdk.tls.ephemeralDHKeySize={int}, but got: "
            r3.append(r4)     // Catch:{ all -> 0x00bc }
            r3.append(r1)     // Catch:{ all -> 0x00bc }
            java.lang.String r1 = r3.toString()     // Catch:{ all -> 0x00bc }
            r2.debug(r1)     // Catch:{ all -> 0x00bc }
        L_0x00bc:
            DH_KEY_LENGTH = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.ReferenceCountedOpenSslContext.<clinit>():void");
    }

    ReferenceCountedOpenSslContext(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long j, long j2, int i, Certificate[] certificateArr, ClientAuth clientAuth2, String[] strArr, boolean z, boolean z2, boolean z3) throws SSLException {
        this(iterable, cipherSuiteFilter, toNegotiator(applicationProtocolConfig), j, j2, i, certificateArr, clientAuth2, strArr, z, z2, z3);
    }

    ReferenceCountedOpenSslContext(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long j, long j2, int i, Certificate[] certificateArr, ClientAuth clientAuth2, String[] strArr, boolean z, boolean z2, boolean z3) throws SSLException {
        Certificate[] certificateArr2;
        super(z);
        this.refCnt = new AbstractReferenceCounted() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<ReferenceCountedOpenSslContext> cls = ReferenceCountedOpenSslContext.class;
            }

            public ReferenceCounted touch(Object obj) {
                if (ReferenceCountedOpenSslContext.this.leak != null) {
                    ReferenceCountedOpenSslContext.this.leak.record(obj);
                }
                return ReferenceCountedOpenSslContext.this;
            }

            /* access modifiers changed from: protected */
            public void deallocate() {
                ReferenceCountedOpenSslContext.this.destroy();
                if (ReferenceCountedOpenSslContext.this.leak != null) {
                    ReferenceCountedOpenSslContext.this.leak.close(ReferenceCountedOpenSslContext.this);
                }
            }
        };
        ArrayList arrayList = null;
        this.engineMap = new DefaultOpenSslEngineMap();
        this.bioNonApplicationBufferSize = DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE;
        OpenSsl.ensureAvailability();
        if (z2 && !OpenSsl.isOcspSupported()) {
            throw new IllegalStateException("OCSP is not supported.");
        } else if (i == 1 || i == 0) {
            this.leak = z3 ? leakDetector.track(this) : null;
            this.mode = i;
            this.clientAuth = isServer() ? (ClientAuth) ObjectUtil.checkNotNull(clientAuth2, "clientAuth") : ClientAuth.NONE;
            this.protocols = strArr;
            this.enableOcsp = z2;
            if (i == 1) {
                this.rejectRemoteInitiatedRenegotiation = JDK_REJECT_CLIENT_INITIATED_RENEGOTIATION;
            }
            if (certificateArr == null) {
                certificateArr2 = null;
            } else {
                certificateArr2 = (Certificate[]) certificateArr.clone();
            }
            this.keyCertChain = certificateArr2;
            if (iterable != null) {
                arrayList = new ArrayList();
                for (String str : iterable) {
                    if (str == null) {
                        break;
                    }
                    String openSsl = CipherSuiteConverter.toOpenSsl(str);
                    if (openSsl != null) {
                        str = openSsl;
                    }
                    arrayList.add(str);
                }
            }
            this.unmodifiableCiphers = Arrays.asList(((CipherSuiteFilter) ObjectUtil.checkNotNull(cipherSuiteFilter, "cipherFilter")).filterCipherSuites(arrayList, DEFAULT_CIPHERS, OpenSsl.availableOpenSslCipherSuites()));
            this.apn = (OpenSslApplicationProtocolNegotiator) ObjectUtil.checkNotNull(openSslApplicationProtocolNegotiator, "apn");
            try {
                synchronized (ReferenceCountedOpenSslContext.class) {
                    try {
                        this.ctx = SSLContext.make(31, i);
                        SSLContext.setOptions(this.ctx, SSLContext.getOptions(this.ctx) | SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_CIPHER_SERVER_PREFERENCE | SSL.SSL_OP_NO_COMPRESSION | SSL.SSL_OP_NO_TICKET);
                        SSLContext.setMode(this.ctx, SSLContext.getMode(this.ctx) | SSL.SSL_MODE_ACCEPT_MOVING_WRITE_BUFFER);
                        if (DH_KEY_LENGTH != null) {
                            SSLContext.setTmpDHLength(this.ctx, DH_KEY_LENGTH.intValue());
                        }
                        SSLContext.setCipherSuite(this.ctx, CipherSuiteConverter.toOpenSsl((Iterable<String>) this.unmodifiableCiphers));
                        List protocols2 = openSslApplicationProtocolNegotiator.protocols();
                        if (!protocols2.isEmpty()) {
                            String[] strArr2 = (String[]) protocols2.toArray(new String[protocols2.size()]);
                            int opensslSelectorFailureBehavior = opensslSelectorFailureBehavior(openSslApplicationProtocolNegotiator.selectorFailureBehavior());
                            int i2 = C57296.f3731xc16482e4[openSslApplicationProtocolNegotiator.protocol().ordinal()];
                            if (i2 == 1) {
                                SSLContext.setNpnProtos(this.ctx, strArr2, opensslSelectorFailureBehavior);
                            } else if (i2 == 2) {
                                SSLContext.setAlpnProtos(this.ctx, strArr2, opensslSelectorFailureBehavior);
                            } else if (i2 == 3) {
                                SSLContext.setNpnProtos(this.ctx, strArr2, opensslSelectorFailureBehavior);
                                SSLContext.setAlpnProtos(this.ctx, strArr2, opensslSelectorFailureBehavior);
                            } else {
                                throw new Error();
                            }
                        }
                        if (j > 0) {
                            this.sessionCacheSize = j;
                            SSLContext.setSessionCacheSize(this.ctx, j);
                        } else {
                            long sessionCacheSize2 = SSLContext.setSessionCacheSize(this.ctx, 20480);
                            this.sessionCacheSize = sessionCacheSize2;
                            SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize2);
                        }
                        if (j2 > 0) {
                            this.sessionTimeout = j2;
                            SSLContext.setSessionCacheTimeout(this.ctx, j2);
                        } else {
                            long sessionCacheTimeout = SSLContext.setSessionCacheTimeout(this.ctx, 300);
                            this.sessionTimeout = sessionCacheTimeout;
                            SSLContext.setSessionCacheTimeout(this.ctx, sessionCacheTimeout);
                        }
                        if (z2) {
                            SSLContext.enableOcsp(this.ctx, isClient());
                        }
                    } catch (Exception e) {
                        throw new SSLException("failed to create an SSL_CTX", e);
                    } catch (SSLException e2) {
                        throw e2;
                    } catch (Exception e3) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("failed to set cipher suite: ");
                        sb.append(this.unmodifiableCiphers);
                        throw new SSLException(sb.toString(), e3);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (Throwable th2) {
                release();
                throw th2;
            }
        } else {
            throw new IllegalArgumentException("mode most be either SSL.SSL_MODE_SERVER or SSL.SSL_MODE_CLIENT");
        }
    }

    private static int opensslSelectorFailureBehavior(SelectorFailureBehavior selectorFailureBehavior) {
        int i = C57296.f3733xb32e3251[selectorFailureBehavior.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        throw new Error();
    }

    public final List<String> cipherSuites() {
        return this.unmodifiableCiphers;
    }

    public final long sessionCacheSize() {
        return this.sessionCacheSize;
    }

    public final long sessionTimeout() {
        return this.sessionTimeout;
    }

    public ApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.apn;
    }

    public final boolean isClient() {
        return this.mode == 0;
    }

    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator, String str, int i) {
        return newEngine0(byteBufAllocator, str, i);
    }

    /* access modifiers changed from: 0000 */
    public SSLEngine newEngine0(ByteBufAllocator byteBufAllocator, String str, int i) {
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = new ReferenceCountedOpenSslEngine(this, byteBufAllocator, str, i, true);
        return referenceCountedOpenSslEngine;
    }

    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator) {
        return newEngine(byteBufAllocator, null, -1);
    }

    @Deprecated
    public final long context() {
        return this.ctx;
    }

    @Deprecated
    public final OpenSslSessionStats stats() {
        return sessionContext().stats();
    }

    public void setRejectRemoteInitiatedRenegotiation(boolean z) {
        this.rejectRemoteInitiatedRenegotiation = z;
    }

    public boolean getRejectRemoteInitiatedRenegotiation() {
        return this.rejectRemoteInitiatedRenegotiation;
    }

    public void setBioNonApplicationBufferSize(int i) {
        this.bioNonApplicationBufferSize = ObjectUtil.checkPositiveOrZero(i, "bioNonApplicationBufferSize");
    }

    public int getBioNonApplicationBufferSize() {
        return this.bioNonApplicationBufferSize;
    }

    @Deprecated
    public final void setTicketKeys(byte[] bArr) {
        sessionContext().setTicketKeys(bArr);
    }

    public final long sslCtxPointer() {
        return this.ctx;
    }

    /* access modifiers changed from: 0000 */
    public final void destroy() {
        synchronized (ReferenceCountedOpenSslContext.class) {
            if (this.ctx != 0) {
                if (this.enableOcsp) {
                    SSLContext.disableOcsp(this.ctx);
                }
                SSLContext.free(this.ctx);
                this.ctx = 0;
            }
        }
    }

    protected static X509Certificate[] certificates(byte[][] bArr) {
        X509Certificate[] x509CertificateArr = new X509Certificate[bArr.length];
        for (int i = 0; i < x509CertificateArr.length; i++) {
            x509CertificateArr[i] = new OpenSslX509Certificate(bArr[i]);
        }
        return x509CertificateArr;
    }

    protected static X509TrustManager chooseTrustManager(TrustManager[] trustManagerArr) {
        for (X509TrustManager x509TrustManager : trustManagerArr) {
            if (x509TrustManager instanceof X509TrustManager) {
                return x509TrustManager;
            }
        }
        throw new IllegalStateException("no X509TrustManager found");
    }

    protected static X509KeyManager chooseX509KeyManager(KeyManager[] keyManagerArr) {
        for (X509KeyManager x509KeyManager : keyManagerArr) {
            if (x509KeyManager instanceof X509KeyManager) {
                return x509KeyManager;
            }
        }
        throw new IllegalStateException("no X509KeyManager found");
    }

    static OpenSslApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig applicationProtocolConfig) {
        if (applicationProtocolConfig == null) {
            return NONE_PROTOCOL_NEGOTIATOR;
        }
        int i = C57296.f3731xc16482e4[applicationProtocolConfig.protocol().ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            int i2 = C57296.f3732xcbdfafc1[applicationProtocolConfig.selectedListenerFailureBehavior().ordinal()];
            String str = " behavior";
            String str2 = "OpenSSL provider does not support ";
            if (i2 == 1 || i2 == 2) {
                int i3 = C57296.f3733xb32e3251[applicationProtocolConfig.selectorFailureBehavior().ordinal()];
                if (i3 == 1 || i3 == 2) {
                    return new OpenSslDefaultApplicationProtocolNegotiator(applicationProtocolConfig);
                }
                StringBuilder sb = new StringBuilder(str2);
                sb.append(applicationProtocolConfig.selectorFailureBehavior());
                sb.append(str);
                throw new UnsupportedOperationException(sb.toString());
            }
            StringBuilder sb2 = new StringBuilder(str2);
            sb2.append(applicationProtocolConfig.selectedListenerFailureBehavior());
            sb2.append(str);
            throw new UnsupportedOperationException(sb2.toString());
        } else if (i == 4) {
            return NONE_PROTOCOL_NEGOTIATOR;
        } else {
            throw new Error();
        }
    }

    static boolean useExtendedTrustManager(X509TrustManager x509TrustManager) {
        return PlatformDependent.javaVersion() >= 7 && (x509TrustManager instanceof X509ExtendedTrustManager);
    }

    static boolean useExtendedKeyManager(X509KeyManager x509KeyManager) {
        return PlatformDependent.javaVersion() >= 7 && (x509KeyManager instanceof X509ExtendedKeyManager);
    }

    public final int refCnt() {
        return this.refCnt.refCnt();
    }

    public final ReferenceCounted retain() {
        this.refCnt.retain();
        return this;
    }

    public final ReferenceCounted retain(int i) {
        this.refCnt.retain(i);
        return this;
    }

    public final ReferenceCounted touch() {
        this.refCnt.touch();
        return this;
    }

    public final ReferenceCounted touch(Object obj) {
        this.refCnt.touch(obj);
        return this;
    }

    public final boolean release() {
        return this.refCnt.release();
    }

    public final boolean release(int i) {
        return this.refCnt.release(i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x007f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void setKeyMaterial(long r15, java.security.cert.X509Certificate[] r17, java.security.PrivateKey r18, java.lang.String r19) throws javax.net.ssl.SSLException {
        /*
            r1 = 0
            r3 = 0
            io.netty.buffer.ByteBufAllocator r0 = p043io.netty.buffer.ByteBufAllocator.DEFAULT     // Catch:{ SSLException -> 0x006f, Exception -> 0x0064, all -> 0x0060 }
            r4 = 1
            r5 = r17
            io.netty.handler.ssl.PemEncoded r3 = p043io.netty.handler.ssl.PemX509Certificate.toPEM(r0, r4, r5)     // Catch:{ SSLException -> 0x006f, Exception -> 0x0064, all -> 0x0060 }
            io.netty.buffer.ByteBufAllocator r0 = p043io.netty.buffer.ByteBufAllocator.DEFAULT     // Catch:{ SSLException -> 0x006f, Exception -> 0x0064, all -> 0x0060 }
            io.netty.handler.ssl.PemEncoded r5 = r3.retain()     // Catch:{ SSLException -> 0x006f, Exception -> 0x0064, all -> 0x0060 }
            long r13 = toBIO(r0, r5)     // Catch:{ SSLException -> 0x006f, Exception -> 0x0064, all -> 0x0060 }
            io.netty.buffer.ByteBufAllocator r0 = p043io.netty.buffer.ByteBufAllocator.DEFAULT     // Catch:{ SSLException -> 0x005d, Exception -> 0x005a, all -> 0x0057 }
            io.netty.handler.ssl.PemEncoded r5 = r3.retain()     // Catch:{ SSLException -> 0x005d, Exception -> 0x005a, all -> 0x0057 }
            long r10 = toBIO(r0, r5)     // Catch:{ SSLException -> 0x005d, Exception -> 0x005a, all -> 0x0057 }
            if (r18 == 0) goto L_0x0031
            long r1 = toBIO(r18)     // Catch:{ SSLException -> 0x002e, Exception -> 0x002b, all -> 0x0027 }
            goto L_0x0031
        L_0x0027:
            r0 = move-exception
            r4 = r10
            goto L_0x0074
        L_0x002b:
            r0 = move-exception
            r4 = r10
            goto L_0x0067
        L_0x002e:
            r0 = move-exception
            r4 = r10
            goto L_0x0072
        L_0x0031:
            if (r19 != 0) goto L_0x0037
            java.lang.String r0 = ""
            r12 = r0
            goto L_0x0039
        L_0x0037:
            r12 = r19
        L_0x0039:
            r6 = r15
            r8 = r13
            r4 = r10
            r10 = r1
            io.netty.internal.tcnative.SSLContext.setCertificateBio(r6, r8, r10, r12)     // Catch:{ SSLException -> 0x0055, Exception -> 0x0053 }
            r0 = 1
            io.netty.internal.tcnative.SSLContext.setCertificateChainBio(r6, r4, r0)     // Catch:{ SSLException -> 0x0055, Exception -> 0x0053 }
            freeBio(r1)
            freeBio(r13)
            freeBio(r4)
            if (r3 == 0) goto L_0x0052
            r3.release()
        L_0x0052:
            return
        L_0x0053:
            r0 = move-exception
            goto L_0x0067
        L_0x0055:
            r0 = move-exception
            goto L_0x0072
        L_0x0057:
            r0 = move-exception
            r4 = r1
            goto L_0x0074
        L_0x005a:
            r0 = move-exception
            r4 = r1
            goto L_0x0067
        L_0x005d:
            r0 = move-exception
            r4 = r1
            goto L_0x0072
        L_0x0060:
            r0 = move-exception
            r4 = r1
            r13 = r4
            goto L_0x0074
        L_0x0064:
            r0 = move-exception
            r4 = r1
            r13 = r4
        L_0x0067:
            javax.net.ssl.SSLException r6 = new javax.net.ssl.SSLException     // Catch:{ all -> 0x0073 }
            java.lang.String r7 = "failed to set certificate and key"
            r6.<init>(r7, r0)     // Catch:{ all -> 0x0073 }
            throw r6     // Catch:{ all -> 0x0073 }
        L_0x006f:
            r0 = move-exception
            r4 = r1
            r13 = r4
        L_0x0072:
            throw r0     // Catch:{ all -> 0x0073 }
        L_0x0073:
            r0 = move-exception
        L_0x0074:
            freeBio(r1)
            freeBio(r13)
            freeBio(r4)
            if (r3 == 0) goto L_0x0082
            r3.release()
        L_0x0082:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.ReferenceCountedOpenSslContext.setKeyMaterial(long, java.security.cert.X509Certificate[], java.security.PrivateKey, java.lang.String):void");
    }

    static void freeBio(long j) {
        if (j != 0) {
            SSL.freeBIO(j);
        }
    }

    static long toBIO(PrivateKey privateKey) throws Exception {
        if (privateKey == null) {
            return 0;
        }
        ByteBufAllocator byteBufAllocator = ByteBufAllocator.DEFAULT;
        PemEncoded pem = PemPrivateKey.toPEM(byteBufAllocator, true, privateKey);
        try {
            return toBIO(byteBufAllocator, pem.retain());
        } finally {
            pem.release();
        }
    }

    static long toBIO(X509Certificate... x509CertificateArr) throws Exception {
        if (x509CertificateArr == null) {
            return 0;
        }
        if (x509CertificateArr.length != 0) {
            ByteBufAllocator byteBufAllocator = ByteBufAllocator.DEFAULT;
            PemEncoded pem = PemX509Certificate.toPEM(byteBufAllocator, true, x509CertificateArr);
            try {
                return toBIO(byteBufAllocator, pem.retain());
            } finally {
                pem.release();
            }
        } else {
            throw new IllegalArgumentException("certChain can't be empty");
        }
    }

    static long toBIO(ByteBufAllocator byteBufAllocator, PemEncoded pemEncoded) throws Exception {
        ByteBuf directBuffer;
        try {
            ByteBuf content = pemEncoded.content();
            if (content.isDirect()) {
                long newBIO = newBIO(content.retainedSlice());
                pemEncoded.release();
                return newBIO;
            }
            directBuffer = byteBufAllocator.directBuffer(content.readableBytes());
            directBuffer.writeBytes(content, content.readerIndex(), content.readableBytes());
            long newBIO2 = newBIO(directBuffer.retainedSlice());
            if (pemEncoded.isSensitive()) {
                SslUtils.zeroout(directBuffer);
            }
            directBuffer.release();
            pemEncoded.release();
            return newBIO2;
        } catch (Throwable th) {
            pemEncoded.release();
            throw th;
        }
    }

    private static long newBIO(ByteBuf byteBuf) throws Exception {
        try {
            long newMemBIO = SSL.newMemBIO();
            int readableBytes = byteBuf.readableBytes();
            if (SSL.bioWrite(newMemBIO, OpenSsl.memoryAddress(byteBuf) + ((long) byteBuf.readerIndex()), readableBytes) == readableBytes) {
                return newMemBIO;
            }
            SSL.freeBIO(newMemBIO);
            throw new IllegalStateException("Could not write data to memory BIO");
        } finally {
            byteBuf.release();
        }
    }
}
