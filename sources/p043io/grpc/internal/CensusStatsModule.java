package p043io.grpc.internal;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import p043io.grpc.CallOptions;
import p043io.grpc.Channel;
import p043io.grpc.ClientCall;
import p043io.grpc.ClientCall.Listener;
import p043io.grpc.ClientInterceptor;
import p043io.grpc.ClientStreamTracer;
import p043io.grpc.ClientStreamTracer.Factory;
import p043io.grpc.Context;
import p043io.grpc.ForwardingClientCall.SimpleForwardingClientCall;
import p043io.grpc.ForwardingClientCallListener.SimpleForwardingClientCallListener;
import p043io.grpc.Metadata;
import p043io.grpc.Metadata.BinaryMarshaller;
import p043io.grpc.Metadata.Key;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.ServerStreamTracer;
import p043io.grpc.Status;
import p043io.opencensus.contrib.grpc.metrics.RpcMeasureConstants;
import p043io.opencensus.stats.Measure.MeasureDouble;
import p043io.opencensus.stats.MeasureMap;
import p043io.opencensus.stats.Stats;
import p043io.opencensus.stats.StatsRecorder;
import p043io.opencensus.tags.TagContext;
import p043io.opencensus.tags.TagValue;
import p043io.opencensus.tags.Tagger;
import p043io.opencensus.tags.Tags;
import p043io.opencensus.tags.propagation.TagContextBinarySerializer;
import p043io.opencensus.tags.propagation.TagContextSerializationException;
import p043io.opencensus.tags.unsafe.ContextUtils;

/* renamed from: io.grpc.internal.CensusStatsModule */
public final class CensusStatsModule {
    /* access modifiers changed from: private */
    public static final ClientTracer BLANK_CLIENT_TRACER = new ClientTracer();
    /* access modifiers changed from: private */
    public static final double NANOS_PER_MILLI = ((double) TimeUnit.MILLISECONDS.toNanos(1));
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(CensusStatsModule.class.getName());
    /* access modifiers changed from: private */
    public final boolean propagateTags;
    final Key<TagContext> statsHeader;
    /* access modifiers changed from: private */
    public final StatsRecorder statsRecorder;
    /* access modifiers changed from: private */
    public final Supplier<Stopwatch> stopwatchSupplier;
    /* access modifiers changed from: private */
    public final Tagger tagger;

    /* renamed from: io.grpc.internal.CensusStatsModule$ClientCallTracer */
    static final class ClientCallTracer extends Factory {
        @Nullable
        private static final AtomicIntegerFieldUpdater<ClientCallTracer> callEndedUpdater;
        @Nullable
        private static final AtomicReferenceFieldUpdater<ClientCallTracer, ClientTracer> streamTracerUpdater;
        private volatile int callEnded;
        private final CensusStatsModule module;
        private final TagContext parentCtx;
        private final boolean recordFinishedRpcs;
        private final TagContext startCtx;
        private final Stopwatch stopwatch;
        private volatile ClientTracer streamTracer;

        static {
            AtomicIntegerFieldUpdater<ClientCallTracer> atomicIntegerFieldUpdater;
            Class<ClientCallTracer> cls = ClientCallTracer.class;
            AtomicReferenceFieldUpdater<ClientCallTracer, ClientTracer> atomicReferenceFieldUpdater = null;
            try {
                AtomicReferenceFieldUpdater<ClientCallTracer, ClientTracer> newUpdater = AtomicReferenceFieldUpdater.newUpdater(cls, ClientTracer.class, "streamTracer");
                atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(cls, "callEnded");
                atomicReferenceFieldUpdater = newUpdater;
            } catch (Throwable th) {
                CensusStatsModule.logger.log(Level.SEVERE, "Creating atomic field updaters failed", th);
                atomicIntegerFieldUpdater = null;
            }
            streamTracerUpdater = atomicReferenceFieldUpdater;
            callEndedUpdater = atomicIntegerFieldUpdater;
        }

