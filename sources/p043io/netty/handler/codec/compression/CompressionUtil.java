package p043io.netty.handler.codec.compression;

import java.nio.ByteBuffer;
import p043io.netty.buffer.ByteBuf;

/* renamed from: io.netty.handler.codec.compression.CompressionUtil */
final class CompressionUtil {
    private CompressionUtil() {
    }

    static void checkChecksum(ByteBufChecksum byteBufChecksum, ByteBuf byteBuf, int i) {
        byteBufChecksum.reset();
        byteBufChecksum.update(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
        int value = (int) byteBufChecksum.getValue();
        if (value != i) {
            throw new DecompressionException(String.format("stream corrupted: mismatching checksum: %d (expected: %d)", new Object[]{Integer.valueOf(value), Integer.valueOf(i)}));
        }
    }

    static ByteBuffer safeNioBuffer(ByteBuf byteBuf) {
        if (byteBuf.nioBufferCount() == 1) {
            return byteBuf.internalNioBuffer(byteBuf.readerIndex(), byteBuf.readableBytes());
        }
        return byteBuf.nioBuffer();
    }
}
