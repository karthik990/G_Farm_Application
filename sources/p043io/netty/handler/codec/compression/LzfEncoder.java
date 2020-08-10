package p043io.netty.handler.codec.compression;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkEncoder;
import com.ning.compress.lzf.LZFEncoder;
import com.ning.compress.lzf.util.ChunkEncoderFactory;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToByteEncoder;

/* renamed from: io.netty.handler.codec.compression.LzfEncoder */
public class LzfEncoder extends MessageToByteEncoder<ByteBuf> {
    private static final int MIN_BLOCK_TO_COMPRESS = 16;
    private final ChunkEncoder encoder;
    private final BufferRecycler recycler;

    public LzfEncoder() {
        this(false, 65535);
    }

    public LzfEncoder(boolean z) {
        this(z, 65535);
    }

    public LzfEncoder(int i) {
        this(false, i);
    }

    public LzfEncoder(boolean z, int i) {
        ChunkEncoder chunkEncoder;
        super(false);
        if (i < 16 || i > 65535) {
            StringBuilder sb = new StringBuilder();
            sb.append("totalLength: ");
            sb.append(i);
            sb.append(" (expected: ");
            sb.append(16);
            sb.append('-');
            sb.append(65535);
            sb.append(')');
            throw new IllegalArgumentException(sb.toString());
        }
        if (z) {
            chunkEncoder = ChunkEncoderFactory.safeNonAllocatingInstance(i);
        } else {
            chunkEncoder = ChunkEncoderFactory.optimalNonAllocatingInstance(i);
        }
        this.encoder = chunkEncoder;
        this.recycler = BufferRecycler.instance();
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        byte[] bArr;
        int readableBytes = byteBuf.readableBytes();
        int readerIndex = byteBuf.readerIndex();
        int i = 0;
        if (byteBuf.hasArray()) {
            bArr = byteBuf.array();
            i = byteBuf.arrayOffset() + readerIndex;
        } else {
            bArr = this.recycler.allocInputBuffer(readableBytes);
            byteBuf.getBytes(readerIndex, bArr, 0, readableBytes);
        }
        byte[] bArr2 = bArr;
        byteBuf2.ensureWritable(LZFEncoder.estimateMaxWorkspaceSize(readableBytes));
        int arrayOffset = byteBuf2.arrayOffset() + byteBuf2.writerIndex();
        byteBuf2.writerIndex(byteBuf2.writerIndex() + (LZFEncoder.appendEncoded(this.encoder, bArr2, i, readableBytes, byteBuf2.array(), arrayOffset) - arrayOffset));
        byteBuf.skipBytes(readableBytes);
        if (!byteBuf.hasArray()) {
            this.recycler.releaseInputBuffer(bArr2);
        }
    }
}
