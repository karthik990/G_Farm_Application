package p043io.netty.handler.codec.serialization;

import java.io.Serializable;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.handler.codec.MessageToByteEncoder;

@Sharable
/* renamed from: io.netty.handler.codec.serialization.ObjectEncoder */
public class ObjectEncoder extends MessageToByteEncoder<Serializable> {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0032  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void encode(p043io.netty.channel.ChannelHandlerContext r4, java.io.Serializable r5, p043io.netty.buffer.ByteBuf r6) throws java.lang.Exception {
        /*
            r3 = this;
            int r4 = r6.writerIndex()
            io.netty.buffer.ByteBufOutputStream r0 = new io.netty.buffer.ByteBufOutputStream
            r0.<init>(r6)
            r1 = 0
            byte[] r2 = LENGTH_PLACEHOLDER     // Catch:{ all -> 0x002b }
            r0.write(r2)     // Catch:{ all -> 0x002b }
            io.netty.handler.codec.serialization.CompactObjectOutputStream r2 = new io.netty.handler.codec.serialization.CompactObjectOutputStream     // Catch:{ all -> 0x002b }
            r2.<init>(r0)     // Catch:{ all -> 0x002b }
            r2.writeObject(r5)     // Catch:{ all -> 0x0028 }
            r2.flush()     // Catch:{ all -> 0x0028 }
            r2.close()
            int r5 = r6.writerIndex()
            int r5 = r5 - r4
            int r5 = r5 + -4
            r6.setInt(r4, r5)
            return
        L_0x0028:
            r4 = move-exception
            r1 = r2
            goto L_0x002c
        L_0x002b:
            r4 = move-exception
        L_0x002c:
            if (r1 == 0) goto L_0x0032
            r1.close()
            goto L_0x0035
        L_0x0032:
            r0.close()
        L_0x0035:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.serialization.ObjectEncoder.encode(io.netty.channel.ChannelHandlerContext, java.io.Serializable, io.netty.buffer.ByteBuf):void");
    }
}
