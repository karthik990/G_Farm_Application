package p043io.opencensus.common;

import java.util.concurrent.TimeUnit;

/* renamed from: io.opencensus.common.Duration */
public abstract class Duration implements Comparable<Duration> {
    public abstract int getNanos();

    public abstract long getSeconds();

    public static Duration create(long j, int i) {
        if (j < -315576000000L) {
            StringBuilder sb = new StringBuilder();
            sb.append("'seconds' is less than minimum (-315576000000): ");
            sb.append(j);
            throw new IllegalArgumentException(sb.toString());
        } else if (j > 315576000000L) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("'seconds' is greater than maximum (315576000000): ");
            sb2.append(j);
            throw new IllegalArgumentException(sb2.toString());
        } else if (i < -999999999) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("'nanos' is less than minimum (-999999999): ");
            sb3.append(i);
            throw new IllegalArgumentException(sb3.toString());
        } else if (i > 999999999) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("'nanos' is greater than maximum (999999999): ");
            sb4.append(i);
            throw new IllegalArgumentException(sb4.toString());
        } else if ((j >= 0 || i <= 0) && (j <= 0 || i >= 0)) {
            return new AutoValue_Duration(j, i);
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("'seconds' and 'nanos' have inconsistent sign: seconds=");
            sb5.append(j);
            sb5.append(", nanos=");
            sb5.append(i);
            throw new IllegalArgumentException(sb5.toString());
        }
    }

    public static Duration fromMillis(long j) {
        return create(j / 1000, (int) ((j % 1000) * 1000000));
    }

    public long toMillis() {
        return TimeUnit.SECONDS.toMillis(getSeconds()) + TimeUnit.NANOSECONDS.toMillis((long) getNanos());
    }

    public int compareTo(Duration duration) {
        int compareLongs = TimeUtils.compareLongs(getSeconds(), duration.getSeconds());
        if (compareLongs != 0) {
            return compareLongs;
        }
        return TimeUtils.compareLongs((long) getNanos(), (long) duration.getNanos());
    }

    Duration() {
    }
}
