package p043io.reactivex.internal.operators.maybe;

import p043io.reactivex.MaybeObserver;
import p043io.reactivex.MaybeSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeHide */
public final class MaybeHide<T> extends AbstractMaybeWithUpstream<T, T> {

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeHide$HideMaybeObserver */
    static final class HideMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super T> actual;

        /* renamed from: d */
        Disposable f3964d;

        HideMaybeObserver(MaybeObserver<? super T> maybeObserver) {
            this.actual = maybeObserver;
        }

        public void dispose() {
            this.f3964d.dispose();
            this.f3964d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f3964d.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3964d, disposable)) {
                this.f3964d = disposable;
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

    public MaybeHide(MaybeSource<T> maybeSource) {
        super(maybeSource);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new HideMaybeObserver(maybeObserver));
    }
}
