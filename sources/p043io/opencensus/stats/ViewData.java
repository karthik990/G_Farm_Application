package p043io.opencensus.stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import p043io.opencensus.common.Duration;
import p043io.opencensus.common.Function;
import p043io.opencensus.common.Functions;
import p043io.opencensus.common.Timestamp;
import p043io.opencensus.stats.Aggregation.Count;
import p043io.opencensus.stats.Aggregation.Distribution;
import p043io.opencensus.stats.Aggregation.LastValue;
import p043io.opencensus.stats.Aggregation.Mean;
import p043io.opencensus.stats.Aggregation.Sum;
import p043io.opencensus.stats.AggregationData.CountData;
import p043io.opencensus.stats.AggregationData.DistributionData;
import p043io.opencensus.stats.AggregationData.LastValueDataDouble;
import p043io.opencensus.stats.AggregationData.LastValueDataLong;
import p043io.opencensus.stats.AggregationData.MeanData;
import p043io.opencensus.stats.AggregationData.SumDataDouble;
import p043io.opencensus.stats.AggregationData.SumDataLong;
import p043io.opencensus.stats.Measure.MeasureDouble;
import p043io.opencensus.stats.Measure.MeasureLong;
import p043io.opencensus.stats.View.AggregationWindow;
import p043io.opencensus.stats.View.AggregationWindow.Cumulative;
import p043io.opencensus.stats.View.AggregationWindow.Interval;
import p043io.opencensus.tags.TagValue;

/* renamed from: io.opencensus.stats.ViewData */
public abstract class ViewData {

    @Deprecated
    /* renamed from: io.opencensus.stats.ViewData$AggregationWindowData */
    public static abstract class AggregationWindowData {

        @Deprecated
        /* renamed from: io.opencensus.stats.ViewData$AggregationWindowData$CumulativeData */
        public static abstract class CumulativeData extends AggregationWindowData {
            public abstract Timestamp getEnd();

            public abstract Timestamp getStart();

            CumulativeData() {
                super();
            }

            public final <T> T match(Function<? super CumulativeData, T> function, Function<? super IntervalData, T> function2, Function<? super AggregationWindowData, T> function3) {
                return function.apply(this);
            }

            public static CumulativeData create(Timestamp timestamp, Timestamp timestamp2) {
                if (timestamp.compareTo(timestamp2) <= 0) {
                    return new AutoValue_ViewData_AggregationWindowData_CumulativeData(timestamp, timestamp2);
                }
                throw new IllegalArgumentException("Start time is later than end time.");
            }
        }

        @Deprecated
        /* renamed from: io.opencensus.stats.ViewData$AggregationWindowData$IntervalData */
        public static abstract class IntervalData extends AggregationWindowData {
            public abstract Timestamp getEnd();

            IntervalData() {
                super();
            }

            public final <T> T match(Function<? super CumulativeData, T> function, Function<? super IntervalData, T> function2, Function<? super AggregationWindowData, T> function3) {
                return function2.apply(this);
            }

            public static IntervalData create(Timestamp timestamp) {
                return new AutoValue_ViewData_AggregationWindowData_IntervalData(timestamp);
            }
        }

        public abstract <T> T match(Function<? super CumulativeData, T> function, Function<? super IntervalData, T> function2, Function<? super AggregationWindowData, T> function3);

        private AggregationWindowData() {
        }
    }

    public abstract Map<List<TagValue>, AggregationData> getAggregationMap();

    public abstract Timestamp getEnd();

    public abstract Timestamp getStart();

    public abstract View getView();

    @Deprecated
    public abstract AggregationWindowData getWindowData();

    ViewData() {
    }

