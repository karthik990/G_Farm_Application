package p043io.netty.resolver;

import androidx.exifinterface.media.ExifInterface;
import java.net.SocketAddress;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Collections;
import java.util.List;
import p043io.netty.util.concurrent.EventExecutor;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.Promise;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.TypeParameterMatcher;

/* renamed from: io.netty.resolver.AbstractAddressResolver */
public abstract class AbstractAddressResolver<T extends SocketAddress> implements AddressResolver<T> {
    private final EventExecutor executor;
    private final TypeParameterMatcher matcher;

    public void close() {
    }

    /* access modifiers changed from: protected */
    public abstract boolean doIsResolved(T t);

    /* access modifiers changed from: protected */
    public abstract void doResolve(T t, Promise<T> promise) throws Exception;

    /* access modifiers changed from: protected */
    public abstract void doResolveAll(T t, Promise<List<T>> promise) throws Exception;

    protected AbstractAddressResolver(EventExecutor eventExecutor) {
        this.executor = (EventExecutor) ObjectUtil.checkNotNull(eventExecutor, "executor");
        this.matcher = TypeParameterMatcher.find(this, AbstractAddressResolver.class, ExifInterface.GPS_DIRECTION_TRUE);
    }

    protected AbstractAddressResolver(EventExecutor eventExecutor, Class<? extends T> cls) {
        this.executor = (EventExecutor) ObjectUtil.checkNotNull(eventExecutor, "executor");
        this.matcher = TypeParameterMatcher.get(cls);
    }

    /* access modifiers changed from: protected */
    public EventExecutor executor() {
        return this.executor;
    }

    public boolean isSupported(SocketAddress socketAddress) {
        return this.matcher.match(socketAddress);
    }

    public final boolean isResolved(SocketAddress socketAddress) {
        if (isSupported(socketAddress)) {
            return doIsResolved(socketAddress);
        }
        throw new UnsupportedAddressTypeException();
    }

    public final Future<T> resolve(SocketAddress socketAddress) {
        if (!isSupported((SocketAddress) ObjectUtil.checkNotNull(socketAddress, "address"))) {
            return executor().newFailedFuture(new UnsupportedAddressTypeException());
        }
        if (isResolved(socketAddress)) {
            return this.executor.newSucceededFuture(socketAddress);
        }
        try {
            Promise newPromise = executor().newPromise();
            doResolve(socketAddress, newPromise);
            return newPromise;
        } catch (Exception e) {
            return executor().newFailedFuture(e);
        }
    }

    public final Future<T> resolve(SocketAddress socketAddress, Promise<T> promise) {
        ObjectUtil.checkNotNull(socketAddress, "address");
        ObjectUtil.checkNotNull(promise, "promise");
        if (!isSupported(socketAddress)) {
            return promise.setFailure(new UnsupportedAddressTypeException());
        }
        if (isResolved(socketAddress)) {
            return promise.setSuccess(socketAddress);
        }
        try {
            doResolve(socketAddress, promise);
            return promise;
        } catch (Exception e) {
            return promise.setFailure(e);
        }
    }

    public final Future<List<T>> resolveAll(SocketAddress socketAddress) {
        if (!isSupported((SocketAddress) ObjectUtil.checkNotNull(socketAddress, "address"))) {
            return executor().newFailedFuture(new UnsupportedAddressTypeException());
        }
        if (isResolved(socketAddress)) {
            return this.executor.newSucceededFuture(Collections.singletonList(socketAddress));
        }
        try {
            Promise newPromise = executor().newPromise();
            doResolveAll(socketAddress, newPromise);
            return newPromise;
        } catch (Exception e) {
            return executor().newFailedFuture(e);
        }
    }

    public final Future<List<T>> resolveAll(SocketAddress socketAddress, Promise<List<T>> promise) {
        ObjectUtil.checkNotNull(socketAddress, "address");
        ObjectUtil.checkNotNull(promise, "promise");
        if (!isSupported(socketAddress)) {
            return promise.setFailure(new UnsupportedAddressTypeException());
        }
        if (isResolved(socketAddress)) {
            return promise.setSuccess(Collections.singletonList(socketAddress));
        }
        try {
            doResolveAll(socketAddress, promise);
            return promise;
        } catch (Exception e) {
            return promise.setFailure(e);
        }
    }
}
