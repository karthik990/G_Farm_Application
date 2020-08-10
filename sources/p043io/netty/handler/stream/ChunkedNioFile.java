package p043io.netty.handler.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.ScatteringByteChannel;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.stream.ChunkedNioFile */
public class ChunkedNioFile implements ChunkedInput<ByteBuf> {
    private final int chunkSize;
    private final long endOffset;

    /* renamed from: in */
    private final FileChannel f3740in;
    private long offset;
    private final long startOffset;

    public ChunkedNioFile(File file) throws IOException {
        this(new FileInputStream(file).getChannel());
    }

    public ChunkedNioFile(File file, int i) throws IOException {
        this(new FileInputStream(file).getChannel(), i);
    }

    public ChunkedNioFile(FileChannel fileChannel) throws IOException {
        this(fileChannel, 8192);
    }

    public ChunkedNioFile(FileChannel fileChannel, int i) throws IOException {
        this(fileChannel, 0, fileChannel.size(), i);
    }

    public ChunkedNioFile(FileChannel fileChannel, long j, long j2, int i) throws IOException {
        if (fileChannel != null) {
            String str = " (expected: 0 or greater)";
            if (j < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("offset: ");
                sb.append(j);
                sb.append(str);
                throw new IllegalArgumentException(sb.toString());
            } else if (j2 < 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("length: ");
                sb2.append(j2);
                sb2.append(str);
                throw new IllegalArgumentException(sb2.toString());
            } else if (i > 0) {
                if (j != 0) {
                    fileChannel.position(j);
                }
                this.f3740in = fileChannel;
                this.chunkSize = i;
                this.startOffset = j;
                this.offset = j;
                this.endOffset = j + j2;
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("chunkSize: ");
                sb3.append(i);
                sb3.append(" (expected: a positive integer)");
                throw new IllegalArgumentException(sb3.toString());
            }
        } else {
            throw new NullPointerException("in");
        }
    }

    public long startOffset() {
        return this.startOffset;
    }

    public long endOffset() {
        return this.endOffset;
    }

    public long currentOffset() {
        return this.offset;
    }

    public boolean isEndOfInput() throws Exception {
        return this.offset >= this.endOffset || !this.f3740in.isOpen();
    }

    public void close() throws Exception {
        this.f3740in.close();
    }

    @Deprecated
    public ByteBuf readChunk(ChannelHandlerContext channelHandlerContext) throws Exception {
        return readChunk(channelHandlerContext.alloc());
    }

    public ByteBuf readChunk(ByteBufAllocator byteBufAllocator) throws Exception {
        long j = this.offset;
        long j2 = this.endOffset;
        if (j >= j2) {
            return null;
        }
        int min = (int) Math.min((long) this.chunkSize, j2 - j);
        ByteBuf buffer = byteBufAllocator.buffer(min);
        int i = 0;
        while (true) {
            try {
                int writeBytes = buffer.writeBytes((ScatteringByteChannel) this.f3740in, min - i);
                if (writeBytes >= 0) {
                    i += writeBytes;
                    if (i == min) {
                        break;
                    }
                } else {
                    break;
                }
            } catch (Throwable th) {
                buffer.release();
                throw th;
            }
        }
        this.offset += (long) i;
        return buffer;
    }

    public long length() {
        return this.endOffset - this.startOffset;
    }

    public long progress() {
        return this.offset - this.startOffset;
    }
}
