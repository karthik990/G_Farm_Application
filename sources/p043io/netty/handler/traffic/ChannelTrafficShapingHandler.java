package p043io.netty.handler.traffic;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.util.concurrent.EventExecutor;

/* renamed from: io.netty.handler.traffic.ChannelTrafficShapingHandler */
public class ChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {
    private final ArrayDeque<ToSend> messagesQueue = new ArrayDeque<>();
    private long queueSize;

    /* renamed from: io.netty.handler.traffic.ChannelTrafficShapingHandler$ToSend */
    private static final class ToSend {
        final ChannelPromise promise;
        final long relativeTimeAction;
        final Object toSend;

        private ToSend(long j, Object obj, ChannelPromise channelPromise) {
            this.relativeTimeAction = j;
            this.toSend = obj;
            this.promise = channelPromise;
        }
    }

    public ChannelTrafficShapingHandler(long j, long j2, long j3, long j4) {
        super(j, j2, j3, j4);
    }

    public ChannelTrafficShapingHandler(long j, long j2, long j3) {
        super(j, j2, j3);
    }

    public ChannelTrafficShapingHandler(long j, long j2) {
        super(j, j2);
    }

    public ChannelTrafficShapingHandler(long j) {
        super(j);
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        EventExecutor executor = channelHandlerContext.executor();
        StringBuilder sb = new StringBuilder();
        sb.append("ChannelTC");
        sb.append(channelHandlerContext.channel().hashCode());
        TrafficCounter trafficCounter = new TrafficCounter(this, executor, sb.toString(), this.checkInterval);
        setTrafficCounter(trafficCounter);
        trafficCounter.start();
        super.handlerAdded(channelHandlerContext);
    }

    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.trafficCounter.stop();
        synchronized (this) {
            if (channelHandlerContext.channel().isActive()) {
                Iterator it = this.messagesQueue.iterator();
                while (it.hasNext()) {
                    ToSend toSend = (ToSend) it.next();
                    long calculateSize = calculateSize(toSend.toSend);
                    this.trafficCounter.bytesRealWriteFlowControl(calculateSize);
                    this.queueSize -= calculateSize;
                    channelHandlerContext.write(toSend.toSend, toSend.promise);
                }
            } else {
                Iterator it2 = this.messagesQueue.iterator();
                while (it2.hasNext()) {
                    ToSend toSend2 = (ToSend) it2.next();
                    if (toSend2.toSend instanceof ByteBuf) {
                        ((ByteBuf) toSend2.toSend).release();
                    }
                }
            }
            this.messagesQueue.clear();
        }
        releaseWriteSuspended(channelHandlerContext);
        releaseReadSuspended(channelHandlerContext);
        super.handlerRemoved(channelHandlerContext);
    }

    /* access modifiers changed from: 0000 */
    public void submitWrite(ChannelHandlerContext channelHandlerContext, Object obj, long j, long j2, long j3, ChannelPromise channelPromise) {
        final ChannelHandlerContext channelHandlerContext2 = channelHandlerContext;
        long j4 = j;
        long j5 = j2;
        synchronized (this) {
            if (j5 == 0) {
                if (this.messagesQueue.isEmpty()) {
                    this.trafficCounter.bytesRealWriteFlowControl(j4);
                    channelHandlerContext2.write(obj, channelPromise);
                    return;
                }
            }
            Object obj2 = obj;
            ChannelPromise channelPromise2 = channelPromise;
            ToSend toSend = new ToSend(j5 + j3, obj, channelPromise);
            this.messagesQueue.addLast(toSend);
            this.queueSize += j4;
            ToSend toSend2 = toSend;
            checkWriteSuspend(channelHandlerContext, j2, this.queueSize);
            final long j6 = toSend2.relativeTimeAction;
            channelHandlerContext.executor().schedule((Runnable) new Runnable() {
                public void run() {
                    ChannelTrafficShapingHandler.this.sendAllValid(channelHandlerContext2, j6);
                }
            }, j5, TimeUnit.MILLISECONDS);
        }
    }

    /* access modifiers changed from: private */
    public void sendAllValid(ChannelHandlerContext channelHandlerContext, long j) {
        synchronized (this) {
            ToSend toSend = (ToSend) this.messagesQueue.pollFirst();
            while (true) {
                if (toSend != null) {
                    if (toSend.relativeTimeAction > j) {
                        this.messagesQueue.addFirst(toSend);
                        break;
                    }
                    long calculateSize = calculateSize(toSend.toSend);
                    this.trafficCounter.bytesRealWriteFlowControl(calculateSize);
                    this.queueSize -= calculateSize;
                    channelHandlerContext.write(toSend.toSend, toSend.promise);
                    toSend = (ToSend) this.messagesQueue.pollFirst();
                } else {
                    break;
                }
            }
            if (this.messagesQueue.isEmpty()) {
                releaseWriteSuspended(channelHandlerContext);
            }
        }
        channelHandlerContext.flush();
    }

    public long queueSize() {
        return this.queueSize;
    }
}
