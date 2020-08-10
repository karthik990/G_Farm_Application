package p043io.netty.handler.codec.json;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufUtil;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.ByteToMessageDecoder;
import p043io.netty.handler.codec.CorruptedFrameException;
import p043io.netty.handler.codec.TooLongFrameException;

/* renamed from: io.netty.handler.codec.json.JsonObjectDecoder */
public class JsonObjectDecoder extends ByteToMessageDecoder {
    private static final int ST_CORRUPTED = -1;
    private static final int ST_DECODING_ARRAY_STREAM = 2;
    private static final int ST_DECODING_NORMAL = 1;
    private static final int ST_INIT = 0;
    private int idx;
    private boolean insideString;
    private int lastReaderIndex;
    private final int maxObjectLength;
    private int openBraces;
    private int state;
    private final boolean streamArrayElements;

    public JsonObjectDecoder() {
        this(1048576);
    }

    public JsonObjectDecoder(int i) {
        this(i, false);
    }

    public JsonObjectDecoder(boolean z) {
        this(1048576, z);
    }

    public JsonObjectDecoder(int i, boolean z) {
        if (i >= 1) {
            this.maxObjectLength = i;
            this.streamArrayElements = z;
            return;
        }
        throw new IllegalArgumentException("maxObjectLength must be a positive int");
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.state == -1) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        if (this.idx > byteBuf.readerIndex() && this.lastReaderIndex != byteBuf.readerIndex()) {
            this.idx = byteBuf.readerIndex();
            reset();
        }
        int i = this.idx;
        int writerIndex = byteBuf.writerIndex();
        String str = ": ";
        if (writerIndex <= this.maxObjectLength) {
            while (i < writerIndex) {
                byte b = byteBuf.getByte(i);
                int i2 = this.state;
                if (i2 == 1) {
                    decodeByte(b, byteBuf, i);
                    if (this.openBraces == 0) {
                        int i3 = i + 1;
                        ByteBuf extractObject = extractObject(channelHandlerContext, byteBuf, byteBuf.readerIndex(), i3 - byteBuf.readerIndex());
                        if (extractObject != null) {
                            list.add(extractObject);
                        }
                        byteBuf.readerIndex(i3);
                        reset();
                    }
                } else if (i2 == 2) {
                    decodeByte(b, byteBuf, i);
                    if (!this.insideString && ((this.openBraces == 1 && b == 44) || (this.openBraces == 0 && b == 93))) {
                        for (int readerIndex = byteBuf.readerIndex(); Character.isWhitespace(byteBuf.getByte(readerIndex)); readerIndex++) {
                            byteBuf.skipBytes(1);
                        }
                        int i4 = i - 1;
                        while (i4 >= byteBuf.readerIndex() && Character.isWhitespace(byteBuf.getByte(i4))) {
                            i4--;
                        }
                        ByteBuf extractObject2 = extractObject(channelHandlerContext, byteBuf, byteBuf.readerIndex(), (i4 + 1) - byteBuf.readerIndex());
                        if (extractObject2 != null) {
                            list.add(extractObject2);
                        }
                        byteBuf.readerIndex(i + 1);
                        if (b == 93) {
                            reset();
                        }
                    }
                } else if (b == 123 || b == 91) {
                    initDecoding(b);
                    if (this.state == 2) {
                        byteBuf.skipBytes(1);
                    }
                } else if (Character.isWhitespace(b)) {
                    byteBuf.skipBytes(1);
                } else {
                    this.state = -1;
                    StringBuilder sb = new StringBuilder();
                    sb.append("invalid JSON received at byte position ");
                    sb.append(i);
                    sb.append(str);
                    sb.append(ByteBufUtil.hexDump(byteBuf));
                    throw new CorruptedFrameException(sb.toString());
                }
                i++;
            }
            if (byteBuf.readableBytes() == 0) {
                this.idx = 0;
            } else {
                this.idx = i;
            }
            this.lastReaderIndex = byteBuf.readerIndex();
            return;
        }
        byteBuf.skipBytes(byteBuf.readableBytes());
        reset();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("object length exceeds ");
        sb2.append(this.maxObjectLength);
        sb2.append(str);
        sb2.append(writerIndex);
        sb2.append(" bytes discarded");
        throw new TooLongFrameException(sb2.toString());
    }

    /* access modifiers changed from: protected */
    public ByteBuf extractObject(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, int i, int i2) {
        return byteBuf.retainedSlice(i, i2);
    }

    private void decodeByte(byte b, ByteBuf byteBuf, int i) {
        if ((b == 123 || b == 91) && !this.insideString) {
            this.openBraces++;
        } else if ((b == 125 || b == 93) && !this.insideString) {
            this.openBraces--;
        } else if (b != 34) {
        } else {
            if (!this.insideString) {
                this.insideString = true;
                return;
            }
            int i2 = i - 1;
            int i3 = 0;
            while (i2 >= 0 && byteBuf.getByte(i2) == 92) {
                i3++;
                i2--;
            }
            if (i3 % 2 == 0) {
                this.insideString = false;
            }
        }
    }

    private void initDecoding(byte b) {
        this.openBraces = 1;
        if (b != 91 || !this.streamArrayElements) {
            this.state = 1;
        } else {
            this.state = 2;
        }
    }

    private void reset() {
        this.insideString = false;
        this.state = 0;
        this.openBraces = 0;
    }
}
