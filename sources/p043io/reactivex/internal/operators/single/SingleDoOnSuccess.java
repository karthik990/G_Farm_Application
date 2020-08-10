package p043io.reactivex.internal.operators.single;

import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Consumer;

/* renamed from: io.reactivex.internal.operators.single.SingleDoOnSuccess */
public final class SingleDoOnSuccess<T> extends Single<T> {
    final Consumer<? super T> onSuccess;
    final SingleSource<T> source;

    /* renamed from: io.reactivex.internal.operators.single.SingleDoOnSuccess$DoOnSuccess */
    final class DoOnSuccess implements SingleObserver<T> {

        /* renamed from: s */
        private final SingleObserver<? super T> f4126s;

        DoOnSuccess(SingleObserver<? super T> singleObserver) {
            this.f4126s = singleObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.f4126s.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            try {
                SingleDoOnSuccess.this.onSuccess.accept(t);
                this.f4126s.onSuccess(t);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.f4126s.onError(th);
            }
        }

        public void onError(Throwable th) {
            this.f4126s.onError(th);
        }
    }

    public SingleDoOnSuccess(SingleSource<T> singleSource, Consumer<? super T> consumer) {
        this.source = singleSource;
        this.onSuccess = consumer;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe(new DoOnSuccess(singleObserver));
    }
}
