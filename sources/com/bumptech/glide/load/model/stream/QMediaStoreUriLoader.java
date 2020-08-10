package com.bumptech.glide.load.model.stream;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.DataFetcher.DataCallback;
import com.bumptech.glide.load.data.mediastore.MediaStoreUtil;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoader.LoadData;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.signature.ObjectKey;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public final class QMediaStoreUriLoader<DataT> implements ModelLoader<Uri, DataT> {
    private final Context context;
    private final Class<DataT> dataClass;
    private final ModelLoader<File, DataT> fileDelegate;
    private final ModelLoader<Uri, DataT> uriDelegate;

    private static abstract class Factory<DataT> implements ModelLoaderFactory<Uri, DataT> {
        private final Context context;
        private final Class<DataT> dataClass;

        public final void teardown() {
        }

        Factory(Context context2, Class<DataT> cls) {
            this.context = context2;
            this.dataClass = cls;
        }

        public final ModelLoader<Uri, DataT> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new QMediaStoreUriLoader(this.context, multiModelLoaderFactory.build(File.class, this.dataClass), multiModelLoaderFactory.build(Uri.class, this.dataClass), this.dataClass);
        }
    }

    public static final class FileDescriptorFactory extends Factory<ParcelFileDescriptor> {
        public FileDescriptorFactory(Context context) {
            super(context, ParcelFileDescriptor.class);
        }
    }

    public static final class InputStreamFactory extends Factory<InputStream> {
        public InputStreamFactory(Context context) {
            super(context, InputStream.class);
        }
    }

    private static final class QMediaStoreUriFetcher<DataT> implements DataFetcher<DataT> {
        private static final String[] PROJECTION = {"_data"};
        private final Context context;
        private final Class<DataT> dataClass;
        private volatile DataFetcher<DataT> delegate;
        private final ModelLoader<File, DataT> fileDelegate;
        private final int height;
        private volatile boolean isCancelled;
        private final Options options;
        private final Uri uri;
        private final ModelLoader<Uri, DataT> uriDelegate;
        private final int width;

        QMediaStoreUriFetcher(Context context2, ModelLoader<File, DataT> modelLoader, ModelLoader<Uri, DataT> modelLoader2, Uri uri2, int i, int i2, Options options2, Class<DataT> cls) {
            this.context = context2.getApplicationContext();
            this.fileDelegate = modelLoader;
            this.uriDelegate = modelLoader2;
            this.uri = uri2;
            this.width = i;
            this.height = i2;
            this.options = options2;
            this.dataClass = cls;
        }

        public void loadData(Priority priority, DataCallback<? super DataT> dataCallback) {
            try {
                DataFetcher<DataT> buildDelegateFetcher = buildDelegateFetcher();
                if (buildDelegateFetcher == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed to build fetcher for: ");
                    sb.append(this.uri);
                    dataCallback.onLoadFailed(new IllegalArgumentException(sb.toString()));
                    return;
                }
                this.delegate = buildDelegateFetcher;
                if (this.isCancelled) {
                    cancel();
                } else {
                    buildDelegateFetcher.loadData(priority, dataCallback);
                }
            } catch (FileNotFoundException e) {
                dataCallback.onLoadFailed(e);
            }
        }

        private DataFetcher<DataT> buildDelegateFetcher() throws FileNotFoundException {
            LoadData buildDelegateData = buildDelegateData();
            if (buildDelegateData != null) {
                return buildDelegateData.fetcher;
            }
            return null;
        }

        private LoadData<DataT> buildDelegateData() throws FileNotFoundException {
            if (Environment.isExternalStorageLegacy()) {
                return this.fileDelegate.buildLoadData(queryForFilePath(this.uri), this.width, this.height, this.options);
            }
            return this.uriDelegate.buildLoadData(isAccessMediaLocationGranted() ? MediaStore.setRequireOriginal(this.uri) : this.uri, this.width, this.height, this.options);
        }

        public void cleanup() {
            DataFetcher<DataT> dataFetcher = this.delegate;
            if (dataFetcher != null) {
                dataFetcher.cleanup();
            }
        }

        public void cancel() {
            this.isCancelled = true;
            DataFetcher<DataT> dataFetcher = this.delegate;
            if (dataFetcher != null) {
                dataFetcher.cancel();
            }
        }

        public Class<DataT> getDataClass() {
            return this.dataClass;
        }

        public DataSource getDataSource() {
            return DataSource.LOCAL;
        }

        /* JADX INFO: finally extract failed */
        private File queryForFilePath(Uri uri2) throws FileNotFoundException {
            Cursor cursor = null;
            try {
                cursor = this.context.getContentResolver().query(uri2, PROJECTION, null, null, null);
                if (cursor == null || !cursor.moveToFirst()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed to media store entry for: ");
                    sb.append(uri2);
                    throw new FileNotFoundException(sb.toString());
                }
                String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                if (!TextUtils.isEmpty(string)) {
                    File file = new File(string);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return file;
                }
                StringBuilder sb2 = new StringBuilder();
                sb2.append("File path was empty in media store for: ");
                sb2.append(uri2);
                throw new FileNotFoundException(sb2.toString());
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }

        private boolean isAccessMediaLocationGranted() {
            return this.context.checkSelfPermission("android.permission.ACCESS_MEDIA_LOCATION") == 0;
        }
    }

    QMediaStoreUriLoader(Context context2, ModelLoader<File, DataT> modelLoader, ModelLoader<Uri, DataT> modelLoader2, Class<DataT> cls) {
        this.context = context2.getApplicationContext();
        this.fileDelegate = modelLoader;
        this.uriDelegate = modelLoader2;
        this.dataClass = cls;
    }

    public LoadData<DataT> buildLoadData(Uri uri, int i, int i2, Options options) {
        Uri uri2 = uri;
        ObjectKey objectKey = new ObjectKey(uri);
        QMediaStoreUriFetcher qMediaStoreUriFetcher = new QMediaStoreUriFetcher(this.context, this.fileDelegate, this.uriDelegate, uri2, i, i2, options, this.dataClass);
        return new LoadData<>(objectKey, qMediaStoreUriFetcher);
    }

    public boolean handles(Uri uri) {
        return VERSION.SDK_INT >= 29 && MediaStoreUtil.isMediaStoreUri(uri);
    }
}
