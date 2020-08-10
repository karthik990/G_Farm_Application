package com.google.android.exoplayer2.scheduler;

import android.app.job.JobInfo;
import android.app.job.JobInfo.Builder;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class PlatformScheduler implements Scheduler {
    private static final boolean DEBUG = false;
    private static final String KEY_REQUIREMENTS = "requirements";
    private static final String KEY_SERVICE_ACTION = "service_action";
    private static final String KEY_SERVICE_PACKAGE = "service_package";
    private static final String TAG = "PlatformScheduler";
    private final int jobId;
    private final JobScheduler jobScheduler;
    private final ComponentName jobServiceComponentName;

    public static final class PlatformSchedulerService extends JobService {
        public boolean onStopJob(JobParameters jobParameters) {
            return false;
        }

        public boolean onStartJob(JobParameters jobParameters) {
            PlatformScheduler.logd("PlatformSchedulerService started");
            PersistableBundle extras = jobParameters.getExtras();
            if (new Requirements(extras.getInt("requirements")).checkRequirements(this)) {
                PlatformScheduler.logd("Requirements are met");
                String string = extras.getString(PlatformScheduler.KEY_SERVICE_ACTION);
                String string2 = extras.getString(PlatformScheduler.KEY_SERVICE_PACKAGE);
                Intent intent = new Intent((String) Assertions.checkNotNull(string)).setPackage(string2);
                StringBuilder sb = new StringBuilder();
                sb.append("Starting service action: ");
                sb.append(string);
                sb.append(" package: ");
                sb.append(string2);
                PlatformScheduler.logd(sb.toString());
                Util.startForegroundService(this, intent);
            } else {
                PlatformScheduler.logd("Requirements are not met");
                jobFinished(jobParameters, true);
            }
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static void logd(String str) {
    }

    public PlatformScheduler(Context context, int i) {
        this.jobId = i;
        this.jobServiceComponentName = new ComponentName(context, PlatformSchedulerService.class);
        this.jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
    }

    public boolean schedule(Requirements requirements, String str, String str2) {
        int schedule = this.jobScheduler.schedule(buildJobInfo(this.jobId, this.jobServiceComponentName, requirements, str2, str));
        StringBuilder sb = new StringBuilder();
        sb.append("Scheduling job: ");
        sb.append(this.jobId);
        sb.append(" result: ");
        sb.append(schedule);
        logd(sb.toString());
        return schedule == 1;
    }

    public boolean cancel() {
        StringBuilder sb = new StringBuilder();
        sb.append("Canceling job: ");
        sb.append(this.jobId);
        logd(sb.toString());
        this.jobScheduler.cancel(this.jobId);
        return true;
    }

    private static JobInfo buildJobInfo(int i, ComponentName componentName, Requirements requirements, String str, String str2) {
        Builder builder = new Builder(i, componentName);
        if (requirements.isUnmeteredNetworkRequired()) {
            builder.setRequiredNetworkType(2);
        } else if (requirements.isNetworkRequired()) {
            builder.setRequiredNetworkType(1);
        }
        builder.setRequiresDeviceIdle(requirements.isIdleRequired());
        builder.setRequiresCharging(requirements.isChargingRequired());
        builder.setPersisted(true);
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(KEY_SERVICE_ACTION, str);
        persistableBundle.putString(KEY_SERVICE_PACKAGE, str2);
        persistableBundle.putInt("requirements", requirements.getRequirements());
        builder.setExtras(persistableBundle);
        return builder.build();
    }
}
