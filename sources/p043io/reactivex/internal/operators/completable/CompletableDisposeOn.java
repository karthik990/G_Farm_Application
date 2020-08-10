package p043io.reactivex.internal.operators.completable;

import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.CompletableSource;
import p043io.reactivex.Scheduler;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletableDisposeOn */
public final class CompletableDisposeOn extends Completable {
    final Scheduler scheduler;
    final CompletableSource source;

    /* renamed from: io.reactivex.internal.operators.completable.CompletableDisposeOn$CompletableObserverImplementation */
    static final class CompletableObserverImplementation implements CompletableObserver, Disposable, Runnable {

        /* renamed from: d */
        Disposable f3803d;
        volatile boolean disposed;

        /* renamed from: s */
        final CompletableObserver f3804s;
        final Scheduler scheduler;

        CompletableObserverImplementation(CompletableObserver completableObserver, Scheduler scheduler2) {
            this.f3804s = completableObserver;
            this.scheduler = scheduler2;
        }

        public void onComplete() {
            if (!this.disposed) {
                this.f3804s.onComplete();
            }
        }

        public void onError(Throwable th) {
            if (this.disposed) {
                RxJavaPlugins.onError(th);
            } else {
                this.f3804s.onError(th);
            }
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3803d, disposable)) {
                this.f3803d = disposable;
                this.f3804s.onSubscribe(this);
            }
        }

        public void dispose() {
            this.disposed = true;
            this.scheduler.scheduleDirect(this);
        }

        public boolean isDisposed() {
            return this.disposed;
        }

        public void run() {
            this.f3803d.dispose();
            this.f3803d = DisposableHelper.DISPOSED;
        }
    }

    public CompletableDisposeOn(CompletableSource completableSource, Scheduler scheduler2) {
        this.source = completableSource;
        this.scheduler = scheduler2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new CompletableObserverImplementation(completableObserver, this.scheduler));
    }
}
