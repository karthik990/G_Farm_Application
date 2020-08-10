package p043io.opencensus.stats;

import p043io.opencensus.stats.Measure.MeasureDouble;
import p043io.opencensus.stats.Measurement.MeasurementDouble;

/* renamed from: io.opencensus.stats.AutoValue_Measurement_MeasurementDouble */
final class AutoValue_Measurement_MeasurementDouble extends MeasurementDouble {
    private final MeasureDouble measure;
    private final double value;

    AutoValue_Measurement_MeasurementDouble(MeasureDouble measureDouble, double d) {
        if (measureDouble != null) {
            this.measure = measureDouble;
            this.value = d;
            return;
        }
        throw new NullPointerException("Null measure");
    }

    public MeasureDouble getMeasure() {
        return this.measure;
    }

    public double getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MeasurementDouble{measure=");
        sb.append(this.measure);
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
        if (!(obj instanceof MeasurementDouble)) {
            return false;
        }
        MeasurementDouble measurementDouble = (MeasurementDouble) obj;
        if (!this.measure.equals(measurementDouble.getMeasure()) || Double.doubleToLongBits(this.value) != Double.doubleToLongBits(measurementDouble.getValue())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (int) (((long) ((this.measure.hashCode() ^ 1000003) * 1000003)) ^ ((Double.doubleToLongBits(this.value) >>> 32) ^ Double.doubleToLongBits(this.value)));
    }
}
