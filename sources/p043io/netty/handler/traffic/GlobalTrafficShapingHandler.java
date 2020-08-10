package p043io.netty.handler.traffic;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.internal.PlatformDependent;

@Sharable
/* renamed from: io.netty.handler.traffic.GlobalTrafficShapingHandler */
public class GlobalTrafficShapingHandler extends AbstractTrafficShapingHandler {
    private final ConcurrentMap<Integer, PerChannel> channelQueues = PlatformDependent.newConcurrentHashMap();
    long maxGlobalWriteSize = 419430400;
    private final AtomicLong queuesSize = new AtomicLong();

    /* renamed from: io.netty.handler.traffic.GlobalTrafficShapingHandler$PerChannel */
    private static final class PerChannel {
        long lastReadTimestamp;
        long lastWriteTimestamp;
        ArrayDeque<ToSend> messagesQueue;
        long queueSize;

        private PerChannel() {
        }
    }

    /* renamed from: io.netty.handler.traffic.GlobalTrafficShapingHandler$ToSend */
    private static final class ToSend {
        final ChannelPromise promise;
        final long relativeTimeAction;
        final long size;
        final Object toSend;

        private ToSend(long j, Object obj, long j2, ChannelPromise channelPromise) {
            this.relativeTimeAction = j;
            this.toSend = obj;
            this.size = j2;
            this.promise = channelPromise;
        }
    }

    /* access modifiers changed from: protected */
    public int userDefinedWritabilityIndex() {
        return 2;
    }

    /* access modifiers changed from: 0000 */
    public void createGlobalTrafficCounter(ScheduledExecutorService scheduledExecutorService) {
        if (scheduledExecutorService != null) {
            ScheduledExecutorService scheduledExecutorService2 = scheduledExecutorService;
            TrafficCounter trafficCounter = new TrafficCounter(this, scheduledExecutorService2, "GlobalTC", this.checkInterval);
            setTrafficCounter(trafficCounter);
            trafficCounter.start();
            return;
        }
        throw new NullPointerException("executor");
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long j, long j2, long j3, long j4) {
        super(j, j2, j3, j4);
        createGlobalTrafficCounter(scheduledExecutorService);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long j, long j2, long j3) {
        super(j, j2, j3);
        createGlobalTrafficCounter(scheduledExecutorService);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long j, long j2) {
        super(j, j2);
        createGlobalTrafficCounter(scheduledExecutorService);
    }

    public GlobalTrafficShapingHandler(ScheduledExecutorService scheduledExecutorService, long j) {
        super(j);
        createGlobalTrafficCounter(scheduledExecutorService);
    }

    public GlobalTrafficShapingHandler(EventExecutor eventExecutor) {
        createGlobalTrafficCounter(eventExecutor);
    }

    public long getMaxGlobalWriteSize() {
        return this.maxGlobalWriteSize;
    }

    public void setMaxGlobalWriteSize(long j) {
        this.maxGlobalWriteSize = j;
    }

    public long queuesSize() {
        return this.queuesSize.get();
    }

    public final void release() {
        this.trafficCounter.stop();
    }

