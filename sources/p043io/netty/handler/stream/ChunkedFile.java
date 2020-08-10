package p043io.netty.handler.stream;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.stream.ChunkedFile */
public class ChunkedFile implements ChunkedInput<ByteBuf> {
    private final int chunkSize;
    private final long endOffset;
    private final RandomAccessFile file;
    private long offset;
    private final long startOffset;

    public ChunkedFile(File file2) throws IOException {
        this(file2, 8192);
    }

    public ChunkedFile(File file2, int i) throws IOException {
        this(new RandomAccessFile(file2, "r"), i);
    }

    public ChunkedFile(RandomAccessFile randomAccessFile) throws IOException {
        this(randomAccessFile, 8192);
    }

    public ChunkedFile(RandomAccessFile randomAccessFile, int i) throws IOException {
        this(randomAccessFile, 0, randomAccessFile.length(), i);
    }

    public ChunkedFile(RandomAccessFile randomAccessFile, long j, long j2, int i) throws IOException {
        if (randomAccessFile != null) {
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
                this.file = randomAccessFile;
                this.startOffset = j;
                this.offset = j;
                this.endOffset = j2 + j;
                this.chunkSize = i;
                randomAccessFile.seek(j);
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("chunkSize: ");
                sb3.append(i);
                sb3.append(" (expected: a positive integer)");
                throw new IllegalArgumentException(sb3.toString());
            }
        } else {
            throw new NullPointerException("file");
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
        return this.offset >= this.endOffset || !this.file.getChannel().isOpen();
    }

    public void close() throws Exception {
        this.file.close();
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
        ByteBuf heapBuffer = byteBufAllocator.heapBuffer(min);
        try {
            this.file.readFully(heapBuffer.array(), heapBuffer.arrayOffset(), min);
            heapBuffer.writerIndex(min);
            this.offset = j + ((long) min);
            return heapBuffer;
        } catch (Throwable th) {
            heapBuffer.release();
            throw th;
        }
    }

    public long length() {
        return this.endOffset - this.startOffset;
    }

    public long progress() {
        return this.offset - this.startOffset;
    }
}
