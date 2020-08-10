package p043io.netty.resolver;

import java.net.SocketAddress;
import p043io.netty.util.concurrent.EventExecutor;

/* renamed from: io.netty.resolver.NoopAddressResolverGroup */
public final class NoopAddressResolverGroup extends AddressResolverGroup<SocketAddress> {
    public static final NoopAddressResolverGroup INSTANCE = new NoopAddressResolverGroup();

    private NoopAddressResolverGroup() {
    }

    /* access modifiers changed from: protected */
    public AddressResolver<SocketAddress> newResolver(EventExecutor eventExecutor) throws Exception {
        return new NoopAddressResolver(eventExecutor);
    }
}
