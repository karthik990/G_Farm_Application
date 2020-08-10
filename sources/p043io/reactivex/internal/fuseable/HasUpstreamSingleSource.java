package p043io.reactivex.internal.fuseable;

import p043io.reactivex.SingleSource;

/* renamed from: io.reactivex.internal.fuseable.HasUpstreamSingleSource */
public interface HasUpstreamSingleSource<T> {
    SingleSource<T> source();
}
