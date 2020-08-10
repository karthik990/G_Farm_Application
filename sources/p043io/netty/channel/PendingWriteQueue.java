package p043io.netty.channel;

import androidx.core.app.NotificationCompat;
import p043io.netty.channel.MessageSizeEstimator.Handle;
import p043io.netty.util.Recycler;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.channel.PendingWriteQueue */
public final class PendingWriteQueue {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int PENDING_WRITE_OVERHEAD = SystemPropertyUtil.getInt("io.netty.transport.pendingWriteSizeOverhead", 64);
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PendingWriteQueue.class);
    private final ChannelOutboundBuffer buffer;
    private long bytes;
    private final ChannelHandlerContext ctx;
    private final Handle estimatorHandle;
    private PendingWrite head;
    private int size;
    private PendingWrite tail;

    /* renamed from: io.netty.channel.PendingWriteQueue$PendingWrite */
    static final class PendingWrite {
        private static final Recycler<PendingWrite> RECYCLER = new Recycler<PendingWrite>() {
            /* access modifiers changed from: protected */
            public PendingWrite newObject(Recycler.Handle<PendingWrite> handle) {
                return new PendingWrite(handle);
            }
        };
        private final Recycler.Handle<PendingWrite> handle;
        /* access modifiers changed from: private */
        public Object msg;
        /* access modifiers changed from: private */
        public PendingWrite next;
        /* access modifiers changed from: private */
        public ChannelPromise promise;
        /* access modifiers changed from: private */
        public long size;

        private PendingWrite(Recycler.Handle<PendingWrite> handle2) {
            this.handle = handle2;
        }

        static PendingWrite newInstance(Object obj, int i, ChannelPromise channelPromise) {
            PendingWrite pendingWrite = (PendingWrite) RECYCLER.get();
            pendingWrite.size = (long) i;
            pendingWrite.msg = obj;
            pendingWrite.promise = channelPromise;
            return pendingWrite;
        }

        /* access modifiers changed from: private */
        public void recycle() {
            this.size = 0;
            this.next = null;
            this.msg = null;
            this.promise = null;
            this.handle.recycle(this);
        }
    }

    private void assertEmpty() {
    }

    public PendingWriteQueue(ChannelHandlerContext channelHandlerContext) {
        if (channelHandlerContext != null) {
            this.ctx = channelHandlerContext;
            this.buffer = channelHandlerContext.channel().unsafe().outboundBuffer();
            this.estimatorHandle = channelHandlerContext.channel().config().getMessageSizeEstimator().newHandle();
            return;
        }
        throw new NullPointerException("ctx");
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public int size() {
        return this.size;
    }

    public long bytes() {
        return this.bytes;
    }

    private int size(Object obj) {
        int size2 = this.estimatorHandle.size(obj);
        if (size2 < 0) {
            size2 = 0;
        }
        return size2 + PENDING_WRITE_OVERHEAD;
    }

    public void add(Object obj, ChannelPromise channelPromise) {
        if (obj == null) {
            throw new NullPointerException(NotificationCompat.CATEGORY_MESSAGE);
        } else if (channelPromise != null) {
            int size2 = size(obj);
            PendingWrite newInstance = PendingWrite.newInstance(obj, size2, channelPromise);
            PendingWrite pendingWrite = this.tail;
            if (pendingWrite == null) {
                this.head = newInstance;
                this.tail = newInstance;
            } else {
                pendingWrite.next = newInstance;
                this.tail = newInstance;
            }
            this.size++;
            this.bytes += (long) size2;
            ChannelOutboundBuffer channelOutboundBuffer = this.buffer;
            if (channelOutboundBuffer != null) {
                channelOutboundBuffer.incrementPendingOutboundBytes(newInstance.size);
            }
        } else {
            throw new NullPointerException("promise");
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003d A[EDGE_INSN: B:15:0x003d->B:10:0x003d ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0017 A[Catch:{ all -> 0x0041 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public p043io.netty.channel.ChannelFuture removeAndWriteAll() {
        /*
            r8 = this;
            boolean r0 = r8.isEmpty()
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            io.netty.channel.ChannelHandlerContext r0 = r8.ctx
            io.netty.channel.ChannelPromise r0 = r0.newPromise()
            io.netty.util.concurrent.PromiseCombiner r2 = new io.netty.util.concurrent.PromiseCombiner
            r2.<init>()
        L_0x0013:
            io.netty.channel.PendingWriteQueue$PendingWrite r3 = r8.head     // Catch:{ all -> 0x0041 }
            if (r3 == 0) goto L_0x003d
            r8.tail = r1     // Catch:{ all -> 0x0041 }
            r8.head = r1     // Catch:{ all -> 0x0041 }
            r4 = 0
            r8.size = r4     // Catch:{ all -> 0x0041 }
            r5 = 0
            r8.bytes = r5     // Catch:{ all -> 0x0041 }
        L_0x0022:
            if (r3 == 0) goto L_0x0013
            io.netty.channel.PendingWriteQueue$PendingWrite r5 = r3.next     // Catch:{ all -> 0x0041 }
            java.lang.Object r6 = r3.msg     // Catch:{ all -> 0x0041 }
            io.netty.channel.ChannelPromise r7 = r3.promise     // Catch:{ all -> 0x0041 }
            r8.recycle(r3, r4)     // Catch:{ all -> 0x0041 }
            r2.add(r7)     // Catch:{ all -> 0x0041 }
            io.netty.channel.ChannelHandlerContext r3 = r8.ctx     // Catch:{ all -> 0x0041 }
            r3.write(r6, r7)     // Catch:{ all -> 0x0041 }
            r3 = r5
            goto L_0x0022
        L_0x003d:
            r2.finish(r0)     // Catch:{ all -> 0x0041 }
            goto L_0x0045
        L_0x0041:
            r1 = move-exception
            r0.setFailure(r1)
        L_0x0045:
            r8.assertEmpty()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.PendingWriteQueue.removeAndWriteAll():io.netty.channel.ChannelFuture");
    }

    public void removeAndFailAll(Throwable th) {
        if (th == null) {
            throw new NullPointerException("cause");
        }
        while (true) {
            PendingWrite pendingWrite = this.head;
            if (pendingWrite != null) {
                this.tail = null;
                this.head = null;
                this.size = 0;
                this.bytes = 0;
                while (pendingWrite != null) {
                    PendingWrite access$000 = pendingWrite.next;
                    ReferenceCountUtil.safeRelease(pendingWrite.msg);
                    ChannelPromise access$300 = pendingWrite.promise;
                    recycle(pendingWrite, false);
                    safeFail(access$300, th);
                    pendingWrite = access$000;
                }
            } else {
                assertEmpty();
                return;
            }
        }
    }

    public void removeAndFail(Throwable th) {
        if (th != null) {
            PendingWrite pendingWrite = this.head;
            if (pendingWrite != null) {
                ReferenceCountUtil.safeRelease(pendingWrite.msg);
                safeFail(pendingWrite.promise, th);
                recycle(pendingWrite, true);
                return;
            }
            return;
        }
        throw new NullPointerException("cause");
    }

    public ChannelFuture removeAndWrite() {
        PendingWrite pendingWrite = this.head;
        if (pendingWrite == null) {
            return null;
        }
        Object access$200 = pendingWrite.msg;
        ChannelPromise access$300 = pendingWrite.promise;
        recycle(pendingWrite, true);
        return this.ctx.write(access$200, access$300);
    }

    public ChannelPromise remove() {
        PendingWrite pendingWrite = this.head;
        if (pendingWrite == null) {
            return null;
        }
        ChannelPromise access$300 = pendingWrite.promise;
        ReferenceCountUtil.safeRelease(pendingWrite.msg);
        recycle(pendingWrite, true);
        return access$300;
    }

    public Object current() {
        PendingWrite pendingWrite = this.head;
        if (pendingWrite == null) {
            return null;
        }
        return pendingWrite.msg;
    }

    private void recycle(PendingWrite pendingWrite, boolean z) {
        PendingWrite access$000 = pendingWrite.next;
        long access$100 = pendingWrite.size;
        if (z) {
            if (access$000 == null) {
                this.tail = null;
                this.head = null;
                this.size = 0;
                this.bytes = 0;
            } else {
                this.head = access$000;
                this.size--;
                this.bytes -= access$100;
            }
        }
        pendingWrite.recycle();
        ChannelOutboundBuffer channelOutboundBuffer = this.buffer;
        if (channelOutboundBuffer != null) {
            channelOutboundBuffer.decrementPendingOutboundBytes(access$100);
        }
    }

    private static void safeFail(ChannelPromise channelPromise, Throwable th) {
        if (!(channelPromise instanceof VoidChannelPromise) && !channelPromise.tryFailure(th)) {
            logger.warn("Failed to mark a promise as failure because it's done already: {}", channelPromise, th);
        }
    }
}
