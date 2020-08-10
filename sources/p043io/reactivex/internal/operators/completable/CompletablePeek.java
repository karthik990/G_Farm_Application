package p043io.reactivex.internal.operators.completable;

import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.CompletableSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.CompositeException;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Action;
import p043io.reactivex.functions.Consumer;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.disposables.EmptyDisposable;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletablePeek */
public final class CompletablePeek extends Completable {
    final Action onAfterTerminate;
    final Action onComplete;
    final Action onDispose;
    final Consumer<? super Throwable> onError;
    final Consumer<? super Disposable> onSubscribe;
    final Action onTerminate;
    final CompletableSource source;

    /* renamed from: io.reactivex.internal.operators.completable.CompletablePeek$CompletableObserverImplementation */
    final class CompletableObserverImplementation implements CompletableObserver, Disposable {
        final CompletableObserver actual;

        /* renamed from: d */
        Disposable f3813d;

        CompletableObserverImplementation(CompletableObserver completableObserver) {
            this.actual = completableObserver;
        }

        public void onSubscribe(Disposable disposable) {
            try {
                CompletablePeek.this.onSubscribe.accept(disposable);
                if (DisposableHelper.validate(this.f3813d, disposable)) {
                    this.f3813d = disposable;
                    this.actual.onSubscribe(this);
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                disposable.dispose();
                this.f3813d = DisposableHelper.DISPOSED;
                EmptyDisposable.error(th, this.actual);
            }
        }

        public void onError(Throwable th) {
            if (this.f3813d == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
                return;
            }
            try {
                CompletablePeek.this.onError.accept(th);
                CompletablePeek.this.onTerminate.run();
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                th = new CompositeException(th, th2);
            }
            this.actual.onError(th);
            doAfter();
        }

        public void onComplete() {
            if (this.f3813d != DisposableHelper.DISPOSED) {
                try {
                    CompletablePeek.this.onComplete.run();
                    CompletablePeek.this.onTerminate.run();
                    this.actual.onComplete();
                    doAfter();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.actual.onError(th);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void doAfter() {
            try {
                CompletablePeek.this.onAfterTerminate.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
        }

        public void dispose() {
            try {
                CompletablePeek.this.onDispose.run();
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                RxJavaPlugins.onError(th);
            }
            this.f3813d.dispose();
        }

        public boolean isDisposed() {
            return this.f3813d.isDisposed();
        }
    }

    public CompletablePeek(CompletableSource completableSource, Consumer<? super Disposable> consumer, Consumer<? super Throwable> consumer2, Action action, Action action2, Action action3, Action action4) {
        this.source = completableSource;
        this.onSubscribe = consumer;
        this.onError = consumer2;
        this.onComplete = action;
        this.onTerminate = action2;
        this.onAfterTerminate = action3;
        this.onDispose = action4;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new CompletableObserverImplementation(completableObserver));
    }
}
