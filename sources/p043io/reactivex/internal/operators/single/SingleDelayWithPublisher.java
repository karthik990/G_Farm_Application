package p043io.reactivex.internal.operators.single;

import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.observers.ResumeSingleObserver;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.single.SingleDelayWithPublisher */
public final class SingleDelayWithPublisher<T, U> extends Single<T> {
    final Publisher<U> other;
    final SingleSource<T> source;

    /* renamed from: io.reactivex.internal.operators.single.SingleDelayWithPublisher$OtherSubscriber */
    static final class OtherSubscriber<T, U> extends AtomicReference<Disposable> implements FlowableSubscriber<U>, Disposable {
        private static final long serialVersionUID = -8565274649390031272L;
        final SingleObserver<? super T> actual;
        boolean done;

        /* renamed from: s */
        Subscription f4119s;
        final SingleSource<T> source;

        OtherSubscriber(SingleObserver<? super T> singleObserver, SingleSource<T> singleSource) {
            this.actual = singleObserver;
            this.source = singleSource;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f4119s, subscription)) {
                this.f4119s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(U u) {
            this.f4119s.cancel();
            onComplete();
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
                this.source.subscribe(new ResumeSingleObserver(this, this.actual));
            }
        }

        public void dispose() {
            this.f4119s.cancel();
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }
    }

    public SingleDelayWithPublisher(SingleSource<T> singleSource, Publisher<U> publisher) {
        this.source = singleSource;
        this.other = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.other.subscribe(new OtherSubscriber(singleObserver, this.source));
    }
}
