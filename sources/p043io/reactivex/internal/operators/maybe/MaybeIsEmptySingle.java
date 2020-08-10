package p043io.reactivex.internal.operators.maybe;

import p043io.reactivex.Maybe;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.MaybeSource;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.fuseable.FuseToMaybe;
import p043io.reactivex.internal.fuseable.HasUpstreamMaybeSource;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeIsEmptySingle */
public final class MaybeIsEmptySingle<T> extends Single<Boolean> implements HasUpstreamMaybeSource<T>, FuseToMaybe<Boolean> {
    final MaybeSource<T> source;

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeIsEmptySingle$IsEmptyMaybeObserver */
    static final class IsEmptyMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        final SingleObserver<? super Boolean> actual;

        /* renamed from: d */
        Disposable f3968d;

        IsEmptyMaybeObserver(SingleObserver<? super Boolean> singleObserver) {
            this.actual = singleObserver;
        }

        public void dispose() {
            this.f3968d.dispose();
            this.f3968d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f3968d.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3968d, disposable)) {
                this.f3968d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.f3968d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(Boolean.valueOf(false));
        }

        public void onError(Throwable th) {
            this.f3968d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
        }

        public void onComplete() {
            this.f3968d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(Boolean.valueOf(true));
        }
    }

    public MaybeIsEmptySingle(MaybeSource<T> maybeSource) {
        this.source = maybeSource;
    }

    public MaybeSource<T> source() {
        return this.source;
    }

    public Maybe<Boolean> fuseToMaybe() {
        return RxJavaPlugins.onAssembly((Maybe<T>) new MaybeIsEmpty<T>(this.source));
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        this.source.subscribe(new IsEmptyMaybeObserver(singleObserver));
    }
}
