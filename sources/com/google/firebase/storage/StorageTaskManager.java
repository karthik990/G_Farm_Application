package com.google.firebase.storage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
class StorageTaskManager {
    private static final StorageTaskManager _instance = new StorageTaskManager();
    private final Map<String, WeakReference<StorageTask>> mInProgressTasks = new HashMap();
    private final Object mSyncObject = new Object();

    StorageTaskManager() {
    }

    static StorageTaskManager getInstance() {
        return _instance;
    }

    public List<UploadTask> getUploadTasksUnder(StorageReference storageReference) {
        List<UploadTask> unmodifiableList;
        synchronized (this.mSyncObject) {
            ArrayList arrayList = new ArrayList();
            String storageReference2 = storageReference.toString();
            for (Entry entry : this.mInProgressTasks.entrySet()) {
                if (((String) entry.getKey()).startsWith(storageReference2)) {
                    StorageTask storageTask = (StorageTask) ((WeakReference) entry.getValue()).get();
                    if (storageTask instanceof UploadTask) {
                        arrayList.add((UploadTask) storageTask);
                    }
                }
            }
            unmodifiableList = Collections.unmodifiableList(arrayList);
        }
        return unmodifiableList;
    }

    public List<FileDownloadTask> getDownloadTasksUnder(StorageReference storageReference) {
        List<FileDownloadTask> unmodifiableList;
        synchronized (this.mSyncObject) {
            ArrayList arrayList = new ArrayList();
            String storageReference2 = storageReference.toString();
            for (Entry entry : this.mInProgressTasks.entrySet()) {
                if (((String) entry.getKey()).startsWith(storageReference2)) {
                    StorageTask storageTask = (StorageTask) ((WeakReference) entry.getValue()).get();
                    if (storageTask instanceof FileDownloadTask) {
                        arrayList.add((FileDownloadTask) storageTask);
                    }
                }
            }
            unmodifiableList = Collections.unmodifiableList(arrayList);
        }
        return unmodifiableList;
    }

    public void ensureRegistered(StorageTask storageTask) {
        synchronized (this.mSyncObject) {
            this.mInProgressTasks.put(storageTask.getStorage().toString(), new WeakReference(storageTask));
        }
    }

    public void unRegister(StorageTask storageTask) {
        synchronized (this.mSyncObject) {
            String storageReference = storageTask.getStorage().toString();
            WeakReference weakReference = (WeakReference) this.mInProgressTasks.get(storageReference);
            StorageTask storageTask2 = weakReference != null ? (StorageTask) weakReference.get() : null;
            if (storageTask2 == null || storageTask2 == storageTask) {
                this.mInProgressTasks.remove(storageReference);
            }
        }
    }
}
