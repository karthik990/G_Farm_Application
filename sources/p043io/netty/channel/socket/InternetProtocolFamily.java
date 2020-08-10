package p043io.netty.channel.socket;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import p043io.netty.util.NetUtil;

/* renamed from: io.netty.channel.socket.InternetProtocolFamily */
public enum InternetProtocolFamily {
    IPv4(Inet4Address.class, 1, NetUtil.LOCALHOST4),
    IPv6(Inet6Address.class, 2, NetUtil.LOCALHOST6);
    
    private final int addressNumber;
    private final Class<? extends InetAddress> addressType;
    private final InetAddress localHost;

    private InternetProtocolFamily(Class<? extends InetAddress> cls, int i, InetAddress inetAddress) {
        this.addressType = cls;
        this.addressNumber = i;
        this.localHost = inetAddress;
    }

    public Class<? extends InetAddress> addressType() {
        return this.addressType;
    }

    public int addressNumber() {
        return this.addressNumber;
    }

    public InetAddress localhost() {
        return this.localHost;
    }

    /* renamed from: of */
    public static InternetProtocolFamily m4003of(InetAddress inetAddress) {
        if (inetAddress instanceof Inet4Address) {
            return IPv4;
        }
        if (inetAddress instanceof Inet6Address) {
            return IPv6;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("address ");
        sb.append(inetAddress);
        sb.append(" not supported");
        throw new IllegalArgumentException(sb.toString());
    }
}
