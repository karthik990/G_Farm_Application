package p043io.netty.util.concurrent;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs.CastExtraArgs;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import p043io.netty.util.Signal;
import p043io.netty.util.internal.InternalThreadLocalMap;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.ThrowableUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.concurrent.DefaultPromise */
public class DefaultPromise<V> extends AbstractFuture<V> implements Promise<V> {
    private static final CauseHolder CANCELLATION_CAUSE_HOLDER;
    private static final int MAX_LISTENER_STACK_DEPTH = Math.min(8, SystemPropertyUtil.getInt("io.netty.defaultPromise.maxListenerStackDepth", 8));
    private static final AtomicReferenceFieldUpdater<DefaultPromise, Object> RESULT_UPDATER;
    private static final Signal SUCCESS;
    private static final Signal UNCANCELLABLE;
    private static final InternalLogger logger;
    private static final InternalLogger rejectedExecutionLogger;
    private final EventExecutor executor;
    private Object listeners;
    private boolean notifyingListeners;
    private volatile Object result;
    private short waiters;

    /* renamed from: io.netty.util.concurrent.DefaultPromise$CauseHolder */
    private static final class CauseHolder {
        final Throwable cause;

        CauseHolder(Throwable th) {
            this.cause = th;
        }
    }

    static {
        Class<DefaultPromise> cls = DefaultPromise.class;
        logger = InternalLoggerFactory.getInstance(cls);
        StringBuilder sb = new StringBuilder();
        sb.append(cls.getName());
        sb.append(".rejectedExecution");
        rejectedExecutionLogger = InternalLoggerFactory.getInstance(sb.toString());
        RESULT_UPDATER = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "result");
        SUCCESS = Signal.valueOf(cls, "SUCCESS");
        UNCANCELLABLE = Signal.valueOf(cls, "UNCANCELLABLE");
        CANCELLATION_CAUSE_HOLDER = new CauseHolder(ThrowableUtil.unknownStackTrace(new CancellationException(), cls, "cancel(...)"));
    }

    public DefaultPromise(EventExecutor eventExecutor) {
        this.executor = (EventExecutor) ObjectUtil.checkNotNull(eventExecutor, "executor");
    }

    protected DefaultPromise() {
        this.executor = null;
    }

    public Promise<V> setSuccess(V v) {
        if (setSuccess0(v)) {
            notifyListeners();
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("complete already: ");
        sb.append(this);
        throw new IllegalStateException(sb.toString());
    }

    public boolean trySuccess(V v) {
        if (!setSuccess0(v)) {
            return false;
        }
        notifyListeners();
        return true;
    }

    public Promise<V> setFailure(Throwable th) {
        if (setFailure0(th)) {
            notifyListeners();
            return this;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("complete already: ");
        sb.append(this);
        throw new IllegalStateException(sb.toString(), th);
    }

    public boolean tryFailure(Throwable th) {
        if (!setFailure0(th)) {
            return false;
        }
        notifyListeners();
        return true;
    }

    public boolean setUncancellable() {
        boolean z = true;
        if (RESULT_UPDATER.compareAndSet(this, null, UNCANCELLABLE)) {
            return true;
        }
        Object obj = this.result;
        if (isDone0(obj) && isCancelled0(obj)) {
            z = false;
        }
        return z;
    }

    public boolean isSuccess() {
        Object obj = this.result;
        return (obj == null || obj == UNCANCELLABLE || (obj instanceof CauseHolder)) ? false : true;
    }

    public boolean isCancellable() {
        return this.result == null;
    }

    public Throwable cause() {
        Object obj = this.result;
        if (obj instanceof CauseHolder) {
            return ((CauseHolder) obj).cause;
        }
        return null;
    }

    public Promise<V> addListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        ObjectUtil.checkNotNull(genericFutureListener, CastExtraArgs.LISTENER);
        synchronized (this) {
            addListener0(genericFutureListener);
        }
        if (isDone()) {
            notifyListeners();
        }
        return this;
    }

    public Promise<V> addListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        ObjectUtil.checkNotNull(genericFutureListenerArr, "listeners");
        synchronized (this) {
            int length = genericFutureListenerArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                GenericFutureListener<? extends Future<? super V>> genericFutureListener = genericFutureListenerArr[i];
                if (genericFutureListener == null) {
                    break;
                }
                addListener0(genericFutureListener);
                i++;
            }
        }
        if (isDone()) {
            notifyListeners();
        }
        return this;
    }

    public Promise<V> removeListener(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        ObjectUtil.checkNotNull(genericFutureListener, CastExtraArgs.LISTENER);
        synchronized (this) {
            removeListener0(genericFutureListener);
        }
        return this;
    }

    public Promise<V> removeListeners(GenericFutureListener<? extends Future<? super V>>... genericFutureListenerArr) {
        ObjectUtil.checkNotNull(genericFutureListenerArr, "listeners");
        synchronized (this) {
            int length = genericFutureListenerArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                GenericFutureListener<? extends Future<? super V>> genericFutureListener = genericFutureListenerArr[i];
                if (genericFutureListener == null) {
                    break;
                }
                removeListener0(genericFutureListener);
                i++;
            }
        }
        return this;
    }

    /* JADX INFO: finally extract failed */
    public Promise<V> await() throws InterruptedException {
        if (isDone()) {
            return this;
        }
        if (!Thread.interrupted()) {
            checkDeadLock();
            synchronized (this) {
                while (!isDone()) {
                    incWaiters();
                    try {
                        wait();
                        decWaiters();
                    } catch (Throwable th) {
                        decWaiters();
                        throw th;
                    }
                }
            }
            return this;
        }
        throw new InterruptedException(toString());
    }

    public Promise<V> awaitUninterruptibly() {
        if (isDone()) {
            return this;
        }
        checkDeadLock();
        boolean z = false;
        synchronized (this) {
            while (!isDone()) {
                incWaiters();
                try {
                    wait();
                    decWaiters();
                } catch (InterruptedException unused) {
                    z = true;
                    decWaiters();
                } catch (Throwable th) {
                    decWaiters();
                    throw th;
                }
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return this;
    }

    public boolean await(long j, TimeUnit timeUnit) throws InterruptedException {
        return await0(timeUnit.toNanos(j), true);
    }

    public boolean await(long j) throws InterruptedException {
        return await0(TimeUnit.MILLISECONDS.toNanos(j), true);
    }

    public boolean awaitUninterruptibly(long j, TimeUnit timeUnit) {
        try {
            return await0(timeUnit.toNanos(j), false);
        } catch (InterruptedException unused) {
            throw new InternalError();
        }
    }

    public boolean awaitUninterruptibly(long j) {
        try {
            return await0(TimeUnit.MILLISECONDS.toNanos(j), false);
        } catch (InterruptedException unused) {
            throw new InternalError();
        }
    }

    public V getNow() {
        V v = this.result;
        if ((v instanceof CauseHolder) || v == SUCCESS) {
            return null;
        }
        return v;
    }

    public boolean cancel(boolean z) {
        if (!RESULT_UPDATER.compareAndSet(this, null, CANCELLATION_CAUSE_HOLDER)) {
            return false;
        }
        checkNotifyWaiters();
        notifyListeners();
        return true;
    }

    public boolean isCancelled() {
        return isCancelled0(this.result);
    }

    public boolean isDone() {
        return isDone0(this.result);
    }

    public Promise<V> sync() throws InterruptedException {
        await();
        rethrowIfFailed();
        return this;
    }

    public Promise<V> syncUninterruptibly() {
        awaitUninterruptibly();
        rethrowIfFailed();
        return this;
    }

    public String toString() {
        return toStringBuilder().toString();
    }

    /* access modifiers changed from: protected */
    public StringBuilder toStringBuilder() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(StringUtil.simpleClassName((Object) this));
        sb.append('@');
        sb.append(Integer.toHexString(hashCode()));
        Object obj = this.result;
        if (obj == SUCCESS) {
            sb.append("(success)");
        } else if (obj == UNCANCELLABLE) {
            sb.append("(uncancellable)");
        } else if (obj instanceof CauseHolder) {
            sb.append("(failure: ");
            sb.append(((CauseHolder) obj).cause);
            sb.append(')');
        } else if (obj != null) {
            sb.append("(success: ");
            sb.append(obj);
            sb.append(')');
        } else {
            sb.append("(incomplete)");
        }
        return sb;
    }

    /* access modifiers changed from: protected */
    public EventExecutor executor() {
        return this.executor;
    }

    /* access modifiers changed from: protected */
    public void checkDeadLock() {
        EventExecutor executor2 = executor();
        if (executor2 != null && executor2.inEventLoop()) {
            throw new BlockingOperationException(toString());
        }
    }

    protected static void notifyListener(EventExecutor eventExecutor, Future<?> future, GenericFutureListener<?> genericFutureListener) {
        ObjectUtil.checkNotNull(eventExecutor, "eventExecutor");
        ObjectUtil.checkNotNull(future, "future");
        ObjectUtil.checkNotNull(genericFutureListener, CastExtraArgs.LISTENER);
        notifyListenerWithStackOverFlowProtection(eventExecutor, future, genericFutureListener);
    }

    private void notifyListeners() {
        EventExecutor executor2 = executor();
        if (executor2.inEventLoop()) {
            InternalThreadLocalMap internalThreadLocalMap = InternalThreadLocalMap.get();
            int futureListenerStackDepth = internalThreadLocalMap.futureListenerStackDepth();
            if (futureListenerStackDepth < MAX_LISTENER_STACK_DEPTH) {
                internalThreadLocalMap.setFutureListenerStackDepth(futureListenerStackDepth + 1);
                try {
                    notifyListenersNow();
                    return;
                } finally {
                    internalThreadLocalMap.setFutureListenerStackDepth(futureListenerStackDepth);
                }
            }
        }
        safeExecute(executor2, new Runnable() {
            public void run() {
                DefaultPromise.this.notifyListenersNow();
            }
        });
    }

    private static void notifyListenerWithStackOverFlowProtection(EventExecutor eventExecutor, final Future<?> future, final GenericFutureListener<?> genericFutureListener) {
        if (eventExecutor.inEventLoop()) {
            InternalThreadLocalMap internalThreadLocalMap = InternalThreadLocalMap.get();
            int futureListenerStackDepth = internalThreadLocalMap.futureListenerStackDepth();
            if (futureListenerStackDepth < MAX_LISTENER_STACK_DEPTH) {
                internalThreadLocalMap.setFutureListenerStackDepth(futureListenerStackDepth + 1);
                try {
                    notifyListener0(future, genericFutureListener);
                    return;
                } finally {
                    internalThreadLocalMap.setFutureListenerStackDepth(futureListenerStackDepth);
                }
            }
        }
        safeExecute(eventExecutor, new Runnable() {
            public void run() {
                DefaultPromise.notifyListener0(future, genericFutureListener);
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        if ((r0 instanceof p043io.netty.util.concurrent.DefaultFutureListeners) == false) goto L_0x001d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
        notifyListeners0((p043io.netty.util.concurrent.DefaultFutureListeners) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001d, code lost:
        notifyListener0(r3, (p043io.netty.util.concurrent.GenericFutureListener) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0022, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0025, code lost:
        if (r3.listeners != null) goto L_0x002c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0027, code lost:
        r3.notifyingListeners = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002a, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002b, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002c, code lost:
        r0 = r3.listeners;
        r3.listeners = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0030, code lost:
        monitor-exit(r3);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void notifyListenersNow() {
        /*
            r3 = this;
            monitor-enter(r3)
            boolean r0 = r3.notifyingListeners     // Catch:{ all -> 0x0037 }
            if (r0 != 0) goto L_0x0035
            java.lang.Object r0 = r3.listeners     // Catch:{ all -> 0x0037 }
            if (r0 != 0) goto L_0x000a
            goto L_0x0035
        L_0x000a:
            r0 = 1
            r3.notifyingListeners = r0     // Catch:{ all -> 0x0037 }
            java.lang.Object r0 = r3.listeners     // Catch:{ all -> 0x0037 }
            r1 = 0
            r3.listeners = r1     // Catch:{ all -> 0x0037 }
            monitor-exit(r3)     // Catch:{ all -> 0x0037 }
        L_0x0013:
            boolean r2 = r0 instanceof p043io.netty.util.concurrent.DefaultFutureListeners
            if (r2 == 0) goto L_0x001d
            io.netty.util.concurrent.DefaultFutureListeners r0 = (p043io.netty.util.concurrent.DefaultFutureListeners) r0
            r3.notifyListeners0(r0)
            goto L_0x0022
        L_0x001d:
            io.netty.util.concurrent.GenericFutureListener r0 = (p043io.netty.util.concurrent.GenericFutureListener) r0
            notifyListener0(r3, r0)
        L_0x0022:
            monitor-enter(r3)
            java.lang.Object r0 = r3.listeners     // Catch:{ all -> 0x0032 }
            if (r0 != 0) goto L_0x002c
            r0 = 0
            r3.notifyingListeners = r0     // Catch:{ all -> 0x0032 }
            monitor-exit(r3)     // Catch:{ all -> 0x0032 }
            return
        L_0x002c:
            java.lang.Object r0 = r3.listeners     // Catch:{ all -> 0x0032 }
            r3.listeners = r1     // Catch:{ all -> 0x0032 }
            monitor-exit(r3)     // Catch:{ all -> 0x0032 }
            goto L_0x0013
        L_0x0032:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0032 }
            throw r0
        L_0x0035:
            monitor-exit(r3)     // Catch:{ all -> 0x0037 }
            return
        L_0x0037:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0037 }
            goto L_0x003b
        L_0x003a:
            throw r0
        L_0x003b:
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.concurrent.DefaultPromise.notifyListenersNow():void");
    }

    private void notifyListeners0(DefaultFutureListeners defaultFutureListeners) {
        GenericFutureListener[] listeners2 = defaultFutureListeners.listeners();
        int size = defaultFutureListeners.size();
        for (int i = 0; i < size; i++) {
            notifyListener0(this, listeners2[i]);
        }
    }

    /* access modifiers changed from: private */
    public static void notifyListener0(Future future, GenericFutureListener genericFutureListener) {
        try {
            genericFutureListener.operationComplete(future);
        } catch (Throwable th) {
            InternalLogger internalLogger = logger;
            StringBuilder sb = new StringBuilder();
            sb.append("An exception was thrown by ");
            sb.append(genericFutureListener.getClass().getName());
            sb.append(".operationComplete()");
            internalLogger.warn(sb.toString(), th);
        }
    }

    private void addListener0(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        Object obj = this.listeners;
        if (obj == null) {
            this.listeners = genericFutureListener;
        } else if (obj instanceof DefaultFutureListeners) {
            ((DefaultFutureListeners) obj).add(genericFutureListener);
        } else {
            this.listeners = new DefaultFutureListeners((GenericFutureListener) obj, genericFutureListener);
        }
    }

    private void removeListener0(GenericFutureListener<? extends Future<? super V>> genericFutureListener) {
        Object obj = this.listeners;
        if (obj instanceof DefaultFutureListeners) {
            ((DefaultFutureListeners) obj).remove(genericFutureListener);
        } else if (obj == genericFutureListener) {
            this.listeners = null;
        }
    }

    private boolean setSuccess0(V v) {
        if (v == null) {
            v = SUCCESS;
        }
        return setValue0(v);
    }

    private boolean setFailure0(Throwable th) {
        return setValue0(new CauseHolder((Throwable) ObjectUtil.checkNotNull(th, "cause")));
    }

    private boolean setValue0(Object obj) {
        if (!RESULT_UPDATER.compareAndSet(this, null, obj) && !RESULT_UPDATER.compareAndSet(this, UNCANCELLABLE, obj)) {
            return false;
        }
        checkNotifyWaiters();
        return true;
    }

    private synchronized void checkNotifyWaiters() {
        if (this.waiters > 0) {
            notifyAll();
        }
    }

    private void incWaiters() {
        short s = this.waiters;
        if (s != Short.MAX_VALUE) {
            this.waiters = (short) (s + 1);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("too many waiters: ");
        sb.append(this);
        throw new IllegalStateException(sb.toString());
    }

    private void decWaiters() {
        this.waiters = (short) (this.waiters - 1);
    }

    private void rethrowIfFailed() {
        Throwable cause = cause();
        if (cause != null) {
            PlatformDependent.throwException(cause);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0037, code lost:
        if (r0 == false) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0039, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0040, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0060, code lost:
        if (isDone() == false) goto L_0x006c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0062, code lost:
        if (r0 == false) goto L_0x006b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0064, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x006b, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        r6 = r13 - (java.lang.System.nanoTime() - r4);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean await0(long r13, boolean r15) throws java.lang.InterruptedException {
        /*
            r12 = this;
            boolean r0 = r12.isDone()
            r1 = 1
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            r2 = 0
            int r0 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r0 > 0) goto L_0x0013
            boolean r13 = r12.isDone()
            return r13
        L_0x0013:
            if (r15 == 0) goto L_0x0026
            boolean r0 = java.lang.Thread.interrupted()
            if (r0 != 0) goto L_0x001c
            goto L_0x0026
        L_0x001c:
            java.lang.InterruptedException r13 = new java.lang.InterruptedException
            java.lang.String r14 = r12.toString()
            r13.<init>(r14)
            throw r13
        L_0x0026:
            r12.checkDeadLock()
            long r4 = java.lang.System.nanoTime()
            r0 = 0
            r6 = r13
        L_0x002f:
            monitor-enter(r12)     // Catch:{ all -> 0x0093 }
            boolean r8 = r12.isDone()     // Catch:{ all -> 0x008a }
            if (r8 == 0) goto L_0x0041
            monitor-exit(r12)     // Catch:{ all -> 0x008a }
            if (r0 == 0) goto L_0x0040
            java.lang.Thread r13 = java.lang.Thread.currentThread()
            r13.interrupt()
        L_0x0040:
            return r1
        L_0x0041:
            r12.incWaiters()     // Catch:{ all -> 0x008a }
            r8 = 1000000(0xf4240, double:4.940656E-318)
            long r10 = r6 / r8
            long r6 = r6 % r8
            int r7 = (int) r6     // Catch:{ InterruptedException -> 0x0054 }
            r12.wait(r10, r7)     // Catch:{ InterruptedException -> 0x0054 }
            r12.decWaiters()     // Catch:{ all -> 0x008a }
            goto L_0x005b
        L_0x0052:
            r13 = move-exception
            goto L_0x0086
        L_0x0054:
            r6 = move-exception
            if (r15 != 0) goto L_0x0085
            r12.decWaiters()     // Catch:{ all -> 0x0091 }
            r0 = 1
        L_0x005b:
            monitor-exit(r12)     // Catch:{ all -> 0x008a }
            boolean r6 = r12.isDone()     // Catch:{ all -> 0x0093 }
            if (r6 == 0) goto L_0x006c
            if (r0 == 0) goto L_0x006b
            java.lang.Thread r13 = java.lang.Thread.currentThread()
            r13.interrupt()
        L_0x006b:
            return r1
        L_0x006c:
            long r6 = java.lang.System.nanoTime()     // Catch:{ all -> 0x0093 }
            long r6 = r6 - r4
            long r6 = r13 - r6
            int r8 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r8 > 0) goto L_0x002f
            boolean r13 = r12.isDone()     // Catch:{ all -> 0x0093 }
            if (r0 == 0) goto L_0x0084
            java.lang.Thread r14 = java.lang.Thread.currentThread()
            r14.interrupt()
        L_0x0084:
            return r13
        L_0x0085:
            throw r6     // Catch:{ all -> 0x0052 }
        L_0x0086:
            r12.decWaiters()     // Catch:{ all -> 0x008a }
            throw r13     // Catch:{ all -> 0x008a }
        L_0x008a:
            r13 = move-exception
            r1 = r0
        L_0x008c:
            monitor-exit(r12)     // Catch:{ all -> 0x0091 }
            throw r13     // Catch:{ all -> 0x008e }
        L_0x008e:
            r13 = move-exception
            r0 = r1
            goto L_0x0094
        L_0x0091:
            r13 = move-exception
            goto L_0x008c
        L_0x0093:
            r13 = move-exception
        L_0x0094:
            if (r0 == 0) goto L_0x009d
            java.lang.Thread r14 = java.lang.Thread.currentThread()
            r14.interrupt()
        L_0x009d:
            goto L_0x009f
        L_0x009e:
            throw r13
        L_0x009f:
            goto L_0x009e
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.concurrent.DefaultPromise.await0(long, boolean):boolean");
    }

    /* access modifiers changed from: 0000 */
    public void notifyProgressiveListeners(long j, long j2) {
        Object progressiveListeners = progressiveListeners();
        if (progressiveListeners != null) {
            final ProgressiveFuture progressiveFuture = (ProgressiveFuture) this;
            EventExecutor executor2 = executor();
            if (executor2.inEventLoop()) {
                if (progressiveListeners instanceof GenericProgressiveFutureListener[]) {
                    notifyProgressiveListeners0(progressiveFuture, (GenericProgressiveFutureListener[]) progressiveListeners, j, j2);
                } else {
                    notifyProgressiveListener0(progressiveFuture, (GenericProgressiveFutureListener) progressiveListeners, j, j2);
                }
            } else if (progressiveListeners instanceof GenericProgressiveFutureListener[]) {
                final GenericProgressiveFutureListener[] genericProgressiveFutureListenerArr = (GenericProgressiveFutureListener[]) progressiveListeners;
                final long j3 = j;
                final long j4 = j2;
                C58283 r1 = new Runnable() {
                    public void run() {
                        DefaultPromise.notifyProgressiveListeners0(progressiveFuture, genericProgressiveFutureListenerArr, j3, j4);
                    }
                };
                safeExecute(executor2, r1);
            } else {
                final GenericProgressiveFutureListener genericProgressiveFutureListener = (GenericProgressiveFutureListener) progressiveListeners;
                final long j5 = j;
                final long j6 = j2;
                C58294 r12 = new Runnable() {
                    public void run() {
                        DefaultPromise.notifyProgressiveListener0(progressiveFuture, genericProgressiveFutureListener, j5, j6);
                    }
                };
                safeExecute(executor2, r12);
            }
        }
    }

    private synchronized Object progressiveListeners() {
        Object obj = this.listeners;
        if (obj == null) {
            return null;
        }
        if (obj instanceof DefaultFutureListeners) {
            DefaultFutureListeners defaultFutureListeners = (DefaultFutureListeners) obj;
            int progressiveSize = defaultFutureListeners.progressiveSize();
            if (progressiveSize == 0) {
                return null;
            }
            int i = 0;
            if (progressiveSize != 1) {
                GenericFutureListener[] listeners2 = defaultFutureListeners.listeners();
                GenericProgressiveFutureListener[] genericProgressiveFutureListenerArr = new GenericProgressiveFutureListener[progressiveSize];
                int i2 = 0;
                while (i < progressiveSize) {
                    GenericFutureListener genericFutureListener = listeners2[i2];
                    if (genericFutureListener instanceof GenericProgressiveFutureListener) {
                        int i3 = i + 1;
                        genericProgressiveFutureListenerArr[i] = (GenericProgressiveFutureListener) genericFutureListener;
                        i = i3;
                    }
                    i2++;
                }
                return genericProgressiveFutureListenerArr;
            }
            GenericFutureListener[] listeners3 = defaultFutureListeners.listeners();
            int length = listeners3.length;
            while (i < length) {
                GenericFutureListener genericFutureListener2 = listeners3[i];
                if (genericFutureListener2 instanceof GenericProgressiveFutureListener) {
                    return genericFutureListener2;
                }
                i++;
            }
            return null;
        } else if (obj instanceof GenericProgressiveFutureListener) {
            return obj;
        } else {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static void notifyProgressiveListeners0(ProgressiveFuture<?> progressiveFuture, GenericProgressiveFutureListener<?>[] genericProgressiveFutureListenerArr, long j, long j2) {
        int length = genericProgressiveFutureListenerArr.length;
        int i = 0;
        while (i < length) {
            GenericProgressiveFutureListener<?> genericProgressiveFutureListener = genericProgressiveFutureListenerArr[i];
            if (genericProgressiveFutureListener != null) {
                notifyProgressiveListener0(progressiveFuture, genericProgressiveFutureListener, j, j2);
                i++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public static void notifyProgressiveListener0(ProgressiveFuture progressiveFuture, GenericProgressiveFutureListener genericProgressiveFutureListener, long j, long j2) {
        try {
            genericProgressiveFutureListener.operationProgressed(progressiveFuture, j, j2);
        } catch (Throwable th) {
            InternalLogger internalLogger = logger;
            StringBuilder sb = new StringBuilder();
            sb.append("An exception was thrown by ");
            sb.append(genericProgressiveFutureListener.getClass().getName());
            sb.append(".operationProgressed()");
            internalLogger.warn(sb.toString(), th);
        }
    }

    private static boolean isCancelled0(Object obj) {
        return (obj instanceof CauseHolder) && (((CauseHolder) obj).cause instanceof CancellationException);
    }

    private static boolean isDone0(Object obj) {
        return (obj == null || obj == UNCANCELLABLE) ? false : true;
    }

    private static void safeExecute(EventExecutor eventExecutor, Runnable runnable) {
        try {
            eventExecutor.execute(runnable);
        } catch (Throwable th) {
            rejectedExecutionLogger.error("Failed to submit a listener notification task. Event loop shut down?", th);
        }
    }
}
