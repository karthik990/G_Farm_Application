package p043io.netty.handler.codec;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.buffer.CompositeByteBuf;
import p043io.netty.buffer.Unpooled;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelInboundHandlerAdapter;
import p043io.netty.channel.socket.ChannelInputShutdownEvent;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.handler.codec.ByteToMessageDecoder */
public abstract class ByteToMessageDecoder extends ChannelInboundHandlerAdapter {
    public static final Cumulator COMPOSITE_CUMULATOR = new Cumulator() {
        public ByteBuf cumulate(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) {
            CompositeByteBuf compositeByteBuf;
            if (byteBuf.refCnt() > 1) {
                ByteBuf expandCumulation = ByteToMessageDecoder.expandCumulation(byteBufAllocator, byteBuf, byteBuf2.readableBytes());
                expandCumulation.writeBytes(byteBuf2);
                byteBuf2.release();
                return expandCumulation;
            }
            if (byteBuf instanceof CompositeByteBuf) {
                compositeByteBuf = (CompositeByteBuf) byteBuf;
            } else {
                compositeByteBuf = byteBufAllocator.compositeBuffer(Integer.MAX_VALUE);
                compositeByteBuf.addComponent(true, byteBuf);
            }
            compositeByteBuf.addComponent(true, byteBuf2);
            return compositeByteBuf;
        }
    };
    public static final Cumulator MERGE_CUMULATOR = new Cumulator() {
        public ByteBuf cumulate(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) {
            if (byteBuf.writerIndex() > byteBuf.maxCapacity() - byteBuf2.readableBytes() || byteBuf.refCnt() > 1 || byteBuf.isReadOnly()) {
                byteBuf = ByteToMessageDecoder.expandCumulation(byteBufAllocator, byteBuf, byteBuf2.readableBytes());
            }
            byteBuf.writeBytes(byteBuf2);
            byteBuf2.release();
            return byteBuf;
        }
    };
    private static final byte STATE_CALLING_CHILD_DECODE = 1;
    private static final byte STATE_HANDLER_REMOVED_PENDING = 2;
    private static final byte STATE_INIT = 0;
    ByteBuf cumulation;
    private Cumulator cumulator = MERGE_CUMULATOR;
    private byte decodeState = 0;
    private boolean decodeWasNull;
    private int discardAfterReads = 16;
    private boolean first;
    private int numReads;
    private boolean singleDecode;

    /* renamed from: io.netty.handler.codec.ByteToMessageDecoder$Cumulator */
    public interface Cumulator {
        ByteBuf cumulate(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2);
    }

    /* access modifiers changed from: protected */
    public abstract void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception;

    /* access modifiers changed from: protected */
    public void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    protected ByteToMessageDecoder() {
        ensureNotSharable();
    }

    public void setSingleDecode(boolean z) {
        this.singleDecode = z;
    }

    public boolean isSingleDecode() {
        return this.singleDecode;
    }

    public void setCumulator(Cumulator cumulator2) {
        if (cumulator2 != null) {
            this.cumulator = cumulator2;
            return;
        }
        throw new NullPointerException("cumulator");
    }

    public void setDiscardAfterReads(int i) {
        if (i > 0) {
            this.discardAfterReads = i;
            return;
        }
        throw new IllegalArgumentException("discardAfterReads must be > 0");
    }

    /* access modifiers changed from: protected */
    public int actualReadableBytes() {
        return internalBuffer().readableBytes();
    }

    /* access modifiers changed from: protected */
    public ByteBuf internalBuffer() {
        ByteBuf byteBuf = this.cumulation;
        if (byteBuf != null) {
            return byteBuf;
        }
        return Unpooled.EMPTY_BUFFER;
    }

