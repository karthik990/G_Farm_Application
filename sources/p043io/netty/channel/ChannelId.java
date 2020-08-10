package p043io.netty.channel;

import java.io.Serializable;

/* renamed from: io.netty.channel.ChannelId */
public interface ChannelId extends Serializable, Comparable<ChannelId> {
    String asLongText();

    String asShortText();
}
