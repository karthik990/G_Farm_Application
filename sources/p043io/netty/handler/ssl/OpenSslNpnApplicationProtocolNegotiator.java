package p043io.netty.handler.ssl;

import java.util.List;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.Protocol;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectedListenerFailureBehavior;
import p043io.netty.handler.ssl.ApplicationProtocolConfig.SelectorFailureBehavior;
import p043io.netty.util.internal.ObjectUtil;

@Deprecated
/* renamed from: io.netty.handler.ssl.OpenSslNpnApplicationProtocolNegotiator */
public final class OpenSslNpnApplicationProtocolNegotiator implements OpenSslApplicationProtocolNegotiator {
    private final List<String> protocols;

    public OpenSslNpnApplicationProtocolNegotiator(Iterable<String> iterable) {
        this.protocols = (List) ObjectUtil.checkNotNull(ApplicationProtocolUtil.toList(iterable), "protocols");
    }

    public OpenSslNpnApplicationProtocolNegotiator(String... strArr) {
        this.protocols = (List) ObjectUtil.checkNotNull(ApplicationProtocolUtil.toList(strArr), "protocols");
    }

    public Protocol protocol() {
        return Protocol.NPN;
    }

    public List<String> protocols() {
        return this.protocols;
    }

    public SelectorFailureBehavior selectorFailureBehavior() {
        return SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
    }

    public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return SelectedListenerFailureBehavior.ACCEPT;
    }
}
