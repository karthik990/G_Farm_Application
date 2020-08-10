package p043io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import p043io.grpc.Attributes;
import p043io.grpc.CallOptions;
import p043io.grpc.ClientCall;
import p043io.grpc.ConnectivityState;
import p043io.grpc.ConnectivityStateInfo;
import p043io.grpc.Context;
import p043io.grpc.EquivalentAddressGroup;
import p043io.grpc.InternalChannelz;
import p043io.grpc.InternalChannelz.ChannelStats;
import p043io.grpc.InternalChannelz.ChannelTrace.Event.Builder;
import p043io.grpc.InternalChannelz.ChannelTrace.Event.Severity;
import p043io.grpc.InternalInstrumented;
import p043io.grpc.InternalLogId;
import p043io.grpc.LoadBalancer.PickResult;
import p043io.grpc.LoadBalancer.PickSubchannelArgs;
import p043io.grpc.LoadBalancer.Subchannel;
import p043io.grpc.LoadBalancer.SubchannelPicker;
import p043io.grpc.ManagedChannel;
import p043io.grpc.Metadata;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.Status;
import p043io.grpc.internal.ManagedClientTransport.Listener;

/* renamed from: io.grpc.internal.OobChannel */
final class OobChannel extends ManagedChannel implements InternalInstrumented<ChannelStats> {
    private static final Logger log = Logger.getLogger(OobChannel.class.getName());
    private final String authority;
    private final CallTracer channelCallsTracer;
    @CheckForNull
    private final ChannelTracer channelTracer;
    private final InternalChannelz channelz;
    private final ScheduledExecutorService deadlineCancellationExecutor;
    /* access modifiers changed from: private */
    public final DelayedClientTransport delayedTransport;
    private final Executor executor;
    private final ObjectPool<? extends Executor> executorPool;
    private final InternalLogId logId = InternalLogId.allocate(getClass().getName());
    private volatile boolean shutdown;
    private InternalSubchannel subchannel;
    /* access modifiers changed from: private */
    public AbstractSubchannel subchannelImpl;
    private SubchannelPicker subchannelPicker;
    private final CountDownLatch terminatedLatch = new CountDownLatch(1);
    private final TimeProvider timeProvider;
    private final ClientTransportProvider transportProvider = new ClientTransportProvider() {
        public ClientTransport get(PickSubchannelArgs pickSubchannelArgs) {
            return OobChannel.this.delayedTransport;
        }

        public <ReqT> RetriableStream<ReqT> newRetriableStream(MethodDescriptor<ReqT, ?> methodDescriptor, CallOptions callOptions, Metadata metadata, Context context) {
            throw new UnsupportedOperationException("OobChannel should not create retriable streams");
        }
    };

    /* renamed from: io.grpc.internal.OobChannel$6 */
    static /* synthetic */ class C54636 {
        static final /* synthetic */ int[] $SwitchMap$io$grpc$ConnectivityState = new int[ConnectivityState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                io.grpc.ConnectivityState[] r0 = p043io.grpc.ConnectivityState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$grpc$ConnectivityState = r0
                int[] r0 = $SwitchMap$io$grpc$ConnectivityState     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.grpc.ConnectivityState r1 = p043io.grpc.ConnectivityState.READY     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$grpc$ConnectivityState     // Catch:{ NoSuchFieldError -> 0x001f }
                io.grpc.ConnectivityState r1 = p043io.grpc.ConnectivityState.IDLE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$io$grpc$ConnectivityState     // Catch:{ NoSuchFieldError -> 0x002a }
                io.grpc.ConnectivityState r1 = p043io.grpc.ConnectivityState.TRANSIENT_FAILURE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.internal.OobChannel.C54636.<clinit>():void");
        }
    }

    OobChannel(String str, ObjectPool<? extends Executor> objectPool, ScheduledExecutorService scheduledExecutorService, ChannelExecutor channelExecutor, CallTracer callTracer, @Nullable ChannelTracer channelTracer2, InternalChannelz internalChannelz, TimeProvider timeProvider2) {
        this.authority = (String) Preconditions.checkNotNull(str, "authority");
        this.executorPool = (ObjectPool) Preconditions.checkNotNull(objectPool, "executorPool");
        this.executor = (Executor) Preconditions.checkNotNull(objectPool.getObject(), "executor");
        this.deadlineCancellationExecutor = (ScheduledExecutorService) Preconditions.checkNotNull(scheduledExecutorService, "deadlineCancellationExecutor");
        this.delayedTransport = new DelayedClientTransport(this.executor, channelExecutor);
        this.channelz = (InternalChannelz) Preconditions.checkNotNull(internalChannelz);
        this.delayedTransport.start(new Listener() {
            public void transportInUse(boolean z) {
            }

            public void transportReady() {
            }

            public void transportShutdown(Status status) {
            }

            public void transportTerminated() {
                OobChannel.this.subchannelImpl.shutdown();
            }
        });
        this.channelCallsTracer = callTracer;
        this.channelTracer = channelTracer2;
        this.timeProvider = timeProvider2;
    }

