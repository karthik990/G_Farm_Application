package p043io.netty.handler.stream;

import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.stream.ChunkedNioStream */
public class ChunkedNioStream implements ChunkedInput<ByteBuf> {
    private final ByteBuffer byteBuffer;
    private final int chunkSize;

    /* renamed from: in */
    private final ReadableByteChannel f3741in;
    private long offset;

    public long length() {
        return -1;
    }

    public ChunkedNioStream(ReadableByteChannel readableByteChannel) {
        this(readableByteChannel, 8192);
    }

    public ChunkedNioStream(ReadableByteChannel readableByteChannel, int i) {
        if (readableByteChannel == null) {
            throw new NullPointerException("in");
        } else if (i > 0) {
            this.f3741in = readableByteChannel;
            this.offset = 0;
            this.chunkSize = i;
            this.byteBuffer = ByteBuffer.allocate(i);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("chunkSize: ");
            sb.append(i);
            sb.append(" (expected: a positive integer)");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public long transferredBytes() {
        return this.offset;
    }

    public boolean isEndOfInput() throws Exception {
        if (this.byteBuffer.position() > 0) {
            return false;
        }
        if (!this.f3741in.isOpen()) {
            return true;
        }
        int read = this.f3741in.read(this.byteBuffer);
        if (read < 0) {
            return true;
        }
        this.offset += (long) read;
        return false;
    }

    public void close() throws Exception {
        this.f3741in.close();
    }

    @Deprecated
    public ByteBuf readChunk(ChannelHandlerContext channelHandlerContext) throws Exception {
        return readChunk(channelHandlerContext.alloc());
    }

    public ByteBuf readChunk(ByteBufAllocator byteBufAllocator) throws Exception {
        if (isEndOfInput()) {
            return null;
        }
        int position = this.byteBuffer.position();
        do {
            int read = this.f3741in.read(this.byteBuffer);
            if (read < 0) {
                break;
            }
            position += read;
            this.offset += (long) read;
        } while (position != this.chunkSize);
        this.byteBuffer.flip();
        ByteBuf buffer = byteBufAllocator.buffer(this.byteBuffer.remaining());
        try {
            buffer.writeBytes(this.byteBuffer);
            this.byteBuffer.clear();
            return buffer;
        } catch (Throwable th) {
            buffer.release();
            throw th;
        }
    }

    public long progress() {
        return this.offset;
    }
}
