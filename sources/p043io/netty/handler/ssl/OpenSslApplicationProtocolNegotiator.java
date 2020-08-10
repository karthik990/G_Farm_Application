package p043io.netty.handler.ssl;

import p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;

/* renamed from: io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator */
public interface OpenSslApplicationProtocolNegotiator extends ApplicationProtocolNegotiator {
    Protocol protocol();

    SelectedListenerFailureBehavior selectedListenerFailureBehavior();

    SelectorFailureBehavior selectorFailureBehavior();
}
