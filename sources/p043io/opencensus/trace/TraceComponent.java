package p043io.opencensus.trace;

import p043io.opencensus.common.Clock;
import p043io.opencensus.internal.ZeroTimeClock;
import p043io.opencensus.trace.config.TraceConfig;
import p043io.opencensus.trace.export.ExportComponent;
import p043io.opencensus.trace.propagation.PropagationComponent;

/* renamed from: io.opencensus.trace.TraceComponent */
public abstract class TraceComponent {

    /* renamed from: io.opencensus.trace.TraceComponent$NoopTraceComponent */
    private static final class NoopTraceComponent extends TraceComponent {
        private final ExportComponent noopExportComponent;

        public Tracer getTracer() {
            return Tracer.getNoopTracer();
        }

        public PropagationComponent getPropagationComponent() {
            return PropagationComponent.getNoopPropagationComponent();
        }

        public Clock getClock() {
            return ZeroTimeClock.getInstance();
        }

        public ExportComponent getExportComponent() {
            return this.noopExportComponent;
        }

        public TraceConfig getTraceConfig() {
            return TraceConfig.getNoopTraceConfig();
        }

        private NoopTraceComponent() {
            this.noopExportComponent = ExportComponent.newNoopExportComponent();
        }
    }

    public abstract Clock getClock();

    public abstract ExportComponent getExportComponent();

    public abstract PropagationComponent getPropagationComponent();

    public abstract TraceConfig getTraceConfig();

    public abstract Tracer getTracer();

    static TraceComponent newNoopTraceComponent() {
        return new NoopTraceComponent();
    }
}
