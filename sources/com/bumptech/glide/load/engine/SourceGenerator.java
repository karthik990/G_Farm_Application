package com.bumptech.glide.load.engine;

import android.util.Log;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.DataFetcher.DataCallback;
import com.bumptech.glide.load.engine.DataFetcherGenerator.FetcherReadyCallback;
import com.bumptech.glide.load.model.ModelLoader.LoadData;
import com.bumptech.glide.util.LogTime;
import java.util.Collections;
import java.util.List;

class SourceGenerator implements DataFetcherGenerator, FetcherReadyCallback {
    private static final String TAG = "SourceGenerator";

    /* renamed from: cb */
    private final FetcherReadyCallback f253cb;
    private Object dataToCache;
    private final DecodeHelper<?> helper;
    private volatile LoadData<?> loadData;
    private int loadDataListIndex;
    private DataCacheKey originalKey;
    private DataCacheGenerator sourceCacheGenerator;

    SourceGenerator(DecodeHelper<?> decodeHelper, FetcherReadyCallback fetcherReadyCallback) {
        this.helper = decodeHelper;
        this.f253cb = fetcherReadyCallback;
    }

    public boolean startNext() {
        Object obj = this.dataToCache;
        if (obj != null) {
            this.dataToCache = null;
            cacheData(obj);
        }
        DataCacheGenerator dataCacheGenerator = this.sourceCacheGenerator;
        if (dataCacheGenerator != null && dataCacheGenerator.startNext()) {
            return true;
        }
        this.sourceCacheGenerator = null;
        this.loadData = null;
        boolean z = false;
        while (!z && hasNextModelLoader()) {
            List loadData2 = this.helper.getLoadData();
            int i = this.loadDataListIndex;
            this.loadDataListIndex = i + 1;
            this.loadData = (LoadData) loadData2.get(i);
            if (this.loadData != null && (this.helper.getDiskCacheStrategy().isDataCacheable(this.loadData.fetcher.getDataSource()) || this.helper.hasLoadPath(this.loadData.fetcher.getDataClass()))) {
                startNextLoad(this.loadData);
                z = true;
            }
        }
        return z;
    }

    private void startNextLoad(final LoadData<?> loadData2) {
        this.loadData.fetcher.loadData(this.helper.getPriority(), new DataCallback<Object>() {
            public void onDataReady(Object obj) {
                if (SourceGenerator.this.isCurrentRequest(loadData2)) {
                    SourceGenerator.this.onDataReadyInternal(loadData2, obj);
                }
            }

            public void onLoadFailed(Exception exc) {
                if (SourceGenerator.this.isCurrentRequest(loadData2)) {
                    SourceGenerator.this.onLoadFailedInternal(loadData2, exc);
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public boolean isCurrentRequest(LoadData<?> loadData2) {
        LoadData<?> loadData3 = this.loadData;
        return loadData3 != null && loadData3 == loadData2;
    }

    private boolean hasNextModelLoader() {
        return this.loadDataListIndex < this.helper.getLoadData().size();
    }

    /* JADX INFO: finally extract failed */
    private void cacheData(Object obj) {
        String str = TAG;
        long logTime = LogTime.getLogTime();
        try {
            Encoder sourceEncoder = this.helper.getSourceEncoder(obj);
            DataCacheWriter dataCacheWriter = new DataCacheWriter(sourceEncoder, obj, this.helper.getOptions());
            this.originalKey = new DataCacheKey(this.loadData.sourceKey, this.helper.getSignature());
            this.helper.getDiskCache().put(this.originalKey, dataCacheWriter);
            if (Log.isLoggable(str, 2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Finished encoding source to cache, key: ");
                sb.append(this.originalKey);
                sb.append(", data: ");
                sb.append(obj);
                sb.append(", encoder: ");
                sb.append(sourceEncoder);
                sb.append(", duration: ");
                sb.append(LogTime.getElapsedMillis(logTime));
                Log.v(str, sb.toString());
            }
            this.loadData.fetcher.cleanup();
            this.sourceCacheGenerator = new DataCacheGenerator(Collections.singletonList(this.loadData.sourceKey), this.helper, this);
        } catch (Throwable th) {
            this.loadData.fetcher.cleanup();
            throw th;
        }
    }

    public void cancel() {
        LoadData<?> loadData2 = this.loadData;
        if (loadData2 != null) {
            loadData2.fetcher.cancel();
        }
    }

    /* access modifiers changed from: 0000 */
    public void onDataReadyInternal(LoadData<?> loadData2, Object obj) {
        DiskCacheStrategy diskCacheStrategy = this.helper.getDiskCacheStrategy();
        if (obj == null || !diskCacheStrategy.isDataCacheable(loadData2.fetcher.getDataSource())) {
            this.f253cb.onDataFetcherReady(loadData2.sourceKey, obj, loadData2.fetcher, loadData2.fetcher.getDataSource(), this.originalKey);
            return;
        }
        this.dataToCache = obj;
        this.f253cb.reschedule();
    }

    /* access modifiers changed from: 0000 */
    public void onLoadFailedInternal(LoadData<?> loadData2, Exception exc) {
        this.f253cb.onDataFetcherFailed(this.originalKey, exc, loadData2.fetcher, loadData2.fetcher.getDataSource());
    }

    public void reschedule() {
        throw new UnsupportedOperationException();
    }

    public void onDataFetcherReady(Key key, Object obj, DataFetcher<?> dataFetcher, DataSource dataSource, Key key2) {
        this.f253cb.onDataFetcherReady(key, obj, dataFetcher, this.loadData.fetcher.getDataSource(), key);
    }

    public void onDataFetcherFailed(Key key, Exception exc, DataFetcher<?> dataFetcher, DataSource dataSource) {
        this.f253cb.onDataFetcherFailed(key, exc, dataFetcher, this.loadData.fetcher.getDataSource());
    }
}
