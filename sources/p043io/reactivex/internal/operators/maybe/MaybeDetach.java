package p043io.reactivex.internal.operators.maybe;

import p043io.reactivex.MaybeObserver;
import p043io.reactivex.MaybeSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeDetach */
public final class MaybeDetach<T> extends AbstractMaybeWithUpstream<T, T> {

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeDetach$DetachMaybeObserver */
    static final class DetachMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        MaybeObserver<? super T> actual;

        /* renamed from: d */
        Disposable f3950d;

        DetachMaybeObserver(MaybeObserver<? super T> maybeObserver) {
            this.actual = maybeObserver;
        }

        public void dispose() {
            this.actual = null;
            this.f3950d.dispose();
            this.f3950d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f3950d.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3950d, disposable)) {
                this.f3950d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.f3950d = DisposableHelper.DISPOSED;
            MaybeObserver<? super T> maybeObserver = this.actual;
            if (maybeObserver != null) {
                maybeObserver.onSuccess(t);
            }
        }

        public void onError(Throwable th) {
            this.f3950d = DisposableHelper.DISPOSED;
            MaybeObserver<? super T> maybeObserver = this.actual;
            if (maybeObserver != null) {
                maybeObserver.onError(th);
            }
        }

        public void onComplete() {
            this.f3950d = DisposableHelper.DISPOSED;
            MaybeObserver<? super T> maybeObserver = this.actual;
            if (maybeObserver != null) {
                maybeObserver.onComplete();
            }
        }
    }

    public MaybeDetach(MaybeSource<T> maybeSource) {
        super(maybeSource);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new DetachMaybeObserver(maybeObserver));
    }
}
