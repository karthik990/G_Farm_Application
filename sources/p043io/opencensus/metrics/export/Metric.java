package p043io.opencensus.metrics.export;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import p043io.opencensus.internal.C5887Utils;
import p043io.opencensus.metrics.export.MetricDescriptor.Type;

/* renamed from: io.opencensus.metrics.export.Metric */
public abstract class Metric {
    public abstract MetricDescriptor getMetricDescriptor();

    public abstract List<TimeSeries> getTimeSeriesList();

    Metric() {
    }

    public static Metric create(MetricDescriptor metricDescriptor, List<TimeSeries> list) {
        C5887Utils.checkListElementNotNull((List) C5887Utils.checkNotNull(list, "timeSeriesList"), "timeSeries");
        return createInternal(metricDescriptor, Collections.unmodifiableList(new ArrayList(list)));
    }

    public static Metric createWithOneTimeSeries(MetricDescriptor metricDescriptor, TimeSeries timeSeries) {
        return createInternal(metricDescriptor, Collections.singletonList(C5887Utils.checkNotNull(timeSeries, "timeSeries")));
    }

    private static Metric createInternal(MetricDescriptor metricDescriptor, List<TimeSeries> list) {
        C5887Utils.checkNotNull(metricDescriptor, "metricDescriptor");
        checkTypeMatch(metricDescriptor.getType(), list);
        return new AutoValue_Metric(metricDescriptor, list);
    }

    private static void checkTypeMatch(Type type, List<TimeSeries> list) {
        for (TimeSeries points : list) {
            for (Point value : points.getPoints()) {
                Value value2 = value.getValue();
                String simpleName = value2.getClass().getSuperclass() != null ? value2.getClass().getSuperclass().getSimpleName() : "";
                String str = "Type mismatch: %s, %s.";
                switch (type) {
                    case GAUGE_INT64:
                    case CUMULATIVE_INT64:
                        C5887Utils.checkArgument(value2 instanceof ValueLong, str, type, simpleName);
                        break;
                    case CUMULATIVE_DOUBLE:
                    case GAUGE_DOUBLE:
                        C5887Utils.checkArgument(value2 instanceof ValueDouble, str, type, simpleName);
                        break;
                    case GAUGE_DISTRIBUTION:
                    case CUMULATIVE_DISTRIBUTION:
                        C5887Utils.checkArgument(value2 instanceof ValueDistribution, str, type, simpleName);
                        break;
                    case SUMMARY:
                        C5887Utils.checkArgument(value2 instanceof ValueSummary, str, type, simpleName);
                        break;
                }
            }
        }
    }
}
