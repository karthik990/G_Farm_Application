package p043io.netty.channel.pool;

import p043io.netty.channel.Channel;

/* renamed from: io.netty.channel.pool.ChannelPoolHandler */
public interface ChannelPoolHandler {
    void channelAcquired(Channel channel) throws Exception;

    void channelCreated(Channel channel) throws Exception;

    void channelReleased(Channel channel) throws Exception;
}
