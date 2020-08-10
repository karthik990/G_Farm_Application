package com.google.firebase.storage;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.StreamDownloadTask.StreamProcessor;
import com.google.firebase.storage.StreamDownloadTask.TaskSnapshot;
import com.google.firebase.storage.internal.SlashUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class StorageReference {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String TAG = "StorageReference";
    private final FirebaseStorage mFirebaseStorage;
    private final Uri mStorageUri;

    StorageReference(Uri uri, FirebaseStorage firebaseStorage) {
        boolean z = true;
        Preconditions.checkArgument(uri != null, "storageUri cannot be null");
        if (firebaseStorage == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "FirebaseApp cannot be null");
        this.mStorageUri = uri;
        this.mFirebaseStorage = firebaseStorage;
    }

    public StorageReference child(String str) {
        Preconditions.checkArgument(!TextUtils.isEmpty(str), "childName cannot be null or empty");
        String normalizeSlashes = SlashUtil.normalizeSlashes(str);
        try {
            return new StorageReference(this.mStorageUri.buildUpon().appendEncodedPath(SlashUtil.preserveSlashEncode(normalizeSlashes)).build(), this.mFirebaseStorage);
        } catch (UnsupportedEncodingException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to create a valid default Uri. ");
            sb.append(normalizeSlashes);
            Log.e(TAG, sb.toString(), e);
            throw new IllegalArgumentException("childName");
        }
    }

    public StorageReference getParent() {
        String path = this.mStorageUri.getPath();
        if (!TextUtils.isEmpty(path)) {
            String str = "/";
            if (!path.equals(str)) {
                int lastIndexOf = path.lastIndexOf(47);
                if (lastIndexOf != -1) {
                    str = path.substring(0, lastIndexOf);
                }
                return new StorageReference(this.mStorageUri.buildUpon().path(str).build(), this.mFirebaseStorage);
            }
        }
        return null;
    }

    public StorageReference getRoot() {
        return new StorageReference(this.mStorageUri.buildUpon().path("").build(), this.mFirebaseStorage);
    }

    public String getName() {
        String path = this.mStorageUri.getPath();
        int lastIndexOf = path.lastIndexOf(47);
        return lastIndexOf != -1 ? path.substring(lastIndexOf + 1) : path;
    }

    public String getPath() {
        return this.mStorageUri.getPath();
    }

    public String getBucket() {
        return this.mStorageUri.getAuthority();
    }

    public FirebaseStorage getStorage() {
        return this.mFirebaseStorage;
    }

    /* access modifiers changed from: 0000 */
    public FirebaseApp getApp() {
        return getStorage().getApp();
    }

    public UploadTask putBytes(byte[] bArr) {
        Preconditions.checkArgument(bArr != null, "bytes cannot be null");
        UploadTask uploadTask = new UploadTask(this, (StorageMetadata) null, bArr);
        uploadTask.queue();
        return uploadTask;
    }

    public UploadTask putBytes(byte[] bArr, StorageMetadata storageMetadata) {
        boolean z = true;
        Preconditions.checkArgument(bArr != null, "bytes cannot be null");
        if (storageMetadata == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "metadata cannot be null");
        UploadTask uploadTask = new UploadTask(this, storageMetadata, bArr);
        uploadTask.queue();
        return uploadTask;
    }

    public UploadTask putFile(Uri uri) {
        Preconditions.checkArgument(uri != null, "uri cannot be null");
        UploadTask uploadTask = new UploadTask(this, null, uri, null);
        uploadTask.queue();
        return uploadTask;
    }

    public UploadTask putFile(Uri uri, StorageMetadata storageMetadata) {
        boolean z = true;
        Preconditions.checkArgument(uri != null, "uri cannot be null");
        if (storageMetadata == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "metadata cannot be null");
        UploadTask uploadTask = new UploadTask(this, storageMetadata, uri, null);
        uploadTask.queue();
        return uploadTask;
    }

    public UploadTask putFile(Uri uri, StorageMetadata storageMetadata, Uri uri2) {
        boolean z = true;
        Preconditions.checkArgument(uri != null, "uri cannot be null");
        if (storageMetadata == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "metadata cannot be null");
        UploadTask uploadTask = new UploadTask(this, storageMetadata, uri, uri2);
        uploadTask.queue();
        return uploadTask;
    }

    public UploadTask putStream(InputStream inputStream) {
        Preconditions.checkArgument(inputStream != null, "stream cannot be null");
        UploadTask uploadTask = new UploadTask(this, (StorageMetadata) null, inputStream);
        uploadTask.queue();
        return uploadTask;
    }

    public UploadTask putStream(InputStream inputStream, StorageMetadata storageMetadata) {
        boolean z = true;
        Preconditions.checkArgument(inputStream != null, "stream cannot be null");
        if (storageMetadata == null) {
            z = false;
        }
        Preconditions.checkArgument(z, "metadata cannot be null");
        UploadTask uploadTask = new UploadTask(this, storageMetadata, inputStream);
        uploadTask.queue();
        return uploadTask;
    }

    public List<UploadTask> getActiveUploadTasks() {
        return StorageTaskManager.getInstance().getUploadTasksUnder(this);
    }

    public List<FileDownloadTask> getActiveDownloadTasks() {
        return StorageTaskManager.getInstance().getDownloadTasksUnder(this);
    }

    public Task<StorageMetadata> getMetadata() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        StorageTaskScheduler.getInstance().scheduleCommand(new GetMetadataTask(this, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    public Task<Uri> getDownloadUrl() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        StorageTaskScheduler.getInstance().scheduleCommand(new GetDownloadUrlTask(this, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    public Task<StorageMetadata> updateMetadata(StorageMetadata storageMetadata) {
        Preconditions.checkNotNull(storageMetadata);
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        StorageTaskScheduler.getInstance().scheduleCommand(new UpdateMetadataTask(this, taskCompletionSource, storageMetadata));
        return taskCompletionSource.getTask();
    }

    public Task<byte[]> getBytes(final long j) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        StreamDownloadTask streamDownloadTask = new StreamDownloadTask(this);
        streamDownloadTask.setStreamProcessor(new StreamProcessor() {
            public void doInBackground(TaskSnapshot taskSnapshot, InputStream inputStream) throws IOException {
                String str = "the maximum allowed buffer size was exceeded.";
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] bArr = new byte[16384];
                    int i = 0;
                    while (true) {
                        int read = inputStream.read(bArr, 0, bArr.length);
                        if (read != -1) {
                            i += read;
                            if (((long) i) <= j) {
                                byteArrayOutputStream.write(bArr, 0, read);
                            } else {
                                Log.e(StorageReference.TAG, str);
                                throw new IndexOutOfBoundsException(str);
                            }
                        } else {
                            byteArrayOutputStream.flush();
                            taskCompletionSource.setResult(byteArrayOutputStream.toByteArray());
                            return;
                        }
                    }
                } finally {
                    inputStream.close();
                }
            }
        }).addOnSuccessListener((OnSuccessListener) new OnSuccessListener<TaskSnapshot>() {
            public void onSuccess(TaskSnapshot taskSnapshot) {
                if (!taskCompletionSource.getTask().isComplete()) {
                    Log.e(StorageReference.TAG, "getBytes 'succeeded', but failed to set a Result.");
                    taskCompletionSource.setException(StorageException.fromErrorStatus(Status.RESULT_INTERNAL_ERROR));
                }
            }
        }).addOnFailureListener((OnFailureListener) new OnFailureListener() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            static {
                Class<StorageReference> cls = StorageReference.class;
            }

            public void onFailure(Exception exc) {
                taskCompletionSource.setException(StorageException.fromExceptionAndHttpCode(exc, 0));
            }
        });
        streamDownloadTask.queue();
        return taskCompletionSource.getTask();
    }

    public FileDownloadTask getFile(Uri uri) {
        FileDownloadTask fileDownloadTask = new FileDownloadTask(this, uri);
        fileDownloadTask.queue();
        return fileDownloadTask;
    }

    public FileDownloadTask getFile(File file) {
        return getFile(Uri.fromFile(file));
    }

    public StreamDownloadTask getStream() {
        StreamDownloadTask streamDownloadTask = new StreamDownloadTask(this);
        streamDownloadTask.queue();
        return streamDownloadTask;
    }

    public StreamDownloadTask getStream(StreamProcessor streamProcessor) {
        StreamDownloadTask streamDownloadTask = new StreamDownloadTask(this);
        streamDownloadTask.setStreamProcessor(streamProcessor);
        streamDownloadTask.queue();
        return streamDownloadTask;
    }

    public Task<Void> delete() {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        StorageTaskScheduler.getInstance().scheduleCommand(new DeleteStorageTask(this, taskCompletionSource));
        return taskCompletionSource.getTask();
    }

    /* access modifiers changed from: 0000 */
    public Uri getStorageUri() {
        return this.mStorageUri;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("gs://");
        sb.append(this.mStorageUri.getAuthority());
        sb.append(this.mStorageUri.getEncodedPath());
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof StorageReference)) {
            return false;
        }
        return ((StorageReference) obj).toString().equals(toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }
}
