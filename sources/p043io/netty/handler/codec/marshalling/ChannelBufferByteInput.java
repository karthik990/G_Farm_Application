package p043io.netty.handler.codec.marshalling;

import java.io.IOException;
import org.jboss.marshalling.ByteInput;
import p043io.netty.buffer.ByteBuf;

/* renamed from: io.netty.handler.codec.marshalling.ChannelBufferByteInput */
class ChannelBufferByteInput implements ByteInput {
    private final ByteBuf buffer;

    public void close() throws IOException {
    }

    ChannelBufferByteInput(ByteBuf byteBuf) {
        this.buffer = byteBuf;
    }

    public int available() throws IOException {
        return this.buffer.readableBytes();
    }

    public int read() throws IOException {
        if (this.buffer.isReadable()) {
            return this.buffer.readByte() & 255;
        }
        return -1;
    }

    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int available = available();
        if (available == 0) {
            return -1;
        }
        int min = Math.min(available, i2);
        this.buffer.readBytes(bArr, i, min);
        return min;
    }

    public long skip(long j) throws IOException {
        long readableBytes = (long) this.buffer.readableBytes();
        if (readableBytes < j) {
            j = readableBytes;
        }
        ByteBuf byteBuf = this.buffer;
        byteBuf.readerIndex((int) (((long) byteBuf.readerIndex()) + j));
        return j;
    }
}
