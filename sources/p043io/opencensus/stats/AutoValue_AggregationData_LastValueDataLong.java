package p043io.opencensus.stats;

import p043io.opencensus.stats.AggregationData.LastValueDataLong;

/* renamed from: io.opencensus.stats.AutoValue_AggregationData_LastValueDataLong */
final class AutoValue_AggregationData_LastValueDataLong extends LastValueDataLong {
    private final long lastValue;

    AutoValue_AggregationData_LastValueDataLong(long j) {
        this.lastValue = j;
    }

    public long getLastValue() {
        return this.lastValue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LastValueDataLong{lastValue=");
        sb.append(this.lastValue);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LastValueDataLong)) {
            return false;
        }
        if (this.lastValue != ((LastValueDataLong) obj).getLastValue()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long j = (long) 1000003;
        long j2 = this.lastValue;
        return (int) (j ^ (j2 ^ (j2 >>> 32)));
    }
}
