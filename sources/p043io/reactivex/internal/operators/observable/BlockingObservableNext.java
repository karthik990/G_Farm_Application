package p043io.reactivex.internal.operators.observable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import p043io.reactivex.Notification;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.internal.util.BlockingHelper;
import p043io.reactivex.internal.util.ExceptionHelper;
import p043io.reactivex.observers.DisposableObserver;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.BlockingObservableNext */
public final class BlockingObservableNext<T> implements Iterable<T> {
    final ObservableSource<T> source;

    /* renamed from: io.reactivex.internal.operators.observable.BlockingObservableNext$NextIterator */
    static final class NextIterator<T> implements Iterator<T> {
        private Throwable error;
        private boolean hasNext = true;
        private boolean isNextConsumed = true;
        private final ObservableSource<T> items;
        private T next;
        private final NextObserver<T> observer;
        private boolean started;

        NextIterator(ObservableSource<T> observableSource, NextObserver<T> nextObserver) {
            this.items = observableSource;
            this.observer = nextObserver;
        }

        public boolean hasNext() {
            Throwable th = this.error;
            if (th == null) {
                boolean z = false;
                if (!this.hasNext) {
                    return false;
                }
                if (!this.isNextConsumed || moveToNext()) {
                    z = true;
                }
                return z;
            }
            throw ExceptionHelper.wrapOrThrow(th);
        }

        private boolean moveToNext() {
            if (!this.started) {
                this.started = true;
                this.observer.setWaiting();
                new ObservableMaterialize(this.items).subscribe((Observer<? super T>) this.observer);
            }
            try {
                Notification takeNext = this.observer.takeNext();
                if (takeNext.isOnNext()) {
                    this.isNextConsumed = false;
                    this.next = takeNext.getValue();
                    return true;
                }
                this.hasNext = false;
                if (takeNext.isOnComplete()) {
                    return false;
                }
                this.error = takeNext.getError();
                throw ExceptionHelper.wrapOrThrow(this.error);
            } catch (InterruptedException e) {
                this.observer.dispose();
                this.error = e;
                throw ExceptionHelper.wrapOrThrow(e);
            }
        }

        public T next() {
            Throwable th = this.error;
            if (th != null) {
                throw ExceptionHelper.wrapOrThrow(th);
            } else if (hasNext()) {
                this.isNextConsumed = true;
                return this.next;
            } else {
                throw new NoSuchElementException("No more elements");
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("Read only iterator");
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.BlockingObservableNext$NextObserver */
    static final class NextObserver<T> extends DisposableObserver<Notification<T>> {
        private final BlockingQueue<Notification<T>> buf = new ArrayBlockingQueue(1);
        final AtomicInteger waiting = new AtomicInteger();

        public void onComplete() {
        }

        NextObserver() {
        }

        public void onError(Throwable th) {
            RxJavaPlugins.onError(th);
        }

        public void onNext(Notification<T> notification) {
            if (this.waiting.getAndSet(0) == 1 || !notification.isOnNext()) {
                while (!this.buf.offer(notification)) {
                    Notification<T> notification2 = (Notification) this.buf.poll();
                    if (notification2 != null && !notification2.isOnNext()) {
                        notification = notification2;
                    }
                }
            }
        }

        public Notification<T> takeNext() throws InterruptedException {
            setWaiting();
            BlockingHelper.verifyNonBlocking();
            return (Notification) this.buf.take();
        }

        /* access modifiers changed from: 0000 */
        public void setWaiting() {
            this.waiting.set(1);
        }
    }

    public BlockingObservableNext(ObservableSource<T> observableSource) {
        this.source = observableSource;
    }

    public Iterator<T> iterator() {
        return new NextIterator(this.source, new NextObserver());
    }
}
