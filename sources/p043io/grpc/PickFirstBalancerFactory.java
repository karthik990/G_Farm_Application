package p043io.grpc;

import com.google.common.base.Preconditions;
import java.util.List;
import p043io.grpc.LoadBalancer.Factory;
import p043io.grpc.LoadBalancer.Helper;
import p043io.grpc.LoadBalancer.PickResult;
import p043io.grpc.LoadBalancer.PickSubchannelArgs;
import p043io.grpc.LoadBalancer.Subchannel;
import p043io.grpc.LoadBalancer.SubchannelPicker;

/* renamed from: io.grpc.PickFirstBalancerFactory */
public final class PickFirstBalancerFactory extends Factory {
    private static final PickFirstBalancerFactory INSTANCE = new PickFirstBalancerFactory();

    /* renamed from: io.grpc.PickFirstBalancerFactory$1 */
    static /* synthetic */ class C53701 {
        static final /* synthetic */ int[] $SwitchMap$io$grpc$ConnectivityState = new int[ConnectivityState.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                io.grpc.ConnectivityState[] r0 = p043io.grpc.ConnectivityState.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$grpc$ConnectivityState = r0
                int[] r0 = $SwitchMap$io$grpc$ConnectivityState     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.grpc.ConnectivityState r1 = p043io.grpc.ConnectivityState.IDLE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$grpc$ConnectivityState     // Catch:{ NoSuchFieldError -> 0x001f }
                io.grpc.ConnectivityState r1 = p043io.grpc.ConnectivityState.CONNECTING     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$io$grpc$ConnectivityState     // Catch:{ NoSuchFieldError -> 0x002a }
                io.grpc.ConnectivityState r1 = p043io.grpc.ConnectivityState.READY     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$io$grpc$ConnectivityState     // Catch:{ NoSuchFieldError -> 0x0035 }
                io.grpc.ConnectivityState r1 = p043io.grpc.ConnectivityState.TRANSIENT_FAILURE     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.PickFirstBalancerFactory.C53701.<clinit>():void");
        }
    }

    /* renamed from: io.grpc.PickFirstBalancerFactory$PickFirstBalancer */
    static final class PickFirstBalancer extends LoadBalancer {
        private final Helper helper;
        private Subchannel subchannel;

        PickFirstBalancer(Helper helper2) {
            this.helper = (Helper) Preconditions.checkNotNull(helper2, "helper");
        }

        public void handleResolvedAddressGroups(List<EquivalentAddressGroup> list, Attributes attributes) {
            Subchannel subchannel2 = this.subchannel;
            if (subchannel2 == null) {
                this.subchannel = this.helper.createSubchannel(list, Attributes.EMPTY);
                this.helper.updateBalancingState(ConnectivityState.CONNECTING, new Picker(PickResult.withSubchannel(this.subchannel)));
                this.subchannel.requestConnection();
                return;
            }
            this.helper.updateSubchannelAddresses(subchannel2, list);
        }

        public void handleNameResolutionError(Status status) {
            Subchannel subchannel2 = this.subchannel;
            if (subchannel2 != null) {
                subchannel2.shutdown();
                this.subchannel = null;
            }
            this.helper.updateBalancingState(ConnectivityState.TRANSIENT_FAILURE, new Picker(PickResult.withError(status)));
        }

        public void handleSubchannelState(Subchannel subchannel2, ConnectivityStateInfo connectivityStateInfo) {
            SubchannelPicker subchannelPicker;
            SubchannelPicker subchannelPicker2;
            ConnectivityState state = connectivityStateInfo.getState();
            if (subchannel2 == this.subchannel && state != ConnectivityState.SHUTDOWN) {
                int i = C53701.$SwitchMap$io$grpc$ConnectivityState[state.ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        subchannelPicker = new Picker(PickResult.withNoResult());
                    } else if (i == 3) {
                        subchannelPicker2 = new Picker(PickResult.withSubchannel(subchannel2));
                    } else if (i == 4) {
                        subchannelPicker = new Picker(PickResult.withError(connectivityStateInfo.getStatus()));
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unsupported state:");
                        sb.append(state);
                        throw new IllegalArgumentException(sb.toString());
                    }
                    this.helper.updateBalancingState(state, subchannelPicker);
                }
                subchannelPicker2 = new RequestConnectionPicker(subchannel2);
                subchannelPicker = subchannelPicker2;
                this.helper.updateBalancingState(state, subchannelPicker);
            }
        }

        public void shutdown() {
            Subchannel subchannel2 = this.subchannel;
            if (subchannel2 != null) {
                subchannel2.shutdown();
            }
        }
    }

    /* renamed from: io.grpc.PickFirstBalancerFactory$Picker */
    private static final class Picker extends SubchannelPicker {
        private final PickResult result;

        Picker(PickResult pickResult) {
            this.result = (PickResult) Preconditions.checkNotNull(pickResult, "result");
        }

        public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
            return this.result;
        }
    }

    /* renamed from: io.grpc.PickFirstBalancerFactory$RequestConnectionPicker */
    private static final class RequestConnectionPicker extends SubchannelPicker {
        private final Subchannel subchannel;

        RequestConnectionPicker(Subchannel subchannel2) {
            this.subchannel = (Subchannel) Preconditions.checkNotNull(subchannel2, "subchannel");
        }

        public PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs) {
            this.subchannel.requestConnection();
            return PickResult.withNoResult();
        }

        public void requestConnection() {
            this.subchannel.requestConnection();
        }
    }

    private PickFirstBalancerFactory() {
    }

    public static PickFirstBalancerFactory getInstance() {
        return INSTANCE;
    }

    public LoadBalancer newLoadBalancer(Helper helper) {
        return new PickFirstBalancer(helper);
    }
}
