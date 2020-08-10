package p043io.opencensus.metrics;

import p043io.opencensus.metrics.export.ExportComponent;

/* renamed from: io.opencensus.metrics.MetricsComponent */
public abstract class MetricsComponent {

    /* renamed from: io.opencensus.metrics.MetricsComponent$NoopMetricsComponent */
    private static final class NoopMetricsComponent extends MetricsComponent {
        private static final ExportComponent EXPORT_COMPONENT = ExportComponent.newNoopExportComponent();
        private static final MetricRegistry METRIC_REGISTRY = MetricRegistry.newNoopMetricRegistry();

        private NoopMetricsComponent() {
        }

        public ExportComponent getExportComponent() {
            return EXPORT_COMPONENT;
        }

        public MetricRegistry getMetricRegistry() {
            return METRIC_REGISTRY;
        }
    }

    public abstract ExportComponent getExportComponent();

    public abstract MetricRegistry getMetricRegistry();

    static MetricsComponent newNoopMetricsComponent() {
        return new NoopMetricsComponent();
    }
}
