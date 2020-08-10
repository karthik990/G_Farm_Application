package p043io.reactivex.internal.operators.maybe;

import p043io.reactivex.MaybeObserver;
import p043io.reactivex.MaybeSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeIsEmpty */
public final class MaybeIsEmpty<T> extends AbstractMaybeWithUpstream<T, Boolean> {

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeIsEmpty$IsEmptyMaybeObserver */
    static final class IsEmptyMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super Boolean> actual;

        /* renamed from: d */
        Disposable f3967d;

        IsEmptyMaybeObserver(MaybeObserver<? super Boolean> maybeObserver) {
            this.actual = maybeObserver;
        }

        public void dispose() {
            this.f3967d.dispose();
        }

        public boolean isDisposed() {
            return this.f3967d.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3967d, disposable)) {
                this.f3967d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.actual.onSuccess(Boolean.valueOf(false));
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onComplete() {
            this.actual.onSuccess(Boolean.valueOf(true));
        }
    }

    public MaybeIsEmpty(MaybeSource<T> maybeSource) {
        super(maybeSource);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super Boolean> maybeObserver) {
        this.source.subscribe(new IsEmptyMaybeObserver(maybeObserver));
    }
}
