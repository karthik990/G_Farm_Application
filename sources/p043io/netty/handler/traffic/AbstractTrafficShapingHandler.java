package p043io.netty.handler.traffic;

import java.util.concurrent.TimeUnit;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufHolder;
import p043io.netty.channel.ChannelConfig;
import p043io.netty.channel.ChannelDuplexHandler;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelOutboundBuffer;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.util.Attribute;
import p043io.netty.util.AttributeKey;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;
import p043io.reactivex.annotations.SchedulerSupport;

/* renamed from: io.netty.handler.traffic.AbstractTrafficShapingHandler */
public abstract class AbstractTrafficShapingHandler extends ChannelDuplexHandler {
    static final int CHANNEL_DEFAULT_USER_DEFINED_WRITABILITY_INDEX = 1;
    public static final long DEFAULT_CHECK_INTERVAL = 1000;
    static final long DEFAULT_MAX_SIZE = 4194304;
    public static final long DEFAULT_MAX_TIME = 15000;
    static final int GLOBALCHANNEL_DEFAULT_USER_DEFINED_WRITABILITY_INDEX = 3;
    static final int GLOBAL_DEFAULT_USER_DEFINED_WRITABILITY_INDEX = 2;
    static final long MINIMAL_WAIT = 10;
    static final AttributeKey<Boolean> READ_SUSPENDED;
    static final AttributeKey<Runnable> REOPEN_TASK;
    /* access modifiers changed from: private */
    public static final InternalLogger logger;
    protected volatile long checkInterval;
    protected volatile long maxTime;
    volatile long maxWriteDelay;
    volatile long maxWriteSize;
    private volatile long readLimit;
    protected TrafficCounter trafficCounter;
    final int userDefinedWritabilityIndex;
    private volatile long writeLimit;

    /* renamed from: io.netty.handler.traffic.AbstractTrafficShapingHandler$ReopenReadTimerTask */
    static final class ReopenReadTimerTask implements Runnable {
        final ChannelHandlerContext ctx;

        ReopenReadTimerTask(ChannelHandlerContext channelHandlerContext) {
            this.ctx = channelHandlerContext;
        }

        public void run() {
            ChannelConfig config = this.ctx.channel().config();
            boolean isAutoRead = config.isAutoRead();
            Boolean valueOf = Boolean.valueOf(false);
            if (isAutoRead || !AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
                if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
                    if (!config.isAutoRead() || AbstractTrafficShapingHandler.isHandlerActive(this.ctx)) {
                        InternalLogger access$000 = AbstractTrafficShapingHandler.logger;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Normal unsuspend: ");
                        sb.append(config.isAutoRead());
                        sb.append(':');
                        sb.append(AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                        access$000.debug(sb.toString());
                    } else {
                        InternalLogger access$0002 = AbstractTrafficShapingHandler.logger;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Unsuspend: ");
                        sb2.append(config.isAutoRead());
                        sb2.append(':');
                        sb2.append(AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                        access$0002.debug(sb2.toString());
                    }
                }
                this.ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(valueOf);
                config.setAutoRead(true);
                this.ctx.channel().read();
            } else {
                if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
                    InternalLogger access$0003 = AbstractTrafficShapingHandler.logger;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Not unsuspend: ");
                    sb3.append(config.isAutoRead());
                    sb3.append(':');
                    sb3.append(AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                    access$0003.debug(sb3.toString());
                }
                this.ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(valueOf);
            }
            if (AbstractTrafficShapingHandler.logger.isDebugEnabled()) {
                InternalLogger access$0004 = AbstractTrafficShapingHandler.logger;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Unsuspend final status => ");
                sb4.append(config.isAutoRead());
                sb4.append(':');
                sb4.append(AbstractTrafficShapingHandler.isHandlerActive(this.ctx));
                access$0004.debug(sb4.toString());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public long checkWaitReadTime(ChannelHandlerContext channelHandlerContext, long j, long j2) {
        return j;
    }

    /* access modifiers changed from: protected */
    public void doAccounting(TrafficCounter trafficCounter2) {
    }

    /* access modifiers changed from: 0000 */
    public void informReadOperation(ChannelHandlerContext channelHandlerContext, long j) {
    }

    /* access modifiers changed from: 0000 */
    public abstract void submitWrite(ChannelHandlerContext channelHandlerContext, Object obj, long j, long j2, long j3, ChannelPromise channelPromise);

    /* access modifiers changed from: protected */
    public int userDefinedWritabilityIndex() {
        return 1;
    }

    static {
        Class<AbstractTrafficShapingHandler> cls = AbstractTrafficShapingHandler.class;
        logger = InternalLoggerFactory.getInstance(cls);
        StringBuilder sb = new StringBuilder();
        sb.append(cls.getName());
        sb.append(".READ_SUSPENDED");
        READ_SUSPENDED = AttributeKey.valueOf(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(cls.getName());
        sb2.append(".REOPEN_TASK");
        REOPEN_TASK = AttributeKey.valueOf(sb2.toString());
    }

    /* access modifiers changed from: 0000 */
    public void setTrafficCounter(TrafficCounter trafficCounter2) {
        this.trafficCounter = trafficCounter2;
    }

    protected AbstractTrafficShapingHandler(long j, long j2, long j3, long j4) {
        this.maxTime = DEFAULT_MAX_TIME;
        this.checkInterval = 1000;
        this.maxWriteDelay = 4000;
        this.maxWriteSize = DEFAULT_MAX_SIZE;
        if (j4 > 0) {
            this.userDefinedWritabilityIndex = userDefinedWritabilityIndex();
            this.writeLimit = j;
            this.readLimit = j2;
            this.checkInterval = j3;
            this.maxTime = j4;
            return;
        }
        throw new IllegalArgumentException("maxTime must be positive");
    }

    protected AbstractTrafficShapingHandler(long j, long j2, long j3) {
        this(j, j2, j3, DEFAULT_MAX_TIME);
    }

    protected AbstractTrafficShapingHandler(long j, long j2) {
        this(j, j2, 1000, DEFAULT_MAX_TIME);
    }

    protected AbstractTrafficShapingHandler() {
        this(0, 0, 1000, DEFAULT_MAX_TIME);
    }

    protected AbstractTrafficShapingHandler(long j) {
        this(0, 0, j, DEFAULT_MAX_TIME);
    }

    public void configure(long j, long j2, long j3) {
        configure(j, j2);
        configure(j3);
    }

    public void configure(long j, long j2) {
        this.writeLimit = j;
        this.readLimit = j2;
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.resetAccounting(TrafficCounter.milliSecondFromNano());
        }
    }

    public void configure(long j) {
        this.checkInterval = j;
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.configure(this.checkInterval);
        }
    }

    public long getWriteLimit() {
        return this.writeLimit;
    }

    public void setWriteLimit(long j) {
        this.writeLimit = j;
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.resetAccounting(TrafficCounter.milliSecondFromNano());
        }
    }

    public long getReadLimit() {
        return this.readLimit;
    }

    public void setReadLimit(long j) {
        this.readLimit = j;
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.resetAccounting(TrafficCounter.milliSecondFromNano());
        }
    }

