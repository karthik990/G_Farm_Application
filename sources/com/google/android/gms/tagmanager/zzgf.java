package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public final class zzgf {
    private Tracker zzrl;
    private Context zzrm;
    private GoogleAnalytics zzro;

    public zzgf(Context context) {
        this.zzrm = context;
    }

    public final Tracker zzbm(String str) {
        zzbn(str);
        return this.zzrl;
    }

    private final synchronized void zzbn(String str) {
        if (this.zzro == null) {
            this.zzro = GoogleAnalytics.getInstance(this.zzrm);
            this.zzro.setLogger(new zzgg());
            this.zzrl = this.zzro.newTracker(str);
        }
    }
}
