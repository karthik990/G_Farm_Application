package p043io.grpc.internal;

import com.google.common.base.Preconditions;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import p043io.grpc.Attributes;
import p043io.grpc.ConnectivityState;
import p043io.grpc.ConnectivityStateInfo;
import p043io.grpc.EquivalentAddressGroup;
import p043io.grpc.InternalChannelz.ChannelTrace.Event.Builder;
import p043io.grpc.InternalChannelz.ChannelTrace.Event.Severity;
import p043io.grpc.LoadBalancer;
import p043io.grpc.LoadBalancer.Factory;
import p043io.grpc.LoadBalancer.Helper;
import p043io.grpc.LoadBalancer.PickResult;
import p043io.grpc.LoadBalancer.PickSubchannelArgs;
import p043io.grpc.LoadBalancer.Subchannel;
import p043io.grpc.LoadBalancer.SubchannelPicker;
import p043io.grpc.PickFirstBalancerFactory;
import p043io.grpc.Status;

/* renamed from: io.grpc.internal.AutoConfiguredLoadBalancerFactory */
final class AutoConfiguredLoadBalancerFactory extends Factory {
    static final String GRPCLB_LOAD_BALANCER_FACTORY_NAME = "io.grpc.grpclb.GrpclbLoadBalancerFactory";
    static final String ROUND_ROBIN_LOAD_BALANCER_FACTORY_NAME = "io.grpc.util.RoundRobinLoadBalancerFactory";
    @Nullable
    private final ChannelTracer channelTracer;
    @Nullable
    private final TimeProvider timeProvider;

    /* renamed from: io.grpc.internal.AutoConfiguredLoadBalancerFactory$AutoConfiguredLoadBalancer */
    static final class AutoConfiguredLoadBalancer extends LoadBalancer {
        @CheckForNull
        private ChannelTracer channelTracer;
        private LoadBalancer delegate;
        private Factory delegateFactory = PickFirstBalancerFactory.getInstance();
        private final Helper helper;
        @Nullable
        private final TimeProvider timeProvider;

        AutoConfiguredLoadBalancer(Helper helper2, @Nullable ChannelTracer channelTracer2, @Nullable TimeProvider timeProvider2) {
            this.helper = helper2;
            this.delegate = this.delegateFactory.newLoadBalancer(helper2);
            this.channelTracer = channelTracer2;
            this.timeProvider = timeProvider2;
            if (channelTracer2 != null) {
                Preconditions.checkNotNull(timeProvider2, "timeProvider");
            }
        }

        public void handleResolvedAddressGroups(List<EquivalentAddressGroup> list, Attributes attributes) {
            try {
                Factory decideLoadBalancerFactory = decideLoadBalancerFactory(list, (Map) attributes.get(GrpcAttributes.NAME_RESOLVER_SERVICE_CONFIG));
                if (!(decideLoadBalancerFactory == null || decideLoadBalancerFactory == this.delegateFactory)) {
                    this.helper.updateBalancingState(ConnectivityState.CONNECTING, new EmptyPicker());
                    this.delegate.shutdown();
                    this.delegateFactory = decideLoadBalancerFactory;
                    LoadBalancer loadBalancer = this.delegate;
                    this.delegate = this.delegateFactory.newLoadBalancer(this.helper);
                    ChannelTracer channelTracer2 = this.channelTracer;
                    if (channelTracer2 != null) {
                        Builder builder = new Builder();
                        StringBuilder sb = new StringBuilder();
                        sb.append("Load balancer changed from ");
                        sb.append(loadBalancer);
                        sb.append(" to ");
                        sb.append(this.delegate);
                        channelTracer2.reportEvent(builder.setDescription(sb.toString()).setSeverity(Severity.CT_INFO).setTimestampNanos(this.timeProvider.currentTimeNanos()).build());
                    }
                }
                getDelegate().handleResolvedAddressGroups(list, attributes);
            } catch (RuntimeException e) {
                this.helper.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new FailingPicker(Status.INTERNAL.withDescription("Failed to pick a load balancer from service config").withCause(e)));
                this.delegate.shutdown();
                this.delegateFactory = null;
                this.delegate = new NoopLoadBalancer();
            }
        }