    private PerChannel getOrSetPerChannel(ChannelHandlerContext channelHandlerContext) {
        Integer valueOf = Integer.valueOf(channelHandlerContext.channel().hashCode());
        PerChannel perChannel = (PerChannel) this.channelQueues.get(valueOf);
        if (perChannel != null) {
            return perChannel;
        }
        PerChannel perChannel2 = new PerChannel();
        perChannel2.messagesQueue = new ArrayDeque<>();
        perChannel2.queueSize = 0;
        perChannel2.lastReadTimestamp = TrafficCounter.milliSecondFromNano();
        perChannel2.lastWriteTimestamp = perChannel2.lastReadTimestamp;
        this.channelQueues.put(valueOf, perChannel2);
        return perChannel2;
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        getOrSetPerChannel(channelHandlerContext);
        super.handlerAdded(channelHandlerContext);
    }

    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        Channel channel = channelHandlerContext.channel();
        PerChannel perChannel = (PerChannel) this.channelQueues.remove(Integer.valueOf(channel.hashCode()));
        if (perChannel != null) {
            synchronized (perChannel) {
                if (channel.isActive()) {
                    Iterator it = perChannel.messagesQueue.iterator();
                    while (it.hasNext()) {
                        ToSend toSend = (ToSend) it.next();
                        long calculateSize = calculateSize(toSend.toSend);
                        this.trafficCounter.bytesRealWriteFlowControl(calculateSize);
                        perChannel.queueSize -= calculateSize;
                        this.queuesSize.addAndGet(-calculateSize);
                        channelHandlerContext.write(toSend.toSend, toSend.promise);
                    }
                } else {
                    this.queuesSize.addAndGet(-perChannel.queueSize);
                    Iterator it2 = perChannel.messagesQueue.iterator();
                    while (it2.hasNext()) {
                        ToSend toSend2 = (ToSend) it2.next();
                        if (toSend2.toSend instanceof ByteBuf) {
                            ((ByteBuf) toSend2.toSend).release();
                        }
                    }
                }
                perChannel.messagesQueue.clear();
            }
        }
        releaseWriteSuspended(channelHandlerContext);
        releaseReadSuspended(channelHandlerContext);
        super.handlerRemoved(channelHandlerContext);
    }

    /* access modifiers changed from: 0000 */
    public long checkWaitReadTime(ChannelHandlerContext channelHandlerContext, long j, long j2) {
        PerChannel perChannel = (PerChannel) this.channelQueues.get(Integer.valueOf(channelHandlerContext.channel().hashCode()));
        return (perChannel == null || j <= this.maxTime || (j2 + j) - perChannel.lastReadTimestamp <= this.maxTime) ? j : this.maxTime;
    }

    /* access modifiers changed from: 0000 */
    public void informReadOperation(ChannelHandlerContext channelHandlerContext, long j) {
        PerChannel perChannel = (PerChannel) this.channelQueues.get(Integer.valueOf(channelHandlerContext.channel().hashCode()));
        if (perChannel != null) {
            perChannel.lastReadTimestamp = j;
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x009a, code lost:
        if (r2 == false) goto L_0x009f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x009c, code lost:
        setUserDefinedWritability(r0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x009f, code lost:
        r2 = r2.relativeTimeAction;
        r4 = r17.executor();
        r20 = r17;
        r21 = r12;
        r22 = r2;
        r18 = new p043io.netty.handler.traffic.GlobalTrafficShapingHandler.C57631(r16);
        r4.schedule((java.lang.Runnable) r18, r13, java.util.concurrent.TimeUnit.MILLISECONDS);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b9, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void submitWrite(p043io.netty.channel.ChannelHandlerContext r17, java.lang.Object r18, long r19, long r21, long r23, p043io.netty.channel.ChannelPromise r25) {
        /*
            r16 = this;
            r1 = r16
            r0 = r17
            r10 = r19
            r2 = r23
            io.netty.channel.Channel r4 = r17.channel()
            int r4 = r4.hashCode()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            java.util.concurrent.ConcurrentMap<java.lang.Integer, io.netty.handler.traffic.GlobalTrafficShapingHandler$PerChannel> r5 = r1.channelQueues
            java.lang.Object r4 = r5.get(r4)
            io.netty.handler.traffic.GlobalTrafficShapingHandler$PerChannel r4 = (p043io.netty.handler.traffic.GlobalTrafficShapingHandler.PerChannel) r4
            if (r4 != 0) goto L_0x0022
            io.netty.handler.traffic.GlobalTrafficShapingHandler$PerChannel r4 = r16.getOrSetPerChannel(r17)
        L_0x0022:
            r12 = r4
            monitor-enter(r12)
            r4 = 0
            int r6 = (r21 > r4 ? 1 : (r21 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x0042
            java.util.ArrayDeque<io.netty.handler.traffic.GlobalTrafficShapingHandler$ToSend> r4 = r12.messagesQueue     // Catch:{ all -> 0x00ba }
            boolean r4 = r4.isEmpty()     // Catch:{ all -> 0x00ba }
            if (r4 == 0) goto L_0x0042
            io.netty.handler.traffic.TrafficCounter r4 = r1.trafficCounter     // Catch:{ all -> 0x00ba }
            r4.bytesRealWriteFlowControl(r10)     // Catch:{ all -> 0x00ba }
            r5 = r18
            r8 = r25
            r0.write(r5, r8)     // Catch:{ all -> 0x00ba }
            r12.lastWriteTimestamp = r2     // Catch:{ all -> 0x00ba }
            monitor-exit(r12)     // Catch:{ all -> 0x00ba }
            return
        L_0x0042:
            r5 = r18
            r8 = r25
            long r6 = r1.maxTime     // Catch:{ all -> 0x00ba }
            int r4 = (r21 > r6 ? 1 : (r21 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x005b
            long r6 = r2 + r21
            long r13 = r12.lastWriteTimestamp     // Catch:{ all -> 0x00ba }
            long r6 = r6 - r13
            long r13 = r1.maxTime     // Catch:{ all -> 0x00ba }
            int r4 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
            if (r4 <= 0) goto L_0x005b
            long r6 = r1.maxTime     // Catch:{ all -> 0x00ba }
            r13 = r6
            goto L_0x005d
        L_0x005b:
            r13 = r21
        L_0x005d:
            io.netty.handler.traffic.GlobalTrafficShapingHandler$ToSend r15 = new io.netty.handler.traffic.GlobalTrafficShapingHandler$ToSend     // Catch:{ all -> 0x00ba }
            long r6 = r13 + r2
            r9 = 0
            r2 = r15
            r3 = r6
            r5 = r18
            r6 = r19
            r8 = r25
            r2.<init>(r3, r5, r6, r8)     // Catch:{ all -> 0x00ba }
            java.util.ArrayDeque<io.netty.handler.traffic.GlobalTrafficShapingHandler$ToSend> r2 = r12.messagesQueue     // Catch:{ all -> 0x00ba }
            r2.addLast(r15)     // Catch:{ all -> 0x00ba }
            long r2 = r12.queueSize     // Catch:{ all -> 0x00ba }
            long r2 = r2 + r10
            r12.queueSize = r2     // Catch:{ all -> 0x00ba }
            java.util.concurrent.atomic.AtomicLong r2 = r1.queuesSize     // Catch:{ all -> 0x00ba }
            r2.addAndGet(r10)     // Catch:{ all -> 0x00ba }
            long r2 = r12.queueSize     // Catch:{ all -> 0x00ba }
            r18 = r16
            r19 = r17
            r20 = r13
            r22 = r2
            r18.checkWriteSuspend(r19, r20, r22)     // Catch:{ all -> 0x00ba }
            java.util.concurrent.atomic.AtomicLong r2 = r1.queuesSize     // Catch:{ all -> 0x00ba }
            long r2 = r2.get()     // Catch:{ all -> 0x00ba }
            long r4 = r1.maxGlobalWriteSize     // Catch:{ all -> 0x00ba }
            r6 = 0
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r7 <= 0) goto L_0x0098
            r2 = 1
            goto L_0x0099
        L_0x0098:
            r2 = 0
        L_0x0099:
            monitor-exit(r12)     // Catch:{ all -> 0x00ba }
            if (r2 == 0) goto L_0x009f
            r1.setUserDefinedWritability(r0, r6)
        L_0x009f:
            long r2 = r15.relativeTimeAction
            io.netty.util.concurrent.EventExecutor r4 = r17.executor()
            io.netty.handler.traffic.GlobalTrafficShapingHandler$1 r5 = new io.netty.handler.traffic.GlobalTrafficShapingHandler$1
            r18 = r5
            r19 = r16
            r20 = r17
            r21 = r12
            r22 = r2
            r18.<init>(r20, r21, r22)
            java.util.concurrent.TimeUnit r0 = java.util.concurrent.TimeUnit.MILLISECONDS
            r4.schedule(r5, r13, r0)
            return
        L_0x00ba:
            r0 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x00ba }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.traffic.GlobalTrafficShapingHandler.submitWrite(io.netty.channel.ChannelHandlerContext, java.lang.Object, long, long, long, io.netty.channel.ChannelPromise):void");
    }

    /* access modifiers changed from: private */
    public void sendAllValid(ChannelHandlerContext channelHandlerContext, PerChannel perChannel, long j) {
        synchronized (perChannel) {
            ToSend toSend = (ToSend) perChannel.messagesQueue.pollFirst();
            while (true) {
                if (toSend != null) {
                    if (toSend.relativeTimeAction > j) {
                        perChannel.messagesQueue.addFirst(toSend);
                        break;
                    }
                    long j2 = toSend.size;
                    this.trafficCounter.bytesRealWriteFlowControl(j2);
                    perChannel.queueSize -= j2;
                    this.queuesSize.addAndGet(-j2);
                    channelHandlerContext.write(toSend.toSend, toSend.promise);
                    perChannel.lastWriteTimestamp = j;
                    toSend = (ToSend) perChannel.messagesQueue.pollFirst();
                } else {
                    break;
                }
            }
            if (perChannel.messagesQueue.isEmpty()) {
                releaseWriteSuspended(channelHandlerContext);
            }
        }
        channelHandlerContext.flush();
    }
}