    /* access modifiers changed from: 0000 */
    public void setSubchannel(final InternalSubchannel internalSubchannel) {
        log.log(Level.FINE, "[{0}] Created with [{1}]", new Object[]{this, internalSubchannel});
        this.subchannel = internalSubchannel;
        this.subchannelImpl = new AbstractSubchannel() {
            public void shutdown() {
                internalSubchannel.shutdown(Status.UNAVAILABLE.withDescription("OobChannel is shutdown"));
            }

            /* access modifiers changed from: 0000 */
            public ClientTransport obtainActiveTransport() {
                return internalSubchannel.obtainActiveTransport();
            }

            /* access modifiers changed from: 0000 */
            public InternalInstrumented<ChannelStats> getInternalSubchannel() {
                return internalSubchannel;
            }

            public void requestConnection() {
                internalSubchannel.obtainActiveTransport();
            }

            public List<EquivalentAddressGroup> getAllAddresses() {
                return internalSubchannel.getAddressGroups();
            }

            public Attributes getAttributes() {
                return Attributes.EMPTY;
            }
        };
        this.subchannelPicker = new SubchannelPicker() {
            final PickResult result = PickResult.withSubchannel(OobChannel.this.subchannelImpl);

            public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
                return this.result;
            }
        };
        this.delayedTransport.reprocess(this.subchannelPicker);
    }

    /* access modifiers changed from: 0000 */
    public void updateAddresses(EquivalentAddressGroup equivalentAddressGroup) {
        this.subchannel.updateAddresses(Collections.singletonList(equivalentAddressGroup));
    }

    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
        ClientCallImpl clientCallImpl = new ClientCallImpl(methodDescriptor, callOptions.getExecutor() == null ? this.executor : callOptions.getExecutor(), callOptions, this.transportProvider, this.deadlineCancellationExecutor, this.channelCallsTracer, false);
        return clientCallImpl;
    }

    public String authority() {
        return this.authority;
    }

    public boolean isTerminated() {
        return this.terminatedLatch.getCount() == 0;
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.terminatedLatch.await(j, timeUnit);
    }

    public ConnectivityState getState(boolean z) {
        InternalSubchannel internalSubchannel = this.subchannel;
        if (internalSubchannel == null) {
            return ConnectivityState.IDLE;
        }
        return internalSubchannel.getState();
    }

    public ManagedChannel shutdown() {
        this.shutdown = true;
        this.delayedTransport.shutdown(Status.UNAVAILABLE.withDescription("OobChannel.shutdown() called"));
        return this;
    }

    public boolean isShutdown() {
        return this.shutdown;
    }

    public ManagedChannel shutdownNow() {
        this.shutdown = true;
        this.delayedTransport.shutdownNow(Status.UNAVAILABLE.withDescription("OobChannel.shutdownNow() called"));
        return this;
    }

    /* access modifiers changed from: 0000 */
    public void handleSubchannelStateChange(final ConnectivityStateInfo connectivityStateInfo) {
        ChannelTracer channelTracer2 = this.channelTracer;
        if (channelTracer2 != null) {
            Builder builder = new Builder();
            StringBuilder sb = new StringBuilder();
            sb.append("Entering ");
            sb.append(connectivityStateInfo.getState());
            sb.append(" state");
            channelTracer2.reportEvent(builder.setDescription(sb.toString()).setSeverity(Severity.CT_INFO).setTimestampNanos(this.timeProvider.currentTimeNanos()).build());
        }
        int i = C54636.$SwitchMap$io$grpc$ConnectivityState[connectivityStateInfo.getState().ordinal()];
        if (i == 1 || i == 2) {
            this.delayedTransport.reprocess(this.subchannelPicker);
        } else if (i == 3) {
            this.delayedTransport.reprocess(new SubchannelPicker() {
                final PickResult errorResult = PickResult.withError(connectivityStateInfo.getStatus());

                public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
                    return this.errorResult;
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public void handleSubchannelTerminated() {
        this.channelz.removeSubchannel(this);
        this.executorPool.returnObject(this.executor);
        this.terminatedLatch.countDown();
    }

    /* access modifiers changed from: 0000 */
    public Subchannel getSubchannel() {
        return this.subchannelImpl;
    }

    /* access modifiers changed from: 0000 */
    public InternalSubchannel getInternalSubchannel() {
        return this.subchannel;
    }

    public ListenableFuture<ChannelStats> getStats() {
        SettableFuture create = SettableFuture.create();
        ChannelStats.Builder builder = new ChannelStats.Builder();
        this.channelCallsTracer.updateBuilder(builder);
        ChannelTracer channelTracer2 = this.channelTracer;
        if (channelTracer2 != null) {
            channelTracer2.updateBuilder(builder);
        }
        builder.setTarget(this.authority).setState(this.subchannel.getState()).setSubchannels(Collections.singletonList(this.subchannel));
        create.set(builder.build());
        return create;
    }

    public InternalLogId getLogId() {
        return this.logId;
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("logId", this.logId.getId()).add("authority", (Object) this.authority).toString();
    }

    public void resetConnectBackoff() {
        this.subchannel.resetConnectBackoff();
    }
}
