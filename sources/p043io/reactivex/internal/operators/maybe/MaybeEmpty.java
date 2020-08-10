package p043io.reactivex.internal.operators.maybe;

import p043io.reactivex.Maybe;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.internal.disposables.EmptyDisposable;
import p043io.reactivex.internal.fuseable.ScalarCallable;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeEmpty */
public final class MaybeEmpty extends Maybe<Object> implements ScalarCallable<Object> {
    public static final MaybeEmpty INSTANCE = new MaybeEmpty();

    public Object call() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super Object> maybeObserver) {
        EmptyDisposable.complete(maybeObserver);
    }
}
