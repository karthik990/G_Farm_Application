package p043io.netty.channel.embedded;

import p043io.netty.channel.ChannelId;

/* renamed from: io.netty.channel.embedded.EmbeddedChannelId */
final class EmbeddedChannelId implements ChannelId {
    static final ChannelId INSTANCE = new EmbeddedChannelId();
    private static final long serialVersionUID = -251711922203466130L;

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "embedded";
    }

    private EmbeddedChannelId() {
    }

    public String asShortText() {
        return toString();
    }

    public String asLongText() {
        return toString();
    }

    public int compareTo(ChannelId channelId) {
        if (channelId instanceof EmbeddedChannelId) {
            return 0;
        }
        return asLongText().compareTo(channelId.asLongText());
    }

    public boolean equals(Object obj) {
        return obj instanceof EmbeddedChannelId;
    }
}
