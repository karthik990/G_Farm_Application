package p043io.reactivex;

import p043io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.MaybeObserver */
public interface MaybeObserver<T> {
    void onComplete();

    void onError(Throwable th);

    void onSubscribe(Disposable disposable);

    void onSuccess(T t);
}
