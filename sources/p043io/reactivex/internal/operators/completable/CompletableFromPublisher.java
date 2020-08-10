package p043io.reactivex.internal.operators.completable;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.completable.CompletableFromPublisher */
public final class CompletableFromPublisher<T> extends Completable {
    final Publisher<T> flowable;

    /* renamed from: io.reactivex.internal.operators.completable.CompletableFromPublisher$FromPublisherSubscriber */
    static final class FromPublisherSubscriber<T> implements FlowableSubscriber<T>, Disposable {

        /* renamed from: cs */
        final CompletableObserver f3807cs;

        /* renamed from: s */
        Subscription f3808s;

        public void onNext(T t) {
        }

        FromPublisherSubscriber(CompletableObserver completableObserver) {
            this.f3807cs = completableObserver;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3808s, subscription)) {
                this.f3808s = subscription;
                this.f3807cs.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onError(Throwable th) {
            this.f3807cs.onError(th);
        }

        public void onComplete() {
            this.f3807cs.onComplete();
        }

        public void dispose() {
            this.f3808s.cancel();
            this.f3808s = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.f3808s == SubscriptionHelper.CANCELLED;
        }
    }

    public CompletableFromPublisher(Publisher<T> publisher) {
        this.flowable = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.flowable.subscribe(new FromPublisherSubscriber(completableObserver));
    }
}
