package com.google.firebase.storage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class StorageTaskScheduler {
    private static final ThreadPoolExecutor CALLBACK_QUEUE_EXECUTOR;
    private static final ThreadPoolExecutor COMMAND_POOL_EXECUTOR;
    private static final ThreadPoolExecutor DOWNLOAD_QUEUE_EXECUTOR;
    private static final ThreadPoolExecutor UPLOAD_QUEUE_EXECUTOR;
    private static BlockingQueue<Runnable> mCallbackQueue = new LinkedBlockingQueue();
    private static BlockingQueue<Runnable> mCommandQueue = new LinkedBlockingQueue();
    private static BlockingQueue<Runnable> mDownloadQueue = new LinkedBlockingQueue();
    private static BlockingQueue<Runnable> mUploadQueue = new LinkedBlockingQueue();
    public static StorageTaskScheduler sInstance = new StorageTaskScheduler();

    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    static class StorageThreadFactory implements ThreadFactory {
        private final String mNameSuffix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        StorageThreadFactory(String str) {
            this.mNameSuffix = str;
        }

        public Thread newThread(Runnable runnable) {
            StringBuilder sb = new StringBuilder();
            sb.append("FirebaseStorage-");
            sb.append(this.mNameSuffix);
            sb.append(this.threadNumber.getAndIncrement());
            Thread thread = new Thread(runnable, sb.toString());
            thread.setDaemon(false);
            thread.setPriority(9);
            return thread;
        }
    }

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, mCommandQueue, new StorageThreadFactory("Command-"));
        COMMAND_POOL_EXECUTOR = threadPoolExecutor;
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(2, 2, 5, TimeUnit.SECONDS, mUploadQueue, new StorageThreadFactory("Upload-"));
        UPLOAD_QUEUE_EXECUTOR = threadPoolExecutor2;
        ThreadPoolExecutor threadPoolExecutor3 = new ThreadPoolExecutor(3, 3, 5, TimeUnit.SECONDS, mDownloadQueue, new StorageThreadFactory("Download-"));
        DOWNLOAD_QUEUE_EXECUTOR = threadPoolExecutor3;
        ThreadPoolExecutor threadPoolExecutor4 = new ThreadPoolExecutor(1, 1, 5, TimeUnit.SECONDS, mCallbackQueue, new StorageThreadFactory("Callbacks-"));
        CALLBACK_QUEUE_EXECUTOR = threadPoolExecutor4;
        COMMAND_POOL_EXECUTOR.allowCoreThreadTimeOut(true);
        UPLOAD_QUEUE_EXECUTOR.allowCoreThreadTimeOut(true);
        DOWNLOAD_QUEUE_EXECUTOR.allowCoreThreadTimeOut(true);
        CALLBACK_QUEUE_EXECUTOR.allowCoreThreadTimeOut(true);
    }

    public static StorageTaskScheduler getInstance() {
        return sInstance;
    }

    public void scheduleCommand(Runnable runnable) {
        COMMAND_POOL_EXECUTOR.execute(runnable);
    }

    public void scheduleUpload(Runnable runnable) {
        UPLOAD_QUEUE_EXECUTOR.execute(runnable);
    }

    public void scheduleDownload(Runnable runnable) {
        DOWNLOAD_QUEUE_EXECUTOR.execute(runnable);
    }

    public void scheduleCallback(Runnable runnable) {
        CALLBACK_QUEUE_EXECUTOR.execute(runnable);
    }
}
