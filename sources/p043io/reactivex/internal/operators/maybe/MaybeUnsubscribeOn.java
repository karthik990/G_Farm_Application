package p043io.reactivex.internal.operators.maybe;

import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.MaybeSource;
import p043io.reactivex.Scheduler;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeUnsubscribeOn */
public final class MaybeUnsubscribeOn<T> extends AbstractMaybeWithUpstream<T, T> {
    final Scheduler scheduler;

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeUnsubscribeOn$UnsubscribeOnMaybeObserver */
    static final class UnsubscribeOnMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable, Runnable {
        private static final long serialVersionUID = 3256698449646456986L;
        final MaybeObserver<? super T> actual;

        /* renamed from: ds */
        Disposable f3977ds;
        final Scheduler scheduler;

        UnsubscribeOnMaybeObserver(MaybeObserver<? super T> maybeObserver, Scheduler scheduler2) {
            this.actual = maybeObserver;
            this.scheduler = scheduler2;
        }

        public void dispose() {
            Disposable disposable = (Disposable) getAndSet(DisposableHelper.DISPOSED);
            if (disposable != DisposableHelper.DISPOSED) {
                this.f3977ds = disposable;
                this.scheduler.scheduleDirect(this);
            }
        }

        public void run() {
            this.f3977ds.dispose();
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

        public void onComplete() {
            this.actual.onComplete();
        }
    }

    public MaybeUnsubscribeOn(MaybeSource<T> maybeSource, Scheduler scheduler2) {
        super(maybeSource);
        this.scheduler = scheduler2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new UnsubscribeOnMaybeObserver(maybeObserver, this.scheduler));
    }
}
