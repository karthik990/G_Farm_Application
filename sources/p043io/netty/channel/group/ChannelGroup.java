package p043io.netty.channel.group;

import java.util.Set;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelId;

/* renamed from: io.netty.channel.group.ChannelGroup */
public interface ChannelGroup extends Set<Channel>, Comparable<ChannelGroup> {
    ChannelGroupFuture close();

    ChannelGroupFuture close(ChannelMatcher channelMatcher);

    @Deprecated
    ChannelGroupFuture deregister();

    @Deprecated
    ChannelGroupFuture deregister(ChannelMatcher channelMatcher);

    ChannelGroupFuture disconnect();

    ChannelGroupFuture disconnect(ChannelMatcher channelMatcher);

    Channel find(ChannelId channelId);

    ChannelGroup flush();

    ChannelGroup flush(ChannelMatcher channelMatcher);

    @Deprecated
    ChannelGroupFuture flushAndWrite(Object obj);

    @Deprecated
    ChannelGroupFuture flushAndWrite(Object obj, ChannelMatcher channelMatcher);

    String name();

    ChannelGroupFuture newCloseFuture();

    ChannelGroupFuture newCloseFuture(ChannelMatcher channelMatcher);

    ChannelGroupFuture write(Object obj);

    ChannelGroupFuture write(Object obj, ChannelMatcher channelMatcher);

    ChannelGroupFuture write(Object obj, ChannelMatcher channelMatcher, boolean z);

    ChannelGroupFuture writeAndFlush(Object obj);

    ChannelGroupFuture writeAndFlush(Object obj, ChannelMatcher channelMatcher);

    ChannelGroupFuture writeAndFlush(Object obj, ChannelMatcher channelMatcher, boolean z);
}
