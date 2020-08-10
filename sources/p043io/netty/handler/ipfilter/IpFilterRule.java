package p043io.netty.handler.ipfilter;

import java.net.InetSocketAddress;

/* renamed from: io.netty.handler.ipfilter.IpFilterRule */
public interface IpFilterRule {
    boolean matches(InetSocketAddress inetSocketAddress);

    IpFilterRuleType ruleType();
}
