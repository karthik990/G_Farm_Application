package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.Observable;
import p043io.reactivex.Observer;
import p043io.reactivex.Scheduler;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableInterval */
public final class ObservableInterval extends Observable<Long> {
    final long initialDelay;
    final long period;
    final Scheduler scheduler;
    final TimeUnit unit;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInterval$IntervalObserver */
    static final class IntervalObserver extends AtomicReference<Disposable> implements Disposable, Runnable {
        private static final long serialVersionUID = 346773832286157679L;
        final Observer<? super Long> actual;
        long count;

        IntervalObserver(Observer<? super Long> observer) {
            this.actual = observer;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        public void run() {
            if (get() != DisposableHelper.DISPOSED) {
                Observer<? super Long> observer = this.actual;
                long j = this.count;
                this.count = 1 + j;
                observer.onNext(Long.valueOf(j));
            }
        }

        public void setResource(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    public ObservableInterval(long j, long j2, TimeUnit timeUnit, Scheduler scheduler2) {
        this.initialDelay = j;
        this.period = j2;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
    }

    public void subscribeActual(Observer<? super Long> observer) {
        IntervalObserver intervalObserver = new IntervalObserver(observer);
        observer.onSubscribe(intervalObserver);
        intervalObserver.setResource(this.scheduler.schedulePeriodicallyDirect(intervalObserver, this.initialDelay, this.period, this.unit));
    }
}
