package p043io.netty.channel;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.RecvByteBufAllocator.ExtendedHandle;
import p043io.netty.channel.RecvByteBufAllocator.Handle;
import p043io.netty.util.UncheckedBooleanSupplier;

/* renamed from: io.netty.channel.DefaultMaxBytesRecvByteBufAllocator */
public class DefaultMaxBytesRecvByteBufAllocator implements MaxBytesRecvByteBufAllocator {
    private volatile int maxBytesPerIndividualRead;
    private volatile int maxBytesPerRead;

    /* renamed from: io.netty.channel.DefaultMaxBytesRecvByteBufAllocator$HandleImpl */
    private final class HandleImpl implements ExtendedHandle {
        /* access modifiers changed from: private */
        public int attemptBytesRead;
        private int bytesToRead;
        private final UncheckedBooleanSupplier defaultMaybeMoreSupplier;
        private int individualReadMax;
        /* access modifiers changed from: private */
        public int lastBytesRead;

        public void incMessagesRead(int i) {
        }

        public void readComplete() {
        }

        private HandleImpl() {
            this.defaultMaybeMoreSupplier = new UncheckedBooleanSupplier() {
                public boolean get() {
                    return HandleImpl.this.attemptBytesRead == HandleImpl.this.lastBytesRead;
                }
            };
        }

        public ByteBuf allocate(ByteBufAllocator byteBufAllocator) {
            return byteBufAllocator.ioBuffer(guess());
        }

        public int guess() {
            return Math.min(this.individualReadMax, this.bytesToRead);
        }

        public void reset(ChannelConfig channelConfig) {
            this.bytesToRead = DefaultMaxBytesRecvByteBufAllocator.this.maxBytesPerRead();
            this.individualReadMax = DefaultMaxBytesRecvByteBufAllocator.this.maxBytesPerIndividualRead();
        }

        public void lastBytesRead(int i) {
            this.lastBytesRead = i;
            this.bytesToRead -= i;
        }

        public int lastBytesRead() {
            return this.lastBytesRead;
        }

        public boolean continueReading() {
            return continueReading(this.defaultMaybeMoreSupplier);
        }

        public boolean continueReading(UncheckedBooleanSupplier uncheckedBooleanSupplier) {
            return this.bytesToRead > 0 && uncheckedBooleanSupplier.get();
        }

        public void attemptedBytesRead(int i) {
            this.attemptBytesRead = i;
        }

        public int attemptedBytesRead() {
            return this.attemptBytesRead;
        }
    }

    public DefaultMaxBytesRecvByteBufAllocator() {
        this(65536, 65536);
    }

    public DefaultMaxBytesRecvByteBufAllocator(int i, int i2) {
        checkMaxBytesPerReadPair(i, i2);
        this.maxBytesPerRead = i;
        this.maxBytesPerIndividualRead = i2;
    }

    public Handle newHandle() {
        return new HandleImpl();
    }

    public int maxBytesPerRead() {
        return this.maxBytesPerRead;
    }

    public DefaultMaxBytesRecvByteBufAllocator maxBytesPerRead(int i) {
        if (i > 0) {
            synchronized (this) {
                int maxBytesPerIndividualRead2 = maxBytesPerIndividualRead();
                if (i >= maxBytesPerIndividualRead2) {
                    this.maxBytesPerRead = i;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("maxBytesPerRead cannot be less than maxBytesPerIndividualRead (");
                    sb.append(maxBytesPerIndividualRead2);
                    sb.append("): ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
                }
            }
            return this;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("maxBytesPerRead: ");
        sb2.append(i);
        sb2.append(" (expected: > 0)");
        throw new IllegalArgumentException(sb2.toString());
    }

    public int maxBytesPerIndividualRead() {
        return this.maxBytesPerIndividualRead;
    }

    public DefaultMaxBytesRecvByteBufAllocator maxBytesPerIndividualRead(int i) {
        if (i > 0) {
            synchronized (this) {
                int maxBytesPerRead2 = maxBytesPerRead();
                if (i <= maxBytesPerRead2) {
                    this.maxBytesPerIndividualRead = i;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("maxBytesPerIndividualRead cannot be greater than maxBytesPerRead (");
                    sb.append(maxBytesPerRead2);
                    sb.append("): ");
                    sb.append(i);
                    throw new IllegalArgumentException(sb.toString());
                }
            }
            return this;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("maxBytesPerIndividualRead: ");
        sb2.append(i);
        sb2.append(" (expected: > 0)");
        throw new IllegalArgumentException(sb2.toString());
    }

    public synchronized Entry<Integer, Integer> maxBytesPerReadPair() {
        return new SimpleEntry(Integer.valueOf(this.maxBytesPerRead), Integer.valueOf(this.maxBytesPerIndividualRead));
    }

    private static void checkMaxBytesPerReadPair(int i, int i2) {
        String str = " (expected: > 0)";
        if (i <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("maxBytesPerRead: ");
            sb.append(i);
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 <= 0) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("maxBytesPerIndividualRead: ");
            sb2.append(i2);
            sb2.append(str);
            throw new IllegalArgumentException(sb2.toString());
        } else if (i < i2) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("maxBytesPerRead cannot be less than maxBytesPerIndividualRead (");
            sb3.append(i2);
            sb3.append("): ");
            sb3.append(i);
            throw new IllegalArgumentException(sb3.toString());
        }
    }

    public DefaultMaxBytesRecvByteBufAllocator maxBytesPerReadPair(int i, int i2) {
        checkMaxBytesPerReadPair(i, i2);
        synchronized (this) {
            this.maxBytesPerRead = i;
            this.maxBytesPerIndividualRead = i2;
        }
        return this;
    }
}
