package p043io.reactivex.internal.operators.completable;

import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.CompletableSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.CompositeException;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Predicate;

/* renamed from: io.reactivex.internal.operators.completable.CompletableOnErrorComplete */
public final class CompletableOnErrorComplete extends Completable {
    final Predicate<? super Throwable> predicate;
    final CompletableSource source;

    /* renamed from: io.reactivex.internal.operators.completable.CompletableOnErrorComplete$OnError */
    final class OnError implements CompletableObserver {

        /* renamed from: s */
        private final CompletableObserver f3812s;

        OnError(CompletableObserver completableObserver) {
            this.f3812s = completableObserver;
        }

        public void onComplete() {
            this.f3812s.onComplete();
        }

        public void onError(Throwable th) {
            try {
                if (CompletableOnErrorComplete.this.predicate.test(th)) {
                    this.f3812s.onComplete();
                } else {
                    this.f3812s.onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.f3812s.onError(new CompositeException(th, th2));
            }
        }

        public void onSubscribe(Disposable disposable) {
            this.f3812s.onSubscribe(disposable);
        }
    }

    public CompletableOnErrorComplete(CompletableSource completableSource, Predicate<? super Throwable> predicate2) {
        this.source = completableSource;
        this.predicate = predicate2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        this.source.subscribe(new OnError(completableObserver));
    }
}
