package p043io.reactivex.internal.operators.observable;

import java.util.concurrent.LinkedBlockingQueue;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.functions.Action;
import p043io.reactivex.functions.Consumer;
import p043io.reactivex.internal.functions.Functions;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.observers.BlockingObserver;
import p043io.reactivex.internal.observers.LambdaObserver;
import p043io.reactivex.internal.util.BlockingHelper;
import p043io.reactivex.internal.util.BlockingIgnoringReceiver;
import p043io.reactivex.internal.util.ExceptionHelper;
import p043io.reactivex.internal.util.NotificationLite;

/* renamed from: io.reactivex.internal.operators.observable.ObservableBlockingSubscribe */
public final class ObservableBlockingSubscribe {
    private ObservableBlockingSubscribe() {
        throw new IllegalStateException("No instances!");
    }

    public static <T> void subscribe(ObservableSource<? extends T> observableSource, Observer<? super T> observer) {
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        BlockingObserver blockingObserver = new BlockingObserver(linkedBlockingQueue);
        observer.onSubscribe(blockingObserver);
        observableSource.subscribe(blockingObserver);
        while (!blockingObserver.isDisposed()) {
            Object poll = linkedBlockingQueue.poll();
            if (poll == null) {
                try {
                    poll = linkedBlockingQueue.take();
                } catch (InterruptedException e) {
                    blockingObserver.dispose();
                    observer.onError(e);
                    return;
                }
            }
            if (!blockingObserver.isDisposed() && observableSource != BlockingObserver.TERMINATED) {
                if (NotificationLite.acceptFull(poll, observer)) {
                    break;
                }
            } else {
                break;
            }
        }
    }

    public static <T> void subscribe(ObservableSource<? extends T> observableSource) {
        BlockingIgnoringReceiver blockingIgnoringReceiver = new BlockingIgnoringReceiver();
        LambdaObserver lambdaObserver = new LambdaObserver(Functions.emptyConsumer(), blockingIgnoringReceiver, blockingIgnoringReceiver, Functions.emptyConsumer());
        observableSource.subscribe(lambdaObserver);
        BlockingHelper.awaitForComplete(blockingIgnoringReceiver, lambdaObserver);
        Throwable th = blockingIgnoringReceiver.error;
        if (th != null) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    public static <T> void subscribe(ObservableSource<? extends T> observableSource, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action) {
        ObjectHelper.requireNonNull(consumer, "onNext is null");
        ObjectHelper.requireNonNull(consumer2, "onError is null");
        ObjectHelper.requireNonNull(action, "onComplete is null");
        subscribe(observableSource, new LambdaObserver(consumer, consumer2, action, Functions.emptyConsumer()));
    }
}
