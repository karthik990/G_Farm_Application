package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Predicate;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableTakeUntilPredicate */
public final class ObservableTakeUntilPredicate<T> extends AbstractObservableWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableTakeUntilPredicate$TakeUntilPredicateObserver */
    static final class TakeUntilPredicateObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> actual;
        boolean done;
        final Predicate<? super T> predicate;

        /* renamed from: s */
        Disposable f4072s;

        TakeUntilPredicateObserver(Observer<? super T> observer, Predicate<? super T> predicate2) {
            this.actual = observer;
            this.predicate = predicate2;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f4072s, disposable)) {
                this.f4072s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f4072s.dispose();
        }

        public boolean isDisposed() {
            return this.f4072s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                this.actual.onNext(t);
                try {
                    if (this.predicate.test(t)) {
                        this.done = true;
                        this.f4072s.dispose();
                        this.actual.onComplete();
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.f4072s.dispose();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (!this.done) {
                this.done = true;
                this.actual.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onComplete();
            }
        }
    }

    public ObservableTakeUntilPredicate(ObservableSource<T> observableSource, Predicate<? super T> predicate2) {
        super(observableSource);
        this.predicate = predicate2;
    }

    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new TakeUntilPredicateObserver(observer, this.predicate));
    }
}
