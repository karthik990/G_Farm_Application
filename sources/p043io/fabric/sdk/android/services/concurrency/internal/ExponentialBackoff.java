package p043io.fabric.sdk.android.services.concurrency.internal;

/* renamed from: io.fabric.sdk.android.services.concurrency.internal.ExponentialBackoff */
public class ExponentialBackoff implements Backoff {
    private static final int DEFAULT_POWER = 2;
    private final long baseTimeMillis;
    private final int power;

    public ExponentialBackoff(long j) {
        this(j, 2);
    }

    public ExponentialBackoff(long j, int i) {
        this.baseTimeMillis = j;
        this.power = i;
    }

    public long getDelayMillis(int i) {
        double d = (double) this.baseTimeMillis;
        double pow = Math.pow((double) this.power, (double) i);
        Double.isNaN(d);
        return (long) (d * pow);
    }
}
