package p043io.netty.handler.codec;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufHolder;
import p043io.netty.buffer.CompositeByteBuf;
import p043io.netty.buffer.Unpooled;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelPipeline;
import p043io.netty.util.ReferenceCountUtil;

/* renamed from: io.netty.handler.codec.MessageAggregator */
public abstract class MessageAggregator<I, S, C extends ByteBufHolder, O extends ByteBufHolder> extends MessageToMessageDecoder<I> {
    private static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;
    private ChannelFutureListener continueResponseWriteListener;
    private ChannelHandlerContext ctx;
    private O currentMessage;
    private boolean handlingOversizedMessage;
    private final int maxContentLength;
    private int maxCumulationBufferComponents = 1024;

    /* access modifiers changed from: protected */
    public void aggregate(O o, C c) throws Exception {
    }

    /* access modifiers changed from: protected */
    public abstract O beginAggregation(S s, ByteBuf byteBuf) throws Exception;

    /* access modifiers changed from: protected */
    public abstract boolean closeAfterContinueResponse(Object obj) throws Exception;

    /* access modifiers changed from: protected */
    public void finishAggregation(O o) throws Exception {
    }

    /* access modifiers changed from: protected */
    public abstract boolean ignoreContentAfterContinueResponse(Object obj) throws Exception;

    /* access modifiers changed from: protected */
    public abstract boolean isAggregated(I i) throws Exception;

    /* access modifiers changed from: protected */
    public abstract boolean isContentLengthInvalid(S s, int i) throws Exception;

    /* access modifiers changed from: protected */
    public abstract boolean isContentMessage(I i) throws Exception;

    /* access modifiers changed from: protected */
    public abstract boolean isLastContentMessage(C c) throws Exception;

    /* access modifiers changed from: protected */
    public abstract boolean isStartMessage(I i) throws Exception;

    /* access modifiers changed from: protected */
    public abstract Object newContinueResponse(S s, int i, ChannelPipeline channelPipeline) throws Exception;

    protected MessageAggregator(int i) {
        validateMaxContentLength(i);
        this.maxContentLength = i;
    }

    protected MessageAggregator(int i, Class<? extends I> cls) {
        super(cls);
        validateMaxContentLength(i);
        this.maxContentLength = i;
    }

    private static void validateMaxContentLength(int i) {
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("maxContentLength: ");
            sb.append(i);
            sb.append(" (expected: >= 0)");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public boolean acceptInboundMessage(Object obj) throws Exception {
        boolean z = false;
        if (!super.acceptInboundMessage(obj)) {
            return false;
        }
        if ((isContentMessage(obj) || isStartMessage(obj)) && !isAggregated(obj)) {
            z = true;
        }
        return z;
    }

    public final int maxContentLength() {
        return this.maxContentLength;
    }

    public final int maxCumulationBufferComponents() {
        return this.maxCumulationBufferComponents;
    }

    public final void setMaxCumulationBufferComponents(int i) {
        if (i < 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("maxCumulationBufferComponents: ");
            sb.append(i);
            sb.append(" (expected: >= 2)");
            throw new IllegalArgumentException(sb.toString());
        } else if (this.ctx == null) {
            this.maxCumulationBufferComponents = i;
        } else {
            throw new IllegalStateException("decoder properties cannot be changed once the decoder is added to a pipeline.");
        }
    }

    @Deprecated
    public final boolean isHandlingOversizedMessage() {
        return this.handlingOversizedMessage;
    }

    /* access modifiers changed from: protected */
    public final ChannelHandlerContext ctx() {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext != null) {
            return channelHandlerContext;
        }
        throw new IllegalStateException("not added to a pipeline yet");
    }

