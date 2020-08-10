package p043io.reactivex;

import org.reactivestreams.Publisher;

/* renamed from: io.reactivex.FlowableTransformer */
public interface FlowableTransformer<Upstream, Downstream> {
    Publisher<Downstream> apply(Flowable<Upstream> flowable);
}
