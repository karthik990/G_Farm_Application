package p043io.opencensus.trace;

import p043io.opencensus.trace.Tracestate.Entry;

/* renamed from: io.opencensus.trace.AutoValue_Tracestate_Entry */
final class AutoValue_Tracestate_Entry extends Entry {
    private final String key;
    private final String value;

    AutoValue_Tracestate_Entry(String str, String str2) {
        if (str != null) {
            this.key = str;
            if (str2 != null) {
                this.value = str2;
                return;
            }
            throw new NullPointerException("Null value");
        }
        throw new NullPointerException("Null key");
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Entry{key=");
        sb.append(this.key);
        sb.append(", value=");
        sb.append(this.value);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Entry)) {
            return false;
        }
        Entry entry = (Entry) obj;
        if (!this.key.equals(entry.getKey()) || !this.value.equals(entry.getValue())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.key.hashCode() ^ 1000003) * 1000003) ^ this.value.hashCode();
    }
}
