package p043io.netty.resolver;

import java.net.InetSocketAddress;
import p043io.netty.util.concurrent.EventExecutor;

/* renamed from: io.netty.resolver.DefaultAddressResolverGroup */
public final class DefaultAddressResolverGroup extends AddressResolverGroup<InetSocketAddress> {
    public static final DefaultAddressResolverGroup INSTANCE = new DefaultAddressResolverGroup();

    private DefaultAddressResolverGroup() {
    }

    /* access modifiers changed from: protected */
    public AddressResolver<InetSocketAddress> newResolver(EventExecutor eventExecutor) throws Exception {
        return new DefaultNameResolver(eventExecutor).asAddressResolver();
    }
}
