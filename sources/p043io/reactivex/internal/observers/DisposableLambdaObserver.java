package p043io.reactivex.internal.observers;

import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Action;
import p043io.reactivex.functions.Consumer;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.disposables.EmptyDisposable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.observers.DisposableLambdaObserver */
public final class DisposableLambdaObserver<T> implements Observer<T>, Disposable {
    final Observer<? super T> actual;
    final Action onDispose;
    final Consumer<? super Disposable> onSubscribe;

    /* renamed from: s */
    Disposable f3783s;

    public DisposableLambdaObserver(Observer<? super T> observer, Consumer<? super Disposable> consumer, Action action) {
        this.actual = observer;
        this.onSubscribe = consumer;
        this.onDispose = action;
    }

    public void onSubscribe(Disposable disposable) {
        try {
            this.onSubscribe.accept(disposable);
            if (DisposableHelper.validate(this.f3783s, disposable)) {
                this.f3783s = disposable;
                this.actual.onSubscribe(this);
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            disposable.dispose();
            this.f3783s = DisposableHelper.DISPOSED;
            EmptyDisposable.error(th, this.actual);
        }
    }

    public void onNext(T t) {
        this.actual.onNext(t);
    }

    public void onError(Throwable th) {
        if (this.f3783s != DisposableHelper.DISPOSED) {
            this.actual.onError(th);
        } else {
            RxJavaPlugins.onError(th);
        }
    }

    public void onComplete() {
        if (this.f3783s != DisposableHelper.DISPOSED) {
            this.actual.onComplete();
        }
    }

    public void dispose() {
        try {
            this.onDispose.run();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
        this.f3783s.dispose();
    }

    public boolean isDisposed() {
        return this.f3783s.isDisposed();
    }
}
