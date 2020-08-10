package com.google.firebase.storage;

import android.app.Activity;
import android.os.Build.VERSION;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.storage.StorageTask.ProvideError;
import com.google.firebase.storage.internal.ActivityLifecycleListener;
import com.google.firebase.storage.internal.SmartHandler;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
class TaskListenerImpl<TListenerType, TResult extends ProvideError> {
    private final HashMap<TListenerType, SmartHandler> mHandlerMap = new HashMap<>();
    private final Queue<TListenerType> mListenerQueue = new ConcurrentLinkedQueue();
    private OnRaise<TListenerType, TResult> mOnRaise;
    private int mTargetStates;
    private StorageTask<TResult> mTask;

    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    interface OnRaise<TListenerType, TResult> {
        void raise(TListenerType tlistenertype, TResult tresult);
    }

    public TaskListenerImpl(StorageTask<TResult> storageTask, int i, OnRaise<TListenerType, TResult> onRaise) {
        this.mTask = storageTask;
        this.mTargetStates = i;
        this.mOnRaise = onRaise;
    }

    public int getListenerCount() {
        return Math.max(this.mListenerQueue.size(), this.mHandlerMap.size());
    }

    public void addListener(Activity activity, Executor executor, TListenerType tlistenertype) {
        boolean z;
        SmartHandler smartHandler;
        Preconditions.checkNotNull(tlistenertype);
        synchronized (this.mTask.getSyncObject()) {
            boolean z2 = true;
            z = (this.mTask.getInternalState() & this.mTargetStates) != 0;
            this.mListenerQueue.add(tlistenertype);
            smartHandler = new SmartHandler(executor);
            this.mHandlerMap.put(tlistenertype, smartHandler);
            if (activity != null) {
                if (VERSION.SDK_INT >= 17) {
                    if (activity.isDestroyed()) {
                        z2 = false;
                    }
                    Preconditions.checkArgument(z2, "Activity is already destroyed!");
                }
                ActivityLifecycleListener.getInstance().runOnActivityStopped(activity, tlistenertype, TaskListenerImpl$$Lambda$1.lambdaFactory$(this, tlistenertype));
            }
        }
        if (z) {
            smartHandler.callBack(TaskListenerImpl$$Lambda$2.lambdaFactory$(this, tlistenertype, this.mTask.snapState()));
        }
    }

    public void onInternalStateChanged() {
        if ((this.mTask.getInternalState() & this.mTargetStates) != 0) {
            ProvideError snapState = this.mTask.snapState();
            for (Object next : this.mListenerQueue) {
                SmartHandler smartHandler = (SmartHandler) this.mHandlerMap.get(next);
                if (smartHandler != null) {
                    smartHandler.callBack(TaskListenerImpl$$Lambda$3.lambdaFactory$(this, next, snapState));
                }
            }
        }
    }

    public void removeListener(TListenerType tlistenertype) {
        Preconditions.checkNotNull(tlistenertype);
        synchronized (this.mTask.getSyncObject()) {
            this.mHandlerMap.remove(tlistenertype);
            this.mListenerQueue.remove(tlistenertype);
            ActivityLifecycleListener.getInstance().removeCookie(tlistenertype);
        }
    }
}
