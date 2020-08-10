package p043io.netty.handler.codec.compression;

import java.util.zip.Adler32;
import java.util.zip.Checksum;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.handler.codec.MessageToByteEncoder;

/* renamed from: io.netty.handler.codec.compression.FastLzFrameEncoder */
public class FastLzFrameEncoder extends MessageToByteEncoder<ByteBuf> {
    private final Checksum checksum;
    private final int level;

    public FastLzFrameEncoder() {
        this(0, null);
    }

    public FastLzFrameEncoder(int i) {
        this(i, null);
    }

    public FastLzFrameEncoder(boolean z) {
        this(0, z ? new Adler32() : null);
    }

    public FastLzFrameEncoder(int i, Checksum checksum2) {
        super(false);
        if (i == 0 || i == 1 || i == 2) {
            this.level = i;
            this.checksum = checksum2;
            return;
        }
        throw new IllegalArgumentException(String.format("level: %d (expected: %d or %d or %d)", new Object[]{Integer.valueOf(i), Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2)}));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00df  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void encode(p043io.netty.channel.ChannelHandlerContext r19, p043io.netty.buffer.ByteBuf r20, p043io.netty.buffer.ByteBuf r21) throws java.lang.Exception {
        /*
            r18 = this;
            r0 = r18
            r1 = r20
            r2 = r21
            java.util.zip.Checksum r3 = r0.checksum
        L_0x0008:
            boolean r4 = r20.isReadable()
            if (r4 != 0) goto L_0x000f
            return
        L_0x000f:
            int r4 = r20.readerIndex()
            int r5 = r20.readableBytes()
            r6 = 65535(0xffff, float:9.1834E-41)
            int r5 = java.lang.Math.min(r5, r6)
            int r6 = r21.writerIndex()
            r7 = 4607066(0x464c5a, float:6.455875E-39)
            r2.setMedium(r6, r7)
            int r7 = r6 + 4
            r8 = 4
            if (r3 == 0) goto L_0x002f
            r9 = 4
            goto L_0x0030
        L_0x002f:
            r9 = 0
        L_0x0030:
            int r14 = r7 + r9
            r9 = 32
            if (r5 >= r9) goto L_0x0077
            int r8 = r14 + 2
            int r8 = r8 + r5
            r2.ensureWritable(r8)
            byte[] r8 = r21.array()
            int r9 = r21.arrayOffset()
            int r9 = r9 + r14
            int r9 = r9 + 2
            if (r3 == 0) goto L_0x0071
            boolean r10 = r20.hasArray()
            if (r10 == 0) goto L_0x0059
            byte[] r10 = r20.array()
            int r11 = r20.arrayOffset()
            int r4 = r4 + r11
            goto L_0x005f
        L_0x0059:
            byte[] r10 = new byte[r5]
            r1.getBytes(r4, r10)
            r4 = 0
        L_0x005f:
            r3.reset()
            r3.update(r10, r4, r5)
            long r11 = r3.getValue()
            int r12 = (int) r11
            r2.setInt(r7, r12)
            java.lang.System.arraycopy(r10, r4, r8, r9, r5)
            goto L_0x0074
        L_0x0071:
            r1.getBytes(r4, r8, r9, r5)
        L_0x0074:
            r7 = r5
            r13 = 0
            goto L_0x00d5
        L_0x0077:
            boolean r9 = r20.hasArray()
            if (r9 == 0) goto L_0x0089
            byte[] r9 = r20.array()
            int r10 = r20.arrayOffset()
            int r10 = r10 + r4
            r4 = r9
            r15 = r10
            goto L_0x0090
        L_0x0089:
            byte[] r9 = new byte[r5]
            r1.getBytes(r4, r9)
            r4 = r9
            r15 = 0
        L_0x0090:
            if (r3 == 0) goto L_0x00a0
            r3.reset()
            r3.update(r4, r15, r5)
            long r9 = r3.getValue()
            int r10 = (int) r9
            r2.setInt(r7, r10)
        L_0x00a0:
            int r7 = p043io.netty.handler.codec.compression.FastLz.calculateOutputBufferLength(r5)
            int r9 = r14 + 4
            int r9 = r9 + r7
            r2.ensureWritable(r9)
            byte[] r12 = r21.array()
            int r7 = r21.arrayOffset()
            int r7 = r7 + r14
            int r16 = r7 + 4
            int r11 = r0.level
            r7 = r4
            r8 = r15
            r9 = r5
            r10 = r12
            r17 = r11
            r11 = r16
            r13 = r12
            r12 = r17
            int r7 = p043io.netty.handler.codec.compression.FastLz.compress(r7, r8, r9, r10, r11, r12)
            if (r7 >= r5) goto L_0x00cf
            r13 = 1
            r2.setShort(r14, r7)
            int r14 = r14 + 2
            goto L_0x00d5
        L_0x00cf:
            int r7 = r16 + -2
            java.lang.System.arraycopy(r4, r15, r13, r7, r5)
            goto L_0x0074
        L_0x00d5:
            r2.setShort(r14, r5)
            int r6 = r6 + 3
            if (r3 == 0) goto L_0x00df
            r4 = 16
            goto L_0x00e0
        L_0x00df:
            r4 = 0
        L_0x00e0:
            r4 = r4 | r13
            r2.setByte(r6, r4)
            int r14 = r14 + 2
            int r14 = r14 + r7
            r2.writerIndex(r14)
            r1.skipBytes(r5)
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.FastLzFrameEncoder.encode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf):void");
    }
}
