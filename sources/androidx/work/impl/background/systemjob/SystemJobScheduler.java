package androidx.work.impl.background.systemjob;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.Scheduler;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.SystemIdInfo;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.IdGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SystemJobScheduler implements Scheduler {
    private static final String TAG = Logger.tagWithPrefix("SystemJobScheduler");
    private final Context mContext;
    private final IdGenerator mIdGenerator;
    private final JobScheduler mJobScheduler;
    private final SystemJobInfoConverter mSystemJobInfoConverter;
    private final WorkManagerImpl mWorkManager;

    public SystemJobScheduler(Context context, WorkManagerImpl workManagerImpl) {
        this(context, workManagerImpl, (JobScheduler) context.getSystemService("jobscheduler"), new SystemJobInfoConverter(context));
    }

    public SystemJobScheduler(Context context, WorkManagerImpl workManagerImpl, JobScheduler jobScheduler, SystemJobInfoConverter systemJobInfoConverter) {
        this.mContext = context;
        this.mWorkManager = workManagerImpl;
        this.mJobScheduler = jobScheduler;
        this.mIdGenerator = new IdGenerator(context);
        this.mSystemJobInfoConverter = systemJobInfoConverter;
    }

    /* JADX INFO: finally extract failed */
    public void schedule(WorkSpec... workSpecArr) {
        int i;
        WorkDatabase workDatabase = this.mWorkManager.getWorkDatabase();
        int length = workSpecArr.length;
        int i2 = 0;
        while (i2 < length) {
            WorkSpec workSpec = workSpecArr[i2];
            workDatabase.beginTransaction();
            try {
                WorkSpec workSpec2 = workDatabase.workSpecDao().getWorkSpec(workSpec.f65id);
                String str = "Skipping scheduling ";
                if (workSpec2 == null) {
                    Logger logger = Logger.get();
                    String str2 = TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(workSpec.f65id);
                    sb.append(" because it's no longer in the DB");
                    logger.warning(str2, sb.toString(), new Throwable[0]);
                    workDatabase.setTransactionSuccessful();
                } else if (workSpec2.state != State.ENQUEUED) {
                    Logger logger2 = Logger.get();
                    String str3 = TAG;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(workSpec.f65id);
                    sb2.append(" because it is no longer enqueued");
                    logger2.warning(str3, sb2.toString(), new Throwable[0]);
                    workDatabase.setTransactionSuccessful();
                } else {
                    SystemIdInfo systemIdInfo = workDatabase.systemIdInfoDao().getSystemIdInfo(workSpec.f65id);
                    int nextJobSchedulerIdWithRange = systemIdInfo != null ? systemIdInfo.systemId : this.mIdGenerator.nextJobSchedulerIdWithRange(this.mWorkManager.getConfiguration().getMinJobSchedulerId(), this.mWorkManager.getConfiguration().getMaxJobSchedulerId());
                    if (systemIdInfo == null) {
                        this.mWorkManager.getWorkDatabase().systemIdInfoDao().insertSystemIdInfo(new SystemIdInfo(workSpec.f65id, nextJobSchedulerIdWithRange));
                    }
                    scheduleInternal(workSpec, nextJobSchedulerIdWithRange);
                    if (VERSION.SDK_INT == 23) {
                        List pendingJobIds = getPendingJobIds(this.mContext, this.mJobScheduler, workSpec.f65id);
                        if (pendingJobIds != null) {
                            int indexOf = pendingJobIds.indexOf(Integer.valueOf(nextJobSchedulerIdWithRange));
                            if (indexOf >= 0) {
                                pendingJobIds.remove(indexOf);
                            }
                            if (!pendingJobIds.isEmpty()) {
                                i = ((Integer) pendingJobIds.get(0)).intValue();
                            } else {
                                i = this.mIdGenerator.nextJobSchedulerIdWithRange(this.mWorkManager.getConfiguration().getMinJobSchedulerId(), this.mWorkManager.getConfiguration().getMaxJobSchedulerId());
                            }
                            scheduleInternal(workSpec, i);
                        }
                    }
                    workDatabase.setTransactionSuccessful();
                }
                workDatabase.endTransaction();
                i2++;
            } catch (Throwable th) {
                workDatabase.endTransaction();
                throw th;
            }
        }
    }

    public void scheduleInternal(WorkSpec workSpec, int i) {
        JobInfo convert = this.mSystemJobInfoConverter.convert(workSpec, i);
        Logger.get().debug(TAG, String.format("Scheduling work ID %s Job ID %s", new Object[]{workSpec.f65id, Integer.valueOf(i)}), new Throwable[0]);
        try {
            this.mJobScheduler.schedule(convert);
        } catch (IllegalStateException e) {
            List pendingJobs = getPendingJobs(this.mContext, this.mJobScheduler);
            String format = String.format(Locale.getDefault(), "JobScheduler 100 job limit exceeded.  We count %d WorkManager jobs in JobScheduler; we have %d tracked jobs in our DB; our Configuration limit is %d.", new Object[]{Integer.valueOf(pendingJobs != null ? pendingJobs.size() : 0), Integer.valueOf(this.mWorkManager.getWorkDatabase().workSpecDao().getScheduledWork().size()), Integer.valueOf(this.mWorkManager.getConfiguration().getMaxSchedulerLimit())});
            Logger.get().error(TAG, format, new Throwable[0]);
            throw new IllegalStateException(format, e);
        } catch (Throwable th) {
            Logger.get().error(TAG, String.format("Unable to schedule %s", new Object[]{workSpec}), th);
        }
    }

    public void cancel(String str) {
        List<Integer> pendingJobIds = getPendingJobIds(this.mContext, this.mJobScheduler, str);
        if (pendingJobIds != null && !pendingJobIds.isEmpty()) {
            for (Integer intValue : pendingJobIds) {
                cancelJobById(this.mJobScheduler, intValue.intValue());
            }
            this.mWorkManager.getWorkDatabase().systemIdInfoDao().removeSystemIdInfo(str);
        }
    }

    private static void cancelJobById(JobScheduler jobScheduler, int i) {
        try {
            jobScheduler.cancel(i);
        } catch (Throwable th) {
            Logger.get().error(TAG, String.format(Locale.getDefault(), "Exception while trying to cancel job (%d)", new Object[]{Integer.valueOf(i)}), th);
        }
    }

    public static void cancelAll(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (jobScheduler != null) {
            List<JobInfo> pendingJobs = getPendingJobs(context, jobScheduler);
            if (pendingJobs != null && !pendingJobs.isEmpty()) {
                for (JobInfo id : pendingJobs) {
                    cancelJobById(jobScheduler, id.getId());
                }
            }
        }
    }

    public static void cancelInvalidJobs(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        if (jobScheduler != null) {
            List<JobInfo> pendingJobs = getPendingJobs(context, jobScheduler);
            if (pendingJobs != null && !pendingJobs.isEmpty()) {
                for (JobInfo jobInfo : pendingJobs) {
                    if (getWorkSpecIdFromJobInfo(jobInfo) == null) {
                        cancelJobById(jobScheduler, jobInfo.getId());
                    }
                }
            }
        }
    }

    private static List<JobInfo> getPendingJobs(Context context, JobScheduler jobScheduler) {
        List<JobInfo> list;
        try {
            list = jobScheduler.getAllPendingJobs();
        } catch (Throwable th) {
            Logger.get().error(TAG, "getAllPendingJobs() is not reliable on this device.", th);
            list = null;
        }
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        ComponentName componentName = new ComponentName(context, SystemJobService.class);
        for (JobInfo jobInfo : list) {
            if (componentName.equals(jobInfo.getService())) {
                arrayList.add(jobInfo);
            }
        }
        return arrayList;
    }

    private static List<Integer> getPendingJobIds(Context context, JobScheduler jobScheduler, String str) {
        List<JobInfo> pendingJobs = getPendingJobs(context, jobScheduler);
        if (pendingJobs == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(2);
        for (JobInfo jobInfo : pendingJobs) {
            if (str.equals(getWorkSpecIdFromJobInfo(jobInfo))) {
                arrayList.add(Integer.valueOf(jobInfo.getId()));
            }
        }
        return arrayList;
    }

    private static String getWorkSpecIdFromJobInfo(JobInfo jobInfo) {
        String str = "EXTRA_WORK_SPEC_ID";
        PersistableBundle extras = jobInfo.getExtras();
        if (extras != null) {
            try {
                if (extras.containsKey(str)) {
                    return extras.getString(str);
                }
            } catch (NullPointerException unused) {
            }
        }
        return null;
    }
}
