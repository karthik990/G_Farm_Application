package p043io.opencensus.metrics;

/* renamed from: io.opencensus.metrics.AutoValue_LabelKey */
final class AutoValue_LabelKey extends LabelKey {
    private final String description;
    private final String key;

    AutoValue_LabelKey(String str, String str2) {
        if (str != null) {
            this.key = str;
            if (str2 != null) {
                this.description = str2;
                return;
            }
            throw new NullPointerException("Null description");
        }
        throw new NullPointerException("Null key");
    }

    public String getKey() {
        return this.key;
    }

    public String getDescription() {
        return this.description;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LabelKey{key=");
        sb.append(this.key);
        sb.append(", description=");
        sb.append(this.description);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LabelKey)) {
            return false;
        }
        LabelKey labelKey = (LabelKey) obj;
        if (!this.key.equals(labelKey.getKey()) || !this.description.equals(labelKey.getDescription())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.key.hashCode() ^ 1000003) * 1000003) ^ this.description.hashCode();
    }
}
