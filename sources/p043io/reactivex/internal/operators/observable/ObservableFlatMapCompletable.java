package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.CompletableSource;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.CompositeDisposable;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.observers.BasicIntQueueDisposable;
import p043io.reactivex.internal.util.AtomicThrowable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMapCompletable */
public final class ObservableFlatMapCompletable<T> extends AbstractObservableWithUpstream<T, T> {
    final boolean delayErrors;
    final Function<? super T, ? extends CompletableSource> mapper;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMapCompletable$FlatMapCompletableMainObserver */
    static final class FlatMapCompletableMainObserver<T> extends BasicIntQueueDisposable<T> implements Observer<T> {
        private static final long serialVersionUID = 8443155186132538303L;
        final Observer<? super T> actual;

        /* renamed from: d */
        Disposable f4020d;
        final boolean delayErrors;
        final AtomicThrowable errors = new AtomicThrowable();
        final Function<? super T, ? extends CompletableSource> mapper;
        final CompositeDisposable set = new CompositeDisposable();

        /* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMapCompletable$FlatMapCompletableMainObserver$InnerObserver */
        final class InnerObserver extends AtomicReference<Disposable> implements CompletableObserver, Disposable {
            private static final long serialVersionUID = 8606673141535671828L;

            InnerObserver() {
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this, disposable);
            }

            public void onComplete() {
                FlatMapCompletableMainObserver.this.innerComplete(this);
            }

            public void onError(Throwable th) {
                FlatMapCompletableMainObserver.this.innerError(this, th);
            }

            public void dispose() {
                DisposableHelper.dispose(this);
            }

            public boolean isDisposed() {
                return DisposableHelper.isDisposed((Disposable) get());
            }
        }

        public void clear() {
        }

        public boolean isEmpty() {
            return true;
        }

        public T poll() throws Exception {
            return null;
        }

        public int requestFusion(int i) {
            return i & 2;
        }

        FlatMapCompletableMainObserver(Observer<? super T> observer, Function<? super T, ? extends CompletableSource> function, boolean z) {
            this.actual = observer;
            this.mapper = function;
            this.delayErrors = z;
            lazySet(1);
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4020d, disposable)) {
                this.f4020d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            try {
                CompletableSource completableSource = (CompletableSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null CompletableSource");
                getAndIncrement();
                InnerObserver innerObserver = new InnerObserver();
                if (this.set.add(innerObserver)) {
                    completableSource.subscribe(innerObserver);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.f4020d.dispose();
                onError(th);
            }
        }

        public void onError(Throwable th) {
            if (!this.errors.addThrowable(th)) {
                RxJavaPlugins.onError(th);
            } else if (!this.delayErrors) {
                dispose();
                if (getAndSet(0) > 0) {
                    this.actual.onError(this.errors.terminate());
                }
            } else if (decrementAndGet() == 0) {
                this.actual.onError(this.errors.terminate());
            }
        }

        public void onComplete() {
            if (decrementAndGet() == 0) {
                Throwable terminate = this.errors.terminate();
                if (terminate != null) {
                    this.actual.onError(terminate);
                } else {
                    this.actual.onComplete();
                }
            }
        }

        public void dispose() {
            this.f4020d.dispose();
            this.set.dispose();
        }

        public boolean isDisposed() {
            return this.f4020d.isDisposed();
        }

        /* access modifiers changed from: 0000 */
        public void innerComplete(InnerObserver innerObserver) {
            this.set.delete(innerObserver);
            onComplete();
        }

        /* access modifiers changed from: 0000 */
        public void innerError(InnerObserver innerObserver, Throwable th) {
            this.set.delete(innerObserver);
            onError(th);
        }
    }

    public ObservableFlatMapCompletable(ObservableSource<T> observableSource, Function<? super T, ? extends CompletableSource> function, boolean z) {
        super(observableSource);
        this.mapper = function;
        this.delayErrors = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new FlatMapCompletableMainObserver(observer, this.mapper, this.delayErrors));
    }
}
