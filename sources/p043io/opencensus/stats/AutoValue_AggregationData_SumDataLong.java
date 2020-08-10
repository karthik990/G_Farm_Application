package p043io.opencensus.stats;

import p043io.opencensus.stats.AggregationData.SumDataLong;

/* renamed from: io.opencensus.stats.AutoValue_AggregationData_SumDataLong */
final class AutoValue_AggregationData_SumDataLong extends SumDataLong {
    private final long sum;

    AutoValue_AggregationData_SumDataLong(long j) {
        this.sum = j;
    }

    public long getSum() {
        return this.sum;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SumDataLong{sum=");
        sb.append(this.sum);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SumDataLong)) {
            return false;
        }
        if (this.sum != ((SumDataLong) obj).getSum()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long j = (long) 1000003;
        long j2 = this.sum;
        return (int) (j ^ (j2 ^ (j2 >>> 32)));
    }
}