    /* access modifiers changed from: protected */
    public void decode(final ChannelHandlerContext channelHandlerContext, I i, List<Object> list) throws Exception {
        boolean z;
        ByteBufHolder byteBufHolder;
        if (isStartMessage(i)) {
            this.handlingOversizedMessage = false;
            O o = this.currentMessage;
            if (o == null) {
                Object newContinueResponse = newContinueResponse(i, this.maxContentLength, channelHandlerContext.pipeline());
                if (newContinueResponse != null) {
                    ChannelFutureListener channelFutureListener = this.continueResponseWriteListener;
                    if (channelFutureListener == null) {
                        channelFutureListener = new ChannelFutureListener() {
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                if (!channelFuture.isSuccess()) {
                                    channelHandlerContext.fireExceptionCaught(channelFuture.cause());
                                }
                            }
                        };
                        this.continueResponseWriteListener = channelFutureListener;
                    }
                    boolean closeAfterContinueResponse = closeAfterContinueResponse(newContinueResponse);
                    this.handlingOversizedMessage = ignoreContentAfterContinueResponse(newContinueResponse);
                    ChannelFuture addListener = channelHandlerContext.writeAndFlush(newContinueResponse).addListener(channelFutureListener);
                    if (closeAfterContinueResponse) {
                        addListener.addListener(ChannelFutureListener.CLOSE);
                        return;
                    } else if (this.handlingOversizedMessage) {
                        return;
                    }
                } else if (isContentLengthInvalid(i, this.maxContentLength)) {
                    invokeHandleOversizedMessage(channelHandlerContext, i);
                    return;
                }
                if (!(i instanceof DecoderResultProvider) || ((DecoderResultProvider) i).decoderResult().isSuccess()) {
                    CompositeByteBuf compositeBuffer = channelHandlerContext.alloc().compositeBuffer(this.maxCumulationBufferComponents);
                    if (i instanceof ByteBufHolder) {
                        appendPartialContent(compositeBuffer, ((ByteBufHolder) i).content());
                    }
                    this.currentMessage = beginAggregation(i, compositeBuffer);
                } else {
                    if (i instanceof ByteBufHolder) {
                        ByteBufHolder byteBufHolder2 = (ByteBufHolder) i;
                        if (byteBufHolder2.content().isReadable()) {
                            byteBufHolder = beginAggregation(i, byteBufHolder2.content().retain());
                            finishAggregation(byteBufHolder);
                            list.add(byteBufHolder);
                        }
                    }
                    byteBufHolder = beginAggregation(i, Unpooled.EMPTY_BUFFER);
                    finishAggregation(byteBufHolder);
                    list.add(byteBufHolder);
                }
            } else {
                o.release();
                this.currentMessage = null;
                throw new MessageAggregationException();
            }
        } else if (isContentMessage(i)) {
            O o2 = this.currentMessage;
            if (o2 != null) {
                CompositeByteBuf compositeByteBuf = (CompositeByteBuf) o2.content();
                ByteBufHolder byteBufHolder3 = (ByteBufHolder) i;
                if (compositeByteBuf.readableBytes() > this.maxContentLength - byteBufHolder3.content().readableBytes()) {
                    invokeHandleOversizedMessage(channelHandlerContext, this.currentMessage);
                    return;
                }
                appendPartialContent(compositeByteBuf, byteBufHolder3.content());
                aggregate(this.currentMessage, byteBufHolder3);
                if (byteBufHolder3 instanceof DecoderResultProvider) {
                    DecoderResult decoderResult = ((DecoderResultProvider) byteBufHolder3).decoderResult();
                    if (!decoderResult.isSuccess()) {
                        O o3 = this.currentMessage;
                        if (o3 instanceof DecoderResultProvider) {
                            ((DecoderResultProvider) o3).setDecoderResult(DecoderResult.failure(decoderResult.cause()));
                        }
                        z = true;
                    } else {
                        z = isLastContentMessage(byteBufHolder3);
                    }
                } else {
                    z = isLastContentMessage(byteBufHolder3);
                }
                if (z) {
                    finishAggregation(this.currentMessage);
                    list.add(this.currentMessage);
                    this.currentMessage = null;
                }
            }
        } else {
            throw new MessageAggregationException();
        }
    }

    private static void appendPartialContent(CompositeByteBuf compositeByteBuf, ByteBuf byteBuf) {
        if (byteBuf.isReadable()) {
            compositeByteBuf.addComponent(true, byteBuf.retain());
        }
    }

    private void invokeHandleOversizedMessage(ChannelHandlerContext channelHandlerContext, S s) throws Exception {
        this.handlingOversizedMessage = true;
        this.currentMessage = null;
        try {
            handleOversizedMessage(channelHandlerContext, s);
        } finally {
            ReferenceCountUtil.release(s);
        }
    }

    /* access modifiers changed from: protected */
    public void handleOversizedMessage(ChannelHandlerContext channelHandlerContext, S s) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("content length exceeded ");
        sb.append(maxContentLength());
        sb.append(" bytes.");
        channelHandlerContext.fireExceptionCaught(new TooLongFrameException(sb.toString()));
    }

    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.currentMessage != null && !channelHandlerContext.channel().config().isAutoRead()) {
            channelHandlerContext.read();
        }
        channelHandlerContext.fireChannelReadComplete();
    }

    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            super.channelInactive(channelHandlerContext);
        } finally {
            releaseCurrentMessage();
        }
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
    }

    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            super.handlerRemoved(channelHandlerContext);
        } finally {
            releaseCurrentMessage();
        }
    }

    private void releaseCurrentMessage() {
        O o = this.currentMessage;
        if (o != null) {
            o.release();
            this.currentMessage = null;
            this.handlingOversizedMessage = false;
        }
    }
}
