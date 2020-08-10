package com.google.android.gms.internal.measurement;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.UserHandle;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class zzdi {
    private static final Method zzacv = zzfu();
    private static final Method zzacw = zzfv();
    private static volatile zzdk zzacx = zzdj.zzacy;
    private final JobScheduler zzacu;

    private static Method zzfu() {
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

    static final /* synthetic */ boolean zzfx() {
        return false;
    }

    private static Method zzfv() {
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

    private static int zzfw() {
        Method method = zzacw;
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

    private zzdi(JobScheduler jobScheduler) {
        this.zzacu = jobScheduler;
    }

    private final int zza(JobInfo jobInfo, String str, int i, String str2) {
        Method method = zzacv;
        if (method != null) {
            try {
                return ((Integer) method.invoke(this.zzacu, new Object[]{jobInfo, str, Integer.valueOf(i), str2})).intValue();
            } catch (IllegalAccessException | InvocationTargetException e) {
                Log.e(str2, "error calling scheduleAsPackage", e);
            }
        }
        return this.zzacu.schedule(jobInfo);
    }

    public static int zza(Context context, JobInfo jobInfo, String str, String str2) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (zzacv == null || !zzacx.zzfy() || context.checkSelfPermission("android.permission.UPDATE_DEVICE_STATS") != 0) {
            return jobScheduler.schedule(jobInfo);
        }
        return new zzdi(jobScheduler).zza(jobInfo, str, zzfw(), str2);
    }
}
