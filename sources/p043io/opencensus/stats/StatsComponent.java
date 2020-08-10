package p043io.opencensus.stats;

/* renamed from: io.opencensus.stats.StatsComponent */
public abstract class StatsComponent {
    public abstract StatsCollectionState getState();

    public abstract StatsRecorder getStatsRecorder();

    public abstract ViewManager getViewManager();

    @Deprecated
    public abstract void setState(StatsCollectionState statsCollectionState);
}
