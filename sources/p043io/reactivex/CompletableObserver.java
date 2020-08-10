package p043io.reactivex;

import p043io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.CompletableObserver */
public interface CompletableObserver {
    void onComplete();

    void onError(Throwable th);

    void onSubscribe(Disposable disposable);
}
