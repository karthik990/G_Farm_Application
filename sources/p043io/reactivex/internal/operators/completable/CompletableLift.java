package p043io.reactivex.internal.operators.completable;

import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.CompletableOperator;
import p043io.reactivex.CompletableSource;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletableLift */
public final class CompletableLift extends Completable {
    final CompletableOperator onLift;
    final CompletableSource source;

    public CompletableLift(CompletableSource completableSource, CompletableOperator completableOperator) {
        this.source = completableSource;
        this.onLift = completableOperator;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        try {
            this.source.subscribe(this.onLift.apply(completableObserver));
        } catch (NullPointerException e) {
            throw e;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
    }
}
