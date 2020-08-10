package p043io.opencensus.metrics.export;

import java.util.Collections;
import java.util.Set;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.metrics.export.MetricProducerManager */
public abstract class MetricProducerManager {

    /* renamed from: io.opencensus.metrics.export.MetricProducerManager$NoopMetricProducerManager */
    private static final class NoopMetricProducerManager extends MetricProducerManager {
        private NoopMetricProducerManager() {
        }

        public void add(MetricProducer metricProducer) {
            C5887Utils.checkNotNull(metricProducer, "metricProducer");
        }

        public void remove(MetricProducer metricProducer) {
            C5887Utils.checkNotNull(metricProducer, "metricProducer");
        }

        public Set<MetricProducer> getAllMetricProducer() {
            return Collections.emptySet();
        }
    }

    public abstract void add(MetricProducer metricProducer);

    public abstract Set<MetricProducer> getAllMetricProducer();

    public abstract void remove(MetricProducer metricProducer);

    static MetricProducerManager newNoopMetricProducerManager() {
        return new NoopMetricProducerManager();
    }
}
