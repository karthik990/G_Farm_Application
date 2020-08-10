package p043io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import p043io.grpc.Attributes;
import p043io.grpc.BinaryLog;
import p043io.grpc.CompressorRegistry;
import p043io.grpc.Context;
import p043io.grpc.Context.CancellableContext;
import p043io.grpc.Context.CancellationListener;
import p043io.grpc.Contexts;
import p043io.grpc.Decompressor;
import p043io.grpc.DecompressorRegistry;
import p043io.grpc.HandlerRegistry;
import p043io.grpc.InternalChannelz;
import p043io.grpc.InternalChannelz.ServerStats;
import p043io.grpc.InternalChannelz.ServerStats.Builder;
import p043io.grpc.InternalInstrumented;
import p043io.grpc.InternalLogId;
import p043io.grpc.InternalServerInterceptors;
import p043io.grpc.Metadata;
import p043io.grpc.Server;
import p043io.grpc.ServerCall.Listener;
import p043io.grpc.ServerCallHandler;
import p043io.grpc.ServerInterceptor;
import p043io.grpc.ServerMethodDefinition;
import p043io.grpc.ServerServiceDefinition;
import p043io.grpc.ServerTransportFilter;
import p043io.grpc.Status;
import p043io.grpc.internal.StreamListener.MessageProducer;

/* renamed from: io.grpc.internal.ServerImpl */
public final class ServerImpl extends Server implements InternalInstrumented<ServerStats> {
    /* access modifiers changed from: private */
    public static final ServerStreamListener NOOP_LISTENER = new NoopListener();
    /* access modifiers changed from: private */
    public static final Logger log = Logger.getLogger(ServerImpl.class.getName());
    /* access modifiers changed from: private */
    public final BinaryLog binlog;
    /* access modifiers changed from: private */
    public final InternalChannelz channelz;
    /* access modifiers changed from: private */
    public final CompressorRegistry compressorRegistry;
    /* access modifiers changed from: private */
    public final DecompressorRegistry decompressorRegistry;
    /* access modifiers changed from: private */
    public Executor executor;
    private final ObjectPool<? extends Executor> executorPool;
    /* access modifiers changed from: private */
    public final HandlerRegistry fallbackRegistry;
    /* access modifiers changed from: private */
    public final long handshakeTimeoutMillis;
    /* access modifiers changed from: private */
    public final ServerInterceptor[] interceptors;
    /* access modifiers changed from: private */
    public final Object lock = new Object();
    private final InternalLogId logId = InternalLogId.allocate(getClass().getName());
    /* access modifiers changed from: private */
    public final HandlerRegistry registry;
    /* access modifiers changed from: private */
    public final Context rootContext;
    /* access modifiers changed from: private */
    public final CallTracer serverCallTracer;
    /* access modifiers changed from: private */
    public boolean serverShutdownCallbackInvoked;
    private boolean shutdown;
    /* access modifiers changed from: private */
    public Status shutdownNowStatus;
    private boolean started;
    private boolean terminated;
    /* access modifiers changed from: private */
    public final List<ServerTransportFilter> transportFilters;
    private final InternalServer transportServer;
    /* access modifiers changed from: private */
    public boolean transportServerTerminated;
    /* access modifiers changed from: private */
    public final Collection<ServerTransport> transports = new HashSet();

    /* renamed from: io.grpc.internal.ServerImpl$ContextCloser */
    static final class ContextCloser implements Runnable {
        private final Throwable cause;
        private final CancellableContext context;

        ContextCloser(CancellableContext cancellableContext, Throwable th) {
            this.context = cancellableContext;
            this.cause = th;
        }

        public void run() {
            this.context.cancel(this.cause);
        }
    }

    /* renamed from: io.grpc.internal.ServerImpl$JumpToApplicationThreadServerStreamListener */
    static final class JumpToApplicationThreadServerStreamListener implements ServerStreamListener {
        private final Executor callExecutor;
        private final Executor cancelExecutor;
        /* access modifiers changed from: private */
        public final CancellableContext context;
        private ServerStreamListener listener;
        private final ServerStream stream;

        public JumpToApplicationThreadServerStreamListener(Executor executor, Executor executor2, ServerStream serverStream, CancellableContext cancellableContext) {
            this.callExecutor = executor;
            this.cancelExecutor = executor2;
            this.stream = serverStream;
            this.context = cancellableContext;
        }

