package p043io.reactivex.internal.operators.completable;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.CompletableSource;
import p043io.reactivex.disposables.CompositeDisposable;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletableMergeIterable */
public final class CompletableMergeIterable extends Completable {
    final Iterable<? extends CompletableSource> sources;

    /* renamed from: io.reactivex.internal.operators.completable.CompletableMergeIterable$MergeCompletableObserver */
    static final class MergeCompletableObserver extends AtomicBoolean implements CompletableObserver {
        private static final long serialVersionUID = -7730517613164279224L;
        final CompletableObserver actual;
        final CompositeDisposable set;
        final AtomicInteger wip;

        MergeCompletableObserver(CompletableObserver completableObserver, CompositeDisposable compositeDisposable, AtomicInteger atomicInteger) {
            this.actual = completableObserver;
            this.set = compositeDisposable;
            this.wip = atomicInteger;
        }

        public void onSubscribe(Disposable disposable) {
            this.set.add(disposable);
        }

        public void onError(Throwable th) {
            this.set.dispose();
            if (compareAndSet(false, true)) {
                this.actual.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            if (this.wip.decrementAndGet() == 0 && compareAndSet(false, true)) {
                this.actual.onComplete();
            }
        }
    }

    public CompletableMergeIterable(Iterable<? extends CompletableSource> iterable) {
        this.sources = iterable;
    }

    public void subscribeActual(CompletableObserver completableObserver) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        completableObserver.onSubscribe(compositeDisposable);
        try {
            Iterator it = (Iterator) ObjectHelper.requireNonNull(this.sources.iterator(), "The source iterator returned is null");
            AtomicInteger atomicInteger = new AtomicInteger(1);
            MergeCompletableObserver mergeCompletableObserver = new MergeCompletableObserver(completableObserver, compositeDisposable, atomicInteger);
            while (!compositeDisposable.isDisposed()) {
                try {
                    if (!it.hasNext()) {
                        mergeCompletableObserver.onComplete();
                        return;
                    } else if (!compositeDisposable.isDisposed()) {
                        try {
                            CompletableSource completableSource = (CompletableSource) ObjectHelper.requireNonNull(it.next(), "The iterator returned a null CompletableSource");
                            if (!compositeDisposable.isDisposed()) {
                                atomicInteger.getAndIncrement();
                                completableSource.subscribe(mergeCompletableObserver);
                            } else {
                                return;
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            compositeDisposable.dispose();
                            mergeCompletableObserver.onError(th);
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    compositeDisposable.dispose();
                    mergeCompletableObserver.onError(th2);
                    return;
                }
            }
        } catch (Throwable th3) {
            Exceptions.throwIfFatal(th3);
            completableObserver.onError(th3);
        }
    }
}
