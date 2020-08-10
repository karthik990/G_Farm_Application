package androidx.work;

import android.content.Context;

public abstract class WorkerFactory {
    private static final String TAG = Logger.tagWithPrefix("WorkerFactory");

    public abstract ListenableWorker createWorker(Context context, String str, WorkerParameters workerParameters);

    public final ListenableWorker createWorkerWithDefaultFallback(Context context, String str, WorkerParameters workerParameters) {
        ListenableWorker createWorker = createWorker(context, str, workerParameters);
        if (createWorker != null) {
            return createWorker;
        }
        try {
            try {
                return (ListenableWorker) Class.forName(str).asSubclass(ListenableWorker.class).getDeclaredConstructor(new Class[]{Context.class, WorkerParameters.class}).newInstance(new Object[]{context, workerParameters});
            } catch (Exception e) {
                Logger logger = Logger.get();
                String str2 = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Could not instantiate ");
                sb.append(str);
                logger.error(str2, sb.toString(), e);
                return null;
            }
        } catch (ClassNotFoundException unused) {
            Logger logger2 = Logger.get();
            String str3 = TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Class not found: ");
            sb2.append(str);
            logger2.error(str3, sb2.toString(), new Throwable[0]);
            return null;
        }
    }

    public static WorkerFactory getDefaultWorkerFactory() {
        return new WorkerFactory() {
            public ListenableWorker createWorker(Context context, String str, WorkerParameters workerParameters) {
                return null;
            }
        };
    }
}
