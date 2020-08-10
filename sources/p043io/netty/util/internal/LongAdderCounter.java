package p043io.netty.util.internal;

import java.util.concurrent.atomic.LongAdder;

/* renamed from: io.netty.util.internal.LongAdderCounter */
final class LongAdderCounter extends LongAdder implements LongCounter {
    LongAdderCounter() {
    }

    public long value() {
        return longValue();
    }
}
