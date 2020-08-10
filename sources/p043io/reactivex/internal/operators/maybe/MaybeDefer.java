package p043io.reactivex.internal.operators.maybe;

import java.util.concurrent.Callable;
import p043io.reactivex.Maybe;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.MaybeSource;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.internal.disposables.EmptyDisposable;
import p043io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeDefer */
public final class MaybeDefer<T> extends Maybe<T> {
    final Callable<? extends MaybeSource<? extends T>> maybeSupplier;

    public MaybeDefer(Callable<? extends MaybeSource<? extends T>> callable) {
        this.maybeSupplier = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        try {
            ((MaybeSource) ObjectHelper.requireNonNull(this.maybeSupplier.call(), "The maybeSupplier returned a null MaybeSource")).subscribe(maybeObserver);
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, maybeObserver);
        }
    }
}
