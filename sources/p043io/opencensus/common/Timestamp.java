package p043io.opencensus.common;

import com.google.android.exoplayer2.C1996C;
import java.math.BigDecimal;
import java.math.RoundingMode;

/* renamed from: io.opencensus.common.Timestamp */
public abstract class Timestamp implements Comparable<Timestamp> {
    public abstract int getNanos();

    public abstract long getSeconds();

    Timestamp() {
    }

    public static Timestamp create(long j, int i) {
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
        } else if (i < 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("'nanos' is less than zero: ");
            sb3.append(i);
            throw new IllegalArgumentException(sb3.toString());
        } else if (i <= 999999999) {
            return new AutoValue_Timestamp(j, i);
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("'nanos' is greater than maximum (999999999): ");
            sb4.append(i);
            throw new IllegalArgumentException(sb4.toString());
        }
    }

    public static Timestamp fromMillis(long j) {
        return create(floorDiv(j, 1000), (int) (((long) ((int) floorMod(j, 1000))) * 1000000));
    }

    public Timestamp addNanos(long j) {
        return plus(0, j);
    }

    public Timestamp addDuration(Duration duration) {
        return plus(duration.getSeconds(), (long) duration.getNanos());
    }

    public Duration subtractTimestamp(Timestamp timestamp) {
        long j;
        long seconds = getSeconds() - timestamp.getSeconds();
        int nanos = getNanos() - timestamp.getNanos();
        if (seconds >= 0 || nanos <= 0) {
            if (seconds > 0 && nanos < 0) {
                seconds--;
                j = ((long) nanos) + C1996C.NANOS_PER_SECOND;
            }
            return Duration.create(seconds, nanos);
        }
        seconds++;
        j = ((long) nanos) - C1996C.NANOS_PER_SECOND;
        nanos = (int) j;
        return Duration.create(seconds, nanos);
    }

    public int compareTo(Timestamp timestamp) {
        int compareLongs = TimeUtils.compareLongs(getSeconds(), timestamp.getSeconds());
        if (compareLongs != 0) {
            return compareLongs;
        }
        return TimeUtils.compareLongs((long) getNanos(), (long) timestamp.getNanos());
    }

    private Timestamp plus(long j, long j2) {
        if ((j | j2) == 0) {
            return this;
        }
        return ofEpochSecond(TimeUtils.checkedAdd(TimeUtils.checkedAdd(getSeconds(), j), j2 / C1996C.NANOS_PER_SECOND), ((long) getNanos()) + (j2 % C1996C.NANOS_PER_SECOND));
    }

    private static Timestamp ofEpochSecond(long j, long j2) {
        return create(TimeUtils.checkedAdd(j, floorDiv(j2, C1996C.NANOS_PER_SECOND)), (int) floorMod(j2, C1996C.NANOS_PER_SECOND));
    }

    private static long floorDiv(long j, long j2) {
        return BigDecimal.valueOf(j).divide(BigDecimal.valueOf(j2), 0, RoundingMode.FLOOR).longValue();
    }

    private static long floorMod(long j, long j2) {
        return j - (floorDiv(j, j2) * j2);
    }
}
