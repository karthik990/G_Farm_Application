package p043io.netty.handler.ssl;

import java.util.List;
import javax.net.ssl.SSLEngine;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory;

/* renamed from: io.netty.handler.ssl.JdkAlpnApplicationProtocolNegotiator */
public final class JdkAlpnApplicationProtocolNegotiator extends JdkBaseApplicationProtocolNegotiator {
    private static final SslEngineWrapperFactory ALPN_WRAPPER = (AVAILABLE ? new AlpnWrapper() : new FailureWrapper());
    private static final boolean AVAILABLE = (ConscryptAlpnSslEngine.isAvailable() || JettyAlpnSslEngine.isAvailable());

    /* renamed from: io.netty.handler.ssl.JdkAlpnApplicationProtocolNegotiator$AlpnWrapper */
    private static final class AlpnWrapper implements SslEngineWrapperFactory {
        private AlpnWrapper() {
        }

        public SSLEngine wrapSslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean z) {
            JettyAlpnSslEngine jettyAlpnSslEngine;
            ConscryptAlpnSslEngine conscryptAlpnSslEngine;
            if (ConscryptAlpnSslEngine.isEngineSupported(sSLEngine)) {
                if (z) {
                    conscryptAlpnSslEngine = ConscryptAlpnSslEngine.newServerEngine(sSLEngine, jdkApplicationProtocolNegotiator);
                } else {
                    conscryptAlpnSslEngine = ConscryptAlpnSslEngine.newClientEngine(sSLEngine, jdkApplicationProtocolNegotiator);
                }
                return conscryptAlpnSslEngine;
            } else if (JettyAlpnSslEngine.isAvailable()) {
                if (z) {
                    jettyAlpnSslEngine = JettyAlpnSslEngine.newServerEngine(sSLEngine, jdkApplicationProtocolNegotiator);
                } else {
                    jettyAlpnSslEngine = JettyAlpnSslEngine.newClientEngine(sSLEngine, jdkApplicationProtocolNegotiator);
                }
                return jettyAlpnSslEngine;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to wrap SSLEngine of type ");
                sb.append(sSLEngine.getClass().getName());
                throw new RuntimeException(sb.toString());
            }
        }
    }

    /* renamed from: io.netty.handler.ssl.JdkAlpnApplicationProtocolNegotiator$FailureWrapper */
    private static final class FailureWrapper implements SslEngineWrapperFactory {
        private FailureWrapper() {
        }

        public SSLEngine wrapSslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean z) {
            throw new RuntimeException("ALPN unsupported. Is your classpath configured correctly? For Conscrypt, add the appropriate Conscrypt JAR to classpath and set the security provider. For Jetty-ALPN, see http://www.eclipse.org/jetty/documentation/current/alpn-chapter.html#alpn-starting");
        }
    }

    public /* bridge */ /* synthetic */ ProtocolSelectionListenerFactory protocolListenerFactory() {
        return super.protocolListenerFactory();
    }

    public /* bridge */ /* synthetic */ ProtocolSelectorFactory protocolSelectorFactory() {
        return super.protocolSelectorFactory();
    }

    public /* bridge */ /* synthetic */ List protocols() {
        return super.protocols();
    }

    public /* bridge */ /* synthetic */ SslEngineWrapperFactory wrapperFactory() {
        return super.wrapperFactory();
    }

    public JdkAlpnApplicationProtocolNegotiator(Iterable<String> iterable) {
        this(false, iterable);
    }

    public JdkAlpnApplicationProtocolNegotiator(String... strArr) {
        this(false, strArr);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean z, Iterable<String> iterable) {
        this(z, z, iterable);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean z, String... strArr) {
        this(z, z, strArr);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean z, boolean z2, Iterable<String> iterable) {
        this(z2 ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, z ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, iterable);
    }

    public JdkAlpnApplicationProtocolNegotiator(boolean z, boolean z2, String... strArr) {
        this(z2 ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, z ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, strArr);
    }

    public JdkAlpnApplicationProtocolNegotiator(ProtocolSelectorFactory protocolSelectorFactory, ProtocolSelectionListenerFactory protocolSelectionListenerFactory, Iterable<String> iterable) {
        super(ALPN_WRAPPER, protocolSelectorFactory, protocolSelectionListenerFactory, iterable);
    }

    public JdkAlpnApplicationProtocolNegotiator(ProtocolSelectorFactory protocolSelectorFactory, ProtocolSelectionListenerFactory protocolSelectionListenerFactory, String... strArr) {
        super(ALPN_WRAPPER, protocolSelectorFactory, protocolSelectionListenerFactory, strArr);
    }
}
