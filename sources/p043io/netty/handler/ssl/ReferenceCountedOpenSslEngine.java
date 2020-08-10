package p043io.netty.handler.ssl;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import io.netty.internal.tcnative.Buffer;
import io.netty.internal.tcnative.SSL;
import java.nio.ByteBuffer;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import p043io.netty.util.AbstractReferenceCounted;
import p043io.netty.util.ReferenceCounted;
import p043io.netty.util.ResourceLeakDetector;
import p043io.netty.util.ResourceLeakDetectorFactory;
import p043io.netty.util.ResourceLeakTracker;
import p043io.netty.util.internal.EmptyArrays;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.ThrowableUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslEngine */
public class ReferenceCountedOpenSslEngine extends SSLEngine implements ReferenceCounted {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final SSLException BEGIN_HANDSHAKE_ENGINE_CLOSED;
    private static final SSLEngineResult CLOSED_NOT_HANDSHAKING = new SSLEngineResult(Status.CLOSED, HandshakeStatus.NOT_HANDSHAKING, 0, 0);
    private static final int DEFAULT_HOSTNAME_VALIDATION_FLAGS = 0;
    private static final AtomicIntegerFieldUpdater<ReferenceCountedOpenSslEngine> DESTROYED_UPDATER;
    private static final SSLException HANDSHAKE_ENGINE_CLOSED;
    private static final String INVALID_CIPHER = "SSL_NULL_WITH_NULL_NULL";
    static final int MAX_ENCRYPTED_PACKET_LENGTH = 16474;
    static final int MAX_PLAINTEXT_LENGTH = 16384;
    static final int MAX_TLS_RECORD_OVERHEAD_LENGTH = 90;
    private static final SSLEngineResult NEED_UNWRAP_CLOSED = new SSLEngineResult(Status.CLOSED, HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_UNWRAP_OK = new SSLEngineResult(Status.OK, HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_CLOSED = new SSLEngineResult(Status.CLOSED, HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_OK = new SSLEngineResult(Status.OK, HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLException RENEGOTIATION_UNSUPPORTED;
    private static final ResourceLeakDetector<ReferenceCountedOpenSslEngine> leakDetector;
    private static final InternalLogger logger;
    private Object algorithmConstraints;
    private final ByteBufAllocator alloc;
    /* access modifiers changed from: private */
    public final OpenSslApplicationProtocolNegotiator apn;
    private boolean certificateSet;
    private volatile ClientAuth clientAuth = ClientAuth.NONE;
    /* access modifiers changed from: private */
    public final boolean clientMode;
    private volatile int destroyed;
    private final boolean enableOcsp;
    private String endPointIdentificationAlgorithm;
    private final OpenSslEngineMap engineMap;
    SSLHandshakeException handshakeException;
    /* access modifiers changed from: private */
    public HandshakeState handshakeState = HandshakeState.NOT_STARTED;
    private boolean isInboundDone;
    private final OpenSslKeyMaterialManager keyMaterialManager;
    /* access modifiers changed from: private */
    public volatile long lastAccessed = -1;
    /* access modifiers changed from: private */
    public final ResourceLeakTracker<ReferenceCountedOpenSslEngine> leak;
    /* access modifiers changed from: private */
    public final Certificate[] localCerts;
    private volatile Collection<?> matchers;
    private long networkBIO;
    private boolean outboundClosed;
    private boolean receivedShutdown;
    private final AbstractReferenceCounted refCnt = new AbstractReferenceCounted() {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<ReferenceCountedOpenSslEngine> cls = ReferenceCountedOpenSslEngine.class;
        }

        public ReferenceCounted touch(Object obj) {
            if (ReferenceCountedOpenSslEngine.this.leak != null) {
                ReferenceCountedOpenSslEngine.this.leak.record(obj);
            }
            return ReferenceCountedOpenSslEngine.this;
        }

        /* access modifiers changed from: protected */
        public void deallocate() {
            ReferenceCountedOpenSslEngine.this.shutdown();
            if (ReferenceCountedOpenSslEngine.this.leak != null) {
                ReferenceCountedOpenSslEngine.this.leak.close(ReferenceCountedOpenSslEngine.this);
            }
        }
    };
    private final boolean rejectRemoteInitiatedRenegotiation;
    private boolean renegotiationPending;
    private final OpenSslSession session;
    private final ByteBuffer[] singleDstBuffer = new ByteBuffer[1];
    private final ByteBuffer[] singleSrcBuffer = new ByteBuffer[1];
    private List<String> sniHostNames;
    /* access modifiers changed from: private */
    public long ssl;

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslEngine$2 */
    static /* synthetic */ class C57312 {

        /* renamed from: $SwitchMap$io$netty$handler$ssl$ApplicationProtocolConfig$Protocol */
        static final /* synthetic */ int[] f3734xc16482e4 = new int[Protocol.values().length];
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$ssl$ClientAuth = new int[ClientAuth.values().length];

        /* renamed from: $SwitchMap$io$netty$handler$ssl$ReferenceCountedOpenSslEngine$HandshakeState */
        static final /* synthetic */ int[] f3735xea902ccf = new int[HandshakeState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(25:0|(2:1|2)|3|(2:5|6)|7|(2:9|10)|11|(2:13|14)|15|17|18|19|20|21|22|23|25|26|27|28|29|30|31|32|34) */
        /* JADX WARNING: Can't wrap try/catch for region: R(27:0|1|2|3|(2:5|6)|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|25|26|27|28|29|30|31|32|34) */
        /* JADX WARNING: Can't wrap try/catch for region: R(28:0|1|2|3|5|6|7|(2:9|10)|11|13|14|15|17|18|19|20|21|22|23|25|26|27|28|29|30|31|32|34) */
        /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0048 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0052 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x006f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0079 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0083 */
        static {
            /*
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol[] r0 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f3734xc16482e4 = r0
                r0 = 1
                int[] r1 = f3734xc16482e4     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol r2 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.NONE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = f3734xc16482e4     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol r3 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.ALPN     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                r2 = 3
                int[] r3 = f3734xc16482e4     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol r4 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.NPN     // Catch:{ NoSuchFieldError -> 0x002a }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                r3 = 4
                int[] r4 = f3734xc16482e4     // Catch:{ NoSuchFieldError -> 0x0035 }
                io.netty.handler.ssl.ApplicationProtocolConfig$Protocol r5 = p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol.NPN_AND_ALPN     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r4[r5] = r3     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                io.netty.handler.ssl.ClientAuth[] r4 = p043io.netty.handler.ssl.ClientAuth.values()
                int r4 = r4.length
                int[] r4 = new int[r4]
                $SwitchMap$io$netty$handler$ssl$ClientAuth = r4
                int[] r4 = $SwitchMap$io$netty$handler$ssl$ClientAuth     // Catch:{ NoSuchFieldError -> 0x0048 }
                io.netty.handler.ssl.ClientAuth r5 = p043io.netty.handler.ssl.ClientAuth.NONE     // Catch:{ NoSuchFieldError -> 0x0048 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0048 }
                r4[r5] = r0     // Catch:{ NoSuchFieldError -> 0x0048 }
            L_0x0048:
                int[] r4 = $SwitchMap$io$netty$handler$ssl$ClientAuth     // Catch:{ NoSuchFieldError -> 0x0052 }
                io.netty.handler.ssl.ClientAuth r5 = p043io.netty.handler.ssl.ClientAuth.REQUIRE     // Catch:{ NoSuchFieldError -> 0x0052 }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x0052 }
                r4[r5] = r1     // Catch:{ NoSuchFieldError -> 0x0052 }
            L_0x0052:
                int[] r4 = $SwitchMap$io$netty$handler$ssl$ClientAuth     // Catch:{ NoSuchFieldError -> 0x005c }
                io.netty.handler.ssl.ClientAuth r5 = p043io.netty.handler.ssl.ClientAuth.OPTIONAL     // Catch:{ NoSuchFieldError -> 0x005c }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x005c }
                r4[r5] = r2     // Catch:{ NoSuchFieldError -> 0x005c }
            L_0x005c:
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState[] r4 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.values()
                int r4 = r4.length
                int[] r4 = new int[r4]
                f3735xea902ccf = r4
                int[] r4 = f3735xea902ccf     // Catch:{ NoSuchFieldError -> 0x006f }
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r5 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED     // Catch:{ NoSuchFieldError -> 0x006f }
                int r5 = r5.ordinal()     // Catch:{ NoSuchFieldError -> 0x006f }
                r4[r5] = r0     // Catch:{ NoSuchFieldError -> 0x006f }
            L_0x006f:
                int[] r0 = f3735xea902ccf     // Catch:{ NoSuchFieldError -> 0x0079 }
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r4 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.FINISHED     // Catch:{ NoSuchFieldError -> 0x0079 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0079 }
                r0[r4] = r1     // Catch:{ NoSuchFieldError -> 0x0079 }
            L_0x0079:
                int[] r0 = f3735xea902ccf     // Catch:{ NoSuchFieldError -> 0x0083 }
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r1 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_IMPLICITLY     // Catch:{ NoSuchFieldError -> 0x0083 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0083 }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0083 }
            L_0x0083:
                int[] r0 = f3735xea902ccf     // Catch:{ NoSuchFieldError -> 0x008d }
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r1 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY     // Catch:{ NoSuchFieldError -> 0x008d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x008d }
                r0[r1] = r3     // Catch:{ NoSuchFieldError -> 0x008d }
            L_0x008d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.C57312.<clinit>():void");
        }
    }

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState */
    private enum HandshakeState {
        NOT_STARTED,
        STARTED_IMPLICITLY,
        STARTED_EXPLICITLY,
        FINISHED
    }

    /* renamed from: io.netty.handler.ssl.ReferenceCountedOpenSslEngine$OpenSslSession */
    private final class OpenSslSession implements SSLSession, ApplicationProtocolAccessor {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private String applicationProtocol;
        private String cipher;
        private long creationTime;

        /* renamed from: id */
        private byte[] f3736id;
        private Certificate[] peerCerts;
        private String protocol;
        private final OpenSslSessionContext sessionContext;
        private Map<String, Object> values;
        private X509Certificate[] x509PeerCerts;

        public int getApplicationBufferSize() {
            return 16384;
        }

        public int getPacketBufferSize() {
            return ReferenceCountedOpenSslEngine.MAX_ENCRYPTED_PACKET_LENGTH;
        }

        static {
            Class<ReferenceCountedOpenSslEngine> cls = ReferenceCountedOpenSslEngine.class;
        }

        OpenSslSession(OpenSslSessionContext openSslSessionContext) {
            this.sessionContext = openSslSessionContext;
        }

        public byte[] getId() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (this.f3736id == null) {
                    byte[] bArr = EmptyArrays.EMPTY_BYTES;
                    return bArr;
                }
                byte[] bArr2 = (byte[]) this.f3736id.clone();
                return bArr2;
            }
        }

        public SSLSessionContext getSessionContext() {
            return this.sessionContext;
        }

        public long getCreationTime() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (this.creationTime == 0 && !ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    this.creationTime = SSL.getTime(ReferenceCountedOpenSslEngine.this.ssl) * 1000;
                }
            }
            return this.creationTime;
        }

        public long getLastAccessedTime() {
            long access$300 = ReferenceCountedOpenSslEngine.this.lastAccessed;
            return access$300 == -1 ? getCreationTime() : access$300;
        }

        public void invalidate() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    SSL.setTimeout(ReferenceCountedOpenSslEngine.this.ssl, 0);
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0031, code lost:
            return r2;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean isValid() {
            /*
                r9 = this;
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine r0 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.this
                monitor-enter(r0)
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine r1 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.this     // Catch:{ all -> 0x0034 }
                boolean r1 = r1.isDestroyed()     // Catch:{ all -> 0x0034 }
                r2 = 0
                if (r1 != 0) goto L_0x0032
                long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0034 }
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine r1 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.this     // Catch:{ all -> 0x0034 }
                long r5 = r1.ssl     // Catch:{ all -> 0x0034 }
                long r5 = io.netty.internal.tcnative.SSL.getTimeout(r5)     // Catch:{ all -> 0x0034 }
                r7 = 1000(0x3e8, double:4.94E-321)
                long r5 = r5 * r7
                long r3 = r3 - r5
                io.netty.handler.ssl.ReferenceCountedOpenSslEngine r1 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.this     // Catch:{ all -> 0x0034 }
                long r5 = r1.ssl     // Catch:{ all -> 0x0034 }
                long r5 = io.netty.internal.tcnative.SSL.getTime(r5)     // Catch:{ all -> 0x0034 }
                long r5 = r5 * r7
                int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r1 >= 0) goto L_0x0030
                r2 = 1
            L_0x0030:
                monitor-exit(r0)     // Catch:{ all -> 0x0034 }
                return r2
            L_0x0032:
                monitor-exit(r0)     // Catch:{ all -> 0x0034 }
                return r2
            L_0x0034:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0034 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.OpenSslSession.isValid():boolean");
        }

        public void putValue(String str, Object obj) {
            if (str == null) {
                throw new NullPointerException(PostalAddressParser.USER_ADDRESS_NAME_KEY);
            } else if (obj != null) {
                Map map = this.values;
                if (map == null) {
                    map = new HashMap(2);
                    this.values = map;
                }
                Object put = map.put(str, obj);
                if (obj instanceof SSLSessionBindingListener) {
                    ((SSLSessionBindingListener) obj).valueBound(new SSLSessionBindingEvent(this, str));
                }
                notifyUnbound(put, str);
            } else {
                throw new NullPointerException(Param.VALUE);
            }
        }

        public Object getValue(String str) {
            if (str != null) {
                Map<String, Object> map = this.values;
                if (map == null) {
                    return null;
                }
                return map.get(str);
            }
            throw new NullPointerException(PostalAddressParser.USER_ADDRESS_NAME_KEY);
        }

        public void removeValue(String str) {
            if (str != null) {
                Map<String, Object> map = this.values;
                if (map != null) {
                    notifyUnbound(map.remove(str), str);
                    return;
                }
                return;
            }
            throw new NullPointerException(PostalAddressParser.USER_ADDRESS_NAME_KEY);
        }

        public String[] getValueNames() {
            Map<String, Object> map = this.values;
            if (map == null || map.isEmpty()) {
                return EmptyArrays.EMPTY_STRINGS;
            }
            return (String[]) map.keySet().toArray(new String[map.size()]);
        }

        private void notifyUnbound(Object obj, String str) {
            if (obj instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener) obj).valueUnbound(new SSLSessionBindingEvent(this, str));
            }
        }

        /* access modifiers changed from: 0000 */
        public void handshakeFinished() throws SSLException {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    this.f3736id = SSL.getSessionId(ReferenceCountedOpenSslEngine.this.ssl);
                    this.cipher = ReferenceCountedOpenSslEngine.this.toJavaCipherSuite(SSL.getCipherForSSL(ReferenceCountedOpenSslEngine.this.ssl));
                    this.protocol = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
                    initPeerCerts();
                    selectApplicationProtocol();
                    ReferenceCountedOpenSslEngine.this.handshakeState = HandshakeState.FINISHED;
                } else {
                    throw new SSLException("Already closed");
                }
            }
        }

        private void initPeerCerts() {
            byte[][] peerCertChain = SSL.getPeerCertChain(ReferenceCountedOpenSslEngine.this.ssl);
            if (!ReferenceCountedOpenSslEngine.this.clientMode) {
                byte[] peerCertificate = SSL.getPeerCertificate(ReferenceCountedOpenSslEngine.this.ssl);
                if (ReferenceCountedOpenSslEngine.isEmpty(peerCertificate)) {
                    this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
                    this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
                } else if (ReferenceCountedOpenSslEngine.isEmpty((Object[]) peerCertChain)) {
                    this.peerCerts = new Certificate[]{new OpenSslX509Certificate(peerCertificate)};
                    this.x509PeerCerts = new X509Certificate[]{new OpenSslJavaxX509Certificate(peerCertificate)};
                } else {
                    this.peerCerts = new Certificate[(peerCertChain.length + 1)];
                    this.x509PeerCerts = new X509Certificate[(peerCertChain.length + 1)];
                    this.peerCerts[0] = new OpenSslX509Certificate(peerCertificate);
                    this.x509PeerCerts[0] = new OpenSslJavaxX509Certificate(peerCertificate);
                    initCerts(peerCertChain, 1);
                }
            } else if (ReferenceCountedOpenSslEngine.isEmpty((Object[]) peerCertChain)) {
                this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
                this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
            } else {
                this.peerCerts = new Certificate[peerCertChain.length];
                this.x509PeerCerts = new X509Certificate[peerCertChain.length];
                initCerts(peerCertChain, 0);
            }
        }

        private void initCerts(byte[][] bArr, int i) {
            for (int i2 = 0; i2 < bArr.length; i2++) {
                int i3 = i + i2;
                this.peerCerts[i3] = new OpenSslX509Certificate(bArr[i2]);
                this.x509PeerCerts[i3] = new OpenSslJavaxX509Certificate(bArr[i2]);
            }
        }

        private void selectApplicationProtocol() throws SSLException {
            SelectedListenerFailureBehavior selectedListenerFailureBehavior = ReferenceCountedOpenSslEngine.this.apn.selectedListenerFailureBehavior();
            List protocols = ReferenceCountedOpenSslEngine.this.apn.protocols();
            int i = C57312.f3734xc16482e4[ReferenceCountedOpenSslEngine.this.apn.protocol().ordinal()];
            if (i == 1) {
                return;
            }
            if (i == 2) {
                String alpnSelected = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
                if (alpnSelected != null) {
                    this.applicationProtocol = selectApplicationProtocol(protocols, selectedListenerFailureBehavior, alpnSelected);
                }
            } else if (i == 3) {
                String nextProtoNegotiated = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
                if (nextProtoNegotiated != null) {
                    this.applicationProtocol = selectApplicationProtocol(protocols, selectedListenerFailureBehavior, nextProtoNegotiated);
                }
            } else if (i == 4) {
                String alpnSelected2 = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
                if (alpnSelected2 == null) {
                    alpnSelected2 = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
                }
                if (alpnSelected2 != null) {
                    this.applicationProtocol = selectApplicationProtocol(protocols, selectedListenerFailureBehavior, alpnSelected2);
                }
            } else {
                throw new Error();
            }
        }

        private String selectApplicationProtocol(List<String> list, SelectedListenerFailureBehavior selectedListenerFailureBehavior, String str) throws SSLException {
            if (selectedListenerFailureBehavior == SelectedListenerFailureBehavior.ACCEPT) {
                return str;
            }
            int size = list.size();
            if (list.contains(str)) {
                return str;
            }
            if (selectedListenerFailureBehavior == SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL) {
                return (String) list.get(size - 1);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("unknown protocol ");
            sb.append(str);
            throw new SSLException(sb.toString());
        }

        public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
            Certificate[] certificateArr;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (!ReferenceCountedOpenSslEngine.isEmpty((Object[]) this.peerCerts)) {
                    certificateArr = (Certificate[]) this.peerCerts.clone();
                } else {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
            }
            return certificateArr;
        }

        public Certificate[] getLocalCertificates() {
            if (ReferenceCountedOpenSslEngine.this.localCerts == null) {
                return null;
            }
            return (Certificate[]) ReferenceCountedOpenSslEngine.this.localCerts.clone();
        }

        public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
            X509Certificate[] x509CertificateArr;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (!ReferenceCountedOpenSslEngine.isEmpty((Object[]) this.x509PeerCerts)) {
                    x509CertificateArr = (X509Certificate[]) this.x509PeerCerts.clone();
                } else {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
            }
            return x509CertificateArr;
        }

        public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
            return ((java.security.cert.X509Certificate) getPeerCertificates()[0]).getSubjectX500Principal();
        }

