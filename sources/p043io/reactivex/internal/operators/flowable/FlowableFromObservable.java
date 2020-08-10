package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.Observable;
import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableFromObservable */
public final class FlowableFromObservable<T> extends Flowable<T> {
    private final Observable<T> upstream;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableFromObservable$SubscriberObserver */
    static class SubscriberObserver<T> implements Observer<T>, Subscription {

        /* renamed from: d */
        private Disposable f3867d;

        /* renamed from: s */
        private final Subscriber<? super T> f3868s;

        public void request(long j) {
        }

        SubscriberObserver(Subscriber<? super T> subscriber) {
            this.f3868s = subscriber;
        }

        public void onComplete() {
            this.f3868s.onComplete();
        }

        public void onError(Throwable th) {
            this.f3868s.onError(th);
        }

        public void onNext(T t) {
            this.f3868s.onNext(t);
        }

        public void onSubscribe(Disposable disposable) {
            this.f3867d = disposable;
            this.f3868s.onSubscribe(this);
        }

        public void cancel() {
            this.f3867d.dispose();
        }
    }

    public FlowableFromObservable(Observable<T> observable) {
        this.upstream = observable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        this.upstream.subscribe((Observer<? super T>) new SubscriberObserver<Object>(subscriber));
    }
}
