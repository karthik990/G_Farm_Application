package p043io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import p043io.grpc.Attributes;
import p043io.grpc.CallOptions;
import p043io.grpc.ConnectivityState;
import p043io.grpc.ConnectivityStateInfo;
import p043io.grpc.EquivalentAddressGroup;
import p043io.grpc.InternalChannelz;
import p043io.grpc.InternalChannelz.ChannelStats;
import p043io.grpc.InternalChannelz.ChannelTrace.Event.Builder;
import p043io.grpc.InternalChannelz.ChannelTrace.Event.Severity;
import p043io.grpc.InternalInstrumented;
import p043io.grpc.InternalLogId;
import p043io.grpc.Metadata;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.Status;
import p043io.grpc.internal.BackoffPolicy.Provider;
import p043io.grpc.internal.ClientStreamListener.RpcProgress;
import p043io.grpc.internal.ClientTransportFactory.ClientTransportOptions;
import p043io.grpc.internal.ManagedClientTransport.Listener;

/* renamed from: io.grpc.internal.InternalSubchannel */
final class InternalSubchannel implements InternalInstrumented<ChannelStats> {
    /* access modifiers changed from: private */
    public static final Logger log = Logger.getLogger(InternalSubchannel.class.getName());
    /* access modifiers changed from: private */
    @Nullable
    public volatile ManagedClientTransport activeTransport;
    /* access modifiers changed from: private */
    public Index addressIndex;
    private final String authority;
    private final Provider backoffPolicyProvider;
    /* access modifiers changed from: private */
    public final Callback callback;
    private final CallTracer callsTracer;
    /* access modifiers changed from: private */
    public final ChannelExecutor channelExecutor;
    @CheckForNull
    private final ChannelTracer channelTracer;
    /* access modifiers changed from: private */
    public final InternalChannelz channelz;
    private final Stopwatch connectingTimer;
    /* access modifiers changed from: private */
    public final InUseStateAggregator<ConnectionClientTransport> inUseStateAggregator = new InUseStateAggregator<ConnectionClientTransport>() {
        /* access modifiers changed from: 0000 */
        public void handleInUse() {
            InternalSubchannel.this.callback.onInUse(InternalSubchannel.this);
        }

        /* access modifiers changed from: 0000 */
        public void handleNotInUse() {
            InternalSubchannel.this.callback.onNotInUse(InternalSubchannel.this);
        }
    };
    /* access modifiers changed from: private */
    public final Object lock = new Object();
    /* access modifiers changed from: private */
    public final InternalLogId logId = InternalLogId.allocate(getClass().getName());
    /* access modifiers changed from: private */
    @Nullable
    public ConnectionClientTransport pendingTransport;
    /* access modifiers changed from: private */
    public boolean reconnectCanceled;
    /* access modifiers changed from: private */
    public BackoffPolicy reconnectPolicy;
    /* access modifiers changed from: private */
    @Nullable
    public ScheduledFuture<?> reconnectTask;
    private final ScheduledExecutorService scheduledExecutor;
    /* access modifiers changed from: private */
    public Status shutdownReason;
    /* access modifiers changed from: private */
    public ConnectivityStateInfo state = ConnectivityStateInfo.forNonError(ConnectivityState.IDLE);
    private final TimeProvider timeProvider;
    private final ClientTransportFactory transportFactory;
    /* access modifiers changed from: private */
    public final Collection<ConnectionClientTransport> transports = new ArrayList();
    private final String userAgent;

    /* renamed from: io.grpc.internal.InternalSubchannel$CallTracingTransport */
    static final class CallTracingTransport extends ForwardingConnectionClientTransport {
        /* access modifiers changed from: private */
        public final CallTracer callTracer;
        private final ConnectionClientTransport delegate;

        private CallTracingTransport(ConnectionClientTransport connectionClientTransport, CallTracer callTracer2) {
            this.delegate = connectionClientTransport;
            this.callTracer = callTracer2;
        }

        /* access modifiers changed from: protected */
        public ConnectionClientTransport delegate() {
            return this.delegate;
        }

