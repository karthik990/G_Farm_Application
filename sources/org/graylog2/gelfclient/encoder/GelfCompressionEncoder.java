package org.graylog2.gelfclient.encoder;

import p043io.netty.buffer.ByteBuf;
import p043io.netty.handler.codec.MessageToMessageEncoder;

public class GelfCompressionEncoder extends MessageToMessageEncoder<ByteBuf> {
    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:12|13|14|15|16) */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0028, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        throw r3;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x002c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void encode(p043io.netty.channel.ChannelHandlerContext r2, p043io.netty.buffer.ByteBuf r3, java.util.List<java.lang.Object> r4) throws java.lang.Exception {
        /*
            r1 = this;
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream
            r2.<init>()
            java.util.zip.GZIPOutputStream r0 = new java.util.zip.GZIPOutputStream     // Catch:{ all -> 0x002d }
            r0.<init>(r2)     // Catch:{ all -> 0x002d }
            byte[] r3 = r3.array()     // Catch:{ all -> 0x0026 }
            r0.write(r3)     // Catch:{ all -> 0x0026 }
            r0.finish()     // Catch:{ all -> 0x0026 }
            byte[] r3 = r2.toByteArray()     // Catch:{ all -> 0x0026 }
            io.netty.buffer.ByteBuf r3 = p043io.netty.buffer.Unpooled.wrappedBuffer(r3)     // Catch:{ all -> 0x0026 }
            r4.add(r3)     // Catch:{ all -> 0x0026 }
            r0.close()     // Catch:{ all -> 0x002d }
            r2.close()
            return
        L_0x0026:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x0028 }
        L_0x0028:
            r3 = move-exception
            r0.close()     // Catch:{ all -> 0x002c }
        L_0x002c:
            throw r3     // Catch:{ all -> 0x002d }
        L_0x002d:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x002f }
        L_0x002f:
            r3 = move-exception
            r2.close()     // Catch:{ all -> 0x0033 }
        L_0x0033:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.graylog2.gelfclient.encoder.GelfCompressionEncoder.encode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List):void");
    }
}