        public Principal getLocalPrincipal() {
            Certificate[] access$1000 = ReferenceCountedOpenSslEngine.this.localCerts;
            if (access$1000 == null || access$1000.length == 0) {
                return null;
            }
            return ((java.security.cert.X509Certificate) access$1000[0]).getIssuerX500Principal();
        }

        public String getCipherSuite() {
            synchronized (ReferenceCountedOpenSslEngine.this) {
                if (this.cipher == null) {
                    String str = ReferenceCountedOpenSslEngine.INVALID_CIPHER;
                    return str;
                }
                String str2 = this.cipher;
                return str2;
            }
        }

        public String getProtocol() {
            String str = this.protocol;
            if (str == null) {
                synchronized (ReferenceCountedOpenSslEngine.this) {
                    str = !ReferenceCountedOpenSslEngine.this.isDestroyed() ? SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl) : "";
                }
            }
            return str;
        }

        public String getApplicationProtocol() {
            String str;
            synchronized (ReferenceCountedOpenSslEngine.this) {
                str = this.applicationProtocol;
            }
            return str;
        }

        public String getPeerHost() {
            return ReferenceCountedOpenSslEngine.this.getPeerHost();
        }

        public int getPeerPort() {
            return ReferenceCountedOpenSslEngine.this.getPeerPort();
        }
    }

    public final Runnable getDelegatedTask() {
        return null;
    }

    public final boolean getEnableSessionCreation() {
        return false;
    }

    static {
        Class<ReferenceCountedOpenSslEngine> cls = ReferenceCountedOpenSslEngine.class;
        logger = InternalLoggerFactory.getInstance(cls);
        String str = "engine closed";
        String str2 = "beginHandshake()";
        BEGIN_HANDSHAKE_ENGINE_CLOSED = (SSLException) ThrowableUtil.unknownStackTrace(new SSLException(str), cls, str2);
        HANDSHAKE_ENGINE_CLOSED = (SSLException) ThrowableUtil.unknownStackTrace(new SSLException(str), cls, "handshake()");
        RENEGOTIATION_UNSUPPORTED = (SSLException) ThrowableUtil.unknownStackTrace(new SSLException("renegotiation unsupported"), cls, str2);
        leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(cls);
        DESTROYED_UPDATER = AtomicIntegerFieldUpdater.newUpdater(cls, "destroyed");
    }

    ReferenceCountedOpenSslEngine(ReferenceCountedOpenSslContext referenceCountedOpenSslContext, ByteBufAllocator byteBufAllocator, String str, int i, boolean z) {
        super(str, i);
        OpenSsl.ensureAvailability();
        this.leak = z ? leakDetector.track(this) : null;
        this.alloc = (ByteBufAllocator) ObjectUtil.checkNotNull(byteBufAllocator, "alloc");
        this.apn = (OpenSslApplicationProtocolNegotiator) referenceCountedOpenSslContext.applicationProtocolNegotiator();
        this.session = new OpenSslSession(referenceCountedOpenSslContext.sessionContext());
        this.clientMode = referenceCountedOpenSslContext.isClient();
        this.engineMap = referenceCountedOpenSslContext.engineMap;
        this.rejectRemoteInitiatedRenegotiation = referenceCountedOpenSslContext.getRejectRemoteInitiatedRenegotiation();
        this.localCerts = referenceCountedOpenSslContext.keyCertChain;
        this.keyMaterialManager = referenceCountedOpenSslContext.keyMaterialManager();
        this.enableOcsp = referenceCountedOpenSslContext.enableOcsp;
        this.ssl = SSL.newSSL(referenceCountedOpenSslContext.ctx, !referenceCountedOpenSslContext.isClient());
        try {
            this.networkBIO = SSL.bioNewByteBuffer(this.ssl, referenceCountedOpenSslContext.getBioNonApplicationBufferSize());
            setClientAuth(this.clientMode ? ClientAuth.NONE : referenceCountedOpenSslContext.clientAuth);
            if (referenceCountedOpenSslContext.protocols != null) {
                setEnabledProtocols(referenceCountedOpenSslContext.protocols);
            }
            if (this.clientMode && str != null) {
                SSL.setTlsExtHostName(this.ssl, str);
            }
            if (this.enableOcsp) {
                SSL.enableOcsp(this.ssl);
            }
        } catch (Throwable th) {
            SSL.freeSSL(this.ssl);
            PlatformDependent.throwException(th);
        }
    }

    public void setOcspResponse(byte[] bArr) {
        if (!this.enableOcsp) {
            throw new IllegalStateException("OCSP stapling is not enabled");
        } else if (!this.clientMode) {
            synchronized (this) {
                SSL.setOcspResponse(this.ssl, bArr);
            }
        } else {
            throw new IllegalStateException("Not a server SSLEngine");
        }
    }

    public byte[] getOcspResponse() {
        byte[] ocspResponse;
        if (!this.enableOcsp) {
            throw new IllegalStateException("OCSP stapling is not enabled");
        } else if (this.clientMode) {
            synchronized (this) {
                ocspResponse = SSL.getOcspResponse(this.ssl);
            }
            return ocspResponse;
        } else {
            throw new IllegalStateException("Not a client SSLEngine");
        }
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

    public final synchronized SSLSession getHandshakeSession() {
        int i = C57312.f3735xea902ccf[this.handshakeState.ordinal()];
        if (i == 1 || i == 2) {
            return null;
        }
        return this.session;
    }

    public final synchronized long sslPointer() {
        return this.ssl;
    }

    public final synchronized void shutdown() {
        if (DESTROYED_UPDATER.compareAndSet(this, 0, 1)) {
            this.engineMap.remove(this.ssl);
            SSL.freeSSL(this.ssl);
            this.networkBIO = 0;
            this.ssl = 0;
            this.outboundClosed = true;
            this.isInboundDone = true;
        }
        SSL.clearError();
    }

    private int writePlaintextData(ByteBuffer byteBuffer, int i) {
        int i2;
        int position = byteBuffer.position();
        int limit = byteBuffer.limit();
        if (byteBuffer.isDirect()) {
            i2 = SSL.writeToSSL(this.ssl, Buffer.address(byteBuffer) + ((long) position), i);
            if (i2 > 0) {
                byteBuffer.position(position + i2);
            }
        } else {
            ByteBuf directBuffer = this.alloc.directBuffer(i);
            try {
                byteBuffer.limit(position + i);
                directBuffer.setBytes(0, byteBuffer);
                byteBuffer.limit(limit);
                i2 = SSL.writeToSSL(this.ssl, OpenSsl.memoryAddress(directBuffer), i);
                if (i2 > 0) {
                    byteBuffer.position(position + i2);
                } else {
                    byteBuffer.position(position);
                }
            } finally {
                directBuffer.release();
            }
        }
        return i2;
    }

    private ByteBuf writeEncryptedData(ByteBuffer byteBuffer, int i) {
        int position = byteBuffer.position();
        if (byteBuffer.isDirect()) {
            SSL.bioSetByteBuffer(this.networkBIO, Buffer.address(byteBuffer) + ((long) position), i, false);
        } else {
            ByteBuf directBuffer = this.alloc.directBuffer(i);
            try {
                int limit = byteBuffer.limit();
                byteBuffer.limit(position + i);
                directBuffer.writeBytes(byteBuffer);
                byteBuffer.position(position);
                byteBuffer.limit(limit);
                SSL.bioSetByteBuffer(this.networkBIO, OpenSsl.memoryAddress(directBuffer), i, false);
                return directBuffer;
            } catch (Throwable th) {
                directBuffer.release();
                PlatformDependent.throwException(th);
            }
        }
        return null;
    }

    /* JADX INFO: finally extract failed */
    private int readPlaintextData(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        if (byteBuffer.isDirect()) {
            int readFromSSL = SSL.readFromSSL(this.ssl, Buffer.address(byteBuffer) + ((long) position), byteBuffer.limit() - position);
            if (readFromSSL <= 0) {
                return readFromSSL;
            }
            byteBuffer.position(position + readFromSSL);
            return readFromSSL;
        }
        int limit = byteBuffer.limit();
        int min = Math.min(MAX_ENCRYPTED_PACKET_LENGTH, limit - position);
        ByteBuf directBuffer = this.alloc.directBuffer(min);
        try {
            int readFromSSL2 = SSL.readFromSSL(this.ssl, OpenSsl.memoryAddress(directBuffer), min);
            if (readFromSSL2 > 0) {
                byteBuffer.limit(position + readFromSSL2);
                directBuffer.getBytes(directBuffer.readerIndex(), byteBuffer);
                byteBuffer.limit(limit);
            }
            directBuffer.release();
            return readFromSSL2;
        } catch (Throwable th) {
            directBuffer.release();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01a1, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x01d0, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x01fe, code lost:
        return r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x0274, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x02bf, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x031f, code lost:
        return r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002c, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:211:0x0345, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:222:0x036f, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x0399, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:0x03c4, code lost:
        return r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0099, code lost:
        return r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00c5, code lost:
        return r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00f3, code lost:
        return r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x013c, code lost:
        return r11;
     */
    /* JADX WARNING: Removed duplicated region for block: B:252:0x03d0 A[Catch:{ all -> 0x03c5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:253:0x03df A[Catch:{ all -> 0x03c5 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final javax.net.ssl.SSLEngineResult wrap(java.nio.ByteBuffer[] r11, int r12, int r13, java.nio.ByteBuffer r14) throws javax.net.ssl.SSLException {
        /*
            r10 = this;
            if (r11 == 0) goto L_0x0426
            if (r14 == 0) goto L_0x041e
            int r0 = r11.length
            if (r12 >= r0) goto L_0x03f1
            int r0 = r12 + r13
            int r1 = r11.length
            if (r0 > r1) goto L_0x03f1
            boolean r13 = r14.isReadOnly()
            if (r13 != 0) goto L_0x03eb
            monitor-enter(r10)
            boolean r13 = r10.isOutboundDone()     // Catch:{ all -> 0x03e8 }
            if (r13 == 0) goto L_0x002d
            boolean r11 = r10.isInboundDone()     // Catch:{ all -> 0x03e8 }
            if (r11 != 0) goto L_0x0029
            boolean r11 = r10.isDestroyed()     // Catch:{ all -> 0x03e8 }
            if (r11 == 0) goto L_0x0026
            goto L_0x0029
        L_0x0026:
            javax.net.ssl.SSLEngineResult r11 = NEED_UNWRAP_CLOSED     // Catch:{ all -> 0x03e8 }
            goto L_0x002b
        L_0x0029:
            javax.net.ssl.SSLEngineResult r11 = CLOSED_NOT_HANDSHAKING     // Catch:{ all -> 0x03e8 }
        L_0x002b:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r11
        L_0x002d:
            r13 = 0
            r1 = 0
            boolean r2 = r14.isDirect()     // Catch:{ all -> 0x03c7 }
            if (r2 == 0) goto L_0x004a
            long r3 = r10.networkBIO     // Catch:{ all -> 0x03c7 }
            long r5 = io.netty.internal.tcnative.Buffer.address(r14)     // Catch:{ all -> 0x03c7 }
            int r2 = r14.position()     // Catch:{ all -> 0x03c7 }
            long r7 = (long) r2     // Catch:{ all -> 0x03c7 }
            long r5 = r5 + r7
            int r7 = r14.remaining()     // Catch:{ all -> 0x03c7 }
            r8 = 1
            io.netty.internal.tcnative.SSL.bioSetByteBuffer(r3, r5, r7, r8)     // Catch:{ all -> 0x03c7 }
            goto L_0x0062
        L_0x004a:
            io.netty.buffer.ByteBufAllocator r2 = r10.alloc     // Catch:{ all -> 0x03c7 }
            int r3 = r14.remaining()     // Catch:{ all -> 0x03c7 }
            io.netty.buffer.ByteBuf r13 = r2.directBuffer(r3)     // Catch:{ all -> 0x03c7 }
            long r2 = r10.networkBIO     // Catch:{ all -> 0x03c7 }
            long r4 = p043io.netty.handler.ssl.OpenSsl.memoryAddress(r13)     // Catch:{ all -> 0x03c7 }
            int r6 = r13.writableBytes()     // Catch:{ all -> 0x03c7 }
            r7 = 1
            io.netty.internal.tcnative.SSL.bioSetByteBuffer(r2, r4, r6, r7)     // Catch:{ all -> 0x03c7 }
        L_0x0062:
            long r2 = r10.networkBIO     // Catch:{ all -> 0x03c7 }
            int r2 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r2)     // Catch:{ all -> 0x03c7 }
            boolean r3 = r10.outboundClosed     // Catch:{ all -> 0x03c7 }
            if (r3 == 0) goto L_0x00f9
            long r11 = r10.networkBIO     // Catch:{ all -> 0x03c7 }
            int r11 = io.netty.internal.tcnative.SSL.bioFlushByteBuffer(r11)     // Catch:{ all -> 0x03c7 }
            if (r11 > 0) goto L_0x009a
            javax.net.ssl.SSLEngineResult$HandshakeStatus r12 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ all -> 0x00f4 }
            javax.net.ssl.SSLEngineResult r12 = r10.newResultMayFinishHandshake(r12, r1, r1)     // Catch:{ all -> 0x00f4 }
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x008a
            int r13 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r13 = r13 + r11
            r14.position(r13)     // Catch:{ all -> 0x03e8 }
            goto L_0x0098
        L_0x008a:
            int r0 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r11 = r13.internalNioBuffer(r0, r11)     // Catch:{ all -> 0x03e8 }
            r14.put(r11)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x0098:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r12
        L_0x009a:
            boolean r12 = r10.doSSLShutdown()     // Catch:{ all -> 0x00f4 }
            if (r12 != 0) goto L_0x00c6
            javax.net.ssl.SSLEngineResult$HandshakeStatus r12 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ all -> 0x00f4 }
            javax.net.ssl.SSLEngineResult r12 = r10.newResultMayFinishHandshake(r12, r1, r11)     // Catch:{ all -> 0x00f4 }
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x00b6
            int r13 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r13 = r13 + r11
            r14.position(r13)     // Catch:{ all -> 0x03e8 }
            goto L_0x00c4
        L_0x00b6:
            int r0 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r11 = r13.internalNioBuffer(r0, r11)     // Catch:{ all -> 0x03e8 }
            r14.put(r11)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x00c4:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r12
        L_0x00c6:
            long r3 = r10.networkBIO     // Catch:{ all -> 0x00f4 }
            int r11 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r3)     // Catch:{ all -> 0x00f4 }
            int r11 = r2 - r11
            javax.net.ssl.SSLEngineResult$HandshakeStatus r12 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ all -> 0x00f4 }
            javax.net.ssl.SSLEngineResult r12 = r10.newResultMayFinishHandshake(r12, r1, r11)     // Catch:{ all -> 0x00f4 }
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x00e4
            int r13 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r13 = r13 + r11
            r14.position(r13)     // Catch:{ all -> 0x03e8 }
            goto L_0x00f2
        L_0x00e4:
            int r0 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r11 = r13.internalNioBuffer(r0, r11)     // Catch:{ all -> 0x03e8 }
            r14.put(r11)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x00f2:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r12
        L_0x00f4:
            r12 = move-exception
            r4 = r11
            r11 = r12
            goto L_0x03c9
        L_0x00f9:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ all -> 0x03c7 }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r4 = r10.handshakeState     // Catch:{ all -> 0x03c7 }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r5 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.FINISHED     // Catch:{ all -> 0x03c7 }
            if (r4 == r5) goto L_0x0207
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r3 = r10.handshakeState     // Catch:{ all -> 0x03c7 }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r4 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY     // Catch:{ all -> 0x03c7 }
            if (r3 == r4) goto L_0x010b
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r3 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_IMPLICITLY     // Catch:{ all -> 0x03c7 }
            r10.handshakeState = r3     // Catch:{ all -> 0x03c7 }
        L_0x010b:
            long r3 = r10.networkBIO     // Catch:{ all -> 0x03c7 }
            int r3 = io.netty.internal.tcnative.SSL.bioFlushByteBuffer(r3)     // Catch:{ all -> 0x03c7 }
            if (r3 <= 0) goto L_0x013d
            javax.net.ssl.SSLHandshakeException r4 = r10.handshakeException     // Catch:{ all -> 0x0203 }
            if (r4 == 0) goto L_0x013d
            javax.net.ssl.SSLEngineResult$HandshakeStatus r11 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ all -> 0x0203 }
            javax.net.ssl.SSLEngineResult r11 = r10.newResult(r11, r1, r3)     // Catch:{ all -> 0x0203 }
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x012d
            int r12 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r12 = r12 + r3
            r14.position(r12)     // Catch:{ all -> 0x03e8 }
            goto L_0x013b
        L_0x012d:
            int r12 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r12 = r13.internalNioBuffer(r12, r3)     // Catch:{ all -> 0x03e8 }
            r14.put(r12)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x013b:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r11
        L_0x013d:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r4 = r10.handshake()     // Catch:{ all -> 0x0203 }
            boolean r5 = r10.renegotiationPending     // Catch:{ all -> 0x0203 }
            if (r5 == 0) goto L_0x015a
            javax.net.ssl.SSLEngineResult$HandshakeStatus r5 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ all -> 0x0203 }
            if (r4 != r5) goto L_0x015a
            r10.renegotiationPending = r1     // Catch:{ all -> 0x0203 }
            long r4 = r10.ssl     // Catch:{ all -> 0x0203 }
            int r6 = io.netty.internal.tcnative.SSL.SSL_ST_ACCEPT     // Catch:{ all -> 0x0203 }
            io.netty.internal.tcnative.SSL.setState(r4, r6)     // Catch:{ all -> 0x0203 }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r4 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY     // Catch:{ all -> 0x0203 }
            r10.handshakeState = r4     // Catch:{ all -> 0x0203 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r4 = r10.handshake()     // Catch:{ all -> 0x0203 }
        L_0x015a:
            long r5 = r10.networkBIO     // Catch:{ all -> 0x0203 }
            int r3 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r5)     // Catch:{ all -> 0x0203 }
            int r3 = r2 - r3
            if (r3 <= 0) goto L_0x01a2
            javax.net.ssl.SSLEngineResult$HandshakeStatus r11 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ all -> 0x0203 }
            if (r4 == r11) goto L_0x0178
            if (r3 != r2) goto L_0x016d
            javax.net.ssl.SSLEngineResult$HandshakeStatus r11 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ all -> 0x0203 }
            goto L_0x017a
        L_0x016d:
            long r11 = r10.networkBIO     // Catch:{ all -> 0x0203 }
            int r11 = io.netty.internal.tcnative.SSL.bioLengthNonApplication(r11)     // Catch:{ all -> 0x0203 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r11 = r10.getHandshakeStatus(r11)     // Catch:{ all -> 0x0203 }
            goto L_0x017a
        L_0x0178:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r11 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ all -> 0x0203 }
        L_0x017a:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r11 = r10.mayFinishHandshake(r11)     // Catch:{ all -> 0x0203 }
            javax.net.ssl.SSLEngineResult r11 = r10.newResult(r11, r1, r3)     // Catch:{ all -> 0x0203 }
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x0192
            int r12 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r12 = r12 + r3
            r14.position(r12)     // Catch:{ all -> 0x03e8 }
            goto L_0x01a0
        L_0x0192:
            int r12 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r12 = r13.internalNioBuffer(r12, r3)     // Catch:{ all -> 0x03e8 }
            r14.put(r12)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x01a0:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r11
        L_0x01a2:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r5 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP     // Catch:{ all -> 0x0203 }
            if (r4 != r5) goto L_0x01d1
            boolean r11 = r10.isOutboundDone()     // Catch:{ all -> 0x0203 }
            if (r11 == 0) goto L_0x01af
            javax.net.ssl.SSLEngineResult r11 = NEED_UNWRAP_CLOSED     // Catch:{ all -> 0x0203 }
            goto L_0x01b1
        L_0x01af:
            javax.net.ssl.SSLEngineResult r11 = NEED_UNWRAP_OK     // Catch:{ all -> 0x0203 }
        L_0x01b1:
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x01c1
            int r12 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r12 = r12 + r3
            r14.position(r12)     // Catch:{ all -> 0x03e8 }
            goto L_0x01cf
        L_0x01c1:
            int r12 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r12 = r13.internalNioBuffer(r12, r3)     // Catch:{ all -> 0x03e8 }
            r14.put(r12)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x01cf:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r11
        L_0x01d1:
            boolean r5 = r10.outboundClosed     // Catch:{ all -> 0x0203 }
            if (r5 == 0) goto L_0x01ff
            long r11 = r10.networkBIO     // Catch:{ all -> 0x0203 }
            int r11 = io.netty.internal.tcnative.SSL.bioFlushByteBuffer(r11)     // Catch:{ all -> 0x0203 }
            javax.net.ssl.SSLEngineResult r12 = r10.newResultMayFinishHandshake(r4, r1, r11)     // Catch:{ all -> 0x00f4 }
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x01ef
            int r13 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r13 = r13 + r11
            r14.position(r13)     // Catch:{ all -> 0x03e8 }
            goto L_0x01fd
        L_0x01ef:
            int r0 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r11 = r13.internalNioBuffer(r0, r11)     // Catch:{ all -> 0x03e8 }
            r14.put(r11)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x01fd:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r12
        L_0x01ff:
            r9 = r4
            r4 = r3
            r3 = r9
            goto L_0x0208
        L_0x0203:
            r11 = move-exception
            r4 = r3
            goto L_0x03c9
        L_0x0207:
            r4 = 0
        L_0x0208:
            r5 = r12
            r6 = 0
        L_0x020a:
            r7 = 16384(0x4000, float:2.2959E-41)
            if (r5 >= r0) goto L_0x023f
            r8 = r11[r5]     // Catch:{ all -> 0x03c5 }
            if (r8 == 0) goto L_0x0223
            if (r6 != r7) goto L_0x0215
            goto L_0x0220
        L_0x0215:
            int r8 = r8.remaining()     // Catch:{ all -> 0x03c5 }
            int r6 = r6 + r8
            if (r6 > r7) goto L_0x021e
            if (r6 >= 0) goto L_0x0220
        L_0x021e:
            r6 = 16384(0x4000, float:2.2959E-41)
        L_0x0220:
            int r5 = r5 + 1
            goto L_0x020a
        L_0x0223:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x03c5 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x03c5 }
            r12.<init>()     // Catch:{ all -> 0x03c5 }
            java.lang.String r0 = "srcs["
            r12.append(r0)     // Catch:{ all -> 0x03c5 }
            r12.append(r5)     // Catch:{ all -> 0x03c5 }
            java.lang.String r0 = "] is null"
            r12.append(r0)     // Catch:{ all -> 0x03c5 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x03c5 }
            r11.<init>(r12)     // Catch:{ all -> 0x03c5 }
            throw r11     // Catch:{ all -> 0x03c5 }
        L_0x023f:
            int r5 = r14.remaining()     // Catch:{ all -> 0x03c5 }
            r8 = 1
            int r6 = calculateOutNetBufSize(r6, r8)     // Catch:{ all -> 0x03c5 }
            if (r5 >= r6) goto L_0x0275
            javax.net.ssl.SSLEngineResult r11 = new javax.net.ssl.SSLEngineResult     // Catch:{ all -> 0x03c5 }
            javax.net.ssl.SSLEngineResult$Status r12 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW     // Catch:{ all -> 0x03c5 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r0 = r10.getHandshakeStatus()     // Catch:{ all -> 0x03c5 }
            r11.<init>(r12, r0, r1, r1)     // Catch:{ all -> 0x03c5 }
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x0265
            int r12 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r12 = r12 + r4
            r14.position(r12)     // Catch:{ all -> 0x03e8 }
            goto L_0x0273
        L_0x0265:
            int r12 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r12 = r13.internalNioBuffer(r12, r4)     // Catch:{ all -> 0x03e8 }
            r14.put(r12)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x0273:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r11
        L_0x0275:
            long r5 = r10.networkBIO     // Catch:{ all -> 0x03c5 }
            int r4 = io.netty.internal.tcnative.SSL.bioFlushByteBuffer(r5)     // Catch:{ all -> 0x03c5 }
        L_0x027b:
            if (r12 >= r0) goto L_0x03a1
            r5 = r11[r12]     // Catch:{ all -> 0x03c5 }
            int r6 = r5.remaining()     // Catch:{ all -> 0x03c5 }
            if (r6 != 0) goto L_0x0288
            int r12 = r12 + 1
            goto L_0x027b
        L_0x0288:
            int r11 = java.lang.Math.min(r6, r7)     // Catch:{ all -> 0x03c5 }
            int r11 = r10.writePlaintextData(r5, r11)     // Catch:{ all -> 0x03c5 }
            if (r11 <= 0) goto L_0x02c4
            int r11 = r11 + r1
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03c5 }
            int r12 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r0)     // Catch:{ all -> 0x03c5 }
            int r2 = r2 - r12
            int r1 = r4 + r2
            javax.net.ssl.SSLEngineResult r11 = r10.newResultMayFinishHandshake(r3, r11, r1)     // Catch:{ all -> 0x02c0 }
            long r2 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r2)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x02b0
            int r12 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r12 = r12 + r1
            r14.position(r12)     // Catch:{ all -> 0x03e8 }
            goto L_0x02be
        L_0x02b0:
            int r12 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r12 = r13.internalNioBuffer(r12, r1)     // Catch:{ all -> 0x03e8 }
            r14.put(r12)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x02be:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r11
        L_0x02c0:
            r11 = move-exception
            r4 = r1
            goto L_0x03c9
        L_0x02c4:
            long r5 = r10.ssl     // Catch:{ all -> 0x03c5 }
            int r11 = io.netty.internal.tcnative.SSL.getError(r5, r11)     // Catch:{ all -> 0x03c5 }
            int r12 = io.netty.internal.tcnative.SSL.SSL_ERROR_ZERO_RETURN     // Catch:{ all -> 0x03c5 }
            if (r11 != r12) goto L_0x0346
            boolean r11 = r10.receivedShutdown     // Catch:{ all -> 0x03c5 }
            if (r11 != 0) goto L_0x0320
            r10.closeAll()     // Catch:{ all -> 0x03c5 }
            long r11 = r10.networkBIO     // Catch:{ all -> 0x03c5 }
            int r11 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r11)     // Catch:{ all -> 0x03c5 }
            int r2 = r2 - r11
            int r11 = r4 + r2
            javax.net.ssl.SSLEngineResult$HandshakeStatus r12 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ all -> 0x00f4 }
            if (r3 == r12) goto L_0x02f6
            int r12 = r14.remaining()     // Catch:{ all -> 0x00f4 }
            if (r11 != r12) goto L_0x02eb
            javax.net.ssl.SSLEngineResult$HandshakeStatus r12 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ all -> 0x00f4 }
            goto L_0x02f8
        L_0x02eb:
            long r2 = r10.networkBIO     // Catch:{ all -> 0x00f4 }
            int r12 = io.netty.internal.tcnative.SSL.bioLengthNonApplication(r2)     // Catch:{ all -> 0x00f4 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r12 = r10.getHandshakeStatus(r12)     // Catch:{ all -> 0x00f4 }
            goto L_0x02f8
        L_0x02f6:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r12 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ all -> 0x00f4 }
        L_0x02f8:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r12 = r10.mayFinishHandshake(r12)     // Catch:{ all -> 0x00f4 }
            javax.net.ssl.SSLEngineResult r12 = r10.newResult(r12, r1, r11)     // Catch:{ all -> 0x00f4 }
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x0310
            int r13 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r13 = r13 + r11
            r14.position(r13)     // Catch:{ all -> 0x03e8 }
            goto L_0x031e
        L_0x0310:
            int r0 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r11 = r13.internalNioBuffer(r0, r11)     // Catch:{ all -> 0x03e8 }
            r14.put(r11)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x031e:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r12
        L_0x0320:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r11 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ all -> 0x03c5 }
            javax.net.ssl.SSLEngineResult r11 = r10.newResult(r11, r1, r4)     // Catch:{ all -> 0x03c5 }
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x0336
            int r12 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r12 = r12 + r4
            r14.position(r12)     // Catch:{ all -> 0x03e8 }
            goto L_0x0344
        L_0x0336:
            int r12 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r12 = r13.internalNioBuffer(r12, r4)     // Catch:{ all -> 0x03e8 }
            r14.put(r12)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x0344:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r11
        L_0x0346:
            int r12 = io.netty.internal.tcnative.SSL.SSL_ERROR_WANT_READ     // Catch:{ all -> 0x03c5 }
            if (r11 != r12) goto L_0x0370
            javax.net.ssl.SSLEngineResult$HandshakeStatus r11 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP     // Catch:{ all -> 0x03c5 }
            javax.net.ssl.SSLEngineResult r11 = r10.newResult(r11, r1, r4)     // Catch:{ all -> 0x03c5 }
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x0360
            int r12 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r12 = r12 + r4
            r14.position(r12)     // Catch:{ all -> 0x03e8 }
            goto L_0x036e
        L_0x0360:
            int r12 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r12 = r13.internalNioBuffer(r12, r4)     // Catch:{ all -> 0x03e8 }
            r14.put(r12)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x036e:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r11
        L_0x0370:
            int r12 = io.netty.internal.tcnative.SSL.SSL_ERROR_WANT_WRITE     // Catch:{ all -> 0x03c5 }
            if (r11 != r12) goto L_0x039a
            javax.net.ssl.SSLEngineResult$HandshakeStatus r11 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ all -> 0x03c5 }
            javax.net.ssl.SSLEngineResult r11 = r10.newResult(r11, r1, r4)     // Catch:{ all -> 0x03c5 }
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x038a
            int r12 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r12 = r12 + r4
            r14.position(r12)     // Catch:{ all -> 0x03e8 }
            goto L_0x0398
        L_0x038a:
            int r12 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r12 = r13.internalNioBuffer(r12, r4)     // Catch:{ all -> 0x03e8 }
            r14.put(r12)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x0398:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r11
        L_0x039a:
            java.lang.String r11 = "SSL_write"
            javax.net.ssl.SSLException r11 = r10.shutdownWithError(r11)     // Catch:{ all -> 0x03c5 }
            throw r11     // Catch:{ all -> 0x03c5 }
        L_0x03a1:
            javax.net.ssl.SSLEngineResult r11 = r10.newResultMayFinishHandshake(r3, r1, r4)     // Catch:{ all -> 0x03c5 }
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 != 0) goto L_0x03b5
            int r12 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r12 = r12 + r4
            r14.position(r12)     // Catch:{ all -> 0x03e8 }
            goto L_0x03c3
        L_0x03b5:
            int r12 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r12 = r13.internalNioBuffer(r12, r4)     // Catch:{ all -> 0x03e8 }
            r14.put(r12)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
        L_0x03c3:
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            return r11
        L_0x03c5:
            r11 = move-exception
            goto L_0x03c9
        L_0x03c7:
            r11 = move-exception
            r4 = 0
        L_0x03c9:
            long r0 = r10.networkBIO     // Catch:{ all -> 0x03e8 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r0)     // Catch:{ all -> 0x03e8 }
            if (r13 == 0) goto L_0x03df
            int r12 = r13.readerIndex()     // Catch:{ all -> 0x03e8 }
            java.nio.ByteBuffer r12 = r13.internalNioBuffer(r12, r4)     // Catch:{ all -> 0x03e8 }
            r14.put(r12)     // Catch:{ all -> 0x03e8 }
            r13.release()     // Catch:{ all -> 0x03e8 }
            goto L_0x03e7
        L_0x03df:
            int r12 = r14.position()     // Catch:{ all -> 0x03e8 }
            int r12 = r12 + r4
            r14.position(r12)     // Catch:{ all -> 0x03e8 }
        L_0x03e7:
            throw r11     // Catch:{ all -> 0x03e8 }
        L_0x03e8:
            r11 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x03e8 }
            throw r11
        L_0x03eb:
            java.nio.ReadOnlyBufferException r11 = new java.nio.ReadOnlyBufferException
            r11.<init>()
            throw r11
        L_0x03f1:
            java.lang.IndexOutOfBoundsException r14 = new java.lang.IndexOutOfBoundsException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "offset: "
            r0.append(r1)
            r0.append(r12)
            java.lang.String r12 = ", length: "
            r0.append(r12)
            r0.append(r13)
            java.lang.String r12 = " (expected: offset <= offset + length <= srcs.length ("
            r0.append(r12)
            int r11 = r11.length
            r0.append(r11)
            java.lang.String r11 = "))"
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            r14.<init>(r11)
            throw r14
        L_0x041e:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.String r12 = "dst is null"
            r11.<init>(r12)
            throw r11
        L_0x0426:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.String r12 = "srcs is null"
            r11.<init>(r12)
            goto L_0x042f
        L_0x042e:
            throw r11
        L_0x042f:
            goto L_0x042e
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.wrap(java.nio.ByteBuffer[], int, int, java.nio.ByteBuffer):javax.net.ssl.SSLEngineResult");
    }

    private SSLEngineResult newResult(HandshakeStatus handshakeStatus, int i, int i2) {
        return newResult(Status.OK, handshakeStatus, i, i2);
    }

    private SSLEngineResult newResult(Status status, HandshakeStatus handshakeStatus, int i, int i2) {
        if (!isOutboundDone()) {
            return new SSLEngineResult(status, handshakeStatus, i, i2);
        }
        if (isInboundDone()) {
            handshakeStatus = HandshakeStatus.NOT_HANDSHAKING;
            shutdown();
        }
        return new SSLEngineResult(Status.CLOSED, handshakeStatus, i, i2);
    }

    private SSLEngineResult newResultMayFinishHandshake(HandshakeStatus handshakeStatus, int i, int i2) throws SSLException {
        return newResult(mayFinishHandshake(handshakeStatus != HandshakeStatus.FINISHED ? getHandshakeStatus() : HandshakeStatus.FINISHED), i, i2);
    }

    private SSLEngineResult newResultMayFinishHandshake(Status status, HandshakeStatus handshakeStatus, int i, int i2) throws SSLException {
        return newResult(status, mayFinishHandshake(handshakeStatus != HandshakeStatus.FINISHED ? getHandshakeStatus() : HandshakeStatus.FINISHED), i, i2);
    }

    private SSLException shutdownWithError(String str) {
        return shutdownWithError(str, SSL.getLastError());
    }

    private SSLException shutdownWithError(String str, String str2) {
        if (logger.isDebugEnabled()) {
            logger.debug("{} failed: OpenSSL error: {}", str, str2);
        }
        shutdown();
        if (this.handshakeState == HandshakeState.FINISHED) {
            return new SSLException(str2);
        }
        return new SSLHandshakeException(str2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:?, code lost:
        io.netty.internal.tcnative.SSL.bioClearByteBuffer(r1.networkBIO);
        rejectRemoteInitiatedRenegotiation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0170, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0185, code lost:
        if (r2 != io.netty.internal.tcnative.SSL.SSL_ERROR_ZERO_RETURN) goto L_0x01ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0189, code lost:
        if (r1.receivedShutdown != false) goto L_0x018e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x018b, code lost:
        closeAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0192, code lost:
        if (isInboundDone() == false) goto L_0x0197;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0194, code lost:
        r0 = javax.net.ssl.SSLEngineResult.Status.CLOSED;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0197, code lost:
        r0 = javax.net.ssl.SSLEngineResult.Status.OK;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x0199, code lost:
        r0 = newResultMayFinishHandshake(r0, r6, r9, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x019d, code lost:
        if (r13 == null) goto L_0x01a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:?, code lost:
        r13.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:?, code lost:
        io.netty.internal.tcnative.SSL.bioClearByteBuffer(r1.networkBIO);
        rejectRemoteInitiatedRenegotiation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x01ab, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:?, code lost:
        r0 = sslReadErrorResult(io.netty.internal.tcnative.SSL.getLastErrorNumber(), r9, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x01b4, code lost:
        if (r13 == null) goto L_0x01b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:?, code lost:
        r13.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:?, code lost:
        io.netty.internal.tcnative.SSL.bioClearByteBuffer(r1.networkBIO);
        rejectRemoteInitiatedRenegotiation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x01c2, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a3, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0157, code lost:
        if (isInboundDone() == false) goto L_0x015c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0159, code lost:
        r0 = javax.net.ssl.SSLEngineResult.Status.CLOSED;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x015c, code lost:
        r0 = javax.net.ssl.SSLEngineResult.Status.OK;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x015e, code lost:
        r0 = newResultMayFinishHandshake(r0, r6, r9, r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0162, code lost:
        if (r13 == null) goto L_0x0167;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:?, code lost:
        r13.release();
     */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x01cf A[Catch:{ all -> 0x01c3, all -> 0x01e6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:187:0x01e0 A[EDGE_INSN: B:187:0x01e0->B:149:0x01e0 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:124:0x01a2=Splitter:B:124:0x01a2, B:100:0x0167=Splitter:B:100:0x0167, B:133:0x01b9=Splitter:B:133:0x01b9} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final javax.net.ssl.SSLEngineResult unwrap(java.nio.ByteBuffer[] r18, int r19, int r20, java.nio.ByteBuffer[] r21, int r22, int r23) throws javax.net.ssl.SSLException {
        /*
            r17 = this;
            r1 = r17
            r0 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r5 = r22
            r6 = r23
            if (r0 == 0) goto L_0x028a
            int r7 = r0.length
            if (r2 >= r7) goto L_0x025d
            int r7 = r2 + r3
            int r8 = r0.length
            if (r7 > r8) goto L_0x025d
            if (r4 == 0) goto L_0x0255
            int r3 = r4.length
            if (r5 >= r3) goto L_0x0228
            int r3 = r5 + r6
            int r8 = r4.length
            if (r3 > r8) goto L_0x0228
            r8 = 0
            r6 = r5
            r10 = r8
        L_0x0026:
            if (r6 >= r3) goto L_0x005d
            r12 = r4[r6]
            if (r12 == 0) goto L_0x0041
            boolean r13 = r12.isReadOnly()
            if (r13 != 0) goto L_0x003b
            int r12 = r12.remaining()
            long r12 = (long) r12
            long r10 = r10 + r12
            int r6 = r6 + 1
            goto L_0x0026
        L_0x003b:
            java.nio.ReadOnlyBufferException r0 = new java.nio.ReadOnlyBufferException
            r0.<init>()
            throw r0
        L_0x0041:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "dsts["
            r2.append(r3)
            r2.append(r6)
            java.lang.String r3 = "] is null"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        L_0x005d:
            r6 = r2
        L_0x005e:
            if (r6 >= r7) goto L_0x0089
            r12 = r0[r6]
            if (r12 == 0) goto L_0x006d
            int r12 = r12.remaining()
            long r12 = (long) r12
            long r8 = r8 + r12
            int r6 = r6 + 1
            goto L_0x005e
        L_0x006d:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "srcs["
            r2.append(r3)
            r2.append(r6)
            java.lang.String r3 = "] is null"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        L_0x0089:
            monitor-enter(r17)
            boolean r6 = r17.isInboundDone()     // Catch:{ all -> 0x0225 }
            if (r6 == 0) goto L_0x00a4
            boolean r0 = r17.isOutboundDone()     // Catch:{ all -> 0x0225 }
            if (r0 != 0) goto L_0x00a0
            boolean r0 = r17.isDestroyed()     // Catch:{ all -> 0x0225 }
            if (r0 == 0) goto L_0x009d
            goto L_0x00a0
        L_0x009d:
            javax.net.ssl.SSLEngineResult r0 = NEED_WRAP_CLOSED     // Catch:{ all -> 0x0225 }
            goto L_0x00a2
        L_0x00a0:
            javax.net.ssl.SSLEngineResult r0 = CLOSED_NOT_HANDSHAKING     // Catch:{ all -> 0x0225 }
        L_0x00a2:
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            return r0
        L_0x00a4:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r6 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ all -> 0x0225 }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r12 = r1.handshakeState     // Catch:{ all -> 0x0225 }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r13 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.FINISHED     // Catch:{ all -> 0x0225 }
            if (r12 == r13) goto L_0x00ca
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r6 = r1.handshakeState     // Catch:{ all -> 0x0225 }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r12 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY     // Catch:{ all -> 0x0225 }
            if (r6 == r12) goto L_0x00b6
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r6 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_IMPLICITLY     // Catch:{ all -> 0x0225 }
            r1.handshakeState = r6     // Catch:{ all -> 0x0225 }
        L_0x00b6:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r6 = r17.handshake()     // Catch:{ all -> 0x0225 }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r12 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ all -> 0x0225 }
            if (r6 != r12) goto L_0x00c2
            javax.net.ssl.SSLEngineResult r0 = NEED_WRAP_OK     // Catch:{ all -> 0x0225 }
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            return r0
        L_0x00c2:
            boolean r12 = r1.isInboundDone     // Catch:{ all -> 0x0225 }
            if (r12 == 0) goto L_0x00ca
            javax.net.ssl.SSLEngineResult r0 = NEED_WRAP_CLOSED     // Catch:{ all -> 0x0225 }
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            return r0
        L_0x00ca:
            r12 = 5
            r14 = 0
            int r15 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1))
            if (r15 >= 0) goto L_0x00d9
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW     // Catch:{ all -> 0x0225 }
            javax.net.ssl.SSLEngineResult r0 = r1.newResultMayFinishHandshake(r0, r6, r14, r14)     // Catch:{ all -> 0x0225 }
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            return r0
        L_0x00d9:
            int r12 = p043io.netty.handler.ssl.SslUtils.getEncryptedPacketLength(r18, r19)     // Catch:{ all -> 0x0225 }
            r13 = -2
            if (r12 == r13) goto L_0x021d
            int r13 = r12 + -5
            long r14 = (long) r13     // Catch:{ all -> 0x0225 }
            int r13 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r13 <= 0) goto L_0x00f0
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW     // Catch:{ all -> 0x0225 }
            r2 = 0
            javax.net.ssl.SSLEngineResult r0 = r1.newResultMayFinishHandshake(r0, r6, r2, r2)     // Catch:{ all -> 0x0225 }
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            return r0
        L_0x00f0:
            long r10 = (long) r12     // Catch:{ all -> 0x0225 }
            int r13 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r13 >= 0) goto L_0x00fe
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW     // Catch:{ all -> 0x0225 }
            r8 = 0
            javax.net.ssl.SSLEngineResult r0 = r1.newResultMayFinishHandshake(r0, r6, r8, r8)     // Catch:{ all -> 0x0225 }
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            return r0
        L_0x00fe:
            r8 = 0
            r9 = 0
        L_0x0100:
            if (r2 >= r7) goto L_0x01f0
            r10 = r0[r2]     // Catch:{ all -> 0x01e6 }
            int r11 = r10.remaining()     // Catch:{ all -> 0x01e6 }
            if (r11 != 0) goto L_0x010f
            r19 = r2
            r2 = r3
            goto L_0x01d7
        L_0x010f:
            int r11 = java.lang.Math.min(r12, r11)     // Catch:{ all -> 0x01e6 }
            io.netty.buffer.ByteBuf r13 = r1.writeEncryptedData(r10, r11)     // Catch:{ all -> 0x01e6 }
        L_0x0117:
            if (r5 >= r3) goto L_0x01ca
            r14 = r4[r5]     // Catch:{ all -> 0x01c3 }
            boolean r15 = r14.hasRemaining()     // Catch:{ all -> 0x01c3 }
            if (r15 != 0) goto L_0x0126
            r19 = r2
            r20 = r3
            goto L_0x014a
        L_0x0126:
            int r15 = r1.readPlaintextData(r14)     // Catch:{ all -> 0x01c3 }
            r19 = r2
            r20 = r3
            long r2 = r1.networkBIO     // Catch:{ all -> 0x01c3 }
            int r2 = io.netty.internal.tcnative.SSL.bioLengthByteBuffer(r2)     // Catch:{ all -> 0x01c3 }
            int r2 = r11 - r2
            int r9 = r9 + r2
            int r12 = r12 - r2
            int r11 = r11 - r2
            int r3 = r10.position()     // Catch:{ all -> 0x01c3 }
            int r3 = r3 + r2
            r10.position(r3)     // Catch:{ all -> 0x01c3 }
            if (r15 <= 0) goto L_0x0174
            int r8 = r8 + r15
            boolean r2 = r14.hasRemaining()     // Catch:{ all -> 0x01c3 }
            if (r2 != 0) goto L_0x0151
        L_0x014a:
            int r5 = r5 + 1
            r2 = r19
            r3 = r20
            goto L_0x0117
        L_0x0151:
            if (r12 != 0) goto L_0x0171
            boolean r0 = r17.isInboundDone()     // Catch:{ all -> 0x01c3 }
            if (r0 == 0) goto L_0x015c
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ all -> 0x01c3 }
            goto L_0x015e
        L_0x015c:
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.OK     // Catch:{ all -> 0x01c3 }
        L_0x015e:
            javax.net.ssl.SSLEngineResult r0 = r1.newResultMayFinishHandshake(r0, r6, r9, r8)     // Catch:{ all -> 0x01c3 }
            if (r13 == 0) goto L_0x0167
            r13.release()     // Catch:{ all -> 0x01e6 }
        L_0x0167:
            long r2 = r1.networkBIO     // Catch:{ all -> 0x0225 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r2)     // Catch:{ all -> 0x0225 }
            r17.rejectRemoteInitiatedRenegotiation()     // Catch:{ all -> 0x0225 }
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            return r0
        L_0x0171:
            r2 = r20
            goto L_0x01cd
        L_0x0174:
            long r2 = r1.ssl     // Catch:{ all -> 0x01c3 }
            int r2 = io.netty.internal.tcnative.SSL.getError(r2, r15)     // Catch:{ all -> 0x01c3 }
            int r3 = io.netty.internal.tcnative.SSL.SSL_ERROR_WANT_READ     // Catch:{ all -> 0x01c3 }
            if (r2 == r3) goto L_0x0171
            int r3 = io.netty.internal.tcnative.SSL.SSL_ERROR_WANT_WRITE     // Catch:{ all -> 0x01c3 }
            if (r2 != r3) goto L_0x0183
            goto L_0x0171
        L_0x0183:
            int r0 = io.netty.internal.tcnative.SSL.SSL_ERROR_ZERO_RETURN     // Catch:{ all -> 0x01c3 }
            if (r2 != r0) goto L_0x01ac
            boolean r0 = r1.receivedShutdown     // Catch:{ all -> 0x01c3 }
            if (r0 != 0) goto L_0x018e
            r17.closeAll()     // Catch:{ all -> 0x01c3 }
        L_0x018e:
            boolean r0 = r17.isInboundDone()     // Catch:{ all -> 0x01c3 }
            if (r0 == 0) goto L_0x0197
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ all -> 0x01c3 }
            goto L_0x0199
        L_0x0197:
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.OK     // Catch:{ all -> 0x01c3 }
        L_0x0199:
            javax.net.ssl.SSLEngineResult r0 = r1.newResultMayFinishHandshake(r0, r6, r9, r8)     // Catch:{ all -> 0x01c3 }
            if (r13 == 0) goto L_0x01a2
            r13.release()     // Catch:{ all -> 0x01e6 }
        L_0x01a2:
            long r2 = r1.networkBIO     // Catch:{ all -> 0x0225 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r2)     // Catch:{ all -> 0x0225 }
            r17.rejectRemoteInitiatedRenegotiation()     // Catch:{ all -> 0x0225 }
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            return r0
        L_0x01ac:
            int r0 = io.netty.internal.tcnative.SSL.getLastErrorNumber()     // Catch:{ all -> 0x01c3 }
            javax.net.ssl.SSLEngineResult r0 = r1.sslReadErrorResult(r0, r9, r8)     // Catch:{ all -> 0x01c3 }
            if (r13 == 0) goto L_0x01b9
            r13.release()     // Catch:{ all -> 0x01e6 }
        L_0x01b9:
            long r2 = r1.networkBIO     // Catch:{ all -> 0x0225 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r2)     // Catch:{ all -> 0x0225 }
            r17.rejectRemoteInitiatedRenegotiation()     // Catch:{ all -> 0x0225 }
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            return r0
        L_0x01c3:
            r0 = move-exception
            if (r13 == 0) goto L_0x01c9
            r13.release()     // Catch:{ all -> 0x01e6 }
        L_0x01c9:
            throw r0     // Catch:{ all -> 0x01e6 }
        L_0x01ca:
            r19 = r2
            r2 = r3
        L_0x01cd:
            if (r5 >= r2) goto L_0x01e0
            if (r12 != 0) goto L_0x01d2
            goto L_0x01e0
        L_0x01d2:
            if (r13 == 0) goto L_0x01d7
            r13.release()     // Catch:{ all -> 0x01e6 }
        L_0x01d7:
            int r3 = r19 + 1
            r16 = r3
            r3 = r2
            r2 = r16
            goto L_0x0100
        L_0x01e0:
            if (r13 == 0) goto L_0x01f0
            r13.release()     // Catch:{ all -> 0x01e6 }
            goto L_0x01f0
        L_0x01e6:
            r0 = move-exception
            long r2 = r1.networkBIO     // Catch:{ all -> 0x0225 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r2)     // Catch:{ all -> 0x0225 }
            r17.rejectRemoteInitiatedRenegotiation()     // Catch:{ all -> 0x0225 }
            throw r0     // Catch:{ all -> 0x0225 }
        L_0x01f0:
            long r2 = r1.networkBIO     // Catch:{ all -> 0x0225 }
            io.netty.internal.tcnative.SSL.bioClearByteBuffer(r2)     // Catch:{ all -> 0x0225 }
            r17.rejectRemoteInitiatedRenegotiation()     // Catch:{ all -> 0x0225 }
            boolean r0 = r1.receivedShutdown     // Catch:{ all -> 0x0225 }
            if (r0 != 0) goto L_0x020c
            long r2 = r1.ssl     // Catch:{ all -> 0x0225 }
            int r0 = io.netty.internal.tcnative.SSL.getShutdown(r2)     // Catch:{ all -> 0x0225 }
            int r2 = io.netty.internal.tcnative.SSL.SSL_RECEIVED_SHUTDOWN     // Catch:{ all -> 0x0225 }
            r0 = r0 & r2
            int r2 = io.netty.internal.tcnative.SSL.SSL_RECEIVED_SHUTDOWN     // Catch:{ all -> 0x0225 }
            if (r0 != r2) goto L_0x020c
            r17.closeAll()     // Catch:{ all -> 0x0225 }
        L_0x020c:
            boolean r0 = r17.isInboundDone()     // Catch:{ all -> 0x0225 }
            if (r0 == 0) goto L_0x0215
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ all -> 0x0225 }
            goto L_0x0217
        L_0x0215:
            javax.net.ssl.SSLEngineResult$Status r0 = javax.net.ssl.SSLEngineResult.Status.OK     // Catch:{ all -> 0x0225 }
        L_0x0217:
            javax.net.ssl.SSLEngineResult r0 = r1.newResultMayFinishHandshake(r0, r6, r9, r8)     // Catch:{ all -> 0x0225 }
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            return r0
        L_0x021d:
            io.netty.handler.ssl.NotSslRecordException r0 = new io.netty.handler.ssl.NotSslRecordException     // Catch:{ all -> 0x0225 }
            java.lang.String r2 = "not an SSL/TLS record"
            r0.<init>(r2)     // Catch:{ all -> 0x0225 }
            throw r0     // Catch:{ all -> 0x0225 }
        L_0x0225:
            r0 = move-exception
            monitor-exit(r17)     // Catch:{ all -> 0x0225 }
            throw r0
        L_0x0228:
            java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "offset: "
            r2.append(r3)
            r2.append(r5)
            java.lang.String r3 = ", length: "
            r2.append(r3)
            r2.append(r6)
            java.lang.String r3 = " (expected: offset <= offset + length <= dsts.length ("
            r2.append(r3)
            int r3 = r4.length
            r2.append(r3)
            java.lang.String r3 = "))"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r0.<init>(r2)
            throw r0
        L_0x0255:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "dsts is null"
            r0.<init>(r2)
            throw r0
        L_0x025d:
            java.lang.IndexOutOfBoundsException r4 = new java.lang.IndexOutOfBoundsException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "offset: "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r2 = ", length: "
            r5.append(r2)
            r5.append(r3)
            java.lang.String r2 = " (expected: offset <= offset + length <= srcs.length ("
            r5.append(r2)
            int r0 = r0.length
            r5.append(r0)
            java.lang.String r0 = "))"
            r5.append(r0)
            java.lang.String r0 = r5.toString()
            r4.<init>(r0)
            throw r4
        L_0x028a:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            java.lang.String r2 = "srcs"
            r0.<init>(r2)
            goto L_0x0293
        L_0x0292:
            throw r0
        L_0x0293:
            goto L_0x0292
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.unwrap(java.nio.ByteBuffer[], int, int, java.nio.ByteBuffer[], int, int):javax.net.ssl.SSLEngineResult");
    }

    private SSLEngineResult sslReadErrorResult(int i, int i2, int i3) throws SSLException {
        String errorString = SSL.getErrorString((long) i);
        if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
            if (this.handshakeException == null && this.handshakeState != HandshakeState.FINISHED) {
                this.handshakeException = new SSLHandshakeException(errorString);
            }
            return new SSLEngineResult(Status.OK, HandshakeStatus.NEED_WRAP, i2, i3);
        }
        throw shutdownWithError("SSL_read", errorString);
    }

    private void closeAll() throws SSLException {
        this.receivedShutdown = true;
        closeOutbound();
        closeInbound();
    }

    private void rejectRemoteInitiatedRenegotiation() throws SSLHandshakeException {
        if (this.rejectRemoteInitiatedRenegotiation && SSL.getHandshakeCount(this.ssl) > 1) {
            shutdown();
            throw new SSLHandshakeException("remote-initiated renegotiation not allowed");
        }
    }

    public final SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, ByteBuffer[] byteBufferArr2) throws SSLException {
        return unwrap(byteBufferArr, 0, byteBufferArr.length, byteBufferArr2, 0, byteBufferArr2.length);
    }

    private ByteBuffer[] singleSrcBuffer(ByteBuffer byteBuffer) {
        ByteBuffer[] byteBufferArr = this.singleSrcBuffer;
        byteBufferArr[0] = byteBuffer;
        return byteBufferArr;
    }

    private void resetSingleSrcBuffer() {
        this.singleSrcBuffer[0] = null;
    }

    private ByteBuffer[] singleDstBuffer(ByteBuffer byteBuffer) {
        ByteBuffer[] byteBufferArr = this.singleDstBuffer;
        byteBufferArr[0] = byteBuffer;
        return byteBufferArr;
    }

    private void resetSingleDstBuffer() {
        this.singleDstBuffer[0] = null;
    }

    public final synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr, int i, int i2) throws SSLException {
        SSLEngineResult unwrap;
        try {
            unwrap = unwrap(singleSrcBuffer(byteBuffer), 0, 1, byteBufferArr, i, i2);
            resetSingleSrcBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            throw th;
        }
        return unwrap;
    }

    public final synchronized SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        SSLEngineResult wrap;
        try {
            wrap = wrap(singleSrcBuffer(byteBuffer), byteBuffer2);
            resetSingleSrcBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            throw th;
        }
        return wrap;
    }

    public final synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        SSLEngineResult unwrap;
        try {
            unwrap = unwrap(singleSrcBuffer(byteBuffer), singleDstBuffer(byteBuffer2));
            resetSingleSrcBuffer();
            resetSingleDstBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            resetSingleDstBuffer();
            throw th;
        }
        return unwrap;
    }

    public final synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArr) throws SSLException {
        SSLEngineResult unwrap;
        try {
            unwrap = unwrap(singleSrcBuffer(byteBuffer), byteBufferArr);
            resetSingleSrcBuffer();
        } catch (Throwable th) {
            resetSingleSrcBuffer();
            throw th;
        }
        return unwrap;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0027, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void closeInbound() throws javax.net.ssl.SSLException {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.isInboundDone     // Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            r0 = 1
            r2.isInboundDone = r0     // Catch:{ all -> 0x0028 }
            boolean r0 = r2.isOutboundDone()     // Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x0013
            r2.shutdown()     // Catch:{ all -> 0x0028 }
        L_0x0013:
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r0 = r2.handshakeState     // Catch:{ all -> 0x0028 }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r1 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED     // Catch:{ all -> 0x0028 }
            if (r0 == r1) goto L_0x0026
            boolean r0 = r2.receivedShutdown     // Catch:{ all -> 0x0028 }
            if (r0 == 0) goto L_0x001e
            goto L_0x0026
        L_0x001e:
            javax.net.ssl.SSLException r0 = new javax.net.ssl.SSLException     // Catch:{ all -> 0x0028 }
            java.lang.String r1 = "Inbound closed before receiving peer's close_notify: possible truncation attack?"
            r0.<init>(r1)     // Catch:{ all -> 0x0028 }
            throw r0     // Catch:{ all -> 0x0028 }
        L_0x0026:
            monitor-exit(r2)
            return
        L_0x0028:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.closeInbound():void");
    }

    public final synchronized boolean isInboundDone() {
        return this.isInboundDone;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void closeOutbound() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.outboundClosed     // Catch:{ all -> 0x002c }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            r0 = 1
            r2.outboundClosed = r0     // Catch:{ all -> 0x002c }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r0 = r2.handshakeState     // Catch:{ all -> 0x002c }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r1 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.NOT_STARTED     // Catch:{ all -> 0x002c }
            if (r0 == r1) goto L_0x0027
            boolean r0 = r2.isDestroyed()     // Catch:{ all -> 0x002c }
            if (r0 != 0) goto L_0x0027
            long r0 = r2.ssl     // Catch:{ all -> 0x002c }
            int r0 = io.netty.internal.tcnative.SSL.getShutdown(r0)     // Catch:{ all -> 0x002c }
            int r1 = io.netty.internal.tcnative.SSL.SSL_SENT_SHUTDOWN     // Catch:{ all -> 0x002c }
            r0 = r0 & r1
            int r1 = io.netty.internal.tcnative.SSL.SSL_SENT_SHUTDOWN     // Catch:{ all -> 0x002c }
            if (r0 == r1) goto L_0x002a
            r2.doSSLShutdown()     // Catch:{ all -> 0x002c }
            goto L_0x002a
        L_0x0027:
            r2.shutdown()     // Catch:{ all -> 0x002c }
        L_0x002a:
            monitor-exit(r2)
            return
        L_0x002c:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.closeOutbound():void");
    }

    private boolean doSSLShutdown() {
        if (SSL.isInInit(this.ssl) != 0) {
            return false;
        }
        int shutdownSSL = SSL.shutdownSSL(this.ssl);
        if (shutdownSSL < 0) {
            int error = SSL.getError(this.ssl, shutdownSSL);
            if (error == SSL.SSL_ERROR_SYSCALL || error == SSL.SSL_ERROR_SSL) {
                if (logger.isDebugEnabled()) {
                    logger.debug("SSL_shutdown failed: OpenSSL error: {}", (Object) SSL.getLastError());
                }
                shutdown();
                return false;
            }
            SSL.clearError();
        }
        return true;
    }

    public final synchronized boolean isOutboundDone() {
        return this.outboundClosed && (this.networkBIO == 0 || SSL.bioLengthNonApplication(this.networkBIO) == 0);
    }

    public final String[] getSupportedCipherSuites() {
        return (String[]) OpenSsl.AVAILABLE_CIPHER_SUITES.toArray(new String[OpenSsl.AVAILABLE_CIPHER_SUITES.size()]);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0016, code lost:
        if (r1 >= r0.length) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0018, code lost:
        r2 = toJavaCipherSuite(r0[r1]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001e, code lost:
        if (r2 == null) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0020, code lost:
        r0[r1] = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0022, code lost:
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0025, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0026, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000e, code lost:
        if (r0 != null) goto L_0x0013;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0012, code lost:
        return p043io.netty.util.internal.EmptyArrays.EMPTY_STRINGS;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0013, code lost:
        monitor-enter(r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String[] getEnabledCipherSuites() {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.isDestroyed()     // Catch:{ all -> 0x002e }
            if (r0 != 0) goto L_0x002a
            long r0 = r3.ssl     // Catch:{ all -> 0x002e }
            java.lang.String[] r0 = io.netty.internal.tcnative.SSL.getCiphers(r0)     // Catch:{ all -> 0x002e }
            monitor-exit(r3)     // Catch:{ all -> 0x002e }
            if (r0 != 0) goto L_0x0013
            java.lang.String[] r0 = p043io.netty.util.internal.EmptyArrays.EMPTY_STRINGS
            return r0
        L_0x0013:
            monitor-enter(r3)
            r1 = 0
        L_0x0015:
            int r2 = r0.length     // Catch:{ all -> 0x0027 }
            if (r1 >= r2) goto L_0x0025
            r2 = r0[r1]     // Catch:{ all -> 0x0027 }
            java.lang.String r2 = r3.toJavaCipherSuite(r2)     // Catch:{ all -> 0x0027 }
            if (r2 == 0) goto L_0x0022
            r0[r1] = r2     // Catch:{ all -> 0x0027 }
        L_0x0022:
            int r1 = r1 + 1
            goto L_0x0015
        L_0x0025:
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            return r0
        L_0x0027:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0027 }
            throw r0
        L_0x002a:
            java.lang.String[] r0 = p043io.netty.util.internal.EmptyArrays.EMPTY_STRINGS     // Catch:{ all -> 0x002e }
            monitor-exit(r3)     // Catch:{ all -> 0x002e }
            return r0
        L_0x002e:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x002e }
            goto L_0x0032
        L_0x0031:
            throw r0
        L_0x0032:
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.getEnabledCipherSuites():java.lang.String[]");
    }

    public final void setEnabledCipherSuites(String[] strArr) {
        ObjectUtil.checkNotNull(strArr, "cipherSuites");
        StringBuilder sb = new StringBuilder();
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String str = strArr[i];
            if (str == null) {
                break;
            }
            String openSsl = CipherSuiteConverter.toOpenSsl(str);
            if (openSsl == null) {
                openSsl = str;
            }
            if (OpenSsl.isCipherSuiteAvailable(openSsl)) {
                sb.append(openSsl);
                sb.append(':');
                i++;
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("unsupported cipher suite: ");
                sb2.append(str);
                sb2.append('(');
                sb2.append(openSsl);
                sb2.append(')');
                throw new IllegalArgumentException(sb2.toString());
            }
        }
        if (sb.length() != 0) {
            sb.setLength(sb.length() - 1);
            String sb3 = sb.toString();
            synchronized (this) {
                if (!isDestroyed()) {
                    try {
                        SSL.setCipherSuites(this.ssl, sb3);
                    } catch (Exception e) {
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("failed to enable cipher suites: ");
                        sb4.append(sb3);
                        throw new IllegalStateException(sb4.toString(), e);
                    }
                } else {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("failed to enable cipher suites: ");
                    sb5.append(sb3);
                    throw new IllegalStateException(sb5.toString());
                }
            }
            return;
        }
        throw new IllegalArgumentException("empty cipher suites");
    }

    public final String[] getSupportedProtocols() {
        return (String[]) OpenSsl.SUPPORTED_PROTOCOLS_SET.toArray(new String[OpenSsl.SUPPORTED_PROTOCOLS_SET.size()]);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0030, code lost:
        if (isProtocolEnabled(r1, io.netty.internal.tcnative.SSL.SSL_OP_NO_TLSv1_1, "TLSv1.1") == false) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0032, code lost:
        r0.add("TLSv1.1");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x003f, code lost:
        if (isProtocolEnabled(r1, io.netty.internal.tcnative.SSL.SSL_OP_NO_TLSv1_2, "TLSv1.2") == false) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0041, code lost:
        r0.add("TLSv1.2");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004e, code lost:
        if (isProtocolEnabled(r1, io.netty.internal.tcnative.SSL.SSL_OP_NO_SSLv2, "SSLv2") == false) goto L_0x0055;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0050, code lost:
        r0.add("SSLv2");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005d, code lost:
        if (isProtocolEnabled(r1, io.netty.internal.tcnative.SSL.SSL_OP_NO_SSLv3, "SSLv3") == false) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x005f, code lost:
        r0.add("SSLv3");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0070, code lost:
        return (java.lang.String[]) r0.toArray(new java.lang.String[r0.size()]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0021, code lost:
        if (isProtocolEnabled(r1, io.netty.internal.tcnative.SSL.SSL_OP_NO_TLSv1, "TLSv1") == false) goto L_0x0028;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0023, code lost:
        r0.add("TLSv1");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String[] getEnabledProtocols() {
        /*
            r4 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 6
            r0.<init>(r1)
            java.lang.String r1 = "SSLv2Hello"
            r0.add(r1)
            monitor-enter(r4)
            boolean r1 = r4.isDestroyed()     // Catch:{ all -> 0x007c }
            if (r1 != 0) goto L_0x0071
            long r1 = r4.ssl     // Catch:{ all -> 0x007c }
            int r1 = io.netty.internal.tcnative.SSL.getOptions(r1)     // Catch:{ all -> 0x007c }
            monitor-exit(r4)     // Catch:{ all -> 0x007c }
            int r2 = io.netty.internal.tcnative.SSL.SSL_OP_NO_TLSv1
            java.lang.String r3 = "TLSv1"
            boolean r2 = isProtocolEnabled(r1, r2, r3)
            if (r2 == 0) goto L_0x0028
            java.lang.String r2 = "TLSv1"
            r0.add(r2)
        L_0x0028:
            int r2 = io.netty.internal.tcnative.SSL.SSL_OP_NO_TLSv1_1
            java.lang.String r3 = "TLSv1.1"
            boolean r2 = isProtocolEnabled(r1, r2, r3)
            if (r2 == 0) goto L_0x0037
            java.lang.String r2 = "TLSv1.1"
            r0.add(r2)
        L_0x0037:
            int r2 = io.netty.internal.tcnative.SSL.SSL_OP_NO_TLSv1_2
            java.lang.String r3 = "TLSv1.2"
            boolean r2 = isProtocolEnabled(r1, r2, r3)
            if (r2 == 0) goto L_0x0046
            java.lang.String r2 = "TLSv1.2"
            r0.add(r2)
        L_0x0046:
            int r2 = io.netty.internal.tcnative.SSL.SSL_OP_NO_SSLv2
            java.lang.String r3 = "SSLv2"
            boolean r2 = isProtocolEnabled(r1, r2, r3)
            if (r2 == 0) goto L_0x0055
            java.lang.String r2 = "SSLv2"
            r0.add(r2)
        L_0x0055:
            int r2 = io.netty.internal.tcnative.SSL.SSL_OP_NO_SSLv3
            java.lang.String r3 = "SSLv3"
            boolean r1 = isProtocolEnabled(r1, r2, r3)
            if (r1 == 0) goto L_0x0064
            java.lang.String r1 = "SSLv3"
            r0.add(r1)
        L_0x0064:
            int r1 = r0.size()
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.Object[] r0 = r0.toArray(r1)
            java.lang.String[] r0 = (java.lang.String[]) r0
            return r0
        L_0x0071:
            r1 = 1
            java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ all -> 0x007c }
            java.lang.Object[] r0 = r0.toArray(r1)     // Catch:{ all -> 0x007c }
            java.lang.String[] r0 = (java.lang.String[]) r0     // Catch:{ all -> 0x007c }
            monitor-exit(r4)     // Catch:{ all -> 0x007c }
            return r0
        L_0x007c:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x007c }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.getEnabledProtocols():java.lang.String[]");
    }

    private static boolean isProtocolEnabled(int i, int i2, String str) {
        return (i & i2) == 0 && OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(str);
    }

    public final void setEnabledProtocols(String[] strArr) {
        if (strArr != null) {
            int length = strArr.length;
            int i = 0;
            int i2 = 0;
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            boolean z4 = false;
            boolean z5 = false;
            while (i2 < length) {
                String str = strArr[i2];
                if (OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(str)) {
                    if (str.equals("SSLv2")) {
                        z = true;
                    } else if (str.equals("SSLv3")) {
                        z2 = true;
                    } else if (str.equals("TLSv1")) {
                        z3 = true;
                    } else if (str.equals("TLSv1.1")) {
                        z4 = true;
                    } else if (str.equals("TLSv1.2")) {
                        z5 = true;
                    }
                    i2++;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Protocol ");
                    sb.append(str);
                    sb.append(" is not supported.");
                    throw new IllegalArgumentException(sb.toString());
                }
            }
            synchronized (this) {
                if (!isDestroyed()) {
                    SSL.clearOptions(this.ssl, SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_NO_TLSv1 | SSL.SSL_OP_NO_TLSv1_1 | SSL.SSL_OP_NO_TLSv1_2);
                    if (!z) {
                        i = 0 | SSL.SSL_OP_NO_SSLv2;
                    }
                    if (!z2) {
                        i |= SSL.SSL_OP_NO_SSLv3;
                    }
                    if (!z3) {
                        i |= SSL.SSL_OP_NO_TLSv1;
                    }
                    if (!z4) {
                        i |= SSL.SSL_OP_NO_TLSv1_1;
                    }
                    if (!z5) {
                        i |= SSL.SSL_OP_NO_TLSv1_2;
                    }
                    SSL.setOptions(this.ssl, i);
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("failed to enable protocols: ");
                    sb2.append(Arrays.asList(strArr));
                    throw new IllegalStateException(sb2.toString());
                }
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    public final SSLSession getSession() {
        return this.session;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x007a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void beginHandshake() throws javax.net.ssl.SSLException {
        /*
            r4 = this;
            monitor-enter(r4)
            int[] r0 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.C57312.f3735xea902ccf     // Catch:{ all -> 0x007b }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r1 = r4.handshakeState     // Catch:{ all -> 0x007b }
            int r1 = r1.ordinal()     // Catch:{ all -> 0x007b }
            r0 = r0[r1]     // Catch:{ all -> 0x007b }
            r1 = 1
            if (r0 == r1) goto L_0x0072
            r2 = 2
            if (r0 == r2) goto L_0x0028
            r1 = 3
            if (r0 == r1) goto L_0x001e
            r1 = 4
            if (r0 != r1) goto L_0x0018
            goto L_0x0079
        L_0x0018:
            java.lang.Error r0 = new java.lang.Error     // Catch:{ all -> 0x007b }
            r0.<init>()     // Catch:{ all -> 0x007b }
            throw r0     // Catch:{ all -> 0x007b }
        L_0x001e:
            javax.net.ssl.SSLException r0 = BEGIN_HANDSHAKE_ENGINE_CLOSED     // Catch:{ all -> 0x007b }
            r4.checkEngineClosed(r0)     // Catch:{ all -> 0x007b }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r0 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY     // Catch:{ all -> 0x007b }
            r4.handshakeState = r0     // Catch:{ all -> 0x007b }
            goto L_0x0079
        L_0x0028:
            boolean r0 = r4.clientMode     // Catch:{ all -> 0x007b }
            if (r0 != 0) goto L_0x006f
            long r2 = r4.ssl     // Catch:{ all -> 0x007b }
            int r0 = io.netty.internal.tcnative.SSL.renegotiate(r2)     // Catch:{ all -> 0x007b }
            if (r0 != r1) goto L_0x004b
            long r2 = r4.ssl     // Catch:{ all -> 0x007b }
            int r0 = io.netty.internal.tcnative.SSL.doHandshake(r2)     // Catch:{ all -> 0x007b }
            if (r0 == r1) goto L_0x003d
            goto L_0x004b
        L_0x003d:
            long r0 = r4.ssl     // Catch:{ all -> 0x007b }
            int r2 = io.netty.internal.tcnative.SSL.SSL_ST_ACCEPT     // Catch:{ all -> 0x007b }
            io.netty.internal.tcnative.SSL.setState(r0, r2)     // Catch:{ all -> 0x007b }
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x007b }
            r4.lastAccessed = r0     // Catch:{ all -> 0x007b }
            goto L_0x0072
        L_0x004b:
            long r2 = r4.ssl     // Catch:{ all -> 0x007b }
            int r0 = io.netty.internal.tcnative.SSL.getError(r2, r0)     // Catch:{ all -> 0x007b }
            int r2 = io.netty.internal.tcnative.SSL.SSL_ERROR_WANT_READ     // Catch:{ all -> 0x007b }
            if (r0 == r2) goto L_0x0061
            int r2 = io.netty.internal.tcnative.SSL.SSL_ERROR_WANT_WRITE     // Catch:{ all -> 0x007b }
            if (r0 != r2) goto L_0x005a
            goto L_0x0061
        L_0x005a:
            java.lang.String r0 = "renegotiation failed"
            javax.net.ssl.SSLException r0 = r4.shutdownWithError(r0)     // Catch:{ all -> 0x007b }
            throw r0     // Catch:{ all -> 0x007b }
        L_0x0061:
            r4.renegotiationPending = r1     // Catch:{ all -> 0x007b }
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r0 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY     // Catch:{ all -> 0x007b }
            r4.handshakeState = r0     // Catch:{ all -> 0x007b }
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x007b }
            r4.lastAccessed = r0     // Catch:{ all -> 0x007b }
            monitor-exit(r4)
            return
        L_0x006f:
            javax.net.ssl.SSLException r0 = RENEGOTIATION_UNSUPPORTED     // Catch:{ all -> 0x007b }
            throw r0     // Catch:{ all -> 0x007b }
        L_0x0072:
            io.netty.handler.ssl.ReferenceCountedOpenSslEngine$HandshakeState r0 = p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.HandshakeState.STARTED_EXPLICITLY     // Catch:{ all -> 0x007b }
            r4.handshakeState = r0     // Catch:{ all -> 0x007b }
            r4.handshake()     // Catch:{ all -> 0x007b }
        L_0x0079:
            monitor-exit(r4)
            return
        L_0x007b:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.ReferenceCountedOpenSslEngine.beginHandshake():void");
    }

    private void checkEngineClosed(SSLException sSLException) throws SSLException {
        if (isDestroyed()) {
            throw sSLException;
        }
    }

    private static HandshakeStatus pendingStatus(int i) {
        return i > 0 ? HandshakeStatus.NEED_WRAP : HandshakeStatus.NEED_UNWRAP;
    }

    /* access modifiers changed from: private */
    public static boolean isEmpty(Object[] objArr) {
        return objArr == null || objArr.length == 0;
    }

    /* access modifiers changed from: private */
    public static boolean isEmpty(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    private HandshakeStatus handshake() throws SSLException {
        if (this.handshakeState == HandshakeState.FINISHED) {
            return HandshakeStatus.FINISHED;
        }
        checkEngineClosed(HANDSHAKE_ENGINE_CLOSED);
        SSLHandshakeException sSLHandshakeException = this.handshakeException;
        if (sSLHandshakeException == null) {
            this.engineMap.add(this);
            if (this.lastAccessed == -1) {
                this.lastAccessed = System.currentTimeMillis();
            }
            if (!this.certificateSet) {
                OpenSslKeyMaterialManager openSslKeyMaterialManager = this.keyMaterialManager;
                if (openSslKeyMaterialManager != null) {
                    this.certificateSet = true;
                    openSslKeyMaterialManager.setKeyMaterial(this);
                }
            }
            int doHandshake = SSL.doHandshake(this.ssl);
            if (doHandshake <= 0) {
                SSLHandshakeException sSLHandshakeException2 = this.handshakeException;
                if (sSLHandshakeException2 == null) {
                    int error = SSL.getError(this.ssl, doHandshake);
                    if (error == SSL.SSL_ERROR_WANT_READ || error == SSL.SSL_ERROR_WANT_WRITE) {
                        return pendingStatus(SSL.bioLengthNonApplication(this.networkBIO));
                    }
                    throw shutdownWithError("SSL_do_handshake");
                }
                this.handshakeException = null;
                shutdown();
                throw sSLHandshakeException2;
            }
            this.session.handshakeFinished();
            this.engineMap.remove(this.ssl);
            return HandshakeStatus.FINISHED;
        } else if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
            return HandshakeStatus.NEED_WRAP;
        } else {
            this.handshakeException = null;
            shutdown();
            throw sSLHandshakeException;
        }
    }

    private HandshakeStatus mayFinishHandshake(HandshakeStatus handshakeStatus) throws SSLException {
        return (handshakeStatus != HandshakeStatus.NOT_HANDSHAKING || this.handshakeState == HandshakeState.FINISHED) ? handshakeStatus : handshake();
    }

    public final synchronized HandshakeStatus getHandshakeStatus() {
        return needPendingStatus() ? pendingStatus(SSL.bioLengthNonApplication(this.networkBIO)) : HandshakeStatus.NOT_HANDSHAKING;
    }

    private HandshakeStatus getHandshakeStatus(int i) {
        return needPendingStatus() ? pendingStatus(i) : HandshakeStatus.NOT_HANDSHAKING;
    }

    private boolean needPendingStatus() {
        return this.handshakeState != HandshakeState.NOT_STARTED && !isDestroyed() && (this.handshakeState != HandshakeState.FINISHED || isInboundDone() || isOutboundDone());
    }

    /* access modifiers changed from: private */
    public String toJavaCipherSuite(String str) {
        if (str == null) {
            return null;
        }
        return CipherSuiteConverter.toJava(str, toJavaCipherSuitePrefix(SSL.getVersion(this.ssl)));
    }

    private static String toJavaCipherSuitePrefix(String str) {
        char c = 0;
        if (str != null && !str.isEmpty()) {
            c = str.charAt(0);
        }
        if (c != 'S') {
            return c != 'T' ? "UNKNOWN" : "TLS";
        }
        return "SSL";
    }

    public final void setUseClientMode(boolean z) {
        if (z != this.clientMode) {
            throw new UnsupportedOperationException();
        }
    }

    public final boolean getUseClientMode() {
        return this.clientMode;
    }

    public final void setNeedClientAuth(boolean z) {
        setClientAuth(z ? ClientAuth.REQUIRE : ClientAuth.NONE);
    }

    public final boolean getNeedClientAuth() {
        return this.clientAuth == ClientAuth.REQUIRE;
    }

    public final void setWantClientAuth(boolean z) {
        setClientAuth(z ? ClientAuth.OPTIONAL : ClientAuth.NONE);
    }

    public final boolean getWantClientAuth() {
        return this.clientAuth == ClientAuth.OPTIONAL;
    }

    public final synchronized void setVerify(int i, int i2) {
        SSL.setVerify(this.ssl, i, i2);
    }

    private void setClientAuth(ClientAuth clientAuth2) {
        if (!this.clientMode) {
            synchronized (this) {
                if (this.clientAuth != clientAuth2) {
                    int i = C57312.$SwitchMap$io$netty$handler$ssl$ClientAuth[clientAuth2.ordinal()];
                    if (i == 1) {
                        SSL.setVerify(this.ssl, 0, 10);
                    } else if (i == 2) {
                        SSL.setVerify(this.ssl, 2, 10);
                    } else if (i == 3) {
                        SSL.setVerify(this.ssl, 1, 10);
                    } else {
                        throw new Error(clientAuth2.toString());
                    }
                    this.clientAuth = clientAuth2;
                }
            }
        }
    }

    public final void setEnableSessionCreation(boolean z) {
        if (z) {
            throw new UnsupportedOperationException();
        }
    }

    public final synchronized SSLParameters getSSLParameters() {
        SSLParameters sSLParameters;
        sSLParameters = super.getSSLParameters();
        int javaVersion = PlatformDependent.javaVersion();
        if (javaVersion >= 7) {
            sSLParameters.setEndpointIdentificationAlgorithm(this.endPointIdentificationAlgorithm);
            Java7SslParametersUtils.setAlgorithmConstraints(sSLParameters, this.algorithmConstraints);
            if (javaVersion >= 8) {
                if (this.sniHostNames != null) {
                    Java8SslUtils.setSniHostNames(sSLParameters, this.sniHostNames);
                }
                if (!isDestroyed()) {
                    Java8SslUtils.setUseCipherSuitesOrder(sSLParameters, (SSL.getOptions(this.ssl) & SSL.SSL_OP_CIPHER_SERVER_PREFERENCE) != 0);
                }
                Java8SslUtils.setSNIMatchers(sSLParameters, this.matchers);
            }
        }
        return sSLParameters;
    }

    public final synchronized void setSSLParameters(SSLParameters sSLParameters) {
        int javaVersion = PlatformDependent.javaVersion();
        if (javaVersion >= 7) {
            if (sSLParameters.getAlgorithmConstraints() == null) {
                if (javaVersion >= 8) {
                    if (!isDestroyed()) {
                        if (this.clientMode) {
                            List<String> sniHostNames2 = Java8SslUtils.getSniHostNames(sSLParameters);
                            for (String tlsExtHostName : sniHostNames2) {
                                SSL.setTlsExtHostName(this.ssl, tlsExtHostName);
                            }
                            this.sniHostNames = sniHostNames2;
                        }
                        if (Java8SslUtils.getUseCipherSuitesOrder(sSLParameters)) {
                            SSL.setOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
                        } else {
                            SSL.clearOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
                        }
                    }
                    this.matchers = sSLParameters.getSNIMatchers();
                }
                String endpointIdentificationAlgorithm = sSLParameters.getEndpointIdentificationAlgorithm();
                boolean z = endpointIdentificationAlgorithm != null && !endpointIdentificationAlgorithm.isEmpty();
                SSL.setHostNameValidation(this.ssl, 0, z ? getPeerHost() : null);
                if (this.clientMode && z) {
                    SSL.setVerify(this.ssl, 2, -1);
                }
                this.endPointIdentificationAlgorithm = endpointIdentificationAlgorithm;
                this.algorithmConstraints = sSLParameters.getAlgorithmConstraints();
            } else {
                throw new IllegalArgumentException("AlgorithmConstraints are not supported.");
            }
        }
        super.setSSLParameters(sSLParameters);
    }

    /* access modifiers changed from: private */
    public boolean isDestroyed() {
        return this.destroyed != 0;
    }

    static int calculateOutNetBufSize(int i, int i2) {
        return (int) Math.min(16474, ((long) i) + (((long) i2) * 90));
    }

    /* access modifiers changed from: 0000 */
    public final boolean checkSniHostnameMatch(String str) {
        return Java8SslUtils.checkSniHostnameMatch(this.matchers, str);
    }
}
