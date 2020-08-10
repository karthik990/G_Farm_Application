package p043io.reactivex.internal.operators.completable;

import p043io.reactivex.Completable;
import p043io.reactivex.CompletableObserver;
import p043io.reactivex.CompletableSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.CompositeException;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Function;
import p043io.reactivex.internal.disposables.SequentialDisposable;

/* renamed from: io.reactivex.internal.operators.completable.CompletableResumeNext */
public final class CompletableResumeNext extends Completable {
    final Function<? super Throwable, ? extends CompletableSource> errorMapper;
    final CompletableSource source;

    /* renamed from: io.reactivex.internal.operators.completable.CompletableResumeNext$ResumeNext */
    final class ResumeNext implements CompletableObserver {

        /* renamed from: s */
        final CompletableObserver f3814s;

        /* renamed from: sd */
        final SequentialDisposable f3815sd;

        /* renamed from: io.reactivex.internal.operators.completable.CompletableResumeNext$ResumeNext$OnErrorObserver */
        final class OnErrorObserver implements CompletableObserver {
            OnErrorObserver() {
            }

            public void onComplete() {
                ResumeNext.this.f3814s.onComplete();
            }

            public void onError(Throwable th) {
                ResumeNext.this.f3814s.onError(th);
            }

            public void onSubscribe(Disposable disposable) {
                ResumeNext.this.f3815sd.update(disposable);
            }
        }

        ResumeNext(CompletableObserver completableObserver, SequentialDisposable sequentialDisposable) {
            this.f3814s = completableObserver;
            this.f3815sd = sequentialDisposable;
        }

        public void onComplete() {
            this.f3814s.onComplete();
        }

        public void onError(Throwable th) {
            try {
                CompletableSource completableSource = (CompletableSource) CompletableResumeNext.this.errorMapper.apply(th);
                if (completableSource == null) {
                    NullPointerException nullPointerException = new NullPointerException("The CompletableConsumable returned is null");
                    nullPointerException.initCause(th);
                    this.f3814s.onError(nullPointerException);
                    return;
                }
                completableSource.subscribe(new OnErrorObserver());
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.f3814s.onError(new CompositeException(th2, th));
            }
        }

        public void onSubscribe(Disposable disposable) {
            this.f3815sd.update(disposable);
        }
    }

    public CompletableResumeNext(CompletableSource completableSource, Function<? super Throwable, ? extends CompletableSource> function) {
        this.source = completableSource;
        this.errorMapper = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver completableObserver) {
        SequentialDisposable sequentialDisposable = new SequentialDisposable();
        completableObserver.onSubscribe(sequentialDisposable);
        this.source.subscribe(new ResumeNext(completableObserver, sequentialDisposable));
    }
}
