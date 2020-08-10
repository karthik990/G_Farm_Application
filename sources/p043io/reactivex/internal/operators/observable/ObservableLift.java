package p043io.reactivex.internal.operators.observable;

import p043io.reactivex.ObservableOperator;
import p043io.reactivex.ObservableSource;
import p043io.reactivex.Observer;
import p043io.reactivex.exceptions.Exceptions;
import p043io.reactivex.internal.functions.ObjectHelper;
import p043io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableLift */
public final class ObservableLift<R, T> extends AbstractObservableWithUpstream<T, R> {
    final ObservableOperator<? extends R, ? super T> operator;

    public ObservableLift(ObservableSource<T> observableSource, ObservableOperator<? extends R, ? super T> observableOperator) {
        super(observableSource);
        this.operator = observableOperator;
    }

    public void subscribeActual(Observer<? super R> observer) {
        try {
            Observer apply = this.operator.apply(observer);
            StringBuilder sb = new StringBuilder();
            sb.append("Operator ");
            sb.append(this.operator);
            sb.append(" returned a null Observer");
            this.source.subscribe((Observer) ObjectHelper.requireNonNull(apply, sb.toString()));
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