        /* access modifiers changed from: private */
        public ServerStreamListener getListener() {
            ServerStreamListener serverStreamListener = this.listener;
            if (serverStreamListener != null) {
                return serverStreamListener;
            }
            throw new IllegalStateException("listener unset");
        }

        /* access modifiers changed from: 0000 */
        public void setListener(ServerStreamListener serverStreamListener) {
            Preconditions.checkNotNull(serverStreamListener, "listener must not be null");
            Preconditions.checkState(this.listener == null, "Listener already set");
            this.listener = serverStreamListener;
        }

        /* access modifiers changed from: private */
        public void internalClose() {
            this.stream.close(Status.UNKNOWN, new Metadata());
        }

        public void messagesAvailable(final MessageProducer messageProducer) {
            this.callExecutor.execute(new ContextRunnable() {
                public void runInContext() {
                    try {
                        JumpToApplicationThreadServerStreamListener.this.getListener().messagesAvailable(messageProducer);
                    } catch (RuntimeException e) {
                        JumpToApplicationThreadServerStreamListener.this.internalClose();
                        throw e;
                    } catch (Error e2) {
                        JumpToApplicationThreadServerStreamListener.this.internalClose();
                        throw e2;
                    }
                }
            });
        }

        public void halfClosed() {
            this.callExecutor.execute(new ContextRunnable() {
                {
                    CancellableContext access$2500 = JumpToApplicationThreadServerStreamListener.this.context;
                }

                public void runInContext() {
                    try {
                        JumpToApplicationThreadServerStreamListener.this.getListener().halfClosed();
                    } catch (RuntimeException e) {
                        JumpToApplicationThreadServerStreamListener.this.internalClose();
                        throw e;
                    } catch (Error e2) {
                        JumpToApplicationThreadServerStreamListener.this.internalClose();
                        throw e2;
                    }
                }
            });
        }

        public void closed(final Status status) {
            if (!status.isOk()) {
                this.cancelExecutor.execute(new ContextCloser(this.context, status.getCause()));
            }
            this.callExecutor.execute(new ContextRunnable() {
                public void runInContext() {
                    JumpToApplicationThreadServerStreamListener.this.getListener().closed(status);
                }
            });
        }

        public void onReady() {
            this.callExecutor.execute(new ContextRunnable() {
                {
                    CancellableContext access$2500 = JumpToApplicationThreadServerStreamListener.this.context;
                }

                public void runInContext() {
                    try {
                        JumpToApplicationThreadServerStreamListener.this.getListener().onReady();
                    } catch (RuntimeException e) {
                        JumpToApplicationThreadServerStreamListener.this.internalClose();
                        throw e;
                    } catch (Error e2) {
                        JumpToApplicationThreadServerStreamListener.this.internalClose();
                        throw e2;
                    }
                }
            });
        }
    }

    /* renamed from: io.grpc.internal.ServerImpl$NoopListener */
    private static final class NoopListener implements ServerStreamListener {
        public void closed(Status status) {
        }

        public void halfClosed() {
        }

        public void onReady() {
        }

        private NoopListener() {
        }

