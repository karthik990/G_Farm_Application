package p043io.reactivex.internal.operators.maybe;

import p043io.reactivex.MaybeObserver;
import p043io.reactivex.MaybeSource;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeCount */
public final class MaybeCount<T> extends Single<Long> implements HasUpstreamMaybeSource<T> {
    final MaybeSource<T> source;

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeCount$CountMaybeObserver */
    static final class CountMaybeObserver implements MaybeObserver<Object>, Disposable {
        final SingleObserver<? super Long> actual;

        /* renamed from: d */
        Disposable f3947d;

        CountMaybeObserver(SingleObserver<? super Long> singleObserver) {
            this.actual = singleObserver;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3947d, disposable)) {
                this.f3947d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(Object obj) {
            this.f3947d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(Long.valueOf(1));
        }

        public void onError(Throwable th) {
            this.f3947d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
        }

        public void onComplete() {
            this.f3947d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(Long.valueOf(0));
        }

        public boolean isDisposed() {
            return this.f3947d.isDisposed();
        }

        public void dispose() {
            this.f3947d.dispose();
            this.f3947d = DisposableHelper.DISPOSED;
        }
    }

    public MaybeCount(MaybeSource<T> maybeSource) {
        this.source = maybeSource;
    }

    public MaybeSource<T> source() {
        return this.source;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Long> singleObserver) {
        this.source.subscribe(new CountMaybeObserver(singleObserver));
    }
}
