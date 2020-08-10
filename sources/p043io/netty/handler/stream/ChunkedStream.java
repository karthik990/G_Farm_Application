package p043io.netty.handler.stream;

import java.io.InputStream;
import java.io.PushbackInputStream;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.stream.ChunkedStream */
public class ChunkedStream implements ChunkedInput<ByteBuf> {
    static final int DEFAULT_CHUNK_SIZE = 8192;
    private final int chunkSize;
    private boolean closed;

    /* renamed from: in */
    private final PushbackInputStream f3742in;
    private long offset;

    public long length() {
        return -1;
    }

    public ChunkedStream(InputStream inputStream) {
        this(inputStream, 8192);
    }

    public ChunkedStream(InputStream inputStream, int i) {
        if (inputStream == null) {
            throw new NullPointerException("in");
        } else if (i > 0) {
            if (inputStream instanceof PushbackInputStream) {
                this.f3742in = (PushbackInputStream) inputStream;
            } else {
                this.f3742in = new PushbackInputStream(inputStream);
            }
            this.chunkSize = i;
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
        if (this.closed) {
            return true;
        }
        int read = this.f3742in.read();
        if (read < 0) {
            return true;
        }
        this.f3742in.unread(read);
        return false;
    }

    public void close() throws Exception {
        this.closed = true;
        this.f3742in.close();
    }

    @Deprecated
    public ByteBuf readChunk(ChannelHandlerContext channelHandlerContext) throws Exception {
        return readChunk(channelHandlerContext.alloc());
    }

    public ByteBuf readChunk(ByteBufAllocator byteBufAllocator) throws Exception {
        int i;
        if (isEndOfInput()) {
            return null;
        }
        if (this.f3742in.available() <= 0) {
            i = this.chunkSize;
        } else {
            i = Math.min(this.chunkSize, this.f3742in.available());
        }
        ByteBuf buffer = byteBufAllocator.buffer(i);
        try {
            this.offset += (long) buffer.writeBytes((InputStream) this.f3742in, i);
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
