package retrofit2.adapter.rxjava2;

import p043io.reactivex.Observable;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.CompositeException;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Call;
import retrofit2.Response;

final class CallExecuteObservable<T> extends Observable<Response<T>> {
    private final Call<T> originalCall;

    private static final class CallDisposable implements Disposable {
        private final Call<?> call;

        CallDisposable(Call<?> call2) {
            this.call = call2;
        }

        public void dispose() {
            this.call.cancel();
        }

        public boolean isDisposed() {
            return this.call.isCanceled();
        }
    }

    CallExecuteObservable(Call<T> call) {
        this.originalCall = call;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super Response<T>> observer) {
        boolean z;
        Call clone = this.originalCall.clone();
        observer.onSubscribe(new CallDisposable(clone));
        try {
            Response execute = clone.execute();
            if (!clone.isCanceled()) {
                observer.onNext(execute);
            }
            if (!clone.isCanceled()) {
                try {
                    observer.onComplete();
                } catch (Throwable th) {
                    th = th;
                    z = true;
                }
            }
        } catch (Throwable th2) {
            th = th2;
            z = false;
            Exceptions.throwIfFatal(th);
            if (z) {
                RxJavaPlugins.onError(th);
            } else if (!clone.isCanceled()) {
                try {
                    observer.onError(th);
                } catch (Throwable th3) {
                    Exceptions.throwIfFatal(th3);
                    RxJavaPlugins.onError(new CompositeException(th, th3));
                }
            }
        }
    }
}
