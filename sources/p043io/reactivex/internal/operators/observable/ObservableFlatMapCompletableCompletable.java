package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.CompletableSource;
import p043io.reactivex.Observable;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.CompositeDisposable;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.fuseable.FuseToObservable;
import p043io.reactivex.internal.util.AtomicThrowable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMapCompletableCompletable */
public final class ObservableFlatMapCompletableCompletable<T> extends Completable implements FuseToObservable<T> {
    final boolean delayErrors;
    final Function<? super T, ? extends CompletableSource> mapper;
    final ObservableSource<T> source;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMapCompletableCompletable$FlatMapCompletableMainObserver */
    static final class FlatMapCompletableMainObserver<T> extends AtomicInteger implements Disposable, Observer<T> {
        private static final long serialVersionUID = 8443155186132538303L;
        final CompletableObserver actual;

        /* renamed from: d */
        Disposable f4021d;
        final boolean delayErrors;
        final AtomicThrowable errors = new AtomicThrowable();
        final Function<? super T, ? extends CompletableSource> mapper;
        final CompositeDisposable set = new CompositeDisposable();

        /* renamed from: io.reactivex.internal.operators.observable.ObservableFlatMapCompletableCompletable$FlatMapCompletableMainObserver$InnerObserver */
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

        FlatMapCompletableMainObserver(CompletableObserver completableObserver, Function<? super T, ? extends CompletableSource> function, boolean z) {
            this.actual = completableObserver;
            this.mapper = function;
            this.delayErrors = z;
            lazySet(1);
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4021d, disposable)) {
                this.f4021d = disposable;
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
                this.f4021d.dispose();
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
            this.f4021d.dispose();
            this.set.dispose();
        }

        public boolean isDisposed() {
            return this.f4021d.isDisposed();
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

    public ObservableFlatMapCompletableCompletable(ObservableSource<T> observableSource, Function<? super T, ? extends CompletableSource> function, boolean z) {
        this.source = observableSource;
        this.mapper = function;
        this.delayErrors = z;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new FlatMapCompletableMainObserver(completableObserver, this.mapper, this.delayErrors));
    }

    public Observable<T> fuseToObservable() {
        return RxJavaPlugins.onAssembly((Observable<T>) new ObservableFlatMapCompletable<T>(this.source, this.mapper, this.delayErrors));
    }
}
