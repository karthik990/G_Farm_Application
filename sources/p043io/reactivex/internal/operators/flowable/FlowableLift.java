package p043io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import p043io.reactivex.Flowable;
import p043io.reactivex.FlowableOperator;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableLift */
public final class FlowableLift<R, T> extends AbstractFlowableWithUpstream<T, R> {
    final FlowableOperator<? extends R, ? super T> operator;

    public FlowableLift(Flowable<T> flowable, FlowableOperator<? extends R, ? super T> flowableOperator) {
        super(flowable);
        this.operator = flowableOperator;
    }

    public void subscribeActual(Subscriber<? super R> subscriber) {
        try {
            Subscriber apply = this.operator.apply(subscriber);
            if (apply != null) {
                this.source.subscribe(apply);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Operator ");
            sb.append(this.operator);
            sb.append(" returned a null Subscriber");
            throw new NullPointerException(sb.toString());
        } catch (NullPointerException e) {
            throw e;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            RxJavaPlugins.onError(th);
            NullPointerException nullPointerException = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
            nullPointerException.initCause(th);
            throw nullPointerException;
        }
    }
}
