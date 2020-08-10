package p043io.netty.handler.ssl;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import org.conscrypt.Conscrypt.Engines;
import org.conscrypt.HandshakeListener;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListener;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelector;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.handler.ssl.ConscryptAlpnSslEngine */
abstract class ConscryptAlpnSslEngine extends JdkSslEngine {
    private static final Class<?> ENGINES_CLASS = getEnginesClass();

    /* renamed from: io.netty.handler.ssl.ConscryptAlpnSslEngine$ClientEngine */
    private static final class ClientEngine extends ConscryptAlpnSslEngine {
        private final ProtocolSelectionListener protocolListener;

        ClientEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator) {
            super(sSLEngine, jdkApplicationProtocolNegotiator.protocols());
            Engines.setHandshakeListener(sSLEngine, new HandshakeListener() {
                public void onHandshakeFinished() throws SSLException {
                    ClientEngine.this.selectProtocol();
                }
            });
            this.protocolListener = (ProtocolSelectionListener) ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolListenerFactory().newListener(this, jdkApplicationProtocolNegotiator.protocols()), "protocolListener");
        }

        /* access modifiers changed from: private */
        public void selectProtocol() throws SSLException {
            try {
                this.protocolListener.selected(Engines.getAlpnSelectedProtocol(getWrappedEngine()));
            } catch (Throwable th) {
                throw SslUtils.toSSLHandshakeException(th);
            }
        }
    }

    /* renamed from: io.netty.handler.ssl.ConscryptAlpnSslEngine$ServerEngine */
    private static final class ServerEngine extends ConscryptAlpnSslEngine {
        private final ProtocolSelector protocolSelector;

        ServerEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator) {
            super(sSLEngine, jdkApplicationProtocolNegotiator.protocols());
            Engines.setHandshakeListener(sSLEngine, new HandshakeListener() {
                public void onHandshakeFinished() throws SSLException {
                    ServerEngine.this.selectProtocol();
                }
            });
            this.protocolSelector = (ProtocolSelector) ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(jdkApplicationProtocolNegotiator.protocols())), "protocolSelector");
        }

        /* access modifiers changed from: private */
        public void selectProtocol() throws SSLException {
            List list;
            try {
                String alpnSelectedProtocol = Engines.getAlpnSelectedProtocol(getWrappedEngine());
                ProtocolSelector protocolSelector2 = this.protocolSelector;
                if (alpnSelectedProtocol != null) {
                    list = Collections.singletonList(alpnSelectedProtocol);
                } else {
                    list = Collections.emptyList();
                }
                protocolSelector2.select(list);
            } catch (Throwable th) {
                throw SslUtils.toSSLHandshakeException(th);
            }
        }
    }

    static boolean isAvailable() {
        return ENGINES_CLASS != null && PlatformDependent.javaVersion() >= 8;
    }

    static boolean isEngineSupported(SSLEngine sSLEngine) {
        return isAvailable() && isConscryptEngine(sSLEngine, ENGINES_CLASS);
    }

    static ConscryptAlpnSslEngine newClientEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator) {
        return new ClientEngine(sSLEngine, jdkApplicationProtocolNegotiator);
    }

    static ConscryptAlpnSslEngine newServerEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator) {
        return new ServerEngine(sSLEngine, jdkApplicationProtocolNegotiator);
    }

    private ConscryptAlpnSslEngine(SSLEngine sSLEngine, List<String> list) {
        super(sSLEngine);
        Engines.setAlpnProtocols(sSLEngine, (String[]) list.toArray(new String[list.size()]));
    }

    /* access modifiers changed from: 0000 */
    public final int calculateOutNetBufSize(int i, int i2) {
        return (int) Math.min(2147483647L, ((long) i) + (((long) Engines.maxSealOverhead(getWrappedEngine())) * ((long) i2)));
    }

    /* access modifiers changed from: 0000 */
    public final SSLEngineResult unwrap(ByteBuffer[] byteBufferArr, ByteBuffer[] byteBufferArr2) throws SSLException {
        return Engines.unwrap(getWrappedEngine(), byteBufferArr, byteBufferArr2);
    }

    private static Class<?> getEnginesClass() {
        try {
            Class<?> cls = Class.forName("org.conscrypt.Conscrypt$Engines", true, ConscryptAlpnSslEngine.class.getClassLoader());
            getIsConscryptMethod(cls);
            return cls;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean isConscryptEngine(SSLEngine sSLEngine, Class<?> cls) {
        try {
            return ((Boolean) getIsConscryptMethod(cls).invoke(null, new Object[]{sSLEngine})).booleanValue();
        } catch (Throwable unused) {
            return false;
        }
    }

    private static Method getIsConscryptMethod(Class<?> cls) throws NoSuchMethodException {
        return cls.getMethod("isConscrypt", new Class[]{SSLEngine.class});
    }
}
