package p043io.opencensus.trace.export;

import p043io.opencensus.trace.export.RunningSpanStore.Filter;

/* renamed from: io.opencensus.trace.export.AutoValue_RunningSpanStore_Filter */
final class AutoValue_RunningSpanStore_Filter extends Filter {
    private final int maxSpansToReturn;
    private final String spanName;

    AutoValue_RunningSpanStore_Filter(String str, int i) {
        if (str != null) {
            this.spanName = str;
            this.maxSpansToReturn = i;
            return;
        }
        throw new NullPointerException("Null spanName");
    }

    public String getSpanName() {
        return this.spanName;
    }

    public int getMaxSpansToReturn() {
        return this.maxSpansToReturn;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Filter{spanName=");
        sb.append(this.spanName);
        sb.append(", maxSpansToReturn=");
        sb.append(this.maxSpansToReturn);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Filter)) {
            return false;
        }
        Filter filter = (Filter) obj;
        if (!this.spanName.equals(filter.getSpanName()) || this.maxSpansToReturn != filter.getMaxSpansToReturn()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.spanName.hashCode() ^ 1000003) * 1000003) ^ this.maxSpansToReturn;
    }
}
