package p043io.opencensus.stats;

import p043io.opencensus.stats.Measure.MeasureLong;
import p043io.opencensus.stats.Measurement.MeasurementLong;

/* renamed from: io.opencensus.stats.AutoValue_Measurement_MeasurementLong */
final class AutoValue_Measurement_MeasurementLong extends MeasurementLong {
    private final MeasureLong measure;
    private final long value;

    AutoValue_Measurement_MeasurementLong(MeasureLong measureLong, long j) {
        if (measureLong != null) {
            this.measure = measureLong;
            this.value = j;
            return;
        }
        throw new NullPointerException("Null measure");
    }

    public MeasureLong getMeasure() {
        return this.measure;
    }

    public long getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MeasurementLong{measure=");
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
        if (!(obj instanceof MeasurementLong)) {
            return false;
        }
        MeasurementLong measurementLong = (MeasurementLong) obj;
        if (!this.measure.equals(measurementLong.getMeasure()) || this.value != measurementLong.getValue()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long hashCode = (long) ((this.measure.hashCode() ^ 1000003) * 1000003);
        long j = this.value;
        return (int) (hashCode ^ (j ^ (j >>> 32)));
    }
}
