package p043io.reactivex.internal.operators.single;

import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.Scheduler;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleUnsubscribeOn */
public final class SingleUnsubscribeOn<T> extends Single<T> {
    final Scheduler scheduler;
    final SingleSource<T> source;

    /* renamed from: io.reactivex.internal.operators.single.SingleUnsubscribeOn$UnsubscribeOnSingleObserver */
    static final class UnsubscribeOnSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable, Runnable {
        private static final long serialVersionUID = 3256698449646456986L;
        final SingleObserver<? super T> actual;

        /* renamed from: ds */
        Disposable f4139ds;
        final Scheduler scheduler;

        UnsubscribeOnSingleObserver(SingleObserver<? super T> singleObserver, Scheduler scheduler2) {
            this.actual = singleObserver;
            this.scheduler = scheduler2;
        }

        public void dispose() {
            Disposable disposable = (Disposable) getAndSet(DisposableHelper.DISPOSED);
            if (disposable != DisposableHelper.DISPOSED) {
                this.f4139ds = disposable;
                this.scheduler.scheduleDirect(this);
            }
        }

        public void run() {
            this.f4139ds.dispose();
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.actual.onSuccess(t);
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }
    }

    public SingleUnsubscribeOn(SingleSource<T> singleSource, Scheduler scheduler2) {
        this.source = singleSource;
        this.scheduler = scheduler2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe(new UnsubscribeOnSingleObserver(singleObserver, this.scheduler));
    }
}
