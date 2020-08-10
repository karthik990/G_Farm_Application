package p043io.netty.channel;

import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufHolder;
import p043io.netty.channel.MessageSizeEstimator.Handle;

/* renamed from: io.netty.channel.DefaultMessageSizeEstimator */
public final class DefaultMessageSizeEstimator implements MessageSizeEstimator {
    public static final MessageSizeEstimator DEFAULT = new DefaultMessageSizeEstimator(8);
    private final Handle handle;

    /* renamed from: io.netty.channel.DefaultMessageSizeEstimator$HandleImpl */
    private static final class HandleImpl implements Handle {
        private final int unknownSize;

        private HandleImpl(int i) {
            this.unknownSize = i;
        }

        public int size(Object obj) {
            if (obj instanceof ByteBuf) {
                return ((ByteBuf) obj).readableBytes();
            }
            if (obj instanceof ByteBufHolder) {
                return ((ByteBufHolder) obj).content().readableBytes();
            }
            if (obj instanceof FileRegion) {
                return 0;
            }
            return this.unknownSize;
        }
    }

    public DefaultMessageSizeEstimator(int i) {
        if (i >= 0) {
            this.handle = new HandleImpl(i);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unknownSize: ");
        sb.append(i);
        sb.append(" (expected: >= 0)");
        throw new IllegalArgumentException(sb.toString());
    }

    public Handle newHandle() {
        return this.handle;
    }
}
