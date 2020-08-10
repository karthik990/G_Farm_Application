package p043io.opencensus.metrics.export;

/* renamed from: io.opencensus.metrics.export.AutoValue_Value_ValueDistribution */
final class AutoValue_Value_ValueDistribution extends ValueDistribution {
    private final Distribution value;

    AutoValue_Value_ValueDistribution(Distribution distribution) {
        if (distribution != null) {
            this.value = distribution;
            return;
        }
        throw new NullPointerException("Null value");
    }

    /* access modifiers changed from: 0000 */
    public Distribution getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValueDistribution{value=");
        sb.append(this.value);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ValueDistribution)) {
            return false;
        }
        return this.value.equals(((ValueDistribution) obj).getValue());
    }

    public int hashCode() {
        return this.value.hashCode() ^ 1000003;
    }
}
