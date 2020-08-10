package p043io.netty.channel;

import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.util.UncheckedBooleanSupplier;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.channel.RecvByteBufAllocator */
public interface RecvByteBufAllocator {

    /* renamed from: io.netty.channel.RecvByteBufAllocator$DelegatingHandle */
    public static class DelegatingHandle implements Handle {
        private final Handle delegate;

        public DelegatingHandle(Handle handle) {
            this.delegate = (Handle) ObjectUtil.checkNotNull(handle, "delegate");
        }

        /* access modifiers changed from: protected */
        public final Handle delegate() {
            return this.delegate;
        }

        public ByteBuf allocate(ByteBufAllocator byteBufAllocator) {
            return this.delegate.allocate(byteBufAllocator);
        }

        public int guess() {
            return this.delegate.guess();
        }

        public void reset(ChannelConfig channelConfig) {
            this.delegate.reset(channelConfig);
        }

        public void incMessagesRead(int i) {
            this.delegate.incMessagesRead(i);
        }

        public void lastBytesRead(int i) {
            this.delegate.lastBytesRead(i);
        }

        public int lastBytesRead() {
            return this.delegate.lastBytesRead();
        }

        public boolean continueReading() {
            return this.delegate.continueReading();
        }

        public int attemptedBytesRead() {
            return this.delegate.attemptedBytesRead();
        }

        public void attemptedBytesRead(int i) {
            this.delegate.attemptedBytesRead(i);
        }

        public void readComplete() {
            this.delegate.readComplete();
        }
    }

    /* renamed from: io.netty.channel.RecvByteBufAllocator$ExtendedHandle */
    public interface ExtendedHandle extends Handle {
        boolean continueReading(UncheckedBooleanSupplier uncheckedBooleanSupplier);
    }

    @Deprecated
    /* renamed from: io.netty.channel.RecvByteBufAllocator$Handle */
    public interface Handle {
        ByteBuf allocate(ByteBufAllocator byteBufAllocator);

        int attemptedBytesRead();

        void attemptedBytesRead(int i);

        boolean continueReading();

        int guess();

        void incMessagesRead(int i);

        int lastBytesRead();

        void lastBytesRead(int i);

        void readComplete();

        void reset(ChannelConfig channelConfig);
    }

    Handle newHandle();
}
