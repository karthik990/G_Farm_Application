package androidx.work.impl;

import android.content.Context;
import androidx.work.Configuration;
import androidx.work.Data;
import androidx.work.InputMerger;
import androidx.work.ListenableWorker;
import androidx.work.ListenableWorker.Result;
import androidx.work.ListenableWorker.Result.Failure;
import androidx.work.ListenableWorker.Result.Retry;
import androidx.work.ListenableWorker.Result.Success;
import androidx.work.Logger;
import androidx.work.WorkInfo.State;
import androidx.work.WorkerParameters;
import androidx.work.WorkerParameters.RuntimeExtras;
import androidx.work.impl.model.DependencyDao;
import androidx.work.impl.model.WorkSpec;
import androidx.work.impl.model.WorkSpecDao;
import androidx.work.impl.model.WorkTagDao;
import androidx.work.impl.utils.futures.SettableFuture;
import androidx.work.impl.utils.taskexecutor.TaskExecutor;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class WorkerWrapper implements Runnable {
    static final String TAG = Logger.tagWithPrefix("WorkerWrapper");
    private Context mAppContext;
    private Configuration mConfiguration;
    private DependencyDao mDependencyDao;
    private SettableFuture<Boolean> mFuture = SettableFuture.create();
    ListenableFuture<Result> mInnerFuture = null;
    private volatile boolean mInterrupted;
    Result mResult = Result.failure();
    private RuntimeExtras mRuntimeExtras;
    private List<Scheduler> mSchedulers;
    private List<String> mTags;
    private WorkDatabase mWorkDatabase;
    private String mWorkDescription;
    WorkSpec mWorkSpec;
    private WorkSpecDao mWorkSpecDao;
    private String mWorkSpecId;
    private WorkTagDao mWorkTagDao;
    private TaskExecutor mWorkTaskExecutor;
    ListenableWorker mWorker;

    public static class Builder {
        Context mAppContext;
        Configuration mConfiguration;
        RuntimeExtras mRuntimeExtras = new RuntimeExtras();
        List<Scheduler> mSchedulers;
        WorkDatabase mWorkDatabase;
        String mWorkSpecId;
        TaskExecutor mWorkTaskExecutor;
        ListenableWorker mWorker;

        public Builder(Context context, Configuration configuration, TaskExecutor taskExecutor, WorkDatabase workDatabase, String str) {
            this.mAppContext = context.getApplicationContext();
            this.mWorkTaskExecutor = taskExecutor;
            this.mConfiguration = configuration;
            this.mWorkDatabase = workDatabase;
            this.mWorkSpecId = str;
        }

        public Builder withSchedulers(List<Scheduler> list) {
            this.mSchedulers = list;
            return this;
        }

        public Builder withRuntimeExtras(RuntimeExtras runtimeExtras) {
            if (runtimeExtras != null) {
                this.mRuntimeExtras = runtimeExtras;
            }
            return this;
        }

        public Builder withWorker(ListenableWorker listenableWorker) {
            this.mWorker = listenableWorker;
            return this;
        }

        public WorkerWrapper build() {
            return new WorkerWrapper(this);
        }
    }

    WorkerWrapper(Builder builder) {
        this.mAppContext = builder.mAppContext;
        this.mWorkTaskExecutor = builder.mWorkTaskExecutor;
        this.mWorkSpecId = builder.mWorkSpecId;
        this.mSchedulers = builder.mSchedulers;
        this.mRuntimeExtras = builder.mRuntimeExtras;
        this.mWorker = builder.mWorker;
        this.mConfiguration = builder.mConfiguration;
        this.mWorkDatabase = builder.mWorkDatabase;
        this.mWorkSpecDao = this.mWorkDatabase.workSpecDao();
        this.mDependencyDao = this.mWorkDatabase.dependencyDao();
        this.mWorkTagDao = this.mWorkDatabase.workTagDao();
    }

    public ListenableFuture<Boolean> getFuture() {
        return this.mFuture;
    }

    public void run() {
        this.mTags = this.mWorkTagDao.getTagsForWorkSpecId(this.mWorkSpecId);
        this.mWorkDescription = createWorkDescription(this.mTags);
        runWorker();
    }

    private void runWorker() {
        Data merge;
        if (!tryCheckForInterruptionAndResolve()) {
            this.mWorkDatabase.beginTransaction();
            try {
                this.mWorkSpec = this.mWorkSpecDao.getWorkSpec(this.mWorkSpecId);
                if (this.mWorkSpec == null) {
                    Logger.get().error(TAG, String.format("Didn't find WorkSpec for id %s", new Object[]{this.mWorkSpecId}), new Throwable[0]);
                    resolve(false);
                } else if (this.mWorkSpec.state != State.ENQUEUED) {
                    resolveIncorrectStatus();
                    this.mWorkDatabase.setTransactionSuccessful();
                    Logger.get().debug(TAG, String.format("%s is not in ENQUEUED state. Nothing more to do.", new Object[]{this.mWorkSpec.workerClassName}), new Throwable[0]);
                    this.mWorkDatabase.endTransaction();
                } else {
                    if (this.mWorkSpec.isPeriodic() || this.mWorkSpec.isBackedOff()) {
                        long currentTimeMillis = System.currentTimeMillis();
                        if (!(this.mWorkSpec.periodStartTime == 0) && currentTimeMillis < this.mWorkSpec.calculateNextRunTime()) {
                            Logger.get().debug(TAG, String.format("Delaying execution for %s because it is being executed before schedule.", new Object[]{this.mWorkSpec.workerClassName}), new Throwable[0]);
                            resolve(true);
                            this.mWorkDatabase.endTransaction();
                            return;
                        }
                    }
                    this.mWorkDatabase.setTransactionSuccessful();
                    this.mWorkDatabase.endTransaction();
                    if (this.mWorkSpec.isPeriodic()) {
                        merge = this.mWorkSpec.input;
                    } else {
                        InputMerger fromClassName = InputMerger.fromClassName(this.mWorkSpec.inputMergerClassName);
                        if (fromClassName == null) {
                            Logger.get().error(TAG, String.format("Could not create Input Merger %s", new Object[]{this.mWorkSpec.inputMergerClassName}), new Throwable[0]);
                            setFailedAndResolve();
                            return;
                        }
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(this.mWorkSpec.input);
                        arrayList.addAll(this.mWorkSpecDao.getInputsFromPrerequisites(this.mWorkSpecId));
                        merge = fromClassName.merge(arrayList);
                    }
                    WorkerParameters workerParameters = new WorkerParameters(UUID.fromString(this.mWorkSpecId), merge, this.mTags, this.mRuntimeExtras, this.mWorkSpec.runAttemptCount, this.mConfiguration.getExecutor(), this.mWorkTaskExecutor, this.mConfiguration.getWorkerFactory());
                    if (this.mWorker == null) {
                        this.mWorker = this.mConfiguration.getWorkerFactory().createWorkerWithDefaultFallback(this.mAppContext, this.mWorkSpec.workerClassName, workerParameters);
                    }
                    ListenableWorker listenableWorker = this.mWorker;
                    if (listenableWorker == null) {
                        Logger.get().error(TAG, String.format("Could not create Worker %s", new Object[]{this.mWorkSpec.workerClassName}), new Throwable[0]);
                        setFailedAndResolve();
                    } else if (listenableWorker.isUsed()) {
                        Logger.get().error(TAG, String.format("Received an already-used Worker %s; WorkerFactory should return new instances", new Object[]{this.mWorkSpec.workerClassName}), new Throwable[0]);
                        setFailedAndResolve();
                    } else {
                        this.mWorker.setUsed();
                        if (!trySetRunning()) {
                            resolveIncorrectStatus();
                        } else if (!tryCheckForInterruptionAndResolve()) {
                            final SettableFuture create = SettableFuture.create();
                            this.mWorkTaskExecutor.getMainThreadExecutor().execute(new Runnable() {
                                public void run() {
                                    try {
                                        Logger.get().debug(WorkerWrapper.TAG, String.format("Starting work for %s", new Object[]{WorkerWrapper.this.mWorkSpec.workerClassName}), new Throwable[0]);
                                        WorkerWrapper.this.mInnerFuture = WorkerWrapper.this.mWorker.startWork();
                                        create.setFuture(WorkerWrapper.this.mInnerFuture);
                                    } catch (Throwable th) {
                                        create.setException(th);
                                    }
                                }
                            });
                            final String str = this.mWorkDescription;
                            create.addListener(new Runnable() {
                                public void run() {
                                    try {
                                        Result result = (Result) create.get();
                                        if (result == null) {
                                            Logger.get().error(WorkerWrapper.TAG, String.format("%s returned a null result. Treating it as a failure.", new Object[]{WorkerWrapper.this.mWorkSpec.workerClassName}), new Throwable[0]);
                                        } else {
                                            Logger.get().debug(WorkerWrapper.TAG, String.format("%s returned a %s result.", new Object[]{WorkerWrapper.this.mWorkSpec.workerClassName, result}), new Throwable[0]);
                                            WorkerWrapper.this.mResult = result;
                                        }
                                    } catch (CancellationException e) {
                                        Logger.get().info(WorkerWrapper.TAG, String.format("%s was cancelled", new Object[]{str}), e);
                                    } catch (InterruptedException | ExecutionException e2) {
                                        Logger.get().error(WorkerWrapper.TAG, String.format("%s failed because it threw an exception/error", new Object[]{str}), e2);
                                    } catch (Throwable th) {
                                        WorkerWrapper.this.onWorkFinished();
                                        throw th;
                                    }
                                    WorkerWrapper.this.onWorkFinished();
                                }
                            }, this.mWorkTaskExecutor.getBackgroundExecutor());
                        }
                    }
                }
            } finally {
                this.mWorkDatabase.endTransaction();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void onWorkFinished() {
        boolean z = false;
        if (!tryCheckForInterruptionAndResolve()) {
            this.mWorkDatabase.beginTransaction();
            try {
                State state = this.mWorkSpecDao.getState(this.mWorkSpecId);
                if (state == null) {
                    resolve(false);
                    z = true;
                } else if (state == State.RUNNING) {
                    handleResult(this.mResult);
                    z = this.mWorkSpecDao.getState(this.mWorkSpecId).isFinished();
                } else if (!state.isFinished()) {
                    rescheduleAndResolve();
                }
                this.mWorkDatabase.setTransactionSuccessful();
            } finally {
                this.mWorkDatabase.endTransaction();
            }
        }
        List<Scheduler> list = this.mSchedulers;
        if (list != null) {
            if (z) {
                for (Scheduler cancel : list) {
                    cancel.cancel(this.mWorkSpecId);
                }
            }
            Schedulers.schedule(this.mConfiguration, this.mWorkDatabase, this.mSchedulers);
        }
    }

    public void interrupt(boolean z) {
        this.mInterrupted = true;
        tryCheckForInterruptionAndResolve();
        ListenableFuture<Result> listenableFuture = this.mInnerFuture;
        if (listenableFuture != null) {
            listenableFuture.cancel(true);
        }
        ListenableWorker listenableWorker = this.mWorker;
        if (listenableWorker != null) {
            listenableWorker.stop();
        }
    }

    private void resolveIncorrectStatus() {
        State state = this.mWorkSpecDao.getState(this.mWorkSpecId);
        if (state == State.RUNNING) {
            Logger.get().debug(TAG, String.format("Status for %s is RUNNING;not doing any work and rescheduling for later execution", new Object[]{this.mWorkSpecId}), new Throwable[0]);
            resolve(true);
            return;
        }
        Logger.get().debug(TAG, String.format("Status for %s is %s; not doing any work", new Object[]{this.mWorkSpecId, state}), new Throwable[0]);
        resolve(false);
    }

    private boolean tryCheckForInterruptionAndResolve() {
        if (!this.mInterrupted) {
            return false;
        }
        Logger.get().debug(TAG, String.format("Work interrupted for %s", new Object[]{this.mWorkDescription}), new Throwable[0]);
        State state = this.mWorkSpecDao.getState(this.mWorkSpecId);
        if (state == null) {
            resolve(false);
        } else {
            resolve(!state.isFinished());
        }
        return true;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x001e A[Catch:{ all -> 0x0039 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void resolve(boolean r4) {
        /*
            r3 = this;
            androidx.work.impl.WorkDatabase r0 = r3.mWorkDatabase
            r0.beginTransaction()
            androidx.work.impl.WorkDatabase r0 = r3.mWorkDatabase     // Catch:{ all -> 0x0039 }
            androidx.work.impl.model.WorkSpecDao r0 = r0.workSpecDao()     // Catch:{ all -> 0x0039 }
            java.util.List r0 = r0.getAllUnfinishedWork()     // Catch:{ all -> 0x0039 }
            r1 = 0
            if (r0 == 0) goto L_0x001b
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0039 }
            if (r0 == 0) goto L_0x0019
            goto L_0x001b
        L_0x0019:
            r0 = 0
            goto L_0x001c
        L_0x001b:
            r0 = 1
        L_0x001c:
            if (r0 == 0) goto L_0x0025
            android.content.Context r0 = r3.mAppContext     // Catch:{ all -> 0x0039 }
            java.lang.Class<androidx.work.impl.background.systemalarm.RescheduleReceiver> r2 = androidx.work.impl.background.systemalarm.RescheduleReceiver.class
            androidx.work.impl.utils.PackageManagerHelper.setComponentEnabled(r0, r2, r1)     // Catch:{ all -> 0x0039 }
        L_0x0025:
            androidx.work.impl.WorkDatabase r0 = r3.mWorkDatabase     // Catch:{ all -> 0x0039 }
            r0.setTransactionSuccessful()     // Catch:{ all -> 0x0039 }
            androidx.work.impl.WorkDatabase r0 = r3.mWorkDatabase
            r0.endTransaction()
            androidx.work.impl.utils.futures.SettableFuture<java.lang.Boolean> r0 = r3.mFuture
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            r0.set(r4)
            return
        L_0x0039:
            r4 = move-exception
            androidx.work.impl.WorkDatabase r0 = r3.mWorkDatabase
            r0.endTransaction()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.work.impl.WorkerWrapper.resolve(boolean):void");
    }

    private void handleResult(Result result) {
        if (result instanceof Success) {
            Logger.get().info(TAG, String.format("Worker result SUCCESS for %s", new Object[]{this.mWorkDescription}), new Throwable[0]);
            if (this.mWorkSpec.isPeriodic()) {
                resetPeriodicAndResolve();
            } else {
                setSucceededAndResolve();
            }
        } else if (result instanceof Retry) {
            Logger.get().info(TAG, String.format("Worker result RETRY for %s", new Object[]{this.mWorkDescription}), new Throwable[0]);
            rescheduleAndResolve();
        } else {
            Logger.get().info(TAG, String.format("Worker result FAILURE for %s", new Object[]{this.mWorkDescription}), new Throwable[0]);
            if (this.mWorkSpec.isPeriodic()) {
                resetPeriodicAndResolve();
            } else {
                setFailedAndResolve();
            }
        }
    }

    private boolean trySetRunning() {
        this.mWorkDatabase.beginTransaction();
        try {
            boolean z = true;
            if (this.mWorkSpecDao.getState(this.mWorkSpecId) == State.ENQUEUED) {
                this.mWorkSpecDao.setState(State.RUNNING, this.mWorkSpecId);
                this.mWorkSpecDao.incrementWorkSpecRunAttemptCount(this.mWorkSpecId);
            } else {
                z = false;
            }
            this.mWorkDatabase.setTransactionSuccessful();
            return z;
        } finally {
            this.mWorkDatabase.endTransaction();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setFailedAndResolve() {
        this.mWorkDatabase.beginTransaction();
        try {
            iterativelyFailWorkAndDependents(this.mWorkSpecId);
            this.mWorkSpecDao.setOutput(this.mWorkSpecId, ((Failure) this.mResult).getOutputData());
            this.mWorkDatabase.setTransactionSuccessful();
        } finally {
            this.mWorkDatabase.endTransaction();
            resolve(false);
        }
    }

    private void iterativelyFailWorkAndDependents(String str) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(str);
        while (!linkedList.isEmpty()) {
            String str2 = (String) linkedList.remove();
            if (this.mWorkSpecDao.getState(str2) != State.CANCELLED) {
                this.mWorkSpecDao.setState(State.FAILED, str2);
            }
            linkedList.addAll(this.mDependencyDao.getDependentWorkIds(str2));
        }
    }

    private void rescheduleAndResolve() {
        this.mWorkDatabase.beginTransaction();
        try {
            this.mWorkSpecDao.setState(State.ENQUEUED, this.mWorkSpecId);
            this.mWorkSpecDao.setPeriodStartTime(this.mWorkSpecId, System.currentTimeMillis());
            this.mWorkSpecDao.markWorkSpecScheduled(this.mWorkSpecId, -1);
            this.mWorkDatabase.setTransactionSuccessful();
        } finally {
            this.mWorkDatabase.endTransaction();
            resolve(true);
        }
    }

    private void resetPeriodicAndResolve() {
        this.mWorkDatabase.beginTransaction();
        try {
            this.mWorkSpecDao.setPeriodStartTime(this.mWorkSpecId, System.currentTimeMillis());
            this.mWorkSpecDao.setState(State.ENQUEUED, this.mWorkSpecId);
            this.mWorkSpecDao.resetWorkSpecRunAttemptCount(this.mWorkSpecId);
            this.mWorkSpecDao.markWorkSpecScheduled(this.mWorkSpecId, -1);
            this.mWorkDatabase.setTransactionSuccessful();
        } finally {
            this.mWorkDatabase.endTransaction();
            resolve(false);
        }
    }

    private void setSucceededAndResolve() {
        this.mWorkDatabase.beginTransaction();
        try {
            this.mWorkSpecDao.setState(State.SUCCEEDED, this.mWorkSpecId);
            this.mWorkSpecDao.setOutput(this.mWorkSpecId, ((Success) this.mResult).getOutputData());
            long currentTimeMillis = System.currentTimeMillis();
            for (String str : this.mDependencyDao.getDependentWorkIds(this.mWorkSpecId)) {
                if (this.mWorkSpecDao.getState(str) == State.BLOCKED && this.mDependencyDao.hasCompletedAllPrerequisites(str)) {
                    Logger.get().info(TAG, String.format("Setting status to enqueued for %s", new Object[]{str}), new Throwable[0]);
                    this.mWorkSpecDao.setState(State.ENQUEUED, str);
                    this.mWorkSpecDao.setPeriodStartTime(str, currentTimeMillis);
                }
            }
            this.mWorkDatabase.setTransactionSuccessful();
        } finally {
            this.mWorkDatabase.endTransaction();
            resolve(false);
        }
    }

    private String createWorkDescription(List<String> list) {
        StringBuilder sb = new StringBuilder("Work [ id=");
        sb.append(this.mWorkSpecId);
        sb.append(", tags={ ");
        boolean z = true;
        for (String str : list) {
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(str);
        }
        sb.append(" } ]");
        return sb.toString();
    }
}
