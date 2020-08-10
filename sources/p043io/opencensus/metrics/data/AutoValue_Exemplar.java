package p043io.opencensus.metrics.data;

import java.util.Map;
import p043io.opencensus.common.Timestamp;

/* renamed from: io.opencensus.metrics.data.AutoValue_Exemplar */
final class AutoValue_Exemplar extends Exemplar {
    private final Map<String, AttachmentValue> attachments;
    private final Timestamp timestamp;
    private final double value;

    AutoValue_Exemplar(double d, Timestamp timestamp2, Map<String, AttachmentValue> map) {
        this.value = d;
        if (timestamp2 != null) {
            this.timestamp = timestamp2;
            if (map != null) {
                this.attachments = map;
                return;
            }
            throw new NullPointerException("Null attachments");
        }
        throw new NullPointerException("Null timestamp");
    }

    public double getValue() {
        return this.value;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public Map<String, AttachmentValue> getAttachments() {
        return this.attachments;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Exemplar{value=");
        sb.append(this.value);
        sb.append(", timestamp=");
        sb.append(this.timestamp);
        sb.append(", attachments=");
        sb.append(this.attachments);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Exemplar)) {
            return false;
        }
        Exemplar exemplar = (Exemplar) obj;
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(exemplar.getValue()) || !this.timestamp.equals(exemplar.getTimestamp()) || !this.attachments.equals(exemplar.getAttachments())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return this.attachments.hashCode() ^ ((this.timestamp.hashCode() ^ (((int) (((long) 1000003) ^ ((Double.doubleToLongBits(this.value) >>> 32) ^ Double.doubleToLongBits(this.value)))) * 1000003)) * 1000003);
    }
}
