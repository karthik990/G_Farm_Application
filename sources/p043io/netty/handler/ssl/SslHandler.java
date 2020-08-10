package p043io.netty.handler.ssl;

import androidx.work.WorkRequest;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import javax.net.ssl.SSLEngineResult.Status;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.buffer.ByteBufUtil;
import p043io.netty.buffer.Unpooled;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelOutboundHandler;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.channel.ChannelPromiseNotifier;
import p043io.netty.channel.PendingWriteQueue;
import p043io.netty.handler.codec.ByteToMessageDecoder;
import p043io.netty.handler.codec.ByteToMessageDecoder.Cumulator;
import p043io.netty.handler.codec.UnsupportedMessageTypeException;
import p043io.netty.util.ReferenceCounted;
import p043io.netty.util.concurrent.DefaultPromise;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.FutureListener;
import p043io.netty.util.concurrent.GenericFutureListener;
import p043io.netty.util.concurrent.ImmediateExecutor;
import p043io.netty.util.concurrent.Promise;
import p043io.netty.util.concurrent.ScheduledFuture;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.ThrowableUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.handler.ssl.SslHandler */
public class SslHandler extends ByteToMessageDecoder implements ChannelOutboundHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final ClosedChannelException CHANNEL_CLOSED;
    /* access modifiers changed from: private */
    public static final SSLException HANDSHAKE_TIMED_OUT;
    private static final Pattern IGNORABLE_CLASS_IN_STACK = Pattern.compile("^.*(?:Socket|Datagram|Sctp|Udt)Channel.*$");
    private static final Pattern IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*(?:reset|closed|abort|broken)|broken.*pipe).*$", 2);
    private static final SSLException SSLENGINE_CLOSED;
    /* access modifiers changed from: private */
    public static final InternalLogger logger;
    private volatile long closeNotifyFlushTimeoutMillis;
    /* access modifiers changed from: private */
    public volatile long closeNotifyReadTimeoutMillis;
    /* access modifiers changed from: private */
    public volatile ChannelHandlerContext ctx;
    private final Executor delegatedTaskExecutor;
    /* access modifiers changed from: private */
    public final SSLEngine engine;
    private final SslEngineType engineType;
    private boolean firedChannelRead;
    private boolean flushedBeforeHandshake;
    private Promise<Channel> handshakePromise;
    private volatile long handshakeTimeoutMillis;
    /* access modifiers changed from: private */
    public final int maxPacketBufferSize;
    private boolean needsFlush;
    /* access modifiers changed from: private */
    public boolean outboundClosed;
    private int packetLength;
    private PendingWriteQueue pendingUnencryptedWrites;
    private boolean readDuringHandshake;
    private boolean sentFirstMessage;
    /* access modifiers changed from: private */
    public final ByteBuffer[] singleBuffer;
    /* access modifiers changed from: private */
    public final LazyChannelPromise sslClosePromise;
    private final boolean startTls;

    /* renamed from: io.netty.handler.ssl.SslHandler$9 */
    static /* synthetic */ class C57449 {
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = new int[HandshakeStatus.values().length];
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$Status = new int[Status.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|(2:1|2)|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|18|20) */
        /* JADX WARNING: Can't wrap try/catch for region: R(17:0|(2:1|2)|3|5|6|7|9|10|11|12|13|14|15|16|17|18|20) */
        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|1|2|3|5|6|7|9|10|11|12|13|14|15|16|17|18|20) */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0032 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x003c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0047 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0052 */
        static {
            /*
                javax.net.ssl.SSLEngineResult$Status[] r0 = javax.net.ssl.SSLEngineResult.Status.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status = r0
                r0 = 1
                int[] r1 = $SwitchMap$javax$net$ssl$SSLEngineResult$Status     // Catch:{ NoSuchFieldError -> 0x0014 }
                javax.net.ssl.SSLEngineResult$Status r2 = javax.net.ssl.SSLEngineResult.Status.BUFFER_OVERFLOW     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = $SwitchMap$javax$net$ssl$SSLEngineResult$Status     // Catch:{ NoSuchFieldError -> 0x001f }
                javax.net.ssl.SSLEngineResult$Status r3 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                javax.net.ssl.SSLEngineResult$HandshakeStatus[] r2 = javax.net.ssl.SSLEngineResult.HandshakeStatus.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = r2
                int[] r2 = $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ NoSuchFieldError -> 0x0032 }
                javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_TASK     // Catch:{ NoSuchFieldError -> 0x0032 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0032 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0032 }
            L_0x0032:
                int[] r0 = $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ NoSuchFieldError -> 0x003c }
                javax.net.ssl.SSLEngineResult$HandshakeStatus r2 = javax.net.ssl.SSLEngineResult.HandshakeStatus.FINISHED     // Catch:{ NoSuchFieldError -> 0x003c }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x003c }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x003c }
            L_0x003c:
                int[] r0 = $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ NoSuchFieldError -> 0x0047 }
                javax.net.ssl.SSLEngineResult$HandshakeStatus r1 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING     // Catch:{ NoSuchFieldError -> 0x0047 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0047 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0047 }
            L_0x0047:
                int[] r0 = $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ NoSuchFieldError -> 0x0052 }
                javax.net.ssl.SSLEngineResult$HandshakeStatus r1 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_WRAP     // Catch:{ NoSuchFieldError -> 0x0052 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0052 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0052 }
            L_0x0052:
                int[] r0 = $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ NoSuchFieldError -> 0x005d }
                javax.net.ssl.SSLEngineResult$HandshakeStatus r1 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP     // Catch:{ NoSuchFieldError -> 0x005d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x005d }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x005d }
            L_0x005d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.SslHandler.C57449.<clinit>():void");
        }
    }

    /* renamed from: io.netty.handler.ssl.SslHandler$LazyChannelPromise */
    private final class LazyChannelPromise extends DefaultPromise<Channel> {
        private LazyChannelPromise() {
        }

        /* access modifiers changed from: protected */
        public EventExecutor executor() {
            if (SslHandler.this.ctx != null) {
                return SslHandler.this.ctx.executor();
            }
            throw new IllegalStateException();
        }

        /* access modifiers changed from: protected */
        public void checkDeadLock() {
            if (SslHandler.this.ctx != null) {
                super.checkDeadLock();
            }
        }
    }

    /* renamed from: io.netty.handler.ssl.SslHandler$SslEngineType */
    private enum SslEngineType {
        TCNATIVE(true, ByteToMessageDecoder.COMPOSITE_CUMULATOR) {
            /* access modifiers changed from: 0000 */
            public SSLEngineResult unwrap(SslHandler sslHandler, ByteBuf byteBuf, int i, int i2, ByteBuf byteBuf2) throws SSLException {
                SSLEngineResult sSLEngineResult;
                int nioBufferCount = byteBuf.nioBufferCount();
                int writerIndex = byteBuf2.writerIndex();
                if (nioBufferCount > 1) {
                    ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = (ReferenceCountedOpenSslEngine) sslHandler.engine;
                    try {
                        sslHandler.singleBuffer[0] = SslHandler.toByteBuffer(byteBuf2, writerIndex, byteBuf2.writableBytes());
                        sSLEngineResult = referenceCountedOpenSslEngine.unwrap(byteBuf.nioBuffers(i, i2), sslHandler.singleBuffer);
                    } finally {
                        sslHandler.singleBuffer[0] = null;
                    }
                } else {
                    sSLEngineResult = sslHandler.engine.unwrap(SslHandler.toByteBuffer(byteBuf, i, i2), SslHandler.toByteBuffer(byteBuf2, writerIndex, byteBuf2.writableBytes()));
                }
                byteBuf2.writerIndex(writerIndex + sSLEngineResult.bytesProduced());
                return sSLEngineResult;
            }

            /* access modifiers changed from: 0000 */
            public int calculateWrapBufferCapacity(SslHandler sslHandler, int i, int i2) {
                return ReferenceCountedOpenSslEngine.calculateOutNetBufSize(i, i2);
            }
        },
        CONSCRYPT(true, ByteToMessageDecoder.COMPOSITE_CUMULATOR) {
            /* access modifiers changed from: 0000 */
            public SSLEngineResult unwrap(SslHandler sslHandler, ByteBuf byteBuf, int i, int i2, ByteBuf byteBuf2) throws SSLException {
                SSLEngineResult sSLEngineResult;
                int nioBufferCount = byteBuf.nioBufferCount();
                int writerIndex = byteBuf2.writerIndex();
                if (nioBufferCount > 1) {
                    try {
                        sslHandler.singleBuffer[0] = SslHandler.toByteBuffer(byteBuf2, writerIndex, byteBuf2.writableBytes());
                        sSLEngineResult = ((ConscryptAlpnSslEngine) sslHandler.engine).unwrap(byteBuf.nioBuffers(i, i2), sslHandler.singleBuffer);
                    } finally {
                        sslHandler.singleBuffer[0] = null;
                    }
                } else {
                    sSLEngineResult = sslHandler.engine.unwrap(SslHandler.toByteBuffer(byteBuf, i, i2), SslHandler.toByteBuffer(byteBuf2, writerIndex, byteBuf2.writableBytes()));
                }
                byteBuf2.writerIndex(writerIndex + sSLEngineResult.bytesProduced());
                return sSLEngineResult;
            }

            /* access modifiers changed from: 0000 */
            public int calculateWrapBufferCapacity(SslHandler sslHandler, int i, int i2) {
                return ((ConscryptAlpnSslEngine) sslHandler.engine).calculateOutNetBufSize(i, i2);
            }
        },
        JDK(false, ByteToMessageDecoder.MERGE_CUMULATOR) {
            /* access modifiers changed from: 0000 */
            public SSLEngineResult unwrap(SslHandler sslHandler, ByteBuf byteBuf, int i, int i2, ByteBuf byteBuf2) throws SSLException {
                int writerIndex = byteBuf2.writerIndex();
                SSLEngineResult unwrap = sslHandler.engine.unwrap(SslHandler.toByteBuffer(byteBuf, i, i2), SslHandler.toByteBuffer(byteBuf2, writerIndex, byteBuf2.writableBytes()));
                byteBuf2.writerIndex(writerIndex + unwrap.bytesProduced());
                return unwrap;
            }

            /* access modifiers changed from: 0000 */
            public int calculateWrapBufferCapacity(SslHandler sslHandler, int i, int i2) {
                return sslHandler.maxPacketBufferSize;
            }
        };
        
        final Cumulator cumulator;
        final boolean wantsDirectBuffer;

        /* access modifiers changed from: 0000 */
        public abstract int calculateWrapBufferCapacity(SslHandler sslHandler, int i, int i2);

        /* access modifiers changed from: 0000 */
        public abstract SSLEngineResult unwrap(SslHandler sslHandler, ByteBuf byteBuf, int i, int i2, ByteBuf byteBuf2) throws SSLException;

        static SslEngineType forEngine(SSLEngine sSLEngine) {
            if (sSLEngine instanceof ReferenceCountedOpenSslEngine) {
                return TCNATIVE;
            }
            if (sSLEngine instanceof ConscryptAlpnSslEngine) {
                return CONSCRYPT;
            }
            return JDK;
        }

        private SslEngineType(boolean z, Cumulator cumulator2) {
            this.wantsDirectBuffer = z;
            this.cumulator = cumulator2;
        }
    }

    static {
        Class<SslHandler> cls = SslHandler.class;
        logger = InternalLoggerFactory.getInstance(cls);
        SSLENGINE_CLOSED = (SSLException) ThrowableUtil.unknownStackTrace(new SSLException("SSLEngine closed already"), cls, "wrap(...)");
        HANDSHAKE_TIMED_OUT = (SSLException) ThrowableUtil.unknownStackTrace(new SSLException("handshake timed out"), cls, "handshake(...)");
        CHANNEL_CLOSED = (ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), cls, "channelInactive(...)");
    }

    public SslHandler(SSLEngine sSLEngine) {
        this(sSLEngine, false);
    }

    public SslHandler(SSLEngine sSLEngine, boolean z) {
        this(sSLEngine, z, ImmediateExecutor.INSTANCE);
    }

    @Deprecated
    public SslHandler(SSLEngine sSLEngine, Executor executor) {
        this(sSLEngine, false, executor);
    }

    @Deprecated
    public SslHandler(SSLEngine sSLEngine, boolean z, Executor executor) {
        this.singleBuffer = new ByteBuffer[1];
        this.handshakePromise = new LazyChannelPromise();
        this.sslClosePromise = new LazyChannelPromise();
        this.handshakeTimeoutMillis = WorkRequest.MIN_BACKOFF_MILLIS;
        this.closeNotifyFlushTimeoutMillis = 3000;
        if (sSLEngine == null) {
            throw new NullPointerException("engine");
        } else if (executor != null) {
            this.engine = sSLEngine;
            this.engineType = SslEngineType.forEngine(sSLEngine);
            this.delegatedTaskExecutor = executor;
            this.startTls = z;
            this.maxPacketBufferSize = sSLEngine.getSession().getPacketBufferSize();
            setCumulator(this.engineType.cumulator);
        } else {
            throw new NullPointerException("delegatedTaskExecutor");
        }
    }

    public long getHandshakeTimeoutMillis() {
        return this.handshakeTimeoutMillis;
    }

    public void setHandshakeTimeout(long j, TimeUnit timeUnit) {
        if (timeUnit != null) {
            setHandshakeTimeoutMillis(timeUnit.toMillis(j));
            return;
        }
        throw new NullPointerException("unit");
    }

    public void setHandshakeTimeoutMillis(long j) {
        if (j >= 0) {
            this.handshakeTimeoutMillis = j;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("handshakeTimeoutMillis: ");
        sb.append(j);
        sb.append(" (expected: >= 0)");
        throw new IllegalArgumentException(sb.toString());
    }

    @Deprecated
    public long getCloseNotifyTimeoutMillis() {
        return getCloseNotifyFlushTimeoutMillis();
    }

    @Deprecated
    public void setCloseNotifyTimeout(long j, TimeUnit timeUnit) {
        setCloseNotifyFlushTimeout(j, timeUnit);
    }

    @Deprecated
    public void setCloseNotifyTimeoutMillis(long j) {
        setCloseNotifyFlushTimeoutMillis(j);
    }

    public final long getCloseNotifyFlushTimeoutMillis() {
        return this.closeNotifyFlushTimeoutMillis;
    }

    public final void setCloseNotifyFlushTimeout(long j, TimeUnit timeUnit) {
        setCloseNotifyFlushTimeoutMillis(timeUnit.toMillis(j));
    }

    public final void setCloseNotifyFlushTimeoutMillis(long j) {
        if (j >= 0) {
            this.closeNotifyFlushTimeoutMillis = j;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("closeNotifyFlushTimeoutMillis: ");
        sb.append(j);
        sb.append(" (expected: >= 0)");
        throw new IllegalArgumentException(sb.toString());
    }

    public final long getCloseNotifyReadTimeoutMillis() {
        return this.closeNotifyReadTimeoutMillis;
    }

    public final void setCloseNotifyReadTimeout(long j, TimeUnit timeUnit) {
        setCloseNotifyReadTimeoutMillis(timeUnit.toMillis(j));
    }

    public final void setCloseNotifyReadTimeoutMillis(long j) {
        if (j >= 0) {
            this.closeNotifyReadTimeoutMillis = j;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("closeNotifyReadTimeoutMillis: ");
        sb.append(j);
        sb.append(" (expected: >= 0)");
        throw new IllegalArgumentException(sb.toString());
    }

    public SSLEngine engine() {
        return this.engine;
    }

    public String applicationProtocol() {
        SSLSession session = engine().getSession();
        if (!(session instanceof ApplicationProtocolAccessor)) {
            return null;
        }
        return ((ApplicationProtocolAccessor) session).getApplicationProtocol();
    }

    public Future<Channel> handshakeFuture() {
        return this.handshakePromise;
    }

    @Deprecated
    public ChannelFuture close() {
        return close(this.ctx.newPromise());
    }

    @Deprecated
    public ChannelFuture close(final ChannelPromise channelPromise) {
        final ChannelHandlerContext channelHandlerContext = this.ctx;
        channelHandlerContext.executor().execute(new Runnable() {
            public void run() {
                SslHandler.this.outboundClosed = true;
                SslHandler.this.engine.closeOutbound();
                try {
                    SslHandler.this.flush(channelHandlerContext, channelPromise);
                } catch (Exception e) {
                    if (!channelPromise.tryFailure(e)) {
                        SslHandler.logger.warn("{} flush() raised a masked exception.", channelHandlerContext.channel(), e);
                    }
                }
            }
        });
        return channelPromise;
    }

    public Future<Channel> sslCloseFuture() {
        return this.sslClosePromise;
    }

    public void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.pendingUnencryptedWrites.isEmpty()) {
            this.pendingUnencryptedWrites.removeAndFailAll(new ChannelException("Pending write on removal of SslHandler"));
        }
        SSLEngine sSLEngine = this.engine;
        if (sSLEngine instanceof ReferenceCounted) {
            ((ReferenceCounted) sSLEngine).release();
        }
    }

    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }

    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        closeOutboundAndChannel(channelHandlerContext, channelPromise, true);
    }

    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        closeOutboundAndChannel(channelHandlerContext, channelPromise, false);
    }

    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.handshakePromise.isDone()) {
            this.readDuringHandshake = true;
        }
        channelHandlerContext.read();
    }

    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (!(obj instanceof ByteBuf)) {
            channelPromise.setFailure(new UnsupportedMessageTypeException(obj, (Class<?>[]) new Class[]{ByteBuf.class}));
            return;
        }
        this.pendingUnencryptedWrites.add(obj, channelPromise);
    }

    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.startTls || this.sentFirstMessage) {
            try {
                wrapAndFlush(channelHandlerContext);
            } catch (Throwable th) {
                setHandshakeFailure(channelHandlerContext, th);
                PlatformDependent.throwException(th);
            }
            return;
        }
        this.sentFirstMessage = true;
        this.pendingUnencryptedWrites.removeAndWriteAll();
        forceFlush(channelHandlerContext);
    }

    private void wrapAndFlush(ChannelHandlerContext channelHandlerContext) throws SSLException {
        if (this.pendingUnencryptedWrites.isEmpty()) {
            this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, channelHandlerContext.newPromise());
        }
        if (!this.handshakePromise.isDone()) {
            this.flushedBeforeHandshake = true;
        }
        try {
            wrap(channelHandlerContext, false);
        } finally {
            forceFlush(channelHandlerContext);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0067, code lost:
        if (r1 == 2) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006a, code lost:
        if (r1 == 3) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x006d, code lost:
        if (r1 == 4) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0070, code lost:
        if (r1 != 5) goto L_0x0078;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0072, code lost:
        r6 = true;
        r1 = r10;
        r2 = r11;
        r3 = r8;
        r4 = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0078, code lost:
        r1 = new java.lang.StringBuilder();
        r1.append("Unknown handshake status: ");
        r1.append(r2.getHandshakeStatus());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0092, code lost:
        throw new java.lang.IllegalStateException(r1.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0093, code lost:
        setHandshakeSuccess();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0096, code lost:
        setHandshakeSuccessIfStillHandshaking();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b2, code lost:
        finishWrap(r11, r3, r4, r12, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00b9, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void wrap(p043io.netty.channel.ChannelHandlerContext r11, boolean r12) throws javax.net.ssl.SSLException {
        /*
            r10 = this;
            io.netty.buffer.ByteBufAllocator r0 = r11.alloc()
            r7 = 0
        L_0x0005:
            r3 = r7
            r4 = r3
        L_0x0007:
            boolean r1 = r11.isRemoved()     // Catch:{ all -> 0x00ba }
            if (r1 != 0) goto L_0x00b2
            io.netty.channel.PendingWriteQueue r1 = r10.pendingUnencryptedWrites     // Catch:{ all -> 0x00ba }
            java.lang.Object r1 = r1.current()     // Catch:{ all -> 0x00ba }
            if (r1 != 0) goto L_0x0017
            goto L_0x00b2
        L_0x0017:
            io.netty.buffer.ByteBuf r1 = (p043io.netty.buffer.ByteBuf) r1     // Catch:{ all -> 0x00ba }
            if (r3 != 0) goto L_0x0029
            int r2 = r1.readableBytes()     // Catch:{ all -> 0x00ba }
            int r5 = r1.nioBufferCount()     // Catch:{ all -> 0x00ba }
            io.netty.buffer.ByteBuf r2 = r10.allocateOutNetBuf(r11, r2, r5)     // Catch:{ all -> 0x00ba }
            r8 = r2
            goto L_0x002a
        L_0x0029:
            r8 = r3
        L_0x002a:
            javax.net.ssl.SSLEngine r2 = r10.engine     // Catch:{ all -> 0x00af }
            javax.net.ssl.SSLEngineResult r2 = r10.wrap(r0, r2, r1, r8)     // Catch:{ all -> 0x00af }
            javax.net.ssl.SSLEngineResult$Status r3 = r2.getStatus()     // Catch:{ all -> 0x00af }
            javax.net.ssl.SSLEngineResult$Status r5 = javax.net.ssl.SSLEngineResult.Status.CLOSED     // Catch:{ all -> 0x00af }
            if (r3 != r5) goto L_0x0048
            io.netty.channel.PendingWriteQueue r0 = r10.pendingUnencryptedWrites     // Catch:{ all -> 0x00af }
            javax.net.ssl.SSLException r1 = SSLENGINE_CLOSED     // Catch:{ all -> 0x00af }
            r0.removeAndFailAll(r1)     // Catch:{ all -> 0x00af }
            r6 = 0
            r1 = r10
            r2 = r11
            r3 = r8
        L_0x0043:
            r5 = r12
            r1.finishWrap(r2, r3, r4, r5, r6)
            return
        L_0x0048:
            boolean r1 = r1.isReadable()     // Catch:{ all -> 0x00af }
            if (r1 != 0) goto L_0x0056
            io.netty.channel.PendingWriteQueue r1 = r10.pendingUnencryptedWrites     // Catch:{ all -> 0x00af }
            io.netty.channel.ChannelPromise r1 = r1.remove()     // Catch:{ all -> 0x00af }
            r9 = r1
            goto L_0x0057
        L_0x0056:
            r9 = r7
        L_0x0057:
            int[] r1 = p043io.netty.handler.ssl.SslHandler.C57449.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ all -> 0x00ab }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = r2.getHandshakeStatus()     // Catch:{ all -> 0x00ab }
            int r3 = r3.ordinal()     // Catch:{ all -> 0x00ab }
            r1 = r1[r3]     // Catch:{ all -> 0x00ab }
            r3 = 1
            if (r1 == r3) goto L_0x00a4
            r3 = 2
            if (r1 == r3) goto L_0x0093
            r3 = 3
            if (r1 == r3) goto L_0x0096
            r3 = 4
            if (r1 == r3) goto L_0x0099
            r0 = 5
            if (r1 != r0) goto L_0x0078
            r6 = 1
            r1 = r10
            r2 = r11
            r3 = r8
            r4 = r9
            goto L_0x0043
        L_0x0078:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00ab }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ab }
            r1.<init>()     // Catch:{ all -> 0x00ab }
            java.lang.String r3 = "Unknown handshake status: "
            r1.append(r3)     // Catch:{ all -> 0x00ab }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r2 = r2.getHandshakeStatus()     // Catch:{ all -> 0x00ab }
            r1.append(r2)     // Catch:{ all -> 0x00ab }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00ab }
            r0.<init>(r1)     // Catch:{ all -> 0x00ab }
            throw r0     // Catch:{ all -> 0x00ab }
        L_0x0093:
            r10.setHandshakeSuccess()     // Catch:{ all -> 0x00ab }
        L_0x0096:
            r10.setHandshakeSuccessIfStillHandshaking()     // Catch:{ all -> 0x00ab }
        L_0x0099:
            r6 = 0
            r1 = r10
            r2 = r11
            r3 = r8
            r4 = r9
            r5 = r12
            r1.finishWrap(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00ab }
            goto L_0x0005
        L_0x00a4:
            r10.runDelegatedTasks()     // Catch:{ all -> 0x00ab }
            r3 = r8
            r4 = r9
            goto L_0x0007
        L_0x00ab:
            r0 = move-exception
            r3 = r8
            r4 = r9
            goto L_0x00bb
        L_0x00af:
            r0 = move-exception
            r3 = r8
            goto L_0x00bb
        L_0x00b2:
            r6 = 0
            r1 = r10
            r2 = r11
            r5 = r12
            r1.finishWrap(r2, r3, r4, r5, r6)
            return
        L_0x00ba:
            r0 = move-exception
        L_0x00bb:
            r6 = 0
            r1 = r10
            r2 = r11
            r5 = r12
            r1.finishWrap(r2, r3, r4, r5, r6)
            goto L_0x00c4
        L_0x00c3:
            throw r0
        L_0x00c4:
            goto L_0x00c3
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.SslHandler.wrap(io.netty.channel.ChannelHandlerContext, boolean):void");
    }

    private void finishWrap(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ChannelPromise channelPromise, boolean z, boolean z2) {
        if (byteBuf == null) {
            byteBuf = Unpooled.EMPTY_BUFFER;
        } else if (!byteBuf.isReadable()) {
            byteBuf.release();
            byteBuf = Unpooled.EMPTY_BUFFER;
        }
        if (channelPromise != null) {
            channelHandlerContext.write(byteBuf, channelPromise);
        } else {
            channelHandlerContext.write(byteBuf);
        }
        if (z) {
            this.needsFlush = true;
        }
        if (z2) {
            readIfNeeded(channelHandlerContext);
        }
    }

    /* JADX INFO: finally extract failed */
    private void wrapNonAppData(ChannelHandlerContext channelHandlerContext, boolean z) throws SSLException {
        ByteBufAllocator alloc = channelHandlerContext.alloc();
        ByteBuf byteBuf = null;
        while (true) {
            try {
                if (!channelHandlerContext.isRemoved()) {
                    if (byteBuf == null) {
                        byteBuf = allocateOutNetBuf(channelHandlerContext, 2048, 1);
                    }
                    SSLEngineResult wrap = wrap(alloc, this.engine, Unpooled.EMPTY_BUFFER, byteBuf);
                    if (wrap.bytesProduced() > 0) {
                        channelHandlerContext.write(byteBuf);
                        if (z) {
                            this.needsFlush = true;
                        }
                        byteBuf = null;
                    }
                    int i = C57449.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[wrap.getHandshakeStatus().ordinal()];
                    if (i == 1) {
                        runDelegatedTasks();
                    } else if (i == 2) {
                        setHandshakeSuccess();
                    } else if (i == 3) {
                        setHandshakeSuccessIfStillHandshaking();
                        if (!z) {
                            unwrapNonAppData(channelHandlerContext);
                        }
                    } else if (i != 4) {
                        if (i != 5) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Unknown handshake status: ");
                            sb.append(wrap.getHandshakeStatus());
                            throw new IllegalStateException(sb.toString());
                        } else if (z) {
                            if (byteBuf != null) {
                                byteBuf.release();
                            }
                            return;
                        } else {
                            unwrapNonAppData(channelHandlerContext);
                        }
                    }
                    if (wrap.bytesProduced() != 0) {
                        if (wrap.bytesConsumed() == 0 && wrap.getHandshakeStatus() == HandshakeStatus.NOT_HANDSHAKING) {
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            } catch (Throwable th) {
                if (byteBuf != null) {
                    byteBuf.release();
                }
                throw th;
            }
        }
        if (byteBuf != null) {
            byteBuf.release();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0080 A[SYNTHETIC, Splitter:B:25:0x0080] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0076 A[EDGE_INSN: B:34:0x0076->B:21:0x0076 ?: BREAK  
    EDGE_INSN: B:34:0x0076->B:21:0x0076 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private javax.net.ssl.SSLEngineResult wrap(p043io.netty.buffer.ByteBufAllocator r8, javax.net.ssl.SSLEngine r9, p043io.netty.buffer.ByteBuf r10, p043io.netty.buffer.ByteBuf r11) throws javax.net.ssl.SSLException {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            int r2 = r10.readerIndex()     // Catch:{ all -> 0x0088 }
            int r3 = r10.readableBytes()     // Catch:{ all -> 0x0088 }
            boolean r4 = r10.isDirect()     // Catch:{ all -> 0x0088 }
            r5 = 1
            if (r4 != 0) goto L_0x002c
            io.netty.handler.ssl.SslHandler$SslEngineType r4 = r7.engineType     // Catch:{ all -> 0x0088 }
            boolean r4 = r4.wantsDirectBuffer     // Catch:{ all -> 0x0088 }
            if (r4 != 0) goto L_0x0018
            goto L_0x002c
        L_0x0018:
            io.netty.buffer.ByteBuf r8 = r8.directBuffer(r3)     // Catch:{ all -> 0x0088 }
            r8.writeBytes(r10, r2, r3)     // Catch:{ all -> 0x0086 }
            java.nio.ByteBuffer[] r2 = r7.singleBuffer     // Catch:{ all -> 0x0086 }
            int r4 = r8.readerIndex()     // Catch:{ all -> 0x0086 }
            java.nio.ByteBuffer r3 = r8.internalNioBuffer(r4, r3)     // Catch:{ all -> 0x0086 }
            r2[r1] = r3     // Catch:{ all -> 0x0086 }
            goto L_0x0045
        L_0x002c:
            boolean r8 = r10 instanceof p043io.netty.buffer.CompositeByteBuf     // Catch:{ all -> 0x0088 }
            if (r8 != 0) goto L_0x0040
            int r8 = r10.nioBufferCount()     // Catch:{ all -> 0x0088 }
            if (r8 != r5) goto L_0x0040
            java.nio.ByteBuffer[] r8 = r7.singleBuffer     // Catch:{ all -> 0x0088 }
            java.nio.ByteBuffer r2 = r10.internalNioBuffer(r2, r3)     // Catch:{ all -> 0x0088 }
            r8[r1] = r2     // Catch:{ all -> 0x0088 }
            r2 = r8
            goto L_0x0044
        L_0x0040:
            java.nio.ByteBuffer[] r2 = r10.nioBuffers()     // Catch:{ all -> 0x0088 }
        L_0x0044:
            r8 = r0
        L_0x0045:
            int r3 = r11.writerIndex()     // Catch:{ all -> 0x0086 }
            int r4 = r11.writableBytes()     // Catch:{ all -> 0x0086 }
            java.nio.ByteBuffer r3 = r11.nioBuffer(r3, r4)     // Catch:{ all -> 0x0086 }
            javax.net.ssl.SSLEngineResult r3 = r9.wrap(r2, r3)     // Catch:{ all -> 0x0086 }
            int r4 = r3.bytesConsumed()     // Catch:{ all -> 0x0086 }
            r10.skipBytes(r4)     // Catch:{ all -> 0x0086 }
            int r4 = r11.writerIndex()     // Catch:{ all -> 0x0086 }
            int r6 = r3.bytesProduced()     // Catch:{ all -> 0x0086 }
            int r4 = r4 + r6
            r11.writerIndex(r4)     // Catch:{ all -> 0x0086 }
            int[] r4 = p043io.netty.handler.ssl.SslHandler.C57449.$SwitchMap$javax$net$ssl$SSLEngineResult$Status     // Catch:{ all -> 0x0086 }
            javax.net.ssl.SSLEngineResult$Status r6 = r3.getStatus()     // Catch:{ all -> 0x0086 }
            int r6 = r6.ordinal()     // Catch:{ all -> 0x0086 }
            r4 = r4[r6]     // Catch:{ all -> 0x0086 }
            if (r4 == r5) goto L_0x0080
            java.nio.ByteBuffer[] r9 = r7.singleBuffer
            r9[r1] = r0
            if (r8 == 0) goto L_0x007f
            r8.release()
        L_0x007f:
            return r3
        L_0x0080:
            int r3 = r7.maxPacketBufferSize     // Catch:{ all -> 0x0086 }
            r11.ensureWritable(r3)     // Catch:{ all -> 0x0086 }
            goto L_0x0045
        L_0x0086:
            r9 = move-exception
            goto L_0x008a
        L_0x0088:
            r9 = move-exception
            r8 = r0
        L_0x008a:
            java.nio.ByteBuffer[] r10 = r7.singleBuffer
            r10[r1] = r0
            if (r8 == 0) goto L_0x0093
            r8.release()
        L_0x0093:
            goto L_0x0095
        L_0x0094:
            throw r9
        L_0x0095:
            goto L_0x0094
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.SslHandler.wrap(io.netty.buffer.ByteBufAllocator, javax.net.ssl.SSLEngine, io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf):javax.net.ssl.SSLEngineResult");
    }

    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        setHandshakeFailure(channelHandlerContext, CHANNEL_CLOSED, !this.outboundClosed);
        notifyClosePromise(CHANNEL_CLOSED);
        super.channelInactive(channelHandlerContext);
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        if (ignoreException(th)) {
            if (logger.isDebugEnabled()) {
                logger.debug("{} Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", channelHandlerContext.channel(), th);
            }
            if (channelHandlerContext.channel().isActive()) {
                channelHandlerContext.close();
                return;
            }
            return;
        }
        channelHandlerContext.fireExceptionCaught(th);
    }

    private boolean ignoreException(Throwable th) {
        StackTraceElement[] stackTrace;
        if (!(th instanceof SSLException) && (th instanceof IOException) && this.sslClosePromise.isDone()) {
            String message = th.getMessage();
            if (message != null && IGNORABLE_ERROR_MESSAGE.matcher(message).matches()) {
                return true;
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                String className = stackTraceElement.getClassName();
                String methodName = stackTraceElement.getMethodName();
                if (!className.startsWith("io.netty.") && "read".equals(methodName)) {
                    if (IGNORABLE_CLASS_IN_STACK.matcher(className).matches()) {
                        return true;
                    }
                    try {
                        Class loadClass = PlatformDependent.getClassLoader(getClass()).loadClass(className);
                        if (!SocketChannel.class.isAssignableFrom(loadClass)) {
                            if (!DatagramChannel.class.isAssignableFrom(loadClass)) {
                                if (PlatformDependent.javaVersion() >= 7 && "com.sun.nio.sctp.SctpChannel".equals(loadClass.getSuperclass().getName())) {
                                }
                            }
                        }
                        return true;
                    } catch (Throwable th2) {
                        logger.debug("Unexpected exception while loading class {} classname {}", getClass(), className, th2);
                    }
                }
            }
        }
        return false;
    }

    public static boolean isEncrypted(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() >= 5) {
            return SslUtils.getEncryptedPacketLength(byteBuf, byteBuf.readerIndex()) != -2;
        }
        throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws SSLException {
        int i;
        boolean z;
        int readerIndex = byteBuf.readerIndex();
        int writerIndex = byteBuf.writerIndex();
        int i2 = this.packetLength;
        boolean z2 = false;
        if (i2 <= 0) {
            i = readerIndex;
            i2 = 0;
        } else if (writerIndex - readerIndex >= i2) {
            i = readerIndex + i2;
            this.packetLength = 0;
        } else {
            return;
        }
        while (true) {
            if (i2 >= 16474) {
                break;
            }
            int i3 = writerIndex - i;
            if (i3 < 5) {
                break;
            }
            int encryptedPacketLength = SslUtils.getEncryptedPacketLength(byteBuf, i);
            if (encryptedPacketLength == -2) {
                z = true;
                break;
            } else if (encryptedPacketLength > i3) {
                this.packetLength = encryptedPacketLength;
                break;
            } else {
                int i4 = i2 + encryptedPacketLength;
                if (i4 > 16474) {
                    break;
                }
                i += encryptedPacketLength;
                i2 = i4;
            }
        }
        z = false;
        if (i2 > 0) {
            byteBuf.skipBytes(i2);
            try {
                if (unwrap(channelHandlerContext, byteBuf, readerIndex, i2) || this.firedChannelRead) {
                    z2 = true;
                }
                this.firedChannelRead = z2;
            } catch (Throwable th) {
                try {
                    wrapAndFlush(channelHandlerContext);
                } catch (SSLException e) {
                    logger.debug("SSLException during trying to call SSLEngine.wrap(...) because of an previous SSLException, ignoring...", (Throwable) e);
                } catch (Throwable th2) {
                    setHandshakeFailure(channelHandlerContext, th);
                    throw th2;
                }
                setHandshakeFailure(channelHandlerContext, th);
                PlatformDependent.throwException(th);
            }
        }
        if (z) {
            StringBuilder sb = new StringBuilder();
            sb.append("not an SSL/TLS record: ");
            sb.append(ByteBufUtil.hexDump(byteBuf));
            NotSslRecordException notSslRecordException = new NotSslRecordException(sb.toString());
            byteBuf.skipBytes(byteBuf.readableBytes());
            setHandshakeFailure(channelHandlerContext, notSslRecordException);
            throw notSslRecordException;
        }
    }

    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        discardSomeReadBytes();
        flushIfNeeded(channelHandlerContext);
        readIfNeeded(channelHandlerContext);
        this.firedChannelRead = false;
        channelHandlerContext.fireChannelReadComplete();
    }

    private void readIfNeeded(ChannelHandlerContext channelHandlerContext) {
        if (channelHandlerContext.channel().config().isAutoRead()) {
            return;
        }
        if (!this.firedChannelRead || !this.handshakePromise.isDone()) {
            channelHandlerContext.read();
        }
    }

    private void flushIfNeeded(ChannelHandlerContext channelHandlerContext) {
        if (this.needsFlush) {
            forceFlush(channelHandlerContext);
        }
    }

    private void unwrapNonAppData(ChannelHandlerContext channelHandlerContext) throws SSLException {
        unwrap(channelHandlerContext, Unpooled.EMPTY_BUFFER, 0, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009a, code lost:
        if (r3 != javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP) goto L_0x00ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x009c, code lost:
        readIfNeeded(r18);
     */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00ed  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean unwrap(p043io.netty.channel.ChannelHandlerContext r18, p043io.netty.buffer.ByteBuf r19, int r20, int r21) throws javax.net.ssl.SSLException {
        /*
            r17 = this;
            r7 = r17
            r8 = r18
            r0 = r21
            io.netty.buffer.ByteBuf r1 = r7.allocate(r8, r0)
            r9 = 0
            r10 = 1
            r11 = r0
            r12 = r1
            r13 = 0
            r14 = 0
            r15 = 0
            r0 = r20
        L_0x0013:
            r6 = 0
            boolean r1 = r18.isRemoved()     // Catch:{ all -> 0x00ea }
            if (r1 != 0) goto L_0x00ce
            io.netty.handler.ssl.SslHandler$SslEngineType r1 = r7.engineType     // Catch:{ all -> 0x00ea }
            r2 = r17
            r3 = r19
            r4 = r0
            r5 = r11
            r6 = r12
            javax.net.ssl.SSLEngineResult r1 = r1.unwrap(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x00ea }
            javax.net.ssl.SSLEngineResult$Status r2 = r1.getStatus()     // Catch:{ all -> 0x00ea }
            javax.net.ssl.SSLEngineResult$HandshakeStatus r3 = r1.getHandshakeStatus()     // Catch:{ all -> 0x00ea }
            int r4 = r1.bytesProduced()     // Catch:{ all -> 0x00ea }
            int r1 = r1.bytesConsumed()     // Catch:{ all -> 0x00ea }
            int r0 = r0 + r1
            int r11 = r11 - r1
            int[] r5 = p043io.netty.handler.ssl.SslHandler.C57449.$SwitchMap$javax$net$ssl$SSLEngineResult$Status     // Catch:{ all -> 0x00ea }
            int r6 = r2.ordinal()     // Catch:{ all -> 0x00ea }
            r5 = r5[r6]     // Catch:{ all -> 0x00ea }
            if (r5 == r10) goto L_0x00a0
            r6 = 2
            if (r5 == r6) goto L_0x0047
            goto L_0x0048
        L_0x0047:
            r15 = 1
        L_0x0048:
            int[] r5 = p043io.netty.handler.ssl.SslHandler.C57449.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus     // Catch:{ all -> 0x00ea }
            int r16 = r3.ordinal()     // Catch:{ all -> 0x00ea }
            r5 = r5[r16]     // Catch:{ all -> 0x00ea }
            if (r5 == r10) goto L_0x008d
            if (r5 == r6) goto L_0x0088
            r6 = 3
            if (r5 == r6) goto L_0x0079
            r6 = 4
            if (r5 == r6) goto L_0x0075
            r6 = 5
            if (r5 != r6) goto L_0x005e
            goto L_0x0090
        L_0x005e:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00ea }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00ea }
            r1.<init>()     // Catch:{ all -> 0x00ea }
            java.lang.String r2 = "unknown handshake status: "
            r1.append(r2)     // Catch:{ all -> 0x00ea }
            r1.append(r3)     // Catch:{ all -> 0x00ea }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00ea }
            r0.<init>(r1)     // Catch:{ all -> 0x00ea }
            throw r0     // Catch:{ all -> 0x00ea }
        L_0x0075:
            r7.wrapNonAppData(r8, r10)     // Catch:{ all -> 0x00ea }
            goto L_0x0090
        L_0x0079:
            boolean r5 = r17.setHandshakeSuccessIfStillHandshaking()     // Catch:{ all -> 0x00ea }
            if (r5 == 0) goto L_0x0081
            r14 = 1
            goto L_0x0013
        L_0x0081:
            boolean r5 = r7.flushedBeforeHandshake     // Catch:{ all -> 0x00ea }
            if (r5 == 0) goto L_0x0090
            r7.flushedBeforeHandshake = r9     // Catch:{ all -> 0x00ea }
            goto L_0x008b
        L_0x0088:
            r17.setHandshakeSuccess()     // Catch:{ all -> 0x00ea }
        L_0x008b:
            r14 = 1
            goto L_0x0090
        L_0x008d:
            r17.runDelegatedTasks()     // Catch:{ all -> 0x00ea }
        L_0x0090:
            javax.net.ssl.SSLEngineResult$Status r5 = javax.net.ssl.SSLEngineResult.Status.BUFFER_UNDERFLOW     // Catch:{ all -> 0x00ea }
            if (r2 == r5) goto L_0x0098
            if (r1 != 0) goto L_0x0013
            if (r4 != 0) goto L_0x0013
        L_0x0098:
            javax.net.ssl.SSLEngineResult$HandshakeStatus r0 = javax.net.ssl.SSLEngineResult.HandshakeStatus.NEED_UNWRAP     // Catch:{ all -> 0x00ea }
            if (r3 != r0) goto L_0x00ce
            r17.readIfNeeded(r18)     // Catch:{ all -> 0x00ea }
            goto L_0x00ce
        L_0x00a0:
            int r1 = r12.readableBytes()     // Catch:{ all -> 0x00ea }
            javax.net.ssl.SSLEngine r2 = r7.engine     // Catch:{ all -> 0x00ea }
            javax.net.ssl.SSLSession r2 = r2.getSession()     // Catch:{ all -> 0x00ea }
            int r2 = r2.getApplicationBufferSize()     // Catch:{ all -> 0x00ea }
            int r2 = r2 - r1
            if (r1 <= 0) goto L_0x00c2
            r8.fireChannelRead(r12)     // Catch:{ all -> 0x00ea }
            if (r2 > 0) goto L_0x00c0
            javax.net.ssl.SSLEngine r1 = r7.engine     // Catch:{ all -> 0x00cb }
            javax.net.ssl.SSLSession r1 = r1.getSession()     // Catch:{ all -> 0x00cb }
            int r2 = r1.getApplicationBufferSize()     // Catch:{ all -> 0x00cb }
        L_0x00c0:
            r13 = 1
            goto L_0x00c5
        L_0x00c2:
            r12.release()     // Catch:{ all -> 0x00ea }
        L_0x00c5:
            io.netty.buffer.ByteBuf r12 = r7.allocate(r8, r2)     // Catch:{ all -> 0x00cb }
            goto L_0x0013
        L_0x00cb:
            r0 = move-exception
            r12 = 0
            goto L_0x00eb
        L_0x00ce:
            if (r14 == 0) goto L_0x00d3
            r7.wrap(r8, r10)     // Catch:{ all -> 0x00ea }
        L_0x00d3:
            if (r15 == 0) goto L_0x00d9
            r1 = 0
            r7.notifyClosePromise(r1)     // Catch:{ all -> 0x00ea }
        L_0x00d9:
            if (r12 == 0) goto L_0x00e9
            boolean r0 = r12.isReadable()
            if (r0 == 0) goto L_0x00e6
            r8.fireChannelRead(r12)
            r13 = 1
            goto L_0x00e9
        L_0x00e6:
            r12.release()
        L_0x00e9:
            return r13
        L_0x00ea:
            r0 = move-exception
        L_0x00eb:
            if (r12 == 0) goto L_0x00fa
            boolean r1 = r12.isReadable()
            if (r1 == 0) goto L_0x00f7
            r8.fireChannelRead(r12)
            goto L_0x00fa
        L_0x00f7:
            r12.release()
        L_0x00fa:
            goto L_0x00fc
        L_0x00fb:
            throw r0
        L_0x00fc:
            goto L_0x00fb
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.ssl.SslHandler.unwrap(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, int, int):boolean");
    }

    /* access modifiers changed from: private */
    public static ByteBuffer toByteBuffer(ByteBuf byteBuf, int i, int i2) {
        if (byteBuf.nioBufferCount() == 1) {
            return byteBuf.internalNioBuffer(i, i2);
        }
        return byteBuf.nioBuffer(i, i2);
    }

    private void runDelegatedTasks() {
        if (this.delegatedTaskExecutor == ImmediateExecutor.INSTANCE) {
            while (true) {
                Runnable delegatedTask = this.engine.getDelegatedTask();
                if (delegatedTask == null) {
                    break;
                }
                delegatedTask.run();
            }
        } else {
            final ArrayList arrayList = new ArrayList(2);
            while (true) {
                Runnable delegatedTask2 = this.engine.getDelegatedTask();
                if (delegatedTask2 == null) {
                    break;
                }
                arrayList.add(delegatedTask2);
            }
            if (!arrayList.isEmpty()) {
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                this.delegatedTaskExecutor.execute(new Runnable() {
                    public void run() {
                        try {
                            for (Runnable run : arrayList) {
                                run.run();
                            }
                        } catch (Exception e) {
                            SslHandler.this.ctx.fireExceptionCaught(e);
                        } catch (Throwable th) {
                            countDownLatch.countDown();
                            throw th;
                        }
                        countDownLatch.countDown();
                    }
                });
                boolean z = false;
                while (countDownLatch.getCount() != 0) {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException unused) {
                        z = true;
                    }
                }
                if (z) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private boolean setHandshakeSuccessIfStillHandshaking() {
        if (this.handshakePromise.isDone()) {
            return false;
        }
        setHandshakeSuccess();
        return true;
    }

    private void setHandshakeSuccess() {
        this.handshakePromise.trySuccess(this.ctx.channel());
        if (logger.isDebugEnabled()) {
            logger.debug("{} HANDSHAKEN: {}", this.ctx.channel(), this.engine.getSession().getCipherSuite());
        }
        this.ctx.fireUserEventTriggered(SslHandshakeCompletionEvent.SUCCESS);
        if (this.readDuringHandshake && !this.ctx.channel().config().isAutoRead()) {
            this.readDuringHandshake = false;
            this.ctx.read();
        }
    }

    private void setHandshakeFailure(ChannelHandlerContext channelHandlerContext, Throwable th) {
        setHandshakeFailure(channelHandlerContext, th, true);
    }

    private void setHandshakeFailure(ChannelHandlerContext channelHandlerContext, Throwable th, boolean z) {
        try {
            this.engine.closeOutbound();
            if (z) {
                this.engine.closeInbound();
            }
        } catch (SSLException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("possible truncation attack")) {
                logger.debug("{} SSLEngine.closeInbound() raised an exception.", channelHandlerContext.channel(), e);
            }
        } catch (Throwable th2) {
            this.pendingUnencryptedWrites.removeAndFailAll(th);
            throw th2;
        }
        notifyHandshakeFailure(th);
        this.pendingUnencryptedWrites.removeAndFailAll(th);
    }

    /* access modifiers changed from: private */
    public void notifyHandshakeFailure(Throwable th) {
        if (this.handshakePromise.tryFailure(th)) {
            SslUtils.notifyHandshakeFailure(this.ctx, th);
        }
    }

    private void notifyClosePromise(Throwable th) {
        if (th == null) {
            if (this.sslClosePromise.trySuccess(this.ctx.channel())) {
                this.ctx.fireUserEventTriggered(SslCloseCompletionEvent.SUCCESS);
            }
        } else if (this.sslClosePromise.tryFailure(th)) {
            this.ctx.fireUserEventTriggered(new SslCloseCompletionEvent(th));
        }
    }

    private void closeOutboundAndChannel(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise, boolean z) throws Exception {
        if (!channelHandlerContext.channel().isActive()) {
            if (z) {
                channelHandlerContext.disconnect(channelPromise);
            } else {
                channelHandlerContext.close(channelPromise);
            }
            return;
        }
        this.outboundClosed = true;
        this.engine.closeOutbound();
        ChannelPromise newPromise = channelHandlerContext.newPromise();
        try {
            flush(channelHandlerContext, newPromise);
        } finally {
            safeClose(channelHandlerContext, newPromise, channelHandlerContext.newPromise().addListener(new ChannelPromiseNotifier(false, channelPromise)));
        }
    }

    /* access modifiers changed from: private */
    public void flush(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, channelPromise);
        flush(channelHandlerContext);
    }

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
        this.pendingUnencryptedWrites = new PendingWriteQueue(channelHandlerContext);
        if (channelHandlerContext.channel().isActive() && this.engine.getUseClientMode()) {
            handshake(null);
        }
    }

    public Future<Channel> renegotiate() {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext != null) {
            return renegotiate(channelHandlerContext.executor().newPromise());
        }
        throw new IllegalStateException();
    }

    public Future<Channel> renegotiate(final Promise<Channel> promise) {
        if (promise != null) {
            ChannelHandlerContext channelHandlerContext = this.ctx;
            if (channelHandlerContext != null) {
                EventExecutor executor = channelHandlerContext.executor();
                if (!executor.inEventLoop()) {
                    executor.execute(new Runnable() {
                        public void run() {
                            SslHandler.this.handshake(promise);
                        }
                    });
                    return promise;
                }
                handshake(promise);
                return promise;
            }
            throw new IllegalStateException();
        }
        throw new NullPointerException("promise");
    }

    /* access modifiers changed from: private */
    public void handshake(final Promise<Channel> promise) {
        if (promise != null) {
            Promise<Channel> promise2 = this.handshakePromise;
            if (!promise2.isDone()) {
                promise2.addListener(new FutureListener<Channel>() {
                    public void operationComplete(Future<Channel> future) throws Exception {
                        if (future.isSuccess()) {
                            promise.setSuccess(future.getNow());
                        } else {
                            promise.setFailure(future.cause());
                        }
                    }
                });
                return;
            }
            this.handshakePromise = promise;
        } else if (this.engine.getHandshakeStatus() == HandshakeStatus.NOT_HANDSHAKING) {
            promise = this.handshakePromise;
        } else {
            return;
        }
        ChannelHandlerContext channelHandlerContext = this.ctx;
        try {
            this.engine.beginHandshake();
            wrapNonAppData(channelHandlerContext, false);
        } catch (Throwable th) {
            forceFlush(channelHandlerContext);
            throw th;
        }
        forceFlush(channelHandlerContext);
        long j = this.handshakeTimeoutMillis;
        if (j > 0 && !promise.isDone()) {
            final ScheduledFuture schedule = channelHandlerContext.executor().schedule((Runnable) new Runnable() {
                public void run() {
                    if (!promise.isDone()) {
                        SslHandler.this.notifyHandshakeFailure(SslHandler.HANDSHAKE_TIMED_OUT);
                    }
                }
            }, j, TimeUnit.MILLISECONDS);
            promise.addListener(new FutureListener<Channel>() {
                public void operationComplete(Future<Channel> future) throws Exception {
                    schedule.cancel(false);
                }
            });
        }
    }

    private void forceFlush(ChannelHandlerContext channelHandlerContext) {
        this.needsFlush = false;
        channelHandlerContext.flush();
    }

    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.startTls && this.engine.getUseClientMode()) {
            handshake(null);
        }
        channelHandlerContext.fireChannelActive();
    }

    private void safeClose(final ChannelHandlerContext channelHandlerContext, final ChannelFuture channelFuture, final ChannelPromise channelPromise) {
        if (!channelHandlerContext.channel().isActive()) {
            channelHandlerContext.close(channelPromise);
            return;
        }
        final java.util.concurrent.ScheduledFuture scheduledFuture = null;
        if (!channelFuture.isDone()) {
            long j = this.closeNotifyFlushTimeoutMillis;
            if (j > 0) {
                scheduledFuture = channelHandlerContext.executor().schedule((Runnable) new Runnable() {
                    public void run() {
                        if (!channelFuture.isDone()) {
                            SslHandler.logger.warn("{} Last write attempt timed out; force-closing the connection.", (Object) channelHandlerContext.channel());
                            ChannelHandlerContext channelHandlerContext = channelHandlerContext;
                            SslHandler.addCloseListener(channelHandlerContext.close(channelHandlerContext.newPromise()), channelPromise);
                        }
                    }
                }, j, TimeUnit.MILLISECONDS);
            }
        }
        channelFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                java.util.concurrent.ScheduledFuture scheduledFuture = scheduledFuture;
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(false);
                }
                final long access$1400 = SslHandler.this.closeNotifyReadTimeoutMillis;
                if (access$1400 <= 0) {
                    ChannelHandlerContext channelHandlerContext = channelHandlerContext;
                    SslHandler.addCloseListener(channelHandlerContext.close(channelHandlerContext.newPromise()), channelPromise);
                    return;
                }
                final ScheduledFuture schedule = !SslHandler.this.sslClosePromise.isDone() ? channelHandlerContext.executor().schedule((Runnable) new Runnable() {
                    public void run() {
                        if (!SslHandler.this.sslClosePromise.isDone()) {
                            SslHandler.logger.debug("{} did not receive close_notify in {}ms; force-closing the connection.", channelHandlerContext.channel(), Long.valueOf(access$1400));
                            SslHandler.addCloseListener(channelHandlerContext.close(channelHandlerContext.newPromise()), channelPromise);
                        }
                    }
                }, access$1400, TimeUnit.MILLISECONDS) : null;
                SslHandler.this.sslClosePromise.addListener((GenericFutureListener) new FutureListener<Channel>() {
                    public void operationComplete(Future<Channel> future) throws Exception {
                        java.util.concurrent.ScheduledFuture scheduledFuture = schedule;
                        if (scheduledFuture != null) {
                            scheduledFuture.cancel(false);
                        }
                        SslHandler.addCloseListener(channelHandlerContext.close(channelHandlerContext.newPromise()), channelPromise);
                    }
                });
            }
        });
    }

    /* access modifiers changed from: private */
    public static void addCloseListener(ChannelFuture channelFuture, ChannelPromise channelPromise) {
        channelFuture.addListener(new ChannelPromiseNotifier(false, channelPromise));
    }

    private ByteBuf allocate(ChannelHandlerContext channelHandlerContext, int i) {
        ByteBufAllocator alloc = channelHandlerContext.alloc();
        if (this.engineType.wantsDirectBuffer) {
            return alloc.directBuffer(i);
        }
        return alloc.buffer(i);
    }

    private ByteBuf allocateOutNetBuf(ChannelHandlerContext channelHandlerContext, int i, int i2) {
        return allocate(channelHandlerContext, this.engineType.calculateWrapBufferCapacity(this, i, i2));
    }
}
