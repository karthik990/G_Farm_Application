package androidx.work.impl.utils;

import androidx.work.WorkerParameters.RuntimeExtras;
import androidx.work.impl.WorkManagerImpl;

public class StartWorkRunnable implements Runnable {
    private RuntimeExtras mRuntimeExtras;
    private WorkManagerImpl mWorkManagerImpl;
    private String mWorkSpecId;

    public StartWorkRunnable(WorkManagerImpl workManagerImpl, String str, RuntimeExtras runtimeExtras) {
        this.mWorkManagerImpl = workManagerImpl;
        this.mWorkSpecId = str;
        this.mRuntimeExtras = runtimeExtras;
    }

    public void run() {
        this.mWorkManagerImpl.getProcessor().startWork(this.mWorkSpecId, this.mRuntimeExtras);
    }
}
