package p043io.opencensus.metrics;

import javax.annotation.Nullable;

/* renamed from: io.opencensus.metrics.AutoValue_LabelValue */
final class AutoValue_LabelValue extends LabelValue {
    private final String value;

    AutoValue_LabelValue(@Nullable String str) {
        this.value = str;
    }

    @Nullable
    public String getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LabelValue{value=");
        sb.append(this.value);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LabelValue)) {
            return false;
        }
        LabelValue labelValue = (LabelValue) obj;
        String str = this.value;
        String value2 = labelValue.getValue();
        if (str != null) {
            z = str.equals(value2);
        } else if (value2 != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        String str = this.value;
        return (str == null ? 0 : str.hashCode()) ^ 1000003;
    }
}
