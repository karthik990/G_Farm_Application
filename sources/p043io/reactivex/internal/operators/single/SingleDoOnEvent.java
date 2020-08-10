package p043io.reactivex.internal.operators.single;

import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.CompositeException;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.BiConsumer;

/* renamed from: io.reactivex.internal.operators.single.SingleDoOnEvent */
public final class SingleDoOnEvent<T> extends Single<T> {
    final BiConsumer<? super T, ? super Throwable> onEvent;
    final SingleSource<T> source;

    /* renamed from: io.reactivex.internal.operators.single.SingleDoOnEvent$DoOnEvent */
    final class DoOnEvent implements SingleObserver<T> {

        /* renamed from: s */
        private final SingleObserver<? super T> f4125s;

        DoOnEvent(SingleObserver<? super T> singleObserver) {
            this.f4125s = singleObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.f4125s.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            try {
                SingleDoOnEvent.this.onEvent.accept(t, null);
                this.f4125s.onSuccess(t);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.f4125s.onError(th);
            }
        }

        public void onError(Throwable th) {
            try {
                SingleDoOnEvent.this.onEvent.accept(null, th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.f4125s.onError(th);
        }
    }

    public SingleDoOnEvent(SingleSource<T> singleSource, BiConsumer<? super T, ? super Throwable> biConsumer) {
        this.source = singleSource;
        this.onEvent = biConsumer;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.source.subscribe(new DoOnEvent(singleObserver));
    }
}
