package p043io.reactivex.internal.operators.observable;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.disposables.EmptyDisposable;
import p043io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableBuffer */
public final class ObservableBuffer<T, U extends Collection<? super T>> extends AbstractObservableWithUpstream<T, U> {
    final Callable<U> bufferSupplier;
    final int count;
    final int skip;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableBuffer$BufferExactObserver */
    static final class BufferExactObserver<T, U extends Collection<? super T>> implements Observer<T>, Disposable {
        final Observer<? super U> actual;
        U buffer;
        final Callable<U> bufferSupplier;
        final int count;

        /* renamed from: s */
        Disposable f3983s;
        int size;

        BufferExactObserver(Observer<? super U> observer, int i, Callable<U> callable) {
            this.actual = observer;
            this.count = i;
            this.bufferSupplier = callable;
        }

        /* access modifiers changed from: 0000 */
        public boolean createBuffer() {
            try {
                this.buffer = (Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "Empty buffer supplied");
                return true;
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                this.buffer = null;
                Disposable disposable = this.f3983s;
                if (disposable == null) {
                    EmptyDisposable.error(th, this.actual);
                } else {
                    disposable.dispose();
                    this.actual.onError(th);
                }
                return false;
            }
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3983s, disposable)) {
                this.f3983s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f3983s.dispose();
        }

        public boolean isDisposed() {
            return this.f3983s.isDisposed();
        }

        public void onNext(T t) {
            U u = this.buffer;
            if (u != null) {
                u.add(t);
                int i = this.size + 1;
                this.size = i;
                if (i >= this.count) {
                    this.actual.onNext(u);
                    this.size = 0;
                    createBuffer();
                }
            }
        }

        public void onError(Throwable th) {
            this.buffer = null;
            this.actual.onError(th);
        }

        public void onComplete() {
            U u = this.buffer;
            this.buffer = null;
            if (u != null && !u.isEmpty()) {
                this.actual.onNext(u);
            }
            this.actual.onComplete();
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableBuffer$BufferSkipObserver */
    static final class BufferSkipObserver<T, U extends Collection<? super T>> extends AtomicBoolean implements Observer<T>, Disposable {
        private static final long serialVersionUID = -8223395059921494546L;
        final Observer<? super U> actual;
        final Callable<U> bufferSupplier;
        final ArrayDeque<U> buffers = new ArrayDeque<>();
        final int count;
        long index;

        /* renamed from: s */
        Disposable f3984s;
        final int skip;

        BufferSkipObserver(Observer<? super U> observer, int i, int i2, Callable<U> callable) {
            this.actual = observer;
            this.count = i;
            this.skip = i2;
            this.bufferSupplier = callable;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3984s, disposable)) {
                this.f3984s = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f3984s.dispose();
        }

        public boolean isDisposed() {
            return this.f3984s.isDisposed();
        }

        public void onNext(T t) {
            long j = this.index;
            this.index = 1 + j;
            if (j % ((long) this.skip) == 0) {
                try {
                    this.buffers.offer((Collection) ObjectHelper.requireNonNull(this.bufferSupplier.call(), "The bufferSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources."));
                } catch (Throwable th) {
                    this.buffers.clear();
                    this.f3984s.dispose();
                    this.actual.onError(th);
                    return;
                }
            }
            Iterator it = this.buffers.iterator();
            while (it.hasNext()) {
                Collection collection = (Collection) it.next();
                collection.add(t);
                if (this.count <= collection.size()) {
                    it.remove();
                    this.actual.onNext(collection);
                }
            }
        }

        public void onError(Throwable th) {
            this.buffers.clear();
            this.actual.onError(th);
        }

        public void onComplete() {
            while (!this.buffers.isEmpty()) {
                this.actual.onNext(this.buffers.poll());
            }
            this.actual.onComplete();
        }
    }

    public ObservableBuffer(ObservableSource<T> observableSource, int i, int i2, Callable<U> callable) {
        super(observableSource);
        this.count = i;
        this.skip = i2;
        this.bufferSupplier = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super U> observer) {
        int i = this.skip;
        int i2 = this.count;
        if (i == i2) {
            BufferExactObserver bufferExactObserver = new BufferExactObserver(observer, i2, this.bufferSupplier);
            if (bufferExactObserver.createBuffer()) {
                this.source.subscribe(bufferExactObserver);
                return;
            }
            return;
        }
        this.source.subscribe(new BufferSkipObserver(observer, this.count, this.skip, this.bufferSupplier));
    }
}
