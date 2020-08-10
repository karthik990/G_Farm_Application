package p043io.netty.handler.ssl;

import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;

/* renamed from: io.netty.handler.ssl.JdkApplicationProtocolNegotiator */
public interface JdkApplicationProtocolNegotiator extends ApplicationProtocolNegotiator {

    /* renamed from: io.netty.handler.ssl.JdkApplicationProtocolNegotiator$ProtocolSelectionListener */
    public interface ProtocolSelectionListener {
        void selected(String str) throws Exception;

        void unsupported();
    }

    /* renamed from: io.netty.handler.ssl.JdkApplicationProtocolNegotiator$ProtocolSelectionListenerFactory */
    public interface ProtocolSelectionListenerFactory {
        ProtocolSelectionListener newListener(SSLEngine sSLEngine, List<String> list);
    }

    /* renamed from: io.netty.handler.ssl.JdkApplicationProtocolNegotiator$ProtocolSelector */
    public interface ProtocolSelector {
        String select(List<String> list) throws Exception;

        void unsupported();
    }

    /* renamed from: io.netty.handler.ssl.JdkApplicationProtocolNegotiator$ProtocolSelectorFactory */
    public interface ProtocolSelectorFactory {
        ProtocolSelector newSelector(SSLEngine sSLEngine, Set<String> set);
    }

    /* renamed from: io.netty.handler.ssl.JdkApplicationProtocolNegotiator$SslEngineWrapperFactory */
    public interface SslEngineWrapperFactory {
        SSLEngine wrapSslEngine(SSLEngine sSLEngine, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean z);
    }

    ProtocolSelectionListenerFactory protocolListenerFactory();

    ProtocolSelectorFactory protocolSelectorFactory();

    SslEngineWrapperFactory wrapperFactory();
}
