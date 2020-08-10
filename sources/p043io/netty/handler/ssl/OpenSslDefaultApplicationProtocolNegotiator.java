package p043io.netty.handler.ssl;

import java.util.List;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.ssl.OpenSslDefaultApplicationProtocolNegotiator */
public final class OpenSslDefaultApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
    private final ApplicationProtocolConfig config;

    public OpenSslDefaultApplicationProtocolNegotiator(ApplicationProtocolConfig applicationProtocolConfig) {
        this.config = (ApplicationProtocolConfig) ObjectUtil.checkNotNull(applicationProtocolConfig, "config");
    }

    public List<String> protocols() {
        return this.config.supportedProtocols();
    }

    public Protocol protocol() {
        return this.config.protocol();
    }

    public SelectorFailureBehavior selectorFailureBehavior() {
        return this.config.selectorFailureBehavior();
    }

    public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return this.config.selectedListenerFailureBehavior();
    }
}
