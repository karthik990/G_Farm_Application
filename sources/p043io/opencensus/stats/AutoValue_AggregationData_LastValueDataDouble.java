package p043io.opencensus.stats;

import p043io.opencensus.stats.AggregationData.LastValueDataDouble;

/* renamed from: io.opencensus.stats.AutoValue_AggregationData_LastValueDataDouble */
final class AutoValue_AggregationData_LastValueDataDouble extends LastValueDataDouble {
    private final double lastValue;

    AutoValue_AggregationData_LastValueDataDouble(double d) {
        this.lastValue = d;
    }

    public double getLastValue() {
        return this.lastValue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LastValueDataDouble{lastValue=");
        sb.append(this.lastValue);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LastValueDataDouble)) {
            return false;
        }
        if (Double.doubleToLongBits(this.lastValue) != Double.doubleToLongBits(((LastValueDataDouble) obj).getLastValue())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (int) (((long) 1000003) ^ ((Double.doubleToLongBits(this.lastValue) >>> 32) ^ Double.doubleToLongBits(this.lastValue)));
    }
}
