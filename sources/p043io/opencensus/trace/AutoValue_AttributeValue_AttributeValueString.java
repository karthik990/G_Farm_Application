package p043io.opencensus.trace;

/* renamed from: io.opencensus.trace.AutoValue_AttributeValue_AttributeValueString */
final class AutoValue_AttributeValue_AttributeValueString extends AttributeValueString {
    private final String stringValue;

    AutoValue_AttributeValue_AttributeValueString(String str) {
        if (str != null) {
            this.stringValue = str;
            return;
        }
        throw new NullPointerException("Null stringValue");
    }

    /* access modifiers changed from: 0000 */
    public String getStringValue() {
        return this.stringValue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AttributeValueString{stringValue=");
        sb.append(this.stringValue);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AttributeValueString)) {
            return false;
        }
        return this.stringValue.equals(((AttributeValueString) obj).getStringValue());
    }

    public int hashCode() {
        return this.stringValue.hashCode() ^ 1000003;
    }
}
