package p043io.reactivex.internal.operators.observable;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.fuseable.QueueDisposable;
import p043io.reactivex.internal.fuseable.SimplePlainQueue;
import p043io.reactivex.internal.fuseable.SimpleQueue;
import p043io.reactivex.internal.queue.SpscArrayQueue;
import p043io.reactivex.internal.queue.SpscLinkedArrayQueue;
import p043io.reactivex.internal.util.AtomicThrowable;
import p043io.reactivex.internal.util.ExceptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMap */
public final class ObservableFlatMap<T, U> extends AbstractObservableWithUpstream<T, U> {
    final int bufferSize;
    final boolean delayErrors;
    final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
    final int maxConcurrency;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMap$InnerObserver */
    static final class InnerObserver<T, U> extends AtomicReference<Disposable> implements Observer<U> {
        private static final long serialVersionUID = -4606175640614850599L;
        volatile boolean done;
        int fusionMode;

        /* renamed from: id */
        final long f4018id;
        final MergeObserver<T, U> parent;
        volatile SimpleQueue<U> queue;

        InnerObserver(MergeObserver<T, U> mergeObserver, long j) {
            this.f4018id = j;
            this.parent = mergeObserver;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable) && (disposable instanceof QueueDisposable)) {
                QueueDisposable queueDisposable = (QueueDisposable) disposable;
                int requestFusion = queueDisposable.requestFusion(7);
                if (requestFusion == 1) {
                    this.fusionMode = requestFusion;
                    this.queue = queueDisposable;
                    this.done = true;
                    this.parent.drain();
                } else if (requestFusion == 2) {
                    this.fusionMode = requestFusion;
                    this.queue = queueDisposable;
                }
            }
        }

        public void onNext(U u) {
            if (this.fusionMode == 0) {
                this.parent.tryEmit(u, this);
            } else {
                this.parent.drain();
            }
        }

        public void onError(Throwable th) {
            if (this.parent.errors.addThrowable(th)) {
                if (!this.parent.delayErrors) {
                    this.parent.disposeAll();
                }
                this.done = true;
                this.parent.drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMap$MergeObserver */
    static final class MergeObserver<T, U> extends AtomicInteger implements Disposable, Observer<T> {
        static final InnerObserver<?, ?>[] CANCELLED = new InnerObserver[0];
        static final InnerObserver<?, ?>[] EMPTY = new InnerObserver[0];
        private static final long serialVersionUID = -2117620485640801370L;
        final Observer<? super U> actual;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final AtomicThrowable errors = new AtomicThrowable();
        long lastId;
        int lastIndex;
        final Function<? super T, ? extends ObservableSource<? extends U>> mapper;
        final int maxConcurrency;
        final AtomicReference<InnerObserver<?, ?>[]> observers;
        volatile SimplePlainQueue<U> queue;

        /* renamed from: s */
        Disposable f4019s;
        Queue<ObservableSource<? extends U>> sources;
        long uniqueId;
        int wip;

        MergeObserver(Observer<? super U> observer, Function<? super T, ? extends ObservableSource<? extends U>> function, boolean z, int i, int i2) {
            this.actual = observer;
            this.mapper = function;
            this.delayErrors = z;
            this.maxConcurrency = i;
            this.bufferSize = i2;
            if (i != Integer.MAX_VALUE) {
                this.sources = new ArrayDeque(i);
            }
            this.observers = new AtomicReference<>(EMPTY);
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4019s, disposable)) {
                this.f4019s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    ObservableSource observableSource = (ObservableSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null ObservableSource");
                    if (this.maxConcurrency != Integer.MAX_VALUE) {
                        synchronized (this) {
                            if (this.wip == this.maxConcurrency) {
                                this.sources.offer(observableSource);
                                return;
                            }
                            this.wip++;
                        }
                    }
                    subscribeInner(observableSource);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f4019s.dispose();
                    onError(th);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void subscribeInner(ObservableSource<? extends U> observableSource) {
            while (observableSource instanceof Callable) {
                tryEmitScalar((Callable) observableSource);
                if (this.maxConcurrency != Integer.MAX_VALUE) {
                    synchronized (this) {
                        observableSource = (ObservableSource) this.sources.poll();
                        if (observableSource == null) {
                            this.wip--;
                            return;
                        }
                    }
                } else {
                    return;
                }
            }
            long j = this.uniqueId;
            this.uniqueId = 1 + j;
            InnerObserver innerObserver = new InnerObserver(this, j);
            if (addInner(innerObserver)) {
                observableSource.subscribe(innerObserver);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean addInner(InnerObserver<T, U> innerObserver) {
            InnerObserver<?, ?>[] innerObserverArr;
            InnerObserver[] innerObserverArr2;
            do {
                innerObserverArr = (InnerObserver[]) this.observers.get();
                if (innerObserverArr == CANCELLED) {
                    innerObserver.dispose();
                    return false;
                }
                int length = innerObserverArr.length;
                innerObserverArr2 = new InnerObserver[(length + 1)];
                System.arraycopy(innerObserverArr, 0, innerObserverArr2, 0, length);
                innerObserverArr2[length] = innerObserver;
            } while (!this.observers.compareAndSet(innerObserverArr, innerObserverArr2));
            return true;
        }

        /* access modifiers changed from: 0000 */
        public void removeInner(InnerObserver<T, U> innerObserver) {
            InnerObserver<T, U>[] innerObserverArr;
            Object obj;
            do {
                innerObserverArr = (InnerObserver[]) this.observers.get();
                int length = innerObserverArr.length;
                if (length != 0) {
                    int i = -1;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        } else if (innerObserverArr[i2] == innerObserver) {
                            i = i2;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i >= 0) {
                        if (length == 1) {
                            obj = EMPTY;
                        } else {
                            InnerObserver[] innerObserverArr2 = new InnerObserver[(length - 1)];
                            System.arraycopy(innerObserverArr, 0, innerObserverArr2, 0, i);
                            System.arraycopy(innerObserverArr, i + 1, innerObserverArr2, i, (length - i) - 1);
                            obj = innerObserverArr2;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.observers.compareAndSet(innerObserverArr, obj));
        }

        /* access modifiers changed from: 0000 */
        public void tryEmitScalar(Callable<? extends U> callable) {
            try {
                Object call = callable.call();
                if (call != null) {
                    if (get() != 0 || !compareAndSet(0, 1)) {
                        SimplePlainQueue<U> simplePlainQueue = this.queue;
                        if (simplePlainQueue == null) {
                            int i = this.maxConcurrency;
                            if (i == Integer.MAX_VALUE) {
                                simplePlainQueue = new SpscLinkedArrayQueue<>(this.bufferSize);
                            } else {
                                simplePlainQueue = new SpscArrayQueue<>(i);
                            }
                            this.queue = simplePlainQueue;
                        }
                        if (!simplePlainQueue.offer(call)) {
                            onError(new IllegalStateException("Scalar queue full?!"));
                            return;
                        } else if (getAndIncrement() != 0) {
                            return;
                        }
                    } else {
                        this.actual.onNext(call);
                        if (decrementAndGet() == 0) {
                            return;
                        }
                    }
                    drainLoop();
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.errors.addThrowable(th);
                drain();
            }
        }

        /* access modifiers changed from: 0000 */
        public void tryEmit(U u, InnerObserver<T, U> innerObserver) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                SimpleQueue simpleQueue = innerObserver.queue;
                if (simpleQueue == null) {
                    simpleQueue = new SpscLinkedArrayQueue(this.bufferSize);
                    innerObserver.queue = simpleQueue;
                }
                simpleQueue.offer(u);
                if (getAndIncrement() != 0) {
                    return;
                }
            } else {
                this.actual.onNext(u);
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            drainLoop();
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            if (this.errors.addThrowable(th)) {
                this.done = true;
                drain();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                drain();
            }
        }

        public void dispose() {
            if (!this.cancelled) {
                this.cancelled = true;
                if (disposeAll()) {
                    Throwable terminate = this.errors.terminate();
                    if (terminate != null && terminate != ExceptionHelper.TERMINATED) {
                        RxJavaPlugins.onError(terminate);
                    }
                }
            }
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: 0000 */
        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x00a2, code lost:
            if (r11 != null) goto L_0x0090;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void drainLoop() {
            /*
                r13 = this;
                io.reactivex.Observer<? super U> r0 = r13.actual
                r1 = 1
                r2 = 1
            L_0x0004:
                boolean r3 = r13.checkTerminate()
                if (r3 == 0) goto L_0x000b
                return
            L_0x000b:
                io.reactivex.internal.fuseable.SimplePlainQueue<U> r3 = r13.queue
                if (r3 == 0) goto L_0x0023
            L_0x000f:
                boolean r4 = r13.checkTerminate()
                if (r4 == 0) goto L_0x0016
                return
            L_0x0016:
                java.lang.Object r4 = r3.poll()
                if (r4 != 0) goto L_0x001f
                if (r4 != 0) goto L_0x000f
                goto L_0x0023
            L_0x001f:
                r0.onNext(r4)
                goto L_0x000f
            L_0x0023:
                boolean r3 = r13.done
                io.reactivex.internal.fuseable.SimplePlainQueue<U> r4 = r13.queue
                java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.ObservableFlatMap$InnerObserver<?, ?>[]> r5 = r13.observers
                java.lang.Object r5 = r5.get()
                io.reactivex.internal.operators.observable.ObservableFlatMap$InnerObserver[] r5 = (p043io.reactivex.internal.operators.observable.ObservableFlatMap.InnerObserver[]) r5
                int r6 = r5.length
                if (r3 == 0) goto L_0x0050
                if (r4 == 0) goto L_0x003a
                boolean r3 = r4.isEmpty()
                if (r3 == 0) goto L_0x0050
            L_0x003a:
                if (r6 != 0) goto L_0x0050
                io.reactivex.internal.util.AtomicThrowable r1 = r13.errors
                java.lang.Throwable r1 = r1.terminate()
                java.lang.Throwable r2 = p043io.reactivex.internal.util.ExceptionHelper.TERMINATED
                if (r1 == r2) goto L_0x004f
                if (r1 != 0) goto L_0x004c
                r0.onComplete()
                goto L_0x004f
            L_0x004c:
                r0.onError(r1)
            L_0x004f:
                return
            L_0x0050:
                r3 = 0
                if (r6 == 0) goto L_0x00f1
                long r7 = r13.lastId
                int r4 = r13.lastIndex
                if (r6 <= r4) goto L_0x0061
                r9 = r5[r4]
                long r9 = r9.f4018id
                int r11 = (r9 > r7 ? 1 : (r9 == r7 ? 0 : -1))
                if (r11 == 0) goto L_0x0082
            L_0x0061:
                if (r6 > r4) goto L_0x0064
                r4 = 0
            L_0x0064:
                r9 = r4
                r4 = 0
            L_0x0066:
                if (r4 >= r6) goto L_0x0079
                r10 = r5[r9]
                long r10 = r10.f4018id
                int r12 = (r10 > r7 ? 1 : (r10 == r7 ? 0 : -1))
                if (r12 != 0) goto L_0x0071
                goto L_0x0079
            L_0x0071:
                int r9 = r9 + 1
                if (r9 != r6) goto L_0x0076
                r9 = 0
            L_0x0076:
                int r4 = r4 + 1
                goto L_0x0066
            L_0x0079:
                r13.lastIndex = r9
                r4 = r5[r9]
                long r7 = r4.f4018id
                r13.lastId = r7
                r4 = r9
            L_0x0082:
                r7 = r4
                r4 = 0
                r8 = 0
            L_0x0085:
                if (r4 >= r6) goto L_0x00e8
                boolean r9 = r13.checkTerminate()
                if (r9 == 0) goto L_0x008e
                return
            L_0x008e:
                r9 = r5[r7]
            L_0x0090:
                boolean r10 = r13.checkTerminate()
                if (r10 == 0) goto L_0x0097
                return
            L_0x0097:
                io.reactivex.internal.fuseable.SimpleQueue<U> r10 = r9.queue
                if (r10 != 0) goto L_0x009c
                goto L_0x00a4
            L_0x009c:
                java.lang.Object r11 = r10.poll()     // Catch:{ all -> 0x00cd }
                if (r11 != 0) goto L_0x00c3
                if (r11 != 0) goto L_0x0090
            L_0x00a4:
                boolean r10 = r9.done
                io.reactivex.internal.fuseable.SimpleQueue<U> r11 = r9.queue
                if (r10 == 0) goto L_0x00bd
                if (r11 == 0) goto L_0x00b2
                boolean r10 = r11.isEmpty()
                if (r10 == 0) goto L_0x00bd
            L_0x00b2:
                r13.removeInner(r9)
                boolean r8 = r13.checkTerminate()
                if (r8 == 0) goto L_0x00bc
                return
            L_0x00bc:
                r8 = 1
            L_0x00bd:
                int r7 = r7 + 1
                if (r7 != r6) goto L_0x00e6
                r7 = 0
                goto L_0x00e6
            L_0x00c3:
                r0.onNext(r11)
                boolean r11 = r13.checkTerminate()
                if (r11 == 0) goto L_0x009c
                return
            L_0x00cd:
                r8 = move-exception
                p043io.reactivex.exceptions.Exceptions.throwIfFatal(r8)
                r9.dispose()
                io.reactivex.internal.util.AtomicThrowable r10 = r13.errors
                r10.addThrowable(r8)
                boolean r8 = r13.checkTerminate()
                if (r8 == 0) goto L_0x00e0
                return
            L_0x00e0:
                r13.removeInner(r9)
                int r4 = r4 + 1
                r8 = 1
            L_0x00e6:
                int r4 = r4 + r1
                goto L_0x0085
            L_0x00e8:
                r13.lastIndex = r7
                r3 = r5[r7]
                long r3 = r3.f4018id
                r13.lastId = r3
                r3 = r8
            L_0x00f1:
                if (r3 == 0) goto L_0x0116
                int r3 = r13.maxConcurrency
                r4 = 2147483647(0x7fffffff, float:NaN)
                if (r3 == r4) goto L_0x0004
                monitor-enter(r13)
                java.util.Queue<io.reactivex.ObservableSource<? extends U>> r3 = r13.sources     // Catch:{ all -> 0x0113 }
                java.lang.Object r3 = r3.poll()     // Catch:{ all -> 0x0113 }
                io.reactivex.ObservableSource r3 = (p043io.reactivex.ObservableSource) r3     // Catch:{ all -> 0x0113 }
                if (r3 != 0) goto L_0x010d
                int r3 = r13.wip     // Catch:{ all -> 0x0113 }
                int r3 = r3 - r1
                r13.wip = r3     // Catch:{ all -> 0x0113 }
                monitor-exit(r13)     // Catch:{ all -> 0x0113 }
                goto L_0x0004
            L_0x010d:
                monitor-exit(r13)     // Catch:{ all -> 0x0113 }
                r13.subscribeInner(r3)
                goto L_0x0004
            L_0x0113:
                r0 = move-exception
                monitor-exit(r13)     // Catch:{ all -> 0x0113 }
                throw r0
            L_0x0116:
                int r2 = -r2
                int r2 = r13.addAndGet(r2)
                if (r2 != 0) goto L_0x0004
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.reactivex.internal.operators.observable.ObservableFlatMap.MergeObserver.drainLoop():void");
        }

        /* access modifiers changed from: 0000 */
        public boolean checkTerminate() {
            if (this.cancelled) {
                return true;
            }
            Throwable th = (Throwable) this.errors.get();
            if (this.delayErrors || th == null) {
                return false;
            }
            disposeAll();
            Throwable terminate = this.errors.terminate();
            if (terminate != ExceptionHelper.TERMINATED) {
                this.actual.onError(terminate);
            }
            return true;
        }

        /* access modifiers changed from: 0000 */
        public boolean disposeAll() {
            this.f4019s.dispose();
            InnerObserver<?, ?>[] innerObserverArr = (InnerObserver[]) this.observers.get();
            InnerObserver<?, ?>[] innerObserverArr2 = CANCELLED;
            if (innerObserverArr != innerObserverArr2) {
                InnerObserver<?, ?>[] innerObserverArr3 = (InnerObserver[]) this.observers.getAndSet(innerObserverArr2);
                if (innerObserverArr3 != CANCELLED) {
                    for (InnerObserver<?, ?> dispose : innerObserverArr3) {
                        dispose.dispose();
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public ObservableFlatMap(ObservableSource<T> observableSource, Function<? super T, ? extends ObservableSource<? extends U>> function, boolean z, int i, int i2) {
        super(observableSource);
        this.mapper = function;
        this.delayErrors = z;
        this.maxConcurrency = i;
        this.bufferSize = i2;
    }

    public void subscribeActual(Observer<? super U> observer) {
        if (!ObservableScalarXMap.tryScalarXMapSubscribe(this.source, observer, this.mapper)) {
            ObservableSource observableSource = this.source;
            MergeObserver mergeObserver = new MergeObserver(observer, this.mapper, this.delayErrors, this.maxConcurrency, this.bufferSize);
            observableSource.subscribe(mergeObserver);
        }
    }
}
