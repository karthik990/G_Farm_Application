package p043io.netty.resolver;

import java.io.Closeable;
import java.net.SocketAddress;
import java.util.IdentityHashMap;
import java.util.Map;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.FutureListener;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.resolver.AddressResolverGroup */
public abstract class AddressResolverGroup<T extends SocketAddress> implements Closeable {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(AddressResolverGroup.class);
    /* access modifiers changed from: private */
    public final Map<EventExecutor, AddressResolver<T>> resolvers = new IdentityHashMap();

    /* access modifiers changed from: protected */
    public abstract AddressResolver<T> newResolver(EventExecutor eventExecutor) throws Exception;

    protected AddressResolverGroup() {
    }

    public AddressResolver<T> getResolver(final EventExecutor eventExecutor) {
        final AddressResolver<T> addressResolver;
        if (eventExecutor == null) {
            throw new NullPointerException("executor");
        } else if (!eventExecutor.isShuttingDown()) {
            synchronized (this.resolvers) {
                addressResolver = (AddressResolver) this.resolvers.get(eventExecutor);
                if (addressResolver == null) {
                    try {
                        addressResolver = newResolver(eventExecutor);
                        this.resolvers.put(eventExecutor, addressResolver);
                        eventExecutor.terminationFuture().addListener(new FutureListener<Object>() {
                            public void operationComplete(Future<Object> future) throws Exception {
                                synchronized (AddressResolverGroup.this.resolvers) {
                                    AddressResolverGroup.this.resolvers.remove(eventExecutor);
                                }
                                addressResolver.close();
                            }
                        });
                    } catch (Exception e) {
                        throw new IllegalStateException("failed to create a new resolver", e);
                    }
                }
            }
            return addressResolver;
        } else {
            throw new IllegalStateException("executor not accepting a task");
        }
    }

    public void close() {
        AddressResolver[] addressResolverArr;
        synchronized (this.resolvers) {
            addressResolverArr = (AddressResolver[]) this.resolvers.values().toArray(new AddressResolver[this.resolvers.size()]);
            this.resolvers.clear();
        }
        for (AddressResolver close : addressResolverArr) {
            try {
                close.close();
            } catch (Throwable th) {
                logger.warn("Failed to close a resolver:", th);
            }
        }
    }
}
