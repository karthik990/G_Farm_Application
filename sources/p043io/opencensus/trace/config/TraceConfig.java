package p043io.opencensus.trace.config;

/* renamed from: io.opencensus.trace.config.TraceConfig */
public abstract class TraceConfig {
    private static final NoopTraceConfig NOOP_TRACE_CONFIG = new NoopTraceConfig();

    /* renamed from: io.opencensus.trace.config.TraceConfig$NoopTraceConfig */
    private static final class NoopTraceConfig extends TraceConfig {
        public void updateActiveTraceParams(TraceParams traceParams) {
        }

        private NoopTraceConfig() {
        }

        public TraceParams getActiveTraceParams() {
            return TraceParams.DEFAULT;
        }
    }

    public abstract TraceParams getActiveTraceParams();

    public abstract void updateActiveTraceParams(TraceParams traceParams);

    public static TraceConfig getNoopTraceConfig() {
        return NOOP_TRACE_CONFIG;
    }
}
