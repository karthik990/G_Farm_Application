package p043io.opencensus.metrics.export;

import java.util.Collection;

/* renamed from: io.opencensus.metrics.export.MetricProducer */
public abstract class MetricProducer {
    public abstract Collection<Metric> getMetrics();
}
