package p043io.reactivex.internal.operators.maybe;

import p043io.reactivex.Maybe;
import p043io.reactivex.MaybeSource;
import p043io.reactivex.internal.fuseable.HasUpstreamMaybeSource;

/* renamed from: io.reactivex.internal.operators.maybe.AbstractMaybeWithUpstream */
abstract class AbstractMaybeWithUpstream<T, R> extends Maybe<R> implements HasUpstreamMaybeSource<T> {
    protected final MaybeSource<T> source;

    AbstractMaybeWithUpstream(MaybeSource<T> maybeSource) {
        this.source = maybeSource;
    }

    public final MaybeSource<T> source() {
        return this.source;
    }
}
