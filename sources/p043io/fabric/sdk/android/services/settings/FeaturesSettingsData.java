package p043io.fabric.sdk.android.services.settings;

/* renamed from: io.fabric.sdk.android.services.settings.FeaturesSettingsData */
public class FeaturesSettingsData {
    public final boolean collectAnalytics;
    public final boolean collectLoggedException;
    public final boolean collectReports;
    public final boolean firebaseCrashlyticsEnabled;
    public final boolean promptEnabled;

    public FeaturesSettingsData(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.promptEnabled = z;
        this.collectLoggedException = z2;
        this.collectReports = z3;
        this.collectAnalytics = z4;
        this.firebaseCrashlyticsEnabled = z5;
    }
}
