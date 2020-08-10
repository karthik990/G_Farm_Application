package com.google.firebase.storage;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.storage.StorageTask.SnapshotBase;
import com.google.firebase.storage.internal.ExponentialBackoffSender;
import com.google.firebase.storage.network.GetNetworkRequest;
import com.google.firebase.storage.network.NetworkRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class StreamDownloadTask extends StorageTask<TaskSnapshot> {
    static final long PREFERRED_CHUNK_SIZE = 262144;
    private static final String TAG = "StreamDownloadTask";
    private long mBytesDownloaded;
    private long mBytesDownloadedSnapped;
    private String mETagVerification;
    private volatile Exception mException = null;
    /* access modifiers changed from: private */
    public InputStream mInputStream;
    private StreamProcessor mProcessor;
    /* access modifiers changed from: private */
    public NetworkRequest mRequest;
    private volatile int mResultCode = 0;
    private ExponentialBackoffSender mSender;
    private StorageReference mStorageRef;
    private long mTotalBytes = -1;

    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    public interface StreamProcessor {
        void doInBackground(TaskSnapshot taskSnapshot, InputStream inputStream) throws IOException;
    }

    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    static class StreamProgressWrapper extends InputStream {
        private long mDownloadedBytes;
        private Callable<InputStream> mInputStreamCallable;
        private long mLastExceptionPosition;
        private StreamDownloadTask mParentTask;
        private boolean mStreamClosed;
        private IOException mTemporaryException;
        private InputStream mWrappedStream;

        public void mark(int i) {
        }

        public boolean markSupported() {
            return false;
        }

        StreamProgressWrapper(Callable<InputStream> callable, StreamDownloadTask streamDownloadTask) {
            this.mParentTask = streamDownloadTask;
            this.mInputStreamCallable = callable;
        }

        private void checkCancel() throws IOException {
            StreamDownloadTask streamDownloadTask = this.mParentTask;
            if (streamDownloadTask != null && streamDownloadTask.getInternalState() == 32) {
                throw new CancelException();
            }
        }

        private void recordDownloadedBytes(long j) {
            StreamDownloadTask streamDownloadTask = this.mParentTask;
            if (streamDownloadTask != null) {
                streamDownloadTask.recordDownloadedBytes(j);
            }
            this.mDownloadedBytes += j;
        }

        /* access modifiers changed from: private */
        public boolean ensureStream() throws IOException {
            checkCancel();
            if (this.mTemporaryException != null) {
                try {
                    if (this.mWrappedStream != null) {
                        this.mWrappedStream.close();
                    }
                } catch (IOException unused) {
                }
                this.mWrappedStream = null;
                long j = this.mLastExceptionPosition;
                long j2 = this.mDownloadedBytes;
                String str = StreamDownloadTask.TAG;
                if (j == j2) {
                    Log.i(str, "Encountered exception during stream operation. Aborting.", this.mTemporaryException);
                    return false;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Encountered exception during stream operation. Retrying at ");
                sb.append(this.mDownloadedBytes);
                Log.i(str, sb.toString(), this.mTemporaryException);
                this.mLastExceptionPosition = this.mDownloadedBytes;
                this.mTemporaryException = null;
            }
            if (!this.mStreamClosed) {
                if (this.mWrappedStream == null) {
                    try {
                        this.mWrappedStream = (InputStream) this.mInputStreamCallable.call();
                    } catch (Exception e) {
                        if (e instanceof IOException) {
                            throw ((IOException) e);
                        }
                        throw new IOException("Unable to open stream", e);
                    }
                }
                return true;
            }
            throw new IOException("Can't perform operation on closed stream");
        }

        public int read() throws IOException {
            while (ensureStream()) {
                try {
                    int read = this.mWrappedStream.read();
                    if (read != -1) {
                        recordDownloadedBytes(1);
                    }
                    return read;
                } catch (IOException e) {
                    this.mTemporaryException = e;
                }
            }
            throw this.mTemporaryException;
        }

        public int available() throws IOException {
            while (ensureStream()) {
                try {
                    return this.mWrappedStream.available();
                } catch (IOException e) {
                    this.mTemporaryException = e;
                }
            }
            throw this.mTemporaryException;
        }

        public void close() throws IOException {
            InputStream inputStream = this.mWrappedStream;
            if (inputStream != null) {
                inputStream.close();
            }
            this.mStreamClosed = true;
            StreamDownloadTask streamDownloadTask = this.mParentTask;
            if (!(streamDownloadTask == null || streamDownloadTask.mRequest == null)) {
                this.mParentTask.mRequest.performRequestEnd();
                this.mParentTask.mRequest = null;
            }
            checkCancel();
        }

        public int read(byte[] bArr, int i, int i2) throws IOException {
            int i3 = 0;
            while (ensureStream()) {
                while (((long) i2) > 262144) {
                    try {
                        int read = this.mWrappedStream.read(bArr, i, 262144);
                        if (read == -1) {
                            if (i3 == 0) {
                                i3 = -1;
                            }
                            return i3;
                        }
                        i3 += read;
                        i += read;
                        i2 -= read;
                        recordDownloadedBytes((long) read);
                        checkCancel();
                    } catch (IOException e) {
                        this.mTemporaryException = e;
                    }
                }
                if (i2 > 0) {
                    int read2 = this.mWrappedStream.read(bArr, i, i2);
                    if (read2 == -1) {
                        if (i3 == 0) {
                            i3 = -1;
                        }
                        return i3;
                    }
                    i += read2;
                    i3 += read2;
                    i2 -= read2;
                    recordDownloadedBytes((long) read2);
                }
                if (i2 == 0) {
                    return i3;
                }
            }
            throw this.mTemporaryException;
        }

        public long skip(long j) throws IOException {
            long j2 = j;
            long j3 = 0;
            while (ensureStream()) {
                while (j2 > 262144) {
                    try {
                        long skip = this.mWrappedStream.skip(262144);
                        if (skip < 0) {
                            if (j3 == 0) {
                                j3 = -1;
                            }
                            return j3;
                        }
                        j3 += skip;
                        j2 -= skip;
                        recordDownloadedBytes(skip);
                        checkCancel();
                    } catch (IOException e) {
                        this.mTemporaryException = e;
                    }
                }
                if (j2 > 0) {
                    long skip2 = this.mWrappedStream.skip(j2);
                    if (skip2 < 0) {
                        if (j3 == 0) {
                            j3 = -1;
                        }
                        return j3;
                    }
                    j3 += skip2;
                    j2 -= skip2;
                    recordDownloadedBytes(skip2);
                }
                if (j2 == 0) {
                    return j3;
                }
            }
            throw this.mTemporaryException;
        }
    }

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
            return StreamDownloadTask.this.getTotalBytes();
        }

        public InputStream getStream() {
            return StreamDownloadTask.this.mInputStream;
        }
    }

    private boolean isValidHttpResponseCode(int i) {
        return i == 308 || (i >= 200 && i < 300);
    }

    StreamDownloadTask(StorageReference storageReference) {
        this.mStorageRef = storageReference;
        FirebaseStorage storage = this.mStorageRef.getStorage();
        this.mSender = new ExponentialBackoffSender(storage.getApp().getApplicationContext(), storage.getAuthProvider(), storage.getMaxDownloadRetryTimeMillis());
    }

    /* access modifiers changed from: 0000 */
    public StreamDownloadTask setStreamProcessor(StreamProcessor streamProcessor) {
        Preconditions.checkNotNull(streamProcessor);
        Preconditions.checkState(this.mProcessor == null);
        this.mProcessor = streamProcessor;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public StorageReference getStorage() {
        return this.mStorageRef;
    }

    /* access modifiers changed from: 0000 */
    public long getTotalBytes() {
        return this.mTotalBytes;
    }

    /* access modifiers changed from: 0000 */
    public void recordDownloadedBytes(long j) {
        this.mBytesDownloaded += j;
        if (this.mBytesDownloadedSnapped + 262144 > this.mBytesDownloaded) {
            return;
        }
        if (getInternalState() == 4) {
            tryChangeState(4, false);
        } else {
            this.mBytesDownloadedSnapped = this.mBytesDownloaded;
        }
    }

    /* access modifiers changed from: protected */
    public void schedule() {
        StorageTaskScheduler.getInstance().scheduleDownload(getRunnable());
    }

    /* access modifiers changed from: private */
    public InputStream createDownloadStream() throws Exception {
        this.mSender.reset();
        NetworkRequest networkRequest = this.mRequest;
        if (networkRequest != null) {
            networkRequest.performRequestEnd();
        }
        this.mRequest = new GetNetworkRequest(this.mStorageRef.getStorageUri(), this.mStorageRef.getApp(), this.mBytesDownloaded);
        boolean z = false;
        this.mSender.sendWithExponentialBackoff(this.mRequest, false);
        this.mResultCode = this.mRequest.getResultCode();
        this.mException = this.mRequest.getException() != null ? this.mRequest.getException() : this.mException;
        if (isValidHttpResponseCode(this.mResultCode) && this.mException == null && getInternalState() == 4) {
            z = true;
        }
        if (z) {
            String resultString = this.mRequest.getResultString("ETag");
            if (!TextUtils.isEmpty(resultString)) {
                String str = this.mETagVerification;
                if (str != null && !str.equals(resultString)) {
                    this.mResultCode = 409;
                    throw new IOException("The ETag on the server changed.");
                }
            }
            this.mETagVerification = resultString;
            if (this.mTotalBytes == -1) {
                this.mTotalBytes = (long) this.mRequest.getResultingContentLength();
            }
            return this.mRequest.getStream();
        }
        throw new IOException("Could not open resulting stream.");
    }

    /* access modifiers changed from: 0000 */
    public void run() {
        String str = TAG;
        int i = 64;
        if (this.mException != null) {
            tryChangeState(64, false);
        } else if (tryChangeState(4, false)) {
            StreamProgressWrapper streamProgressWrapper = new StreamProgressWrapper(new Callable<InputStream>() {
                public InputStream call() throws Exception {
                    return StreamDownloadTask.this.createDownloadStream();
                }
            }, this);
            this.mInputStream = new BufferedInputStream(streamProgressWrapper);
            try {
                streamProgressWrapper.ensureStream();
                if (this.mProcessor != null) {
                    try {
                        this.mProcessor.doInBackground((TaskSnapshot) snapState(), this.mInputStream);
                    } catch (Exception e) {
                        Log.w(str, "Exception occurred calling doInBackground.", e);
                        this.mException = e;
                    }
                }
            } catch (IOException e2) {
                Log.d(str, "Initial opening of Stream failed", e2);
                this.mException = e2;
            }
            if (this.mInputStream == null) {
                this.mRequest.performRequestEnd();
                this.mRequest = null;
            }
            if (this.mException == null && getInternalState() == 4) {
                tryChangeState(4, false);
                tryChangeState(128, false);
            } else {
                if (getInternalState() == 32) {
                    i = 256;
                }
                if (!tryChangeState(i, false)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unable to change download task to final state from ");
                    sb.append(getInternalState());
                    Log.w(str, sb.toString());
                }
            }
        }
    }

    public boolean resume() {
        throw new UnsupportedOperationException("this operation is not supported on StreamDownloadTask.");
    }

    public boolean pause() {
        throw new UnsupportedOperationException("this operation is not supported on StreamDownloadTask.");
    }

    /* access modifiers changed from: 0000 */
    public TaskSnapshot snapStateImpl() {
        return new TaskSnapshot(StorageException.fromExceptionAndHttpCode(this.mException, this.mResultCode), this.mBytesDownloadedSnapped);
    }

    /* access modifiers changed from: protected */
    public void onCanceled() {
        this.mSender.cancel();
        this.mException = StorageException.fromErrorStatus(Status.RESULT_CANCELED);
    }

    /* access modifiers changed from: protected */
    public void onProgress() {
        this.mBytesDownloadedSnapped = this.mBytesDownloaded;
    }
}
