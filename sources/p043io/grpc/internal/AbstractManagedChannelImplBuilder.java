package p043io.grpc.internal;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.MoreExecutors;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import p043io.grpc.Attributes;
import p043io.grpc.BinaryLog;
import p043io.grpc.ClientInterceptor;
import p043io.grpc.CompressorRegistry;
import p043io.grpc.DecompressorRegistry;
import p043io.grpc.EquivalentAddressGroup;
import p043io.grpc.InternalChannelz;
import p043io.grpc.LoadBalancer;
import p043io.grpc.ManagedChannel;
import p043io.grpc.ManagedChannelBuilder;
import p043io.grpc.NameResolver;
import p043io.grpc.NameResolver.Factory;
import p043io.grpc.NameResolver.Listener;
import p043io.grpc.NameResolverProvider;
import p043io.grpc.internal.AbstractManagedChannelImplBuilder;
import p043io.grpc.internal.ExponentialBackoffPolicy.Provider;
import p043io.opencensus.trace.Tracing;

/* renamed from: io.grpc.internal.AbstractManagedChannelImplBuilder */
public abstract class AbstractManagedChannelImplBuilder<T extends AbstractManagedChannelImplBuilder<T>> extends ManagedChannelBuilder<T> {
    private static final CompressorRegistry DEFAULT_COMPRESSOR_REGISTRY = CompressorRegistry.getDefaultInstance();
    private static final DecompressorRegistry DEFAULT_DECOMPRESSOR_REGISTRY = DecompressorRegistry.getDefaultInstance();
    private static final ObjectPool<? extends Executor> DEFAULT_EXECUTOR_POOL = SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR);
    private static final Factory DEFAULT_NAME_RESOLVER_FACTORY = NameResolverProvider.asFactory();
    private static final long DEFAULT_PER_RPC_BUFFER_LIMIT_IN_BYTES = 1048576;
    private static final long DEFAULT_RETRY_BUFFER_SIZE_IN_BYTES = 16777216;
    private static final String DIRECT_ADDRESS_SCHEME = "directaddress";
    static final long IDLE_MODE_DEFAULT_TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(30);
    static final long IDLE_MODE_MAX_TIMEOUT_DAYS = 30;
    static final long IDLE_MODE_MIN_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(1);
    @Nullable
    String authorityOverride;
    @Nullable
    BinaryLog binlog;
    @Nullable
    private CensusStatsModule censusStatsOverride;
    InternalChannelz channelz = InternalChannelz.instance();
    CompressorRegistry compressorRegistry = DEFAULT_COMPRESSOR_REGISTRY;
    DecompressorRegistry decompressorRegistry = DEFAULT_DECOMPRESSOR_REGISTRY;
    @Nullable
    private final SocketAddress directServerAddress;
    ObjectPool<? extends Executor> executorPool = DEFAULT_EXECUTOR_POOL;
    boolean fullStreamDecompression;
    long idleTimeoutMillis = IDLE_MODE_DEFAULT_TIMEOUT_MILLIS;
    private final List<ClientInterceptor> interceptors = new ArrayList();
    @Nullable
    LoadBalancer.Factory loadBalancerFactory;
    int maxHedgedAttempts = 5;
    private int maxInboundMessageSize = 4194304;
    int maxRetryAttempts = 5;
    int maxTraceEvents;
    private Factory nameResolverFactory = DEFAULT_NAME_RESOLVER_FACTORY;
    long perRpcBufferLimit = 1048576;
    private boolean recordFinishedRpcs = true;
    private boolean recordStartedRpcs = true;
    long retryBufferSize = DEFAULT_RETRY_BUFFER_SIZE_IN_BYTES;
    boolean retryEnabled = false;
    private boolean statsEnabled = true;
    final String target;
    boolean temporarilyDisableRetry;
    private boolean tracingEnabled = true;
    protected TransportTracer.Factory transportTracerFactory = TransportTracer.getDefaultFactory();
    @Nullable
    String userAgent;

    /* renamed from: io.grpc.internal.AbstractManagedChannelImplBuilder$DirectAddressNameResolverFactory */
    private static class DirectAddressNameResolverFactory extends Factory {
        final SocketAddress address;
        final String authority;

        public String getDefaultScheme() {
            return AbstractManagedChannelImplBuilder.DIRECT_ADDRESS_SCHEME;
        }

        DirectAddressNameResolverFactory(SocketAddress socketAddress, String str) {
            this.address = socketAddress;
            this.authority = str;
        }

        public NameResolver newNameResolver(URI uri, Attributes attributes) {
            return new NameResolver() {
                public void shutdown() {
                }

                public String getServiceAuthority() {
                    return DirectAddressNameResolverFactory.this.authority;
                }

                public void start(Listener listener) {
                    listener.onAddresses(Collections.singletonList(new EquivalentAddressGroup(DirectAddressNameResolverFactory.this.address)), Attributes.EMPTY);
                }
            };
        }
    }

    private T thisT() {
        return this;
    }

    /* access modifiers changed from: protected */
    public abstract ClientTransportFactory buildTransportFactory();

    public static ManagedChannelBuilder<?> forAddress(String str, int i) {
        throw new UnsupportedOperationException("Subclass failed to hide static factory");
    }

    public static ManagedChannelBuilder<?> forTarget(String str) {
        throw new UnsupportedOperationException("Subclass failed to hide static factory");
    }

    public T maxInboundMessageSize(int i) {
        Preconditions.checkArgument(i >= 0, "negative max");
        this.maxInboundMessageSize = i;
        return thisT();
    }

    /* access modifiers changed from: protected */
    public final int maxInboundMessageSize() {
        return this.maxInboundMessageSize;
    }

    protected AbstractManagedChannelImplBuilder(String str) {
        this.target = (String) Preconditions.checkNotNull(str, "target");
        this.directServerAddress = null;
    }

    static String makeTargetStringForDirectAddress(SocketAddress socketAddress) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("/");
            sb.append(socketAddress);
            return new URI(DIRECT_ADDRESS_SCHEME, "", sb.toString(), null).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected AbstractManagedChannelImplBuilder(SocketAddress socketAddress, String str) {
        this.target = makeTargetStringForDirectAddress(socketAddress);
        this.directServerAddress = socketAddress;
        this.nameResolverFactory = new DirectAddressNameResolverFactory(socketAddress, str);
    }

    public final T directExecutor() {
        return executor(MoreExecutors.directExecutor());
    }

    public final T executor(Executor executor) {
        if (executor != null) {
            this.executorPool = new FixedObjectPool(executor);
        } else {
            this.executorPool = DEFAULT_EXECUTOR_POOL;
        }
        return thisT();
    }

    public final T intercept(List<ClientInterceptor> list) {
        this.interceptors.addAll(list);
        return thisT();
    }

    public final T intercept(ClientInterceptor... clientInterceptorArr) {
        return intercept(Arrays.asList(clientInterceptorArr));
    }

    public final T nameResolverFactory(Factory factory) {
        Preconditions.checkState(this.directServerAddress == null, "directServerAddress is set (%s), which forbids the use of NameResolverFactory", (Object) this.directServerAddress);
        if (factory != null) {
            this.nameResolverFactory = factory;
        } else {
            this.nameResolverFactory = DEFAULT_NAME_RESOLVER_FACTORY;
        }
        return thisT();
    }

    public final T loadBalancerFactory(LoadBalancer.Factory factory) {
        Preconditions.checkState(this.directServerAddress == null, "directServerAddress is set (%s), which forbids the use of LoadBalancer.Factory", (Object) this.directServerAddress);
        this.loadBalancerFactory = factory;
        return thisT();
    }

    public final T enableFullStreamDecompression() {
        this.fullStreamDecompression = true;
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

    public final T userAgent(@Nullable String str) {
        this.userAgent = str;
        return thisT();
    }

    public final T overrideAuthority(String str) {
        this.authorityOverride = checkAuthority(str);
        return thisT();
    }

    public final T idleTimeout(long j, TimeUnit timeUnit) {
        Preconditions.checkArgument(j > 0, "idle timeout is %s, but must be positive", j);
        if (timeUnit.toDays(j) >= 30) {
            this.idleTimeoutMillis = -1;
        } else {
            this.idleTimeoutMillis = Math.max(timeUnit.toMillis(j), IDLE_MODE_MIN_TIMEOUT_MILLIS);
        }
        return thisT();
    }

    public final T maxRetryAttempts(int i) {
        this.maxRetryAttempts = i;
        return thisT();
    }

    public final T maxHedgedAttempts(int i) {
        this.maxHedgedAttempts = i;
        return thisT();
    }

    public final T retryBufferSize(long j) {
        Preconditions.checkArgument(j > 0, "retry buffer size must be positive");
        this.retryBufferSize = j;
        return thisT();
    }

    public final T perRpcBufferLimit(long j) {
        Preconditions.checkArgument(j > 0, "per RPC buffer limit must be positive");
        this.perRpcBufferLimit = j;
        return thisT();
    }

    public final T disableRetry() {
        this.retryEnabled = false;
        return thisT();
    }

    public final T enableRetry() {
        this.retryEnabled = true;
        return thisT();
    }

    public final T setBinaryLog(BinaryLog binaryLog) {
        this.binlog = binaryLog;
        return thisT();
    }

    public T maxTraceEvents(int i) {
        Preconditions.checkArgument(i >= 0, "maxTraceEvents must be non-negative");
        this.maxTraceEvents = i;
        return thisT();
    }

    /* access modifiers changed from: protected */
    public final T overrideCensusStatsModule(CensusStatsModule censusStatsModule) {
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

    /* access modifiers changed from: 0000 */
    public final long getIdleTimeoutMillis() {
        return this.idleTimeoutMillis;
    }

    /* access modifiers changed from: protected */
    public String checkAuthority(String str) {
        return GrpcUtil.checkAuthority(str);
    }

    public ManagedChannel build() {
        ManagedChannelImpl managedChannelImpl = new ManagedChannelImpl(this, buildTransportFactory(), new Provider(), SharedResourcePool.forResource(GrpcUtil.SHARED_CHANNEL_EXECUTOR), GrpcUtil.STOPWATCH_SUPPLIER, getEffectiveInterceptors(), TimeProvider.SYSTEM_TIME_PROVIDER);
        return new ManagedChannelOrphanWrapper(managedChannelImpl);
    }

    /* access modifiers changed from: 0000 */
    public final List<ClientInterceptor> getEffectiveInterceptors() {
        ArrayList arrayList = new ArrayList(this.interceptors);
        this.temporarilyDisableRetry = false;
        if (this.statsEnabled) {
            this.temporarilyDisableRetry = true;
            CensusStatsModule censusStatsModule = this.censusStatsOverride;
            if (censusStatsModule == null) {
                censusStatsModule = new CensusStatsModule(GrpcUtil.STOPWATCH_SUPPLIER, true);
            }
            arrayList.add(0, censusStatsModule.getClientInterceptor(this.recordStartedRpcs, this.recordFinishedRpcs));
        }
        if (this.tracingEnabled) {
            this.temporarilyDisableRetry = true;
            arrayList.add(0, new CensusTracingModule(Tracing.getTracer(), Tracing.getPropagationComponent().getBinaryFormat()).getClientInterceptor());
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public Attributes getNameResolverParams() {
        return Attributes.EMPTY;
    }

    /* access modifiers changed from: 0000 */
    public Factory getNameResolverFactory() {
        String str = this.authorityOverride;
        if (str == null) {
            return this.nameResolverFactory;
        }
        return new OverrideAuthorityNameResolverFactory(this.nameResolverFactory, str);
    }
}
