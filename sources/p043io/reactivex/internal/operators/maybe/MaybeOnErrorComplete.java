package p043io.reactivex.internal.operators.maybe;

import p043io.reactivex.MaybeObserver;
import p043io.reactivex.MaybeSource;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.CompositeException;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.functions.Predicate;
import p043io.reactivex.internal.disposables.DisposableHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeOnErrorComplete */
public final class MaybeOnErrorComplete<T> extends AbstractMaybeWithUpstream<T, T> {
    final Predicate<? super Throwable> predicate;

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeOnErrorComplete$OnErrorCompleteMaybeObserver */
    static final class OnErrorCompleteMaybeObserver<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super T> actual;

        /* renamed from: d */
        Disposable f3970d;
        final Predicate<? super Throwable> predicate;

        OnErrorCompleteMaybeObserver(MaybeObserver<? super T> maybeObserver, Predicate<? super Throwable> predicate2) {
            this.actual = maybeObserver;
            this.predicate = predicate2;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.f3970d, disposable)) {
                this.f3970d = disposable;
                this.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.actual.onSuccess(t);
        }

        public void onError(Throwable th) {
            try {
                if (this.predicate.test(th)) {
                    this.actual.onComplete();
                } else {
                    this.actual.onError(th);
                }
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.actual.onError(new CompositeException(th, th2));
            }
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void dispose() {
            this.f3970d.dispose();
        }

        public boolean isDisposed() {
            return this.f3970d.isDisposed();
        }
    }

    public MaybeOnErrorComplete(MaybeSource<T> maybeSource, Predicate<? super Throwable> predicate2) {
        super(maybeSource);
        this.predicate = predicate2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new OnErrorCompleteMaybeObserver(maybeObserver, this.predicate));
    }
}
