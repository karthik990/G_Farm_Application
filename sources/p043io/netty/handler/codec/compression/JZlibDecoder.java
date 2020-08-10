package p043io.netty.handler.codec.compression;

import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.codec.compression.JZlibDecoder */
public class JZlibDecoder extends ZlibDecoder {
    private byte[] dictionary;
    private volatile boolean finished;

    /* renamed from: z */
    private final Inflater f3722z;

    public JZlibDecoder() {
        this(ZlibWrapper.ZLIB);
    }

    public JZlibDecoder(ZlibWrapper zlibWrapper) {
        this.f3722z = new Inflater();
        if (zlibWrapper != null) {
            int init = this.f3722z.init(ZlibUtil.convertWrapperType(zlibWrapper));
            if (init != 0) {
                ZlibUtil.fail(this.f3722z, "initialization failure", init);
                return;
            }
            return;
        }
        throw new NullPointerException("wrapper");
    }

    public JZlibDecoder(byte[] bArr) {
        this.f3722z = new Inflater();
        if (bArr != null) {
            this.dictionary = bArr;
            int inflateInit = this.f3722z.inflateInit(JZlib.W_ZLIB);
            if (inflateInit != 0) {
                ZlibUtil.fail(this.f3722z, "initialization failure", inflateInit);
                return;
            }
            return;
        }
        throw new NullPointerException("dictionary");
    }

    public boolean isClosed() {
        return this.finished;
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int i;
        ByteBuf heapBuffer;
        if (this.finished) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes != 0) {
            try {
                this.f3722z.avail_in = readableBytes;
                if (byteBuf.hasArray()) {
                    this.f3722z.next_in = byteBuf.array();
                    this.f3722z.next_in_index = byteBuf.arrayOffset() + byteBuf.readerIndex();
                } else {
                    byte[] bArr = new byte[readableBytes];
                    byteBuf.getBytes(byteBuf.readerIndex(), bArr);
                    this.f3722z.next_in = bArr;
                    this.f3722z.next_in_index = 0;
                }
                i = this.f3722z.next_in_index;
                heapBuffer = channelHandlerContext.alloc().heapBuffer(readableBytes << 1);
                while (true) {
                    heapBuffer.ensureWritable(this.f3722z.avail_in << 1);
                    this.f3722z.avail_out = heapBuffer.writableBytes();
                    this.f3722z.next_out = heapBuffer.array();
                    this.f3722z.next_out_index = heapBuffer.arrayOffset() + heapBuffer.writerIndex();
                    int i2 = this.f3722z.next_out_index;
                    int inflate = this.f3722z.inflate(2);
                    int i3 = this.f3722z.next_out_index - i2;
                    if (i3 > 0) {
                        heapBuffer.writerIndex(heapBuffer.writerIndex() + i3);
                    }
                    if (inflate != -5) {
                        if (inflate != 0) {
                            if (inflate == 1) {
                                this.finished = true;
                                this.f3722z.inflateEnd();
                                break;
                            }
                            String str = "decompression failure";
                            if (inflate != 2) {
                                ZlibUtil.fail(this.f3722z, str, inflate);
                            } else if (this.dictionary == null) {
                                ZlibUtil.fail(this.f3722z, str, inflate);
                            } else {
                                int inflateSetDictionary = this.f3722z.inflateSetDictionary(this.dictionary, this.dictionary.length);
                                if (inflateSetDictionary != 0) {
                                    ZlibUtil.fail(this.f3722z, "failed to set the dictionary", inflateSetDictionary);
                                }
                            }
                        } else {
                            continue;
                        }
                    } else if (this.f3722z.avail_in > 0) {
                    }
                }
                byteBuf.skipBytes(this.f3722z.next_in_index - i);
                if (heapBuffer.isReadable()) {
                    list.add(heapBuffer);
                } else {
                    heapBuffer.release();
                }
                Inflater inflater = this.f3722z;
                inflater.next_in = null;
                inflater.next_out = null;
            } catch (Throwable th) {
                Inflater inflater2 = this.f3722z;
                inflater2.next_in = null;
                inflater2.next_out = null;
                throw th;
            }
        }
    }
}
