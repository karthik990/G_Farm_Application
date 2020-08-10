package p043io.reactivex.internal.operators.maybe;

import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.MaybeSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.CompositeException;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeDelayOtherPublisher */
public final class MaybeDelayOtherPublisher<T, U> extends AbstractMaybeWithUpstream<T, T> {
    final Publisher<U> other;

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeDelayOtherPublisher$DelayMaybeObserver */
    static final class DelayMaybeObserver<T, U> implements MaybeObserver<T>, Disposable {

        /* renamed from: d */
        Disposable f3948d;
        final OtherSubscriber<T> other;
        final Publisher<U> otherSource;

        DelayMaybeObserver(MaybeObserver<? super T> maybeObserver, Publisher<U> publisher) {
            this.other = new OtherSubscriber<>(maybeObserver);
            this.otherSource = publisher;
        }

        public void dispose() {
            this.f3948d.dispose();
            this.f3948d = DisposableHelper.DISPOSED;
            SubscriptionHelper.cancel(this.other);
        }

        public boolean isDisposed() {
            return SubscriptionHelper.isCancelled((Subscription) this.other.get());
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3948d, disposable)) {
                this.f3948d = disposable;
                this.other.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.f3948d = DisposableHelper.DISPOSED;
            this.other.value = t;
            subscribeNext();
        }

        public void onError(Throwable th) {
            this.f3948d = DisposableHelper.DISPOSED;
            this.other.error = th;
            subscribeNext();
        }

        public void onComplete() {
            this.f3948d = DisposableHelper.DISPOSED;
            subscribeNext();
        }

        /* access modifiers changed from: 0000 */
        public void subscribeNext() {
            this.otherSource.subscribe(this.other);
        }
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeDelayOtherPublisher$OtherSubscriber */
    static final class OtherSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
        private static final long serialVersionUID = -1215060610805418006L;
        final MaybeObserver<? super T> actual;
        Throwable error;
        T value;

        OtherSubscriber(MaybeObserver<? super T> maybeObserver) {
            this.actual = maybeObserver;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object obj) {
            Subscription subscription = (Subscription) get();
            if (subscription != SubscriptionHelper.CANCELLED) {
                lazySet(SubscriptionHelper.CANCELLED);
                subscription.cancel();
                onComplete();
            }
        }

        public void onError(Throwable th) {
            Throwable th2 = this.error;
            if (th2 == null) {
                this.actual.onError(th);
                return;
            }
            this.actual.onError(new CompositeException(th2, th));
        }

        public void onComplete() {
            Throwable th = this.error;
            if (th != null) {
                this.actual.onError(th);
                return;
            }
            T t = this.value;
            if (t != null) {
                this.actual.onSuccess(t);
            } else {
                this.actual.onComplete();
            }
        }
    }

    public MaybeDelayOtherPublisher(MaybeSource<T> maybeSource, Publisher<U> publisher) {
        super(maybeSource);
        this.other = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new DelayMaybeObserver(maybeObserver, this.other));
    }
}
