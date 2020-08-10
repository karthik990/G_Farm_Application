package p043io.reactivex.internal.operators.observable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.Notification;
import p043io.reactivex.Observable;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.internal.util.BlockingHelper;
import p043io.reactivex.internal.util.ExceptionHelper;
import p043io.reactivex.observers.DisposableObserver;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.BlockingObservableLatest */
public final class BlockingObservableLatest<T> implements Iterable<T> {
    final ObservableSource<T> source;

    /* renamed from: io.reactivex.internal.operators.observable.BlockingObservableLatest$BlockingObservableLatestIterator */
    static final class BlockingObservableLatestIterator<T> extends DisposableObserver<Notification<T>> implements Iterator<T> {
        Notification<T> iteratorNotification;
        final Semaphore notify = new Semaphore(0);
        final AtomicReference<Notification<T>> value = new AtomicReference<>();

        public void onComplete() {
        }

        BlockingObservableLatestIterator() {
        }

        public void onNext(Notification<T> notification) {
            if (this.value.getAndSet(notification) == null) {
                this.notify.release();
            }
        }

        public void onError(Throwable th) {
            RxJavaPlugins.onError(th);
        }

        public boolean hasNext() {
            Notification<T> notification = this.iteratorNotification;
            if (notification == null || !notification.isOnError()) {
                if (this.iteratorNotification == null) {
                    try {
                        BlockingHelper.verifyNonBlocking();
                        this.notify.acquire();
                        Notification<T> notification2 = (Notification) this.value.getAndSet(null);
                        this.iteratorNotification = notification2;
                        if (notification2.isOnError()) {
                            throw ExceptionHelper.wrapOrThrow(notification2.getError());
                        }
                    } catch (InterruptedException e) {
                        dispose();
                        this.iteratorNotification = Notification.createOnError(e);
                        throw ExceptionHelper.wrapOrThrow(e);
                    }
                }
                return this.iteratorNotification.isOnNext();
            }
            throw ExceptionHelper.wrapOrThrow(this.iteratorNotification.getError());
        }

        public T next() {
            if (hasNext()) {
                T value2 = this.iteratorNotification.getValue();
                this.iteratorNotification = null;
                return value2;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException("Read-only iterator.");
        }
    }

    public BlockingObservableLatest(ObservableSource<T> observableSource) {
        this.source = observableSource;
    }

    public Iterator<T> iterator() {
        BlockingObservableLatestIterator blockingObservableLatestIterator = new BlockingObservableLatestIterator();
        Observable.wrap(this.source).materialize().subscribe((Observer<? super T>) blockingObservableLatestIterator);
        return blockingObservableLatestIterator;
    }
}
