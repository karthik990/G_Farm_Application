package p043io.reactivex.internal.operators.maybe;

import p043io.reactivex.MaybeObserver;
import p043io.reactivex.MaybeSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeIgnoreElement */
public final class MaybeIgnoreElement<T> extends AbstractMaybeWithUpstream<T, T> {

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeIgnoreElement$IgnoreMaybeObserver */
    static final class IgnoreMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super T> actual;

        /* renamed from: d */
        Disposable f3965d;

        IgnoreMaybeObserver(MaybeObserver<? super T> maybeObserver) {
            this.actual = maybeObserver;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3965d, disposable)) {
                this.f3965d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.f3965d = DisposableHelper.DISPOSED;
            this.actual.onComplete();
        }

        public void onError(Throwable th) {
            this.f3965d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
        }

        public void onComplete() {
            this.f3965d = DisposableHelper.DISPOSED;
            this.actual.onComplete();
        }

        public boolean isDisposed() {
            return this.f3965d.isDisposed();
        }

        public void dispose() {
            this.f3965d.dispose();
            this.f3965d = DisposableHelper.DISPOSED;
        }
    }

    public MaybeIgnoreElement(MaybeSource<T> maybeSource) {
        super(maybeSource);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new IgnoreMaybeObserver(maybeObserver));
    }
}
