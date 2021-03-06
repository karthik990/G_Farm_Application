package p043io.grpc.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import p043io.grpc.NameResolver;
import p043io.grpc.NameResolver.Listener;

/* renamed from: io.grpc.internal.ForwardingNameResolver */
abstract class ForwardingNameResolver extends NameResolver {
    private final NameResolver delegate;

    ForwardingNameResolver(NameResolver nameResolver) {
        Preconditions.checkNotNull(nameResolver, "delegate can not be null");
        this.delegate = nameResolver;
    }

    public String getServiceAuthority() {
        return this.delegate.getServiceAuthority();
    }

    public void start(Listener listener) {
        this.delegate.start(listener);
    }

    public void shutdown() {
        this.delegate.shutdown();
    }

    public void refresh() {
        this.delegate.refresh();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add("delegate", (Object) this.delegate).toString();
    }
}
