package p043io.netty.handler.codec.protobuf;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLite.Builder;
import com.google.protobuf.MessageLiteOrBuilder;
import java.util.List;
import p043io.netty.buffer.Unpooled;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
/* renamed from: io.netty.handler.codec.protobuf.ProtobufEncoder */
public class ProtobufEncoder extends MessageToMessageEncoder<MessageLiteOrBuilder> {
    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, MessageLiteOrBuilder messageLiteOrBuilder, List<Object> list) throws Exception {
        if (messageLiteOrBuilder instanceof MessageLite) {
            list.add(Unpooled.wrappedBuffer(((MessageLite) messageLiteOrBuilder).toByteArray()));
            return;
        }
        if (messageLiteOrBuilder instanceof Builder) {
            list.add(Unpooled.wrappedBuffer(((Builder) messageLiteOrBuilder).build().toByteArray()));
        }
    }
}
