package p043io.opencensus.trace.export;

import java.util.Map;
import p043io.opencensus.trace.AttributeValue;
import p043io.opencensus.trace.export.SpanData.Attributes;

/* renamed from: io.opencensus.trace.export.AutoValue_SpanData_Attributes */
final class AutoValue_SpanData_Attributes extends Attributes {
    private final Map<String, AttributeValue> attributeMap;
    private final int droppedAttributesCount;

    AutoValue_SpanData_Attributes(Map<String, AttributeValue> map, int i) {
        if (map != null) {
            this.attributeMap = map;
            this.droppedAttributesCount = i;
            return;
        }
        throw new NullPointerException("Null attributeMap");
    }

    public Map<String, AttributeValue> getAttributeMap() {
        return this.attributeMap;
    }

    public int getDroppedAttributesCount() {
        return this.droppedAttributesCount;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Attributes{attributeMap=");
        sb.append(this.attributeMap);
        sb.append(", droppedAttributesCount=");
        sb.append(this.droppedAttributesCount);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Attributes)) {
            return false;
        }
        Attributes attributes = (Attributes) obj;
        if (!this.attributeMap.equals(attributes.getAttributeMap()) || this.droppedAttributesCount != attributes.getDroppedAttributesCount()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.attributeMap.hashCode() ^ 1000003) * 1000003) ^ this.droppedAttributesCount;
    }
}
