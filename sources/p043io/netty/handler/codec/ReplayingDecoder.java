package p043io.netty.handler.codec;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.Unpooled;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.util.Signal;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.handler.codec.ReplayingDecoder */
public abstract class ReplayingDecoder<S> extends ByteToMessageDecoder {
    static final Signal REPLAY = Signal.valueOf(ReplayingDecoder.class, "REPLAY");
    private int checkpoint;
    private final ReplayingDecoderByteBuf replayable;
    private S state;

    protected ReplayingDecoder() {
        this(null);
    }

    protected ReplayingDecoder(S s) {
        this.replayable = new ReplayingDecoderByteBuf();
        this.checkpoint = -1;
        this.state = s;
    }

    /* access modifiers changed from: protected */
    public void checkpoint() {
        this.checkpoint = internalBuffer().readerIndex();
    }

    /* access modifiers changed from: protected */
    public void checkpoint(S s) {
        checkpoint();
        state(s);
    }

    /* access modifiers changed from: protected */
    public S state() {
        return this.state;
    }

    /* access modifiers changed from: protected */
    public S state(S s) {
        S s2 = this.state;
        this.state = s;
        return s2;
    }

    /* access modifiers changed from: 0000 */
    public final void channelInputClosed(ChannelHandlerContext channelHandlerContext, List<Object> list) throws Exception {
        try {
            this.replayable.terminate();
            if (this.cumulation != null) {
                callDecode(channelHandlerContext, internalBuffer(), list);
                decodeLast(channelHandlerContext, this.replayable, list);
                return;
            }
            this.replayable.setCumulation(Unpooled.EMPTY_BUFFER);
            decodeLast(channelHandlerContext, this.replayable, list);
        } catch (Signal e) {
            e.expect(REPLAY);
        }
    }

    /* access modifiers changed from: protected */
    public void callDecode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        this.replayable.setCumulation(byteBuf);
        while (byteBuf.isReadable()) {
            try {
                int readerIndex = byteBuf.readerIndex();
                this.checkpoint = readerIndex;
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
                S s = this.state;
                int readableBytes = byteBuf.readableBytes();
                try {
                    decodeRemovalReentryProtection(channelHandlerContext, this.replayable, list);
                    if (!channelHandlerContext.isRemoved()) {
                        if (size != list.size()) {
                            if (readerIndex == byteBuf.readerIndex()) {
                                if (s == this.state) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(StringUtil.simpleClassName(getClass()));
                                    sb.append(".decode() method must consume the inbound data or change its state if it decoded something.");
                                    throw new DecoderException(sb.toString());
                                }
                            }
                            if (isSingleDecode()) {
                                return;
                            }
                        } else if (readableBytes != byteBuf.readableBytes()) {
                            continue;
                        } else if (s == this.state) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(StringUtil.simpleClassName(getClass()));
                            sb2.append(".decode() must consume the inbound data or change its state if it did not decode anything.");
                            throw new DecoderException(sb2.toString());
                        }
                    } else {
                        return;
                    }
                } catch (Signal e) {
                    e.expect(REPLAY);
                    if (!channelHandlerContext.isRemoved()) {
                        int i = this.checkpoint;
                        if (i >= 0) {
                            byteBuf.readerIndex(i);
                            return;
                        }
                        return;
                    }
                    return;
                }
            } catch (DecoderException e2) {
                throw e2;
            } catch (Throwable th) {
                throw new DecoderException(th);
            }
        }
    }
}
