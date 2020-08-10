package p043io.netty.channel;

import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.RecvByteBufAllocator.ExtendedHandle;
import p043io.netty.util.UncheckedBooleanSupplier;

/* renamed from: io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator */
public abstract class DefaultMaxMessagesRecvByteBufAllocator implements MaxMessagesRecvByteBufAllocator {
    private volatile int maxMessagesPerRead;

    /* renamed from: io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator$MaxMessageHandle */
    public abstract class MaxMessageHandle implements ExtendedHandle {
        /* access modifiers changed from: private */
        public int attemptedBytesRead;
        private ChannelConfig config;
        private final UncheckedBooleanSupplier defaultMaybeMoreSupplier = new UncheckedBooleanSupplier() {
            public boolean get() {
                return MaxMessageHandle.this.attemptedBytesRead == MaxMessageHandle.this.lastBytesRead;
            }
        };
        /* access modifiers changed from: private */
        public int lastBytesRead;
        private int maxMessagePerRead;
        private int totalBytesRead;
        private int totalMessages;

        public void readComplete() {
        }

        public MaxMessageHandle() {
        }

        public void reset(ChannelConfig channelConfig) {
            this.config = channelConfig;
            this.maxMessagePerRead = DefaultMaxMessagesRecvByteBufAllocator.this.maxMessagesPerRead();
            this.totalBytesRead = 0;
            this.totalMessages = 0;
        }

        public ByteBuf allocate(ByteBufAllocator byteBufAllocator) {
            return byteBufAllocator.ioBuffer(guess());
        }

        public final void incMessagesRead(int i) {
            this.totalMessages += i;
        }

        public final void lastBytesRead(int i) {
            this.lastBytesRead = i;
            if (i > 0) {
                this.totalBytesRead += i;
            }
        }

        public final int lastBytesRead() {
            return this.lastBytesRead;
        }

        public boolean continueReading() {
            return continueReading(this.defaultMaybeMoreSupplier);
        }

        public boolean continueReading(UncheckedBooleanSupplier uncheckedBooleanSupplier) {
            return this.config.isAutoRead() && uncheckedBooleanSupplier.get() && this.totalMessages < this.maxMessagePerRead && this.totalBytesRead > 0;
        }

        public int attemptedBytesRead() {
            return this.attemptedBytesRead;
        }

        public void attemptedBytesRead(int i) {
            this.attemptedBytesRead = i;
        }

        /* access modifiers changed from: protected */
        public final int totalBytesRead() {
            int i = this.totalBytesRead;
            if (i < 0) {
                return Integer.MAX_VALUE;
            }
            return i;
        }
    }

    public DefaultMaxMessagesRecvByteBufAllocator() {
        this(1);
    }

    public DefaultMaxMessagesRecvByteBufAllocator(int i) {
        maxMessagesPerRead(i);
    }

    public int maxMessagesPerRead() {
        return this.maxMessagesPerRead;
    }

    public MaxMessagesRecvByteBufAllocator maxMessagesPerRead(int i) {
        if (i > 0) {
            this.maxMessagesPerRead = i;
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("maxMessagesPerRead: ");
        sb.append(i);
        sb.append(" (expected: > 0)");
        throw new IllegalArgumentException(sb.toString());
    }
}
