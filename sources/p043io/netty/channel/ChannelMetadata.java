package p043io.netty.channel;

/* renamed from: io.netty.channel.ChannelMetadata */
public final class ChannelMetadata {
    private final int defaultMaxMessagesPerRead;
    private final boolean hasDisconnect;

    public ChannelMetadata(boolean z) {
        this(z, 1);
    }

    public ChannelMetadata(boolean z, int i) {
        if (i > 0) {
            this.hasDisconnect = z;
            this.defaultMaxMessagesPerRead = i;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("defaultMaxMessagesPerRead: ");
        sb.append(i);
        sb.append(" (expected > 0)");
        throw new IllegalArgumentException(sb.toString());
    }

    public boolean hasDisconnect() {
        return this.hasDisconnect;
    }

    public int defaultMaxMessagesPerRead() {
        return this.defaultMaxMessagesPerRead;
    }
}
