package p043io.netty.channel.oio;

import java.util.ArrayList;
import java.util.List;
import p043io.netty.channel.Channel;

/* renamed from: io.netty.channel.oio.AbstractOioMessageChannel */
public abstract class AbstractOioMessageChannel extends AbstractOioChannel {
    private final List<Object> readBuf = new ArrayList();

    /* access modifiers changed from: protected */
    public abstract int doReadMessages(List<Object> list) throws Exception;

    protected AbstractOioMessageChannel(Channel channel) {
        super(channel);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x007d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doRead() {
        /*
            r10 = this;
            boolean r0 = r10.readPending
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            r10.readPending = r0
            io.netty.channel.ChannelConfig r1 = r10.config()
            io.netty.channel.ChannelPipeline r2 = r10.pipeline()
            io.netty.channel.Channel$Unsafe r3 = r10.unsafe()
            io.netty.channel.RecvByteBufAllocator$Handle r3 = r3.recvBufAllocHandle()
            r3.reset(r1)
            r4 = 0
        L_0x001c:
            r5 = 1
            java.util.List<java.lang.Object> r6 = r10.readBuf     // Catch:{ all -> 0x0034 }
            int r6 = r10.doReadMessages(r6)     // Catch:{ all -> 0x0034 }
            if (r6 != 0) goto L_0x0026
            goto L_0x0033
        L_0x0026:
            if (r6 >= 0) goto L_0x002a
            r6 = 1
            goto L_0x0036
        L_0x002a:
            r3.incMessagesRead(r6)     // Catch:{ all -> 0x0034 }
            boolean r6 = r3.continueReading()     // Catch:{ all -> 0x0034 }
            if (r6 != 0) goto L_0x001c
        L_0x0033:
            goto L_0x0035
        L_0x0034:
            r4 = move-exception
        L_0x0035:
            r6 = 0
        L_0x0036:
            java.util.List<java.lang.Object> r7 = r10.readBuf
            int r7 = r7.size()
            if (r7 <= 0) goto L_0x005b
            r8 = 0
        L_0x003f:
            if (r8 >= r7) goto L_0x004f
            r10.readPending = r0
            java.util.List<java.lang.Object> r9 = r10.readBuf
            java.lang.Object r9 = r9.get(r8)
            r2.fireChannelRead(r9)
            int r8 = r8 + 1
            goto L_0x003f
        L_0x004f:
            java.util.List<java.lang.Object> r0 = r10.readBuf
            r0.clear()
            r3.readComplete()
            r2.fireChannelReadComplete()
            r0 = 1
        L_0x005b:
            if (r4 == 0) goto L_0x0065
            boolean r3 = r4 instanceof java.io.IOException
            if (r3 == 0) goto L_0x0062
            r6 = 1
        L_0x0062:
            r2.fireExceptionCaught(r4)
        L_0x0065:
            if (r6 == 0) goto L_0x007d
            boolean r0 = r10.isOpen()
            if (r0 == 0) goto L_0x0092
            io.netty.channel.Channel$Unsafe r0 = r10.unsafe()
            io.netty.channel.Channel$Unsafe r1 = r10.unsafe()
            io.netty.channel.ChannelPromise r1 = r1.voidPromise()
            r0.close(r1)
            goto L_0x0092
        L_0x007d:
            boolean r2 = r10.readPending
            if (r2 != 0) goto L_0x008f
            boolean r1 = r1.isAutoRead()
            if (r1 != 0) goto L_0x008f
            if (r0 != 0) goto L_0x0092
            boolean r0 = r10.isActive()
            if (r0 == 0) goto L_0x0092
        L_0x008f:
            r10.read()
        L_0x0092:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.oio.AbstractOioMessageChannel.doRead():void");
    }
}
