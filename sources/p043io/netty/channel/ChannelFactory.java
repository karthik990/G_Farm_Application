package p043io.netty.channel;

import p043io.netty.channel.Channel;

/* renamed from: io.netty.channel.ChannelFactory */
public interface ChannelFactory<T extends Channel> extends p043io.netty.bootstrap.ChannelFactory<T> {
    T newChannel();
}
