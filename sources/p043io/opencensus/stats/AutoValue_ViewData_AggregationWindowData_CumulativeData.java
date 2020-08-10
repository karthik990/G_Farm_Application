package p043io.opencensus.stats;

import p043io.opencensus.common.Timestamp;
import p043io.opencensus.stats.ViewData.AggregationWindowData.CumulativeData;

@Deprecated
/* renamed from: io.opencensus.stats.AutoValue_ViewData_AggregationWindowData_CumulativeData */
final class AutoValue_ViewData_AggregationWindowData_CumulativeData extends CumulativeData {
    private final Timestamp end;
    private final Timestamp start;

    AutoValue_ViewData_AggregationWindowData_CumulativeData(Timestamp timestamp, Timestamp timestamp2) {
        if (timestamp != null) {
            this.start = timestamp;
            if (timestamp2 != null) {
                this.end = timestamp2;
                return;
            }
            throw new NullPointerException("Null end");
        }
        throw new NullPointerException("Null start");
    }

    public Timestamp getStart() {
        return this.start;
    }

    public Timestamp getEnd() {
        return this.end;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CumulativeData{start=");
        sb.append(this.start);
        sb.append(", end=");
        sb.append(this.end);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CumulativeData)) {
            return false;
        }
        CumulativeData cumulativeData = (CumulativeData) obj;
        if (!this.start.equals(cumulativeData.getStart()) || !this.end.equals(cumulativeData.getEnd())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.start.hashCode() ^ 1000003) * 1000003) ^ this.end.hashCode();
    }
}
