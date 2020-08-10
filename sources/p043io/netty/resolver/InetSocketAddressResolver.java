package p043io.netty.resolver;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.FutureListener;
import p043io.netty.util.concurrent.Promise;

/* renamed from: io.netty.resolver.InetSocketAddressResolver */
public class InetSocketAddressResolver extends AbstractAddressResolver<InetSocketAddress> {
    final NameResolver<InetAddress> nameResolver;

    public InetSocketAddressResolver(EventExecutor eventExecutor, NameResolver<InetAddress> nameResolver2) {
        super(eventExecutor, InetSocketAddress.class);
        this.nameResolver = nameResolver2;
    }

    /* access modifiers changed from: protected */
    public boolean doIsResolved(InetSocketAddress inetSocketAddress) {
        return !inetSocketAddress.isUnresolved();
    }

    /* access modifiers changed from: protected */
    public void doResolve(final InetSocketAddress inetSocketAddress, final Promise<InetSocketAddress> promise) throws Exception {
        this.nameResolver.resolve(inetSocketAddress.getHostName()).addListener(new FutureListener<InetAddress>() {
            public void operationComplete(Future<InetAddress> future) throws Exception {
                if (future.isSuccess()) {
                    promise.setSuccess(new InetSocketAddress((InetAddress) future.getNow(), inetSocketAddress.getPort()));
                } else {
                    promise.setFailure(future.cause());
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void doResolveAll(final InetSocketAddress inetSocketAddress, final Promise<List<InetSocketAddress>> promise) throws Exception {
        this.nameResolver.resolveAll(inetSocketAddress.getHostName()).addListener(new FutureListener<List<InetAddress>>() {
            public void operationComplete(Future<List<InetAddress>> future) throws Exception {
                if (future.isSuccess()) {
                    List<InetAddress> list = (List) future.getNow();
                    ArrayList arrayList = new ArrayList(list.size());
                    for (InetAddress inetSocketAddress : list) {
                        arrayList.add(new InetSocketAddress(inetSocketAddress, inetSocketAddress.getPort()));
                    }
                    promise.setSuccess(arrayList);
                    return;
                }
                promise.setFailure(future.cause());
            }
        });
    }

    public void close() {
        this.nameResolver.close();
    }
}
