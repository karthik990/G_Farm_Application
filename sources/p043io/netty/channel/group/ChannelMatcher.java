package p043io.netty.channel.group;

import p043io.netty.channel.Channel;

/* renamed from: io.netty.channel.group.ChannelMatcher */
public interface ChannelMatcher {
    boolean matches(Channel channel);
}
