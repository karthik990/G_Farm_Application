package p043io.opencensus.stats;

import p043io.opencensus.common.Function;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.stats.Aggregation */
public abstract class Aggregation {

    /* renamed from: io.opencensus.stats.Aggregation$Count */
    public static abstract class Count extends Aggregation {
        private static final Count INSTANCE = new AutoValue_Aggregation_Count();

        Count() {
            super();
        }

        public static Count create() {
            return INSTANCE;
        }

        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Distribution, T> function3, Function<? super LastValue, T> function4, Function<? super Aggregation, T> function5) {
            return function2.apply(this);
        }
    }

    /* renamed from: io.opencensus.stats.Aggregation$Distribution */
    public static abstract class Distribution extends Aggregation {
        public abstract BucketBoundaries getBucketBoundaries();

        Distribution() {
            super();
        }

        public static Distribution create(BucketBoundaries bucketBoundaries) {
            C5887Utils.checkNotNull(bucketBoundaries, "bucketBoundaries");
            return new AutoValue_Aggregation_Distribution(bucketBoundaries);
        }

        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Distribution, T> function3, Function<? super LastValue, T> function4, Function<? super Aggregation, T> function5) {
            return function3.apply(this);
        }
    }

    /* renamed from: io.opencensus.stats.Aggregation$LastValue */
    public static abstract class LastValue extends Aggregation {
        private static final LastValue INSTANCE = new AutoValue_Aggregation_LastValue();

        LastValue() {
            super();
        }

        public static LastValue create() {
            return INSTANCE;
        }

        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Distribution, T> function3, Function<? super LastValue, T> function4, Function<? super Aggregation, T> function5) {
            return function4.apply(this);
        }
    }

    @Deprecated
    /* renamed from: io.opencensus.stats.Aggregation$Mean */
    public static abstract class Mean extends Aggregation {
        private static final Mean INSTANCE = new AutoValue_Aggregation_Mean();

        Mean() {
            super();
        }

        public static Mean create() {
            return INSTANCE;
        }

        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Distribution, T> function3, Function<? super LastValue, T> function4, Function<? super Aggregation, T> function5) {
            return function5.apply(this);
        }
    }

    /* renamed from: io.opencensus.stats.Aggregation$Sum */
    public static abstract class Sum extends Aggregation {
        private static final Sum INSTANCE = new AutoValue_Aggregation_Sum();

        Sum() {
            super();
        }

        public static Sum create() {
            return INSTANCE;
        }

        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Distribution, T> function3, Function<? super LastValue, T> function4, Function<? super Aggregation, T> function5) {
            return function.apply(this);
        }
    }

    public abstract <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Distribution, T> function3, Function<? super LastValue, T> function4, Function<? super Aggregation, T> function5);

    private Aggregation() {
    }
}
