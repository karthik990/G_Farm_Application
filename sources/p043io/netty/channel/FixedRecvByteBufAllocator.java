package p043io.netty.channel;

import p043io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle;
import p043io.netty.channel.RecvByteBufAllocator.Handle;

/* renamed from: io.netty.channel.FixedRecvByteBufAllocator */
public class FixedRecvByteBufAllocator extends DefaultMaxMessagesRecvByteBufAllocator {
    private final int bufferSize;

    /* renamed from: io.netty.channel.FixedRecvByteBufAllocator$HandleImpl */
    private final class HandleImpl extends MaxMessageHandle {
        private final int bufferSize;

        public HandleImpl(int i) {
            super();
            this.bufferSize = i;
        }

        public int guess() {
            return this.bufferSize;
        }
    }

    public FixedRecvByteBufAllocator(int i) {
        if (i > 0) {
            this.bufferSize = i;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("bufferSize must greater than 0: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public Handle newHandle() {
        return new HandleImpl(this.bufferSize);
    }
}
