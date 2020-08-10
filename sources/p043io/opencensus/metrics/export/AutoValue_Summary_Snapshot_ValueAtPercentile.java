package p043io.opencensus.metrics.export;

import p043io.opencensus.metrics.export.Summary.Snapshot.ValueAtPercentile;

/* renamed from: io.opencensus.metrics.export.AutoValue_Summary_Snapshot_ValueAtPercentile */
final class AutoValue_Summary_Snapshot_ValueAtPercentile extends ValueAtPercentile {
    private final double percentile;
    private final double value;

    AutoValue_Summary_Snapshot_ValueAtPercentile(double d, double d2) {
        this.percentile = d;
        this.value = d2;
    }

    public double getPercentile() {
        return this.percentile;
    }

    public double getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValueAtPercentile{percentile=");
        sb.append(this.percentile);
        sb.append(", value=");
        sb.append(this.value);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ValueAtPercentile)) {
            return false;
        }
        ValueAtPercentile valueAtPercentile = (ValueAtPercentile) obj;
        if (!(Double.doubleToLongBits(this.percentile) == Double.doubleToLongBits(valueAtPercentile.getPercentile()) && Double.doubleToLongBits(this.value) == Double.doubleToLongBits(valueAtPercentile.getValue()))) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (int) (((long) (((int) (((long) 1000003) ^ ((Double.doubleToLongBits(this.percentile) >>> 32) ^ Double.doubleToLongBits(this.percentile)))) * 1000003)) ^ ((Double.doubleToLongBits(this.value) >>> 32) ^ Double.doubleToLongBits(this.value)));
    }
}
