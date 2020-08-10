package p043io.reactivex.internal.operators.maybe;

import p043io.reactivex.CompletableObserver;
import p043io.reactivex.CompletableSource;
import p043io.reactivex.Maybe;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.fuseable.HasUpstreamCompletableSource;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeFromCompletable */
public final class MaybeFromCompletable<T> extends Maybe<T> implements HasUpstreamCompletableSource {
    final CompletableSource source;

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeFromCompletable$FromCompletableObserver */
    static final class FromCompletableObserver<T> implements CompletableObserver, Disposable {
        final MaybeObserver<? super T> actual;

        /* renamed from: d */
        Disposable f3962d;

        FromCompletableObserver(MaybeObserver<? super T> maybeObserver) {
            this.actual = maybeObserver;
        }

        public void dispose() {
            this.f3962d.dispose();
            this.f3962d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f3962d.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3962d, disposable)) {
                this.f3962d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onComplete() {
            this.f3962d = DisposableHelper.DISPOSED;
            this.actual.onComplete();
        }

        public void onError(Throwable th) {
            this.f3962d = DisposableHelper.DISPOSED;
            this.actual.onError(th);
        }
    }

    public MaybeFromCompletable(CompletableSource completableSource) {
        this.source = completableSource;
    }

    public CompletableSource source() {
        return this.source;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new FromCompletableObserver(maybeObserver));
    }
}
