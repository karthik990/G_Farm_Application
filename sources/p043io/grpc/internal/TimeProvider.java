package p043io.grpc.internal;

import java.util.concurrent.TimeUnit;

/* renamed from: io.grpc.internal.TimeProvider */
public interface TimeProvider {
    public static final TimeProvider SYSTEM_TIME_PROVIDER = new TimeProvider() {
        final long offsetNanos = (TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis()) - System.nanoTime());

        public long currentTimeNanos() {
            return System.nanoTime() + this.offsetNanos;
        }
    };

    long currentTimeNanos();
}
