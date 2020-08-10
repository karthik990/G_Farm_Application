package p043io.reactivex.internal.operators.maybe;

import p043io.reactivex.Maybe;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.fuseable.HasUpstreamSingleSource;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeFromSingle */
public final class MaybeFromSingle<T> extends Maybe<T> implements HasUpstreamSingleSource<T> {
    final SingleSource<T> source;

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeFromSingle$FromSingleObserver */
    static final class FromSingleObserver<T> implements SingleObserver<T>, Disposable {
        final MaybeObserver<? super T> actual;

        /* renamed from: d */
        Disposable f3963d;

        FromSingleObserver(MaybeObserver<? super T> maybeObserver) {
            this.actual = maybeObserver;
        }

        public void dispose() {
            this.f3963d.dispose();
            this.f3963d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f3963d.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3963d, disposable)) {
                this.f3963d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.f3963d = DisposableHelper.DISPOSED;
            this.actual.onSuccess(t);
        }

        public void onError(Throwable th) {
            this.f3963d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
        }
    }

    public MaybeFromSingle(SingleSource<T> singleSource) {
        this.source = singleSource;
    }

    public SingleSource<T> source() {
        return this.source;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new FromSingleObserver(maybeObserver));
    }
}
