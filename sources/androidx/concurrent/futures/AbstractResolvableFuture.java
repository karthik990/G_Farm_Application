package androidx.concurrent.futures;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractResolvableFuture<V> implements ListenableFuture<V> {
    static final AtomicHelper ATOMIC_HELPER;
    static final boolean GENERATE_CANCELLATION_CAUSES = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    private static final Object NULL = new Object();
    private static final long SPIN_THRESHOLD_NANOS = 1000;
    private static final Logger log;
    volatile Listener listeners;
    volatile Object value;
    volatile Waiter waiters;

    private static abstract class AtomicHelper {
        /* access modifiers changed from: 0000 */
        public abstract boolean casListeners(AbstractResolvableFuture<?> abstractResolvableFuture, Listener listener, Listener listener2);

        /* access modifiers changed from: 0000 */
        public abstract boolean casValue(AbstractResolvableFuture<?> abstractResolvableFuture, Object obj, Object obj2);

        /* access modifiers changed from: 0000 */
        public abstract boolean casWaiters(AbstractResolvableFuture<?> abstractResolvableFuture, Waiter waiter, Waiter waiter2);

        /* access modifiers changed from: 0000 */
        public abstract void putNext(Waiter waiter, Waiter waiter2);

        /* access modifiers changed from: 0000 */
        public abstract void putThread(Waiter waiter, Thread thread);

        private AtomicHelper() {
        }
    }

    private static final class Cancellation {
        static final Cancellation CAUSELESS_CANCELLED;
        static final Cancellation CAUSELESS_INTERRUPTED;
        final Throwable cause;
        final boolean wasInterrupted;

        static {
            if (AbstractResolvableFuture.GENERATE_CANCELLATION_CAUSES) {
                CAUSELESS_CANCELLED = null;
                CAUSELESS_INTERRUPTED = null;
                return;
            }
            CAUSELESS_CANCELLED = new Cancellation(false, null);
            CAUSELESS_INTERRUPTED = new Cancellation(true, null);
        }

        Cancellation(boolean z, Throwable th) {
            this.wasInterrupted = z;
            this.cause = th;
        }
    }

    private static final class Failure {
        static final Failure FALLBACK_INSTANCE = new Failure(new Throwable("Failure occurred while trying to finish a future.") {
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        final Throwable exception;

        Failure(Throwable th) {
            this.exception = (Throwable) AbstractResolvableFuture.checkNotNull(th);
        }
    }

    private static final class Listener {
        static final Listener TOMBSTONE = new Listener(null, null);
        final Executor executor;
        Listener next;
        final Runnable task;

        Listener(Runnable runnable, Executor executor2) {
            this.task = runnable;
            this.executor = executor2;
        }
    }

    private static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Listener> listenersUpdater;
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> valueUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater;
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Waiter> waitersUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater<Waiter, Thread> atomicReferenceFieldUpdater, AtomicReferenceFieldUpdater<Waiter, Waiter> atomicReferenceFieldUpdater2, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Waiter> atomicReferenceFieldUpdater3, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Listener> atomicReferenceFieldUpdater4, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> atomicReferenceFieldUpdater5) {
            super();
            this.waiterThreadUpdater = atomicReferenceFieldUpdater;
            this.waiterNextUpdater = atomicReferenceFieldUpdater2;
            this.waitersUpdater = atomicReferenceFieldUpdater3;
            this.listenersUpdater = atomicReferenceFieldUpdater4;
            this.valueUpdater = atomicReferenceFieldUpdater5;
        }

        /* access modifiers changed from: 0000 */
        public void putThread(Waiter waiter, Thread thread) {
            this.waiterThreadUpdater.lazySet(waiter, thread);
        }

        /* access modifiers changed from: 0000 */
        public void putNext(Waiter waiter, Waiter waiter2) {
            this.waiterNextUpdater.lazySet(waiter, waiter2);
        }

        /* access modifiers changed from: 0000 */
        public boolean casWaiters(AbstractResolvableFuture<?> abstractResolvableFuture, Waiter waiter, Waiter waiter2) {
            return this.waitersUpdater.compareAndSet(abstractResolvableFuture, waiter, waiter2);
        }

        /* access modifiers changed from: 0000 */
        public boolean casListeners(AbstractResolvableFuture<?> abstractResolvableFuture, Listener listener, Listener listener2) {
            return this.listenersUpdater.compareAndSet(abstractResolvableFuture, listener, listener2);
        }

        /* access modifiers changed from: 0000 */
        public boolean casValue(AbstractResolvableFuture<?> abstractResolvableFuture, Object obj, Object obj2) {
            return this.valueUpdater.compareAndSet(abstractResolvableFuture, obj, obj2);
        }
    }

    private static final class SetFuture<V> implements Runnable {
        final ListenableFuture<? extends V> future;
        final AbstractResolvableFuture<V> owner;

        SetFuture(AbstractResolvableFuture<V> abstractResolvableFuture, ListenableFuture<? extends V> listenableFuture) {
            this.owner = abstractResolvableFuture;
            this.future = listenableFuture;
        }

        public void run() {
            if (this.owner.value == this) {
                if (AbstractResolvableFuture.ATOMIC_HELPER.casValue(this.owner, this, AbstractResolvableFuture.getFutureValue(this.future))) {
                    AbstractResolvableFuture.complete(this.owner);
                }
            }
        }
    }

    private static final class SynchronizedHelper extends AtomicHelper {
        SynchronizedHelper() {
            super();
        }

        /* access modifiers changed from: 0000 */
        public void putThread(Waiter waiter, Thread thread) {
            waiter.thread = thread;
        }

        /* access modifiers changed from: 0000 */
        public void putNext(Waiter waiter, Waiter waiter2) {
            waiter.next = waiter2;
        }

        /* access modifiers changed from: 0000 */
        public boolean casWaiters(AbstractResolvableFuture<?> abstractResolvableFuture, Waiter waiter, Waiter waiter2) {
            synchronized (abstractResolvableFuture) {
                if (abstractResolvableFuture.waiters != waiter) {
                    return false;
                }
                abstractResolvableFuture.waiters = waiter2;
                return true;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean casListeners(AbstractResolvableFuture<?> abstractResolvableFuture, Listener listener, Listener listener2) {
            synchronized (abstractResolvableFuture) {
                if (abstractResolvableFuture.listeners != listener) {
                    return false;
                }
                abstractResolvableFuture.listeners = listener2;
                return true;
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean casValue(AbstractResolvableFuture<?> abstractResolvableFuture, Object obj, Object obj2) {
            synchronized (abstractResolvableFuture) {
                if (abstractResolvableFuture.value != obj) {
                    return false;
                }
                abstractResolvableFuture.value = obj2;
                return true;
            }
        }
    }

    private static final class Waiter {
        static final Waiter TOMBSTONE = new Waiter(false);
        volatile Waiter next;
        volatile Thread thread;

        Waiter(boolean z) {
        }

        Waiter() {
            AbstractResolvableFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
        }

        /* access modifiers changed from: 0000 */
        public void setNext(Waiter waiter) {
            AbstractResolvableFuture.ATOMIC_HELPER.putNext(this, waiter);
        }

        /* access modifiers changed from: 0000 */
        public void unpark() {
            Thread thread2 = this.thread;
            if (thread2 != null) {
                this.thread = null;
                LockSupport.unpark(thread2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void afterDone() {
    }

    /* access modifiers changed from: protected */
    public void interruptTask() {
    }

    /* JADX WARNING: type inference failed for: r1v8, types: [androidx.concurrent.futures.AbstractResolvableFuture$SynchronizedHelper] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    static {
        /*
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture> r0 = androidx.concurrent.futures.AbstractResolvableFuture.class
            java.lang.String r1 = "guava.concurrent.generate_cancellation_cause"
            java.lang.String r2 = "false"
            java.lang.String r1 = java.lang.System.getProperty(r1, r2)
            boolean r1 = java.lang.Boolean.parseBoolean(r1)
            GENERATE_CANCELLATION_CAUSES = r1
            java.lang.String r1 = r0.getName()
            java.util.logging.Logger r1 = java.util.logging.Logger.getLogger(r1)
            log = r1
            androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper r1 = new androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper     // Catch:{ all -> 0x0051 }
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture$Waiter> r2 = androidx.concurrent.futures.AbstractResolvableFuture.Waiter.class
            java.lang.Class<java.lang.Thread> r3 = java.lang.Thread.class
            java.lang.String r4 = "thread"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r3 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r2, r3, r4)     // Catch:{ all -> 0x0051 }
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture$Waiter> r2 = androidx.concurrent.futures.AbstractResolvableFuture.Waiter.class
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture$Waiter> r4 = androidx.concurrent.futures.AbstractResolvableFuture.Waiter.class
            java.lang.String r5 = "next"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r2, r4, r5)     // Catch:{ all -> 0x0051 }
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture$Waiter> r2 = androidx.concurrent.futures.AbstractResolvableFuture.Waiter.class
            java.lang.String r5 = "waiters"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r5 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r0, r2, r5)     // Catch:{ all -> 0x0051 }
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture$Listener> r2 = androidx.concurrent.futures.AbstractResolvableFuture.Listener.class
            java.lang.String r6 = "listeners"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r6 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r0, r2, r6)     // Catch:{ all -> 0x0051 }
            java.lang.Class<java.lang.Object> r2 = java.lang.Object.class
            java.lang.String r7 = "value"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r7 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r0, r2, r7)     // Catch:{ all -> 0x0051 }
            r2 = r1
            r2.<init>(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0051 }
            r0 = 0
            goto L_0x0057
        L_0x0051:
            r0 = move-exception
            androidx.concurrent.futures.AbstractResolvableFuture$SynchronizedHelper r1 = new androidx.concurrent.futures.AbstractResolvableFuture$SynchronizedHelper
            r1.<init>()
        L_0x0057:
            ATOMIC_HELPER = r1
            java.lang.Class<java.util.concurrent.locks.LockSupport> r1 = java.util.concurrent.locks.LockSupport.class
            if (r0 == 0) goto L_0x0066
            java.util.logging.Logger r1 = log
            java.util.logging.Level r2 = java.util.logging.Level.SEVERE
            java.lang.String r3 = "SafeAtomicHelper is broken!"
            r1.log(r2, r3, r0)
        L_0x0066:
            java.lang.Object r0 = new java.lang.Object
            r0.<init>()
            NULL = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.concurrent.futures.AbstractResolvableFuture.<clinit>():void");
    }

    private void removeWaiter(Waiter waiter) {
        waiter.thread = null;
        while (true) {
            Waiter waiter2 = this.waiters;
            if (waiter2 != Waiter.TOMBSTONE) {
                Waiter waiter3 = null;
                while (waiter2 != null) {
                    Waiter waiter4 = waiter2.next;
                    if (waiter2.thread != null) {
                        waiter3 = waiter2;
                    } else if (waiter3 != null) {
                        waiter3.next = waiter4;
                        if (waiter3.thread == null) {
                        }
                    } else if (!ATOMIC_HELPER.casWaiters(this, waiter2, waiter4)) {
                    }
                    waiter2 = waiter4;
                }
                return;
            }
            return;
        }
    }

    protected AbstractResolvableFuture() {
    }

    public final V get(long j, TimeUnit timeUnit) throws InterruptedException, TimeoutException, ExecutionException {
        long j2 = j;
        TimeUnit timeUnit2 = timeUnit;
        long nanos = timeUnit2.toNanos(j2);
        if (!Thread.interrupted()) {
            Object obj = this.value;
            if ((obj != null) && (!(obj instanceof SetFuture))) {
                return getDoneValue(obj);
            }
            long nanoTime = nanos > 0 ? System.nanoTime() + nanos : 0;
            if (nanos >= 1000) {
                Waiter waiter = this.waiters;
                if (waiter != Waiter.TOMBSTONE) {
                    Waiter waiter2 = new Waiter();
                    do {
                        waiter2.setNext(waiter);
                        if (ATOMIC_HELPER.casWaiters(this, waiter, waiter2)) {
                            do {
                                LockSupport.parkNanos(this, nanos);
                                if (!Thread.interrupted()) {
                                    Object obj2 = this.value;
                                    if ((obj2 != null) && (!(obj2 instanceof SetFuture))) {
                                        return getDoneValue(obj2);
                                    }
                                    nanos = nanoTime - System.nanoTime();
                                } else {
                                    removeWaiter(waiter2);
                                    throw new InterruptedException();
                                }
                            } while (nanos >= 1000);
                            removeWaiter(waiter2);
                        } else {
                            waiter = this.waiters;
                        }
                    } while (waiter != Waiter.TOMBSTONE);
                }
                return getDoneValue(this.value);
            }
            while (nanos > 0) {
                Object obj3 = this.value;
                if ((obj3 != null) && (!(obj3 instanceof SetFuture))) {
                    return getDoneValue(obj3);
                }
                if (!Thread.interrupted()) {
                    nanos = nanoTime - System.nanoTime();
                } else {
                    throw new InterruptedException();
                }
            }
            String abstractResolvableFuture = toString();
            String lowerCase = timeUnit.toString().toLowerCase(Locale.ROOT);
            StringBuilder sb = new StringBuilder();
            sb.append("Waited ");
            sb.append(j2);
            String str = " ";
            sb.append(str);
            sb.append(timeUnit.toString().toLowerCase(Locale.ROOT));
            String sb2 = sb.toString();
            if (nanos + 1000 < 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(" (plus ");
                String sb4 = sb3.toString();
                long j3 = -nanos;
                long convert = timeUnit2.convert(j3, TimeUnit.NANOSECONDS);
                long nanos2 = j3 - timeUnit2.toNanos(convert);
                boolean z = convert == 0 || nanos2 > 1000;
                if (convert > 0) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append(sb4);
                    sb5.append(convert);
                    sb5.append(str);
                    sb5.append(lowerCase);
                    String sb6 = sb5.toString();
                    if (z) {
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append(sb6);
                        sb7.append(",");
                        sb6 = sb7.toString();
                    }
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append(sb6);
                    sb8.append(str);
                    sb4 = sb8.toString();
                }
                if (z) {
                    StringBuilder sb9 = new StringBuilder();
                    sb9.append(sb4);
                    sb9.append(nanos2);
                    sb9.append(" nanoseconds ");
                    sb4 = sb9.toString();
                }
                StringBuilder sb10 = new StringBuilder();
                sb10.append(sb4);
                sb10.append("delay)");
                sb2 = sb10.toString();
            }
            if (isDone()) {
                StringBuilder sb11 = new StringBuilder();
                sb11.append(sb2);
                sb11.append(" but future completed as timeout expired");
                throw new TimeoutException(sb11.toString());
            }
            StringBuilder sb12 = new StringBuilder();
            sb12.append(sb2);
            sb12.append(" for ");
            sb12.append(abstractResolvableFuture);
            throw new TimeoutException(sb12.toString());
        }
        throw new InterruptedException();
    }

    public final V get() throws InterruptedException, ExecutionException {
        Object obj;
        if (!Thread.interrupted()) {
            Object obj2 = this.value;
            if ((obj2 != null) && (!(obj2 instanceof SetFuture))) {
                return getDoneValue(obj2);
            }
            Waiter waiter = this.waiters;
            if (waiter != Waiter.TOMBSTONE) {
                Waiter waiter2 = new Waiter();
                do {
                    waiter2.setNext(waiter);
                    if (ATOMIC_HELPER.casWaiters(this, waiter, waiter2)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                obj = this.value;
                            } else {
                                removeWaiter(waiter2);
                                throw new InterruptedException();
                            }
                        } while (!((obj != null) & (!(obj instanceof SetFuture))));
                        return getDoneValue(obj);
                    }
                    waiter = this.waiters;
                } while (waiter != Waiter.TOMBSTONE);
            }
            return getDoneValue(this.value);
        }
        throw new InterruptedException();
    }

    private V getDoneValue(Object obj) throws ExecutionException {
        if (obj instanceof Cancellation) {
            throw cancellationExceptionWithCause("Task was cancelled.", ((Cancellation) obj).cause);
        } else if (obj instanceof Failure) {
            throw new ExecutionException(((Failure) obj).exception);
        } else if (obj == NULL) {
            return null;
        } else {
            return obj;
        }
    }

    public final boolean isDone() {
        Object obj = this.value;
        return (!(obj instanceof SetFuture)) & (obj != null);
    }

    public final boolean isCancelled() {
        return this.value instanceof Cancellation;
    }

    public final boolean cancel(boolean z) {
        Object obj = this.value;
        if (!(obj == null) && !(obj instanceof SetFuture)) {
            return false;
        }
        Cancellation cancellation = GENERATE_CANCELLATION_CAUSES ? new Cancellation(z, new CancellationException("Future.cancel() was called.")) : z ? Cancellation.CAUSELESS_INTERRUPTED : Cancellation.CAUSELESS_CANCELLED;
        boolean z2 = false;
        Object obj2 = obj;
        AbstractResolvableFuture abstractResolvableFuture = this;
        while (true) {
            if (ATOMIC_HELPER.casValue(abstractResolvableFuture, obj2, cancellation)) {
                if (z) {
                    abstractResolvableFuture.interruptTask();
                }
                complete(abstractResolvableFuture);
                if (!(obj2 instanceof SetFuture)) {
                    return true;
                }
                ListenableFuture<? extends V> listenableFuture = ((SetFuture) obj2).future;
                if (listenableFuture instanceof AbstractResolvableFuture) {
                    abstractResolvableFuture = (AbstractResolvableFuture) listenableFuture;
                    obj2 = abstractResolvableFuture.value;
                    if (!(obj2 == null) && !(obj2 instanceof SetFuture)) {
                        return true;
                    }
                    z2 = true;
                } else {
                    listenableFuture.cancel(z);
                    return true;
                }
            } else {
                obj2 = abstractResolvableFuture.value;
                if (!(obj2 instanceof SetFuture)) {
                    return z2;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final boolean wasInterrupted() {
        Object obj = this.value;
        return (obj instanceof Cancellation) && ((Cancellation) obj).wasInterrupted;
    }

    public final void addListener(Runnable runnable, Executor executor) {
        checkNotNull(runnable);
        checkNotNull(executor);
        Listener listener = this.listeners;
        if (listener != Listener.TOMBSTONE) {
            Listener listener2 = new Listener(runnable, executor);
            do {
                listener2.next = listener;
                if (!ATOMIC_HELPER.casListeners(this, listener, listener2)) {
                    listener = this.listeners;
                } else {
                    return;
                }
            } while (listener != Listener.TOMBSTONE);
        }
        executeListener(runnable, executor);
    }

    /* access modifiers changed from: protected */
    public boolean set(V v) {
        if (v == null) {
            v = NULL;
        }
        if (!ATOMIC_HELPER.casValue(this, null, v)) {
            return false;
        }
        complete(this);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean setException(Throwable th) {
        if (!ATOMIC_HELPER.casValue(this, null, new Failure((Throwable) checkNotNull(th)))) {
            return false;
        }
        complete(this);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean setFuture(ListenableFuture<? extends V> listenableFuture) {
        SetFuture setFuture;
        Failure failure;
        checkNotNull(listenableFuture);
        Object obj = this.value;
        if (obj == null) {
            if (listenableFuture.isDone()) {
                if (!ATOMIC_HELPER.casValue(this, null, getFutureValue(listenableFuture))) {
                    return false;
                }
                complete(this);
                return true;
            }
            setFuture = new SetFuture(this, listenableFuture);
            if (ATOMIC_HELPER.casValue(this, null, setFuture)) {
                try {
                    listenableFuture.addListener(setFuture, DirectExecutor.INSTANCE);
                } catch (Throwable unused) {
                    failure = Failure.FALLBACK_INSTANCE;
                }
                return true;
            }
            obj = this.value;
        }
        if (obj instanceof Cancellation) {
            listenableFuture.cancel(((Cancellation) obj).wasInterrupted);
        }
        return false;
        ATOMIC_HELPER.casValue(this, setFuture, failure);
        return true;
    }

    static Object getFutureValue(ListenableFuture<?> listenableFuture) {
        if (listenableFuture instanceof AbstractResolvableFuture) {
            Object obj = ((AbstractResolvableFuture) listenableFuture).value;
            if (obj instanceof Cancellation) {
                Cancellation cancellation = (Cancellation) obj;
                if (cancellation.wasInterrupted) {
                    obj = cancellation.cause != null ? new Cancellation(false, cancellation.cause) : Cancellation.CAUSELESS_CANCELLED;
                }
            }
            return obj;
        }
        boolean isCancelled = listenableFuture.isCancelled();
        if ((!GENERATE_CANCELLATION_CAUSES) && isCancelled) {
            return Cancellation.CAUSELESS_CANCELLED;
        }
        try {
            Object uninterruptibly = getUninterruptibly(listenableFuture);
            if (uninterruptibly == null) {
                uninterruptibly = NULL;
            }
            return uninterruptibly;
        } catch (ExecutionException e) {
            return new Failure(e.getCause());
        } catch (CancellationException e2) {
            if (isCancelled) {
                return new Cancellation(false, e2);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("get() threw CancellationException, despite reporting isCancelled() == false: ");
            sb.append(listenableFuture);
            return new Failure(new IllegalArgumentException(sb.toString(), e2));
        } catch (Throwable th) {
            return new Failure(th);
        }
    }

    private static <V> V getUninterruptibly(Future<V> future) throws ExecutionException {
        V v;
        boolean z = false;
        while (true) {
            try {
                v = future.get();
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return v;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=androidx.concurrent.futures.AbstractResolvableFuture<?>, code=androidx.concurrent.futures.AbstractResolvableFuture, for r4v0, types: [androidx.concurrent.futures.AbstractResolvableFuture<?>, androidx.concurrent.futures.AbstractResolvableFuture] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void complete(androidx.concurrent.futures.AbstractResolvableFuture r4) {
        /*
            r0 = 0
        L_0x0001:
            r4.releaseWaiters()
            r4.afterDone()
            androidx.concurrent.futures.AbstractResolvableFuture$Listener r4 = r4.clearListeners(r0)
        L_0x000b:
            if (r4 == 0) goto L_0x0033
            androidx.concurrent.futures.AbstractResolvableFuture$Listener r0 = r4.next
            java.lang.Runnable r1 = r4.task
            boolean r2 = r1 instanceof androidx.concurrent.futures.AbstractResolvableFuture.SetFuture
            if (r2 == 0) goto L_0x002c
            androidx.concurrent.futures.AbstractResolvableFuture$SetFuture r1 = (androidx.concurrent.futures.AbstractResolvableFuture.SetFuture) r1
            androidx.concurrent.futures.AbstractResolvableFuture<V> r4 = r1.owner
            java.lang.Object r2 = r4.value
            if (r2 != r1) goto L_0x0031
            com.google.common.util.concurrent.ListenableFuture<? extends V> r2 = r1.future
            java.lang.Object r2 = getFutureValue(r2)
            androidx.concurrent.futures.AbstractResolvableFuture$AtomicHelper r3 = ATOMIC_HELPER
            boolean r1 = r3.casValue(r4, r1, r2)
            if (r1 == 0) goto L_0x0031
            goto L_0x0001
        L_0x002c:
            java.util.concurrent.Executor r4 = r4.executor
            executeListener(r1, r4)
        L_0x0031:
            r4 = r0
            goto L_0x000b
        L_0x0033:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.concurrent.futures.AbstractResolvableFuture.complete(androidx.concurrent.futures.AbstractResolvableFuture):void");
    }

    /* access modifiers changed from: 0000 */
    public final void maybePropagateCancellationTo(Future<?> future) {
        if ((future != null) && isCancelled()) {
            future.cancel(wasInterrupted());
        }
    }

    private void releaseWaiters() {
        Waiter waiter;
        do {
            waiter = this.waiters;
        } while (!ATOMIC_HELPER.casWaiters(this, waiter, Waiter.TOMBSTONE));
        while (waiter != null) {
            waiter.unpark();
            waiter = waiter.next;
        }
    }

    private Listener clearListeners(Listener listener) {
        Listener listener2;
        do {
            listener2 = this.listeners;
        } while (!ATOMIC_HELPER.casListeners(this, listener2, Listener.TOMBSTONE));
        Listener listener3 = listener2;
        Listener listener4 = listener;
        Listener listener5 = listener3;
        while (listener5 != null) {
            Listener listener6 = listener5.next;
            listener5.next = listener4;
            listener4 = listener5;
            listener5 = listener6;
        }
        return listener4;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[status=");
        String str2 = "]";
        if (isCancelled()) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            addDoneString(sb);
        } else {
            try {
                str = pendingToString();
            } catch (RuntimeException e) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Exception thrown from implementation: ");
                sb2.append(e.getClass());
                str = sb2.toString();
            }
            if (str != null && !str.isEmpty()) {
                sb.append("PENDING, info=[");
                sb.append(str);
                sb.append(str2);
            } else if (isDone()) {
                addDoneString(sb);
            } else {
                sb.append("PENDING");
            }
        }
        sb.append(str2);
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String pendingToString() {
        Object obj = this.value;
        if (obj instanceof SetFuture) {
            StringBuilder sb = new StringBuilder();
            sb.append("setFuture=[");
            sb.append(userObjectToString(((SetFuture) obj).future));
            sb.append("]");
            return sb.toString();
        } else if (!(this instanceof ScheduledFuture)) {
            return null;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("remaining delay=[");
            sb2.append(((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS));
            sb2.append(" ms]");
            return sb2.toString();
        }
    }

    private void addDoneString(StringBuilder sb) {
        String str = "]";
        try {
            Object uninterruptibly = getUninterruptibly(this);
            sb.append("SUCCESS, result=[");
            sb.append(userObjectToString(uninterruptibly));
            sb.append(str);
        } catch (ExecutionException e) {
            sb.append("FAILURE, cause=[");
            sb.append(e.getCause());
            sb.append(str);
        } catch (CancellationException unused) {
            sb.append("CANCELLED");
        } catch (RuntimeException e2) {
            sb.append("UNKNOWN, cause=[");
            sb.append(e2.getClass());
            sb.append(" thrown from get()]");
        }
    }

    private String userObjectToString(Object obj) {
        return obj == this ? "this future" : String.valueOf(obj);
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            Logger logger = log;
            Level level = Level.SEVERE;
            StringBuilder sb = new StringBuilder();
            sb.append("RuntimeException while executing runnable ");
            sb.append(runnable);
            sb.append(" with executor ");
            sb.append(executor);
            logger.log(level, sb.toString(), e);
        }
    }

    private static CancellationException cancellationExceptionWithCause(String str, Throwable th) {
        CancellationException cancellationException = new CancellationException(str);
        cancellationException.initCause(th);
        return cancellationException;
    }

    static <T> T checkNotNull(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }
}
