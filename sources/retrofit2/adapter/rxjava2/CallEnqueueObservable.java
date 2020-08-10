package retrofit2.adapter.rxjava2;

import p043io.reactivex.Observable;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.CompositeException;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

final class CallEnqueueObservable<T> extends Observable<Response<T>> {
    private final Call<T> originalCall;

    private static final class CallCallback<T> implements Disposable, Callback<T> {
        private final Call<?> call;
        private final Observer<? super Response<T>> observer;
        boolean terminated = false;

        CallCallback(Call<?> call2, Observer<? super Response<T>> observer2) {
            this.call = call2;
            this.observer = observer2;
        }

        public void onResponse(Call<T> call2, Response<T> response) {
            if (!call2.isCanceled()) {
                try {
                    this.observer.onNext(response);
                    if (!call2.isCanceled()) {
                        this.terminated = true;
                        this.observer.onComplete();
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(new CompositeException(th, th));
                }
            }
        }

        public void onFailure(Call<T> call2, Throwable th) {
            if (!call2.isCanceled()) {
                try {
                    this.observer.onError(th);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    RxJavaPlugins.onError(new CompositeException(th, th2));
                }
            }
        }

        public void dispose() {
            this.call.cancel();
        }

        public boolean isDisposed() {
            return this.call.isCanceled();
        }
    }

    CallEnqueueObservable(Call<T> call) {
        this.originalCall = call;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super Response<T>> observer) {
        Call clone = this.originalCall.clone();
        CallCallback callCallback = new CallCallback(clone, observer);
        observer.onSubscribe(callCallback);
        clone.enqueue(callCallback);
    }
}