        public ClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions) {
            final ClientStream newStream = super.newStream(methodDescriptor, metadata, callOptions);
            return new ForwardingClientStream() {
                /* access modifiers changed from: protected */
                public ClientStream delegate() {
                    return newStream;
                }

                public void start(final ClientStreamListener clientStreamListener) {
                    CallTracingTransport.this.callTracer.reportCallStarted();
                    super.start(new ForwardingClientStreamListener() {
                        /* access modifiers changed from: protected */
                        public ClientStreamListener delegate() {
                            return clientStreamListener;
                        }

                        public void closed(Status status, Metadata metadata) {
                            CallTracingTransport.this.callTracer.reportCallEnded(status.isOk());
                            super.closed(status, metadata);
                        }

                        public void closed(Status status, RpcProgress rpcProgress, Metadata metadata) {
                            CallTracingTransport.this.callTracer.reportCallEnded(status.isOk());
                            super.closed(status, rpcProgress, metadata);
                        }
                    });
                }
            };
        }
    }

    /* renamed from: io.grpc.internal.InternalSubchannel$Callback */
    static abstract class Callback {
        /* access modifiers changed from: 0000 */
        public void onInUse(InternalSubchannel internalSubchannel) {
        }

        /* access modifiers changed from: 0000 */
        public void onNotInUse(InternalSubchannel internalSubchannel) {
        }

        /* access modifiers changed from: 0000 */
        public void onStateChange(InternalSubchannel internalSubchannel, ConnectivityStateInfo connectivityStateInfo) {
        }

        /* access modifiers changed from: 0000 */
        public void onTerminated(InternalSubchannel internalSubchannel) {
        }

        Callback() {
        }
    }

    /* renamed from: io.grpc.internal.InternalSubchannel$Index */
    static final class Index {
        private List<EquivalentAddressGroup> addressGroups;
        private int addressIndex;
        private int groupIndex;

        public Index(List<EquivalentAddressGroup> list) {
            this.addressGroups = list;
        }

        public boolean isValid() {
            return this.groupIndex < this.addressGroups.size();
        }

        public boolean isAtBeginning() {
            return this.groupIndex == 0 && this.addressIndex == 0;
        }

        public void increment() {
            EquivalentAddressGroup equivalentAddressGroup = (EquivalentAddressGroup) this.addressGroups.get(this.groupIndex);
            this.addressIndex++;
            if (this.addressIndex >= equivalentAddressGroup.getAddresses().size()) {
                this.groupIndex++;
                this.addressIndex = 0;
            }
        }

        public void reset() {
            this.groupIndex = 0;
            this.addressIndex = 0;
        }

        public SocketAddress getCurrentAddress() {
            return (SocketAddress) ((EquivalentAddressGroup) this.addressGroups.get(this.groupIndex)).getAddresses().get(this.addressIndex);
        }

        public Attributes getCurrentEagAttributes() {
            return ((EquivalentAddressGroup) this.addressGroups.get(this.groupIndex)).getAttributes();
        }

        public List<EquivalentAddressGroup> getGroups() {
            return this.addressGroups;
        }

        public void updateGroups(List<EquivalentAddressGroup> list) {
            this.addressGroups = list;
            reset();
        }

        public boolean seekTo(SocketAddress socketAddress) {
            int i = 0;
            while (i < this.addressGroups.size()) {
                int indexOf = ((EquivalentAddressGroup) this.addressGroups.get(i)).getAddresses().indexOf(socketAddress);
                if (indexOf == -1) {
                    i++;
                } else {
                    this.groupIndex = i;
                    this.addressIndex = indexOf;
                    return true;
                }
            }
            return false;
        }
    }

    /* renamed from: io.grpc.internal.InternalSubchannel$TransportListener */
    private class TransportListener implements Listener {
        final SocketAddress address;
        final ConnectionClientTransport transport;

        TransportListener(ConnectionClientTransport connectionClientTransport, SocketAddress socketAddress) {
            this.transport = connectionClientTransport;
            this.address = socketAddress;
        }

        public void transportReady() {
            Status access$1100;
            boolean z = true;
            if (InternalSubchannel.log.isLoggable(Level.FINE)) {
                InternalSubchannel.log.log(Level.FINE, "[{0}] {1} for {2} is ready", new Object[]{InternalSubchannel.this.logId, this.transport.getLogId(), this.address});
            }
            try {
                synchronized (InternalSubchannel.this.lock) {
                    access$1100 = InternalSubchannel.this.shutdownReason;
                    InternalSubchannel.this.reconnectPolicy = null;
                    if (access$1100 != null) {
                        if (InternalSubchannel.this.activeTransport != null) {
                            z = false;
                        }
                        Preconditions.checkState(z, "Unexpected non-null activeTransport");
                    } else if (InternalSubchannel.this.pendingTransport == this.transport) {
                        InternalSubchannel.this.gotoNonErrorState(ConnectivityState.READY);
                        InternalSubchannel.this.activeTransport = this.transport;
                        InternalSubchannel.this.pendingTransport = null;
                    }
                }
                InternalSubchannel.this.channelExecutor.drain();
                if (access$1100 != null) {
                    this.transport.shutdown(access$1100);
                }
            } catch (Throwable th) {
                InternalSubchannel.this.channelExecutor.drain();
                throw th;
            }
        }

        public void transportInUse(boolean z) {
            InternalSubchannel.this.handleTransportInUseState(this.transport, z);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:27:0x00cd, code lost:
            p043io.grpc.internal.InternalSubchannel.access$800(r7.this$0).drain();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x00d6, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void transportShutdown(p043io.grpc.Status r8) {
            /*
                r7 = this;
                java.util.logging.Logger r0 = p043io.grpc.internal.InternalSubchannel.log
                java.util.logging.Level r1 = java.util.logging.Level.FINE
                boolean r0 = r0.isLoggable(r1)
                r1 = 1
                r2 = 0
                if (r0 == 0) goto L_0x0034
                java.util.logging.Logger r0 = p043io.grpc.internal.InternalSubchannel.log
                java.util.logging.Level r3 = java.util.logging.Level.FINE
                r4 = 4
                java.lang.Object[] r4 = new java.lang.Object[r4]
                io.grpc.internal.InternalSubchannel r5 = p043io.grpc.internal.InternalSubchannel.this
                io.grpc.InternalLogId r5 = r5.logId
                r4[r2] = r5
                io.grpc.internal.ConnectionClientTransport r5 = r7.transport
                io.grpc.InternalLogId r5 = r5.getLogId()
                r4[r1] = r5
                r5 = 2
                java.net.SocketAddress r6 = r7.address
                r4[r5] = r6
                r5 = 3
                r4[r5] = r8
                java.lang.String r5 = "[{0}] {1} for {2} is being shutdown with status {3}"
                r0.log(r3, r5, r4)
            L_0x0034:
                io.grpc.internal.InternalSubchannel r0 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00da }
                java.lang.Object r0 = r0.lock     // Catch:{ all -> 0x00da }
                monitor-enter(r0)     // Catch:{ all -> 0x00da }
                io.grpc.internal.InternalSubchannel r3 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                io.grpc.ConnectivityStateInfo r3 = r3.state     // Catch:{ all -> 0x00d7 }
                io.grpc.ConnectivityState r3 = r3.getState()     // Catch:{ all -> 0x00d7 }
                io.grpc.ConnectivityState r4 = p043io.grpc.ConnectivityState.SHUTDOWN     // Catch:{ all -> 0x00d7 }
                if (r3 != r4) goto L_0x0054
                monitor-exit(r0)     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.InternalSubchannel r8 = p043io.grpc.internal.InternalSubchannel.this
                io.grpc.internal.ChannelExecutor r8 = r8.channelExecutor
                r8.drain()
                return
            L_0x0054:
                io.grpc.internal.InternalSubchannel r3 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.ManagedClientTransport r3 = r3.activeTransport     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.ConnectionClientTransport r4 = r7.transport     // Catch:{ all -> 0x00d7 }
                r5 = 0
                if (r3 != r4) goto L_0x0075
                io.grpc.internal.InternalSubchannel r8 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                io.grpc.ConnectivityState r1 = p043io.grpc.ConnectivityState.IDLE     // Catch:{ all -> 0x00d7 }
                r8.gotoNonErrorState(r1)     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.InternalSubchannel r8 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                r8.activeTransport = r5     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.InternalSubchannel r8 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.InternalSubchannel$Index r8 = r8.addressIndex     // Catch:{ all -> 0x00d7 }
                r8.reset()     // Catch:{ all -> 0x00d7 }
                goto L_0x00cc
            L_0x0075:
                io.grpc.internal.InternalSubchannel r3 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.ConnectionClientTransport r3 = r3.pendingTransport     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.ConnectionClientTransport r4 = r7.transport     // Catch:{ all -> 0x00d7 }
                if (r3 != r4) goto L_0x00cc
                io.grpc.internal.InternalSubchannel r3 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                io.grpc.ConnectivityStateInfo r3 = r3.state     // Catch:{ all -> 0x00d7 }
                io.grpc.ConnectivityState r3 = r3.getState()     // Catch:{ all -> 0x00d7 }
                io.grpc.ConnectivityState r4 = p043io.grpc.ConnectivityState.CONNECTING     // Catch:{ all -> 0x00d7 }
                if (r3 != r4) goto L_0x008e
                goto L_0x008f
            L_0x008e:
                r1 = 0
            L_0x008f:
                java.lang.String r2 = "Expected state is CONNECTING, actual state is %s"
                io.grpc.internal.InternalSubchannel r3 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                io.grpc.ConnectivityStateInfo r3 = r3.state     // Catch:{ all -> 0x00d7 }
                io.grpc.ConnectivityState r3 = r3.getState()     // Catch:{ all -> 0x00d7 }
                com.google.common.base.Preconditions.checkState(r1, r2, r3)     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.InternalSubchannel r1 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.InternalSubchannel$Index r1 = r1.addressIndex     // Catch:{ all -> 0x00d7 }
                r1.increment()     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.InternalSubchannel r1 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.InternalSubchannel$Index r1 = r1.addressIndex     // Catch:{ all -> 0x00d7 }
                boolean r1 = r1.isValid()     // Catch:{ all -> 0x00d7 }
                if (r1 != 0) goto L_0x00c7
                io.grpc.internal.InternalSubchannel r1 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                r1.pendingTransport = r5     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.InternalSubchannel r1 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.InternalSubchannel$Index r1 = r1.addressIndex     // Catch:{ all -> 0x00d7 }
                r1.reset()     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.InternalSubchannel r1 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                r1.scheduleBackoff(r8)     // Catch:{ all -> 0x00d7 }
                goto L_0x00cc
            L_0x00c7:
                io.grpc.internal.InternalSubchannel r8 = p043io.grpc.internal.InternalSubchannel.this     // Catch:{ all -> 0x00d7 }
                r8.startNewTransport()     // Catch:{ all -> 0x00d7 }
            L_0x00cc:
                monitor-exit(r0)     // Catch:{ all -> 0x00d7 }
                io.grpc.internal.InternalSubchannel r8 = p043io.grpc.internal.InternalSubchannel.this
                io.grpc.internal.ChannelExecutor r8 = r8.channelExecutor
                r8.drain()
                return
            L_0x00d7:
                r8 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00d7 }
                throw r8     // Catch:{ all -> 0x00da }
            L_0x00da:
                r8 = move-exception
                io.grpc.internal.InternalSubchannel r0 = p043io.grpc.internal.InternalSubchannel.this
                io.grpc.internal.ChannelExecutor r0 = r0.channelExecutor
                r0.drain()
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.internal.InternalSubchannel.TransportListener.transportShutdown(io.grpc.Status):void");
        }

        public void transportTerminated() {
            boolean z = true;
            if (InternalSubchannel.log.isLoggable(Level.FINE)) {
                InternalSubchannel.log.log(Level.FINE, "[{0}] {1} for {2} is terminated", new Object[]{InternalSubchannel.this.logId, this.transport.getLogId(), this.address});
            }
            InternalSubchannel.this.channelz.removeClientSocket(this.transport);
            InternalSubchannel.this.handleTransportInUseState(this.transport, false);
            try {
                synchronized (InternalSubchannel.this.lock) {
                    InternalSubchannel.this.transports.remove(this.transport);
                    if (InternalSubchannel.this.state.getState() == ConnectivityState.SHUTDOWN && InternalSubchannel.this.transports.isEmpty()) {
                        if (InternalSubchannel.log.isLoggable(Level.FINE)) {
                            InternalSubchannel.log.log(Level.FINE, "[{0}] Terminated in transportTerminated()", InternalSubchannel.this.logId);
                        }
                        InternalSubchannel.this.handleTermination();
                    }
                }
                InternalSubchannel.this.channelExecutor.drain();
                if (InternalSubchannel.this.activeTransport == this.transport) {
                    z = false;
                }
                Preconditions.checkState(z, "activeTransport still points to this transport. Seems transportShutdown() was not called.");
            } catch (Throwable th) {
                InternalSubchannel.this.channelExecutor.drain();
                throw th;
            }
        }
    }

    InternalSubchannel(List<EquivalentAddressGroup> list, String str, String str2, Provider provider, ClientTransportFactory clientTransportFactory, ScheduledExecutorService scheduledExecutorService, Supplier<Stopwatch> supplier, ChannelExecutor channelExecutor2, Callback callback2, InternalChannelz internalChannelz, CallTracer callTracer, @Nullable ChannelTracer channelTracer2, TimeProvider timeProvider2) {
        Preconditions.checkNotNull(list, "addressGroups");
        Preconditions.checkArgument(!list.isEmpty(), "addressGroups is empty");
        checkListHasNoNulls(list, "addressGroups contains null entry");
        this.addressIndex = new Index(Collections.unmodifiableList(new ArrayList(list)));
        this.authority = str;
        this.userAgent = str2;
        this.backoffPolicyProvider = provider;
        this.transportFactory = clientTransportFactory;
        this.scheduledExecutor = scheduledExecutorService;
        this.connectingTimer = (Stopwatch) supplier.get();
        this.channelExecutor = channelExecutor2;
        this.callback = callback2;
        this.channelz = internalChannelz;
        this.callsTracer = callTracer;
        this.channelTracer = channelTracer2;
        this.timeProvider = timeProvider2;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0026, code lost:
        r3.channelExecutor.drain();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002c, code lost:
        return null;
     */
    @javax.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public p043io.grpc.internal.ClientTransport obtainActiveTransport() {
        /*
            r3 = this;
            io.grpc.internal.ManagedClientTransport r0 = r3.activeTransport
            if (r0 == 0) goto L_0x0005
            return r0
        L_0x0005:
            java.lang.Object r0 = r3.lock     // Catch:{ all -> 0x0030 }
            monitor-enter(r0)     // Catch:{ all -> 0x0030 }
            io.grpc.internal.ManagedClientTransport r1 = r3.activeTransport     // Catch:{ all -> 0x002d }
            if (r1 == 0) goto L_0x0013
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            io.grpc.internal.ChannelExecutor r0 = r3.channelExecutor
            r0.drain()
            return r1
        L_0x0013:
            io.grpc.ConnectivityStateInfo r1 = r3.state     // Catch:{ all -> 0x002d }
            io.grpc.ConnectivityState r1 = r1.getState()     // Catch:{ all -> 0x002d }
            io.grpc.ConnectivityState r2 = p043io.grpc.ConnectivityState.IDLE     // Catch:{ all -> 0x002d }
            if (r1 != r2) goto L_0x0025
            io.grpc.ConnectivityState r1 = p043io.grpc.ConnectivityState.CONNECTING     // Catch:{ all -> 0x002d }
            r3.gotoNonErrorState(r1)     // Catch:{ all -> 0x002d }
            r3.startNewTransport()     // Catch:{ all -> 0x002d }
        L_0x0025:
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            io.grpc.internal.ChannelExecutor r0 = r3.channelExecutor
            r0.drain()
            r0 = 0
            return r0
        L_0x002d:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002d }
            throw r1     // Catch:{ all -> 0x0030 }
        L_0x0030:
            r0 = move-exception
            io.grpc.internal.ChannelExecutor r1 = r3.channelExecutor
            r1.drain()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.internal.InternalSubchannel.obtainActiveTransport():io.grpc.internal.ClientTransport");
    }

    /* access modifiers changed from: private */
    public void startNewTransport() {
        ProxyParameters proxyParameters;
        Preconditions.checkState(this.reconnectTask == null, "Should have no reconnectTask scheduled");
        if (this.addressIndex.isAtBeginning()) {
            this.connectingTimer.reset().start();
        }
        SocketAddress currentAddress = this.addressIndex.getCurrentAddress();
        if (currentAddress instanceof ProxySocketAddress) {
            ProxySocketAddress proxySocketAddress = (ProxySocketAddress) currentAddress;
            proxyParameters = proxySocketAddress.getProxyParameters();
            currentAddress = proxySocketAddress.getAddress();
        } else {
            proxyParameters = null;
        }
        CallTracingTransport callTracingTransport = new CallTracingTransport(this.transportFactory.newClientTransport(currentAddress, new ClientTransportOptions().setAuthority(this.authority).setEagAttributes(this.addressIndex.getCurrentEagAttributes()).setUserAgent(this.userAgent).setProxyParameters(proxyParameters)), this.callsTracer);
        this.channelz.addClientSocket(callTracingTransport);
        if (log.isLoggable(Level.FINE)) {
            log.log(Level.FINE, "[{0}] Created {1} for {2}", new Object[]{this.logId, callTracingTransport.getLogId(), currentAddress});
        }
        this.pendingTransport = callTracingTransport;
        this.transports.add(callTracingTransport);
        Runnable start = callTracingTransport.start(new TransportListener(callTracingTransport, currentAddress));
        if (start != null) {
            this.channelExecutor.executeLater(start);
        }
    }

    /* access modifiers changed from: private */
    public void scheduleBackoff(Status status) {
        gotoState(ConnectivityStateInfo.forTransientFailure(status));
        if (this.reconnectPolicy == null) {
            this.reconnectPolicy = this.backoffPolicyProvider.get();
        }
        long nextBackoffNanos = this.reconnectPolicy.nextBackoffNanos() - this.connectingTimer.elapsed(TimeUnit.NANOSECONDS);
        boolean z = true;
        if (log.isLoggable(Level.FINE)) {
            log.log(Level.FINE, "[{0}] Scheduling backoff for {1} ns", new Object[]{this.logId, Long.valueOf(nextBackoffNanos)});
        }
        if (this.reconnectTask != null) {
            z = false;
        }
        Preconditions.checkState(z, "previous reconnectTask is not done");
        this.reconnectCanceled = false;
        this.reconnectTask = this.scheduledExecutor.schedule(new LogExceptionRunnable(new Runnable() {
            public void run() {
                try {
                    synchronized (InternalSubchannel.this.lock) {
                        InternalSubchannel.this.reconnectTask = null;
                        if (InternalSubchannel.this.reconnectCanceled) {
                            InternalSubchannel.this.channelExecutor.drain();
                            return;
                        } else {
                            InternalSubchannel.this.gotoNonErrorState(ConnectivityState.CONNECTING);
                            InternalSubchannel.this.startNewTransport();
                        }
                    }
                } catch (Throwable th) {
                    try {
                        InternalSubchannel.log.log(Level.WARNING, "Exception handling end of backoff", th);
                    } catch (Throwable th2) {
                        InternalSubchannel.this.channelExecutor.drain();
                        throw th2;
                    }
                }
                InternalSubchannel.this.channelExecutor.drain();
            }
        }), nextBackoffNanos, TimeUnit.NANOSECONDS);
    }

    /* access modifiers changed from: 0000 */
    public void resetConnectBackoff() {
        try {
            synchronized (this.lock) {
                if (this.state.getState() != ConnectivityState.TRANSIENT_FAILURE) {
                    this.channelExecutor.drain();
                    return;
                }
                cancelReconnectTask();
                gotoNonErrorState(ConnectivityState.CONNECTING);
                startNewTransport();
                this.channelExecutor.drain();
            }
        } catch (Throwable th) {
            this.channelExecutor.drain();
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public void gotoNonErrorState(ConnectivityState connectivityState) {
        gotoState(ConnectivityStateInfo.forNonError(connectivityState));
    }

    private void gotoState(final ConnectivityStateInfo connectivityStateInfo) {
        if (this.state.getState() != connectivityStateInfo.getState()) {
            boolean z = this.state.getState() != ConnectivityState.SHUTDOWN;
            StringBuilder sb = new StringBuilder();
            sb.append("Cannot transition out of SHUTDOWN to ");
            sb.append(connectivityStateInfo);
            Preconditions.checkState(z, sb.toString());
            this.state = connectivityStateInfo;
            ChannelTracer channelTracer2 = this.channelTracer;
            if (channelTracer2 != null) {
                Builder builder = new Builder();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Entering ");
                sb2.append(this.state);
                sb2.append(" state");
                channelTracer2.reportEvent(builder.setDescription(sb2.toString()).setSeverity(Severity.CT_INFO).setTimestampNanos(this.timeProvider.currentTimeNanos()).build());
            }
            this.channelExecutor.executeLater(new Runnable() {
                public void run() {
                    InternalSubchannel.this.callback.onStateChange(InternalSubchannel.this, connectivityStateInfo);
                }
            });
        }
    }

    public void updateAddresses(List<EquivalentAddressGroup> list) {
        ManagedClientTransport managedClientTransport;
        Preconditions.checkNotNull(list, "newAddressGroups");
        checkListHasNoNulls(list, "newAddressGroups contains null entry");
        Preconditions.checkArgument(!list.isEmpty(), "newAddressGroups is empty");
        List unmodifiableList = Collections.unmodifiableList(new ArrayList(list));
        try {
            synchronized (this.lock) {
                SocketAddress currentAddress = this.addressIndex.getCurrentAddress();
                this.addressIndex.updateGroups(unmodifiableList);
                if ((this.state.getState() != ConnectivityState.READY && this.state.getState() != ConnectivityState.CONNECTING) || this.addressIndex.seekTo(currentAddress)) {
                    managedClientTransport = null;
                } else if (this.state.getState() == ConnectivityState.READY) {
                    managedClientTransport = this.activeTransport;
                    this.activeTransport = null;
                    this.addressIndex.reset();
                    gotoNonErrorState(ConnectivityState.IDLE);
                } else {
                    managedClientTransport = this.pendingTransport;
                    this.pendingTransport = null;
                    this.addressIndex.reset();
                    startNewTransport();
                }
            }
            this.channelExecutor.drain();
            if (managedClientTransport != null) {
                managedClientTransport.shutdown(Status.UNAVAILABLE.withDescription("InternalSubchannel closed transport due to address change"));
            }
        } catch (Throwable th) {
            this.channelExecutor.drain();
            throw th;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004d, code lost:
        r7.channelExecutor.drain();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0052, code lost:
        if (r1 == null) goto L_0x0057;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0054, code lost:
        r1.shutdown(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0057, code lost:
        if (r2 == null) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0059, code lost:
        r2.shutdown(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void shutdown(p043io.grpc.Status r8) {
        /*
            r7 = this;
            java.lang.Object r0 = r7.lock     // Catch:{ all -> 0x0060 }
            monitor-enter(r0)     // Catch:{ all -> 0x0060 }
            io.grpc.ConnectivityStateInfo r1 = r7.state     // Catch:{ all -> 0x005d }
            io.grpc.ConnectivityState r1 = r1.getState()     // Catch:{ all -> 0x005d }
            io.grpc.ConnectivityState r2 = p043io.grpc.ConnectivityState.SHUTDOWN     // Catch:{ all -> 0x005d }
            if (r1 != r2) goto L_0x0014
            monitor-exit(r0)     // Catch:{ all -> 0x005d }
            io.grpc.internal.ChannelExecutor r8 = r7.channelExecutor
            r8.drain()
            return
        L_0x0014:
            r7.shutdownReason = r8     // Catch:{ all -> 0x005d }
            io.grpc.ConnectivityState r1 = p043io.grpc.ConnectivityState.SHUTDOWN     // Catch:{ all -> 0x005d }
            r7.gotoNonErrorState(r1)     // Catch:{ all -> 0x005d }
            io.grpc.internal.ManagedClientTransport r1 = r7.activeTransport     // Catch:{ all -> 0x005d }
            io.grpc.internal.ConnectionClientTransport r2 = r7.pendingTransport     // Catch:{ all -> 0x005d }
            r3 = 0
            r7.activeTransport = r3     // Catch:{ all -> 0x005d }
            r7.pendingTransport = r3     // Catch:{ all -> 0x005d }
            io.grpc.internal.InternalSubchannel$Index r3 = r7.addressIndex     // Catch:{ all -> 0x005d }
            r3.reset()     // Catch:{ all -> 0x005d }
            java.util.Collection<io.grpc.internal.ConnectionClientTransport> r3 = r7.transports     // Catch:{ all -> 0x005d }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x005d }
            if (r3 == 0) goto L_0x0049
            r7.handleTermination()     // Catch:{ all -> 0x005d }
            java.util.logging.Logger r3 = log     // Catch:{ all -> 0x005d }
            java.util.logging.Level r4 = java.util.logging.Level.FINE     // Catch:{ all -> 0x005d }
            boolean r3 = r3.isLoggable(r4)     // Catch:{ all -> 0x005d }
            if (r3 == 0) goto L_0x0049
            java.util.logging.Logger r3 = log     // Catch:{ all -> 0x005d }
            java.util.logging.Level r4 = java.util.logging.Level.FINE     // Catch:{ all -> 0x005d }
            java.lang.String r5 = "[{0}] Terminated in shutdown()"
            io.grpc.InternalLogId r6 = r7.logId     // Catch:{ all -> 0x005d }
            r3.log(r4, r5, r6)     // Catch:{ all -> 0x005d }
        L_0x0049:
            r7.cancelReconnectTask()     // Catch:{ all -> 0x005d }
            monitor-exit(r0)     // Catch:{ all -> 0x005d }
            io.grpc.internal.ChannelExecutor r0 = r7.channelExecutor
            r0.drain()
            if (r1 == 0) goto L_0x0057
            r1.shutdown(r8)
        L_0x0057:
            if (r2 == 0) goto L_0x005c
            r2.shutdown(r8)
        L_0x005c:
            return
        L_0x005d:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005d }
            throw r8     // Catch:{ all -> 0x0060 }
        L_0x0060:
            r8 = move-exception
            io.grpc.internal.ChannelExecutor r0 = r7.channelExecutor
            r0.drain()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.internal.InternalSubchannel.shutdown(io.grpc.Status):void");
    }

    public String toString() {
        List groups;
        synchronized (this.lock) {
            groups = this.addressIndex.getGroups();
        }
        return MoreObjects.toStringHelper((Object) this).add("logId", this.logId.getId()).add("addressGroups", (Object) groups).toString();
    }

    /* access modifiers changed from: private */
    public void handleTermination() {
        this.channelExecutor.executeLater(new Runnable() {
            public void run() {
                InternalSubchannel.this.callback.onTerminated(InternalSubchannel.this);
            }
        });
    }

    /* access modifiers changed from: private */
    public void handleTransportInUseState(final ConnectionClientTransport connectionClientTransport, final boolean z) {
        this.channelExecutor.executeLater(new Runnable() {
            public void run() {
                InternalSubchannel.this.inUseStateAggregator.updateObjectInUse(connectionClientTransport, z);
            }
        }).drain();
    }

    /* access modifiers changed from: 0000 */
    public void shutdownNow(Status status) {
        ArrayList<ManagedClientTransport> arrayList;
        shutdown(status);
        try {
            synchronized (this.lock) {
                arrayList = new ArrayList<>(this.transports);
            }
            this.channelExecutor.drain();
            for (ManagedClientTransport shutdownNow : arrayList) {
                shutdownNow.shutdownNow(status);
            }
        } catch (Throwable th) {
            this.channelExecutor.drain();
            throw th;
        }
    }

    /* access modifiers changed from: 0000 */
    public List<EquivalentAddressGroup> getAddressGroups() {
        List<EquivalentAddressGroup> groups;
        try {
            synchronized (this.lock) {
                groups = this.addressIndex.getGroups();
            }
            this.channelExecutor.drain();
            return groups;
        } catch (Throwable th) {
            this.channelExecutor.drain();
            throw th;
        }
    }

    private void cancelReconnectTask() {
        ScheduledFuture<?> scheduledFuture = this.reconnectTask;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            this.reconnectCanceled = true;
            this.reconnectTask = null;
            this.reconnectPolicy = null;
        }
    }

    public InternalLogId getLogId() {
        return this.logId;
    }

    public ListenableFuture<ChannelStats> getStats() {
        List groups;
        ArrayList arrayList;
        SettableFuture create = SettableFuture.create();
        ChannelStats.Builder builder = new ChannelStats.Builder();
        synchronized (this.lock) {
            groups = this.addressIndex.getGroups();
            arrayList = new ArrayList(this.transports);
        }
        builder.setTarget(groups.toString()).setState(getState());
        builder.setSockets(arrayList);
        this.callsTracer.updateBuilder(builder);
        ChannelTracer channelTracer2 = this.channelTracer;
        if (channelTracer2 != null) {
            channelTracer2.updateBuilder(builder);
        }
        create.set(builder.build());
        return create;
    }

    /* access modifiers changed from: 0000 */
    public ConnectivityState getState() {
        ConnectivityState state2;
        try {
            synchronized (this.lock) {
                state2 = this.state.getState();
            }
            this.channelExecutor.drain();
            return state2;
        } catch (Throwable th) {
            this.channelExecutor.drain();
            throw th;
        }
    }

    private static void checkListHasNoNulls(List<?> list, String str) {
        for (Object checkNotNull : list) {
            Preconditions.checkNotNull(checkNotNull, str);
        }
    }
}
