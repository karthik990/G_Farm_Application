package p043io.opencensus.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import p043io.opencensus.common.Duration;
import p043io.opencensus.common.Function;
import p043io.opencensus.internal.C5887Utils;
import p043io.opencensus.internal.StringUtils;
import p043io.opencensus.tags.TagKey;

/* renamed from: io.opencensus.stats.View */
public abstract class View {
    static final int NAME_MAX_LENGTH = 255;
    private static final Comparator<TagKey> TAG_KEY_COMPARATOR = new Comparator<TagKey>() {
        public int compare(TagKey tagKey, TagKey tagKey2) {
            return tagKey.getName().compareToIgnoreCase(tagKey2.getName());
        }
    };

    @Deprecated
    /* renamed from: io.opencensus.stats.View$AggregationWindow */
    public static abstract class AggregationWindow {

        @Deprecated
        /* renamed from: io.opencensus.stats.View$AggregationWindow$Cumulative */
        public static abstract class Cumulative extends AggregationWindow {
            private static final Cumulative CUMULATIVE = new AutoValue_View_AggregationWindow_Cumulative();

            Cumulative() {
                super();
            }

            public static Cumulative create() {
                return CUMULATIVE;
            }

            public final <T> T match(Function<? super Cumulative, T> function, Function<? super Interval, T> function2, Function<? super AggregationWindow, T> function3) {
                return function.apply(this);
            }
        }

        @Deprecated
        /* renamed from: io.opencensus.stats.View$AggregationWindow$Interval */
        public static abstract class Interval extends AggregationWindow {
            private static final Duration ZERO = Duration.create(0, 0);

            public abstract Duration getDuration();

            Interval() {
                super();
            }

            public static Interval create(Duration duration) {
                C5887Utils.checkArgument(duration.compareTo(ZERO) > 0, "Duration must be positive");
                return new AutoValue_View_AggregationWindow_Interval(duration);
            }

            public final <T> T match(Function<? super Cumulative, T> function, Function<? super Interval, T> function2, Function<? super AggregationWindow, T> function3) {
                return function2.apply(this);
            }
        }

        public abstract <T> T match(Function<? super Cumulative, T> function, Function<? super Interval, T> function2, Function<? super AggregationWindow, T> function3);

        private AggregationWindow() {
        }
    }

    /* renamed from: io.opencensus.stats.View$Name */
    public static abstract class Name {
        public abstract String asString();

        Name() {
        }

        public static Name create(String str) {
            C5887Utils.checkArgument(StringUtils.isPrintableString(str) && str.length() <= 255, "Name should be a ASCII string with a length no greater than 255 characters.");
            return new AutoValue_View_Name(str);
        }
    }

    public abstract Aggregation getAggregation();

    public abstract List<TagKey> getColumns();

    public abstract String getDescription();

    public abstract Measure getMeasure();

    public abstract Name getName();

    @Deprecated
    public abstract AggregationWindow getWindow();

    View() {
    }

    @Deprecated
    public static View create(Name name, String str, Measure measure, Aggregation aggregation, List<TagKey> list, AggregationWindow aggregationWindow) {
        C5887Utils.checkArgument(new HashSet(list).size() == list.size(), "Columns have duplicate.");
        ArrayList arrayList = new ArrayList(list);
        Collections.sort(arrayList, TAG_KEY_COMPARATOR);
        AutoValue_View autoValue_View = new AutoValue_View(name, str, measure, aggregation, Collections.unmodifiableList(arrayList), aggregationWindow);
        return autoValue_View;
    }

    public static View create(Name name, String str, Measure measure, Aggregation aggregation, List<TagKey> list) {
        C5887Utils.checkArgument(new HashSet(list).size() == list.size(), "Columns have duplicate.");
        return create(name, str, measure, aggregation, list, Cumulative.create());
    }
}
