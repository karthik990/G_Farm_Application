package p043io.opencensus.metrics.export;

/* renamed from: io.opencensus.metrics.export.AutoValue_Value_ValueSummary */
final class AutoValue_Value_ValueSummary extends ValueSummary {
    private final Summary value;

    AutoValue_Value_ValueSummary(Summary summary) {
        if (summary != null) {
            this.value = summary;
            return;
        }
        throw new NullPointerException("Null value");
    }

    /* access modifiers changed from: 0000 */
    public Summary getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValueSummary{value=");
        sb.append(this.value);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ValueSummary)) {
            return false;
        }
        return this.value.equals(((ValueSummary) obj).getValue());
    }

    public int hashCode() {
        return this.value.hashCode() ^ 1000003;
    }
}
