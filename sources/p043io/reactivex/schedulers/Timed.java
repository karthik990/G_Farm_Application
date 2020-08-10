package p043io.reactivex.schedulers;

import java.util.concurrent.TimeUnit;
import p043io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.schedulers.Timed */
public final class Timed<T> {
    final long time;
    final TimeUnit unit;
    final T value;

    public Timed(T t, long j, TimeUnit timeUnit) {
        this.value = t;
        this.time = j;
        this.unit = (TimeUnit) ObjectHelper.requireNonNull(timeUnit, "unit is null");
    }

    public T value() {
        return this.value;
    }

    public TimeUnit unit() {
        return this.unit;
    }

    public long time() {
        return this.time;
    }

    public long time(TimeUnit timeUnit) {
        return timeUnit.convert(this.time, this.unit);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Timed)) {
            return false;
        }
        Timed timed = (Timed) obj;
        if (!ObjectHelper.equals(this.value, timed.value) || this.time != timed.time || !ObjectHelper.equals(this.unit, timed.unit)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        T t = this.value;
        int hashCode = (t != null ? t.hashCode() : 0) * 31;
        long j = this.time;
        return ((hashCode + ((int) (j ^ (j >>> 31)))) * 31) + this.unit.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Timed[time=");
        sb.append(this.time);
        sb.append(", unit=");
        sb.append(this.unit);
        sb.append(", value=");
        sb.append(this.value);
        sb.append("]");
        return sb.toString();
    }
}
