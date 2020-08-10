package p043io.opencensus.trace.export;

/* renamed from: io.opencensus.trace.export.ExportComponent */
public abstract class ExportComponent {

    /* renamed from: io.opencensus.trace.export.ExportComponent$NoopExportComponent */
    private static final class NoopExportComponent extends ExportComponent {
        private final SampledSpanStore noopSampledSpanStore;

        private NoopExportComponent() {
            this.noopSampledSpanStore = SampledSpanStore.newNoopSampledSpanStore();
        }

        public SpanExporter getSpanExporter() {
            return SpanExporter.getNoopSpanExporter();
        }

        public RunningSpanStore getRunningSpanStore() {
            return RunningSpanStore.getNoopRunningSpanStore();
        }

        public SampledSpanStore getSampledSpanStore() {
            return this.noopSampledSpanStore;
        }
    }

    public abstract RunningSpanStore getRunningSpanStore();

    public abstract SampledSpanStore getSampledSpanStore();

    public abstract SpanExporter getSpanExporter();

    public void shutdown() {
    }

    public static ExportComponent newNoopExportComponent() {
        return new NoopExportComponent();
    }
}
