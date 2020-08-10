package p043io.reactivex.observers;

import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.MaybeObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.util.EndConsumerHelper;

/* renamed from: io.reactivex.observers.DisposableMaybeObserver */
public abstract class DisposableMaybeObserver<T> implements MaybeObserver<T>, Disposable {

    /* renamed from: s */
    final AtomicReference<Disposable> f4178s = new AtomicReference<>();

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    public final void onSubscribe(Disposable disposable) {
        if (EndConsumerHelper.setOnce(this.f4178s, disposable, getClass())) {
            onStart();
        }
    }

    public final boolean isDisposed() {
        return this.f4178s.get() == DisposableHelper.DISPOSED;
    }

    public final void dispose() {
        DisposableHelper.dispose(this.f4178s);
    }
}
