package p043io.netty.handler.codec;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelDuplexHandler;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.util.internal.TypeParameterMatcher;

/* renamed from: io.netty.handler.codec.ByteToMessageCodec */
public abstract class ByteToMessageCodec<I> extends ChannelDuplexHandler {
    private final ByteToMessageDecoder decoder;
    private final MessageToByteEncoder<I> encoder;
    private final TypeParameterMatcher outboundMsgMatcher;

    /* renamed from: io.netty.handler.codec.ByteToMessageCodec$Encoder */
    private final class Encoder extends MessageToByteEncoder<I> {
        Encoder(boolean z) {
            super(z);
        }

        public boolean acceptOutboundMessage(Object obj) throws Exception {
            return ByteToMessageCodec.this.acceptOutboundMessage(obj);
        }

        /* access modifiers changed from: protected */
        public void encode(ChannelHandlerContext channelHandlerContext, I i, ByteBuf byteBuf) throws Exception {
            ByteToMessageCodec.this.encode(channelHandlerContext, i, byteBuf);
        }
    }

    /* access modifiers changed from: protected */
    public abstract void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception;

    /* access modifiers changed from: protected */
    public abstract void encode(ChannelHandlerContext channelHandlerContext, I i, ByteBuf byteBuf) throws Exception;

    protected ByteToMessageCodec() {
        this(true);
    }

    protected ByteToMessageCodec(Class<? extends I> cls) {
        this(cls, true);
    }

    protected ByteToMessageCodec(boolean z) {
        this.decoder = new ByteToMessageDecoder() {
            public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
                ByteToMessageCodec.this.decode(channelHandlerContext, byteBuf, list);
            }

            /* access modifiers changed from: protected */
            public void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
                ByteToMessageCodec.this.decodeLast(channelHandlerContext, byteBuf, list);
            }
        };
        ensureNotSharable();
        this.outboundMsgMatcher = TypeParameterMatcher.find(this, ByteToMessageCodec.class, "I");
        this.encoder = new Encoder(z);
    }

    protected ByteToMessageCodec(Class<? extends I> cls, boolean z) {
        this.decoder = new ByteToMessageDecoder() {
            public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
                ByteToMessageCodec.this.decode(channelHandlerContext, byteBuf, list);
            }

            /* access modifiers changed from: protected */
            public void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
                ByteToMessageCodec.this.decodeLast(channelHandlerContext, byteBuf, list);
            }
        };
        ensureNotSharable();
        this.outboundMsgMatcher = TypeParameterMatcher.get(cls);
        this.encoder = new Encoder(z);
    }

    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return this.outboundMsgMatcher.match(obj);
    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        this.decoder.channelRead(channelHandlerContext, obj);
    }

    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        this.encoder.write(channelHandlerContext, obj, channelPromise);
    }

    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.decoder.channelReadComplete(channelHandlerContext);
    }

    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.decoder.channelInactive(channelHandlerContext);
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            this.decoder.handlerAdded(channelHandlerContext);
        } finally {
            this.encoder.handlerAdded(channelHandlerContext);
        }
    }

    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            this.decoder.handlerRemoved(channelHandlerContext);
        } finally {
            this.encoder.handlerRemoved(channelHandlerContext);
        }
    }

    /* access modifiers changed from: protected */
    public void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.isReadable()) {
            decode(channelHandlerContext, byteBuf, list);
        }
    }
}
