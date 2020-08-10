package p043io.netty.handler.ipfilter;

import java.net.InetSocketAddress;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;

@Sharable
/* renamed from: io.netty.handler.ipfilter.RuleBasedIpFilter */
public class RuleBasedIpFilter extends AbstractRemoteAddressFilter<InetSocketAddress> {
    private final IpFilterRule[] rules;

    public RuleBasedIpFilter(IpFilterRule... ipFilterRuleArr) {
        if (ipFilterRuleArr != null) {
            this.rules = ipFilterRuleArr;
            return;
        }
        throw new NullPointerException("rules");
    }

    /* access modifiers changed from: protected */
    public boolean accept(ChannelHandlerContext channelHandlerContext, InetSocketAddress inetSocketAddress) throws Exception {
        IpFilterRule[] ipFilterRuleArr = this.rules;
        int length = ipFilterRuleArr.length;
        boolean z = false;
        int i = 0;
        while (i < length) {
            IpFilterRule ipFilterRule = ipFilterRuleArr[i];
            if (ipFilterRule == null) {
                break;
            } else if (ipFilterRule.matches(inetSocketAddress)) {
                if (ipFilterRule.ruleType() == IpFilterRuleType.ACCEPT) {
                    z = true;
                }
                return z;
            } else {
                i++;
            }
        }
        return true;
    }
}
