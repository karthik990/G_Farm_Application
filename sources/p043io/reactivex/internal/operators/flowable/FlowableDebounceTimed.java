package p043io.reactivex.internal.operators.flowable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableSubscriber;
import p043io.reactivex.Scheduler;
import p043io.reactivex.Scheduler.Worker;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.MissingBackpressureException;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.disposables.SequentialDisposable;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.internal.util.BackpressureHelper;
import p043io.reactivex.plugins.RxJavaPlugins;
import p043io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableDebounceTimed */
public final class FlowableDebounceTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    final Scheduler scheduler;
    final long timeout;
    final TimeUnit unit;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDebounceTimed$DebounceEmitter */
    static final class DebounceEmitter<T> extends AtomicReference<Disposable> implements Runnable, Disposable {
        private static final long serialVersionUID = 6812032969491025141L;
        final long idx;
        final AtomicBoolean once = new AtomicBoolean();
        final DebounceTimedSubscriber<T> parent;
        final T value;

        DebounceEmitter(T t, long j, DebounceTimedSubscriber<T> debounceTimedSubscriber) {
            this.value = t;
            this.idx = j;
            this.parent = debounceTimedSubscriber;
        }

        public void run() {
            emit();
        }

        /* access modifiers changed from: 0000 */
        public void emit() {
            if (this.once.compareAndSet(false, true)) {
                this.parent.emit(this.idx, this.value, this);
            }
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return get() == DisposableHelper.DISPOSED;
        }

        public void setResource(Disposable disposable) {
            DisposableHelper.replace(this, disposable);
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDebounceTimed$DebounceTimedSubscriber */
    static final class DebounceTimedSubscriber<T> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -9102637559663639004L;
        final Subscriber<? super T> actual;
        boolean done;
        volatile long index;

        /* renamed from: s */
        Subscription f3843s;
        final long timeout;
        final SequentialDisposable timer = new SequentialDisposable();
        final TimeUnit unit;
        final Worker worker;

        DebounceTimedSubscriber(Subscriber<? super T> subscriber, long j, TimeUnit timeUnit, Worker worker2) {
            this.actual = subscriber;
            this.timeout = j;
            this.unit = timeUnit;
            this.worker = worker2;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.f3843s, subscription)) {
                this.f3843s = subscription;
                this.actual.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                long j = this.index + 1;
                this.index = j;
                Disposable disposable = (Disposable) this.timer.get();
                if (disposable != null) {
                    disposable.dispose();
                }
                DebounceEmitter debounceEmitter = new DebounceEmitter(t, j, this);
                if (this.timer.replace(debounceEmitter)) {
                    debounceEmitter.setResource(this.worker.schedule(debounceEmitter, this.timeout, this.unit));
                }
            }
        }

        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.actual.onError(th);
            this.worker.dispose();
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                Disposable disposable = (Disposable) this.timer.get();
                if (!DisposableHelper.isDisposed(disposable)) {
                    DebounceEmitter debounceEmitter = (DebounceEmitter) disposable;
                    if (debounceEmitter != null) {
                        debounceEmitter.emit();
                    }
                    DisposableHelper.dispose(this.timer);
                    this.actual.onComplete();
                    this.worker.dispose();
                }
            }
        }

        public void request(long j) {
            if (SubscriptionHelper.validate(j)) {
                BackpressureHelper.add(this, j);
            }
        }

        public void cancel() {
            this.f3843s.cancel();
            this.worker.dispose();
        }

        /* access modifiers changed from: 0000 */
        public void emit(long j, T t, DebounceEmitter<T> debounceEmitter) {
            if (j != this.index) {
                return;
            }
            if (get() != 0) {
                this.actual.onNext(t);
                BackpressureHelper.produced(this, 1);
                debounceEmitter.dispose();
                return;
            }
            cancel();
            this.actual.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
        }
    }

    public FlowableDebounceTimed(Flowable<T> flowable, long j, TimeUnit timeUnit, Scheduler scheduler2) {
        super(flowable);
        this.timeout = j;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> subscriber) {
        Flowable flowable = this.source;
        DebounceTimedSubscriber debounceTimedSubscriber = new DebounceTimedSubscriber(new SerializedSubscriber(subscriber), this.timeout, this.unit, this.scheduler.createWorker());
        flowable.subscribe((FlowableSubscriber<? super T>) debounceTimedSubscriber);
    }
}
