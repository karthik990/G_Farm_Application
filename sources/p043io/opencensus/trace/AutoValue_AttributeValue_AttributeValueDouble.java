package p043io.opencensus.trace;

/* renamed from: io.opencensus.trace.AutoValue_AttributeValue_AttributeValueDouble */
final class AutoValue_AttributeValue_AttributeValueDouble extends AttributeValueDouble {
    private final Double doubleValue;

    AutoValue_AttributeValue_AttributeValueDouble(Double d) {
        if (d != null) {
            this.doubleValue = d;
            return;
        }
        throw new NullPointerException("Null doubleValue");
    }

    /* access modifiers changed from: 0000 */
    public Double getDoubleValue() {
        return this.doubleValue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AttributeValueDouble{doubleValue=");
        sb.append(this.doubleValue);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AttributeValueDouble)) {
            return false;
        }
        return this.doubleValue.equals(((AttributeValueDouble) obj).getDoubleValue());
    }

    public int hashCode() {
        return this.doubleValue.hashCode() ^ 1000003;
    }
}
