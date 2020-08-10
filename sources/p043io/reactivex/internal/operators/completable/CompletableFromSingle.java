package p043io.reactivex.internal.operators.completable;

import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.completable.CompletableFromSingle */
public final class CompletableFromSingle<T> extends Completable {
    final SingleSource<T> single;

    /* renamed from: io.reactivex.internal.operators.completable.CompletableFromSingle$CompletableFromSingleObserver */
    static final class CompletableFromSingleObserver<T> implements SingleObserver<T> {

        /* renamed from: co */
        final CompletableObserver f3809co;

        CompletableFromSingleObserver(CompletableObserver completableObserver) {
            this.f3809co = completableObserver;
        }

        public void onError(Throwable th) {
            this.f3809co.onError(th);
        }

        public void onSubscribe(Disposable disposable) {
            this.f3809co.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            this.f3809co.onComplete();
        }
    }

    public CompletableFromSingle(SingleSource<T> singleSource) {
        this.single = singleSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.single.subscribe(new CompletableFromSingleObserver(completableObserver));
    }
}
