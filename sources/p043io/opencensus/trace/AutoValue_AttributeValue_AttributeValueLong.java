package p043io.opencensus.trace;

/* renamed from: io.opencensus.trace.AutoValue_AttributeValue_AttributeValueLong */
final class AutoValue_AttributeValue_AttributeValueLong extends AttributeValueLong {
    private final Long longValue;

    AutoValue_AttributeValue_AttributeValueLong(Long l) {
        if (l != null) {
            this.longValue = l;
            return;
        }
        throw new NullPointerException("Null longValue");
    }

    /* access modifiers changed from: 0000 */
    public Long getLongValue() {
        return this.longValue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AttributeValueLong{longValue=");
        sb.append(this.longValue);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AttributeValueLong)) {
            return false;
        }
        return this.longValue.equals(((AttributeValueLong) obj).getLongValue());
    }

    public int hashCode() {
        return this.longValue.hashCode() ^ 1000003;
    }
}
