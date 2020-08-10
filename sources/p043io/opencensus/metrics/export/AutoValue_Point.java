package p043io.opencensus.metrics.export;

import p043io.opencensus.common.Timestamp;

/* renamed from: io.opencensus.metrics.export.AutoValue_Point */
final class AutoValue_Point extends Point {
    private final Timestamp timestamp;
    private final Value value;

    AutoValue_Point(Value value2, Timestamp timestamp2) {
        if (value2 != null) {
            this.value = value2;
            if (timestamp2 != null) {
                this.timestamp = timestamp2;
                return;
            }
            throw new NullPointerException("Null timestamp");
        }
        throw new NullPointerException("Null value");
    }

    public Value getValue() {
        return this.value;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Point{value=");
        sb.append(this.value);
        sb.append(", timestamp=");
        sb.append(this.timestamp);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point point = (Point) obj;
        if (!this.value.equals(point.getValue()) || !this.timestamp.equals(point.getTimestamp())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.value.hashCode() ^ 1000003) * 1000003) ^ this.timestamp.hashCode();
    }
}