        ClientCallTracer(CensusStatsModule censusStatsModule, TagContext tagContext, String str, boolean z, boolean z2) {
            this.module = censusStatsModule;
            this.parentCtx = (TagContext) Preconditions.checkNotNull(tagContext);
            this.startCtx = censusStatsModule.tagger.toBuilder(tagContext).put(RpcMeasureConstants.RPC_METHOD, TagValue.create(str)).build();
            this.stopwatch = ((Stopwatch) censusStatsModule.stopwatchSupplier.get()).start();
            this.recordFinishedRpcs = z2;
            if (z) {
                censusStatsModule.statsRecorder.newMeasureMap().put(RpcMeasureConstants.RPC_CLIENT_STARTED_COUNT, 1).record(this.startCtx);
            }
        }

        public ClientStreamTracer newClientStreamTracer(CallOptions callOptions, Metadata metadata) {
            ClientTracer clientTracer = new ClientTracer();
            AtomicReferenceFieldUpdater<ClientCallTracer, ClientTracer> atomicReferenceFieldUpdater = streamTracerUpdater;
            String str = "Are you creating multiple streams per call? This class doesn't yet support this case";
            if (atomicReferenceFieldUpdater != null) {
                Preconditions.checkState(atomicReferenceFieldUpdater.compareAndSet(this, null, clientTracer), str);
            } else {
                Preconditions.checkState(this.streamTracer == null, str);
                this.streamTracer = clientTracer;
            }
            if (this.module.propagateTags) {
                metadata.discardAll(this.module.statsHeader);
                if (!this.module.tagger.empty().equals(this.parentCtx)) {
                    metadata.put(this.module.statsHeader, this.parentCtx);
                }
            }
            return clientTracer;
        }

        /* access modifiers changed from: 0000 */
        public void callEnded(Status status) {
            AtomicIntegerFieldUpdater<ClientCallTracer> atomicIntegerFieldUpdater = callEndedUpdater;
            if (atomicIntegerFieldUpdater != null) {
                if (atomicIntegerFieldUpdater.getAndSet(this, 1) != 0) {
                    return;
                }
            } else if (this.callEnded == 0) {
                this.callEnded = 1;
            } else {
                return;
            }
            if (this.recordFinishedRpcs) {
                this.stopwatch.stop();
                long elapsed = this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
                ClientTracer clientTracer = this.streamTracer;
                if (clientTracer == null) {
                    clientTracer = CensusStatsModule.BLANK_CLIENT_TRACER;
                }
                MeasureMap put = this.module.statsRecorder.newMeasureMap().put(RpcMeasureConstants.RPC_CLIENT_FINISHED_COUNT, 1);
                MeasureDouble measureDouble = RpcMeasureConstants.RPC_CLIENT_ROUNDTRIP_LATENCY;
                double d = (double) elapsed;
                double access$700 = CensusStatsModule.NANOS_PER_MILLI;
                Double.isNaN(d);
                MeasureMap put2 = put.put(measureDouble, d / access$700).put(RpcMeasureConstants.RPC_CLIENT_REQUEST_COUNT, clientTracer.outboundMessageCount).put(RpcMeasureConstants.RPC_CLIENT_RESPONSE_COUNT, clientTracer.inboundMessageCount).put(RpcMeasureConstants.RPC_CLIENT_REQUEST_BYTES, (double) clientTracer.outboundWireSize).put(RpcMeasureConstants.RPC_CLIENT_RESPONSE_BYTES, (double) clientTracer.inboundWireSize).put(RpcMeasureConstants.RPC_CLIENT_UNCOMPRESSED_REQUEST_BYTES, (double) clientTracer.outboundUncompressedSize).put(RpcMeasureConstants.RPC_CLIENT_UNCOMPRESSED_RESPONSE_BYTES, (double) clientTracer.inboundUncompressedSize);
                if (!status.isOk()) {
                    put2.put(RpcMeasureConstants.RPC_CLIENT_ERROR_COUNT, 1);
                }
                put2.record(this.module.tagger.toBuilder(this.startCtx).put(RpcMeasureConstants.RPC_STATUS, TagValue.create(status.getCode().toString())).build());
            }
        }
    }

