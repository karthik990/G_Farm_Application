package p043io.opencensus.common;

/* renamed from: io.opencensus.common.Clock */
public abstract class Clock {
    public abstract Timestamp now();

    public abstract long nowNanos();
}
