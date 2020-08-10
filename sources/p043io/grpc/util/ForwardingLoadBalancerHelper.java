package p043io.grpc.util;

import com.google.common.base.MoreObjects;
import java.util.List;
import p043io.grpc.Attributes;
import p043io.grpc.ConnectivityState;
import p043io.grpc.EquivalentAddressGroup;
import p043io.grpc.LoadBalancer.Helper;
import p043io.grpc.LoadBalancer.Subchannel;
import p043io.grpc.LoadBalancer.SubchannelPicker;
import p043io.grpc.ManagedChannel;
import p043io.grpc.NameResolver.Factory;

/* renamed from: io.grpc.util.ForwardingLoadBalancerHelper */
public abstract class ForwardingLoadBalancerHelper extends Helper {
    /* access modifiers changed from: protected */
    public abstract Helper delegate();

    public Subchannel createSubchannel(EquivalentAddressGroup equivalentAddressGroup, Attributes attributes) {
        return delegate().createSubchannel(equivalentAddressGroup, attributes);
    }

    public Subchannel createSubchannel(List<EquivalentAddressGroup> list, Attributes attributes) {
        return delegate().createSubchannel(list, attributes);
    }

    public void updateSubchannelAddresses(Subchannel subchannel, EquivalentAddressGroup equivalentAddressGroup) {
        delegate().updateSubchannelAddresses(subchannel, equivalentAddressGroup);
    }

    public void updateSubchannelAddresses(Subchannel subchannel, List<EquivalentAddressGroup> list) {
        delegate().updateSubchannelAddresses(subchannel, list);
    }

    public ManagedChannel createOobChannel(EquivalentAddressGroup equivalentAddressGroup, String str) {
        return delegate().createOobChannel(equivalentAddressGroup, str);
    }

    public void updateOobChannelAddresses(ManagedChannel managedChannel, EquivalentAddressGroup equivalentAddressGroup) {
        delegate().updateOobChannelAddresses(managedChannel, equivalentAddressGroup);
    }

    public void updateBalancingState(ConnectivityState connectivityState, SubchannelPicker subchannelPicker) {
        delegate().updateBalancingState(connectivityState, subchannelPicker);
    }

    public void runSerialized(Runnable runnable) {
        delegate().runSerialized(runnable);
    }

    public Factory getNameResolverFactory() {
        return delegate().getNameResolverFactory();
    }

    public String getAuthority() {
        return delegate().getAuthority();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("delegate", (Object) delegate()).toString();
    }
}
