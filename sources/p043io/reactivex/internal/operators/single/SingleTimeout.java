package p043io.reactivex.internal.operators.single;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import p043io.reactivex.Scheduler;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.CompositeDisposable;
import p043io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.single.SingleTimeout */
public final class SingleTimeout<T> extends Single<T> {
    final SingleSource<? extends T> other;
    final Scheduler scheduler;
    final SingleSource<T> source;
    final long timeout;
    final TimeUnit unit;

    /* renamed from: io.reactivex.internal.operators.single.SingleTimeout$TimeoutDispose */
    final class TimeoutDispose implements Runnable {
        private final AtomicBoolean once;

        /* renamed from: s */
        final SingleObserver<? super T> f4135s;
        final CompositeDisposable set;

        /* renamed from: io.reactivex.internal.operators.single.SingleTimeout$TimeoutDispose$TimeoutObserver */
        final class TimeoutObserver implements SingleObserver<T> {
            TimeoutObserver() {
            }

            public void onError(Throwable th) {
                TimeoutDispose.this.set.dispose();
                TimeoutDispose.this.f4135s.onError(th);
            }

            public void onSubscribe(Disposable disposable) {
                TimeoutDispose.this.set.add(disposable);
            }

            public void onSuccess(T t) {
                TimeoutDispose.this.set.dispose();
                TimeoutDispose.this.f4135s.onSuccess(t);
            }
        }

        TimeoutDispose(AtomicBoolean atomicBoolean, CompositeDisposable compositeDisposable, SingleObserver<? super T> singleObserver) {
            this.once = atomicBoolean;
            this.set = compositeDisposable;
            this.f4135s = singleObserver;
        }

        public void run() {
            if (!this.once.compareAndSet(false, true)) {
                return;
            }
            if (SingleTimeout.this.other != null) {
                this.set.clear();
                SingleTimeout.this.other.subscribe(new TimeoutObserver());
                return;
            }
            this.set.dispose();
            this.f4135s.onError(new TimeoutException());
        }
    }

    /* renamed from: io.reactivex.internal.operators.single.SingleTimeout$TimeoutObserver */
    final class TimeoutObserver implements SingleObserver<T> {
        private final AtomicBoolean once;

        /* renamed from: s */
        private final SingleObserver<? super T> f4136s;
        private final CompositeDisposable set;

        TimeoutObserver(AtomicBoolean atomicBoolean, CompositeDisposable compositeDisposable, SingleObserver<? super T> singleObserver) {
            this.once = atomicBoolean;
            this.set = compositeDisposable;
            this.f4136s = singleObserver;
        }

        public void onError(Throwable th) {
            if (this.once.compareAndSet(false, true)) {
                this.set.dispose();
                this.f4136s.onError(th);
            }
        }

        public void onSubscribe(Disposable disposable) {
            this.set.add(disposable);
        }

        public void onSuccess(T t) {
            if (this.once.compareAndSet(false, true)) {
                this.set.dispose();
                this.f4136s.onSuccess(t);
            }
        }
    }

    public SingleTimeout(SingleSource<T> singleSource, long j, TimeUnit timeUnit, Scheduler scheduler2, SingleSource<? extends T> singleSource2) {
        this.source = singleSource;
        this.timeout = j;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
        this.other = singleSource2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        singleObserver.onSubscribe(compositeDisposable);
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        compositeDisposable.add(this.scheduler.scheduleDirect(new TimeoutDispose(atomicBoolean, compositeDisposable, singleObserver), this.timeout, this.unit));
        this.source.subscribe(new TimeoutObserver(atomicBoolean, compositeDisposable, singleObserver));
    }
}
