package p043io.netty.util.internal;

/* renamed from: io.netty.util.internal.LongCounter */
public interface LongCounter {
    void add(long j);

    void decrement();

    void increment();

    long value();
}
