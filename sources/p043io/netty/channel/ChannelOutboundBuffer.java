package p043io.netty.channel;

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufHolder;
import p043io.netty.buffer.Unpooled;
import p043io.netty.util.Recycler;
import p043io.netty.util.Recycler.Handle;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.concurrent.FastThreadLocal;
import p043io.netty.util.internal.InternalThreadLocalMap;
import p043io.netty.util.internal.PromiseNotificationUtil;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.ChannelOutboundBuffer */
public final class ChannelOutboundBuffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.outboundBufferEntrySizeOverhead", 96);
    private static final FastThreadLocal<ByteBuffer[]> NIO_BUFFERS = new FastThreadLocal<ByteBuffer[]>() {
        /* access modifiers changed from: protected */
        public ByteBuffer[] initialValue() throws Exception {
            return new ByteBuffer[1024];
        }
    };
    private static final AtomicLongFieldUpdater<ChannelOutboundBuffer> TOTAL_PENDING_SIZE_UPDATER;
    private static final AtomicIntegerFieldUpdater<ChannelOutboundBuffer> UNWRITABLE_UPDATER;
    private static final InternalLogger logger;
    private final Channel channel;
    private volatile Runnable fireChannelWritabilityChangedTask;
    private int flushed;
    private Entry flushedEntry;
    private boolean inFail;
    private int nioBufferCount;
    private long nioBufferSize;
    private Entry tailEntry;
    private volatile long totalPendingSize;
    private Entry unflushedEntry;
    private volatile int unwritable;

    /* renamed from: io.netty.channel.ChannelOutboundBuffer$Entry */
    static final class Entry {
        private static final Recycler<Entry> RECYCLER = new Recycler<Entry>() {
            /* access modifiers changed from: protected */
            public Entry newObject(Handle<Entry> handle) {
                return new Entry(handle);
            }
        };
        ByteBuffer buf;
        ByteBuffer[] bufs;
        boolean cancelled;
        int count;
        private final Handle<Entry> handle;
        Object msg;
        Entry next;
        int pendingSize;
        long progress;
        ChannelPromise promise;
        long total;

        private Entry(Handle<Entry> handle2) {
            this.count = -1;
            this.handle = handle2;
        }

        static Entry newInstance(Object obj, int i, long j, ChannelPromise channelPromise) {
            Entry entry = (Entry) RECYCLER.get();
            entry.msg = obj;
            entry.pendingSize = i + ChannelOutboundBuffer.CHANNEL_OUTBOUND_BUFFER_ENTRY_OVERHEAD;
            entry.total = j;
            entry.promise = channelPromise;
            return entry;
        }

        /* access modifiers changed from: 0000 */
        public int cancel() {
            if (this.cancelled) {
                return 0;
            }
            this.cancelled = true;
            int i = this.pendingSize;
            ReferenceCountUtil.safeRelease(this.msg);
            this.msg = Unpooled.EMPTY_BUFFER;
            this.pendingSize = 0;
            this.total = 0;
            this.progress = 0;
            this.bufs = null;
            this.buf = null;
            return i;
        }

        /* access modifiers changed from: 0000 */
        public void recycle() {
            this.next = null;
            this.bufs = null;
            this.buf = null;
            this.msg = null;
            this.promise = null;
            this.progress = 0;
            this.total = 0;
            this.pendingSize = 0;
            this.count = -1;
            this.cancelled = false;
            this.handle.recycle(this);
        }

        /* access modifiers changed from: 0000 */
        public Entry recycleAndGetNext() {
            Entry entry = this.next;
            recycle();
            return entry;
        }
    }

    /* renamed from: io.netty.channel.ChannelOutboundBuffer$MessageProcessor */
    public interface MessageProcessor {
        boolean processMessage(Object obj) throws Exception;
    }

    @Deprecated
    public void recycle() {
    }

    static {
        Class<ChannelOutboundBuffer> cls = ChannelOutboundBuffer.class;
        logger = InternalLoggerFactory.getInstance(cls);
        TOTAL_PENDING_SIZE_UPDATER = AtomicLongFieldUpdater.newUpdater(cls, "totalPendingSize");
        UNWRITABLE_UPDATER = AtomicIntegerFieldUpdater.newUpdater(cls, "unwritable");
    }

    ChannelOutboundBuffer(AbstractChannel abstractChannel) {
        this.channel = abstractChannel;
    }

    public void addMessage(Object obj, int i, ChannelPromise channelPromise) {
        Entry newInstance = Entry.newInstance(obj, i, total(obj), channelPromise);
        Entry entry = this.tailEntry;
        if (entry == null) {
            this.flushedEntry = null;
            this.tailEntry = newInstance;
        } else {
            entry.next = newInstance;
            this.tailEntry = newInstance;
        }
        if (this.unflushedEntry == null) {
            this.unflushedEntry = newInstance;
        }
        incrementPendingOutboundBytes((long) newInstance.pendingSize, false);
    }

    public void addFlush() {
        Entry entry = this.unflushedEntry;
        if (entry != null) {
            if (this.flushedEntry == null) {
                this.flushedEntry = entry;
            }
            do {
                this.flushed++;
                if (!entry.promise.setUncancellable()) {
                    decrementPendingOutboundBytes((long) entry.cancel(), false, true);
                }
                entry = entry.next;
            } while (entry != null);
            this.unflushedEntry = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void incrementPendingOutboundBytes(long j) {
        incrementPendingOutboundBytes(j, true);
    }

    private void incrementPendingOutboundBytes(long j, boolean z) {
        if (j != 0 && TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, j) > ((long) this.channel.config().getWriteBufferHighWaterMark())) {
            setUnwritable(z);
        }
    }

    /* access modifiers changed from: 0000 */
    public void decrementPendingOutboundBytes(long j) {
        decrementPendingOutboundBytes(j, true, true);
    }

    private void decrementPendingOutboundBytes(long j, boolean z, boolean z2) {
        if (j != 0) {
            long addAndGet = TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, -j);
            if (z2 && addAndGet < ((long) this.channel.config().getWriteBufferLowWaterMark())) {
                setWritable(z);
            }
        }
    }

    private static long total(Object obj) {
        if (obj instanceof ByteBuf) {
            return (long) ((ByteBuf) obj).readableBytes();
        }
        if (obj instanceof FileRegion) {
            return ((FileRegion) obj).count();
        }
        if (obj instanceof ByteBufHolder) {
            return (long) ((ByteBufHolder) obj).content().readableBytes();
        }
        return -1;
    }

    public Object current() {
        Entry entry = this.flushedEntry;
        if (entry == null) {
            return null;
        }
        return entry.msg;
    }

    public void progress(long j) {
        Entry entry = this.flushedEntry;
        ChannelPromise channelPromise = entry.promise;
        if (channelPromise instanceof ChannelProgressivePromise) {
            long j2 = entry.progress + j;
            entry.progress = j2;
            ((ChannelProgressivePromise) channelPromise).tryProgress(j2, entry.total);
        }
    }

    public boolean remove() {
        Entry entry = this.flushedEntry;
        if (entry == null) {
            clearNioBuffers();
            return false;
        }
        Object obj = entry.msg;
        ChannelPromise channelPromise = entry.promise;
        int i = entry.pendingSize;
        removeEntry(entry);
        if (!entry.cancelled) {
            ReferenceCountUtil.safeRelease(obj);
            safeSuccess(channelPromise);
            decrementPendingOutboundBytes((long) i, false, true);
        }
        entry.recycle();
        return true;
    }

    public boolean remove(Throwable th) {
        return remove0(th, true);
    }

    private boolean remove0(Throwable th, boolean z) {
        Entry entry = this.flushedEntry;
        if (entry == null) {
            clearNioBuffers();
            return false;
        }
        Object obj = entry.msg;
        ChannelPromise channelPromise = entry.promise;
        int i = entry.pendingSize;
        removeEntry(entry);
        if (!entry.cancelled) {
            ReferenceCountUtil.safeRelease(obj);
            safeFail(channelPromise, th);
            decrementPendingOutboundBytes((long) i, false, z);
        }
        entry.recycle();
        return true;
    }

    private void removeEntry(Entry entry) {
        int i = this.flushed - 1;
        this.flushed = i;
        if (i == 0) {
            this.flushedEntry = null;
            if (entry == this.tailEntry) {
                this.tailEntry = null;
                this.unflushedEntry = null;
                return;
            }
            return;
        }
        this.flushedEntry = entry.next;
    }

    public void removeBytes(long j) {
        while (true) {
            Object current = current();
            if (!(current instanceof ByteBuf)) {
                break;
            }
            ByteBuf byteBuf = (ByteBuf) current;
            int readerIndex = byteBuf.readerIndex();
            long writerIndex = (long) (byteBuf.writerIndex() - readerIndex);
            if (writerIndex <= j) {
                if (j != 0) {
                    progress(writerIndex);
                    j -= writerIndex;
                }
                remove();
            } else if (j != 0) {
                byteBuf.readerIndex(readerIndex + ((int) j));
                progress(j);
            }
        }
        clearNioBuffers();
    }

    private void clearNioBuffers() {
        int i = this.nioBufferCount;
        if (i > 0) {
            this.nioBufferCount = 0;
            Arrays.fill((Object[]) NIO_BUFFERS.get(), 0, i, null);
        }
    }

    public ByteBuffer[] nioBuffers() {
        InternalThreadLocalMap internalThreadLocalMap = InternalThreadLocalMap.get();
        ByteBuffer[] byteBufferArr = (ByteBuffer[]) NIO_BUFFERS.get(internalThreadLocalMap);
        Entry entry = this.flushedEntry;
        long j = 0;
        int i = 0;
        while (isFlushedEntry(entry) && (entry.msg instanceof ByteBuf)) {
            if (!entry.cancelled) {
                ByteBuf byteBuf = (ByteBuf) entry.msg;
                int readerIndex = byteBuf.readerIndex();
                int writerIndex = byteBuf.writerIndex() - readerIndex;
                if (writerIndex <= 0) {
                    continue;
                } else if (((long) (Integer.MAX_VALUE - writerIndex)) < j) {
                    break;
                } else {
                    j += (long) writerIndex;
                    int i2 = entry.count;
                    if (i2 == -1) {
                        i2 = byteBuf.nioBufferCount();
                        entry.count = i2;
                    }
                    int i3 = i + i2;
                    if (i3 > byteBufferArr.length) {
                        byteBufferArr = expandNioBufferArray(byteBufferArr, i3, i);
                        NIO_BUFFERS.set(internalThreadLocalMap, byteBufferArr);
                    }
                    if (i2 == 1) {
                        ByteBuffer byteBuffer = entry.buf;
                        if (byteBuffer == null) {
                            byteBuffer = byteBuf.internalNioBuffer(readerIndex, writerIndex);
                            entry.buf = byteBuffer;
                        }
                        int i4 = i + 1;
                        byteBufferArr[i] = byteBuffer;
                        i = i4;
                    } else {
                        ByteBuffer[] byteBufferArr2 = entry.bufs;
                        if (byteBufferArr2 == null) {
                            byteBufferArr2 = byteBuf.nioBuffers();
                            entry.bufs = byteBufferArr2;
                        }
                        i = fillBufferArray(byteBufferArr2, byteBufferArr, i);
                    }
                }
            }
            entry = entry.next;
        }
        this.nioBufferCount = i;
        this.nioBufferSize = j;
        return byteBufferArr;
    }

    private static int fillBufferArray(ByteBuffer[] byteBufferArr, ByteBuffer[] byteBufferArr2, int i) {
        int length = byteBufferArr.length;
        int i2 = 0;
        while (i2 < length) {
            ByteBuffer byteBuffer = byteBufferArr[i2];
            if (byteBuffer == null) {
                break;
            }
            int i3 = i + 1;
            byteBufferArr2[i] = byteBuffer;
            i2++;
            i = i3;
        }
        return i;
    }

    private static ByteBuffer[] expandNioBufferArray(ByteBuffer[] byteBufferArr, int i, int i2) {
        int length = byteBufferArr.length;
        do {
            length <<= 1;
            if (length < 0) {
                throw new IllegalStateException();
            }
        } while (i > length);
        ByteBuffer[] byteBufferArr2 = new ByteBuffer[length];
        System.arraycopy(byteBufferArr, 0, byteBufferArr2, 0, i2);
        return byteBufferArr2;
    }

    public int nioBufferCount() {
        return this.nioBufferCount;
    }

    public long nioBufferSize() {
        return this.nioBufferSize;
    }

    public boolean isWritable() {
        return this.unwritable == 0;
    }

    public boolean getUserDefinedWritability(int i) {
        return (writabilityMask(i) & this.unwritable) == 0;
    }

    public void setUserDefinedWritability(int i, boolean z) {
        if (z) {
            setUserDefinedWritability(i);
        } else {
            clearUserDefinedWritability(i);
        }
    }

    private void setUserDefinedWritability(int i) {
        int i2;
        int i3;
        int writabilityMask = writabilityMask(i) ^ -1;
        do {
            i2 = this.unwritable;
            i3 = i2 & writabilityMask;
        } while (!UNWRITABLE_UPDATER.compareAndSet(this, i2, i3));
        if (i2 != 0 && i3 == 0) {
            fireChannelWritabilityChanged(true);
        }
    }

    private void clearUserDefinedWritability(int i) {
        int i2;
        int i3;
        int writabilityMask = writabilityMask(i);
        do {
            i2 = this.unwritable;
            i3 = i2 | writabilityMask;
        } while (!UNWRITABLE_UPDATER.compareAndSet(this, i2, i3));
        if (i2 == 0 && i3 != 0) {
            fireChannelWritabilityChanged(true);
        }
    }

    private static int writabilityMask(int i) {
        if (i >= 1 && i <= 31) {
            return 1 << i;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("index: ");
        sb.append(i);
        sb.append(" (expected: 1~31)");
        throw new IllegalArgumentException(sb.toString());
    }

    private void setWritable(boolean z) {
        int i;
        int i2;
        do {
            i = this.unwritable;
            i2 = i & -2;
        } while (!UNWRITABLE_UPDATER.compareAndSet(this, i, i2));
        if (i != 0 && i2 == 0) {
            fireChannelWritabilityChanged(z);
        }
    }

    private void setUnwritable(boolean z) {
        int i;
        int i2;
        do {
            i = this.unwritable;
            i2 = i | 1;
        } while (!UNWRITABLE_UPDATER.compareAndSet(this, i, i2));
        if (i == 0 && i2 != 0) {
            fireChannelWritabilityChanged(z);
        }
    }

    private void fireChannelWritabilityChanged(boolean z) {
        final ChannelPipeline pipeline = this.channel.pipeline();
        if (z) {
            Runnable runnable = this.fireChannelWritabilityChangedTask;
            if (runnable == null) {
                runnable = new Runnable() {
                    public void run() {
                        pipeline.fireChannelWritabilityChanged();
                    }
                };
                this.fireChannelWritabilityChangedTask = runnable;
            }
            this.channel.eventLoop().execute(runnable);
            return;
        }
        pipeline.fireChannelWritabilityChanged();
    }

    public int size() {
        return this.flushed;
    }

    public boolean isEmpty() {
        return this.flushed == 0;
    }

    /* access modifiers changed from: 0000 */
    public void failFlushed(Throwable th, boolean z) {
        if (!this.inFail) {
            try {
                this.inFail = true;
                do {
                } while (remove0(th, z));
            } finally {
                this.inFail = false;
            }
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: 0000 */
    public void close(final ClosedChannelException closedChannelException) {
        if (this.inFail) {
            this.channel.eventLoop().execute(new Runnable() {
                public void run() {
                    ChannelOutboundBuffer.this.close(closedChannelException);
                }
            });
            return;
        }
        this.inFail = true;
        if (this.channel.isOpen()) {
            throw new IllegalStateException("close() must be invoked after the channel is closed.");
        } else if (isEmpty()) {
            try {
                for (Entry entry = this.unflushedEntry; entry != null; entry = entry.recycleAndGetNext()) {
                    TOTAL_PENDING_SIZE_UPDATER.addAndGet(this, (long) (-entry.pendingSize));
                    if (!entry.cancelled) {
                        ReferenceCountUtil.safeRelease(entry.msg);
                        safeFail(entry.promise, closedChannelException);
                    }
                }
                this.inFail = false;
                clearNioBuffers();
            } catch (Throwable th) {
                this.inFail = false;
                throw th;
            }
        } else {
            throw new IllegalStateException("close() must be invoked after all flushed writes are handled.");
        }
    }

    private static void safeSuccess(ChannelPromise channelPromise) {
        PromiseNotificationUtil.trySuccess(channelPromise, null, channelPromise instanceof VoidChannelPromise ? null : logger);
    }

    private static void safeFail(ChannelPromise channelPromise, Throwable th) {
        PromiseNotificationUtil.tryFailure(channelPromise, th, channelPromise instanceof VoidChannelPromise ? null : logger);
    }

    public long totalPendingWriteBytes() {
        return this.totalPendingSize;
    }

    public long bytesBeforeUnwritable() {
        long writeBufferHighWaterMark = ((long) this.channel.config().getWriteBufferHighWaterMark()) - this.totalPendingSize;
        if (writeBufferHighWaterMark <= 0) {
            return 0;
        }
        if (!isWritable()) {
            writeBufferHighWaterMark = 0;
        }
        return writeBufferHighWaterMark;
    }

    public long bytesBeforeWritable() {
        long writeBufferLowWaterMark = this.totalPendingSize - ((long) this.channel.config().getWriteBufferLowWaterMark());
        if (writeBufferLowWaterMark <= 0) {
            return 0;
        }
        if (isWritable()) {
            writeBufferLowWaterMark = 0;
        }
        return writeBufferLowWaterMark;
    }

    public void forEachFlushedMessage(MessageProcessor messageProcessor) throws Exception {
        if (messageProcessor != null) {
            Entry entry = this.flushedEntry;
            if (entry != null) {
                do {
                    if (entry.cancelled || messageProcessor.processMessage(entry.msg)) {
                        entry = entry.next;
                    } else {
                        return;
                    }
                } while (isFlushedEntry(entry));
                return;
            }
            return;
        }
        throw new NullPointerException("processor");
    }

    private boolean isFlushedEntry(Entry entry) {
        return (entry == null || entry == this.unflushedEntry) ? false : true;
    }
}
