package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Maybe;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableLastMaybe */
public final class FlowableLastMaybe<T> extends Maybe<T> {
    final Publisher<T> source;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableLastMaybe$LastSubscriber */
    static final class LastSubscriber<T> implements FlowableSubscriber<T>, Disposable {
        final MaybeObserver<? super T> actual;
        T item;

        /* renamed from: s */
        Subscription f3874s;

        LastSubscriber(MaybeObserver<? super T> maybeObserver) {
            this.actual = maybeObserver;
        }

        public void dispose() {
            this.f3874s.cancel();
            this.f3874s = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.f3874s == SubscriptionHelper.CANCELLED;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3874s, subscription)) {
                this.f3874s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            this.item = t;
        }

        public void onError(Throwable th) {
            this.f3874s = SubscriptionHelper.CANCELLED;
            this.item = null;
            this.actual.onError(th);
        }

        public void onComplete() {
            this.f3874s = SubscriptionHelper.CANCELLED;
            T t = this.item;
            if (t != null) {
                this.item = null;
                this.actual.onSuccess(t);
                return;
            }
            this.actual.onComplete();
        }
    }

    public FlowableLastMaybe(Publisher<T> publisher) {
        this.source = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new LastSubscriber(maybeObserver));
    }
}
