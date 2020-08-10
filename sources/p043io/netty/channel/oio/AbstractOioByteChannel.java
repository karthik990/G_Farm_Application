package p043io.netty.channel.oio;

import java.io.IOException;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelMetadata;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.ChannelOutboundBuffer;
import p043io.netty.channel.ChannelPipeline;
import p043io.netty.channel.FileRegion;
import p043io.netty.channel.RecvByteBufAllocator.Handle;
import p043io.netty.channel.socket.ChannelInputShutdownEvent;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.channel.oio.AbstractOioByteChannel */
public abstract class AbstractOioByteChannel extends AbstractOioChannel {
    private static final String EXPECTED_TYPES;
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);

    /* access modifiers changed from: protected */
    public abstract int available();

    /* access modifiers changed from: protected */
    public abstract int doReadBytes(ByteBuf byteBuf) throws Exception;

    /* access modifiers changed from: protected */
    public abstract void doWriteBytes(ByteBuf byteBuf) throws Exception;

    /* access modifiers changed from: protected */
    public abstract void doWriteFileRegion(FileRegion fileRegion) throws Exception;

    /* access modifiers changed from: protected */
    public abstract boolean isInputShutdown();

    /* access modifiers changed from: protected */
    public abstract ChannelFuture shutdownInput();

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(" (expected: ");
        sb.append(StringUtil.simpleClassName(ByteBuf.class));
        sb.append(", ");
        sb.append(StringUtil.simpleClassName(FileRegion.class));
        sb.append(')');
        EXPECTED_TYPES = sb.toString();
    }

    protected AbstractOioByteChannel(Channel channel) {
        super(channel);
    }

    public ChannelMetadata metadata() {
        return METADATA;
    }

    private void closeOnRead(ChannelPipeline channelPipeline) {
        if (!isOpen()) {
            return;
        }
        if (Boolean.TRUE.equals(config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
            shutdownInput();
            channelPipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
            return;
        }
        unsafe().close(unsafe().voidPromise());
    }

    private void handleReadException(ChannelPipeline channelPipeline, ByteBuf byteBuf, Throwable th, boolean z, Handle handle) {
        if (byteBuf != null) {
            if (byteBuf.isReadable()) {
                this.readPending = false;
                channelPipeline.fireChannelRead(byteBuf);
            } else {
                byteBuf.release();
            }
        }
        handle.readComplete();
        channelPipeline.fireChannelReadComplete();
        channelPipeline.fireExceptionCaught(th);
        if (z || (th instanceof IOException)) {
            closeOnRead(channelPipeline);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003e, code lost:
        if (r6.isReadable() != false) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0040, code lost:
        r6.release();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0047, code lost:
        if (r7.lastBytesRead() >= 0) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004a, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004b, code lost:
        r6 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004d, code lost:
        r2 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004e, code lost:
        r4 = null;
        r6 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0052, code lost:
        r4 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005a, code lost:
        r4 = false;
        r8 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00cb, code lost:
        if (isActive() == false) goto L_0x00fa;
     */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00e9 A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void doRead() {
        /*
            r12 = this;
            io.netty.channel.ChannelConfig r0 = r12.config()
            boolean r1 = r12.isInputShutdown()
            if (r1 != 0) goto L_0x0112
            boolean r1 = r12.readPending
            if (r1 != 0) goto L_0x0010
            goto L_0x0112
        L_0x0010:
            r1 = 0
            r12.readPending = r1
            io.netty.channel.ChannelPipeline r3 = r12.pipeline()
            io.netty.buffer.ByteBufAllocator r2 = r0.getAllocator()
            io.netty.channel.Channel$Unsafe r4 = r12.unsafe()
            io.netty.channel.RecvByteBufAllocator$Handle r7 = r4.recvBufAllocHandle()
            r7.reset(r0)
            r4 = 1
            r5 = 0
            io.netty.buffer.ByteBuf r6 = r7.allocate(r2)     // Catch:{ all -> 0x00dc }
            r8 = 0
        L_0x002d:
            int r9 = r12.doReadBytes(r6)     // Catch:{ all -> 0x00d7 }
            r7.lastBytesRead(r9)     // Catch:{ all -> 0x00d7 }
            int r9 = r7.lastBytesRead()     // Catch:{ all -> 0x00d7 }
            if (r9 > 0) goto L_0x0054
            boolean r2 = r6.isReadable()     // Catch:{ all -> 0x00d7 }
            if (r2 != 0) goto L_0x0052
            r6.release()     // Catch:{ all -> 0x00d7 }
            int r2 = r7.lastBytesRead()     // Catch:{ all -> 0x004d }
            if (r2 >= 0) goto L_0x004a
            goto L_0x004b
        L_0x004a:
            r4 = 0
        L_0x004b:
            r6 = r5
            goto L_0x008f
        L_0x004d:
            r2 = move-exception
            r4 = r5
            r6 = 0
            goto L_0x00e0
        L_0x0052:
            r4 = 0
            goto L_0x008f
        L_0x0054:
            int r8 = r12.available()     // Catch:{ all -> 0x00d1 }
            if (r8 > 0) goto L_0x005d
        L_0x005a:
            r4 = 0
            r8 = 1
            goto L_0x008f
        L_0x005d:
            boolean r9 = r6.isWritable()     // Catch:{ all -> 0x00d1 }
            if (r9 != 0) goto L_0x0088
            int r9 = r6.capacity()     // Catch:{ all -> 0x00d1 }
            int r10 = r6.maxCapacity()     // Catch:{ all -> 0x00d1 }
            if (r9 != r10) goto L_0x007a
            r7.incMessagesRead(r4)     // Catch:{ all -> 0x00d1 }
            r12.readPending = r1     // Catch:{ all -> 0x00d1 }
            r3.fireChannelRead(r6)     // Catch:{ all -> 0x00d1 }
            io.netty.buffer.ByteBuf r6 = r7.allocate(r2)     // Catch:{ all -> 0x00d1 }
            goto L_0x0088
        L_0x007a:
            int r9 = r6.writerIndex()     // Catch:{ all -> 0x00d1 }
            int r9 = r9 + r8
            if (r9 <= r10) goto L_0x0085
            r6.capacity(r10)     // Catch:{ all -> 0x00d1 }
            goto L_0x0088
        L_0x0085:
            r6.ensureWritable(r8)     // Catch:{ all -> 0x00d1 }
        L_0x0088:
            boolean r8 = r7.continueReading()     // Catch:{ all -> 0x00d1 }
            if (r8 != 0) goto L_0x00ce
            goto L_0x005a
        L_0x008f:
            if (r6 == 0) goto L_0x00a7
            boolean r2 = r6.isReadable()     // Catch:{ all -> 0x00a1 }
            if (r2 == 0) goto L_0x009d
            r12.readPending = r1     // Catch:{ all -> 0x00a1 }
            r3.fireChannelRead(r6)     // Catch:{ all -> 0x00a1 }
            goto L_0x00a8
        L_0x009d:
            r6.release()     // Catch:{ all -> 0x00a1 }
            goto L_0x00a8
        L_0x00a1:
            r1 = move-exception
            r5 = r1
            r11 = r6
            r6 = r4
            r4 = r11
            goto L_0x00e1
        L_0x00a7:
            r5 = r6
        L_0x00a8:
            if (r8 == 0) goto L_0x00b6
            r7.readComplete()     // Catch:{ all -> 0x00b1 }
            r3.fireChannelReadComplete()     // Catch:{ all -> 0x00b1 }
            goto L_0x00b6
        L_0x00b1:
            r1 = move-exception
            r6 = r4
            r4 = r5
            r5 = r1
            goto L_0x00e1
        L_0x00b6:
            if (r4 == 0) goto L_0x00bb
            r12.closeOnRead(r3)     // Catch:{ all -> 0x00b1 }
        L_0x00bb:
            boolean r1 = r12.readPending
            if (r1 != 0) goto L_0x00f7
            boolean r0 = r0.isAutoRead()
            if (r0 != 0) goto L_0x00f7
            if (r8 != 0) goto L_0x00fa
            boolean r0 = r12.isActive()
            if (r0 == 0) goto L_0x00fa
            goto L_0x00f7
        L_0x00ce:
            r8 = 1
            goto L_0x002d
        L_0x00d1:
            r2 = move-exception
            r5 = r2
            r4 = r6
            r6 = 0
            r8 = 1
            goto L_0x00e1
        L_0x00d7:
            r2 = move-exception
            r5 = r2
            r4 = r6
            r6 = 0
            goto L_0x00e1
        L_0x00dc:
            r2 = move-exception
            r4 = r5
            r6 = 0
            r8 = 0
        L_0x00e0:
            r5 = r2
        L_0x00e1:
            r2 = r12
            r2.handleReadException(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00fb }
            boolean r1 = r12.readPending
            if (r1 != 0) goto L_0x00f7
            boolean r0 = r0.isAutoRead()
            if (r0 != 0) goto L_0x00f7
            if (r8 != 0) goto L_0x00fa
            boolean r0 = r12.isActive()
            if (r0 == 0) goto L_0x00fa
        L_0x00f7:
            r12.read()
        L_0x00fa:
            return
        L_0x00fb:
            r1 = move-exception
            boolean r2 = r12.readPending
            if (r2 != 0) goto L_0x010e
            boolean r0 = r0.isAutoRead()
            if (r0 != 0) goto L_0x010e
            if (r8 != 0) goto L_0x0111
            boolean r0 = r12.isActive()
            if (r0 == 0) goto L_0x0111
        L_0x010e:
            r12.read()
        L_0x0111:
            throw r1
        L_0x0112:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.oio.AbstractOioByteChannel.doRead():void");
    }

    /* access modifiers changed from: protected */
    public void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        while (true) {
            Object current = channelOutboundBuffer.current();
            if (current != null) {
                if (current instanceof ByteBuf) {
                    ByteBuf byteBuf = (ByteBuf) current;
                    int readableBytes = byteBuf.readableBytes();
                    while (readableBytes > 0) {
                        doWriteBytes(byteBuf);
                        int readableBytes2 = byteBuf.readableBytes();
                        channelOutboundBuffer.progress((long) (readableBytes - readableBytes2));
                        readableBytes = readableBytes2;
                    }
                    channelOutboundBuffer.remove();
                } else if (current instanceof FileRegion) {
                    FileRegion fileRegion = (FileRegion) current;
                    long transferred = fileRegion.transferred();
                    doWriteFileRegion(fileRegion);
                    channelOutboundBuffer.progress(fileRegion.transferred() - transferred);
                    channelOutboundBuffer.remove();
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("unsupported message type: ");
                    sb.append(StringUtil.simpleClassName(current));
                    channelOutboundBuffer.remove(new UnsupportedOperationException(sb.toString()));
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public final Object filterOutboundMessage(Object obj) throws Exception {
        if ((obj instanceof ByteBuf) || (obj instanceof FileRegion)) {
            return obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unsupported message type: ");
        sb.append(StringUtil.simpleClassName(obj));
        sb.append(EXPECTED_TYPES);
        throw new UnsupportedOperationException(sb.toString());
    }
}
