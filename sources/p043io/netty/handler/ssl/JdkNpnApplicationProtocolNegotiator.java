package p043io.netty.handler.ssl;

import java.util.List;
import javax.net.ssl.SSLEngine;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory;

/* renamed from: io.netty.handler.ssl.JdkNpnApplicationProtocolNegotiator */
public final class JdkNpnApplicationProtocolNegotiator extends JdkBaseApplicationProtocolNegotiator {
    private static final SslEngineWrapperFactory NPN_WRAPPER = new SslEngineWrapperFactory() {
        {
            if (!JettyNpnSslEngine.isAvailable()) {
                throw new RuntimeException("NPN unsupported. Is your classpath configured correctly? See https://wiki.eclipse.org/Jetty/Feature/NPN");
            }
        }

        public SSLEngine wrapSslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean z) {
            return new JettyNpnSslEngine(sSLEngine, jdkApplicationProtocolNegotiator, z);
        }
    };

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

    public JdkNpnApplicationProtocolNegotiator(Iterable<String> iterable) {
        this(false, iterable);
    }

    public JdkNpnApplicationProtocolNegotiator(String... strArr) {
        this(false, strArr);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean z, Iterable<String> iterable) {
        this(z, z, iterable);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean z, String... strArr) {
        this(z, z, strArr);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean z, boolean z2, Iterable<String> iterable) {
        this(z ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, z2 ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, iterable);
    }

    public JdkNpnApplicationProtocolNegotiator(boolean z, boolean z2, String... strArr) {
        this(z ? FAIL_SELECTOR_FACTORY : NO_FAIL_SELECTOR_FACTORY, z2 ? FAIL_SELECTION_LISTENER_FACTORY : NO_FAIL_SELECTION_LISTENER_FACTORY, strArr);
    }

    public JdkNpnApplicationProtocolNegotiator(ProtocolSelectorFactory protocolSelectorFactory, ProtocolSelectionListenerFactory protocolSelectionListenerFactory, Iterable<String> iterable) {
        super(NPN_WRAPPER, protocolSelectorFactory, protocolSelectionListenerFactory, iterable);
    }

    public JdkNpnApplicationProtocolNegotiator(ProtocolSelectorFactory protocolSelectorFactory, ProtocolSelectionListenerFactory protocolSelectionListenerFactory, String... strArr) {
        super(NPN_WRAPPER, protocolSelectorFactory, protocolSelectionListenerFactory, strArr);
    }
}