        public void handleNameResolutionError(Status status) {
            getDelegate().handleNameResolutionError(status);
        }

        public void handleSubchannelState(Subchannel subchannel, ConnectivityStateInfo connectivityStateInfo) {
            getDelegate().handleSubchannelState(subchannel, connectivityStateInfo);
        }

        public void shutdown() {
            this.delegate.shutdown();
            this.delegate = null;
        }

        /* access modifiers changed from: 0000 */
        public LoadBalancer getDelegate() {
            return this.delegate;
        }

        /* access modifiers changed from: 0000 */
        public void setDelegate(LoadBalancer loadBalancer) {
            this.delegate = loadBalancer;
        }

        /* access modifiers changed from: 0000 */
        public Factory getDelegateFactory() {
            return this.delegateFactory;
        }

        @Nullable
        static Factory decideLoadBalancerFactory(List<EquivalentAddressGroup> list, @Nullable Map<String, Object> map) {
            boolean z;
            Iterator it = list.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((EquivalentAddressGroup) it.next()).getAttributes().get(GrpcAttributes.ATTR_LB_ADDR_AUTHORITY) != null) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            String str = "getInstance";
            if (z) {
                try {
                    return (Factory) Class.forName(AutoConfiguredLoadBalancerFactory.GRPCLB_LOAD_BALANCER_FACTORY_NAME).getMethod(str, new Class[0]).invoke(null, new Object[0]);
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e2) {
                    throw new RuntimeException("Can't get GRPCLB, but balancer addresses were present", e2);
                }
            } else {
                String loadBalancingPolicyFromServiceConfig = map != null ? ServiceConfigUtil.getLoadBalancingPolicyFromServiceConfig(map) : null;
                if (loadBalancingPolicyFromServiceConfig == null) {
                    return PickFirstBalancerFactory.getInstance();
                }
                if (loadBalancingPolicyFromServiceConfig.toUpperCase(Locale.ROOT).equals("ROUND_ROBIN")) {
                    try {
                        return (Factory) Class.forName(AutoConfiguredLoadBalancerFactory.ROUND_ROBIN_LOAD_BALANCER_FACTORY_NAME).getMethod(str, new Class[0]).invoke(null, new Object[0]);
                    } catch (RuntimeException e3) {
                        throw e3;
                    } catch (Exception e4) {
                        throw new RuntimeException("Can't get Round Robin LB", e4);
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unknown service config policy: ");
                    sb.append(loadBalancingPolicyFromServiceConfig);
                    throw new IllegalArgumentException(sb.toString());
                }
            }
        }
    }

    /* renamed from: io.grpc.internal.AutoConfiguredLoadBalancerFactory$EmptyPicker */
    private static final class EmptyPicker extends SubchannelPicker {
        private EmptyPicker() {
        }

        public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
            return PickResult.withNoResult();
        }
    }

    /* renamed from: io.grpc.internal.AutoConfiguredLoadBalancerFactory$FailingPicker */
    private static final class FailingPicker extends SubchannelPicker {
        private final Status failure;

        FailingPicker(Status status) {
            this.failure = status;
        }

        public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
            return PickResult.withError(this.failure);
        }
    }

    /* renamed from: io.grpc.internal.AutoConfiguredLoadBalancerFactory$NoopLoadBalancer */
    private static final class NoopLoadBalancer extends LoadBalancer {
        public void handleNameResolutionError(Status status) {
        }

        public void handleResolvedAddressGroups(List<EquivalentAddressGroup> list, Attributes attributes) {
        }

        public void handleSubchannelState(Subchannel subchannel, ConnectivityStateInfo connectivityStateInfo) {
        }

        public void shutdown() {
        }

        private NoopLoadBalancer() {
        }
    }

    AutoConfiguredLoadBalancerFactory(@Nullable ChannelTracer channelTracer2, @Nullable TimeProvider timeProvider2) {
        this.channelTracer = channelTracer2;
        this.timeProvider = timeProvider2;
    }

    public LoadBalancer newLoadBalancer(Helper helper) {
        return new AutoConfiguredLoadBalancer(helper, this.channelTracer, this.timeProvider);
    }
}