    /* renamed from: io.grpc.internal.CensusStatsModule$ClientTracer */
    private static final class ClientTracer extends ClientStreamTracer {
        @Nullable
        private static final AtomicLongFieldUpdater<ClientTracer> inboundMessageCountUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ClientTracer> inboundUncompressedSizeUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ClientTracer> inboundWireSizeUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ClientTracer> outboundMessageCountUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ClientTracer> outboundUncompressedSizeUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ClientTracer> outboundWireSizeUpdater;
        volatile long inboundMessageCount;
        volatile long inboundUncompressedSize;
        volatile long inboundWireSize;
        volatile long outboundMessageCount;
        volatile long outboundUncompressedSize;
        volatile long outboundWireSize;

        private ClientTracer() {
        }

        static {
            AtomicLongFieldUpdater<ClientTracer> atomicLongFieldUpdater;
            AtomicLongFieldUpdater<ClientTracer> atomicLongFieldUpdater2;
            AtomicLongFieldUpdater<ClientTracer> atomicLongFieldUpdater3;
            AtomicLongFieldUpdater<ClientTracer> atomicLongFieldUpdater4;
            AtomicLongFieldUpdater<ClientTracer> atomicLongFieldUpdater5;
            Class<ClientTracer> cls = ClientTracer.class;
            AtomicLongFieldUpdater<ClientTracer> atomicLongFieldUpdater6 = null;
            try {
                AtomicLongFieldUpdater<ClientTracer> newUpdater = AtomicLongFieldUpdater.newUpdater(cls, "outboundMessageCount");
                atomicLongFieldUpdater4 = AtomicLongFieldUpdater.newUpdater(cls, "inboundMessageCount");
                atomicLongFieldUpdater3 = AtomicLongFieldUpdater.newUpdater(cls, "outboundWireSize");
                atomicLongFieldUpdater2 = AtomicLongFieldUpdater.newUpdater(cls, "inboundWireSize");
                atomicLongFieldUpdater = AtomicLongFieldUpdater.newUpdater(cls, "outboundUncompressedSize");
                atomicLongFieldUpdater5 = AtomicLongFieldUpdater.newUpdater(cls, "inboundUncompressedSize");
                atomicLongFieldUpdater6 = newUpdater;
            } catch (Throwable th) {
                CensusStatsModule.logger.log(Level.SEVERE, "Creating atomic field updaters failed", th);
                atomicLongFieldUpdater5 = null;
                atomicLongFieldUpdater4 = null;
                atomicLongFieldUpdater3 = null;
                atomicLongFieldUpdater2 = null;
                atomicLongFieldUpdater = null;
            }
            outboundMessageCountUpdater = atomicLongFieldUpdater6;
            inboundMessageCountUpdater = atomicLongFieldUpdater4;
            outboundWireSizeUpdater = atomicLongFieldUpdater3;
            inboundWireSizeUpdater = atomicLongFieldUpdater2;
            outboundUncompressedSizeUpdater = atomicLongFieldUpdater;
            inboundUncompressedSizeUpdater = atomicLongFieldUpdater5;
        }

        public void outboundWireSize(long j) {
            AtomicLongFieldUpdater<ClientTracer> atomicLongFieldUpdater = outboundWireSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.outboundWireSize += j;
            }
        }

