package p043io.grpc.okhttp;

import com.google.common.base.Preconditions;
import com.squareup.okhttp.CipherSuite;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.ConnectionSpec.Builder;
import com.squareup.okhttp.TlsVersion;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import p043io.grpc.Attributes;
import p043io.grpc.NameResolver;
import p043io.grpc.internal.AbstractManagedChannelImplBuilder;
import p043io.grpc.internal.AtomicBackoff;
import p043io.grpc.internal.AtomicBackoff.State;
import p043io.grpc.internal.ClientTransportFactory;
import p043io.grpc.internal.ClientTransportFactory.ClientTransportOptions;
import p043io.grpc.internal.ConnectionClientTransport;
import p043io.grpc.internal.GrpcUtil;
import p043io.grpc.internal.KeepAliveManager;
import p043io.grpc.internal.SharedResourceHolder;
import p043io.grpc.internal.SharedResourceHolder.Resource;
import p043io.grpc.internal.TransportTracer.Factory;
import p043io.grpc.okhttp.internal.Platform;

/* renamed from: io.grpc.okhttp.OkHttpChannelBuilder */
public class OkHttpChannelBuilder extends AbstractManagedChannelImplBuilder<OkHttpChannelBuilder> {
    private static final long AS_LARGE_AS_INFINITE = TimeUnit.DAYS.toNanos(1000);
    @Deprecated
    public static final ConnectionSpec DEFAULT_CONNECTION_SPEC = new Builder(ConnectionSpec.MODERN_TLS).cipherSuites(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384).tlsVersions(TlsVersion.TLS_1_2).supportsTlsExtensions(true).build();
    static final p043io.grpc.okhttp.internal.ConnectionSpec INTERNAL_DEFAULT_CONNECTION_SPEC = new p043io.grpc.okhttp.internal.ConnectionSpec.Builder(p043io.grpc.okhttp.internal.ConnectionSpec.MODERN_TLS).cipherSuites(p043io.grpc.okhttp.internal.CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384, p043io.grpc.okhttp.internal.CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, p043io.grpc.okhttp.internal.CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384, p043io.grpc.okhttp.internal.CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, p043io.grpc.okhttp.internal.CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, p043io.grpc.okhttp.internal.CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256, p043io.grpc.okhttp.internal.CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384, p043io.grpc.okhttp.internal.CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384).tlsVersions(p043io.grpc.okhttp.internal.TlsVersion.TLS_1_2).supportsTlsExtensions(true).build();
    /* access modifiers changed from: private */
    public static final Resource<ExecutorService> SHARED_EXECUTOR = new Resource<ExecutorService>() {
        public ExecutorService create() {
            return Executors.newCachedThreadPool(GrpcUtil.getThreadFactory("grpc-okhttp-%d", true));
        }

        public void close(ExecutorService executorService) {
            executorService.shutdown();
        }
    };
    private p043io.grpc.okhttp.internal.ConnectionSpec connectionSpec;
    private HostnameVerifier hostnameVerifier;
    private long keepAliveTimeNanos;
    private long keepAliveTimeoutNanos;
    private boolean keepAliveWithoutCalls;
    private NegotiationType negotiationType;
    private ScheduledExecutorService scheduledExecutorService;
    private SSLSocketFactory sslSocketFactory;
    private Executor transportExecutor;

