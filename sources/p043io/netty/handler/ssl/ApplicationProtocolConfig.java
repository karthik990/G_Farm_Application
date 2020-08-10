package p043io.netty.handler.ssl;

import java.util.Collections;
import java.util.List;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.ssl.ApplicationProtocolConfig */
public final class ApplicationProtocolConfig {
    public static final ApplicationProtocolConfig DISABLED = new ApplicationProtocolConfig();
    private final Protocol protocol;
    private final SelectedListenerFailureBehavior selectedBehavior;
    private final SelectorFailureBehavior selectorBehavior;
    private final List<String> supportedProtocols;

    /* renamed from: io.netty.handler.ssl.ApplicationProtocolConfig$Protocol */
    public enum Protocol {
        NONE,
        NPN,
        ALPN,
        NPN_AND_ALPN
    }

    /* renamed from: io.netty.handler.ssl.ApplicationProtocolConfig$SelectedListenerFailureBehavior */
    public enum SelectedListenerFailureBehavior {
        ACCEPT,
        FATAL_ALERT,
        CHOOSE_MY_LAST_PROTOCOL
    }

    /* renamed from: io.netty.handler.ssl.ApplicationProtocolConfig$SelectorFailureBehavior */
    public enum SelectorFailureBehavior {
        FATAL_ALERT,
        NO_ADVERTISE,
        CHOOSE_MY_LAST_PROTOCOL
    }

    public ApplicationProtocolConfig(Protocol protocol2, SelectorFailureBehavior selectorFailureBehavior, SelectedListenerFailureBehavior selectedListenerFailureBehavior, Iterable<String> iterable) {
        this(protocol2, selectorFailureBehavior, selectedListenerFailureBehavior, ApplicationProtocolUtil.toList(iterable));
    }

    public ApplicationProtocolConfig(Protocol protocol2, SelectorFailureBehavior selectorFailureBehavior, SelectedListenerFailureBehavior selectedListenerFailureBehavior, String... strArr) {
        this(protocol2, selectorFailureBehavior, selectedListenerFailureBehavior, ApplicationProtocolUtil.toList(strArr));
    }

    private ApplicationProtocolConfig(Protocol protocol2, SelectorFailureBehavior selectorFailureBehavior, SelectedListenerFailureBehavior selectedListenerFailureBehavior, List<String> list) {
        this.supportedProtocols = Collections.unmodifiableList((List) ObjectUtil.checkNotNull(list, "supportedProtocols"));
        this.protocol = (Protocol) ObjectUtil.checkNotNull(protocol2, "protocol");
        this.selectorBehavior = (SelectorFailureBehavior) ObjectUtil.checkNotNull(selectorFailureBehavior, "selectorBehavior");
        this.selectedBehavior = (SelectedListenerFailureBehavior) ObjectUtil.checkNotNull(selectedListenerFailureBehavior, "selectedBehavior");
        if (protocol2 == Protocol.NONE) {
            StringBuilder sb = new StringBuilder();
            sb.append("protocol (");
            sb.append(Protocol.NONE);
            sb.append(") must not be ");
            sb.append(Protocol.NONE);
            sb.append('.');
            throw new IllegalArgumentException(sb.toString());
        } else if (list.isEmpty()) {
            throw new IllegalArgumentException("supportedProtocols must be not empty");
        }
    }

    private ApplicationProtocolConfig() {
        this.supportedProtocols = Collections.emptyList();
        this.protocol = Protocol.NONE;
        this.selectorBehavior = SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
        this.selectedBehavior = SelectedListenerFailureBehavior.ACCEPT;
    }

    public List<String> supportedProtocols() {
        return this.supportedProtocols;
    }

    public Protocol protocol() {
        return this.protocol;
    }

    public SelectorFailureBehavior selectorFailureBehavior() {
        return this.selectorBehavior;
    }

    public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return this.selectedBehavior;
    }
}
