package p043io.grpc.stub;

import com.google.common.base.Preconditions;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.Iterator;

/* renamed from: io.grpc.stub.StreamObservers */
public final class StreamObservers {
    public static <V> void copyWithFlowControl(final Iterator<V> it, final CallStreamObserver<V> callStreamObserver) {
        Preconditions.checkNotNull(it, Param.SOURCE);
        Preconditions.checkNotNull(callStreamObserver, "target");
        callStreamObserver.setOnReadyHandler(new Runnable() {
            public void run() {
                while (CallStreamObserver.this.isReady() && it.hasNext()) {
                    CallStreamObserver.this.onNext(it.next());
                }
                if (!it.hasNext()) {
                    CallStreamObserver.this.onCompleted();
                }
            }
        });
    }

    public static <V> void copyWithFlowControl(Iterable<V> iterable, CallStreamObserver<V> callStreamObserver) {
        Preconditions.checkNotNull(iterable, Param.SOURCE);
        copyWithFlowControl(iterable.iterator(), callStreamObserver);
    }
}
