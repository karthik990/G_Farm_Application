package p043io.grpc.internal;

import javax.annotation.Nullable;
import p043io.grpc.InternalChannelz.ChannelStats;
import p043io.grpc.InternalInstrumented;
import p043io.grpc.LoadBalancer.Subchannel;

/* renamed from: io.grpc.internal.AbstractSubchannel */
abstract class AbstractSubchannel extends Subchannel {
    /* access modifiers changed from: 0000 */
    public abstract InternalInstrumented<ChannelStats> getInternalSubchannel();

    /* access modifiers changed from: 0000 */
    @Nullable
    public abstract ClientTransport obtainActiveTransport();

    AbstractSubchannel() {
    }
}
