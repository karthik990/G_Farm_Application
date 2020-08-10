package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicInteger;
import p043io.reactivex.Observable;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.CompositeException;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Predicate;
import p043io.reactivex.internal.disposables.SequentialDisposable;

/* renamed from: io.reactivex.internal.operators.observable.ObservableRetryPredicate */
public final class ObservableRetryPredicate<T> extends AbstractObservableWithUpstream<T, T> {
    final long count;
    final Predicate<? super Throwable> predicate;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableRetryPredicate$RepeatObserver */
    static final class RepeatObserver<T> extends AtomicInteger implements Observer<T> {
        private static final long serialVersionUID = -7098360935104053232L;
        final Observer<? super T> actual;
        final Predicate<? super Throwable> predicate;
        long remaining;

        /* renamed from: sa */
        final SequentialDisposable f4047sa;
        final ObservableSource<? extends T> source;

        RepeatObserver(Observer<? super T> observer, long j, Predicate<? super Throwable> predicate2, SequentialDisposable sequentialDisposable, ObservableSource<? extends T> observableSource) {
            this.actual = observer;
            this.f4047sa = sequentialDisposable;
            this.source = observableSource;
            this.predicate = predicate2;
            this.remaining = j;
        }

        public void onSubscribe(Disposable disposable) {
            this.f4047sa.update(disposable);
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable th) {
            long j = this.remaining;
            if (j != Long.MAX_VALUE) {
                this.remaining = j - 1;
            }
            if (j == 0) {
                this.actual.onError(th);
            } else {
                try {
                    if (!this.predicate.test(th)) {
                        this.actual.onError(th);
                        return;
                    }
                    subscribeNext();
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.actual.onError(new CompositeException(th, th2));
                }
            }
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        /* access modifiers changed from: 0000 */
        public void subscribeNext() {
            if (getAndIncrement() == 0) {
                int i = 1;
                while (!this.f4047sa.isDisposed()) {
                    this.source.subscribe(this);
                    i = addAndGet(-i);
                    if (i == 0) {
                    }
                }
            }
        }
    }

    public ObservableRetryPredicate(Observable<T> observable, long j, Predicate<? super Throwable> predicate2) {
        super(observable);
        this.predicate = predicate2;
        this.count = j;
    }

    public void subscribeActual(Observer<? super T> observer) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        observer.onSubscribe(sequentialDisposable);
        RepeatObserver repeatObserver = new RepeatObserver(observer, this.count, this.predicate, sequentialDisposable, this.source);
        repeatObserver.subscribeNext();
    }
}
