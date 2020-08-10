package p043io.reactivex.observers;

import java.util.concurrent.atomic.AtomicReference;
import p043io.reactivex.SingleObserver;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.disposables.ListCompositeDisposable;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.util.EndConsumerHelper;

/* renamed from: io.reactivex.observers.ResourceSingleObserver */
public abstract class ResourceSingleObserver<T> implements SingleObserver<T>, Disposable {
    private final ListCompositeDisposable resources = new ListCompositeDisposable();

    /* renamed from: s */
    private final AtomicReference<Disposable> f4184s = new AtomicReference<>();

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    public final void add(Disposable disposable) {
        ObjectHelper.requireNonNull(disposable, "resource is null");
        this.resources.add(disposable);
    }

    public final void onSubscribe(Disposable disposable) {
        if (EndConsumerHelper.setOnce(this.f4184s, disposable, getClass())) {
            onStart();
        }
    }

    public final void dispose() {
        if (DisposableHelper.dispose(this.f4184s)) {
            this.resources.dispose();
        }
    }

    public final boolean isDisposed() {
        return DisposableHelper.isDisposed((Disposable) this.f4184s.get());
    }
}
