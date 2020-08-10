package p043io.netty.handler.codec.protobuf;

import com.google.common.base.Ascii;
import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.ByteToMessageDecoder;
import p043io.netty.handler.codec.CorruptedFrameException;

/* renamed from: io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder */
public class ProtobufVarint32FrameDecoder extends ByteToMessageDecoder {
    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        int readerIndex = byteBuf.readerIndex();
        int readRawVarint32 = readRawVarint32(byteBuf);
        if (readerIndex != byteBuf.readerIndex()) {
            if (readRawVarint32 >= 0) {
                if (byteBuf.readableBytes() < readRawVarint32) {
                    byteBuf.resetReaderIndex();
                } else {
                    list.add(byteBuf.readRetainedSlice(readRawVarint32));
                }
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("negative length: ");
            sb.append(readRawVarint32);
            throw new CorruptedFrameException(sb.toString());
        }
    }

    private static int readRawVarint32(ByteBuf byteBuf) {
        byte b;
        int i;
        if (!byteBuf.isReadable()) {
            return 0;
        }
        byteBuf.markReaderIndex();
        byte readByte = byteBuf.readByte();
        if (readByte >= 0) {
            return readByte;
        }
        byte b2 = readByte & Byte.MAX_VALUE;
        if (!byteBuf.isReadable()) {
            byteBuf.resetReaderIndex();
            return 0;
        }
        byte readByte2 = byteBuf.readByte();
        if (readByte2 >= 0) {
            i = readByte2 << 7;
        } else {
            b2 |= (readByte2 & Byte.MAX_VALUE) << 7;
            if (!byteBuf.isReadable()) {
                byteBuf.resetReaderIndex();
                return 0;
            }
            byte readByte3 = byteBuf.readByte();
            if (readByte3 >= 0) {
                i = readByte3 << Ascii.f1876SO;
            } else {
                b2 |= (readByte3 & Byte.MAX_VALUE) << Ascii.f1876SO;
                if (!byteBuf.isReadable()) {
                    byteBuf.resetReaderIndex();
                    return 0;
                }
                byte readByte4 = byteBuf.readByte();
                if (readByte4 >= 0) {
                    i = readByte4 << Ascii.NAK;
                } else {
                    byte b3 = b2 | ((readByte4 & Byte.MAX_VALUE) << Ascii.NAK);
                    if (!byteBuf.isReadable()) {
                        byteBuf.resetReaderIndex();
                        return 0;
                    }
                    byte readByte5 = byteBuf.readByte();
                    byte b4 = b3 | (readByte5 << Ascii.f1869FS);
                    if (readByte5 >= 0) {
                        b = b4;
                        return b;
                    }
                    throw new CorruptedFrameException("malformed varint.");
                }
            }
        }
        b = i | b2;
        return b;
    }
}
