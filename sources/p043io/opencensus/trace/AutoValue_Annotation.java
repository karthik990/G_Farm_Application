package p043io.opencensus.trace;

import java.util.Map;

/* renamed from: io.opencensus.trace.AutoValue_Annotation */
final class AutoValue_Annotation extends Annotation {
    private final Map<String, AttributeValue> attributes;
    private final String description;

    AutoValue_Annotation(String str, Map<String, AttributeValue> map) {
        if (str != null) {
            this.description = str;
            if (map != null) {
                this.attributes = map;
                return;
            }
            throw new NullPointerException("Null attributes");
        }
        throw new NullPointerException("Null description");
    }

    public String getDescription() {
        return this.description;
    }

    public Map<String, AttributeValue> getAttributes() {
        return this.attributes;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Annotation{description=");
        sb.append(this.description);
        sb.append(", attributes=");
        sb.append(this.attributes);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Annotation)) {
            return false;
        }
        Annotation annotation = (Annotation) obj;
        if (!this.description.equals(annotation.getDescription()) || !this.attributes.equals(annotation.getAttributes())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.description.hashCode() ^ 1000003) * 1000003) ^ this.attributes.hashCode();
    }
}
