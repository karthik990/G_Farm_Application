package com.firebase.p037ui.storage.images;

import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.DataFetcher.DataCallback;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoader.LoadData;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.StreamDownloadTask.TaskSnapshot;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/* renamed from: com.firebase.ui.storage.images.FirebaseImageLoader */
public class FirebaseImageLoader implements ModelLoader<StorageReference, InputStream> {
    private static final String TAG = "FirebaseImageLoader";

    /* renamed from: com.firebase.ui.storage.images.FirebaseImageLoader$Factory */
    public static class Factory implements ModelLoaderFactory<StorageReference, InputStream> {
        public void teardown() {
        }

        public ModelLoader<StorageReference, InputStream> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new FirebaseImageLoader();
        }
    }

    /* renamed from: com.firebase.ui.storage.images.FirebaseImageLoader$FirebaseStorageFetcher */
    private static class FirebaseStorageFetcher implements DataFetcher<InputStream> {
        /* access modifiers changed from: private */
        public InputStream mInputStream;
        private StorageReference mRef;
        private StreamDownloadTask mStreamTask;

        public FirebaseStorageFetcher(StorageReference storageReference) {
            this.mRef = storageReference;
        }

        public void loadData(Priority priority, final DataCallback<? super InputStream> dataCallback) {
            this.mStreamTask = this.mRef.getStream();
            this.mStreamTask.addOnSuccessListener((OnSuccessListener) new OnSuccessListener<TaskSnapshot>() {
                public void onSuccess(TaskSnapshot taskSnapshot) {
                    FirebaseStorageFetcher.this.mInputStream = taskSnapshot.getStream();
                    dataCallback.onDataReady(FirebaseStorageFetcher.this.mInputStream);
                }
            }).addOnFailureListener((OnFailureListener) new OnFailureListener() {
                public void onFailure(Exception exc) {
                    dataCallback.onLoadFailed(exc);
                }
            });
        }

        public void cleanup() {
            InputStream inputStream = this.mInputStream;
            if (inputStream != null) {
                try {
                    inputStream.close();
                    this.mInputStream = null;
                } catch (IOException e) {
                    Log.w(FirebaseImageLoader.TAG, "Could not close stream", e);
                }
            }
        }

        public void cancel() {
            StreamDownloadTask streamDownloadTask = this.mStreamTask;
            if (streamDownloadTask != null && streamDownloadTask.isInProgress()) {
                this.mStreamTask.cancel();
            }
        }

        public Class<InputStream> getDataClass() {
            return InputStream.class;
        }

        public DataSource getDataSource() {
            return DataSource.REMOTE;
        }
    }

    /* renamed from: com.firebase.ui.storage.images.FirebaseImageLoader$FirebaseStorageKey */
    private static class FirebaseStorageKey implements Key {
        private StorageReference mRef;

        public FirebaseStorageKey(StorageReference storageReference) {
            this.mRef = storageReference;
        }

        public void updateDiskCacheKey(MessageDigest messageDigest) {
            messageDigest.update(this.mRef.getPath().getBytes(Charset.defaultCharset()));
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return this.mRef.equals(((FirebaseStorageKey) obj).mRef);
        }

        public int hashCode() {
            return this.mRef.hashCode();
        }
    }

    public boolean handles(StorageReference storageReference) {
        return true;
    }

    public LoadData<InputStream> buildLoadData(StorageReference storageReference, int i, int i2, Options options) {
        return new LoadData<>(new FirebaseStorageKey(storageReference), new FirebaseStorageFetcher(storageReference));
    }
}
