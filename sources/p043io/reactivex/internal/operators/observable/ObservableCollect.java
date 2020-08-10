package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.Callable;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.functions.BiConsumer;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.disposables.EmptyDisposable;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableCollect */
public final class ObservableCollect<T, U> extends AbstractObservableWithUpstream<T, U> {
    final BiConsumer<? super U, ? super T> collector;
    final Callable<? extends U> initialSupplier;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableCollect$CollectObserver */
    static final class CollectObserver<T, U> implements Observer<T>, Disposable {
        final Observer<? super U> actual;
        final BiConsumer<? super U, ? super T> collector;
        boolean done;

        /* renamed from: s */
        Disposable f3994s;

        /* renamed from: u */
        final U f3995u;

        CollectObserver(Observer<? super U> observer, U u, BiConsumer<? super U, ? super T> biConsumer) {
            this.actual = observer;
            this.collector = biConsumer;
            this.f3995u = u;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3994s, disposable)) {
                this.f3994s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f3994s.dispose();
        }

        public boolean isDisposed() {
            return this.f3994s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    this.collector.accept(this.f3995u, t);
                } catch (Throwable th) {
                    this.f3994s.dispose();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.actual.onError(th);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onNext(this.f3995u);
                this.actual.onComplete();
            }
        }
    }

    public ObservableCollect(ObservableSource<T> observableSource, Callable<? extends U> callable, BiConsumer<? super U, ? super T> biConsumer) {
        super(observableSource);
        this.initialSupplier = callable;
        this.collector = biConsumer;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super U> observer) {
        try {
            this.source.subscribe(new CollectObserver(observer, ObjectHelper.requireNonNull(this.initialSupplier.call(), "The initialSupplier returned a null value"), this.collector));
        } catch (Throwable th) {
            EmptyDisposable.error(th, observer);
        }
    }
}
