package com.google.android.gms.internal.gtm;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.UserHandle;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class zzdb {
    private static final Method zzacz = zzgk();
    private static final Method zzada = zzgl();
    private static volatile zzdd zzadb = zzdc.zzadc;
    private final JobScheduler zzacy;

    private static Method zzgk() {
        if (VERSION.SDK_INT >= 24) {
            try {
                return JobScheduler.class.getDeclaredMethod("scheduleAsPackage", new Class[]{JobInfo.class, String.class, Integer.TYPE, String.class});
            } catch (NoSuchMethodException unused) {
                String str = "JobSchedulerCompat";
                if (Log.isLoggable(str, 6)) {
                    Log.e(str, "No scheduleAsPackage method available, falling back to schedule");
                }
            }
        }
        return null;
    }

    static final /* synthetic */ boolean zzgn() {
        return false;
    }

    private static Method zzgl() {
        if (VERSION.SDK_INT >= 24) {
            try {
                return UserHandle.class.getDeclaredMethod("myUserId", null);
            } catch (NoSuchMethodException unused) {
                String str = "JobSchedulerCompat";
                if (Log.isLoggable(str, 6)) {
                    Log.e(str, "No myUserId method available");
                }
            }
        }
        return null;
    }

    private static int zzgm() {
        Method method = zzada;
        if (method != null) {
            try {
                return ((Integer) method.invoke(null, new Object[0])).intValue();
            } catch (IllegalAccessException | InvocationTargetException e) {
                String str = "JobSchedulerCompat";
                if (Log.isLoggable(str, 6)) {
                    Log.e(str, "myUserId invocation illegal", e);
                }
            }
        }
        return 0;
    }

    private zzdb(JobScheduler jobScheduler) {
        this.zzacy = jobScheduler;
    }

    private final int zza(JobInfo jobInfo, String str, int i, String str2) {
        Method method = zzacz;
        if (method != null) {
            try {
                return ((Integer) method.invoke(this.zzacy, new Object[]{jobInfo, str, Integer.valueOf(i), str2})).intValue();
            } catch (IllegalAccessException | InvocationTargetException e) {
                Log.e(str2, "error calling scheduleAsPackage", e);
            }
        }
        return this.zzacy.schedule(jobInfo);
    }

    public static int zza(Context context, JobInfo jobInfo, String str, String str2) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (zzacz == null || !zzadb.zzgo() || context.checkSelfPermission("android.permission.UPDATE_DEVICE_STATS") != 0) {
            return jobScheduler.schedule(jobInfo);
        }
        return new zzdb(jobScheduler).zza(jobInfo, str, zzgm(), str2);
    }
}
