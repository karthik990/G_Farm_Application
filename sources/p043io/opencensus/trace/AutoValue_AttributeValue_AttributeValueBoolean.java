package p043io.opencensus.trace;

/* renamed from: io.opencensus.trace.AutoValue_AttributeValue_AttributeValueBoolean */
final class AutoValue_AttributeValue_AttributeValueBoolean extends AttributeValueBoolean {
    private final Boolean booleanValue;

    AutoValue_AttributeValue_AttributeValueBoolean(Boolean bool) {
        if (bool != null) {
            this.booleanValue = bool;
            return;
        }
        throw new NullPointerException("Null booleanValue");
    }

    /* access modifiers changed from: 0000 */
    public Boolean getBooleanValue() {
        return this.booleanValue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AttributeValueBoolean{booleanValue=");
        sb.append(this.booleanValue);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AttributeValueBoolean)) {
            return false;
        }
        return this.booleanValue.equals(((AttributeValueBoolean) obj).getBooleanValue());
    }

    public int hashCode() {
        return this.booleanValue.hashCode() ^ 1000003;
    }
}
