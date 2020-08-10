package p043io.reactivex.subscribers;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.exceptions.CompositeException;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.internal.subscriptions.EmptySubscription;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.subscribers.SafeSubscriber */
public final class SafeSubscriber<T> implements FlowableSubscriber<T>, Subscription {
    final Subscriber<? super T> actual;
    boolean done;

    /* renamed from: s */
    Subscription f4192s;

    public SafeSubscriber(Subscriber<? super T> subscriber) {
        this.actual = subscriber;
    }

    public void onSubscribe(Subscription subscription) {
        if (SubscriptionHelper.validate(this.f4192s, subscription)) {
            this.f4192s = subscription;
            try {
                this.actual.onSubscribe(this);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(th, th));
            }
        }
    }

    public void onNext(T t) {
        if (!this.done) {
            if (this.f4192s == null) {
                onNextNoSubscription();
            } else if (t == null) {
                NullPointerException nullPointerException = new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
                try {
                    this.f4192s.cancel();
                    onError(nullPointerException);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    onError(new CompositeException(nullPointerException, th));
                }
            } else {
                try {
                    this.actual.onNext(t);
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    onError(new CompositeException(th, th2));
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void onNextNoSubscription() {
        this.done = true;
        NullPointerException nullPointerException = new NullPointerException("Subscription not set!");
        try {
            this.actual.onSubscribe(EmptySubscription.INSTANCE);
            try {
                this.actual.onError(nullPointerException);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(nullPointerException, th));
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            RxJavaPlugins.onError(new CompositeException(nullPointerException, th2));
        }
    }

    public void onError(Throwable th) {
        if (this.done) {
            RxJavaPlugins.onError(th);
            return;
        }
        this.done = true;
        if (this.f4192s == null) {
            NullPointerException nullPointerException = new NullPointerException("Subscription not set!");
            try {
                this.actual.onSubscribe(EmptySubscription.INSTANCE);
                try {
                    this.actual.onError(new CompositeException(th, nullPointerException));
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    RxJavaPlugins.onError(new CompositeException(th, nullPointerException, th2));
                }
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(new CompositeException(th, nullPointerException, th3));
            }
        } else {
            if (th == null) {
                th = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            try {
                this.actual.onError(th);
            } catch (Throwable th4) {
                Exceptions.throwIfFatal(th4);
                RxJavaPlugins.onError(new CompositeException(th, th4));
            }
        }
    }

    public void onComplete() {
        if (!this.done) {
            this.done = true;
            if (this.f4192s == null) {
                onCompleteNoSubscription();
                return;
            }
            try {
                this.actual.onComplete();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void onCompleteNoSubscription() {
        NullPointerException nullPointerException = new NullPointerException("Subscription not set!");
        try {
            this.actual.onSubscribe(EmptySubscription.INSTANCE);
            try {
                this.actual.onError(nullPointerException);
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(new CompositeException(nullPointerException, th));
            }
        } catch (Throwable th2) {
            Exceptions.throwIfFatal(th2);
            RxJavaPlugins.onError(new CompositeException(nullPointerException, th2));
        }
    }

    public void request(long j) {
        try {
            this.f4192s.request(j);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(new CompositeException(th, th));
        }
    }

    public void cancel() {
        try {
            this.f4192s.cancel();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
        }
    }
}
