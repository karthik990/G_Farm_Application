package p043io.reactivex.internal.operators.maybe;

import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.Maybe;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.MaybeSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.fuseable.FuseToMaybe;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeIgnoreElementCompletable */
public final class MaybeIgnoreElementCompletable<T> extends Completable implements FuseToMaybe<T> {
    final MaybeSource<T> source;

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeIgnoreElementCompletable$IgnoreMaybeObserver */
    static final class IgnoreMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        final CompletableObserver actual;

        /* renamed from: d */
        Disposable f3966d;

        IgnoreMaybeObserver(CompletableObserver completableObserver) {
            this.actual = completableObserver;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3966d, disposable)) {
                this.f3966d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.f3966d = DisposableHelper.DISPOSED;
            this.actual.onComplete();
        }

        public void onError(Throwable th) {
            this.f3966d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
        }

        public void onComplete() {
            this.f3966d = DisposableHelper.DISPOSED;
            this.actual.onComplete();
        }

        public boolean isDisposed() {
            return this.f3966d.isDisposed();
        }

        public void dispose() {
            this.f3966d.dispose();
            this.f3966d = DisposableHelper.DISPOSED;
        }
    }

    public MaybeIgnoreElementCompletable(MaybeSource<T> maybeSource) {
        this.source = maybeSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new IgnoreMaybeObserver(completableObserver));
    }

    public Maybe<T> fuseToMaybe() {
        return RxJavaPlugins.onAssembly((Maybe<T>) new MaybeIgnoreElement<T>(this.source));
    }
}