    public long getCheckInterval() {
        return this.checkInterval;
    }

    public void setCheckInterval(long j) {
        this.checkInterval = j;
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            trafficCounter2.configure(j);
        }
    }

    public void setMaxTimeWait(long j) {
        if (j > 0) {
            this.maxTime = j;
            return;
        }
        throw new IllegalArgumentException("maxTime must be positive");
    }

    public long getMaxTimeWait() {
        return this.maxTime;
    }

    public long getMaxWriteDelay() {
        return this.maxWriteDelay;
    }

    public void setMaxWriteDelay(long j) {
        if (j > 0) {
            this.maxWriteDelay = j;
            return;
        }
        throw new IllegalArgumentException("maxWriteDelay must be positive");
    }

    public long getMaxWriteSize() {
        return this.maxWriteSize;
    }

    public void setMaxWriteSize(long j) {
        this.maxWriteSize = j;
    }

    /* access modifiers changed from: 0000 */
    public void releaseReadSuspended(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.attr(READ_SUSPENDED).set(Boolean.valueOf(false));
        channelHandlerContext.channel().config().setAutoRead(true);
    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        long calculateSize = calculateSize(obj);
        long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
        if (calculateSize > 0) {
            long j = milliSecondFromNano;
            long checkWaitReadTime = checkWaitReadTime(channelHandlerContext, this.trafficCounter.readTimeToWait(calculateSize, this.readLimit, this.maxTime, j), j);
            if (checkWaitReadTime >= MINIMAL_WAIT) {
                ChannelConfig config = channelHandlerContext.channel().config();
                if (logger.isDebugEnabled()) {
                    InternalLogger internalLogger = logger;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Read suspend: ");
                    sb.append(checkWaitReadTime);
                    sb.append(':');
                    sb.append(config.isAutoRead());
                    sb.append(':');
                    sb.append(isHandlerActive(channelHandlerContext));
                    internalLogger.debug(sb.toString());
                }
                if (config.isAutoRead() && isHandlerActive(channelHandlerContext)) {
                    config.setAutoRead(false);
                    channelHandlerContext.attr(READ_SUSPENDED).set(Boolean.valueOf(true));
                    Attribute attr = channelHandlerContext.attr(REOPEN_TASK);
                    Runnable runnable = (Runnable) attr.get();
                    if (runnable == null) {
                        runnable = new ReopenReadTimerTask(channelHandlerContext);
                        attr.set(runnable);
                    }
                    channelHandlerContext.executor().schedule(runnable, checkWaitReadTime, TimeUnit.MILLISECONDS);
                    if (logger.isDebugEnabled()) {
                        InternalLogger internalLogger2 = logger;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Suspend final status => ");
                        sb2.append(config.isAutoRead());
                        sb2.append(':');
                        sb2.append(isHandlerActive(channelHandlerContext));
                        sb2.append(" will reopened at: ");
                        sb2.append(checkWaitReadTime);
                        internalLogger2.debug(sb2.toString());
                    }
                }
            }
        }
        informReadOperation(channelHandlerContext, milliSecondFromNano);
        channelHandlerContext.fireChannelRead(obj);
    }

