package p043io.reactivex.internal.operators.flowable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Notification;
import p043io.reactivex.internal.util.BlockingHelper;
import p043io.reactivex.internal.util.ExceptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;
import p043io.reactivex.subscribers.DisposableSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.BlockingFlowableLatest */
public final class BlockingFlowableLatest<T> implements Iterable<T> {
    final Publisher<? extends T> source;

    /* renamed from: io.reactivex.internal.operators.flowable.BlockingFlowableLatest$LatestSubscriberIterator */
    static final class LatestSubscriberIterator<T> extends DisposableSubscriber<Notification<T>> implements Iterator<T> {
        Notification<T> iteratorNotification;
        final Semaphore notify = new Semaphore(0);
        final AtomicReference<Notification<T>> value = new AtomicReference<>();

        public void onComplete() {
        }

        LatestSubscriberIterator() {
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
                Notification<T> notification2 = this.iteratorNotification;
                if ((notification2 == null || notification2.isOnNext()) && this.iteratorNotification == null) {
                    try {
                        BlockingHelper.verifyNonBlocking();
                        this.notify.acquire();
                        Notification<T> notification3 = (Notification) this.value.getAndSet(null);
                        this.iteratorNotification = notification3;
                        if (notification3.isOnError()) {
                            throw ExceptionHelper.wrapOrThrow(notification3.getError());
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
            if (!hasNext() || !this.iteratorNotification.isOnNext()) {
                throw new NoSuchElementException();
            }
            T value2 = this.iteratorNotification.getValue();
            this.iteratorNotification = null;
            return value2;
        }

        public void remove() {
            throw new UnsupportedOperationException("Read-only iterator.");
        }
    }

    public BlockingFlowableLatest(Publisher<? extends T> publisher) {
        this.source = publisher;
    }

    public Iterator<T> iterator() {
        LatestSubscriberIterator latestSubscriberIterator = new LatestSubscriberIterator();
        Flowable.fromPublisher(this.source).materialize().subscribe((FlowableSubscriber<? super T>) latestSubscriberIterator);
        return latestSubscriberIterator;
    }
}
