package p043io.netty.handler.traffic;

import com.braintreepayments.api.models.PostalAddressParser;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.objectweb.asm.Opcodes;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.traffic.TrafficCounter */
public class TrafficCounter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(TrafficCounter.class);
    final AtomicLong checkInterval = new AtomicLong(1000);
    private final AtomicLong cumulativeReadBytes = new AtomicLong();
    private final AtomicLong cumulativeWrittenBytes = new AtomicLong();
    private final AtomicLong currentReadBytes = new AtomicLong();
    private final AtomicLong currentWrittenBytes = new AtomicLong();
    final ScheduledExecutorService executor;
    private long lastCumulativeTime;
    private volatile long lastReadBytes;
    private long lastReadThroughput;
    private volatile long lastReadingTime;
    final AtomicLong lastTime = new AtomicLong();
    private long lastWriteThroughput;
    private volatile long lastWritingTime;
    private volatile long lastWrittenBytes;
    Runnable monitor;
    volatile boolean monitorActive;
    final String name;
    private long readingTime;
    private long realWriteThroughput;
    private final AtomicLong realWrittenBytes = new AtomicLong();
    volatile ScheduledFuture<?> scheduledFuture;
    final AbstractTrafficShapingHandler trafficShapingHandler;
    private long writingTime;

    /* renamed from: io.netty.handler.traffic.TrafficCounter$TrafficMonitoringTask */
    private final class TrafficMonitoringTask implements Runnable {
        private TrafficMonitoringTask() {
        }

        public void run() {
            if (TrafficCounter.this.monitorActive) {
                TrafficCounter.this.resetAccounting(TrafficCounter.milliSecondFromNano());
                if (TrafficCounter.this.trafficShapingHandler != null) {
                    TrafficCounter.this.trafficShapingHandler.doAccounting(TrafficCounter.this);
                }
                TrafficCounter trafficCounter = TrafficCounter.this;
                trafficCounter.scheduledFuture = trafficCounter.executor.schedule(this, TrafficCounter.this.checkInterval.get(), TimeUnit.MILLISECONDS);
            }
        }
    }

    public static long milliSecondFromNano() {
        return System.nanoTime() / 1000000;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0038, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void start() {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.monitorActive     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r5)
            return
        L_0x0007:
            java.util.concurrent.atomic.AtomicLong r0 = r5.lastTime     // Catch:{ all -> 0x0039 }
            long r1 = milliSecondFromNano()     // Catch:{ all -> 0x0039 }
            r0.set(r1)     // Catch:{ all -> 0x0039 }
            java.util.concurrent.atomic.AtomicLong r0 = r5.checkInterval     // Catch:{ all -> 0x0039 }
            long r0 = r0.get()     // Catch:{ all -> 0x0039 }
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 <= 0) goto L_0x0037
            java.util.concurrent.ScheduledExecutorService r2 = r5.executor     // Catch:{ all -> 0x0039 }
            if (r2 == 0) goto L_0x0037
            r2 = 1
            r5.monitorActive = r2     // Catch:{ all -> 0x0039 }
            io.netty.handler.traffic.TrafficCounter$TrafficMonitoringTask r2 = new io.netty.handler.traffic.TrafficCounter$TrafficMonitoringTask     // Catch:{ all -> 0x0039 }
            r3 = 0
            r2.<init>()     // Catch:{ all -> 0x0039 }
            r5.monitor = r2     // Catch:{ all -> 0x0039 }
            java.util.concurrent.ScheduledExecutorService r2 = r5.executor     // Catch:{ all -> 0x0039 }
            java.lang.Runnable r3 = r5.monitor     // Catch:{ all -> 0x0039 }
            java.util.concurrent.TimeUnit r4 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ all -> 0x0039 }
            java.util.concurrent.ScheduledFuture r0 = r2.schedule(r3, r0, r4)     // Catch:{ all -> 0x0039 }
            r5.scheduledFuture = r0     // Catch:{ all -> 0x0039 }
        L_0x0037:
            monitor-exit(r5)
            return
        L_0x0039:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.traffic.TrafficCounter.start():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0025, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void stop() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.monitorActive     // Catch:{ all -> 0x0026 }
            if (r0 != 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            r0 = 0
            r2.monitorActive = r0     // Catch:{ all -> 0x0026 }
            long r0 = milliSecondFromNano()     // Catch:{ all -> 0x0026 }
            r2.resetAccounting(r0)     // Catch:{ all -> 0x0026 }
            io.netty.handler.traffic.AbstractTrafficShapingHandler r0 = r2.trafficShapingHandler     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x001a
            io.netty.handler.traffic.AbstractTrafficShapingHandler r0 = r2.trafficShapingHandler     // Catch:{ all -> 0x0026 }
            r0.doAccounting(r2)     // Catch:{ all -> 0x0026 }
        L_0x001a:
            java.util.concurrent.ScheduledFuture<?> r0 = r2.scheduledFuture     // Catch:{ all -> 0x0026 }
            if (r0 == 0) goto L_0x0024
            java.util.concurrent.ScheduledFuture<?> r0 = r2.scheduledFuture     // Catch:{ all -> 0x0026 }
            r1 = 1
            r0.cancel(r1)     // Catch:{ all -> 0x0026 }
        L_0x0024:
            monitor-exit(r2)
            return
        L_0x0026:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.traffic.TrafficCounter.stop():void");
    }

    /* access modifiers changed from: 0000 */
    public synchronized void resetAccounting(long j) {
        long andSet = j - this.lastTime.getAndSet(j);
        if (andSet != 0) {
            if (logger.isDebugEnabled() && andSet > (checkInterval() << 1)) {
                InternalLogger internalLogger = logger;
                StringBuilder sb = new StringBuilder();
                sb.append("Acct schedule not ok: ");
                sb.append(andSet);
                sb.append(" > 2*");
                sb.append(checkInterval());
                sb.append(" from ");
                sb.append(this.name);
                internalLogger.debug(sb.toString());
            }
            this.lastReadBytes = this.currentReadBytes.getAndSet(0);
            this.lastWrittenBytes = this.currentWrittenBytes.getAndSet(0);
            this.lastReadThroughput = (this.lastReadBytes * 1000) / andSet;
            this.lastWriteThroughput = (this.lastWrittenBytes * 1000) / andSet;
            this.realWriteThroughput = (this.realWrittenBytes.getAndSet(0) * 1000) / andSet;
            this.lastWritingTime = Math.max(this.lastWritingTime, this.writingTime);
            this.lastReadingTime = Math.max(this.lastReadingTime, this.readingTime);
        }
    }

    public TrafficCounter(ScheduledExecutorService scheduledExecutorService, String str, long j) {
        if (str != null) {
            this.trafficShapingHandler = null;
            this.executor = scheduledExecutorService;
            this.name = str;
            init(j);
            return;
        }
        throw new NullPointerException(PostalAddressParser.USER_ADDRESS_NAME_KEY);
    }

    public TrafficCounter(AbstractTrafficShapingHandler abstractTrafficShapingHandler, ScheduledExecutorService scheduledExecutorService, String str, long j) {
        if (abstractTrafficShapingHandler == null) {
            throw new IllegalArgumentException("trafficShapingHandler");
        } else if (str != null) {
            this.trafficShapingHandler = abstractTrafficShapingHandler;
            this.executor = scheduledExecutorService;
            this.name = str;
            init(j);
        } else {
            throw new NullPointerException(PostalAddressParser.USER_ADDRESS_NAME_KEY);
        }
    }

    private void init(long j) {
        this.lastCumulativeTime = System.currentTimeMillis();
        this.writingTime = milliSecondFromNano();
        long j2 = this.writingTime;
        this.readingTime = j2;
        this.lastWritingTime = j2;
        this.lastReadingTime = j2;
        configure(j);
    }

    public void configure(long j) {
        long j2 = (j / 10) * 10;
        if (this.checkInterval.getAndSet(j2) == j2) {
            return;
        }
        if (j2 <= 0) {
            stop();
            this.lastTime.set(milliSecondFromNano());
            return;
        }
        start();
    }

    /* access modifiers changed from: 0000 */
    public void bytesRecvFlowControl(long j) {
        this.currentReadBytes.addAndGet(j);
        this.cumulativeReadBytes.addAndGet(j);
    }

    /* access modifiers changed from: 0000 */
    public void bytesWriteFlowControl(long j) {
        this.currentWrittenBytes.addAndGet(j);
        this.cumulativeWrittenBytes.addAndGet(j);
    }

    /* access modifiers changed from: 0000 */
    public void bytesRealWriteFlowControl(long j) {
        this.realWrittenBytes.addAndGet(j);
    }

    public long checkInterval() {
        return this.checkInterval.get();
    }

    public long lastReadThroughput() {
        return this.lastReadThroughput;
    }

    public long lastWriteThroughput() {
        return this.lastWriteThroughput;
    }

    public long lastReadBytes() {
        return this.lastReadBytes;
    }

    public long lastWrittenBytes() {
        return this.lastWrittenBytes;
    }

    public long currentReadBytes() {
        return this.currentReadBytes.get();
    }

    public long currentWrittenBytes() {
        return this.currentWrittenBytes.get();
    }

    public long lastTime() {
        return this.lastTime.get();
    }

    public long cumulativeWrittenBytes() {
        return this.cumulativeWrittenBytes.get();
    }

    public long cumulativeReadBytes() {
        return this.cumulativeReadBytes.get();
    }

    public long lastCumulativeTime() {
        return this.lastCumulativeTime;
    }

    public AtomicLong getRealWrittenBytes() {
        return this.realWrittenBytes;
    }

    public long getRealWriteThroughput() {
        return this.realWriteThroughput;
    }

    public void resetCumulativeTime() {
        this.lastCumulativeTime = System.currentTimeMillis();
        this.cumulativeReadBytes.set(0);
        this.cumulativeWrittenBytes.set(0);
    }

    public String name() {
        return this.name;
    }

    @Deprecated
    public long readTimeToWait(long j, long j2, long j3) {
        return readTimeToWait(j, j2, j3, milliSecondFromNano());
    }

    public long readTimeToWait(long j, long j2, long j3, long j4) {
        long j5 = j4;
        bytesRecvFlowControl(j);
        if (j == 0 || j2 == 0) {
            return 0;
        }
        long j6 = this.lastTime.get();
        long j7 = this.currentReadBytes.get();
        long j8 = this.readingTime;
        long j9 = j5 - j6;
        long j10 = this.lastReadBytes;
        long max = Math.max(this.lastReadingTime - j6, 0);
        String str = "Time: ";
        if (j9 > 10) {
            long j11 = (((1000 * j7) / j2) - j9) + max;
            if (j11 > 10) {
                if (logger.isDebugEnabled()) {
                    InternalLogger internalLogger = logger;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(j11);
                    sb.append(':');
                    sb.append(j7);
                    sb.append(':');
                    sb.append(j9);
                    sb.append(':');
                    sb.append(max);
                    internalLogger.debug(sb.toString());
                }
                if (j11 > j3 && (j5 + j11) - j8 > j3) {
                    j11 = j3;
                }
                this.readingTime = Math.max(j8, j5 + j11);
                return j11;
            }
            this.readingTime = Math.max(j8, j5);
        } else {
            long j12 = j7 + j10;
            long j13 = j9 + this.checkInterval.get();
            long j14 = (((1000 * j12) / j2) - j13) + max;
            if (j14 > 10) {
                if (logger.isDebugEnabled()) {
                    InternalLogger internalLogger2 = logger;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(j14);
                    sb2.append(':');
                    sb2.append(j12);
                    sb2.append(':');
                    sb2.append(j13);
                    sb2.append(':');
                    sb2.append(max);
                    internalLogger2.debug(sb2.toString());
                }
                if (j14 > j3 && (j5 + j14) - j8 > j3) {
                    j14 = j3;
                }
                this.readingTime = Math.max(j8, j5 + j14);
                return j14;
            }
            this.readingTime = Math.max(j8, j5);
        }
        return 0;
    }

    @Deprecated
    public long writeTimeToWait(long j, long j2, long j3) {
        return writeTimeToWait(j, j2, j3, milliSecondFromNano());
    }

    public long writeTimeToWait(long j, long j2, long j3, long j4) {
        long j5 = j4;
        bytesWriteFlowControl(j);
        if (j == 0 || j2 == 0) {
            return 0;
        }
        long j6 = this.lastTime.get();
        long j7 = this.currentWrittenBytes.get();
        long j8 = this.lastWrittenBytes;
        long j9 = this.writingTime;
        long max = Math.max(this.lastWritingTime - j6, 0);
        long j10 = j5 - j6;
        String str = "Time: ";
        if (j10 > 10) {
            long j11 = (((1000 * j7) / j2) - j10) + max;
            if (j11 > 10) {
                if (logger.isDebugEnabled()) {
                    InternalLogger internalLogger = logger;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(j11);
                    sb.append(':');
                    sb.append(j7);
                    sb.append(':');
                    sb.append(j10);
                    sb.append(':');
                    sb.append(max);
                    internalLogger.debug(sb.toString());
                }
                if (j11 > j3 && (j5 + j11) - j9 > j3) {
                    j11 = j3;
                }
                this.writingTime = Math.max(j9, j5 + j11);
                return j11;
            }
            this.writingTime = Math.max(j9, j5);
        } else {
            long j12 = j7 + j8;
            long j13 = j10 + this.checkInterval.get();
            long j14 = (((1000 * j12) / j2) - j13) + max;
            if (j14 > 10) {
                if (logger.isDebugEnabled()) {
                    InternalLogger internalLogger2 = logger;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(j14);
                    sb2.append(':');
                    sb2.append(j12);
                    sb2.append(':');
                    sb2.append(j13);
                    sb2.append(':');
                    sb2.append(max);
                    internalLogger2.debug(sb2.toString());
                }
                if (j14 > j3 && (j5 + j14) - j9 > j3) {
                    j14 = j3;
                }
                this.writingTime = Math.max(j9, j5 + j14);
                return j14;
            }
            this.writingTime = Math.max(j9, j5);
        }
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(Opcodes.IF_ACMPEQ);
        sb.append("Monitor ");
        sb.append(this.name);
        sb.append(" Current Speed Read: ");
        sb.append(this.lastReadThroughput >> 10);
        String str = " KB/s, ";
        sb.append(str);
        sb.append("Asked Write: ");
        sb.append(this.lastWriteThroughput >> 10);
        sb.append(str);
        sb.append("Real Write: ");
        sb.append(this.realWriteThroughput >> 10);
        sb.append(str);
        sb.append("Current Read: ");
        sb.append(this.currentReadBytes.get() >> 10);
        String str2 = " KB, ";
        sb.append(str2);
        sb.append("Current asked Write: ");
        sb.append(this.currentWrittenBytes.get() >> 10);
        sb.append(str2);
        sb.append("Current real Write: ");
        sb.append(this.realWrittenBytes.get() >> 10);
        sb.append(" KB");
        return sb.toString();
    }
}
