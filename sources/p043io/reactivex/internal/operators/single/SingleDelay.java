package p043io.reactivex.internal.operators.single;

import java.util.concurrent.TimeUnit;
import p043io.reactivex.Scheduler;
import p043io.reactivex.Single;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.SingleSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.SequentialDisposable;

/* renamed from: io.reactivex.internal.operators.single.SingleDelay */
public final class SingleDelay<T> extends Single<T> {
    final Scheduler scheduler;
    final SingleSource<? extends T> source;
    final long time;
    final TimeUnit unit;

    /* renamed from: io.reactivex.internal.operators.single.SingleDelay$Delay */
    final class Delay implements SingleObserver<T> {

        /* renamed from: s */
        final SingleObserver<? super T> f4116s;

        /* renamed from: sd */
        private final SequentialDisposable f4117sd;

        /* renamed from: io.reactivex.internal.operators.single.SingleDelay$Delay$OnError */
        final class OnError implements Runnable {

            /* renamed from: e */
            private final Throwable f4118e;

            OnError(Throwable th) {
                this.f4118e = th;
            }

            public void run() {
                Delay.this.f4116s.onError(this.f4118e);
            }
        }

        /* renamed from: io.reactivex.internal.operators.single.SingleDelay$Delay$OnSuccess */
        final class OnSuccess implements Runnable {
            private final T value;

            OnSuccess(T t) {
                this.value = t;
            }

            public void run() {
                Delay.this.f4116s.onSuccess(this.value);
            }
        }

        Delay(SequentialDisposable sequentialDisposable, SingleObserver<? super T> singleObserver) {
            this.f4117sd = sequentialDisposable;
            this.f4116s = singleObserver;
        }

        public void onSubscribe(Disposable disposable) {
            this.f4117sd.replace(disposable);
        }

        public void onSuccess(T t) {
            this.f4117sd.replace(SingleDelay.this.scheduler.scheduleDirect(new OnSuccess(t), SingleDelay.this.time, SingleDelay.this.unit));
        }

        public void onError(Throwable th) {
            this.f4117sd.replace(SingleDelay.this.scheduler.scheduleDirect(new OnError(th), 0, SingleDelay.this.unit));
        }
    }

    public SingleDelay(SingleSource<? extends T> singleSource, long j, TimeUnit timeUnit, Scheduler scheduler2) {
        this.source = singleSource;
        this.time = j;
        this.unit = timeUnit;
        this.scheduler = scheduler2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        singleObserver.onSubscribe(sequentialDisposable);
        this.source.subscribe(new Delay(sequentialDisposable, singleObserver));
    }
}
