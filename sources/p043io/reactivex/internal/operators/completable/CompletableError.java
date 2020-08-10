package p043io.reactivex.internal.operators.completable;

import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.internal.disposables.EmptyDisposable;

/* renamed from: io.reactivex.internal.operators.completable.CompletableError */
public final class CompletableError extends Completable {
    final Throwable error;

    public CompletableError(Throwable th) {
        this.error = th;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        EmptyDisposable.error(this.error, completableObserver);
    }
}
