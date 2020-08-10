package p043io.netty.handler.ssl;

import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLEngine;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory;

/* renamed from: io.netty.handler.ssl.JdkDefaultApplicationProtocolNegotiator */
final class JdkDefaultApplicationProtocolNegotiator implements JdkApplicationProtocolNegotiator {
    private static final SslEngineWrapperFactory DEFAULT_SSL_ENGINE_WRAPPER_FACTORY = new SslEngineWrapperFactory() {
        public SSLEngine wrapSslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean z) {
            return sSLEngine;
        }
    };
    public static final JdkDefaultApplicationProtocolNegotiator INSTANCE = new JdkDefaultApplicationProtocolNegotiator();

    private JdkDefaultApplicationProtocolNegotiator() {
    }

    public SslEngineWrapperFactory wrapperFactory() {
        return DEFAULT_SSL_ENGINE_WRAPPER_FACTORY;
    }

    public ProtocolSelectorFactory protocolSelectorFactory() {
        throw new UnsupportedOperationException("Application protocol negotiation unsupported");
    }

    public ProtocolSelectionListenerFactory protocolListenerFactory() {
        throw new UnsupportedOperationException("Application protocol negotiation unsupported");
    }

    public List<String> protocols() {
        return Collections.emptyList();
    }
}
