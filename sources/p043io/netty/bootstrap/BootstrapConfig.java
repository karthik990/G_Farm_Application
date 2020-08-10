package p043io.netty.bootstrap;

import java.net.SocketAddress;
import p043io.netty.channel.Channel;
import p043io.netty.resolver.AddressResolverGroup;

/* renamed from: io.netty.bootstrap.BootstrapConfig */
public final class BootstrapConfig extends AbstractBootstrapConfig<Bootstrap, Channel> {
    BootstrapConfig(Bootstrap bootstrap) {
        super(bootstrap);
    }

    public SocketAddress remoteAddress() {
        return ((Bootstrap) this.bootstrap).remoteAddress();
    }

    public AddressResolverGroup<?> resolver() {
        return ((Bootstrap) this.bootstrap).resolver();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.setLength(sb.length() - 1);
        sb.append(", resolver: ");
        sb.append(resolver());
        SocketAddress remoteAddress = remoteAddress();
        if (remoteAddress != null) {
            sb.append(", remoteAddress: ");
            sb.append(remoteAddress);
        }
        sb.append(')');
        return sb.toString();
    }
}
