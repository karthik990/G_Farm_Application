package p043io.netty.handler.codec.protobuf;

import org.objectweb.asm.Opcodes;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToByteEncoder;

@Sharable
/* renamed from: io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender */
public class ProtobufVarint32LengthFieldPrepender extends MessageToByteEncoder<ByteBuf> {
    static int computeRawVarint32Size(int i) {
        if ((i & -128) == 0) {
            return 1;
        }
        if ((i & -16384) == 0) {
            return 2;
        }
        if ((-2097152 & i) == 0) {
            return 3;
        }
        return (i & -268435456) == 0 ? 4 : 5;
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int readableBytes = byteBuf.readableBytes();
        byteBuf2.ensureWritable(computeRawVarint32Size(readableBytes) + readableBytes);
        writeRawVarint32(byteBuf2, readableBytes);
        byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
    }

    static void writeRawVarint32(ByteBuf byteBuf, int i) {
        while ((i & -128) != 0) {
            byteBuf.writeByte((i & Opcodes.LAND) | 128);
            i >>>= 7;
        }
        byteBuf.writeByte(i);
    }
}
