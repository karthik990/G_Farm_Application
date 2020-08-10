package p043io.opencensus.metrics.export;

import java.util.List;

/* renamed from: io.opencensus.metrics.export.AutoValue_Metric */
final class AutoValue_Metric extends Metric {
    private final MetricDescriptor metricDescriptor;
    private final List<TimeSeries> timeSeriesList;

    AutoValue_Metric(MetricDescriptor metricDescriptor2, List<TimeSeries> list) {
        if (metricDescriptor2 != null) {
            this.metricDescriptor = metricDescriptor2;
            if (list != null) {
                this.timeSeriesList = list;
                return;
            }
            throw new NullPointerException("Null timeSeriesList");
        }
        throw new NullPointerException("Null metricDescriptor");
    }

    public MetricDescriptor getMetricDescriptor() {
        return this.metricDescriptor;
    }

    public List<TimeSeries> getTimeSeriesList() {
        return this.timeSeriesList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Metric{metricDescriptor=");
        sb.append(this.metricDescriptor);
        sb.append(", timeSeriesList=");
        sb.append(this.timeSeriesList);
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Metric)) {
            return false;
        }
        Metric metric = (Metric) obj;
        if (!this.metricDescriptor.equals(metric.getMetricDescriptor()) || !this.timeSeriesList.equals(metric.getTimeSeriesList())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.metricDescriptor.hashCode() ^ 1000003) * 1000003) ^ this.timeSeriesList.hashCode();
    }
}
