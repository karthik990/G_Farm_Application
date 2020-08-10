package p043io.reactivex.internal.operators.observable;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Observable;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFromPublisher */
public final class ObservableFromPublisher<T> extends Observable<T> {
    final Publisher<? extends T> source;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFromPublisher$PublisherSubscriber */
    static final class PublisherSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final Observer<? super T> actual;

        /* renamed from: s */
        Subscription f4026s;

        PublisherSubscriber(Observer<? super T> observer) {
            this.actual = observer;
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void onError(Throwable th) {
            this.actual.onError(th);
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f4026s, subscription)) {
                this.f4026s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void dispose() {
            this.f4026s.cancel();
            this.f4026s = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.f4026s == SubscriptionHelper.CANCELLED;
        }
    }

    public ObservableFromPublisher(Publisher<? extends T> publisher) {
        this.source = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new PublisherSubscriber(observer));
    }
}
