package p043io.netty.handler.codec;

import java.util.List;
import p043io.netty.channel.ChannelDuplexHandler;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.util.internal.TypeParameterMatcher;

/* renamed from: io.netty.handler.codec.MessageToMessageCodec */
public abstract class MessageToMessageCodec<INBOUND_IN, OUTBOUND_IN> extends ChannelDuplexHandler {
    private final MessageToMessageDecoder<Object> decoder = new MessageToMessageDecoder<Object>() {
        public boolean acceptInboundMessage(Object obj) throws Exception {
            return MessageToMessageCodec.this.acceptInboundMessage(obj);
        }

        /* access modifiers changed from: protected */
        public void decode(ChannelHandlerContext channelHandlerContext, Object obj, List<Object> list) throws Exception {
            MessageToMessageCodec.this.decode(channelHandlerContext, obj, list);
        }
    };
    private final MessageToMessageEncoder<Object> encoder = new MessageToMessageEncoder<Object>() {
        public boolean acceptOutboundMessage(Object obj) throws Exception {
            return MessageToMessageCodec.this.acceptOutboundMessage(obj);
        }

        /* access modifiers changed from: protected */
        public void encode(ChannelHandlerContext channelHandlerContext, Object obj, List<Object> list) throws Exception {
            MessageToMessageCodec.this.encode(channelHandlerContext, obj, list);
        }
    };
    private final TypeParameterMatcher inboundMsgMatcher;
    private final TypeParameterMatcher outboundMsgMatcher;

    /* access modifiers changed from: protected */
    public abstract void decode(ChannelHandlerContext channelHandlerContext, INBOUND_IN inbound_in, List<Object> list) throws Exception;

    /* access modifiers changed from: protected */
    public abstract void encode(ChannelHandlerContext channelHandlerContext, OUTBOUND_IN outbound_in, List<Object> list) throws Exception;

    protected MessageToMessageCodec() {
        Class<MessageToMessageCodec> cls = MessageToMessageCodec.class;
        this.inboundMsgMatcher = TypeParameterMatcher.find(this, cls, "INBOUND_IN");
        this.outboundMsgMatcher = TypeParameterMatcher.find(this, cls, "OUTBOUND_IN");
    }

    protected MessageToMessageCodec(Class<? extends INBOUND_IN> cls, Class<? extends OUTBOUND_IN> cls2) {
        this.inboundMsgMatcher = TypeParameterMatcher.get(cls);
        this.outboundMsgMatcher = TypeParameterMatcher.get(cls2);
    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        this.decoder.channelRead(channelHandlerContext, obj);
    }

    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        this.encoder.write(channelHandlerContext, obj, channelPromise);
    }

    public boolean acceptInboundMessage(Object obj) throws Exception {
        return this.inboundMsgMatcher.match(obj);
    }

    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return this.outboundMsgMatcher.match(obj);
    }
}
