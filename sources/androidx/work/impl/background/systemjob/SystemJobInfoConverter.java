package androidx.work.impl.background.systemjob;

import android.app.job.JobInfo;
import android.app.job.JobInfo.Builder;
import android.app.job.JobInfo.TriggerContentUri;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ContentUriTriggers.Trigger;
import androidx.work.Logger;
import androidx.work.NetworkType;
import androidx.work.impl.model.WorkSpec;

class SystemJobInfoConverter {
    static final String EXTRA_IS_PERIODIC = "EXTRA_IS_PERIODIC";
    static final String EXTRA_WORK_SPEC_ID = "EXTRA_WORK_SPEC_ID";
    private static final String TAG = Logger.tagWithPrefix("SystemJobInfoConverter");
    private final ComponentName mWorkServiceComponent;

    /* renamed from: androidx.work.impl.background.systemjob.SystemJobInfoConverter$1 */
    static /* synthetic */ class C07321 {
        static final /* synthetic */ int[] $SwitchMap$androidx$work$NetworkType = new int[NetworkType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                androidx.work.NetworkType[] r0 = androidx.work.NetworkType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$androidx$work$NetworkType = r0
                int[] r0 = $SwitchMap$androidx$work$NetworkType     // Catch:{ NoSuchFieldError -> 0x0014 }
                androidx.work.NetworkType r1 = androidx.work.NetworkType.NOT_REQUIRED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$androidx$work$NetworkType     // Catch:{ NoSuchFieldError -> 0x001f }
                androidx.work.NetworkType r1 = androidx.work.NetworkType.CONNECTED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$androidx$work$NetworkType     // Catch:{ NoSuchFieldError -> 0x002a }
                androidx.work.NetworkType r1 = androidx.work.NetworkType.UNMETERED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$androidx$work$NetworkType     // Catch:{ NoSuchFieldError -> 0x0035 }
                androidx.work.NetworkType r1 = androidx.work.NetworkType.NOT_ROAMING     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = $SwitchMap$androidx$work$NetworkType     // Catch:{ NoSuchFieldError -> 0x0040 }
                androidx.work.NetworkType r1 = androidx.work.NetworkType.METERED     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.work.impl.background.systemjob.SystemJobInfoConverter.C07321.<clinit>():void");
        }
    }

    SystemJobInfoConverter(Context context) {
        this.mWorkServiceComponent = new ComponentName(context.getApplicationContext(), SystemJobService.class);
    }

    /* access modifiers changed from: 0000 */
    public JobInfo convert(WorkSpec workSpec, int i) {
        Constraints constraints = workSpec.constraints;
        int convertNetworkType = convertNetworkType(constraints.getRequiredNetworkType());
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(EXTRA_WORK_SPEC_ID, workSpec.f65id);
        persistableBundle.putBoolean(EXTRA_IS_PERIODIC, workSpec.isPeriodic());
        Builder extras = new Builder(i, this.mWorkServiceComponent).setRequiredNetworkType(convertNetworkType).setRequiresCharging(constraints.requiresCharging()).setRequiresDeviceIdle(constraints.requiresDeviceIdle()).setExtras(persistableBundle);
        if (!constraints.requiresDeviceIdle()) {
            extras.setBackoffCriteria(workSpec.backoffDelayDuration, workSpec.backoffPolicy == BackoffPolicy.LINEAR ? 0 : 1);
        }
        extras.setMinimumLatency(Math.max(workSpec.calculateNextRunTime() - System.currentTimeMillis(), 0));
        if (VERSION.SDK_INT >= 24 && constraints.hasContentUriTriggers()) {
            for (Trigger convertContentUriTrigger : constraints.getContentUriTriggers().getTriggers()) {
                extras.addTriggerContentUri(convertContentUriTrigger(convertContentUriTrigger));
            }
            extras.setTriggerContentUpdateDelay(constraints.getTriggerContentUpdateDelay());
            extras.setTriggerContentMaxDelay(constraints.getTriggerMaxContentDelay());
        }
        extras.setPersisted(false);
        if (VERSION.SDK_INT >= 26) {
            extras.setRequiresBatteryNotLow(constraints.requiresBatteryNotLow());
            extras.setRequiresStorageNotLow(constraints.requiresStorageNotLow());
        }
        return extras.build();
    }

    private static TriggerContentUri convertContentUriTrigger(Trigger trigger) {
        return new TriggerContentUri(trigger.getUri(), trigger.shouldTriggerForDescendants() ? 1 : 0);
    }

    static int convertNetworkType(NetworkType networkType) {
        int i = C07321.$SwitchMap$androidx$work$NetworkType[networkType.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 3) {
            return 2;
        }
        if (i != 4) {
            if (i == 5 && VERSION.SDK_INT >= 26) {
                return 4;
            }
        } else if (VERSION.SDK_INT >= 24) {
            return 3;
        }
        Logger.get().debug(TAG, String.format("API version too low. Cannot convert network type value %s", new Object[]{networkType}), new Throwable[0]);
        return 1;
    }
}
