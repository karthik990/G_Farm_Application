package p043io.opencensus.metrics.export;

/* renamed from: io.opencensus.metrics.export.AutoValue_Value_ValueDouble */
final class AutoValue_Value_ValueDouble extends ValueDouble {
    private final double value;

    AutoValue_Value_ValueDouble(double d) {
        this.value = d;
    }

    /* access modifiers changed from: 0000 */
    public double getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValueDouble{value=");
        sb.append(this.value);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ValueDouble)) {
            return false;
        }
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(((ValueDouble) obj).getValue())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (int) (((long) 1000003) ^ ((Double.doubleToLongBits(this.value) >>> 32) ^ Double.doubleToLongBits(this.value)));
    }
}
