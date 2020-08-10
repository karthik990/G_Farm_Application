package p043io.netty.channel.local;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentMap;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelException;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.channel.local.LocalChannelRegistry */
final class LocalChannelRegistry {
    private static final ConcurrentMap<LocalAddress, Channel> boundChannels = PlatformDependent.newConcurrentHashMap();

    static LocalAddress register(Channel channel, LocalAddress localAddress, SocketAddress socketAddress) {
        if (localAddress != null) {
            throw new ChannelException("already bound");
        } else if (socketAddress instanceof LocalAddress) {
            LocalAddress localAddress2 = (LocalAddress) socketAddress;
            if (LocalAddress.ANY.equals(localAddress2)) {
                localAddress2 = new LocalAddress(channel);
            }
            Channel channel2 = (Channel) boundChannels.putIfAbsent(localAddress2, channel);
            if (channel2 == null) {
                return localAddress2;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("address already in use by: ");
            sb.append(channel2);
            throw new ChannelException(sb.toString());
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("unsupported address type: ");
            sb2.append(StringUtil.simpleClassName((Object) socketAddress));
            throw new ChannelException(sb2.toString());
        }
    }

    static Channel get(SocketAddress socketAddress) {
        return (Channel) boundChannels.get(socketAddress);
    }

    static void unregister(LocalAddress localAddress) {
        boundChannels.remove(localAddress);
    }

    private LocalChannelRegistry() {
    }
}
