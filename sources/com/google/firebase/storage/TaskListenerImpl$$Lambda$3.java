package com.google.firebase.storage;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
final /* synthetic */ class TaskListenerImpl$$Lambda$3 implements Runnable {
    private final TaskListenerImpl arg$1;
    private final Object arg$2;
    private final ProvideError arg$3;

    private TaskListenerImpl$$Lambda$3(TaskListenerImpl taskListenerImpl, Object obj, ProvideError provideError) {
        this.arg$1 = taskListenerImpl;
        this.arg$2 = obj;
        this.arg$3 = provideError;
    }

    public static Runnable lambdaFactory$(TaskListenerImpl taskListenerImpl, Object obj, ProvideError provideError) {
        return new TaskListenerImpl$$Lambda$3(taskListenerImpl, obj, provideError);
    }

    public void run() {
        this.arg$1.mOnRaise.raise(this.arg$2, this.arg$3);
    }
}
