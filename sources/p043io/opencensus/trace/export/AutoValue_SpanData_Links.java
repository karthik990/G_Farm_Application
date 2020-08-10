package p043io.opencensus.trace.export;

import java.util.List;
import p043io.opencensus.trace.Link;
import p043io.opencensus.trace.export.SpanData.Links;

/* renamed from: io.opencensus.trace.export.AutoValue_SpanData_Links */
final class AutoValue_SpanData_Links extends Links {
    private final int droppedLinksCount;
    private final List<Link> links;

    AutoValue_SpanData_Links(List<Link> list, int i) {
        if (list != null) {
            this.links = list;
            this.droppedLinksCount = i;
            return;
        }
        throw new NullPointerException("Null links");
    }

    public List<Link> getLinks() {
        return this.links;
    }

    public int getDroppedLinksCount() {
        return this.droppedLinksCount;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Links{links=");
        sb.append(this.links);
        sb.append(", droppedLinksCount=");
        sb.append(this.droppedLinksCount);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Links)) {
            return false;
        }
        Links links2 = (Links) obj;
        if (!this.links.equals(links2.getLinks()) || this.droppedLinksCount != links2.getDroppedLinksCount()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.links.hashCode() ^ 1000003) * 1000003) ^ this.droppedLinksCount;
    }
}