    @Deprecated
    public static ViewData create(final View view, Map<? extends List<TagValue>, ? extends AggregationData> map, AggregationWindowData aggregationWindowData) {
        checkWindow(view.getWindow(), aggregationWindowData);
        final HashMap hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            checkAggregation(view.getAggregation(), (AggregationData) entry.getValue(), view.getMeasure());
            hashMap.put(Collections.unmodifiableList(new ArrayList((Collection) entry.getKey())), entry.getValue());
        }
        return (ViewData) aggregationWindowData.match(new Function<CumulativeData, ViewData>() {
            public ViewData apply(CumulativeData cumulativeData) {
                return ViewData.createInternal(View.this, Collections.unmodifiableMap(hashMap), cumulativeData, cumulativeData.getStart(), cumulativeData.getEnd());
            }
        }, new Function<IntervalData, ViewData>() {
            public ViewData apply(IntervalData intervalData) {
                Duration duration = ((Interval) View.this.getWindow()).getDuration();
                return ViewData.createInternal(View.this, Collections.unmodifiableMap(hashMap), intervalData, intervalData.getEnd().addDuration(Duration.create(-duration.getSeconds(), -duration.getNanos())), intervalData.getEnd());
            }
        }, Functions.throwAssertionError());
    }

    public static ViewData create(View view, Map<? extends List<TagValue>, ? extends AggregationData> map, Timestamp timestamp, Timestamp timestamp2) {
        HashMap hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            checkAggregation(view.getAggregation(), (AggregationData) entry.getValue(), view.getMeasure());
            hashMap.put(Collections.unmodifiableList(new ArrayList((Collection) entry.getKey())), entry.getValue());
        }
        return createInternal(view, Collections.unmodifiableMap(hashMap), CumulativeData.create(timestamp, timestamp2), timestamp, timestamp2);
    }

    /* access modifiers changed from: private */
    public static ViewData createInternal(View view, Map<List<TagValue>, AggregationData> map, AggregationWindowData aggregationWindowData, Timestamp timestamp, Timestamp timestamp2) {
        AutoValue_ViewData autoValue_ViewData = new AutoValue_ViewData(view, map, aggregationWindowData, timestamp, timestamp2);
        return autoValue_ViewData;
    }

    private static void checkWindow(AggregationWindow aggregationWindow, final AggregationWindowData aggregationWindowData) {
        aggregationWindow.match(new Function<Cumulative, Void>() {
            public Void apply(Cumulative cumulative) {
                AggregationWindowData aggregationWindowData = AggregationWindowData.this;
                ViewData.throwIfWindowMismatch(aggregationWindowData instanceof CumulativeData, cumulative, aggregationWindowData);
                return null;
            }
        }, new Function<Interval, Void>() {
            public Void apply(Interval interval) {
                AggregationWindowData aggregationWindowData = AggregationWindowData.this;
                ViewData.throwIfWindowMismatch(aggregationWindowData instanceof IntervalData, interval, aggregationWindowData);
                return null;
            }
        }, Functions.throwAssertionError());
    }

    /* access modifiers changed from: private */
    public static void throwIfWindowMismatch(boolean z, AggregationWindow aggregationWindow, AggregationWindowData aggregationWindowData) {
        if (!z) {
            throw new IllegalArgumentException(createErrorMessageForWindow(aggregationWindow, aggregationWindowData));
        }
    }

    private static String createErrorMessageForWindow(AggregationWindow aggregationWindow, AggregationWindowData aggregationWindowData) {
        StringBuilder sb = new StringBuilder();
        sb.append("AggregationWindow and AggregationWindowData types mismatch. AggregationWindow: ");
        sb.append(aggregationWindow.getClass().getSimpleName());
        sb.append(" AggregationWindowData: ");
        sb.append(aggregationWindowData.getClass().getSimpleName());
        return sb.toString();
    }

    private static void checkAggregation(final Aggregation aggregation, final AggregationData aggregationData, final Measure measure) {
        aggregation.match(new Function<Sum, Void>() {
            public Void apply(Sum sum) {
                Measure.this.match(new Function<MeasureDouble, Void>() {
                    public Void apply(MeasureDouble measureDouble) {
                        ViewData.throwIfAggregationMismatch(aggregationData instanceof SumDataDouble, aggregation, aggregationData);
                        return null;
                    }
                }, new Function<MeasureLong, Void>() {
                    public Void apply(MeasureLong measureLong) {
                        ViewData.throwIfAggregationMismatch(aggregationData instanceof SumDataLong, aggregation, aggregationData);
                        return null;
                    }
                }, Functions.throwAssertionError());
                return null;
            }
        }, new Function<Count, Void>() {
            public Void apply(Count count) {
                AggregationData aggregationData = AggregationData.this;
                ViewData.throwIfAggregationMismatch(aggregationData instanceof CountData, aggregation, aggregationData);
                return null;
            }
        }, new Function<Distribution, Void>() {
            public Void apply(Distribution distribution) {
                AggregationData aggregationData = AggregationData.this;
                ViewData.throwIfAggregationMismatch(aggregationData instanceof DistributionData, aggregation, aggregationData);
                return null;
            }
        }, new Function<LastValue, Void>() {
            public Void apply(LastValue lastValue) {
                Measure.this.match(new Function<MeasureDouble, Void>() {
                    public Void apply(MeasureDouble measureDouble) {
                        ViewData.throwIfAggregationMismatch(aggregationData instanceof LastValueDataDouble, aggregation, aggregationData);
                        return null;
                    }
                }, new Function<MeasureLong, Void>() {
                    public Void apply(MeasureLong measureLong) {
                        ViewData.throwIfAggregationMismatch(aggregationData instanceof LastValueDataLong, aggregation, aggregationData);
                        return null;
                    }
                }, Functions.throwAssertionError());
                return null;
            }
        }, new Function<Aggregation, Void>() {
            public Void apply(Aggregation aggregation) {
                if (aggregation instanceof Mean) {
                    AggregationData aggregationData = AggregationData.this;
                    ViewData.throwIfAggregationMismatch(aggregationData instanceof MeanData, aggregation, aggregationData);
                    return null;
                }
                throw new AssertionError();
            }
        });
    }

    /* access modifiers changed from: private */
    public static void throwIfAggregationMismatch(boolean z, Aggregation aggregation, AggregationData aggregationData) {
        if (!z) {
            throw new IllegalArgumentException(createErrorMessageForAggregation(aggregation, aggregationData));
        }
    }

    private static String createErrorMessageForAggregation(Aggregation aggregation, AggregationData aggregationData) {
        StringBuilder sb = new StringBuilder();
        sb.append("Aggregation and AggregationData types mismatch. Aggregation: ");
        sb.append(aggregation.getClass().getSimpleName());
        sb.append(" AggregationData: ");
        sb.append(aggregationData.getClass().getSimpleName());
        return sb.toString();
    }
}
