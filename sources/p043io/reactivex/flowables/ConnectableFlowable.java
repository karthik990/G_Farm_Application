package p043io.reactivex.flowables;

import p043io.reactivex.Flowable;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.functions.Consumer;
import p043io.reactivex.internal.functions.Functions;
import p043io.reactivex.internal.operators.flowable.FlowableAutoConnect;
import p043io.reactivex.internal.operators.flowable.FlowableRefCount;
import p043io.reactivex.internal.util.ConnectConsumer;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.flowables.ConnectableFlowable */
public abstract class ConnectableFlowable<T> extends Flowable<T> {
    public abstract void connect(Consumer<? super Disposable> consumer);

    public final Disposable connect() {
        ConnectConsumer connectConsumer = new ConnectConsumer();
        connect(connectConsumer);
        return connectConsumer.disposable;
    }

    public Flowable<T> refCount() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableRefCount<T>(this));
    }

    public Flowable<T> autoConnect() {
        return autoConnect(1);
    }

    public Flowable<T> autoConnect(int i) {
        return autoConnect(i, Functions.emptyConsumer());
    }

    public Flowable<T> autoConnect(int i, Consumer<? super Disposable> consumer) {
        if (i > 0) {
            return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableAutoConnect<T>(this, i, consumer));
        }
        connect(consumer);
        return RxJavaPlugins.onAssembly(this);
    }
}
