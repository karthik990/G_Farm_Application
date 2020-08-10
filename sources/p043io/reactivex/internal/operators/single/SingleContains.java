package p043io.reactivex.internal.operators.single;

import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.BiPredicate;

/* renamed from: io.reactivex.internal.operators.single.SingleContains */
public final class SingleContains<T> extends p043io.reactivex.Single<Boolean> {
    final BiPredicate<Object, Object> comparer;
    final SingleSource<T> source;
    final Object value;

    /* renamed from: io.reactivex.internal.operators.single.SingleContains$Single */
    final class Single implements SingleObserver<T> {

        /* renamed from: s */
        private final SingleObserver<? super Boolean> f4115s;

        Single(SingleObserver<? super Boolean> singleObserver) {
            this.f4115s = singleObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.f4115s.onSubscribe(disposable);
        }

        public void onSuccess(T t) {
            try {
                this.f4115s.onSuccess(Boolean.valueOf(SingleContains.this.comparer.test(t, SingleContains.this.value)));
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.f4115s.onError(th);
            }
        }

        public void onError(Throwable th) {
            this.f4115s.onError(th);
        }
    }

    public SingleContains(SingleSource<T> singleSource, Object obj, BiPredicate<Object, Object> biPredicate) {
        this.source = singleSource;
        this.value = obj;
        this.comparer = biPredicate;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super Boolean> singleObserver) {
        this.source.subscribe(new Single(singleObserver));
    }
}
