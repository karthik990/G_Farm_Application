package androidx.work.impl.background.greedy;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.ExecutionListener;
import androidx.work.impl.Scheduler;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.constraints.WorkConstraintsCallback;
import androidx.work.impl.constraints.WorkConstraintsTracker;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import java.util.ArrayList;
import java.util.List;

public class GreedyScheduler implements Scheduler, WorkConstraintsCallback, ExecutionListener {
    private static final String TAG = Logger.tagWithPrefix("GreedyScheduler");
    private List<WorkSpec> mConstrainedWorkSpecs = new ArrayList();
    private final Object mLock;
    private boolean mRegisteredExecutionListener;
    private WorkConstraintsTracker mWorkConstraintsTracker;
    private WorkManagerImpl mWorkManagerImpl;

    public GreedyScheduler(Context context, TaskExecutor taskExecutor, WorkManagerImpl workManagerImpl) {
        this.mWorkManagerImpl = workManagerImpl;
        this.mWorkConstraintsTracker = new WorkConstraintsTracker(context, taskExecutor, this);
        this.mLock = new Object();
    }

    public GreedyScheduler(WorkManagerImpl workManagerImpl, WorkConstraintsTracker workConstraintsTracker) {
        this.mWorkManagerImpl = workManagerImpl;
        this.mWorkConstraintsTracker = workConstraintsTracker;
        this.mLock = new Object();
    }

    public void schedule(WorkSpec... workSpecArr) {
        registerExecutionListenerIfNeeded();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (WorkSpec workSpec : workSpecArr) {
            if (workSpec.state == State.ENQUEUED && !workSpec.isPeriodic() && workSpec.initialDelay == 0 && !workSpec.isBackedOff()) {
                if (!workSpec.hasConstraints()) {
                    Logger.get().debug(TAG, String.format("Starting work for %s", new Object[]{workSpec.f65id}), new Throwable[0]);
                    this.mWorkManagerImpl.startWork(workSpec.f65id);
                } else if (VERSION.SDK_INT < 24 || !workSpec.constraints.hasContentUriTriggers()) {
                    arrayList.add(workSpec);
                    arrayList2.add(workSpec.f65id);
                }
            }
        }
        synchronized (this.mLock) {
            if (!arrayList.isEmpty()) {
                Logger.get().debug(TAG, String.format("Starting tracking for [%s]", new Object[]{TextUtils.join(",", arrayList2)}), new Throwable[0]);
                this.mConstrainedWorkSpecs.addAll(arrayList);
                this.mWorkConstraintsTracker.replace(this.mConstrainedWorkSpecs);
            }
        }
    }

    public void cancel(String str) {
        registerExecutionListenerIfNeeded();
        Logger.get().debug(TAG, String.format("Cancelling work ID %s", new Object[]{str}), new Throwable[0]);
        this.mWorkManagerImpl.stopWork(str);
    }

    public void onAllConstraintsMet(List<String> list) {
        for (String str : list) {
            Logger.get().debug(TAG, String.format("Constraints met: Scheduling work ID %s", new Object[]{str}), new Throwable[0]);
            this.mWorkManagerImpl.startWork(str);
        }
    }

    public void onAllConstraintsNotMet(List<String> list) {
        for (String str : list) {
            Logger.get().debug(TAG, String.format("Constraints not met: Cancelling work ID %s", new Object[]{str}), new Throwable[0]);
            this.mWorkManagerImpl.stopWork(str);
        }
    }

    public void onExecuted(String str, boolean z) {
        removeConstraintTrackingFor(str);
    }

    private void removeConstraintTrackingFor(String str) {
        synchronized (this.mLock) {
            int size = this.mConstrainedWorkSpecs.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                } else if (((WorkSpec) this.mConstrainedWorkSpecs.get(i)).f65id.equals(str)) {
                    Logger.get().debug(TAG, String.format("Stopping tracking for %s", new Object[]{str}), new Throwable[0]);
                    this.mConstrainedWorkSpecs.remove(i);
                    this.mWorkConstraintsTracker.replace(this.mConstrainedWorkSpecs);
                    break;
                } else {
                    i++;
                }
            }
        }
    }

    private void registerExecutionListenerIfNeeded() {
        if (!this.mRegisteredExecutionListener) {
            this.mWorkManagerImpl.getProcessor().addExecutionListener(this);
            this.mRegisteredExecutionListener = true;
        }
    }
}
