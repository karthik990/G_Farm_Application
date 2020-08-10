package p043io.netty.handler.ssl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLHandshakeException;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListener;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelector;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.SslEngineWrapperFactory;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator */
class JdkBaseApplicationProtocolNegotiator implements JdkApplicationProtocolNegotiator {
    static final ProtocolSelectionListenerFactory FAIL_SELECTION_LISTENER_FACTORY = new ProtocolSelectionListenerFactory() {
        public ProtocolSelectionListener newListener(SSLEngine sSLEngine, List<String> list) {
            return new FailProtocolSelectionListener((JdkSslEngine) sSLEngine, list);
        }
    };
    static final ProtocolSelectorFactory FAIL_SELECTOR_FACTORY = new ProtocolSelectorFactory() {
        public ProtocolSelector newSelector(SSLEngine sSLEngine, Set<String> set) {
            return new FailProtocolSelector((JdkSslEngine) sSLEngine, set);
        }
    };
    static final ProtocolSelectionListenerFactory NO_FAIL_SELECTION_LISTENER_FACTORY = new ProtocolSelectionListenerFactory() {
        public ProtocolSelectionListener newListener(SSLEngine sSLEngine, List<String> list) {
            return new NoFailProtocolSelectionListener((JdkSslEngine) sSLEngine, list);
        }
    };
    static final ProtocolSelectorFactory NO_FAIL_SELECTOR_FACTORY = new ProtocolSelectorFactory() {
        public ProtocolSelector newSelector(SSLEngine sSLEngine, Set<String> set) {
            return new NoFailProtocolSelector((JdkSslEngine) sSLEngine, set);
        }
    };
    private final ProtocolSelectionListenerFactory listenerFactory;
    private final List<String> protocols;
    private final ProtocolSelectorFactory selectorFactory;
    private final SslEngineWrapperFactory wrapperFactory;

    /* renamed from: io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator$FailProtocolSelectionListener */
    private static final class FailProtocolSelectionListener extends NoFailProtocolSelectionListener {
        FailProtocolSelectionListener(JdkSslEngine jdkSslEngine, List<String> list) {
            super(jdkSslEngine, list);
        }

        /* access modifiers changed from: protected */
        public void noSelectedMatchFound(String str) throws Exception {
            throw new SSLHandshakeException("No compatible protocols found");
        }
    }

    /* renamed from: io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator$FailProtocolSelector */
    private static final class FailProtocolSelector extends NoFailProtocolSelector {
        FailProtocolSelector(JdkSslEngine jdkSslEngine, Set<String> set) {
            super(jdkSslEngine, set);
        }

        public String noSelectMatchFound() throws Exception {
            throw new SSLHandshakeException("Selected protocol is not supported");
        }
    }

    /* renamed from: io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator$NoFailProtocolSelectionListener */
    private static class NoFailProtocolSelectionListener implements ProtocolSelectionListener {
        private final JdkSslEngine engineWrapper;
        private final List<String> supportedProtocols;

        /* access modifiers changed from: protected */
        public void noSelectedMatchFound(String str) throws Exception {
        }

        NoFailProtocolSelectionListener(JdkSslEngine jdkSslEngine, List<String> list) {
            this.engineWrapper = jdkSslEngine;
            this.supportedProtocols = list;
        }

        public void unsupported() {
            this.engineWrapper.getSession().setApplicationProtocol(null);
        }

        public void selected(String str) throws Exception {
            if (this.supportedProtocols.contains(str)) {
                this.engineWrapper.getSession().setApplicationProtocol(str);
            } else {
                noSelectedMatchFound(str);
            }
        }
    }

    /* renamed from: io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator$NoFailProtocolSelector */
    static class NoFailProtocolSelector implements ProtocolSelector {
        private final JdkSslEngine engineWrapper;
        private final Set<String> supportedProtocols;

        NoFailProtocolSelector(JdkSslEngine jdkSslEngine, Set<String> set) {
            this.engineWrapper = jdkSslEngine;
            this.supportedProtocols = set;
        }

        public void unsupported() {
            this.engineWrapper.getSession().setApplicationProtocol(null);
        }

        public String select(List<String> list) throws Exception {
            for (String str : this.supportedProtocols) {
                if (list.contains(str)) {
                    this.engineWrapper.getSession().setApplicationProtocol(str);
                    return str;
                }
            }
            return noSelectMatchFound();
        }

        public String noSelectMatchFound() throws Exception {
            this.engineWrapper.getSession().setApplicationProtocol(null);
            return null;
        }
    }

    JdkBaseApplicationProtocolNegotiator(SslEngineWrapperFactory sslEngineWrapperFactory, ProtocolSelectorFactory protocolSelectorFactory, ProtocolSelectionListenerFactory protocolSelectionListenerFactory, Iterable<String> iterable) {
        this(sslEngineWrapperFactory, protocolSelectorFactory, protocolSelectionListenerFactory, ApplicationProtocolUtil.toList(iterable));
    }

    JdkBaseApplicationProtocolNegotiator(SslEngineWrapperFactory sslEngineWrapperFactory, ProtocolSelectorFactory protocolSelectorFactory, ProtocolSelectionListenerFactory protocolSelectionListenerFactory, String... strArr) {
        this(sslEngineWrapperFactory, protocolSelectorFactory, protocolSelectionListenerFactory, ApplicationProtocolUtil.toList(strArr));
    }

    private JdkBaseApplicationProtocolNegotiator(SslEngineWrapperFactory sslEngineWrapperFactory, ProtocolSelectorFactory protocolSelectorFactory, ProtocolSelectionListenerFactory protocolSelectionListenerFactory, List<String> list) {
        this.wrapperFactory = (SslEngineWrapperFactory) ObjectUtil.checkNotNull(sslEngineWrapperFactory, "wrapperFactory");
        this.selectorFactory = (ProtocolSelectorFactory) ObjectUtil.checkNotNull(protocolSelectorFactory, "selectorFactory");
        this.listenerFactory = (ProtocolSelectionListenerFactory) ObjectUtil.checkNotNull(protocolSelectionListenerFactory, "listenerFactory");
        this.protocols = Collections.unmodifiableList((List) ObjectUtil.checkNotNull(list, "protocols"));
    }

    public List<String> protocols() {
        return this.protocols;
    }

    public ProtocolSelectorFactory protocolSelectorFactory() {
        return this.selectorFactory;
    }

    public ProtocolSelectionListenerFactory protocolListenerFactory() {
        return this.listenerFactory;
    }

    public SslEngineWrapperFactory wrapperFactory() {
        return this.wrapperFactory;
    }
}
