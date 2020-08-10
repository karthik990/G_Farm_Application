package p043io.reactivex.internal.util;

import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;
import p043io.reactivex.disposables.Disposable;
import p043io.reactivex.exceptions.ProtocolViolationException;
import p043io.reactivex.internal.disposables.DisposableHelper;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.internal.subscriptions.SubscriptionHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.util.EndConsumerHelper */
public final class EndConsumerHelper {
    private EndConsumerHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static boolean validate(Disposable disposable, Disposable disposable2, Class<?> cls) {
        ObjectHelper.requireNonNull(disposable2, "next is null");
        if (disposable == null) {
            return true;
        }
        disposable2.dispose();
        if (disposable != DisposableHelper.DISPOSED) {
            reportDoubleSubscription(cls);
        }
        return false;
    }

    public static boolean setOnce(AtomicReference<Disposable> atomicReference, Disposable disposable, Class<?> cls) {
        ObjectHelper.requireNonNull(disposable, "next is null");
        if (atomicReference.compareAndSet(null, disposable)) {
            return true;
        }
        disposable.dispose();
        if (atomicReference.get() != DisposableHelper.DISPOSED) {
            reportDoubleSubscription(cls);
        }
        return false;
    }

    public static boolean validate(Subscription subscription, Subscription subscription2, Class<?> cls) {
        ObjectHelper.requireNonNull(subscription2, "next is null");
        if (subscription == null) {
            return true;
        }
        subscription2.cancel();
        if (subscription != SubscriptionHelper.CANCELLED) {
            reportDoubleSubscription(cls);
        }
        return false;
    }

    public static boolean setOnce(AtomicReference<Subscription> atomicReference, Subscription subscription, Class<?> cls) {
        ObjectHelper.requireNonNull(subscription, "next is null");
        if (atomicReference.compareAndSet(null, subscription)) {
            return true;
        }
        subscription.cancel();
        if (atomicReference.get() != SubscriptionHelper.CANCELLED) {
            reportDoubleSubscription(cls);
        }
        return false;
    }

    public static String composeMessage(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("It is not allowed to subscribe with a(n) ");
        sb.append(str);
        sb.append(" multiple times. ");
        sb.append("Please create a fresh instance of ");
        sb.append(str);
        sb.append(" and subscribe that to the target source instead.");
        return sb.toString();
    }

    public static void reportDoubleSubscription(Class<?> cls) {
        RxJavaPlugins.onError(new ProtocolViolationException(composeMessage(cls.getName())));
    }
}
