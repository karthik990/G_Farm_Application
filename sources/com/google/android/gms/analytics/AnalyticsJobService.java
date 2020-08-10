package com.google.android.gms.analytics;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import com.google.android.gms.internal.gtm.zzcq;
import com.google.android.gms.internal.gtm.zzcu;

public final class AnalyticsJobService extends JobService implements zzcu {
    private zzcq<AnalyticsJobService> zzrd;

    private final zzcq<AnalyticsJobService> zzad() {
        if (this.zzrd == null) {
            this.zzrd = new zzcq<>(this);
        }
        return this.zzrd;
    }

    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public final void onCreate() {
        super.onCreate();
        zzad().onCreate();
    }

    public final void onDestroy() {
        zzad().onDestroy();
        super.onDestroy();
    }

    public final int onStartCommand(Intent intent, int i, int i2) {
        return zzad().onStartCommand(intent, i, i2);
    }

    public final boolean onStartJob(JobParameters jobParameters) {
        return zzad().onStartJob(jobParameters);
    }

    public final boolean callServiceStopSelfResult(int i) {
        return stopSelfResult(i);
    }

    public final void zza(JobParameters jobParameters, boolean z) {
        jobFinished(jobParameters, false);
    }
}
