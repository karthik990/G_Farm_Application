package p043io.netty.resolver;

import java.util.List;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.Promise;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.resolver.SimpleNameResolver */
public abstract class SimpleNameResolver<T> implements NameResolver<T> {
    private final EventExecutor executor;

    public void close() {
    }

    /* access modifiers changed from: protected */
    public abstract void doResolve(String str, Promise<T> promise) throws Exception;

    /* access modifiers changed from: protected */
    public abstract void doResolveAll(String str, Promise<List<T>> promise) throws Exception;

    protected SimpleNameResolver(EventExecutor eventExecutor) {
        this.executor = (EventExecutor) ObjectUtil.checkNotNull(eventExecutor, "executor");
    }

    /* access modifiers changed from: protected */
    public EventExecutor executor() {
        return this.executor;
    }

    public final Future<T> resolve(String str) {
        return resolve(str, executor().newPromise());
    }

    public Future<T> resolve(String str, Promise<T> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            doResolve(str, promise);
            return promise;
        } catch (Exception e) {
            return promise.setFailure(e);
        }
    }

    public final Future<List<T>> resolveAll(String str) {
        return resolveAll(str, executor().newPromise());
    }

    public Future<List<T>> resolveAll(String str, Promise<List<T>> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        try {
            doResolveAll(str, promise);
            return promise;
        } catch (Exception e) {
            return promise.setFailure(e);
        }
    }
}
