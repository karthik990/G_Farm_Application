package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.Observable;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.CompositeDisposable;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.queue.SpscLinkedArrayQueue;
import p043io.reactivex.internal.util.AtomicThrowable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMapSingle */
public final class ObservableFlatMapSingle<T, R> extends AbstractObservableWithUpstream<T, R> {
    final boolean delayErrors;
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMapSingle$FlatMapSingleObserver */
    static final class FlatMapSingleObserver<T, R> extends AtomicInteger implements Observer<T>, Disposable {
        private static final long serialVersionUID = 8600231336733376951L;
        final AtomicInteger active = new AtomicInteger(1);
        final Observer<? super R> actual;
        volatile boolean cancelled;

        /* renamed from: d */
        Disposable f4023d;
        final boolean delayErrors;
        final AtomicThrowable errors = new AtomicThrowable();
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;
        final AtomicReference<SpscLinkedArrayQueue<R>> queue = new AtomicReference<>();
        final CompositeDisposable set = new CompositeDisposable();

        /* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMapSingle$FlatMapSingleObserver$InnerObserver */
        final class InnerObserver extends AtomicReference<Disposable> implements SingleObserver<R>, Disposable {
            private static final long serialVersionUID = -502562646270949838L;

            InnerObserver() {
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            public void onSuccess(R r) {
                FlatMapSingleObserver.this.innerSuccess(this, r);
            }

            public void onError(Throwable th) {
                FlatMapSingleObserver.this.innerError(this, th);
            }

            public boolean isDisposed() {
                return DisposableHelper.isDisposed((Disposable) get());
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }
        }

        FlatMapSingleObserver(Observer<? super R> observer, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
            this.actual = observer;
            this.mapper = function;
            this.delayErrors = z;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4023d, disposable)) {
                this.f4023d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            try {
                SingleSource singleSource = (SingleSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null SingleSource");
                this.active.getAndIncrement();
                InnerObserver innerObserver = new InnerObserver();
                if (this.set.add(innerObserver)) {
                    singleSource.subscribe(innerObserver);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.f4023d.dispose();
                onError(th);
            }
        }

        public void onError(Throwable th) {
            this.active.decrementAndGet();
            if (this.errors.addThrowable(th)) {
                if (!this.delayErrors) {
                    this.set.dispose();
                }
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            this.active.decrementAndGet();
            drain();
        }

        public void dispose() {
            this.cancelled = true;
            this.f4023d.dispose();
            this.set.dispose();
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: 0000 */
        public void innerSuccess(InnerObserver innerObserver, R r) {
            this.set.delete(innerObserver);
            if (get() == 0) {
                boolean z = true;
                if (compareAndSet(0, 1)) {
                    this.actual.onNext(r);
                    if (this.active.decrementAndGet() != 0) {
                        z = false;
                    }
                    SpscLinkedArrayQueue spscLinkedArrayQueue = (SpscLinkedArrayQueue) this.queue.get();
                    if (!z || (spscLinkedArrayQueue != null && !spscLinkedArrayQueue.isEmpty())) {
                        if (decrementAndGet() == 0) {
                            return;
                        }
                        drainLoop();
                    }
                    Throwable terminate = this.errors.terminate();
                    if (terminate != null) {
                        this.actual.onError(terminate);
                    } else {
                        this.actual.onComplete();
                    }
                    return;
                }
            }
            SpscLinkedArrayQueue orCreateQueue = getOrCreateQueue();
            synchronized (orCreateQueue) {
                orCreateQueue.offer(r);
            }
            this.active.decrementAndGet();
            if (getAndIncrement() != 0) {
                return;
            }
            drainLoop();
        }

        /* access modifiers changed from: 0000 */
        public SpscLinkedArrayQueue<R> getOrCreateQueue() {
            SpscLinkedArrayQueue<R> spscLinkedArrayQueue;
            do {
                SpscLinkedArrayQueue<R> spscLinkedArrayQueue2 = (SpscLinkedArrayQueue) this.queue.get();
                if (spscLinkedArrayQueue2 != null) {
                    return spscLinkedArrayQueue2;
                }
                spscLinkedArrayQueue = new SpscLinkedArrayQueue<>(Observable.bufferSize());
            } while (!this.queue.compareAndSet(null, spscLinkedArrayQueue));
            return spscLinkedArrayQueue;
        }

        /* access modifiers changed from: 0000 */
        public void innerError(InnerObserver innerObserver, Throwable th) {
            this.set.delete(innerObserver);
            if (this.errors.addThrowable(th)) {
                if (!this.delayErrors) {
                    this.f4023d.dispose();
                    this.set.dispose();
                }
                this.active.decrementAndGet();
                drain();
                return;
            }
            RxJavaPlugins.onError(th);
        }

        /* access modifiers changed from: 0000 */
        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        /* access modifiers changed from: 0000 */
        public void clear() {
            SpscLinkedArrayQueue spscLinkedArrayQueue = (SpscLinkedArrayQueue) this.queue.get();
            if (spscLinkedArrayQueue != null) {
                spscLinkedArrayQueue.clear();
            }
        }

        /* access modifiers changed from: 0000 */
        public void drainLoop() {
            Observer<? super R> observer = this.actual;
            AtomicInteger atomicInteger = this.active;
            AtomicReference<SpscLinkedArrayQueue<R>> atomicReference = this.queue;
            int i = 1;
            while (!this.cancelled) {
                if (this.delayErrors || ((Throwable) this.errors.get()) == null) {
                    boolean z = false;
                    boolean z2 = atomicInteger.get() == 0;
                    SpscLinkedArrayQueue spscLinkedArrayQueue = (SpscLinkedArrayQueue) atomicReference.get();
                    Object poll = spscLinkedArrayQueue != null ? spscLinkedArrayQueue.poll() : null;
                    if (poll == null) {
                        z = true;
                    }
                    if (z2 && z) {
                        Throwable terminate = this.errors.terminate();
                        if (terminate != null) {
                            observer.onError(terminate);
                        } else {
                            observer.onComplete();
                        }
                        return;
                    } else if (z) {
                        i = addAndGet(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        observer.onNext(poll);
                    }
                } else {
                    Throwable terminate2 = this.errors.terminate();
                    clear();
                    observer.onError(terminate2);
                    return;
                }
            }
            clear();
        }
    }

    public ObservableFlatMapSingle(ObservableSource<T> observableSource, Function<? super T, ? extends SingleSource<? extends R>> function, boolean z) {
        super(observableSource);
        this.mapper = function;
        this.delayErrors = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super R> observer) {
        this.source.subscribe(new FlatMapSingleObserver(observer, this.mapper, this.delayErrors));
    }
}
