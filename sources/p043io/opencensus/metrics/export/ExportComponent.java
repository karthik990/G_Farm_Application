package p043io.opencensus.metrics.export;

/* renamed from: io.opencensus.metrics.export.ExportComponent */
public abstract class ExportComponent {

    /* renamed from: io.opencensus.metrics.export.ExportComponent$NoopExportComponent */
    private static final class NoopExportComponent extends ExportComponent {
        private static final MetricProducerManager METRIC_PRODUCER_MANAGER = MetricProducerManager.newNoopMetricProducerManager();

        private NoopExportComponent() {
        }

        public MetricProducerManager getMetricProducerManager() {
            return METRIC_PRODUCER_MANAGER;
        }
    }

    public abstract MetricProducerManager getMetricProducerManager();

    public static ExportComponent newNoopExportComponent() {
        return new NoopExportComponent();
    }
}
