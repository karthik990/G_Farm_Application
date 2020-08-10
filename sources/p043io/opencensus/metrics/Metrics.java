package p043io.opencensus.metrics;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import p043io.opencensus.internal.Provider;
import p043io.opencensus.metrics.export.ExportComponent;

/* renamed from: io.opencensus.metrics.Metrics */
public final class Metrics {
    private static final Logger logger = Logger.getLogger(Metrics.class.getName());
    private static final MetricsComponent metricsComponent = loadMetricsComponent(MetricsComponent.class.getClassLoader());

    public static ExportComponent getExportComponent() {
        return metricsComponent.getExportComponent();
    }

    public static MetricRegistry getMetricRegistry() {
        return metricsComponent.getMetricRegistry();
    }

    static MetricsComponent loadMetricsComponent(@Nullable ClassLoader classLoader) {
        try {
            return (MetricsComponent) Provider.createInstance(Class.forName("io.opencensus.impl.metrics.MetricsComponentImpl", true, classLoader), MetricsComponent.class);
        } catch (ClassNotFoundException e) {
            logger.log(Level.FINE, "Couldn't load full implementation for MetricsComponent, now trying to load lite implementation.", e);
            try {
                return (MetricsComponent) Provider.createInstance(Class.forName("io.opencensus.impllite.metrics.MetricsComponentImplLite", true, classLoader), MetricsComponent.class);
            } catch (ClassNotFoundException e2) {
                logger.log(Level.FINE, "Couldn't load lite implementation for MetricsComponent, now using default implementation for MetricsComponent.", e2);
                return MetricsComponent.newNoopMetricsComponent();
            }
        }
    }

    private Metrics() {
    }
}