    protected static boolean isHandlerActive(ChannelHandlerContext channelHandlerContext) {
        Boolean bool = (Boolean) channelHandlerContext.attr(READ_SUSPENDED).get();
        return bool == null || Boolean.FALSE.equals(bool);
    }

    public void read(ChannelHandlerContext channelHandlerContext) {
        if (isHandlerActive(channelHandlerContext)) {
            channelHandlerContext.read();
        }
    }

    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        long calculateSize = calculateSize(obj);
        long milliSecondFromNano = TrafficCounter.milliSecondFromNano();
        if (calculateSize > 0) {
            long writeTimeToWait = this.trafficCounter.writeTimeToWait(calculateSize, this.writeLimit, this.maxTime, milliSecondFromNano);
            if (writeTimeToWait >= MINIMAL_WAIT) {
                if (logger.isDebugEnabled()) {
                    InternalLogger internalLogger = logger;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Write suspend: ");
                    sb.append(writeTimeToWait);
                    sb.append(':');
                    sb.append(channelHandlerContext.channel().config().isAutoRead());
                    sb.append(':');
                    sb.append(isHandlerActive(channelHandlerContext));
                    internalLogger.debug(sb.toString());
                }
                submitWrite(channelHandlerContext, obj, calculateSize, writeTimeToWait, milliSecondFromNano, channelPromise);
                return;
            }
        }
        submitWrite(channelHandlerContext, obj, calculateSize, 0, milliSecondFromNano, channelPromise);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void submitWrite(ChannelHandlerContext channelHandlerContext, Object obj, long j, ChannelPromise channelPromise) {
        submitWrite(channelHandlerContext, obj, calculateSize(obj), j, TrafficCounter.milliSecondFromNano(), channelPromise);
    }

    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        setUserDefinedWritability(channelHandlerContext, true);
        super.channelRegistered(channelHandlerContext);
    }

    /* access modifiers changed from: 0000 */
    public void setUserDefinedWritability(ChannelHandlerContext channelHandlerContext, boolean z) {
        ChannelOutboundBuffer outboundBuffer = channelHandlerContext.channel().unsafe().outboundBuffer();
        if (outboundBuffer != null) {
            outboundBuffer.setUserDefinedWritability(this.userDefinedWritabilityIndex, z);
        }
    }

    /* access modifiers changed from: 0000 */
    public void checkWriteSuspend(ChannelHandlerContext channelHandlerContext, long j, long j2) {
        if (j2 > this.maxWriteSize || j > this.maxWriteDelay) {
            setUserDefinedWritability(channelHandlerContext, false);
        }
    }

    /* access modifiers changed from: 0000 */
    public void releaseWriteSuspended(ChannelHandlerContext channelHandlerContext) {
        setUserDefinedWritability(channelHandlerContext, true);
    }

    public TrafficCounter trafficCounter() {
        return this.trafficCounter;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(290);
        sb.append("TrafficShaping with Write Limit: ");
        sb.append(this.writeLimit);
        sb.append(" Read Limit: ");
        sb.append(this.readLimit);
        sb.append(" CheckInterval: ");
        sb.append(this.checkInterval);
        sb.append(" maxDelay: ");
        sb.append(this.maxWriteDelay);
        sb.append(" maxSize: ");
        sb.append(this.maxWriteSize);
        sb.append(" and Counter: ");
        TrafficCounter trafficCounter2 = this.trafficCounter;
        if (trafficCounter2 != null) {
            sb.append(trafficCounter2);
        } else {
            sb.append(SchedulerSupport.NONE);
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public long calculateSize(Object obj) {
        int readableBytes;
        if (obj instanceof ByteBuf) {
            readableBytes = ((ByteBuf) obj).readableBytes();
        } else if (!(obj instanceof ByteBufHolder)) {
            return -1;
        } else {
            readableBytes = ((ByteBufHolder) obj).content().readableBytes();
        }
        return (long) readableBytes;
    }
}
