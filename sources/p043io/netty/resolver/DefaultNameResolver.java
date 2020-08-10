package p043io.netty.resolver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Promise;
import p043io.netty.util.internal.SocketUtils;

/* renamed from: io.netty.resolver.DefaultNameResolver */
public class DefaultNameResolver extends InetNameResolver {
    public DefaultNameResolver(EventExecutor eventExecutor) {
        super(eventExecutor);
    }

    /* access modifiers changed from: protected */
    public void doResolve(String str, Promise<InetAddress> promise) throws Exception {
        try {
            promise.setSuccess(SocketUtils.addressByName(str));
        } catch (UnknownHostException e) {
            promise.setFailure(e);
        }
    }

    /* access modifiers changed from: protected */
    public void doResolveAll(String str, Promise<List<InetAddress>> promise) throws Exception {
        try {
            promise.setSuccess(Arrays.asList(SocketUtils.allAddressesByName(str)));
        } catch (UnknownHostException e) {
            promise.setFailure(e);
        }
    }
}
