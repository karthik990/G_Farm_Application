package p043io.grpc;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/* renamed from: io.grpc.LoadBalancer */
public abstract class LoadBalancer {

    /* renamed from: io.grpc.LoadBalancer$Helper */
    public static abstract class Helper {
        public abstract ManagedChannel createOobChannel(EquivalentAddressGroup equivalentAddressGroup, String str);

        public abstract String getAuthority();

        public abstract p043io.grpc.NameResolver.Factory getNameResolverFactory();

        public abstract void runSerialized(Runnable runnable);

        public abstract void updateBalancingState(@Nonnull ConnectivityState connectivityState, @Nonnull SubchannelPicker subchannelPicker);

        public Subchannel createSubchannel(EquivalentAddressGroup equivalentAddressGroup, Attributes attributes) {
            Preconditions.checkNotNull(equivalentAddressGroup, "addrs");
            return createSubchannel(Collections.singletonList(equivalentAddressGroup), attributes);
        }

        public Subchannel createSubchannel(List<EquivalentAddressGroup> list, Attributes attributes) {
            throw new UnsupportedOperationException();
        }

        public void updateSubchannelAddresses(Subchannel subchannel, EquivalentAddressGroup equivalentAddressGroup) {
            Preconditions.checkNotNull(equivalentAddressGroup, "addrs");
            updateSubchannelAddresses(subchannel, Collections.singletonList(equivalentAddressGroup));
        }

        public void updateSubchannelAddresses(Subchannel subchannel, List<EquivalentAddressGroup> list) {
            throw new UnsupportedOperationException();
        }

        public void updateOobChannelAddresses(ManagedChannel managedChannel, EquivalentAddressGroup equivalentAddressGroup) {
            throw new UnsupportedOperationException();
        }
    }

    /* renamed from: io.grpc.LoadBalancer$SubchannelPicker */
    public static abstract class SubchannelPicker {
        public abstract PickResult pickSubchannel(PickSubchannelArgs pickSubchannelArgs);

        public void requestConnection() {
        }
    }

    /* renamed from: io.grpc.LoadBalancer$Factory */
    public static abstract class Factory {
        public abstract LoadBalancer newLoadBalancer(Helper helper);
    }

    /* renamed from: io.grpc.LoadBalancer$PickResult */
    public static final class PickResult {
        private static final PickResult NO_RESULT = new PickResult(null, null, Status.f3691OK, false);
        private final boolean drop;
        private final Status status;
        @Nullable
        private final p043io.grpc.ClientStreamTracer.Factory streamTracerFactory;
        @Nullable
        private final Subchannel subchannel;

        private PickResult(@Nullable Subchannel subchannel2, @Nullable p043io.grpc.ClientStreamTracer.Factory factory, Status status2, boolean z) {
            this.subchannel = subchannel2;
            this.streamTracerFactory = factory;
            this.status = (Status) Preconditions.checkNotNull(status2, "status");
            this.drop = z;
        }

        public static PickResult withSubchannel(Subchannel subchannel2, @Nullable p043io.grpc.ClientStreamTracer.Factory factory) {
            return new PickResult((Subchannel) Preconditions.checkNotNull(subchannel2, "subchannel"), factory, Status.f3691OK, false);
        }

        public static PickResult withSubchannel(Subchannel subchannel2) {
            return withSubchannel(subchannel2, null);
        }

        public static PickResult withError(Status status2) {
            Preconditions.checkArgument(!status2.isOk(), "error status shouldn't be OK");
            return new PickResult(null, null, status2, false);
        }

        public static PickResult withDrop(Status status2) {
            Preconditions.checkArgument(!status2.isOk(), "drop status shouldn't be OK");
            return new PickResult(null, null, status2, true);
        }

        public static PickResult withNoResult() {
            return NO_RESULT;
        }

        @Nullable
        public Subchannel getSubchannel() {
            return this.subchannel;
        }

        @Nullable
        public p043io.grpc.ClientStreamTracer.Factory getStreamTracerFactory() {
            return this.streamTracerFactory;
        }

        public Status getStatus() {
            return this.status;
        }

        public boolean isDrop() {
            return this.drop;
        }

        public String toString() {
            return MoreObjects.toStringHelper((Object) this).add("subchannel", (Object) this.subchannel).add("streamTracerFactory", (Object) this.streamTracerFactory).add("status", (Object) this.status).add("drop", this.drop).toString();
        }

        public int hashCode() {
            return Objects.hashCode(this.subchannel, this.status, this.streamTracerFactory, Boolean.valueOf(this.drop));
        }

        public boolean equals(Object obj) {
            boolean z = false;
            if (!(obj instanceof PickResult)) {
                return false;
            }
            PickResult pickResult = (PickResult) obj;
            if (Objects.equal(this.subchannel, pickResult.subchannel) && Objects.equal(this.status, pickResult.status) && Objects.equal(this.streamTracerFactory, pickResult.streamTracerFactory) && this.drop == pickResult.drop) {
                z = true;
            }
            return z;
        }
    }

    /* renamed from: io.grpc.LoadBalancer$PickSubchannelArgs */
    public static abstract class PickSubchannelArgs {
        public abstract CallOptions getCallOptions();

        public abstract Metadata getHeaders();

        public abstract MethodDescriptor<?, ?> getMethodDescriptor();
    }

    /* renamed from: io.grpc.LoadBalancer$Subchannel */
    public static abstract class Subchannel {
        public abstract Attributes getAttributes();

        public abstract void requestConnection();

        public abstract void shutdown();

        public EquivalentAddressGroup getAddresses() {
            List allAddresses = getAllAddresses();
            boolean z = true;
            if (allAddresses.size() != 1) {
                z = false;
            }
            Preconditions.checkState(z, "Does not have exactly one group");
            return (EquivalentAddressGroup) allAddresses.get(0);
        }

        public List<EquivalentAddressGroup> getAllAddresses() {
            throw new UnsupportedOperationException();
        }
    }

    public abstract void handleNameResolutionError(Status status);

    public abstract void handleResolvedAddressGroups(List<EquivalentAddressGroup> list, Attributes attributes);

    public abstract void handleSubchannelState(Subchannel subchannel, ConnectivityStateInfo connectivityStateInfo);

    public abstract void shutdown();

    public String toString() {
        return getClass().getSimpleName();
    }
}
