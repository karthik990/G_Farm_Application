package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.lang.Throwable;
import java.util.concurrent.Executor;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractCatchingFuture<V, X extends Throwable, F, T> extends TrustedFuture<V> implements Runnable {
    @NullableDecl
    Class<X> exceptionType;
    @NullableDecl
    F fallback;
    @NullableDecl
    ListenableFuture<? extends V> inputFuture;

    private static final class AsyncCatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, AsyncFunction<? super X, ? extends V>, ListenableFuture<? extends V>> {
        AsyncCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction) {
            super(listenableFuture, cls, asyncFunction);
        }

        /* access modifiers changed from: 0000 */
        public ListenableFuture<? extends V> doFallback(AsyncFunction<? super X, ? extends V> asyncFunction, X x) throws Exception {
            ListenableFuture<? extends V> apply = asyncFunction.apply(x);
            Preconditions.checkNotNull(apply, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
            return apply;
        }

        /* access modifiers changed from: 0000 */
        public void setResult(ListenableFuture<? extends V> listenableFuture) {
            setFuture(listenableFuture);
        }
    }

    private static final class CatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, Function<? super X, ? extends V>, V> {
        CatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function) {
            super(listenableFuture, cls, function);
        }

        /* access modifiers changed from: 0000 */
        @NullableDecl
        public V doFallback(Function<? super X, ? extends V> function, X x) throws Exception {
            return function.apply(x);
        }

        /* access modifiers changed from: 0000 */
        public void setResult(@NullableDecl V v) {
            set(v);
        }
    }

    /* access modifiers changed from: 0000 */
    @NullableDecl
    public abstract T doFallback(F f, X x) throws Exception;

    /* access modifiers changed from: 0000 */
    public abstract void setResult(@NullableDecl T t);

    static <V, X extends Throwable> ListenableFuture<V> create(ListenableFuture<? extends V> listenableFuture, Class<X> cls, Function<? super X, ? extends V> function, Executor executor) {
        CatchingFuture catchingFuture = new CatchingFuture(listenableFuture, cls, function);
        listenableFuture.addListener(catchingFuture, MoreExecutors.rejectionPropagatingExecutor(executor, catchingFuture));
        return catchingFuture;
    }

    static <X extends Throwable, V> ListenableFuture<V> create(ListenableFuture<? extends V> listenableFuture, Class<X> cls, AsyncFunction<? super X, ? extends V> asyncFunction, Executor executor) {
        AsyncCatchingFuture asyncCatchingFuture = new AsyncCatchingFuture(listenableFuture, cls, asyncFunction);
        listenableFuture.addListener(asyncCatchingFuture, MoreExecutors.rejectionPropagatingExecutor(executor, asyncCatchingFuture));
        return asyncCatchingFuture;
    }

    AbstractCatchingFuture(ListenableFuture<? extends V> listenableFuture, Class<X> cls, F f) {
        this.inputFuture = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        this.exceptionType = (Class) Preconditions.checkNotNull(cls);
        this.fallback = Preconditions.checkNotNull(f);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r7 = this;
            com.google.common.util.concurrent.ListenableFuture<? extends V> r0 = r7.inputFuture
            java.lang.Class<X> r1 = r7.exceptionType
            F r2 = r7.fallback
            r3 = 1
            r4 = 0
            if (r0 != 0) goto L_0x000c
            r5 = 1
            goto L_0x000d
        L_0x000c:
            r5 = 0
        L_0x000d:
            if (r1 != 0) goto L_0x0011
            r6 = 1
            goto L_0x0012
        L_0x0011:
            r6 = 0
        L_0x0012:
            r5 = r5 | r6
            if (r2 != 0) goto L_0x0016
            goto L_0x0017
        L_0x0016:
            r3 = 0
        L_0x0017:
            r3 = r3 | r5
            boolean r4 = r7.isCancelled()
            r3 = r3 | r4
            if (r3 == 0) goto L_0x0020
            return
        L_0x0020:
            r3 = 0
            r7.inputFuture = r3
            java.lang.Object r0 = com.google.common.util.concurrent.Futures.getDone(r0)     // Catch:{ ExecutionException -> 0x002c, all -> 0x002a }
            r4 = r0
            r0 = r3
            goto L_0x0038
        L_0x002a:
            r0 = move-exception
            goto L_0x0037
        L_0x002c:
            r0 = move-exception
            java.lang.Throwable r0 = r0.getCause()
            java.lang.Object r0 = com.google.common.base.Preconditions.checkNotNull(r0)
            java.lang.Throwable r0 = (java.lang.Throwable) r0
        L_0x0037:
            r4 = r3
        L_0x0038:
            if (r0 != 0) goto L_0x003e
            r7.set(r4)
            return
        L_0x003e:
            boolean r1 = com.google.common.util.concurrent.Platform.isInstanceOfThrowableClass(r0, r1)
            if (r1 != 0) goto L_0x0048
            r7.setException(r0)
            return
        L_0x0048:
            java.lang.Object r0 = r7.doFallback(r2, r0)     // Catch:{ all -> 0x0054 }
            r7.exceptionType = r3
            r7.fallback = r3
            r7.setResult(r0)
            return
        L_0x0054:
            r0 = move-exception
            r7.setException(r0)     // Catch:{ all -> 0x005d }
            r7.exceptionType = r3
            r7.fallback = r3
            return
        L_0x005d:
            r0 = move-exception
            r7.exceptionType = r3
            r7.fallback = r3
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractCatchingFuture.run():void");
    }

    /* access modifiers changed from: protected */
    public String pendingToString() {
        String str;
        ListenableFuture<? extends V> listenableFuture = this.inputFuture;
        Class<X> cls = this.exceptionType;
        F f = this.fallback;
        String pendingToString = super.pendingToString();
        if (listenableFuture != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("inputFuture=[");
            sb.append(listenableFuture);
            sb.append("], ");
            str = sb.toString();
        } else {
            str = "";
        }
        if (cls != null && f != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("exceptionType=[");
            sb2.append(cls);
            sb2.append("], fallback=[");
            sb2.append(f);
            sb2.append("]");
            return sb2.toString();
        } else if (pendingToString == null) {
            return null;
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(pendingToString);
            return sb3.toString();
        }
    }

    /* access modifiers changed from: protected */
    public final void afterDone() {
        maybePropagateCancellationTo(this.inputFuture);
        this.inputFuture = null;
        this.exceptionType = null;
        this.fallback = null;
    }
}
