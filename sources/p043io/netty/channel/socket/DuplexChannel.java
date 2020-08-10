package p043io.netty.channel.socket;

import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelPromise;

/* renamed from: io.netty.channel.socket.DuplexChannel */
public interface DuplexChannel extends Channel {
    boolean isInputShutdown();

    boolean isOutputShutdown();

    boolean isShutdown();

    ChannelFuture shutdown();

    ChannelFuture shutdown(ChannelPromise channelPromise);

    ChannelFuture shutdownInput();

    ChannelFuture shutdownInput(ChannelPromise channelPromise);

    ChannelFuture shutdownOutput();

    ChannelFuture shutdownOutput(ChannelPromise channelPromise);
}