        public void inboundWireSize(long j) {
            AtomicLongFieldUpdater<ClientTracer> atomicLongFieldUpdater = inboundWireSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.inboundWireSize += j;
            }
        }

        public void outboundUncompressedSize(long j) {
            AtomicLongFieldUpdater<ClientTracer> atomicLongFieldUpdater = outboundUncompressedSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.outboundUncompressedSize += j;
            }
        }

        public void inboundUncompressedSize(long j) {
            AtomicLongFieldUpdater<ClientTracer> atomicLongFieldUpdater = inboundUncompressedSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.inboundUncompressedSize += j;
            }
        }

        public void inboundMessage(int i) {
            AtomicLongFieldUpdater<ClientTracer> atomicLongFieldUpdater = inboundMessageCountUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndIncrement(this);
            } else {
                this.inboundMessageCount++;
            }
        }

        public void outboundMessage(int i) {
            AtomicLongFieldUpdater<ClientTracer> atomicLongFieldUpdater = outboundMessageCountUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndIncrement(this);
            } else {
                this.outboundMessageCount++;
            }
        }
    }

    /* renamed from: io.grpc.internal.CensusStatsModule$ServerTracer */
    private static final class ServerTracer extends ServerStreamTracer {
        @Nullable
        private static final AtomicLongFieldUpdater<ServerTracer> inboundMessageCountUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ServerTracer> inboundUncompressedSizeUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ServerTracer> inboundWireSizeUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ServerTracer> outboundMessageCountUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ServerTracer> outboundUncompressedSizeUpdater;
        @Nullable
        private static final AtomicLongFieldUpdater<ServerTracer> outboundWireSizeUpdater;
        @Nullable
        private static final AtomicIntegerFieldUpdater<ServerTracer> streamClosedUpdater;
        private volatile long inboundMessageCount;
        private volatile long inboundUncompressedSize;
        private volatile long inboundWireSize;
        private final CensusStatsModule module;
        private volatile long outboundMessageCount;
        private volatile long outboundUncompressedSize;
        private volatile long outboundWireSize;
        private final TagContext parentCtx;
        private final boolean recordFinishedRpcs;
        private final Stopwatch stopwatch;
        private volatile int streamClosed;
        private final Tagger tagger;

        static {
            AtomicLongFieldUpdater<ServerTracer> atomicLongFieldUpdater;
            AtomicLongFieldUpdater<ServerTracer> atomicLongFieldUpdater2;
            AtomicLongFieldUpdater<ServerTracer> atomicLongFieldUpdater3;
            AtomicLongFieldUpdater<ServerTracer> atomicLongFieldUpdater4;
            AtomicLongFieldUpdater<ServerTracer> atomicLongFieldUpdater5;
            AtomicLongFieldUpdater<ServerTracer> atomicLongFieldUpdater6;
            Class<ServerTracer> cls = ServerTracer.class;
            AtomicIntegerFieldUpdater<ServerTracer> atomicIntegerFieldUpdater = null;
            try {
                AtomicIntegerFieldUpdater<ServerTracer> newUpdater = AtomicIntegerFieldUpdater.newUpdater(cls, "streamClosed");
                atomicLongFieldUpdater5 = AtomicLongFieldUpdater.newUpdater(cls, "outboundMessageCount");
                atomicLongFieldUpdater4 = AtomicLongFieldUpdater.newUpdater(cls, "inboundMessageCount");
                atomicLongFieldUpdater3 = AtomicLongFieldUpdater.newUpdater(cls, "outboundWireSize");
                atomicLongFieldUpdater2 = AtomicLongFieldUpdater.newUpdater(cls, "inboundWireSize");
                atomicLongFieldUpdater = AtomicLongFieldUpdater.newUpdater(cls, "outboundUncompressedSize");
                atomicLongFieldUpdater6 = AtomicLongFieldUpdater.newUpdater(cls, "inboundUncompressedSize");
                atomicIntegerFieldUpdater = newUpdater;
            } catch (Throwable th) {
                CensusStatsModule.logger.log(Level.SEVERE, "Creating atomic field updaters failed", th);
                atomicLongFieldUpdater6 = null;
                atomicLongFieldUpdater5 = null;
                atomicLongFieldUpdater4 = null;
                atomicLongFieldUpdater3 = null;
                atomicLongFieldUpdater2 = null;
                atomicLongFieldUpdater = null;
            }
            streamClosedUpdater = atomicIntegerFieldUpdater;
            outboundMessageCountUpdater = atomicLongFieldUpdater5;
            inboundMessageCountUpdater = atomicLongFieldUpdater4;
            outboundWireSizeUpdater = atomicLongFieldUpdater3;
            inboundWireSizeUpdater = atomicLongFieldUpdater2;
            outboundUncompressedSizeUpdater = atomicLongFieldUpdater;
            inboundUncompressedSizeUpdater = atomicLongFieldUpdater6;
        }

        ServerTracer(CensusStatsModule censusStatsModule, TagContext tagContext, Supplier<Stopwatch> supplier, Tagger tagger2, boolean z, boolean z2) {
            this.module = censusStatsModule;
            this.parentCtx = (TagContext) Preconditions.checkNotNull(tagContext, "parentCtx");
            this.stopwatch = ((Stopwatch) supplier.get()).start();
            this.tagger = tagger2;
            this.recordFinishedRpcs = z2;
            if (z) {
                censusStatsModule.statsRecorder.newMeasureMap().put(RpcMeasureConstants.RPC_SERVER_STARTED_COUNT, 1).record(tagContext);
            }
        }

        public void outboundWireSize(long j) {
            AtomicLongFieldUpdater<ServerTracer> atomicLongFieldUpdater = outboundWireSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.outboundWireSize += j;
            }
        }

        public void inboundWireSize(long j) {
            AtomicLongFieldUpdater<ServerTracer> atomicLongFieldUpdater = inboundWireSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.inboundWireSize += j;
            }
        }

        public void outboundUncompressedSize(long j) {
            AtomicLongFieldUpdater<ServerTracer> atomicLongFieldUpdater = outboundUncompressedSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.outboundUncompressedSize += j;
            }
        }

        public void inboundUncompressedSize(long j) {
            AtomicLongFieldUpdater<ServerTracer> atomicLongFieldUpdater = inboundUncompressedSizeUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndAdd(this, j);
            } else {
                this.inboundUncompressedSize += j;
            }
        }

        public void inboundMessage(int i) {
            AtomicLongFieldUpdater<ServerTracer> atomicLongFieldUpdater = inboundMessageCountUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndIncrement(this);
            } else {
                this.inboundMessageCount++;
            }
        }

        public void outboundMessage(int i) {
            AtomicLongFieldUpdater<ServerTracer> atomicLongFieldUpdater = outboundMessageCountUpdater;
            if (atomicLongFieldUpdater != null) {
                atomicLongFieldUpdater.getAndIncrement(this);
            } else {
                this.outboundMessageCount++;
            }
        }

        public void streamClosed(Status status) {
            AtomicIntegerFieldUpdater<ServerTracer> atomicIntegerFieldUpdater = streamClosedUpdater;
            if (atomicIntegerFieldUpdater != null) {
                if (atomicIntegerFieldUpdater.getAndSet(this, 1) != 0) {
                    return;
                }
            } else if (this.streamClosed == 0) {
                this.streamClosed = 1;
            } else {
                return;
            }
            if (this.recordFinishedRpcs) {
                this.stopwatch.stop();
                long elapsed = this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
                MeasureMap put = this.module.statsRecorder.newMeasureMap().put(RpcMeasureConstants.RPC_SERVER_FINISHED_COUNT, 1);
                MeasureDouble measureDouble = RpcMeasureConstants.RPC_SERVER_SERVER_LATENCY;
                double d = (double) elapsed;
                double access$700 = CensusStatsModule.NANOS_PER_MILLI;
                Double.isNaN(d);
                MeasureMap put2 = put.put(measureDouble, d / access$700).put(RpcMeasureConstants.RPC_SERVER_RESPONSE_COUNT, this.outboundMessageCount).put(RpcMeasureConstants.RPC_SERVER_REQUEST_COUNT, this.inboundMessageCount).put(RpcMeasureConstants.RPC_SERVER_RESPONSE_BYTES, (double) this.outboundWireSize).put(RpcMeasureConstants.RPC_SERVER_REQUEST_BYTES, (double) this.inboundWireSize).put(RpcMeasureConstants.RPC_SERVER_UNCOMPRESSED_RESPONSE_BYTES, (double) this.outboundUncompressedSize).put(RpcMeasureConstants.RPC_SERVER_UNCOMPRESSED_REQUEST_BYTES, (double) this.inboundUncompressedSize);
                if (!status.isOk()) {
                    put2.put(RpcMeasureConstants.RPC_SERVER_ERROR_COUNT, 1);
                }
                put2.record(this.module.tagger.toBuilder(this.parentCtx).put(RpcMeasureConstants.RPC_STATUS, TagValue.create(status.getCode().toString())).build());
            }
        }

        public Context filterContext(Context context) {
            return !this.tagger.empty().equals(this.parentCtx) ? context.withValue(ContextUtils.TAG_CONTEXT_KEY, this.parentCtx) : context;
        }
    }

    /* renamed from: io.grpc.internal.CensusStatsModule$ServerTracerFactory */
    final class ServerTracerFactory extends ServerStreamTracer.Factory {
        private final boolean recordFinishedRpcs;
        private final boolean recordStartedRpcs;

        ServerTracerFactory(boolean z, boolean z2) {
            this.recordStartedRpcs = z;
            this.recordFinishedRpcs = z2;
        }

        public ServerStreamTracer newServerStreamTracer(String str, Metadata metadata) {
            TagContext tagContext = (TagContext) metadata.get(CensusStatsModule.this.statsHeader);
            if (tagContext == null) {
                tagContext = CensusStatsModule.this.tagger.empty();
            }
            TagContext build = CensusStatsModule.this.tagger.toBuilder(tagContext).put(RpcMeasureConstants.RPC_METHOD, TagValue.create(str)).build();
            CensusStatsModule censusStatsModule = CensusStatsModule.this;
            ServerTracer serverTracer = new ServerTracer(censusStatsModule, build, censusStatsModule.stopwatchSupplier, CensusStatsModule.this.tagger, this.recordStartedRpcs, this.recordFinishedRpcs);
            return serverTracer;
        }
    }

    /* renamed from: io.grpc.internal.CensusStatsModule$StatsClientInterceptor */
    final class StatsClientInterceptor implements ClientInterceptor {
        private final boolean recordFinishedRpcs;
        private final boolean recordStartedRpcs;

        StatsClientInterceptor(boolean z, boolean z2) {
            this.recordStartedRpcs = z;
            this.recordFinishedRpcs = z2;
        }

        public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
            final ClientCallTracer newClientCallTracer = CensusStatsModule.this.newClientCallTracer(CensusStatsModule.this.tagger.getCurrentTagContext(), methodDescriptor.getFullMethodName(), this.recordStartedRpcs, this.recordFinishedRpcs);
            return new SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions.withStreamTracerFactory(newClientCallTracer))) {
                public void start(Listener<RespT> listener, Metadata metadata) {
                    delegate().start(new SimpleForwardingClientCallListener<RespT>(listener) {
                        public void onClose(Status status, Metadata metadata) {
                            newClientCallTracer.callEnded(status);
                            super.onClose(status, metadata);
                        }
                    }, metadata);
                }
            };
        }
    }

    CensusStatsModule(Supplier<Stopwatch> supplier, boolean z) {
        this(Tags.getTagger(), Tags.getTagPropagationComponent().getBinarySerializer(), Stats.getStatsRecorder(), supplier, z);
    }

    public CensusStatsModule(final Tagger tagger2, final TagContextBinarySerializer tagContextBinarySerializer, StatsRecorder statsRecorder2, Supplier<Stopwatch> supplier, boolean z) {
        this.tagger = (Tagger) Preconditions.checkNotNull(tagger2, "tagger");
        this.statsRecorder = (StatsRecorder) Preconditions.checkNotNull(statsRecorder2, "statsRecorder");
        Preconditions.checkNotNull(tagContextBinarySerializer, "tagCtxSerializer");
        this.stopwatchSupplier = (Supplier) Preconditions.checkNotNull(supplier, "stopwatchSupplier");
        this.propagateTags = z;
        this.statsHeader = Key.m3993of("grpc-tags-bin", (BinaryMarshaller<T>) new BinaryMarshaller<TagContext>() {
            public byte[] toBytes(TagContext tagContext) {
                try {
                    return tagContextBinarySerializer.toByteArray(tagContext);
                } catch (TagContextSerializationException e) {
                    throw new RuntimeException(e);
                }
            }

            public TagContext parseBytes(byte[] bArr) {
                try {
                    return tagContextBinarySerializer.fromByteArray(bArr);
                } catch (Exception e) {
                    CensusStatsModule.logger.log(Level.FINE, "Failed to parse stats header", e);
                    return tagger2.empty();
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public ClientCallTracer newClientCallTracer(TagContext tagContext, String str, boolean z, boolean z2) {
        ClientCallTracer clientCallTracer = new ClientCallTracer(this, tagContext, str, z, z2);
        return clientCallTracer;
    }

    /* access modifiers changed from: 0000 */
    public ServerStreamTracer.Factory getServerTracerFactory(boolean z, boolean z2) {
        return new ServerTracerFactory(z, z2);
    }

    /* access modifiers changed from: 0000 */
    public ClientInterceptor getClientInterceptor(boolean z, boolean z2) {
        return new StatsClientInterceptor(z, z2);
    }
}
