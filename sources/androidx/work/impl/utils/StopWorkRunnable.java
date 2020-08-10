package androidx.work.impl.utils;

import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.impl.WorkDatabase;
import androidx.work.impl.WorkManagerImpl;
import androidx.work.impl.model.WorkSpecDao;

public class StopWorkRunnable implements Runnable {
    private static final String TAG = Logger.tagWithPrefix("StopWorkRunnable");
    private WorkManagerImpl mWorkManagerImpl;
    private String mWorkSpecId;

    public StopWorkRunnable(WorkManagerImpl workManagerImpl, String str) {
        this.mWorkManagerImpl = workManagerImpl;
        this.mWorkSpecId = str;
    }

    public void run() {
        WorkDatabase workDatabase = this.mWorkManagerImpl.getWorkDatabase();
        WorkSpecDao workSpecDao = workDatabase.workSpecDao();
        workDatabase.beginTransaction();
        try {
            if (workSpecDao.getState(this.mWorkSpecId) == State.RUNNING) {
                workSpecDao.setState(State.ENQUEUED, this.mWorkSpecId);
            }
            boolean stopWork = this.mWorkManagerImpl.getProcessor().stopWork(this.mWorkSpecId);
            Logger.get().debug(TAG, String.format("StopWorkRunnable for %s; Processor.stopWork = %s", new Object[]{this.mWorkSpecId, Boolean.valueOf(stopWork)}), new Throwable[0]);
            workDatabase.setTransactionSuccessful();
        } finally {
            workDatabase.endTransaction();
        }
    }
}
