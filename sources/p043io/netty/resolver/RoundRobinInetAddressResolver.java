package p043io.netty.resolver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.FutureListener;
import p043io.netty.util.concurrent.Promise;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.resolver.RoundRobinInetAddressResolver */
public class RoundRobinInetAddressResolver extends InetNameResolver {
    private final NameResolver<InetAddress> nameResolver;

    public RoundRobinInetAddressResolver(EventExecutor eventExecutor, NameResolver<InetAddress> nameResolver2) {
        super(eventExecutor);
        this.nameResolver = nameResolver2;
    }

    /* access modifiers changed from: protected */
    public void doResolve(final String str, final Promise<InetAddress> promise) throws Exception {
        this.nameResolver.resolveAll(str).addListener(new FutureListener<List<InetAddress>>() {
            public void operationComplete(Future<List<InetAddress>> future) throws Exception {
                if (future.isSuccess()) {
                    List list = (List) future.getNow();
                    int size = list.size();
                    if (size > 0) {
                        promise.setSuccess(list.get(RoundRobinInetAddressResolver.randomIndex(size)));
                    } else {
                        promise.setFailure(new UnknownHostException(str));
                    }
                } else {
                    promise.setFailure(future.cause());
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void doResolveAll(String str, final Promise<List<InetAddress>> promise) throws Exception {
        this.nameResolver.resolveAll(str).addListener(new FutureListener<List<InetAddress>>() {
            public void operationComplete(Future<List<InetAddress>> future) throws Exception {
                if (future.isSuccess()) {
                    List list = (List) future.getNow();
                    if (!list.isEmpty()) {
                        ArrayList arrayList = new ArrayList(list);
                        Collections.rotate(arrayList, RoundRobinInetAddressResolver.randomIndex(list.size()));
                        promise.setSuccess(arrayList);
                        return;
                    }
                    promise.setSuccess(list);
                    return;
                }
                promise.setFailure(future.cause());
            }
        });
    }

    /* access modifiers changed from: private */
    public static int randomIndex(int i) {
        if (i == 1) {
            return 0;
        }
        return PlatformDependent.threadLocalRandom().nextInt(i);
    }
}
