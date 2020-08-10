package p043io.netty.channel;

import java.util.ArrayDeque;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.CompositeByteBuf;
import p043io.netty.buffer.Unpooled;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.channel.CoalescingBufferQueue */
public final class CoalescingBufferQueue {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final ArrayDeque<Object> bufAndListenerPairs;
    private final Channel channel;
    private int readableBytes;

    public CoalescingBufferQueue(Channel channel2) {
        this(channel2, 4);
    }

    public CoalescingBufferQueue(Channel channel2, int i) {
        this.channel = (Channel) ObjectUtil.checkNotNull(channel2, "channel");
        this.bufAndListenerPairs = new ArrayDeque<>(i);
    }

    public void add(ByteBuf byteBuf) {
        add(byteBuf, (ChannelFutureListener) null);
    }

    public void add(ByteBuf byteBuf, ChannelPromise channelPromise) {
        ChannelFutureListener channelFutureListener;
        ObjectUtil.checkNotNull(channelPromise, "promise");
        if (channelPromise.isVoid()) {
            channelFutureListener = null;
        } else {
            channelFutureListener = new ChannelPromiseNotifier(channelPromise);
        }
        add(byteBuf, channelFutureListener);
    }

    public void add(ByteBuf byteBuf, ChannelFutureListener channelFutureListener) {
        ObjectUtil.checkNotNull(byteBuf, "buf");
        if (this.readableBytes <= Integer.MAX_VALUE - byteBuf.readableBytes()) {
            this.bufAndListenerPairs.add(byteBuf);
            if (channelFutureListener != null) {
                this.bufAndListenerPairs.add(channelFutureListener);
            }
            this.readableBytes += byteBuf.readableBytes();
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("buffer queue length overflow: ");
        sb.append(this.readableBytes);
        sb.append(" + ");
        sb.append(byteBuf.readableBytes());
        throw new IllegalStateException(sb.toString());
    }

    public ByteBuf remove(int i, ChannelPromise channelPromise) {
        if (i >= 0) {
            ObjectUtil.checkNotNull(channelPromise, "aggregatePromise");
            if (this.bufAndListenerPairs.isEmpty()) {
                return Unpooled.EMPTY_BUFFER;
            }
            int min = Math.min(i, this.readableBytes);
            ByteBuf byteBuf = null;
            int i2 = min;
            while (true) {
                Object poll = this.bufAndListenerPairs.poll();
                if (poll == null) {
                    break;
                } else if (poll instanceof ChannelFutureListener) {
                    channelPromise.addListener((ChannelFutureListener) poll);
                } else {
                    ByteBuf byteBuf2 = (ByteBuf) poll;
                    if (byteBuf2.readableBytes() > i2) {
                        this.bufAndListenerPairs.addFirst(byteBuf2);
                        if (i2 > 0) {
                            byteBuf = compose(byteBuf, byteBuf2.readRetainedSlice(i2));
                            i2 = 0;
                        }
                    } else {
                        byteBuf = compose(byteBuf, byteBuf2);
                        i2 -= byteBuf2.readableBytes();
                    }
                }
            }
            this.readableBytes -= min - i2;
            return byteBuf;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("bytes (expected >= 0): ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    private ByteBuf compose(ByteBuf byteBuf, ByteBuf byteBuf2) {
        if (byteBuf == null) {
            return byteBuf2;
        }
        if (byteBuf instanceof CompositeByteBuf) {
            CompositeByteBuf compositeByteBuf = (CompositeByteBuf) byteBuf;
            compositeByteBuf.addComponent(true, byteBuf2);
            return compositeByteBuf;
        }
        CompositeByteBuf compositeBuffer = this.channel.alloc().compositeBuffer(this.bufAndListenerPairs.size() + 2);
        compositeBuffer.addComponent(true, byteBuf);
        compositeBuffer.addComponent(true, byteBuf2);
        return compositeBuffer;
    }

    public int readableBytes() {
        return this.readableBytes;
    }

    public boolean isEmpty() {
        return this.bufAndListenerPairs.isEmpty();
    }

    public void releaseAndFailAll(Throwable th) {
        releaseAndCompleteAll(this.channel.newFailedFuture(th));
    }

    private void releaseAndCompleteAll(ChannelFuture channelFuture) {
        this.readableBytes = 0;
        Throwable th = null;
        while (true) {
            Object poll = this.bufAndListenerPairs.poll();
            if (poll == null) {
                break;
            }
            try {
                if (poll instanceof ByteBuf) {
                    ReferenceCountUtil.safeRelease(poll);
                } else {
                    ((ChannelFutureListener) poll).operationComplete(channelFuture);
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        if (th != null) {
            throw new IllegalStateException(th);
        }
    }

    public void copyTo(CoalescingBufferQueue coalescingBufferQueue) {
        coalescingBufferQueue.bufAndListenerPairs.addAll(this.bufAndListenerPairs);
        coalescingBufferQueue.readableBytes += this.readableBytes;
    }
}
