package com.google.firebase.storage;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.firebase.storage.StorageTask.SnapshotBase;
import com.google.firebase.storage.internal.ExponentialBackoffSender;
import com.google.firebase.storage.network.GetNetworkRequest;
import com.google.firebase.storage.network.NetworkRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class FileDownloadTask extends StorageTask<TaskSnapshot> {
    static final int PREFERRED_CHUNK_SIZE = 262144;
    private static final String TAG = "FileDownloadTask";
    private long mBytesDownloaded;
    private final Uri mDestinationFile;
    private String mETagVerification = null;
    private volatile Exception mException = null;
    private int mResultCode;
    private long mResumeOffset = 0;
    private ExponentialBackoffSender mSender;
    private StorageReference mStorageRef;
    private long mTotalBytes = -1;

    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    public class TaskSnapshot extends SnapshotBase {
        private final long mBytesDownloaded;

        TaskSnapshot(Exception exc, long j) {
            super(exc);
            this.mBytesDownloaded = j;
        }

        public long getBytesTransferred() {
            return this.mBytesDownloaded;
        }

        public long getTotalByteCount() {
            return FileDownloadTask.this.getTotalBytes();
        }
    }

    private boolean isValidHttpResponseCode(int i) {
        return i == 308 || (i >= 200 && i < 300);
    }

    FileDownloadTask(StorageReference storageReference, Uri uri) {
        this.mStorageRef = storageReference;
        this.mDestinationFile = uri;
        FirebaseStorage storage = this.mStorageRef.getStorage();
        this.mSender = new ExponentialBackoffSender(storage.getApp().getApplicationContext(), storage.getAuthProvider(), storage.getMaxDownloadRetryTimeMillis());
    }

    /* access modifiers changed from: 0000 */
    public long getDownloadedSizeInBytes() {
        return this.mBytesDownloaded;
    }

    /* access modifiers changed from: 0000 */
    public long getTotalBytes() {
        return this.mTotalBytes;
    }

    /* access modifiers changed from: 0000 */
    public StorageReference getStorage() {
        return this.mStorageRef;
    }

    /* access modifiers changed from: protected */
    public void schedule() {
        StorageTaskScheduler.getInstance().scheduleDownload(getRunnable());
    }

    /* access modifiers changed from: 0000 */
    public TaskSnapshot snapStateImpl() {
        return new TaskSnapshot(StorageException.fromExceptionAndHttpCode(this.mException, this.mResultCode), this.mBytesDownloaded + this.mResumeOffset);
    }

    private int fillBuffer(InputStream inputStream, byte[] bArr) {
        int i = 0;
        boolean z = false;
        while (i != bArr.length) {
            try {
                int read = inputStream.read(bArr, i, bArr.length - i);
                if (read == -1) {
                    break;
                }
                z = true;
                i += read;
            } catch (IOException e) {
                this.mException = e;
            }
        }
        if (z) {
            return i;
        }
        return -1;
    }

    /* JADX INFO: finally extract failed */
    private boolean processResponse(NetworkRequest networkRequest) throws IOException {
        FileOutputStream fileOutputStream;
        InputStream stream = networkRequest.getStream();
        if (stream != null) {
            File file = new File(this.mDestinationFile.getPath());
            boolean exists = file.exists();
            String str = TAG;
            if (!exists) {
                if (this.mResumeOffset > 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("The file downloading to has been deleted:");
                    sb.append(file.getAbsolutePath());
                    Log.e(str, sb.toString());
                    throw new IllegalStateException("expected a file to resume from.");
                } else if (!file.createNewFile()) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("unable to create file:");
                    sb2.append(file.getAbsolutePath());
                    Log.w(str, sb2.toString());
                }
            }
            boolean z = true;
            if (this.mResumeOffset > 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Resuming download file ");
                sb3.append(file.getAbsolutePath());
                sb3.append(" at ");
                sb3.append(this.mResumeOffset);
                Log.d(str, sb3.toString());
                fileOutputStream = new FileOutputStream(file, true);
            } else {
                fileOutputStream = new FileOutputStream(file);
            }
            try {
                byte[] bArr = new byte[262144];
                while (z) {
                    int fillBuffer = fillBuffer(stream, bArr);
                    if (fillBuffer == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, fillBuffer);
                    this.mBytesDownloaded += (long) fillBuffer;
                    if (this.mException != null) {
                        Log.d(str, "Exception occurred during file download. Retrying.", this.mException);
                        this.mException = null;
                        z = false;
                    }
                    if (!tryChangeState(4, false)) {
                        z = false;
                    }
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                stream.close();
                return z;
            } catch (Throwable th) {
                fileOutputStream.flush();
                fileOutputStream.close();
                stream.close();
                throw th;
            }
        } else {
            this.mException = new IllegalStateException("Unable to open Firebase Storage stream.");
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void run() {
        if (this.mException != null) {
            tryChangeState(64, false);
        } else if (tryChangeState(4, false)) {
            do {
                this.mBytesDownloaded = 0;
                this.mException = null;
                this.mSender.reset();
                GetNetworkRequest getNetworkRequest = new GetNetworkRequest(this.mStorageRef.getStorageUri(), this.mStorageRef.getApp(), this.mResumeOffset);
                this.mSender.sendWithExponentialBackoff(getNetworkRequest, false);
                this.mResultCode = getNetworkRequest.getResultCode();
                this.mException = getNetworkRequest.getException() != null ? getNetworkRequest.getException() : this.mException;
                boolean z = true;
                boolean z2 = isValidHttpResponseCode(this.mResultCode) && this.mException == null && getInternalState() == 4;
                String str = TAG;
                if (z2) {
                    this.mTotalBytes = (long) getNetworkRequest.getResultingContentLength();
                    String resultString = getNetworkRequest.getResultString("ETag");
                    if (!TextUtils.isEmpty(resultString)) {
                        String str2 = this.mETagVerification;
                        if (str2 != null && !str2.equals(resultString)) {
                            Log.w(str, "The file at the server has changed.  Restarting from the beginning.");
                            this.mResumeOffset = 0;
                            this.mETagVerification = null;
                            getNetworkRequest.performRequestEnd();
                            schedule();
                            return;
                        }
                    }
                    this.mETagVerification = resultString;
                    try {
                        z2 = processResponse(getNetworkRequest);
                    } catch (IOException e) {
                        Log.e(str, "Exception occurred during file write.  Aborting.", e);
                        this.mException = e;
                    }
                }
                getNetworkRequest.performRequestEnd();
                if (!(z2 && this.mException == null && getInternalState() == 4)) {
                    z = false;
                }
                if (z) {
                    tryChangeState(128, false);
                    return;
                }
                File file = new File(this.mDestinationFile.getPath());
                if (file.exists()) {
                    this.mResumeOffset = file.length();
                } else {
                    this.mResumeOffset = 0;
                }
                if (getInternalState() == 8) {
                    tryChangeState(16, false);
                    return;
                } else if (getInternalState() == 32) {
                    if (!tryChangeState(256, false)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unable to change download task to final state from ");
                        sb.append(getInternalState());
                        Log.w(str, sb.toString());
                    }
                    return;
                }
            } while (this.mBytesDownloaded > 0);
            tryChangeState(64, false);
        }
    }

    /* access modifiers changed from: protected */
    public void onCanceled() {
        this.mSender.cancel();
        this.mException = StorageException.fromErrorStatus(Status.RESULT_CANCELED);
    }
}
