package p043io.reactivex.internal.operators.completable;

import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.CompletableSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.completable.CompletableHide */
public final class CompletableHide extends Completable {
    final CompletableSource source;

    /* renamed from: io.reactivex.internal.operators.completable.CompletableHide$HideCompletableObserver */
    static final class HideCompletableObserver implements CompletableObserver, Disposable {
        final CompletableObserver actual;

        /* renamed from: d */
        Disposable f3810d;

        HideCompletableObserver(CompletableObserver completableObserver) {
            this.actual = completableObserver;
        }

        public void dispose() {
            this.f3810d.dispose();
            this.f3810d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f3810d.isDisposed();
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3810d, disposable)) {
                this.f3810d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onComplete() {
            this.actual.onComplete();
        }
    }

    public CompletableHide(CompletableSource completableSource) {
        this.source = completableSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new HideCompletableObserver(completableObserver));
    }
}