    public final void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.decodeState == 1) {
            this.decodeState = 2;
            return;
        }
        ByteBuf byteBuf = this.cumulation;
        if (byteBuf != null) {
            this.cumulation = null;
            int readableBytes = byteBuf.readableBytes();
            if (readableBytes > 0) {
                ByteBuf readBytes = byteBuf.readBytes(readableBytes);
                byteBuf.release();
                channelHandlerContext.fireChannelRead(readBytes);
            } else {
                byteBuf.release();
            }
            this.numReads = 0;
            channelHandlerContext.fireChannelReadComplete();
        }
        handlerRemoved0(channelHandlerContext);
    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof ByteBuf) {
            CodecOutputList newInstance = CodecOutputList.newInstance();
            try {
                ByteBuf byteBuf = (ByteBuf) obj;
                this.first = this.cumulation == null;
                if (this.first) {
                    this.cumulation = byteBuf;
                } else {
                    this.cumulation = this.cumulator.cumulate(channelHandlerContext.alloc(), this.cumulation, byteBuf);
                }
                callDecode(channelHandlerContext, this.cumulation, newInstance);
                ByteBuf byteBuf2 = this.cumulation;
                if (byteBuf2 == null || byteBuf2.isReadable()) {
                    int i = this.numReads + 1;
                    this.numReads = i;
                    if (i >= this.discardAfterReads) {
                        this.numReads = 0;
                        discardSomeReadBytes();
                    }
                } else {
                    this.numReads = 0;
                    this.cumulation.release();
                    this.cumulation = null;
                }
                int size = newInstance.size();
                this.decodeWasNull = !newInstance.insertSinceRecycled();
                fireChannelRead(channelHandlerContext, newInstance, size);
                newInstance.recycle();
            } catch (DecoderException e) {
                throw e;
            } catch (Throwable th) {
                ByteBuf byteBuf3 = this.cumulation;
                if (byteBuf3 == null || byteBuf3.isReadable()) {
                    int i2 = this.numReads + 1;
                    this.numReads = i2;
                    if (i2 >= this.discardAfterReads) {
                        this.numReads = 0;
                        discardSomeReadBytes();
                    }
                } else {
                    this.numReads = 0;
                    this.cumulation.release();
                    this.cumulation = null;
                }
                int size2 = newInstance.size();
                this.decodeWasNull = true ^ newInstance.insertSinceRecycled();
                fireChannelRead(channelHandlerContext, newInstance, size2);
                newInstance.recycle();
                throw th;
            }
        } else {
            channelHandlerContext.fireChannelRead(obj);
        }
    }

    static void fireChannelRead(ChannelHandlerContext channelHandlerContext, List<Object> list, int i) {
        if (list instanceof CodecOutputList) {
            fireChannelRead(channelHandlerContext, (CodecOutputList) list, i);
            return;
        }
        for (int i2 = 0; i2 < i; i2++) {
            channelHandlerContext.fireChannelRead(list.get(i2));
        }
    }

    static void fireChannelRead(ChannelHandlerContext channelHandlerContext, CodecOutputList codecOutputList, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            channelHandlerContext.fireChannelRead(codecOutputList.getUnsafe(i2));
        }
    }

    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.numReads = 0;
        discardSomeReadBytes();
        if (this.decodeWasNull) {
            this.decodeWasNull = false;
            if (!channelHandlerContext.channel().config().isAutoRead()) {
                channelHandlerContext.read();
            }
        }
        channelHandlerContext.fireChannelReadComplete();
    }

    /* access modifiers changed from: protected */
    public final void discardSomeReadBytes() {
        ByteBuf byteBuf = this.cumulation;
        if (byteBuf != null && !this.first && byteBuf.refCnt() == 1) {
            this.cumulation.discardSomeReadBytes();
        }
    }

    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelInputClosed(channelHandlerContext, true);
    }

    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (obj instanceof ChannelInputShutdownEvent) {
            channelInputClosed(channelHandlerContext, false);
        }
        super.userEventTriggered(channelHandlerContext, obj);
    }

    private void channelInputClosed(ChannelHandlerContext channelHandlerContext, boolean z) throws Exception {
        CodecOutputList newInstance = CodecOutputList.newInstance();
        try {
            channelInputClosed(channelHandlerContext, (List<Object>) newInstance);
            try {
                if (this.cumulation != null) {
                    this.cumulation.release();
                    this.cumulation = null;
                }
                int size = newInstance.size();
                fireChannelRead(channelHandlerContext, newInstance, size);
                if (size > 0) {
                    channelHandlerContext.fireChannelReadComplete();
                }
                if (z) {
                    channelHandlerContext.fireChannelInactive();
                }
            } finally {
                newInstance.recycle();
            }
        } catch (DecoderException e) {
            throw e;
        } catch (Exception e2) {
            throw new DecoderException((Throwable) e2);
        } catch (Throwable th) {
            newInstance.recycle();
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public void channelInputClosed(ChannelHandlerContext channelHandlerContext, List<Object> list) throws Exception {
        ByteBuf byteBuf = this.cumulation;
        if (byteBuf != null) {
            callDecode(channelHandlerContext, byteBuf, list);
            decodeLast(channelHandlerContext, this.cumulation, list);
            return;
        }
        decodeLast(channelHandlerContext, Unpooled.EMPTY_BUFFER, list);
    }

    /* access modifiers changed from: protected */
    public void callDecode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        while (byteBuf.isReadable()) {
            try {
                int size = list.size();
                if (size > 0) {
                    fireChannelRead(channelHandlerContext, list, size);
                    list.clear();
                    if (!channelHandlerContext.isRemoved()) {
                        size = 0;
                    } else {
                        return;
                    }
                }
                int readableBytes = byteBuf.readableBytes();
                decodeRemovalReentryProtection(channelHandlerContext, byteBuf, list);
                if (!channelHandlerContext.isRemoved()) {
                    if (size == list.size()) {
                        if (readableBytes == byteBuf.readableBytes()) {
                            return;
                        }
                    } else if (readableBytes == byteBuf.readableBytes()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(StringUtil.simpleClassName(getClass()));
                        sb.append(".decode() did not read anything but decoded a message.");
                        throw new DecoderException(sb.toString());
                    } else if (isSingleDecode()) {
                        return;
                    }
                } else {
                    return;
                }
            } catch (DecoderException e) {
                throw e;
            } catch (Throwable th) {
                throw new DecoderException(th);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void decodeRemovalReentryProtection(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte b = 1;
        this.decodeState = b;
        try {
            decode(channelHandlerContext, byteBuf, list);
        } finally {
            if (this.decodeState != 2) {
                b = 0;
            }
            this.decodeState = 0;
            if (b != 0) {
                handlerRemoved(channelHandlerContext);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.isReadable()) {
            decodeRemovalReentryProtection(channelHandlerContext, byteBuf, list);
        }
    }

    static ByteBuf expandCumulation(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, int i) {
        ByteBuf buffer = byteBufAllocator.buffer(byteBuf.readableBytes() + i);
        buffer.writeBytes(byteBuf);
        byteBuf.release();
        return buffer;
    }
}
