package p043io.opencensus.metrics.export;

import p043io.opencensus.common.Timestamp;

/* renamed from: io.opencensus.metrics.export.Point */
public abstract class Point {
    public abstract Timestamp getTimestamp();

    public abstract Value getValue();

    Point() {
    }

    public static Point create(Value value, Timestamp timestamp) {
        return new AutoValue_Point(value, timestamp);
    }
}
