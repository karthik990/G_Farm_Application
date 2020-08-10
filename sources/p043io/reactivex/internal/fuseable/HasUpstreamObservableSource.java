package p043io.reactivex.internal.fuseable;

import p043io.reactivex.ObservableSource;

/* renamed from: io.reactivex.internal.fuseable.HasUpstreamObservableSource */
public interface HasUpstreamObservableSource<T> {
    ObservableSource<T> source();
}
