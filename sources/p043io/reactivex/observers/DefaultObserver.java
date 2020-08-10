package p043io.reactivex.observers;

import p043io.reactivex.Observer;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.util.EndConsumerHelper;

/* renamed from: io.reactivex.observers.DefaultObserver */
public abstract class DefaultObserver<T> implements Observer<T> {

    /* renamed from: s */
    private Disposable f4176s;

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    public final void onSubscribe(Disposable disposable) {
        if (EndConsumerHelper.validate(this.f4176s, disposable, getClass())) {
            this.f4176s = disposable;
            onStart();
        }
    }

    /* access modifiers changed from: protected */
    public final void cancel() {
        Disposable disposable = this.f4176s;
        this.f4176s = DisposableHelper.DISPOSED;
        disposable.dispose();
    }
}
