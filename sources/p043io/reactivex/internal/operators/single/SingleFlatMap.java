package p043io.reactivex.internal.operators.single;

import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleFlatMap */
public final class SingleFlatMap<T, R> extends Single<R> {
    final Function<? super T, ? extends SingleSource<? extends R>> mapper;
    final SingleSource<? extends T> source;

    /* renamed from: io.reactivex.internal.operators.single.SingleFlatMap$SingleFlatMapCallback */
    static final class SingleFlatMapCallback<T, R> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = 3258103020495908596L;
        final SingleObserver<? super R> actual;
        final Function<? super T, ? extends SingleSource<? extends R>> mapper;

        /* renamed from: io.reactivex.internal.operators.single.SingleFlatMap$SingleFlatMapCallback$FlatMapSingleObserver */
        static final class FlatMapSingleObserver<R> implements SingleObserver<R> {
            final SingleObserver<? super R> actual;
            final AtomicReference<Disposable> parent;

            FlatMapSingleObserver(AtomicReference<Disposable> atomicReference, SingleObserver<? super R> singleObserver) {
                this.parent = atomicReference;
                this.actual = singleObserver;
            }

            public void onSubscribe(Disposable disposable) {
                DisposableHelper.replace(this.parent, disposable);
            }

            public void onSuccess(R r) {
                this.actual.onSuccess(r);
            }

            public void onError(Throwable th) {
                this.actual.onError(th);
            }
        }

        SingleFlatMapCallback(SingleObserver<? super R> singleObserver, Function<? super T, ? extends SingleSource<? extends R>> function) {
            this.actual = singleObserver;
            this.mapper = function;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            try {
                SingleSource singleSource = (SingleSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The single returned by the mapper is null");
                if (!isDisposed()) {
                    singleSource.subscribe(new FlatMapSingleObserver(this, this.actual));
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.actual.onError(th);
            }
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }
    }

    public SingleFlatMap(SingleSource<? extends T> singleSource, Function<? super T, ? extends SingleSource<? extends R>> function) {
        this.mapper = function;
        this.source = singleSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        this.source.subscribe(new SingleFlatMapCallback(singleObserver, this.mapper));
    }
}
