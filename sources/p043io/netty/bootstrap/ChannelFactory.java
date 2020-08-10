package p043io.netty.bootstrap;

import p043io.netty.channel.Channel;

@Deprecated
/* renamed from: io.netty.bootstrap.ChannelFactory */
public interface ChannelFactory<T extends Channel> {
    T newChannel();
}
