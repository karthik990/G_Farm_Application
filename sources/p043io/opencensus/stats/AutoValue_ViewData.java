package p043io.opencensus.stats;

import java.util.List;
import java.util.Map;
import p043io.opencensus.common.Timestamp;
import p043io.opencensus.stats.ViewData.AggregationWindowData;
import p043io.opencensus.tags.TagValue;

/* renamed from: io.opencensus.stats.AutoValue_ViewData */
final class AutoValue_ViewData extends ViewData {
    private final Map<List<TagValue>, AggregationData> aggregationMap;
    private final Timestamp end;
    private final Timestamp start;
    private final View view;
    private final AggregationWindowData windowData;

    AutoValue_ViewData(View view2, Map<List<TagValue>, AggregationData> map, AggregationWindowData aggregationWindowData, Timestamp timestamp, Timestamp timestamp2) {
        if (view2 != null) {
            this.view = view2;
            if (map != null) {
                this.aggregationMap = map;
                if (aggregationWindowData != null) {
                    this.windowData = aggregationWindowData;
                    if (timestamp != null) {
                        this.start = timestamp;
                        if (timestamp2 != null) {
                            this.end = timestamp2;
                            return;
                        }
                        throw new NullPointerException("Null end");
                    }
                    throw new NullPointerException("Null start");
                }
                throw new NullPointerException("Null windowData");
            }
            throw new NullPointerException("Null aggregationMap");
        }
        throw new NullPointerException("Null view");
    }

    public View getView() {
        return this.view;
    }

    public Map<List<TagValue>, AggregationData> getAggregationMap() {
        return this.aggregationMap;
    }

    @Deprecated
    public AggregationWindowData getWindowData() {
        return this.windowData;
    }

    public Timestamp getStart() {
        return this.start;
    }

    public Timestamp getEnd() {
        return this.end;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ViewData{view=");
        sb.append(this.view);
        sb.append(", aggregationMap=");
        sb.append(this.aggregationMap);
        sb.append(", windowData=");
        sb.append(this.windowData);
        sb.append(", start=");
        sb.append(this.start);
        sb.append(", end=");
        sb.append(this.end);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ViewData)) {
            return false;
        }
        ViewData viewData = (ViewData) obj;
        if (!this.view.equals(viewData.getView()) || !this.aggregationMap.equals(viewData.getAggregationMap()) || !this.windowData.equals(viewData.getWindowData()) || !this.start.equals(viewData.getStart()) || !this.end.equals(viewData.getEnd())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((((((this.view.hashCode() ^ 1000003) * 1000003) ^ this.aggregationMap.hashCode()) * 1000003) ^ this.windowData.hashCode()) * 1000003) ^ this.start.hashCode()) * 1000003) ^ this.end.hashCode();
    }
}
