package p043io.opencensus.stats;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import p043io.opencensus.common.Functions;
import p043io.opencensus.common.Timestamp;
import p043io.opencensus.internal.C5887Utils;
import p043io.opencensus.stats.Measure.MeasureDouble;
import p043io.opencensus.stats.Measure.MeasureLong;
import p043io.opencensus.stats.View.AggregationWindow.Interval;
import p043io.opencensus.stats.View.Name;
import p043io.opencensus.stats.ViewData.AggregationWindowData;
import p043io.opencensus.stats.ViewData.AggregationWindowData.CumulativeData;
import p043io.opencensus.stats.ViewData.AggregationWindowData.IntervalData;
import p043io.opencensus.tags.TagContext;

/* renamed from: io.opencensus.stats.NoopStats */
final class NoopStats {

    /* renamed from: io.opencensus.stats.NoopStats$NoopMeasureMap */
    private static final class NoopMeasureMap extends MeasureMap {
        private static final Logger logger = Logger.getLogger(NoopMeasureMap.class.getName());
        private boolean hasUnsupportedValues;

        public void record() {
        }

        private NoopMeasureMap() {
        }

        public MeasureMap put(MeasureDouble measureDouble, double d) {
            if (d < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                this.hasUnsupportedValues = true;
            }
            return this;
        }

        public MeasureMap put(MeasureLong measureLong, long j) {
            if (j < 0) {
                this.hasUnsupportedValues = true;
            }
            return this;
        }

        public void record(TagContext tagContext) {
            C5887Utils.checkNotNull(tagContext, "tags");
            if (this.hasUnsupportedValues) {
                logger.log(Level.WARNING, "Dropping values, value to record must be non-negative.");
            }
        }
    }

    /* renamed from: io.opencensus.stats.NoopStats$NoopStatsComponent */
    private static final class NoopStatsComponent extends StatsComponent {
        private volatile boolean isRead;
        private final ViewManager viewManager;

        private NoopStatsComponent() {
            this.viewManager = NoopStats.newNoopViewManager();
        }

        public ViewManager getViewManager() {
            return this.viewManager;
        }

        public StatsRecorder getStatsRecorder() {
            return NoopStats.getNoopStatsRecorder();
        }

        public StatsCollectionState getState() {
            this.isRead = true;
            return StatsCollectionState.DISABLED;
        }

        @Deprecated
        public void setState(StatsCollectionState statsCollectionState) {
            C5887Utils.checkNotNull(statsCollectionState, PostalAddressParser.REGION_KEY);
            C5887Utils.checkState(!this.isRead, "State was already read, cannot set state.");
        }
    }

    /* renamed from: io.opencensus.stats.NoopStats$NoopStatsRecorder */
    private static final class NoopStatsRecorder extends StatsRecorder {
        static final StatsRecorder INSTANCE = new NoopStatsRecorder();

        private NoopStatsRecorder() {
        }

        public MeasureMap newMeasureMap() {
            return NoopStats.newNoopMeasureMap();
        }
    }

    /* renamed from: io.opencensus.stats.NoopStats$NoopViewManager */
    private static final class NoopViewManager extends ViewManager {
        private static final Timestamp ZERO_TIMESTAMP = Timestamp.create(0, 0);
        @Nullable
        private volatile Set<View> exportedViews;
        private final Map<Name, View> registeredViews;

        private NoopViewManager() {
            this.registeredViews = new HashMap();
        }

        /* JADX WARNING: Removed duplicated region for block: B:13:0x002a  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void registerView(p043io.opencensus.stats.View r5) {
            /*
                r4 = this;
                java.lang.String r0 = "newView"
                p043io.opencensus.internal.C5887Utils.checkNotNull(r5, r0)
                java.util.Map<io.opencensus.stats.View$Name, io.opencensus.stats.View> r0 = r4.registeredViews
                monitor-enter(r0)
                r1 = 0
                r4.exportedViews = r1     // Catch:{ all -> 0x0035 }
                java.util.Map<io.opencensus.stats.View$Name, io.opencensus.stats.View> r1 = r4.registeredViews     // Catch:{ all -> 0x0035 }
                io.opencensus.stats.View$Name r2 = r5.getName()     // Catch:{ all -> 0x0035 }
                java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x0035 }
                io.opencensus.stats.View r1 = (p043io.opencensus.stats.View) r1     // Catch:{ all -> 0x0035 }
                if (r1 == 0) goto L_0x0022
                boolean r2 = r5.equals(r1)     // Catch:{ all -> 0x0035 }
                if (r2 == 0) goto L_0x0020
                goto L_0x0022
            L_0x0020:
                r2 = 0
                goto L_0x0023
            L_0x0022:
                r2 = 1
            L_0x0023:
                java.lang.String r3 = "A different view with the same name already exists."
                p043io.opencensus.internal.C5887Utils.checkArgument(r2, r3)     // Catch:{ all -> 0x0035 }
                if (r1 != 0) goto L_0x0033
                java.util.Map<io.opencensus.stats.View$Name, io.opencensus.stats.View> r1 = r4.registeredViews     // Catch:{ all -> 0x0035 }
                io.opencensus.stats.View$Name r2 = r5.getName()     // Catch:{ all -> 0x0035 }
                r1.put(r2, r5)     // Catch:{ all -> 0x0035 }
            L_0x0033:
                monitor-exit(r0)     // Catch:{ all -> 0x0035 }
                return
            L_0x0035:
                r5 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0035 }
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.opencensus.stats.NoopStats.NoopViewManager.registerView(io.opencensus.stats.View):void");
        }

        @Nullable
        public ViewData getView(Name name) {
            C5887Utils.checkNotNull(name, PostalAddressParser.USER_ADDRESS_NAME_KEY);
            synchronized (this.registeredViews) {
                View view = (View) this.registeredViews.get(name);
                if (view == null) {
                    return null;
                }
                ViewData create = ViewData.create(view, Collections.emptyMap(), (AggregationWindowData) view.getWindow().match(Functions.returnConstant(CumulativeData.create(ZERO_TIMESTAMP, ZERO_TIMESTAMP)), Functions.returnConstant(IntervalData.create(ZERO_TIMESTAMP)), Functions.throwAssertionError()));
                return create;
            }
        }

        public Set<View> getAllExportedViews() {
            Set<View> set = this.exportedViews;
            if (set == null) {
                synchronized (this.registeredViews) {
                    set = filterExportedViews(this.registeredViews.values());
                    this.exportedViews = set;
                }
            }
            return set;
        }

        private static Set<View> filterExportedViews(Collection<View> collection) {
            HashSet hashSet = new HashSet();
            for (View view : collection) {
                if (!(view.getWindow() instanceof Interval)) {
                    hashSet.add(view);
                }
            }
            return Collections.unmodifiableSet(hashSet);
        }
    }

    private NoopStats() {
    }

    static StatsComponent newNoopStatsComponent() {
        return new NoopStatsComponent();
    }

    static StatsRecorder getNoopStatsRecorder() {
        return NoopStatsRecorder.INSTANCE;
    }

    static MeasureMap newNoopMeasureMap() {
        return new NoopMeasureMap();
    }

    static ViewManager newNoopViewManager() {
        return new NoopViewManager();
    }
}
