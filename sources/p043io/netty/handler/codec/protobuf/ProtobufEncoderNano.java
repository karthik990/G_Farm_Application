package p043io.netty.handler.codec.protobuf;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
/* renamed from: io.netty.handler.codec.protobuf.ProtobufEncoderNano */
public class ProtobufEncoderNano extends MessageToMessageEncoder<MessageNano> {
    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, MessageNano messageNano, List<Object> list) throws Exception {
        int serializedSize = messageNano.getSerializedSize();
        ByteBuf heapBuffer = channelHandlerContext.alloc().heapBuffer(serializedSize, serializedSize);
        messageNano.writeTo(CodedOutputByteBufferNano.newInstance(heapBuffer.array(), heapBuffer.arrayOffset(), heapBuffer.capacity()));
        heapBuffer.writerIndex(serializedSize);
        list.add(heapBuffer);
    }
}
