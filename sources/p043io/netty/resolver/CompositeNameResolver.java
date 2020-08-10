package p043io.netty.resolver;

import java.util.Arrays;
import java.util.List;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.FutureListener;
import p043io.netty.util.concurrent.Promise;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.resolver.CompositeNameResolver */
public final class CompositeNameResolver<T> extends SimpleNameResolver<T> {
    private final NameResolver<T>[] resolvers;

    public CompositeNameResolver(EventExecutor eventExecutor, NameResolver<T>... nameResolverArr) {
        super(eventExecutor);
        ObjectUtil.checkNotNull(nameResolverArr, "resolvers");
        int i = 0;
        while (i < nameResolverArr.length) {
            if (nameResolverArr[i] != null) {
                i++;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("resolvers[");
                sb.append(i);
                sb.append(']');
                throw new NullPointerException(sb.toString());
            }
        }
        if (nameResolverArr.length >= 2) {
            this.resolvers = (NameResolver[]) nameResolverArr.clone();
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("resolvers: ");
        sb2.append(Arrays.asList(nameResolverArr));
        sb2.append(" (expected: at least 2 resolvers)");
        throw new IllegalArgumentException(sb2.toString());
    }

    /* access modifiers changed from: protected */
    public void doResolve(String str, Promise<T> promise) throws Exception {
        doResolveRec(str, promise, 0, null);
    }

    /* access modifiers changed from: private */
    public void doResolveRec(final String str, final Promise<T> promise, final int i, Throwable th) throws Exception {
        NameResolver<T>[] nameResolverArr = this.resolvers;
        if (i >= nameResolverArr.length) {
            promise.setFailure(th);
        } else {
            nameResolverArr[i].resolve(str).addListener(new FutureListener<T>() {
                public void operationComplete(Future<T> future) throws Exception {
                    if (future.isSuccess()) {
                        promise.setSuccess(future.getNow());
                    } else {
                        CompositeNameResolver.this.doResolveRec(str, promise, i + 1, future.cause());
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void doResolveAll(String str, Promise<List<T>> promise) throws Exception {
        doResolveAllRec(str, promise, 0, null);
    }

    /* access modifiers changed from: private */
    public void doResolveAllRec(final String str, final Promise<List<T>> promise, final int i, Throwable th) throws Exception {
        NameResolver<T>[] nameResolverArr = this.resolvers;
        if (i >= nameResolverArr.length) {
            promise.setFailure(th);
        } else {
            nameResolverArr[i].resolveAll(str).addListener(new FutureListener<List<T>>() {
                public void operationComplete(Future<List<T>> future) throws Exception {
                    if (future.isSuccess()) {
                        promise.setSuccess(future.getNow());
                    } else {
                        CompositeNameResolver.this.doResolveAllRec(str, promise, i + 1, future.cause());
                    }
                }
            });
        }
    }
}
