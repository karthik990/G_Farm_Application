package p043io.netty.handler.traffic;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* renamed from: io.netty.handler.traffic.GlobalChannelTrafficCounter */
public class GlobalChannelTrafficCounter extends TrafficCounter {

    /* renamed from: io.netty.handler.traffic.GlobalChannelTrafficCounter$MixedTrafficMonitoringTask */
    private static class MixedTrafficMonitoringTask implements Runnable {
        private final TrafficCounter counter;
        private final GlobalChannelTrafficShapingHandler trafficShapingHandler1;

        MixedTrafficMonitoringTask(GlobalChannelTrafficShapingHandler globalChannelTrafficShapingHandler, TrafficCounter trafficCounter) {
            this.trafficShapingHandler1 = globalChannelTrafficShapingHandler;
            this.counter = trafficCounter;
        }

        public void run() {
            if (this.counter.monitorActive) {
                long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
                this.counter.resetAccounting(milliSecondFromNano);
                for (PerChannel perChannel : this.trafficShapingHandler1.channelQueues.values()) {
                    perChannel.channelTrafficCounter.resetAccounting(milliSecondFromNano);
                }
                this.trafficShapingHandler1.doAccounting(this.counter);
                TrafficCounter trafficCounter = this.counter;
                trafficCounter.scheduledFuture = trafficCounter.executor.schedule(this, this.counter.checkInterval.get(), TimeUnit.MILLISECONDS);
            }
        }
    }

    public GlobalChannelTrafficCounter(GlobalChannelTrafficShapingHandler globalChannelTrafficShapingHandler, ScheduledExecutorService scheduledExecutorService, String str, long j) {
        super(globalChannelTrafficShapingHandler, scheduledExecutorService, str, j);
        if (scheduledExecutorService == null) {
            throw new IllegalArgumentException("Executor must not be null");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0037, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void start() {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.monitorActive     // Catch:{ all -> 0x0038 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r5)
            return
        L_0x0007:
            java.util.concurrent.atomic.AtomicLong r0 = r5.lastTime     // Catch:{ all -> 0x0038 }
            long r1 = milliSecondFromNano()     // Catch:{ all -> 0x0038 }
            r0.set(r1)     // Catch:{ all -> 0x0038 }
            java.util.concurrent.atomic.AtomicLong r0 = r5.checkInterval     // Catch:{ all -> 0x0038 }
            long r0 = r0.get()     // Catch:{ all -> 0x0038 }
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x0036
            r2 = 1
            r5.monitorActive = r2     // Catch:{ all -> 0x0038 }
            io.netty.handler.traffic.GlobalChannelTrafficCounter$MixedTrafficMonitoringTask r2 = new io.netty.handler.traffic.GlobalChannelTrafficCounter$MixedTrafficMonitoringTask     // Catch:{ all -> 0x0038 }
            io.netty.handler.traffic.AbstractTrafficShapingHandler r3 = r5.trafficShapingHandler     // Catch:{ all -> 0x0038 }
            io.netty.handler.traffic.GlobalChannelTrafficShapingHandler r3 = (p043io.netty.handler.traffic.GlobalChannelTrafficShapingHandler) r3     // Catch:{ all -> 0x0038 }
            r2.<init>(r3, r5)     // Catch:{ all -> 0x0038 }
            r5.monitor = r2     // Catch:{ all -> 0x0038 }
            java.util.concurrent.ScheduledExecutorService r2 = r5.executor     // Catch:{ all -> 0x0038 }
            java.lang.Runnable r3 = r5.monitor     // Catch:{ all -> 0x0038 }
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x0038 }
            java.util.concurrent.ScheduledFuture r0 = r2.schedule(r3, r0, r4)     // Catch:{ all -> 0x0038 }
            r5.scheduledFuture = r0     // Catch:{ all -> 0x0038 }
        L_0x0036:
            monitor-exit(r5)
            return
        L_0x0038:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.traffic.GlobalChannelTrafficCounter.start():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0021, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void stop() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.monitorActive     // Catch:{ all -> 0x0022 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            r0 = 0
            r2.monitorActive = r0     // Catch:{ all -> 0x0022 }
            long r0 = milliSecondFromNano()     // Catch:{ all -> 0x0022 }
            r2.resetAccounting(r0)     // Catch:{ all -> 0x0022 }
            io.netty.handler.traffic.AbstractTrafficShapingHandler r0 = r2.trafficShapingHandler     // Catch:{ all -> 0x0022 }
            r0.doAccounting(r2)     // Catch:{ all -> 0x0022 }
            java.util.concurrent.ScheduledFuture r0 = r2.scheduledFuture     // Catch:{ all -> 0x0022 }
            if (r0 == 0) goto L_0x0020
            java.util.concurrent.ScheduledFuture r0 = r2.scheduledFuture     // Catch:{ all -> 0x0022 }
            r1 = 1
            r0.cancel(r1)     // Catch:{ all -> 0x0022 }
        L_0x0020:
            monitor-exit(r2)
            return
        L_0x0022:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.traffic.GlobalChannelTrafficCounter.stop():void");
    }

    public void resetCumulativeTime() {
        for (PerChannel perChannel : ((GlobalChannelTrafficShapingHandler) this.trafficShapingHandler).channelQueues.values()) {
            perChannel.channelTrafficCounter.resetCumulativeTime();
        }
        super.resetCumulativeTime();
    }
}