    /* renamed from: io.grpc.okhttp.OkHttpChannelBuilder$2 */
    static /* synthetic */ class C54922 {
        static final /* synthetic */ int[] $SwitchMap$io$grpc$okhttp$NegotiationType = new int[NegotiationType.values().length];
        static final /* synthetic */ int[] $SwitchMap$io$grpc$okhttp$OkHttpChannelBuilder$NegotiationType = new int[NegotiationType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(11:0|1|2|3|(2:5|6)|7|9|10|11|12|14) */
        /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0032 */
        static {
            /*
                io.grpc.okhttp.OkHttpChannelBuilder$NegotiationType[] r0 = p043io.grpc.okhttp.OkHttpChannelBuilder.NegotiationType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$grpc$okhttp$OkHttpChannelBuilder$NegotiationType = r0
                r0 = 1
                int[] r1 = $SwitchMap$io$grpc$okhttp$OkHttpChannelBuilder$NegotiationType     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.grpc.okhttp.OkHttpChannelBuilder$NegotiationType r2 = p043io.grpc.okhttp.OkHttpChannelBuilder.NegotiationType.PLAINTEXT     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                r1 = 2
                int[] r2 = $SwitchMap$io$grpc$okhttp$OkHttpChannelBuilder$NegotiationType     // Catch:{ NoSuchFieldError -> 0x001f }
                io.grpc.okhttp.OkHttpChannelBuilder$NegotiationType r3 = p043io.grpc.okhttp.OkHttpChannelBuilder.NegotiationType.TLS     // Catch:{ NoSuchFieldError -> 0x001f }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2[r3] = r1     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                io.grpc.okhttp.NegotiationType[] r2 = p043io.grpc.okhttp.NegotiationType.values()
                int r2 = r2.length
                int[] r2 = new int[r2]
                $SwitchMap$io$grpc$okhttp$NegotiationType = r2
                int[] r2 = $SwitchMap$io$grpc$okhttp$NegotiationType     // Catch:{ NoSuchFieldError -> 0x0032 }
                io.grpc.okhttp.NegotiationType r3 = p043io.grpc.okhttp.NegotiationType.TLS     // Catch:{ NoSuchFieldError -> 0x0032 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x0032 }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x0032 }
            L_0x0032:
                int[] r0 = $SwitchMap$io$grpc$okhttp$NegotiationType     // Catch:{ NoSuchFieldError -> 0x003c }
                io.grpc.okhttp.NegotiationType r2 = p043io.grpc.okhttp.NegotiationType.PLAINTEXT     // Catch:{ NoSuchFieldError -> 0x003c }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x003c }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x003c }
            L_0x003c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.okhttp.OkHttpChannelBuilder.C54922.<clinit>():void");
        }
    }

    /* renamed from: io.grpc.okhttp.OkHttpChannelBuilder$NegotiationType */
    private enum NegotiationType {
        TLS,
        PLAINTEXT
    }

    /* renamed from: io.grpc.okhttp.OkHttpChannelBuilder$OkHttpTransportFactory */
    static final class OkHttpTransportFactory implements ClientTransportFactory {
        private boolean closed;
        private final p043io.grpc.okhttp.internal.ConnectionSpec connectionSpec;
        private final boolean enableKeepAlive;
        private final Executor executor;
        @Nullable
        private final HostnameVerifier hostnameVerifier;
        private final AtomicBackoff keepAliveTimeNanos;
        private final long keepAliveTimeoutNanos;
        private final boolean keepAliveWithoutCalls;
        private final int maxMessageSize;
        @Nullable
        private final SSLSocketFactory socketFactory;
        private final ScheduledExecutorService timeoutService;
        private final Factory transportTracerFactory;
        private final boolean usingSharedExecutor;
        private final boolean usingSharedScheduler;

        private OkHttpTransportFactory(Executor executor2, @Nullable ScheduledExecutorService scheduledExecutorService, @Nullable SSLSocketFactory sSLSocketFactory, @Nullable HostnameVerifier hostnameVerifier2, p043io.grpc.okhttp.internal.ConnectionSpec connectionSpec2, int i, boolean z, long j, long j2, boolean z2, Factory factory) {
            Executor executor3 = executor2;
            boolean z3 = true;
            this.usingSharedScheduler = scheduledExecutorService == null;
            this.timeoutService = this.usingSharedScheduler ? (ScheduledExecutorService) SharedResourceHolder.get(GrpcUtil.TIMER_SERVICE) : scheduledExecutorService;
            this.socketFactory = sSLSocketFactory;
            this.hostnameVerifier = hostnameVerifier2;
            this.connectionSpec = connectionSpec2;
            this.maxMessageSize = i;
            this.enableKeepAlive = z;
            this.keepAliveTimeNanos = new AtomicBackoff("keepalive time nanos", j);
            this.keepAliveTimeoutNanos = j2;
            this.keepAliveWithoutCalls = z2;
            if (executor3 != null) {
                z3 = false;
            }
            this.usingSharedExecutor = z3;
            this.transportTracerFactory = (Factory) Preconditions.checkNotNull(factory, "transportTracerFactory");
            if (this.usingSharedExecutor) {
                this.executor = (Executor) SharedResourceHolder.get(OkHttpChannelBuilder.SHARED_EXECUTOR);
            } else {
                this.executor = executor3;
            }
        }

        public ConnectionClientTransport newClientTransport(SocketAddress socketAddress, ClientTransportOptions clientTransportOptions) {
            if (!this.closed) {
                final State state = this.keepAliveTimeNanos.getState();
                OkHttpClientTransport okHttpClientTransport = new OkHttpClientTransport((InetSocketAddress) socketAddress, clientTransportOptions.getAuthority(), clientTransportOptions.getUserAgent(), this.executor, this.socketFactory, this.hostnameVerifier, this.connectionSpec, this.maxMessageSize, clientTransportOptions.getProxyParameters(), new Runnable() {
                    public void run() {
                        state.backoff();
                    }
                }, this.transportTracerFactory.create());
                if (this.enableKeepAlive) {
                    okHttpClientTransport.enableKeepAlive(true, state.get(), this.keepAliveTimeoutNanos, this.keepAliveWithoutCalls);
                }
                return okHttpClientTransport;
            }
            throw new IllegalStateException("The transport factory is closed.");
        }

        public ScheduledExecutorService getScheduledExecutorService() {
            return this.timeoutService;
        }

        public void close() {
            if (!this.closed) {
                this.closed = true;
                if (this.usingSharedScheduler) {
                    SharedResourceHolder.release(GrpcUtil.TIMER_SERVICE, this.timeoutService);
                }
                if (this.usingSharedExecutor) {
                    SharedResourceHolder.release(OkHttpChannelBuilder.SHARED_EXECUTOR, (ExecutorService) this.executor);
                }
            }
        }
    }

    public static OkHttpChannelBuilder forAddress(String str, int i) {
        return new OkHttpChannelBuilder(str, i);
    }

    public static OkHttpChannelBuilder forTarget(String str) {
        return new OkHttpChannelBuilder(str);
    }

    protected OkHttpChannelBuilder(String str, int i) {
        this(GrpcUtil.authorityFromHostAndPort(str, i));
    }

    private OkHttpChannelBuilder(String str) {
        super(str);
        this.connectionSpec = INTERNAL_DEFAULT_CONNECTION_SPEC;
        this.negotiationType = NegotiationType.TLS;
        this.keepAliveTimeNanos = Long.MAX_VALUE;
        this.keepAliveTimeoutNanos = GrpcUtil.DEFAULT_KEEPALIVE_TIMEOUT_NANOS;
    }

    /* access modifiers changed from: 0000 */
    public final OkHttpChannelBuilder setTransportTracerFactory(Factory factory) {
        this.transportTracerFactory = factory;
        return this;
    }

    public final OkHttpChannelBuilder transportExecutor(@Nullable Executor executor) {
        this.transportExecutor = executor;
        return this;
    }

    @Deprecated
    public final OkHttpChannelBuilder negotiationType(NegotiationType negotiationType2) {
        Preconditions.checkNotNull(negotiationType2, "type");
        int i = C54922.$SwitchMap$io$grpc$okhttp$NegotiationType[negotiationType2.ordinal()];
        if (i == 1) {
            this.negotiationType = NegotiationType.TLS;
        } else if (i == 2) {
            this.negotiationType = NegotiationType.PLAINTEXT;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown negotiation type: ");
            sb.append(negotiationType2);
            throw new AssertionError(sb.toString());
        }
        return this;
    }

    @Deprecated
    public final OkHttpChannelBuilder enableKeepAlive(boolean z) {
        if (z) {
            return keepAliveTime(GrpcUtil.DEFAULT_KEEPALIVE_TIME_NANOS, TimeUnit.NANOSECONDS);
        }
        return keepAliveTime(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    @Deprecated
    public final OkHttpChannelBuilder enableKeepAlive(boolean z, long j, TimeUnit timeUnit, long j2, TimeUnit timeUnit2) {
        if (z) {
            return keepAliveTime(j, timeUnit).keepAliveTimeout(j2, timeUnit2);
        }
        return keepAliveTime(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

    public OkHttpChannelBuilder keepAliveTime(long j, TimeUnit timeUnit) {
        Preconditions.checkArgument(j > 0, "keepalive time must be positive");
        this.keepAliveTimeNanos = timeUnit.toNanos(j);
        this.keepAliveTimeNanos = KeepAliveManager.clampKeepAliveTimeInNanos(this.keepAliveTimeNanos);
        if (this.keepAliveTimeNanos >= AS_LARGE_AS_INFINITE) {
            this.keepAliveTimeNanos = Long.MAX_VALUE;
        }
        return this;
    }

    public OkHttpChannelBuilder keepAliveTimeout(long j, TimeUnit timeUnit) {
        Preconditions.checkArgument(j > 0, "keepalive timeout must be positive");
        this.keepAliveTimeoutNanos = timeUnit.toNanos(j);
        this.keepAliveTimeoutNanos = KeepAliveManager.clampKeepAliveTimeoutInNanos(this.keepAliveTimeoutNanos);
        return this;
    }

    public OkHttpChannelBuilder keepAliveWithoutCalls(boolean z) {
        this.keepAliveWithoutCalls = z;
        return this;
    }

    public final OkHttpChannelBuilder sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.sslSocketFactory = sSLSocketFactory;
        this.negotiationType = NegotiationType.TLS;
        return this;
    }

    public final OkHttpChannelBuilder hostnameVerifier(@Nullable HostnameVerifier hostnameVerifier2) {
        this.hostnameVerifier = hostnameVerifier2;
        return this;
    }

    public final OkHttpChannelBuilder connectionSpec(ConnectionSpec connectionSpec2) {
        Preconditions.checkArgument(connectionSpec2.isTls(), "plaintext ConnectionSpec is not accepted");
        this.connectionSpec = C5498Utils.convertSpec(connectionSpec2);
        return this;
    }

    @Deprecated
    public final OkHttpChannelBuilder usePlaintext(boolean z) {
        if (z) {
            negotiationType(NegotiationType.PLAINTEXT);
            return this;
        }
        throw new IllegalArgumentException("Plaintext negotiation not currently supported");
    }

    public final OkHttpChannelBuilder usePlaintext() {
        this.negotiationType = NegotiationType.PLAINTEXT;
        return this;
    }

    public final OkHttpChannelBuilder useTransportSecurity() {
        this.negotiationType = NegotiationType.TLS;
        return this;
    }

    public final OkHttpChannelBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService2) {
        this.scheduledExecutorService = (ScheduledExecutorService) Preconditions.checkNotNull(scheduledExecutorService2, "scheduledExecutorService");
        return this;
    }

    /* access modifiers changed from: protected */
    public final ClientTransportFactory buildTransportFactory() {
        OkHttpTransportFactory okHttpTransportFactory = new OkHttpTransportFactory(this.transportExecutor, this.scheduledExecutorService, createSocketFactory(), this.hostnameVerifier, this.connectionSpec, maxInboundMessageSize(), this.keepAliveTimeNanos != Long.MAX_VALUE, this.keepAliveTimeNanos, this.keepAliveTimeoutNanos, this.keepAliveWithoutCalls, this.transportTracerFactory);
        return okHttpTransportFactory;
    }

    /* access modifiers changed from: protected */
    public Attributes getNameResolverParams() {
        int i;
        int i2 = C54922.$SwitchMap$io$grpc$okhttp$OkHttpChannelBuilder$NegotiationType[this.negotiationType.ordinal()];
        if (i2 == 1) {
            i = 80;
        } else if (i2 == 2) {
            i = GrpcUtil.DEFAULT_PORT_SSL;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(this.negotiationType);
            sb.append(" not handled");
            throw new AssertionError(sb.toString());
        }
        return Attributes.newBuilder().set(NameResolver.Factory.PARAMS_DEFAULT_PORT, Integer.valueOf(i)).build();
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public SSLSocketFactory createSocketFactory() {
        SSLContext sSLContext;
        int i = C54922.$SwitchMap$io$grpc$okhttp$OkHttpChannelBuilder$NegotiationType[this.negotiationType.ordinal()];
        if (i == 1) {
            return null;
        }
        if (i == 2) {
            try {
                if (this.sslSocketFactory == null) {
                    if (GrpcUtil.IS_RESTRICTED_APPENGINE) {
                        sSLContext = SSLContext.getInstance("TLS", Platform.get().getProvider());
                        TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                        instance.init(null);
                        sSLContext.init(null, instance.getTrustManagers(), SecureRandom.getInstance("SHA1PRNG", Platform.get().getProvider()));
                    } else {
                        sSLContext = SSLContext.getInstance("Default", Platform.get().getProvider());
                    }
                    this.sslSocketFactory = sSLContext.getSocketFactory();
                }
                return this.sslSocketFactory;
            } catch (GeneralSecurityException e) {
                throw new RuntimeException("TLS Provider failure", e);
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown negotiation type: ");
            sb.append(this.negotiationType);
            throw new RuntimeException(sb.toString());
        }
    }
}
