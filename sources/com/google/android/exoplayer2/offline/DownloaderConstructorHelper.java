package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSource.Factory;
import com.google.android.exoplayer2.upstream.DummyDataSource;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.PriorityDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSinkFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory;
import com.google.android.exoplayer2.upstream.cache.CacheUtil;
import com.google.android.exoplayer2.util.PriorityTaskManager;

public final class DownloaderConstructorHelper {
    private final Cache cache;
    private final CacheKeyFactory cacheKeyFactory;
    private final CacheDataSourceFactory offlineCacheDataSourceFactory;
    private final CacheDataSourceFactory onlineCacheDataSourceFactory;
    private final PriorityTaskManager priorityTaskManager;

    public DownloaderConstructorHelper(Cache cache2, Factory factory) {
        this(cache2, factory, null, null, null);
    }

    public DownloaderConstructorHelper(Cache cache2, Factory factory, Factory factory2, DataSink.Factory factory3, PriorityTaskManager priorityTaskManager2) {
        this(cache2, factory, factory2, factory3, priorityTaskManager2, null);
    }

    public DownloaderConstructorHelper(Cache cache2, Factory factory, Factory factory2, DataSink.Factory factory3, PriorityTaskManager priorityTaskManager2, CacheKeyFactory cacheKeyFactory2) {
        Factory factory4;
        Factory factory5;
        Cache cache3 = cache2;
        PriorityTaskManager priorityTaskManager3 = priorityTaskManager2;
        if (priorityTaskManager3 != null) {
            Factory factory6 = factory;
            factory4 = new PriorityDataSourceFactory(factory, priorityTaskManager3, -1000);
        } else {
            factory4 = factory;
        }
        if (factory2 != null) {
            factory5 = factory2;
        } else {
            factory5 = new FileDataSource.Factory();
        }
        Cache cache4 = cache2;
        Factory factory7 = factory5;
        CacheKeyFactory cacheKeyFactory3 = cacheKeyFactory2;
        CacheDataSourceFactory cacheDataSourceFactory = new CacheDataSourceFactory(cache4, factory4, factory7, factory3 == null ? new CacheDataSinkFactory(cache2, CacheDataSink.DEFAULT_FRAGMENT_SIZE) : factory3, 1, null, cacheKeyFactory3);
        this.onlineCacheDataSourceFactory = cacheDataSourceFactory;
        CacheDataSourceFactory cacheDataSourceFactory2 = new CacheDataSourceFactory(cache4, DummyDataSource.FACTORY, factory7, null, 1, null, cacheKeyFactory3);
        this.offlineCacheDataSourceFactory = cacheDataSourceFactory2;
        this.cache = cache3;
        this.priorityTaskManager = priorityTaskManager3;
        this.cacheKeyFactory = cacheKeyFactory2;
    }

    public Cache getCache() {
        return this.cache;
    }

    public CacheKeyFactory getCacheKeyFactory() {
        CacheKeyFactory cacheKeyFactory2 = this.cacheKeyFactory;
        return cacheKeyFactory2 != null ? cacheKeyFactory2 : CacheUtil.DEFAULT_CACHE_KEY_FACTORY;
    }

    public PriorityTaskManager getPriorityTaskManager() {
        PriorityTaskManager priorityTaskManager2 = this.priorityTaskManager;
        return priorityTaskManager2 != null ? priorityTaskManager2 : new PriorityTaskManager();
    }

    public CacheDataSource createCacheDataSource() {
        return this.onlineCacheDataSourceFactory.createDataSource();
    }

    public CacheDataSource createOfflineCacheDataSource() {
        return this.offlineCacheDataSourceFactory.createDataSource();
    }
}
