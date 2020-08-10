package p043io.opencensus.stats;

import p043io.opencensus.stats.AggregationData.CountData;

/* renamed from: io.opencensus.stats.AutoValue_AggregationData_CountData */
final class AutoValue_AggregationData_CountData extends CountData {
    private final long count;

    AutoValue_AggregationData_CountData(long j) {
        this.count = j;
    }

    public long getCount() {
        return this.count;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CountData{count=");
        sb.append(this.count);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CountData)) {
            return false;
        }
        if (this.count != ((CountData) obj).getCount()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long j = (long) 1000003;
        long j2 = this.count;
        return (int) (j ^ (j2 ^ (j2 >>> 32)));
    }
}
