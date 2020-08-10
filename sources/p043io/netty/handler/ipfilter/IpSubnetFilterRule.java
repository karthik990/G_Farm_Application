package p043io.netty.handler.ipfilter;

import com.google.common.base.Ascii;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import p043io.netty.util.internal.SocketUtils;

/* renamed from: io.netty.handler.ipfilter.IpSubnetFilterRule */
public final class IpSubnetFilterRule implements IpFilterRule {
    private final IpFilterRule filterRule;

    /* renamed from: io.netty.handler.ipfilter.IpSubnetFilterRule$Ip4SubnetFilterRule */
    private static final class Ip4SubnetFilterRule implements IpFilterRule {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final int networkAddress;
        private final IpFilterRuleType ruleType;
        private final int subnetMask;

        private static int prefixToSubnetMask(int i) {
            return (int) (-1 & (-1 << (32 - i)));
        }

        static {
            Class<IpSubnetFilterRule> cls = IpSubnetFilterRule.class;
        }

        private Ip4SubnetFilterRule(Inet4Address inet4Address, int i, IpFilterRuleType ipFilterRuleType) {
            if (i < 0 || i > 32) {
                throw new IllegalArgumentException(String.format("IPv4 requires the subnet prefix to be in range of [0,32]. The prefix was: %d", new Object[]{Integer.valueOf(i)}));
            }
            this.subnetMask = prefixToSubnetMask(i);
            this.networkAddress = ipToInt(inet4Address) & this.subnetMask;
            this.ruleType = ipFilterRuleType;
        }

        public boolean matches(InetSocketAddress inetSocketAddress) {
            InetAddress address = inetSocketAddress.getAddress();
            if (!(address instanceof Inet4Address) || (ipToInt((Inet4Address) address) & this.subnetMask) != this.networkAddress) {
                return false;
            }
            return true;
        }

        public IpFilterRuleType ruleType() {
            return this.ruleType;
        }

        private static int ipToInt(Inet4Address inet4Address) {
            byte[] address = inet4Address.getAddress();
            return (address[3] & 255) | ((address[0] & 255) << Ascii.CAN) | ((address[1] & 255) << Ascii.DLE) | ((address[2] & 255) << 8);
        }
    }

    /* renamed from: io.netty.handler.ipfilter.IpSubnetFilterRule$Ip6SubnetFilterRule */
    private static final class Ip6SubnetFilterRule implements IpFilterRule {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final BigInteger MINUS_ONE = BigInteger.valueOf(-1);
        private final BigInteger networkAddress;
        private final IpFilterRuleType ruleType;
        private final BigInteger subnetMask;

        static {
            Class<IpSubnetFilterRule> cls = IpSubnetFilterRule.class;
        }

        private Ip6SubnetFilterRule(Inet6Address inet6Address, int i, IpFilterRuleType ipFilterRuleType) {
            if (i < 0 || i > 128) {
                throw new IllegalArgumentException(String.format("IPv6 requires the subnet prefix to be in range of [0,128]. The prefix was: %d", new Object[]{Integer.valueOf(i)}));
            }
            this.subnetMask = prefixToSubnetMask(i);
            this.networkAddress = ipToInt(inet6Address).and(this.subnetMask);
            this.ruleType = ipFilterRuleType;
        }

        public boolean matches(InetSocketAddress inetSocketAddress) {
            InetAddress address = inetSocketAddress.getAddress();
            if (address instanceof Inet6Address) {
                return ipToInt((Inet6Address) address).and(this.subnetMask).equals(this.networkAddress);
            }
            return false;
        }

        public IpFilterRuleType ruleType() {
            return this.ruleType;
        }

        private static BigInteger ipToInt(Inet6Address inet6Address) {
            return new BigInteger(inet6Address.getAddress());
        }

        private static BigInteger prefixToSubnetMask(int i) {
            return MINUS_ONE.shiftLeft(128 - i);
        }
    }

    public IpSubnetFilterRule(String str, int i, IpFilterRuleType ipFilterRuleType) {
        try {
            this.filterRule = selectFilterRule(SocketUtils.addressByName(str), i, ipFilterRuleType);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("ipAddress", e);
        }
    }

    public IpSubnetFilterRule(InetAddress inetAddress, int i, IpFilterRuleType ipFilterRuleType) {
        this.filterRule = selectFilterRule(inetAddress, i, ipFilterRuleType);
    }

    private static IpFilterRule selectFilterRule(InetAddress inetAddress, int i, IpFilterRuleType ipFilterRuleType) {
        if (inetAddress == null) {
            throw new NullPointerException("ipAddress");
        } else if (ipFilterRuleType == null) {
            throw new NullPointerException("ruleType");
        } else if (inetAddress instanceof Inet4Address) {
            return new Ip4SubnetFilterRule((Inet4Address) inetAddress, i, ipFilterRuleType);
        } else {
            if (inetAddress instanceof Inet6Address) {
                return new Ip6SubnetFilterRule((Inet6Address) inetAddress, i, ipFilterRuleType);
            }
            throw new IllegalArgumentException("Only IPv4 and IPv6 addresses are supported");
        }
    }

    public boolean matches(InetSocketAddress inetSocketAddress) {
        return this.filterRule.matches(inetSocketAddress);
    }

    public IpFilterRuleType ruleType() {
        return this.filterRule.ruleType();
    }
}
