package p043io.reactivex.internal.operators.single;

import java.util.concurrent.atomic.AtomicBoolean;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.CompositeDisposable;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.internal.disposables.EmptyDisposable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.single.SingleAmb */
public final class SingleAmb<T> extends Single<T> {
    private final SingleSource<? extends T>[] sources;
    private final Iterable<? extends SingleSource<? extends T>> sourcesIterable;

    /* renamed from: io.reactivex.internal.operators.single.SingleAmb$AmbSingleObserver */
    static final class AmbSingleObserver<T> extends AtomicBoolean implements SingleObserver<T> {
        private static final long serialVersionUID = -1944085461036028108L;

        /* renamed from: s */
        final SingleObserver<? super T> f4114s;
        final CompositeDisposable set;

        AmbSingleObserver(SingleObserver<? super T> singleObserver, CompositeDisposable compositeDisposable) {
            this.f4114s = singleObserver;
            this.set = compositeDisposable;
        }

        public void onSubscribe(Disposable disposable) {
            this.set.add(disposable);
        }

        public void onSuccess(T t) {
            if (compareAndSet(false, true)) {
                this.set.dispose();
                this.f4114s.onSuccess(t);
            }
        }

        public void onError(Throwable th) {
            if (compareAndSet(false, true)) {
                this.set.dispose();
                this.f4114s.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }
    }

    public SingleAmb(SingleSource<? extends T>[] singleSourceArr, Iterable<? extends SingleSource<? extends T>> iterable) {
        this.sources = singleSourceArr;
        this.sourcesIterable = iterable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        int i;
        SingleSource<? extends T>[] singleSourceArr = this.sources;
        String str = "One of the sources is null";
        if (singleSourceArr == null) {
            singleSourceArr = new SingleSource[8];
            try {
                i = 0;
                for (SingleSource<? extends T> singleSource : this.sourcesIterable) {
                    if (singleSource == null) {
                        EmptyDisposable.error((Throwable) new NullPointerException(str), singleObserver);
                        return;
                    }
                    if (i == singleSourceArr.length) {
                        SingleSource<? extends T>[] singleSourceArr2 = new SingleSource[((i >> 2) + i)];
                        System.arraycopy(singleSourceArr, 0, singleSourceArr2, 0, i);
                        singleSourceArr = singleSourceArr2;
                    }
                    int i2 = i + 1;
                    singleSourceArr[i] = singleSource;
                    i = i2;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, singleObserver);
                return;
            }
        } else {
            i = singleSourceArr.length;
        }
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AmbSingleObserver ambSingleObserver = new AmbSingleObserver(singleObserver, compositeDisposable);
        singleObserver.onSubscribe(compositeDisposable);
        int i3 = 0;
        while (i3 < i) {
            SingleSource<? extends T> singleSource2 = singleSourceArr[i3];
            if (!ambSingleObserver.get()) {
                if (singleSource2 == null) {
                    compositeDisposable.dispose();
                    NullPointerException nullPointerException = new NullPointerException(str);
                    if (ambSingleObserver.compareAndSet(false, true)) {
                        singleObserver.onError(nullPointerException);
                    } else {
                        RxJavaPlugins.onError(nullPointerException);
                    }
                    return;
                }
                singleSource2.subscribe(ambSingleObserver);
                i3++;
            } else {
                return;
            }
        }
    }
}
