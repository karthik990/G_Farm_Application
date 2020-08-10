package p043io.grpc.internal;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import p043io.grpc.BinaryLog;
import p043io.grpc.BindableService;
import p043io.grpc.CompressorRegistry;
import p043io.grpc.Context;
import p043io.grpc.DecompressorRegistry;
import p043io.grpc.HandlerRegistry;
import p043io.grpc.InternalChannelz;
import p043io.grpc.InternalNotifyOnServerBuild;
import p043io.grpc.Server;
import p043io.grpc.ServerBuilder;
import p043io.grpc.ServerInterceptor;
import p043io.grpc.ServerMethodDefinition;
import p043io.grpc.ServerServiceDefinition;
import p043io.grpc.ServerStreamTracer;
import p043io.grpc.ServerTransportFilter;
import p043io.grpc.internal.AbstractServerImplBuilder;
import p043io.grpc.internal.CallTracer.Factory;
import p043io.opencensus.trace.Tracing;

/* renamed from: io.grpc.internal.AbstractServerImplBuilder */
public abstract class AbstractServerImplBuilder<T extends AbstractServerImplBuilder<T>> extends ServerBuilder<T> {
    private static final CompressorRegistry DEFAULT_COMPRESSOR_REGISTRY = CompressorRegistry.getDefaultInstance();
    private static final DecompressorRegistry DEFAULT_DECOMPRESSOR_REGISTRY = DecompressorRegistry.getDefaultInstance();
    private static final ObjectPool<? extends Executor> DEFAULT_EXECUTOR_POOL = SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR);
    private static final HandlerRegistry DEFAULT_FALLBACK_REGISTRY = new HandlerRegistry() {
        @Nullable
        public ServerMethodDefinition<?, ?> lookupMethod(String str, @Nullable String str2) {
            return null;
        }

        public List<ServerServiceDefinition> getServices() {
            return Collections.emptyList();
        }
    };
    private static final long DEFAULT_HANDSHAKE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(120);
    @Nullable
    protected BinaryLog binlog;
    protected Factory callTracerFactory = CallTracer.getDefaultFactory();
    @Nullable
    private CensusStatsModule censusStatsOverride;
    protected InternalChannelz channelz = InternalChannelz.instance();
    CompressorRegistry compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
    DecompressorRegistry decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
    ObjectPool<? extends Executor> executorPool = DEFAULT_EXECUTOR_POOL;
    HandlerRegistry fallbackRegistry = DEFAULT_FALLBACK_REGISTRY;
    long handshakeTimeoutMillis = DEFAULT_HANDSHAKE_TIMEOUT_MILLIS;
    final List<ServerInterceptor> interceptors = new ArrayList();
    private final List<InternalNotifyOnServerBuild> notifyOnBuildList = new ArrayList();
    private boolean recordFinishedRpcs = true;
    private boolean recordStartedRpcs = true;
    final Builder registryBuilder = new Builder();
    private boolean statsEnabled = true;
    private final List<ServerStreamTracer.Factory> streamTracerFactories = new ArrayList();
    private boolean tracingEnabled = true;
    final List<ServerTransportFilter> transportFilters = new ArrayList();
    protected TransportTracer.Factory transportTracerFactory = TransportTracer.getDefaultFactory();

    private T thisT() {
        return this;
    }

    /* access modifiers changed from: protected */
    public abstract InternalServer buildTransportServer(List<ServerStreamTracer.Factory> list);

    public static ServerBuilder<?> forPort(int i) {
        throw new UnsupportedOperationException("Subclass failed to hide static factory");
    }

    public final T directExecutor() {
        return executor(MoreExecutors.directExecutor());
    }

    public final T executor(@Nullable Executor executor) {
        if (executor != null) {
            this.executorPool = new FixedObjectPool(executor);
        } else {
            this.executorPool = DEFAULT_EXECUTOR_POOL;
        }
        return thisT();
    }

    public final T addService(ServerServiceDefinition serverServiceDefinition) {
        this.registryBuilder.addService(serverServiceDefinition);
        return thisT();
    }

    public final T addService(BindableService bindableService) {
        if (bindableService instanceof InternalNotifyOnServerBuild) {
            this.notifyOnBuildList.add((InternalNotifyOnServerBuild) bindableService);
        }
        return addService(bindableService.bindService());
    }

    public final T addTransportFilter(ServerTransportFilter serverTransportFilter) {
        this.transportFilters.add(Preconditions.checkNotNull(serverTransportFilter, "filter"));
        return thisT();
    }

    public final T intercept(ServerInterceptor serverInterceptor) {
        this.interceptors.add(serverInterceptor);
        return thisT();
    }

    public final T addStreamTracerFactory(ServerStreamTracer.Factory factory) {
        this.streamTracerFactories.add(Preconditions.checkNotNull(factory, "factory"));
        return thisT();
    }

    public final T fallbackHandlerRegistry(HandlerRegistry handlerRegistry) {
        if (handlerRegistry != null) {
            this.fallbackRegistry = handlerRegistry;
        } else {
            this.fallbackRegistry = DEFAULT_FALLBACK_REGISTRY;
        }
        return thisT();
    }

    public final T decompressorRegistry(DecompressorRegistry decompressorRegistry2) {
        if (decompressorRegistry2 != null) {
            this.decompressorRegistry = decompressorRegistry2;
        } else {
            this.decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
        }
        return thisT();
    }

    public final T compressorRegistry(CompressorRegistry compressorRegistry2) {
        if (compressorRegistry2 != null) {
            this.compressorRegistry = compressorRegistry2;
        } else {
            this.compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
        }
        return thisT();
    }

    public final T handshakeTimeout(long j, TimeUnit timeUnit) {
        Preconditions.checkArgument(j > 0, "handshake timeout is %s, but must be positive", j);
        this.handshakeTimeoutMillis = timeUnit.toMillis(j);
        return thisT();
    }

    public final T setBinaryLog(BinaryLog binaryLog) {
        this.binlog = binaryLog;
        return thisT();
    }

    /* access modifiers changed from: protected */
    public T overrideCensusStatsModule(CensusStatsModule censusStatsModule) {
        this.censusStatsOverride = censusStatsModule;
        return thisT();
    }

    /* access modifiers changed from: protected */
    public void setStatsEnabled(boolean z) {
        this.statsEnabled = z;
    }

    /* access modifiers changed from: protected */
    public void setStatsRecordStartedRpcs(boolean z) {
        this.recordStartedRpcs = z;
    }

    /* access modifiers changed from: protected */
    public void setStatsRecordFinishedRpcs(boolean z) {
        this.recordFinishedRpcs = z;
    }

    /* access modifiers changed from: protected */
    public void setTracingEnabled(boolean z) {
        this.tracingEnabled = z;
    }

    public Server build() {
        ServerImpl serverImpl = new ServerImpl(this, buildTransportServer(Collections.unmodifiableList(getTracerFactories())), Context.ROOT);
        for (InternalNotifyOnServerBuild notifyOnBuild : this.notifyOnBuildList) {
            notifyOnBuild.notifyOnBuild(serverImpl);
        }
        return serverImpl;
    }

    /* access modifiers changed from: 0000 */
    public final List<ServerStreamTracer.Factory> getTracerFactories() {
        ArrayList arrayList = new ArrayList();
        if (this.statsEnabled) {
            CensusStatsModule censusStatsModule = this.censusStatsOverride;
            if (censusStatsModule == null) {
                censusStatsModule = new CensusStatsModule(GrpcUtil.STOPWATCH_SUPPLIER, true);
            }
            arrayList.add(censusStatsModule.getServerTracerFactory(this.recordStartedRpcs, this.recordFinishedRpcs));
        }
        if (this.tracingEnabled) {
            arrayList.add(new CensusTracingModule(Tracing.getTracer(), Tracing.getPropagationComponent().getBinaryFormat()).getServerTracerFactory());
        }
        arrayList.addAll(this.streamTracerFactories);
        return arrayList;
    }
}