        public void messagesAvailable(MessageProducer messageProducer) {
            while (true) {
                InputStream next = messageProducer.next();
                if (next != null) {
                    try {
                        next.close();
                    } catch (IOException e) {
                        while (true) {
                            InputStream next2 = messageProducer.next();
                            if (next2 != null) {
                                try {
                                    next2.close();
                                } catch (IOException e2) {
                                    ServerImpl.log.log(Level.WARNING, "Exception closing stream", e2);
                                }
                            } else {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* renamed from: io.grpc.internal.ServerImpl$ServerListenerImpl */
    private final class ServerListenerImpl implements ServerListener {
        private ServerListenerImpl() {
        }

        public ServerTransportListener transportCreated(ServerTransport serverTransport) {
            synchronized (ServerImpl.this.lock) {
                ServerImpl.this.transports.add(serverTransport);
            }
            ServerTransportListenerImpl serverTransportListenerImpl = new ServerTransportListenerImpl(serverTransport);
            serverTransportListenerImpl.init();
            return serverTransportListenerImpl;
        }

        public void serverShutdown() {
            ArrayList arrayList;
            Status access$400;
            synchronized (ServerImpl.this.lock) {
                arrayList = new ArrayList(ServerImpl.this.transports);
                access$400 = ServerImpl.this.shutdownNowStatus;
                ServerImpl.this.serverShutdownCallbackInvoked = true;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ServerTransport serverTransport = (ServerTransport) it.next();
                if (access$400 == null) {
                    serverTransport.shutdown();
                } else {
                    serverTransport.shutdownNow(access$400);
                }
            }
            synchronized (ServerImpl.this.lock) {
                ServerImpl.this.transportServerTerminated = true;
                ServerImpl.this.checkForTermination();
            }
        }
    }

    /* renamed from: io.grpc.internal.ServerImpl$ServerTransportListenerImpl */
    private final class ServerTransportListenerImpl implements ServerTransportListener {
        private Attributes attributes;
        private Future<?> handshakeTimeoutFuture;
        /* access modifiers changed from: private */
        public final ServerTransport transport;

        ServerTransportListenerImpl(ServerTransport serverTransport) {
            this.transport = serverTransport;
        }

        public void init() {
            if (ServerImpl.this.handshakeTimeoutMillis != Long.MAX_VALUE) {
                this.handshakeTimeoutFuture = this.transport.getScheduledExecutorService().schedule(new Runnable() {
                    public void run() {
                        ServerTransportListenerImpl.this.transport.shutdownNow(Status.CANCELLED.withDescription("Handshake timeout exceeded"));
                    }
                }, ServerImpl.this.handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
            } else {
                this.handshakeTimeoutFuture = new FutureTask(new Runnable() {
                    public void run() {
                    }
                }, null);
            }
            ServerImpl.this.channelz.addServerSocket(ServerImpl.this, this.transport);
        }

        public Attributes transportReady(Attributes attributes2) {
            this.handshakeTimeoutFuture.cancel(false);
            this.handshakeTimeoutFuture = null;
            for (ServerTransportFilter serverTransportFilter : ServerImpl.this.transportFilters) {
                attributes2 = (Attributes) Preconditions.checkNotNull(serverTransportFilter.transportReady(attributes2), "Filter %s returned null", (Object) serverTransportFilter);
            }
            this.attributes = attributes2;
            return attributes2;
        }

        public void transportTerminated() {
            Future<?> future = this.handshakeTimeoutFuture;
            if (future != null) {
                future.cancel(false);
                this.handshakeTimeoutFuture = null;
            }
            for (ServerTransportFilter transportTerminated : ServerImpl.this.transportFilters) {
                transportTerminated.transportTerminated(this.attributes);
            }
            ServerImpl.this.transportClosed(this.transport);
        }

        public void streamCreated(ServerStream serverStream, String str, Metadata metadata) {
            Executor executor;
            if (metadata.containsKey(GrpcUtil.MESSAGE_ENCODING_KEY)) {
                String str2 = (String) metadata.get(GrpcUtil.MESSAGE_ENCODING_KEY);
                Decompressor lookupDecompressor = ServerImpl.this.decompressorRegistry.lookupDecompressor(str2);
                if (lookupDecompressor == null) {
                    serverStream.close(Status.UNIMPLEMENTED.withDescription(String.format("Can't find decompressor for %s", new Object[]{str2})), new Metadata());
                    return;
                }
                serverStream.setDecompressor(lookupDecompressor);
            }
            StatsTraceContext statsTraceContext = (StatsTraceContext) Preconditions.checkNotNull(serverStream.statsTraceContext(), "statsTraceCtx not present from stream");
            CancellableContext createContext = createContext(serverStream, metadata, statsTraceContext);
            if (ServerImpl.this.executor == MoreExecutors.directExecutor()) {
                executor = new SerializeReentrantCallsDirectExecutor();
            } else {
                executor = new SerializingExecutor(ServerImpl.this.executor);
            }
            JumpToApplicationThreadServerStreamListener jumpToApplicationThreadServerStreamListener = new JumpToApplicationThreadServerStreamListener(executor, ServerImpl.this.executor, serverStream, createContext);
            serverStream.setListener(jumpToApplicationThreadServerStreamListener);
            AnonymousClass1StreamCreated r1 = new ContextRunnable(createContext, str, serverStream, metadata, statsTraceContext, jumpToApplicationThreadServerStreamListener) {
                final /* synthetic */ CancellableContext val$context;
                final /* synthetic */ Metadata val$headers;
                final /* synthetic */ JumpToApplicationThreadServerStreamListener val$jumpListener;
                final /* synthetic */ String val$methodName;
                final /* synthetic */ StatsTraceContext val$statsTraceCtx;
                final /* synthetic */ ServerStream val$stream;

                {
                    this.val$context = r2;
                    this.val$methodName = r3;
                    this.val$stream = r4;
                    this.val$headers = r5;
                    this.val$statsTraceCtx = r6;
                    this.val$jumpListener = r7;
                }

                public void runInContext() {
                    ServerStreamListener access$1500 = ServerImpl.NOOP_LISTENER;
                    try {
                        ServerMethodDefinition lookupMethod = ServerImpl.this.registry.lookupMethod(this.val$methodName);
                        if (lookupMethod == null) {
                            lookupMethod = ServerImpl.this.fallbackRegistry.lookupMethod(this.val$methodName, this.val$stream.getAuthority());
                        }
                        ServerMethodDefinition serverMethodDefinition = lookupMethod;
                        if (serverMethodDefinition == null) {
                            Status status = Status.UNIMPLEMENTED;
                            StringBuilder sb = new StringBuilder();
                            sb.append("Method not found: ");
                            sb.append(this.val$methodName);
                            this.val$stream.close(status.withDescription(sb.toString()), new Metadata());
                            this.val$context.cancel(null);
                            this.val$jumpListener.setListener(access$1500);
                            return;
                        }
                        this.val$jumpListener.setListener(ServerTransportListenerImpl.this.startCall(this.val$stream, this.val$methodName, serverMethodDefinition, this.val$headers, this.val$context, this.val$statsTraceCtx));
                    } catch (RuntimeException e) {
                        this.val$stream.close(Status.fromThrowable(e), new Metadata());
                        this.val$context.cancel(null);
                        throw e;
                    } catch (Error e2) {
                        this.val$stream.close(Status.fromThrowable(e2), new Metadata());
                        this.val$context.cancel(null);
                        throw e2;
                    } catch (Throwable th) {
                        this.val$jumpListener.setListener(access$1500);
                        throw th;
                    }
                }
            };
            executor.execute(r1);
        }

        private CancellableContext createContext(final ServerStream serverStream, Metadata metadata, StatsTraceContext statsTraceContext) {
            Long l = (Long) metadata.get(GrpcUtil.TIMEOUT_KEY);
            Context serverFilterContext = statsTraceContext.serverFilterContext(ServerImpl.this.rootContext);
            if (l == null) {
                return serverFilterContext.withCancellation();
            }
            CancellableContext withDeadlineAfter = serverFilterContext.withDeadlineAfter(l.longValue(), TimeUnit.NANOSECONDS, this.transport.getScheduledExecutorService());
            withDeadlineAfter.addListener(new CancellationListener() {
                public void cancelled(Context context) {
                    Status statusFromCancelled = Contexts.statusFromCancelled(context);
                    if (Status.DEADLINE_EXCEEDED.getCode().equals(statusFromCancelled.getCode())) {
                        serverStream.cancel(statusFromCancelled);
                    }
                }
            }, MoreExecutors.directExecutor());
            return withDeadlineAfter;
        }

        /* access modifiers changed from: private */
        public <ReqT, RespT> ServerStreamListener startCall(ServerStream serverStream, String str, ServerMethodDefinition<ReqT, RespT> serverMethodDefinition, Metadata metadata, CancellableContext cancellableContext, StatsTraceContext statsTraceContext) {
            statsTraceContext.serverCallStarted(new ServerCallInfoImpl(serverMethodDefinition.getMethodDescriptor(), serverStream.getAttributes(), serverStream.getAuthority()));
            ServerCallHandler serverCallHandler = serverMethodDefinition.getServerCallHandler();
            for (ServerInterceptor interceptCallHandler : ServerImpl.this.interceptors) {
                serverCallHandler = InternalServerInterceptors.interceptCallHandler(interceptCallHandler, serverCallHandler);
            }
            ServerMethodDefinition withServerCallHandler = serverMethodDefinition.withServerCallHandler(serverCallHandler);
            if (ServerImpl.this.binlog != null) {
                withServerCallHandler = ServerImpl.this.binlog.wrapMethodDefinition(withServerCallHandler);
            }
            return startWrappedCall(str, withServerCallHandler, serverStream, metadata, cancellableContext);
        }

        private <WReqT, WRespT> ServerStreamListener startWrappedCall(String str, ServerMethodDefinition<WReqT, WRespT> serverMethodDefinition, ServerStream serverStream, Metadata metadata, CancellableContext cancellableContext) {
            ServerCallImpl serverCallImpl = new ServerCallImpl(serverStream, serverMethodDefinition.getMethodDescriptor(), metadata, cancellableContext, ServerImpl.this.decompressorRegistry, ServerImpl.this.compressorRegistry, ServerImpl.this.serverCallTracer);
            Listener startCall = serverMethodDefinition.getServerCallHandler().startCall(serverCallImpl, metadata);
            if (startCall != null) {
                return serverCallImpl.newServerStreamListener(startCall);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("startCall() returned a null listener for method ");
            sb.append(str);
            throw new NullPointerException(sb.toString());
        }
    }

    ServerImpl(AbstractServerImplBuilder<?> abstractServerImplBuilder, InternalServer internalServer, Context context) {
        this.executorPool = (ObjectPool) Preconditions.checkNotNull(abstractServerImplBuilder.executorPool, "executorPool");
        this.registry = (HandlerRegistry) Preconditions.checkNotNull(abstractServerImplBuilder.registryBuilder.build(), "registryBuilder");
        this.fallbackRegistry = (HandlerRegistry) Preconditions.checkNotNull(abstractServerImplBuilder.fallbackRegistry, "fallbackRegistry");
        this.transportServer = (InternalServer) Preconditions.checkNotNull(internalServer, "transportServer");
        this.rootContext = ((Context) Preconditions.checkNotNull(context, "rootContext")).fork();
        this.decompressorRegistry = abstractServerImplBuilder.decompressorRegistry;
        this.compressorRegistry = abstractServerImplBuilder.compressorRegistry;
        this.transportFilters = Collections.unmodifiableList(new ArrayList(abstractServerImplBuilder.transportFilters));
        this.interceptors = (ServerInterceptor[]) abstractServerImplBuilder.interceptors.toArray(new ServerInterceptor[abstractServerImplBuilder.interceptors.size()]);
        this.handshakeTimeoutMillis = abstractServerImplBuilder.handshakeTimeoutMillis;
        this.binlog = abstractServerImplBuilder.binlog;
        this.channelz = abstractServerImplBuilder.channelz;
        this.serverCallTracer = abstractServerImplBuilder.callTracerFactory.create();
        this.channelz.addServer(this);
    }

    public ServerImpl start() throws IOException {
        synchronized (this.lock) {
            boolean z = false;
            Preconditions.checkState(!this.started, "Already started");
            if (!this.shutdown) {
                z = true;
            }
            Preconditions.checkState(z, "Shutting down");
            this.transportServer.start(new ServerListenerImpl());
            this.executor = (Executor) Preconditions.checkNotNull(this.executorPool.getObject(), "executor");
            this.started = true;
        }
        return this;
    }

    public int getPort() {
        int port;
        synchronized (this.lock) {
            Preconditions.checkState(this.started, "Not started");
            Preconditions.checkState(!this.terminated, "Already terminated");
            port = this.transportServer.getPort();
        }
        return port;
    }

    public List<ServerServiceDefinition> getServices() {
        List services = this.fallbackRegistry.getServices();
        if (services.isEmpty()) {
            return this.registry.getServices();
        }
        List services2 = this.registry.getServices();
        ArrayList arrayList = new ArrayList(services2.size() + services.size());
        arrayList.addAll(services2);
        arrayList.addAll(services);
        return Collections.unmodifiableList(arrayList);
    }

    public List<ServerServiceDefinition> getImmutableServices() {
        return this.registry.getServices();
    }

    public List<ServerServiceDefinition> getMutableServices() {
        return Collections.unmodifiableList(this.fallbackRegistry.getServices());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0016, code lost:
        if (r2 == false) goto L_0x001d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        r3.transportServer.shutdown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001d, code lost:
        return r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public p043io.grpc.internal.ServerImpl shutdown() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.lock
            monitor-enter(r0)
            boolean r1 = r3.shutdown     // Catch:{ all -> 0x001e }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            return r3
        L_0x0009:
            r1 = 1
            r3.shutdown = r1     // Catch:{ all -> 0x001e }
            boolean r2 = r3.started     // Catch:{ all -> 0x001e }
            if (r2 != 0) goto L_0x0015
            r3.transportServerTerminated = r1     // Catch:{ all -> 0x001e }
            r3.checkForTermination()     // Catch:{ all -> 0x001e }
        L_0x0015:
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            if (r2 == 0) goto L_0x001d
            io.grpc.internal.InternalServer r0 = r3.transportServer
            r0.shutdown()
        L_0x001d:
            return r3
        L_0x001e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x001e }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.internal.ServerImpl.shutdown():io.grpc.internal.ServerImpl");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0022, code lost:
        r1 = r2.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002a, code lost:
        if (r1.hasNext() == false) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x002c, code lost:
        ((p043io.grpc.internal.ServerTransport) r1.next()).shutdownNow(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0036, code lost:
        return r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0020, code lost:
        if (r3 == false) goto L_0x0036;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public p043io.grpc.internal.ServerImpl shutdownNow() {
        /*
            r4 = this;
            r4.shutdown()
            io.grpc.Status r0 = p043io.grpc.Status.UNAVAILABLE
            java.lang.String r1 = "Server shutdownNow invoked"
            io.grpc.Status r0 = r0.withDescription(r1)
            java.lang.Object r1 = r4.lock
            monitor-enter(r1)
            io.grpc.Status r2 = r4.shutdownNowStatus     // Catch:{ all -> 0x0037 }
            if (r2 == 0) goto L_0x0014
            monitor-exit(r1)     // Catch:{ all -> 0x0037 }
            return r4
        L_0x0014:
            r4.shutdownNowStatus = r0     // Catch:{ all -> 0x0037 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x0037 }
            java.util.Collection<io.grpc.internal.ServerTransport> r3 = r4.transports     // Catch:{ all -> 0x0037 }
            r2.<init>(r3)     // Catch:{ all -> 0x0037 }
            boolean r3 = r4.serverShutdownCallbackInvoked     // Catch:{ all -> 0x0037 }
            monitor-exit(r1)     // Catch:{ all -> 0x0037 }
            if (r3 == 0) goto L_0x0036
            java.util.Iterator r1 = r2.iterator()
        L_0x0026:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0036
            java.lang.Object r2 = r1.next()
            io.grpc.internal.ServerTransport r2 = (p043io.grpc.internal.ServerTransport) r2
            r2.shutdownNow(r0)
            goto L_0x0026
        L_0x0036:
            return r4
        L_0x0037:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0037 }
            goto L_0x003b
        L_0x003a:
            throw r0
        L_0x003b:
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.internal.ServerImpl.shutdownNow():io.grpc.internal.ServerImpl");
    }

    public boolean isShutdown() {
        boolean z;
        synchronized (this.lock) {
            z = this.shutdown;
        }
        return z;
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        boolean z;
        synchronized (this.lock) {
            long nanoTime = System.nanoTime() + timeUnit.toNanos(j);
            while (!this.terminated) {
                long nanoTime2 = nanoTime - System.nanoTime();
                if (nanoTime2 <= 0) {
                    break;
                }
                TimeUnit.NANOSECONDS.timedWait(this.lock, nanoTime2);
            }
            z = this.terminated;
        }
        return z;
    }

    public void awaitTermination() throws InterruptedException {
        synchronized (this.lock) {
            while (!this.terminated) {
                this.lock.wait();
            }
        }
    }

    public boolean isTerminated() {
        boolean z;
        synchronized (this.lock) {
            z = this.terminated;
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void transportClosed(ServerTransport serverTransport) {
        synchronized (this.lock) {
            if (this.transports.remove(serverTransport)) {
                this.channelz.removeServerSocket(this, serverTransport);
                checkForTermination();
            } else {
                throw new AssertionError("Transport already removed");
            }
        }
    }

    /* access modifiers changed from: private */
    public void checkForTermination() {
        synchronized (this.lock) {
            if (this.shutdown && this.transports.isEmpty() && this.transportServerTerminated) {
                if (!this.terminated) {
                    this.terminated = true;
                    this.channelz.removeServer(this);
                    if (this.executor != null) {
                        this.executor = (Executor) this.executorPool.returnObject(this.executor);
                    }
                    this.lock.notifyAll();
                } else {
                    throw new AssertionError("Server already terminated");
                }
            }
        }
    }

    public InternalLogId getLogId() {
        return this.logId;
    }

    public ListenableFuture<ServerStats> getStats() {
        Builder listenSockets = new Builder().setListenSockets(this.transportServer.getListenSockets());
        this.serverCallTracer.updateBuilder(listenSockets);
        SettableFuture create = SettableFuture.create();
        create.set(listenSockets.build());
        return create;
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("logId", this.logId.getId()).add("transportServer", (Object) this.transportServer).toString();
    }
}
