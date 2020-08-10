package p043io.opencensus.metrics.export;

import java.util.List;
import javax.annotation.Nullable;
import p043io.opencensus.common.Timestamp;
import p043io.opencensus.metrics.LabelValue;

/* renamed from: io.opencensus.metrics.export.AutoValue_TimeSeries */
final class AutoValue_TimeSeries extends TimeSeries {
    private final List<LabelValue> labelValues;
    private final List<Point> points;
    private final Timestamp startTimestamp;

    AutoValue_TimeSeries(List<LabelValue> list, List<Point> list2, @Nullable Timestamp timestamp) {
        if (list != null) {
            this.labelValues = list;
            if (list2 != null) {
                this.points = list2;
                this.startTimestamp = timestamp;
                return;
            }
            throw new NullPointerException("Null points");
        }
        throw new NullPointerException("Null labelValues");
    }

    public List<LabelValue> getLabelValues() {
        return this.labelValues;
    }

    public List<Point> getPoints() {
        return this.points;
    }

    @Nullable
    public Timestamp getStartTimestamp() {
        return this.startTimestamp;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TimeSeries{labelValues=");
        sb.append(this.labelValues);
        sb.append(", points=");
        sb.append(this.points);
        sb.append(", startTimestamp=");
        sb.append(this.startTimestamp);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimeSeries)) {
            return false;
        }
        TimeSeries timeSeries = (TimeSeries) obj;
        if (this.labelValues.equals(timeSeries.getLabelValues()) && this.points.equals(timeSeries.getPoints())) {
            Timestamp timestamp = this.startTimestamp;
            if (timestamp != null) {
            }
        }
        z = false;
        return z;
    }

    public int hashCode() {
        int hashCode = (((this.labelValues.hashCode() ^ 1000003) * 1000003) ^ this.points.hashCode()) * 1000003;
        Timestamp timestamp = this.startTimestamp;
        return hashCode ^ (timestamp == null ? 0 : timestamp.hashCode());
    }
}
