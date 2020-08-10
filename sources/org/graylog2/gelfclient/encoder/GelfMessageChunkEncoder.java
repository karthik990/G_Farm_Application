package org.graylog2.gelfclient.encoder;

import com.google.common.base.Ascii;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.Unpooled;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.EncoderException;
import p043io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
public class GelfMessageChunkEncoder extends MessageToMessageEncoder<ByteBuf> {
    /* access modifiers changed from: private */
    public static final byte[] CHUNK_MAGIC_BYTES = {Ascii.f1874RS, Ascii.f1875SI};
    /* access modifiers changed from: private */
    public static final Logger LOG = LoggerFactory.getLogger(GelfMessageChunkEncoder.class);
    private static final int MAX_CHUNKS = 128;
    private static final int MAX_CHUNK_SIZE = 1420;
    private static final int MAX_MESSAGE_SIZE = 181760;
    /* access modifiers changed from: private */
    public final byte[] machineIdentifier;

    private class Chunker {
        private final byte[] messageId = generateMessageId();
        private final byte[] sequenceCount;
        private int sequenceNumber = 0;

        public Chunker(int i) {
            int i2 = i / GelfMessageChunkEncoder.MAX_CHUNK_SIZE;
            if (i % GelfMessageChunkEncoder.MAX_CHUNK_SIZE != 0) {
                i2++;
            }
            this.sequenceCount = new byte[]{(byte) i2};
        }

        public ByteBuf nextChunk(ByteBuf byteBuf) {
            int i = this.sequenceNumber;
            this.sequenceNumber = i + 1;
            byte[] bArr = {(byte) i};
            byte[] bArr2 = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bArr2);
            GelfMessageChunkEncoder.LOG.debug("nextChunk bytes magicBytes={} messageId={} sequenceNumber={} sequenceCount={} data={}", Integer.valueOf(GelfMessageChunkEncoder.CHUNK_MAGIC_BYTES.length), Integer.valueOf(this.messageId.length), Integer.valueOf(bArr.length), Integer.valueOf(this.sequenceCount.length), Integer.valueOf(bArr2.length));
            return Unpooled.copiedBuffer(GelfMessageChunkEncoder.CHUNK_MAGIC_BYTES, this.messageId, bArr, this.sequenceCount, bArr2);
        }

        private byte[] generateMessageId() {
            ByteBuf buffer = Unpooled.buffer(8, 8);
            buffer.writeInt((int) System.currentTimeMillis());
            buffer.writeBytes(GelfMessageChunkEncoder.this.machineIdentifier, 0, 4);
            return buffer.array();
        }
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        super.exceptionCaught(channelHandlerContext, th);
        LOG.error("Chunking error", th);
    }

    public GelfMessageChunkEncoder(byte[] bArr) {
        this.machineIdentifier = new byte[4];
        if (bArr.length >= 4) {
            System.arraycopy(bArr, 0, this.machineIdentifier, 0, 4);
            return;
        }
        throw new IllegalArgumentException("The machine identifier must at least be 4 bytes long.");
    }

    public GelfMessageChunkEncoder() {
        this(randomIdentifier(4));
    }

    private static byte[] randomIdentifier(int i) {
        byte[] bArr = new byte[i];
        new Random().nextBytes(bArr);
        return bArr;
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() > MAX_MESSAGE_SIZE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Message too big. ");
            sb.append(byteBuf.readableBytes());
            sb.append(" bytes (max ");
            sb.append(MAX_MESSAGE_SIZE);
            sb.append(")");
            throw new EncoderException(sb.toString());
        } else if (byteBuf.readableBytes() <= MAX_CHUNK_SIZE) {
            list.add(byteBuf.retain());
        } else {
            Chunker chunker = new Chunker(byteBuf.readableBytes());
            while (byteBuf.readableBytes() > 0) {
                try {
                    if (byteBuf.readableBytes() >= MAX_CHUNK_SIZE) {
                        list.add(chunker.nextChunk(byteBuf.readSlice(MAX_CHUNK_SIZE)));
                    } else {
                        list.add(chunker.nextChunk(byteBuf.readSlice(byteBuf.readableBytes())));
                    }
                } catch (Exception e) {
                    LOG.error("Chunk encoder error", (Throwable) e);
                    byteBuf.release();
                    return;
                }
            }
        }
    }
}
