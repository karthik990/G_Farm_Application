package p043io.opencensus.metrics.export;

/* renamed from: io.opencensus.metrics.export.AutoValue_Value_ValueLong */
final class AutoValue_Value_ValueLong extends ValueLong {
    private final long value;

    AutoValue_Value_ValueLong(long j) {
        this.value = j;
    }

    /* access modifiers changed from: 0000 */
    public long getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ValueLong{value=");
        sb.append(this.value);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ValueLong)) {
            return false;
        }
        if (this.value != ((ValueLong) obj).getValue()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long j = (long) 1000003;
        long j2 = this.value;
        return (int) (j ^ (j2 ^ (j2 >>> 32)));
    }
}
