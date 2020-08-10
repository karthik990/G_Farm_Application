package p043io.opencensus.metrics.data;

import p043io.opencensus.metrics.data.AttachmentValue.AttachmentValueString;

/* renamed from: io.opencensus.metrics.data.AutoValue_AttachmentValue_AttachmentValueString */
final class AutoValue_AttachmentValue_AttachmentValueString extends AttachmentValueString {
    private final String value;

    AutoValue_AttachmentValue_AttachmentValueString(String str) {
        if (str != null) {
            this.value = str;
            return;
        }
        throw new NullPointerException("Null value");
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AttachmentValueString{value=");
        sb.append(this.value);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AttachmentValueString)) {
            return false;
        }
        return this.value.equals(((AttachmentValueString) obj).getValue());
    }

    public int hashCode() {
        return this.value.hashCode() ^ 1000003;
    }
}
